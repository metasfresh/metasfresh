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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Commission_Forecast (IN p_BPartner_SalesRep_ID numeric,
                                                                                              IN p_ad_language          Character Varying(6))
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Commission_Forecast(IN p_BPartner_SalesRep_ID numeric,
                                                                                     IN p_ad_language          Character Varying(6))
    RETURNS TABLE
            (
                OrderDocumentNo     varchar,
                poreference         varchar,
                dateordered         date,
                BPartnerValue       varchar,
                BPartnerName        varchar,
                ProductValue        varchar,
                ProductName         varchar,
                qtyentered          numeric,
                uom                 varchar,
                pointsbase_invoiced numeric,
                percentofbasepoints numeric,
                pointssum_tosettle  numeric,
                DeliveryToAddress   varchar
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT o.documentno                                              AS OrderDocumentNo,
       o.poreference                                             AS poreference,
       o.dateordered::date                                       AS dateordered,
       bp.value                                                  AS BPartnerValue,
       bp.Name                                                   AS BPartnerName,

       -- Name Kunde
       -- Produkt
       -- Menge
       p.value                                                   AS ProductValue,
       COALESCE(pt.name, p.name)                                 AS ProductName,
       c.qtyentered                                              AS qtyentered,
       COALESCE(ut.uomsymbol, u.uomsymbol)                       AS UOM,
       c.pointsbase_invoiced                                     AS pointsbase_invoiced,
       --c.pointssum_settled,
       c.percentofbasepoints                                     AS percentofbasepoints,
       c.pointssum_tosettle                                      AS pointssum_tosettle,
       CASE WHEN o.isdropship = 'Y' THEN o.DeliveryToAddress END AS DeliveryToAddress

FROM C_Commission_Overview_V c
         LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = c.Bill_Bpartner_ID
         LEFT JOIN c_invoiceline il ON il.c_invoiceline_id = c.c_invoiceline_commission_id
         LEFT JOIN m_product pil ON pil.m_product_id = m_product_order_id
         LEFT JOIN m_product_trl pilt ON pilt.m_product_id = pil.m_product_id AND pilt.ad_language = p_ad_language
         LEFT JOIN c_order o ON c.c_order_id = o.c_order_id
         LEFT JOIN M_Product p ON c.M_Product_Order_ID = p.m_product_id
         LEFT JOIN m_product_trl pt ON pt.m_product_id = p.m_product_id AND pt.ad_language = p_ad_language
         LEFT JOIN C_Uom u ON c.C_Uom_id = u.C_Uom_id
         LEFT JOIN C_Uom_trl ut ON ut.C_Uom_id = u.C_Uom_id AND ut.ad_language = p_ad_language
WHERE c.C_BPartner_SalesRep_ID = p_BPartner_SalesRep_ID
ORDER BY documentno, BPartnerValue, ProductValue, ProductName;
$$
;
