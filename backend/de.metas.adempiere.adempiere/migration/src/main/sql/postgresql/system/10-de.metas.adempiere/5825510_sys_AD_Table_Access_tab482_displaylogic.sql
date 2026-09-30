-- 2026-09-21
-- Window 268 / tab 482: drop the DisplayLogic that referenced AccessTypeRule.
--
-- Companion to 5825500, which removed the AccessTypeRule column and field 8349. Three fields on
-- tab 482 gated their display on the rule type of the row being edited:
--
--   6714 Schreibgeschützt        @AccessTypeRule@=A
--   8320 Kann Berichte erstellen @AccessTypeRule@=R
--   8321 Kann exportieren        @AccessTypeRule@=E
--
-- With AccessTypeRule gone, @AccessTypeRule@ names a field that no longer exists on the tab. That
-- is a window-descriptor defect, not merely dead configuration: the WebUI window health check
-- reports an unresolvable context variable per expression, and the three fields would never render
-- again, because the variable resolves to nothing and nothing equals 'A' / 'R' / 'E'.
--
-- One row now carries every flag, so all three are always relevant: the logic is cleared rather
-- than rewritten.
--
-- Separate from 5825500 because that script was already applied when this was found, and a
-- migration is applied once — the correction has to arrive under its own sequence number.

UPDATE AD_Field
   SET DisplayLogic = NULL,
       Updated      = TO_TIMESTAMP('2026-09-21 12:00:01','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Field_ID = 6714
;

UPDATE AD_Field
   SET DisplayLogic = NULL,
       Updated      = TO_TIMESTAMP('2026-09-21 12:00:02','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Field_ID = 8320
;

UPDATE AD_Field
   SET DisplayLogic = NULL,
       Updated      = TO_TIMESTAMP('2026-09-21 12:00:03','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Field_ID = 8321
;
