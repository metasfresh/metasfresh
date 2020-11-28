-- 2017-04-18T17:33:59.957
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y'' AND exists (select 1 from M_Inventory i where M_Inventory_ID = i.M_Inventory_ID and i.C_DocType_ID != 540948 )',Updated=TO_TIMESTAMP('2017-04-18 17:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=50015
;

-- 2017-04-18T17:34:30.117
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='',Updated=TO_TIMESTAMP('2017-04-18 17:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=50015
;

-- 2017-04-18T17:34:53.303
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556484,540797,50015,0,TO_TIMESTAMP('2017-04-18 17:34:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y',50,TO_TIMESTAMP('2017-04-18 17:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-18T17:34:55.534
-- URL zum Konzept
DROP INDEX IF EXISTS m_inventoryline_productlocattr
;

-- 2017-04-18T17:34:55.536
-- URL zum Konzept
CREATE UNIQUE INDEX m_inventoryline_productlocattr ON M_InventoryLine (M_Inventory_ID,M_Locator_ID,M_Product_ID,M_AttributeSetInstance_ID,M_InOutLine_ID)
;