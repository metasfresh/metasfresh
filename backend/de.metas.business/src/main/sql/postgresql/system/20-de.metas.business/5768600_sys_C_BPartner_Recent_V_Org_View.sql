DROP VIEW IF EXISTS C_BPartner_Recent_V;


CREATE OR REPLACE VIEW C_BPartner_Recent_V
AS
SELECT C_BPartner_ID AS C_BPartner_Recent_V_ID, C_BPartner_ID, Updated, ad_org_id
FROM C_BPartner
UNION DISTINCT
SELECT C_BPartner_ID, C_BPartner_ID, Updated, ad_org_id
FROM C_BPartner_Location
UNION DISTINCT
SELECT C_BPartner_ID, C_BPartner_ID, Updated,  ad_org_id
FROM AD_User
WHERE C_BPartner_ID IS NOT NULL
;
