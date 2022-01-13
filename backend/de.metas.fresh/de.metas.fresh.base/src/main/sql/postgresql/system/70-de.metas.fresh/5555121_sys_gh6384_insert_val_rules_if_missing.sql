---------------------------
--- Fresh_SalesPriceList_Version_of_BPartner

update AD_Val_Rule set description='DEPRECATED; this is a duplicate of Fresh_SalesPriceList_Version_of_BPartner (ad_val_rule_id=540488)', name='OLD_Fresh_SalesPriceList_Version_of_BPartner', updatedby=99, updated='2021-07-06 08:38'
where ad_val_rule.ad_val_rule_id=540490;

-- 2020-03-23T14:05:48.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ 
AND )',Updated=TO_TIMESTAMP('2020-03-23 16:05:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540243
;

-- 2020-03-23T14:35:23.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ 
AND plv.IsSOTrx = ''Y'' )', Updated=TO_TIMESTAMP('2020-03-23 16:35:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540243
;

-- 2020-03-23T15:11:00.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ )', Name='Fresh_PriceList_Version_of_BPartner',Updated=TO_TIMESTAMP('2020-03-23 17:11:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540243
;

-- 2020-03-23T15:11:21.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='Fresh_AnyPriceList_Version_of_BPartner',Updated=TO_TIMESTAMP('2020-03-23 17:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540243
;

-- 2020-03-23T15:12:50.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) SELECT 0,0,540488,'EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ 
AND plv.IsSOTrx = ''Y'' )',TO_TIMESTAMP('2020-03-23 17:12:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Fresh_SalesPriceList_Version_of_BPartner','S',TO_TIMESTAMP('2020-03-23 17:12:50','YYYY-MM-DD HH24:MI:SS'),100
where not exists (select 1 from ad_val_rule where ad_val_rule_id=540488);
;

-------------------------
-- Fresh_PurchasePriceList_Version_of_BPartner

update AD_Val_Rule set description='DEPRECATED; this is a duplicate of Fresh_PurchasePriceList_Version_of_BPartner (ad_val_rule_id=540488)', name='OLD_Fresh_PurchasePriceList_Version_of_BPartner', updatedby=99, updated='2021-07-06 08:38'
where ad_val_rule.ad_val_rule_id=540491;

-- 2020-03-23T14:35:45.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy)
 SELECT 0,0,540487,'EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ 
AND plv.IsSOTrx = ''N'' )',TO_TIMESTAMP('2020-03-23 16:35:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Fresh_PurchasePriceList_Version_of_BPartner','S',TO_TIMESTAMP('2020-03-23 16:35:44','YYYY-MM-DD HH24:MI:SS'),100
where not exists (select 1 from ad_val_rule where AD_Val_Rule_ID=540487)
;
