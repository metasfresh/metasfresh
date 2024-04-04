-- Run mode: SWING_CLIENT

-- Name: M_Product (CoProduct for given M_Raw_Product_ID)
-- 2024-04-04T07:47:26.082Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541864,TO_TIMESTAMP('2024-04-04 10:47:25.951','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','M_Product (CoProduct for given M_Raw_Product_ID)',TO_TIMESTAMP('2024-04-04 10:47:25.951','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-04-04T07:47:26.088Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541864 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Product (CoProduct for given M_Raw_Product_ID)
-- Table: M_Product
-- Key: M_Product.M_Product_ID
-- 2024-04-04T07:48:46.916Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1402,0,541864,208,TO_TIMESTAMP('2024-04-04 10:48:46.885','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2024-04-04 10:48:46.885','YYYY-MM-DD HH24:MI:SS.US'),100,' EXISTS (SELECT 1               from pp_product_bomline coProductBomLine                        INNER JOIN pp_product_bom bom on coProductBomLine.pp_product_bom_id = bom.pp_product_bom_id                        INNER JOIN pp_product_bomline rawProductBomLine on rawProductBomLine.pp_product_bom_id = bom.pp_product_bom_id                   AND rawProductBomLine.componenttype = ''CO'' --Component               where coProductBomLine.m_product_id = m_product.m_product_id                 AND bom.isactive = ''Y''                 AND bom.bomuse = ''M''-- Manufacturing                 AND bom.bomtype = ''A''                 AND bom.docstatus = ''CO''                 AND bom.processed = ''Y''                 AND bom.c_doctype_id = 541027 -- Bill of Material Version                 AND coProductBomLine.componenttype = ''CP'' --Co-product                 AND (bom.validto IS NULL OR bom.validto > NOW())                 AND rawProductBomLine.m_product_id = 2005575 @M_Raw_Product_ID/-1@ )')
;

-- Reference: M_Product (CoProduct for given M_Raw_Product_ID)
-- Table: M_Product
-- Key: M_Product.M_Product_ID
-- 2024-04-04T07:49:47.139Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1               from pp_product_bomline coProductBomLine                        INNER JOIN pp_product_bom bom on coProductBomLine.pp_product_bom_id = bom.pp_product_bom_id                        INNER JOIN pp_product_bomline rawProductBomLine on rawProductBomLine.pp_product_bom_id = bom.pp_product_bom_id                   AND rawProductBomLine.componenttype = ''CO'' /*Component*/               where coProductBomLine.m_product_id = m_product.m_product_id                 AND bom.isactive = ''Y''                 AND bom.bomuse = ''M'' /*Manufacturing*/                 AND bom.bomtype = ''A''                 AND bom.docstatus = ''CO''                 AND bom.processed = ''Y''                 AND bom.c_doctype_id = 541027 /*Bill of Material Version*/                 AND coProductBomLine.componenttype = ''CP'' /*Co-product*/                 AND (bom.validto IS NULL OR bom.validto > NOW())                 AND rawProductBomLine.m_product_id = @M_Raw_Product_ID/-1@ )',Updated=TO_TIMESTAMP('2024-04-04 10:49:47.139','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541864
;

-- Name: M_Product (Processed Product for given M_Raw_Product_ID)
-- 2024-04-04T07:50:06.011Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541865,TO_TIMESTAMP('2024-04-04 10:50:05.874','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','M_Product (Processed Product for given M_Raw_Product_ID)',TO_TIMESTAMP('2024-04-04 10:50:05.874','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-04-04T07:50:06.011Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541865 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Product (Processed Product for given M_Raw_Product_ID)
-- Table: M_Product
-- Key: M_Product.M_Product_ID
-- 2024-04-04T07:51:00.025Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1402,0,541865,208,TO_TIMESTAMP('2024-04-04 10:51:00.022','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2024-04-04 10:51:00.022','YYYY-MM-DD HH24:MI:SS.US'),100,'EXISTS (SELECT 1               from pp_product_bom bom                        INNER JOIN pp_product_bomline rawProductBomLine on rawProductBomLine.pp_product_bom_id = bom.pp_product_bom_id                   AND rawProductBomLine.componenttype = ''CO'' /*Component*/               where bom.m_product_id = m_product.m_product_id                 AND bom.isactive = ''Y''                 AND bom.bomuse = ''M'' /*Manufacturing*/                 AND bom.bomtype = ''A''                 AND bom.docstatus = ''CO''                 AND bom.processed = ''Y''                 AND bom.c_doctype_id = 541027 /*Bill of Material Version*/                 AND (bom.validto IS NULL OR bom.validto > NOW())                 AND rawProductBomLine.m_product_id = @M_Raw_Product_ID/-1@ )')
;

-- Reference: M_Product (Processed Product for given M_Raw_Product_ID)
-- Table: M_Product
-- Key: M_Product.M_Product_ID
-- 2024-04-04T07:51:16.012Z
UPDATE AD_Ref_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2024-04-04 10:51:16.012','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541865
;

-- Reference: M_Product (CoProduct for given M_Raw_Product_ID)
-- Table: M_Product
-- Key: M_Product.M_Product_ID
-- 2024-04-04T07:51:47.902Z
UPDATE AD_Ref_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2024-04-04 10:51:47.902','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541864
;

-- Column: ModCntr_Settings.M_Co_Product_ID
-- 2024-04-04T07:52:04.094Z
UPDATE AD_Column SET AD_Reference_Value_ID=541864,Updated=TO_TIMESTAMP('2024-04-04 10:52:04.094','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588087
;

-- Column: ModCntr_Settings.M_Processed_Product_ID
-- 2024-04-04T07:52:17.359Z
UPDATE AD_Column SET AD_Reference_Value_ID=541865,Updated=TO_TIMESTAMP('2024-04-04 10:52:17.359','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588086
;
