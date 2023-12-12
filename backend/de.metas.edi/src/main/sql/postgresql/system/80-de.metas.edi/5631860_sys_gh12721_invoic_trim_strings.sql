
-- View: EDI_Cctop_INVOIC_v

DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v;
CREATE OR REPLACE VIEW EDI_Cctop_INVOIC_v AS
SELECT
    i.C_Invoice_ID AS EDI_Cctop_INVOIC_v_ID
     , i.C_Invoice_ID
     , i.C_Order_ID
     , REGEXP_REPLACE(i.DocumentNo, '\s+$', '') AS Invoice_DocumentNo
     , i.DateInvoiced
     , (CASE
            WHEN REGEXP_REPLACE(i.POReference::TEXT, '\s+$', '') <> ''::TEXT AND i.POReference IS NOT NULL /* task 09182: if there is a POReference, then export it */
                THEN REGEXP_REPLACE(i.POReference, '\s+$', '')
                ELSE NULL::CHARACTER VARYING
        END) AS POReference
     , (CASE
            WHEN COALESCE(i.DateOrdered, o.DateOrdered) IS NOT NULL /* task 09182: if there is an orderDate, then export it */
                THEN COALESCE(i.DateOrdered, o.DateOrdered)
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END) AS DateOrdered
     , (CASE dt.DocBaseType
            WHEN 'ARI'::BPChar THEN (CASE
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='' THEN '380'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='AQ' THEN '383'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='AP' THEN '84'::TEXT
                                         ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
            WHEN 'ARC'::BPChar THEN (CASE

                /* CQ => "GS - Lieferdifferenz"; CS => "GS - Retoure" */
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) IN ('CQ','CS') THEN '381'
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='CR' THEN '83'::TEXT
                                                                                                                        ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
                               ELSE 'ERROR EAN_DocType'::TEXT
        END) AS EANCOM_DocType
     , i.GrandTotal
     , i.TotalLines
    /* IF docSubType is CS, the we don't reference the original shipment*/
     , CASE WHEN dt.DocSubType='CS' THEN NULL ELSE COALESCE(desadv.MovementDate, s.MovementDate) END AS MovementDate
     , CASE WHEN dt.DocSubType='CS' THEN NULL ELSE COALESCE(desadv.DocumentNo, s.DocumentNo) END AS Shipment_DocumentNo
     , t.TotalVAT
     , t.TotalTaxBaseAmt
     , COALESCE(rbp.EdiInvoicRecipientGLN, rl.GLN) AS ReceiverGLN
     , rl.C_BPartner_Location_ID
     , (
    SELECT DISTINCT ON (REGEXP_REPLACE(sl.GLN, '\s+$', ''))
        REGEXP_REPLACE(sl.GLN, '\s+$', '') as GLN
    FROM C_BPartner_Location sl
    WHERE true
      AND sl.C_BPartner_ID = sp.C_BPartner_ID
      AND sl.IsRemitTo = 'Y'::BPChar
      AND sl.GLN IS NOT NULL
      AND sl.IsActive = 'Y'::BPChar
) AS SenderGLN
     , REGEXP_REPLACE(sp.VATaxId, '\s+$', '') as VATaxId
     , c.ISO_Code
     , REGEXP_REPLACE(i.CreditMemoReason, '\s+$', '') as CreditMemoReason
     , (
    SELECT
        REGEXP_REPLACE(Name, '\s+$', '') as Name
    FROM AD_Ref_List
    WHERE AD_Reference_ID=540014 -- C_CreditMemo_Reason
      AND Value=i.CreditMemoReason
) AS CreditMemoReasonText
     , (select CASE
                   WHEN array_length(array_agg(DISTINCT ol.invoicableqtybasedon), 1) = 1
                       THEN (array_agg(DISTINCT ol.invoicableqtybasedon))[1]
                       ELSE NULL
               END
        from c_orderline ol
        where ol.c_order_id=o.c_order_id) as invoicableqtybasedon
     , cc.CountryCode
     , cc.CountryCode_3Digit
     , cc.CountryCode as AD_Language
     , i.AD_Client_ID , i.AD_Org_ID, i.Created, i.CreatedBy, i.Updated, i.UpdatedBy, i.IsActive
FROM C_Invoice i
         LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypetarget_ID
         LEFT JOIN C_Order o ON o.C_Order_ID=i.C_Order_ID
         LEFT JOIN EDI_Desadv desadv ON desadv.EDI_Desadv_ID = o.EDI_Desadv_ID -- note that we prefer the EDI_Desadv over M_InOut. there might be multiple InOuts, all with the same POReference and the same EDI_Desadv_ID
         LEFT JOIN LATERAL (
    SELECT
        io.DocumentNo, io.MovementDate
    FROM M_InOut io
    WHERE io.C_Order_ID=o.C_Order_ID AND io.DocStatus IN ('CO', 'CL')
    ORDER BY io.Created
    LIMIT 1
    ) s ON true -- for the case of missing EDI_Desadv, we still get the first M_InOut; DESADV can be switched off for individual C_BPartners
         LEFT JOIN C_BPartner rbp ON rbp.C_BPartner_ID = i.C_BPartner_ID
         LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
         LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
         LEFT JOIN C_Country cc ON cc.C_Country_ID = l.C_Country_ID
         LEFT JOIN (
    SELECT
        C_InvoiceTax.C_Invoice_ID
         , SUM(C_InvoiceTax.TaxAmt) AS TotalVAT
         , SUM(C_InvoiceTax.TaxBaseAmt) AS TotalTaxBaseAmt
    FROM C_InvoiceTax
    GROUP BY C_InvoiceTax.C_Invoice_ID
) t ON t.C_Invoice_ID = i.C_Invoice_ID
         LEFT JOIN C_BPartner sp ON sp.AD_OrgBP_ID = i.AD_Org_ID
;



-- View: edi_cctop_000_v

DROP VIEW IF EXISTS edi_cctop_000_v;
CREATE OR REPLACE VIEW edi_cctop_000_v AS
SELECT
    l.c_bpartner_location_id AS edi_cctop_000_v_id,
    l.c_bpartner_location_id,
    REGEXP_REPLACE(bp.EdiInvoicRecipientGLN, '\s+$', '') AS EdiInvoicRecipientGLN,
    l.ad_client_id,
    l.ad_org_id,
    l.created,
    l.createdby,
    l.updated,
    l.updatedby,
    l.isactive
FROM c_bpartner_location l
         INNER JOIN C_BPartner bp ON bp.C_BPartner_ID=l.C_BPartner_ID;

-- View: edi_cctop_111_v

DROP VIEW IF EXISTS edi_cctop_111_v;
CREATE OR REPLACE VIEW edi_cctop_111_v AS
SELECT o.c_order_id AS edi_cctop_111_v_id, o.c_order_id,
       CASE
           WHEN REGEXP_REPLACE(o.poreference::text, '\s+$', '') <> ''::text AND o.poreference IS NOT NULL AND o.dateordered IS NOT NULL THEN REGEXP_REPLACE(o.poreference, '\s+$', '')
                                                                                                                  ELSE NULL::character varying
       END AS poreference,
       CASE
           WHEN REGEXP_REPLACE(o.poreference::text, '\s+$', '') <> ''::text AND o.poreference IS NOT NULL AND o.dateordered IS NOT NULL THEN o.dateordered
                                                                                                                  ELSE NULL::timestamp without time zone
       END AS dateordered,
       s.m_inout_id,
       s.documentno AS shipment_documentno, --// *not* send in edi-marshal-compudata-fresh.ftl, so won't do harm; TODO replace with EDI_DESADV
       s.movementdate,
       o.ad_client_id,
       o.ad_org_id,
       o.created,
       o.createdby,
       o.updated,
       o.updatedby,
       o.isactive
FROM c_order o
         JOIN m_inout s ON s.c_order_id = o.c_order_id;



-- View: public.edi_cctop_invoic_500_v

DROP VIEW IF EXISTS public.edi_cctop_invoic_500_v;
CREATE OR REPLACE VIEW public.edi_cctop_invoic_500_v AS
SELECT
    sum(il.qtyEntered) AS QtyInvoiced,
    CASE
        WHEN u.x12de355 = 'TU' THEN 'PCE'
                               ELSE u.x12de355
    END AS eancom_uom, /* C_InvoiceLine's UOM */
    CASE
        WHEN u_ordered.x12de355 IN ('TU', 'COLI') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
                                                  ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced)
    END AS QtyInvoicedInOrderedUOM,
    u_ordered.x12de355 AS eancom_ordered_uom, /* leaving out that CASE-mumbo-jumbo from the other uoms; IDK if it's needed (anymore) */
    min(il.c_invoiceline_id) AS edi_cctop_invoic_500_v_id,
    sum(il.linenetamt) AS linenetamt,
    min(il.line) AS line,
    il.c_invoice_id,
    il.c_invoice_id AS edi_cctop_invoic_v_id,
    il.priceactual,
    il.pricelist,
    ol.invoicableqtybasedon,
    REGEXP_REPLACE(pp.UPC, '\s+$', '') AS UPC_CU,
    REGEXP_REPLACE(pp.EAN_CU, '\s+$', '') AS EAN_CU,
    REGEXP_REPLACE(p.value, '\s+$', '') AS Value,
    REGEXP_REPLACE(pp.productno, '\s+$', '') AS vendorproductno,
    substr(p.name, 1, 35) AS name,
    substr(p.name, 36, 70) AS name2,
    t.rate,
    CASE /* be lenient if il.price_uom_id is not set; see https://github.com/metasfresh/metasfresh/issues/6458 */
        WHEN COALESCE(u_price.x12de355, u.x12de355) = 'TU' THEN 'PCE'
                                                           ELSE COALESCE(u_price.x12de355, u.x12de355)
    END AS eancom_price_uom /* C_InvoiceLine's Price-UOM */,
    CASE
        WHEN t.rate = 0 THEN 'Y'
                        ELSE ''
    END AS taxfree,
    c.iso_code,
    il.ad_client_id,
    il.ad_org_id,
    min(il.created) AS created,
    min(il.createdby)::numeric(10,0) AS createdby,
    max(il.updated) AS updated,
    max(il.updatedby)::numeric(10,0) AS updatedby,
    il.isactive,
    CASE pc.value
        WHEN 'Leergut' THEN 'P'
                       ELSE ''
    END AS leergut,
    COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''), p.name)::character varying AS productdescription,
    COALESCE(ol.line, il.line) AS orderline,
    COALESCE(NULLIF(o.poreference, ''), i.poreference)::character varying(40) AS orderporeference,
    il.c_orderline_id,
    sum(il.taxamtinfo) AS taxamtinfo,
    REGEXP_REPLACE(pip.GTIN, '\s+$', '') AS GTIN,
    REGEXP_REPLACE(pip.EAN_TU, '\s+$', '') AS EAN_TU,
    REGEXP_REPLACE(pip.UPC, '\s+$', '') AS UPC_TU
