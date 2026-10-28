-- gh#28967: Add IsIncludeAllProducts parameter to Package_Licensing_InOut_Report
-- When Y (default): include all products regardless of packaging masterdata
-- When N: only include products with packaging masterdata for the given country (old behavior)

-- 1) Drop old signatures and create updated function
DROP FUNCTION IF EXISTS report.Package_Licensing_InOut_Report(p_DateFrom              timestamp with time zone,
                                                              p_DateTo                timestamp with time zone,
                                                              p_Country_id            numeric)
;
DROP FUNCTION IF EXISTS report.Package_Licensing_InOut_Report(p_DateFrom              timestamp with time zone,
                                                              p_DateTo                timestamp with time zone,
                                                              p_Country_id            numeric,
                                                              p_IsIncludeAllProducts  varchar)
;

CREATE OR REPLACE FUNCTION report.Package_Licensing_InOut_Report(p_DateFrom              timestamp with time zone,
                                                                 p_DateTo                timestamp with time zone,
                                                                 p_Country_id            numeric,
                                                                 p_IsIncludeAllProducts  varchar DEFAULT 'Y')

    RETURNS TABLE
            (
                DocumentNo                 varchar,
                MovementDate               date,
                CountryCode                varchar,
                ProductValue               varchar,
                ProductName                varchar,
                MovementQty                numeric,
                PurchaseQty                numeric,
                ForeignSalesQty            numeric,
                UOMSymbol                  varchar,
                Weight                     numeric,
                ProductGroup               varchar,
                MaterialType               varchar,
                SmallPackagingMaterial     varchar,
                SmallPackagingWeight       numeric,
                OuterPackagingMaterial     varchar,
                OuterPackagingWeight       numeric,
                PackagingInstructionFactor numeric
            )

AS
$$

SELECT io.DocumentNo,
       io.MovementDate,
       c.CountryCode,
       p.value                                                                                 AS ProductValue,
       p.name                                                                                  AS ProductName,
       (CASE WHEN io.movementtype = 'C-' THEN iol.MovementQty * (-1) ELSE iol.MovementQty END) AS MovementQty,
       (CASE WHEN io.IsSoTrx = 'N' THEN iol.MovementQty END)                                   AS PurchaseQty,
       (CASE WHEN io.movementtype = 'C-' AND bc.c_country_id != p_Country_id
             THEN iol.MovementQty END)                                                          AS ForeignSalesQty,
       uom.UOMSymbol,
       p.weight                                                                                AS Weight,
       -- ProductGroup (first matching for country)
       (SELECT pg.value
        FROM M_Product_PackageLicensing_ProductGroup pppg
                 JOIN M_PackageLicensing_ProductGroup pg
                      ON pg.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                          AND pg.IsActive = 'Y'
                          AND pg.C_Country_ID = p_Country_id
        WHERE pppg.M_Product_ID = p.M_Product_ID
          AND pppg.IsActive = 'Y'
        ORDER BY pg.M_PackageLicensing_ProductGroup_ID
        LIMIT 1)                                                                               AS ProductGroup,
       -- MaterialType (comma-separated when multiple)
       (SELECT STRING_AGG(pg.Name, ', ' ORDER BY pg.Name)
        FROM M_Product_PackageLicensing_ProductGroup pppg
                 JOIN M_PackageLicensing_ProductGroup pg
                      ON pg.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                          AND pg.IsActive = 'Y'
                          AND (p_Country_id IS NULL OR pg.C_Country_ID = p_Country_id)
        WHERE pppg.M_Product_ID = p.M_Product_ID
          AND pppg.IsActive = 'Y')                                                             AS MaterialType,
       -- SmallPackagingMaterial (resolved by country parameter)
       (SELECT mg.name
        FROM M_Product_SmallPackagingMaterial spm
                 JOIN M_PackageLicensing_MaterialGroup mg
                      ON mg.M_PackageLicensing_MaterialGroup_ID = spm.M_PackageLicensing_MaterialGroup_ID
                          AND mg.IsActive = 'Y'
                          AND mg.C_Country_ID = p_Country_id
        WHERE spm.M_Product_ID = p.M_Product_ID
          AND spm.IsActive = 'Y'
        LIMIT 1)                                                                               AS SmallPackagingMaterial,
       p.SmallPackagingWeight,
       -- OuterPackagingMaterial (resolved by country parameter)
       (SELECT mg.name
        FROM M_Product_OuterPackagingMaterial opm
                 JOIN M_PackageLicensing_MaterialGroup mg
                      ON mg.M_PackageLicensing_MaterialGroup_ID = opm.M_PackageLicensing_MaterialGroup_ID
                          AND mg.IsActive = 'Y'
                          AND mg.C_Country_ID = p_Country_id
        WHERE opm.M_Product_ID = p.M_Product_ID
          AND opm.IsActive = 'Y'
        LIMIT 1)                                                                               AS OuterPackagingMaterial,
       p.OuterPackagingWeight,
       -- Packaging instruction factor (default PI preferred)
       (SELECT piip.Qty
        FROM M_HU_PI_Item_Product piip
        WHERE piip.M_Product_ID = p.M_Product_ID
          AND piip.IsActive = 'Y'
        ORDER BY piip.IsDefaultForProduct DESC, piip.Created DESC
        LIMIT 1)                                                                               AS PackagingInstructionFactor

