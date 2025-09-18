DROP VIEW IF EXISTS C_BPartner_Recent_V;


CREATE OR REPLACE VIEW C_BPartner_Recent_V
AS
SELECT bp.C_BPartner_ID AS C_BPartner_Recent_V_ID, bp.C_BPartner_ID, bp.Updated, bp.ad_org_id, extRef.externalsystem
FROM C_BPartner bp
         LEFT JOIN S_ExternalReference extRef ON extRef.referenced_ad_table_id = get_table_id('C_BPartner') AND extRef.record_id = bp.C_BPartner_ID
UNION
DISTINCT
SELECT bpl.C_BPartner_ID, bpl.C_BPartner_ID, bpl.Updated, bpl.ad_org_id, extRef.externalsystem
FROM C_BPartner_Location bpl
         LEFT JOIN s_externalreference extRef ON extRef.referenced_ad_table_id = get_table_id('C_BPartner_Location') AND extRef.record_id = bpl.C_BPartner_Location_ID
UNION
DISTINCT
SELECT u.C_BPartner_ID, u.C_BPartner_ID, u.Updated, u.ad_org_id, extRef.externalsystem
FROM AD_User u
         LEFT JOIN S_ExternalReference extRef ON extRef.referenced_ad_table_id = get_table_id('AD_User') AND extRef.record_id = u.AD_User_ID

WHERE u.C_BPartner_ID IS NOT NULL
;
