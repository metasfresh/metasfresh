-- C_BPartner.DebtorId / C_BPartner.CreditorId must NOT be copied when a business partner is cloned.
-- Both carry a per-org unique index (c_bpartner_debtorid_uniqe / c_bpartner_creditorid_uniqe on
-- (<col>, ad_org_id) WHERE coalesce(<col>,0)>0). With CloningStrategy='XX' (Auto) the WebUI clone
-- (CopyTemplateService.extractValueToCopy_Autodetect) falls through to DIRECT_COPY for these
-- user-facing, non-calculated Integer columns, so the clone inherits the source's DebtorId/CreditorId
-- and the INSERT violates the unique index. Switch both to 'SK' (Skip) -- the purpose-built,
-- side-effect-free strategy meaning "leave this column unset on clone".

-- Column: C_BPartner.DebtorId (AD_Column_ID=531087)
-- 2026-09-28T10:00:00.000Z
UPDATE AD_Column SET CloningStrategy='SK', Updated=TO_TIMESTAMP('2026-09-28 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=531087
;

-- Column: C_BPartner.CreditorId (AD_Column_ID=531088)
-- 2026-09-28T10:00:01.000Z
UPDATE AD_Column SET CloningStrategy='SK', Updated=TO_TIMESTAMP('2026-09-28 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=531088
;
