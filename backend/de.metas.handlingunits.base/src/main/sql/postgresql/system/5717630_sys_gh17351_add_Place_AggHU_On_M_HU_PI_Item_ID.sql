-- 2024-02-20T09:10:34.666Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582971,0,'Place_AggHU_On_M_HU_PI_Item_ID',TO_TIMESTAMP('2024-02-20 11:10:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Place Aggregated-HUs on LU','Place Aggregated-HUs on LU',TO_TIMESTAMP('2024-02-20 11:10:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-20T09:10:34.677Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582971 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- 2024-02-20T09:10:59.140Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587948,582971,0,30,540501,207,'Place_AggHU_On_M_HU_PI_Item_ID',TO_TIMESTAMP('2024-02-20 11:10:58','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Place Aggregated-HUs on LU',0,0,TO_TIMESTAMP('2024-02-20 11:10:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-20T09:10:59.153Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587948 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-20T09:10:59.201Z
/* DDL */  select update_Column_Translation_From_AD_Element(582971) 
;

-- 2024-02-20T09:11:17.426Z
/* DDL */ SELECT public.db_alter_table('M_Locator','ALTER TABLE public.M_Locator ADD COLUMN Place_AggHU_On_M_HU_PI_Item_ID NUMERIC(10)')
;

-- 2024-02-20T09:11:18.106Z
ALTER TABLE M_Locator ADD CONSTRAINT PlaceAggHUOnMHUPIItem_MLocator FOREIGN KEY (Place_AggHU_On_M_HU_PI_Item_ID) REFERENCES public.M_HU_PI_Item DEFERRABLE INITIALLY DEFERRED
;

-- Field: Lager und Lagerort -> Lagerort -> Place Aggregated-HUs on LU
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- Field: Lager und Lagerort(139,D) -> Lagerort(178,D) -> Place Aggregated-HUs on LU
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- 2024-02-20T09:50:14.500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587948,725188,0,178,TO_TIMESTAMP('2024-02-20 11:50:14','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Place Aggregated-HUs on LU',TO_TIMESTAMP('2024-02-20 11:50:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-20T09:50:14.506Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725188 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-20T09:50:14.512Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582971) 
;

-- 2024-02-20T09:50:14.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725188
;

-- 2024-02-20T09:50:14.535Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725188)
;

-- UI Element: Lager und Lagerort -> Lagerort.Place Aggregated-HUs on LU
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- UI Element: Lager und Lagerort(139,D) -> Lagerort(178,D) -> main -> 10 -> default.Place Aggregated-HUs on LU
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- 2024-02-20T09:50:33.928Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725188,0,178,622951,541165,'F',TO_TIMESTAMP('2024-02-20 11:50:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Place Aggregated-HUs on LU',130,0,0,TO_TIMESTAMP('2024-02-20 11:50:33','YYYY-MM-DD HH24:MI:SS'),100)
;

