-- 2024-10-02T14:58:07.723Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583296,0,'ScannedBarcode',TO_TIMESTAMP('2024-10-02 17:58:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Scanned Barcode','Scanned Barcode',TO_TIMESTAMP('2024-10-02 17:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-02T14:58:07.740Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583296 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_OrderLine.ScannedBarcode
-- Column: C_POS_OrderLine.ScannedBarcode
-- 2024-10-02T14:58:22.247Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589179,583296,0,36,542436,'XX','ScannedBarcode',TO_TIMESTAMP('2024-10-02 17:58:22','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Scanned Barcode',0,0,TO_TIMESTAMP('2024-10-02 17:58:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-02T14:58:22.251Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589179 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-02T14:58:22.303Z
/* DDL */  select update_Column_Translation_From_AD_Element(583296) 
;

-- 2024-10-02T14:58:23.629Z
/* DDL */ SELECT public.db_alter_table('C_POS_OrderLine','ALTER TABLE public.C_POS_OrderLine ADD COLUMN ScannedBarcode TEXT')
;

-- Field: POS Order -> POS Order Line -> Scanned Barcode
-- Column: C_POS_OrderLine.ScannedBarcode
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Scanned Barcode
-- Column: C_POS_OrderLine.ScannedBarcode
-- 2024-10-02T14:58:44.144Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589179,731796,0,547592,TO_TIMESTAMP('2024-10-02 17:58:43','YYYY-MM-DD HH24:MI:SS'),100,2000,'de.metas.pos','Y','N','N','N','N','N','N','N','Scanned Barcode',TO_TIMESTAMP('2024-10-02 17:58:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-02T14:58:44.149Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-02T14:58:44.153Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583296) 
;

-- 2024-10-02T14:58:44.171Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731796
;

-- 2024-10-02T14:58:44.176Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731796)
;

-- UI Element: POS Order -> POS Order Line.Scanned Barcode
-- Column: C_POS_OrderLine.ScannedBarcode
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> advanced -> 10 -> advanced.Scanned Barcode
-- Column: C_POS_OrderLine.ScannedBarcode
-- 2024-10-02T14:59:14.705Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731796,0,547592,551962,626111,'F',TO_TIMESTAMP('2024-10-02 17:59:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Scanned Barcode',20,0,0,TO_TIMESTAMP('2024-10-02 17:59:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Scanned Barcode
-- Column: C_POS_OrderLine.ScannedBarcode
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> advanced -> 10 -> advanced.Scanned Barcode
-- Column: C_POS_OrderLine.ScannedBarcode
-- 2024-10-02T14:59:24.002Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-10-02 17:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626111
;

-- Field: POS Order -> POS Order Line -> Scanned Barcode
-- Column: C_POS_OrderLine.ScannedBarcode
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Scanned Barcode
-- Column: C_POS_OrderLine.ScannedBarcode
-- 2024-10-02T14:59:38.577Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-10-02 17:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731796
;