FROM m_inout io
         INNER JOIN m_inoutline iol ON io.m_inout_id = iol.m_inout_id
         INNER JOIN C_UOM uom ON iol.c_uom_id = uom.c_uom_id
         INNER JOIN m_product p ON p.m_product_id = iol.m_product_id
         INNER JOIN m_warehouse wh ON wh.m_warehouse_id = io.m_warehouse_id
         INNER JOIN c_location l ON l.c_location_id = wh.c_location_id
         INNER JOIN c_country c ON c.c_country_id = l.c_country_id

    -- Shipment destination
         LEFT JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = io.c_bpartner_location_id
         LEFT JOIN c_location bl ON bl.c_location_id = bpl.c_location_id
         LEFT JOIN c_country bc ON bc.c_country_id = bl.c_country_id


WHERE io.movementdate BETWEEN p_DateFrom AND p_DateTo
  AND io.DocStatus IN ('CO', 'CL')
  -- When IsIncludeAllProducts='N', only include products with packaging data for the given country
  AND (COALESCE(p_IsIncludeAllProducts, 'Y') = 'Y'
    OR EXISTS (SELECT 1
               FROM M_Product_PackageLicensing_ProductGroup pppg
                        JOIN M_PackageLicensing_ProductGroup pg
                             ON pg.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                                 AND pg.IsActive = 'Y'
                                 AND pg.C_Country_ID = p_Country_id
               WHERE pppg.M_Product_ID = p.M_Product_ID
                 AND pppg.IsActive = 'Y'))
  AND (COALESCE(p_IsIncludeAllProducts, 'Y') = 'Y'
    OR EXISTS (SELECT 1
               FROM M_Product_SmallPackagingMaterial spm
                        JOIN M_PackageLicensing_MaterialGroup mg
                             ON mg.M_PackageLicensing_MaterialGroup_ID = spm.M_PackageLicensing_MaterialGroup_ID
                                 AND mg.IsActive = 'Y'
                                 AND mg.C_Country_ID = p_Country_id
               WHERE spm.M_Product_ID = p.M_Product_ID
                 AND spm.IsActive = 'Y')
    OR EXISTS (SELECT 1
               FROM M_Product_OuterPackagingMaterial opm
                        JOIN M_PackageLicensing_MaterialGroup mg
                             ON mg.M_PackageLicensing_MaterialGroup_ID = opm.M_PackageLicensing_MaterialGroup_ID
                                 AND mg.IsActive = 'Y'
                                 AND mg.C_Country_ID = p_Country_id
               WHERE opm.M_Product_ID = p.M_Product_ID
                 AND opm.IsActive = 'Y'))
ORDER BY io.movementdate, io.documentno, p.value
    ;

$$
    LANGUAGE sql STABLE
;

-- 2) AD_Element for IsIncludeAllProducts
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy,
                        IsActive, EntityType, ColumnName, Name, PrintName)
