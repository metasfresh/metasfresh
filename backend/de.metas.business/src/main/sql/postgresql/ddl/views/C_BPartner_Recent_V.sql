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

DROP VIEW IF EXISTS C_BPartner_Recent_V
;


CREATE OR REPLACE VIEW C_BPartner_Recent_V
AS
SELECT bp.C_BPartner_ID AS C_BPartner_Recent_V_ID, bp.C_BPartner_ID, bp.Updated, bp.ad_org_id, extSys.value AS externalsystem
FROM C_BPartner bp
         LEFT JOIN S_ExternalReference extRef ON extRef.referenced_ad_table_id = get_table_id('C_BPartner') AND extRef.record_id = bp.C_BPartner_ID
         LEFT JOIN ExternalSystem extSys ON extSys.ExternalSystem_ID = extRef.externalsystem_id
UNION
DISTINCT
SELECT bpl.C_BPartner_ID, bpl.C_BPartner_ID, bpl.Updated, bpl.ad_org_id, extSys.value AS externalsystem
FROM C_BPartner_Location bpl
         LEFT JOIN s_externalreference extRef ON extRef.referenced_ad_table_id = get_table_id('C_BPartner_Location') AND extRef.record_id = bpl.C_BPartner_Location_ID
         LEFT JOIN ExternalSystem extSys ON extSys.ExternalSystem_ID = extRef.externalsystem_id
UNION
DISTINCT
SELECT u.C_BPartner_ID, u.C_BPartner_ID, u.Updated, u.ad_org_id, extSys.value AS externalsystem
FROM AD_User u
         LEFT JOIN S_ExternalReference extRef ON extRef.referenced_ad_table_id = get_table_id('AD_User') AND extRef.record_id = u.AD_User_ID
         LEFT JOIN ExternalSystem extSys ON extSys.ExternalSystem_ID = extRef.externalsystem_id
WHERE u.C_BPartner_ID IS NOT NULL
;
