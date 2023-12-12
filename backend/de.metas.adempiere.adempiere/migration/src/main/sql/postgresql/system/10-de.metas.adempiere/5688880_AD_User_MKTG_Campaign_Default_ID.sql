-- 2023-05-23T07:43:31.965Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582361,0,'MKTG_Campaign_Default_ID',TO_TIMESTAMP('2023-05-23 10:43:31','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','Standard-Werbemassnahme','Standard-Werbemassnahme',TO_TIMESTAMP('2023-05-23 10:43:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T07:43:31.978Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582361 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-05-23T07:43:42.890Z
UPDATE AD_Element SET EntityType='de.metas.marketing.base',Updated=TO_TIMESTAMP('2023-05-23 10:43:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582361
;

-- 2023-05-23T07:43:42.924Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582361,'de_DE') 
;

-- 2023-05-23T07:46:04.508Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541737,TO_TIMESTAMP('2023-05-23 10:46:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','N','MKTG_Campaign',TO_TIMESTAMP('2023-05-23 10:46:04','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-05-23T07:46:04.511Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541737 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2023-05-23T07:46:27.122Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,559917,0,541737,540970,TO_TIMESTAMP('2023-05-23 10:46:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','N','N',TO_TIMESTAMP('2023-05-23 10:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: AD_User.MKTG_Campaign_Default_ID
-- 2023-05-23T07:47:29.692Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586687,582361,0,18,541737,114,'MKTG_Campaign_Default_ID',TO_TIMESTAMP('2023-05-23 10:47:29','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.marketing.base',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standard-Werbemassnahme',0,0,TO_TIMESTAMP('2023-05-23 10:47:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T07:47:29.697Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586687 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T07:47:29.703Z
/* DDL */  select update_Column_Translation_From_AD_Element(582361) 
;

-- 2023-05-23T07:47:33.185Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN MKTG_Campaign_Default_ID NUMERIC(10)')
;

-- 2023-05-23T07:47:34.151Z
ALTER TABLE AD_User ADD CONSTRAINT MKTGCampaignDefault_ADUser FOREIGN KEY (MKTG_Campaign_Default_ID) REFERENCES public.MKTG_Campaign DEFERRABLE INITIALLY DEFERRED
;

-- Field: Mein Profil -> User Contact -> Standard-Werbemassnahme
-- Column: AD_User.MKTG_Campaign_Default_ID
-- 2023-05-23T07:49:50.736Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586687,715493,0,53282,TO_TIMESTAMP('2023-05-23 10:49:50','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','Y','N','N','N','N','N','N','N','Standard-Werbemassnahme',TO_TIMESTAMP('2023-05-23 10:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T07:49:50.740Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715493 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-23T07:49:50.744Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582361) 
;

-- 2023-05-23T07:49:50.758Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715493
;

-- 2023-05-23T07:49:50.764Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715493)
;

-- 2023-05-23T07:51:23.518Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540344,550717,TO_TIMESTAMP('2023-05-23 10:51:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','defaults',40,TO_TIMESTAMP('2023-05-23 10:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mein Profil -> User Contact.Standard-Werbemassnahme
-- Column: AD_User.MKTG_Campaign_Default_ID
-- 2023-05-23T07:51:49.871Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715493,0,53282,550717,617542,'F',TO_TIMESTAMP('2023-05-23 10:51:49','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','N','N','Standard-Werbemassnahme',10,0,0,TO_TIMESTAMP('2023-05-23 10:51:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: AD_User.MKTG_Campaign_Default_ID
-- 2023-05-23T07:52:35.309Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2023-05-23 10:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586687
;

