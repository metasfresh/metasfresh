/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


DROP VIEW IF EXISTS EDI_Cctop_119_v;
CREATE OR REPLACE VIEW EDI_Cctop_119_v AS
SELECT lookup.C_Invoice_ID       AS EDI_Cctop_119_v_ID,
       lookup.C_Invoice_ID,
       lookup.C_Invoice_ID       AS EDI_Cctop_INVOIC_v_ID,
       lookup.M_InOut_ID,
       pl.C_BPartner_Location_ID,
       pl.GLN,
       pl.Phone,
       pl.Fax,
       p.Name,
       p.Name2,
       p.Value,
       p.VATaxID,
       lookup.Vendor_ReferenceNo AS ReferenceNo,
       lookup.SiteName,
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
                                  pl_cust.bpartnername AS SiteName,
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
                         pl_vend.bpartnername AS SiteName,
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
                                  pl_ship.bpartnername AS SiteName,
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
                         pl_bill.bpartnername AS SiteName,
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
                                  pl_snum.bpartnername AS SiteName,
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
    REGEXP_REPLACE(pp.productno, '\s+$', '') AS CustomerProductNo,
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

