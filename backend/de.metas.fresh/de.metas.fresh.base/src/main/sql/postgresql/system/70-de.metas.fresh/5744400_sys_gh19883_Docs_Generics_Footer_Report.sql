DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Generics_Footer_Report(IN p_org_id numeric)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Generics_Footer_Report(p_org_id numeric)
    RETURNS TABLE
            (
                org_name        character varying,
                org_address1    character varying,
                org_postal      character varying,
                org_city        character varying,
                org_bank_acct   character varying,
                org_bank_name   character varying,
                org_bank_blz    character varying,
                org_bank_iban   character varying,
                org_bank_swift  character varying,
                org_addressline character varying,
                description     character varying,
                manager         character varying,
                vataxid         character varying,
                phone           character varying,
                fax             character varying,
                email           character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(org_bp.name, '')               AS org_name,
       COALESCE(loc.address1, '')              AS org_address1,
       COALESCE(loc.postal, '')                AS org_postal,
       COALESCE(loc.city, '')                  AS org_city,
       COALESCE(bpb.accountno, '')             AS org_bank_acct,
       COALESCE(bank.name, '')                 AS org_bank_name,
       COALESCE(bpb.routingno, '')             AS org_bank_blz,
       COALESCE(bpb.iban, '')                  AS org_bank_iban,
       COALESCE(bank.swiftcode, '')            AS org_bank_swift,
       TRIM(
               CASE WHEN org_bp.name IS NULL THEN '' ELSE org_bp.name || ', ' END ||
               CASE WHEN loc.address1 IS NULL THEN '' ELSE loc.address1 || ', ' END ||
               CASE WHEN loc.postal IS NULL THEN '' ELSE loc.postal || ', ' END ||
               CASE WHEN loc.city IS NULL THEN '' ELSE loc.city || ', ' END
       )                                       AS org_addressline,
       org_bp.description                      AS description,
       usr.firstname || ' ' || usr.lastname    AS manager,
       org_bp.vataxid                          AS vataxid,
       COALESCE(usr.phone, org_bpl.phone, '-') AS phone,
       COALESCE(usr.fax, org_bpl.fax, '-')     AS fax,
       usr.email                               AS email

FROM ad_org org
         INNER JOIN c_bpartner org_bp ON org.ad_org_id = org_bp.ad_orgbp_id
         INNER JOIN c_bpartner_location org_bpl ON org_bp.c_bpartner_id = org_bpl.c_bpartner_id
         INNER JOIN ad_user usr ON org_bp.c_bpartner_id = usr.c_bpartner_id AND usr.isdefaultcontact = 'Y'
         LEFT OUTER JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id
         LEFT OUTER JOIN c_country country ON loc.c_country_id = country.c_country_id
         LEFT OUTER JOIN c_bp_bankaccount bpb ON org_bp.c_bpartner_id = bpb.c_bpartner_id
         LEFT OUTER JOIN c_bank bank ON bpb.c_bank_id = bank.c_bank_id

WHERE org.ad_org_id = p_org_id
ORDER BY bpb.isDefault DESC
$$
;
