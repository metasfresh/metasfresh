/*
 * #%L
 * de.metas.handlingunits.base
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

-- Value: MovingNotAllowed
-- 2023-02-14T15:52:28.088Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545237,0,TO_TIMESTAMP('2023-02-14 17:52:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Uncleared HUs are not allowed to be moved to the respective Locator!','E',TO_TIMESTAMP('2023-02-14 17:52:27','YYYY-MM-DD HH24:MI:SS'),100,'MovingNotAllowed')
;

-- 2023-02-14T15:52:28.096Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545237 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: MovingNotAllowed
-- 2023-02-14T15:54:05.899Z
UPDATE AD_Message_Trl SET MsgText='Nur freigegebene HUs dürfen in den jeweiligen Lagerort verschoben werden!',Updated=TO_TIMESTAMP('2023-02-14 17:54:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545237
;

-- Value: MovingNotAllowed
-- 2023-02-14T15:54:08.760Z
UPDATE AD_Message_Trl SET MsgText='Nur freigegebene HUs dürfen in den jeweiligen Lagerort verschoben werden!',Updated=TO_TIMESTAMP('2023-02-14 17:54:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545237
;

-- Value: MovingNotAllowed
-- 2023-02-14T15:54:11.484Z
UPDATE AD_Message_Trl SET MsgText='Nur freigegebene HUs dürfen in den jeweiligen Lagerort verschoben werden!',Updated=TO_TIMESTAMP('2023-02-14 17:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545237
;

-- Value: MovingNotAllowed
-- 2023-02-14T15:54:13.116Z
UPDATE AD_Message_Trl SET MsgText='Nur freigegebene HUs dürfen in den jeweiligen Lagerort verschoben werden!',Updated=TO_TIMESTAMP('2023-02-14 17:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545237
;

-- 2023-02-14T16:01:41.178Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582067,0,'OnlyClearedHUs',TO_TIMESTAMP('2023-02-14 18:01:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Only cleared HUs','Only cleared HUs',TO_TIMESTAMP('2023-02-14 18:01:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-14T16:01:41.182Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582067 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: OnlyClearedHUs
-- 2023-02-14T16:01:55.672Z
UPDATE AD_Element_Trl SET Name='Nur freigegebene HUs', PrintName='Nur freigegebene HUs',Updated=TO_TIMESTAMP('2023-02-14 18:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582067 AND AD_Language='de_CH'
;

-- 2023-02-14T16:01:55.702Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582067,'de_CH') 
;

-- Element: OnlyClearedHUs
-- 2023-02-14T16:01:58.295Z
UPDATE AD_Element_Trl SET Name='Nur freigegebene HUs', PrintName='Nur freigegebene HUs',Updated=TO_TIMESTAMP('2023-02-14 18:01:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582067 AND AD_Language='de_DE'
;

-- 2023-02-14T16:01:58.298Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582067,'de_DE') 
;

-- 2023-02-14T16:01:58.305Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582067,'de_DE') 
;

-- Element: OnlyClearedHUs
-- 2023-02-14T16:02:01.320Z
UPDATE AD_Element_Trl SET Name='Nur freigegebene HUs', PrintName='Nur freigegebene HUs',Updated=TO_TIMESTAMP('2023-02-14 18:02:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582067 AND AD_Language='fr_CH'
;

-- 2023-02-14T16:02:01.323Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582067,'fr_CH') 
;

-- Element: OnlyClearedHUs
-- 2023-02-14T16:02:03.359Z
UPDATE AD_Element_Trl SET Name='Nur freigegebene HUs', PrintName='Nur freigegebene HUs',Updated=TO_TIMESTAMP('2023-02-14 18:02:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582067 AND AD_Language='nl_NL'
;

-- 2023-02-14T16:02:03.362Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582067,'nl_NL') 
;

-- Element: OnlyClearedHUs
-- 2023-02-14T16:04:18.144Z
UPDATE AD_Element_Trl SET Description='Nur freigegebene HUs können zum Lagerort verschoben werden',Updated=TO_TIMESTAMP('2023-02-14 18:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582067 AND AD_Language='de_CH'
;

-- 2023-02-14T16:04:18.146Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582067,'de_CH') 
;

-- Element: OnlyClearedHUs
-- 2023-02-14T16:04:19.993Z
UPDATE AD_Element_Trl SET Description='Nur freigegebene HUs können zum Lagerort verschoben werden',Updated=TO_TIMESTAMP('2023-02-14 18:04:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582067 AND AD_Language='de_DE'
;

-- 2023-02-14T16:04:19.996Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582067,'de_DE') 
;

-- 2023-02-14T16:04:19.998Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582067,'de_DE') 
;

-- Element: OnlyClearedHUs
-- 2023-02-14T16:04:22.246Z
UPDATE AD_Element_Trl SET Description='Nur freigegebene HUs können zum Lagerort verschoben werden',Updated=TO_TIMESTAMP('2023-02-14 18:04:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582067 AND AD_Language='fr_CH'
;

-- 2023-02-14T16:04:22.248Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582067,'fr_CH') 
;

-- Element: OnlyClearedHUs
-- 2023-02-14T16:04:24.300Z
UPDATE AD_Element_Trl SET Description='Nur freigegebene HUs können zum Lagerort verschoben werden',Updated=TO_TIMESTAMP('2023-02-14 18:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582067 AND AD_Language='nl_NL'
;

-- 2023-02-14T16:04:24.301Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582067,'nl_NL') 
;

-- Element: OnlyClearedHUs
-- 2023-02-14T16:04:39.723Z
UPDATE AD_Element_Trl SET Description='Allow only cleared HUs to be moved to Locator',Updated=TO_TIMESTAMP('2023-02-14 18:04:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582067 AND AD_Language='en_US'
;

-- 2023-02-14T16:04:39.724Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582067,'en_US') 
;

-- Column: M_Locator.OnlyClearedHUs
-- Column: M_Locator.OnlyClearedHUs
-- 2023-02-14T16:08:03.053Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586047,582067,0,20,207,'OnlyClearedHUs',TO_TIMESTAMP('2023-02-14 18:08:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Nur freigegebene HUs können zum Lagerort verschoben werden','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Nur freigegebene HUs',0,0,TO_TIMESTAMP('2023-02-14 18:08:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-14T16:08:03.055Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586047 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-14T16:08:03.059Z
/* DDL */  select update_Column_Translation_From_AD_Element(582067) 
;

