DROP FUNCTION IF EXISTS material_cockpit_bom_demand_report(varchar, varchar);

CREATE OR REPLACE FUNCTION material_cockpit_bom_demand_report(
    p_qtydemand_qtysupply_v_ids varchar,
    p_uuid                      varchar
)
    RETURNS TABLE
            (
                parent_product             text,
                parent_qty_reserved        numeric,
                bom_line                   text,
                component_product          text,
                bom_qty_per_unit           numeric,
                total_component_qty_needed numeric,
                total_on_hand_qty          numeric,
                total_reserved_qty         numeric,
                total_ordered_qty          numeric,
                shortage                   numeric,
                UOMSymbol                  text
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
             -- Expands BOM recursively via PP_Product_BOM_Recursive and accumulates
             -- quantities along each path (e.g. qty_D1_per_A = qty_C1_per_A * qty_D1_per_C1).
             bom_components AS (
                 SELECT pd.product_info                             AS parent_product_info,
                        pd.qtyReserved                             AS parent_qty_res,
                        r.line_text                                AS b_line,
                        r.sort_path                                AS sort_path,
                        (p_comp.Value || '_' || p_comp.Name)::text AS c_info,
                        r.acc_qty                                  AS q_per_unit,
                        (r.acc_qty * pd.qtyReserved)               AS t_needed,
                        r.uom_symbol::text                         AS uom,
                        r.component_product_id                     AS c_id
                 FROM product_demand pd
                          INNER JOIN active_boms ab ON pd.m_product_id = ab.m_product_id
                          CROSS JOIN LATERAL (
                     -- bom_all: all BOM nodes (depth>=2 = components, not the root product)
                     -- filtered to fixed-quantity lines only (IsQtyPercentage='N')
                     WITH RECURSIVE
                         bom_all AS (
                             SELECT rec.Line       AS line_text,
                                    rec.M_Product_ID,
                                    rec.QtyBOM,
                                    rec.UOMSymbol,
                                    rec.depth,
                                    rec.path
                             FROM PP_Product_BOM_Recursive(ab.pp_product_bom_id, NULL) rec
                             WHERE rec.depth >= 2
                               AND rec.IsQtyPercentage = 'N'
                               AND COALESCE(rec.QtyBOM, 0) <> 0
                         ),
                         -- accumulated: walks the path top-down, multiplying QtyBOM at each level
                         -- so acc_qty for D1 (under C1 under A) = qty_C1_per_A * qty_D1_per_C1
                         accumulated(line_text, component_product_id, uom_symbol, sort_path, acc_qty) AS (
                             -- base: direct children of the root BOM (depth=2)
                             SELECT ba.line_text, ba.M_Product_ID, ba.UOMSymbol, ba.path,
                                    ba.QtyBOM
                             FROM bom_all ba
                             WHERE ba.depth = 2

                             UNION ALL

                             -- recursive: each deeper level multiplies its parent's accumulated qty
                             SELECT ba.line_text, ba.M_Product_ID, ba.UOMSymbol, ba.path,
                                    a.acc_qty * ba.QtyBOM
                             FROM bom_all ba
                                      INNER JOIN accumulated a
                                                 ON ba.path[1:array_length(ba.path, 1) - 1] = a.sort_path
                         )
                     SELECT acc.line_text,
                            acc.component_product_id,
                            acc.uom_symbol,
                            acc.sort_path,
                            acc.acc_qty
                     FROM accumulated acc
                     ) r
                          INNER JOIN m_product p_comp ON r.component_product_id = p_comp.m_product_id
             ),
             -- Aggregate demand across all BOM paths per component.
             -- A component can appear in multiple sub-BOMs (e.g. D1 under both C1 and C2).
             -- Summing t_needed gives the correct total demand; bom_line lists all paths.
             component_demand AS (
                 SELECT bc.parent_product_info,
                        bc.parent_qty_res,
                        STRING_AGG(bc.b_line, ', ' ORDER BY bc.sort_path) AS b_line,
                        MIN(bc.sort_path)                                  AS first_sort_path,
                        bc.c_info,
                        bc.c_id,
                        bc.uom,
                        SUM(bc.q_per_unit)                                 AS q_per_unit,
                        SUM(bc.t_needed)                                   AS t_needed
                 FROM bom_components bc
                 GROUP BY bc.parent_product_info, bc.parent_qty_res, bc.c_info, bc.c_id, bc.uom
             ),
             -- Global stock, reservations, and open PO/MO quantities per component product.
             -- Reserved = qty committed via sales orders (qtyReserved)
             --          + production order candidates (qtyToProduce)
             --          + open manufacturing orders (PP_Order docstatus CO/IP, not yet issued).
             -- Ordered  = qty incoming from purchase orders without goods receipt (qtyToMove).
             global_stock AS (
                 SELECT src.m_product_id,
                        SUM(src.qty_stock)    AS total_qty_stock,
                        SUM(src.qty_reserved) AS total_reserved,
                        SUM(src.qty_ordered)  AS total_ordered
                 FROM (
                          -- stock, SO reservations, PP_Order_Candidate demand, PO incoming
                          SELECT v.m_product_id,
                                 COALESCE(v.qtyStock, 0)                                   AS qty_stock,
                                 COALESCE(v.qtyReserved, 0) + COALESCE(v.qtyToProduce, 0) AS qty_reserved,
                                 COALESCE(v.qtyToMove, 0)                                  AS qty_ordered
                          FROM QtyDemand_QtySupply_V v

                          UNION ALL

                          -- raw material demand from confirmed/in-progress manufacturing orders
                          SELECT pobl.m_product_id,
                                 0                                                                             AS qty_stock,
                                 GREATEST(pobl.qtyrequiered - COALESCE(pobl.qtydelivered, 0), 0)              AS qty_reserved,
                                 0                                                                             AS qty_ordered
                          FROM pp_order po
                                   INNER JOIN pp_order_bomline pobl ON pobl.pp_order_id = po.pp_order_id
                          WHERE po.docstatus IN ('CO', 'IP')
                            AND po.isactive = 'Y'
                            AND pobl.isactive = 'Y'

                          UNION ALL

                          -- supply from confirmed/in-progress manufacturing orders (product being produced)
                          -- counts as incoming supply for the semi-finished/component product
                          SELECT po.m_product_id,
                                 0                                                                             AS qty_stock,
                                 0                                                                             AS qty_reserved,
                                 GREATEST(po.qtyordered - COALESCE(po.qtydelivered, 0), 0)                    AS qty_ordered
                          FROM pp_order po
                          WHERE po.docstatus IN ('CO', 'IP')
                            AND po.isactive = 'Y'
                      ) src
                 GROUP BY src.m_product_id
             )
        SELECT cd.parent_product_info,
               cd.parent_qty_res,
               cd.b_line,
               cd.c_info,
               cd.q_per_unit,
               cd.t_needed,
               COALESCE(gs.total_qty_stock, 0),
               COALESCE(gs.total_reserved, 0),
               COALESCE(gs.total_ordered, 0),
               -- shortage = demand - available, where available = stock - reserved + ordered
               GREATEST(cd.t_needed - (COALESCE(gs.total_qty_stock, 0)
                                           - COALESCE(gs.total_reserved, 0)
                   + COALESCE(gs.total_ordered, 0)), 0) AS shortage,
               cd.uom
        FROM component_demand cd
                 LEFT JOIN global_stock gs ON cd.c_id = gs.m_product_id
        ORDER BY cd.parent_product_info, cd.first_sort_path;
END;
$$
    LANGUAGE plpgsql;

-- -- With WebUI selection
-- SELECT * FROM material_cockpit_bom_demand_report('all', 'your-uuid-here');
--
-- -- With explicit IDs
-- SELECT * FROM material_cockpit_bom_demand_report('123456,789012', NULL);
