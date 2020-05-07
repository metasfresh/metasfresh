-- NOTE: we lower cased the SQL keywords (like SELECT, FROM, WHERE),
-- because else the Preisliste (RV_Fresh_PriceList) was failing on webui,
-- because the addAccessSql was failing to parse it.


-- Nov 29, 2016 11:20 AM
-- Fresh_PriceList_Version_of_BPartner
UPDATE AD_Val_Rule SET Code='EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ )',Updated=TO_TIMESTAMP('2016-11-29 11:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540243
;

