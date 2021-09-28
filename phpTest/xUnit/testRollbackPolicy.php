<?php

require_once('test_setup.php');

require_once(relativeAppPath.'classes/Rollbacks.php');
require_once('classes/policy/DocsisPolicy.php');
require_once('classes/policy/GponPolicy.php');

class RollbackPolicyTest extends PHPUnit_Framework_TestCase
{

	var $subId = '9999';
	var $productId = '9999';
	
	var $gponSubId = '99999';
	var $gponProductId = '99999';

	var $sub;
	var $gponSub;
	
    var $params = array(
    	'license-value'=>'',
		'technology_id'=>'docsis',
		'modem_subscriber'=>'0001 - Bertamoni Agustin',
		'number'=>'',
		'name'=>'',
		'last_name'=>'',
		'address'=>'',
		'phone'=>'','email'=>'',
		'product_id'=>'21',
		'billing_status_id'=>'2',
		'modem_macaddr'=>'1111ffff1111ffff',
		'ont_sn'=>null,
		'old_id_text'=>'',
		'subscription-old-id-combo'=>null,'mta_macaddr'=>'','config_file'=>'','activation_code_date'=>'','activation_code'=>'','cpe6_flag'=>0,'cpe4_tag_flag'=>0,'mta_tag_flag'=>0,'cpe4_tag_id'=>'','mta_tag_id'=>'','responseType'=>'default','voip_params'=>[],'wifi_params'=>[]
    );

    /*var $gponParams = array(
    	'license-value'=>'',
		'technology_id'=>'gpon',
		'modem_subscriber'=>'0001 - Bertamoni Agustin',
		'number'=>'',
		'name'=>'',
		'last_name'=>'',
		'address'=>'',
		'phone'=>'','email'=>'',
		'product_id'=>'21',
		'billing_status_id'=>'2',
		'modem_macaddr'=>'',
		'ont_sn'=> '1111ffff1111ffff1111',
		'old_id_text'=>'',
		'subscription-old-id-combo'=>null,
		'mta_macaddr'=>'',
		'config_file'=>'',
		'activation_code_date'=>'',
		'activation_code'=>'',
		'cpe6_flag'=>0,'cpe4_tag_flag'=>0,'mta_tag_flag'=>0,'cpe4_tag_id'=>'','mta_tag_id'=>'','responseType'=>'default','wifi_params'=>[]
    );**/

    /*var $gponParams = array(
    	'license-value'=>'',
		'technology_id'=>'gpon',
		'modem_subscriber'=>'100001 - Stechs Labs',
		'number'=>'',
		'product_id'=>'44',
		'billing_status_id'=>'2',
		'modem_macaddr'=>'',
		'ont_sn'=>'',
		'old_id_text'=>'',
		'subscription-old-id-combo'=>null,
		'mta_macaddr'=>'','config_file'=>'',
		'activation_code_date'=>'',
		'activation_code'=>'',
		'cpe6_flag'=>0,'cpe4_tag_flag'=>0,
		'mta_tag_flag'=>0,'cpe4_tag_id'=>'',
		'mta_tag_id'=>'',
		'voip_params'=>[],
		'wifi_params'=>[]
		);*/
var $gponParams = array(
		'id' => '9999',
		'license-value'=>'',
		'technology_id'=>'gpon',
		'modem_subscriber'=>'0001 - Bertamoni Agustin',
		'number'=>'',
		'name'=>'',
		'last_name'=>'','address'=>'','phone'=>'','email'=>'',
		'product_id'=>'44',
		'billing_status_id'=>'1',
		'modem_macaddr'=>'',
		'ont_sn'=>'1111ffff1111ffff1111',
		'old_id_text'=>'',
		'subscription-old-id-combo'=>null,
		'mta_macaddr'=>'',
		'config_file'=>'','activation_code_date'=>'',
		'activation_code'=>'','cpe6_flag'=>0,
		'cpe4_tag_flag'=>0,'mta_tag_flag'=>0,'cpe4_tag_id'=>'',
		'mta_tag_id'=>'','responseType'=>'default','voip_params'=>[],'wifi_params'=>[]
		);

