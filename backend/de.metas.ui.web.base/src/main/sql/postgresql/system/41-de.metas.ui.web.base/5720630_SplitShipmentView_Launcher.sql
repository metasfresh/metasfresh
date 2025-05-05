-- Run mode: SWING_CLIENT

-- Value: SplitShipmentView_Launcher
-- Classname: de.metas.ui.web.split_shipment.SplitShipmentView_Launcher
-- 2024-04-02T17:13:14.298Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,
                        EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,
                        IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,
                        IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,
                        Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585374,'Y','de.metas.ui.web.split_shipment.SplitShipmentView_Launcher','N',TO_TIMESTAMP('2024-04-02 20:13:14.068','YYYY-MM-DD HH24:MI:SS.US'),100,
        'de.metas.inoutcandidate','Y','N','N','N','Y',
        'N','N','N','Y',
        'Y',0,'Split Shipments','json','N','N','xls',
        'Java',TO_TIMESTAMP('2024-04-02 20:13:14.068','YYYY-MM-DD HH24:MI:SS.US'),100,'SplitShipmentView_Launcher')
;

-- 2024-04-02T17:13:14.308Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585374 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: SplitShipmentView_Launcher(de.metas.ui.web.split_shipment.SplitShipmentView_Launcher)
-- Table: M_ShipmentSchedule
-- EntityType: de.metas.inoutcandidate
-- 2024-04-02T17:13:56.492Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585374,500221,541472,TO_TIMESTAMP('2024-04-02 20:13:56.331','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inoutcandidate','Y',TO_TIMESTAMP('2024-04-02 20:13:56.331','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Y','Y','N')
;

