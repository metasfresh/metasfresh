DO $$
BEGIN
    IF NOT EXISTS(select * from M_Warehouse where M_Warehouse_ID=1000109) THEN

    INSERT INTO M_Warehouse (AD_Client_ID,AD_Org_ID,AD_User_ID,C_BPartner_Location_ID,C_Location_ID,Created,CreatedBy,IsActive,IsHUStorageDisabled,IsInTransit,IsIssueWarehouse,IsPickingWarehouse,IsPOWarehouse,IsQualityReturnWarehouse,IsQuarantineWarehouse,IsSOWarehouse,M_Warehouse_ID,Name,Separator,Updated,UpdatedBy,Value) VALUES (1000000,1000000,100,2202690,2189861,TO_TIMESTAMP('2019-08-12 16:49:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Y','N','N','Y',1000109,'Materialentnahmelager','*',TO_TIMESTAMP('2019-08-12 16:49:16','YYYY-MM-DD HH24:MI:SS'),100,'Materialentnahmelager')
    ;

    INSERT INTO M_Warehouse_Acct (M_Warehouse_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,W_Differences_Acct,W_InvActualAdjust_Acct,W_Inventory_Acct,W_Revaluation_Acct) SELECT 1000109, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.W_Differences_Acct,p.W_InvActualAdjust_Acct,p.W_Inventory_Acct,p.W_Revaluation_Acct FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000 AND NOT EXISTS (SELECT * FROM M_Warehouse_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.M_Warehouse_ID=1000109)
    ;

    INSERT INTO M_Locator (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsAfterPickingLocator,IsDefault,M_Locator_ID,M_Warehouse_ID,PriorityNo,Updated,UpdatedBy,Value,X,X1,Y,Z) VALUES (1000000,1000000,TO_TIMESTAMP('2019-08-12 16:49:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N',540032,1000109,50,TO_TIMESTAMP('2019-08-12 16:49:27','YYYY-MM-DD HH24:MI:SS'),100,'Materialentnahmelager','0','0','0','0')
    ;

    END IF;
END;
$$