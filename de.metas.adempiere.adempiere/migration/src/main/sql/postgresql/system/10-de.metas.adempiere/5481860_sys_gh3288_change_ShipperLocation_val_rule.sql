-- 2018-01-08T14:54:37.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists (
	select 1
	from M_Warehouse wh
	where wh.IsActive=''Y''
	and wh.AD_Org_ID=@AD_Org_ID@
	and wh.C_BPartner_Location_ID=C_BPartner_Location.C_BPartner_Location_ID
)
',Updated=TO_TIMESTAMP('2018-01-08 14:54:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540007
;

-- 2018-01-08T14:57:34.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists (
	select *
	from M_Warehouse wh
	where wh.IsActive=''Y''
	and wh.IsPickingWarehouse=''Y''
	and wh.AD_Org_ID=@AD_Org_ID@
	and wh.C_BPartner_Location_ID=C_BPartner_Location.C_BPartner_Location_ID
)
',Updated=TO_TIMESTAMP('2018-01-08 14:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540007
;

