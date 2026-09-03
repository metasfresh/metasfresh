INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout, Name, PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585570, 'Y',
        'de.metas.ui.web.material.cockpit.v2.process.C_OrderLine_OpenMaterialCockpit',
        'N',
        TO_TIMESTAMP('2026-02-23 12:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Y', 0,
        'Materialcockpit öffnen',
        'json', 'N', 'N', 'xls', 'Java',
        TO_TIMESTAMP('2026-02-23 12:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'C_OrderLine_OpenMaterialCockpit')
;

-- AD_Process_Trl (translation template)
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585570
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- English translation
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name         = 'Open Material Cockpit',
    Updated      = TO_TIMESTAMP('2026-02-23 12:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Language = 'en_US'
  AND AD_Process_ID = 585570
;

UPDATE AD_Process base
SET Name      = trl.Name,
    Updated   = trl.Updated,
    UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID = base.AD_Process_ID
  AND trl.AD_Language = 'en_US'
  AND trl.AD_Language = getBaseLanguage()
;

-- Run mode: SWING_CLIENT

-- Value: C_OrderLine_OpenMaterialCockpit
-- Classname: de.metas.ui.web.material.cockpit.v2.process.C_OrderLine_OpenMaterialCockpit
-- 2026-02-24T23:56:40.877Z
UPDATE AD_Process SET EntityType='D',Updated=TO_TIMESTAMP('2026-02-24 23:56:40.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585570
;




-- Run mode: SWING_CLIENT

-- Process: C_OrderLine_OpenMaterialCockpit(de.metas.ui.web.material.cockpit.v2.process.C_OrderLine_OpenMaterialCockpit)
-- Table: C_OrderLine
-- Tab: Auftrag_OLD(143,D) -> Auftrag(186,D)
-- Window: Auftrag_OLD(143,D)
-- 2026-02-24T23:53:44.409Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541611
;

-- Process: C_OrderLine_OpenMaterialCockpit(de.metas.ui.web.material.cockpit.v2.process.C_OrderLine_OpenMaterialCockpit)
-- Table: C_Order
-- EntityType: D
-- 2026-02-24T23:54:22.536Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585570,259,541617,TO_TIMESTAMP('2026-02-24 23:54:22.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2026-02-24 23:54:22.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N')
;




