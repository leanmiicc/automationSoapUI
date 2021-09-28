<?php

require_once('phpTest/xUnit/test_setup.php');
require_once('classes/subscription/DocsisSubscription.php');

class DocsisSubscriptionTest extends PHPUnit_Framework_TestCase
{


    protected function setUp()
    {

    }	


    public function testCreation(){
        $sub = new DocsisSubscription();
    
        // $sub->create();

        $this->assertNotNull($sub);
    }

    public function testRetrieveSubscription(){
		$recordSub = SubscriptionModel::last();
    	$sub = Subscription::find($recordSub->id);

    	$this->assertNotNull($sub);
    	$this->assertInstanceOf('DocsisSubscription',$sub);
    	//$this->assertEquals($sub->id,$recordSub->id);
    }
}

?>