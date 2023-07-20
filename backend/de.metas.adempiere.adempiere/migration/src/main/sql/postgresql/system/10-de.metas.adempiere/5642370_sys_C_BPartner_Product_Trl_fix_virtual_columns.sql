-- Column: C_BPartner_Product_Trl.C_BPartner_ID
-- Column SQL (old): (SELECT cbp.C_BPartner_ID from C_Bpartner_Product cbp where cbp.C_Bpartner_Product_ID = C_Bpartner_Product_Trl.C_Bpartner_Product_Id)
-- 2022-06-06T14:00:06.379Z
UPDATE AD_Column SET ColumnSQL='(SELECT cbp.C_BPartner_ID from C_Bpartner_Product cbp where cbp.C_Bpartner_Product_ID = C_BPartner_Product_Trl.C_BPartner_Product_ID)',Updated=TO_TIMESTAMP('2022-06-06 17:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570681
;

-- Column: C_BPartner_Product_Trl.M_Product_ID
-- Column SQL (old): (SELECT cbp.M_Product_ID from C_Bpartner_Product cbp where cbp.C_BPartner_Product_ID = C_Bpartner_Product_Trl.C_Bpartner_Product_Id)
-- 2022-06-06T14:00:39.614Z
UPDATE AD_Column SET ColumnSQL='(SELECT cbp.M_Product_ID from C_Bpartner_Product cbp where cbp.C_BPartner_Product_ID = C_BPartner_Product_Trl.C_BPartner_Product_ID)', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 17:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570682
;

