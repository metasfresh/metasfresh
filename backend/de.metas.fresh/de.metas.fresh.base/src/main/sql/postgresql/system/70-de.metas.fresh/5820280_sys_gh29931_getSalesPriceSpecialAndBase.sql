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
WITH
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
    -- Pick the SO price list matching the pricing system; country-specific wins over generic.
    -- (No core helper exists for this step -- the resolvers below all start from a price list.)
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
    -- The layering chain, from the core resolver: the price-list versions to search, ordered
    -- nearest-first (seq 1 = the list the customer is assigned to) down to the base list.
    -- Cycle-protection, the depth cap and the newest-valid-version-per-list pick all live in
    -- public.getPriceListVersionsUpToBase, so this report resolves prices the same way pricing
    -- does. An unresolvable root (no version valid on p_Date) yields NULL -> unnest gives no
    -- rows -> both prices come back NULL.
    chain AS (SELECT u.M_PriceList_Version_ID,
                     u.seq
              FROM pricelist_root r
                       CROSS JOIN LATERAL
                  unnest(public.getPriceListVersionsUpToBase(r.M_PriceList_ID, p_Date))
                  WITH ORDINALITY AS u(M_PriceList_Version_ID, seq)),
    -- Every rung of the chain that actually prices this article, tagged with whether that rung is
    -- the TRUE base list (its M_PriceList has no BasePriceList_ID). Invalid, attribute-set-instance
    -- (ASI) specific and packaging (HU/PI) specific prices are excluded, matching the canonical
    -- ProductPriceQuery / get_Product_Price resolution.
    priced AS (SELECT c.seq,
                      (pl.BasePriceList_ID IS NULL)  AS is_base_list,
                      pp.PriceStd,
                      pp.C_UOM_ID,
                      plv.Name                       AS PLVName,
                      COALESCE(pp.SeqNo, 2147483647) AS seqno_sort,
                      pp.M_ProductPrice_ID
               FROM chain c
                        JOIN M_PriceList_Version plv ON plv.M_PriceList_Version_ID = c.M_PriceList_Version_ID
                        JOIN M_PriceList         pl  ON pl.M_PriceList_ID = plv.M_PriceList_ID
                        JOIN M_ProductPrice pp ON pp.M_PriceList_Version_ID = c.M_PriceList_Version_ID
               WHERE pp.M_Product_ID = p_M_Product_ID
                 AND pp.IsActive = 'Y'
                 AND pp.IsInvalidPrice <> 'Y'
                 AND pp.IsAttributeDependant = 'N'
                 AND pp.M_HU_PI_Item_Product_ID IS NULL),
    -- Base = the standard price: the price on the TRUE base list of the chain (BasePriceList_ID
    -- IS NULL). It always reflects the base list -- NULL when the base list does not price this
    -- article, never an override that merely sits deeper. Prevents a customer-specific override
    -- from being reported as the base/standard price.
    base AS (SELECT pr.PriceStd, pr.C_UOM_ID, pr.PLVName
             FROM priced pr
             WHERE pr.is_base_list
             ORDER BY pr.seqno_sort ASC,
                      pr.M_ProductPrice_ID ASC
             LIMIT 1),
    -- Special = the customer-specific price: the nearest priced OVERRIDE rung (any non-base list
    -- layered on top of the base). NULL when the article is priced only on the base list -- there
    -- is then no customer-specific arrangement and only Base is shown.
    special AS (SELECT pr.PriceStd, pr.C_UOM_ID, pr.PLVName
                FROM priced pr
                WHERE NOT pr.is_base_list
                ORDER BY pr.seq ASC,
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
    IS 'Resolves, for a business-partner location and product on a given date, the customer-specific special price (the nearest override rung of the price-list layering chain that prices the article -- any non-base list) and the standard base-list price (the price on the true base list, BasePriceList_ID IS NULL). Special is NULL when the article is priced only on the base list (no customer-specific arrangement); Base is NULL when the base list does not price the article. Chain resolution is delegated to public.getPriceListVersionsUpToBase so the report prices articles the same way pricing does. Used by report.getCustomerDeliveryPriceOverview.'
;