FROM c_invoiceline il
         LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id AND ol.isactive = 'Y'
         LEFT JOIN M_HU_PI_Item_Product pip ON ol.M_HU_PI_Item_Product_ID=pip.M_HU_PI_Item_Product_ID
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN c_uom u_ordered ON u_ordered.c_uom_id = COALESCE(ol.c_uom_id, il.c_uom_id)
         LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
         LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
         LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
         LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
         LEFT JOIN c_bpartner_product pp ON pp.c_bpartner_id = i.c_bpartner_id AND pp.m_product_id = il.m_product_id AND pp.isactive = 'Y'
         LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
         LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id
         LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
WHERE true
  AND il.m_product_id IS NOT NULL
  AND il.isactive = 'Y'
  AND il.qtyentered <> 0
GROUP BY
    il.c_invoice_id,
    il.priceactual,
    il.pricelist,
    ol.InvoicableQtyBasedOn,
    pp.UPC,
    pp.EAN_CU,
    p.value,
    pp.productno,
    (substr(p.name, 1, 35)),
    (substr(p.name, 36, 70)),
    t.rate,
    (CASE
         WHEN u.x12de355 = 'TU' THEN 'PCE'
                                ELSE u.x12de355
     END),
    (CASE /* be lenient if il.price_uom_id is not set; see https://github.com/metasfresh/metasfresh/issues/6458 */
         WHEN COALESCE(u_price.x12de355, u.x12de355) = 'TU' THEN 'PCE'
                                                            ELSE COALESCE(u_price.x12de355, u.x12de355)
     END),
    (CASE
         WHEN u_ordered.x12de355 IN ('TU', 'COLI') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
                                                   ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced)
     END),
    u_ordered.x12de355,
    (CASE
         WHEN t.rate = 0 THEN 'Y'
                         ELSE ''
     END),
    c.iso_code,
    il.ad_client_id,
    il.ad_org_id,
    il.isactive,
    (CASE pc.value
         WHEN 'Leergut' THEN 'P'
                        ELSE ''
     END),
    (COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''), p.name)),
    (COALESCE(NULLIF(o.poreference, ''), i.poreference)),
    (COALESCE(ol.line, il.line)),
    il.c_orderline_id,
    pip.UPC, pip.GTIN, pip.EAN_TU
