DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Footer_Report(numeric,
                                                                                       character(1),
                                                                                       IN p_ad_language character varying)
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Footer_Report(numeric,
                                                                                       character(1),
                                                                                       IN p_ad_language character varying,
                                                                                       IN p_c_invoice_id numeric)
;

/*
 * Two overloads:
 * - (p_org_id, p_isFactoringPartner, p_ad_language): every document except the sales invoice; unchanged result.
 * - (p_org_id, p_isFactoringPartner, p_ad_language, p_c_invoice_id): the sales invoice footer. No parameter has a
 *   default, so a call with 1 to 3 arguments can only resolve to the first overload.
 *
 * p_c_invoice_id: the sales invoice whose footer is printed. NULL prints the organisation's VAT ID like the
 * 3-argument overload. With an invoice:
 * - the invoice's fiscal registration has no representative: vataxid = the stored SellerVATaxID, or the
 *   organisation's VAT ID when the invoice stores none;
 * - the invoice's fiscal registration has a representative: vataxid = the organisation's VAT ID, and the
 *   taxrep_* columns carry the representative's name, address (the registration's location, else the
 *   representative's bill-to address) and the stored SellerVATaxID.
 * The taxrep_* columns are NULL in every other case.
 */
CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Generics_Footer_Report(p_org_id             numeric,
                                                                               p_isFactoringPartner character(1),
                                                                               IN p_ad_language     character varying,
                                                                               IN p_c_invoice_id    numeric)
    RETURNS TABLE
            (
                org_name              character varying,
                org_address1          character varying,
                org_postal            character varying,
                org_city              character varying,
                org_country           character varying,
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
                url                   character varying,
                taxrep_name           character varying,
                taxrep_address        character varying,
                taxrep_vataxid        character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(org_bp.name, '')                    AS org_name,
       COALESCE(loc.address1, '')                   AS org_address1,
       COALESCE(loc.postal, '')                     AS org_postal,
       COALESCE(loc.city, '')                       AS org_city,
       COALESCE(country_trl.name, country.name, '') AS org_country,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bpbf.accountno
                       ELSE org_ba.org_bank_acct
               END,
               ''
       )                                            AS org_bank_acct,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bankf.name
                       ELSE org_ba.org_bank_name
               END,
               ''
       )                                            AS org_bank_name,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bpbf.routingno
                       ELSE org_ba.org_bank_blz
               END,
               ''
       )                                            AS org_bank_blz,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bpbf.iban
                       ELSE org_ba.org_bank_iban
               END,
               ''
       )                                            AS org_bank_iban,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN bankf.swiftcode
                       ELSE org_ba.org_bank_swift
               END,
               ''
       )                                            AS org_bank_swift,
       COALESCE(
               CASE
                   WHEN bpf.c_bpartner_id IS NOT NULL AND p_isFactoringPartner = 'Y'
                       THEN curf.iso_code
                       ELSE cur.iso_code
               END,
               ''
       )                                            AS org_bank_currency,
       TRIM(
               CASE WHEN org_bp.name IS NULL THEN '' ELSE org_bp.name || ', ' END ||
               CASE WHEN loc.address1 IS NULL THEN '' ELSE loc.address1 || ', ' END ||
               CASE WHEN loc.postal IS NULL THEN '' ELSE loc.postal || ', ' END ||
               CASE WHEN loc.city IS NULL THEN '' ELSE loc.city || ', ' END
       )                                            AS org_addressline,
       org_bp.description                           AS orgpartnerdescription,
       usr.firstname || ' ' || usr.lastname         AS manager,
       CASE
           WHEN rep.c_bpartner_id IS NOT NULL
               THEN org_bp.vataxid
               ELSE COALESCE(inv.sellervataxid, org_bp.vataxid)
       END                                          AS vataxid,
       COALESCE(usr.phone, org_bpl.phone, '-')      AS phone,
       usr.phone2                                   AS phone2,
       COALESCE(usr.fax, org_bpl.fax, '-')          AS fax,
       COALESCE(usr.email, '-')                     AS email,
       org_bp.url,
       rep.name                                     AS taxrep_name,
       NULLIF(CONCAT_WS(', ',
                        NULLIF(TRIM(rep_loc.address1), ''),
                        NULLIF(TRIM(CONCAT_WS(' ', NULLIF(TRIM(rep_loc.postal), ''), NULLIF(TRIM(rep_loc.city), ''))), ''),
                        COALESCE(rep_country_trl.name, rep_country.name)
              ), '')                                AS taxrep_address,
       CASE WHEN rep.c_bpartner_id IS NOT NULL THEN inv.sellervataxid END AS taxrep_vataxid

