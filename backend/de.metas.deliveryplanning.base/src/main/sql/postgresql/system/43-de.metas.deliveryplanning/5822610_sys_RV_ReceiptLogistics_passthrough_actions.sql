-- The receipt-logistics window's five PASS-THROUGH actions (REQUIREMENTS 3.4, AC7):
-- "Korrektur", "Leergut Ausgabe", "Leergut Rücknahme", "Foto" and "Drucken Produktanlieferung".
--
-- They do exactly what window 541954's actions of the same name do, to the same record - the row's receipt
-- schedule - and both row types offer them, because the photo, the report, the correction and the empties
-- document all belong to the schedule, which the planned and the unplanned branch of the view both have.
--
-- Why NEW AD_Process rows rather than 541954's: the same mechanical reason as the four receive actions
-- (5822600). For a view row the platform resolves a process' record via IView#getTableRecordReferenceOrNull,
-- which on this window yields RV_ReceiptLogistics, while every WEBUI_M_ReceiptSchedule_* action asks for
-- M_ReceiptSchedule - and the seam is sealed (JavaProcess#getRecord is protected final). Pointing this
-- window's AD_Table_Process at them would ship five buttons that throw when pressed. The new classes read the
-- schedule off the selected GRID ROW instead and call the shared ReceiptScheduleActions, which is the ONE
-- place each action's body lives - the receipt-schedule window's own processes call it too, so the two
-- windows cannot drift.
--
-- WHAT IS DELIBERATELY NOT HERE, and must stay not here (REQUIREMENTS 3.4):
--   * "Auswahl - Zeilen schließen"          (M_ReceiptSchedule_Close, 540547)
--   * "Auswahl - Zeilen reaktivieren"       (M_ReceiptSchedule_ReOpen, 540546)
--         - a PLANNED row is closed and reopened through the planning's own pair, not the schedule's;
--           closing the shared schedule would hit every sibling planning of a split.
--   * "Zugesagten Termin und Referenz ändern" (M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference,
--           585538) - the planning owns its dates.
--   * "Zum Transportauftrag hinzufügen"     (M_ReceiptSchedule_AddTo_M_ShipperTransportation, 585522)
--   * "Exportstatus Ändern"                 (M_ReceiptSchedule_ChangeExportStatus, 584730)
--         - not part of this window at all.
-- "Absent" here means exactly that: no AD_Table_Process row on AD_Table 542644 for those five AD_Process ids,
-- so the platform never offers them - as opposed to an inactive row or a rejecting precondition, which would
-- still put a greyed-out entry in the menu. Their rows on M_ReceiptSchedule are untouched.
--
-- EntityType 'D' throughout, matching AD_Table 542644 and AD_Window 542190, i.e. the rest of this window.
--
-- Ids 585662-585666 (AD_Process), 543305 (AD_Process_Para), 541682-541686 (AD_Table_Process):
-- all From ID Server.

-- ---------------------------------------------------------------------------------------------------
-- AD_Process
-- ---------------------------------------------------------------------------------------------------

INSERT INTO AD_Process (AD_Process_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                        Value,Name,AccessLevel,EntityType,IsReport,IsDirectPrint,ClassName,Type,ShowHelp,
                        IsBetaFunctionality,IsServerProcess,CopyFromProcess,IsOneInstanceOnly,LockWaitTimeout,
                        RefreshAllAfterExecution,AllowProcessRerun,IsUseBPartnerLanguage,IsApplySecuritySettings,
                        IsTranslateExcelHeaders,IsNotifyUserAfterExecution,PostgRestResponseFormat,IsFormatExcelFile,
                        CSVFieldDelimiter,IsUpdateExportDate,IsLogWarning,CSVFieldQuote,
                        IsIncludeCSVHeaderRow,IsPdfA3Output)
VALUES
 (585662 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptLogistics_SelectHUsToReverse','Korrektur',3,'D','N','N',
  'de.metas.ui.web.receiptlogistics.process.WEBUI_RV_ReceiptLogistics_SelectHUsToReverse','Java','N',
  'N','N','N','N',0,'N','N','Y','N','Y','N','json','Y','','N','N','"','Y','N'),
 (585663 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsToVendor','Leergut Ausgabe',3,'D','N','N',
  'de.metas.ui.web.receiptlogistics.process.WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsToVendor','Java','N',
  'N','N','N','N',0,'N','N','Y','N','Y','N','json','Y','','N','N','"','Y','N'),
 (585664 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsFromCustomer','Leergut Rücknahme',3,'D','N','N',
  'de.metas.ui.web.receiptlogistics.process.WEBUI_RV_ReceiptLogistics_CreateEmptiesReturnsFromCustomer','Java','N',
  'N','N','N','N',0,'N','N','Y','N','Y','N','json','Y','','N','N','"','Y','N'),
 (585665 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptLogistics_AttachPhoto','Foto',3,'D','N','N',
  'de.metas.ui.web.receiptlogistics.process.WEBUI_RV_ReceiptLogistics_AttachPhoto','Java','Y',
  'N','N','N','N',0,'N','N','Y','N','Y','N','json','Y','','N','N','"','Y','N'),
 (585666 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptLogistics_RunMaterialReceiptJasper','Drucken Produktanlieferung',3,'D','N','N',
  'de.metas.ui.web.receiptlogistics.process.WEBUI_RV_ReceiptLogistics_RunMaterialReceiptJasper','Java','N',
  'N','N','N','N',0,'N','N','Y','N','Y','N','json','Y','','N','N','"','Y','N')
;

-- seed AD_Process_Trl for every active system or base language, copying the German base name
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Name, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, p.AD_Process_ID, p.Name, p.Description, p.Help, 'N',
       p.AD_Client_ID, p.AD_Org_ID, 'Y', p.Created, p.CreatedBy, p.Updated, p.UpdatedBy
FROM AD_Language l, AD_Process p
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND p.AD_Process_ID IN (585662,585663,585664,585665,585666)
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Process_ID=p.AD_Process_ID)
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Process_ID IN (585662,585663,585664,585665,585666);

