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

DROP FUNCTION IF EXISTS getCustoms_Invoice_Line_SSCC18 (IN p_c_customs_invoice_line_id numeric)
;

CREATE OR REPLACE FUNCTION getCustoms_Invoice_Line_SSCC18(p_c_customs_invoice_line_id NUMERIC)
    RETURNS TEXT
    LANGUAGE sql
    STABLE
AS
$$
SELECT STRING_AGG(lua_sscc.value, E'\n') FILTER (WHERE lua_sscc.value IS NOT NULL) AS SSCC18
FROM M_InOutLine_To_C_Customs_Invoice_Line io_to_ci
         LEFT OUTER JOIN M_HU_Assignment asgn
                         ON asgn.AD_Table_ID = ((SELECT get_Table_ID('M_InOutLine')))
                             AND asgn.Record_ID = io_to_ci.M_InOutLine_ID
         LEFT OUTER JOIN M_HU_Attribute lua_sscc
                         ON asgn.M_HU_ID = lua_sscc.M_HU_ID
                             AND lua_sscc.M_Attribute_ID = (SELECT M_Attribute_ID
                                                            FROM M_Attribute
                                                            WHERE name = 'SSCC18')
WHERE io_to_ci.c_customs_invoice_line_id = p_c_customs_invoice_line_id
$$
;
