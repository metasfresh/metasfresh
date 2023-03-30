-- 2023-03-30T10:19:31.388Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582196,0,'SAP_AccountType',TO_TIMESTAMP('2023-03-30 13:19:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','SAP Account Type','SAP Account Type',TO_TIMESTAMP('2023-03-30 13:19:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T10:19:31.641Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582196 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_ElementValue.SAP_AccountType
-- 2023-03-30T10:20:28.025Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586377,582196,0,10,188,'SAP_AccountType',TO_TIMESTAMP('2023-03-30 13:20:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SAP Account Type',0,0,TO_TIMESTAMP('2023-03-30 13:20:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-30T10:20:28.036Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586377 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-30T10:20:28.763Z
/* DDL */  select update_Column_Translation_From_AD_Element(582196) 
;

-- 2023-03-30T10:20:32.861Z
/* DDL */ SELECT public.db_alter_table('C_ElementValue','ALTER TABLE public.C_ElementValue ADD COLUMN SAP_AccountType VARCHAR(2)')
;

-- Field: Konten(540761,D) -> Kontenart(542127,D) -> SAP Account Type
-- Column: C_ElementValue.SAP_AccountType
-- 2023-03-30T10:22:35.669Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586377,713579,0,542127,TO_TIMESTAMP('2023-03-30 13:22:35','YYYY-MM-DD HH24:MI:SS'),100,2,'D','Y','N','N','N','N','N','N','N','SAP Account Type',TO_TIMESTAMP('2023-03-30 13:22:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T10:22:35.679Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-30T10:22:35.686Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582196) 
;

-- 2023-03-30T10:22:35.713Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713579
;

-- 2023-03-30T10:22:35.721Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713579)
;

-- Field: Konten(540761,D) -> Kontenart(542127,D) -> SAP Account Type
-- Column: C_ElementValue.SAP_AccountType
-- 2023-03-30T10:23:05.399Z
UPDATE AD_Field SET DisplayLogic='1=0',Updated=TO_TIMESTAMP('2023-03-30 13:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713579
;

-- UI Element: Konten(540761,D) -> Kontenart(542127,D) -> main -> 10 -> desc.SAP Account Type
-- Column: C_ElementValue.SAP_AccountType
-- 2023-03-30T10:23:34.096Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,713579,0,542127,616487,543187,'F',TO_TIMESTAMP('2023-03-30 13:23:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SAP Account Type',60,0,0,TO_TIMESTAMP('2023-03-30 13:23:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Konten(540761,D) -> Kontenart(542127,D) -> main -> 10 -> desc.SAP Account Type
-- Column: C_ElementValue.SAP_AccountType
-- 2023-03-30T10:48:40.739Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-03-30 13:48:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616487
;

