
-- Warehouse "Hauptlager" make the default locator "not-after-picking" and insert a dedicated after-picking-locator
-- 29.09.2016 09:36
-- URL zum Konzept
UPDATE M_Locator SET IsAfterPickingLocator='N',Updated=TO_TIMESTAMP('2016-09-29 09:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Locator_ID=540007
;

-- 29.09.2016 09:36
-- URL zum Konzept
INSERT INTO M_Locator (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsAfterPickingLocator,IsDefault,M_Locator_ID,M_Warehouse_ID,PriorityNo,Updated,UpdatedBy,Value,X,Y,Z) VALUES (1000000,1000000,TO_TIMESTAMP('2016-09-29 09:36:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N',540010,540008,50,TO_TIMESTAMP('2016-09-29 09:36:46','YYYY-MM-DD HH24:MI:SS'),100,'Verdichtung','0','0','1')
;