    protected function setUp()
    {
    	try{
    		$this->sub = SubscriptionModel::find($this->subId);
    	}catch(ActiveRecord\RecordNotFound $e){
	    	$this->sub = new SubscriptionModel();
	    	$this->sub->id = $this->subId;
	    	$this->sub->subscriber_id = 1;
	    	$this->sub->product_id = 21;
	    	$this->sub->billing_status_id = 1;
	    	$this->sub->save();	
    	}

    	try{
    		$this->gponSub = SubscriptionModel::find($this->gponSubId);
    	}catch(ActiveRecord\RecordNotFound $e){
	    	$this->gponSub = new SubscriptionModel();
	    	$this->gponSub->id = $this->gponSubId;
	    	$this->gponSub->subscriber_id = 1;
	    	$this->gponSub->product_id = 44;
	    	$this->gponSub->billing_status_id = 1;
	    	$this->gponSub->save();	
    	}
    	
    	$gParam = GponParam::find_by_ont_sn('1111ffff1111ffff1111');
    	if (!$gParam){
			$gParam = new GponParam();
			$gParam->subscription_id = $this->gponSubId;
			$gParam->ont_sn = '1111ffff1111ffff1111';
			$gParam->ont_params =  '{"loid":"null","portId":"1","ontId":"1","checkcode":"null","shelfId":"1","autoFindTime":null,"sn":"1111ffff1111ffff","mainSoftVer":"","password":"1","equipmentId":"","slotId":"1","vendorId":"1","gponPortIndex":"1","oltId":"1","ontVer":"1"}';
			$gParam->voip_params = '{"custom":[],"mtaEnable":"0"}';
			$gParam->save();
		} 

    	$this->params = json_decode(json_encode($this->params),false);

    	$this->gponParams = json_decode(json_encode($this->gponParams),false);
    }	


     
	public function testRollbackWhenTheyCreateADocsisPolicy(){
		$policy = new DocsisPolicy();

		$policy->setPolicyModel(21);
		$policy->setDocsisParam($this->sub,$this->params);
		$policy->enableDocsisService($this->sub);

		$policy->rollback();

		try{
			$params = DocsisParam::find_by_subscription_id($this->subId);	
			$this->assertNotNull($params);
		}catch(ActiveRecord\RecordNotFound $e){
	  		$this->fail('No se encontro el parametro que deberia ir');
	  	}
		
		// $this->assertNotTrue($policy->Get($this->subId));
	}


	/*public function testRollbackWhenTheyCreateAGponPolicy(){
		$policy = new GponPolicy();

		$policy->setPolicyModel(47);
		$policy->setGponParam($this->gponSub,$this->gponParams);
		$policy->activateHSIService($this->gponSub);

		$policy->rollback();

		try{
			$params = GponParam::find_by_subscription_id($this->gponSubId);	
			$this->assertNotNull($params);
		}catch(ActiveRecord\RecordNotFound $e){
	  		$this->fail('No se encontro el parametro que deberia ir');
	  	}
		
		// $this->assertNotTrue($policy->Get($this->subId));
	}*/


	public function testPop()
    {
    	$this->sub->delete();
    	$this->gponSub->delete();
    	try
    	{
    		$modem = DocsisModem::find('1111ffff1111fff');
    		$modem->delete();
    	}catch(ActiveRecord\RecordNotFound $e){
    		
    	}

    	try
    	{
    		$gponParam = GponParam::find_by_ont_sn('1111ffff1111ffff1111');
    		if ($gponParam){
    			$gponParam->delete();	
    		}   		
    	}catch(ActiveRecord\RecordNotFound $e){
    		
    	}
    }
}

?>