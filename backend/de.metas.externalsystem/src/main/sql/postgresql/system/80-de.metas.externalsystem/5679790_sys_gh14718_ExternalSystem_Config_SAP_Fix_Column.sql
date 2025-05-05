-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> credentials.Post accounting documents path
-- Column: ExternalSystem_Config_SAP.Post_Acct_Documnets_Path
-- 2023-03-01T09:17:33.997Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615925
;

-- 2023-03-01T09:17:34.005Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712727
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Post accounting documents path
-- Column: ExternalSystem_Config_SAP.Post_Acct_Documnets_Path
-- 2023-03-01T09:17:34.018Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712727
;

-- 2023-03-01T09:17:34.025Z
DELETE FROM AD_Field WHERE AD_Field_ID=712727
;

-- 2023-03-01T09:17:34.031Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS Post_Acct_Documnets_Path')
;

-- Column: ExternalSystem_Config_SAP.Post_Acct_Documnets_Path
-- 2023-03-01T09:17:34.419Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586270
;

-- 2023-03-01T09:17:34.423Z
DELETE FROM AD_Column WHERE AD_Column_ID=586270
;

-- 2023-03-01T09:19:36.522Z
UPDATE AD_Element SET ColumnName='Post_Acct_Documents_Path',Updated=TO_TIMESTAMP('2023-03-01 11:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582113
;

-- 2023-03-01T09:19:36.524Z
UPDATE AD_Column SET ColumnName='Post_Acct_Documents_Path' WHERE AD_Element_ID=582113
;

-- 2023-03-01T09:19:36.526Z
UPDATE AD_Process_Para SET ColumnName='Post_Acct_Documents_Path' WHERE AD_Element_ID=582113
;

-- 2023-03-01T09:19:36.529Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582113,'en_US') 
;

-- Column: ExternalSystem_Config_SAP.Post_Acct_Documents_Path
-- 2023-03-01T10:34:46.102Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586274,582113,0,10,542238,'Post_Acct_Documents_Path',TO_TIMESTAMP('2023-03-01 12:34:45','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Post accounting documents path',0,0,TO_TIMESTAMP('2023-03-01 12:34:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-01T10:34:46.115Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586274 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-01T10:34:46.131Z
/* DDL */  select update_Column_Translation_From_AD_Element(582113) 
;

-- 2023-03-01T10:34:52.225Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN Post_Acct_Documents_Path VARCHAR(255)')
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Post accounting documents path
-- Column: ExternalSystem_Config_SAP.Post_Acct_Documents_Path
-- 2023-03-01T11:03:16.647Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586274,712770,0,546671,TO_TIMESTAMP('2023-03-01 13:03:16','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Post accounting documents path',TO_TIMESTAMP('2023-03-01 13:03:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T11:03:16.651Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712770 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T11:03:16.668Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582113)
;

-- 2023-03-01T11:03:16.693Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712770
;

-- 2023-03-01T11:03:16.712Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712770)
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> credentials.Post accounting documents path
-- Column: ExternalSystem_Config_SAP.Post_Acct_Documents_Path
-- 2023-03-01T11:03:57.419Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712770,0,546671,615966,550413,'F',TO_TIMESTAMP('2023-03-01 13:03:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Post accounting documents path',70,0,0,TO_TIMESTAMP('2023-03-01 13:03:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> credentials.Post accounting documents path
-- Column: ExternalSystem_Config_SAP.Post_Acct_Documents_Path
-- 2023-03-01T11:04:07.941Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-03-01 13:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615966
;

