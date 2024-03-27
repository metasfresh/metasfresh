-- 2021-11-05T10:15:11.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-11-05 11:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540134
;

-- 2021-11-05T10:16:17.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='',Updated=TO_TIMESTAMP('2021-11-05 11:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540594
;


-- 2021-11-05T13:49:08.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_Material_Tracking.M_Material_Tracking_ID in  (select mtr.M_Material_Tracking_ID from M_Material_Tracking_Ref mtr where mtr.Record_ID = @C_Invoice_ID/-1@ and mtr.AD_Table_ID =(select ad_Table_ID from AD_Table where tablename = ''C_Invoice'') and mtr.isActive = ''Y''  )',Updated=TO_TIMESTAMP('2021-11-05 14:49:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540595
;

