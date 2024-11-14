-- 2024-11-11T10:01:25.071Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583363,0,'IsFinalInvoiced',TO_TIMESTAMP('2024-11-11 11:01:24.658','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Schlusszahlung fertiggestellt','Schlusszahlung fertiggestellt',TO_TIMESTAMP('2024-11-11 11:01:24.658','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-11T10:01:25.079Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583363 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsFinalInvoiced
-- 2024-11-11T10:01:53.047Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Final Invoice completed', PrintName='Final Invoice completed',Updated=TO_TIMESTAMP('2024-11-11 11:01:53.047','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583363 AND AD_Language='en_US'
;

-- 2024-11-11T10:01:53.053Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583363,'en_US')
;

-- Element: IsFinalInvoiced
-- 2024-11-11T10:01:53.995Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-11 11:01:53.995','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583363 AND AD_Language='de_CH'
;

-- 2024-11-11T10:01:53.997Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583363,'de_CH')
;

-- Element: IsFinalInvoiced
-- 2024-11-11T10:01:54.966Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-11-11 11:01:54.966','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583363 AND AD_Language='de_DE'
;

-- 2024-11-11T10:01:54.972Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583363,'de_DE')
;

-- 2024-11-11T10:01:54.975Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583363,'de_DE')
;

-- Column: C_Flatrate_Term.IsFinalInvoiced
-- 2024-11-11T10:03:24.857Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589371,583363,0,20,540320,'IsFinalInvoiced',TO_TIMESTAMP('2024-11-11 11:03:24.739','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.contracts',0,1,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Schlusszahlung fertiggestellt',0,0,TO_TIMESTAMP('2024-11-11 11:03:24.739','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-11-11T10:03:24.859Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589371 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-11T10:03:24.862Z
/* DDL */  select update_Column_Translation_From_AD_Element(583363)
;

-- 2024-11-11T10:03:26.281Z
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN IsFinalInvoiced CHAR(1) DEFAULT ''N'' CHECK (IsFinalInvoiced IN (''Y'',''N'')) NOT NULL')
;

-- Field: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Bereit für endgültige Schlussrechnung
-- Column: C_Flatrate_Term.IsReadyForDefinitiveInvoice
-- 2024-11-11T14:46:59.416Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588323,733473,0,540859,TO_TIMESTAMP('2024-11-11 15:46:59.263','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.contracts','Y','N','N','N','N','N','N','N','Bereit für endgültige Schlussrechnung',TO_TIMESTAMP('2024-11-11 15:46:59.263','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-11T14:46:59.419Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733473 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-11T14:46:59.421Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583126)
;

-- 2024-11-11T14:46:59.426Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733473
;

-- 2024-11-11T14:46:59.427Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733473)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Schlusszahlung fertiggestellt
-- Column: C_Flatrate_Term.IsFinalInvoiced
-- 2024-11-11T14:47:12.430Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589371,733474,0,540859,TO_TIMESTAMP('2024-11-11 15:47:12.32','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.contracts','Y','N','N','N','N','N','N','N','Schlusszahlung fertiggestellt',TO_TIMESTAMP('2024-11-11 15:47:12.32','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-11T14:47:12.433Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733474 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-11T14:47:12.435Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583363)
;

-- 2024-11-11T14:47:12.439Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733474
;

-- 2024-11-11T14:47:12.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733474)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> advanced edit -> 10 -> advanced edit.Bereit für endgültige Schlussrechnung
-- Column: C_Flatrate_Term.IsReadyForDefinitiveInvoice
-- 2024-11-11T14:48:19.514Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733473,0,540859,541104,627013,'F',TO_TIMESTAMP('2024-11-11 15:48:19.395','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Bereit für endgültige Schlussrechnung',480,0,0,TO_TIMESTAMP('2024-11-11 15:48:19.395','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> advanced edit -> 10 -> advanced edit.Schlusszahlung fertiggestellt
-- Column: C_Flatrate_Term.IsFinalInvoiced
-- 2024-11-11T14:48:45.566Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733474,0,540859,541104,627014,'F',TO_TIMESTAMP('2024-11-11 15:48:45.318','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Schlusszahlung fertiggestellt',490,0,0,TO_TIMESTAMP('2024-11-11 15:48:45.318','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Schlusszahlung fertiggestellt
-- Column: C_Flatrate_Term.IsFinalInvoiced
-- 2024-11-11T14:49:13.802Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-11-11 15:49:13.802','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=733474
;

-- Field: Vertrag(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Bereit für endgültige Schlussrechnung
-- Column: C_Flatrate_Term.IsReadyForDefinitiveInvoice
-- 2024-11-11T14:49:19.207Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-11-11 15:49:19.207','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=733473
;
