-- The receipt-logistics window's four SINGLE-ROW receive actions (REQUIREMENTS 3.4, AC7 / AC7a / AC7b / AC8).
--
-- They are NEW AD_Process rows over NEW classes rather than the receipt-schedule window's existing four,
-- and the reason is mechanical rather than stylistic: for a view row the platform resolves the process'
-- record via IView#getTableRecordReferenceOrNull, which for this window yields RV_ReceiptLogistics. Every
-- WEBUI_M_ReceiptSchedule_* action asks for its record as M_ReceiptSchedule, so pointing this window's
-- AD_Table_Process at them would ship buttons that throw when pressed. The new classes instead read the two
-- source ids off the selected ROW - which is also the only way they can see the row's delivery planning at
-- all - and hand them to the ONE shared receive path, whose delivery-planning id is nullable: present on a
-- planned row (so the receipt carries M_Delivery_Planning_ID before completion and the planning learns it
-- was delivered), absent on an unplanned one (so the result is the plain receipt window 541954 produces).
--
-- Names, parameters and reachability mirror window 541954's actions exactly, because REQUIREMENTS 3.4 says
-- this window "carries the receipt-schedule action set". Reachability (owner, 2026-09-02): every action is
-- both an action-menu entry and a quick action; "HUs annehmen Voreinst." is the DEFAULT quick action, and
-- where its precondition rejects - no default LU/TU configuration resolves for the row - "CUs annehmen"
-- remains as the one-click path (AC7b), which is why all four are quick actions rather than only the default.
--
-- EntityType 'D' throughout, matching AD_Table 542644 and AD_Window 542190, i.e. the rest of this window.
--
-- Ids 585658-585661 (AD_Process), 543298-543304 (AD_Process_Para), 541673-541676 (AD_Table_Process):
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
 (585658 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptLogistics_ReceiveHUs_UsingDefaults','HUs annehmen Voreinst.',3,'D','N','N',
  'de.metas.ui.web.receiptlogistics.process.WEBUI_RV_ReceiptLogistics_ReceiveHUs_UsingDefaults','Java','N',
  'N','N','N','N',0,'N','N','Y','N','Y','N','json','Y','','N','N','"','Y','N'),
 (585659 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptLogistics_ReceiveHUs_UsingConfig','HUs annehmen',3,'D','N','N',
  'de.metas.ui.web.receiptlogistics.process.WEBUI_RV_ReceiptLogistics_ReceiveHUs_UsingConfig','Java','N',
  'N','N','N','N',0,'N','N','Y','N','Y','N','json','Y','','N','N','"','Y','N'),
 (585660 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptLogistics_ReceiveCUs','CUs annehmen',3,'D','N','N',
  'de.metas.ui.web.receiptlogistics.process.WEBUI_RV_ReceiptLogistics_ReceiveCUs','Java','N',
  'N','N','N','N',0,'N','N','Y','N','Y','N','json','Y','','N','N','"','Y','N'),
 (585661 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  'WEBUI_RV_ReceiptLogistics_ReceiveCUs_WithParam','CUs annehmen mit Menge',3,'D','N','N',
  'de.metas.ui.web.receiptlogistics.process.WEBUI_RV_ReceiptLogistics_ReceiveCUs_WithParam','Java','N',
  'N','N','N','N',0,'N','N','Y','N','Y','N','json','Y','','N','N','"','Y','N')
;

-- seed AD_Process_Trl for every active system or base language, copying the German base name
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Name, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, p.AD_Process_ID, p.Name, p.Description, p.Help, 'N',
       p.AD_Client_ID, p.AD_Org_ID, 'Y', p.Created, p.CreatedBy, p.Updated, p.UpdatedBy
FROM AD_Language l, AD_Process p
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND p.AD_Process_ID IN (585658,585659,585660,585661)
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Process_ID=p.AD_Process_ID)
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Process_ID IN (585658,585659,585660,585661);

-- en_US: the English names window 541954's counterparts already carry, so the two windows read alike.
UPDATE AD_Process_Trl SET Name='Receive HUs (default)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585658;
UPDATE AD_Process_Trl SET Name='Receive HUs',           IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585659;
UPDATE AD_Process_Trl SET Name='Receive CUs',           IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585660;
UPDATE AD_Process_Trl SET Name='Receive CU with Qty',   IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585661;