ORDER BY COALESCE(ol.line, il.line);

COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
';


-- View: edi_cctop_901_991_v

DROP VIEW IF EXISTS edi_cctop_901_991_v;
CREATE OR REPLACE VIEW edi_cctop_901_991_v AS
SELECT i.c_invoice_id AS edi_cctop_901_991_v_id, i.c_invoice_id, i.c_invoice_id AS edi_cctop_invoic_v_id,
       it.taxamt + it.taxbaseamt AS totalamt,
       it.taxamt,
       it.taxbaseamt,
       t.rate, i.ad_client_id, i.ad_org_id, i.created, i.createdby, i.updated, i.updatedby, i.isactive,
       REGEXP_REPLACE(rn.referenceNo, '\s+$', '') AS ESRReferenceNumber
FROM c_invoice i
         LEFT JOIN c_invoicetax it ON it.c_invoice_id = i.c_invoice_id
         LEFT JOIN c_tax t ON t.c_tax_id = it.c_tax_id
         LEFT JOIN (
    SELECT sit.c_invoice_id, count(DISTINCT st.rate) AS tax_count
    FROM c_invoicetax sit
             LEFT JOIN c_tax st ON st.c_tax_id = sit.c_tax_id
    GROUP BY sit.c_invoice_id
) tc ON tc.c_invoice_id = it.c_invoice_id
         LEFT JOIN c_referenceno_doc rnd on rnd.record_id=i.c_invoice_id and rnd.ad_table_id=318 /* C_Invoice DocumentType */
         LEFT JOIN c_referenceno rn on rn.c_referenceno_id=rnd.c_referenceno_id

