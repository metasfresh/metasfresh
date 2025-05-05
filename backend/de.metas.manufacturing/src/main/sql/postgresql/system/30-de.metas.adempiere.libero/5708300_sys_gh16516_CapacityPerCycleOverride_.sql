-- 2023-10-23T11:28:06.568Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582777,0,'CapacityPerProductionCycleOverride',TO_TIMESTAMP('2023-10-23 14:28:06','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Kapazität pro Produktionszyklus','Kapazität pro Produktionszyklus',TO_TIMESTAMP('2023-10-23 14:28:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T11:28:06.577Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582777 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CapacityPerProductionCycleOverride
-- 2023-10-23T11:28:31.501Z
UPDATE AD_Element_Trl SET Name='Capacity Per Production Cycle', PrintName='Capacity Per Production Cycle',Updated=TO_TIMESTAMP('2023-10-23 14:28:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582777 AND AD_Language='en_US'
;

-- 2023-10-23T11:28:31.547Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582777,'en_US') 
;

-- Column: PP_Order_Candidate.CapacityPerProductionCycleOverride
-- Column: PP_Order_Candidate.CapacityPerProductionCycleOverride
-- 2023-10-23T11:29:15.829Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587602,582777,0,29,541913,'CapacityPerProductionCycleOverride',TO_TIMESTAMP('2023-10-23 14:29:15','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kapazität pro Produktionszyklus',0,0,TO_TIMESTAMP('2023-10-23 14:29:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-23T11:29:15.831Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587602 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-23T11:29:15.836Z
/* DDL */  select update_Column_Translation_From_AD_Element(582777) 
;

-- 2023-10-23T11:30:32.493Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE public.PP_Order_Candidate ADD COLUMN CapacityPerProductionCycleOverride NUMERIC')
;

-- Field: Produktionsdisposition -> Produktionsdisposition -> Kapazität pro Produktionszyklus
-- Column: PP_Order_Candidate.CapacityPerProductionCycleOverride
-- Field: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> Kapazität pro Produktionszyklus
-- Column: PP_Order_Candidate.CapacityPerProductionCycleOverride
-- 2023-10-23T11:31:37.377Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587602,721714,0,544794,TO_TIMESTAMP('2023-10-23 14:31:37','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Kapazität pro Produktionszyklus',TO_TIMESTAMP('2023-10-23 14:31:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T11:31:37.385Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721714 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-23T11:31:37.394Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582777) 
;

-- 2023-10-23T11:31:37.419Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721714
;

-- 2023-10-23T11:31:37.428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721714)
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Kapazität pro Produktionszyklus
-- Column: PP_Order_Candidate.CapacityPerProductionCycleOverride
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Kapazität pro Produktionszyklus
-- Column: PP_Order_Candidate.CapacityPerProductionCycleOverride
-- 2023-10-23T11:32:01.937Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721714,0,544794,621165,547107,'F',TO_TIMESTAMP('2023-10-23 14:32:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kapazität pro Produktionszyklus',50,0,0,TO_TIMESTAMP('2023-10-23 14:32:01','YYYY-MM-DD HH24:MI:SS'),100)
;

