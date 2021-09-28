<?php

require_once('phpTest/xUnit/test_setup.php');
require_once('actions/Product.php');



class ActionProductTest extends PHPUnit_Framework_TestCase
{

	var $saveGponProdcutInfo = array(
			"product_type"=>"gpon",
			"name"=>"TEST",
			"policy_name"=>"TEST",
			"bdp_file_tag"=>"",
			"description"=>"TEST",
			"static_ip"=>"1",
			"ds_max"=>"",
			"us_max"=>"",
			"voip_flag"=>0,
			"wifi_flag"=>0,
			"static_flag"=>0,
			"ipv6_flag"=>0,
			"enable"=>1,
			"update_product_id"=>"");
	

	var $setting;

	protected $actionRetrieveInfo = array ( 
			'sub_id' => ''
	);

    protected function setUp()
    {
    	global $setting;
    	$this->setting = $setting;
    }	

   public function testSaveAGpointProduct(){
    	$action = new ProductAction();
		$action->setArray($this->saveGponProdcutInfo);

		$action->createConnection($this->setting);
		
		$prodBeforeInsert = Product::last();

		$response = $action->save_gpon();

		$prodAfterInsert = Product::last();

		$this->assertEquals($response->getType(),'Success');
    	$this->assertNotNull($action);

    	$this->assertGreaterThan($prodBeforeInsert->id,$prodAfterInsert->id);

    }

/*   public function testRetrieveTheLastSub(){
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
    }*/

}

?>