-- en_US: the English names window 541954's counterparts already carry, so the two windows read alike.
-- "Korrektur" and "Foto" carry the German word in en_US there too; copied rather than "improved", because a
-- dispatcher who knows one window must recognise the entry on the other.
UPDATE AD_Process_Trl SET Name='Korrektur',       IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585662;
UPDATE AD_Process_Trl SET Name='Empties Return',  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585663;
UPDATE AD_Process_Trl SET Name='Empties Receive', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585664;
UPDATE AD_Process_Trl SET Name='Foto',            IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585665;
UPDATE AD_Process_Trl SET Name='Print Material Receipt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585666;

-- fr_CH per the convention stated once in 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql:
-- the en_US text, IsTranslated='N'. Runs after the en_US overrides so it copies the English name.
UPDATE AD_Process_Trl trl
   SET Name = en.Name, IsTranslated = 'N',
       Updated = TO_TIMESTAMP('2026-09-04 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
  FROM AD_Process_Trl en
 WHERE en.AD_Process_ID = trl.AD_Process_ID AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH' AND trl.AD_Process_ID IN (585662,585663,585664,585665,585666)
;

-- ---------------------------------------------------------------------------------------------------
-- AD_Process_Para -- only "Foto" takes one. Copied from the counterpart (540755 -> AD_Process_Para 541147)
-- rather than retyped, so the two dialogs cannot drift: both classes read the SAME parameter name.
-- ---------------------------------------------------------------------------------------------------

INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
                             Created, CreatedBy, Updated, UpdatedBy, Name, Description, Help, SeqNo,
                             AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID, ColumnName,
                             IsCentrallyMaintained, FieldLength, IsMandatory, IsRange, DefaultValue,
                             DefaultValue2, VFormat, ValueMin, ValueMax, AD_Element_ID, EntityType,
                             ReadOnlyLogic, DisplayLogic, IsEncrypted, IsAutocomplete, BarcodeScannerType,
                             ShowInactiveValues)
SELECT m.new_id /*From ID Server*/, m.new_process_id, src.AD_Client_ID, src.AD_Org_ID, src.IsActive,
       TO_TIMESTAMP('2026-09-04 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-09-04 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
       src.Name, src.Description, src.Help, src.SeqNo,
       src.AD_Reference_ID, src.AD_Reference_Value_ID, src.AD_Val_Rule_ID, src.ColumnName,
       src.IsCentrallyMaintained, src.FieldLength, src.IsMandatory, src.IsRange, src.DefaultValue,
       src.DefaultValue2, src.VFormat, src.ValueMin, src.ValueMax, src.AD_Element_ID, 'D',
       src.ReadOnlyLogic, src.DisplayLogic, src.IsEncrypted, src.IsAutocomplete, src.BarcodeScannerType,
       src.ShowInactiveValues
FROM (VALUES
        (543305, 585665, 541147)   -- AD_Image_ID
     ) AS m(new_id, new_process_id, src_id)
JOIN AD_Process_Para src ON src.AD_Process_Para_ID = m.src_id
;

INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
                                 Created, CreatedBy, Updated, UpdatedBy, Name, Description, Help, IsTranslated)
SELECT m.new_id, srctrl.AD_Language, srctrl.AD_Client_ID, srctrl.AD_Org_ID, srctrl.IsActive,
       TO_TIMESTAMP('2026-09-04 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-09-04 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
       srctrl.Name, srctrl.Description, srctrl.Help, srctrl.IsTranslated
FROM (VALUES
        (543305, 541147)
     ) AS m(new_id, src_id)
JOIN AD_Process_Para_Trl srctrl ON srctrl.AD_Process_Para_ID = m.src_id
;

-- ---------------------------------------------------------------------------------------------------
-- AD_Table_Process -- what makes the five actions appear on AD_Table 542644 (RV_ReceiptLogistics).
-- Reachability mirrors window 541954's rows exactly: quick action AND action-menu entry, none of them the
-- default (that is "HUs annehmen Voreinst.", set in 5822600).
-- ---------------------------------------------------------------------------------------------------

INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
                              Updated, UpdatedBy, EntityType, AD_Table_ID, AD_Process_ID,
                              WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default,
                              WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_IncludedTabTopAction)
VALUES
 (541682 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585662,'Y','N','Y','Y','N'),
 (541683 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585663,'Y','N','Y','Y','N'),
 (541684 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585664,'Y','N','Y','Y','N'),
 (541685 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585665,'Y','N','Y','Y','N'),
 (541686 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585666,'Y','N','Y','Y','N')
;
