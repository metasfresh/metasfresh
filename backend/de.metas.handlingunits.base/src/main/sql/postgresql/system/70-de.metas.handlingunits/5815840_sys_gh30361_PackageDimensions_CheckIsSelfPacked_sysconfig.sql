-- Insert AD_SysConfig row for the IsSelfPacked dimension gate.
-- Default Value='N' → gate is OFF → flag-independent behaviour (new default).
-- Set to 'Y' on a specific instance to restore the legacy gate
-- (non-self-packed products → PackageDimensions.UNSPECIFIED).
--
-- ID allocated from idserver.metas.de on 2026-07-23:
--   AD_SysConfig 541837

INSERT INTO AD_SysConfig
    (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID,
     Name, Value, Description,
     EntityType, ConfigurationLevel,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (541837 /*From ID Server*/, 0, 0,
     'de.metas.handlingunits.PackageDimensions.CheckIsSelfPacked',
     'N',
     'When Y, the legacy IsSelfPacked gate is active: non-self-packed products return PackageDimensions.UNSPECIFIED instead of using their named dimensions. Default N = flag-independent behaviour.',
     'de.metas.handlingunits', 'S',
     'Y',
     TO_TIMESTAMP('2026-07-23 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-23 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
