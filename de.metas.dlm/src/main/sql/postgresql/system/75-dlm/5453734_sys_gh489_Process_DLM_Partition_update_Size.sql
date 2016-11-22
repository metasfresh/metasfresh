

-- 22.11.2016 15:59
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540744,'Y','N',TO_TIMESTAMP('2016-11-22 15:59:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','N','N','N','N','N','N','Y',0,'Auswahl - Anz. zugeordn. Datensätze aktualisieren','N','Y',0,0,'Java',TO_TIMESTAMP('2016-11-22 15:59:30','YYYY-MM-DD HH24:MI:SS'),100,'DLM_Partition_Update_PartitionSize')
;

-- 22.11.2016 15:59
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540744 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 22.11.2016 16:00
-- URL zum Konzept
UPDATE AD_Process SET Description='Aktualisiert die Angabe "Anzahl zugeordneter Datensätze" bei den ausgewählten Partitionen. Die Ausführung sollte in der Regel nicht nötig sein.',Updated=TO_TIMESTAMP('2016-11-22 16:00:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540744
;

-- 22.11.2016 16:00
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540744
;

-- 22.11.2016 16:01
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540744,540788,TO_TIMESTAMP('2016-11-22 16:01:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-11-22 16:01:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.11.2016 16:02
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.dlm.partitioner.process.DLM_Partition_Update_PartitionSize',Updated=TO_TIMESTAMP('2016-11-22 16:02:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540744
;

-- 22.11.2016 16:02
-- URL zum Konzept
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-11-22 16:02:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540744
;

