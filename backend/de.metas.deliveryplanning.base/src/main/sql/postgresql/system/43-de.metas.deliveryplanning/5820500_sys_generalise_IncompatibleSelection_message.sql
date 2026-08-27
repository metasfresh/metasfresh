-- Delivery Planning: generalise the incompatible-selection rejection, now that TWO actions raise it.
--
-- "Add to Delivery Instruction" now enforces the same admissibility rule "Combine into one Delivery
-- Instruction" does, but over the selection TOGETHER WITH the plannings the target instruction already
-- holds - because that is what the document ends up holding. Without it a planner could assemble, one
-- add-to at a time, an instruction whose header names one forwarder while its cargo belongs to another.
--
-- That makes message 545796 the rejection of both actions, and its wording no longer fits: it speaks of
-- "the SELECTED delivery plannings" being "combined", while an add-to rejection is very often about a
-- SINGLE selected planning differing from what is already on the target - for which "they differ in"
-- reads as nonsense. Generalised here rather than answered with a second, near-identical message, exactly
-- as 5820460 already did for the closed-planning rejection 545797.
--
-- The AD_Message.Value and the ErrorCode are deliberately left alone: the Value is the key the Java side
-- resolves (DeliveryPlanningService.MSG_M_Delivery_Planning_IncompatibleSelection) and the ErrorCode is
-- what an API consumer already keys on. Renaming either would break a caller to fix a sentence - and
-- 5820460 kept 545797's DP_COMBINE_CLOSED_PLANNING for the same reason.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_MigrationScript  582050 -> this script's 5820500 prefix
--
-- Reused, NOT newly created:
--   AD_Message  545796  CombineIntoDeliveryInstruction.IncompatibleSelection (created by 5820450)

-- ---------------------------------------------------------------------------------------------
-- the German base text, which is the fallback every language falls back to
-- ---------------------------------------------------------------------------------------------
UPDATE AD_Message
SET MsgText='Diese Lieferplanungen können nicht zusammen auf einer Lieferanweisung stehen. Sie unterscheiden sich in: {0}.',
    Updated=TO_TIMESTAMP('2026-08-27 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545796
;

UPDATE AD_Message_Trl
SET MsgText='Diese Lieferplanungen können nicht zusammen auf einer Lieferanweisung stehen. Sie unterscheiden sich in: {0}.',
    Updated=TO_TIMESTAMP('2026-08-27 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545796 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Message_Trl
SET MsgText='These delivery plannings cannot be put on one delivery instruction together. They differ in: {0}.',
    Updated=TO_TIMESTAMP('2026-08-27 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545796 AND AD_Language='en_US'
;

-- and the languages that only ever held the German base text as a seed: they have to follow it, or a
-- session in one of them keeps rendering the old sentence for good
UPDATE AD_Message_Trl trl
SET MsgText=(SELECT m.MsgText FROM AD_Message m WHERE m.AD_Message_ID=trl.AD_Message_ID),
    Updated=TO_TIMESTAMP('2026-08-27 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE trl.AD_Message_ID=545796 AND trl.IsTranslated='N'
;
