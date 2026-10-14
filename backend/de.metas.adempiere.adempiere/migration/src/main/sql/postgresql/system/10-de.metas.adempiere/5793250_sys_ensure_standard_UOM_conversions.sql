-- 2026-03-09
-- Ensure standard UOM conversions exist between system-level measurement units (AD_Client_ID=0).
-- Some instances may be missing conversions (e.g., GRM↔KGM) which causes NoUOMConversionException
-- in OrderLineBL.setGrossWeightInKg and similar code paths.
--
-- Each INSERT checks both directions: if either A→B or B→A already exists, we skip the insert.
-- UOM IDs are looked up by X12DE355 code to be instance-independent.

-- ============================================================
-- Weight: GRM (Gramm) ↔ KGM (Kilogramm)
-- 1 GRM = 0.001 KGM
-- ============================================================
INSERT INTO C_UOM_Conversion (C_UOM_Conversion_ID, AD_Client_ID, AD_Org_ID, IsActive,
                              Created, CreatedBy, Updated, UpdatedBy,
                              C_UOM_ID, C_UOM_To_ID, MultiplyRate, DivideRate, M_Product_ID)
SELECT 540050, 0, 0, 'Y',
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       grm.C_UOM_ID, kgm.C_UOM_ID,
       0.001, 1000, NULL
FROM C_UOM grm, C_UOM kgm
WHERE grm.X12DE355 = 'GRM' AND grm.AD_Client_ID = 0
  AND kgm.X12DE355 = 'KGM' AND kgm.AD_Client_ID = 0
  AND NOT EXISTS (
    SELECT 1 FROM C_UOM_Conversion uc
    WHERE uc.AD_Client_ID = 0 AND uc.M_Product_ID IS NULL AND uc.IsActive = 'Y'
      AND (  (uc.C_UOM_ID = grm.C_UOM_ID AND uc.C_UOM_To_ID = kgm.C_UOM_ID)
          OR (uc.C_UOM_ID = kgm.C_UOM_ID AND uc.C_UOM_To_ID = grm.C_UOM_ID))
);

-- ============================================================
-- Weight: KGM (Kilogramm) ↔ TNE (Tonne)
-- 1 KGM = 0.001 TNE
-- ============================================================
INSERT INTO C_UOM_Conversion (C_UOM_Conversion_ID, AD_Client_ID, AD_Org_ID, IsActive,
                              Created, CreatedBy, Updated, UpdatedBy,
                              C_UOM_ID, C_UOM_To_ID, MultiplyRate, DivideRate, M_Product_ID)
SELECT 540051, 0, 0, 'Y',
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       kgm.C_UOM_ID, tne.C_UOM_ID,
       0.001, 1000, NULL
FROM C_UOM kgm, C_UOM tne
WHERE kgm.X12DE355 = 'KGM' AND kgm.AD_Client_ID = 0
  AND tne.X12DE355 = 'TNE' AND tne.AD_Client_ID = 0
  AND NOT EXISTS (
    SELECT 1 FROM C_UOM_Conversion uc
    WHERE uc.AD_Client_ID = 0 AND uc.M_Product_ID IS NULL AND uc.IsActive = 'Y'
      AND (  (uc.C_UOM_ID = kgm.C_UOM_ID AND uc.C_UOM_To_ID = tne.C_UOM_ID)
          OR (uc.C_UOM_ID = tne.C_UOM_ID AND uc.C_UOM_To_ID = kgm.C_UOM_ID))
);

-- ============================================================
-- Weight: GRM (Gramm) ↔ TNE (Tonne)
-- 1 GRM = 0.000001 TNE
-- ============================================================
INSERT INTO C_UOM_Conversion (C_UOM_Conversion_ID, AD_Client_ID, AD_Org_ID, IsActive,
                              Created, CreatedBy, Updated, UpdatedBy,
                              C_UOM_ID, C_UOM_To_ID, MultiplyRate, DivideRate, M_Product_ID)
