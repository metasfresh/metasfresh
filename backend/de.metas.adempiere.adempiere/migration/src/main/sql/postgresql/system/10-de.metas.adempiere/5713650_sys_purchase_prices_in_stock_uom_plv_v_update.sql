/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2023 metas GmbH
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

DROP VIEW IF EXISTS purchase_prices_in_stock_uom_plv_v
;

CREATE VIEW purchase_prices_in_stock_uom_plv_v AS
WITH cte (c_currency_id) AS (
    (SELECT c_currency_id
     FROM c_currency
     WHERE iso_code = get_sysconfig_value('de.metas.ui.web.material.cockpit.field.HighestPurchasePrice_AtDate.CurrencyCode', 'CHF')))
SELECT pl.m_pricelist_id,
       cte.c_currency_id,
       plv.m_pricelist_version_id,
       plv.validfrom,
       COALESCE(
               (SELECT plv_next.validfrom
                FROM m_pricelist_version plv_next
                WHERE plv_next.m_pricelist_id = plv.m_pricelist_id
                  AND plv_next.validfrom > plv.validfrom
                ORDER BY plv_next.validfrom
                LIMIT 1),
               '9999-12-31'
           ) AS validTo,
       pp.m_product_id,
       pp.m_productprice_id,
       COALESCE(
               currencyconvert(
                       priceuomconvert(
                               pp.m_product_id,
                               pp.pricestd,
                               pp.c_uom_id,
                               p.c_uom_id
                           ),
                       pl.c_currency_id,
                       cte.c_currency_id,
                       NOW(),
                       0,
                       pp.ad_client_id,
                       pp.ad_org_id
                   ),
               0
           ) AS ProductPriceInStockUOM,
       p.c_uom_id
FROM cte,
     m_productprice pp
         JOIN m_pricelist_version plv ON plv.m_pricelist_version_id = pp.m_pricelist_version_id
         JOIN m_pricelist pl ON plv.m_pricelist_id = pl.m_pricelist_id AND pl.issopricelist = 'N'
         JOIN m_product p ON p.m_product_id = pp.m_product_id AND p.isactive = 'Y' AND p.ispurchased = 'Y' AND p.discontinued = 'N'
WHERE pp.isinvalidprice = 'N'
;

