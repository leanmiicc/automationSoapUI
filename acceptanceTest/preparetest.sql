use `jenkings_dsa_v3.0`;
SET FOREIGN_KEY_CHECKS = 0;

truncate table subscriptions; 
truncate table subscribers; 
truncate table hsi_instance;
truncate table hsi_template;
truncate table tls_instance;
truncate table tls_template;
truncate table voip_instance;
truncate table voip_template;
truncate table voip_profile;
truncate table video_instance;
truncate table video_template;
truncate table dummy_instance;
truncate table dummy_template;
truncate table subscription_service;
truncate table service_template;
truncate table activation_code;
truncate table issues;
truncate table crm_code_defs;
truncate table crm_code_values;
truncate table srv_traffic_profile;
truncate table logs;

SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (1,'hsi', 'Hsi');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (2,'liv', 'Hsi');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (3,'optimo', 'Hsi');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (4,'unico', 'Hsi');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (5,'gigaplus', 'Hsi');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (6,'voippack', 'Hsi');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (7,'maximo', 'Hsi');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (8,'scm', 'Dummy');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (9,'tls', 'tls');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (10,'tls qinq', 'tls');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (11,'voip', 'voip');
INSERT INTO `service_template` (`template_id`,`template_name`, `service_type`) VALUES (12,'video', 'video');

INSERT INTO `hsi_template` (
    `name`,
    `trafficprofile`,
    `trafficprofile_editable`,
    `clientclass`,
    `clientclass_editable`,
    `maxcpe`,
    `maxcpe_editable`,
    `reservediplist_allow`,
    `blps_allocation_type`,
    `blps_allocation_type_editable`,
    `blps`,
    `blps_editable`,
    `gd_status`,
    `gd_status_editable`,
    `gd_ipv4_nat`,
    `gd_ipv4_nat_editable`,
    `gd_ipv4_ipmode`,
    `gd_ipv4_ipmode_editable`,
    `gd_ipv4_dns`,
    `gd_ipv4_dns_editable`,
    `gd_ipv6_status`,
    `gd_ipv6_status_editable`,
    `wifi_status`,
    `wifi_status_editable`,
    `fdm_srv_hsi`,
    `fdm_srv_hsi_editable`)
VALUES (
    'hsi', -- name
    'test',	-- trafficprofile
    1, -- trafficprofile_editable
    'residencial', -- clientclass
    1, -- clientclass_editable
    1, -- maxcpe
    1, -- maxcpe_editable
    0, -- reservedIpList_allow
    'static', -- bindLanPorts_allocation_type
    1, -- bindLanPorts_allocation_type_editable
    '1,2,3,4', -- bindLanPorts
    1, -- bindLanPorts_editable
    'disabled', -- gd_status
    1, -- gd_status_editable  
    'enabled', -- gd_ipv4_nat
    1, -- gd_ipv4_nat_editable
    'DHCP', -- gd_ipv4_ipMode
    1, -- gd_ipv4_ipMode_editable
    '8.8.8.8', -- gd_ipv4_dns
    1, -- gd_ipv4_dns_editable
    'disabled', -- gd_ipv6_status
    1, -- gd_ipv6_status_editable
    'disabled', -- wifi_status
    1, -- wifi_status_editable
    'hsi', -- fdm_srv_hsi
    0 -- fdm_srv_hsi_editable
);

INSERT INTO dummy_template (
    `id`,
    `type`,
    `type_editable`,
    `eth_port_4`,
    `eth_port_4_editable`,
    `eth_port_1`,
    `eth_port_1_editable`,
    `eth_port_2`,
    `eth_port_2_editable`,
    `eth_port_3`,
    `eth_port_3_editable`,
    `wan_4`,
    `wan_4_editable`,
    `wan_1`,
    `wan_1_editable`,
    `wan_2`,
    `wan_2_editable`,
    `wan_3`,
    `wan_3_editable`,
    `wifi_24_ssid_4`,
    `wifi_24_ssid_4_editable`,
    `wifi_24_ssid_1`,
    `wifi_24_ssid_1_editable`,
    `wifi_24_ssid_2`,
    `wifi_24_ssid_2_editable`,
    `wifi_24_ssid_3`,
    `wifi_24_ssid_3_editable`,
    `wifi_5_ssid_4`,
    `wifi_5_ssid_4_editable`,
    `wifi_5_ssid_1`,
    `wifi_5_ssid_1_editable`,
    `wifi_5_ssid_2`,
    `wifi_5_ssid_2_editable`,
    `wifi_5_ssid_3`,
    `wifi_5_ssid_3_editable`,
    `pots_2`,
    `pots_2_editable`,
    `pots_1`,
    `pots_1_editable`,
    `catv_2`,
    `catv_2_editable`,
    `catv_1`,
    `catv_1_editable`,
    `fdm_srv`,
    `fdm_srv_editable`
)
VALUES (8, null, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,'dummy',0);

