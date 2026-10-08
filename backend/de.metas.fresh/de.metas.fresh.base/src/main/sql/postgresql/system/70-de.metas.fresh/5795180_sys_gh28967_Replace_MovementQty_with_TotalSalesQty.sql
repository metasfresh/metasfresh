-- gh#28967: Replace MovementQty with TotalSalesQty in all Package Licensing reports
-- MovementQty (net: receipts positive, shipments negative) was confusing.
-- TotalSalesQty shows total shipment quantity (positive) alongside PurchaseQty and ForeignSalesQty.
-- Summary Report now uses (PurchaseQty - ForeignSalesQty) for material weight calculation,
-- matching Austrian Verpackungsverordnung: packaging placed on domestic market = imports minus re-exports.

-- ==========================================================================
-- 1) InOut Report — replace MovementQty with TotalSalesQty
-- ==========================================================================

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
                PurchaseQty                numeric,
                ForeignSalesQty            numeric,
                TotalSalesQty              numeric,
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
       (CASE WHEN io.IsSoTrx = 'N' THEN iol.MovementQty END)                                   AS PurchaseQty,
       (CASE WHEN io.movementtype = 'C-' AND bc.c_country_id != p_Country_id
             THEN iol.MovementQty END)                                                          AS ForeignSalesQty,
       (CASE WHEN io.movementtype = 'C-' THEN iol.MovementQty END)                              AS TotalSalesQty,
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

-- ==========================================================================
-- 2) Product Report — replace MovementQty with TotalSalesQty
-- ==========================================================================

DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(numeric);
DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(numeric, varchar);
DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(timestamp with time zone, timestamp with time zone, numeric, varchar);

CREATE OR REPLACE FUNCTION report.Package_Licensing_Product_Report(
    p_DateFrom             timestamp with time zone,
    p_DateTo               timestamp with time zone,
    p_C_Country_ID         numeric,
    p_IsIncludeAllProducts varchar DEFAULT 'Y'
)
    RETURNS TABLE
            (
                ProductValue               varchar,
                ProductName                varchar,
                PurchaseQty                numeric,
                ForeignSalesQty            numeric,
                TotalSalesQty              numeric,
                UOMSymbol                  varchar,
                Weight                     numeric,
                ProductGroup               varchar,
                MaterialType               varchar,
                SmallPackagingMaterial      varchar,
                SmallPackagingWeight        numeric,
                OuterPackagingMaterial      varchar,
                OuterPackagingWeight        numeric,
                PackagingInstructionFactor  numeric
            )
    LANGUAGE sql
    STABLE
AS
$$
-- Aggregates the InOut Report to product level.
-- Same columns as the detail report, minus per-InOut fields (DocumentNo, MovementDate, CountryCode).
SELECT r.ProductValue,
       r.ProductName,
       SUM(r.PurchaseQty)       AS PurchaseQty,
       SUM(r.ForeignSalesQty)   AS ForeignSalesQty,
       SUM(r.TotalSalesQty)     AS TotalSalesQty,
       r.UOMSymbol,
       r.Weight,
       r.ProductGroup,
       r.MaterialType,
       r.SmallPackagingMaterial,
       r.SmallPackagingWeight,
       r.OuterPackagingMaterial,
       r.OuterPackagingWeight,
       r.PackagingInstructionFactor
FROM report.Package_Licensing_InOut_Report(
             p_DateFrom := p_DateFrom,
             p_DateTo := p_DateTo,
             p_Country_id := p_C_Country_ID,
             p_IsIncludeAllProducts := p_IsIncludeAllProducts
     ) r
GROUP BY r.ProductValue,
         r.ProductName,
         r.UOMSymbol,
         r.Weight,
         r.ProductGroup,
         r.MaterialType,
         r.SmallPackagingMaterial,
         r.SmallPackagingWeight,
         r.OuterPackagingMaterial,
         r.OuterPackagingWeight,
         r.PackagingInstructionFactor
