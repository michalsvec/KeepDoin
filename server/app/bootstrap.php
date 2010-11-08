<?php

/**
 * Bootstrap file.
 *
 * @author Jan Javorek <honza@javorek.net>
 * @copyright Copyright (c) 2010 Jan Javorek
 */



require LIBS_DIR . '/Nette/loader.php';

Debug::$strictMode = TRUE;
Debug::enable();

$config = Environment::loadConfig();

$application = Environment::getApplication();
$application->errorPresenter = 'Error';
//$application->catchExceptions = TRUE;

dibi::connect($config->database);

$router = $application->getRouter();

$router[] = new Route('api/<action>/<id>', array(
    'presenter' => 'Server',
    'id' => NULL,
));

$application->run();
