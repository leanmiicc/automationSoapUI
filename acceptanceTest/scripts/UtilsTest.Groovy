// Import the Groovy class required to work with SQL databases.
import groovy.sql.Sql

class UtilsTest {

    def getInstanceDB(dbDsa, dbHost, dbPort,user, password, log) {
        log.info('executeDSAQuery : ' + 'jdbc:mysql://' + dbHost + '/' + dbDsa + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */

        /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + ':' + dbPort + '/' + dbDsa 
        def driver = 'com.mysql.cj.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver(driver)

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        return sql
    }

    def recreateDsaDB(dbDsa, dbHost, dbPort,user, password, log) {

        log.info('DSA base recreate: ' + dbHost + '/' + dbDsa + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' 
        /* IMPORTANT: must start with jdbc:mysql:// */
        //def url = 'jdbc:mysql://' + dbHost + '/' + dbDsa 
        def url = 'jdbc:mysql://' + dbHost + ':' + dbPort + '/' + dbDsa
        def driver = 'com.mysql.cj.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver(driver)

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

        def x = 0
        //List of tables DSA v2. Enable if necessary.
        //def tables = ["subscriptions","subscribers","issues","logs","docsis_policies","docsis_params","gpon_policies","gpon_params","srv_traffic_profile","gpon_service_type","voip_profile","docsis_tags","brands","eoc_policies", "eoc_params","activation_params","activation_code"]
        def tables = ["subscriptions", "subscribers", "subscription_service", "crm_code_defs", "crm_code_values", "service_template", "dummy_instance", "tls_instance", "hsi_instance", "voip_instance","video_instance", "dummy_template", "tls_template", "hsi_template", "voip_template", "video_template", "service_template","voip_lines","gpon_voip_profile", "issues", "logs", "docsis_policies", "docsis_params", "gpon_policies", "gpon_params", "srv_traffic_profile", "gpon_service_type", "docsis_tags", "activation_params", "activation_code"]

        for (x = 0; x < tables.size(); x++) {
            res = sql.execute('TRUNCATE TABLE ' + tables[x] + ';');
        }

        res = sql.execute('SET FOREIGN_KEY_CHECKS = 1;');


        // Insert on Table crm_code_defs

        res = sql.execute("INSERT INTO crm_code_defs (id, name, label, shortcut, object_to_apply, searcheable, `unique`) VALUES(1, 'SubscriptionId', 'SubscriptionID', 'subID', 'Service', 1, 0);");

        // Insert on Table service_template

        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(1, 'hsi', 'Hsi');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(2, 'scm', 'Dummy');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(3, 'scm_voip', 'Dummy');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(4, 'scm_tls', 'Dummy');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(5, 'liv', 'Hsi');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(6, 'optimo', 'Hsi');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(7, 'unico', 'Hsi');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(8, 'gigaplus', 'Hsi');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(9, 'voippack', 'Hsi');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(10, 'maximo', 'Hsi');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(11, 'tls', 'tls');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(12, 'tls-qinq', 'tls');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(13, 'trama-e1', 'tls');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(14, 'video', 'video');");
        res = sql.execute("INSERT INTO service_template (template_id, template_name, service_type) VALUES(15, 'voip', 'voip');");

        // Insert on table dummy_template

        res = sql.execute("INSERT INTO dummy_template (id, `type`, type_editable, eth_port_1, eth_port_1_editable, eth_port_2, eth_port_2_editable, eth_port_3, eth_port_3_editable, eth_port_4, eth_port_4_editable, wan_1, wan_1_editable, wan_2, wan_2_editable, wan_3, wan_3_editable, wan_4, wan_4_editable, wifi_24_ssid_1, wifi_24_ssid_1_editable, wifi_24_ssid_2, wifi_24_ssid_2_editable, wifi_24_ssid_3, wifi_24_ssid_3_editable, wifi_24_ssid_4, wifi_24_ssid_4_editable, wifi_5_ssid_1, wifi_5_ssid_1_editable, wifi_5_ssid_2, wifi_5_ssid_2_editable, wifi_5_ssid_3, wifi_5_ssid_3_editable, wifi_5_ssid_4, wifi_5_ssid_4_editable, pots_1, pots_1_editable, pots_2, pots_2_editable, catv_1, catv_1_editable, catv_2, catv_2_editable, fdm_srv, fdm_srv_editable, vport, vport_editable) VALUES(2, 'hsi', 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 'scm', 0, 0, 1);");
        res = sql.execute("INSERT INTO dummy_template (id, `type`, type_editable, eth_port_1, eth_port_1_editable, eth_port_2, eth_port_2_editable, eth_port_3, eth_port_3_editable, eth_port_4, eth_port_4_editable, wan_1, wan_1_editable, wan_2, wan_2_editable, wan_3, wan_3_editable, wan_4, wan_4_editable, wifi_24_ssid_1, wifi_24_ssid_1_editable, wifi_24_ssid_2, wifi_24_ssid_2_editable, wifi_24_ssid_3, wifi_24_ssid_3_editable, wifi_24_ssid_4, wifi_24_ssid_4_editable, wifi_5_ssid_1, wifi_5_ssid_1_editable, wifi_5_ssid_2, wifi_5_ssid_2_editable, wifi_5_ssid_3, wifi_5_ssid_3_editable, wifi_5_ssid_4, wifi_5_ssid_4_editable, pots_1, pots_1_editable, pots_2, pots_2_editable, catv_1, catv_1_editable, catv_2, catv_2_editable, fdm_srv, fdm_srv_editable, vport, vport_editable) VALUES(3, 'voip', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'scm', 0, 0, 0);");
        res = sql.execute("INSERT INTO dummy_template (id, `type`, type_editable, eth_port_1, eth_port_1_editable, eth_port_2, eth_port_2_editable, eth_port_3, eth_port_3_editable, eth_port_4, eth_port_4_editable, wan_1, wan_1_editable, wan_2, wan_2_editable, wan_3, wan_3_editable, wan_4, wan_4_editable, wifi_24_ssid_1, wifi_24_ssid_1_editable, wifi_24_ssid_2, wifi_24_ssid_2_editable, wifi_24_ssid_3, wifi_24_ssid_3_editable, wifi_24_ssid_4, wifi_24_ssid_4_editable, wifi_5_ssid_1, wifi_5_ssid_1_editable, wifi_5_ssid_2, wifi_5_ssid_2_editable, wifi_5_ssid_3, wifi_5_ssid_3_editable, wifi_5_ssid_4, wifi_5_ssid_4_editable, pots_1, pots_1_editable, pots_2, pots_2_editable, catv_1, catv_1_editable, catv_2, catv_2_editable, fdm_srv, fdm_srv_editable, vport, vport_editable) VALUES(4, 'tls', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'scm', 0, 0, 0);");

        // Insert on table hsi_template

        res = sql.execute("INSERT INTO hsi_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv_hsi, fdm_srv_hsi_editable, vlan_editable, vlan_fdm_override, initial_state) VALUES(1, 'hsi', '200 Megas', 1, 'residencial', 1, 1, 1, 1, 'dynamic', 1, '1', 1, 4, 'enabled', 1, 'enabled', 1, 'DHCP', 1, '8.8.8.8', 1, 'disabled', 1, 'disabled', 1, 'hsi', 0, 0, 0, 'active');");
        res = sql.execute("INSERT INTO hsi_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv_hsi, fdm_srv_hsi_editable, vlan_editable, vlan_fdm_override, initial_state) VALUES(5, 'liv', '200 Megas', 1, 'residencial', 0, 1, 1, 1, 'dynamic', 1, '1', 1, 4, 'enabled', 1, 'enabled', 1, 'DHCP', 1, '8.8.8.8', 1, 'disabled', 1, 'disabled', 1, 'liv', 0, 0, 0, 'active');");
        res = sql.execute("INSERT INTO hsi_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv_hsi, fdm_srv_hsi_editable, vlan_editable, vlan_fdm_override, initial_state) VALUES(6, 'optimo', '200 Megas', 1, 'corporate', 0, 1, 1, 1, 'dynamic', 1, '1', 1, 4, 'enabled', 1, 'enabled', 1, 'DHCP', 1, '8.8.8.8', 1, 'disabled', 1, 'disabled', 1, 'optimo', 0, 0, 0, 'active');");
        res = sql.execute("INSERT INTO hsi_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv_hsi, fdm_srv_hsi_editable, vlan_editable, vlan_fdm_override, initial_state) VALUES(7, 'unico', '200 Megas', 1, 'corporate', 0, 1, 1, 1, 'dynamic', 1, '1', 1, 4, 'enabled', 1, 'enabled', 1, 'DHCP', 1, '8.8.8.8', 1, 'disabled', 1, 'disabled', 1, 'unico', 0, 0, 0, 'active');");
        res = sql.execute("INSERT INTO hsi_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv_hsi, fdm_srv_hsi_editable, vlan_editable, vlan_fdm_override, initial_state) VALUES(8, 'gigaplus', '200 Megas', 1, 'corporate', 0, 1, 1, 1, 'dynamic', 1, '1', 1, 4, 'enabled', 1, 'enabled', 1, 'DHCP', 1, '8.8.8.8', 1, 'disabled', 1, 'disabled', 1, 'gigaplus', 0, 0, 0, 'active');");
        res = sql.execute("INSERT INTO hsi_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv_hsi, fdm_srv_hsi_editable, vlan_editable, vlan_fdm_override, initial_state) VALUES(9, 'voippack', '200 Megas', 1, 'corporate', 0, 1, 0, 1, 'dynamic', 1, '1', 1, 4, 'disabled', 0, 'disabled', 0, 'DHCP', 0, NULL, 0, 'disabled', 0, 'disabled', 0, 'voippack', 0, 1, 1, 'active');");
        res = sql.execute("INSERT INTO hsi_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv_hsi, fdm_srv_hsi_editable, vlan_editable, vlan_fdm_override, initial_state) VALUES(10, 'maximo', '200 Megas', 1, 'corporate', 0, 1, 1, 0, 'dynamic', 1, '1', 1, 4, 'disabled', 1, 'enabled', 1, 'DHCP', 1, '8.8.8.8', 1, 'disabled', 1, 'disabled', 1, 'maximo', 0, 1, 1, 'inactive');");

        // Insert on table tls_template

        res = sql.execute("INSERT INTO tls_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv, fdm_srv_editable, vlan, vlan_editable, initial_state) VALUES(11, 'tls', 'test', 1, 'none', 0, 1, 0, 0, 'dynamic', 1, '1', 1, 4, 'disabled', 0, 'enabled', 0, 'DHCP', 0, '8.8.8.8', 0, 'disabled', 0, 'disabled', 0, 'tls', 0, 0, 1, 'active');");
        res = sql.execute("INSERT INTO tls_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv, fdm_srv_editable, vlan, vlan_editable, initial_state) VALUES(12, 'tls-qinq', 'test', 1, 'none', 0, 1, 0, 0, 'dynamic', 1, '1', 1, 4, 'disabled', 0, 'enabled', 0, 'DHCP', 0, '8.8.8.8', 0, 'disabled', 0, 'disabled', 0, 'tlsqinq', 0, 0, 1, 'inactive');");
        res = sql.execute("INSERT INTO tls_template (id, name, trafficProfile, trafficProfile_editable, clientClass, clientClass_editable, maxCpe, maxCpe_editable, reservedIpList_allow, blps_allocation_type, blps_allocation_type_editable, blps, blps_editable, blps_max_amount, gd_status, gd_status_editable, gd_ipv4_nat, gd_ipv4_nat_editable, gd_ipv4_ipMode, gd_ipv4_ipMode_editable, gd_ipv4_dns, gd_ipv4_dns_editable, gd_ipv6_status, gd_ipv6_status_editable, wifi_status, wifi_status_editable, fdm_srv, fdm_srv_editable, vlan, vlan_editable, initial_state) VALUES(13, 'trama-e1', 'test', 1, 'none', 0, 1, 0, 0, 'dynamic', 1, '1', 1, 4, 'disabled', 0, 'enabled', 0, 'DHCP', 0, '8.8.8.8', 0, 'disabled', 0, 'disabled', 0, 'tls', 0, 0, 1, 'inactive');");
        
        // Insert on table video_template

        res = sql.execute("INSERT INTO video_template (id, name, trafficProfile, fdm_srv, fdm_srv_editable, initial_state) VALUES(14, 'video', 'test', 'video', 1, 'active');");
       
        // Insert on table gpon_service_type

        res = sql.execute("INSERT INTO `gpon_service_type` (`id`, `name`, `enable`) VALUES(1, 'publico', 1);");
        res = sql.execute("INSERT INTO `gpon_service_type` (`id`, `name`, `enable`) VALUES(2, 'privado', 1);");
        res = sql.execute("INSERT INTO `gpon_service_type` (`id`, `name`, `enable`) VALUES(3, 'corporativo', 1);");

        
        // Adds traffic profile (public - gpon_service_type) and tags

        res = sql.execute("INSERT INTO srv_traffic_profile (id, name, description, vendors_enabled, service_type_id) VALUES(1, '200 Megas', '200 Megas', 'Zte', 1);");
        res = sql.execute("INSERT INTO srv_traffic_profile (id, name, description, vendors_enabled, service_type_id) VALUES(2, '300 Megas', '300 Megas', 'Zte', 1);");
        res = sql.execute("INSERT INTO srv_traffic_profile (id, name, description, vendors_enabled, service_type_id) VALUES(3, 'VoIP Nacional', 'VoIP Nacional', 'Zte', 1);");

        // Adds gpon_voip_profile

        res = sql.execute("INSERT INTO gpon_voip_profile (id, name) VALUES (1, 'voip');");

        // Adds voip_template

        res = sql.execute("INSERT INTO voip_template (id, name, trafficProfile, trafficProfile_editable, voipProfile, voipProfile_editable, amountLinesMax, amountLinesMax_editable, username_editable, password_editable, fdm_srv, fdm_srv_editable, initial_state) VALUES(15, 'voip', 'VoIP Nacional', 1, 1, 1, 4, 1, 1, 1, 'voip', 1, 'active');");

        //deshabilitamos los BDPs
        res = sql.execute("UPDATE cluster_bdp set enabled=1;");

        // Close the SQL instance.
        sql.close()
    }
}
