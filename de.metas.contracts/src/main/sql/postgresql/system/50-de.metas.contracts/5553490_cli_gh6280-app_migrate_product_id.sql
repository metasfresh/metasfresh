
-- migrate existing c_commission_shares and settings to the one commission product that we have right now
UPDATE c_commission_share SET C_Commission_Product_ID=540420, Updated=TO_TIMESTAMP('2020-02-27 16:09:53','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99 WHERE C_Commission_Product_ID IS NULL;
UPDATE C_HierarchyCommissionSettings SET C_Commission_Product_ID=540420, Updated=TO_TIMESTAMP('2020-02-27 16:09:53','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99 WHERE C_Commission_Product_ID IS NULL;

UPDATE C_Flatrate_Term SET M_Product_ID=540420, Updated='2020-02-27 20:09:38.73509+00', UpdatedBy=99 WHERE Type_Conditions='Commission' AND COALESCE(M_Product_ID,0)=0;
