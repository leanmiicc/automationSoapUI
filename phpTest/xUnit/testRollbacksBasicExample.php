<?php
require_once('test_setup.php');

require_once(relativeAppPath.'classes/Rollbacks.php');
require_once(relativeAppPath.'classes/InsertRollback.php');
require_once(relativeAppPath.'classes/exceptionDSA/RollbackException.php');


$database = array();
/**
 * ejecutar con:
 * =============
 * 
 * cd /var/www/bp-juanma-dev/dsa/test/phpTest/xUnit
 * 
 * ../phpunit.phar --bootstrap ../../../vendor/autoload.php ./testRollbacksBasicExample.php
 *
 * */
class BasicClass{
	
	var $value = "";
	var $index = -1;
	var $flag = 0;
	
	
	public function turnOff(){		
		$this->flag = 0;
	}
	
	public function  turnOn(){
		
		$this->flag = 1;
	}

	public function  setFlagAnyValue($value){
		
		$this->flag = $value;
	}
	
	public function BasicClass($val,$index=-1,$flag=false){
		$this->value=$val;
		$this->index=$index;
		$this->flag=$flag;
	}
	
	public function save(){
		global $database;
		
		if($this->value == "Curly Joe") throw new RollbackException();
		
		$database[] = ($this->value);
		$this->index = count($database)-1;
	}
	
	/*para la version 3,0*/
	public static function create($arrayOfAttributes){
		$aux = new BasicClass($arrayOfAttributes['value'],$arrayOfAttributes['index'],$arrayOfAttributes['flag']);
		$aux->save();
	}
	/*para la version 3,0*/
	public function to_array(){
		
		return array("value"=> $this->value, "index" => $this->index, "flag"=> $this->flag);
	}
	
	public function delete(){
		global $database;
		if($this->value == "curly") throw new RollbackException();
		unset($database[$this->index]);
		
	}
	
	public function getElement(){
		global $database;
		$this->value = $database[$this->index];
		return $this->value;
	}
	
	public static function getAll(){
		global $database;		
		return $database;
	}
}

class RollbackTestBasicExample extends PHPUnit_Framework_TestCase
{
	
	var $rollback; 
	var $myStooges = array();
	
	protected function setUp()
	{
		$this->rollback = new Rollbacks();
		$moe = new BasicClass("moe");
		$moe->save();
		
		$larry= new BasicClass("larry");
		$larry->save();
		
		$curly= new BasicClass("curly");
		$curly->save();
		
		$this->myStooges[] = $moe;
		$this->myStooges[] = $larry;
		$this->myStooges[] = $curly;
	}

	public function testInsertValues(){
		
		echo "\nTEST INSERT \n\n:";
		
		
		$data = BasicClass::getAll();
		$this->assertEquals(count($data),3);
		echo "data\n";
		print_r($data);
	}
	
	public function testDeleteValues(){
		
		echo "\nTEST DELETE \n\n:";
		$shemp = new BasicClass("Shemp");
		$shemp->save();
				
		$data = BasicClass::getAll();
		$this->assertEquals(count($data),4);
		echo "data\n";
		print_r($data);
		
		$shemp->delete();
		$data = BasicClass::getAll();
		$this->assertEquals(count($data),3);
		echo "data\n";
		print_r($data);
	}
	
	public function testRollbackInsert(){
		echo "\nTEST ROLLBACK INSERT \n\n:";
		
		$newInserts = array("Joe", "Curly Joe");
		
			try{
				foreach ($newInserts as $stoogeName){
					$stooge = new BasicClass($stoogeName);
					$stooge->save();
					$this->rollback->addObjToDelete($stooge);
					echo "se insertó: ".$stoogeName."\n";
				}
			}catch (RollbackException $eRollback){
				echo "falló la inserción de $stoogeName\n";
				$this->rollback->execute();
			}
		
		
		$data = BasicClass::getAll();
		$this->assertEquals(count($data),3);
		echo "data\n";
		print_r($data);
		
		
	}
	
	public function testRollbackDelete(){
		echo "\nTEST ROLLBACK DELETE \n\n:";
				
		
			try{
				foreach ($this->myStooges as $stooge){
					$stooge->delete();
					$this->rollback->addObjToInsert($stooge);
					echo "se eliminó: ".$stooge->value."\n";
				}
			}catch (RollbackException $eRollback){
				echo "falló la eliminación de $stooge->value\n";
				$this->rollback->execute();
			}
		
		
		$data = BasicClass::getAll();
		$this->assertEquals(count($data),3);
		echo "data\n";
		print_r($data);
		
	}
	
	/*similar al insert pero seteamos parametros antes de insertar*/
	public function testRollbackDowngrade(){
		echo "\nTEST ROLLBACK Downgrade \n\n:";
		
		try{
			foreach ($this->myStooges as $stooge){
				$stooge->delete();
				$this->rollback->addObjToDownGrade(new BasicClass(""),array("value" => $stooge->value));
				echo "se eliminó: ".$stooge->value."\n";
			}
		}catch (RollbackException $eRollback){
			echo "falló la eliminación de $stooge->value\n";
			$this->rollback->execute();
		}
		
		
		$data = BasicClass::getAll();
		$this->assertEquals(count($data),3);
		echo "data\n";
		print_r($data);
		
		
	}
	
	public function testRollbackExecute(){
		echo "\nTEST ROLLBACK addObjToExecute \n\n:";
		
		try {
			foreach ($this->myStooges as $stooge){
				$stooge->turnOn();
				$this->rollback->addObjToExecute($stooge,'turnOff',null);
				echo "se hizo turn-on de : ".$stooge->value."\n";
			}
			
			print_r($this->myStooges);
			throw new RollbackException();
			
		} catch (RollbackException $eRollback) {
			echo "ejecutamos rollback";
			$this->rollback->execute();
		}
		print_r($this->myStooges);
		
		foreach ($this->myStooges as $stooge){			
			$this->assertEquals($stooge->flag,0);
		}
		
	}
	
	public function testRollbackExecuteWithParams(){
		echo "\nTEST ROLLBACK addObjToExecute con parametros \n\n:";
		
		try {
			foreach ($this->myStooges as $stooge){
				$stooge->setFlagAnyValue(5);
				$this->rollback->addObjToExecute($stooge,'setFlagAnyValue',3);
				echo "se puso el flag en 5 de : ".$stooge->value."\n";
			}
			
			print_r($this->myStooges);
			throw new RollbackException();
			
		} catch (RollbackException $eRollback) {
			echo "ejecutamos rollback";
			$this->rollback->execute();
		}
		print_r($this->myStooges);
		
		foreach ($this->myStooges as $stooge){
			$this->assertEquals($stooge->flag,3);
		}
		
	}

	
}







?>