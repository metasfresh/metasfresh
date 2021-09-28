-- 2021-08-25T07:27:19.085Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575499
;

-- 2021-08-25T07:27:19.314Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575499
;

-- 2021-08-25T07:30:13.710Z
-- URL zum Konzept
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=579570
;

-- 2021-08-25T07:30:13.947Z
-- URL zum Konzept
DELETE FROM AD_Element WHERE AD_Element_ID=579570
;

-- 2021-08-25T07:30:48.859Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579576,0,'CRM_Occupation_Parent_ID',TO_TIMESTAMP('2021-08-25 10:30:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Parent Specialization','Parent Specialization',TO_TIMESTAMP('2021-08-25 10:30:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-25T07:30:48.940Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579576 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-08-25T07:31:49.720Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575500,579576,0,30,541384,541771,'CRM_Occupation_Parent_ID',TO_TIMESTAMP('2021-08-25 10:31:49','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Parent Specialization',0,0,TO_TIMESTAMP('2021-08-25 10:31:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-25T07:31:49.812Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575500 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-25T07:31:49.950Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579576)
;

-- 2021-08-25T07:33:54.891Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575500,652708,0,544248,TO_TIMESTAMP('2021-08-25 10:33:54','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Parent Specialization',TO_TIMESTAMP('2021-08-25 10:33:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-25T07:33:55.100Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652708 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-25T07:33:55.142Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579576)
;

-- 2021-08-25T07:33:55.185Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652708
;

-- 2021-08-25T07:33:55.222Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652708)
;

-- 2021-08-25T07:35:34.222Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652708,0,544248,546319,588106,'F',TO_TIMESTAMP('2021-08-25 10:35:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Parent Specialization',60,0,0,TO_TIMESTAMP('2021-08-25 10:35:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-25T07:37:08.036Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-08-25 10:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=588093
;

-- 2021-08-25T07:37:08.267Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-08-25 10:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=588094
;

-- 2021-08-25T07:37:08.454Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-08-25 10:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=588095
;

-- 2021-08-25T07:37:08.656Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-08-25 10:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=588106
;

-- 2021-08-25T07:37:08.851Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-08-25 10:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=588096
;

-- 2021-08-25T07:37:56.910Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2021-08-25 10:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=588106
;