-- Column: C_OLCand.InvoiceRule
-- Column: C_OLCand.InvoiceRule
-- 2024-09-26T06:32:35.769Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589169,559,0,17,150,540244,'XX','InvoiceRule',TO_TIMESTAMP('2024-09-26 09:32:35','YYYY-MM-DD HH24:MI:SS'),100,'N','"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.','de.metas.ordercandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsstellung',0,0,TO_TIMESTAMP('2024-09-26 09:32:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-26T06:32:35.771Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589169 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-26T06:32:35.777Z
/* DDL */  select update_Column_Translation_From_AD_Element(559) 
;

-- 2024-09-26T06:32:36.417Z
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN InvoiceRule CHAR(1)')
;

-- Column: C_OLCand.IsManualQtyItemCapacity
-- Column: C_OLCand.IsManualQtyItemCapacity
-- 2024-09-26T07:00:47.669Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2024-09-26 10:00:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549992
;

-- Field: Auftragsdisposition_OLD -> Kandidat -> Rechnungsstellung
-- Column: C_OLCand.InvoiceRule
-- Field: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Rechnungsstellung
-- Column: C_OLCand.InvoiceRule
-- 2024-09-26T12:25:05.575Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589169,731785,0,540282,TO_TIMESTAMP('2024-09-26 15:25:04','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.',1,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Rechnungsstellung',TO_TIMESTAMP('2024-09-26 15:25:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-26T12:25:05.580Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731785 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-26T12:25:05.586Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(559) 
;

-- 2024-09-26T12:25:05.630Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731785
;

-- 2024-09-26T12:25:05.637Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731785)
;

-- UI Element: Auftragsdisposition_OLD -> Kandidat.Rechnungsstellung
-- Column: C_OLCand.InvoiceRule
-- UI Element: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Rechnungsstellung
-- Column: C_OLCand.InvoiceRule
-- 2024-09-26T12:25:39.774Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731785,0,540282,540962,626095,'F',TO_TIMESTAMP('2024-09-26 15:25:38','YYYY-MM-DD HH24:MI:SS'),100,'"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.','Y','N','Y','N','N','Rechnungsstellung',700,0,0,TO_TIMESTAMP('2024-09-26 15:25:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftragsdisposition_OLD -> Kandidat.Rechnungsstellung
-- Column: C_OLCand.InvoiceRule
-- UI Element: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Rechnungsstellung
-- Column: C_OLCand.InvoiceRule
-- 2024-09-26T12:31:49.828Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-09-26 15:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626095
;

