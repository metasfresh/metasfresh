-- Delivery Planning: the "Combine into one Delivery Instruction" action, on the Delivery Planning
-- window (541632). Where "Generate Delivery Instruction" (585176) creates one delivery instruction
-- PER selected planning, Combine creates exactly ONE for the whole selection, each planning keeping
-- its own allocation, shipping package and release number. Its IsComplete parameter defaults to 'N':
-- a combined instruction is assembled over days, so it stays a draft until the planner says it is
-- final.
-- The admissibility rejection is ONE message naming EVERY field the selection disagrees on ({0} is
-- the joined list of field labels), so the planner is not sent back for one field at a time. 545796
-- and 545797 are worded action-neutrally because the other delivery-instruction actions raise them
-- too -- do not reword them to mention combining.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Process       585653 (M_Delivery_Planning_CombineIntoDeliveryInstruction)
--   AD_Process_Para  543276 (its IsComplete parameter; reuses the existing AD_Element 2047 'Fertigstellen')
--   AD_Table_Process 541665 (placement on M_Delivery_Planning / window 541632)
--   AD_Message       545796 (IncompatibleSelection), 545797 (ClosedPlannings), 545798 (AlreadyOnDeliveryInstruction)
--   AD_Message       545799..545806 (the eight admissibility field labels, in enum declaration order)
--
-- EDITED AFTER FIRST APPLY (WebUI_ViewQuickAction_Default 'N' -> 'Y' on AD_Table_Process 541665).
-- The runner keys applied-ness on the file NAME with no checksum, so a stack that already ran the
-- earlier version silently keeps the old value. Reconcile such a stack with:
--   UPDATE AD_Table_Process SET WebUI_ViewQuickAction_Default='Y' WHERE AD_Table_Process_ID=541665;


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
VALUES (585653 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'M_Delivery_Planning_CombineIntoDeliveryInstruction',
        'Zu einer Lieferanweisung zusammenfassen',
        'Fasst die ausgewählten Lieferplanungen zu genau einer Lieferanweisung zusammen. Jede Lieferplanung behält ihre eigene Menge und ihre eigene Releasenummer.',
        'de.metas.deliveryplanning.process.M_Delivery_Planning_CombineIntoDeliveryInstruction',
        'Java', 3, 'D',
        'N', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y', 'Y',
        'N', 'Y', 'N',
        'json', 'Y', 'N', 'Y',
        'N')
;

-- seed AD_Process_Trl for every active system or base language, copying the German base text
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585653
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

UPDATE AD_Process_Trl
SET Name='Combine into one Delivery Instruction',
    Description='Combines the selected delivery plannings into exactly one delivery instruction. Each planning keeps its own quantity and its own release number.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-08-27 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585653 AND AD_Language='en_US'
;

UPDATE AD_Process_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585653 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ---------------------------------------------------------------------------------------------
-- 2) the IsComplete parameter - default 'N', so the combined instruction stays a draft
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, ColumnName, Name, SeqNo,
                             AD_Reference_ID, FieldLength, DefaultValue,
                             IsCentrallyMaintained, IsMandatory, IsRange, IsEncrypted, ShowInActiveValues, EntityType)
VALUES (543276 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        585653, 2047, 'IsComplete', 'Fertigstellen', 10,
        20, 0, 'N',
        'Y', 'N', 'N', 'N', 'N', 'D')
;

-- seed AD_Process_Para_Trl for every active system or base language ...
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543276
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ... and fill them from the parameter's element, which owns the label in every language
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2047)
;

-- ---------------------------------------------------------------------------------------------
-- 3) placement: the DEFAULT quick action on the Delivery Planning grid.
--    NOT a document action, unlike the other three and unlike every pre-existing action on this window:
--    checkAtLeastTwoSelected rejects anything but a multi-row selection, so on a single open record this
--    would sit permanently disabled. Combining one planning is what the 1:1 generate already does.
--    Default because combining is the normal way a planner turns a selection into a delivery
--    instruction; the 1:1 generate stays available but is no longer the first thing offered. With no
--    default flagged, the strip falls back to caption order, which is what put the 1:1 generate first.
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Table_ID, AD_Process_ID, AD_Window_ID, EntityType,
                              WebUI_ViewQuickAction, WebUI_ViewQuickAction_Default, WebUI_ViewAction, WebUI_DocumentAction, WebUI_IncludedTabTopAction)
