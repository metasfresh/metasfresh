-- Run mode: SWING_CLIENT

-- Process: M_PickingSlot_PrintQRCodes(de.metas.picking.process.M_PickingSlot_PrintQRCodes)
-- Table: M_PickingSlot
-- Tab: Kommissionier Fach(540206,de.metas.picking) -> Kommissionier Fach(540561,de.metas.picking)
-- Window: Kommissionier Fach(540206,de.metas.picking)
-- EntityType: D
-- 2025-10-21T18:07:42.846Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585000,540561,540543,541576,540206,TO_TIMESTAMP('2025-10-21 18:07:41.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-10-21 18:07:41.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Process: M_PickingSlot_PrintQRCodes(de.metas.picking.process.M_PickingSlot_PrintQRCodes)
-- Table: M_PickingSlot
-- Tab: Kommissionier Fach(540206,de.metas.picking) -> Kommissionier Fach(540561,de.metas.picking)
-- Window: Kommissionier Fach(540206,de.metas.picking)
-- EntityType: D
-- 2025-10-21T18:12:40.207Z
UPDATE AD_Table_Process SET WEBUI_IncludedTabTopAction='Y',Updated=TO_TIMESTAMP('2025-10-21 18:12:40.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541576
;

-- Process: M_PickingSlot_PrintQRCodes(de.metas.picking.process.M_PickingSlot_PrintQRCodes)
-- Table: M_PickingSlot
-- Tab: Kommissionier Fach(540206,de.metas.picking) -> Kommissionier Fach(540561,de.metas.picking)
-- Window: Kommissionier Fach(540206,de.metas.picking)
-- EntityType: D
-- 2025-10-21T18:17:58.717Z
UPDATE AD_Table_Process SET WEBUI_IncludedTabTopAction='N',Updated=TO_TIMESTAMP('2025-10-21 18:17:58.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541576
;

-- Process: M_PickingSlot_PrintQRCodes(de.metas.picking.process.M_PickingSlot_PrintQRCodes)
-- Table: M_PickingSlot
-- EntityType: D
-- 2025-10-21T18:18:23.304Z
UPDATE AD_Table_Process SET AD_Tab_ID=NULL, AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2025-10-21 18:18:23.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541576
;

-- Process: M_PickingSlot_PrintQRCodes(de.metas.picking.process.M_PickingSlot_PrintQRCodes)
-- ParameterName: M_PickingSlot_ID
-- 2025-10-21T19:07:05.570Z
UPDATE AD_Process_Para SET DefaultValue='@M_PickingSlot_ID/-1@',Updated=TO_TIMESTAMP('2025-10-21 19:07:05.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=542209
;

