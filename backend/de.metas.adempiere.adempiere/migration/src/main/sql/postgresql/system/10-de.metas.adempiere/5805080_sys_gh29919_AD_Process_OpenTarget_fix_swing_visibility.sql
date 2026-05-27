-- Migration: 5805080
-- Issue: https://github.com/metasfresh/me03/issues/29919
-- Purpose: Make AD_Process.OpenTarget visible in the AD_Process maintenance window.
--
-- AD_Process is AccessLevel='4' (SystemOnly) → its maintenance window is opened in the
-- legacy Swing client, which renders the form/grid from AD_Field (IsDisplayed/SeqNo/
-- IsDisplayedGrid/SeqNoGrid) and IGNORES the AD_UI_* layout tables.
-- PR-A (5804600) wired the AD_UI_Element (for the WebUI) but left AD_Field.SeqNo=0, so the
-- field had no position in the Swing form and never rendered. Its SeqNoGrid (360) also
-- collided with the sibling AD_RelationType_ID field.
--
-- Fix: give AD_Field 780486 (AD_Process.OpenTarget) a real Swing position, aligned with its
-- sibling AD_RelationType_ID (SeqNo=310): SeqNo=315 (next free slot in that cluster), and a
-- unique SeqNoGrid=370 (was colliding at 360, the previous max).

UPDATE AD_Field
SET SeqNo=315,
    SeqNoGrid=370,
    Updated=TO_TIMESTAMP('2026-05-27 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Field_ID=780486;
