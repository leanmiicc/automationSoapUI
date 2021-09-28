<?php

require_once('test_setup.php');

require_once(relativeAppPath.'classes/billingStatus/DocsisBillingStatus.php');

class DocsisBillingStatusTest extends PHPUnit_Framework_TestCase
{
    
    var $actionPendingInfo = array ( 
            'sub_id' => '',
            'license-value' => '',
            'technology_id' => 'docsis',
            'modem_subscriber' => '0001 - Bertamoni Agustin',
            'number' => '',
            'name' => '',
            'last_name' => '',
            'address' => '',
            'phone' => '',
            'email' => '',
            'product_id' => 55 ,
            'billing_status_id' => 2 ,
            'modem_macaddr' => '',
            'mta_macaddr' => '',
            'config_file' => 'test.pepe',
            'activation_code_date' => '',
            'activation_code' =>'', 
            'cpe6_flag' => 1 ,
            'cpe4-tag-flag' => 0,
             'mta-tag-flag' => 0 ,
             'cpe4_tag_id' => '',
             'mta_tag_id' => '',
             'isNewSubscriber' => '',
            'responseType' => 'default' );

    public function testCreationDocsisBillingStatus(){
        $recordSub = SubscriptionModel::last();
        $billing = new DocsisBillingStatus($recordSub);

        $this->assertNotNull($billing);
    }

   public function testAppluBillingStatusToActivePendingStatus(){
        $recordSub = SubscriptionModel::last();
        $billing = new DocsisBillingStatus($recordSub);
        
        $this->actionPendingInfo['sub_id'] = $recordSub->id;
        $policy = new DocsisPolicy();

        $parameters = $policy->setFormParams($this->actionPendingInfo);

        $result = $billing->applyBillingStatus($parameters);

        $this->assertTrue($result);

    }
}
?>