WHERE tc.tax_count > 0
  -- can either be an ESRReferenceNumber, or we may not have it at all. Regardless, both cases should work.
  AND (rn.c_referenceno_type_id = 540005 OR rnd.c_referenceno_doc_id is null) /* c_referenceno_type_id = 540005 (ESRReferenceNumber) */;


-- View: EDI_Cctop_119_v

DROP VIEW IF EXISTS EDI_Cctop_119_v;
CREATE OR REPLACE VIEW EDI_Cctop_119_v AS
SELECT lookup.C_Invoice_ID       AS EDI_Cctop_119_v_ID,
       lookup.C_Invoice_ID,
       lookup.C_Invoice_ID       AS EDI_Cctop_INVOIC_v_ID,
       lookup.M_InOut_ID,
       pl.C_BPartner_Location_ID,
       REGEXP_REPLACE(pl.GLN, '\s+$', '') AS GLN,
       pl.Phone,
       pl.Fax,
       p.Name,
       p.Name2,
       p.Value,
       p.VATaxID,
       lookup.Vendor_ReferenceNo AS ReferenceNo,
       lookup.Setup_Place_No,
       CASE lookup.Type_V
           WHEN 'ship'::TEXT THEN 'DP'::TEXT
           WHEN 'bill'::TEXT THEN 'IV'::TEXT
           WHEN 'cust'::TEXT THEN 'BY'::TEXT
           WHEN 'vend'::TEXT THEN 'SU'::TEXT
           WHEN 'snum'::TEXT THEN 'SN'::TEXT
                             ELSE 'Error EANCOM_Type'::TEXT
       END                       AS eancom_locationtype,
       l.Address1,
       l.Address2,
       l.Postal,
       l.City,
       c.CountryCode,
       l.AD_Client_ID,
       l.AD_Org_ID,
       l.Created,
       l.CreatedBy,
       l.Updated,
       l.UpdatedBy,
       l.IsActive
