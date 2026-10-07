-- gh29853 follow-up — make the GRAI → Packing-Instruction mapping (M_HU_PI_GRAI) show up as a
-- Related Document / zoom target on the Packing Instruction (M_HU_PI) record.
--
-- Root cause: AD_Column.IsExcludeFromZoomTargets has a physical DB default of 'Y', so the FK
-- column M_HU_PI_GRAI.M_HU_PI_ID created by 5805770 was excluded from zoom targets. The
-- related-documents view ad_table_related_windows_v requires
--   COALESCE(NULLIF(AD_Field.IsExcludeFromZoomTargets, ''), AD_Column.IsExcludeFromZoomTargets) = 'N'
-- The field (AD_Field 780647) is NULL, so the AD_Column value decides → set it to 'N'.
--
--   AD_Column 592690 = M_HU_PI_GRAI.M_HU_PI_ID (FK → M_HU_PI)

UPDATE AD_Column
SET    IsExcludeFromZoomTargets = 'N',
       Updated   = TO_TIMESTAMP('2026-06-07 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Column_ID = 592690
;