INSERT INTO `crm_code_defs` (`id`, `name`, `label`, `shortcut`, `object_to_apply`, `searcheable`, `unique`)
VALUES ('1', 'idSuscripcion', 'Subscription', 'subid', 'Service', '1', '1');

INSERT INTO `srv_traffic_profile` (`name`, `description`, `vendors_enabled`) VALUES ('test', 'test', 'ZTE');
INSERT INTO `srv_traffic_profile` (`name`, `description`, `vendors_enabled`) VALUES ('hsi-test', 'hsi-test', 'ZTE');
UPDATE `dummy_template` SET
    `eth_port_1_editable`='1',
    `eth_port_2_editable`='1',
    `eth_port_3_editable`='1',
    `eth_port_4_editable`='1',
    `wan_1_editable`='1',
    `wan_2_editable`='1',
    `wan_3_editable`='1',
    `wan_4_editable`='1',
    `wifi_24_ssid_1_editable`='1',
    `wifi_24_ssid_2_editable`='1',
    `wifi_24_ssid_3_editable`='1',
    `wifi_24_ssid_4_editable`='1',
    `wifi_5_ssid_1_editable`='1',
    `wifi_5_ssid_2_editable`='1',
    `wifi_5_ssid_3_editable`='1',
    `wifi_5_ssid_4_editable`='1',
    `pots_1_editable`='1',
    `pots_2_editable`='1',
    `catv_1_editable`='1',
    `catv_2_editable`='1'
WHERE `id`='8';

INSERT INTO `hsi_template` (
    `name`,
    `trafficprofile`,
    `trafficprofile_editable`,
    `clientclass`,
    `clientclass_editable`,
    `maxcpe`,
    `maxcpe_editable`,
    `reservediplist_allow`,
    `blps_allocation_type`,
    `blps_allocation_type_editable`,
    `blps`,
    `blps_editable`,
    `gd_status`,
    `gd_status_editable`,
    `gd_ipv4_nat`,
    `gd_ipv4_nat_editable`,
    `gd_ipv4_ipmode`,
    `gd_ipv4_ipmode_editable`,
    `gd_ipv4_dns`,
    `gd_ipv4_dns_editable`,
    `gd_ipv6_status`,
    `gd_ipv6_status_editable`,
    `wifi_status`,
    `wifi_status_editable`,
    `fdm_srv_hsi`,
    `fdm_srv_hsi_editable`
)
VALUES ( 
    'liv', -- name
    'test', -- trafficprofile
    1, -- trafficprofile_editable
    'residencial', -- clientclass
    1, -- clientclass_editable
    1, -- maxcpe
    1, -- maxcpe_editable
    0, -- reservedIpList_allow
    'static', -- bindLanPorts_allocation_type
    1, -- bindLanPorts_allocation_type_editable
    '1,2,3,4', -- bindLanPorts
    1, -- bindLanPorts_editable
    'disabled', -- gd_status
    1, -- gd_status_editable
    'enabled', -- gd_ipv4_nat
    1, -- gd_ipv4_nat_editable
    'DHCP', -- gd_ipv4_ipMode
    1, -- gd_ipv4_ipMode_editable
    '8.8.8.8', -- gd_ipv4_dns
    1, -- gd_ipv4_dns_editable
    'disabled', -- gd_ipv6_status
    1, -- gd_ipv6_status_editable
    'disabled', -- wifi_status
    1, -- wifi_status_editable
    'liv', -- fdm_srv_hsi
    0 -- fdm_srv_hsi_editable
);



