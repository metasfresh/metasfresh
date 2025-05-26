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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU_BAL (IN p_InOut_ID numeric)
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU_BAL(IN p_InOut_ID numeric)
    RETURNS TABLE
            (
                CountryCode     Text,
                isGoodsNoteHidden CHAR,
                isSignatureHidden CHAR
            )
AS
$$

SELECT c.countrycode,
       report.IsHiddenReportElement(io.C_DocType_ID, 'GoodsNote') AS isGoodsNoteHidden,
       report.IsHiddenReportElement(io.C_DocType_ID, 'Signature') AS isSignatureHidden
FROM m_inout io
         INNER JOIN AD_Org org ON io.ad_org_id = org.ad_org_id
         LEFT OUTER JOIN AD_OrgInfo orginfo ON orginfo.ad_org_id = org.ad_org_id
         LEFT OUTER JOIN C_BPartner_Location org_loc ON orginfo.Orgbp_Location_ID = org_loc.C_BPartner_Location_ID
         LEFT OUTER JOIN C_Location org_l ON org_loc.C_Location_ID = org_l.C_Location_ID
         LEFT OUTER JOIN C_Country c ON org_l.C_Country_ID = c.C_Country_ID
WHERE io.m_inout_id = p_InOut_ID
    ;

$$
    LANGUAGE sql
    STABLE
;
