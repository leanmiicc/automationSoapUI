<?php 
// File Name: proxy.php
//if (!isset($_GET['url'])) die();
//$url = urldecode($_GET['url']);
//$url = 'http://' . str_replace('http://', '', $url); // Avoid accessing the file system
// $url = 'http://24.232.50.153:8080/cgi-bin/services_status.py?cmd=get_status_services';
// echo file_get_contents($url);

require_once('../classes/guzzle.phar');

$client = new Guzzle\Http\Client("http://localhost/dsa");

$request = $client->get('snmp/reset_cm/121212121212');
$request->setAuth('stechs', 'stechs');

$response = $request->send();
echo "<pre>";print_r($response);echo "</pre>";

?>