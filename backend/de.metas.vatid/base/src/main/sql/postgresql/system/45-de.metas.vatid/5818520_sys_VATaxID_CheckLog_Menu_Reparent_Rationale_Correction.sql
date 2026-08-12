-- Correction of record for 5818480_sys_VATaxID_CheckLog_Menu_Reparent.sql's rationale comment.
-- That script is already applied and is immutable (a wrong comment is not the kind of
-- apply-blocking bug that permits editing an integrated script), so this documents the
-- correction alongside it instead of altering it.
--
-- 5818480's comment cites AD_Replication_Log and AD_WF_EventAudit as examples of a STANDALONE
-- read-only log/audit window (its own menu entry, not a tab embedded in a config window).
-- Verified live: both are the opposite of what is claimed there —
--   * AD_Replication_Log is a tablevel=2 tab embedded inside window 284 ("Replizierung"); there
--     is no standalone AD_Replication_Log window. The window "Replizierung" (not the log table)
--     is what has the direct menu entry, under "Replication Data" (53098).
--   * AD_WF_EventAudit is a tablevel=1 tab embedded inside window 297 ("Workflow-Prozess");
--     likewise no standalone window exists — "Workflow-Prozess" has the direct menu entry, under
--     "Workflow" (501).
-- Both are exactly the "tab embedded in a parent window" pattern that comment explicitly
-- contrasts itself against.
--
-- This does NOT change the placement decision 5818480 made: its other five cross-domain
-- precedents (PayPal_Log, M_Securpharm_Log, C_Doc_Outbound_Log, AD_ChangeLog, AD_Issue) plus the
-- two same-domain Finanzen precedents (nodes 540806, 542238) are genuine, standalone,
-- top-level-tab windows with their own direct menu entry, and are sufficient on their own to
-- support moving VATaxID_CheckLog to a direct child of Finanzen.
--
-- No AD data is changed by this script. The guard below only verifies the reparent 5818480
-- performed is still in place, so this correction can never silently drift from the row it
-- describes.
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM AD_TreeNodeMM
        WHERE AD_Tree_ID = 10 AND Node_ID = 542357 AND Parent_ID = 1000015 AND SeqNo = 54
    ) THEN
        RAISE EXCEPTION 'VATaxID_CheckLog menu node (542357) no longer matches the state set by 5818480 - this correction comment may be orphaned from the row it describes.';
    END IF;
END $$;
