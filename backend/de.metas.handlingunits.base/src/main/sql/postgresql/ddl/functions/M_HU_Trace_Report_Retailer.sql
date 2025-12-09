drop function if exists m_hu_trace_report_retailer(numeric);

create function m_hu_trace_report_retailer(p_ad_pinstance_id numeric)
    returns TABLE
            (
                lotnumber           character varying,
                hutracetype         character varying,
                product             character varying,
                inout               character varying,
                movementdate        timestamp with time zone,
                qty                 numeric,
                uom                 character varying,
                recipient_no        character varying,
                recipient_name      character varying,
                bpartneraddress     text,
                sales_order_no      character varying,
                invoice_no          character varying,
                shipper             character varying,
                delivery_date       character varying,
                receipt_date        character varying,
                current_stock       numeric,
                traceid             numeric,
                reportdate          character varying
            )
    stable
    language sql
as
$$

-- Current Stock
SELECT DISTINCT t.LotNumber                                                                                     AS LotNumber,
                'Current Stock'                                                                                 AS HUTraceType,
                p.value || '_' || p.name                                                                        AS Product,
                NULL                                                                                            AS InOut,
                NOW()                                                                                           AS MovementDate,
                getcurrentstoragestock(t.m_product_id,
                                       t.c_uom_id,
                                       1000017, -- Lot-Nummer
                                       t.lotnumber,
                                       t.ad_client_id,
                                       t.ad_org_id
                    )                                                                                           AS Qty,
                u.uomsymbol                                                                                     AS UOM,
                NULL                                                                                            AS recipient_no,
                NULL                                                                                            AS recipient_name,
                NULL                                                                                            AS BPartnerAddress,
                NULL                                                                                            AS sales_order_no,
                NULL                                                                                            AS invoice_no,
                NULL                                                                                            AS shipper,
                NULL::varchar                                                                                   AS delivery_date,
                NULL::varchar                                                                                   AS receipt_date,
                getcurrentstoragestock(t.m_product_id,
                                       t.c_uom_id,
                                       1000017, -- Lot-Nummer
                                       t.lotnumber,
                                       t.ad_client_id,
                                       t.ad_org_id
                    )                                                                                           AS current_stock,
                NULL::numeric                                                                                   AS traceid,
                to_char(now(), 'DD.MM.YYYY HH24:MI')                                                            AS reportdate

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
(SELECT DISTINCT ON (t.m_inout_ID)
        t.LotNumber                                                                                             AS LotNumber,
        t.hutracetype                                                                                           AS HUTraceType,
        p.value || '_' || p.name                                                                                AS Product,
        io.documentno                                                                                           AS InOut,
        io.movementdate                                                                                         AS MovementDate,
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
                                                                           )))                                  AS Qty,
        u.uomsymbol                                                                                             AS UOM,
        bp.value                                                                                                AS recipient_no,
        bp.name                                                                                                 AS recipient_name,
        io.BPartnerAddress                                                                                      AS BPartnerAddress,
        (SELECT o.documentno FROM C_Order o WHERE o.c_order_id = io.c_order_id)                                AS sales_order_no,
        NULL                                                                                                    AS invoice_no,
        (SELECT sh.name FROM M_Shipper sh WHERE sh.m_shipper_id = io.m_shipper_id)                             AS shipper,
        to_char(io.movementdate, 'DD.MM.YYYY')                                                                  AS delivery_date,
        to_char(io.movementdate, 'DD.MM.YYYY')                                                                  AS receipt_date,
        getcurrentstoragestock(t.m_product_id,
                               t.c_uom_id,
                               1000017, -- Lot-Nummer
                               t.lotnumber,
                               t.ad_client_id,
                               t.ad_org_id
            )                                                                                                   AS current_stock,
        t.m_hu_trace_id                                                                                         AS traceid,
        to_char(now(), 'DD.MM.YYYY HH24:MI')                                                                    AS reportdate

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
(SELECT t.LotNumber                                                                                             AS LotNumber,
        t.hutracetype                                                                                           AS HUTraceType,
        p.value || '_' || p.name                                                                                AS Product,
        io.documentno                                                                                           AS InOut,
        io.movementdate                                                                                         AS MovementDate,
        CASE WHEN dt.isSOTrx = 'Y' THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision)                            AS Qty,
        u.uomsymbol                                                                                             AS UOM,
        bp.value                                                                                                AS recipient_no,
        bp.name                                                                                                 AS recipient_name,
        io.BPartnerAddress                                                                                      AS BPartnerAddress,
        (SELECT o.documentno FROM C_Order o WHERE o.c_order_id = io.c_order_id)                                AS sales_order_no,
        (SELECT STRING_AGG(DISTINCT inv.documentno, ', ')
         FROM C_Invoice inv
                  JOIN C_InvoiceLine invl ON inv.c_invoice_id = invl.c_invoice_id
                  JOIN M_InOutLine iol ON invl.m_inoutline_id = iol.m_inoutline_id
         WHERE iol.m_inout_id = io.m_inout_id
           AND inv.docstatus IN ('CO', 'CL'))                                                                   AS invoice_no,
        (SELECT sh.name FROM M_Shipper sh WHERE sh.m_shipper_id = io.m_shipper_id)                             AS shipper,
        to_char(io.movementdate, 'DD.MM.YYYY')                                                                  AS delivery_date,
        NULL::varchar                                                                                           AS receipt_date,
        getcurrentstoragestock(t.m_product_id,
                               t.c_uom_id,
                               1000017, -- Lot-Nummer
                               t.lotnumber,
                               t.ad_client_id,
                               t.ad_org_id
            )                                                                                                   AS current_stock,
        t.m_hu_trace_id                                                                                         AS traceid,
        to_char(now(), 'DD.MM.YYYY HH24:MI')                                                                    AS reportdate

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
(SELECT t.LotNumber                                                                                             AS LotNumber,
        t.hutracetype                                                                                           AS HUTraceType,
        p.value || '_' || p.name                                                                                AS Product,
        i.documentno                                                                                            AS InOut,
        i.movementdate                                                                                          AS MovementDate,
        CASE WHEN i.C_Doctype_ID = 540948 THEN -1 ELSE 1 END * ROUND(t.qty, u.stdprecision)                     AS Qty,
        u.uomsymbol                                                                                             AS UOM,
        NULL                                                                                                    AS recipient_no,
        NULL                                                                                                    AS recipient_name,
        NULL                                                                                                    AS BPartnerAddress,
        NULL                                                                                                    AS sales_order_no,
        NULL                                                                                                    AS invoice_no,
        NULL                                                                                                    AS shipper,
        NULL::varchar                                                                                           AS delivery_date,
        NULL::varchar                                                                                           AS receipt_date,
        getcurrentstoragestock(t.m_product_id,
                               t.c_uom_id,
                               1000017, -- Lot-Nummer
                               t.lotnumber,
                               t.ad_client_id,
                               t.ad_org_id
            )                                                                                                   AS current_stock,
        t.m_hu_trace_id                                                                                         AS traceid,
        to_char(now(), 'DD.MM.YYYY HH24:MI')                                                                    AS reportdate

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

ORDER BY LotNumber, HUTraceType, MovementDate;

$$;

alter function m_hu_trace_report_retailer(numeric) owner to metasfresh;
