-- Run mode: SWING_CLIENT

-- 2025-05-05T14:45:47.173Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583606,0,'C_Project_Value',TO_TIMESTAMP('2025-05-05 17:45:46.91','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Projekt Suchschlüssel','Projekt Suchschlüssel',TO_TIMESTAMP('2025-05-05 17:45:46.91','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-05T14:45:47.188Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583606 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_Project_Value
-- 2025-05-05T14:47:21.809Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Projekt Value', PrintName='Projekt Value',Updated=TO_TIMESTAMP('2025-05-05 17:47:21.809','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583606 AND AD_Language='en_US'
;

-- 2025-05-05T14:47:21.841Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583606,'en_US')
;

-- Column: I_Invoice_Candidate.C_Project_Value
-- 2025-05-05T14:47:55.217Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589927,583606,0,10,542207,'C_Project_Value',TO_TIMESTAMP('2025-05-05 17:47:54.956','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt Suchschlüssel',0,0,TO_TIMESTAMP('2025-05-05 17:47:54.956','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-05T14:47:55.226Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589927 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-05T14:47:55.243Z
/* DDL */  select update_Column_Translation_From_AD_Element(583606)
;

-- 2025-05-05T14:47:59.740Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN C_Project_Value VARCHAR(255)')
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Projekt Suchschlüssel
-- Column: I_Invoice_Candidate.C_Project_Value
-- 2025-05-05T15:22:57.914Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589927,741999,0,546594,TO_TIMESTAMP('2025-05-05 18:22:57.758','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Projekt Suchschlüssel',TO_TIMESTAMP('2025-05-05 18:22:57.758','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-05T15:22:57.916Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-05T15:22:57.917Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583606)
;

-- 2025-05-05T15:22:57.930Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741999
;

-- 2025-05-05T15:22:57.932Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741999)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> default.Projekt Suchschlüssel
-- Column: I_Invoice_Candidate.C_Project_Value
-- 2025-05-05T15:23:44.338Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741999,0,546594,549832,631410,'F',TO_TIMESTAMP('2025-05-05 18:23:43.964','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Projekt Suchschlüssel',100,0,0,TO_TIMESTAMP('2025-05-05 18:23:43.964','YYYY-MM-DD HH24:MI:SS.US'),100)
;

