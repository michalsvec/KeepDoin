<?php

/* Diffie-Hellman - key exchange */

function dh_get_tosend($my_integer)
{
	$dh_p = gmp_init('265252859812191058636308479999999');
	$dh_g = gmp_init('5');

	return gmp_powm($dh_g, $my_integer, $dh_p);
}

function dh_get_secret($my_integer, $received)
{
	$dh_p = gmp_init('265252859812191058636308479999999');

	return gmp_powm($received, $my_integer, $dh_p);
}


/* encryption/decryption routines */

function encrypt($plainText, $keyString)
{
	$cipher = mcrypt_module_open('rijndael-128', '', 'ecb', '');
	$iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($cipher), MCRYPT_RAND);
	
	mcrypt_generic_init($cipher, $keyString, $iv);
	
	$metaText = strlen($plainText) . '|' . $plainText;
	$cipherText = mcrypt_generic($cipher, $metaText);
	
	mcrypt_generic_deinit($cipher);
	mcrypt_module_close($cipher);
	
	return base64_encode($cipherText);
}

function decrypt($cipherText, $keyString)
{
	$encrypted_string = base64_decode($cipherText);
	
	$cipher = mcrypt_module_open('rijndael-128', '', 'ecb', '');
	$iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($cipher), MCRYPT_RAND);
	
	mcrypt_generic_init($cipher, $keyString, $iv);
	
	$cipherText = base64_decode($cipherText);
	$metaText = mdecrypt_generic($cipher, $cipherText);
	
	mcrypt_generic_deinit($cipher);
	mcrypt_module_close($cipher);
	
	list($length, $plainText) = explode('|', $metaText, 2);
	
	return substr($plainText, 0, $length);
}