SELECT 540052, 0, 0, 'Y',
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       grm.C_UOM_ID, tne.C_UOM_ID,
       0.000001, 1000000, NULL
FROM C_UOM grm, C_UOM tne
WHERE grm.X12DE355 = 'GRM' AND grm.AD_Client_ID = 0
  AND tne.X12DE355 = 'TNE' AND tne.AD_Client_ID = 0
  AND NOT EXISTS (
    SELECT 1 FROM C_UOM_Conversion uc
    WHERE uc.AD_Client_ID = 0 AND uc.M_Product_ID IS NULL AND uc.IsActive = 'Y'
      AND (  (uc.C_UOM_ID = grm.C_UOM_ID AND uc.C_UOM_To_ID = tne.C_UOM_ID)
          OR (uc.C_UOM_ID = tne.C_UOM_ID AND uc.C_UOM_To_ID = grm.C_UOM_ID))
);

-- ============================================================
-- Length: CM (Centimeter) ↔ mm (Millimeter)
-- 1 CM = 10 mm
-- ============================================================
INSERT INTO C_UOM_Conversion (C_UOM_Conversion_ID, AD_Client_ID, AD_Org_ID, IsActive,
                              Created, CreatedBy, Updated, UpdatedBy,
                              C_UOM_ID, C_UOM_To_ID, MultiplyRate, DivideRate, M_Product_ID)
SELECT 540053, 0, 0, 'Y',
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       cm.C_UOM_ID, mm.C_UOM_ID,
       10, 0.1, NULL
FROM C_UOM cm, C_UOM mm
WHERE cm.X12DE355 = 'CM' AND cm.AD_Client_ID = 0
  AND mm.X12DE355 = 'mm' AND mm.AD_Client_ID = 0
  AND NOT EXISTS (
    SELECT 1 FROM C_UOM_Conversion uc
    WHERE uc.AD_Client_ID = 0 AND uc.M_Product_ID IS NULL AND uc.IsActive = 'Y'
      AND (  (uc.C_UOM_ID = cm.C_UOM_ID AND uc.C_UOM_To_ID = mm.C_UOM_ID)
          OR (uc.C_UOM_ID = mm.C_UOM_ID AND uc.C_UOM_To_ID = cm.C_UOM_ID))
);

-- ============================================================
-- Time: MJ (Minute) ↔ HR (Stunde/Hour)
-- 1 MJ = 1/60 HR
-- ============================================================
INSERT INTO C_UOM_Conversion (C_UOM_Conversion_ID, AD_Client_ID, AD_Org_ID, IsActive,
                              Created, CreatedBy, Updated, UpdatedBy,
                              C_UOM_ID, C_UOM_To_ID, MultiplyRate, DivideRate, M_Product_ID)
SELECT 540054, 0, 0, 'Y',
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       mj.C_UOM_ID, hr.C_UOM_ID,
       0.016666666667, 60, NULL
FROM C_UOM mj, C_UOM hr
WHERE mj.X12DE355 = 'MJ' AND mj.AD_Client_ID = 0
  AND hr.X12DE355 = 'HR' AND hr.AD_Client_ID = 0
  AND NOT EXISTS (
    SELECT 1 FROM C_UOM_Conversion uc
    WHERE uc.AD_Client_ID = 0 AND uc.M_Product_ID IS NULL AND uc.IsActive = 'Y'
      AND (  (uc.C_UOM_ID = mj.C_UOM_ID AND uc.C_UOM_To_ID = hr.C_UOM_ID)
          OR (uc.C_UOM_ID = hr.C_UOM_ID AND uc.C_UOM_To_ID = mj.C_UOM_ID))
);

-- ============================================================
-- Time: HR (Stunde/Hour) ↔ DA (Tag/Day)
-- 1 HR = 1/24 DA
-- ============================================================
INSERT INTO C_UOM_Conversion (C_UOM_Conversion_ID, AD_Client_ID, AD_Org_ID, IsActive,
                              Created, CreatedBy, Updated, UpdatedBy,
                              C_UOM_ID, C_UOM_To_ID, MultiplyRate, DivideRate, M_Product_ID)
