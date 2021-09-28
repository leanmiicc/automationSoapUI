<?php

require_once('phpTest/xUnit/test_setup.php');
require_once('classes/subscription/GponSubscription.php');

class GponSubscriptionTest extends PHPUnit_Framework_TestCase
{

    var $paramsActiveStatusGpon = array(
        'sub_id'=>'',
        'license-value'=>'',
        'ont_sn' => '12341234123434',
        'technology_id'=>'gpon',
        'modem_subscriber'=>'002 - Perez Juan',
        'number'=>'',
        'name'=>'',
        'last_name'=>'',
        'address'=>'',
        'phone'=>'',
        'email'=>'',
        'product_id'=>'4',
        'billing_status_id'=>'1',
        'modem_macaddr'=>'',
        'mta_macaddr'=>'',
        'config_file'=>'',
        'activation_code_date'=>'',
        'activation_code'=>'',
        'cpe6_flag'=>0,
        'cpe4-tag-flag'=>0,
        'mta-tag-flag'=>0,
        'cpe4_tag_id'=>'',
        'mta_tag_id'=>'',
        'isNewSubscriber'=>false,
        'responseType'=>'default'
    );

    protected function setUp()
    {
        $this->paramsActiveStatusGpon = json_decode(json_encode($this->paramsActiveStatusGpon),false);
    }	


    /*public function testCreation(){
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
    }*/

    public function testRollbackGponSubscription(){
        $sub = new GponSubscription();
        $lastSubBefore = SubscriptionModel::last();

        $sub->setSubscriber(Subscriber::first());

        $sub->setParams($this->paramsActiveStatusGpon);

        $sub->create();

        $this->assertNotNull($sub);
        $this->assertNotEquals(SubscriptionModel::last()->id,$lastSubBefore->id);

        $sub->rollback();
        // $this->assertInstanceOf('DocsisSubscription',$sub);
        $this->assertEquals(SubscriptionModel::last()->id,$lastSubBefore->id);
    }

    public function testRollbackGponSubscriptionWithBillingStatus(){
        $sub = new GponSubscription();
        $lastSubBefore = SubscriptionModel::last();

        $sub->setSubscriber(Subscriber::first());

        $sub->setParams($this->paramsActiveStatusGpon);

        $sub->create();

        $this->assertNotNull($sub);
        $this->assertNotEquals(SubscriptionModel::last()->id,$lastSubBefore->id);

        $sub->generateStatus();  

        $sub->rollback();
        // $this->assertInstanceOf('DocsisSubscription',$sub);
        $this->assertEquals(SubscriptionModel::last()->id,$lastSubBefore->id);
    }
}

?>