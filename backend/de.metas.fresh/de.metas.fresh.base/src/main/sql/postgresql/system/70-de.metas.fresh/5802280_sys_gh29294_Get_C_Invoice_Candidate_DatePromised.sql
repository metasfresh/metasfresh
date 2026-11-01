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

DROP FUNCTION IF EXISTS Get_C_Invoice_Candidate_DatePromised(numeric)
;

CREATE OR REPLACE FUNCTION Get_C_Invoice_Candidate_DatePromised(p_C_Invoice_Candidate_ID numeric)
    RETURNS date
AS
$$
SELECT ol.DatePromised::date
FROM C_Invoice_Candidate ic
         LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID = ic.C_OrderLine_ID
WHERE ic.C_Invoice_Candidate_ID = p_C_Invoice_Candidate_ID
$$
    LANGUAGE sql STABLE
;
