-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_cctop_119_v_view.sql and edi_cctop_invoic_v_view.sql
-- INVOIC export: declare the VAT IDs stored on the invoice instead of the partners' current ones.
--   EDI_Cctop_119_v   : the buyer (BY) and invoice (IV) parties show C_Invoice.BuyerVATaxID, the supplier (SU)
--                       party C_Invoice.SellerVATaxID; without a stored value, and for the other parties, the partner's VATaxID.
--   EDI_Cctop_INVOIC_v: the sender VATaxId is C_Invoice.SellerVATaxID, else the VAT ID of the organisation's partner.
-- Both views have dependent views (c_invoice_export_edi_invoic_json_v and others), so the change goes through db_alter_view.

DROP VIEW IF EXISTS EDI_Cctop_119_v$new;

CREATE OR REPLACE VIEW EDI_Cctop_119_v$new AS
SELECT lookup.C_Invoice_ID                                               AS EDI_Cctop_119_v_ID,
       lookup.C_Invoice_ID,
       lookup.C_Invoice_ID                                               AS EDI_Cctop_INVOIC_v_ID,
       lookup.M_InOut_ID,
       pl.C_BPartner_Location_ID,
       COALESCE(lookup.GLN_Override, REGEXP_REPLACE(pl.GLN, '\s+$', '')) AS GLN,
       pl.Phone,
       pl.Fax,
       p.Name,
       p.Name2,
       p.Value,
       /* the buyer (BY) and invoice (IV) parties declare the buyer VAT ID stored on the invoice, the supplier (SU) party the seller VAT ID stored on it; if nothing is stored, and for the other parties, the partner's own VAT ID */
       CASE
           WHEN lookup.Type_V IN ('cust', 'bill') THEN COALESCE(NULLIF(TRIM(inv.BuyerVATaxID), ''), p.VATaxID)
           WHEN lookup.Type_V = 'vend'            THEN COALESCE(NULLIF(TRIM(inv.SellerVATaxID), ''), p.VATaxID)
                                                  ELSE p.VATaxID
       END                                                               AS VATaxID,
       lookup.Vendor_ReferenceNo                                         AS ReferenceNo,
       lookup.SiteName,
       lookup.Setup_Place_No,
       CASE lookup.Type_V
           WHEN 'ship'::TEXT THEN 'DP'::TEXT
           WHEN 'bill'::TEXT THEN 'IV'::TEXT
           WHEN 'cust'::TEXT THEN 'BY'::TEXT
           WHEN 'vend'::TEXT THEN 'SU'::TEXT
           WHEN 'snum'::TEXT THEN 'SN'::TEXT
                             ELSE 'Error EANCOM_Type'::TEXT
       END                                                               AS eancom_locationtype,
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
       l.IsActive,
       CASE lookup.Type_V WHEN 'vend'::text THEN u.Name END              AS Contact
