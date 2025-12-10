DROP FUNCTION IF EXISTS m_hu_trace_report_retailer(numeric)
;

CREATE FUNCTION m_hu_trace_report_retailer(p_ad_pinstance_id numeric)
    RETURNS TABLE
            (
                lotnumber          character varying,
                hutracetype        character varying,
                product            character varying,
                "InOut"            character varying,
                documentdate       timestamp with time zone,
                qty                numeric,
                uom                character varying,
                mhd                character varying,
                subproducer        character varying,
                netweight          character varying,
                grossweight        character varying,
                bpvalue            character varying,
                bpname             character varying,
                bpartneraddress    text,
                countryname        character varying,
                orderdocumentno    character varying,
                invoicedocumentno  character varying,
                priceactual        numeric,
                linenetamt         numeric,
                paymentterm        character varying,
                shippername        character varying,
                warehousename      character varying,
                prod_stock         numeric,
                traceid            numeric,
                reportdate         character varying
            )
    STABLE
    LANGUAGE sql
AS
$$

    -- Current Stock
SELECT DISTINCT t.LotNumber                          AS LotNumber,
                'Current Stock'                      AS HUTraceType,
                p.value || '_' || p.name             AS Product,
                NULL                                 AS InOut,
                NOW()                                AS DocumentDate,
                getcurrentstoragestock(t.m_product_id,
                                       t.c_uom_id,
                                       1000017, -- Lot-Nummer
                                       t.lotnumber,
                                       t.ad_client_id,
                                       t.ad_org_id
                )                                    AS Qty,
                u.uomsymbol                          AS UOM,
                NULL::varchar                        AS mhd,
                NULL::varchar                        AS subproducer,
                NULL::varchar                        AS netweight,
                NULL::varchar                        AS grossweight,
                NULL                                 AS bpvalue,
                NULL                                 AS bpname,
                NULL                                 AS BPartnerAddress,
                NULL                                 AS countryname,
                NULL                                 AS orderdocumentno,
                NULL                                 AS invoicedocumentno,
                NULL::numeric                        AS priceactual,
                NULL::numeric                        AS linenetamt,
                NULL                                 AS paymentterm,
                NULL                                 AS shippername,
                NULL                                 AS warehousename,
                getcurrentstoragestock(t.m_product_id,
                                       t.c_uom_id,
                                       1000017, -- Lot-Nummer
                                       t.lotnumber,
                                       t.ad_client_id,
                                       t.ad_org_id
                )                                    AS prod_stock,
                NULL::numeric                        AS traceid,
                TO_CHAR(NOW(), 'DD.MM.YYYY HH24:MI') AS reportdate

FROM M_HU_Trace t
         JOIN M_Product p ON t.m_product_id = p.m_product_id
         JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id

WHERE t.hutracetype IN ('MATERIAL_RECEIPT', 'MATERIAL_SHIPMENT', 'MATERIAL_INVENTORY')
  AND EXISTS (SELECT 1
              FROM T_Selection s
              WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                AND s.T_Selection_ID = t.m_hu_trace_id)


UNION ALL

