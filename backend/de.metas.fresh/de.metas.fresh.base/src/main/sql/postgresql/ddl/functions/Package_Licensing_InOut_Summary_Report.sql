DROP FUNCTION IF EXISTS report.Package_Licensing_InOut_Summary_Report(
    p_DateFrom   timestamp with time zone,
    p_DateTo     timestamp with time zone,
    p_Country_id numeric
)
;

CREATE OR REPLACE FUNCTION report.Package_Licensing_InOut_Summary_Report(
    p_DateFrom   timestamp with time zone,
    p_DateTo     timestamp with time zone,
    p_Country_id numeric
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
          FROM M_PackageLicensing_MaterialGroup
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
            'report.Package_Licensing_InOut_Report(p_DateFrom := %L, p_DateTo := %L, p_Country_id := %s)',
            p_DateFrom, p_DateTo, p_Country_id
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
                    'Small'::varchar AS PackagingType,
                    r.SmallPackagingMaterial AS material_name,
                    (r.MovementQty * COALESCE(r.SmallPackagingWeight, 0)) AS material_weight
                FROM %4$s r
                WHERE r.SmallPackagingMaterial = ANY(%5$s)

                UNION ALL

                -- Outer Packaging Rows
                SELECT
                    r.ProductGroup,
                    'Outer'::varchar AS PackagingType,
                    r.OuterPackagingMaterial AS material_name,
                    (r.MovementQty * COALESCE(r.OuterPackagingWeight, 0)) AS material_weight
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