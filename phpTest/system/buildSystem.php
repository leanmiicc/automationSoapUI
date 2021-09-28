<?php
	@session_start();

	define('relativeAppPath','../../');	
	require_once(relativeAppPath.'headers/log.php');
	require_once(relativeAppPath.'headers/functions.php');
	require_once(relativeAppPath.'php-activerecord/ActiveRecord.php');	
	require_once(relativeAppPath.'classes/Setting.php');
	
	// Setting the global configuration
	$settingObject = new Setting();
	$settingObject->load();
	$setting = $settingObject->getGeneralSetting(); 
	

	require_once (relativeAppPath.'languages/language-'.$setting->language.'.php');
	require_once (relativeAppPath.'languages/language.php');
	
	require_once(relativeAppPath.'headers/database.php');
	require_once(relativeAppPath.'classes/ActionResponse.php');
	require_once(relativeAppPath.'actions/Action.php'); 
	
	require_once(relativeAppPath.'actions/Subscription.php'); 	
	require_once(relativeAppPath.'classes/Setting.php');
	require_once(relativeAppPath.'classes/GeneralSetting.php');	
	
	require_once (relativeAppPath.'log4php/Logger.php');
	Logger::configure(relativeAppPath.'config/log_config.xml');

	require_once(relativeAppPath.'classes/Setting.php');

	ini_set('max_execution_time', 1500);

	// Create subscriber
	$fh = fopen('nombres.csv','r');
	echo "Creating subscribers<br/>";

	Subscriber::delete_all("1=1");

	while ($line = fgets($fh)) {
		$subscriber = new subscriber();
		$values = explode(";", $line);
		$subscriber->number = $values[0];
		$subscriber->last_name = $values[1];
		$subscriber->name = $values[2];
		$subscriber->phone = $values[0] ."-". $values[0];
		$subscriber->email = strtolower ($values[2] . "_". $values[1]) . "@" . "test.com.ar";
		$subscriber->address = $values[2].", " . $values[1]. $values[0];

	  	$subscriber->save();
	}
	fclose($fh);

	echo "subscribers have been created<br/>";
	// Create product

	// Create the subscription

	// Create scope

	// 

?>