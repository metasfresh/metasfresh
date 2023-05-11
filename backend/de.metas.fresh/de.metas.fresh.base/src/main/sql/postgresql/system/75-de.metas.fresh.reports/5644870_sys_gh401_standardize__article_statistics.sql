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

-- new column for modified function using the org name
ALTER TABLE report.fresh_product_statistics_report
ADD COLUMN ad_org_name VARCHAR;

drop function report.fresh_product_statistics_report(c_period_id numeric, issotrx varchar, c_bpartner_id numeric, c_activity_id numeric, m_product_id numeric, m_product_category_id numeric, m_attributesetinstance_id numeric, ad_org_id numeric, ad_language varchar);

create or replace function report.fresh_product_statistics_report(c_period_id numeric, issotrx character varying, c_bpartner_id numeric, c_activity_id numeric, m_product_id numeric, m_product_category_id numeric, m_attributesetinstance_id numeric, ad_org_id numeric, ad_language character varying)
    returns SETOF report.fresh_product_statistics_report
    language sql
as
$$
SELECT
    *,
    1 AS UnionOrder,
    (SELECT name from ad_org where ad_org.ad_org_id = $8)::varchar AS ad_org_name
FROM
    report.fresh_statistics ($1, $2, $3, $4, $5, $6, $7, $8, $9)
UNION ALL
SELECT
    null, null, pc_name, P_name, P_value, UOMSymbol,
    Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
    SUM( Period1Sum ) AS Period1Sum, SUM( Period2Sum ) AS Period2Sum, SUM( Period3Sum ) AS Period3Sum,
    SUM( Period4Sum ) AS Period4Sum, SUM( Period5Sum ) AS Period5Sum, SUM( Period6Sum ) AS Period6Sum,
    SUM( Period7Sum ) AS Period7Sum, SUM( Period8Sum ) AS Period8Sum, SUM( Period9Sum ) AS Period9Sum,
    SUM( Period10Sum ) AS Period10Sum, SUM( Period11Sum ) AS Period11Sum, SUM( Period12Sum ) AS Period12Sum,
    SUM( TotalSum ) AS TotalSum, SUM( TotalAmt ) AS TotalAmt,
    StartDate, EndDate, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id, iso_code,
    2 AS UnionOrder,
    (SELECT name from ad_org where ad_org.ad_org_id = $8)::varchar AS ad_org_name
FROM
    report.fresh_statistics ($1, $2, $3, $4, $5, $6, $7, $8, $9)
GROUP BY
    pc_name, P_name, P_value, UOMSymbol,
    Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
    StartDate, EndDate, param_bp, param_Activity, param_product, param_Product_Category, Param_Attributes,ad_org_id, iso_code
ORDER BY
    p_name, UnionOrder, TotalSum DESC
$$;

-- Default Value as current year and mandatory to yes
-- 2022-06-23T07:34:23.806Z
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT C_Year_ID AS DefaultValue FROM C_Year WHERE fiscalyear=(SELECT extract(''year'' from now())::varchar)', IsMandatory='Y',Updated=TO_TIMESTAMP('2022-06-23 09:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540695
;
