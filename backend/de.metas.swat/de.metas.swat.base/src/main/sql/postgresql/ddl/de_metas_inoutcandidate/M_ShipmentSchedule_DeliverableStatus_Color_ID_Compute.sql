/*
 * #%L
 * de.metas.swat.base
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

create or replace function m_shipmentschedule_deliverablestatus_color_id_compute(p_m_shipmentschedule m_shipmentschedule) returns numeric
    stable
    language plpgsql
as
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
                   WHEN p_m_shipmentschedule.qtyreserved = 0     THEN NULL
                   WHEN p_m_shipmentschedule.qtytodeliver = 0     THEN v_Red
                   WHEN (p_m_shipmentschedule.qtytodeliver > 0 AND p_m_shipmentschedule.qtytodeliver < p_m_shipmentschedule.qtyreserved) THEN v_Yellow
                   WHEN (p_m_shipmentschedule.qtytodeliver = p_m_shipmentschedule.qtyreserved)                                          THEN v_Green
                   WHEN (p_m_shipmentschedule.qtytodeliver > p_m_shipmentschedule.qtyreserved)                                          THEN v_Blue
               END
               )

    INTO v_ColorID;

    RETURN v_ColorID;
END;
$$;
