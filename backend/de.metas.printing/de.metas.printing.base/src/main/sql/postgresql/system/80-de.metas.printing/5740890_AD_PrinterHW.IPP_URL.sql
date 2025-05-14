-- 2024-12-03T10:22:47.976Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583382,0,'IPP_URL',TO_TIMESTAMP('2024-12-03 12:22:47','YYYY-MM-DD HH24:MI:SS'),100,'Internet Printing Protol URL','de.metas.printing','Y','IPP URL','IPP URL',TO_TIMESTAMP('2024-12-03 12:22:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-12-03T10:22:47.981Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583382 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_PrinterHW.IPP_URL
-- Column: AD_PrinterHW.IPP_URL
-- 2024-12-03T10:23:11.555Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589469,583382,0,10,540438,'XX','IPP_URL',TO_TIMESTAMP('2024-12-03 12:23:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Internet Printing Protol URL','de.metas.printing',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'IPP URL',0,0,TO_TIMESTAMP('2024-12-03 12:23:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-12-03T10:23:11.559Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589469 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-03T10:23:11.733Z
/* DDL */  select update_Column_Translation_From_AD_Element(583382) 
;

-- 2024-12-03T10:23:12.783Z
/* DDL */ SELECT public.db_alter_table('AD_PrinterHW','ALTER TABLE public.AD_PrinterHW ADD COLUMN IPP_URL VARCHAR(255)')
;

-- Field: Hardware-Drucker -> Drucker -> IPP URL
-- Column: AD_PrinterHW.IPP_URL
-- Field: Hardware-Drucker(540167,de.metas.printing) -> Drucker(540463,de.metas.printing) -> IPP URL
-- Column: AD_PrinterHW.IPP_URL
-- 2024-12-03T10:23:43.671Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589469,734058,0,540463,TO_TIMESTAMP('2024-12-03 12:23:43','YYYY-MM-DD HH24:MI:SS'),100,'Internet Printing Protol URL',255,'de.metas.printing','Y','N','N','N','N','N','N','N','IPP URL',TO_TIMESTAMP('2024-12-03 12:23:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-12-03T10:23:43.673Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-03T10:23:43.677Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583382) 
;

-- 2024-12-03T10:23:43.698Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734058
;

-- 2024-12-03T10:23:43.703Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734058)
;

-- UI Element: Hardware-Drucker -> Drucker.IPP URL
-- Column: AD_PrinterHW.IPP_URL
-- UI Element: Hardware-Drucker(540167,de.metas.printing) -> Drucker(540463,de.metas.printing) -> main -> 10 -> default.IPP URL
-- Column: AD_PrinterHW.IPP_URL
-- 2024-12-03T10:24:39.734Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734058,0,540463,541040,627377,'F',TO_TIMESTAMP('2024-12-03 12:24:39','YYYY-MM-DD HH24:MI:SS'),100,'Internet Printing Protol URL','Y','N','Y','N','N','IPP URL',50,0,0,TO_TIMESTAMP('2024-12-03 12:24:39','YYYY-MM-DD HH24:MI:SS'),100)
;