-- fr_CH per the convention stated once in 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql:
-- the en_US text, IsTranslated='N'. Runs after the en_US overrides so it copies the English name -- without
-- this the seeded row keeps the German base name, which is unusable rather than merely untranslated.
UPDATE AD_Process_Trl trl
   SET Name = en.Name, IsTranslated = 'N',
       Updated = TO_TIMESTAMP('2026-09-04 09:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
  FROM AD_Process_Trl en
 WHERE en.AD_Process_ID = trl.AD_Process_ID AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH' AND trl.AD_Process_ID IN (585658,585659,585660,585661)
;

-- ---------------------------------------------------------------------------------------------------
-- AD_Process_Para -- copied from the counterpart processes rather than retyped, so the two windows'
-- dialogs cannot drift: the classes read the SAME parameter names, references and defaults.
--   585659 (HUs annehmen)           <- 540753 WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig  (6 paras)
--   585661 (CUs annehmen mit Menge) <- 540765 WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam    (1 para)
-- ---------------------------------------------------------------------------------------------------

INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
                             Created, CreatedBy, Updated, UpdatedBy, Name, Description, Help, SeqNo,
                             AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID, ColumnName,
                             IsCentrallyMaintained, FieldLength, IsMandatory, IsRange, DefaultValue,
                             DefaultValue2, VFormat, ValueMin, ValueMax, AD_Element_ID, EntityType,
                             ReadOnlyLogic, DisplayLogic, IsEncrypted, IsAutocomplete, BarcodeScannerType,
                             ShowInactiveValues)
SELECT m.new_id /*From ID Server*/, m.new_process_id, src.AD_Client_ID, src.AD_Org_ID, src.IsActive,
       TO_TIMESTAMP('2026-09-04 09:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-09-04 09:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
       src.Name, src.Description, src.Help, src.SeqNo,
       src.AD_Reference_ID, src.AD_Reference_Value_ID, src.AD_Val_Rule_ID, src.ColumnName,
       src.IsCentrallyMaintained, src.FieldLength, src.IsMandatory, src.IsRange, src.DefaultValue,
       src.DefaultValue2, src.VFormat, src.ValueMin, src.ValueMax, src.AD_Element_ID, 'D',
       src.ReadOnlyLogic, src.DisplayLogic, src.IsEncrypted, src.IsAutocomplete, src.BarcodeScannerType,
       src.ShowInactiveValues
FROM (VALUES
        (543298, 585659, 541151),  -- IsSaveLUTUConfiguration
        (543299, 585659, 541135),  -- M_HU_PI_Item_Product_ID
        (543300, 585659, 541136),  -- M_LU_HU_PI_ID
        (543301, 585659, 541137),  -- QtyCUsPerTU
        (543302, 585659, 541138),  -- QtyTU
        (543303, 585659, 541139),  -- QtyLU
        (543304, 585661, 541163)   -- QtyCUsPerTU
     ) AS m(new_id, new_process_id, src_id)
JOIN AD_Process_Para src ON src.AD_Process_Para_ID = m.src_id
;

-- ... and their translations, likewise copied from the counterpart's, so the labels are identical.
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
                                 Created, CreatedBy, Updated, UpdatedBy, Name, Description, Help, IsTranslated)
SELECT m.new_id, srctrl.AD_Language, srctrl.AD_Client_ID, srctrl.AD_Org_ID, srctrl.IsActive,
       TO_TIMESTAMP('2026-09-04 09:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-09-04 09:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
       srctrl.Name, srctrl.Description, srctrl.Help, srctrl.IsTranslated
FROM (VALUES
        (543298, 541151),
        (543299, 541135),
        (543300, 541136),
        (543301, 541137),
        (543302, 541138),
        (543303, 541139),
        (543304, 541163)
     ) AS m(new_id, src_id)
JOIN AD_Process_Para_Trl srctrl ON srctrl.AD_Process_Para_ID = m.src_id
;

-- ---------------------------------------------------------------------------------------------------
-- AD_Table_Process -- what makes the four actions appear on AD_Table 542644 (RV_ReceiptLogistics).
-- ---------------------------------------------------------------------------------------------------

INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
                              Updated, UpdatedBy, EntityType, AD_Table_ID, AD_Process_ID,
                              WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default,
                              WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_IncludedTabTopAction)
VALUES
 (541673 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585658,'Y','Y','Y','Y','N'),
 (541674 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585659,'Y','N','Y','Y','N'),
 (541675 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585660,'Y','N','Y','Y','N'),
 (541676 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',542644,585661,'Y','N','Y','Y','N')
;
