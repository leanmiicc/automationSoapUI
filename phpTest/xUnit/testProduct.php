<?php

require_once('test_setup.php');

require_once(relativeAppPath.'actions/Product.php');

class ProductTest extends PHPUnit_Framework_TestCase
{
    var $testData = '{"name":"Wifi 256","descriptions":"Wifi familiar","enable":"true","tech_id": 1}';
    //var $testData = '{"a":"Uno","b":2,"c":3,"d":4,"e":5}';
    var $jsonData;


    protected function setUp()
    {
        $this->jsonData = json_decode($this->testData);
    }

    /*public function testCheckParamsInActionSaveProduct()
    {
        
        $action = new ProductAction($this->jsonData);
        $this->assertEquals($action->params->name,"Wifi 256");
        $this->assertEquals($action->params->description,"Wifi familiar");
    }*/

    public function testSaveTheParamsInActionSaveProduct()
    {
    
        $jsonDataTestProduct =  
            '{"name":"TEST-'.date("H:mm:ss").'","policy_name":"TEST2_Policy-'.date("H:mm:ss").'","bdp_file_tag":"taggg",'.
            '"description":"description","static_ip":"1","ds_max":"1000",'.
            '"us_max":"10","voip_flag":1,"wifi_flag":0,"static_ip":0,"enable":"off"}';

        $action = new ProductAction(json_decode($jsonDataTestProduct));

        $lastProductBeforeInsert = Product::last();

        $r = $action->save();

        $lastProductAfterInsert = Product::last();

        $this->assertLessThan($lastProductAfterInsert->id, $lastProductBeforeInsert->id);

        $lastDocsisProductAfterInasert = DocsisPolicy::last();

        echo "Result..";
        // print_r($lastDocsisProductAfterInasert);

        // checking the flags
        $this->assertEquals(0, $lastDocsisProductAfterInasert->voip_flag);
        $this->assertEquals(1, $lastDocsisProductAfterInasert->wifi_flag);
        $this->assertEquals(1, $lastDocsisProductAfterInasert->static_ip);
        $this->assertEquals(0, $lastDocsisProductAfterInasert->l2vpn_flag);
    }

    /*public function testGettingGridOnTheProductPage(){
        
        $jsonGridRequest = json_decode('{"sEcho":1,"iColumns":6,"sColumns":"","iDisplayStart":0,"iDisplayLength":10'
                    .',"DataProp_0":0,"mDataProp_1":1,"mDataProp_2":0,"mDataProp_3":3,"mDataProp_4":4,"mDataProp_5":5, "sSearch":""'
                    .',"bRegex":false,"sSearch_0":"","bRegex_0":true,"bSearchable_0":true,"sSearch_1":"","bRegex_1":true,"bSearchable_1":true'
                    .',"sSearch_2":"","bRegex_2":true,"bSearchable_2":true,"sSearch_3":"","bRegex_3":true,"bSearchable_3":true,"sSearch_4":""'
                    .',"bRegex_4":false,"bSearchable_4":true,"sSearch_5":"", "bRegex_5":false,"bSearchable_5":true,"action":"products.gridRequest","isGrid":true}');

    
        $action = new ProductAction($jsonGridRequest);

        $response = $action->gridRequest($this->jsonData);

        $this->assertNotNull($response);

        $this->assertInternalType('array',$response);

        $this->assertArrayHasKey('aaData',$response);
    }*/

    /*public function testRetrieveAdocsisProduct(){

        $firstIdData = json_decode('{"id" : 1}');

        $action = new ProductAction($firstIdData);

        $product = json_decode($action->retrieve());

        $this->assertNotNull($product);
        $this->assertEquals($product->id,1);
        $this->assertNotEmpty($product->name);


    }*/
}
?>