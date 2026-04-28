-- Field: Produkt -> Product Tax Category -> Land
-- Column: M_Product_TaxCategory.C_Country_ID
-- 2022-05-11T09:38:14.891Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-11 12:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691799
;

-- Field: Produkt -> Product Tax Category -> Produkt
-- Column: M_Product_TaxCategory.M_Product_ID
-- 2022-05-11T09:38:28.498Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-11 12:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691797
;

-- 2022-05-11T09:43:21.921Z
UPDATE C_PricingRule SET SeqNo=2200,Updated=TO_TIMESTAMP('2022-05-11 12:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_PricingRule_ID=540018
;

-- 2022-05-11T09:56:08.554Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540695,0,542131,TO_TIMESTAMP('2022-05-11 12:56:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','ProductTaxCategory_Unique_Country_Product','N',TO_TIMESTAMP('2022-05-11 12:56:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-11T09:56:08.562Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540695 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-05-11T09:56:29.227Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,582923,541241,540695,0,TO_TIMESTAMP('2022-05-11 12:56:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-05-11 12:56:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-11T09:56:39.322Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,582925,541242,540695,0,TO_TIMESTAMP('2022-05-11 12:56:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2022-05-11 12:56:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-11T09:56:59.069Z
CREATE UNIQUE INDEX ProductTaxCategory_Unique_Country_Product ON M_Product_TaxCategory (M_Product_ID,C_Country_ID)
;