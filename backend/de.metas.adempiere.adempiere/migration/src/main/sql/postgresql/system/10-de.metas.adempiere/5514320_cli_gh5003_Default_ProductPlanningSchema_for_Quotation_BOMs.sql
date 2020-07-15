-- 2019-03-01T10:40:43.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product_PlanningSchema (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DeliveryTime_Promised,IsActive,IsAttributeDependant,IsCreatePlan,IsDocComplete,IsManufactured,IsMPS,IsPickDirectlyIfFeasible,IsPurchased,IsTraded,M_AttributeSetInstance_ID,M_Product_PlanningSchema_ID,M_ProductPlanningSchema_Selector,OnMaterialReceiptWithDestWarehouse,TransfertTime,Updated,UpdatedBy,WorkingTime,Yield) VALUES (1000000,1000000,TO_TIMESTAMP('2019-03-01 10:40:43','YYYY-MM-DD HH24:MI:SS'),2188224,0,'Y','N','Y','N','N','N','N','N','N',0,540000,'Q','M',0,TO_TIMESTAMP('2019-03-01 10:40:43','YYYY-MM-DD HH24:MI:SS'),2188224,0,100)
;

-- 2019-03-01T10:40:49.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product_PlanningSchema SET IsManufactured='Y',Updated=TO_TIMESTAMP('2019-03-01 10:40:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188224 WHERE M_Product_PlanningSchema_ID=540000
;

-- 2019-03-01T10:40:59.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product_PlanningSchema SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2019-03-01 10:40:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188224 WHERE M_Product_PlanningSchema_ID=540000
;

-- 2019-03-01T10:41:09.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product_PlanningSchema SET IsDocComplete='Y',Updated=TO_TIMESTAMP('2019-03-01 10:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188224 WHERE M_Product_PlanningSchema_ID=540000
;

