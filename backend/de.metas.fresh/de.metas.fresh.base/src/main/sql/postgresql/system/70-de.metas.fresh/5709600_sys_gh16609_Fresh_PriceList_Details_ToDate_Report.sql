/*
 * #%L
 * de.metas.fresh.base
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

DROP FUNCTION IF EXISTS report.Fresh_PriceList_Details_ToDate_Report(character varying,
                                                                     text,
                                                                     date)
;

CREATE OR REPLACE FUNCTION report.Fresh_PriceList_Details_ToDate_Report(
    p_ad_language                character varying,
    p_show_product_price_pi_flag text,
    p_validfrom                  date
)
    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                productcategory            text,
                m_product_id               integer,
                value                      text,
                customerproductnumber      text,
                productname                text,
                isseasonfixedprice         character,
                itemproductname            character varying,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
                pricepattern1              text,
                altpricestd                numeric,
                pricepattern2              text,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   character,
                currency2                  character,
                show_product_price_pi_flag text,
                plv_name                   text
            )
AS
$$
BEGIN

    DROP TABLE IF EXISTS temp_price_list_details;

    CREATE TEMP TABLE IF NOT EXISTS temp_price_list_details AS
    SELECT DISTINCT bp.c_bpartner_id,
                    plv.M_PriceList_Version_ID,
                    plv.name
    FROM c_bpartner bp
             INNER JOIN M_PricingSystem ps ON bp.m_pricingsystem_id = ps.m_pricingsystem_id
             INNER JOIN C_BPartner_Product bpp ON bp.c_bpartner_id = bpp.c_bpartner_id AND bp.iscustomer = 'Y'
             INNER JOIN M_ProductPrice pp ON pp.M_Product_ID = bpp.M_Product_ID
             INNER JOIN M_PriceList_Version plv ON plv.M_PriceList_Version_ID = pp.M_PriceList_Version_ID
             INNER JOIN M_PriceList pl ON plv.m_pricelist_id = pl.m_pricelist_id AND ps.m_pricingsystem_id = pl.m_pricingsystem_id AND pl.issopricelist = 'Y'
        AND plv.validfrom >= p_validfrom;


    IF p_show_product_price_pi_flag = 'N' THEN
        RETURN QUERY (SELECT DISTINCT d.*, tmp.name::text AS plv_name
                      FROM temp_price_list_details tmp
                               INNER JOIN report.fresh_PriceList_Details_Report(
                              tmp.c_bpartner_id,
                              tmp.M_PriceList_Version_ID,
                              NULL,
                              p_ad_language,
                              p_show_product_price_pi_flag
                                          ) d ON TRUE
                      ORDER BY bp_value,
                               plv_name,
                               productcategory,
                               m_product_id);

    END IF;

    IF p_show_product_price_pi_flag = 'Y' THEN
        RETURN QUERY (SELECT pp.*, tmp.name::text AS plv_name
                      FROM temp_price_list_details tmp
                               INNER JOIN report.fresh_pricelist_details_report_with_pp_pi(
                              tmp.c_bpartner_id,
                              tmp.M_PriceList_Version_ID,
                              NULL,
                              p_ad_language,
                              p_show_product_price_pi_flag
                                          ) pp ON TRUE
                      ORDER BY bp_value,
                               plv_name,
                               productcategory,
                               m_product_id);

    END IF;

END;
$$
    LANGUAGE plpgsql
;



