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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Invoice_Export_Tax_Consultants (IN p_Date_From   timestamp without time zone,
                                                                                                IN p_Date_To     timestamp without time zone,
                                                                                                IN p_ad_language Character Varying(6))
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Invoice_Export_Tax_Consultants(IN p_Date_From   timestamp without time zone,
                                                                                       IN p_Date_To     timestamp without time zone,
                                                                                       IN p_ad_language Character Varying(6))
    RETURNS TABLE
            (
                doctype                 character varying,
                DocumentNo_Ref          character varying,
                Article                 character varying,
                DateDoc                 character varying,
                Partner                 character varying,
                PriceActual             numeric,
                Unit                    character varying,
                Qty                     numeric,
                Partner_DebitID         character varying,
                Product_Category_Name   character varying,
                Foreign_Document_Number character varying,
                Partner_CreditorID      character varying,
                UID_Number              character varying,
                Tax_Amount              numeric,
                Tax_Rate                numeric,
                Currency                character varying,
                TotalPrice              numeric
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT DISTINCT COALESCE(dtt.name, dt.name)                            AS doctype,
                i.documentno                                           AS DocumentNo_Ref,
                COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) AS Article,
                i.dateinvoiced                                         AS DateDoc,
                bp.name                                                AS Partner,
                il.PriceActual                                         AS PriceActual,
                COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                AS Unit,
                il.QtyInvoicedInPriceUOM                               AS Qty,
                bp.debtorid                                            AS Partner_DebitID,
                COALESCE(pct.name, pc.name)                            AS Product_Category_Name,
                i.poreference                                          AS Foreign_Document_Number,
                bp.creditorid                                          AS Partner_CreditorID,
                bp.VATaxID                                             AS UID_Number,
                il.taxamt                                              AS Tax_Amount,
                t.rate                                                 AS Tax_Rate,
                c.cursymbol                                            AS Currency,
                i.grandtotal                                           AS TotalPrice

FROM C_Invoice i
         INNER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID
         INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id

    -- Get DocType translation
         LEFT OUTER JOIN C_DocType_Trl dtt ON i.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_ad_language

    -- Get Product and its translation
         LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language
         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID

    -- Get Product category and its translation
         LEFT OUTER JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
         LEFT OUTER JOIN m_product_category_trl pct ON pct.m_product_category_id = p.m_product_category_id

    -- Get Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_ad_language

    -- Tax rate
         LEFT OUTER JOIN C_Tax t ON il.C_Tax_ID = t.C_Tax_ID

    -- Tax Currency
         LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID
WHERE i.dateinvoiced BETWEEN p_Date_From AND p_Date_To
ORDER BY i.documentno
    ;
$$
;
