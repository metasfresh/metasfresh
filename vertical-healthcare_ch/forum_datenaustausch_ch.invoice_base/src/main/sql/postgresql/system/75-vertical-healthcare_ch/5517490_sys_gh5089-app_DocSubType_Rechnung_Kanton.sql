-- 2019-03-27T16:33:48.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,541898,TO_TIMESTAMP('2019-03-27 16:33:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Rechnung - Kanton',TO_TIMESTAMP('2019-03-27 16:33:48','YYYY-MM-DD HH24:MI:SS'),100,'KT','Healthcare_CH-KT')
;

-- 2019-03-27T16:33:48.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541898 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-03-27T16:45:12.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' (''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT''))
 OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC''))
 OR (''@DocBaseType@'' IN(''API'', ''MOP'') AND AD_Ref_List.Value IN (''QI'', ''VI''))
OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value = ''MD'')
 OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'')) /* fallback for the rest of the entries */
',Updated=TO_TIMESTAMP('2019-03-27 16:45:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

