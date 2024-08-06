select db_alter_view('edi_cctop_140_v',
                     'SELECT i.c_invoice_id                                                AS edi_cctop_140_v_id,
       i.c_invoice_id,
       i.c_invoice_id                                                AS edi_cctop_invoic_v_id,
       pterm.discount,
       pterm.discountdays,
       i.dateinvoiced::timestamp WITH TIME ZONE + pterm.discountdays AS discountdate,
       t.rate,
       pterm.name,
       ROUND(SUM(it.TaxBaseAmt), 2)                                  AS DiscountBaseAmt,
       ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2)          AS DiscountAmt,
       i.ad_client_id,
       i.ad_org_id,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.isactive
FROM c_invoice i
         LEFT JOIN c_paymentterm pterm ON pterm.c_paymentterm_id = i.c_paymentterm_id
         LEFT JOIN c_invoicetax it ON it.c_invoice_id = i.c_invoice_id
         LEFT JOIN c_tax t ON t.c_tax_id = it.c_tax_id
GROUP BY i.c_invoice_id,
         pterm.discount,
         pterm.discountdays,
         pterm.Name,
         i.dateinvoiced,
         pterm.discountdays,
         t.rate,
         pterm.discount,
         i.ad_client_id,
         i.ad_org_id,
         i.created,
         i.createdby,
         i.updated,
         i.updatedby,
         i.isactive,
         it.TaxBaseAmt
;');
-----