INSERT INTO `hsi_template` (
    `name`,
    `trafficprofile`, 
    `trafficprofile_editable`, 
    `clientclass`, 
    `clientclass_editable`, 
    `maxcpe`, 
    `maxcpe_editable`, 
    `reservediplist_allow`, 
    `blps_allocation_type`, 
    `blps_allocation_type_editable`, 
    `blps`, 
    `blps_editable`, 
    `gd_status`, 
    `gd_status_editable`, 
    `gd_ipv4_nat`, 
    `gd_ipv4_nat_editable`, 
    `gd_ipv4_ipmode`, 
    `gd_ipv4_ipmode_editable`, 
    `gd_ipv4_dns`, 
    `gd_ipv4_dns_editable`, 
    `gd_ipv6_status`, 
    `gd_ipv6_status_editable`, 
    `wifi_status`, 
    `wifi_status_editable`, 
    `fdm_srv_hsi`, 
    `fdm_srv_hsi_editable`
) 
VALUES ( 
    'optimo', -- name
    'test', -- trafficprofile 
    1, -- trafficprofile_editable 
    'residencial', -- clientclass 
    1, -- clientclass_editable 
    1, -- maxcpe 
    1, -- maxcpe_editable 
    0, -- reservedIpList_allow 
    'static', -- bindLanPorts_allocation_type 
    1, -- bindLanPorts_allocation_type_editable 
    '1,2,3,4', -- bindLanPorts 
    1, -- bindLanPorts_editable 
    'disabled', -- gd_status 
    1, -- gd_status_editable   
    'enabled', -- gd_ipv4_nat 
    1, -- gd_ipv4_nat_editable 
    'DHCP', -- gd_ipv4_ipMode 
    1, -- gd_ipv4_ipMode_editable 
    '8.8.8.8', -- gd_ipv4_dns 
    1, -- gd_ipv4_dns_editable 
    'disabled', -- gd_ipv6_status 
    1, -- gd_ipv6_status_editable 
    'disabled', -- wifi_status 
    1, -- wifi_status_editable 
    'optimo', -- fdm_srv_hsi 
    0 -- fdm_srv_hsi_editable
);


INSERT INTO `hsi_template` (
    `name`,
    `trafficprofile`, 
    `trafficprofile_editable`, 
    `clientclass`, 
    `clientclass_editable`, 
    `maxcpe`, 
    `maxcpe_editable`, 
    `reservediplist_allow`, 
    `blps_allocation_type`, 
    `blps_allocation_type_editable`, 
    `blps`, 
    `blps_editable`, 
    `gd_status`, 
    `gd_status_editable`, 
    `gd_ipv4_nat`, 
    `gd_ipv4_nat_editable`, 
    `gd_ipv4_ipmode`, 
    `gd_ipv4_ipmode_editable`, 
    `gd_ipv4_dns`, 
    `gd_ipv4_dns_editable`, 
    `gd_ipv6_status`, 
    `gd_ipv6_status_editable`, 
    `wifi_status`, 
    `wifi_status_editable`, 
    `fdm_srv_hsi`, 
    `fdm_srv_hsi_editable`
) 
VALUES ( 
    'uncio', -- name
    'test', -- trafficprofile
    1, -- trafficprofile_editable 
    'residencial', -- clientclass 
    1, -- clientclass_editable 
    1, -- maxcpe 
    1, -- maxcpe_editable 
    0, -- reservedIpList_allow 
    'static', -- bindLanPorts_allocation_type 
    1, -- bindLanPorts_allocation_type_editable 
    '1,2,3,4', -- bindLanPorts 
    1, -- bindLanPorts_editable 
    'disabled', -- gd_status 
    1, -- gd_status_editable   
    'enabled', -- gd_ipv4_nat 
    1, -- gd_ipv4_nat_editable 
    'DHCP', -- gd_ipv4_ipMode 
    1, -- gd_ipv4_ipMode_editable 
    '8.8.8.8', -- gd_ipv4_dns 
    1, -- gd_ipv4_dns_editable 
    'disabled', -- gd_ipv6_status 
    1, -- gd_ipv6_status_editable 
    'disabled', -- wifi_status 
    1, -- wifi_status_editable 
    'unico', -- fdm_srv_hsi 
    0 -- fdm_srv_hsi_editable
);

