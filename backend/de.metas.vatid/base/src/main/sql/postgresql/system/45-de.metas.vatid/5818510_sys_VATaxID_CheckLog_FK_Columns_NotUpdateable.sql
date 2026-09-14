-- VAT-ID online check: VATaxID_CheckLog is append-only evidence with exactly one allowed update
-- (VATaxIDCheckRepository#completeCheck, which sets only VATaxIDStatus / ResponseDate /
-- RequestIdentifier / RawResponse). 5818450 already set IsUpdateable='N' on AD_Org_ID (593166)
-- and IsActive (593167) to match that guarantee; C_BPartner_ID (593172) and
-- C_BPartner_Location_ID (593173) were never touched and remained 'Y'.
--
-- Verified in code: both columns are written exactly once, by writeRequestSent's INSERT, and are
-- never referenced by completeCheck's UPDATE — so locking them against updates does not affect
-- the completion path. Defense-in-depth only: no live UI/REST write path exists today (the only
-- tab for this table is fully read-only), but a future tab added for this table without
-- independently re-locking would otherwise leave exactly these two columns writable while every
-- other business column on the table is not.
UPDATE AD_Column
SET IsUpdateable = 'N',
    Updated = TO_TIMESTAMP('2026-08-12 13:05:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID IN (593172, 593173);
