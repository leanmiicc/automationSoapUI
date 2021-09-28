<?php

require_once('test_setup.php');

require_once(relativeAppPath.'classes/Rollbacks.php');
require_once(relativeAppPath.'classes/InsertRollback.php');
require_once(relativeAppPath.'classes/exceptionDSA/RollbackException.php');

class RollbackTest extends PHPUnit_Framework_TestCase
{


    protected function setUp()
    {
        try{
        	$macToTest = '999988887777'; 
			$cm = DocsisModem::find($macToTest);
			print_r($cm);
			$cm->delete();	
    	}catch(Exception $e){

    	}
    }	

	/**
     * expectedException ActiveRecord\RecordNotFound
     */
	public function testExecuteASimpleRollback(){
		/*$macToTest = '999988887777';
		$cmRollback = new Rollbacks();

		$cm  = new DocsisModem();
		$cm->modem_macaddr = $macToTest;

		$cm->save();

		$cmRollback->addAction(new InsertRollback($cm));

		$cmRollback->execute();

		DocsisModem::find($macToTest);	*/
	}

	public function testRollbackExceptionCreationByInnerException(){
		$e = new RollbackException();

		$this->assertNotNull($e);
		
		$e = RollbackException::createByException(new Exception("Test case"));
		$this->assertNotNull($e);
		$this->assertEquals($e->getError(),"Test case");

		$e = RollbackException::createByMessage("Test case");
		$this->assertNotNull($e);
		$this->assertEquals($e->getError(),"Test case");
	}

}

?>