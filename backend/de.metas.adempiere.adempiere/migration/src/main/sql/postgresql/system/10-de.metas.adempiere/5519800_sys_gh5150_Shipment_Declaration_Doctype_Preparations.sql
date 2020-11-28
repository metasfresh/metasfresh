-- 2019-04-19T13:25:51.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541953,183,TO_TIMESTAMP('2019-04-19 13:25:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abgabemeldung',TO_TIMESTAMP('2019-04-19 13:25:51','YYYY-MM-DD HH24:MI:SS'),100,'SD','Shipment Declaration')
;

-- 2019-04-19T13:25:51.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541953 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-04-19T13:30:34.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541954,148,TO_TIMESTAMP('2019-04-19 13:30:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Narcotic',TO_TIMESTAMP('2019-04-19 13:30:34','YYYY-MM-DD HH24:MI:SS'),100,'NAR','NAR')
;

-- 2019-04-19T13:30:34.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541954 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-04-19T13:59:05.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' (''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT''))
 OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC''))
 OR (''@DocBaseType@'' IN(''API'', ''MOP'') AND AD_Ref_List.Value IN (''QI'', ''VI''))
OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value = ''MD'')
OR (''@DocBaseType@''=''SD'' AND AD_Ref_List.Value = ''NAR'')
 OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'')) /* fallback for the rest of the entries */
',Updated=TO_TIMESTAMP('2019-04-19 13:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;


-- 2019-04-19T15:37:34.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='SDD',Updated=TO_TIMESTAMP('2019-04-19 15:37:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541953
;

-- 2019-04-19T15:37:49.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocBaseType='SDD',Updated=TO_TIMESTAMP('2019-04-19 15:37:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540969
;



-- 2019-04-19T15:48:44.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' (''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT''))
 OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC''))
 OR (''@DocBaseType@'' IN(''API'', ''MOP'') AND AD_Ref_List.Value IN (''QI'', ''VI''))
OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value = ''MD'')
OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'')
 OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'')) /* fallback for the rest of the entries */
',Updated=TO_TIMESTAMP('2019-04-19 15:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- 2019-04-19T16:06:43.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''SOO'' | @DocBaseType@=''POO'' | @DocBaseType@=''ARI'' | @DocBaseType@=''ARC'' | @DocBaseType@=''MOP'' | @DocBaseType@=''MMR'' | @DocBaseType@=''MMS'' | @DocBaseType@=''API'' | @DocBaseType@=''MMI'' | @DocBaseType@ = ''APC'' | @DocBaseType@=''SDD''',Updated=TO_TIMESTAMP('2019-04-19 16:06:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2581
;