ORDER BY r.ProductValue, r.ProductName;
$$;

-- ==========================================================================
-- 3) Summary Report — use (PurchaseQty - ForeignSalesQty) for material weight
-- ==========================================================================

DROP FUNCTION IF EXISTS report.Package_Licensing_InOut_Summary_Report(
    p_DateFrom             timestamp with time zone,
    p_DateTo               timestamp with time zone,
    p_Country_id           numeric
)
;
DROP FUNCTION IF EXISTS report.Package_Licensing_InOut_Summary_Report(
    p_DateFrom             timestamp with time zone,
    p_DateTo               timestamp with time zone,
    p_Country_id           numeric,
    p_IsIncludeAllProducts varchar
)
;

CREATE OR REPLACE FUNCTION report.Package_Licensing_InOut_Summary_Report(
    p_DateFrom             timestamp with time zone,
    p_DateTo               timestamp with time zone,
    p_Country_id           numeric,
    p_IsIncludeAllProducts varchar DEFAULT 'Y'
)
    RETURNS TABLE
            (
                ProductGroup        varchar,
                PackagingType       varchar,
                Material_M1_Weight  varchar,
                Material_M2_Weight  varchar,
                Material_M3_Weight  varchar,
                Material_M4_Weight  varchar,
                Material_M5_Weight  varchar,
                Material_M6_Weight  varchar,
                Material_M7_Weight  varchar,
                Material_M8_Weight  varchar,
                Material_M9_Weight  varchar,
                Material_M10_Weight varchar,
                Material_M11_Weight varchar,
                Material_M12_Weight varchar,
                Material_M13_Weight varchar,
                Material_M14_Weight varchar,
                Material_M15_Weight varchar,
                Material_M16_Weight varchar
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_sql                     text;
    v_data_cols_list          text;
    v_header_cols_list        text;
    C_REQUIRED_MATERIALS      TEXT[];
    v_materials_sql_array     text;
    v_column_aliases          TEXT[];
    v_report_func_call        text;
    v_column_aliases_list     text;
    v_prefixed_aliases_list   text;
    v_q_prefixed_aliases_list text; -- **NEW: q.Glas, q.Kunststoff, etc.**

BEGIN

    SELECT ARRAY_AGG(name)
    INTO C_REQUIRED_MATERIALS
    FROM (SELECT DISTINCT NAME
          FROM M_PackageLicensing_MaterialGroup where isactive='Y'
          ORDER BY name
          LIMIT 16) AS dl;

    -- 1) Generate the array of safe column aliases
    SELECT ARRAY_AGG(REGEXP_REPLACE(material, '[^a-zA-Z0-9_]', '_', 'g'))
    INTO v_column_aliases
    FROM UNNEST(C_REQUIRED_MATERIALS) AS material;

    -- 1.5) Build the safe SQL literal array string for filtering
    SELECT 'ARRAY[' || STRING_AGG(QUOTE_LITERAL(material), ', ') || ']'
    INTO v_materials_sql_array
    FROM UNNEST(C_REQUIRED_MATERIALS) AS material;

    -- Build clean lists for final SELECT and prefixed referencing, ensuring TRIM for safety
    SELECT TRIM(ARRAY_TO_STRING(v_column_aliases, ', '))
    INTO v_column_aliases_list;

    SELECT TRIM(STRING_AGG(FORMAT('agg.%I', material), ', '))
    INTO v_prefixed_aliases_list
    FROM UNNEST(v_column_aliases) AS material;

    -- **NEW: Create the q. prefix list for the outer SELECT**
    SELECT TRIM(STRING_AGG(FORMAT('q.%I', material), ', '))
    INTO v_q_prefixed_aliases_list
    FROM UNNEST(v_column_aliases) AS material;


    -- 2) Build the dynamic column list for DATA AGGREGATION (v_data_cols_list), trimmed
    SELECT TRIM(
                   STRING_AGG(
                           FORMAT(
                                   '(SUM(t.material_weight) FILTER (WHERE t.material_name = %L))::varchar AS %I',
                                   material_name,
                                   v_column_aliases[idx]
                           ),
                           ', '
                   )
           )
    INTO v_data_cols_list
    FROM UNNEST(C_REQUIRED_MATERIALS) WITH ORDINALITY t(material_name, idx);

    -- 3) Build the dynamic column list for the HEADER ROW (v_header_cols_list), trimmed
    SELECT TRIM(
                   STRING_AGG(
                           FORMAT('%L::varchar AS %I',
                                  material_name,
                                  v_column_aliases[idx]
                           ),
                           ', '
                   )
           )
    INTO v_header_cols_list
    FROM UNNEST(C_REQUIRED_MATERIALS) WITH ORDINALITY t(material_name, idx);

    -- 4) Build the SQL statement to execute (separate function call argument)
    v_report_func_call := FORMAT(
            'report.Package_Licensing_InOut_Report(p_DateFrom := %L, p_DateTo := %L, p_Country_id := %s, p_IsIncludeAllProducts := %L)',
            p_DateFrom, p_DateTo, p_Country_id, p_IsIncludeAllProducts
                          );

    -- 5) REWRITTEN V_SQL CONTENT - Wrapped the entire query to exclude the sort_order column
    v_sql := FORMAT($f$
    -- OUTER WRAPPER SELECT: selects only the 10 columns matching RETURNS TABLE
    SELECT
        q.ProductGroup,
        q.PackagingType,
        %6$s -- **FIX: q.Glas, q.Kunststoff, etc.**
    FROM (
        -- Inner Query: UNION ALL with sort column
        -- HEADER ROW
        SELECT
               0 AS sort_order, -- Sort column (Integer)
               '---HEADER---'::varchar AS ProductGroup,
               'Material Name'::varchar AS PackagingType,
               %1$s -- v_header_cols_list

        UNION ALL

        -- DATA ROWS: Wrapped Aggregation
        SELECT
            1 AS sort_order, -- Sort column (Integer)
            agg.ProductGroup,
            agg.PackagingType,
            %2$s -- v_prefixed_aliases_list
        FROM (
            -- Subquery 2: Aggregation and Grouping
            SELECT
                t.ProductGroup,
                t.PackagingType,
                %3$s -- v_data_cols_list
            FROM (
                -- Subquery 1: Un-pivoting the report results
                -- Small Packaging Rows
                SELECT
                    r.ProductGroup,
                    'Haushalt'::varchar AS PackagingType,
                    r.SmallPackagingMaterial AS material_name,
                    ((COALESCE(r.PurchaseQty, 0) - COALESCE(r.ForeignSalesQty, 0)) * COALESCE(r.SmallPackagingWeight, 0)) AS material_weight
                FROM %4$s r
                WHERE r.SmallPackagingMaterial = ANY(%5$s)

                UNION ALL

                -- Outer Packaging Rows
                SELECT
                    r.ProductGroup,
                    'Gewerbe'::varchar AS PackagingType,
                    r.OuterPackagingMaterial AS material_name,
                    ((COALESCE(r.PurchaseQty, 0) - COALESCE(r.ForeignSalesQty, 0)) * COALESCE(r.OuterPackagingWeight, 0)) AS material_weight
                FROM %4$s r
                WHERE r.OuterPackagingMaterial = ANY(%5$s)
            ) t
            GROUP BY t.ProductGroup, t.PackagingType
        ) agg
    ) q
    ORDER BY
        q.sort_order,
        q.ProductGroup,
        q.PackagingType
$f$,
                    v_header_cols_list, -- %1$s
                    v_prefixed_aliases_list, -- %2$s
                    v_data_cols_list, -- %3$s
                    v_report_func_call, -- %4$s
                    v_materials_sql_array, -- %5$s
                    v_q_prefixed_aliases_list -- **%6$s (New q. prefixed list)**
             );

    -- 6) Execute and return the result.
    RETURN QUERY EXECUTE v_sql;
END;
$$
;