FROM ad_org org
         INNER JOIN c_bpartner org_bp ON org.ad_org_id = org_bp.ad_orgbp_id
         LEFT OUTER JOIN ad_orginfo org_info ON org.ad_org_id = org_info.ad_org_id
         INNER JOIN c_bpartner_location org_bpl ON org_bp.c_bpartner_id = org_bpl.c_bpartner_id
         INNER JOIN ad_user usr ON org_bp.c_bpartner_id = usr.c_bpartner_id AND usr.isdefaultcontact = 'Y'
         INNER JOIN LATERAL report.Fresh_Org_BankAccount(org.AD_Org_ID) org_ba ON TRUE
         LEFT OUTER JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id
         LEFT OUTER JOIN c_country country ON loc.c_country_id = country.c_country_id
         LEFT OUTER JOIN c_country_trl country_trl ON country_trl.c_country_id = country.c_country_id AND country_trl.ad_language = p_ad_language
         LEFT OUTER JOIN c_bp_bankaccount bpb ON org_bp.c_bpartner_id = bpb.c_bpartner_id
         LEFT OUTER JOIN c_bank bank ON bpb.c_bank_id = bank.c_bank_id
         LEFT OUTER JOIN C_Currency cur ON bpb.C_Currency_ID = cur.C_Currency_ID

         LEFT OUTER JOIN C_BPartner bpf ON bpf.IsFactorer = 'Y' AND org.AD_Org_ID = bpf.AD_Org_ID
         LEFT OUTER JOIN c_bp_bankaccount bpbf ON bpf.c_bpartner_id = bpbf.c_bpartner_id
         LEFT OUTER JOIN c_bank bankf ON bpbf.c_bank_id = bankf.c_bank_id
         LEFT OUTER JOIN C_Currency curf ON bpbf.C_Currency_ID = curf.C_Currency_ID

         LEFT OUTER JOIN c_invoice inv ON inv.c_invoice_id = p_c_invoice_id
         LEFT OUTER JOIN c_fiscal_representation fr ON fr.c_fiscal_representation_id = inv.c_fiscal_representation_id
         LEFT OUTER JOIN c_bpartner rep ON rep.c_bpartner_id = fr.c_bpartner_representative_id
         LEFT OUTER JOIN c_bpartner_location rep_bpl ON rep_bpl.c_bpartner_location_id = fr.c_bpartner_location_id
    -- a registration without location: the representative's bill-to address, as the e-invoice (BG-12) takes it
         LEFT OUTER JOIN LATERAL (SELECT rep_bpl_fallback.c_location_id
                                  FROM c_bpartner_location rep_bpl_fallback
                                  WHERE rep_bpl_fallback.c_bpartner_id = rep.c_bpartner_id
                                    AND rep_bpl_fallback.isactive = 'Y'
                                  ORDER BY rep_bpl_fallback.isbillto DESC, rep_bpl_fallback.c_bpartner_location_id
                                  LIMIT 1) rep_fallback ON fr.c_bpartner_location_id IS NULL
         LEFT OUTER JOIN c_location rep_loc ON rep_loc.c_location_id = COALESCE(rep_bpl.c_location_id, rep_fallback.c_location_id)
         LEFT OUTER JOIN c_country rep_country ON rep_country.c_country_id = rep_loc.c_country_id
         LEFT OUTER JOIN c_country_trl rep_country_trl ON rep_country_trl.c_country_id = rep_country.c_country_id AND rep_country_trl.ad_language = p_ad_language

WHERE org.ad_org_id = p_org_id
-- Prefer the location explicitly configured in AD_OrgInfo (OrgBP_Location_ID) when it is set
-- and belongs to the org BPartner; otherwise fall back to the default bill-to / ship-to address.
-- Kept as an ordering preference (not a restricting join) so a missing/foreign AD_OrgInfo
-- location never leaves the footer empty.
ORDER BY CASE
             WHEN org_info.orgbp_location_id IS NOT NULL
                 AND org_bpl.c_bpartner_location_id = org_info.orgbp_location_id
                 THEN 0
                 ELSE 1
         END,
         org_bpl.isbilltodefault DESC,
         org_bpl.isshiptodefault DESC,
         bpb.isDefault DESC
LIMIT 1
$$
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Generics_Footer_Report(p_org_id             numeric,
                                                                               p_isFactoringPartner character(1) DEFAULT 'N',
                                                                               IN p_ad_language     character varying DEFAULT 'de_DE')
    RETURNS TABLE
            (
                org_name              character varying,
                org_address1          character varying,
                org_postal            character varying,
                org_city              character varying,
                org_country           character varying,
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
SELECT f.org_name,
       f.org_address1,
       f.org_postal,
       f.org_city,
       f.org_country,
       f.org_bank_acct,
       f.org_bank_name,
       f.org_bank_blz,
       f.org_bank_iban,
       f.org_bank_swift,
       f.org_bank_currency,
       f.org_addressline,
       f.orgpartnerdescription,
       f.manager,
       f.vataxid,
       f.phone,
       f.phone2,
       f.fax,
       f.email,
       f.url
FROM de_metas_endcustomer_fresh_reports.Docs_Generics_Footer_Report(p_org_id,
                                                                    p_isFactoringPartner,
                                                                    p_ad_language,
                                                                    NULL::numeric) f
$$
;
