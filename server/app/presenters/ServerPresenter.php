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
    	
    	$this->setLayout(FALSE);
    	$this->absoluteUrls = TRUE;
    }
	
    public function afterRender()
    {
	    if (Environment::isProduction()) {
	        $this->setView('json');
	    } else {
	        $this->setView('text');
	    }
	    $this->template->data = json_encode($this->data);
    }
    
	public function renderDefault()
	{
		$this->data = 'test';
	}

}
