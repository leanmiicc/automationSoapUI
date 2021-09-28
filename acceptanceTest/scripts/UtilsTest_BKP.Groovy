// Import the Groovy class required to work with SQL databases.
import groovy.sql.Sql

class UtilsTest {

    def getInstanceDB(dbDsa, dbHost, user, password, log) {
        log.info('executeDSAQuery : ' + dbHost + '/' + dbDsa + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbDsa /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        return sql
    }

    def recreateDsaDB(dbDsa, dbHost, user, password, log) {
        log.info('DSA base recreate: ' + dbHost + '/' + dbDsa + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbDsa /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

        def x = 0
        //List of tables DSA v2. Enable if necessary.
        //def tables = ["subscriptions","subscribers","products","issues","logs","docsis_policies","docsis_params","gpon_policies","gpon_params","srv_traffic_profile","gpon_service_type","voip_profile","docsis_tags","brands","eoc_policies", "eoc_params","activation_params","activation_code"]
        def tables = ['subscriptions', 'subscribers', 'issues', 'logs', 'docsis_policies', 'docsis_params', 'gpon_policies', 'gpon_params', 'srv_traffic_profile', 'gpon_service_type', 'docsis_tags', 'activation_params', 'activation_code']

        for (x = 0; x < tables.size(); x++) {
            res = sql.execute('TRUNCATE TABLE ' + tables[x] + ';');
        }

        res = sql.execute('SET FOREIGN_KEY_CHECKS = 1;');

    // Insert on table gpon_service_type

        res = sql.execute("INSERT INTO `gpon_service_type` (`id`, `name`, `enable`) VALUES(1, 'publico', 1);");
        res = sql.execute("INSERT INTO `gpon_service_type` (`id`, `name`, `enable`) VALUES(2, 'privado', 1);");
        res = sql.execute("INSERT INTO `gpon_service_type` (`id`, `name`, `enable`) VALUES(3, 'corporativo', 1);");

    // Adds traffic profile (public - gpon_service_type) and tags

        res = sql.execute("INSERT INTO srv_traffic_profile (id, name, description, vendors_enabled, service_type_id) VALUES(1, '200 Megas', '200 Megas', 'Zte', 1);");
        res = sql.execute("INSERT INTO srv_traffic_profile (id, name, description, vendors_enabled, service_type_id) VALUES(1, '300 Megas', '300 Megas', 'Zte', 1);");

        // Adds on docsis_tags
        res = sql.execute("INSERT INTO docsis_tags(`id`,`type`,`name`,`description`,`enable`) VALUES (1,'cpe4', 'private','privada',1);");
        res = sql.execute("INSERT INTO docsis_tags(`id`,`type`,`name`,`description`,`enable`) VALUES (2,'cpe4', 'publica','public',1);");

        res = sql.execute('UPDATE cluster_bdp set enabled=1;'); //deshabilitamos los BDPs

        // Close the SQL instance.
        sql.close()
    }

    def insertActivationParamsInDsaDB(dbDsa, dbHost, user, password, log) {
        log.info('Inserting activation params in DSA: ' + dbHost + '/' + dbDsa + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbDsa /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

    // Adds tenants, traffic profile and tags

        def res = sql.execute("INSERT INTO `activation_params` (`id`, `technology`, `name`, `type`, `data_type`, `default`, `mandatory`, `editabled`) VALUES ('1', '1', 'crm', 'custom', 'string', '', '1', '1');");
        res = sql.execute("INSERT INTO `activation_params` (`id`, `technology`, `name`, `type`, `data_type`, `default`, `mandatory`, `editabled`) VALUES ('2', '2', 'crm', 'custom', 'string', '', '1', '1');");

        sql.close()
    }

    def recreateDsaProductsV03(dbDsa, dbHost, user, password, log) {
        log.info('DSA base recreate: ' + dbHost + '/' + dbDsa + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbDsa /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

    // Products and Policies DOCSIS with brand

        res = sql.execute("INSERT INTO `products` ( `id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('1', 'Docsis - Internet', '1', 'Docsis - Internet', '1', '2');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`,`name`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`) VALUES ('1', 'Docsis - Internet', 'test', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '0');");

        res = sql.execute("INSERT INTO `products` (`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('2','Docsis - Voip SIP', '1', 'Docsis - Voip SIP', '1', '2');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`,`name`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`) VALUES ('2', 'Docsis - Voip SIP', 'test2', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '0');");

        res = sql.execute("INSERT INTO `products` (`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ( '3', 'Docsis - L2vpn', '1', 'Docsis - L2vpn', '1', '2');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`,`name`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`) VALUES ('3', 'Docsis - L2vpn', 'test3', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '0');");

        res = sql.execute("INSERT INTO `products` (`id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('4', 'Docsis - VoIP NCS', '1', 'Docsis - VoIP NCS', '1', '2');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`,`name`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`) VALUES ('4', 'Docsis - VoIP NCS', 'test4', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '0');");

    // Products and Policies GPON with brand

        res = sql.execute("INSERT INTO `products` (`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('5','Gpon - Internet', '2', 'Gpon - Internet', '1', '2');");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`,`name`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`) VALUES ('5','Gpon - Internet', '3', '0', '1', '1', '1', '1', '1', '0', '0', '0');");

        res = sql.execute("INSERT INTO `products`(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('6','Gpon - Voip', 2, 'Gpon - Voip', 1, 2);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`,`name`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`) VALUES ('6','Gpon - Voip', '3', '0', '1', '1', '1', '1', '1', '0', '0', '0');");

        res = sql.execute("INSERT INTO `products`(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('7','Gpon - Internet + Video', 2, 'Gpon - Internet + Video', 1, 2);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`,`name`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`) VALUES ('7','Gpon - Internet + Video', '3', '0', '1', '1', '1', '1', '1', '0', '0', '0');");

        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('8','Gpon - Internet + VoIp + Video', 2, 'Gpon - Internet + VoIp + Video', 1, 2);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`,`name`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`) VALUES ('8','Gpon - Internet + VoIp + Video', '3', '0', '1', '1', '1', '1', '1', '0', '0', '0');");

        // Products and Policies DOCSIS with brand

        res = sql.execute("INSERT INTO `products` ( `id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('9', 'DOCSIS - 20Mbps Basic', '1', 'DOCSIS - 20Mbps Basic', '1', '2');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`,`name`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`) VALUES ('9', 'DOCSIS - 20Mbps Basic', 'test', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '0');");

        res = sql.execute("INSERT INTO `products` ( `id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('10', 'Docsis Full', '1', 'Docsis Full', '1', '2');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`,`name`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe4_tag_id`, `cpe6_tag_id`, `mta_tag_id`) VALUES ('10','Docsis Full', 'test', '0', '0', '1', '1', '0', '1', '1', '1', '0', '1', '0', '0');");


        // Products and Policies GPON with brand
        
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('11','Gpon Full', 2, 'Gpon Full', 1, 2);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `name`,`srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`) VALUES ('11','Gpon Full','3', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0');");
        
        //Products and Policies DOCSIS with brand

        res = sql.execute("INSERT INTO `products` ( `id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('12', 'Docsis Full SIP', '1', 'Docsis Full SIP', '1', '2');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`,`name`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe4_tag_id`, `cpe6_tag_id`, `mta_tag_id`) VALUES ('12','Docsis Full SIP','test', '0', '0', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0');");

        //Products and Policies GPON with brand
        
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('13','Gpon only voIP', 2, 'Gpon only voIP', 1, 2);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `name`,`srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`) VALUES ('13','Gpon only voIP', '3', '1', '0', '0', '0', '1', '0', '0', '2', '0');");
        
        // Hasta Aca.

        //Gpon
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('14','Gpon only video', '2', 'Gpon only video', '1', '2');");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `data_flag`, `voip_profile`, `tls_flag`) VALUES ('14', '3', '0', '0', '0', '8', '1', '0', '1', '0', '0', '0', '0', '0');");

        //Products and Policies DOCSIS with brand
        res = sql.execute("INSERT INTO products(`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES('15','Docsis-sin-tag', '1', 'test', '1', '2');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_profile`, `sip_params`) VALUES ('15', 'test', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', 'USER_PASS');");

    // ############# Descomentar lo siguiente cuando se haya pasado los cambios del arreglo de parámetros SIP opcionales al branch de 2.6 ######################### //

        //Products and Policies GPON with brand
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('16','Gpon SIP USER', 2, 'Gpon SIP USER', 1, 2);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('16', '3', '1', '0', '0', '8', '1', '0', '0', '1', '0', 'SIP', '0', '0', 'USER', '0');");
        //Gpon
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('17','Gpon SIP PASSWORD', 2, 'Gpon SIP PASSWORD', 1, 2);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('17', '3', '1', '0', '0', '8', '1', '0', '0', '1', '0', 'SIP', '0', '0', 'PASSWORD', '0');");

        //Gpon
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('18','Gpon SIP NONE', 2, 'Gpon SIP NONE', 1, 2);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('18', '3', '1', '0', '0', '8', '1', '0', '0', '1', '0', 'SIP', '0', '0', 'NONE', '0');");

        // Products and Policies GPON with brand
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('19','Docsis - Voip SIP USER', 1, 'Docsis - Voip SIP USER', 1, 2);");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('19', 'test', '0', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '1', 'SIP', '0', 'USER');");

