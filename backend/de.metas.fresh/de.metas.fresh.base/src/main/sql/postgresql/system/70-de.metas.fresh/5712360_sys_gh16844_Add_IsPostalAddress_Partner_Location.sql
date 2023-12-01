-- 2023-12-01T13:54:50.587Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582828,0,'IsPostalAddress',TO_TIMESTAMP('2023-12-01 14:54:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Postadresse','Postadresse',TO_TIMESTAMP('2023-12-01 14:54:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-01T13:54:50.932Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582828 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-12-01T13:56:22.132Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Postal Address', PrintName='Postal Address',Updated=TO_TIMESTAMP('2023-12-01 14:56:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582828 AND AD_Language='en_US'
;

-- 2023-12-01T13:56:22.343Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582828,'en_US') 
;

-- Column: C_BPartner_Location.IsPostalAddress
-- 2023-12-01T14:00:43.969Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587671,582828,0,20,293,'IsPostalAddress',TO_TIMESTAMP('2023-12-01 15:00:43','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Postadresse',0,0,TO_TIMESTAMP('2023-12-01 15:00:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-12-01T14:00:44.410Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587671 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-01T14:00:44.552Z
/* DDL */  select update_Column_Translation_From_AD_Element(582828) 
;

-- 2023-12-01T14:01:09.745Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN IsPostalAddress CHAR(1) DEFAULT ''N'' CHECK (IsPostalAddress IN (''Y'',''N'')) NOT NULL')
;

-- Field: Geschäftspartner -> Adresse -> Postadresse
-- Column: C_BPartner_Location.IsPostalAddress
-- 2023-12-01T14:01:47.327Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587671,721961,0,222,0,TO_TIMESTAMP('2023-12-01 15:01:46','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Postadresse',0,200,0,1,1,TO_TIMESTAMP('2023-12-01 15:01:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-01T14:01:47.750Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-01T14:01:47.822Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582828) 
;

-- 2023-12-01T14:01:47.917Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721961
;

-- 2023-12-01T14:01:47.990Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721961)
;

-- UI Element: Geschäftspartner -> Adresse.Postadresse
-- Column: C_BPartner_Location.IsPostalAddress
-- 2023-12-01T14:02:57.953Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721961,0,222,1000034,621342,'F',TO_TIMESTAMP('2023-12-01 15:02:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Postadresse',55,0,0,TO_TIMESTAMP('2023-12-01 15:02:57','YYYY-MM-DD HH24:MI:SS'),100)
;

