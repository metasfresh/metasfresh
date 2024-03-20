-- UI Element: Lager und Lagerort -> Lagerort.Place Aggregated-HUs on LU
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- UI Element: Lager und Lagerort(139,D) -> Lagerort(178,D) -> main -> 10 -> default.Place Aggregated-HUs on LU
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- 2024-02-20T12:48:32.590Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=622951
;

-- 2024-02-20T12:48:32.596Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725188
;

-- Field: Lager und Lagerort -> Lagerort -> Place Aggregated-HUs on LU
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- Field: Lager und Lagerort(139,D) -> Lagerort(178,D) -> Place Aggregated-HUs on LU
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- 2024-02-20T12:48:32.619Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=725188
;

-- 2024-02-20T12:48:32.628Z
DELETE FROM AD_Field WHERE AD_Field_ID=725188
;

-- 2024-02-20T12:48:32.636Z
/* DDL */ SELECT public.db_alter_table('M_Locator','ALTER TABLE M_Locator DROP COLUMN IF EXISTS Place_AggHU_On_M_HU_PI_Item_ID')
;

-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- Column: M_Locator.Place_AggHU_On_M_HU_PI_Item_ID
-- 2024-02-20T12:48:34.008Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587948
;

-- 2024-02-20T12:48:34.012Z
DELETE FROM AD_Column WHERE AD_Column_ID=587948
;

-- 2024-02-20T12:53:28.281Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582972,0,'PlaceAggHUOnNewLU',TO_TIMESTAMP('2024-02-20 14:53:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Place aggregated HUs on new LU','Place aggregated HUs on new LU',TO_TIMESTAMP('2024-02-20 14:53:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-20T12:53:28.288Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582972 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Locator.PlaceAggHUOnNewLU
-- Column: M_Locator.PlaceAggHUOnNewLU
-- 2024-02-20T12:54:15.102Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587949,582972,0,20,207,'PlaceAggHUOnNewLU',TO_TIMESTAMP('2024-02-20 14:54:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Place aggregated HUs on new LU',0,0,TO_TIMESTAMP('2024-02-20 14:54:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-20T12:54:15.108Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587949 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-20T12:54:15.115Z
/* DDL */  select update_Column_Translation_From_AD_Element(582972) 
;

-- 2024-02-20T12:54:18.101Z
/* DDL */ SELECT public.db_alter_table('M_Locator','ALTER TABLE public.M_Locator ADD COLUMN PlaceAggHUOnNewLU CHAR(1) DEFAULT ''N'' CHECK (PlaceAggHUOnNewLU IN (''Y'',''N'')) NOT NULL')
;

-- Field: Lager und Lagerort -> Lagerort -> Place aggregated HUs on new LU
-- Column: M_Locator.PlaceAggHUOnNewLU
-- Field: Lager und Lagerort(139,D) -> Lagerort(178,D) -> Place aggregated HUs on new LU
-- Column: M_Locator.PlaceAggHUOnNewLU
-- 2024-02-20T12:54:48.242Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587949,725189,0,178,TO_TIMESTAMP('2024-02-20 14:54:48','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Place aggregated HUs on new LU',TO_TIMESTAMP('2024-02-20 14:54:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-20T12:54:48.245Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725189 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-20T12:54:48.255Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582972) 
;

-- 2024-02-20T12:54:48.270Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725189
;

-- 2024-02-20T12:54:48.282Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725189)
;

-- UI Element: Lager und Lagerort -> Lagerort.Place aggregated HUs on new LU
-- Column: M_Locator.PlaceAggHUOnNewLU
-- UI Element: Lager und Lagerort(139,D) -> Lagerort(178,D) -> main -> 10 -> default.Place aggregated HUs on new LU
-- Column: M_Locator.PlaceAggHUOnNewLU
-- 2024-02-20T12:55:07.378Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725189,0,178,622952,541165,'F',TO_TIMESTAMP('2024-02-20 14:55:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Place aggregated HUs on new LU',130,0,0,TO_TIMESTAMP('2024-02-20 14:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