VALUES (584691 /*From ID Server*/, 0, 0, '2026-03-21 12:00', 100, '2026-03-21 12:00', 100,
        'Y', 'D', 'IsIncludeAllProducts', 'Alle Produkte einbeziehen', 'Alle Produkte einbeziehen')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584691, 'Alle Produkte einbeziehen', 'Alle Produkte einbeziehen', NULL,
       'N', 0, 0, '2026-03-21 12:00', 100, '2026-03-21 12:00', 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 584691)
;

-- de_DE translation (base language — IsTranslated stays N)
UPDATE AD_Element_Trl
SET IsTranslated   = 'N',
    Name           = 'Alle Produkte einbeziehen',
    PrintName      = 'Alle Produkte einbeziehen',
    Description    = 'Produkte ohne Verpackungslizenz-Stammdaten für das gewählte Land mit einbeziehen',
    Updated        = '2026-03-21 12:00',
    UpdatedBy      = 100
WHERE AD_Element_ID = 584691
  AND AD_Language = 'de_DE'
;
SELECT update_ad_element_on_ad_element_trl_update(584691, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'de_DE');

-- de_CH translation
UPDATE AD_Element_Trl
SET IsTranslated   = 'Y',
    Name           = 'Alle Produkte einbeziehen',
    PrintName      = 'Alle Produkte einbeziehen',
    Description    = 'Produkte ohne Verpackungslizenz-Stammdaten für das gewählte Land mit einbeziehen',
    Updated        = '2026-03-21 12:00',
    UpdatedBy      = 100
WHERE AD_Element_ID = 584691
  AND AD_Language = 'de_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'de_CH');

-- en_US translation
UPDATE AD_Element_Trl
SET IsTranslated   = 'Y',
    Name           = 'Include All Products',
    PrintName      = 'Include All Products',
    Description    = 'Include products that have no packaging licensing master data for the selected country',
    Updated        = '2026-03-21 12:00',
    UpdatedBy      = 100
WHERE AD_Element_ID = 584691
  AND AD_Language = 'en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'en_US');

-- 3) AD_Process_Para for detail report (585503)
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID,
                             AD_Reference_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength,
                             IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
                             Name, SeqNo, DefaultValue, Updated, UpdatedBy)
VALUES (0, 584691 /*From ID Server*/, 0, 585503, 543158 /*From ID Server*/,
        20, 'IsIncludeAllProducts', '2026-03-21 12:00', 100, 'D', 0,
        'Y', 'N', 'Y', 'N', 'Y', 'N',
        'Alle Produkte einbeziehen', 40, 'Y', '2026-03-21 12:00', 100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name,
                                 IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 543158, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543158
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- Propagate element translations (en_US name/description) to process parameter labels
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'en_US');

-- 4) AD_Process_Para for summary report (585504)
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID,
                             AD_Reference_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength,
                             IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
                             Name, SeqNo, DefaultValue, Updated, UpdatedBy)
VALUES (0, 584691 /*From ID Server*/, 0, 585504, 543159 /*From ID Server*/,
        20, 'IsIncludeAllProducts', '2026-03-21 12:00', 100, 'D', 0,
        'Y', 'N', 'Y', 'N', 'Y', 'N',
        'Alle Produkte einbeziehen', 40, 'Y', '2026-03-21 12:00', 100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name,
                                 IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 543159, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543159
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- Propagate element translations (en_US name/description) to process parameter labels
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584691, 'en_US');

-- 5) Update SQLStatement on detail report to pass the new parameter
UPDATE AD_Process
SET SQLStatement = 'SELECT * FROM report.Package_Licensing_InOut_Report( @DateFrom/null@, @DateTo/null@, @C_Country_ID/null@, ''@IsIncludeAllProducts@'')',
    Updated      = '2026-03-21 12:00',
    UpdatedBy    = 100
WHERE AD_Process_ID = 585503
;

-- 6) Update SQLStatement on summary report to pass the new parameter
UPDATE AD_Process
SET SQLStatement = 'SELECT * FROM report.Package_Licensing_InOut_Summary_Report( @DateFrom/null@, @DateTo/null@, @C_Country_ID/null@, ''@IsIncludeAllProducts@'')',
    Updated      = '2026-03-21 12:00',
    UpdatedBy    = 100
WHERE AD_Process_ID = 585504
;
