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

DROP FUNCTION IF EXISTS report.Fresh_Org_BankAccount(IN p_Org_ID numeric)
;

CREATE FUNCTION report.Fresh_Org_BankAccount(p_Org_ID numeric)
    RETURNS TABLE
            (
                org_bank_acct  character varying,
                org_bank_name  character varying,
                org_bank_blz   character varying,
                org_bank_iban  character varying,
                org_bank_swift character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(bpb.accountno, '')                 AS org_bank_acct,
       COALESCE(bank.name, '')                     AS org_bank_name,
       COALESCE(bpb.routingno, bank.routingno, '') AS org_bank_blz,
       COALESCE(bpb.iban, '')                      AS org_bank_iban,
       COALESCE(bank.swiftcode, '')                AS org_bank_swift

FROM ad_org org
         INNER JOIN c_bpartner org_bp ON org.ad_org_id = org_bp.ad_orgbp_id
         LEFT OUTER JOIN c_bp_bankaccount bpb ON org_bp.c_bpartner_id = bpb.c_bpartner_id AND bpb.IsActive = 'Y' AND (bpb.bankaccounttype = 'C' OR bpb.bankaccounttype IS NULL)
         LEFT OUTER JOIN c_bank bank ON bpb.c_bank_id = bank.c_bank_id

WHERE org.ad_org_id = p_Org_ID

ORDER BY (bpb.bankaccounttype = 'C') DESC, -- Prefer 'C' accounts over others (including NULL)
         (bpb.IsDefault = 'Y') DESC,       -- Prefer default accounts
         bpb.C_BP_BankAccount_ID DESC      -- Prefer newest / highest ID
LIMIT 1
$$
;



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Footer_Report(numeric, character(1))
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Generics_Footer_Report(p_org_id             numeric,
                                                                               p_isFactoringPartner character(1) DEFAULT 'N')
    RETURNS TABLE
            (
                org_name              character varying,
                org_address1          character varying,
                org_postal            character varying,
                org_city              character varying,
                org_bank_acct         character varying,
                org_bank_name         character varying,
                org_bank_blz          character varying,
                org_bank_iban         character varying,
                org_bank_swift        character varying,
                org_bank_currency     character varying,
                org_addressline       character varying,
                orgpartnerdescription character varying,
                manager               character varying,
                vataxid               character varying,
                phone                 character varying,
                phone2                character varying,
                fax                   character varying,
                email                 character varying,
                url                   character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(org_bp.name, '')               AS org_name,
       COALESCE(loc.address1, '')              AS org_address1,
       COALESCE(loc.postal, '')                AS org_postal,
       COALESCE(loc.city, '')                  AS org_city,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bpbf.accountno
                       ELSE org_ba.org_bank_acct
               END,
               ''
       )                                       AS org_bank_acct,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bankf.name
                       ELSE org_ba.org_bank_name
               END,
               ''
       )                                       AS org_bank_name,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bpbf.routingno
                       ELSE org_ba.org_bank_blz
               END,
               ''
       )                                       AS org_bank_blz,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bpbf.iban
                       ELSE org_ba.org_bank_iban
               END,
               ''
       )                                       AS org_bank_iban,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bankf.swiftcode
                       ELSE org_ba.org_bank_swift
               END,
               ''
       )                                       AS org_bank_swift,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN curf.iso_code
                       ELSE cur.iso_code
               END,
               ''
       )                                       AS org_bank_currency,
       TRIM(
               CASE WHEN org_bp.name IS NULL THEN '' ELSE org_bp.name || ', ' END ||
               CASE WHEN loc.address1 IS NULL THEN '' ELSE loc.address1 || ', ' END ||
               CASE WHEN loc.postal IS NULL THEN '' ELSE loc.postal || ', ' END ||
               CASE WHEN loc.city IS NULL THEN '' ELSE loc.city || ', ' END
       )                                       AS org_addressline,
       org_bp.description                      AS orgpartnerdescription,
       usr.firstname || ' ' || usr.lastname    AS manager,
       org_bp.vataxid                          AS vataxid,
       COALESCE(usr.phone, org_bpl.phone, '-') AS phone,
       usr.phone2                              AS phone2,
       COALESCE(usr.fax, org_bpl.fax, '-')     AS fax,
       COALESCE(usr.email, '-')                AS email,
       org_bp.url

FROM ad_org org
         INNER JOIN c_bpartner org_bp ON org.ad_org_id = org_bp.ad_orgbp_id
         INNER JOIN c_bpartner_location org_bpl ON org_bp.c_bpartner_id = org_bpl.c_bpartner_id
         INNER JOIN ad_user usr ON org_bp.c_bpartner_id = usr.c_bpartner_id AND usr.isdefaultcontact = 'Y'
         INNER JOIN LATERAL report.Fresh_Org_BankAccount(org.AD_Org_ID) org_ba ON TRUE
         LEFT OUTER JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id
         LEFT OUTER JOIN c_country country ON loc.c_country_id = country.c_country_id
         LEFT OUTER JOIN c_bp_bankaccount bpb ON org_bp.c_bpartner_id = bpb.c_bpartner_id
         LEFT OUTER JOIN c_bank bank ON bpb.c_bank_id = bank.c_bank_id
         LEFT OUTER JOIN C_Currency cur ON bpb.C_Currency_ID = cur.C_Currency_ID

         LEFT OUTER JOIN C_BPartner bpf ON bpf.IsFactorer = 'Y' AND org.AD_Org_ID = bpf.AD_Org_ID
         LEFT OUTER JOIN c_bp_bankaccount bpbf ON bpf.c_bpartner_id = bpbf.c_bpartner_id
         LEFT OUTER JOIN c_bank bankf ON bpbf.c_bank_id = bankf.c_bank_id
         LEFT OUTER JOIN C_Currency curf ON bpbf.C_Currency_ID = curf.C_Currency_ID

WHERE org.ad_org_id = p_org_id
ORDER BY bpb.isDefault DESC
LIMIT 1
$$
;

