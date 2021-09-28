<?php

require_once('phpTest/xUnit/test_setup.php');
require_once('actions/Subscription.php');

class ActionSubscriptionTest extends PHPUnit_Framework_TestCase
{

	var $actionPendingInfo = array ( 
			'sub_id' => '',
			'license-value' => '',
			'technology_id' => 'docsis',
			'modem_subscriber' => '0001 - Bertamoni Agustin',
			'number' => '',
			'name' => '',
			'last_name' => '',
			'address' => '',
			'phone' => '',
			'email' => '',
			'product_id' => 55 ,
			'billing_status_id' => 2 ,
			'modem_macaddr' => '',
			'mta_macaddr' => '',
			'config_file' => '',
			'activation_code_date' => '',
			'activation_code' =>'', 
			'cpe6_flag' => 1 ,
			'cpe4-tag-flag' => 0,
			 'mta-tag-flag' => 0 ,
			 'cpe4_tag_id' => '',
			 'mta_tag_id' => '',
			 'isNewSubscriber' => '',
			'responseType' => 'default' );

	var $actionPendingStatusGpon = array(
		'sub_id'=>'',
		'license-value'=>'',
		'technology_id'=>'gpon',
		'modem_subscriber'=>'002 - Perez Juan',
		'number'=>'','name'=>'',
		'last_name'=>'',
		'address'=>'',
		'phone'=>'',
		'email'=>'',
		'product_id'=>'4',
		'billing_status_id'=>'2',
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

	var $actionActiveStatusGpon = array(
		'sub_id'=>'',
		'license-value'=>'',
		'modem_serial' => '12341234123434',
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

	var $setting;

	protected $actionRetrieveInfo = array ( 
			'sub_id' => ''
	);

    protected function setUp()
    {
    	global $setting;
    	$this->setting = $setting;
    }	

  public function testSaveADocsisSubWithPendingStatus(){
    	$action = new SubscriptionAction();
		$action->setArray($this->actionPendingInfo);

		$action->createConnection($this->setting);
		
		$subBeforeInsert = SubscriptionModel::last();

		$response = $action->save();

		$subAfterInsert = SubscriptionModel::last();

		$this->assertEquals($response->getType(),'Success');
    	$this->assertNotNull($action);

    	$this->assertGreaterThan($subBeforeInsert->id,$subAfterInsert->id);

    }

   public function testRetrieveTheLastSub(){
    	$lastSub = SubscriptionModel::last();
    	$params = array('id' => $lastSub->id);
    	$action = new SubscriptionAction();

		$action->setArray($params);

		$action->createConnection($this->setting);
		
		$response = $action->retrieve();

		$this->assertEquals($response->getType(),'Success');
    }

   public function testDeleteTheLabSun(){
    	$lastSub = SubscriptionModel::last();
    	$params = array('id' => $lastSub->id);
    	$action = new SubscriptionAction();

		$action->setArray($params);

		$action->createConnection($this->setting);
		
		$response = $action->delete();

		$lastSubAfter = SubscriptionModel::last();

		$this->assertEquals($response->getType(),'Success');
		$this->assertGreaterThan($lastSubAfter->id,$lastSub->id);
    }

    public function testSaveAGponSubWithPendingStatus(){
    	$action = new SubscriptionAction();
		$action->setArray($this->actionPendingStatusGpon);

		$action->createConnection($this->setting);
		
		$subBeforeInsert = SubscriptionModel::last();

		$response = $action->save();

		$subAfterInsert = SubscriptionModel::last();

		$this->assertEquals($response->getType(),'Success');
    	$this->assertNotNull($action);

    	$this->assertGreaterThan($subBeforeInsert->id,$subAfterInsert->id);

    } 

     public function testSaveAGponSubWithActiveStatus(){
    	$action = new SubscriptionAction();
		$action->setArray($this->actionActiveStatusGpon);

		$action->createConnection($this->setting);
		
		$subBeforeInsert = SubscriptionModel::last();

		$response = $action->save();

		$subAfterInsert = SubscriptionModel::last();

		$this->assertEquals($response->getType(),'Success');
    	$this->assertNotNull($action);

    	$this->assertGreaterThan($subBeforeInsert->id,$subAfterInsert->id);

    }

}

?>