-- 2023-02-14T16:08:05.193Z
/* DDL */ SELECT public.db_alter_table('M_Locator','ALTER TABLE public.M_Locator ADD COLUMN OnlyClearedHUs CHAR(1) DEFAULT ''N'' CHECK (OnlyClearedHUs IN (''Y'',''N'')) NOT NULL')
;

-- Field: Lager und Lagerort -> Lagerort -> Nur freigegebene HUs
-- Column: M_Locator.OnlyClearedHUs
-- Field: Lager und Lagerort(139,D) -> Lagerort(178,D) -> Nur freigegebene HUs
-- Column: M_Locator.OnlyClearedHUs
-- 2023-02-14T16:12:56.533Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586047,712527,0,178,TO_TIMESTAMP('2023-02-14 18:12:56','YYYY-MM-DD HH24:MI:SS'),100,'Nur freigegebene HUs können zum Lagerort verschoben werden',1,'D','Y','N','N','N','N','N','N','N','Nur freigegebene HUs',TO_TIMESTAMP('2023-02-14 18:12:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-14T16:12:56.540Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712527 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-14T16:12:56.544Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582067) 
;

-- 2023-02-14T16:12:56.565Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712527
;

-- 2023-02-14T16:12:56.571Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712527)
;

-- UI Element: Lager und Lagerort -> Lagerort.Nur freigegebene HUs
-- Column: M_Locator.OnlyClearedHUs
-- UI Element: Lager und Lagerort(139,D) -> Lagerort(178,D) -> main -> 10 -> default.Nur freigegebene HUs
-- Column: M_Locator.OnlyClearedHUs
-- 2023-02-14T16:14:47.440Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712527,0,178,615818,541165,'F',TO_TIMESTAMP('2023-02-14 18:14:47','YYYY-MM-DD HH24:MI:SS'),100,'Nur freigegebene HUs können zum Lagerort verschoben werden','Y','N','N','Y','N','N','N',0,'Nur freigegebene HUs',95,0,0,TO_TIMESTAMP('2023-02-14 18:14:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lager und Lagerort -> Lagerort.Nur freigegebene HUs
-- Column: M_Locator.OnlyClearedHUs
-- UI Element: Lager und Lagerort(139,D) -> Lagerort(178,D) -> main -> 10 -> default.Nur freigegebene HUs
-- Column: M_Locator.OnlyClearedHUs
-- 2023-02-14T16:16:39.964Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-02-14 18:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615818
;

-- UI Element: Lager und Lagerort -> Lagerort.Sektion
-- Column: M_Locator.AD_Org_ID
-- UI Element: Lager und Lagerort(139,D) -> Lagerort(178,D) -> main -> 10 -> default.Sektion
-- Column: M_Locator.AD_Org_ID
-- 2023-02-14T16:16:39.974Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-02-14 18:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548716
;

-- Value: MovingNotAllowed
-- 2023-02-14T18:44:39.620Z
UPDATE AD_Message SET MsgText='Nur freigegebene HUs dürfen in den jeweiligen Lagerort verschoben werden!',Updated=TO_TIMESTAMP('2023-02-14 20:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545237
;
