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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getFactorer_BankDetails(IN p_AD_Org_ID numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getFactorer_BankDetails(IN p_AD_Org_ID numeric)
    RETURNS TABLE
            (
                org_name        character varying,
                org_addressline character varying,
                org_address1    character varying,
                org_postal      character varying,
                org_city        character varying,
                org_country     character varying,
                bank_acct       character varying,
                bank_currency   character varying,
                bank_code       character varying,
                bank_name       character varying,
                bank_iban       character varying,
                bank_swift      character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(org_bp.name, '')    AS org_name,
       TRIM(
               COALESCE(org_bp.name || ', ', '') ||
               COALESCE(loc.address1 || ' ', '') ||
               COALESCE(loc.postal || ' ', '') ||
               COALESCE(loc.city, '')
       )                            AS org_addressline,
       COALESCE(loc.address1, '') as org_address1,
       COALESCE(loc.postal, '') as org_postal,
       COALESCE(loc.city, '') as org_city,
       COALESCE(c.name, '') as org_country,
       COALESCE(bpb.accountno, '')  AS bank_acct,
       cur.iso_code                 AS bank_currency,
       COALESCE(bank.routingno, '') AS bank_code,
       COALESCE(bank.name, '')      AS bank_name,
       COALESCE(bpb.iban, '')       AS bank_iban,
       COALESCE(bank.swiftcode, '') AS bank_swift
FROM ad_orginfo oi
         JOIN c_bpartner_location org_bpl
              ON org_bpl.c_bpartner_location_ID = oi.orgbp_location_id
         JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id
         JOIN C_Country c ON loc.C_Country_ID = c.C_Country_ID
         JOIN C_BPartner org_bp ON org_bpl.c_bpartner_id = org_bp.c_bpartner_id

         LEFT OUTER JOIN C_BPartner bpf ON bpf.IsFactorer = 'Y' AND oi.AD_Org_ID = bpf.AD_Org_ID
         LEFT OUTER JOIN c_bp_bankaccount bpb ON bpf.c_bpartner_id = bpb.c_bpartner_id
         LEFT OUTER JOIN c_bank bank ON bpb.c_bank_id = bank.c_bank_id
         LEFT JOIN C_Currency cur ON bpb.C_Currency_ID = cur.C_Currency_ID

WHERE oi.ad_org_id = p_AD_Org_ID
$$
;

