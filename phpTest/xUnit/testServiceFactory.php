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
    }
    
    
    public function testServicesTypes()
    {
        // checking the flags
        $this->assertEquals(5, count($this->serviceFactoryInstance) );
        $this->assertContains('HSI', $this->serviceFactoryInstance );
        $this->assertContains('VOIP', $this->serviceFactoryInstance );
        $this->assertContains('TLS', $this->serviceFactoryInstance );   
    }
    
    public function testGetServiceTamplates()
    {
    	// checking the flags
    	$s = new ServiceFactory();
    try{
    	//espero que no pueda hacer nada
    	$s->a();
    	}catch (ServiceNotFoundException $e){
    		$this->assertEquals(true, true);
    	}
    	

    	try{
    		
    		$s->HsiInstance();
    	}catch (ServiceNotFoundException $e){
    		echo $e;
    		$this->fail('No se encontro la calse para manejar instancia de HSI');
    	}
    	 
    	
    try{
    		//espero que pueda instancias las clases
    		$s->HsiTemplate();
    		$s->HsiInstance();
    		$this->assertEquals(true, true);
    	}catch (ServiceNotFoundException $e){
    		echo $e;
    		$this->assertEquals(true, false);
    	}
    	
    	try{
    		//creo una instancia
    		$s->HsiTemplate()->createInstance(1, 61);
    		$this->assertEquals(true, true);
    	}catch (ServiceNotFoundException $e){
    		echo $e;
    		$this->assertEquals(true, false);
    	}
    	
    }
    
    
    public function testWrongServicesName() {
        $s = new ServiceFactory();
        
        try{
            $s->HsiTemplate()->createInstanceByServiceName("HSI-BASIC-NO-EXISTE!!", 61);
        }catch (Exception $e){
            $this->assertInstanceOf(ServiceNotFoundException::class, $e);
        }        
    }
        
        
    
    /**
     * Obtengo todos los servicios asignados a una subscripcion
     */
    public function testGetAllServices() {
    	$s = new ServiceFactory();
    	
    	try{
    		//creo una instancia
    	    $s->HsiTemplate()->createInstanceByServiceName("HSI-BASIC", 61,  array("999999"=>'subscriptionId'), array( "trafficProfile"=>'pepe', "gd"=>array("ipv4"=>array("address"=>"9.9.0.1") )   ) );
    		$this->assertEquals(true, true);
    	}catch (ServiceNotFoundException $e){
    		echo $e;
    		$this->assertEquals(true, false);
    	}
    	
    	$services =  $s->getServicesFromSubscriptionId(61);
    	    	
    	$this->assertEquals(2,count($services) );
    	
    	
    	$service =  $s->getService($services[1]['srvId'], 'HSI');
    	
    	$this->assertNotNull($service);
    	$this->assertEquals($service->hsiInstance['trafficprofile'], "pepe" );

    	$this->assertEquals($service->hsiInstance['gd_ipv4_address'], "9.9.0.1" );
    	 
    	$this->assertEquals('inactive', $service->getStatus() );
    	$this->assertTrue($service->inStatus('inactive'));
    	
    }
    
    
    /**
     * Obtengo todos los servicios asignados a una subscripcion
     */
    public function testCanActivate() {
        $s = new ServiceFactory();
        
        $services =  $s->getServicesFromSubscriptionId(61);
        
        $service =  $s->getService($services[0]['srvId'], 'HSI');
        
        $this->assertNotNull($service);
        
        $this->assertEquals($service->getStatus(), 'inactive');
        $this->assertEquals($service->inStatus('inactive'),true);
        
        
        $this->assertTrue($service->canStatusTo('active'));
        $this->assertFalse($service->canStatusTo('suspended'));
        
        
    }
    
    /**
     * Obtengo todos los servicios asignados a una subscripcion
     */
    public function testActivateService() {
        $s = new ServiceFactory();
        
        $services =  $s->getServicesFromSubscriptionId(61);
        
        $service =  $s->getService($services[0]['srvId'], 'HSI' );
        
        $service->to_json();
        $this->assertNotNull($service);
        
        $service->changeStatus("active");
        
        
    }
    
    
}
?>