-- Jan 19, 2017 10:30 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-01-19 10:30:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2901
;

-- Jan 19, 2017 10:55 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-01-19 10:55:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2901
;

-- Jan 19, 2017 5:17 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU_PI_Version.M_HU_PI_Version_ID IN (
select i.M_HU_PI_Version_ID from M_HU_PI_Item_Product ip
left join M_HU_PI_Item i on ip.M_HU_PI_Item_ID=i.M_HU_PI_Item_ID
where @M_Product_ID@=ip.M_Product_ID) AND M_HU_PI_Version.IsActive=''Y''',Updated=TO_TIMESTAMP('2017-01-19 17:17:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=52243
;

