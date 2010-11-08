<?php

/**
 * ToDoGame
 *
 * @author Jan Javorek <honza@javorek.net>
 * @copyright Copyright (c) 2010 Jan Javorek
 */



/**
 * Server presenter.
 *
 * @author Jan Javorek <honza@javorek.net>
 */
class ServerPresenter extends BasePresenter
{
	protected $data = NULL;
	
    protected function startup()
    {
    	parent::startup();
    	
        if (!method_exists($this, self::formatRenderMethod($this->getView()))) {
            throw new BadRequestException("API doesn't contain any '" . $this->getView() . "' method for HTTP " . strtoupper(Environment::getHttpRequest()->getMethod()) . ".");
        }
        
        $this->setLayout(FALSE);
        $this->absoluteUrls = TRUE;
    }
	
    public function afterRender()
    {
    	Debug::$showBar = FALSE;
    	
	    if (Environment::isProduction()) {
	        $this->setView('json');
	        
	    } else {
	        $this->setView('text');
	    }
	    $this->template->data = json_encode($this->data);
    }
    
	protected static function formatRenderMethod($view)
	{
        return 'render' . ucfirst(strtolower(Environment::getHttpRequest()->getMethod())) . ucfirst($view);
	}
	
	protected function getInput()
	{
		return json_decode(file_get_contents('php://input'));
	}
    
    
    
    /* *********************** API METHODS *********************** */
    
	public function renderGetUser($id)
	{
		$this->data = dibi::query('
		    SELECT *
		    FROM [users]
		    WHERE [id] = %i
		', $id)->fetch();
		unset($this->data['password']);
		
		// TODO post-db hook to compute score every time
	}
	
    public function renderGetTasks($id, $orderBy = 'smart')
    {
    	$userId = $id; // to be clear
    	$this->data = array();
    	$urgentPeriod = 1; // days
    	
    	// ordering
    	switch ($orderBy) {
    		case 'deadline':
    			$orderBy = array(
    			    'deadline' => 'DESC',
    			    'task_priority' => 'DESC',
    			);
    			break;
    		case 'priority':
    			$orderBy = array(
                    'task_priority' => 'DESC',
                    'deadline' => 'DESC',
                );
    			break;
    		default: // smart
    			$orderBy = array(
    			    'is_urgent' => 'DESC',
                    'task_priority' => 'DESC',
                    'deadline' => 'DESC',
                );
    			break;
    	}
    	$orderBy = array_merge(array(
           'category_priority' => 'DESC', // always highest priority
        ), $orderBy);
    	
        // tasks
        $this->data['tasks'] = dibi::query('
            SELECT [tasks].*,
                [priorities.name] AS [priority_name], [priorities.value] AS [task_priority],
                [categories.priority] AS [category_priority],
                IF ([deadline] < DATE_ADD(NOW(), INTERVAL %i DAY) AND [deadline] >= NOW(), 1, 0) AS [is_urgent]
            FROM [tasks]
            JOIN [categories]
            ON [tasks.category_id] = [categories.id]
            JOIN [priorities]
            ON [tasks.priority_id] = [priorities.id]
            WHERE [categories.user_id] = %i
            ORDER BY %by
        ', $urgentPeriod, $userId, $orderBy)->fetchAll();
        
        // categories
        $this->data['categories'] = dibi::query('
            SELECT [id], [name], [priority]
            FROM [categories]
            WHERE [categories.user_id] = %i
            ORDER BY [priority] DESC
        ', $userId)->fetchAll();
        
        // TODO post-db hook to compute rewards every time
    }
    
    public function renderPostFriendship()
    {
    	dibi::query('INSERT INTO [friendships]', (array)$this->getInput());
    	$this->data = TRUE;
    	
    	// TODO check duplicities
    }
    
    // TODO PUT/DELETE handling
    // TODO read http://www.gen-x-design.com/archives/create-a-rest-api-with-php/

}
