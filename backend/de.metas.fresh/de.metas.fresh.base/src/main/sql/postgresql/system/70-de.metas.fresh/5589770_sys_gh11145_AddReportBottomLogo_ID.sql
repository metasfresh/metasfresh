-- 2021-05-24T13:23:13.431Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579244,0,'ReportBottom_Logo_ID',TO_TIMESTAMP('2021-05-24 15:23:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Report Bottom Logo','Report Bottom Logo',TO_TIMESTAMP('2021-05-24 15:23:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:23:13.434Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579244 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-24T13:23:31.293Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574073,579244,0,19,228,'ReportBottom_Logo_ID',TO_TIMESTAMP('2021-05-24 15:23:31','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Report Bottom Logo',0,0,TO_TIMESTAMP('2021-05-24 15:23:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-24T13:23:31.295Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574073 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-24T13:23:31.313Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579244) 
;

-- 2021-05-24T13:23:46.858Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=32,Updated=TO_TIMESTAMP('2021-05-24 15:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574073
;

-- 2021-05-24T13:23:50.004Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_OrgInfo','ALTER TABLE public.AD_OrgInfo ADD COLUMN ReportBottom_Logo_ID NUMERIC(10)')
;

-- 2021-05-24T13:23:50.363Z
-- URL zum Konzept
ALTER TABLE AD_OrgInfo ADD CONSTRAINT ReportBottomLogo_ADOrgInfo FOREIGN KEY (ReportBottom_Logo_ID) REFERENCES public.AD_Image DEFERRABLE INITIALLY DEFERRED
;

-- 2021-05-24T13:24:08.310Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563754,646808,0,170,TO_TIMESTAMP('2021-05-24 15:24:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','AD_OrgInfo_ID',TO_TIMESTAMP('2021-05-24 15:24:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:24:08.312Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:24:08.314Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575936) 
;

-- 2021-05-24T13:24:08.322Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646808
;

-- 2021-05-24T13:24:08.325Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646808)
;

-- 2021-05-24T13:24:08.392Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574073,646809,0,170,TO_TIMESTAMP('2021-05-24 15:24:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Report Bottom Logo',TO_TIMESTAMP('2021-05-24 15:24:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:24:08.393Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-24T13:24:08.394Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579244) 
;

-- 2021-05-24T13:24:08.396Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646809
;

-- 2021-05-24T13:24:08.396Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(646809)
;

-- 2021-05-24T13:24:42.555Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646809,0,170,540463,585258,'F',TO_TIMESTAMP('2021-05-24 15:24:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Report Bottom Logo',92,0,0,TO_TIMESTAMP('2021-05-24 15:24:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-24T13:25:10.120Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2021-05-24 15:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560149
;

-- 2021-05-24T13:25:10.124Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2021-05-24 15:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544522
;

-- 2021-05-24T13:25:10.127Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2021-05-24 15:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585258
;

-- 2021-05-24T13:25:49.827Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2021-05-24 15:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585258
;

