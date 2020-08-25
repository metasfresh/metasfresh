-- 2020-07-22T09:24:50.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='(     -- product as component     EXISTS(SELECT 1 from PP_Product_BOMLine pbl where pbl.PP_Product_BOM_ID = PP_Product_BOM.PP_Product_BOM_ID AND pbl.M_Product_ID = @M_Product_ID / -1@)     OR     -- product as finished good     (PP_Product_BOM.M_Product_ID = @M_Product_ID / -1@) )',Updated=TO_TIMESTAMP('2020-07-22 12:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541135
;

-- 2020-07-22T09:33:43.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2020-07-22 12:33:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53018
;

-- 2020-07-22T09:43:02.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='D',Updated=TO_TIMESTAMP('2020-07-22 12:43:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540272
;

-- 2020-07-22T10:01:57.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='(     EXISTS(SELECT 1 from PP_Product_BOMLine pbl where pbl.PP_Product_BOM_ID = PP_Product_BOM.PP_Product_BOM_ID AND pbl.M_Product_ID = @M_Product_ID / -1@)     OR     (PP_Product_BOM.M_Product_ID = @M_Product_ID / -1@) )',Updated=TO_TIMESTAMP('2020-07-22 13:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541135
;

