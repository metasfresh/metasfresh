-- Column: M_Product_Trl.TechnicalDescription
-- Column: M_Product_Trl.TechnicalDescription
-- 2022-12-20T13:34:11.877Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585419,576062,0,10,312,'TechnicalDescription',TO_TIMESTAMP('2022-12-20 14:34:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Technische Bezeichnung des Produktes','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sachbezeichnung',0,0,TO_TIMESTAMP('2022-12-20 14:34:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-20T13:34:11.942Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585419 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-20T13:34:11.964Z
/* DDL */  select update_Column_Translation_From_AD_Element(576062) 
;

-- 2022-12-20T13:34:29.565Z
/* DDL */ SELECT public.db_alter_table('M_Product_Trl','ALTER TABLE public.M_Product_Trl ADD COLUMN TechnicalDescription VARCHAR(2000)')
;

-- Column: M_Product.TechnicalDescription
-- Column: M_Product.TechnicalDescription
-- 2022-12-20T13:34:44.930Z
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-20 14:34:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564005
;

-- Field: Produkt Übersetzung -> Produkt Übersetzung -> Sachbezeichnung
-- Column: M_Product_Trl.TechnicalDescription
-- Field: Produkt Übersetzung(540420,D) -> Produkt Übersetzung(541054,D) -> Sachbezeichnung
-- Column: M_Product_Trl.TechnicalDescription
-- 2022-12-20T18:10:16.980Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585419,710052,0,541054,0,TO_TIMESTAMP('2022-12-20 19:10:16','YYYY-MM-DD HH24:MI:SS'),100,'Technische Bezeichnung des Produktes',0,'D',0,'Y','Y','Y','N','N','N','N','N','Sachbezeichnung',0,140,0,1,1,TO_TIMESTAMP('2022-12-20 19:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-20T18:10:16.989Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-20T18:10:16.991Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576062) 
;

-- 2022-12-20T18:10:17.002Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710052
;

-- 2022-12-20T18:10:17.004Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710052)
;

-- UI Element: Produkt Übersetzung -> Produkt Übersetzung.Sachbezeichnung
-- Column: M_Product_Trl.TechnicalDescription
-- UI Element: Produkt Übersetzung(540420,D) -> Produkt Übersetzung(541054,D) -> main -> 10 -> default.Sachbezeichnung
-- Column: M_Product_Trl.TechnicalDescription
-- 2022-12-20T18:11:35.597Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710052,0,541054,541509,614570,'F',TO_TIMESTAMP('2022-12-20 19:11:35','YYYY-MM-DD HH24:MI:SS'),100,'Technische Bezeichnung des Produktes','Y','N','N','Y','N','N','N',0,'Sachbezeichnung',75,0,0,TO_TIMESTAMP('2022-12-20 19:11:35','YYYY-MM-DD HH24:MI:SS'),100)
;

