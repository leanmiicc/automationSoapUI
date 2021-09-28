<?php

require_once('test_setup.php');

// require_once(relativeAppPath.'actions/Product.php');

class DocsisModemTest extends PHPUnit_Framework_TestCase
{

    var $cmTest;
    protected function setUp()
    {
        //$cmTest = new DocsisModem();

        //$cmTest->modem_macaddre
    }

    public function testAllowProductsInAMTA(){
        $modemWithMTA = DocsisModem::find('121212123333');

        //print_r($modemWithMTA->allowPolicies()->to_json());        
            print_r($modemWithMTA->to_json(array('methods' => 'allowPolicies')));
    }

}
?>