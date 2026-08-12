INSERT INTO ad_user_purchaser (ad_client_id, ad_org_id, ad_user_id, ad_user_purchaser_id, created, createdby, isactive, updated, updatedby, c_bp_group_id)

SELECT AD_Client_ID,
       AD_Org_ID,
       Purchaser_User_ID,
       NEXTVAL('ad_user_purchaser_seq'),
       '2025-12-02 14:39:02.246000 +01:00',
       99,
       'Y',
       '2025-12-02 14:39:02.246000 +01:00',
       99,
       C_BP_Group_ID

FROM c_bp_group
WHERE Purchaser_User_ID IS NOT NULL
;
