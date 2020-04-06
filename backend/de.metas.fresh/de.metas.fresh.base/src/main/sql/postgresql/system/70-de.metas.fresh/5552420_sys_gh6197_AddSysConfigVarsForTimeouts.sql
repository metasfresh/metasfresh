INSERT INTO ad_sysconfig (ad_sysconfig_id, ad_client_id, ad_org_id, created, updated, createdby, updatedby, isactive,
                          name, value, description, entitytype, configurationlevel)
VALUES (nextval('ad_sysconfig_seq'), 0, 0, now(), now(), 100, 100, 'Y', 'reports.remoteServletInvoker.connectTimeout',
        '15000', 'Connection timeout in milliseconds when invoking the reports servlet from another metasfresh component', 'D', 'S');


INSERT INTO ad_sysconfig (ad_sysconfig_id, ad_client_id, ad_org_id, created, updated, createdby, updatedby, isactive,
                          name, value, description, entitytype, configurationlevel)
VALUES (nextval('ad_sysconfig_seq'), 0, 0, now(), now(), 100, 100, 'Y', 'reports.remoteServletInvoker.readTimeout',
        '20000', 'Read timeout in milliseconds when the reports servlet retrieving data from database', 'D', 'S');