-- Material Receipt
(SELECT DISTINCT ON (t.m_inout_ID) t.LotNumber                                                                AS LotNumber,
                                   t.hutracetype                                                              AS HUTraceType,
                                   p.value || '_' || p.name                                                   AS Product,
                                   io.documentno                                                              AS InOut,
                                   io.movementdate                                                            AS DocumentDate,
                                   CASE WHEN dt.isSOTrx = 'Y' THEN -1 ELSE 1 END * (SELECT SUM(uomconvert(p_m_product_id := p.m_product_id,
                                                                                                          p_c_uom_from_id := iol.c_uom_id,
                                                                                                          p_c_uom_to_id := p.c_uom_id,
                                                                                                          p_qty := iol.movementqty))
                                                                                    FROM metasfresh.public.m_inoutline iol
                                                                                    WHERE io.m_inout_id = iol.m_inout_id
                                                                                      AND iol.m_product_id = p.m_product_id
                                                                                      AND EXISTS (SELECT 1
                                                                                                  FROM m_hu_assignment hua
                                                                                                           JOIN M_HU_Attribute huat
                                                                                                                ON hua.m_hu_id = huat.m_hu_id
                                                                                                  WHERE hua.ad_table_id = get_table_id('M_InOutLine')
                                                                                                    AND hua.record_id = iol.m_inoutline_id
                                                                                                    AND huat.m_attribute_id = 1000017
                                                                                                    AND ((t.lotnumber IS NOT NULL AND
                                                                                                          huat.value = t.lotnumber)
                                                                                                      OR (t.lotnumber IS NULL AND huat.value IS NULL)
                                                                                                      )))     AS Qty,
                                   u.uomsymbol                                                                AS UOM,
                                   (SELECT TO_CHAR(valuedate, 'DD.MM.YYYY')
                                    FROM m_hu_attribute mhd
                                    WHERE mhd.m_hu_id = t.m_hu_id
                                      AND mhd.m_attribute_id = 540020::numeric)                               AS mhd,
                                   (SELECT value
                                    FROM m_hu_attribute subprod
                                    WHERE subprod.m_hu_id = t.m_hu_id
                                      AND subprod.m_attribute_id = 540017::numeric)                           AS subproducer,
                                   (SELECT value
                                    FROM m_hu_attribute nw
                                    WHERE nw.m_hu_id = t.m_hu_id
                                      AND nw.m_attribute_id = 540004::numeric)                                AS netweight,
                                   (SELECT value
                                    FROM m_hu_attribute gw
                                    WHERE gw.m_hu_id = t.m_hu_id
                                      AND gw.m_attribute_id = 540005::numeric)                                AS grossweight,
                                   bp.value                                                                   AS bpvalue,
                                   bp.name                                                                    AS bpname,
                                   io.BPartnerAddress                                                         AS BPartnerAddress,
                                   (SELECT c.name
                                    FROM C_BPartner_Location bpl
                                             JOIN C_Location l ON bpl.c_location_id = l.c_location_id
                                             JOIN C_Country c ON l.c_country_id = c.c_country_id
                                    WHERE bpl.c_bpartner_location_id = io.c_bpartner_location_id)             AS countryname,
                                   (SELECT o.documentno FROM C_Order o WHERE o.c_order_id = io.c_order_id)    AS orderdocumentno,
                                   NULL                                                                       AS invoicedocumentno,
                                   NULL::numeric                                                              AS priceactual,
                                   NULL::numeric                                                              AS linenetamt,
                                   NULL                                                                       AS paymentterm,
                                   (SELECT sh.name FROM M_Shipper sh WHERE sh.m_shipper_id = io.m_shipper_id) AS shippername,
                                   (SELECT w.name FROM M_Warehouse w WHERE w.m_warehouse_id = io.m_warehouse_id) AS warehousename,
                                   getcurrentstoragestock(t.m_product_id,
                                                          t.c_uom_id,
                                                          1000017, -- Lot-Nummer
                                                          t.lotnumber,
                                                          t.ad_client_id,
                                                          t.ad_org_id
                                   )                                                                          AS prod_stock,
                                   t.m_hu_trace_id                                                            AS traceid,
                                   TO_CHAR(NOW(), 'DD.MM.YYYY HH24:MI')                                       AS reportdate

 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
          LEFT JOIN C_DocType dt ON io.c_doctype_id = dt.c_doctype_id
          LEFT JOIN c_bpartner bp ON io.c_bpartner_id = bp.c_bpartner_id

 WHERE t.hutracetype IN ('MATERIAL_RECEIPT')
   AND io.docstatus IN ('CO', 'CL')
   AND EXISTS (SELECT 1
               FROM T_Selection s
               WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                 AND s.T_Selection_ID = t.m_hu_trace_id))


UNION ALL

