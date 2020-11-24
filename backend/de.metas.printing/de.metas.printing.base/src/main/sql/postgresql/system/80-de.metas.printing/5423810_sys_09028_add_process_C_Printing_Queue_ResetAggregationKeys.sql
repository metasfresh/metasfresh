


-- 14.08.2015 05:21
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540599,'de.metas.printing.process.C_Printing_Queue_ResetAggregationKeys','N',TO_TIMESTAMP('2015-08-14 05:21:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','N','N','N','N','N',0,'Auswahl Aggregationsmerkmale neu berechnen','N','Y',0,0,'Java',TO_TIMESTAMP('2015-08-14 05:21:12','YYYY-MM-DD HH24:MI:SS'),100,'C_Printing_Queue_ResetAggregationKeys')
;

-- 14.08.2015 05:21
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540599 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 14.08.2015 05:21
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540599,540435,TO_TIMESTAMP('2015-08-14 05:21:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y',TO_TIMESTAMP('2015-08-14 05:21:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.08.2015 05:23
-- URL zum Konzept
UPDATE AD_Process SET Description='Berechnet den Aggregationsschlüssel der laut Filterkriterien ausgewählten Datensätze neu.', Help='Die Prozessausführung sollte in der Regel nicht nötig sein.', Name='Aggregationsmerkmale neu berechnen',Updated=TO_TIMESTAMP('2015-08-14 05:23:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540599
;

-- 14.08.2015 05:23
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540599
;
