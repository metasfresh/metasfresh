/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Commission_Calculation (IN p_BPartner_SalesRep_ID numeric,
                                                                                                 IN p_CommissionDate_From  timestamp without time zone,
                                                                                                 IN p_CommissionDate_To    timestamp without time zone,
                                                                                                 IN p_Bill_BPartner_ID     numeric,
                                                                                                 IN p_ad_language          Character Varying(6))
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Commission_Calculation(IN p_BPartner_SalesRep_ID numeric,
                                                                                        IN p_CommissionDate_From  timestamp without time zone,
                                                                                        IN p_CommissionDate_To    timestamp without time zone,
                                                                                        IN p_Bill_BPartner_ID     numeric,
                                                                                        IN p_ad_language          Character Varying(6))
    RETURNS TABLE
            (
                c_invoice_id          numeric,
                c_invoiceline_id      numeric,
                bpvalue               character varying,
                bpname                character varying,
                c_bpartner_id         numeric,
                line                  numeric,
                qtyinvoicedinpriceuom numeric,
                invoiceproductvalue   character varying,
                invoiceproductname    character varying,
                productvalue          character varying,
                productname           character varying,
                qtyentered            numeric,
                uom                   character varying,
                pointsbase_invoiced   numeric,
                pointssum_settled     numeric,
                percentofbasepoints   numeric,
                documentno            character varying,
                poreference           character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT c.c_invoice_commission_id           AS C_Invoice_id,
       c.c_invoiceline_commission_id       AS C_InvoiceLine_id,
       bp.value                            AS bpValue,
       bp.Name                             AS bpName,
       bp.C_Bpartner_ID,
       il.line,
       il.qtyinvoicedinpriceuom,
       pil.value                           AS invoiceproductValue,
       COALESCE(pilt.name, pil.name)       AS invoiceproductName,
       p.value                             AS productValue,
       COALESCE(pt.name, p.name)           AS productName,
       c.qtyentered,
       COALESCE(ut.uomsymbol, u.uomsymbol) AS uom,
       c.pointsbase_invoiced,
       c.pointssum_settled,
       c.percentofbasepoints,
       o.documentno,
       o.poreference
FROM C_Commission_Overview_V c
         JOIN c_bpartner bp ON bp.c_bpartner_id = c.Bill_Bpartner_ID
         JOIN c_invoiceline il ON il.c_invoiceline_id = c.c_invoiceline_commission_id
         JOIN m_product pil ON pil.m_product_id = il.m_product_id
         JOIN m_product_trl pilt ON pilt.m_product_id = pil.m_product_id AND pilt.ad_language = p_AD_Language
         LEFT JOIN c_order o ON c.c_order_id = o.c_order_id
         LEFT JOIN M_Product p ON c.M_Product_Order_ID = p.m_product_id
         LEFT JOIN m_product_trl pt ON pt.m_product_id = p.m_product_id AND pt.ad_language = p_AD_Language
         LEFT JOIN C_Uom u ON c.C_Uom_id = u.C_Uom_id
         LEFT JOIN C_Uom_trl ut ON ut.C_Uom_id = u.C_Uom_id AND ut.ad_language = p_AD_Language
WHERE c.C_BPartner_SalesRep_ID = p_BPartner_SalesRep_ID
  AND (c.CommissionDate BETWEEN p_CommissionDate_From AND p_CommissionDate_To)
  AND (c.Bill_BPartner_ID = p_Bill_BPartner_ID OR p_Bill_BPartner_ID IS NULL)
ORDER BY C_Bpartner_ID, productValue, productName;
$$
;
