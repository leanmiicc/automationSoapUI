<?php

require_once('test_setup.php');

require_once(relativeAppPath.'classes/service/ServiceFactory.php');

class ServiceFactoryTest extends PHPUnit_Framework_TestCase
{
    
    
    
	protected function setUp()
    {
    	$this->serviceFactoryInstance = ServiceFactory::getServices();
    	 
	}
    
    public static function setUpBeforeClass()
    {
    	ServiceSubscriptionModel::delete_all(array("conditions"=>array("subscription_id"=>61) ));
    	HsiInstanceModel::delete_all();
    	DummyInstanceModel::delete_all();
    }
    
    
    public function testCreateService()
    {
    	// checking the flags
    	$s = new ServiceFactory();

    	try{
    		//espero que pueda instancias las clases dummy
    		$t = $s->getTemplateByName("scm");
    		$r = $t->createInstance(2, 61);
    		$this->assertEquals(null, $r->getInstanceObject()['type']);
    		$this->assertEquals(true, true);
    	}catch (ServiceNotFoundException $e){
    		echo $e;
    		$this->assertEquals(true, false);
    	}
    	 
    	
//le agrego un parametro de nombre
    	try{
    		//espero que pueda instancias las clases dummy
    		$t = $s->getTemplateByName("scm");
    		$t->createInstance(2, 61, array("99999"=>"subsource_id"), array("type"=>"TSL"));
    		$this->assertEquals("TSL", $r->getInstanceObject()['type']);
    		$this->assertEquals(true,  isset($r->getInstanceObject()['crmcode']) );
    	}catch (ServiceNotFoundException $e){
    		echo $e;
    		$this->assertEquals(true, false);
    	}
    	 
    	}
    
    
    
}
?>