-- Material Shipment
(SELECT t.LotNumber                                                                  AS LotNumber,
        t.hutracetype                                                                AS HUTraceType,
        p.value || '_' || p.name                                                     AS Product,
        io.documentno                                                                AS InOut,
        io.movementdate                                                              AS DocumentDate,
        CASE WHEN dt.isSOTrx = 'Y' THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
        u.uomsymbol                                                                  AS UOM,
        (SELECT TO_CHAR(valuedate, 'DD.MM.YYYY')
         FROM m_hu_attribute mhd
         WHERE mhd.m_hu_id = t.m_hu_id
           AND mhd.m_attribute_id = 540020::numeric)                                 AS mhd,
        (SELECT value
         FROM m_hu_attribute subprod
         WHERE subprod.m_hu_id = t.m_hu_id
           AND subprod.m_attribute_id = 540017::numeric)                             AS subproducer,
        (SELECT value
         FROM m_hu_attribute nw
         WHERE nw.m_hu_id = t.m_hu_id
           AND nw.m_attribute_id = 540004::numeric)                                  AS netweight,
        (SELECT value
         FROM m_hu_attribute gw
         WHERE gw.m_hu_id = t.m_hu_id
           AND gw.m_attribute_id = 540005::numeric)                                  AS grossweight,
        bp.value                                                                     AS bpvalue,
        bp.name                                                                      AS bpname,
        io.BPartnerAddress                                                           AS BPartnerAddress,
        (SELECT c.name
         FROM C_BPartner_Location bpl
                  JOIN C_Location l ON bpl.c_location_id = l.c_location_id
                  JOIN C_Country c ON l.c_country_id = c.c_country_id
         WHERE bpl.c_bpartner_location_id = io.c_bpartner_location_id)               AS countryname,
        (SELECT o.documentno FROM C_Order o WHERE o.c_order_id = io.c_order_id)      AS orderdocumentno,
        (SELECT STRING_AGG(DISTINCT inv.documentno, ', ')
         FROM C_Invoice inv
                  JOIN C_InvoiceLine invl ON inv.c_invoice_id = invl.c_invoice_id
                  JOIN M_InOutLine iol ON invl.m_inoutline_id = iol.m_inoutline_id
         WHERE iol.m_inout_id = io.m_inout_id
           AND inv.docstatus IN ('CO', 'CL'))                                        AS invoicedocumentno,
        (SELECT AVG(invl.priceactual)
         FROM C_Invoice inv
                  JOIN C_InvoiceLine invl ON inv.c_invoice_id = invl.c_invoice_id
                  JOIN M_InOutLine iol ON invl.m_inoutline_id = iol.m_inoutline_id
         WHERE iol.m_inout_id = io.m_inout_id
           AND inv.docstatus IN ('CO', 'CL')
           AND iol.m_product_id = t.m_product_id)                                    AS priceactual,
        (SELECT SUM(invl.linenetamt)
         FROM C_Invoice inv
                  JOIN C_InvoiceLine invl ON inv.c_invoice_id = invl.c_invoice_id
                  JOIN M_InOutLine iol ON invl.m_inoutline_id = iol.m_inoutline_id
         WHERE iol.m_inout_id = io.m_inout_id
           AND inv.docstatus IN ('CO', 'CL')
           AND iol.m_product_id = t.m_product_id)                                    AS linenetamt,
        (SELECT pt.name
         FROM C_Invoice inv
                  JOIN C_PaymentTerm pt ON inv.c_paymentterm_id = pt.c_paymentterm_id
                  JOIN C_InvoiceLine invl ON inv.c_invoice_id = invl.c_invoice_id
                  JOIN M_InOutLine iol ON invl.m_inoutline_id = iol.m_inoutline_id
         WHERE iol.m_inout_id = io.m_inout_id
           AND inv.docstatus IN ('CO', 'CL')
         LIMIT 1)                                                                    AS paymentterm,
        (SELECT sh.name FROM M_Shipper sh WHERE sh.m_shipper_id = io.m_shipper_id)   AS shippername,
        (SELECT w.name FROM M_Warehouse w WHERE w.m_warehouse_id = io.m_warehouse_id) AS warehousename,
        getcurrentstoragestock(t.m_product_id,
                               t.c_uom_id,
                               1000017, -- Lot-Nummer
                               t.lotnumber,
                               t.ad_client_id,
                               t.ad_org_id
        )                                                                            AS prod_stock,
        t.m_hu_trace_id                                                              AS traceid,
        TO_CHAR(NOW(), 'DD.MM.YYYY HH24:MI')                                         AS reportdate

 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_InOut io ON t.m_inout_id = io.m_inout_id
          LEFT JOIN C_DocType dt ON io.c_doctype_id = dt.c_doctype_id
          LEFT JOIN c_bpartner bp ON io.c_bpartner_id = bp.c_bpartner_id

 WHERE t.hutracetype IN ('MATERIAL_SHIPMENT')
   AND io.docstatus IN ('CO', 'CL')
   AND EXISTS (SELECT 1
               FROM T_Selection s
               WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                 AND s.T_Selection_ID = t.m_hu_trace_id))


