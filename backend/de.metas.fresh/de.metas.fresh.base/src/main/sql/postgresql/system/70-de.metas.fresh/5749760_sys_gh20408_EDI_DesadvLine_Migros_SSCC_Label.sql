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

DROP FUNCTION IF EXISTS report.EDI_DesadvLine_Migros_SSCC_Label(IN p_PInstance_ID numeric)
;

CREATE FUNCTION report.EDI_DesadvLine_Migros_SSCC_Label(IN p_PInstance_ID numeric)
    RETURNS TABLE
            (
                org_address     Character Varying,
                SSCC            Character Varying,
                p_customervalue Character Varying,
                priceactual     numeric,
                p_name          Character Varying,
                cu_per_tu       numeric,
                tu_per_lu       numeric,
                net_weight      numeric,
                gross_weight    numeric,
                order_docno     Character Varying,
                p_value         Character Varying,
                lotcode         Character Varying,
                paletno         Character Varying,
                customer        Character Varying,
                ad_language     Character Varying,
                lotnumberdate   text
            )
AS
$$
SELECT DISTINCT *
FROM (WITH value_check AS (SELECT COUNT(DISTINCT p_value) AS distinct_count
                           FROM report.fresh_EDI_DesadvLine_SSCC_Label_Report
                           WHERE ad_pinstance_id = p_PInstance_ID)
      SELECT org_address,
             sscc,
             CASE
                 WHEN vc.distinct_count > 1 THEN NULL
                                            ELSE r.p_customervalue
             END AS p_customervalue,
             CASE
                 WHEN vc.distinct_count > 1 THEN NULL
                                            ELSE r.priceactual
             END AS priceactual,
             CASE
                 WHEN vc.distinct_count > 1 THEN NULL
                                            ELSE r.p_name
             END AS p_name,
             CASE
                 WHEN vc.distinct_count > 1 THEN NULL
                                            ELSE r.cu_per_tu
             END AS cu_per_tu,
             CASE
                 WHEN vc.distinct_count > 1 THEN NULL
                                            ELSE r.tu_per_lu
             END AS tu_per_lu,
             CASE
                 WHEN vc.distinct_count > 1 THEN NULL
                                            ELSE r.net_weight
             END AS net_weight,
             CASE
                 WHEN vc.distinct_count > 1 THEN NULL
                                            ELSE r.gross_weight
             END AS gross_weight,
             order_docno,
             CASE
                 WHEN vc.distinct_count > 1 THEN NULL
                                            ELSE r.p_value
             END AS p_value,
             lotcode,
             paletno,
             customer,
             ad_language,
             lotnumberdate
      FROM report.fresh_EDI_DesadvLine_SSCC_Label_Report r
               CROSS JOIN value_check vc
      WHERE r.ad_pinstance_id = p_PInstance_ID
      ORDER BY r.EDI_DesadvLine_SSCC_ID) result
    ;

$$
    LANGUAGE sql
    STABLE
;
