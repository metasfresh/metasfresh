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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Description(IN p_ShipperTransportation_ID numeric)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Description(IN p_ShipperTransportation_ID numeric)
    RETURNS TABLE
            (
                description     varchar(255),
                documentno      varchar(30),
                datedoc         timestamp,
                PickupLocation  varchar(360),
                name            varchar(60),
                datetobefetched timestamp with time zone,
                UserName        varchar(60)
            )
AS
$$
SELECT st.description AS description,
       st.documentno  AS documentno,
       st.datedoc     AS datedoc,
       TRIM(
               COALESCE(bp.name || E'\n', '') ||
               COALESCE(bpl.Address || E'\n', '')
       )              AS PickupLocation,
       t.name,
       st.DateToBeFetched,
       us.name        AS UserName
FROM M_ShipperTransportation st
         INNER JOIN C_BPartner_Location bpl ON st.Shipper_Location_ID = bpl.C_BPartner_Location_ID
         INNER JOIN C_BPartner bp ON bpl.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_Location l ON bpl.C_Location_ID = l.C_Location_ID
         LEFT OUTER JOIN AD_User us ON st.SalesRep_ID = us.AD_User_ID
         LEFT OUTER JOIN M_Tour t ON st.M_Tour_ID = t.M_Tour_ID
WHERE st.M_ShipperTransportation_ID = p_ShipperTransportation_ID
    ;
$$
    LANGUAGE sql STABLE
;

