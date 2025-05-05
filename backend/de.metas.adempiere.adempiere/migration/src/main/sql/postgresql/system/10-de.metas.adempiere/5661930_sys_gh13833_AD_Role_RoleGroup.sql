
-- 2022-10-25T12:17:33.312Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581606,0,'Role_Group',TO_TIMESTAMP('2022-10-25 14:17:33','YYYY-MM-DD HH24:MI:SS'),100,'Can be used to mark roles; the respective group is added to the logged user''s CTX and can be used e.g. to determine if particular fields shall be read-only.','D','Y','Role group','Role group',TO_TIMESTAMP('2022-10-25 14:17:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T12:17:33.320Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581606 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Role_Group
-- 2022-10-25T12:17:45.014Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 14:17:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581606 AND AD_Language='en_US'
;

-- 2022-10-25T12:17:45.048Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581606,'en_US') 
;

-- Element: Role_Group
-- 2022-10-25T12:18:18.367Z
UPDATE AD_Element_Trl SET Description='Kann verwendet werden, um Rollen zu markieren; die jeweilige Gruppe wird dem CTX des angemeldeten Benutzers hinzugefügt und kann z. B. verwendet werden, um festzulegen, ob bestimmte Felder schreibgeschützt sein sollen.', IsTranslated='Y', Name='Rollen-Gruppe', PrintName='Rollen-Gruppe',Updated=TO_TIMESTAMP('2022-10-25 14:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581606 AND AD_Language='de_CH'
;

-- 2022-10-25T12:18:18.372Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581606,'de_CH') 
;

-- Element: Role_Group
-- 2022-10-25T12:18:34.062Z
UPDATE AD_Element_Trl SET Description='Kann verwendet werden, um Rollen zu markieren; die jeweilige Gruppe wird dem CTX des angemeldeten Benutzers hinzugefügt und kann z. B. verwendet werden, um festzulegen, ob bestimmte Felder schreibgeschützt sein sollen', IsTranslated='Y', Name='Rollen-Gruppe', PrintName='Rollen-Gruppe',Updated=TO_TIMESTAMP('2022-10-25 14:18:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581606 AND AD_Language='de_DE'
;

-- 2022-10-25T12:18:34.066Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581606,'de_DE') 
;

-- 2022-10-25T12:18:34.069Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581606,'de_DE') 
;

