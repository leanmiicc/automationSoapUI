<?php

require_once('test_setup.php');

require_once(relativeAppPath.'classes/Rollbacks.php');
require_once(relativeAppPath.'classes/InsertRollback.php');

class RollbackTest extends PHPUnit_Framework_TestCase
{


    protected function setUp()
    {
       
    }	

	/**
     * @expectedException ActiveRecord\RecordNotFound
     */
	public function testExecuteASimpleRollback(){
		$macToTest = '999988887777';
		$cmRollback = new Rollbacks();

		$cm  = new DocsisModem();
		$cm->modem_macaddr = $macToTest;

		$cm->save();

		$cmRollback->addAction(new InsertRollback($cm));

		$cmRollback->execute();

		DocsisModem::find($macToTest);	
	}
}

?>