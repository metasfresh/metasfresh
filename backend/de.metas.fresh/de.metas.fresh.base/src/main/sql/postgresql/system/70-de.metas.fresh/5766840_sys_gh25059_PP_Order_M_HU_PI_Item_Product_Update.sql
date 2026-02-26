-- Run mode: SWING_CLIENT

-- Name: M_HU_PI_Item_Product_PP_Order
-- 2025-09-04T17:33:30.418Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541975,TO_TIMESTAMP('2025-09-04 17:33:29.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_HU_PI_Item_Product_PP_Order',TO_TIMESTAMP('2025-09-04 17:33:29.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-09-04T17:33:30.554Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541975 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_HU_PI_Item_Product_PP_Order
-- Table: M_HU_PI_Item_Product
-- Key: M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID
-- 2025-09-04T17:36:40.036Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,549187,0,541975,540508,TO_TIMESTAMP('2025-09-04 17:36:39.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','CASE WHEN M_HU_PI_Item_Product.C_BPartner_ID IS NULL THEN 1 ELSE 0 END, CASE WHEN M_HU_PI_Item_Product.C_BPartner_ID IS NOT NULL THEN M_HU_PI_Item_Product.C_BPartner_ID END, M_HU_PI_Item_Product.IsDefaultForProduct DESC','N',TO_TIMESTAMP('2025-09-04 17:36:39.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)')
;

-- Reference: M_HU_PI_Item_Product_PP_Order
-- Table: M_HU_PI_Item_Product
-- Key: M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID
-- 2025-09-04T17:54:08.843Z
UPDATE AD_Ref_Table SET WhereClause='M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)',Updated=TO_TIMESTAMP('2025-09-04 17:54:08.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541975
;

-- Column: PP_Order.M_HU_PI_Item_Product_ID
-- 2025-09-04T17:39:26.221Z
UPDATE AD_Column SET AD_Reference_Value_ID=541975,Updated=TO_TIMESTAMP('2025-09-04 17:39:26.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583383
;

