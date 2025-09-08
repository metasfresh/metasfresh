-- 2022-11-15T08:04:45.422Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581673,0,'IsBypassAudit',TO_TIMESTAMP('2022-11-15 10:04:44','YYYY-MM-DD HH24:MI:SS'),100,'Completelly bypass audit system. Useful when dealing with non-json endpoints like images.','D','Y','Bypass Audit','Bypass Audit',TO_TIMESTAMP('2022-11-15 10:04:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T08:04:45.458Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581673 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-15T08:05:03.857Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584981,581673,0,20,541635,'IsBypassAudit',TO_TIMESTAMP('2022-11-15 10:05:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Completelly bypass audit system. Useful when dealing with non-json endpoints like images.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Bypass Audit',0,0,TO_TIMESTAMP('2022-11-15 10:05:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-15T08:05:03.894Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584981 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-15T08:05:03.998Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581673) 
;

-- 2022-11-15T08:05:09.133Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE public.API_Audit_Config ADD COLUMN IsBypassAudit CHAR(1) DEFAULT ''N'' CHECK (IsBypassAudit IN (''Y'',''N'')) NOT NULL')
;

-- 2022-11-15T08:07:29.969Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=541125,Updated=TO_TIMESTAMP('2022-11-15 10:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541635
;

/*
-- 2022-11-15T08:08:27.113Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645155
;

-- 2022-11-15T08:08:27.213Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645156
;

-- 2022-11-15T08:08:27.312Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645157
;

-- 2022-11-15T08:08:27.411Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645158
;

-- 2022-11-15T08:08:27.510Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645159
;

-- 2022-11-15T08:08:27.609Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645160
;

-- 2022-11-15T08:08:27.710Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645166
;

-- 2022-11-15T08:08:27.812Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645446
;

-- 2022-11-15T08:08:27.912Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645447
;

-- 2022-11-15T08:08:28.011Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645448
;

-- 2022-11-15T08:08:28.113Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645449
;

-- 2022-11-15T08:08:28.211Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645694
;

-- 2022-11-15T08:08:28.312Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649879
;

-- 2022-11-15T08:08:28.413Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=675216
;

-- 2022-11-15T08:08:28.512Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=675217
;

-- 2022-11-15T08:08:28.611Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-15 10:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=676033
;

*/

-- 2022-11-15T08:09:25.262Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584981,708057,0,543895,TO_TIMESTAMP('2022-11-15 10:09:24','YYYY-MM-DD HH24:MI:SS'),100,'Completelly bypass audit system. Useful when dealing with non-json endpoints like images.',1,'D','Y','N','N','N','N','N','N','N','Bypass Audit',TO_TIMESTAMP('2022-11-15 10:09:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T08:09:25.298Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-15T08:09:25.333Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581673) 
;

-- 2022-11-15T08:09:25.377Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708057
;

-- 2022-11-15T08:09:25.411Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(708057)
;

-- 2022-11-15T08:10:24.881Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708057,0,543895,545768,613466,'F',TO_TIMESTAMP('2022-11-15 10:10:23','YYYY-MM-DD HH24:MI:SS'),100,'Completelly bypass audit system. Useful when dealing with non-json endpoints like images.','Y','N','Y','N','N','Bypass Audit',40,0,0,TO_TIMESTAMP('2022-11-15 10:10:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T08:10:46.977Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-15 10:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586906
;

-- 2022-11-15T08:10:47.115Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-15 10:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584838
;

-- 2022-11-15T08:10:47.253Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-11-15 10:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613466
;

