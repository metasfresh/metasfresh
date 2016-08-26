-- 26.08.2016 14:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,540715,'Y','org.adempiere.ad.window.process.AD_UI_ElementGroup_Move','N',TO_TIMESTAMP('2016-08-26 14:22:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y',0,'Move Element Group','N','Y',0,0,'Java',TO_TIMESTAMP('2016-08-26 14:22:54','YYYY-MM-DD HH24:MI:SS'),100,'AD_UI_ElementGroup_Move')
;

-- 26.08.2016 14:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540715 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 26.08.2016 14:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543153,0,540715,541005,19,'AD_UI_Column_ID',TO_TIMESTAMP('2016-08-26 14:24:06','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','Y','N','UI Column',10,TO_TIMESTAMP('2016-08-26 14:24:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541005 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 26.08.2016 14:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540341,'exists (
	select 1
	from AD_UI_Section s
	where s.AD_UI_Section_ID=AD_UI_Column.AD_UI_Section_ID
	and s.AD_Tab_ID=@AD_Tab_ID/-1@
)
',TO_TIMESTAMP('2016-08-26 14:28:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_UI_Column_for_AD_Tab_ID','S',TO_TIMESTAMP('2016-08-26 14:28:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540341, EntityType='D',Updated=TO_TIMESTAMP('2016-08-26 14:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541005
;

-- 26.08.2016 14:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540715,540782,TO_TIMESTAMP('2016-08-26 14:29:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2016-08-26 14:29:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,540716,'Y','org.adempiere.ad.window.process.AD_UI_Element_Move','N',TO_TIMESTAMP('2016-08-26 14:30:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y',0,'Move UI Element','N','Y',0,0,'Java',TO_TIMESTAMP('2016-08-26 14:30:00','YYYY-MM-DD HH24:MI:SS'),100,'AD_UI_Element_Move')
;

-- 26.08.2016 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540716 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 26.08.2016 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543154,0,540716,541006,19,'AD_UI_ElementGroup_ID',TO_TIMESTAMP('2016-08-26 14:30:04','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','Y','N','UI Element Group',10,TO_TIMESTAMP('2016-08-26 14:30:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541006 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 26.08.2016 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Move UI Element Group',Updated=TO_TIMESTAMP('2016-08-26 14:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540715
;

-- 26.08.2016 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540715
;

-- 26.08.2016 14:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2016-08-26 14:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541006
;

-- 26.08.2016 14:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543153,0,540716,541007,19,'AD_UI_Column_ID',TO_TIMESTAMP('2016-08-26 14:35:00','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','UI Column',20,TO_TIMESTAMP('2016-08-26 14:35:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541007 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 26.08.2016 14:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543152,0,540716,541008,19,'AD_UI_Section_ID',TO_TIMESTAMP('2016-08-26 14:35:34','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','UI Section',30,TO_TIMESTAMP('2016-08-26 14:35:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541008 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 26.08.2016 14:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=9,Updated=TO_TIMESTAMP('2016-08-26 14:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541008
;

-- 26.08.2016 14:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=30,Updated=TO_TIMESTAMP('2016-08-26 14:36:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541006
;

-- 26.08.2016 14:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=10,Updated=TO_TIMESTAMP('2016-08-26 14:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541008
;

-- 26.08.2016 14:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540342,'AD_UI_Section.AD_Tab_ID=@AD_Tab_ID/-1@',TO_TIMESTAMP('2016-08-26 14:38:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_UI_Section_for_AD_Tab_ID','S',TO_TIMESTAMP('2016-08-26 14:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540342,Updated=TO_TIMESTAMP('2016-08-26 14:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541008
;

-- 26.08.2016 14:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540343,'AD_UI_Column.AD_UI_Section_ID=@AD_UI_Section_ID/-1@',TO_TIMESTAMP('2016-08-26 14:39:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_UI_Column_for_AD_UI_Section_ID','S',TO_TIMESTAMP('2016-08-26 14:39:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540343,Updated=TO_TIMESTAMP('2016-08-26 14:40:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541007
;

-- 26.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540344,'AD_UI_ElementGroup.AD_UI_Column_ID=@AD_UI_Column_ID/-1@',TO_TIMESTAMP('2016-08-26 14:41:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_UI_ElementGroup_for_AD_UI_Column_ID','S',TO_TIMESTAMP('2016-08-26 14:41:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540344,Updated=TO_TIMESTAMP('2016-08-26 14:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541006
;

-- 26.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2016-08-26 14:42:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541005
;

-- 26.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543152,0,540715,541009,19,540342,'AD_UI_Section_ID',TO_TIMESTAMP('2016-08-26 14:42:37','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','Y','N','UI Section',30,TO_TIMESTAMP('2016-08-26 14:42:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541009 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 26.08.2016 14:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=10,Updated=TO_TIMESTAMP('2016-08-26 14:42:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541009
;

-- 26.08.2016 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540343,Updated=TO_TIMESTAMP('2016-08-26 14:43:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541005
;

-- 26.08.2016 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540341
;

-- 26.08.2016 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2016-08-26 14:43:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541009
;

-- 26.08.2016 14:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540716,540783,TO_TIMESTAMP('2016-08-26 14:43:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2016-08-26 14:43:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2016 14:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N', IsUseBPartnerLanguage='N', RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-08-26 14:56:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540716
;

-- 26.08.2016 14:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N', IsUseBPartnerLanguage='N',Updated=TO_TIMESTAMP('2016-08-26 14:56:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540715
;

-- 26.08.2016 15:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-08-26 15:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540715
;

