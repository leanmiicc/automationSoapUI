<?php 
	define('relativeAppPath', '../../../');
	//define('relativeAppPath', '');
	require_once (relativeAppPath.'php-activerecord/ActiveRecord.php');	
	
	require_once(relativeAppPath.'classes/Setting.php');
	
	$settingObject = new Setting();

	// $settingObject->load();

	//$setting = $settingObject->getGeneralSetting(); 
	
	$setting = new GeneralSetting(); 
	
	$setting->theme = 'Spacelab';
	$setting->databaseLocation = '10.0.0.21';
	$setting->databaseName ='dsa_andres_test';
	$setting->databaseUser = 'root';
	$setting->databasePassword = 'stsr00t';
	$setting->serviceLocation  = '10.0.0.100';
	$setting->serviceName = 'bdp';
	$setting->serviceUser = 'dsa-ext';
	$setting->servicePassword = 'stsr00t';
	$setting->serviceWS = 'http://10.0.0.100:8001/cgi-bin';
	$setting->fdmLocation = 'http://10.0.0.100:87/fdm/api/v0.1/';
	$setting->fdmUser='stechs';
	$setting->fdmPassword='stechs';
	$setting->bdpLocation='http://10.0.0.100:82/bdp/api/v0.1/';
	$setting->bdpUser='stechs';
	$setting->bdpPassword='stechs';
	$setting->bdpIPv6Location='10.0.0.100:443';
	$setting->bdpIPv6User='dsa-ext';
	$setting->bdpIPv6Password='stsr00t';
	$setting->bdpIPv6DBName='bdp6';
	$setting->bdp6SrvLocation='http://10.0.0.100:8060/bdp6/api/v0.1/';
	$setting->bdp6SrvUser='stechs';
	$setting->bdp6SrvPassword='stechs';
	
	require_once (relativeAppPath.'languages/language-en.php');
	require_once (relativeAppPath.'languages/language.php');
	require_once (relativeAppPath.'headers/database.php');
	require_once(relativeAppPath.'classes/ActionResponse.php');
	require_once (relativeAppPath.'actions/Action.php');

	// Loggin setting
	require_once(relativeAppPath.'log4php/Logger.php');

	Logger::configure(relativeAppPath."config/log_config.xml");
	
	$log = Logger::getLogger('Provisioning');
?>
