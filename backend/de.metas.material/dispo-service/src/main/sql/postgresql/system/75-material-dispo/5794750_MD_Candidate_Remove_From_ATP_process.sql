-- Run mode: SWING_CLIENT

-- Value: MD_Candidate_RemoveFromATP
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-03-18T14:41:29.826Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585600,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2026-03-18 14:41:29.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Kandidat aus Zusagbar (ATP) entfernen','json','N','N','xls','SELECT de_metas_material.MD_Candidate_Remove_From_ATP(@MD_Candidate_ID/-1@);','SQL',TO_TIMESTAMP('2026-03-18 14:41:29.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MD_Candidate_RemoveFromATP')
;

-- 2026-03-18T14:41:29.837Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585600 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: MD_Candidate_RemoveFromATP(de.metas.process.ExecuteUpdateSQL)
-- 2026-03-18T14:41:54.123Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Remove from ATP',Updated=TO_TIMESTAMP('2026-03-18 14:41:54.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585600
;

-- 2026-03-18T14:41:54.124Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: MD_Candidate_RemoveFromATP
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-03-18T14:44:03.037Z
UPDATE AD_Process SET Name='Aus Zusagbar entfernen',Updated=TO_TIMESTAMP('2026-03-18 14:44:03.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585600
;

-- 2026-03-18T14:44:03.040Z
UPDATE AD_Process_Trl trl SET Name='Aus Zusagbar entfernen' WHERE AD_Process_ID=585600 AND AD_Language='de_DE'
;

-- Process: MD_Candidate_RemoveFromATP(de.metas.process.ExecuteUpdateSQL)
-- 2026-03-18T14:44:07.326Z
UPDATE AD_Process_Trl SET Name='Aus Zusagbar entfernen',Updated=TO_TIMESTAMP('2026-03-18 14:44:07.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585600
;

-- 2026-03-18T14:44:07.327Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: MD_Candidate_RemoveFromATP(de.metas.process.ExecuteUpdateSQL)
-- 2026-03-18T14:44:09.484Z
UPDATE AD_Process_Trl SET Name='Aus Zusagbar entfernen',Updated=TO_TIMESTAMP('2026-03-18 14:44:09.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585600
;

-- 2026-03-18T14:44:09.485Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: MD_Candidate_RemoveFromATP(de.metas.process.ExecuteUpdateSQL)
-- Table: MD_Candidate
-- EntityType: de.metas.materialtracking
-- 2026-03-19T05:00:56.036Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585600,540808,541634,TO_TIMESTAMP('2026-03-19 05:00:55.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.materialtracking','Y',TO_TIMESTAMP('2026-03-19 05:00:55.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Value: MD_Candidate_RemoveFromATP
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-03-19T05:09:11.877Z
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2026-03-19 05:09:11.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585600
;

-- Value: MD_Candidate_RemoveFromATP
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-03-19T06:20:25.784Z
UPDATE AD_Process SET TechnicalNote=' Purpose: Remove an MD_Candidate record from ATP (Available to Promise) calculations

 This function:
 1. Calculates the candidate''s stock impact based on type, business case, qty, and qtyFulfilled
 2. Negates this impact to remove it from ATP
 3. Sets the candidate''s Qty/QtyFulfilled to 0
 4. Updates the associated STOCK candidate''s ATP value
 5. Propagates changes through the entire MD_Candidate_QtyDetails chain chronologically
 6. Creates QtyDetails if they don''t exist (looking up previous STOCK record)',Updated=TO_TIMESTAMP('2026-03-19 06:20:25.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585600
;