FROM (
         SELECT union_lookup.*
         FROM (
                  SELECT DISTINCT 1::INTEGER   AS SeqNo,
                                  'cust'::TEXT AS Type_V,
                                  pl_cust.C_BPartner_Location_ID,
                                  i.C_Invoice_ID,
                                  0::INTEGER   AS M_InOut_ID,
                                  NULL::TEXT   AS Vendor_ReferenceNo,
                                  pl_cust.Setup_Place_No
                  FROM C_Invoice i
                           LEFT JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID
                           LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID = il.C_OrderLine_ID
                           LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
                           LEFT JOIN C_BPartner_Location pl_cust ON pl_cust.C_BPartner_Location_ID =
                                                                    (CASE
                                                                         WHEN o.C_BPartner_Location_ID IS NOT NULL AND o.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN o.C_BPartner_Location_ID
                                                                             ELSE -- Fallback if the C_Invoice is Hand Solo :) (I mean no C_Order)
                                                                             i.C_BPartner_Location_ID
                                                                     END)
                       --
                  UNION
                  --
                  SELECT 2::INTEGER         AS SeqNo,
                         'vend'::TEXT       AS Type_V,
                         pl_vend.C_BPartner_Location_ID,
                         i.C_Invoice_ID,
                         0::INTEGER         AS M_InOut_ID,
                         p_cust.ReferenceNo AS Vendor_ReferenceNo,
                         pl_vend.Setup_Place_No
                  FROM C_Invoice i
                           JOIN C_BPartner p_cust ON p_cust.C_BPartner_ID = i.C_BPartner_ID
                           JOIN C_BPartner p_vend ON p_vend.AD_OrgBP_ID = i.AD_Org_ID
                           JOIN C_BPartner_Location pl_vend ON pl_vend.C_BPartner_ID = p_vend.C_BPartner_ID AND pl_vend.isremitto = 'Y'
                       --
                  UNION
                  --
                  SELECT DISTINCT 3::INTEGER   AS SeqNo,
                                  'ship'::TEXT AS Type_V,
                                  pl_ship.c_bpartner_location_id,
                                  i.C_Invoice_ID,
                                  0::INTEGER   AS M_InOut_ID,
                                  NULL::TEXT   AS Vendor_ReferenceNo,
                                  pl_ship.Setup_Place_No
                  FROM C_Invoice i
                           INNER JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID
                           LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID = il.C_OrderLine_ID
                           LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
                           LEFT JOIN M_InOutline sl ON ( -- try to join directly from C_InvoiceLine
                                                                   sl.M_InOutLine_ID = il.M_InOutLine_ID AND il.M_InOutLine_ID IS NOT NULL AND il.M_InOutLine_ID != 0)
                      -- fallback and try to join from C_OrderLine if (and only if) it's missing in C_InvoiceLine
                      OR (sl.C_OrderLine_ID = il.C_OrderLine_ID AND (il.M_InOutLine_ID IS NULL OR il.M_InOutLine_ID = 0))
                           LEFT JOIN M_InOut s ON s.M_InOut_ID = sl.M_InOut_ID

                           LEFT JOIN C_BPartner_Location pl_ship ON pl_ship.C_BPartner_Location_ID =
                                                                    (CASE -- Could use COALESCE(NULLIF(.., ..), ..) here, but it gets messy, so I prefer CASE
                                                                         WHEN o.HandOver_Location_ID IS NOT NULL AND o.HandOver_Location_ID != 0::INTEGER
                                                                             THEN o.HandOver_Location_ID
                                                                         WHEN s.C_BPartner_Location_ID IS NOT NULL AND s.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN s.C_BPartner_Location_ID
                                                                         WHEN o.DropShip_Location_ID IS NOT NULL AND o.DropShip_Location_ID != 0::INTEGER
                                                                             THEN o.DropShip_Location_ID
                                                                         WHEN o.C_BPartner_Location_ID IS NOT NULL AND o.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN o.C_BPartner_Location_ID
                                                                             ELSE -- Fallback if the C_Invoice is Hand Solo :) (I mean no C_Order)
                                                                             i.C_BPartner_Location_ID
                                                                     END)
                       --
                  UNION
                  --
                  SELECT 4::INTEGER   AS SeqNo,
                         'bill'::TEXT AS Type_V,
                         pl_bill.C_BPartner_Location_ID,
                         i.C_Invoice_ID,
                         0::INTEGER   AS M_InOut_ID,
                         NULL::TEXT   AS Vendor_ReferenceNo,
                         pl_bill.Setup_Place_No
                  FROM C_Invoice i
                           LEFT JOIN C_BPartner_Location pl_bill ON pl_bill.C_BPartner_Location_ID = i.C_BPartner_Location_ID
                       --
                  UNION
                  --
                  SELECT DISTINCT 5::INTEGER   AS SeqNo,
                                  'snum'::TEXT AS Type_V,
                                  pl_snum.C_BPartner_Location_ID,
                                  i.C_Invoice_ID,
                                  s.M_InOut_ID,
                                  NULL::TEXT   AS Vendor_ReferenceNo,
                                  pl_snum.Setup_Place_No
                  FROM C_Invoice i
                           INNER JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID
                           LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID = il.C_OrderLine_ID
                           LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
                           LEFT JOIN M_InOutline sl ON ( -- try to join directly from C_InvoiceLine
                                                                   sl.M_InOutLine_ID = il.M_InOutLine_ID AND il.M_InOutLine_ID IS NOT NULL AND il.M_InOutLine_ID != 0)
                      -- fallback and try to join from C_OrderLine if (and only if) it's missing in C_InvoiceLine
                      OR (sl.C_OrderLine_ID = il.C_OrderLine_ID AND (il.M_InOutLine_ID IS NULL OR il.M_InOutLine_ID = 0))
                           LEFT JOIN M_InOut s ON s.M_InOut_ID = sl.M_InOut_ID
                           LEFT JOIN C_BPartner_Location pl_snum ON pl_snum.C_BPartner_Location_ID =
                                                                    (CASE -- Could use COALESCE(NULLIF(.., ..), ..) here, but it gets messy, so I prefer CASE
                                                                         WHEN s.C_BPartner_Location_ID IS NOT NULL AND s.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN s.C_BPartner_Location_ID
                                                                         WHEN o.DropShip_Location_ID IS NOT NULL AND o.DropShip_Location_ID != 0::INTEGER
                                                                             THEN o.DropShip_Location_ID
                                                                         WHEN o.C_BPartner_Location_ID IS NOT NULL AND o.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN o.C_BPartner_Location_ID
                                                                             ELSE -- Fallback if the C_Invoice is Hand Solo :) (I mean no C_Order)
                                                                             i.C_BPartner_Location_ID
                                                                     END)
              ) union_lookup
         ORDER BY union_lookup.SeqNo, union_lookup.Type_V, union_lookup.C_BPartner_Location_ID, C_Invoice_ID, M_InOut_ID
     ) lookup
         LEFT JOIN C_BPartner_Location pl ON pl.C_BPartner_Location_ID = lookup.C_BPartner_Location_ID
         LEFT JOIN C_BPartner p ON p.C_BPartner_ID = pl.C_BPartner_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = pl.C_Location_ID
         LEFT JOIN C_Country c ON c.C_Country_ID = l.C_Country_ID
WHERE TRUE
  AND p.VATaxID IS NOT NULL
  AND (l.Address1 IS NOT NULL OR l.Address2 IS NOT NULL)
ORDER BY (
          lookup.C_Invoice_ID,
          CASE lookup.Type_V
              WHEN 'ship'::TEXT THEN 'DP'::TEXT
              WHEN 'bill'::TEXT THEN 'IV'::TEXT
              WHEN 'cust'::TEXT THEN 'BY'::TEXT
              WHEN 'vend'::TEXT THEN 'SU'::TEXT
              WHEN 'snum'::TEXT THEN 'SN'::TEXT
                                ELSE 'Error EANCOM_Type'::TEXT
          END);
