-- 2023-01-05T09:09:13.160Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581907,0,'InternalPriority',TO_TIMESTAMP('2023-01-05 11:09:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Internal Priority','Internal Priority',TO_TIMESTAMP('2023-01-05 11:09:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-05T09:09:13.166Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581907 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project.InternalPriority
-- 2023-01-05T09:10:17.316Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585449,581907,0,17,154,203,'InternalPriority',TO_TIMESTAMP('2023-01-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Internal Priority',0,0,TO_TIMESTAMP('2023-01-05 11:10:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-05T09:10:17.319Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585449 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-05T09:10:17.348Z
/* DDL */  select update_Column_Translation_From_AD_Element(581907) 
;

-- 2023-01-05T09:10:18.251Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN InternalPriority CHAR(1) DEFAULT 5')
;

-- Field: Budget Project -> Projekt -> Internal Priority
-- Column: C_Project.InternalPriority
-- 2023-01-05T09:12:46.573Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585449,710127,0,546269,0,TO_TIMESTAMP('2023-01-05 11:12:46','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Internal Priority',0,390,0,1,1,TO_TIMESTAMP('2023-01-05 11:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-05T09:12:46.577Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710127 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-05T09:12:46.580Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581907) 
;

-- 2023-01-05T09:12:46.592Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710127
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- 2023-01-05T09:12:46.596Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710127)
;

-- UI Element: Budget Project -> Projekt.Internal Priority
-- Column: C_Project.InternalPriority
-- 2023-01-05T09:13:02.702Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710127,0,546269,614630,549125,'F',TO_TIMESTAMP('2023-01-05 11:13:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Internal Priority',65,0,0,TO_TIMESTAMP('2023-01-05 11:13:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Work Order Project -> Projekt -> Internal Priority
-- Column: C_Project.InternalPriority
-- 2023-01-05T09:14:14.376Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585449,710128,0,546289,0,TO_TIMESTAMP('2023-01-05 11:14:14','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Internal Priority',0,440,0,1,1,TO_TIMESTAMP('2023-01-05 11:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-05T09:14:14.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710128 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-05T09:14:14.380Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581907) 
;

-- 2023-01-05T09:14:14.382Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710128
;

-- 2023-01-05T09:14:14.384Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710128)
;

-- UI Element: Work Order Project -> Projekt.Internal Priority
-- Column: C_Project.InternalPriority
-- 2023-01-05T09:14:28.673Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710128,0,546289,614631,549191,'F',TO_TIMESTAMP('2023-01-05 11:14:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Internal Priority',55,0,0,TO_TIMESTAMP('2023-01-05 11:14:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Parent Project
-- 2023-01-11T08:29:03.737Z
UPDATE AD_Val_Rule SET Code='C_Project.C_Project_ID NOT IN (SELECT DISTINCT pp.C_Project_ID FROM c_project pp WHERE pp.C_Project_Parent_ID = @C_Project_ID@) AND C_Project.C_Project_ID != @C_Project_ID@ AND (''@ProjectCategory@'' <> ''W'' OR C_Project.ProjectCategory = ''B'')',Updated=TO_TIMESTAMP('2023-01-11 10:29:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540581
;

-- 2023-01-19T07:33:47.059Z
UPDATE AD_Element_Trl SET Name='Interne Priorität', PrintName='Interne Priorität',Updated=TO_TIMESTAMP('2023-01-19 09:33:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581907 AND AD_Language='de_CH'
;

-- 2023-01-19T07:33:47.092Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581907,'de_CH')
;

-- 2023-01-19T07:33:56.614Z
UPDATE AD_Element_Trl SET Name='Interne Priorität', PrintName='Interne Priorität',Updated=TO_TIMESTAMP('2023-01-19 09:33:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581907 AND AD_Language='de_DE'
;

-- 2023-01-19T07:33:56.615Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581907,'de_DE')
;

-- 2023-01-19T07:33:56.631Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581907,'de_DE')
;

-- 2023-01-19T07:33:56.633Z
UPDATE AD_Column SET ColumnName='InternalPriority', Name='Interne Priorität', Description=NULL, Help=NULL WHERE AD_Element_ID=581907
;

-- 2022-12-22T13:52:18.273Z
INSERT INTO AD_JavaClass (AD_Client_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,EntityType,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540075,540040,0,'de.metas.document.sequenceno.ExternalProjectRefSequenceNoProvider',TO_TIMESTAMP('2022-12-22 15:52:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','ExternalProjectRefSequenceNoProvider',TO_TIMESTAMP('2022-12-22 15:52:18','YYYY-MM-DD HH24:MI:SS'),100)
;
