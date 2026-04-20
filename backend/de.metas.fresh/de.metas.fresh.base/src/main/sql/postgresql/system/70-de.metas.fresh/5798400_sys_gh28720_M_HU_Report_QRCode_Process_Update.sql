-- Run mode: SWING_CLIENT

-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- Table: M_HU
-- EntityType: D
-- 2026-04-17T09:21:28.258Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584980,540516,541640,TO_TIMESTAMP('2026-04-17 09:21:27.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2026-04-17 09:21:27.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N')
;

-- Value: M_HU_Report_QRCode
-- Classname: de.metas.handlingunits.process.M_HU_Report_QRCode
-- 2026-04-17T09:32:04.604Z
UPDATE AD_Process SET AllowProcessReRun='Y',Updated=TO_TIMESTAMP('2026-04-17 09:32:04.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=584980
;

-- Value: M_HU_Report_QRCode
-- Classname: de.metas.handlingunits.process.M_HU_Report_QRCode
-- 2026-04-17T10:30:05.530Z
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2026-04-17 10:30:05.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=584980
;

-- 2026-04-17T11:56:00.663Z
-- Remove legacy M_HU_Process binding that is superseded by the AD_Table_Process entry above
DELETE FROM M_HU_Process WHERE M_HU_Process_ID=540019
;

-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- ParameterName: PrintCopies
-- 2026-04-17T12:06:49.118Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,582963,0,584980,543183,11,'PrintCopies',TO_TIMESTAMP('2026-04-17 12:06:48.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.printing',0,'Y','N','Y','N','N','N','Exemplare',30,'N',TO_TIMESTAMP('2026-04-17 12:06:48.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-17T12:06:49.135Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543183 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: PDF QR  Jasper Reports
-- 2026-04-20T10:12:38.605Z
UPDATE AD_Val_Rule SET Code='AD_Process.IsActive=''Y'' AND AD_Process.JasperReport IS NOT NULL AND AD_Process.JasperReport ilike ''%QR_Label%'' AND AD_Process.value NOT IN (''AD_Printer_Config_PrintQRCode'')',Updated=TO_TIMESTAMP('2026-04-20 10:12:38.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540648
;
