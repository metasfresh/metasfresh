ALTER TABLE M_Product_Nutrition DROP CONSTRAINT parentelement_mproductnutrition;

ALTER TABLE M_Product_Ingredients DROP CONSTRAINT parentelement_mproductingredients;

-- 2021-12-10T14:48:19.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541528,TO_TIMESTAMP('2021-12-10 15:48:19','YYYY-MM-DD HH24:MI:SS'),100,'Nutrition Parent','D','Y','N','M_Nutrition_Fact_Parent_ID',TO_TIMESTAMP('2021-12-10 15:48:19','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-12-10T14:48:19.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541528 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-12-10T14:49:05.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,560470,560469,0,541528,540996,TO_TIMESTAMP('2021-12-10 15:49:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-12-10 15:49:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-10T14:49:34.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541528,Updated=TO_TIMESTAMP('2021-12-10 15:49:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578835
;

-- 2021-12-10T14:51:30.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_nutrition','ParentElement_ID','NUMERIC(10)',null,null)
;

-- 2021-12-10T15:00:44.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_nutrition','ParentElement_ID','NUMERIC(10)',null,null)
;

-- 2021-12-10T15:06:44.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541529,TO_TIMESTAMP('2021-12-10 16:06:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Ingredients_Parent_ID',TO_TIMESTAMP('2021-12-10 16:06:44','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-12-10T15:06:44.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541529 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-12-10T15:07:11.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,569019,569020,0,541529,541413,TO_TIMESTAMP('2021-12-10 16:07:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-12-10 16:07:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-10T15:08:10.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541529,Updated=TO_TIMESTAMP('2021-12-10 16:08:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578844
;

-- 2021-12-10T15:08:13.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_ingredients','ParentElement_ID','NUMERIC(10)',null,null)
;

-- 2021-12-10T15:19:34.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541030,547723,TO_TIMESTAMP('2021-12-10 16:19:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2021-12-10 16:19:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-10T15:20:08.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=547723, SeqNo=10,Updated=TO_TIMESTAMP('2021-12-10 16:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552449
;

-- 2021-12-10T15:20:27.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=547723, SeqNo=20,Updated=TO_TIMESTAMP('2021-12-10 16:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552448
;

