-- Migration: Create M_MaterialCockpit_Base_V and the rebuild function
-- Part of: Material Cockpit V2 (Increment 1) -- me03#27512, se203#252

--
-- 1. Create the base view
--
DROP VIEW IF EXISTS M_MaterialCockpit_Base_V
;

CREATE OR REPLACE VIEW M_MaterialCockpit_Base_V AS

    -- On-hand stock from md_stock
SELECT s.ad_client_id,
       s.ad_org_id,
       s.m_product_id,
       p.m_product_category_id,
       p.value                                                                                          AS ProductValue,
       p.name                                                                                           AS ProductName,
       p.c_uom_id,
       s.m_warehouse_id,
       s.attributeskey                                                                                  AS AttributesKey,
       'OH'::varchar(2)                                                                                 AS SupplyType,
       NULL::numeric(10, 0)                                                                             AS C_BPartner_Vendor_ID,
       NOW()::timestamp without time zone                                                               AS DatePromised,
       s.qtyonhand                                                                                      AS QtyOnHand,
       0::numeric                                                                                       AS QtyTU,
       0::numeric                                                                                       AS QtyLU,
       COALESCE(s.qtyonhand * NULLIF(p.weight, 0), 0)                                                   AS WeightNet,
       getLastCostPrice(p.m_product_id)                                                                 AS LastCostPrice,
       ABS((('x' || SUBSTR(MD5(CONCAT_WS('#',
                                         'OH',
                                         s.ad_client_id::text,
                                         s.ad_org_id::text,
                                         s.m_product_id::text,
                                         COALESCE(s.attributeskey, '')::text,
                                         COALESCE(s.m_warehouse_id, 0)::text)), 1, 10))::bit(32)::int)) AS QtyDemand_QtySupply_V_ID
FROM md_stock s
         INNER JOIN m_product p ON s.m_product_id = p.m_product_id
WHERE s.isactive = 'Y'
  AND COALESCE(s.qtyonhand, 0) <> 0

UNION ALL

-- Planned supply from M_ReceiptSchedule
SELECT rs.ad_client_id,
       rs.ad_org_id,
       rs.m_product_id,
       p.m_product_category_id,
       p.value                                                                                                    AS ProductValue,
       p.name                                                                                                     AS ProductName,
       p.c_uom_id,
       rs.m_warehouse_id,
       generateasistorageattributeskey(rs.m_attributesetinstance_id)                                              AS AttributesKey,
       'PS'::varchar(2)                                                                                           AS SupplyType,
       rs.c_bpartner_id                                                                                           AS C_BPartner_Vendor_ID,
       rs.DatePromised                                                                                            AS DatePromised,
       SUM(uomconvert(rs.m_product_id, rs.c_uom_id, p.c_uom_id, rs.qtytomove))                                    AS QtyOnHand,
       0::numeric                                                                                                 AS QtyTU,
       0::numeric                                                                                                 AS QtyLU,
       COALESCE(SUM(uomconvert(rs.m_product_id, rs.c_uom_id, p.c_uom_id, rs.qtytomove) * NULLIF(p.weight, 0)), 0) AS WeightNet,
       getLastCostPrice(p.m_product_id)                                                                           AS LastCostPrice,
       ABS((('x' || SUBSTR(MD5(CONCAT_WS('#',
                                         'PS',
                                         rs.ad_client_id::text,
                                         rs.ad_org_id::text,
                                         rs.m_product_id::text,
                                         COALESCE(generateasistorageattributeskey(rs.m_attributesetinstance_id), '')::text,
                                         COALESCE(rs.m_warehouse_id, 0)::text,
                                         COALESCE(rs.c_bpartner_id, 0)::text,
                                         COALESCE(rs.datepromised::text, ''))), 1, 10))::bit(32)::int))           AS QtyDemand_QtySupply_V_ID
FROM (SELECT rs.*,
             COALESCE(rs.DatePromised_Override, rs.MovementDate) AS DatePromised
      FROM m_receiptschedule rs) rs
         INNER JOIN m_product p ON rs.m_product_id = p.m_product_id
WHERE rs.processed = 'N'
  AND rs.isactive = 'Y'
  AND COALESCE(rs.qtytomove, 0) <> 0
GROUP BY rs.ad_client_id, rs.ad_org_id, rs.m_product_id, p.m_product_category_id,
         p.value, p.name, p.c_uom_id, rs.m_warehouse_id,
         generateasistorageattributeskey(rs.m_attributesetinstance_id),
         rs.c_bpartner_id, rs.datepromised, p.weight, p.m_product_id
;


--
-- 2. Create the rebuild function
--
CREATE OR REPLACE FUNCTION after_migration_M_MaterialCockpit_rebuild()
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_source_view  TEXT;
    v_count        INTEGER;
    v_missing_cols TEXT;
BEGIN
    -- Step 1: Find customer/override views matching '%_MaterialCockpit_V' excluding the base view
    SELECT COUNT(*), MAX(viewname)
    INTO v_count, v_source_view
    FROM pg_views
    WHERE viewname LIKE '%_materialcockpit_v'
      AND viewname <> 'm_materialcockpit_base_v'
      AND viewname <> 'qtydemand_qtysupply_v';

    -- Step 2: Decide which source view to use
    IF v_count = 0 THEN
        v_source_view := 'm_materialcockpit_base_v';
        RAISE NOTICE 'after_migration_M_MaterialCockpit_rebuild: No customer override found. Using base view: %', v_source_view;
    ELSIF v_count = 1 THEN
        RAISE NOTICE 'after_migration_M_MaterialCockpit_rebuild: Found customer override: %', v_source_view;
    ELSE
        RAISE EXCEPTION 'after_migration_M_MaterialCockpit_rebuild: Found % customer override views matching %%_MaterialCockpit_V. Expected at most 1.', v_count;
    END IF;

    -- Step 3: Validate that the chosen source has all required base columns
    SELECT STRING_AGG(bc.column_name, ', ')
    INTO v_missing_cols
    FROM information_schema.columns bc
    WHERE bc.table_name = 'm_materialcockpit_base_v'
      AND bc.table_schema = 'public'
      AND NOT EXISTS (SELECT 1
                      FROM information_schema.columns sc
                      WHERE sc.table_name = v_source_view
                        AND sc.table_schema = 'public'
                        AND sc.column_name = bc.column_name);

    IF v_missing_cols IS NOT NULL AND v_source_view <> 'm_materialcockpit_base_v' THEN
        RAISE EXCEPTION 'after_migration_M_MaterialCockpit_rebuild: Source view "%" is missing base columns: %', v_source_view, v_missing_cols;
    END IF;

    -- Step 4: Recreate the API view QtyDemand_QtySupply_V
    EXECUTE 'DROP VIEW IF EXISTS QtyDemand_QtySupply_V';
    EXECUTE 'CREATE OR REPLACE VIEW QtyDemand_QtySupply_V AS SELECT * FROM ' || v_source_view;

    RAISE NOTICE 'after_migration_M_MaterialCockpit_rebuild: Successfully created QtyDemand_QtySupply_V from %', v_source_view;
END;
$$
;


--
-- 3. Call the rebuild function
--
SELECT after_migration_M_MaterialCockpit_rebuild()
;
