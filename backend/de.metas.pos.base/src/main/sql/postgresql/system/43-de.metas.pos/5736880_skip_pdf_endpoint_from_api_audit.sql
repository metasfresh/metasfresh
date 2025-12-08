INSERT INTO api_audit_config (ad_client_id, ad_org_id, 
                              api_audit_config_id, 
                              created, createdby, isactive, method, pathprefix, seqno, updated, updatedby, keeprequestdays, keeprequestbodydays, keepresponsedays, keepresponsebodydays, notifyuserincharge, ad_usergroup_incharge_id, iswrapapiresponse, issynchronousauditloggingenabled, isforceprocessedasync, keeperroredrequestdays, isbypassaudit)
VALUES  (1000000, 0,
         540008, 
         '2024-10-11 23:57:04.000000 +00:00', 540116, 'Y', 'GET', '**/pos/orders/receipt/**', 20, '2024-10-11 23:58:04.000000 +00:00', 540116, 0, 0, 0, 0, NULL, NULL, 'Y', 'Y', 'N', 7, 'Y')
;