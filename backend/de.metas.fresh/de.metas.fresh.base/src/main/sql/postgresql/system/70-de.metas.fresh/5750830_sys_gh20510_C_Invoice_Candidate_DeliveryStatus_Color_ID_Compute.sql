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

CREATE FUNCTION C_Invoice_Candidate_DeliveryStatus_Color_ID_Compute(p_Invoice_Candidate C_Invoice_Candidate) RETURNS numeric
    STABLE
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_ColorID numeric;
    v_Red     numeric := getColor_ID_By_SysConfig('Red_Color');
    v_Yellow  numeric := getColor_ID_By_SysConfig('Yellow_Color');
    v_Green   numeric := getColor_ID_By_SysConfig('Green_Color');
    v_Blue    numeric := getColor_ID_By_SysConfig('Blue_Color');
BEGIN

    SELECT (
               CASE
                   WHEN p_Invoice_Candidate.QtyDelivered = 0                                                                         THEN v_Red
                   WHEN (p_Invoice_Candidate.QtyDelivered > 0 AND p_Invoice_Candidate.QtyDelivered < p_Invoice_Candidate.QtyOrdered) THEN v_Yellow
                   WHEN (p_Invoice_Candidate.QtyDelivered = p_Invoice_Candidate.QtyOrdered)                                          THEN v_Green
                   WHEN (p_Invoice_Candidate.QtyDelivered > p_Invoice_Candidate.QtyOrdered)                                          THEN v_Blue
               END
               )

    INTO v_ColorID;

    RETURN v_ColorID;
END;
$$
;

