/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2021 metas GmbH
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

INSERT INTO c_incoterms (ad_client_id, ad_org_id, created, createdby, c_incoterms_id, isactive, description, name, updated, updatedby, value)
    (
        SELECT ad_client_id, ad_org_id, created, '99', nextval('C_INCOTERMS_SEQ'), 'Y', null, name, now(), '99', value

        FROM ad_ref_list where ad_reference_id = 501599
    )
;

INSERT INTO c_incoterms_trl (ad_client_id, ad_org_id, created, createdby, c_incoterms_trl_id, isactive, description, name, updated, updatedby, c_incoterms_id, ad_language)
    (
        SELECT ad_client_id, ad_org_id, created, '99', nextval('C_INCOTERMS_TRL_SEQ'), 'Y', description, name, now(), '99',  (select c_incoterms_id from c_incoterms where value ILIKE substring(ad_ref_list_trl.name, 0, position(' - ' in  ad_ref_list_trl.name))and c_incoterms.isactive = 'N'), ad_language

        FROM ad_ref_list_trl where ad_ref_list_id in  (select ad_ref_list_id from ad_ref_list where ad_reference_id = 501599)
    )
;