select db_alter_view('edi_cctop_901_991_v',
                     'SELECT i.c_invoice_id                                                                                       AS edi_cctop_901_991_v_id,
       i.c_invoice_id,
       i.c_invoice_id                                                                                       AS edi_cctop_invoic_v_id,
       SUM(it.taxamt + it.taxbaseamt)                                                                       AS TotalAmt,
       SUM(it.taxamt)                                                                                       AS TaxAmt,
       SUM(it.taxbaseamt)                                                                                   AS TaxBaseAmt,
       t.Rate /* we support the case of having two different C_Tax_IDs with the same tax-rate */,
       t.IsTaxExempt,
       i.ad_client_id,
       i.ad_org_id,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.isactive,
       REGEXP_REPLACE(rn.referenceno, ''\s+$'', '''')                                                           AS ESRReferenceNumber,
       ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2)                                                 AS SurchargeAmt, -- may be be positve or negative
       ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2) + SUM(it.TaxBaseAmt)                            AS TaxBaseAmtWithSurchargeAmt,
       ROUND((ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2) + SUM(it.TaxBaseAmt)) * t.rate / 100, 2) AS TaxAmtWithSurchargeAmt,
       CASE
           WHEN EXISTS (SELECT SUM(it_bigger.taxbaseamt), t.rate
                        FROM c_invoicetax it_bigger
                                 JOIN c_tax t_bigger ON it_bigger.c_tax_id = t_bigger.c_tax_id
                        WHERE it_bigger.isactive = ''Y''
                          AND it_bigger.c_invoice_id = i.c_invoice_id
                        GROUP BY t_bigger.rate
                        HAVING SUM(it_bigger.taxbaseamt) > SUM(it.taxbaseamt))
               THEN ''N''  /*If there is a bigger tax-base-amount, then this is not the main tax*/
               ELSE ''Y''
       END                                                                                                  AS IsMainVAT     /* we mark the tax-rate with the biggest baseAmt as the invoice''s main-tax */
FROM c_invoice i
         LEFT JOIN c_invoicetax it ON it.c_invoice_id = i.c_invoice_id
         LEFT JOIN c_tax t ON t.c_tax_id = it.c_tax_id
         LEFT JOIN (SELECT sit.c_invoice_id,
                           COUNT(DISTINCT st.rate
                           ) AS tax_count
                    FROM c_invoicetax sit
                             LEFT JOIN c_tax st ON st.c_tax_id = sit.c_tax_id
                    GROUP BY sit.c_invoice_id) tc ON tc.c_invoice_id = it.c_invoice_id
         LEFT JOIN c_referenceno_doc rnd ON rnd.record_id = i.c_invoice_id AND rnd.ad_table_id = 318 /* C_Invoice DocumentType */
         LEFT JOIN c_referenceno rn ON rn.c_referenceno_id = rnd.c_referenceno_id
         LEFT JOIN C_PaymentTerm pterm ON i.c_paymentterm_id = pterm.c_paymentterm_id
WHERE it.IsActive = ''Y''
  AND tc.tax_count > 0
  -- can either be an ESRReferenceNumber, or we may not have it at all. Regardless, both cases should work.
  AND (rn.c_referenceno_type_id = 540005 OR rnd.c_referenceno_doc_id IS NULL
    ) /* c_referenceno_type_id = 540005 (ESRReferenceNumber) */
GROUP BY i.c_invoice_id, i.ad_client_id,
         t.rate,
         t.IsTaxExempt,
         i.ad_org_id,
         i.created,
         i.createdby,
         i.updated,
         i.updatedby,
         i.isactive,
         pterm.discount,
         rn.referenceno
;');




    
-----

select db_alter_view('edi_cctop_invoic_500_v', 
                     'SELECT SUM(il.qtyEntered)                                                        AS QtyInvoiced,
       CASE
           WHEN u.x12de355 = ''TU'' THEN ''PCE''
                                  ELSE u.x12de355
       END                                                                       AS eancom_uom, /* C_InvoiceLine''s UOM */
       CASE
           WHEN u_ordered.x12de355 IN (''TU'', ''COLI'') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
                                                     ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced)
       END                                                                       AS QtyInvoicedInOrderedUOM,
       u_ordered.x12de355                                                        AS eancom_ordered_uom, /* leaving out that CASE-mumbo-jumbo from the other uoms; IDK if it''s needed (anymore) */
       MIN(il.c_invoiceline_id)                                                  AS edi_cctop_invoic_500_v_id,
       SUM(il.linenetamt)                                                        AS linenetamt,
       MIN(il.line)                                                              AS line,
       il.c_invoice_id,
       il.c_invoice_id                                                           AS edi_cctop_invoic_v_id,
       il.priceactual,
       il.pricelist,
       il.discount,
       ol.invoicableqtybasedon,
       REGEXP_REPLACE(pp.UPC, ''\s+$'', '''')                                        AS UPC_CU,
       REGEXP_REPLACE(pp.EAN_CU, ''\s+$'', '''')                                     AS EAN_CU, -- Deprecated: superseded by buyer_ean_cu
       REGEXP_REPLACE(p.value, ''\s+$'', '''')                                       AS Value,
       REGEXP_REPLACE(pp.productno, ''\s+$'', '''')                                  AS CustomerProductNo,
       SUBSTR(p.name, 1, 35)                                                     AS name,
       SUBSTR(p.name, 36, 70)                                                    AS name2,
       t.rate,
       CASE /* be lenient if il.price_uom_id is not set; see https://github.com/metasfresh/metasfresh/issues/6458 */
           WHEN COALESCE(u_price.x12de355, u.x12de355) = ''TU'' THEN ''PCE''
                                                              ELSE COALESCE(u_price.x12de355, u.x12de355)
       END                                                                       AS eancom_price_uom /* C_InvoiceLine''s Price-UOM */,
       CASE
           WHEN t.rate = 0 THEN ''Y''
                           ELSE ''''
       END                                                                       AS taxfree,
       t.IsTaxExempt,
       c.iso_code,
       il.ad_client_id,
       il.ad_org_id,
       MIN(il.created)                                                           AS created,
       MIN(il.createdby)::numeric(10, 0)                                         AS createdby,
       MAX(il.updated)                                                           AS updated,
       MAX(il.updatedby)::numeric(10, 0)                                         AS updatedby,
       il.isactive,
       CASE pc.value
           WHEN ''Leergut'' THEN ''P''
                          ELSE ''''
       END                                                                       AS leergut,
       COALESCE(NULLIF(pp.productdescription, ''''), NULLIF(pp.description, ''''), NULLIF(p.description, ''''),
                p.name)::character varying                                       AS productdescription,
       COALESCE(ol.line, il.line)                                                AS orderline,
       COALESCE(NULLIF(o.poreference, ''''), i.poreference)::character varying(40) AS orderporeference,
       il.c_orderline_id,
       SUM(il.taxamtinfo)                                                        AS taxamtinfo,
       REGEXP_REPLACE(pip.GTIN, ''\s+$'', '''')                                      AS GTIN,   -- Deprecated: superseded by buyer_gtin_tu
       REGEXP_REPLACE(pip.EAN_TU, ''\s+$'', '''')                                    AS EAN_TU,
       REGEXP_REPLACE(pip.UPC, ''\s+$'', '''')                                       AS UPC_TU,
       REGEXP_REPLACE(pip.GTIN::text, ''\s+$''::text, ''''::text)                    AS Buyer_GTIN_TU,
       REGEXP_REPLACE(pp.GTIN::text, ''\s+$''::text, ''''::text)                     AS Buyer_GTIN_CU,
       REGEXP_REPLACE(pp.EAN_CU::text, ''\s+$''::text, ''''::text)                   AS Buyer_EAN_CU,
       REGEXP_REPLACE(p.GTIN::text, ''\s+$''::text, ''''::text)                      AS Supplier_GTIN_CU,
       il.QtyEnteredInBPartnerUOM                                                AS qtyEnteredInBPartnerUOM,
       il.C_UOM_BPartner_ID                                                      AS C_UOM_BPartner_ID,
       ol.externalseqno                                                          AS externalSeqNo
FROM c_invoiceline il
         LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id AND ol.isactive = ''Y''
         LEFT JOIN M_HU_PI_Item_Product pip ON ol.M_HU_PI_Item_Product_ID = pip.M_HU_PI_Item_Product_ID
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN c_uom u_ordered ON u_ordered.c_uom_id = COALESCE(ol.c_uom_id, il.c_uom_id)
         LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
         LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
         LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
         LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
         LEFT JOIN c_bpartner_product pp
                   ON pp.c_bpartner_id = i.c_bpartner_id AND pp.m_product_id = il.m_product_id AND pp.isactive = ''Y''
         LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
         LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id
         LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
WHERE TRUE
  AND il.m_product_id IS NOT NULL
  AND il.isactive = ''Y''
  AND il.qtyentered <> 0
GROUP BY il.c_invoice_id,
         il.priceactual,
         il.pricelist,
         il.discount,
         ol.InvoicableQtyBasedOn,
         pp.UPC,
         pp.EAN_CU,
         p.value,
         pp.productno,
         (SUBSTR(p.name, 1, 35)),
         (SUBSTR(p.name, 36, 70)),
         t.rate,
         t.IsTaxExempt,
         (CASE
              WHEN u.x12de355 = ''TU'' THEN ''PCE''
                                     ELSE u.x12de355
          END),
         (CASE /* be lenient if il.price_uom_id is not set; see https://github.com/metasfresh/metasfresh/issues/6458 */
              WHEN COALESCE(u_price.x12de355, u.x12de355) = ''TU'' THEN ''PCE''
                                                                 ELSE COALESCE(u_price.x12de355, u.x12de355)
          END),
         (CASE
              WHEN u_ordered.x12de355 IN (''TU'', ''COLI'') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
                                                        ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced)
          END),
         u_ordered.x12de355,
         (CASE
              WHEN t.rate = 0 THEN ''Y''
                              ELSE ''''
          END),
         c.iso_code,
         il.ad_client_id,
         il.ad_org_id,
         il.isactive,
         (CASE pc.value
              WHEN ''Leergut'' THEN ''P''
                             ELSE ''''
          END),
         (COALESCE(NULLIF(pp.productdescription, ''''), NULLIF(pp.description, ''''), NULLIF(p.description, ''''), p.name)),
         (COALESCE(NULLIF(o.poreference, ''''), i.poreference)),
         (COALESCE(ol.line, il.line)),
         il.c_orderline_id,
         pip.UPC, pip.GTIN, pip.EAN_TU, pp.GTIN, p.GTIN,
         il.QtyEnteredInBPartnerUOM, il.C_UOM_BPartner_ID, ol.externalseqno
ORDER BY COALESCE(ol.line, il.line)
;');



COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
'
;
