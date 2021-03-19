-- Create Reference List with values
-- 2021-03-01T13:17:11.268Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541270,TO_TIMESTAMP('2021-03-01 15:17:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Ernährungsart',TO_TIMESTAMP('2021-03-01 15:17:09','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-03-01T13:17:11.497Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541270 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-01T13:18:02.178Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-01 15:18:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541270
;

-- 2021-03-01T13:18:27.595Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='DietType',Updated=TO_TIMESTAMP('2021-03-01 15:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Reference_ID=541270
;

-- 2021-03-01T13:21:10.732Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541270,542292,TO_TIMESTAMP('2021-03-01 15:21:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vegan',TO_TIMESTAMP('2021-03-01 15:21:10','YYYY-MM-DD HH24:MI:SS'),100,'Vegan','Vegan')
;

-- 2021-03-01T13:21:10.784Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542292 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-01T13:21:23.122Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-01 15:21:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542292
;

-- 2021-03-01T13:21:33.686Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-01 15:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542292
;

-- 2021-03-01T13:21:45.627Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-01 15:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542292
;

-- 2021-03-01T13:22:29.436Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541270,542293,TO_TIMESTAMP('2021-03-01 15:22:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vegetarisch',TO_TIMESTAMP('2021-03-01 15:22:28','YYYY-MM-DD HH24:MI:SS'),100,'Vegetarian','Vegetarian')
;

-- 2021-03-01T13:22:29.487Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542293 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-01T13:22:39.535Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-01 15:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542293
;

-- 2021-03-01T13:22:56.985Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Vegetarian',Updated=TO_TIMESTAMP('2021-03-01 15:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542293
;

-- 2021-03-01T13:23:06.652Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Vegetarian',Updated=TO_TIMESTAMP('2021-03-01 15:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=542293
;

-- Create AD_ELEMENT for the new column


-- 2021-03-01T13:25:41.386Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578790,0,'DietType',TO_TIMESTAMP('2021-03-01 15:25:40','YYYY-MM-DD HH24:MI:SS'),100,'Ernährungsart','D','Y','Ernährungsart','Ernährungsart',TO_TIMESTAMP('2021-03-01 15:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-01T13:25:41.441Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578790 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-01T13:26:18.811Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Diet Type', IsTranslated='Y', Name='Diet Type', PrintName='Diet Type',Updated=TO_TIMESTAMP('2021-03-01 15:26:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578790 AND AD_Language='en_GB'
;

-- 2021-03-01T13:26:18.924Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578790,'en_GB')
;

-- 2021-03-01T13:26:33.091Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Diet Type', IsTranslated='Y', Name='Diet Type', PrintName='Diet Type',Updated=TO_TIMESTAMP('2021-03-01 15:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578790 AND AD_Language='en_US'
;

-- 2021-03-01T13:26:33.143Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578790,'en_US')
;

-- 2021-03-01T13:26:48.587Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-01 15:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578790 AND AD_Language='de_DE'
;

-- 2021-03-01T13:26:48.641Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578790,'de_DE')
;

-- 2021-03-01T13:26:48.770Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578790,'de_DE')
;


-- Create Column in M_Product table
-- 2021-03-01T13:30:08.426Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573008,578790,0,17,541270,208,'DietType',TO_TIMESTAMP('2021-03-01 15:30:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Ernährungsart','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ernährungsart',0,0,TO_TIMESTAMP('2021-03-01 15:30:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-01T13:30:08.479Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573008 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-01T13:30:08.530Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578790)
;


--Modify Metasfresh Product Window
-- 2021-03-01T13:43:52.665Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573008,632804,578790,0,180,0,TO_TIMESTAMP('2021-03-01 15:43:51','YYYY-MM-DD HH24:MI:SS'),100,'Ernährungsart',0,'D',0,'Y','Y','Y','N','N','N','N','N','Ernährungsart',460,490,0,1,1,TO_TIMESTAMP('2021-03-01 15:43:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-01T13:43:52.721Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=632804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-01T13:43:52.777Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578790)
;

-- 2021-03-01T13:43:52.875Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=632804
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2021 metas GmbH
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

-- 2021-03-01T13:43:52.931Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(632804)
;

-- 2021-03-01T13:46:57.098Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000005,545003,TO_TIMESTAMP('2021-03-01 15:46:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','diet',30,TO_TIMESTAMP('2021-03-01 15:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-01T13:47:12.959Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2021-03-01 15:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540582
;

-- 2021-03-01T13:48:00.486Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,632804,0,180,545003,578402,'F',TO_TIMESTAMP('2021-03-01 15:47:59','YYYY-MM-DD HH24:MI:SS'),100,'Ernährungsart','Y','N','N','Y','N','N','N',0,'Ernährungsart',10,0,0,TO_TIMESTAMP('2021-03-01 15:47:59','YYYY-MM-DD HH24:MI:SS'),100)
;