UNION ALL

-- Material Inventory
(SELECT t.LotNumber                                                                         AS LotNumber,
        t.hutracetype                                                                       AS HUTraceType,
        p.value || '_' || p.name                                                            AS Product,
        i.documentno                                                                        AS InOut,
        i.movementdate                                                                      AS DocumentDate,
        CASE WHEN i.C_Doctype_ID = 540948 THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision) AS Qty,
        u.uomsymbol                                                                         AS UOM,
        (SELECT TO_CHAR(valuedate, 'DD.MM.YYYY')
         FROM m_hu_attribute mhd
         WHERE mhd.m_hu_id = t.m_hu_id
           AND mhd.m_attribute_id = 540020::numeric)                                        AS mhd,
        (SELECT value
         FROM m_hu_attribute subprod
         WHERE subprod.m_hu_id = t.m_hu_id
           AND subprod.m_attribute_id = 540017::numeric)                                    AS subproducer,
        (SELECT value
         FROM m_hu_attribute nw
         WHERE nw.m_hu_id = t.m_hu_id
           AND nw.m_attribute_id = 540004::numeric)                                         AS netweight,
        (SELECT value
         FROM m_hu_attribute gw
         WHERE gw.m_hu_id = t.m_hu_id
           AND gw.m_attribute_id = 540005::numeric)                                         AS grossweight,
        NULL                                                                                AS bpvalue,
        NULL                                                                                AS bpname,
        NULL                                                                                AS BPartnerAddress,
        NULL                                                                                AS countryname,
        NULL                                                                                AS orderdocumentno,
        NULL                                                                                AS invoicedocumentno,
        NULL::numeric                                                                       AS priceactual,
        NULL::numeric                                                                       AS linenetamt,
        NULL                                                                                AS paymentterm,
        NULL                                                                                AS shippername,
        NULL                                                                                AS warehousename,
        getcurrentstoragestock(t.m_product_id,
                               t.c_uom_id,
                               1000017, -- Lot-Nummer
                               t.lotnumber,
                               t.ad_client_id,
                               t.ad_org_id
        )                                                                                   AS prod_stock,
        t.m_hu_trace_id                                                                     AS traceid,
        TO_CHAR(NOW(), 'DD.MM.YYYY HH24:MI')                                                AS reportdate

 FROM M_HU_Trace t
          JOIN M_Product p
               ON t.m_product_id = p.m_product_id
          JOIN C_UOM u ON t.C_UOM_ID = u.c_uom_id
          LEFT JOIN M_Inventory i ON t.M_Inventory_ID = i.m_inventory_id

 WHERE t.hutracetype = 'MATERIAL_INVENTORY'
   AND i.docstatus IN ('CO', 'CL')
   AND EXISTS (SELECT 1
               FROM T_Selection s
               WHERE s.AD_PInstance_ID = p_AD_PInstance_ID
                 AND s.T_Selection_ID = t.m_hu_trace_id))

ORDER BY LotNumber, HUTraceType, DocumentDate;

$$
;

ALTER FUNCTION m_hu_trace_report_retailer(numeric) OWNER TO metasfresh
;
