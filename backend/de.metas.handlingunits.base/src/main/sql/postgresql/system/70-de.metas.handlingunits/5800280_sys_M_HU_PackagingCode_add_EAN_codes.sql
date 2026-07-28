-- Add EAN/EANCOM 7065 packaging codes used by Migros DESADV (per
-- mf15-documentation/edi/customer-docs/Migros_DESADV/DESADV0702_RWW_Gebindeabbildung.pdf,
-- segment PAC, DE 7065). Existing alphabetic codes (ISO1, ISO2, CART, CONT, ...)
-- are kept in place; the EAN-coded variants (200, 201, CT, CN) are added so HU
-- PIs can reference the exact code the clearing center expects.
--
-- Idempotent: skip the INSERT if a row with the same PackagingCode already
-- exists (some customers may have added these manually before this script ran).

INSERT INTO M_HU_PackagingCode (
    M_HU_PackagingCode_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    PackagingCode, Description, HU_UnitType
)
SELECT
    540016 /*From ID Server*/, 1000000, 0, 'Y',
    TO_TIMESTAMP('2026-04-29 10:00','YYYY-MM-DD HH24:MI'), 0,
    TO_TIMESTAMP('2026-04-29 10:00','YYYY-MM-DD HH24:MI'), 0,
    '200', 'Pallet ISO 0 - 1/2 EURO Pallet (EAN Code)', 'LU'
WHERE NOT EXISTS (SELECT 1 FROM M_HU_PackagingCode WHERE PackagingCode = '200');

INSERT INTO M_HU_PackagingCode (
    M_HU_PackagingCode_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    PackagingCode, Description, HU_UnitType
)
SELECT
    540017 /*From ID Server*/, 1000000, 0, 'Y',
    TO_TIMESTAMP('2026-04-29 10:00','YYYY-MM-DD HH24:MI'), 0,
    TO_TIMESTAMP('2026-04-29 10:00','YYYY-MM-DD HH24:MI'), 0,
    '201', 'Pallet ISO 1 - 1/1 EURO Pallet (EAN Code)', 'LU'
WHERE NOT EXISTS (SELECT 1 FROM M_HU_PackagingCode WHERE PackagingCode = '201');

INSERT INTO M_HU_PackagingCode (
    M_HU_PackagingCode_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    PackagingCode, Description, HU_UnitType
)
SELECT
    540018 /*From ID Server*/, 1000000, 0, 'Y',
    TO_TIMESTAMP('2026-04-29 10:00','YYYY-MM-DD HH24:MI'), 0,
    TO_TIMESTAMP('2026-04-29 10:00','YYYY-MM-DD HH24:MI'), 0,
    'CT', 'Carton (EAN Code)', 'TU'
WHERE NOT EXISTS (SELECT 1 FROM M_HU_PackagingCode WHERE PackagingCode = 'CT');

INSERT INTO M_HU_PackagingCode (
    M_HU_PackagingCode_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    PackagingCode, Description, HU_UnitType
)
SELECT
    540019 /*From ID Server*/, 1000000, 0, 'Y',
    TO_TIMESTAMP('2026-04-29 10:00','YYYY-MM-DD HH24:MI'), 0,
    TO_TIMESTAMP('2026-04-29 10:00','YYYY-MM-DD HH24:MI'), 0,
    'CN', 'Container, not otherwise specified as transport equipment (EAN Code)', 'LU'
WHERE NOT EXISTS (SELECT 1 FROM M_HU_PackagingCode WHERE PackagingCode = 'CN');
