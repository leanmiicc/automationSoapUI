<?php

require_once('test_setup.php');

require_once(relativeAppPath.'classes/Rollbacks.php');
require_once('classes/ActivationCode.php');

class RollbackActivationCodeTest extends PHPUnit_Framework_TestCase
{
	var $subId = '999';
	var $subExisted = false;

    protected function setUp()
    {
       	 try{
	  		$savedCode = ActivationCodeDB::find($this->subId);
	  		$savedCode->delete();
	  	}catch(ActiveRecord\RecordNotFound $e){
	  		$this->subExisted = true;
	  	}
    }	


     
	public function testRollbackWhenTheyCreateAnActivationCode(){
		$actCodeObj = new ActivationCode();
		$dataCode = $actCodeObj->Generate($this->subId);	

		$this->assertNotNull($dataCode);
		$this->assertNotEquals($dataCode['code'],'');
		$this->assertNotEquals($dataCode['expiration'],'');

		$actCodeObj->rollback();

		$this->assertNotTrue($actCodeObj->Get($this->subId));
	}

	public function testRollbackWhenTheyUpdateAnActivationCode(){
	
		$actCodeObj = new ActivationCode();
		$dataCodeOriginal = $actCodeObj->Generate($this->subId);	

		$this->assertNotNull($dataCodeOriginal);
		/*$this->assertNotEquals($dataCode['code'],'');
		$this->assertNotEquals($dataCode['code'],'expiration');*/

		$dataCodeAfterGenerate = $actCodeObj->Generate($this->subId);			

		
		$this->assertNotEquals($dataCodeOriginal['code'],$dataCodeAfterGenerate['code']);

		$actCodeObj->rollback();

		$savedCodeAfterRollback = ActivationCodeDB::find($this->subId);

		$this->assertEquals($dataCodeOriginal['code'],$savedCodeAfterRollback->code);
		$this->assertEquals($dataCodeOriginal['expiration'],$savedCodeAfterRollback->expiration_date->format("Y-m-d H:i:s"));

	}

	public function testPop()
    {
    	try{
    		if (!$this->subExisted){
	    		$savedCode = ActivationCodeDB::find($this->subId);
		  		$savedCode->delete();	
    		}	  		
	  	}catch(ActiveRecord\RecordNotFound $e){

	  	}
    }
}

?>