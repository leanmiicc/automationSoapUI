<?php

require_once('test_setup.php');

require_once(relativeAppPath.'classes/policy/DocsisPolicy.php');

class DocsisPolicyTest extends PHPUnit_Framework_TestCase
{
    var $testData = '{"name":"Wifi 256","descriptions":"Wifi familiar","enable":"true","tech_id": 1}';
    var $jsonData;
    var $config =  array(
                    'drivers' => array('bdp'=> 'GenericExampleDriver'),
                    'billing' => array()
                );


    protected function setUp()
    {
        $this->jsonData = json_decode($this->testData);
    }

    

    public function testCreatePolicyWithGenericFactoryRemoveLeaseCPE(){
        $docsisPolicy = DocsisPolicy::Create(new GenericFactory($this->config));

        $this->assertNotNull($docsisPolicy);

        $response = $docsisPolicy->removeCpeLeases('123412341234');    

        $this->assertTrue($response);
    }

        public function testCreatePolicyWithGenericFactoryAndResetModem(){
        $docsisPolicy = DocsisPolicy::Create(new GenericFactory($this->config));

        $this->assertNotNull($docsisPolicy);

        $response = $docsisPolicy->resetModemAsync('123412341234');    

        $this->assertTrue($response);
    }

    

    public function testFindThePolicyForTheFirstProduct(){
        $products = Product::all();

        $this->assertNotNull($products);
        $this->assertNotEmpty($products);

        $policies = DocsisPolicyModel::findByProduct($products);

        $this->assertNotNull($policies);        
        $this->assertNotEmpty($policies);
    }

}
?>