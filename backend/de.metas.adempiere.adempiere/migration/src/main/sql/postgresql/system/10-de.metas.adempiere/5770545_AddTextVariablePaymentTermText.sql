--- create the text variable that will allow us to diplay payment term details in invoice
INSERT INTO ad_boilerplate_var (ad_boilerplate_var_id, ad_client_id, ad_org_id, ad_rule_id, code, created, createdby, isactive, name, type, updated, updatedby, value)
VALUES (540003, 1000000, 1000000, null, 'select paytermtext from de_metas_endcustomer_fresh_reports.docs_invoice_details_footer_paymentterm(@C_Invoice_ID@)', '2025-09-22 15:09:58.947000 +02:00', 100, 'Y', 'paymenttermtext', 'S', '2025-09-22 15:10:52.361000 +02:00', 100, 'paymenttermtext');

-- deactivate the feature by default
update ad_boilerplate_var set isactive = 'N' where ad_boilerplate_var_id = 540003;