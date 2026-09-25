-- Commission settings lines: allow several lines without customer group / product category, as long as they are for different customers.
-- The unique index C_CommissionSettingsLine_UC was created before C_BPartner_Customer_ID existed on the line, so it allowed only ONE line
-- without customer group and product category per settings - i.e. customer-specific percentages were impossible.

-- the index column for Customer_Group_ID still referenced the column's old name C_BP_Group_ID
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(Customer_Group_ID, 0)', Updated=TO_TIMESTAMP('2026-09-25 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Index_Column_ID=540969
;

-- add C_BPartner_Customer_ID to the index
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570071,541546 /*From ID Server*/,540507,0,'COALESCE(C_BPartner_Customer_ID, 0)',TO_TIMESTAMP('2026-09-25 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',40,TO_TIMESTAMP('2026-09-25 10:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

DROP INDEX IF EXISTS C_CommissionSettingsLine_UC
;

CREATE UNIQUE INDEX C_CommissionSettingsLine_UC ON C_CommissionSettingsLine (C_HierarchyCommissionSettings_ID,COALESCE(Customer_Group_ID, 0),COALESCE(M_Product_Category_ID, 0),COALESCE(C_BPartner_Customer_ID, 0)) WHERE IsActive='Y'
;
