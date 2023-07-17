/*
 * #%L
 * de.metas.fresh.base
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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_Product_Price(numeric,
                                                                             numeric,
                                                                             date)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price(IN p_productId   numeric,
                                                                                IN p_priceListId numeric,
                                                                                IN p_onDate      date)
    RETURNS table
            (
                priceStd      numeric,
                c_uom_id      numeric,
                c_currency_id numeric
            )
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    result           record;
    priceList        record;
    priceListVersion record;
BEGIN
    --get m_pricelist
    SELECT * FROM m_pricelist INTO priceList;
    --get m_pricelist_version
    SELECT *
    FROM M_PriceList_Version
    WHERE M_PriceList_ID = p_priceListId
      AND TRUNC(ValidFrom, 'DD') <= TRUNC(p_onDate::TIMESTAMP, 'DD')
      AND IsActive = 'Y'
    ORDER BY ValidFrom DESC NULLS FIRST
    INTO priceListVersion;

    SELECT *
    FROM M_ProductPrice
    WHERE IsActive = 'Y'
      AND M_PriceList_Version_ID = priceListVersion.m_pricelist_version_id
      AND M_Product_ID = p_productId
      AND IsInvalidPrice <> 'Y'
      AND IsAttributeDependant = 'N'
      AND M_HU_PI_Item_Product_ID IS NULL
    INTO result;

    IF (result.m_productprice_id IS NOT NULL) AND (pricelist.m_pricelist_id IS NOT NULL) THEN
        RETURN QUERY (SELECT result.priceStd, result.c_uom_id, priceList.c_currency_id);
        -- calculated from current pricelist
    ELSIF pricelist.basepricelist_id IS NOT NULL THEN
        RETURN QUERY SELECT * FROM de_metas_endcustomer_fresh_reports.get_Product_Price(p_productId, pricelist.basepricelist_id, p_onDate);
        -- try to retrieve from basepricelist
    END IF;
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price(numeric, numeric, date) IS 'Retrieve the current price of the given product in the given pricelist at the given date'
;
