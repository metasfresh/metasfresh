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

DROP VIEW IF EXISTS purchaseprices_plv_v
;

CREATE TEMP VIEW purchase_prices_in_stock_uom_plv_v AS
SELECT pl.m_pricelist_id,
       pl.c_currency_id,
       plv.m_pricelist_version_id,
       plv.validfrom,
       COALESCE(plv_next.validfrom, '9999-12-31') AS validto,
       pp.m_product_id,
       pp.m_productprice_id,
       priceuomconvert(
               pp.m_product_id,
               pp.pricestd,
               pp.c_uom_id,
               (SELECT p.c_uom_id FROM m_product p WHERE pp.m_product_id = p.m_product_id)
           ) AS priceStdInStockUOM,
       (SELECT p.c_uom_id FROM m_product p WHERE pp.m_product_id = p.m_product_id) AS C_UOM_ID
FROM m_productprice pp
         JOIN m_pricelist_version plv ON plv.m_pricelist_version_id = pp.m_pricelist_version_id
         JOIN m_pricelist pl ON plv.m_pricelist_id = pl.m_pricelist_id AND pl.issopricelist = 'N'
         LEFT JOIN m_pricelist_version plv_next ON pl.m_pricelist_id = plv_next.m_pricelist_id AND plv_next.validfrom > plv.validfrom
         LEFT JOIN m_pricelist_version plv_between ON pl.m_pricelist_id = plv_between.m_pricelist_id AND plv_between.validfrom > plv.validfrom AND plv_between.validfrom < plv_next.validfrom
WHERE plv_between.m_pricelist_version_id IS NULL
;

