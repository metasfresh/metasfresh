-- 2022-09-13T14:11:57.245Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581445,0,'IsDunningDefault',TO_TIMESTAMP('2022-09-13 16:11:57','YYYY-MM-DD HH24:MI:SS'),100,'Dunning Default','D','Y','Dunning Default','Dunning Default',TO_TIMESTAMP('2022-09-13 16:11:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-13T14:11:57.250Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581445 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_User.IsDunningDefault
-- 2022-09-13T14:15:17.943Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584329,581445,0,20,114,'IsDunningDefault',TO_TIMESTAMP('2022-09-13 16:15:17','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Dunning Default','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Dunning Default',0,0,TO_TIMESTAMP('2022-09-13 16:15:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-13T14:15:17.944Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584329 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-13T14:15:17.972Z
/* DDL */  select update_Column_Translation_From_AD_Element(581445)
;

-- 2022-09-13T14:15:35.461Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN IsDunningDefault CHAR(1) DEFAULT ''N'' CHECK (IsDunningDefault IN (''Y'',''N'')) NOT NULL')
;


-- Field: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> Dunning Default
-- Column: AD_User.IsDunningDefault
-- 2022-09-14T07:58:09.514Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584329,707277,0,496,1,TO_TIMESTAMP('2022-09-14 09:58:09','YYYY-MM-DD HH24:MI:SS'),100,'Dunning Default',1,'U',0,'Y','Y','Y','N','N','N','N','N','Dunning Default',0,320,0,1,1,TO_TIMESTAMP('2022-09-14 09:58:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-14T07:58:09.517Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707277 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-14T07:58:09.544Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581445)
;

-- 2022-09-14T07:58:09.556Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707277
;

-- 2022-09-14T07:58:09.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707277)
;

-- UI Element: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.Dunning Default
-- Column: AD_User.IsDunningDefault
-- 2022-09-14T07:59:51.450Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707277,0,496,540578,612981,'F',TO_TIMESTAMP('2022-09-14 09:59:51','YYYY-MM-DD HH24:MI:SS'),100,'Dunning Default','Y','N','N','Y','N','N','N',0,'Dunning Default',105,0,0,TO_TIMESTAMP('2022-09-14 09:59:51','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2022-09-14T09:02:51.047Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540707,0,114,'IsDunningDefault=''N''','SQLS',TO_TIMESTAMP('2022-09-14 11:02:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Only one dunning default contact can be active. If you change it, the previous dunning default contact will be automatically unchecked.','Y','Y','AD_User_IsDunningDefault','N',TO_TIMESTAMP('2022-09-14 11:02:50','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_ID IS NOT NULL AND IsDunningDefault=''Y''')
;

-- 2022-09-14T09:02:51.051Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540707 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-09-14T09:03:14.075Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5844,541268,540707,0,TO_TIMESTAMP('2022-09-14 11:03:13','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',10,TO_TIMESTAMP('2022-09-14 11:03:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-14T09:03:34.531Z
UPDATE AD_Index_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-09-14 11:03:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=541268
;

-- 2022-09-14T09:03:48.649Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584329,541269,540707,0,TO_TIMESTAMP('2022-09-14 11:03:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2022-09-14 11:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-14T09:09:18.223Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-14 11:09:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540707 AND AD_Language='en_US'
;


-- 2022-09-14T09:17:29.304Z
UPDATE AD_Index_Table SET BeforeChangeWarning='Do you really want to change the dunning default?',Updated=TO_TIMESTAMP('2022-09-14 11:17:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540707
;


-- UI Element: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.Dunning Default
-- Column: AD_User.isDunningDefault
-- 2022-09-14T18:31:09.721Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2022-09-14 20:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612981
;