INSERT INTO `hsi_template` (
    `name`,
    `trafficprofile`, 
    `trafficprofile_editable`, 
    `clientclass`, 
    `clientclass_editable`, 
    `maxcpe`, 
    `maxcpe_editable`, 
    `reservediplist_allow`, 
    `blps_allocation_type`, 
    `blps_allocation_type_editable`, 
    `blps`, 
    `blps_editable`, 
    `gd_status`, 
    `gd_status_editable`, 
    `gd_ipv4_nat`, 
    `gd_ipv4_nat_editable`, 
    `gd_ipv4_ipmode`, 
    `gd_ipv4_ipmode_editable`, 
    `gd_ipv4_dns`, 
    `gd_ipv4_dns_editable`, 
    `gd_ipv6_status`, 
    `gd_ipv6_status_editable`, 
    `wifi_status`, 
    `wifi_status_editable`, 
    `fdm_srv_hsi`, 
    `fdm_srv_hsi_editable`
) 
VALUES ( 
    'gigaplus', -- name
    'test', -- trafficprofile 
    1, -- trafficprofile_editable 
    'residencial', -- clientclass 
    1, -- clientclass_editable 
    1, -- maxcpe 
    1, -- maxcpe_editable 
    0, -- reservedIpList_allow 
    'static', -- bindLanPorts_allocation_type 
    1, -- bindLanPorts_allocation_type_editable 
    '1,2,3,4', -- bindLanPorts 
    1, -- bindLanPorts_editable 
    'disabled', -- gd_status 
    1, -- gd_status_editable   
    'enabled', -- gd_ipv4_nat 
    1, -- gd_ipv4_nat_editable 
    'DHCP', -- gd_ipv4_ipMode 
    1, -- gd_ipv4_ipMode_editable 
    '8.8.8.8', -- gd_ipv4_dns 
    1, -- gd_ipv4_dns_editable 
    'disabled', -- gd_ipv6_status 
    1, -- gd_ipv6_status_editable 
    'disabled', -- wifi_status 
    1, -- wifi_status_editable 
    'gigaplus', -- fdm_srv_hsi 
    0 -- fdm_srv_hsi_editable
);


INSERT INTO `hsi_template` (
    `name`,
    `trafficprofile`, 
    `trafficprofile_editable`, 
    `clientclass`, 
    `clientclass_editable`, 
    `maxcpe`, 
    `maxcpe_editable`, 
    `reservediplist_allow`, 
    `blps_allocation_type`, 
    `blps_allocation_type_editable`, 
    `blps`, 
    `blps_editable`, 
    `gd_status`, 
    `gd_status_editable`, 
    `gd_ipv4_nat`, 
    `gd_ipv4_nat_editable`, 
    `gd_ipv4_ipmode`, 
    `gd_ipv4_ipmode_editable`, 
    `gd_ipv4_dns`, 
    `gd_ipv4_dns_editable`, 
    `gd_ipv6_status`, 
    `gd_ipv6_status_editable`, 
    `wifi_status`, 
    `wifi_status_editable`, 
    `fdm_srv_hsi`, 
    `fdm_srv_hsi_editable`,
    `vlan_editable`,
    `vlan_fdm_override`
) 
VALUES ( 
    'voippack',
    'test',
    1,
    'residencial',
    0,
    1,
    0,
    0,
    'static',
    0,
    '',
    1,
    'disabled',
    0,
    'disabled',
    0,
    'DHCP',
    0,
    '8.8.8.8',
    0,
    'disabled',
    0,
    'disabled',
    0,
    'voippack',
    0,
    1,
    1
);


INSERT INTO `hsi_template` (
    `name`,
    `trafficprofile`, 
    `trafficprofile_editable`, 
    `clientclass`, 
    `clientclass_editable`, 
    `maxcpe`, 
    `maxcpe_editable`, 
    `reservediplist_allow`, 
    `blps_allocation_type`, 
    `blps_allocation_type_editable`, 
    `blps`, 
    `blps_editable`, 
    `gd_status`, 
    `gd_status_editable`, 
    `gd_ipv4_nat`, 
    `gd_ipv4_nat_editable`, 
    `gd_ipv4_ipmode`, 
    `gd_ipv4_ipmode_editable`, 
    `gd_ipv4_dns`, 
    `gd_ipv4_dns_editable`, 
    `gd_ipv6_status`, 
    `gd_ipv6_status_editable`, 
    `wifi_status`, 
    `wifi_status_editable`, 
    `fdm_srv_hsi`, 
    `fdm_srv_hsi_editable`,
    `vlan_editable`,
    `vlan_fdm_override`
) 
VALUES ( 
    'maximo',
    'test',
    1,
    'residencial',
    0,
    1,
    0,
    0,
    'static',
    0,
    '',
    1,
    'disabled',
    0,
    'disabled',
    0,
    'DHCP',
    0,
    '8.8.8.8',
    0,
    'disabled',
    0,
    'disabled',
    0,
    'maximo',
    0,
    1,
    1
);


