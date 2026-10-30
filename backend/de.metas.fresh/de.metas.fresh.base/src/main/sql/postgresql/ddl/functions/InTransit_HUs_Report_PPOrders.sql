SELECT db_drop_functions('*.InTransit_HUs_Report_PPOrders');

CREATE OR REPLACE FUNCTION report.InTransit_HUs_Report_PPOrders(IN p_DD_OrderLine_ID numeric,
                                                                IN p_AD_Language    character varying(6))
    RETURNS TABLE
            (
                pp_order_id         numeric,
                production_order_no character varying,
                datestartschedule   timestamp WITH TIME ZONE,
                qty                 numeric,
                uomsymbol           character varying,
                pickinginstruction  character varying
            )
AS
$$
-- Every PP_Order (and its BOM line) this DD_OrderLine supplies. An HU is picked against the line, not against
-- a candidate, so the HU's own PP_Order is unknown: callers list all of them. DD_Order.Forward_PP_Order_ID is
-- empty for aggregated DD_Orders, so it is only a fallback for lines without candidates.
-- qty and uomsymbol are NULL when the reference carries no PP_Order_BOMLine; callers must null-guard them.
WITH refs AS (SELECT DISTINCT c.Forward_PP_Order_ID AS pp_order_id, c.Forward_PP_Order_BOMLine_ID AS pp_order_bomline_id
              FROM DD_Order_Candidate_DDOrder alloc
                       INNER JOIN DD_Order_Candidate c ON alloc.DD_Order_Candidate_ID = c.DD_Order_Candidate_ID
              WHERE alloc.DD_OrderLine_ID = p_DD_OrderLine_ID
                AND alloc.IsActive = 'Y'
                AND c.Forward_PP_Order_ID IS NOT NULL),
     header_ref AS (SELECT ddo.Forward_PP_Order_ID AS pp_order_id, ddo.Forward_PP_Order_BOMLine_ID AS pp_order_bomline_id
                    FROM DD_OrderLine ddol
                             INNER JOIN DD_Order ddo ON ddo.DD_Order_ID = ddol.DD_Order_ID
                    WHERE ddol.DD_OrderLine_ID = p_DD_OrderLine_ID
                      AND ddo.Forward_PP_Order_ID IS NOT NULL
                      AND NOT EXISTS (SELECT 1 FROM refs)),
     all_refs AS (SELECT * FROM refs UNION SELECT * FROM header_ref)
SELECT pp.PP_Order_ID,
       pp.DocumentNo,
       pp.DateStartSchedule,
       bl.QtyRequiered,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol),
       NULLIF(TRIM(bl.PickingInstruction), '')
FROM all_refs r
         INNER JOIN PP_Order pp ON pp.PP_Order_ID = r.pp_order_id
         LEFT JOIN PP_Order_BOMLine bl ON bl.PP_Order_BOMLine_ID = r.pp_order_bomline_id
         LEFT JOIN C_UOM uom ON uom.C_UOM_ID = bl.C_UOM_ID
         LEFT JOIN C_UOM_Trl uomt ON uomt.C_UOM_ID = bl.C_UOM_ID AND uomt.AD_Language = p_AD_Language
ORDER BY pp.DocumentNo, pp.PP_Order_ID, bl.PP_Order_BOMLine_ID
$$
    LANGUAGE SQL STABLE;