VALUES (541665 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        542259, 585653, 541632, 'D',
        'Y', 'Y', 'Y', 'N', 'N')
;

-- ---------------------------------------------------------------------------------------------
-- 4) the rejection messages (base text German, English via the en_US translation)
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
VALUES
 (545796 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.IncompatibleSelection',
  'Diese Lieferplanungen können nicht zusammen auf einer Lieferanweisung stehen. Sie unterscheiden sich in: {0}.', 'E', 'D'),
 (545797 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.ClosedPlannings',
  'Geschlossene Lieferplanungen können nicht auf eine Lieferanweisung gesetzt werden: {0}.', 'E', 'D'),
 (545798 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:02', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.AlreadyOnDeliveryInstruction',
  'Diese Lieferplanungen sind bereits auf einer Lieferanweisung: {0}.', 'E', 'D'),
 (545799 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:03', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.Organisation', 'Sektion', 'I', 'D'),
 (545800 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.Direction', 'Richtung', 'I', 'D'),
 (545801 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:05', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.Forwarder', 'Spediteur', 'I', 'D'),
 (545802 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:06', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.Incoterms', 'Incoterms', 'I', 'D'),
 (545803 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:07', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.IncotermLocation', 'Incoterm Ort', 'I', 'D'),
 (545804 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:08', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:08', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.MeansOfTransportation', 'Transportmittel', 'I', 'D'),
 (545805 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:09', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.LoadingAddress', 'Verladeadresse', 'I', 'D'),
 (545806 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-27 09:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 09:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
  'de.metas.deliveryplanning.CombineIntoDeliveryInstruction.Field.DeliveryAddress', 'Lieferadresse', 'I', 'D')
;

-- error codes, so an API consumer can react to the three rejections without parsing text
UPDATE AD_Message SET ErrorCode='DP_COMBINE_INCOMPATIBLE_SELECTION', Updated=TO_TIMESTAMP('2026-08-27 09:04:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545796;
UPDATE AD_Message SET ErrorCode='DP_COMBINE_CLOSED_PLANNING', Updated=TO_TIMESTAMP('2026-08-27 09:04:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545797;
UPDATE AD_Message SET ErrorCode='DP_COMBINE_ALREADY_ON_INSTRUCTION', Updated=TO_TIMESTAMP('2026-08-27 09:04:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545798;

-- seed AD_Message_Trl for every active system or base language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID BETWEEN 545796 AND 545806
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- the English texts
UPDATE AD_Message_Trl SET MsgText='These delivery plannings cannot be put on one delivery instruction together. They differ in: {0}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545796;
UPDATE AD_Message_Trl SET MsgText='Closed delivery plannings cannot be put on a delivery instruction: {0}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545797;
UPDATE AD_Message_Trl SET MsgText='These delivery plannings are already on a delivery instruction: {0}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545798;
UPDATE AD_Message_Trl SET MsgText='Organisation', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545799;
UPDATE AD_Message_Trl SET MsgText='Direction', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545800;
UPDATE AD_Message_Trl SET MsgText='Forwarder', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545801;
UPDATE AD_Message_Trl SET MsgText='Incoterms', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545802;
UPDATE AD_Message_Trl SET MsgText='Incoterm Location', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545803;
UPDATE AD_Message_Trl SET MsgText='Means of Transportation', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545804;
UPDATE AD_Message_Trl SET MsgText='Loading Address', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545805;
UPDATE AD_Message_Trl SET MsgText='Delivery Address', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:05:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545806;

-- the German rows already carry their final text
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:06:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID BETWEEN 545796 AND 545806;
