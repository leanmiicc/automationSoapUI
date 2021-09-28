<?php

require_once('test_setup.php');

require_once(relativeAppPath.'classes/ioc/IOC.php');


 
class DI extends PHPUnit_Framework_TestCase
{
    
    public function testServicesTypes()
    {
    	
    	$i = IOC::getInstance();
    	
		
		$a  = $i->container->get('HsiInstance');
       
       	print_r($a);
    }
    
    
    
}
?>