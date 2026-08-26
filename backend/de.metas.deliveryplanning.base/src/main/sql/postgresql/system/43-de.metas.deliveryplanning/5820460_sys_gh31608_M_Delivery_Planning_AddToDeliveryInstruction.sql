-- Delivery Planning: the "Add to Delivery Instruction" action.
--
-- Puts the selected delivery plannings on an EXISTING draft delivery instruction, taking each off
-- whatever draft instruction it was on before. Where "Combine into one Delivery Instruction"
-- creates a new document, this one joins an existing one, so the whole action is a single move:
-- the source allocation and its shipping package are deleted, new ones are created on the target,
-- and the planning's release number is re-stamped from the target - all in one transaction.
--
-- The dialog has exactly ONE visible field, the target instruction, and its list is narrowed by a
-- new value rule to the DRAFTED delivery instructions whose direction matches the selection's. The
-- direction is not something the planner chooses - it is a property of the selection - so it is
-- carried in by a second parameter that is kept out of the dialog with an always-false DisplayLogic
-- (the established form on this table: AD_Process_Para 542571 and 541949 both use "1=0") and filled
-- by the process's IProcessDefaultParametersProvider from the selected rows. Same layering the
-- picking four-eyes review already uses with M_ShipperTransportation_Open_ForShipper: the value
-- rule makes a wrong target unofferable, the precondition explains a wrong selection, and the
-- service asserts for callers that bypass the picker.
--
-- Rejection messages: the two rules a planner can provoke that Combine does not already cover -
-- a selected planning sitting on a COMPLETED instruction (which forbids the move outright, rather
-- than moving the rest), and a target that is no longer a draft. The closed-planning rejection
-- reuses Combine's message 545797, whose wording is generalised here from "cannot be combined" to
-- "cannot be put on a delivery instruction" so that it reads correctly for both actions instead of
-- a second, near-identical message being minted.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Process       585654 (M_Delivery_Planning_AddToDeliveryInstruction)
--   AD_Process_Para  543277 (the hidden M_Delivery_Planning_Type discriminator, SeqNo 10)
--   AD_Process_Para  543278 (the visible M_ShipperTransportation_ID target, SeqNo 20)
--   AD_Table_Process 541666 (placement on M_Delivery_Planning / window 541632)
--   AD_Val_Rule      540796 (M_ShipperTransportation_DraftDI_ForDirection)
--   AD_Message       545807 (OnCompletedInstruction - shared with "Remove from Delivery Instruction")
--   AD_Message       545808 (TargetNotDraft)
--
-- Reused, NOT newly created:
--   AD_Element   581679  M_Delivery_Planning_Type - the direction, same element the planning and the
--                        instruction both carry, so the comparison is a plain equality
--   AD_Element   540089  M_ShipperTransportation_ID - the label reads "Transport Auftrag" rather than
--                        "Lieferanweisung"; reused anyway, because AD_Process_Para has no per-parameter
--                        AD_Name_ID override and a second element for the same ColumnName would be a
--                        duplicate. The two documents sharing one table is what makes the label read
--                        oddly, and that is not this script's to fix.
--   AD_Reference 541689  the three-valued direction list (Incoming / Outgoing / Dropship)
--   AD_Message   545797  ClosedPlannings (text generalised below)
--
-- DB lookups (deep_tundra_uat_2, port 21632):
--   AD_Table_ID of M_Delivery_Planning                          -> 542259
--   AD_Window_ID of the Delivery Planning window                -> 541632
--   C_DocType of the delivery instruction: 541085, DocSubType 'DI', DocBaseType 'MST'
--   M_ShipperTransportation.DocStatus                           -> NOT NULL, DEFAULT 'DR'

