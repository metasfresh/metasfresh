-- 2019-03-27T13:09:17.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540969,TO_TIMESTAMP('2019-03-27 13:09:16','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.vertical.pharma','Y','N','C_BPartner (Pharma)',TO_TIMESTAMP('2019-03-27 13:09:16','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-03-27T13:09:17.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=540969 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-03-27T13:10:41.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2902,2893,0,540969,291,540409,TO_TIMESTAMP('2019-03-27 13:10:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Y',TO_TIMESTAMP('2019-03-27 13:10:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T13:10:50.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540969,Updated=TO_TIMESTAMP('2019-03-27 13:10:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564599
;