        //Docsis
        res = sql.execute("INSERT INTO products(`id`, `name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('20','Docsis - Voip SIP PASS', 1, 'Docsis - Voip SIP PASS', 1, 2);");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('20', 'test', '0', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '1', 'SIP', '0', 'PASSWORD');");

        //Docsis
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('21','Docsis - Voip SIP NONE', 1, 'Docsis - Voip SIP NONE', 1, 2);");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('21', 'test', '0', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '1', 'SIP', '0', 'NONE');");

        // Complete column sip_params
        res = sql.execute("UPDATE docsis_policies SET sip_params = 'USER_PASS' WHERE voip_type = 'SIP' AND sip_params IS NULL")
        res = sql.execute("UPDATE gpon_policies SET sip_params = 'USER_PASS' WHERE voip_type = 'SIP' AND sip_params IS NULL")

        //voIP Profile
        res = sql.execute("INSERT INTO `voip_profile`(`id`,`name`,`label`) VALUES(1,'test','Profile voIP de prueba');");
        res = sql.execute("INSERT INTO `voip_profile`(`id`,`name`,`label`) VALUES(2,'fake-profile','Profile voIP de prueba 2');");

        // Products and Policies DOCSIS with brand
        // Agrego el caso de productos con tag para las pruebas de cambio de producto.
        res = sql.execute("INSERT INTO products(`id`, `name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('22','Docsis - Internet con tag public', 1, 'Docsis - Internet con tag public', 1, 2);");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe4_tag_id`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_profile`, `sip_params`) VALUES ('22', 'test', '0', '0', '0', '1', '0', '1', '1', '1', '0', '2', '0', '0', '0', '0', 'NONE');");

        // Products and Policies GPON with brand
        // Agregamos un producto con service name y tag activados
        res = sql.execute("INSERT INTO `products` ( `id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('23','GPON - Producto con internet y tag', '2', 'Un producto que agrega el tag', '0', '2');");
        res = sql.execute("INSERT INTO  `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `data_flag`, `voip_profile`, `tls_flag`) VALUES ('23', '3', '0', '0', '0', '1', '1', '1', '1', '0', '0', '0', '1', '0', '0');");

        // Products and Policies DOCSIS without brand

        res = sql.execute("INSERT INTO `products` ( `id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('33', 'Docsis - Internet', '1', 'Docsis - Internet', '1', '1');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_profile`, `sip_params`) VALUES ('33', 'test', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', 'USER_PASS');");

        res = sql.execute("INSERT INTO `products` (`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('34','Docsis - Voip SIP', '1', 'Docsis - Voip SIP', '1', '1');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('34', 'test', '0', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '2', 'SIP', '0', 'USER_PASS');");

        res = sql.execute("INSERT INTO `products` (`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ( '35', 'Docsis - L2vpn', '1', 'Docsis - L2vpn', '1', '1');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_profile`, `sip_params`) VALUES ('35', '', '0', '0', '0', '0', '1', '0', '0', '1', '0', '0', '0', '0', '0', 'USER_PASS');");

        res = sql.execute("INSERT INTO `products` (`id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('36', 'Docsis - VoIP NCS', '1', 'Docsis - VoIP NCS', '1', '1');");
        res = sql.execute("INSERT INTO  `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('36', 'test', '0', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '2', 'NCS', '0', 'USER_PASS');");

    // Products and Policies GPON without brand

        res = sql.execute("INSERT INTO `products` (`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('37','Gpon - Internet', '2', 'Gpon - Internet', '1', '1');");
        res = sql.execute("INSERT INTO  `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `data_flag`, `voip_profile`, `tls_flag`) VALUES ('37', '3', '0', '1', '1', '1', '1', '1', '0', '0', '0', '1', '0', '0');");

        res = sql.execute("INSERT INTO `products`(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('38','Gpon - Voip', 2, 'Gpon - Voip', 1, 1);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('38', '3', '1', '0', '0', '8', '1', '0', '0', '2', '0', 'SIP', '0', '0', 'USER_PASS', '0');");

        res = sql.execute("INSERT INTO `products`(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('39','Gpon - Internet + Video', 2, 'Gpon - Internet + Vide', 1, 1);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `video_flag`, `voip_lines_amount`, `nat_enable`, `data_flag`, `voip_profile`, `tls_flag`) VALUES ('39', '3', '0', '1', '1', '1', '1', '1', '0', '0', '1', '0', '0');");

        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('40','Gpon - Internet + VoIp + Video', 2, 'Gpon - Internet + VoIp + Video', 1, 1);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('40', '3', '1', '0', '0', '1', '1', '1', '1', '1', '1', '0', 'SIP', '1', '0', 'USER_PASS', '0');");

        // Products and Policies DOCSIS without brand
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('41','DOCSIS - 20Mbps Basic', 1, 'DOCSIS - 20Mbps Basic', 1, 1);");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_profile`, `sip_params`) VALUES ('41', 'test', '0', '0', '0', '1', '0', '1', '1', '1', '1', '0', '0', '0', '0', 'USER_PASS');");

        res = sql.execute("INSERT INTO `products`(`id`,`name`,`tech_id`,`description`,`enable`,`brand_id`) VALUES ('42','Docsis Full', '1', 'Docsis Full', '1', '1');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe4_tag_id`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('42', 'test', '0', '0', '1', '1', '0', '1', '1', '1', '0', '1', '0', '0', '2', 'NCS', '0', 'USER_PASS');");

        // Products and Policies GPON without brand
        res = sql.execute("INSERT INTO `products`(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('43', 'Gpon Full', '2', 'Gpon Full', '1', '1');");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('43', '3', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', 'SIP', '1', '0', 'USER_PASS', '0');");

        //Products and Policies DOCSIS without brand
        res = sql.execute("INSERT INTO `products`(`id`,`name`,`tech_id`,`description`,`enable`,`brand_id`) VALUES ('44','Docsis Full SIP', '1', 'Docsis Full SIP', '1', '1');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe4_tag_id`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('44', 'test', '0', '0', '1', '1', '0', '1', '1', '1', '1', '1', '0', '0', '1', 'SIP', '0', 'USER_PASS');");

        //Products and Policies GPON without brand
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('45','Gpon only voIP', '2', 'Gpon only voIP', '1', '1');");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('45', '3', '1', '0', '0', '0', '1', '0', '0', '2', '0', 'SIP', '0', '0', 'USER_PASS', '0');");
        //Gpon
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('46','Gpon only video', '2', 'Gpon only video', '1', '1');");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `data_flag`, `voip_profile`, `tls_flag`) VALUES ('46', '3', '0', '0', '0', '8', '1', '0', '1', '0', '0', '0', '0', '0');");

        //Products and Policies DOCSIS without brand
        res = sql.execute("INSERT INTO products(`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES('47','Docsis-sin-tag', '1', 'Docsis-sin-tag', '1', '1');");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_profile`, `sip_params`) VALUES ('47', 'test', '0', '0', '0', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', 'USER_PASS');");

    // ############# Descomentar lo siguiente cuando se haya pasado los cambios del arreglo de parámetros SIP opcionales al branch de 2.6 ######################### //

        //Products and Policies GPON without brand
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('48','Gpon SIP USER', 2, 'Gpon SIP USER', 1, 1);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('48', '3', '1', '0', '0', '8', '1', '0', '0', '1', '0', 'SIP', '0', '0', 'USER', '0');");
        //Gpon
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('49','Gpon SIP PASSWORD', 2, 'Gpon SIP PASSWORD', 1, 1);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('49', '3', '1', '0', '0', '8', '1', '0', '0', '1', '0', 'SIP', '0', '0', 'PASSWORD', '0');");

        //Gpon
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('50','Gpon SIP NONE', 2, 'Gpon SIP NONE', 1, 1);");
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`, `data_flag`, `voip_profile`, `sip_params`, `tls_flag`) VALUES ('50', '3', '1', '0', '0', '8', '1', '0', '0', '1', '0', 'SIP', '0', '0', 'NONE', '0');");

        //Products and Policies GPON without brand
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('51','Docsis - Voip SIP USER', 1, 'Docsis - Voip SIP USER', 1, 1);");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('51', 'test', '0', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '1', 'SIP', '0', 'USER');");

        //Docsis
        res = sql.execute("INSERT INTO products(`id`, `name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('52','Docsis - Voip SIP PASS', 1, 'Docsis - Voip SIP PASS', 1, 1);");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('52', 'test', '0', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '1', 'SIP', '0', 'PASSWORD');");

        //Docsis
        res = sql.execute("INSERT INTO products(`id`,`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('53','Docsis - Voip SIP NONE', 1, 'Docsis - Voip SIP NONE', 1, 1);");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `voip_profile`, `sip_params`) VALUES ('53', 'test', '0', '0', '1', '0', '0', '0', '0', '1', '0', '0', '0', '1', 'SIP', '0', 'NONE');");

        // Products and Policies DOCSIS without brand
        // Agrego el caso de productos con tag para las pruebas de cambio de producto.
        res = sql.execute("INSERT INTO products(`id`, `name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('54','Docsis - Internet con tag public', 1, 'Docsis - Internet con tag public', 1, 1);");
        res = sql.execute("INSERT INTO `docsis_policies` (`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe4_tag_id`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_profile`, `sip_params`) VALUES ('54', 'test', '0', '0', '0', '1', '0', '1', '1', '1', '0', '2', '0', '0', '0', '0', 'NONE');");

        // Products and Policies GPON without brand
        // Agregamos un producto con service name y tag activados
        res = sql.execute("INSERT INTO `products` ( `id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('55','GPON - Producto con internet y tag', '2', 'Un producto que agrega el tag', '0', '1');");
        res = sql.execute("INSERT INTO  `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `data_flag`, `voip_profile`, `tls_flag`) VALUES ('55', '3', '0', '0', '0', '1', '1', '1', '1', '0', '0', '0', '1', '0', '0');");

        //Products and Policies EoC without brand
        res = sql.execute("INSERT INTO products(`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES(56,'Eoc - test 1', 3, 'test 1', 1, 1)")
        res = sql.execute("INSERT INTO eoc_policies(`product_id`, `traffic_profile`, `data_flag`) VALUES('56', 'profile_test01', 1)")

        res = sql.execute("INSERT INTO products(`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES(57,'Eoc - test 2', 3, 'test 2', 1, 1)")
        res = sql.execute("INSERT INTO eoc_policies(`product_id`, `traffic_profile`, `data_flag`) VALUES('57', 'profile_test02', 1)")

        //Products and Policies EoC with brand
        res = sql.execute("INSERT INTO products(`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES(58,'Eoc - test 1', 3, 'test 1', 1, 2)")
        res = sql.execute("INSERT INTO eoc_policies(`product_id`, `traffic_profile`, `data_flag`) VALUES('58', 'profile_test01', 1)")

        res = sql.execute("INSERT INTO products(`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES(59,'Eoc - test 2', 3, 'test 2', 1, 2)")
        res = sql.execute("INSERT INTO eoc_policies(`product_id`, `traffic_profile`, `data_flag`) VALUES('59', 'profile_test02', 1)")

        // Close the SQL instance.
        sql.close()
    }

    def recreateDsaProductsV01(dbDsa, dbHost, user, password, log) {
        log.info('DSA base recreate: ' + dbHost + '/' + dbDsa + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbDsa /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

    // Products and Policies DOCSIS with brand

    // Products and Policies DOCSIS

        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Docsis - Internet', 1, 'Docsis - Internet', 1, 1);");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`) VALUES (1, 'test', 10, 10, 0, 0, 0, 0, 1, 1, 0, 0, 0);");

        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Docsis - Voip SIP', 1, 'Docsis - Voip SIP', 1, 1);");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `sip_params`) VALUES (2, 'voip-test', 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 'SIP', 'USER_PASS');");

        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Docsis - L2vpn', 1, 'Docsis - L2vpn', 1, 1);");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`) VALUES (3, '', 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0);");

        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Docsis - VoIP NCS', 1, 'Docsis - VoIP NCS', 1, 1);");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`) VALUES (4, 'docsis-voip-ncs', 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 'NCS');");

    // Products and Policies GPON

        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon - Internet', 2, 'Gpon - Internet', 1, 1);");
        res = sql.execute('INSERT INTO gpon_policies(`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`,`data_flag`) VALUES (5, 1, 0, 0, 0, 8, 1, 1, 0, 0, 0,NULL,1);');

        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon - Voip', 2, 'Gpon - Voip', 1, 1);");
        res = sql.execute("INSERT INTO gpon_policies(`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`,`data_flag`) VALUES (6, 1, 1, 0, 0, 8, 1, 1, 0, 1, 0, 'SIP',0);");

        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon - Internet + Video', 2, 'Gpon - Internet + Video', 1, 1);");
        res = sql.execute('INSERT INTO gpon_policies(`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`,`data_flag`) VALUES (7, 2, 0, 0, 0, 8, 1, 1, 1, 0, 0,NULL,1);');

        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon - Internet + VoIp + Video', 2, 'Gpon - Internet + VoIp + Video', 1, 1);");
        res = sql.execute("INSERT INTO gpon_policies(`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`,`data_flag`) VALUES (8, 1, 1, 0, 0, 8, 1, 1, 1, 1, 0, 'SIP',1);");

        // insert en tabla gpon_service_type
        res = sql.execute("INSERT INTO `gpon_service_type` (`id`, `name`, `service_name`, `enable`) VALUES ('1', 'publico', 'public', '1');");
        res = sql.execute("INSERT INTO `gpon_service_type` (`id`, `name`, `service_name`, `enable`) VALUES ('2', 'privado', 'private', '1');");

        //Docsis
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('DOCSIS - 20Mbps Basic', 1, 'DOCSIS - 20Mbps Basic', 1, 1);");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`) VALUES (9, 'file01Test', 50000, 25000, 0, 1, 0, 1, 1, 1, 0, 0, 0);");

        res = sql.execute("INSERT INTO `products`(`name`,`tech_id`,`description`,`enable`,`brand_id`) VALUES ('Docsis Full', '1', 'Docsis Full', '1', '1');");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`,`bdp_file_tag`,`service_type_id`,`ds_max`,`us_max`,`voip_flag`,`wifi_flag`,`l2vpn_flag`,`static_ip_flag`,`static_ip`,`dynamic_ip`,`ipv6_flag`,`cpe4_tag_id`,`cpe6_tag_id`,`mta_tag_id`,`voip_lines_amount`,`voip_type`) VALUES('10', 'tagBDP', NULL, '500', '500', '1', '1', '0', '1', '20', '1', '1', '1', '0', '0', '1', 'NCS');");

        //Gpon
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon Full', '2', 'Gpon Full', '1', '1');");
        res = sql.execute("INSERT INTO gpon_policies(`product_id`,`srv_traffic_profile_id`,`voip_flag`,`wifi_flag`,`static_ip_flag`,`static_ip`,`dynamic_ip`,`service_name`,`video_flag`,`voip_lines_amount`,`nat_enable`,`voip_type`,`data_flag`) VALUES ('11', '2', '1', '1', '1', '1', '1', '0', '1', '1', '1', 'SIP',1);");

        //Docsis
        res = sql.execute("INSERT INTO `products`(`name`,`tech_id`,`description`,`enable`,`brand_id`) VALUES ('Docsis Full SIP', '1', 'Docsis Full SIP', '1', '1');");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`,`bdp_file_tag`,`service_type_id`,`ds_max`,`us_max`,`voip_flag`,`wifi_flag`,`l2vpn_flag`,`static_ip_flag`,`static_ip`,`dynamic_ip`,`ipv6_flag`,`cpe4_tag_id`,`cpe6_tag_id`,`mta_tag_id`,`voip_lines_amount`,`voip_type`) VALUES('12', 'tagBDP', NULL, '500', '500', '1', '1', '0', '1', '20', '1', '1', '1', '0', '0', '2', 'SIP');");

        //Gpon
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon only voIP', '2', 'Gpon only voIP', '1', '1');");
        res = sql.execute("INSERT INTO gpon_policies(`product_id`,`srv_traffic_profile_id`,`voip_flag`,`wifi_flag`,`static_ip_flag`,`static_ip`,`dynamic_ip`,`service_name`,`video_flag`,`voip_lines_amount`,`nat_enable`,`voip_type`,data_flag, sip_params) VALUES ('13', '2', '1', '0', '0', '0', '1', '0', '0', '2', '0', 'SIP', '0', 'USER_PASS');");

        //Gpon
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon only video', '2', 'Gpon only video', '1', '1');");
        res = sql.execute("INSERT INTO gpon_policies(`product_id`,`srv_traffic_profile_id`,`voip_flag`,`wifi_flag`,`static_ip_flag`,`static_ip`,`dynamic_ip`,`service_name`,`video_flag`,`voip_lines_amount`,`nat_enable`,`voip_type`,data_flag) VALUES ('14', '2', '0', '0', '0', '0', '1', '0', '1', '0', '0', NULL, '0');");

        //Docsis
        res = sql.execute("INSERT INTO products(`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES('Docsis-sin-tag', '1', 'Docsis-sin-tag', '1', '1');");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`,`bdp_file_tag`,`service_type_id`,`ds_max`,`us_max`,`voip_flag`,`wifi_flag`,`l2vpn_flag`,`static_ip_flag`,`static_ip`,`dynamic_ip`,`ipv6_flag`,`cpe4_tag_id`,`cpe6_tag_id`,`mta_tag_id`,`voip_lines_amount`) VALUES('15', 'test', NULL, '50', '50', '0', '0', '0', '0', '1', '1', '0', NULL, '0', '0', '0');");

    // ############# Descomentar lo siguiente cuando se haya pasado los cambios del arreglo de parámetros SIP opcionales al branch de 2.6 ######################### //

        //Gpon
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon SIP USER', 2, 'Gpon SIP USER', 1, 1);");
        res = sql.execute("INSERT INTO gpon_policies(`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`,`data_flag`, `sip_params`) VALUES (16, 1, 1, 0, 0, 8, 1, 1, 0, 1, 0, 'SIP',0, 'USER');");

        //Gpon
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon SIP PASSWORD', 2, 'Gpon SIP PASSWORD', 1, 1);");
        res = sql.execute("INSERT INTO gpon_policies(`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`,`data_flag`, `sip_params`) VALUES (17, 1, 1, 0, 0, 8, 1, 1, 0, 1, 0, 'SIP',0, 'PASSWORD');");

        //Gpon
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Gpon SIP NONE', 2, 'Gpon SIP NONE', 1, 1);");
        res = sql.execute("INSERT INTO gpon_policies(`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `voip_type`,`data_flag`, `sip_params`) VALUES (18, 1, 1, 0, 0, 8, 1, 1, 0, 1, 0, 'SIP',0, 'NONE');");

        //Docsis
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Docsis - Voip SIP USER', 1, 'Docsis - Voip SIP USER', 1, 1);");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `sip_params`) VALUES (19, 'voip-test', 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 'SIP', 'USER');");

        //Docsis
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Docsis - Voip SIP PASS', 1, 'Docsis - Voip SIP PASS', 1, 1);");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `sip_params`) VALUES (20, 'voip-test', 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 'SIP', 'PASSWORD');");

        //Docsis
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Docsis - Voip SIP NONE', 1, 'Docsis - Voip SIP NONE', 1, 1);");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`, `voip_lines_amount`, `voip_type`, `sip_params`) VALUES (21, 'voip-test', 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 'SIP', 'NONE');");

        // Complete column sip_params
        res = sql.execute("UPDATE docsis_policies SET sip_params = 'USER_PASS' WHERE voip_type = 'SIP' AND sip_params IS NULL")
        res = sql.execute("UPDATE gpon_policies SET sip_params = 'USER_PASS' WHERE voip_type = 'SIP' AND sip_params IS NULL")

        //voIP Profile
        res = sql.execute("INSERT INTO `voip_profile`(`id`,`name`,`label`) VALUES(1,'test','Profile voIP de prueba');");
        res = sql.execute("INSERT INTO `voip_profile`(`id`,`name`,`label`) VALUES(2,'fake-profile','Profile voIP de prueba 2');");

        // Agrego el caso de productos con tag para las pruebas de cambio de producto.
        res = sql.execute("INSERT INTO products(`name`,`tech_id`, `description`, `enable`, `brand_id`) VALUES ('Docsis - Internet con tag public', 1, 'Docsis - Internet con tag public', 1, 1);");
        res = sql.execute("INSERT INTO docsis_policies(`product_id`, `bdp_file_tag`, `ds_max`, `us_max`, `voip_flag`, `wifi_flag`, `l2vpn_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `ipv6_flag`, `cpe6_tag_id`, `mta_tag_id`,`cpe4_tag_id`) VALUES (22, 'test', 10, 10, 0, 0, 0, 0, 1, 1, 0, 0, 0,2);");

        // Agregamos un producto con service name y tag activados
        res = sql.execute("INSERT INTO `gpon_policies` (`product_id`, `srv_traffic_profile_id`, `voip_flag`, `wifi_flag`, `static_ip_flag`, `static_ip`, `dynamic_ip`, `service_type`, `service_name`, `video_flag`, `voip_lines_amount`, `nat_enable`, `data_flag`, `voip_profile`, `sip_params`) VALUES ('23', '1', '0', '0', '0', '1', '1', '1', '1', '0', '0', '1', '1', '0', 'USER_PASS');");
        res = sql.execute("INSERT INTO `products` (`id`, `name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES ('23', 'GPON - Producto con internet y tag', '2', 'Un producto que agrega el tag', '1', '1');");

        //EoC
        res = sql.execute("INSERT INTO products(`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES(24,'Eoc - test 1', 3, 'test 1', 1, 1)")
        res = sql.execute("INSERT INTO eoc_policies(`product_id`, `traffic_profile`, `data_flag`) VALUES('24', 'profile_test01', 1)")

        res = sql.execute("INSERT INTO products(`id`,`name`, `tech_id`, `description`, `enable`, `brand_id`) VALUES(25,'Eoc - test 2', 3, 'test 2', 1, 1)")
        res = sql.execute("INSERT INTO eoc_policies(`product_id`, `traffic_profile`, `data_flag`) VALUES('25', 'profile_test02', 1)")

        // Close the SQL instance.
        sql.close()
    }

    def recreateFdmDB(projectObject, log) {

        def dbFdm = projectObject.getPropertyValue('dbFdm')
        def dbHost = projectObject.getPropertyValue('dbHost')
        def user = projectObject.getPropertyValue('dbUser')
        def password = projectObject.getPropertyValue('dbPass')

        log.info('FDM base recreate: ' + dbHost + '/' + dbFdm + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbFdm /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

        def x = 0
        def tables = ['fdm_ont_instance_srv', 'fdm_ont_rule_srv', 'fdm_ont_inventory', 'fdm_ont_location', 'fdm_olt_inventory', 'fdm_develop_ont_profile', 'fdm_ont_capability', 'fdm_tasks']

        for (x = 0; x < tables.size(); x++) {
            res = sql.execute('TRUNCATE TABLE ' + tables[x] + ';');
        }

        res = sql.execute('SET FOREIGN_KEY_CHECKS = 1;');

        res = sql.execute("INSERT INTO `fdm_ont_capability` (`id`, `eth_port`, `wan`, `wifi_24_ssid`, `wifi_5_ssid`, `pot`, `catv`, `wan_mgmt`, `wan_l3`, `wan_l2`, `wan_voip`, `wan_iptv`) VALUES ('1', '4', '4', '4', '4', '4', '4', '4', '0', '0', '0', '0');");
        res = sql.execute("INSERT INTO `fdm_develop_ont_profile` (`name`, `wan`, `id_capability`) VALUES ('Develop', '2', '1');");

        // Agregamos registros en la tabla fdm_ont_rule_srv, fdm_olt_inventory, fdm_develop_ont_profile, fdm_ont_capability
        res = sql.execute("INSERT INTO `fdm_ont_rule_srv` (`sn`, `model`, `vendor`, `swver`, `hwver`, `olt_profile`, `srv_name`, `srv_file`, `brand`, `olt_name`) VALUES ('*', '*', '*', '*', '*', 'Develop', 'hsi', 'hsi.json', '*', '*');");
        res = sql.execute("INSERT INTO `fdm_ont_rule_srv` (`sn`, `model`, `vendor`, `swver`, `hwver`, `olt_profile`, `srv_name`, `srv_file`, `brand`, `olt_name`) VALUES ('*', '*', '*', '*', '*', 'Develop', 'public_hsi', 'hsi.json', '*', '*');");
        res = sql.execute("INSERT INTO `fdm_ont_rule_srv` (`sn`, `model`, `vendor`, `swver`, `hwver`, `olt_profile`, `srv_name`, `srv_file`, `brand`, `olt_name`) VALUES ('*', '*', '*', '*', '*', 'Develop', 'private_hsi', 'hsi.json', '*', '*');");
        res = sql.execute("INSERT INTO `fdm_ont_rule_srv` (`sn`, `model`, `vendor`, `swver`, `hwver`, `olt_profile`, `srv_name`, `srv_file`, `brand`, `olt_name`) VALUES ('*', '*', '*', '*', '*', 'Develop', 'video', 'video.json', '*', '*');");

        res = sql.execute("INSERT INTO `fdm_ont_rule_srv` (`sn`, `model`, `vendor`, `swver`, `hwver`, `olt_profile`, `srv_name`, `srv_file`, `brand`, `olt_name`) VALUES ('*', '*', '*', '*', '*', 'Develop', 'voip', 'voip.json', '*', '*');");
        res = sql.execute("INSERT INTO `fdm_ont_rule_srv` (`sn`, `model`, `vendor`, `swver`, `hwver`, `olt_profile`, `srv_name`, `srv_file`, `brand`, `olt_name`) VALUES ('*', '*', '*', '*', '*', 'Develop', 'tls', 'tls.json', '*', '*');");
        res = sql.execute("INSERT INTO `fdm_ont_rule_srv` (`sn`, `model`, `vendor`, `swver`, `hwver`, `olt_profile`, `srv_name`, `srv_file`, `brand`, `olt_name`) VALUES ('*', '*', '*', '*', '*', 'Develop', 'noauth', 'noauth.json', '*', '*');");

        res = sql.execute("INSERT INTO `fdm_olt_inventory` (`id`, `ip`, `config`, `community_ro`, `community_rw`, `name`, `autofind_enable`, `enable`, `profile`, `tenant`) VALUES ('1', '1.1.1.1', '{\"data\": \"esto es data 1\"}', 'sc', 'sc', 'OLT-1', '1', '1', 'Develop', 'default');");
        res = sql.execute("INSERT INTO `fdm_olt_inventory` (`id`, `ip`, `config`, `community_ro`, `community_rw`, `name`, `autofind_enable`, `enable`, `profile`, `tenant`) VALUES ('2', '10.200.0.100', '{\"data\": \"esto es data 2\"}', 'public', 'publicRW', 'OLT2', '1', '1', 'Develop', 'Guernica');");

        // Close the SQL instance.
        sql.close()
    }

    def FdmOntWithBrand(projectObject, log) {
        def dbFdm = projectObject.getPropertyValue('dbFdm')
        def dbHost = projectObject.getPropertyValue('dbHost')
        def user = projectObject.getPropertyValue('dbUser')
        def password = projectObject.getPropertyValue('dbPass')

        log.info('FDM base recreate: ' + dbHost + '/' + dbFdm + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbFdm /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

        res = sql.execute('SET FOREIGN_KEY_CHECKS = 1;');
        def x = 0

        def serialNumbers = [
            'DEV1999303834CCC',
            'DEVD33330383112A',
            'DEVD33330383110A',
            'DEVD33330383110B',
            'DEVD33330383110C',
            'DEVD33330383110D',
            'DEVD33330383110E',
            'DEVD33330383110F',
            'DEVD33330383110H',
            'DEVD33330383110G',
            'DEVD33330383110I',
            'DEVD33330383110J',
            'DEVD33330383110K',
            'DEVD33330383110L',
            'DEVD33330383110M',
            'DEVD33330383110N',
            'DEVD33330383110O',
            'DEVD33330383110P',
            'DEV1999303834C11'
            ]

        for (x = 0; x < serialNumbers.size(); x++) {
            log.info(x.toString()  + ':' + serialNumbers[x])
            res = sql.execute("INSERT INTO `fdm_ont_inventory` (`sn`, `model`, `vendor`, `swver`, `hwver`, `discover_time`, `ont_profile`, `tr069_mgmt`, `status`, `description`, `roaming`) VALUES ('" + serialNumbers[x] + "', 'stechsLab', 'Develop', '', '', '2019-01-15 00:00:00', 'develop', 'disabled', 'discovered', '', 'disabled');");
            res = sql.execute("INSERT INTO `fdm_ont_location` (`sn`, `type`, `olt_id`, `pon_id`, `shelf_id`, `slot_id`, `port_id`, `ont_id`) VALUES ('" + serialNumbers[x] + "', 'primary', '2', '45464656', '0', '1', '1', '" + x.toString() + "');");
        }

        // Close the SQL instance.
        sql.close()
    }

    def FdmOntWithoutBrand(projectObject, log) {
        def dbFdm = projectObject.getPropertyValue('dbFdm')
        def dbHost = projectObject.getPropertyValue('dbHost')
        def user = projectObject.getPropertyValue('dbUser')
        def password = projectObject.getPropertyValue('dbPass')

        log.info('FDM base recreate: ' + dbHost + '/' + dbFdm + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbFdm /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

        res = sql.execute('SET FOREIGN_KEY_CHECKS = 1;');
        def x = 0

        def serialNumbers = [
        'DEV1999303834CCC',
        'DEVD33330383112A',
        'DEVS0372544D',
        'DEVS0372545A',
        'DEVS03725489',
        'DEVS0372548D',
        'DEVS037DBC67',
        'DEVS037DBC68',
        'DEVS037DBC74',
        'DEVS037DBC75',
        'DEVS037DBC76',
        'DEVS037DBC77',
        'DEVS037DBC73',
        'DEVS037DBC72',
        'DEVS037DBC71',
        'DEVS037DBC70',
        'DEVS037DBC69',
        'DEVS037DBC78',
        'DEVS037DBC79',
        'DEVS037DBC80'
        ]

        for (x = 0; x < serialNumbers.size(); x++) {
            log.info(x.toString()  + ':' + serialNumbers[x])
            res = sql.execute("INSERT INTO `fdm_ont_inventory` (`sn`, `model`, `vendor`, `swver`, `hwver`, `discover_time`, `ont_profile`, `tr069_mgmt`, `status`, `description`, `roaming`) VALUES ('" + serialNumbers[x] + "', 'stechsLab', 'Develop', '', '', '2019-01-15 00:00:00', 'develop', 'disabled', 'discovered', '', 'disabled');");
            res = sql.execute("INSERT INTO `fdm_ont_location` (`sn`, `type`, `olt_id`, `pon_id`, `shelf_id`, `slot_id`, `port_id`, `ont_id`) VALUES ('" + serialNumbers[x] + "', 'primary', '1', '45464656', '0', '1', '1', '" + x.toString() + "');");
        }

        // Close the SQL instance.
        sql.close()
    }

    def recreateBdpDB(projectObject, log) {

        def dbBdp = projectObject.getPropertyValue('dbBdp')
        def dbHost = projectObject.getPropertyValue('dbHost')
        def user = projectObject.getPropertyValue('dbUser')
        def password = projectObject.getPropertyValue('dbPass')

        log.info('BDP base recreate: ' + dbHost + '/' + dbBdp + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbBdp /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

        def x = 0
        def tables = ['docsis_modem', 'config_opts_macs', 'docsis_mta', 'docsis_update']

        for (x = 0; x < tables.size(); x++) {
            res = sql.execute('TRUNCATE TABLE ' + tables[x] + ';');
        }

        res = sql.execute('SET FOREIGN_KEY_CHECKS = 1;');

        // Close the SQL instance.
        sql.close()
    }

    def settingBDP(projectObject, log) {

        def dbDsa = projectObject.getPropertyValue('dbDsa')
        def dbHost = projectObject.getPropertyValue('dbHost')
        def user = projectObject.getPropertyValue('dbUser')
        def password = projectObject.getPropertyValue('dbPass')
        def ipBdpApi = projectObject.getPropertyValue('ipBdpApi')
        def portBdpApi = projectObject.getPropertyValue('portBdpApi')

        log.info('BDP base recreate: ' + dbHost + '/' + dbDsa + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbDsa /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

        def x = 0
        def tables = ['cluster_bdp']

        for (x = 0; x < tables.size(); x++) {
            res = sql.execute('TRUNCATE TABLE ' + tables[x] + ';');
        }

        res = sql.execute("INSERT INTO `cluster_bdp` (`id`, `name`, `mode`, `ip`, `port`, `user`, `password`, `version`, `enabled`, `type`) VALUES ('2', 'BDP Testing', '0', '" + ipBdpApi +"','" + portBdpApi + "', 'stechs', 'stechs', 'v0.2', '1', '0');");
        res = sql.execute('SET FOREIGN_KEY_CHECKS = 1;');

        // Close the SQL instance.
        sql.close()
    }

    def recreateRadiusDB(projectObject, log) {

        def dbRadius = projectObject.getPropertyValue('dbRadius')
        def dbHost = projectObject.getPropertyValue('dbHost')
        def user = projectObject.getPropertyValue('dbUser')
        def password = projectObject.getPropertyValue('dbPass')

        log.info('BDP base recreate: ' + dbHost + '/' + dbRadius + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbRadius /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

        def x = 0
        def tables = ['freeradius_profile', 'radcheck', 'radusergroup']

        for (x = 0; x < tables.size(); x++) {
            res = sql.execute('TRUNCATE TABLE ' + tables[x] + ';');
        }

        res = sql.execute('SET FOREIGN_KEY_CHECKS = 1;');

        res = sql.execute("INSERT INTO `freeradius_profile` (`name`) VALUES ('profile_test01');");
        res = sql.execute("INSERT INTO `freeradius_profile` (`name`) VALUES ('profile_test02');");
        res = sql.execute("INSERT INTO `freeradius_profile` (`name`) VALUES ('noauth');");

        // Close the SQL instance.
        sql.close()
    }

    def recreateAsteriskDB(projectObject, log) {

        def dbAsterisk = projectObject.getPropertyValue('dbAsterisk')
        def dbHost = projectObject.getPropertyValue('dbHost')
        def user = projectObject.getPropertyValue('dbUser')
        def password = projectObject.getPropertyValue('dbPass')

        log.info('BDP base recreate: ' + dbHost + '/' + dbAsterisk + ' ' + user + ':' + password)
        // Set up database connection properties.
        // def url = 'jdbc:mysql://10.0.0.21/fdm_nico_v3.0_testing' /* IMPORTANT: must start with jdbc:mysql:// */
        def url = 'jdbc:mysql://' + dbHost + '/' + dbAsterisk /* IMPORTANT: must start with jdbc:mysql:// */
        def driver = 'com.mysql.jdbc.Driver'

        // Register the MySQL JDBC driver – required for Groovy to send requests to the database.
        com.eviware.soapui.support.GroovyUtils.registerJdbcDriver( driver )

        // Connect to the SQL instance.
        def sql = Sql.newInstance(url, user, password, driver)

        // Use the SQL instance.
        def res = sql.execute('SET FOREIGN_KEY_CHECKS = 0;');

        def x = 0
        def tables = ['sippeers']

        for (x = 0; x < tables.size(); x++) {
            res = sql.execute('TRUNCATE TABLE ' + tables[x] + ';');
        }

        res = sql.execute('SET FOREIGN_KEY_CHECKS = 1;');

        res = sql.execute("INSERT INTO `sippeers` (`id`, `name`, `line_profile`, `defaultuser`, `host`, `type`, `context`, `secret`, `dtmfmode`, `directmedia`, `nat`, `language`, `disallow`, `allow`, `accountcode`, `callerid`, `fromdomain`) VALUES ('1', '567687877', 'GponZhoneSipLineProfile', '567687877', 'dynamic', 'friend', 'subscriber', 'b50f9660', 'info', 'no', 'no', 'es', 'all', 'alaw', '2', '567687877', 'tvfuego.com');");

        // Close the SQL instance.
        sql.close()
    }

}
