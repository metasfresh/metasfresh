-- Delivery Planning: the "Move to another Delivery Instruction" action.
--
-- A move is its own action, separate from "Add to Delivery Instruction", because it changes the SOURCE
-- document as well as the target -- it deactivates the source allocation and its shipping package and
-- resets the planning's dates. The two are offered EXCLUSIVELY: Add is unavailable as soon as the
-- selection holds an allocated planning, Move as soon as it holds an unallocated one, so a planner is
-- offered exactly one of them for any selection and each description names the other.
--
-- Both are grid actions on the delivery-planning window (AD_Table_Process), not document actions: they
-- act on a selection of rows, which a single open record is not.
--
-- IDs allocated from idserver.metas.de on 2026-08-31:
--   AD_Process       585656 (M_Delivery_Planning_MoveToDeliveryInstruction)
--   AD_Process_Para  543280 (the hidden TransportDirection discriminator, SeqNo 10)
--   AD_Process_Para  543281 (the visible M_ShipperTransportation_ID target, SeqNo 20)
--   AD_Table_Process 541670 (placement on M_Delivery_Planning / window 541632)
--   AD_Message       545815 (AddToDeliveryInstruction.AlreadyOnDeliveryInstruction)
--
-- IDs reused, NOT newly created:
--   AD_Val_Rule  540796  M_ShipperTransportation_DraftDI_ForDirection -- the drafted instructions of
--                        the selection's direction, exactly the target list Move needs
--   AD_Element   585383  TransportDirection
--   AD_Element   540089  M_ShipperTransportation_ID -- its label reads "Transport Auftrag", not
--                        "Lieferanweisung"; reused anyway, as AD_Process_Para has no AD_Name_ID override
--   AD_Message   545807  OnCompletedInstruction -- the shared refusal for a completed instruction
--   AD_Message   545809  RemoveFromDeliveryInstruction.NotOnDeliveryInstruction -- states the STATE,
--                        not the action, so Move refuses an unallocated selection with it; only the
--                        Value still names Remove, and renaming it would break its AdMessageKey

-- ---------------------------------------------------------------------------------------------
-- 1) the process
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Process (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, Name, Description, Classname, Type, AccessLevel, EntityType,
                        IsReport, IsDirectPrint, IsBetaFunctionality, IsServerProcess, ShowHelp,
                        IsOneInstanceOnly, RefreshAllAfterExecution, AllowProcessRerun, IsUseBPartnerLanguage,
                        IsApplySecuritySettings, IsTranslateExcelHeaders, IsNotifyUserAfterExecution,
                        PostgrestResponseFormat, IsFormatExcelFile, IsLogWarning, IsIncludeCSVHeaderRow,
                        IsPDFA3Output)
VALUES (585656 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-31 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-31 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'M_Delivery_Planning_MoveToDeliveryInstruction',
        'Auf andere Lieferanweisung verschieben',
        'Nur für Lieferplanungen, die bereits auf einer Lieferanweisung im Entwurf sind - für noch nicht verplante nutzen Sie "Zu Lieferanweisung hinzufügen". Verschiebt sie auf eine andere Lieferanweisung im Entwurf: die bisherige Zuordnung samt Packstück wird aufgehoben, die Termine werden auf die Werte aus Auftrag und Lieferdisposition zurückgesetzt, und die Releasenummer wird von der Ziel-Lieferanweisung neu vergeben. Alles oder nichts: ist eine der ausgewählten Lieferplanungen auf einer fertiggestellten Lieferanweisung, wird die ganze Aktion abgelehnt.',
        'de.metas.deliveryplanning.process.M_Delivery_Planning_MoveToDeliveryInstruction',
        'Java', 3, 'D',
        'N', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y', 'Y',
        'N', 'Y', 'N',
        'json', 'Y', 'N', 'Y',
        'N')
;

-- seed AD_Process_Trl for every active system language, copying the German base text
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_ID=585656
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