SELECT 540055, 0, 0, 'Y',
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       hr.C_UOM_ID, da.C_UOM_ID,
       0.041666666667, 24, NULL
FROM C_UOM hr, C_UOM da
WHERE hr.X12DE355 = 'HR' AND hr.AD_Client_ID = 0
  AND da.X12DE355 = 'DA' AND da.AD_Client_ID = 0
  AND NOT EXISTS (
    SELECT 1 FROM C_UOM_Conversion uc
    WHERE uc.AD_Client_ID = 0 AND uc.M_Product_ID IS NULL AND uc.IsActive = 'Y'
      AND (  (uc.C_UOM_ID = hr.C_UOM_ID AND uc.C_UOM_To_ID = da.C_UOM_ID)
          OR (uc.C_UOM_ID = da.C_UOM_ID AND uc.C_UOM_To_ID = hr.C_UOM_ID))
);

-- ============================================================
-- Count: PCE (Stück) ↔ TSTK (1000 Stk)
-- 1 PCE = 0.001 TSTK
-- ============================================================
INSERT INTO C_UOM_Conversion (C_UOM_Conversion_ID, AD_Client_ID, AD_Org_ID, IsActive,
                              Created, CreatedBy, Updated, UpdatedBy,
                              C_UOM_ID, C_UOM_To_ID, MultiplyRate, DivideRate, M_Product_ID)
SELECT 540056, 0, 0, 'Y',
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       pce.C_UOM_ID, tstk.C_UOM_ID,
       0.001, 1000, NULL
FROM C_UOM pce, C_UOM tstk
WHERE pce.X12DE355 = 'PCE' AND pce.AD_Client_ID = 0
  AND tstk.X12DE355 = 'TSTK' AND tstk.AD_Client_ID = 0
  AND NOT EXISTS (
    SELECT 1 FROM C_UOM_Conversion uc
    WHERE uc.AD_Client_ID = 0 AND uc.M_Product_ID IS NULL AND uc.IsActive = 'Y'
      AND (  (uc.C_UOM_ID = pce.C_UOM_ID AND uc.C_UOM_To_ID = tstk.C_UOM_ID)
          OR (uc.C_UOM_ID = tstk.C_UOM_ID AND uc.C_UOM_To_ID = pce.C_UOM_ID))
);

-- ============================================================
-- Count: COLI (Kollo) ↔ PCE (Stück)
-- 1 COLI = 1 PCE (default; product-specific overrides may differ)
-- ============================================================
INSERT INTO C_UOM_Conversion (C_UOM_Conversion_ID, AD_Client_ID, AD_Org_ID, IsActive,
                              Created, CreatedBy, Updated, UpdatedBy,
                              C_UOM_ID, C_UOM_To_ID, MultiplyRate, DivideRate, M_Product_ID)
SELECT 540057, 0, 0, 'Y',
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-09 17:30', 'YYYY-MM-DD HH24:MI'), 100,
       coli.C_UOM_ID, pce.C_UOM_ID,
       1, 1, NULL
FROM C_UOM coli, C_UOM pce
WHERE coli.X12DE355 = 'COLI' AND coli.AD_Client_ID = 0
  AND pce.X12DE355 = 'PCE' AND pce.AD_Client_ID = 0
  AND NOT EXISTS (
    SELECT 1 FROM C_UOM_Conversion uc
    WHERE uc.AD_Client_ID = 0 AND uc.M_Product_ID IS NULL AND uc.IsActive = 'Y'
      AND (  (uc.C_UOM_ID = coli.C_UOM_ID AND uc.C_UOM_To_ID = pce.C_UOM_ID)
          OR (uc.C_UOM_ID = pce.C_UOM_ID AND uc.C_UOM_To_ID = coli.C_UOM_ID))
);
