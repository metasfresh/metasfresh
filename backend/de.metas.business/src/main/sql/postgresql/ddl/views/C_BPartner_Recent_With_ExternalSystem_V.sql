DROP VIEW IF EXISTS C_BPartner_Recent_V
;

/*
Note: no need for union distinct. we might still have the same C_BPartner_ID with different Updated, and we make sure later that each partner is loaded just once.
 */
CREATE OR REPLACE VIEW C_BPartner_Recent_With_ExternalSystem_V_ID
AS
SELECT bp.C_BPartner_ID AS C_BPartner_Recent_V_ID, bp.C_BPartner_ID, bp.Updated, bp.AD_Org_ID, extRef.ExternalSystem
FROM C_BPartner bp
         JOIN S_ExternalReference extRef ON extRef.referenced_ad_table_id = get_table_id('C_BPartner') AND extRef.record_id = bp.C_BPartner_ID
UNION
SELECT bpl.C_BPartner_ID, bpl.C_BPartner_ID, bpl.Updated, bpl.AD_Org_ID, extRef.ExternalSystem
FROM C_BPartner_Location bpl
         JOIN s_externalreference extRef ON extRef.referenced_ad_table_id = get_table_id('C_BPartner_Location') AND extRef.record_id = bpl.C_BPartner_Location_ID
UNION
SELECT u.C_BPartner_ID, u.C_BPartner_ID, u.Updated, u.AD_Org_ID, extRef.ExternalSystem
FROM AD_User u
         JOIN S_ExternalReference extRef ON extRef.referenced_ad_table_id = get_table_id('AD_User') AND extRef.record_id = u.AD_User_ID
WHERE u.C_BPartner_ID IS NOT NULL

