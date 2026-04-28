-- 2025-04-14T07:39:27.873Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583582,0,TO_TIMESTAMP('2025-04-14 07:39:27.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gewicht eines Produktes  (kg)','D','The Weight indicates the weight  of the product in the Weight UOM of the Client','Y','Gewicht netto (kg)','Gewicht netto (kg)',TO_TIMESTAMP('2025-04-14 07:39:27.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-14T07:39:27.936Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583582 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-04-14T07:42:42.061Z
UPDATE AD_Element_Trl SET Description='Weight of a product (kg)', Help='The Weight indicates the weight of the product in (kg)', IsTranslated='Y', Name='Net Weight (kg)', PrintName='Net Weight (kg)',Updated=TO_TIMESTAMP('2025-04-14 07:42:42.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583582 AND AD_Language='en_US'
;

-- 2025-04-14T07:42:42.184Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583582,'en_US') 
;

-- Field: Produkt -> Produkt -> Gewicht netto (kg)
-- Column: M_Product.Weight
-- Field: Produkt(140,D) -> Produkt(180,D) -> Gewicht netto (kg)
-- Column: M_Product.Weight
-- 2025-04-14T07:47:18.194Z
UPDATE AD_Field SET AD_Name_ID=583582, Description='Gewicht eines Produktes  (kg)', Help='The Weight indicates the weight  of the product in the Weight UOM of the Client', Name='Gewicht netto (kg)',Updated=TO_TIMESTAMP('2025-04-14 07:47:18.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=1032
;

-- 2025-04-14T07:47:18.259Z
UPDATE AD_Field_Trl trl SET Description='Gewicht eines Produktes  (kg)',Name='Gewicht netto (kg)' WHERE AD_Field_ID=1032 AND AD_Language='de_DE'
;

-- 2025-04-14T07:47:24.978Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583582) 
;

-- 2025-04-14T07:47:25.045Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=1032
;

-- 2025-04-14T07:47:25.104Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(1032)
;

-- Field: Produkt -> Produkt -> Brutto-Verkaufsmengeneinheit
-- Column: M_Product.GrossWeight_UOM_ID
-- Field: Produkt(140,D) -> Produkt(180,D) -> Brutto-Verkaufsmengeneinheit
-- Column: M_Product.GrossWeight_UOM_ID
-- 2025-04-14T07:51:00.851Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578971,741966,0,180,0,TO_TIMESTAMP('2025-04-14 07:50:59.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Brutto-Verkaufsmengeneinheit ',0,0,610,0,1,1,TO_TIMESTAMP('2025-04-14 07:50:59.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-14T07:51:00.902Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-14T07:51:00.954Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583224) 
;

-- 2025-04-14T07:51:01.017Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741966
;

-- 2025-04-14T07:51:01.070Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741966)
;

-- UI Element: Produkt -> Produkt.Brutto-Verkaufsmengeneinheit
-- Column: M_Product.GrossWeight_UOM_ID
-- UI Element: Produkt(140,D) -> Produkt(180,D) -> main -> 10 -> No.Brutto-Verkaufsmengeneinheit
-- Column: M_Product.GrossWeight_UOM_ID
-- 2025-04-14T07:52:43.936Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741966,0,180,1000015,631343,'F',TO_TIMESTAMP('2025-04-14 07:52:43.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Brutto-Verkaufsmengeneinheit ',37,0,0,TO_TIMESTAMP('2025-04-14 07:52:43.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: GrossWeight_UOM_ID
-- 2025-04-14T07:55:25.288Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gross Weight UOM', PrintName='Gross Weight UOM',Updated=TO_TIMESTAMP('2025-04-14 07:55:25.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583224 AND AD_Language='en_US'
;

-- 2025-04-14T07:55:25.386Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583224,'en_US') 
;

-- Column: M_Product.GrossWeight_UOM_ID
-- Column: M_Product.GrossWeight_UOM_ID
-- 2025-04-14T08:01:44.305Z
UPDATE AD_Column SET MandatoryLogic='@GrossWeight@>0',Updated=TO_TIMESTAMP('2025-04-14 08:01:44.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=578971
;

