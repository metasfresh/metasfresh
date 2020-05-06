-- 17.07.2015 16:20:18
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540589,'de.metas.materialtracking.process.M_Material_Tracking_CreateOrUpdate_ID','N',TO_TIMESTAMP('2015-07-17 16:20:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','N','N','N','N',0,'Material-Vorgang-ID einfügen','N','Y',0,0,'Java',TO_TIMESTAMP('2015-07-17 16:20:17','YYYY-MM-DD HH24:MI:SS'),100,'M_Material_Tracking_CreateOrUpdate_ID')
;

-- 17.07.2015 16:20:18
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540589 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 17.07.2015 16:20:56
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,439,0,540589,540721,11,'Line',TO_TIMESTAMP('2015-07-17 16:20:56','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','de.metas.materialtracking',0,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','Y','N','Zeile Nr.',10,TO_TIMESTAMP('2015-07-17 16:20:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.07.2015 16:20:56
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540721 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 17.07.2015 16:24:08
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540300,'M_Material_Tracking.ValidFrom >= @#Date@ AND M_Material_Tracking.ValidTo <= @#Date@',TO_TIMESTAMP('2015-07-17 16:24:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','M_Material_Tracking_ID Valid','S',TO_TIMESTAMP('2015-07-17 16:24:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.07.2015 16:24:25
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542532,0,540589,540722,30,540300,'M_Material_Tracking_ID',TO_TIMESTAMP('2015-07-17 16:24:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking',0,'Y','N','Y','N','N','N','Material-Vorgang-ID',20,TO_TIMESTAMP('2015-07-17 16:24:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.07.2015 16:24:25
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540722 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;


-- 17.07.2015 16:27:35
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540589,259,TO_TIMESTAMP('2015-07-17 16:27:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y',TO_TIMESTAMP('2015-07-17 16:27:35','YYYY-MM-DD HH24:MI:SS'),100)
;




-- 20.07.2015 08:18
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_Material_Tracking.ValidFrom <= ''@#Date@'' AND M_Material_Tracking.ValidTo >= ''@#Date@''', Description='filters for M_Material_Trackings that are valid at @#Date@',Updated=TO_TIMESTAMP('2015-07-20 08:18:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540300
;


-- 20.07.2015 08:20
-- URL zum Konzept
UPDATE AD_Process_Para SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2015-07-20 08:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540722
;
