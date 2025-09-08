-- 2024-09-27T08:44:32.216Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583290,0,'QtyShipped_CatchWeight',TO_TIMESTAMP('2024-09-27 11:44:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Shipped Weight','Shipped Weight',TO_TIMESTAMP('2024-09-27 11:44:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-27T08:44:32.223Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583290 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-09-27T08:44:47.403Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583291,0,'QtyShipped_CatchWeight_UOM_ID',TO_TIMESTAMP('2024-09-27 11:44:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Shipped Weight UOM','Shipped Weight UOM',TO_TIMESTAMP('2024-09-27 11:44:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-27T08:44:47.405Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583291 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_OLCand.QtyShipped_CatchWeight
-- Column: C_OLCand.QtyShipped_CatchWeight
-- 2024-09-27T08:45:02.422Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589171,583290,0,29,540244,'XX','QtyShipped_CatchWeight',TO_TIMESTAMP('2024-09-27 11:45:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Shipped Weight',0,0,TO_TIMESTAMP('2024-09-27 11:45:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-27T08:45:02.425Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589171 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-27T08:45:02.428Z
/* DDL */  select update_Column_Translation_From_AD_Element(583290) 
;

-- 2024-09-27T08:45:03.013Z
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN QtyShipped_CatchWeight NUMERIC')
;

-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- 2024-09-27T08:45:16.742Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589172,583291,0,30,114,540244,'XX','QtyShipped_CatchWeight_UOM_ID',TO_TIMESTAMP('2024-09-27 11:45:16','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Shipped Weight UOM',0,0,TO_TIMESTAMP('2024-09-27 11:45:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-27T08:45:16.744Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589172 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-27T08:45:16.747Z
/* DDL */  select update_Column_Translation_From_AD_Element(583291) 
;

-- 2024-09-27T08:45:18.003Z
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN QtyShipped_CatchWeight_UOM_ID NUMERIC(10)')
;

-- 2024-09-27T08:45:18.072Z
ALTER TABLE C_OLCand ADD CONSTRAINT QtyShippedCatchWeightUOM_COLCand FOREIGN KEY (QtyShipped_CatchWeight_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Field: Auftragsdisposition_OLD -> Kandidat -> Shipped Weight
-- Column: C_OLCand.QtyShipped_CatchWeight
-- Field: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Shipped Weight
-- Column: C_OLCand.QtyShipped_CatchWeight
-- 2024-09-27T09:33:21.471Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589171,731787,0,540282,TO_TIMESTAMP('2024-09-27 12:33:21','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Shipped Weight',TO_TIMESTAMP('2024-09-27 12:33:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-27T09:33:21.476Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-27T09:33:21.480Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583290) 
;

-- 2024-09-27T09:33:21.487Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731787
;

-- 2024-09-27T09:33:21.492Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731787)
;

-- Field: Auftragsdisposition_OLD -> Kandidat -> Shipped Weight UOM
-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- Field: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Shipped Weight UOM
-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- 2024-09-27T09:33:29.931Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589172,731788,0,540282,TO_TIMESTAMP('2024-09-27 12:33:29','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Shipped Weight UOM',TO_TIMESTAMP('2024-09-27 12:33:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-27T09:33:29.933Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-27T09:33:29.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583291) 
;

-- 2024-09-27T09:33:29.941Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731788
;

-- 2024-09-27T09:33:29.943Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731788)
;

-- UI Column: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10
-- UI Element Group: qty shipped
-- 2024-09-27T09:37:35.399Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540547,552019,TO_TIMESTAMP('2024-09-27 12:37:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','qty shipped',20,TO_TIMESTAMP('2024-09-27 12:37:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftragsdisposition_OLD -> Kandidat.Qty Shipped
-- Column: C_OLCand.QtyShipped
-- UI Element: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> qty shipped.Qty Shipped
-- Column: C_OLCand.QtyShipped
-- 2024-09-27T09:37:54.444Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691401,0,540282,552019,626100,'F',TO_TIMESTAMP('2024-09-27 12:37:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Qty Shipped',10,0,0,TO_TIMESTAMP('2024-09-27 12:37:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftragsdisposition_OLD -> Kandidat.Shipped Weight
-- Column: C_OLCand.QtyShipped_CatchWeight
-- UI Element: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> qty shipped.Shipped Weight
-- Column: C_OLCand.QtyShipped_CatchWeight
-- 2024-09-27T09:38:19.166Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731787,0,540282,552019,626101,'F',TO_TIMESTAMP('2024-09-27 12:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Shipped Weight',20,0,0,TO_TIMESTAMP('2024-09-27 12:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftragsdisposition_OLD -> Kandidat.Shipped Weight UOM
-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- UI Element: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> qty shipped.Shipped Weight UOM
-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- 2024-09-27T09:38:33.210Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731788,0,540282,552019,626102,'F',TO_TIMESTAMP('2024-09-27 12:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Shipped Weight UOM',30,0,0,TO_TIMESTAMP('2024-09-27 12:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftragsdisposition_OLD -> Kandidat.Qty Shipped
-- Column: C_OLCand.QtyShipped
-- UI Element: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> qty shipped.Qty Shipped
-- Column: C_OLCand.QtyShipped
-- 2024-09-27T09:38:45.937Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-09-27 12:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626100
;

-- UI Element: Auftragsdisposition_OLD -> Kandidat.Shipped Weight
-- Column: C_OLCand.QtyShipped_CatchWeight
-- UI Element: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> qty shipped.Shipped Weight
-- Column: C_OLCand.QtyShipped_CatchWeight
-- 2024-09-27T09:38:46.902Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-09-27 12:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626101
;

-- UI Element: Auftragsdisposition_OLD -> Kandidat.Shipped Weight UOM
-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- UI Element: Auftragsdisposition_OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> qty shipped.Shipped Weight UOM
-- Column: C_OLCand.QtyShipped_CatchWeight_UOM_ID
-- 2024-09-27T09:38:48.314Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-09-27 12:38:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626102
;

