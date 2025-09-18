DROP VIEW IF EXISTS C_BPartner_Recent_V
;

/*
Note: no need for union distinct. we might still have the same C_BPartner_ID with different Updated, and we make sure later that each partner is loaded just once.
 */
CREATE OR REPLACE VIEW C_BPartner_Recent_V
AS
SELECT C_BPartner_ID AS C_BPartner_Recent_V_ID, C_BPartner_ID, Updated, AD_Org_ID
FROM C_BPartner
UNION
SELECT C_BPartner_ID, C_BPartner_ID, Updated, AD_Org_ID
FROM C_BPartner_Location
UNION
SELECT C_BPartner_ID, C_BPartner_ID, Updated, AD_Org_ID
FROM AD_User
WHERE C_BPartner_ID IS NOT NULL
;
