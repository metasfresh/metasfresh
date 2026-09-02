-- Ship 3 inactive TU packing instructions, one per dimension-calculation mode (Strapping/Repacking/Nesting).
-- These are INACTIVE seed records; a separate customer migration activates and configures them per deployment.
-- PackageDimensionCalcMethod codes: S=Strapping, R=Repacking, N=Nesting (AD_Reference 542122).
--
-- IDs allocated from idserver.metas.de on 2026-07-22:
--   M_HU_PI               540013 (Strapping), 540014 (Repacking), 540015 (Nesting)
--   M_HU_PI_Version        540013 (Strapping), 540014 (Repacking), 540015 (Nesting)
--   M_HU_PI_Item           540018 (Strapping), 540019 (Repacking), 540020 (Nesting)
--   M_HU_PI_Item_Product   540023 (Strapping), 540021 (Repacking), 540022 (Nesting)

-- ============================================================
-- STRAPPING mode (S)
-- ============================================================

INSERT INTO M_HU_PI
    (AD_Client_ID, AD_Org_ID, M_HU_PI_ID, Name, Description,
     IsActive, IsDefaultLU, IsDefaultForPicking,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540013 /*From ID Server*/, 'Maßberechnung - Bändern',
     'TU-Packvorschrift für die Maßberechnungsmethode Bändern.',
     'N', 'N', 'N',
     TO_TIMESTAMP('2026-07-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

INSERT INTO M_HU_PI_Version
    (AD_Client_ID, AD_Org_ID, M_HU_PI_Version_ID, M_HU_PI_ID, Name,
     HU_UnitType, PackageDimensionCalcMethod, IsCurrent,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540013 /*From ID Server*/, 540013 /*M_HU_PI Bändern*/,
     'Maßberechnung - Bändern',
     'TU', 'S', 'N',
     'N',
     TO_TIMESTAMP('2026-07-22 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

INSERT INTO M_HU_PI_Item
    (AD_Client_ID, AD_Org_ID, M_HU_PI_Item_ID, M_HU_PI_Version_ID,
     ItemType, Qty,
     IsActive, IsAllowDirectlyOnPM,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540018 /*From ID Server*/, 540013 /*PIV Strapping*/,
     'MI', 0,
     'N', 'N',
     TO_TIMESTAMP('2026-07-22 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

INSERT INTO M_HU_PI_Item_Product
    (AD_Client_ID, AD_Org_ID, M_HU_PI_Item_Product_ID, M_HU_PI_Item_ID,
     M_Product_ID, Qty, IsAllowAnyProduct, IsInfiniteCapacity, IsDefaultForProduct,
     IsOrderInTUUOMWhenMatched,
     Name, Description,
     ValidFrom,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540023 /*From ID Server*/, 540018 /*Item Bändern*/,
     NULL, 0, 'Y', 'Y', 'N',
     'N',
     'Maßberechnung - Bändern', NULL,
     TO_TIMESTAMP('2001-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
     'N',
     TO_TIMESTAMP('2026-07-22 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

-- ============================================================
-- REPACKING mode (R)
-- ============================================================

INSERT INTO M_HU_PI
    (AD_Client_ID, AD_Org_ID, M_HU_PI_ID, Name, Description,
     IsActive, IsDefaultLU, IsDefaultForPicking,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540014 /*From ID Server*/, 'Maßberechnung - Umverpacken',
     'TU-Packvorschrift für die Maßberechnungsmethode Umverpacken.',
     'N', 'N', 'N',
     TO_TIMESTAMP('2026-07-22 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

INSERT INTO M_HU_PI_Version
    (AD_Client_ID, AD_Org_ID, M_HU_PI_Version_ID, M_HU_PI_ID, Name,
     HU_UnitType, PackageDimensionCalcMethod, IsCurrent,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540014 /*From ID Server*/, 540014 /*M_HU_PI Umverpacken*/,
     'Maßberechnung - Umverpacken',
     'TU', 'R', 'N',
     'N',
     TO_TIMESTAMP('2026-07-22 10:00:05', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:05', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

INSERT INTO M_HU_PI_Item
    (AD_Client_ID, AD_Org_ID, M_HU_PI_Item_ID, M_HU_PI_Version_ID,
     ItemType, Qty,
     IsActive, IsAllowDirectlyOnPM,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540019 /*From ID Server*/, 540014 /*PIV Repacking*/,
     'MI', 0,
     'N', 'N',
     TO_TIMESTAMP('2026-07-22 10:00:06', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:06', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

INSERT INTO M_HU_PI_Item_Product
    (AD_Client_ID, AD_Org_ID, M_HU_PI_Item_Product_ID, M_HU_PI_Item_ID,
     M_Product_ID, Qty, IsAllowAnyProduct, IsInfiniteCapacity, IsDefaultForProduct,
     IsOrderInTUUOMWhenMatched,
     Name, Description,
     ValidFrom,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540021 /*From ID Server*/, 540019 /*Item Umverpacken*/,
     NULL, 0, 'Y', 'Y', 'N',
     'N',
     'Maßberechnung - Umverpacken', NULL,
     TO_TIMESTAMP('2001-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
     'N',
     TO_TIMESTAMP('2026-07-22 10:00:07', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:07', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

-- ============================================================
-- NESTING mode (N)
-- ============================================================

INSERT INTO M_HU_PI
    (AD_Client_ID, AD_Org_ID, M_HU_PI_ID, Name, Description,
     IsActive, IsDefaultLU, IsDefaultForPicking,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540015 /*From ID Server*/, 'Maßberechnung - Verschachteln',
     'TU-Packvorschrift für die Maßberechnungsmethode Verschachteln.',
     'N', 'N', 'N',
     TO_TIMESTAMP('2026-07-22 10:00:08', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:08', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

INSERT INTO M_HU_PI_Version
    (AD_Client_ID, AD_Org_ID, M_HU_PI_Version_ID, M_HU_PI_ID, Name,
     HU_UnitType, PackageDimensionCalcMethod, IsCurrent,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540015 /*From ID Server*/, 540015 /*M_HU_PI Verschachteln*/,
     'Maßberechnung - Verschachteln',
     'TU', 'N', 'N',
     'N',
     TO_TIMESTAMP('2026-07-22 10:00:09', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:09', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

INSERT INTO M_HU_PI_Item
    (AD_Client_ID, AD_Org_ID, M_HU_PI_Item_ID, M_HU_PI_Version_ID,
     ItemType, Qty,
     IsActive, IsAllowDirectlyOnPM,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540020 /*From ID Server*/, 540015 /*PIV Nesting*/,
     'MI', 0,
     'N', 'N',
     TO_TIMESTAMP('2026-07-22 10:00:10', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:10', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;

INSERT INTO M_HU_PI_Item_Product
    (AD_Client_ID, AD_Org_ID, M_HU_PI_Item_Product_ID, M_HU_PI_Item_ID,
     M_Product_ID, Qty, IsAllowAnyProduct, IsInfiniteCapacity, IsDefaultForProduct,
     IsOrderInTUUOMWhenMatched,
     Name, Description,
     ValidFrom,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0, 540022 /*From ID Server*/, 540020 /*Item Verschachteln*/,
     NULL, 0, 'Y', 'Y', 'N',
     'N',
     'Maßberechnung - Verschachteln', NULL,
     TO_TIMESTAMP('2001-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
     'N',
     TO_TIMESTAMP('2026-07-22 10:00:11', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-07-22 10:00:11', 'YYYY-MM-DD HH24:MI:SS'),
     100)
;
