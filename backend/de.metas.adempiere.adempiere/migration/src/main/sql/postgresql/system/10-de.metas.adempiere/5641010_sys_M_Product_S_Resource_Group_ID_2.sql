-- Field: produkt_old -> Produkt -> Resource Group
-- Column: M_Product.S_Resource_Group_ID
-- 2022-05-27T10:54:58.335Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583197,696468,0,180,TO_TIMESTAMP('2022-05-27 13:54:58','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Resource Group',TO_TIMESTAMP('2022-05-27 13:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-27T10:54:58.337Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696468 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-27T10:54:58.371Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580932) 
;

-- 2022-05-27T10:54:58.386Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696468
;

-- 2022-05-27T10:54:58.390Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696468)
;

-- UI Element: produkt_old -> Produkt.Resource Group
-- Column: M_Product.S_Resource_Group_ID
-- 2022-05-27T10:56:52.232Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696468,0,180,607611,1000039,'F',TO_TIMESTAMP('2022-05-27 13:56:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Resource Group',20,0,0,TO_TIMESTAMP('2022-05-27 13:56:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: produkt_old -> Produkt -> Ressource
-- Column: M_Product.S_Resource_ID
-- 2022-05-27T10:57:33.879Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-27 13:57:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5383
;

-- Field: produkt_old -> Produkt -> Resource Group
-- Column: M_Product.S_Resource_Group_ID
-- 2022-05-27T10:57:45.814Z
UPDATE AD_Field SET DisplayLogic='@ProductType@=''R''', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-27 13:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696468
;

