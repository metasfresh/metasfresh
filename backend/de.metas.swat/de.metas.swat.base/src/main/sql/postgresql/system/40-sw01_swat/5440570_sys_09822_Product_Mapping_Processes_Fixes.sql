-- 23.02.2016 10:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2016-02-23 10:40:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552999
;

-- 23.02.2016 11:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Process_ID=540658 AND AD_Table_ID=540704
;

-- 23.02.2016 11:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540658,540704,TO_TIMESTAMP('2016-02-23 11:03:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y',TO_TIMESTAMP('2016-02-23 11:03:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.02.2016 11:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-02-23 11:06:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

-- 23.02.2016 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540642,TO_TIMESTAMP('2016-02-23 11:14:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','M_Product_Name',TO_TIMESTAMP('2016-02-23 11:14:55','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 23.02.2016 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540642 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 23.02.2016 11:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,1410,1402,0,540642,208,TO_TIMESTAMP('2016-02-23 11:15:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N',TO_TIMESTAMP('2016-02-23 11:15:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.02.2016 11:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540642,Updated=TO_TIMESTAMP('2016-02-23 11:15:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540898
;

