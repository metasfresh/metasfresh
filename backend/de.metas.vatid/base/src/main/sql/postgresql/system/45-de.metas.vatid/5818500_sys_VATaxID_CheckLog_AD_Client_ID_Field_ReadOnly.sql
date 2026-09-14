-- VAT-ID online check: the check-log tab (549365) was locked read-only by 5818450, which set
-- IsReadOnly='Y' on every non-system field on the tab except AD_Client_ID (781971) — an oversight
-- relative to precedent tabs 488 (AD_ChangeLog) and 777 (AD_Issue), which both set their
-- AD_Client_ID field to IsReadOnly='Y' for the same "lock every field individually, not just the
-- tab" consistency reasoning 5818450 used for the other 16 fields on this tab.
--
-- No behaviour change: AD_Tab.IsReadOnly='Y' on 549365 already forces every field on the tab
-- read-only in the WebUI regardless of its own flag, so this closes a latent metadata
-- inconsistency, not a functional gap.
UPDATE AD_Field
SET IsReadOnly = 'Y',
    Updated = TO_TIMESTAMP('2026-08-12 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781971;
