-- 2023-03-30T08:12:54.338Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582193,0,'WODueDate',TO_TIMESTAMP('2023-03-30 11:12:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Due date','Due date',TO_TIMESTAMP('2023-03-30 11:12:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T08:12:54.347Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582193 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Step.WODueDate
-- 2023-03-30T08:13:15.586Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586375,582193,0,16,542159,'WODueDate',TO_TIMESTAMP('2023-03-30 11:13:15','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Due date',0,0,TO_TIMESTAMP('2023-03-30 11:13:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-30T08:13:15.588Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586375 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-30T08:13:15.615Z
/* DDL */  select update_Column_Translation_From_AD_Element(582193) 
;

-- 2023-03-30T08:13:18.353Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN WODueDate TIMESTAMP WITH TIME ZONE')
;

-- 2023-03-30T08:14:02.197Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582194,0,'IsManuallyLocked',TO_TIMESTAMP('2023-03-30 11:14:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Manually locked','Manually locked',TO_TIMESTAMP('2023-03-30 11:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T08:14:02.198Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582194 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Step.IsManuallyLocked
-- 2023-03-30T08:14:14.396Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586376,582194,0,20,542159,'IsManuallyLocked',TO_TIMESTAMP('2023-03-30 11:14:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Manually locked',0,0,TO_TIMESTAMP('2023-03-30 11:14:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-30T08:14:14.398Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586376 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-30T08:14:14.401Z
/* DDL */  select update_Column_Translation_From_AD_Element(582194) 
;

-- 2023-03-30T08:14:16.721Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN IsManuallyLocked CHAR(1) DEFAULT ''N'' CHECK (IsManuallyLocked IN (''Y'',''N'')) NOT NULL')
;

-- Field: Prüf Projekt_OLD -> Prüfschritt -> Due date
-- Column: C_Project_WO_Step.WODueDate
-- 2023-03-30T08:14:56.119Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586375,713563,0,546290,TO_TIMESTAMP('2023-03-30 11:14:55','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Due date',TO_TIMESTAMP('2023-03-30 11:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T08:14:56.121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=713563 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-30T08:14:56.123Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582193) 
;

-- 2023-03-30T08:14:56.132Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713563
;

-- 2023-03-30T08:14:56.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713563)
;

-- Field: Prüf Projekt_OLD -> Prüfschritt -> Manually locked
-- Column: C_Project_WO_Step.IsManuallyLocked
-- 2023-03-30T08:16:15.202Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586376,713564,0,546290,TO_TIMESTAMP('2023-03-30 11:16:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Manually locked',TO_TIMESTAMP('2023-03-30 11:16:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T08:16:15.205Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=713564 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-30T08:16:15.207Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582194) 
;

-- 2023-03-30T08:16:15.210Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713564
;

-- 2023-03-30T08:16:15.215Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713564)
;

-- 2023-03-30T08:17:24.032Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545973,550515,TO_TIMESTAMP('2023-03-30 11:17:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',5,TO_TIMESTAMP('2023-03-30 11:17:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt_OLD -> Prüfschritt.Manually locked
-- Column: C_Project_WO_Step.IsManuallyLocked
-- 2023-03-30T08:17:36.309Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,713564,0,546290,616483,550515,'F',TO_TIMESTAMP('2023-03-30 11:17:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Manually locked',10,0,0,TO_TIMESTAMP('2023-03-30 11:17:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüf Projekt_OLD -> Prüfschritt.Due date
-- Column: C_Project_WO_Step.WODueDate
-- 2023-03-30T08:18:02.991Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,713563,0,546290,616484,549198,'F',TO_TIMESTAMP('2023-03-30 11:18:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Due date',30,0,0,TO_TIMESTAMP('2023-03-30 11:18:02','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2023-04-03T07:41:05.650Z
UPDATE AD_Element_Trl SET Description='Fälligkeitsdatum für Prüfschritt', Name='Fälligkeitsdatum', PrintName='Fälligkeitsdatum',Updated=TO_TIMESTAMP('2023-04-03 10:41:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582193 AND AD_Language='de_CH'
;

-- 2023-04-03T07:41:05.753Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582193,'de_CH')
;

-- 2023-04-03T07:41:23.943Z
UPDATE AD_Element_Trl SET Description='Fälligkeitsdatum für Prüfschritt', Name='Fälligkeitsdatum', PrintName='Fälligkeitsdatum',Updated=TO_TIMESTAMP('2023-04-03 10:41:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582193 AND AD_Language='de_DE'
;

-- 2023-04-03T07:41:23.945Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582193,'de_DE')
;

-- 2023-04-03T07:41:23.977Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582193,'de_DE')
;

-- 2023-04-03T07:41:23.984Z
UPDATE AD_Column SET ColumnName='WODueDate', Name='Fälligkeitsdatum', Description='Fälligkeitsdatum für Prüfschritt', Help=NULL WHERE AD_Element_ID=582193
;

-- 2023-04-03T07:41:23.986Z
UPDATE AD_Process_Para SET ColumnName='WODueDate', Name='Fälligkeitsdatum', Description='Fälligkeitsdatum für Prüfschritt', Help=NULL, AD_Element_ID=582193 WHERE UPPER(ColumnName)='WODUEDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-04-03T07:41:23.990Z
UPDATE AD_Process_Para SET ColumnName='WODueDate', Name='Fälligkeitsdatum', Description='Fälligkeitsdatum für Prüfschritt', Help=NULL WHERE AD_Element_ID=582193 AND IsCentrallyMaintained='Y'
;

-- 2023-04-03T07:41:23.991Z
UPDATE AD_Field SET Name='Fälligkeitsdatum', Description='Fälligkeitsdatum für Prüfschritt', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582193) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582193)
;

-- 2023-04-03T07:41:24.071Z
UPDATE AD_PrintFormatItem pi SET PrintName='Fälligkeitsdatum', Name='Fälligkeitsdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582193)
;

-- 2023-04-03T07:41:24.074Z
UPDATE AD_Tab SET Name='Fälligkeitsdatum', Description='Fälligkeitsdatum für Prüfschritt', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582193
;

-- 2023-04-03T07:41:24.076Z
UPDATE AD_WINDOW SET Name='Fälligkeitsdatum', Description='Fälligkeitsdatum für Prüfschritt', Help=NULL WHERE AD_Element_ID = 582193
;

-- 2023-04-03T07:41:24.078Z
UPDATE AD_Menu SET   Name = 'Fälligkeitsdatum', Description = 'Fälligkeitsdatum für Prüfschritt', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582193
;

-- 2023-04-03T07:41:37.630Z
UPDATE AD_Element_Trl SET Description='Fälligkeitsdatum für Prüfschritt', Name='Fälligkeitsdatum', PrintName='Fälligkeitsdatum',Updated=TO_TIMESTAMP('2023-04-03 10:41:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582193 AND AD_Language='nl_NL'
;

-- 2023-04-03T07:41:37.631Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582193,'nl_NL')
;

-- 2023-04-03T07:41:53.651Z
UPDATE AD_Element_Trl SET Description='Due date for test step', Name='Due Date', PrintName='Due Date',Updated=TO_TIMESTAMP('2023-04-03 10:41:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582193 AND AD_Language='en_US'
;

-- 2023-04-03T07:41:53.653Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582193,'en_US')
;

-- 2023-04-03T07:43:12.784Z
UPDATE AD_Element_Trl SET Description='Start date and end date cannot be moved.',Updated=TO_TIMESTAMP('2023-04-03 10:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582194 AND AD_Language='en_US'
;

-- 2023-04-03T07:43:12.785Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582194,'en_US')
;

-- 2023-04-03T07:44:33.697Z
UPDATE AD_Element_Trl SET Description='Startdatum und Enddatum können nicht verschoben werden.', Name='Manuell gesperrt', PrintName='Manuell gesperrt',Updated=TO_TIMESTAMP('2023-04-03 10:44:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582194 AND AD_Language='de_CH'
;

-- 2023-04-03T07:44:33.700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582194,'de_CH')
;

-- 2023-04-03T07:44:43.075Z
UPDATE AD_Element_Trl SET Description='Startdatum und Enddatum können nicht verschoben werden.', Name='Manuell gesperrt', PrintName='Manuell gesperrt',Updated=TO_TIMESTAMP('2023-04-03 10:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582194 AND AD_Language='de_DE'
;

-- 2023-04-03T07:44:43.076Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582194,'de_DE')
;

-- 2023-04-03T07:44:43.091Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582194,'de_DE')
;

-- 2023-04-03T07:44:43.095Z
UPDATE AD_Column SET ColumnName='IsManuallyLocked', Name='Manuell gesperrt', Description='Startdatum und Enddatum können nicht verschoben werden.', Help=NULL WHERE AD_Element_ID=582194
;

-- 2023-04-03T07:44:43.097Z
UPDATE AD_Process_Para SET ColumnName='IsManuallyLocked', Name='Manuell gesperrt', Description='Startdatum und Enddatum können nicht verschoben werden.', Help=NULL, AD_Element_ID=582194 WHERE UPPER(ColumnName)='ISMANUALLYLOCKED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-04-03T07:44:43.099Z
UPDATE AD_Process_Para SET ColumnName='IsManuallyLocked', Name='Manuell gesperrt', Description='Startdatum und Enddatum können nicht verschoben werden.', Help=NULL WHERE AD_Element_ID=582194 AND IsCentrallyMaintained='Y'
;

-- 2023-04-03T07:44:43.100Z
UPDATE AD_Field SET Name='Manuell gesperrt', Description='Startdatum und Enddatum können nicht verschoben werden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582194) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582194)
;

-- 2023-04-03T07:44:43.136Z
UPDATE AD_PrintFormatItem pi SET PrintName='Manuell gesperrt', Name='Manuell gesperrt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582194)
;

-- 2023-04-03T07:44:43.138Z
UPDATE AD_Tab SET Name='Manuell gesperrt', Description='Startdatum und Enddatum können nicht verschoben werden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582194
;

-- 2023-04-03T07:44:43.140Z
UPDATE AD_WINDOW SET Name='Manuell gesperrt', Description='Startdatum und Enddatum können nicht verschoben werden.', Help=NULL WHERE AD_Element_ID = 582194
;

-- 2023-04-03T07:44:43.142Z
UPDATE AD_Menu SET   Name = 'Manuell gesperrt', Description = 'Startdatum und Enddatum können nicht verschoben werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582194
;

-- 2023-04-03T07:44:53.942Z
UPDATE AD_Element_Trl SET Description='Startdatum und Enddatum können nicht verschoben werden.', Name='Manuell gesperrt', PrintName='Manuell gesperrt',Updated=TO_TIMESTAMP('2023-04-03 10:44:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582194 AND AD_Language='nl_NL'
;

-- 2023-04-03T07:44:53.944Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582194,'nl_NL')
;


-- Column: C_Project_WO_Step.DateEnd
-- 2023-04-04T08:20:16.311Z
UPDATE AD_Column SET ReadOnlyLogic='@IsManuallyLocked@=Y',Updated=TO_TIMESTAMP('2023-04-04 11:20:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583255
;

/*
 * #%L
 * de.metas.business
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

-- 2023-04-04T08:20:20.684Z
INSERT INTO t_alter_column values('c_project_wo_step','DateEnd','TIMESTAMP WITH TIME ZONE',null,null)
;

-- Column: C_Project_WO_Step.DateStart
-- 2023-04-04T08:20:36.001Z
UPDATE AD_Column SET ReadOnlyLogic='@IsManuallyLocked@=Y',Updated=TO_TIMESTAMP('2023-04-04 11:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583254
;

-- 2023-04-04T08:20:38.858Z
INSERT INTO t_alter_column values('c_project_wo_step','DateStart','TIMESTAMP WITH TIME ZONE',null,null)
;

