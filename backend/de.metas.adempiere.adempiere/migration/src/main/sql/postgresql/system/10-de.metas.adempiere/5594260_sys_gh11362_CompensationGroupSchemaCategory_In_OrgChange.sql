-- 2021-06-23T14:11:51.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='   exists (select 1 from M_Product p where p.AD_Org_ID = @AD_Org_Target_ID / -1@
             and C_CompensationGroup_Schema_Category_ID = p.C_CompensationGroup_Schema_Category_ID)', Name='C_CompensationGroup_Schema_Category from target org',Updated=TO_TIMESTAMP('2021-06-23 17:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540535
;

-- 2021-06-23T14:11:57.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists (select 1 from M_Product p where p.AD_Org_ID = @AD_Org_Target_ID / -1@
             and C_CompensationGroup_Schema_Category_ID = p.C_CompensationGroup_Schema_Category_ID)',Updated=TO_TIMESTAMP('2021-06-23 17:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540535
;

-- 2021-06-23T14:12:15.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=579366, AD_Reference_Value_ID=NULL, ColumnName='C_CompensationGroup_Schema_Category_ID', Name='Kompensationsgruppe Schema Kategorie',Updated=TO_TIMESTAMP('2021-06-23 17:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541947
;

-- 2021-06-23T14:12:54.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=579366, ColumnName='C_CompensationGroup_Schema_Category_ID', Name='Kompensationsgruppe Schema Kategorie',Updated=TO_TIMESTAMP('2021-06-23 17:12:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541972
;

-- 2021-06-23T14:12:59.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2021-06-23 17:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541972
;

