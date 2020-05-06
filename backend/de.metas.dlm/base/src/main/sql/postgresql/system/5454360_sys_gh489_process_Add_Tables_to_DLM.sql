-- 13.12.2016 15:17
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540747,'Y','de.metas.dlm.process.Add_Tables_to_DLM','N',TO_TIMESTAMP('2016-12-13 15:17:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','N','N','N','N','N','N','Y',0,'Alle Tabellen für DLM einrichten','N','Y','Java',TO_TIMESTAMP('2016-12-13 15:17:37','YYYY-MM-DD HH24:MI:SS'),100,'Add_Tables_to_DLM')
;

-- 13.12.2016 15:17
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540747 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 13.12.2016 15:18
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543191,0,540747,541130,18,'DLM_Partition_Config_ID',TO_TIMESTAMP('2016-12-13 15:18:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm',0,'Y','N','Y','N','Y','N','DLM Partitionierungskonfiguration',10,TO_TIMESTAMP('2016-12-13 15:18:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.12.2016 15:18
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541130 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 13.12.2016 15:20
-- URL zum Konzept
UPDATE AD_Process SET Description='Stellt zu einer Partitionierungskonfiguration sicher, dass alle dort vorkommenden Tabellen für DLM eingerichtet sind.
Hinweis: Wenn das System Partitionen erstellt wird dies ohnehin sichergestellt. Dieser Prozess erlaubt es aber die Tabellen vorab entsprechend einzurichten.',Updated=TO_TIMESTAMP('2016-12-13 15:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540747
;

-- 13.12.2016 15:20
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540747
;

-- 13.12.2016 15:20
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540747,540789,TO_TIMESTAMP('2016-12-13 15:20:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-12-13 15:20:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.12.2016 15:21
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540747,540790,TO_TIMESTAMP('2016-12-13 15:21:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-12-13 15:21:12','YYYY-MM-DD HH24:MI:SS'),100)
;