FROM (SELECT union_lookup.*
      FROM (SELECT DISTINCT 1::INTEGER           AS SeqNo,
                            'cust'::TEXT         AS Type_V,
                            pl_cust.C_BPartner_Location_ID,
                            i.C_Invoice_ID,
                            0::INTEGER           AS M_InOut_ID,
                            NULL::TEXT           AS Vendor_ReferenceNo,
                            pl_cust.bpartnername AS SiteName,
                            pl_cust.Setup_Place_No,
                            NULL::TEXT           AS GLN_Override,
                            i.CreatedBy
            FROM C_Invoice i
                     LEFT JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID AND il.ispackagingmaterial = 'N' /* exclude package-lines (no c_orderline_id) to avoid a spurious second 'cust' row via the C_Order fallback; LEFT JOIN keeps the fallback row for line-less invoices */
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
            SELECT 2::INTEGER                                                                                                       AS SeqNo,
                   'vend'::TEXT                                                                                                     AS Type_V,
                   COALESCE(pl_wh.C_BPartner_Location_ID, pl_vend.C_BPartner_Location_ID)                                           AS C_BPartner_Location_ID,
                   i.C_Invoice_ID,
                   0::INTEGER                                                                                                       AS M_InOut_ID,
                   COALESCE(p_wh.ReferenceNo, p_vend.ReferenceNo)                                                                   AS Vendor_ReferenceNo,
                   COALESCE(pl_wh.bpartnername, pl_vend.bpartnername)                                                               AS SiteName,
                   COALESCE(pl_wh.Setup_Place_No, pl_vend.Setup_Place_No)                                                           AS Setup_Place_No,
                   COALESCE(NULLIF(REGEXP_REPLACE(pl_wh.GLN, '\s+$', ''), ''), NULLIF(REGEXP_REPLACE(pl_vend.GLN, '\s+$', ''), '')) AS GLN_Override,
                   i.CreatedBy
            FROM C_Invoice i
                     JOIN C_BPartner p_vend ON p_vend.AD_OrgBP_ID = i.AD_Org_ID
                     JOIN C_BPartner_Location pl_vend ON pl_vend.C_BPartner_ID = p_vend.C_BPartner_ID AND pl_vend.isremitto = 'Y'
                     LEFT JOIN m_inout inout ON inout.m_inout_id = i.m_inout_id
                     LEFT JOIN M_Warehouse wh ON wh.M_Warehouse_ID = inout.M_Warehouse_ID
                     LEFT JOIN C_BPartner p_wh ON p_wh.C_BPartner_ID = wh.C_BPartner_ID
                     LEFT JOIN C_BPartner_Location pl_wh ON pl_wh.C_BPartner_Location_ID = wh.C_BPartner_Location_ID
            --
            UNION
            --
            SELECT DISTINCT 3::INTEGER           AS SeqNo,
                            'ship'::TEXT         AS Type_V,
                            pl_ship.c_bpartner_location_id,
                            i.C_Invoice_ID,
                            0::INTEGER           AS M_InOut_ID,
                            NULL::TEXT           AS Vendor_ReferenceNo,
                            pl_ship.bpartnername AS SiteName,
                            pl_ship.Setup_Place_No,
                            NULL::TEXT           AS GLN_Override,
                            i.CreatedBy
            FROM C_Invoice i
                     INNER JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID AND il.ispackagingmaterial = 'N' /* exclude package-lines that have no c_orderline_id, to avoid two 'ship' rows in conjunction with the "Fallback if the C_Invoice has no C_Order "*/
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
                                                                       ELSE /* Fallback if the C_Invoice has no C_Order */
                                                                       i.C_BPartner_Location_ID
                                                               END)
            --
            UNION
            --
            SELECT 4::INTEGER           AS SeqNo,
                   'bill'::TEXT         AS Type_V,
                   pl_bill.C_BPartner_Location_ID,
                   i.C_Invoice_ID,
                   0::INTEGER           AS M_InOut_ID,
                   NULL::TEXT           AS Vendor_ReferenceNo,
                   pl_bill.bpartnername AS SiteName,
                   pl_bill.Setup_Place_No,
                   NULL::TEXT           AS GLN_Override,
                   i.CreatedBy
            FROM C_Invoice i
                     LEFT JOIN C_BPartner_Location pl_bill ON pl_bill.C_BPartner_Location_ID = i.C_BPartner_Location_ID
            --
            UNION
            --
            SELECT DISTINCT 5::INTEGER           AS SeqNo,
                            'snum'::TEXT         AS Type_V,
                            pl_snum.C_BPartner_Location_ID,
                            i.C_Invoice_ID,
                            0::numeric           AS M_InOut_ID, -- don't return the s.M_InOut_ID, bc there might be many and we don't need them here
                            NULL::TEXT           AS Vendor_ReferenceNo,
                            pl_snum.bpartnername AS SiteName,
                            pl_snum.Setup_Place_No,
                            NULL::TEXT           AS GLN_Override,
                            i.CreatedBy
            FROM C_Invoice i
                     INNER JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID AND il.ispackagingmaterial = 'N' /* exclude package-lines that have no c_orderline_id, to avoid two 'snum' rows in conjunction with the "Fallback if the C_Invoice has no C_Order "*/
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
                                                                       ELSE /* Fallback if the C_Invoice has no C_Order */
                                                                       i.C_BPartner_Location_ID
                                                               END)) union_lookup
      ORDER BY union_lookup.SeqNo, union_lookup.Type_V, union_lookup.C_BPartner_Location_ID, C_Invoice_ID, M_InOut_ID) lookup
         LEFT JOIN C_BPartner_Location pl ON pl.C_BPartner_Location_ID = lookup.C_BPartner_Location_ID
         LEFT JOIN C_BPartner p ON p.C_BPartner_ID = pl.C_BPartner_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = pl.C_Location_ID
         LEFT JOIN C_Country c ON c.C_Country_ID = l.C_Country_ID
         LEFT JOIN AD_User u ON u.AD_User_ID = lookup.CreatedBy
         LEFT JOIN C_Invoice inv ON inv.C_Invoice_ID = lookup.C_Invoice_ID
WHERE TRUE
  --  AND p.VATaxID IS NOT NULL -- VATaxIDs are not needed in general, but only if the customer is in a different country or if the customer explicitly requests them to be in their INVOICs
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
          END)
;

SELECT public.db_alter_view(
    'edi_cctop_119_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_name) = lower('edi_cctop_119_v$new'))
);

DROP VIEW IF EXISTS EDI_Cctop_119_v$new;

DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v$new;

CREATE OR REPLACE VIEW EDI_Cctop_INVOIC_v$new AS
SELECT i.C_Invoice_ID                                                                                       AS EDI_Cctop_INVOIC_v_ID
     , i.C_Invoice_ID
     , i.C_Order_ID
     , i.C_BPartner_ID
     , REGEXP_REPLACE(i.DocumentNo, '\s+$', '')                                                             AS Invoice_DocumentNo
     , i.DateInvoiced
     , i.DateAcct
     , (CASE
            WHEN REGEXP_REPLACE(i.POReference::TEXT, '\s+$', '') <> ''::TEXT AND i.POReference IS NOT NULL /* task 09182: if there is a POReference, then export it */
                THEN REGEXP_REPLACE(i.POReference, '\s+$', '')
                ELSE NULL::CHARACTER VARYING
        END)                                                                                                AS POReference
     /* the invoice's own order date, then its order header's, then the one date shared by all its order lines' orders; DateInvoiced as the last resort, so DateOrdered is never empty */
     , COALESCE(i.DateOrdered, o.DateOrdered, ol.dateordered, i.DateInvoiced)                              AS DateOrdered
     , dt.docbasetype
     , dt.docsubtype
     , (CASE dt.DocBaseType
            WHEN 'ARI'::BPChar THEN (CASE
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = ''   THEN '380'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AQ' THEN '383'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AP' THEN '84'::TEXT
                                                                                                                ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
            WHEN 'ARC'::BPChar THEN (CASE
                /* CQ => "GS - Delivery-Difference"; CS => "GS - Retoure"; CR => Price-Difference */
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) IN ('CQ', 'CS') THEN '381'
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'CR'          THEN '83'
                                                                                                                         ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
                               ELSE 'ERROR EAN_DocType'::TEXT
        END)                                                                                                AS EANCOM_DocType
     , i.GrandTotal
     , i.TotalLines
     , i.DocStatus
     , i.ExternalId
     , esystem.value                                                                                        AS ExternalSystemCode
     , (CASE
            WHEN dsource.internalname IS NOT NULL
                THEN 'int-' || dsource.internalname
        END)                                                                                                AS DataSource
    /* IF docSubType is CS, the we don't reference the original shipment*/
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE COALESCE(shipment.MovementDate, iomd.movementdate) END AS MovementDate
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE COALESCE(shipment.DocumentNo, iodn.documentno) END     AS Shipment_DocumentNo
     , taxAndSurchage.TotalVAT
     , taxAndSurchage.TotalTaxBaseAmt
     -- EdiInvoicRecipientGLN resolved from C_BPartner_EDI_Setting via LATERAL:
     -- lowest SeqNo (then lowest ID) among active rows matching the doc's partner+location
     -- (exact-location OR partner-default), mirroring Java EDIBPartnerConfigMap.resolve.
     -- Falls back to rl.GLN (location-level GLN) when no EDI setting row matches.
     , COALESCE(edi_setting.EdiInvoicRecipientGLN, rl.GLN)                                                 AS ReceiverGLN
     , rl.C_BPartner_Location_ID
     , (SELECT DISTINCT ON (REGEXP_REPLACE(sl.GLN, '\s+$', '')) REGEXP_REPLACE(sl.GLN, '\s+$', '') AS GLN
        FROM C_BPartner_Location sl
        WHERE TRUE
          AND sl.C_BPartner_ID = sp.C_BPartner_ID
          AND sl.IsRemitTo = 'Y'::BPChar
          AND sl.GLN IS NOT NULL
          AND sl.IsActive = 'Y'::BPChar)                                                                    AS SenderGLN
     /* the seller VAT ID stored on the invoice, else the VAT ID of the organisation's partner */
     , REGEXP_REPLACE(COALESCE(NULLIF(TRIM(i.SellerVATaxID), ''), sp.VATaxId), '\s+$', '')                   AS VATaxId
     , c.ISO_Code
     , REGEXP_REPLACE(i.CreditMemoReason, '\s+$', '')                                                       AS CreditMemoReason
     , (SELECT REGEXP_REPLACE(Name, '\s+$', '') AS Name
        FROM AD_Ref_List
        WHERE AD_Reference_ID = 540014 -- C_CreditMemo_Reason
          AND Value = i.CreditMemoReason)                                                                   AS CreditMemoReasonText
     , (SELECT CASE
                   WHEN ARRAY_LENGTH(ARRAY_AGG(DISTINCT ol.invoicableqtybasedon), 1) = 1
                       THEN (ARRAY_AGG(DISTINCT ol.invoicableqtybasedon))[1]
                       ELSE NULL
               END
        FROM c_orderline ol
        WHERE ol.c_order_id = o.c_order_id)                                                                 AS invoicableqtybasedon
     , cc.CountryCode
     , cc.CountryCode_3Digit
     , cc.CountryCode                                                                                       AS AD_Language
     , i.AD_Client_ID
     , i.AD_Org_ID
     , i.Created
     , i.CreatedBy
     , i.Updated
     , i.UpdatedBy
     , i.IsActive
     , (SELECT STRING_AGG(DISTINCT edi.DocumentNo, ',')
        FROM c_invoiceline icl
                 INNER JOIN c_invoice_line_alloc inalloc ON icl.c_invoiceline_id = inalloc.c_invoiceline_id
                 INNER JOIN C_InvoiceCandidate_InOutLine candinout
                            ON inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id
                 INNER JOIN M_InOutLine inoutline ON candinout.m_inoutline_id = inoutline.m_inoutline_id
                 INNER JOIN m_inout inout ON inoutline.m_inout_id = inout.m_inout_id
                 INNER JOIN EDI_Desadv edi ON inout.EDI_Desadv_ID = edi.EDI_Desadv_ID
        WHERE icl.c_invoice_id = i.c_invoice_id)                                                            AS EDIDesadvDocumentNo
     , taxAndSurchage.SurchargeAmt
     , taxAndSurchage.TotalLinesWithSurchargeAmt
     , taxAndSurchage.TotalVatWithSurchargeAmt
     , taxAndSurchage.GrandTotalWithSurchargeAmt
FROM C_Invoice i
         LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypetarget_ID
         LEFT JOIN C_Order o ON o.C_Order_ID = i.C_Order_ID
         LEFT JOIN LATERAL ( SELECT io.DocumentNo,
                                    io.MovementDate
                             FROM M_InOut io
                                      LEFT JOIN c_invoice inv ON io.m_inout_id = inv.m_inout_id AND inv.c_invoice_id = i.c_invoice_id
                             WHERE io.C_Order_ID = o.C_Order_ID
                               AND io.DocStatus IN ('CO', 'CL')
                             ORDER BY inv.c_invoice_id NULLS LAST, io.Created
                             LIMIT 1 ) shipment ON TRUE -- for the case of missing EDI_Desadv, we still get the first M_InOut; DESADV can be switched off for individual C_BPartners
         -- EDI setting: LATERAL picks the single active row with lowest SeqNo (then lowest ID)
         -- among rows matching partner+location exactly OR partner-default (location IS NULL).
         LEFT JOIN LATERAL (
             SELECT s.*
             FROM c_bpartner_edi_setting s
             WHERE s.c_bpartner_id = i.C_BPartner_ID
               AND (s.c_bpartner_location_id = i.C_BPartner_Location_ID OR s.c_bpartner_location_id IS NULL)
               AND s.isactive = 'Y'
             ORDER BY s.seqno, s.c_bpartner_edi_setting_id
             LIMIT 1
         ) edi_setting ON TRUE
         LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
         LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
         LEFT JOIN C_Country cc ON cc.C_Country_ID = l.C_Country_ID
         LEFT JOIN C_BPartner sp ON sp.AD_OrgBP_ID = i.AD_Org_ID
         LEFT JOIN AD_InputDataSource dsource ON dsource.AD_InputDataSource_ID = i.AD_InputDataSource_ID
         LEFT JOIN ExternalSystem esystem ON esystem.externalsystem_id = i.externalsystem_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(o.dateordered) AS dateordered --only add values if there is only one unique value
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT ol.dateordered) = 1 ) ol ON ol.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(io.movementdate) AS movementdate
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                                      INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT io.movementdate) = 1 ) iomd ON iomd.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(io.documentno) AS documentno
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                                      INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT io.documentno) = 1 ) iodn ON iodn.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT C_Invoice_ID
                                  , SUM(TaxAmt)                                              AS TotalVAT
                                  , SUM(TaxBaseAmt)                                          AS TotalTaxBaseAmt
                                  , SUM(SurchargeAmt)                                        AS SurchargeAmt
                                  , SUM(TaxBaseAmtWithSurchargeAmt)                          AS TotalLinesWithSurchargeAmt
                                  , SUM(TaxAmtWithSurchargeAmt)                              AS TotalVatWithSurchargeAmt
                                  , SUM(TaxBaseAmtWithSurchargeAmt + TaxAmtWithSurchargeAmt) AS GrandTotalWithSurchargeAmt
                             FROM edi_cctop_901_991_v
                             WHERE c_invoice_id = i.c_invoice_id
                             GROUP BY C_Invoice_ID) taxAndSurchage ON TRUE
;

SELECT public.db_alter_view(
    'edi_cctop_invoic_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_name) = lower('edi_cctop_invoic_v$new'))
);

DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v$new;

