/*
 * #%L
 * de.metas.business
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

DROP VIEW IF EXISTS C_BPartner_Recent_With_ExternalSystem_V_ID
;

/*
Note: no need for union distinct. we might still have the same C_BPartner_ID with different Updated, and we make sure later that each partner is loaded just once.
 */
CREATE OR REPLACE VIEW C_BPartner_Recent_With_ExternalSystem_V_ID
AS
SELECT bp.C_BPartner_ID AS C_BPartner_Recent_V_ID, bp.C_BPartner_ID, bp.Updated, bp.AD_Org_ID, extRef.ExternalReference
FROM C_BPartner bp
         JOIN S_ExternalReference extRef ON extRef.referenced_ad_table_id = get_table_id('C_BPartner') AND extRef.record_id = bp.C_BPartner_ID
UNION
SELECT bpl.C_BPartner_ID, bpl.C_BPartner_ID, bpl.Updated, bpl.AD_Org_ID, extRef.ExternalReference
FROM C_BPartner_Location bpl
         JOIN s_externalreference extRef ON extRef.referenced_ad_table_id = get_table_id('C_BPartner_Location') AND extRef.record_id = bpl.C_BPartner_Location_ID
UNION
SELECT u.C_BPartner_ID, u.C_BPartner_ID, u.Updated, u.AD_Org_ID, extRef.ExternalReference
FROM AD_User u
         JOIN S_ExternalReference extRef ON extRef.referenced_ad_table_id = get_table_id('AD_User') AND extRef.record_id = u.AD_User_ID
WHERE u.C_BPartner_ID IS NOT NULL
;

DROP VIEW IF EXISTS C_BPartner_Recent_V
;

CREATE OR REPLACE VIEW C_BPartner_Recent_V
AS
SELECT C_BPartner_ID AS C_BPartner_Recent_V_ID, C_BPartner_ID, Updated, AD_Org_ID
FROM C_BPartner
UNION
SELECT C_BPartner_ID, C_BPartner_ID, Updated, AD_Org_ID
FROM C_BPartner_Location
UNION
SELECT C_BPartner_ID, C_BPartner_ID, Updated, AD_Org_ID
FROM AD_User
WHERE C_BPartner_ID IS NOT NULL
;
