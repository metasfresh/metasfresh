CREATE VIEW AD_User_Purchaser_BPartner_V AS
SELECT p.AD_Client_ID,
       p.AD_Org_ID,
       p.AD_User_ID,
       p.AD_User_Purchaser_ID,
       p.Created,
       p.CreatedBy,
       p.Updated,
       p.UpdatedBy,
       p.IsActive,
       p.C_BP_group_ID,
       bp.c_bpartner_id
FROM AD_User_Purchaser p
         JOIN c_bpartner bp ON p.C_BP_group_ID = bp.C_BP_Group_ID;