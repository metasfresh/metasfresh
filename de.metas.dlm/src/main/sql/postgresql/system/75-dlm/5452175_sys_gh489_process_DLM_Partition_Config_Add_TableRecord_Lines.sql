

-- 24.10.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540732,'Y','de.metas.dlm.partitioner.process.DLM_Partition_Config_Add_TableRecord_Lines','N',TO_TIMESTAMP('2016-10-24 13:00:34','YYYY-MM-DD HH24:MI:SS'),100,'Erweitert eine Paritionierungskonfiguration um Tabellen, die eine der bereits bestehdenden Tabellen per [*_Table_ID,*Record_ID] referenzieren ','de.metas.dlm','Beispiel: wenn C_OrderLine schon Teil der Konfiguration ist, und es AD_ChangeLog-Datensätze gibt, die eine C_OrderLine referenzieren, dann wird die Konfiguration um eine AD_ChangeLog-Zeile erweitert.','Y','N','N','N','N','N','N','Y',0,'Konfiguration um Tabellen-Datensatzreferenzen erweitern','N','Y',0,0,'Java',TO_TIMESTAMP('2016-10-24 13:00:34','YYYY-MM-DD HH24:MI:SS'),100,'DLM_Partition_Config_Add_TableRecord_Lines')
;

-- 24.10.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540732 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 24.10.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543191,0,540732,541043,19,'DLM_Partition_Config_ID',TO_TIMESTAMP('2016-10-24 13:00:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm',0,'Y','N','Y','N','Y','N','DLM Partitionierungskonfiguration',10,TO_TIMESTAMP('2016-10-24 13:00:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.10.2016 13:00
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541043 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 24.10.2016 13:01
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@DLM_Partition_Config_ID/-1@',Updated=TO_TIMESTAMP('2016-10-24 13:01:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541043
;

-- 24.10.2016 13:01
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540732,540789,TO_TIMESTAMP('2016-10-24 13:01:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-10-24 13:01:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.10.2016 13:02
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540732,540790,TO_TIMESTAMP('2016-10-24 13:02:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-10-24 13:02:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.10.2016 13:18
-- URL zum Konzept
UPDATE AD_Process SET Description='Erweitert eine Paritionierungskonfiguration um Tabellen, die eine der bereits bestehdenden Tabellen per [*_Table_ID,*Record_ID] referenzieren.',Updated=TO_TIMESTAMP('2016-10-24 13:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540732
;

-- 24.10.2016 13:18
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540732
;

-- 24.10.2016 14:09
-- URL zum Konzept
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-10-24 14:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540732
;

