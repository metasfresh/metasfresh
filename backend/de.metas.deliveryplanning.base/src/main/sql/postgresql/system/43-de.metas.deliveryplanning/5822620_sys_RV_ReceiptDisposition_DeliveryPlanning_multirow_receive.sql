-- The receipt-logistics window's MULTI-ROW receive (REQUIREMENTS 3.4, AC8 / TC9):
-- "Wareneingangsdispo zu Wareneingang" - receive the whole selection in one gesture, planned rows, unplanned
-- rows, or a mixture.
--
-- Deliberately NOT a quick action (WEBUI_ViewQuickAction='N'), mirroring window 541954's own multi-row entry
-- (M_ReceiptSchedule_Generate_M_InOuts, 540557, whose AD_Table_Process row carries the same 'N'): it books
-- goods for every selected row at once, which does not belong one careless click away. It is an action-menu
-- entry and a document action, like its counterpart.
--
-- NO AD_Process_Para. The counterpart's four parameters (warehouse, date from/to, "create movements") are
-- FILTERS over the whole M_ReceiptSchedule table - the shape a batch run from a menu needs. This action
-- receives exactly the rows the dispatcher selected in the grid, so a filter dialog would be a second, silently
-- competing selection.
--
-- The class routes per row on the nullable M_Delivery_Planning_ID and hands the selection to the ONE shared
-- receive (ReceiptFromReceiptScheduleService#receiveRows), which is the HU-aware route -
-- IHUReceiptScheduleBL#processReceiptSchedules -> InOutProducerFromReceiptScheduleHU - i.e. the same route the
-- counterpart batch takes, and the same one the window's single-row actions and the delivery-planning window's
-- generate-receipt already take. The non-HU de.metas.swat InOutProducer is not reached.
--
-- EntityType 'D' throughout, matching AD_Table 542644 and AD_Window 542190, i.e. the rest of this window.
--
-- Ids 585667 (AD_Process) and 541687 (AD_Table_Process): both From ID Server.

INSERT INTO AD_Process (AD_Process_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                        Value,Name,AccessLevel,EntityType,IsReport,IsDirectPrint,ClassName,Type,ShowHelp,
                        IsBetaFunctionality,IsServerProcess,CopyFromProcess,IsOneInstanceOnly,LockWaitTimeout,
                        RefreshAllAfterExecution,AllowProcessRerun,IsUseBPartnerLanguage,IsApplySecuritySettings,
                        IsTranslateExcelHeaders,IsNotifyUserAfterExecution,PostgRestResponseFormat,IsFormatExcelFile,
                        CSVFieldDelimiter,IsUpdateExportDate,IsLogWarning,CSVFieldQuote,
                        IsIncludeCSVHeaderRow,IsPdfA3Output)
VALUES
 (585667 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptDisposition_DeliveryPlanning_Generate_M_InOuts','Wareneingangsdispo zu Wareneingang',3,'D','N','N',
  'de.metas.ui.web.receiptdisposition_deliveryplanning.process.WEBUI_RV_ReceiptDisposition_DeliveryPlanning_Generate_M_InOuts','Java','Y',
  'N','N','N','N',0,'Y','N','Y','N','Y','N','json','Y','','N','N','"','Y','N')
;

-- seed AD_Process_Trl for every active system or base language, copying the German base name
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Name, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, p.AD_Process_ID, p.Name, p.Description, p.Help, 'N',
       p.AD_Client_ID, p.AD_Org_ID, 'Y', p.Created, p.CreatedBy, p.Updated, p.UpdatedBy
FROM AD_Language l, AD_Process p
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND p.AD_Process_ID = 585667
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Process_ID=p.AD_Process_ID)
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Process_ID = 585667;

UPDATE AD_Process_Trl SET Name='Generate Material Receipts', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 11:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Language='en_US' AND AD_Process_ID = 585667;

-- fr_CH per the convention stated once in 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql:
-- the en_US text, IsTranslated='N'. Runs after the en_US override so it copies the English name.
UPDATE AD_Process_Trl trl
   SET Name = en.Name, IsTranslated = 'N',
       Updated = TO_TIMESTAMP('2026-09-04 11:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
  FROM AD_Process_Trl en
 WHERE en.AD_Process_ID = trl.AD_Process_ID AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH' AND trl.AD_Process_ID = 585667
;

-- What makes the action appear on AD_Table 542644 (RV_ReceiptDisposition_DeliveryPlanning), action menu only.
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
                              Updated, UpdatedBy, EntityType, AD_Table_ID, AD_Process_ID,
                              WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default,
                              WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_IncludedTabTopAction)
VALUES
 (541687 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 11:00:04','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585667,'N','N','Y','Y','N')
;
