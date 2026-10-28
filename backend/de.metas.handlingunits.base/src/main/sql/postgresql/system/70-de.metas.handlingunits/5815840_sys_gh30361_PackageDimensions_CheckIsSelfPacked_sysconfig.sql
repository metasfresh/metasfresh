-- AD_SysConfig row controlling the IsSelfPacked dimension gate.
-- Value='N' → product dimensions are used regardless of the self-packed flag.
-- Value='Y' → a non-self-packed product yields PackageDimensions.UNSPECIFIED.
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
     'Controls the IsSelfPacked dimension gate. When Y, a non-self-packed product yields PackageDimensions.UNSPECIFIED; when N, product dimensions are used regardless of the self-packed flag.',
     'de.metas.handlingunits', 'S',
     'Y',
     TO_TIMESTAMP('2026-07-23 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-23 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
