
-- 2024-12-11T18:52:20.604Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583394,0,'IsSwitchCreditMemo',TO_TIMESTAMP('2024-12-11 20:52:19.921','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Switch Credit Memo','Switch Credit Memo',TO_TIMESTAMP('2024-12-11 20:52:19.921','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-11T18:52:20.632Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583394 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: DATEV_Export.IsSwitchCreditMemo
-- Column: DATEV_Export.IsSwitchCreditMemo
-- 2024-12-11T18:54:35.095Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589481,583394,0,20,540934,'IsSwitchCreditMemo',TO_TIMESTAMP('2024-12-11 20:54:34.686','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.datev',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Switch Credit Memo',0,0,TO_TIMESTAMP('2024-12-11 20:54:34.686','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-12-11T18:54:35.123Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589481 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-11T18:54:35.266Z
/* DDL */  select update_Column_Translation_From_AD_Element(583394) 
;

-- 2024-12-11T18:54:40.946Z
/* DDL */ SELECT public.db_alter_table('DATEV_Export','ALTER TABLE public.DATEV_Export ADD COLUMN IsSwitchCreditMemo CHAR(1) DEFAULT ''N'' CHECK (IsSwitchCreditMemo IN (''Y'',''N'')) NOT NULL')
;

-- 2024-12-11T19:10:48.642Z
INSERT INTO t_alter_column values('datev_export','IsSwitchCreditMemo','CHAR(1)',null,'N')
;

-- 2024-12-11T19:10:48.682Z
UPDATE DATEV_Export SET IsSwitchCreditMemo='N' WHERE IsSwitchCreditMemo IS NULL
;

-- Field: Buchungen Export_OLD -> Buchungen Export -> Switch Credit Memo
-- Column: DATEV_Export.IsSwitchCreditMemo
-- Field: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> Switch Credit Memo
-- Column: DATEV_Export.IsSwitchCreditMemo
-- 2024-12-11T19:11:48.805Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589481,734079,0,541036,TO_TIMESTAMP('2024-12-11 21:11:48.211','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.datev','Y','N','N','N','N','N','N','N','Switch Credit Memo',TO_TIMESTAMP('2024-12-11 21:11:48.211','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-11T19:11:48.832Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-11T19:11:48.860Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583394) 
;

-- 2024-12-11T19:11:48.910Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734079
;

-- 2024-12-11T19:11:48.942Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734079)
;

-- UI Element: Buchungen Export_OLD -> Buchungen Export.Switch Credit Memo
-- Column: DATEV_Export.IsSwitchCreditMemo
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Switch Credit Memo
-- Column: DATEV_Export.IsSwitchCreditMemo
-- 2024-12-11T19:21:26.154Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734079,0,541036,541481,627390,'F',TO_TIMESTAMP('2024-12-11 21:21:25.127','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Switch Credit Memo',30,0,0,TO_TIMESTAMP('2024-12-11 21:21:25.127','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchungen Export_OLD -> Buchungen Export.Switch Credit Memo
-- Column: DATEV_Export.IsSwitchCreditMemo
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Switch Credit Memo
-- Column: DATEV_Export.IsSwitchCreditMemo
-- 2024-12-11T19:21:42.735Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-12-11 21:21:42.735','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=627390
;

-- UI Element: Buchungen Export_OLD -> Buchungen Export.Verarbeitet
-- Column: DATEV_Export.Processed
-- UI Element: Buchungen Export_OLD(540413,de.metas.datev) -> Buchungen Export(541036,de.metas.datev) -> main -> 20 -> flags.Verarbeitet
-- Column: DATEV_Export.Processed
-- 2024-12-11T19:21:42.956Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-12-11 21:21:42.956','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=551038
;

