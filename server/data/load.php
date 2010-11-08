<?php

/**
 * Database setup file.
 *
 * @author Jan Javorek <honza@javorek.net>
 * @copyright Copyright (c) 2010 Jan Javorek
 */



// settings
define('PRODUCTION', FALSE);
define('DROP_TABLES', TRUE);
define('CLI_TASK', (php_sapi_name() == 'cli'));

// directories
if (!defined('ROOT_DIR')) {
    define('ROOT_DIR', dirname(__FILE__) . '/..');
}

define('WWW_DIR', ROOT_DIR . '/public');
define('APP_DIR', ROOT_DIR . '/app');
define('LIBS_DIR', ROOT_DIR . '/libs');
define('TEMP_DIR', ROOT_DIR . '/temp');

define('SCHEMA_DIR', ROOT_DIR . '/data');
define('SQL_DIR', ROOT_DIR . ((PRODUCTION)? '/data/fixtures' : '/data/test-fixtures'));

// new line setting
$nl = (CLI_TASK)? "\n" : '<br>';

// hello
echo "Welcome to task for loading database schema and fixtures!$nl$nl";
echo "Mode? [" . ((PRODUCTION)? 'production' : 'test') . "].";
if (!PRODUCTION) {
	echo " To switch to production, edit PRODUCTION constant to TRUE in " . __FILE__ . '.';
}
echo $nl;
echo "Drop tables? [" . ((DROP_TABLES)? 'yes' : 'no') . "].$nl$nl";

// bootstrap
require LIBS_DIR . '/Nette/loader.php';

if (PRODUCTION) {
	Environment::setMode(Environment::PRODUCTION);
    Environment::setName('production');
} else {
	Environment::setMode(Environment::DEVELOPMENT);
    Environment::setName('development');
}
Debug::enable();

$config = Environment::loadConfig();
echo "Connecting to [{$config->database->database}] at [{$config->database->host}].$nl";
try {
    dibi::connect($config->database);
} catch (Exception $e) {
	print "Cannot connect to database.$nl";
	print $e->getCode() . "$nl";
	print $e->getTraceAsString();
	die;
}

// drop tables
if (defined('DROP_TABLES') && DROP_TABLES === TRUE) {
	do {
	    foreach (dibi::query('SHOW TABLES') as $row) {
	        $table = reset($row);
	        echo "Dropping [$table].";
	        try {
	        	dibi::query('DROP TABLE IF EXISTS %n', $table);
	        } catch (DibiDriverException $e) {
	        	echo " CONSTRAINT FAIL";
	        }
	        echo $nl;
	    }
	    $tablesExist = count(dibi::query('SHOW TABLES'));
	} while ($tablesExist);
	echo $nl;
}

// load fixtures
echo "Loading [schema.sql].$nl$nl";
dibi::loadFile(SCHEMA_DIR . '/schema.sql');

$files = 0;
foreach (glob(SQL_DIR . '/*.sql') as $filename) {
  if (is_file($filename)) {
    $basename = basename($filename);
    echo "Loading [$basename].$nl";
    dibi::loadFile($filename);
    $files++;
  }
}
if ($files) {
	echo $nl;
}

// bye bye
echo "Success loading fixtures!$nl";

