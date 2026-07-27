-- AD_Message for the DD_Order mobileUI distribution completion gate: shown to the mover when he taps
-- Complete while a job line's planned quantity has not been fully moved. A distribution order can serve
-- several deliveries at once, so completing it short would dispose of the document while other
-- contributors still have unmet demand.
--
-- Must be an AD_Message, not a raw AdempiereException string: AdempiereException(String) sets
-- userValidationError=false unless the text contains '@', and the mobile-webui toast then renders a
-- generic "Internal Error" instead of the message. The outstanding quantity would never reach the mover.
--
-- AD_Message base text is in German (DE). Rationale: most users are German-speakers; if a translation
-- is missing the fallback (AD_Message.MsgText) shows German. en_US translation is provided below.
-- {0} is the outstanding quantity per line, as "<qty> <uom> <product>" joined by ", "
-- (filled in by AdempiereException(MSG_OutstandingQtyToMove, outstanding)).
--
-- CONVERGENT BY VALUE, ON PURPOSE. An earlier script (5816220, since removed from this branch) already
-- created this very message under AD_Message_ID 545791 on every stack the branch was rolled out to --
-- verified present on the local deep_tundra_uat_6 DB. AD_Message carries an unconditional UNIQUE index
-- on Value (ad_message_value), so a plain INSERT of a new AD_Message_ID with the same Value aborts the
-- whole migration batch on exactly those stacks. Hence: insert the fresh ID only when no row holds the
-- Value yet, and key every following statement on the Value, so a stack that already carries 545791 and
-- a fresh stack both end up with the same message content.

-- =============================================================================
-- MobileUI_Distribution_OutstandingQtyToMove  (AD_Message_ID 545794, ErrorCode OutstandingQtyToMove)
-- =============================================================================

-- 2026-07-27T00:00:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
SELECT 0,545794 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abschluss nicht möglich, es ist noch Menge zu bewegen: {0}','E',TO_TIMESTAMP('2026-07-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MobileUI_Distribution_OutstandingQtyToMove'
WHERE NOT EXISTS (SELECT 1 FROM AD_Message WHERE Value='MobileUI_Distribution_OutstandingQtyToMove')
;

-- Converge the message content and reactivate it, whichever AD_Message_ID carries the Value.
-- AD_Message.ErrorCode is varchar(40); use a short form (the full key lives in AD_Message.Value).
-- 2026-07-27T00:00:01.000Z
UPDATE AD_Message SET MsgText='Abschluss nicht möglich, es ist noch Menge zu bewegen: {0}', MsgType='E', ErrorCode='OutstandingQtyToMove', IsActive='Y', Updated=TO_TIMESTAMP('2026-07-27 00:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE Value='MobileUI_Distribution_OutstandingQtyToMove'
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-07-27T00:00:02.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.Value='MobileUI_Distribution_OutstandingQtyToMove'
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-07-27T00:00:03.000Z
UPDATE AD_Message_Trl SET MsgText='Cannot complete, there is still quantity to be moved: {0}',IsTranslated='Y',IsActive='Y',Updated=TO_TIMESTAMP('2026-07-27 00:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID IN (SELECT AD_Message_ID FROM AD_Message WHERE Value='MobileUI_Distribution_OutstandingQtyToMove')
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-07-27T00:00:04.000Z
UPDATE AD_Message_Trl SET MsgText='Abschluss nicht möglich, es ist noch Menge zu bewegen: {0}',IsTranslated='Y',IsActive='Y',Updated=TO_TIMESTAMP('2026-07-27 00:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID IN (SELECT AD_Message_ID FROM AD_Message WHERE Value='MobileUI_Distribution_OutstandingQtyToMove')
;

-- 2026-07-27T00:00:05.000Z
UPDATE AD_Message_Trl SET MsgText='Abschluss nicht möglich, es ist noch Menge zu bewegen: {0}',IsTranslated='Y',IsActive='Y',Updated=TO_TIMESTAMP('2026-07-27 00:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID IN (SELECT AD_Message_ID FROM AD_Message WHERE Value='MobileUI_Distribution_OutstandingQtyToMove')
;
