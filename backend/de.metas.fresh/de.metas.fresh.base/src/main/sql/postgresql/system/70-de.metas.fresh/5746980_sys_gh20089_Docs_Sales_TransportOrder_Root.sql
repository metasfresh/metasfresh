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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Root(IN p_ShipperTransportation_ID numeric,
                                                                                         IN p_Language                 Character Varying(6))
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Root(IN p_ShipperTransportation_ID numeric,
                                                                                 IN p_Language                 Character Varying(6))
    RETURNS TABLE
            (
                AD_Org_ID              numeric(10, 0),
                DocStatus              varchar(2),
                PrintName              varchar(60),
                c_bpartner_location_id numeric(10)
            )
AS
$$
SELECT st.AD_Org_ID,
       st.DocStatus,
       dt.PrintName,
       (SELECT bpl.C_BPartner_Location_ID
        FROM C_BPartner_Location bpl
        WHERE bp.C_BPartner_ID = bpl.C_BPartner_ID
          AND bpl.isActive = 'Y'
        ORDER BY isbilltodefault DESC, isbillto DESC
        LIMIT 1) AS C_BPartner_Location_ID
FROM M_ShipperTransportation st
         INNER JOIN M_Shipper s ON st.M_Shipper_ID = s.M_Shipper_ID
         INNER JOIN C_BPartner bp ON s.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_DocType dt ON st.C_DocType_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON st.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_Language
WHERE st.M_ShipperTransportation_ID = p_ShipperTransportation_ID
    ;
$$
    LANGUAGE sql STABLE
;

