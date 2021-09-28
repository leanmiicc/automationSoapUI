<?php

require_once('phpTest/xUnit/test_setup.php');
require_once('classes/WSMapper.php');

use Respect\Validation\Validator as v;

class ValidationTest extends PHPUnit_Framework_TestCase
{


    protected function setUp()
    {
        
    }	


    public function testValidateAsimpleField(){
        $mapper = new WSMapper(array(
                array(
                    'original_name' => 'number',
                    'public_name' => 'subcriber_number',
                    'validator' => v::numeric()->positive()->between(1, 2000),
                    'mandatory' => true
                )
        ));

        $obj = json_decode('{"subcriber_number" : 1234}');
        //print_r($obj);
        $objResult = $mapper->validate($obj);
        $this->assertNotNull($objResult);
        $this->assertObjectHasAttribute('number',$objResult);
    }

        public function testValidateADobleField(){
            $mapper = new WSMapper(array(
                    array(
                        'original_name' => 'number',
                        'public_name' => 'subcriber_number',
                        'validator' => v::numeric()->positive()->between(1, 2000),
                        'mandatory' => true
                    )
            ));

            $obj = json_decode('{"subcriber_number" : 1234 , "name" : "Nico Betti"}');
            //print_r($obj);
            $objResult = $mapper->validate($obj);
            $this->assertNotNull($objResult);
            $this->assertObjectHasAttribute('number',$objResult);
    }


    public function testValidateTheSameNameToMapper(){
            $mapper = new WSMapper(array(
                    array(
                        'public_name' => 'subcriber_number',
                        'validator' => v::numeric()->positive()->between(1, 2000),
                        'mandatory' => true
                    )
            ));

            $obj = json_decode('{"subcriber_number" : 1234 , "name" : "Nico Betti"}');
            //print_r($obj);
            $objResult = $mapper->validate($obj);
            $this->assertNotNull($objResult);
            $this->assertObjectHasAttribute('subcriber_number',$objResult);
    }

    public function testInvalidValueToMapper(){
            $mapper = new WSMapper(array(
                    array(
                        'public_name' => 'subcriber_number',
                        'validator' => v::numeric()->positive()->between(1, 100),
                        'mandatory' => true
                    )
            ));

            $obj = json_decode('{"subcriber_number" : 1234 , "name" : "Nico Betti"}');
            // print_r($obj);
            $objResult = $mapper->validate($obj);
            $this->assertNotNull($objResult);
            // print_r($mapper);
            $this->assertFalse($mapper->isValid());
            // $this->assertObjectHasAttribute('subcriber_number',$objResult);
    }

     public function testMandatoryFieldNotExist(){
            $mapper = new WSMapper(array(
                    array(
                        'public_name' => 'subcriber_number',
                        'validator' => v::numeric()->positive()->between(1, 100),
                        'mandatory' => true
                    ),
                    array(
                        'public_name' => 'last_name',
                        'mandatory' => true
                    )
            ));

            $obj = json_decode('{"subcriber_number" : 1234 , "name" : "Nico Betti"}');
            // print_r($obj);
            $objResult = $mapper->validate($obj);
            $this->assertNotNull($objResult);
            // print_r($mapper);
            $this->assertFalse($mapper->isValid());
            // $this->assertObjectHasAttribute('subcriber_number',$objResult);
    }
}

?>