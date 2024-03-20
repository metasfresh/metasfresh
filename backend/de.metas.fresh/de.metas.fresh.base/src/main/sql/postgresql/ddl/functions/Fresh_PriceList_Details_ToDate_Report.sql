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
                                                                     date,
                                                                     numeric,
                                                                     numeric,
                                                                     text)
;

CREATE OR REPLACE FUNCTION report.Fresh_PriceList_Details_ToDate_Report(
    p_ad_language                character varying,
    p_show_product_price_pi_flag text,
    p_validfrom                  date,
    p_c_bpartner_id              numeric DEFAULT NULL::numeric,
    p_c_bp_group_id              numeric DEFAULT NULL::numeric,
    p_issotrx                    text DEFAULT 'Y'::text
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
                plv_name                   text,
                c_bpartner_location_id     numeric,
                ad_org_id                  numeric
            )
AS
$$
BEGIN

    DROP TABLE IF EXISTS temp_price_list_details;

    CREATE TEMP TABLE IF NOT EXISTS temp_price_list_details AS
    SELECT DISTINCT bp.c_bpartner_id,
                    plv.M_PriceList_Version_ID,
                    plv.name,
                    (SELECT bpl.c_bpartner_location_id
                     FROM c_bpartner_location bpl
                     WHERE bpl.c_bpartner_id = bp.c_bpartner_id
                     ORDER BY bpl.isbilltodefault
                             DESC
                     LIMIT 1) AS c_bpartner_location_id,
                    bp.ad_org_id
    FROM c_bpartner bp
             INNER JOIN M_PriceList pl ON bp.m_pricingsystem_id = pl.m_pricingsystem_id
             INNER JOIN M_PriceList_Version plv ON pl.m_pricelist_id = plv.m_pricelist_id
    WHERE pl.issopricelist = p_issotrx
      AND plv.validfrom >= p_validfrom
      AND (p_C_BPartner_ID IS NULL OR bp.c_bpartner_id = p_C_BPartner_ID)
      AND (p_C_BP_Group_ID IS NULL OR bp.c_bp_group_id = p_C_BP_Group_ID);


    IF p_show_product_price_pi_flag = 'N' THEN
        RETURN QUERY (SELECT DISTINCT d.*,
                                      tmp.name::text AS plv_name,
                                      tmp.c_bpartner_location_id,
                                      tmp.ad_org_id
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
                               ProductName,
                               attributes,
                               ItemProductName);

    END IF;

    IF p_show_product_price_pi_flag = 'Y' THEN
        RETURN QUERY (SELECT DISTINCT pp.*,
                                      tmp.name::text AS plv_name,
                                      tmp.c_bpartner_location_id,
                                      tmp.ad_org_id
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
                               ProductName,
                               attributes,
                               ItemProductName);

    END IF;

END;
$$
    LANGUAGE plpgsql
;



