-- Run mode: SWING_CLIENT

-- 2024-07-09T11:32:44.931Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583178,0,'InsertLogic',TO_TIMESTAMP('2024-07-09 14:32:44.676','YYYY-MM-DD HH24:MI:SS.US'),100,'Logic to determine if tab allows creation of new records or not.','U','Y','Insert Logic','Insert Logic',TO_TIMESTAMP('2024-07-09 14:32:44.676','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-09T11:32:44.938Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583178 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InsertLogic
-- 2024-07-09T11:33:11.339Z
UPDATE AD_Element_Trl SET Help='Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten k�nnen (optional) zwischen einfachen Anf�hrungszeichen stehen.',Updated=TO_TIMESTAMP('2024-07-09 14:33:11.339','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583178 AND AD_Language='en_US'
;

-- 2024-07-09T11:33:11.365Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583178,'en_US')
;

-- Element: InsertLogic
-- 2024-07-09T11:33:12.387Z
UPDATE AD_Element_Trl SET Help='Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten k�nnen (optional) zwischen einfachen Anf�hrungszeichen stehen.',Updated=TO_TIMESTAMP('2024-07-09 14:33:12.387','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583178 AND AD_Language='fr_CH'
;

-- 2024-07-09T11:33:12.389Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583178,'fr_CH')
;

-- Element: InsertLogic
-- 2024-07-09T11:33:13.397Z
UPDATE AD_Element_Trl SET Help='Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten k�nnen (optional) zwischen einfachen Anf�hrungszeichen stehen.',Updated=TO_TIMESTAMP('2024-07-09 14:33:13.397','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583178 AND AD_Language='it_IT'
;

-- 2024-07-09T11:33:13.399Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583178,'it_IT')
;

-- Element: InsertLogic
-- 2024-07-09T11:33:14.023Z
UPDATE AD_Element_Trl SET Help='Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten k�nnen (optional) zwischen einfachen Anf�hrungszeichen stehen.',Updated=TO_TIMESTAMP('2024-07-09 14:33:14.022','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583178 AND AD_Language='de_DE'
;

-- 2024-07-09T11:33:14.024Z
UPDATE AD_Element SET Help='Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten k�nnen (optional) zwischen einfachen Anf�hrungszeichen stehen.' WHERE AD_Element_ID=583178
;

-- 2024-07-09T11:33:14.327Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583178,'de_DE')
;

-- 2024-07-09T11:33:14.328Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583178,'de_DE')
;

-- Element: InsertLogic
-- 2024-07-09T11:33:31.414Z
UPDATE AD_Element_Trl SET Help='Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten k�nnen (optional) zwischen einfachen Anf�hrungszeichen stehen.',Updated=TO_TIMESTAMP('2024-07-09 14:33:31.414','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583178 AND AD_Language='de_CH'
;

-- 2024-07-09T11:33:31.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583178,'de_CH')
;

-- Column: AD_Tab.InsertLogic
-- 2024-07-09T11:35:44.072Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,588715,583178,0,10,106,'InsertLogic',TO_TIMESTAMP('2024-07-09 14:35:43.933','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Logic to determine if tab allows creation of new records or not.','D',2000,'Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten k�nnen (optional) zwischen einfachen Anf�hrungszeichen stehen.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Insert Logic','NP',0,TO_TIMESTAMP('2024-07-09 14:35:43.933','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-07-09T11:35:44.073Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588715 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-09T11:35:44.076Z
/* DDL */  select update_Column_Translation_From_AD_Element(583178)
;

-- 2024-07-09T11:35:48.532Z
/* DDL */ SELECT public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN InsertLogic VARCHAR(2000)')
;

-- Field: Fenster Verwaltung(102,D) -> Register(106,D) -> Insert Logic
-- Column: AD_Tab.InsertLogic
-- 2024-07-09T11:40:54.448Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588715,729066,0,106,0,TO_TIMESTAMP('2024-07-09 14:40:54.281','YYYY-MM-DD HH24:MI:SS.US'),100,'Logic to determine if tab allows creation of new records or not.',0,'@IsReadOnly@=N&@IsInsertRecord@=N','D','Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten k�nnen (optional) zwischen einfachen Anf�hrungszeichen stehen.',0,'Y','Y','Y','N','N','N','N','N','N','Insert Logic',285,450,0,1,1,TO_TIMESTAMP('2024-07-09 14:40:54.281','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-09T11:40:54.450Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- 2024-07-09T11:40:54.451Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583178)
;

-- 2024-07-09T11:40:54.461Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729066
;

-- 2024-07-09T11:40:54.462Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729066)
;

