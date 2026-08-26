/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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


DROP FUNCTION IF EXISTS report.getSalesPriceSpecialAndBase(timestamp with time zone,
                                                           numeric,
                                                           numeric
)
;

CREATE OR REPLACE FUNCTION report.getSalesPriceSpecialAndBase(
    p_Date                   timestamp with time zone,
    p_C_BPartner_Location_ID numeric,
    p_M_Product_ID           numeric
)
    RETURNS TABLE
            (
                SpecialPriceStd numeric,
                SpecialC_UOM_ID numeric,
                SpecialPLV      varchar,
                BasePriceStd    numeric,
                BaseC_UOM_ID    numeric,
                BasePLV         varchar
            )
AS
$$
WITH RECURSIVE
    bpl AS (SELECT bpl.C_BPartner_ID,
                   loc.C_Country_ID
            FROM C_BPartner_Location bpl
                     JOIN C_Location loc ON loc.C_Location_ID = bpl.C_Location_ID
            WHERE bpl.C_BPartner_Location_ID = p_C_BPartner_Location_ID),
    pricing_system AS (SELECT COALESCE(bp.M_PricingSystem_ID, bpg.M_PricingSystem_ID) AS M_PricingSystem_ID,
                              bpl.C_Country_ID
                       FROM bpl
                                JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                                LEFT JOIN C_BP_Group bpg ON bpg.C_BP_Group_ID = bp.C_BP_Group_ID),
    -- Pick the SO price list matching the pricing system; country-specific wins over generic
    pricelist_root AS (SELECT pl.M_PriceList_ID
                       FROM M_PriceList pl,
                            pricing_system ps
                       WHERE pl.M_PricingSystem_ID = ps.M_PricingSystem_ID
                         AND pl.IsSOPriceList = 'Y'
                         AND pl.IsActive = 'Y'
                         AND (pl.C_Country_ID = ps.C_Country_ID OR pl.C_Country_ID IS NULL)
                       ORDER BY (pl.C_Country_ID IS NOT NULL) DESC,
                                pl.M_PriceList_ID
                       LIMIT 1),
    -- Walk M_PriceList.BasePriceList_ID chain (cycle-protected, max depth 10)
    pl_chain AS (SELECT pl.M_PriceList_ID,
                        pl.BasePriceList_ID,
                        0                                    AS depth,
                        ARRAY [pl.M_PriceList_ID]::numeric[] AS visited
                 FROM M_PriceList pl
                          JOIN pricelist_root r ON r.M_PriceList_ID = pl.M_PriceList_ID

                 UNION ALL

                 SELECT pl.M_PriceList_ID,
                        pl.BasePriceList_ID,
                        c.depth + 1,
                        c.visited || pl.M_PriceList_ID
                 FROM pl_chain c
                          JOIN M_PriceList pl ON pl.M_PriceList_ID = c.BasePriceList_ID
                 WHERE c.BasePriceList_ID IS NOT NULL
                   AND pl.IsActive = 'Y'
                   AND NOT (pl.M_PriceList_ID = ANY (c.visited))
                   AND c.depth < 10),
    -- For each PL in the chain, take its newest active PLV valid on p_Date
    plv_per_pl AS (SELECT DISTINCT ON (c.M_PriceList_ID) c.depth,
                                                         (c.BasePriceList_ID IS NULL) AS is_base_list,
                                                         c.M_PriceList_ID,
                                                         plv.M_PriceList_Version_ID
                   FROM pl_chain c
                            JOIN M_PriceList_Version plv ON plv.M_PriceList_ID = c.M_PriceList_ID
                   WHERE plv.IsActive = 'Y'
                     AND plv.ValidFrom <= p_Date
                   ORDER BY c.M_PriceList_ID, plv.ValidFrom DESC),
    -- Is the customer's assigned (depth-0) list an OVERRIDE list (has a base beneath it)?
    root_info AS (SELECT (c.BasePriceList_ID IS NOT NULL) AS is_override
                  FROM pl_chain c
                  WHERE c.depth = 0),
    -- All PLV rows in the chain that actually price this product
    priced AS (SELECT x.depth,
                      x.is_base_list,
                      pp.PriceStd,
                      pp.C_UOM_ID,
                      plv.Name                       AS PLVName,
                      COALESCE(pp.SeqNo, 2147483647) AS seqno_sort,
                      pp.M_ProductPrice_ID
               FROM plv_per_pl x
                        JOIN M_PriceList_Version plv ON plv.M_PriceList_Version_ID = x.M_PriceList_Version_ID
                        JOIN M_ProductPrice pp ON pp.M_PriceList_Version_ID = x.M_PriceList_Version_ID
               WHERE pp.M_Product_ID = p_M_Product_ID
                 AND pp.IsActive = 'Y'
                 -- exclude invalid, attribute-set-instance (ASI) specific and packaging (HU/PI) specific
                 -- prices, matching the canonical ProductPriceQuery / get_Product_Price resolution
                 AND pp.IsInvalidPrice <> 'Y'
                 AND pp.IsAttributeDependant = 'N'
                 AND pp.M_HU_PI_Item_Product_ID IS NULL),
    -- Special: the depth-0 price, but ONLY when the assigned list is an override
    special AS (SELECT pr.PriceStd, pr.C_UOM_ID, pr.PLVName
                FROM priced pr,
                     root_info ri
                WHERE ri.is_override
                  AND pr.depth = 0
                ORDER BY pr.seqno_sort ASC,
                         pr.M_ProductPrice_ID ASC
                LIMIT 1),
    -- Base: the price from the TRUE base list of the chain -- the price list whose
    -- BasePriceList_ID IS NULL. It stays the standard/base price even when the customer's
    -- assigned list is a multi-level override; a middle override level is never treated as base.
    base AS (SELECT pr.PriceStd, pr.C_UOM_ID, pr.PLVName
             FROM priced pr
             WHERE pr.is_base_list
             ORDER BY pr.depth ASC,
                      pr.seqno_sort ASC,
                      pr.M_ProductPrice_ID ASC
             LIMIT 1)
SELECT s.PriceStd::numeric AS SpecialPriceStd,
       s.C_UOM_ID::numeric AS SpecialC_UOM_ID,
       s.PLVName::varchar  AS SpecialPLV,
       b.PriceStd::numeric AS BasePriceStd,
       b.C_UOM_ID::numeric AS BaseC_UOM_ID,
       b.PLVName::varchar  AS BasePLV
FROM (SELECT 1) d
         LEFT JOIN special s ON TRUE
         LEFT JOIN base b ON TRUE
$$
    LANGUAGE sql
    STABLE
;

COMMENT ON FUNCTION report.getSalesPriceSpecialAndBase(timestamp with time zone, numeric, numeric)
    IS 'Resolves, for a business-partner location and product on a given date, both the customer-specific special price (depth-0 override list) and the base/standard list price (base-list in the BasePriceList_ID chain). Either may be NULL. Used by report.getCustomerDeliveryPriceOverview.'
;
