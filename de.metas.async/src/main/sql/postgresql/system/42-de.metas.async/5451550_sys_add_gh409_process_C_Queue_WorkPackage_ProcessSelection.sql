
--
-- DDL: make the description column bigger
--
-- 07.10.2016 07:20
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_process','Description','VARCHAR(2000)',null,'NULL')
;

COMMIT;



-- 07.10.2016 07:20
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=2000,Updated=TO_TIMESTAMP('2016-10-07 07:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2810
;


-- 05.10.2016 14:35
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540726,'Y','de.metas.async.process.C_Queue_WorkPackage_ProcessSelection','N',TO_TIMESTAMP('2016-10-05 14:35:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y','N','N','N','N','N','N','Y',0,'Auswahl per Prozess verarbeiten','N','Y',0,0,'Java',TO_TIMESTAMP('2016-10-05 14:35:30','YYYY-MM-DD HH24:MI:SS'),100,'C_Queue_WorkPackage_ProcessSelection')
;

-- 05.10.2016 14:35
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540726 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 05.10.2016 14:36
-- URL zum Konzept
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-10-05 14:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540726
;

-- 05.10.2016 14:38
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540726,540425,TO_TIMESTAMP('2016-10-05 14:38:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y',TO_TIMESTAMP('2016-10-05 14:38:31','YYYY-MM-DD HH24:MI:SS'),100)
;

UPDATE AD_Process SET Description='
Quick and dirty process that allows to process a selection of workpackages which are not flagged as ready for processing, or are flagged as error.
It would be cleaner to use the <code>WorkpackageProcessorTask</code>, but I wasn''t able to refactor it and make it usable in this short time.
Note that the WPs are processed with the context of this process.'
WHERE AD_Process_ID=540726;
