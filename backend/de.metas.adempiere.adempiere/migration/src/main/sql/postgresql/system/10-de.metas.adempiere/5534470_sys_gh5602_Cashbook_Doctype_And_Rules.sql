-- 2019-10-15T14:21:10.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocBaseType='CMB',Updated=TO_TIMESTAMP('2019-10-15 17:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000021
;



-- 2019-10-16T13:06:06.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code=' (''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT''))
 OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC''))
 OR (''@DocBaseType@'' IN(''API'', ''MOP'') AND AD_Ref_List.Value IN (''QI'', ''VI''))
OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value = ''MD'')
OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'')
OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'')
OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS''))
 OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'')) /* fallback for the rest of the entries */
',Updated=TO_TIMESTAMP('2019-10-16 16:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- 2019-10-16T13:28:46.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542047,148,TO_TIMESTAMP('2019-10-16 16:28:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kassenjournal',TO_TIMESTAMP('2019-10-16 16:28:46','YYYY-MM-DD HH24:MI:SS'),100,'CB','Cash Book')
;

-- 2019-10-16T13:28:46.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542047 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-10-16T13:29:28.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542048,148,TO_TIMESTAMP('2019-10-16 16:29:28','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Bankauszug',TO_TIMESTAMP('2019-10-16 16:29:28','YYYY-MM-DD HH24:MI:SS'),100,'BS','Bankstatement')
;

-- 2019-10-16T13:29:28.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542048 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-10-16T13:29:35.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-16 16:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542048
;

-- 2019-10-16T13:29:49.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET ValueName='Cashbook',Updated=TO_TIMESTAMP('2019-10-16 16:29:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542047
;

-- 2019-10-16T13:42:15.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''SOO'' | @DocBaseType@=''POO'' | @DocBaseType@=''ARI'' | @DocBaseType@=''ARC'' | @DocBaseType@=''MOP'' | @DocBaseType@=''MMR'' | @DocBaseType@=''MMS'' | @DocBaseType@=''API'' | @DocBaseType@=''MMI'' | @DocBaseType@ = ''APC'' | @DocBaseType@=''SDD'' | @DocBaseType@ = ''CMB''',Updated=TO_TIMESTAMP('2019-10-16 16:42:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2581
;

-- 2019-10-16T13:51:22.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541067,TO_TIMESTAMP('2019-10-16 16:51:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_DocType Cashbook',TO_TIMESTAMP('2019-10-16 16:51:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-10-16T13:51:22.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541067 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-10-16T13:52:29.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,1501,0,541067,217,135,TO_TIMESTAMP('2019-10-16 16:52:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2019-10-16 16:52:29','YYYY-MM-DD HH24:MI:SS'),100,'C_DocType.DocBaseType = ''CMB'' AND C_DocType.DocSubType=''CB'' AND C_DocType.AD_Client_ID=@#AD_Client_ID@')
;

-- 2019-10-16T13:52:52.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541068,TO_TIMESTAMP('2019-10-16 16:52:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_DocType Bank Statement',TO_TIMESTAMP('2019-10-16 16:52:51','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-10-16T13:52:52.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541068 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-10-16T13:53:16.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,1501,0,541068,217,135,TO_TIMESTAMP('2019-10-16 16:53:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2019-10-16 16:53:16','YYYY-MM-DD HH24:MI:SS'),100,'C_DocType.DocBaseType = ''CMB'' AND C_DocType.DocSubType=''BS'' AND C_DocType.AD_Client_ID=@#AD_Client_ID@')
;

-- 2019-10-16T13:56:39.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=18, AD_Reference_Value_ID=541068,Updated=TO_TIMESTAMP('2019-10-16 16:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582015
;

-- 2019-10-16T13:57:44.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=18, AD_Reference_Value_ID=541067,Updated=TO_TIMESTAMP('2019-10-16 16:57:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589302
;

-- 2019-10-16T13:58:29.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2019-10-16 16:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568323
;

-- 2019-10-16T13:58:33.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutoApplyValidationRule='N',Updated=TO_TIMESTAMP('2019-10-16 16:58:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568323
;





-- 2019-10-16T13:48:26.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='CB',Updated=TO_TIMESTAMP('2019-10-16 16:48:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000021
;

-- 2019-10-16T13:48:36.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='BS',Updated=TO_TIMESTAMP('2019-10-16 16:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000020
;




-- 2019-10-16T14:13:48.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=SELECT C_DocType_ID from C_DocType WHERE DocBaseType = ''CMB'' AND DocSubType = ''CB''',Updated=TO_TIMESTAMP('2019-10-16 17:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589302
;

-- 2019-10-16T14:20:57.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2019-10-16 17:20:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589302
;

-- 2019-10-16T14:21:29.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_Value_ID=NULL, DefaultValue='@SQL=SELECT C_DocType_ID from C_DocType WHERE DocBaseType = ''CMB'' AND DocSubType = ''CB''',Updated=TO_TIMESTAMP('2019-10-16 17:21:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582015
;

-- 2019-10-16T14:21:55.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=SELECT C_DocType_ID from C_DocType WHERE DocBaseType = ''CMB'' AND DocSubType = ''BS''',Updated=TO_TIMESTAMP('2019-10-16 17:21:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582015
;

-- 2019-10-16T14:22:53.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540444,Updated=TO_TIMESTAMP('2019-10-16 17:22:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568323
;





-- 2019-10-16T15:08:58.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_Value_ID=541068,Updated=TO_TIMESTAMP('2019-10-16 18:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582015
;

-- 2019-10-16T15:09:28.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_Value_ID=541067,Updated=TO_TIMESTAMP('2019-10-16 18:09:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589302
;

