DROP FUNCTION IF EXISTS New_Project_Onboarding
(
    /* p_project_value */ varchar
, /* p_project_name */    varchar
, /* p_partner_value */   varchar
, /* p_partner_name */    varchar
)
;

CREATE OR REPLACE FUNCTION New_Project_Onboarding(p_project_value varchar,
                                                  p_project_name  varchar,
                                                  p_partner_value varchar,
                                                  p_partner_name  varchar)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    _partner_id           numeric := NEXTVAL(''c_bpartner_seq '');
    _project_id           numeric := NEXTVAL(''c_project_seq '');
    _location_id          numeric := NEXTVAL(''c_location_seq '');
    _bpartner_location_id numeric := NEXTVAL(''c_bpartner_location_seq '');
    _AD_Client_ID         numeric := 1000000;
    _AD_Org_ID            numeric := 1000000;
BEGIN

    INSERT INTO C_BPartner(c_bpartner_id, c_bp_group_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, value, name, url)
    VALUES (_partner_id, 1000000, _AD_Client_ID, _AD_Org_ID, NOW(), 99, NOW(), 99, p_partner_value, p_partner_name, CONCAT('' git @ github.com:metasfresh / '', p_project_value, ''.git ''));

    INSERT INTO c_location(c_location_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, c_country_id)
    VALUES (_location_id, _AD_Client_ID, _AD_Org_ID, NOW(), 99, NOW(), 99, 101);

    INSERT INTO c_bpartner_location(c_bpartner_location_id, c_bpartner_id, c_location_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby)
    VALUES (_bpartner_location_id, _partner_id, _location_id, _AD_Client_ID, _AD_Org_ID, NOW(), 99, NOW(), 99);

    INSERT INTO c_project(c_project_id, c_bpartner_id, c_bpartner_location_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, value, name, c_currency_id, r_statuscategory_id)
    VALUES (_project_id, _partner_id, _bpartner_location_id, _AD_Client_ID, _AD_Org_ID, NOW(), 99, NOW(), 99, p_project_value, p_project_name, 102, 540009);

    INSERT INTO s_externalprojectreference(s_externalprojectreference_id, isactive, ad_client_id, ad_org_id, created, createdby, updated, updatedby, c_project_id, projecttype, externalsystem, externalreference, externalprojectowner)
    VALUES (NEXTVAL(''s_externalprojectreference_seq ''), ''Y'', _AD_Client_ID, _AD_Org_ID, NOW(), 99, NOW(), 99, _project_id, ''Budget'', ''Github'', p_project_value, ''metasfresh'');

END;
$$
;

COMMENT ON FUNCTION New_Project_Onboarding(varchar, varchar, varchar, varchar) IS ''Automated Customer Onboarding: CREATE the NEW customer project AND the necessary entries''
;
