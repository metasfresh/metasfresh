--- create the text variable that will allow us to diplay payment term details in invoice
INSERT INTO ad_boilerplate_var (ad_boilerplate_var_id, ad_client_id, ad_org_id, ad_rule_id, code, created, createdby, isactive, name, type, updated, updatedby, value)
VALUES (540003, 1000000, 1000000, null, 'select paytermtext from de_metas_endcustomer_fresh_reports.docs_invoice_details_footer_paymentterm(@C_Invoice_ID@)', '2025-09-22 15:09:58.947000 +02:00', 100, 'Y', 'paymenttermtext', 'S', '2025-09-22 15:10:52.361000 +02:00', 100, 'paymenttermtext');



INSERT INTO ad_boilerplate_var_eval (ad_boilerplate_var_id, ad_client_id, ad_org_id, c_doctype_id, created, createdby, evaltime, isactive, updated, updatedby, ad_boilerplate_var_eval_id)
VALUES (540003, 1000000, 1000000, 1000002, '2025-09-23 09:27:16.596000 +00:00', 100, 'DAPR', 'Y', '2025-09-23 09:27:16.596000 +00:00', 100, nextval('ad_boilerplate_var_eval_seq'));



-- deactivate the feature by default
update ad_boilerplate_var set isactive = 'N' where ad_boilerplate_var_id = 540003;