INSERT INTO `tls_template` (
    `id`,
    `name`,
    `trafficprofile`, 
    `trafficprofile_editable`, 
    `clientclass`, 
    `clientclass_editable`, 
    `maxcpe`, 
    `maxcpe_editable`, 
    `reservediplist_allow`, 
    `blps_allocation_type`, 
    `blps_allocation_type_editable`, 
    `blps`, 
    `blps_editable`, 
    `gd_status`, 
    `gd_status_editable`, 
    `gd_ipv4_nat`, 
    `gd_ipv4_nat_editable`, 
    `gd_ipv4_ipmode`, 
    `gd_ipv4_ipmode_editable`, 
    `gd_ipv4_dns`, 
    `gd_ipv4_dns_editable`, 
    `gd_ipv6_status`, 
    `gd_ipv6_status_editable`, 
    `wifi_status`, 
    `wifi_status_editable`, 
    `fdm_srv`, 
    `fdm_srv_editable`, 
    `vlan`, 
    `vlan_editable`
) 
VALUES ( 
    9,
    'tls',
    'test',
    1,
    'residencial',
    0,
    1,
    0,
    0,
    'static',
    0,
    '',
    1,
    'disabled',
    0,
    'enabled',
    0,
    'DHCP',
    0,
    '8.8.8.8',
    0,
    'disabled',
    0,
    'disabled',
    0,
    'tls',
    0,
    0,
    1
);

INSERT INTO `tls_template` (
    `id`,
    `name`,
    `trafficprofile`, 
    `trafficprofile_editable`, 
    `clientclass`, 
    `clientclass_editable`, 
    `maxcpe`, 
    `maxcpe_editable`, 
    `reservediplist_allow`, 
    `blps_allocation_type`, 
    `blps_allocation_type_editable`, 
    `blps`, 
    `blps_editable`, 
    `gd_status`, 
    `gd_status_editable`, 
    `gd_ipv4_nat`, 
    `gd_ipv4_nat_editable`, 
    `gd_ipv4_ipmode`, 
    `gd_ipv4_ipmode_editable`, 
    `gd_ipv4_dns`, 
    `gd_ipv4_dns_editable`, 
    `gd_ipv6_status`, 
    `gd_ipv6_status_editable`, 
    `wifi_status`, 
    `wifi_status_editable`, 
    `fdm_srv`, 
    `fdm_srv_editable`, 
    `vlan`, 
    `vlan_editable`
) 
VALUES (
    10,
    'tls qinq',
    'test',
    1,
    'residencial',
    0,
    1,
    0,
    0,
    'static',
    0,
    '',
    1,
    'disabled',
    0,
    'enabled',
    0,
    'DHCP',
    0,
    '8.8.8.8',
    0,
    'disabled',
    0,
    'disabled',
    0,
    'tlsqinq',
    0,
    0,
    1
);

INSERT INTO `voip_profile` (
    `name`,
    `proxy_server`,
    `proxy_port`,
    `digit_map`
) VALUES (
    'test',
    'abc',
    8080,
    'abc1234'
);

INSERT INTO `voip_template` (
    `id`,
    `name`,
    `trafficProfile`,
    `trafficProfile_editable`,
    `voipProfile`,
    `voipProfile_editable`,
    `amountLinesMax`,
    `amountLinesMax_editable`,
    `username_editable`,
    `password_editable`,
    `fdm_srv`,
    `fdm_srv_editable`
) VALUES (
    11, -- id
    'voip', -- name
    'test', -- trafficProfile
    1, -- trafficProfile_editable
    1, -- voipProfile
    1, -- voipProfile_editable
    1, -- amountLinesMax
    1, -- amountLinesMax_editable
    1, -- username_editable
    1, -- password_editable
    'voip', -- fdm_srv
    1 -- fdm_srv_editable
);

INSERT INTO `video_template` (
    `id`,
    `name`,
    `trafficProfile`,
    `fdm_srv`,
    `fdm_srv_editable`
) VALUES (
    12, -- id
    'video', -- name
    'test', -- trafficProfile
    'video', -- fdm_srv
    1 -- fdm_srv_editable
);