SELECT db_drop_functions('*.InTransit_HUs_Report');

CREATE OR REPLACE FUNCTION report.InTransit_HUs_Report(IN p_M_Locator_ID numeric,
                                                       IN p_AD_Language  Character Varying(6))
    RETURNS TABLE
            (
                ProductName         character varying(255),
                p_value             character varying(40),
                qtyEntered          Numeric,
                UOMSymbol           Character Varying(10),
                documentno          character varying(30),
                bp_value            character varying(40),
                partner_name        character varying(255),
                order_docno         character varying(30),
                warehouse_name      character varying(255),
                locator_to          character varying(255),
                production_order_no character varying(30),
                datestartschedule   timestamp WITH TIME ZONE,
                renderedqrcode      text,
                m_hu_id             numeric
            )
AS
$$
SELECT COALESCE(pt.Name, p.name) AS ProductName,
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
       pp.documentno             AS production_order_no,
       pp.datestartschedule      AS datestartschedule,
       qr.renderedqrcode         AS renderedqrcode,
       hu.m_hu_id                AS m_hu_id

FROM M_HU hu

         -- Join to get HUs on the specified locator
         INNER JOIN M_Locator loc ON hu.M_Locator_ID = loc.M_Locator_ID
         -- Warehouse information
         INNER JOIN m_warehouse w ON loc.m_warehouse_id = w.m_warehouse_id

         -- Join DD Order data via DD_OrderLine_HU_Candidate (as per requirement)
         INNER JOIN DD_OrderLine_HU_Candidate ddolhc ON ddolhc.M_HU_ID = hu.M_HU_ID
         INNER JOIN DD_OrderLine ddol ON ddolhc.DD_OrderLine_ID = ddol.DD_OrderLine_ID
         INNER JOIN DD_Order ddo ON ddol.DD_Order_ID = ddo.DD_Order_ID

         -- Product information
         INNER JOIN M_Product p ON ddol.M_Product_ID = p.M_Product_ID
         INNER JOIN M_Product_Trl pt
                    ON ddol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language

         -- UOM information
         INNER JOIN C_UOM uom ON ddol.C_UOM_ID = uom.C_UOM_ID
         INNER JOIN C_UOM_Trl uomt ON ddol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language

         -- Locator To information
         INNER JOIN m_locator loc_to ON ddol.m_locatorto_id = loc_to.m_locator_id

          -- Sales Order information
         LEFT JOIN C_OrderLine ol ON ddol.C_OrderLineSO_ID = ol.C_OrderLine_ID
         LEFT JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
         LEFT JOIN c_bpartner bp ON o.c_bpartner_id = bp.c_bpartner_id

         -- Production Order information
         LEFT JOIN pp_order pp ON ddo.forward_pp_order_id = pp.pp_order_id

         -- QR Code information
         INNER JOIN m_hu_qrcode_assignment qr_assign ON qr_assign.m_hu_id = hu.m_hu_id
         INNER JOIN M_HU_QRCode qr ON qr_assign.m_hu_qrcode_id = qr.m_hu_qrcode_id

WHERE hu.M_Locator_ID = p_M_Locator_ID
  AND hu.HUStatus IN ('A', 'S', 'I')
ORDER BY hu.m_hu_id, ddo.documentno

$$
    LANGUAGE SQL STABLE
;