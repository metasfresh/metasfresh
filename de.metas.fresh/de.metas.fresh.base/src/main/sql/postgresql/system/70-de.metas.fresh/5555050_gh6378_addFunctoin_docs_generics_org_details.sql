DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_generics_org_details(org_id numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_generics_org_details(org_id numeric)
    RETURNS TABLE
            (
                org_name        varchar,
                org_addressline varchar,
                address1        varchar,
                postal          varchar,
                city            varchar,
                country         varchar
            )
AS
$BODY$
SELECT COALESCE(org_bp.name, '')  as org_name,
       trim(
                           COALESCE(org_bp.name || ', ', '') ||
                           COALESCE(loc.address1 || ' ', '') ||
                           COALESCE(loc.postal || ' ', '') ||
                           COALESCE(loc.city, '')
           )                      as org_addressline,
       COALESCE(loc.address1, '') as address1,
       COALESCE(loc.postal, '')   as postal,
       COALESCE(loc.city, '')     as city,
       c.Name                     as country
FROM ad_orginfo oi
         JOIN c_bpartner_location org_bpl
              ON org_bpl.c_bpartner_location_ID = oi.orgbp_location_id AND org_bpl.isActive = 'Y'
         JOIN c_location loc ON org_bpl.c_location_id = loc.c_location_id AND loc.isActive = 'Y'
         JOIN C_Country c ON loc.C_Country_ID = c.C_Country_ID and c.isActive = 'Y'
         JOIN C_BPartner org_bp on org_bpl.c_bpartner_id = org_bp.c_bpartner_id
WHERE oi.ad_org_id = org_id;

$BODY$
    LANGUAGE sql
    STABLE;