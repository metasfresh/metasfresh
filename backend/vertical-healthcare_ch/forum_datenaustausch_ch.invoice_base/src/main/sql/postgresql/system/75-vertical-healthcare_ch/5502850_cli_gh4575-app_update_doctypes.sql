
-- 2018-10-08T09:21:11.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,Value,EntityType) VALUES (148,0,'Y',TO_TIMESTAMP('2018-10-08 09:21:11','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-08 09:21:11','YYYY-MM-DD HH24:MI:SS'),100,541739,'Healthcare_CH-GM',0,'Rechnung - Gemeinde','GM','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-08T09:21:11.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541739 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-08T09:22:23.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,Value,EntityType) VALUES (148,0,'Y',TO_TIMESTAMP('2018-10-08 09:22:22','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-08 09:22:22','YYYY-MM-DD HH24:MI:SS'),100,541740,'Healthcare_CH-EA',0,'Rechnung - Klientenanteil','EA','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-08T09:22:23.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541740 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-08T09:23:13.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,Value,EntityType) VALUES (148,0,'Y',TO_TIMESTAMP('2018-10-08 09:23:13','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-08 09:23:13','YYYY-MM-DD HH24:MI:SS'),100,541741,'Healthcare_CH-KV',0,'Rechnung - Krankenversicherung','KV','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-08T09:23:13.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541741 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

--Fix theses two ValueNames so we can improve the validationRule a bit while we extend it.
UPDATE AD_Ref_List SET ValueName='AP' WHERE AD_Ref_List_ID=540740;
UPDATE AD_Ref_List SET ValueName='AQ' WHERE AD_Ref_List_ID=540739;


-- 2018-10-08T10:55:54.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' (''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV''))
 OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC''))
 OR (''@DocBaseType@'' IN(''API'', ''MOP'') AND AD_Ref_List.Value IN (''QI'', ''VI''))
OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value = ''MD'')
 OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'')) /* fallback for the rest of the entries */
',Updated=TO_TIMESTAMP('2018-10-08 10:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- 2018-10-08T11:10:08.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='GM',Updated=TO_TIMESTAMP('2018-10-08 11:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540963
;

-- 2018-10-08T11:10:26.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Rechnung - Klientenanteil', DocSubType='EA',Updated=TO_TIMESTAMP('2018-10-08 11:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540962
;

-- 2018-10-08T11:10:45.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Rechnung - Krankenversicherung', DocSubType='KV',Updated=TO_TIMESTAMP('2018-10-08 11:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540964
;

-- 2018-10-08T11:10:52.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Rechnung - Gemeinde',Updated=TO_TIMESTAMP('2018-10-08 11:10:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540963
;

