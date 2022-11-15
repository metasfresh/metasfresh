-- 2022-11-15T11:03:12.206Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581674,0,'SFTP_Product_FileName_Pattern',TO_TIMESTAMP('2022-11-15 13:03:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','				','Y','SFTP Product File Name Pattern','SFTP Product File Name Pattern',TO_TIMESTAMP('2022-11-15 13:03:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T11:03:12.212Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581674 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-15T11:04:11.407Z
UPDATE AD_Element_Trl SET Description='Pattern used to find the file from which products are pulled from the SFTP server - the filename must match the given pattern. (If not provided, there is no constraint on the filename).',Updated=TO_TIMESTAMP('2022-11-15 13:04:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581674 AND AD_Language='en_US'
;

-- 2022-11-15T11:04:11.431Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581674,'en_US') 
;

-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-15T11:04:31.546Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584982,581674,0,10,542238,'SFTP_Product_FileName_Pattern',TO_TIMESTAMP('2022-11-15 13:04:31','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'				','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SFTP Product File Name Pattern',0,0,TO_TIMESTAMP('2022-11-15 13:04:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-15T11:04:31.551Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584982 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-15T11:04:31.556Z
/* DDL */  select update_Column_Translation_From_AD_Element(581674) 
;

-- 2022-11-15T11:04:32.588Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN SFTP_Product_FileName_Pattern VARCHAR(255)')
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP Product File Name Pattern
-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-15T11:11:34.586Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584982,708060,0,546647,0,TO_TIMESTAMP('2022-11-15 13:11:34','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem','				',0,'Y','Y','Y','N','N','N','N','N','SFTP Product File Name Pattern',0,130,0,1,1,TO_TIMESTAMP('2022-11-15 13:11:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T11:11:34.590Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-15T11:11:34.594Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581674) 
;

-- 2022-11-15T11:11:34.603Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708060
;

-- 2022-11-15T11:11:34.611Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708060)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP Product File Name Pattern
-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-15T11:12:02.474Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708060,0,546647,613469,549954,'F',TO_TIMESTAMP('2022-11-15 13:12:02','YYYY-MM-DD HH24:MI:SS'),100,'				','Y','N','N','Y','N','N','N',0,'SFTP Product File Name Pattern',120,0,0,TO_TIMESTAMP('2022-11-15 13:12:02','YYYY-MM-DD HH24:MI:SS'),100)
;

