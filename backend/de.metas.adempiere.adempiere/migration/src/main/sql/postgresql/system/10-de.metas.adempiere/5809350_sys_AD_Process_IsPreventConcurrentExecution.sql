-- AD_Process.IsPreventConcurrentExecution: when 'Y', the process engine runs only one instance of
-- this process at a time (a concurrent run skips). Generic server-side single-instance protection
-- (distinct from the legacy Swing-client IsOneInstanceOnly form-open guard).

-- Element
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585044 /*From ID Server*/,0,'IsPreventConcurrentExecution',TO_TIMESTAMP('2026-06-23 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gleichzeitige Ausführung verhindern','Gleichzeitige Ausführung verhindern',TO_TIMESTAMP('2026-06-23 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585044 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gleichzeitige Ausführung verhindern', PrintName='Gleichzeitige Ausführung verhindern',Updated=TO_TIMESTAMP('2026-06-23 12:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585044 AND AD_Language='de_CH'
;
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585044,'de_CH')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gleichzeitige Ausführung verhindern', PrintName='Gleichzeitige Ausführung verhindern',Updated=TO_TIMESTAMP('2026-06-23 12:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585044 AND AD_Language='de_DE'
;
/* DDL */  select update_ad_element_on_ad_element_trl_update(585044,'de_DE')
;
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585044,'de_DE')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Prevent Concurrent Execution', PrintName='Prevent Concurrent Execution',Updated=TO_TIMESTAMP('2026-06-23 12:00:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585044 AND AD_Language='en_US'
;
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585044,'en_US')
;

-- Column: AD_Process.IsPreventConcurrentExecution (AD_Reference_ID=20 YesNo, AD_Table_ID=284)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592877 /*From ID Server*/,585044,0,20,284,'IsPreventConcurrentExecution',TO_TIMESTAMP('2026-06-23 12:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gleichzeitige Ausführung verhindern','NP',0,0,TO_TIMESTAMP('2026-06-23 12:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592877 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */  select update_Column_Translation_From_AD_Element(585044)
;

-- DB column
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN IsPreventConcurrentExecution CHAR(1) DEFAULT ''N'' CHECK (IsPreventConcurrentExecution IN (''Y'',''N'')) NOT NULL')
;

-- Field on AD_Process window 165, tab 245 (Bericht & Prozess), in the "flags" element group (541395)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592877,781244 /*From ID Server*/,0,245,0,TO_TIMESTAMP('2026-06-23 12:02:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','N','N','N','N','N','N','Gleichzeitige Ausführung verhindern',155,0,0,1,1,TO_TIMESTAMP('2026-06-23 12:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=781244 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585044)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781244
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(781244)
;

-- UI Element in the "flags" group (541395) of tab 245, between IsServerProcess (SeqNo 40) and IsApplySecuritySettings (SeqNo 50)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781244,0,245,541395,652363 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-23 12:02:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Gleichzeitige Ausführung verhindern',45,0,0,TO_TIMESTAMP('2026-06-23 12:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Enable single-instance protection for the MD_Stock_Update_From_M_HUs process (AD_Process_ID=540907)
UPDATE AD_Process SET IsPreventConcurrentExecution='Y',Updated=TO_TIMESTAMP('2026-06-23 12:03:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540907
;
