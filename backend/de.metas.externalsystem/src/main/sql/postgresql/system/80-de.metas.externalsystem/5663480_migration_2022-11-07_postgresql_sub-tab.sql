-- Tab: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh
-- Table: ExternalSystem_Config_Metasfresh
-- 2022-11-07T12:19:41.376Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584878,581641,0,546666,542253,541024,'Y',TO_TIMESTAMP('2022-11-07 14:19:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_Metasfresh','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_Metasfresh','N',80,1,TO_TIMESTAMP('2022-11-07 14:19:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:19:41.382Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546666 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-07T12:19:41.389Z
/* DDL */  select update_tab_translation_from_ad_element(581641) 
;

-- 2022-11-07T12:19:41.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546666)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_Metasfresh.AD_Client_ID
-- 2022-11-07T12:19:59.629Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584869,707974,0,546666,TO_TIMESTAMP('2022-11-07 14:19:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-11-07 14:19:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:19:59.633Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:19:59.637Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-07T12:20:01.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707974
;

-- 2022-11-07T12:20:01.135Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707974)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_Metasfresh.AD_Org_ID
-- 2022-11-07T12:20:01.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584870,707975,0,546666,TO_TIMESTAMP('2022-11-07 14:20:01','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-11-07 14:20:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:01.272Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:01.274Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-07T12:20:01.840Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707975
;

-- 2022-11-07T12:20:01.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707975)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_Metasfresh.IsActive
-- 2022-11-07T12:20:02.136Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584873,707976,0,546666,TO_TIMESTAMP('2022-11-07 14:20:01','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-11-07 14:20:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:02.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:02.139Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-07T12:20:03.785Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707976
;

-- 2022-11-07T12:20:03.785Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707976)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystem_Config_Metasfresh_ID
-- 2022-11-07T12:20:04.117Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584876,707977,0,546666,TO_TIMESTAMP('2022-11-07 14:20:03','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','ExternalSystem_Config_Metasfresh',TO_TIMESTAMP('2022-11-07 14:20:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:04.120Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:04.122Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581641) 
;

-- 2022-11-07T12:20:04.127Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707977
;

-- 2022-11-07T12:20:04.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707977)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> User Authentication Token
-- Column: ExternalSystem_Config_Metasfresh.CamelHttpResourceAuthKey
-- 2022-11-07T12:20:04.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584877,707978,0,546666,TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','User Authentication Token ',TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:04.230Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:04.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579512) 
;

-- 2022-11-07T12:20:04.237Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707978
;

-- 2022-11-07T12:20:04.238Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707978)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> External System Config
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystem_Config_ID
-- 2022-11-07T12:20:04.330Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584878,707979,0,546666,TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:04.332Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:04.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2022-11-07T12:20:04.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707979
;

-- 2022-11-07T12:20:04.345Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707979)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Suchschlüssel
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystemValue
-- 2022-11-07T12:20:04.453Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584879,707980,0,546666,TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:04.454Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:04.456Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788) 
;

-- 2022-11-07T12:20:04.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707980
;

-- 2022-11-07T12:20:04.463Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707980)
;

-- Tab: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem)
-- UI Section: main
-- 2022-11-07T12:20:20.581Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546666,545291,TO_TIMESTAMP('2022-11-07 14:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-07 14:20:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

/*
 * #%L
 * de.metas.externalsystem
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

-- 2022-11-07T12:20:20.583Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545291 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2022-11-07T12:20:27.784Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546438,545291,TO_TIMESTAMP('2022-11-07 14:20:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-07 14:20:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10
-- UI Element Group: default
-- 2022-11-07T12:20:43.492Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546438,550001,TO_TIMESTAMP('2022-11-07 14:20:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-07 14:20:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10 -> default.ExternalSystem_Config_Metasfresh
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystem_Config_Metasfresh_ID
-- 2022-11-07T12:55:39.792Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707977,0,546666,613402,550001,'F',TO_TIMESTAMP('2022-11-07 14:55:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalSystem_Config_Metasfresh',10,0,0,TO_TIMESTAMP('2022-11-07 14:55:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10 -> default.Suchschlüssel
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystemValue
-- 2022-11-07T12:55:53.985Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707980,0,546666,613403,550001,'F',TO_TIMESTAMP('2022-11-07 14:55:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',20,0,0,TO_TIMESTAMP('2022-11-07 14:55:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10 -> default.User Authentication Token
-- Column: ExternalSystem_Config_Metasfresh.CamelHttpResourceAuthKey
-- 2022-11-07T12:56:19.038Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707978,0,546666,613404,550001,'F',TO_TIMESTAMP('2022-11-07 14:56:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'User Authentication Token ',30,0,0,TO_TIMESTAMP('2022-11-07 14:56:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:57:19.674Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Metasfresh','ALTER TABLE public.ExternalSystem_Config_Metasfresh ADD COLUMN ExternalSystemValue VARCHAR(255) NOT NULL')
;