UPDATE AD_Process_Trl
SET Name='Move to another Delivery Instruction',
    Description='Only for delivery plannings that are already on a draft delivery instruction - for plannings that are on none, use "Add to Delivery Instruction". Moves them onto another draft delivery instruction: the previous allocation and its shipping package are released, the dates return to those derived from the order and the delivery schedule, and the release number is re-stamped from the target delivery instruction. All or nothing: if any selected planning sits on a completed delivery instruction, the whole action is refused.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-08-31 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585656 AND AD_Language='en_US'
;

-- de_DE (the base language) and de_CH already carry their final German text.
UPDATE AD_Process_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-31 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585656 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ---------------------------------------------------------------------------------------------
-- 2) the direction discriminator - filled from the selection, kept out of the dialog
--
--    Not a choice but a property of the selection, so it is hidden with an always-false DisplayLogic
--    and filled by the process's IProcessDefaultParametersProvider. Its only reader is the value rule
--    of the target parameter below.
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, ColumnName, Name, SeqNo,
                             AD_Reference_ID, AD_Reference_Value_ID, FieldLength, DisplayLogic,
                             IsCentrallyMaintained, IsMandatory, IsRange, IsEncrypted, ShowInActiveValues, EntityType)
VALUES (543280 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-31 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-31 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        585656, 585383, 'TransportDirection', 'Richtung', 10,
        17, 541689, 250, '1=0',
        'Y', 'N', 'N', 'N', 'N', 'D')
;

-- ---------------------------------------------------------------------------------------------
-- 3) the target instruction - the only field the planner sees, narrowed by the shared value rule
--    to the DRAFTED delivery instructions of the selection's own direction, so the commonest wrong
--    pick is unofferable rather than rejected afterwards
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, ColumnName, Name, SeqNo,
                             AD_Reference_ID, AD_Val_Rule_ID, FieldLength,
                             IsCentrallyMaintained, IsMandatory, IsRange, IsEncrypted, ShowInActiveValues, EntityType)
VALUES (543281 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-31 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-31 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        585656, 540089, 'M_ShipperTransportation_ID', 'Transport Auftrag', 20,
        30, 540796, 0,
        'Y', 'Y', 'N', 'N', 'N', 'D')
;

-- seed AD_Process_Para_Trl for both parameters ...
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_Para_ID IN (543280, 543281)
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ... and fill them from the elements that own those labels in every language. AD_Process_Para is
-- element-driven, so a direct UPDATE of its Name/Description/Help would be overwritten by the next
-- element sync.
/* DDL */ SELECT update_process_para_translation_from_ad_element(585383)
;
/* DDL */ SELECT update_process_para_translation_from_ad_element(540089)
;

-- ---------------------------------------------------------------------------------------------
-- 4) placement: an action on the Delivery Planning grid, beside Add to and Remove from
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Table_ID, AD_Process_ID, AD_Window_ID, EntityType,
                              WebUI_ViewQuickAction, WebUI_ViewQuickAction_Default, WebUI_ViewAction, WebUI_DocumentAction, WebUI_IncludedTabTopAction)
VALUES (541670 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-31 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-31 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        542259, 585656, 541632, 'D',
        'Y', 'N', 'Y', 'N', 'N')
;

-- ---------------------------------------------------------------------------------------------
-- 5) the Add-to rejection: an already-allocated planning, pointed at Move
--
--    Distinct from 545798 (CombineIntoDeliveryInstruction.AlreadyOnDeliveryInstruction), which
--    refuses COMBINING such a planning into a NEW instruction: there the answer is to take it off
--    first, here it is to move it.
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
VALUES (545815 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-31 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-31 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.deliveryplanning.AddToDeliveryInstruction.AlreadyOnDeliveryInstruction',
        'Diese Lieferplanungen sind bereits auf einer Lieferanweisung. Nutzen Sie "Auf andere Lieferanweisung verschieben": {0}.', 'E', 'D')
;

-- error code, so an API consumer can react without parsing text
UPDATE AD_Message SET ErrorCode='DP_ADDTO_ALREADY_ALLOCATED',
                      Updated=TO_TIMESTAMP('2026-08-31 10:04:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545815
;

-- seed AD_Message_Trl for every active system language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545815
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='These delivery plannings are already on a delivery instruction. Use "Move to another Delivery Instruction": {0}.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-31 10:04:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545815
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-31 10:04:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID=545815
;
