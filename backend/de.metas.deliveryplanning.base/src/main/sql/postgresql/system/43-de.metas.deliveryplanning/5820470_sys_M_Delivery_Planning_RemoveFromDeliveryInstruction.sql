-- Delivery Planning: the "Remove from Delivery Instruction" action.
--
-- Takes the selected delivery plannings off the draft delivery instruction they are on: the
-- allocation and its shipping package are deleted, and the planning loses its release number and
-- its instruction reference, so it can be planned again. The instruction and its other plannings
-- are untouched -- which is why removal is not void-and-regenerate: a regenerated instruction is a
-- NEW document, so it would re-stamp the release number of every planning that did not move, and
-- hand the forwarder a new document number for a booking they already hold.
--
-- No parameters: which instruction a planning leaves is not a choice, it is the one it is on.
-- Refused when the instruction is COMPLETED (message 545807), but ALLOWED for a CLOSED planning:
-- closing a planning says "stop processing this", which is exactly the situation in which taking it
-- off the truck is the right correction.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Process       585655 (M_Delivery_Planning_RemoveFromDeliveryInstruction)
--   AD_Table_Process 541667 (placement on M_Delivery_Planning / window 541632)
--   AD_Message       545809 (NotOnDeliveryInstruction)
--
-- Reused, NOT newly created:
--   AD_Message   545807  OnCompletedInstruction

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
VALUES (585655 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'M_Delivery_Planning_RemoveFromDeliveryInstruction',
        'Von Lieferanweisung entfernen',
        'Nimmt die ausgewählten Lieferplanungen von der Lieferanweisung im Entwurf, auf der sie sind. Die Lieferanweisung und ihre übrigen Lieferplanungen bleiben unverändert; die entfernte Lieferplanung verliert ihre Releasenummer und kann neu geplant werden.',
        'de.metas.deliveryplanning.process.M_Delivery_Planning_RemoveFromDeliveryInstruction',
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
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_ID=585655
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

UPDATE AD_Process_Trl
SET Name='Remove from Delivery Instruction',
    Description='Takes the selected delivery plannings off the draft delivery instruction they are on. The delivery instruction and its other plannings are unaffected; the removed planning loses its release number and can be planned again.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-08-27 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585655 AND AD_Language='en_US'
;

UPDATE AD_Process_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585655 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ---------------------------------------------------------------------------------------------
-- 2) placement: an action on the Delivery Planning grid.
--    WebUI_ViewQuickAction='N', unlike Combine (585653) and Add to (585654): removal is the only
--    destructive one of the three -- it deletes the allocation and its shipping package and drops a
--    release number the forwarder may already hold -- so it stays off the one-click quick-action
--    toolbar and is reached through the actions menu instead.
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Table_ID, AD_Process_ID, AD_Window_ID, EntityType,
                              WebUI_ViewQuickAction, WebUI_ViewQuickAction_Default, WebUI_ViewAction, WebUI_DocumentAction, WebUI_IncludedTabTopAction)
VALUES (541667 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        542259, 585655, 541632, 'D',
        'N', 'N', 'Y', 'N', 'N')
;

-- ---------------------------------------------------------------------------------------------
-- 3) the rejection message (base text German, English via the en_US translation)
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType)
VALUES (545809 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-27 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-27 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.deliveryplanning.RemoveFromDeliveryInstruction.NotOnDeliveryInstruction',
        'Diese Lieferplanungen sind auf keiner Lieferanweisung: {0}.', 'E', 'D')
;

UPDATE AD_Message SET ErrorCode='DP_REMOVE_NOT_ON_INSTRUCTION', Updated=TO_TIMESTAMP('2026-08-27 11:03:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545809;

-- seed AD_Message_Trl for every active system language, copying the German base text
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545809
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='These delivery plannings are not on any delivery instruction: {0}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 11:04:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545809;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 11:04:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID=545809;
