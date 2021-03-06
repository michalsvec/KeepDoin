<?php

/**
 * This file is part of the Nette Framework.
 *
 * Copyright (c) 2004, 2010 David Grudl (http://davidgrudl.com)
 *
 * This source file is subject to the "Nette license", and/or
 * GPL license. For more information please see http://nette.org
 * @package Nette\Web
 */



/**
 * Extended HTTP URL.
 *
 * <pre>
 * http://nette.org/admin/script.php/pathinfo/?name=param#fragment
 *                 \_______________/\________/
 *                        |              |
 *                   scriptPath       pathInfo
 * </pre>
 *
 * - scriptPath:  /admin/script.php (or simply /admin/ when script is directory index)
 * - pathInfo:    /pathinfo/ (additional path information)
 *
 * @author     David Grudl
 *
 * @property   string $scriptPath
 * @property-read string $pathInfo
 */
class UriScript extends Uri
{
	/** @var string */
	private $scriptPath = '/';



	/**
	 * Sets the script-path part of URI.
	 * @param  string
	 * @return UriScript  provides a fluent interface
	 */
	public function setScriptPath($value)
	{
		$this->updating();
		$this->scriptPath = (string) $value;
		return $this;
	}



	/**
	 * Returns the script-path part of URI.
	 * @return string
	 */
	public function getScriptPath()
	{
		return $this->scriptPath;
	}



	/**
	 * Returns the base-path.
	 * @return string
	 */
	public function getBasePath()
	{
		$pos = strrpos($this->scriptPath, '/');
		return $pos === FALSE ? '' : substr($this->path, 0, $pos + 1);
	}



	/**
	 * Returns the additional path information.
	 * @return string
	 */
	public function getPathInfo()
	{
		return (string) substr($this->path, strlen($this->scriptPath));
	}

}
