
-- 04.03.2016 10:24
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540665,'Y','de.metas.javaclasses.process.AD_JavaClass_Type_UpdateClassRecordsList','N',TO_TIMESTAMP('2016-03-04 10:24:57','YYYY-MM-DD HH24:MI:SS'),100,'Scans the classpath and updates the AD_JavaClass records of the current AD_JavaClass_Type','de.metas.javaclasses','For newly found classes, the process creates a new  AD_JavaClass record. Records for classes that are not anymore on the classpath are deactivated, but not deleted.','Y','N','N','N','N','N',0,'AD_JavaClass_Type_UpdateClassRecordsList','N','Y',0,0,'Java',TO_TIMESTAMP('2016-03-04 10:24:57','YYYY-MM-DD HH24:MI:SS'),100,'AD_JavaClass_Type_UpdateClassRecordsList')
;

-- 04.03.2016 10:24
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540665 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 04.03.2016 10:25
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540665,540521,TO_TIMESTAMP('2016-03-04 10:25:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.javaclasses','Y',TO_TIMESTAMP('2016-03-04 10:25:15','YYYY-MM-DD HH24:MI:SS'),100)
;

