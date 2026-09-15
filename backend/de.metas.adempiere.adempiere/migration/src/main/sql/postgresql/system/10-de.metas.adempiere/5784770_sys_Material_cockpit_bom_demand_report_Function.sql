DROP FUNCTION IF EXISTS material_cockpit_bom_demand_report(varchar, varchar);

CREATE OR REPLACE FUNCTION material_cockpit_bom_demand_report(
    p_qtydemand_qtysupply_v_ids varchar,
    p_uuid                      varchar
)
    RETURNS TABLE
            (
                parent_product            text,
                parent_qty_reserved       numeric,
                bom_line                  text,
                component_product         text,
                bom_qty_per_unit          numeric,
                total_component_qty_needed numeric,
                total_on_hand_qty         numeric,
                shortage                  numeric,
                UOMSymbol                text
            )
AS
$$
BEGIN
    RETURN QUERY
        WITH selected_ids AS (
            SELECT CASE
                       WHEN p_qtydemand_qtysupply_v_ids = 'all' THEN
                           ARRAY(
                                   SELECT s.intkey1
                                   FROM t_webui_viewselection s
                                   WHERE s.uuid = p_uuid
                           )::int[]
                                                                ELSE
                           REGEXP_SPLIT_TO_ARRAY(p_qtydemand_qtysupply_v_ids, ',')::int[]
                   END AS ids
        ),
             product_demand AS (
                 SELECT v.m_product_id,
                        (v.ProductValue || '_' || v.ProductName)::text AS product_info,
                        SUM(v.qtyReserved)                             AS qtyReserved
                 FROM QtyDemand_QtySupply_V v
                          CROSS JOIN selected_ids si
                 WHERE v.QtyDemand_QtySupply_V_ID = ANY (si.ids)
                 GROUP BY v.m_product_id, v.ProductValue, v.ProductName
                 HAVING SUM(v.qtyReserved) > 0
             ),
             active_boms AS (
                 SELECT DISTINCT ON (pb.m_product_id) pb.m_product_id,
                                                      pb.pp_product_bom_id
                 FROM pp_product_bom pb
                 WHERE pb.IsActive = 'Y'
                   AND pb.docstatus = 'CO'
                   AND pb.validfrom <= NOW()
                   AND (pb.validto IS NULL OR pb.validto >= NOW())
                 ORDER BY pb.m_product_id, pb.validfrom DESC
             ),
             bom_components AS (
                 SELECT pd.product_info                                    AS parent_product_info,
                        pd.qtyReserved                                     AS parent_qty_res,
                        bl.line::text                                      AS b_line,
                        (p_comp.Value || '_' || p_comp.Name)::text         AS c_info,
                        bl.qtybom                                          AS q_per_unit,
                        (bl.qtybom * pd.qtyReserved)                       AS t_needed,
                        uom.UOMSymbol::text                                AS uom,
                        p_comp.m_product_id                                AS c_id
                 FROM product_demand pd
                          INNER JOIN active_boms ab ON pd.m_product_id = ab.m_product_id
                          INNER JOIN pp_product_bomline bl ON ab.pp_product_bom_id = bl.pp_product_bom_id
                          INNER JOIN m_product p_comp ON bl.m_product_id = p_comp.m_product_id
                          LEFT JOIN c_uom uom ON bl.c_uom_id = uom.c_uom_id
                 WHERE bl.IsActive = 'Y'
                   AND bl.componenttype = 'CO'
                   AND COALESCE(bl.qtybom, 0) <> 0
                   AND bl.validfrom <= NOW()
                   AND (bl.validto IS NULL OR bl.validto >= NOW())
             ),
             global_stock AS (
                 SELECT v.m_product_id,
                        SUM(v.qtyStock) AS total_qty_stock
                 FROM QtyDemand_QtySupply_V v
                 GROUP BY v.m_product_id
             )
        SELECT bc.parent_product_info,
               bc.parent_qty_res,
               bc.b_line,
               bc.c_info,
               bc.q_per_unit,
               bc.t_needed,
               COALESCE(gs.total_qty_stock, 0),
               GREATEST(bc.t_needed - COALESCE(gs.total_qty_stock, 0), 0),
               bc.uom
        FROM bom_components bc
                 LEFT JOIN global_stock gs ON bc.c_id = gs.m_product_id
        ORDER BY bc.parent_product_info, bc.b_line::numeric;
END;
$$
    LANGUAGE plpgsql;

-- -- With WebUI selection
-- SELECT * FROM material_cockpit_bom_demand_report('all', 'your-uuid-here');
--
-- -- With explicit IDs
-- SELECT * FROM material_cockpit_bom_demand_report('123456,789012', NULL);
