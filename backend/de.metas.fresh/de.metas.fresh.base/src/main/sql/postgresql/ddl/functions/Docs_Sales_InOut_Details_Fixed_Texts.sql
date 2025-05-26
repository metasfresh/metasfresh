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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Fixed_Texts (IN p_InOut_ID numeric)
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_Fixed_Texts(IN p_InOut_ID numeric)
    RETURNS TABLE
            (
                isGoodsNoteHidden CHAR,
                isSignatureHidden CHAR
            )
AS
$$

SELECT report.IsHiddenReportElement(io.C_DocType_ID, 'GoodsNote') AS isGoodsNoteHidden,
       report.IsHiddenReportElement(io.C_DocType_ID, 'Signature') AS isSignatureHidden
FROM m_inout io
WHERE io.m_inout_id = p_InOut_ID
    ;

$$
    LANGUAGE sql
    STABLE
;
