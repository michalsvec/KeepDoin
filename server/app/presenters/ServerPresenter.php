<?php

/**
 * KeepGoin'
 *
 * @author Jan Javorek <honza@javorek.net>
 * @copyright Copyright (c) 2010 Jan Javorek
 */



/**
 * Server presenter.
 *
 *
 * http://www.gen-x-design.com/archives/create-a-rest-api-with-php/
 * http://www.gen-x-design.com/archives/making-restful-requests-in-php/
 * http://www.recessframework.org/page/towards-restful-php-5-basic-tips
 * http://stackoverflow.com/questions/359047/php-detecting-request-type-get-post-put-or-delete
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



    public function renderGetUsers()
    {
    	$this->data = array();
    	
        // email added - necessary for gravatars
        $this->data['users'] = dibi::query('
            SELECT id, real_name, rank_id, email
            FROM [users]
        ')->fetchAll();
        
    }



    public function renderGetFriends($id)
    {
    	$this->data = array();
    	
    	
    	$friendships = dibi::fetchAll("SELECT * FROM [friendships] WHERE [user1_id] = %i OR user2_id = %i", $id, $id);

		if(empty($friendships)) {
			$this->data['status'] = FALSE;
			return;
		}
    	
    	$friends = array();
    	foreach($friendships as $friendship) {
    		$friends[] = $friendship->user1_id;
    		$friends[] = $friendship->user2_id;
    	}
    	

        $this->data['friends'] = dibi::fetchAll('
            SELECT id, real_name as name, rank_id, email
            FROM [users] WHERE id IN ('.join(", ", $friends).') AND id != %i
        ', $id);
    }



    public function renderGetFriendsAndUser($id)
    {
    	$this->data = array();
    	
		$friendships = dibi::fetchAll("SELECT * FROM [friendships] WHERE [user1_id] = %i OR user2_id = %i", $id, $id);

        $query[] = 'SELECT id, real_name as name, rank_id, email
            FROM [users] WHERE'; 

		if(!empty($friendships)) {
	    	$ids = array();

	    	foreach($friendships as $friendship) {
	    		$ids[] = $friendship->user1_id;
	    		$ids[] = $friendship->user2_id;
	    	}
	    	
	    	$query[] = '([id] IN ('.join(", ", $ids).')) OR ';
		}
    	
    	$query[] = " [id] = %i";
    	$query[] = $id;

        $this->data['friendsanduser'] = dibi::fetchAll($query);
    }


	/**
	 * TODO: maybe add some extra parameters
	 */
	public function renderPostRegistration($id)
    {
    	$email = $id;
		$user = dibi::query('
		    SELECT *
		    FROM [users]
		    WHERE [email] = %s
		', $email)->fetch();

		if(empty($user)) {
			dibi::query('INSERT INTO [users]', array('email' => $email));
			
			$this->data['status'] = 'true';
			$this->data['id'] = dibi::insertId();
		}
		else {
			$this->data['status'] = 'false';
		}
	}



    public function renderGetLogin($id)
    {
    	$user = dibi::query('
            SELECT *
            FROM [users] 
            WHERE [email]=%s
        ', $id)->fetch();
        
        if(!empty($user)) {
			$this->data['status'] = 'true';
			$this->data['id'] = $user['id'];

		}
		else
			$this->data['status'] = 'false';
    }



    public function renderPostFriendship($id)
    {
    	$input = $this->getInput();

    	$email = $input->email;
    	$id    = $input->id;

    	
		$mail = new Mail;
		$mail->setFrom('KeepDoin <god@heaven.com>');
		$mail->addTo($email);
		$mail->setSubject('Požadavek na přátelství');
		$mail->setBody("ahojkyyyyy. http://todogame.michalsvec.cz/api/friendship/$id?email=$email");
		$mail->send();

    	$this->data = TRUE;
    }



    public function renderGetFriendship($id)
    {
    
    	$id_2nd = dibi::fetchSingle("SELECT [id] FROM [users] WHERE [email] = %s", $_GET['email']);
    	dibi::query('INSERT INTO [friendships] VALUES (%i, %i)', $id, $id_2nd);

		$this->data = TRUE;
    }



    // TODO PUT/DELETE handling
    // TODO read http://www.gen-x-design.com/archives/create-a-rest-api-with-php/

}