-- ---------------------------------------------------------------------------------------------
-- 1) the value rule: drafted delivery instructions of ONE direction
--
--    DocStatus='DR' rather than Processed='N': what may be added to is a DRAFT, and 'DR' says that
--    precisely, where Processed='N' would also admit an in-progress document.
--    The DocSubType condition is what keeps transport orders - the other document on this table -
--    out of a delivery-planning picker.
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         Name, Description, Type, Code, EntityType)
VALUES (540796 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'M_ShipperTransportation_DraftDI_ForDirection',
        'Delivery instructions still in draft, of the direction given by @M_Delivery_Planning_Type@.',
        'S',
        'M_ShipperTransportation.DocStatus = ''DR''
AND EXISTS (SELECT 1
            FROM C_DocType dt
            WHERE dt.C_DocType_ID = M_ShipperTransportation.C_DocType_ID
              AND dt.DocSubType = ''DI'')
AND M_ShipperTransportation.M_Delivery_Planning_Type = ''@M_Delivery_Planning_Type/-@''',
        'D')
;

-- ---------------------------------------------------------------------------------------------
-- 2) the process
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Process (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, Name, Description, Classname, Type, AccessLevel, EntityType,
                        IsReport, IsDirectPrint, IsBetaFunctionality, IsServerProcess, ShowHelp,
                        IsOneInstanceOnly, RefreshAllAfterExecution, AllowProcessRerun, IsUseBPartnerLanguage,
                        IsApplySecuritySettings, IsTranslateExcelHeaders, IsNotifyUserAfterExecution,
                        PostgrestResponseFormat, IsFormatExcelFile, IsLogWarning, IsIncludeCSVHeaderRow,
                        IsPDFA3Output, IsPreventConcurrentExecution)
VALUES (585654 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'M_Delivery_Planning_AddToDeliveryInstruction',
        'Zu Lieferanweisung hinzufügen',
        'Setzt die ausgewählten Lieferplanungen auf eine bestehende Lieferanweisung im Entwurf und nimmt sie von der Lieferanweisung, auf der sie bisher waren. Die Releasenummer wird von der Ziel-Lieferanweisung neu vergeben.',
        'de.metas.deliveryplanning.process.M_Delivery_Planning_AddToDeliveryInstruction',
        'Java', 3, 'D',
        'N', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y', 'Y',
        'N', 'Y', 'N',
        'json', 'Y', 'N', 'Y',
        'N', 'N')
;

-- seed AD_Process_Trl for every active system language, copying the German base text
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_ID=585654
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

UPDATE AD_Process_Trl
SET Name='Add to Delivery Instruction',
    Description='Puts the selected delivery plannings on an existing draft delivery instruction and takes them off the one they were on before. The release number is re-stamped from the target delivery instruction.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-08-27 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585654 AND AD_Language='en_US'
;

UPDATE AD_Process_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 10:01:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585654 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ---------------------------------------------------------------------------------------------
-- 3) the direction discriminator - filled from the selection, kept out of the dialog
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, ColumnName, Name, SeqNo,
                             AD_Reference_ID, AD_Reference_Value_ID, FieldLength, DisplayLogic,
                             IsCentrallyMaintained, IsMandatory, IsRange, IsEncrypted, ShowInActiveValues, EntityType)
VALUES (543277 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        585654, 581679, 'M_Delivery_Planning_Type', 'Lieferplanung Art', 10,
        17, 541689, 250, '1=0',
        'Y', 'N', 'N', 'N', 'N', 'D')
;

-- ---------------------------------------------------------------------------------------------
-- 4) the target instruction - the only field the planner sees, narrowed by the value rule above
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, ColumnName, Name, SeqNo,
                             AD_Reference_ID, AD_Val_Rule_ID, FieldLength,
                             IsCentrallyMaintained, IsMandatory, IsRange, IsEncrypted, ShowInActiveValues, EntityType)
VALUES (543278 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        585654, 540089, 'M_ShipperTransportation_ID', 'Transport Auftrag', 20,
        30, 540796, 0,
        'Y', 'Y', 'N', 'N', 'N', 'D')
;

-- seed AD_Process_Para_Trl for both parameters ...
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_Para_ID IN (543277, 543278)
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ... and fill them from the elements that own those labels in every language
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581679)
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(540089)
;

-- ---------------------------------------------------------------------------------------------
-- 5) placement: an action on the Delivery Planning grid, beside Combine.
--    NOT a document action: it acts on a selection of rows, which a single open record is not.
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Table_ID, AD_Process_ID, AD_Window_ID, EntityType,
                              WebUI_ViewQuickAction, WebUI_ViewQuickAction_Default, WebUI_ViewAction, WebUI_DocumentAction, WebUI_IncludedTabTopAction)
VALUES (541666 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        542259, 585654, 541632, 'D',
        'Y', 'N', 'Y', 'N', 'N')
;

-- ---------------------------------------------------------------------------------------------
-- 6) the two new rejection messages (base text German, English via the en_US translation)
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
VALUES
 (545807 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.DeliveryInstruction.OnCompletedInstruction',
  'Diese Lieferplanungen sind auf einer fertiggestellten Lieferanweisung und können weder verschoben noch entfernt werden: {0}.', 'E', 'D'),
 (545808 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 10:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 10:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.AddToDeliveryInstruction.TargetNotDraft',
  'Die gewählte Lieferanweisung ist kein Entwurf mehr.', 'E', 'D')
;

-- error codes, so an API consumer can react without parsing text
UPDATE AD_Message SET ErrorCode='DP_ON_COMPLETED_INSTRUCTION', Updated=TO_TIMESTAMP('2026-08-27 10:06:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545807;
UPDATE AD_Message SET ErrorCode='DP_ADDTO_TARGET_NOT_DRAFT', Updated=TO_TIMESTAMP('2026-08-27 10:06:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545808;

-- seed AD_Message_Trl for every active system language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID IN (545807, 545808)
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='These delivery plannings are on a completed delivery instruction and can neither be moved nor removed: {0}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 10:07:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545807;
UPDATE AD_Message_Trl SET MsgText='The chosen delivery instruction is no longer a draft.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 10:07:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545808;

-- the German rows already carry their final text
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 10:07:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID IN (545807, 545808);

-- ---------------------------------------------------------------------------------------------
-- 7) generalise the closed-planning rejection, now that two actions raise it
-- ---------------------------------------------------------------------------------------------
UPDATE AD_Message
SET MsgText='Geschlossene Lieferplanungen können nicht auf eine Lieferanweisung gesetzt werden: {0}.',
    Updated=TO_TIMESTAMP('2026-08-27 10:08:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545797
;

UPDATE AD_Message_Trl
SET MsgText='Geschlossene Lieferplanungen können nicht auf eine Lieferanweisung gesetzt werden: {0}.',
    Updated=TO_TIMESTAMP('2026-08-27 10:08:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545797 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Message_Trl
SET MsgText='Closed delivery plannings cannot be put on a delivery instruction: {0}.',
    Updated=TO_TIMESTAMP('2026-08-27 10:08:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545797 AND AD_Language='en_US'
;

-- and the languages that only ever held the German base text as a seed: they have to follow it, or a
-- session in one of them keeps rendering the old sentence for good
UPDATE AD_Message_Trl trl
SET MsgText=(SELECT m.MsgText FROM AD_Message m WHERE m.AD_Message_ID=trl.AD_Message_ID),
    Updated=TO_TIMESTAMP('2026-08-27 10:08:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE trl.AD_Message_ID=545797 AND trl.IsTranslated='N'
;
