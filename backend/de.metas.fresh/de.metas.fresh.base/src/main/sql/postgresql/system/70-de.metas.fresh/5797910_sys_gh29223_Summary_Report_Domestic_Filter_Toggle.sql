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
    p_DateFrom                      timestamp with time zone,
    p_DateTo                        timestamp with time zone,
    p_Country_id                    numeric,
    p_IsExcludeDomesticPurchases    varchar DEFAULT 'Y'
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
    v_q_prefixed_aliases_list text;
    v_org_country_code        varchar;

BEGIN

    -- Resolve org country code for domestic-purchase filtering
    SELECT CountryCode INTO v_org_country_code
    FROM C_Country
    WHERE C_Country_ID = p_Country_id;

    -- Guard: if country not resolvable, use empty string (never matches a real country code)
    IF v_org_country_code IS NULL THEN
        v_org_country_code := '';
    END IF;

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

    SELECT TRIM(STRING_AGG(FORMAT('q.%I', material), ', '))
    INTO v_q_prefixed_aliases_list
    FROM UNNEST(v_column_aliases) AS material;


    -- 2) Build the dynamic column list for DATA AGGREGATION (v_data_cols_list), trimmed
    SELECT TRIM(
                   STRING_AGG(
                           FORMAT(
                                   '(ROUND(SUM(t.material_weight) FILTER (WHERE t.material_name = %L), 3))::varchar AS %I',
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
    -- The detail report is always called with IsIncludeAllProducts='Y' from the summary;
    -- the summary's own filtering handles what to include.
    v_report_func_call := FORMAT(
            'report.Package_Licensing_InOut_Report(p_DateFrom := %L, p_DateTo := %L, p_Country_id := %s)',
            p_DateFrom, p_DateTo, p_Country_id
                          );

    -- 5) REWRITTEN V_SQL CONTENT - Wrapped the entire query to exclude the sort_order column
    v_sql := FORMAT($f$
    -- OUTER WRAPPER SELECT: selects only the 10 columns matching RETURNS TABLE
    SELECT
        q.ProductGroup,
        q.PackagingType,
        %6$s
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
                  -- Exclude domestic purchases and pre-licensed (exempt) vendors (when toggle is 'Y')
                  AND (%8$s = 'N'
                       OR r.VendorCountryCode IS NULL
                       OR (r.VendorCountryCode != %7$s AND COALESCE(r.IsVendorPackageLicensingExempt, 'N') != 'Y'))

                UNION ALL

                -- Outer Packaging Rows
                -- OuterPackagingWeight is the weight of the entire outer package (e.g. one cardboard box),
                -- so we divide by PackagingInstructionFactor to get the per-unit weight.
                SELECT
                    r.ProductGroup,
                    'Gewerbe'::varchar AS PackagingType,
                    r.OuterPackagingMaterial AS material_name,
                    ((COALESCE(r.PurchaseQty, 0) - COALESCE(r.ForeignSalesQty, 0)) * COALESCE(r.OuterPackagingWeight, 0) / NULLIF(COALESCE(r.PackagingInstructionFactor, 1), 0)) AS material_weight
                FROM %4$s r
                WHERE r.OuterPackagingMaterial = ANY(%5$s)
                  -- Exclude domestic purchases and pre-licensed (exempt) vendors (when toggle is 'Y')
                  AND (%8$s = 'N'
                       OR r.VendorCountryCode IS NULL
                       OR (r.VendorCountryCode != %7$s AND COALESCE(r.IsVendorPackageLicensingExempt, 'N') != 'Y'))
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
                    v_q_prefixed_aliases_list, -- %6$s
                    QUOTE_LITERAL(v_org_country_code), -- %7$s — org country code for domestic-purchase filter
                    QUOTE_LITERAL(p_IsExcludeDomesticPurchases) -- %8$s — toggle for domestic-purchase filter
             );

    -- 6) Execute and return the result.
    RETURN QUERY EXECUTE v_sql;
END;
$$
;

-- ==========================================================================
-- AD metadata: add IsExcludeDomesticPurchases parameter to Mengenmeldung process
-- ==========================================================================

-- AD_Element
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Description, Updated, UpdatedBy)
VALUES (0, 584757 /*From ID Server*/, 0, 'IsExcludeDomesticPurchases', '2026-04-13 10:00', 0, 'D', 'Y',
        'Inländische Einkäufe ausschließen', 'Inländische Einkäufe ausschl.',
        'Wareneingänge von inländischen und vorlizenzierte Lieferanten von der Berechnung ausschließen',
        '2026-04-13 10:00', 0);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584757, e.Name, e.PrintName, e.Description, 'N', 0, 0, e.Created, 0, e.Updated, 0, 'Y'
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND e.AD_Element_ID = 584757
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Element_ID = 584757);

UPDATE AD_Element_Trl SET Name = 'Exclude Domestic Purchases', PrintName = 'Excl. Domestic Purch.',
                          Description = 'Exclude receipts from domestic and pre-licensed vendors from the calculation',
                          IsTranslated = 'Y', Updated = '2026-04-13 10:00', UpdatedBy = 0
WHERE AD_Element_ID = 584757 AND AD_Language = 'en_US';

-- AD_Process_Para on Mengenmeldung (AD_Process_ID=585504)
-- Using AD_Reference_ID=20 (YesNo), default 'Y'
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID,
                             ColumnName, Created, CreatedBy, DefaultValue, EntityType, FieldLength, IsActive, IsCentrallyMaintained,
                             IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 584757, 0, 585504, 543179 /*From ID Server*/, 20,
        'IsExcludeDomesticPurchases', '2026-04-13 10:00', 0, 'Y', 'D', 1, 'Y', 'Y',
        'Y', 'N', 'Inländische Einkäufe ausschließen', 50, '2026-04-13 10:00', 0);

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 543179, pp.Name, pp.Description, pp.Help, 'N', 0, 0, pp.Created, 0, pp.Updated, 0, 'Y'
FROM AD_Language l, AD_Process_Para pp
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND pp.AD_Process_Para_ID = 543179
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Process_Para_ID = 543179);

-- Update SQLStatement to pass the new parameter
UPDATE AD_Process
SET SQLStatement = 'SELECT * FROM report.Package_Licensing_InOut_Summary_Report(''@DateFrom@''::timestamp with time zone, ''@DateTo@''::timestamp with time zone, @C_Country_ID/null@, ''@IsExcludeDomesticPurchases@'')',
    Updated = '2026-04-13 10:00',
    UpdatedBy = 0
WHERE AD_Process_ID = 585504;