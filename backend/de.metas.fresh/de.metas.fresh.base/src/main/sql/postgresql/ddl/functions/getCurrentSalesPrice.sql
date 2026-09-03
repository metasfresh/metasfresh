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

DROP FUNCTION IF EXISTS report.getCurrentSalesPrice(timestamp with time zone,
                                                    numeric,
                                                    numeric
)
;

CREATE OR REPLACE FUNCTION report.getCurrentSalesPrice(
    p_Date                   timestamp with time zone,
    p_C_BPartner_Location_ID numeric,
    p_M_Product_ID           numeric
)
    RETURNS TABLE
            (
                PriceStd               numeric,
                C_Currency_ID          numeric,
                C_UOM_ID               numeric,
                M_PriceList_ID         numeric,
                M_PriceList_Version_ID numeric,
                M_ProductPrice_ID      numeric,
                IsFromBasePriceList    char(1)
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
                                                         c.M_PriceList_ID,
                                                         plv.M_PriceList_Version_ID
                   FROM pl_chain c
                            JOIN M_PriceList_Version plv ON plv.M_PriceList_ID = c.M_PriceList_ID
                   WHERE plv.IsActive = 'Y'
                     AND plv.ValidFrom <= p_Date
                   ORDER BY c.M_PriceList_ID, plv.ValidFrom DESC)
SELECT pp.PriceStd,
       pl.C_Currency_ID,
       pp.C_UOM_ID,
       pl.M_PriceList_ID,
       x.M_PriceList_Version_ID,
       pp.M_ProductPrice_ID,
       (CASE WHEN x.depth = 0 THEN 'N' ELSE 'Y' END)::char(1) AS IsFromBasePriceList
FROM plv_per_pl x
         JOIN M_PriceList pl ON pl.M_PriceList_ID = x.M_PriceList_ID
         JOIN M_ProductPrice pp ON pp.M_PriceList_Version_ID = x.M_PriceList_Version_ID
WHERE pp.M_Product_ID = p_M_Product_ID
  AND pp.IsActive = 'Y'
ORDER BY x.depth ASC,                        -- direct PL wins over base PL
         COALESCE(pp.SeqNo, 2147483647) ASC, -- then lowest SeqNo (NULL last)
         pp.M_ProductPrice_ID ASC            -- deterministic tiebreaker
LIMIT 1
$$
    LANGUAGE sql
    STABLE
;