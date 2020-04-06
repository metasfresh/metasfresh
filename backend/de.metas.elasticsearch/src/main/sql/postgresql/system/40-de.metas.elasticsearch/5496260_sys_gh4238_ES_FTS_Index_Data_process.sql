-- 2018-06-20T18:20:20.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','ES_FTS_Index_Data','6','de.metas.elasticsearch','Y','N','Y','N',540981,'N','Y','N','Java','N','N',0,0,'Add data to index','de.metas.elasticsearch.process.ES_FTS_Index_Data',100,TO_TIMESTAMP('2018-06-20 18:20:20','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 18:20:20','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-06-20T18:20:20.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540981 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-06-20T18:20:34.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AD_Client_ID=0, IsActive='Y', ProcedureName=NULL, IsReport='N', IsDirectPrint='N', AD_ReportView_ID=NULL, AccessLevel='4', EntityType='de.metas.elasticsearch', ShowHelp='Y', WorkflowValue=NULL, AD_Workflow_ID=NULL, IsBetaFunctionality='N', IsServerProcess='Y', AD_Form_ID=NULL, CopyFromProcess='N', AD_PrintFormat_ID=NULL, Help=NULL, JasperReport=NULL, SQLStatement=NULL, JasperReport_Tabular=NULL, AllowProcessReRun='N', IsUseBPartnerLanguage='Y', IsApplySecuritySettings='N', Description=NULL, Type='Java', RefreshAllAfterExecution='N', IsOneInstanceOnly='N', LockWaitTimeout=0, AD_Org_ID=0, Classname='de.metas.elasticsearch.process.ES_IndexTable',Updated=TO_TIMESTAMP('2018-06-20 18:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540981
;

-- 2018-06-20T18:20:34.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,EntityType,Description,Help,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540981,10,'N',541312,'Y',630,'de.metas.elasticsearch','Fully qualified SQL WHERE clause','The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','WhereClause','N','N',0,'Sql WHERE',100,TO_TIMESTAMP('2018-06-20 18:20:34','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 18:20:34','YYYY-MM-DD HH24:MI:SS'),0,10)
;

-- 2018-06-20T18:20:34.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541312 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-06-20T18:20:34.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541312
;

-- 2018-06-20T18:20:34.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541312
;

-- 2018-06-20T18:20:34.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,EntityType,Description,Help,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540981,10,'N',541313,'Y',475,'de.metas.elasticsearch','Fully qualified ORDER BY clause','The ORDER BY Clause indicates the SQL ORDER BY clause to use for record selection','OrderByClause','N','N',0,'Sql ORDER BY',100,TO_TIMESTAMP('2018-06-20 18:20:34','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 18:20:34','YYYY-MM-DD HH24:MI:SS'),0,20)
;

-- 2018-06-20T18:20:34.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541313 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-06-20T18:20:34.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541313
;

-- 2018-06-20T18:20:34.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541313
;

-- 2018-06-20T18:20:34.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,EntityType,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540981,11,'N',541314,'Y',543188,'de.metas.elasticsearch','Limit','N','N',0,'Limit',100,TO_TIMESTAMP('2018-06-20 18:20:34','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 18:20:34','YYYY-MM-DD HH24:MI:SS'),0,30)
;

-- 2018-06-20T18:20:34.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541314 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-06-20T18:20:34.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541314
;

-- 2018-06-20T18:20:34.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541314
;

-- 2018-06-20T18:20:34.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,DefaultValue,IsCentrallyMaintained,AD_Element_ID,EntityType,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540981,20,'N',541315,'N','Y',543303,'de.metas.elasticsearch','ES_DeleteIndex','Y','N',0,'Delete elasticsearch index',100,TO_TIMESTAMP('2018-06-20 18:20:34','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 18:20:34','YYYY-MM-DD HH24:MI:SS'),0,40)
;

-- 2018-06-20T18:20:34.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541315 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-06-20T18:20:34.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541315
;

-- 2018-06-20T18:20:34.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541315
;

-- 2018-06-20T18:20:43.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.elasticsearch.process.ES_FTS_Index_Data',Updated=TO_TIMESTAMP('2018-06-20 18:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540981
;

-- 2018-06-20T18:21:09.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (EntityType,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('de.metas.elasticsearch',100,'Y',540990,0,'N','N',540981,0,100,TO_TIMESTAMP('2018-06-20 18:21:09','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 18:21:09','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-06-20T18:21:14.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2018-06-20 18:21:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540990 AND AD_Process_ID=540981
;

-- 2018-06-20T18:34:12.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541312
;

-- 2018-06-20T18:34:12.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541312
;

-- 2018-06-20T18:34:12.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541313
;

-- 2018-06-20T18:34:12.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541313
;

-- 2018-06-20T18:34:12.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541314
;

-- 2018-06-20T18:34:12.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541314
;

-- 2018-06-20T18:34:12.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541315
;

-- 2018-06-20T18:34:12.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541315
;

-- 2018-06-20T19:05:17.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,EntityType,IsEncrypted,Description,Help,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540981,10,'N',541317,'Y',630,'de.metas.elasticsearch','N','Fully qualified SQL WHERE clause','The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','WhereClause','N','N',0,'Sql WHERE',100,TO_TIMESTAMP('2018-06-20 19:05:16','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 19:05:16','YYYY-MM-DD HH24:MI:SS'),0,10)
;

-- 2018-06-20T19:05:17.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541317 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-06-20T19:05:17.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541317
;

-- 2018-06-20T19:05:17.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541317, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541029
;

-- 2018-06-20T19:05:17.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,EntityType,IsEncrypted,Description,Help,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540981,10,'N',541318,'Y',475,'de.metas.elasticsearch','N','Fully qualified ORDER BY clause','The ORDER BY Clause indicates the SQL ORDER BY clause to use for record selection','OrderByClause','N','N',0,'Sql ORDER BY',100,TO_TIMESTAMP('2018-06-20 19:05:17','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 19:05:17','YYYY-MM-DD HH24:MI:SS'),0,20)
;

-- 2018-06-20T19:05:17.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541318 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-06-20T19:05:17.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541318
;

-- 2018-06-20T19:05:17.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541318, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541028
;

-- 2018-06-20T19:05:17.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,EntityType,IsEncrypted,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540981,11,'N',541319,'Y',543188,'de.metas.elasticsearch','N','Limit','N','N',0,'Limit',100,TO_TIMESTAMP('2018-06-20 19:05:17','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 19:05:17','YYYY-MM-DD HH24:MI:SS'),0,30)
;

-- 2018-06-20T19:05:17.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541319 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-06-20T19:05:17.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541319
;

-- 2018-06-20T19:05:17.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541319, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541030
;

-- 2018-06-20T19:05:17.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,DefaultValue,IsCentrallyMaintained,AD_Element_ID,EntityType,IsEncrypted,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540981,20,'N',541320,'N','Y',543303,'de.metas.elasticsearch','N','ES_DeleteIndex','Y','N',0,'Delete elasticsearch index',100,TO_TIMESTAMP('2018-06-20 19:05:17','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-20 19:05:17','YYYY-MM-DD HH24:MI:SS'),0,40)
;

-- 2018-06-20T19:05:17.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541320 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-06-20T19:05:17.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541320
;

-- 2018-06-20T19:05:17.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541320, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541168
;