-- Name: Role_Group
-- 2022-10-25T12:19:41.796Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541681,TO_TIMESTAMP('2022-10-25 14:19:41','YYYY-MM-DD HH24:MI:SS'),100,'Role_Group','D','Y','N','Role_Group',TO_TIMESTAMP('2022-10-25 14:19:41','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-10-25T12:19:41.798Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541681 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Role_Group
-- Value: Accounting
-- ValueName: Accounting
-- 2022-10-25T12:20:26.521Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541681,543318,TO_TIMESTAMP('2022-10-25 14:20:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Buchhaltung',TO_TIMESTAMP('2022-10-25 14:20:26','YYYY-MM-DD HH24:MI:SS'),100,'Accounting','Accounting')
;

-- Reference: Role_Group
-- Value: Accounting
-- ValueName: Accounting
-- 2022-10-27T09:54:58.386Z
UPDATE AD_Ref_List SET Name='Accounting',Updated=TO_TIMESTAMP('2022-10-27 11:54:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543318
;



-- 2022-10-25T12:20:26.524Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543318 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Role_Group -> Accounting_Accounting
-- 2022-10-25T12:20:29.951Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 14:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543318
;

-- Reference Item: Role_Group -> Accounting_Accounting
-- 2022-10-25T12:20:32.800Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 14:20:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543318
;

-- Reference Item: Role_Group -> Accounting_Accounting
-- 2022-10-25T12:20:39.202Z
UPDATE AD_Ref_List_Trl SET Name='Accounting',Updated=TO_TIMESTAMP('2022-10-25 14:20:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543318
;

-- Column: AD_Role.Role_Group
-- 2022-10-25T12:21:18.206Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584804,581606,0,17,541681,156,'Role_Group',TO_TIMESTAMP('2022-10-25 14:21:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Kann verwendet werden, um Rollen zu markieren; die jeweilige Gruppe wird dem CTX des angemeldeten Benutzers hinzugefügt und kann z. B. verwendet werden, um festzulegen, ob bestimmte Felder schreibgeschützt sein sollen','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rollen-Gruppe',0,0,TO_TIMESTAMP('2022-10-25 14:21:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T12:21:18.209Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584804 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T12:21:18.216Z
/* DDL */  select update_Column_Translation_From_AD_Element(581606) 
;

-- 2022-10-25T12:21:24.627Z
/* DDL */ SELECT public.db_alter_table('AD_Role','ALTER TABLE public.AD_Role ADD COLUMN Role_Group VARCHAR(20)')
;

-- Field: Rolle - Verwaltung(111,D) -> Rolle(119,D) -> Rollen-Gruppe
-- Column: AD_Role.Role_Group
-- 2022-10-25T12:21:52.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584804,707861,0,119,0,TO_TIMESTAMP('2022-10-25 14:21:52','YYYY-MM-DD HH24:MI:SS'),100,'Kann verwendet werden, um Rollen zu markieren; die jeweilige Gruppe wird dem CTX des angemeldeten Benutzers hinzugefügt und kann z. B. verwendet werden, um festzulegen, ob bestimmte Felder schreibgeschützt sein sollen',0,'U',0,'Y','Y','Y','N','N','N','N','N','Rollen-Gruppe',0,470,0,1,1,TO_TIMESTAMP('2022-10-25 14:21:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T12:21:52.680Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T12:21:52.685Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581606) 
;

-- 2022-10-25T12:21:52.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707861
;

-- 2022-10-25T12:21:52.703Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707861)
;

-- UI Element: Rolle - Verwaltung(111,D) -> Rolle(119,D) -> main -> 20 -> org.Role_Group_Rollen-Gruppe
-- Column: AD_Role.Role_Group
-- 2022-10-25T12:23:29.696Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707861,0,119,540245,613303,'F',TO_TIMESTAMP('2022-10-25 14:23:29','YYYY-MM-DD HH24:MI:SS'),100,'Kann verwendet werden, um Rollen zu markieren; die jeweilige Gruppe wird dem CTX des angemeldeten Benutzers hinzugefügt und kann z. B. verwendet werden, um festzulegen, ob bestimmte Felder schreibgeschützt sein sollen','Y','N','N','Y','N','N','N',0,'Role_Group_Rollen-Gruppe',10,0,0,TO_TIMESTAMP('2022-10-25 14:23:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: Role_Group
-- 2022-10-25T12:40:26.185Z
UPDATE AD_Element_Trl SET Description='Kann verwendet werden, um Rollen zu markieren; wenn gesetzt die jeweilige Gruppe wird dem #AD_Role_Group Kontextwert des angemeldeten Benutzers hinzugefügt und kann z. B. verwendet werden, um festzulegen, ob bestimmte Felder schreibgeschützt sein sollen.',Updated=TO_TIMESTAMP('2022-10-25 14:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581606 AND AD_Language='de_CH'
;

-- 2022-10-25T12:40:26.189Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581606,'de_CH') 
;

-- Element: Role_Group
-- 2022-10-25T12:40:31.423Z
UPDATE AD_Element_Trl SET Description='Kann verwendet werden, um Rollen zu markieren; wenn gesetzt die jeweilige Gruppe wird dem #AD_Role_Group Kontextwert des angemeldeten Benutzers hinzugefügt und kann z. B. verwendet werden, um festzulegen, ob bestimmte Felder schreibgeschützt sein sollen.',Updated=TO_TIMESTAMP('2022-10-25 14:40:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581606 AND AD_Language='de_DE'
;

-- 2022-10-25T12:40:31.429Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581606,'de_DE') 
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- 2022-10-25T12:40:31.432Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581606,'de_DE') 
;

-- Element: Role_Group
-- 2022-10-25T12:40:46.453Z
UPDATE AD_Element_Trl SET Description='Can be used to mark roles; the respective group is added to the logged user''s #AD_Role_Group context value and can be used e.g. to determine if particular fields shall be read-only.',Updated=TO_TIMESTAMP('2022-10-25 14:40:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581606 AND AD_Language='en_US'
;

-- 2022-10-25T12:40:46.458Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581606,'en_US') 
;