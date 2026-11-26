-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/InTransit_HUs_report.sql
SELECT db_drop_functions('*.InTransit_HUs_Report');

CREATE OR REPLACE FUNCTION report.InTransit_HUs_Report(IN p_M_Locator_ID numeric,
                                                       IN p_AD_Language  Character Varying(6))
    RETURNS TABLE
            (
                ProductName                     character varying(255),
                p_value                         character varying(40),
                qtyEntered                      Numeric,
                UOMSymbol                       Character Varying(10),
                documentno                      character varying(30),
                bp_value                        character varying(40),
                partner_name                    character varying(255),
                order_docno                     character varying(30),
                warehouse_name                  character varying(255),
                locator_to                      character varying(255),
                production_order_no             character varying,
                datestartschedule               timestamp WITH TIME ZONE,
                pickinginstruction              character varying,
                renderedqrcode                  text,
                m_hu_id                         numeric,
                dd_orderline_id                 numeric,
                is_single_pporder_header        boolean,
                pporder_pickinginstruction_lines character varying
            )
AS
$$
-- Per HU row: all PP_Orders its DD_Order line supplies (production_order_no, pporder_pickinginstruction_lines),
-- one row per HU as before.
-- pickinginstruction: "<order no>: <instruction>" lines when a BOM line carries an instruction, else the packing
-- description. This changes the value for lines with a BOM instruction (formerly the bare instruction), so any
-- report layout override printing pickinginstruction must be removed in the same rollout.
-- is_single_pporder_header (per order_docno group, the report group) is STRICT: true only when the whole group
-- has exactly one PP_Order, every row of the group belongs to a PP_Order, and no BOM line carries an instruction.
-- A group mixing PP and non-PP lines is false, so the header never names one order for a line that does not supply it.
WITH r AS (SELECT COALESCE(pt.Name, p.name) AS ProductName,
                  p.value                   AS p_value,

                  CASE
                      WHEN ddolhc.qtypicked > 0 THEN ddolhc.qtypicked
                                                ELSE ddol.qtyentered
                  END                       AS QtyEntered,

                  uomt.uomsymbol            AS UOMSymbol,
                  ddo.documentno            AS documentno,
                  bp.value                  AS bp_value,
                  bp.name                   AS partner_name,
                  o.documentno              AS order_docno,
                  w.name                    AS warehouse_name,
                  loc_to.value              AS locator_to,
                  pps.production_order_nos  AS production_order_no,
                  pps.datestartschedule     AS datestartschedule,
                  COALESCE(pps.pickinginstruction_lines, piip.description) AS pickinginstruction,
                  qr.renderedqrcode         AS renderedqrcode,
                  hu.m_hu_id                AS m_hu_id,
                  ddol.DD_OrderLine_ID      AS dd_orderline_id,
                  pps.pickinginstruction_lines AS pporder_pickinginstruction_lines,
                  pps.pp_order_ids          AS pp_order_ids,
                  pps.has_instruction       AS has_instruction

           FROM M_HU hu

                    INNER JOIN M_Locator loc ON hu.M_Locator_ID = loc.M_Locator_ID
                    INNER JOIN m_warehouse w ON loc.m_warehouse_id = w.m_warehouse_id

                    INNER JOIN DD_OrderLine_HU_Candidate ddolhc ON ddolhc.M_HU_ID = hu.M_HU_ID
                    INNER JOIN DD_OrderLine ddol ON ddolhc.DD_OrderLine_ID = ddol.DD_OrderLine_ID
                    INNER JOIN DD_Order ddo ON ddol.DD_Order_ID = ddo.DD_Order_ID

                    INNER JOIN M_Product p ON ddol.M_Product_ID = p.M_Product_ID
                    INNER JOIN M_Product_Trl pt
                               ON ddol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language

                    INNER JOIN C_UOM uom ON ddol.C_UOM_ID = uom.C_UOM_ID
                    INNER JOIN C_UOM_Trl uomt ON ddol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language

                    INNER JOIN m_locator loc_to ON ddol.m_locatorto_id = loc_to.m_locator_id

                    LEFT JOIN C_OrderLine ol ON ddol.C_OrderLineSO_ID = ol.C_OrderLine_ID
                    LEFT JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
                    LEFT JOIN c_bpartner bp ON o.c_bpartner_id = bp.c_bpartner_id

                    LEFT JOIN LATERAL (
                        SELECT array_agg(DISTINCT ppo.pp_order_id)                                                       AS pp_order_ids,
                               string_agg(DISTINCT ppo.production_order_no, E'\n' ORDER BY ppo.production_order_no)        AS production_order_nos,
                               min(ppo.datestartschedule)                                                                AS datestartschedule,
                               string_agg(DISTINCT ppo.production_order_no || ': ' || ppo.pickinginstruction, E'\n'
                                          ORDER BY ppo.production_order_no || ': ' || ppo.pickinginstruction)
                               FILTER (WHERE ppo.pickinginstruction IS NOT NULL)                                         AS pickinginstruction_lines,
                               bool_or(ppo.pickinginstruction IS NOT NULL)                                               AS has_instruction
                        FROM report.InTransit_HUs_Report_PPOrders(ddol.DD_OrderLine_ID, p_AD_Language) ppo
                        ) pps ON TRUE
                    LEFT JOIN M_HU_PI_Item_Product piip ON ddol.M_HU_PI_Item_Product_ID = piip.M_HU_PI_Item_Product_ID

                    INNER JOIN m_hu_qrcode_assignment qr_assign ON qr_assign.m_hu_id = hu.m_hu_id
                    INNER JOIN M_HU_QRCode qr ON qr_assign.m_hu_qrcode_id = qr.m_hu_qrcode_id

           WHERE hu.M_Locator_ID = p_M_Locator_ID
             AND hu.HUStatus IN ('A', 'S', 'I')),
     g AS (SELECT r.order_docno,
                  count(DISTINCT x.id) = 1
                      AND bool_and(r.pp_order_ids IS NOT NULL)
                      AND NOT bool_or(COALESCE(r.has_instruction, false)) AS is_single
           FROM r
                    LEFT JOIN LATERAL unnest(r.pp_order_ids) AS x(id) ON TRUE
           GROUP BY r.order_docno)
SELECT r.ProductName,
       r.p_value,
       r.QtyEntered,
       r.UOMSymbol,
       r.documentno,
       r.bp_value,
       r.partner_name,
       r.order_docno,
       r.warehouse_name,
       r.locator_to,
       r.production_order_no,
       r.datestartschedule,
       r.pickinginstruction,
       r.renderedqrcode,
       r.m_hu_id,
       r.dd_orderline_id,
       g.is_single AS is_single_pporder_header,
       r.pporder_pickinginstruction_lines
FROM r
         INNER JOIN g ON g.order_docno IS NOT DISTINCT FROM r.order_docno
ORDER BY r.m_hu_id, r.documentno

$$
    LANGUAGE SQL STABLE
;
