-- 2022-10-05T10:52:19.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581536,0,'CapacityPerProductionCycle_UOM_ID',TO_TIMESTAMP('2022-10-05 13:52:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Maßeinheit','Maßeinheit',TO_TIMESTAMP('2022-10-05 13:52:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-05T10:52:19.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581536 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-05T10:52:48.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Unit of measurement', PrintName='Unit of measurement',Updated=TO_TIMESTAMP('2022-10-05 13:52:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581536 AND AD_Language='en_US'
;

-- 2022-10-05T10:52:48.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581536,'en_US') 
;

-- 2022-10-05T10:55:22.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584673,581536,0,18,114,487,210,'CapacityPerProductionCycle_UOM_ID',TO_TIMESTAMP('2022-10-05 13:55:22','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2022-10-05 13:55:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-05T10:55:22.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584673 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-05T10:55:22.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581536) 
;

-- 2022-10-05T10:55:23.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('S_Resource','ALTER TABLE public.S_Resource ADD COLUMN CapacityPerProductionCycle_UOM_ID NUMERIC(10)')
;

-- 2022-10-05T10:55:23.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE S_Resource ADD CONSTRAINT MaxCapacityPerProductionCycleUOM_SResource FOREIGN KEY (CapacityPerProductionCycle_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2022-10-07T08:47:36.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584673,707754,0,414,0,TO_TIMESTAMP('2022-10-07 11:47:36','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Maßeinheit',0,180,0,1,1,TO_TIMESTAMP('2022-10-07 11:47:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-07T08:47:36.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707754 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-07T08:47:36.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581536)
;

-- 2022-10-07T08:47:36.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707754
;

-- 2022-10-07T08:47:36.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(707754)
;

-- 2022-10-07T08:48:25.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Capacity Per Production Cycle',Updated=TO_TIMESTAMP('2022-10-07 11:48:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581534 AND AD_Language='de_CH'
;

-- 2022-10-07T08:48:25.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581534,'de_CH')
;

-- 2022-10-07T08:48:27.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Capacity Per Production Cycle',Updated=TO_TIMESTAMP('2022-10-07 11:48:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581534 AND AD_Language='de_DE'
;

-- 2022-10-07T08:48:27.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581534,'de_DE')
;

-- 2022-10-07T08:48:27.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581534,'de_DE')
;

-- 2022-10-07T08:48:27.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CapacityPerProductionCycle', Name='Capacity Per Production Cycle', Description=NULL, Help=NULL WHERE AD_Element_ID=581534
;

-- 2022-10-07T08:48:27.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CapacityPerProductionCycle', Name='Capacity Per Production Cycle', Description=NULL, Help=NULL, AD_Element_ID=581534 WHERE UPPER(ColumnName)='CAPACITYPERPRODUCTIONCYCLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-10-07T08:48:27.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CapacityPerProductionCycle', Name='Capacity Per Production Cycle', Description=NULL, Help=NULL WHERE AD_Element_ID=581534 AND IsCentrallyMaintained='Y'
;

-- 2022-10-07T08:48:27.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Capacity Per Production Cycle', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581534) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581534)
;

-- 2022-10-07T08:48:27.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Capacity per production cycle', Name='Capacity Per Production Cycle' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581534)
;

-- 2022-10-07T08:48:27.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Capacity Per Production Cycle', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581534
;

-- 2022-10-07T08:48:27.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Capacity Per Production Cycle', Description=NULL, Help=NULL WHERE AD_Element_ID = 581534
;

-- 2022-10-07T08:48:27.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Capacity Per Production Cycle', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581534
;

-- 2022-10-07T08:48:29.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Capacity Per Production Cycle',Updated=TO_TIMESTAMP('2022-10-07 11:48:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581534 AND AD_Language='en_US'
;

-- 2022-10-07T08:48:29.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581534,'en_US')
;

-- 2022-10-07T08:48:33.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Capacity Per Production Cycle',Updated=TO_TIMESTAMP('2022-10-07 11:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581534 AND AD_Language='nl_NL'
;

-- 2022-10-07T08:48:33.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581534,'nl_NL')
;

-- 2022-10-07T08:49:51.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707754,0,414,613221,543930,'F',TO_TIMESTAMP('2022-10-07 11:49:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Maßeinheit',50,0,0,TO_TIMESTAMP('2022-10-07 11:49:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-07T08:52:45.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@CapacityPerProductionCycle@!0',Updated=TO_TIMESTAMP('2022-10-07 11:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584673
;

-- 2022-10-07T08:53:52.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542589,549971,TO_TIMESTAMP('2022-10-07 11:53:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','capacity',30,TO_TIMESTAMP('2022-10-07 11:53:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-07T08:54:02.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613120
;

-- 2022-10-07T08:54:06.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613221
;

-- 2022-10-07T08:54:19.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707472,0,414,613222,549971,'F',TO_TIMESTAMP('2022-10-07 11:54:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Capacity Per Production Cycle',10,0,0,TO_TIMESTAMP('2022-10-07 11:54:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-07T08:54:28.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707754,0,414,613223,549971,'F',TO_TIMESTAMP('2022-10-07 11:54:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Maßeinheit',20,0,0,TO_TIMESTAMP('2022-10-07 11:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-07T12:20:55.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kapazität pro Produktionszyklus ', PrintName='Kapazität pro Produktionszyklus ',Updated=TO_TIMESTAMP('2022-10-07 15:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581534 AND AD_Language='de_CH'
;

-- 2022-10-07T12:20:55.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581534,'de_CH')
;

-- 2022-10-07T12:21:00.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kapazität pro Produktionszyklus ', PrintName='Kapazität pro Produktionszyklus ',Updated=TO_TIMESTAMP('2022-10-07 15:21:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581534 AND AD_Language='de_DE'
;

-- 2022-10-07T12:21:00.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581534,'de_DE')
;

-- 2022-10-07T12:21:00.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581534,'de_DE')
;

-- 2022-10-07T12:21:00.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CapacityPerProductionCycle', Name='Kapazität pro Produktionszyklus ', Description=NULL, Help=NULL WHERE AD_Element_ID=581534
;

-- 2022-10-07T12:21:00.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CapacityPerProductionCycle', Name='Kapazität pro Produktionszyklus ', Description=NULL, Help=NULL, AD_Element_ID=581534 WHERE UPPER(ColumnName)='CAPACITYPERPRODUCTIONCYCLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-10-07T12:21:00.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CapacityPerProductionCycle', Name='Kapazität pro Produktionszyklus ', Description=NULL, Help=NULL WHERE AD_Element_ID=581534 AND IsCentrallyMaintained='Y'
;

-- 2022-10-07T12:21:00.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kapazität pro Produktionszyklus ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581534) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581534)
;

-- 2022-10-07T12:21:00.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kapazität pro Produktionszyklus ', Name='Kapazität pro Produktionszyklus ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581534)
;

-- 2022-10-07T12:21:00.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kapazität pro Produktionszyklus ', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581534
;

-- 2022-10-07T12:21:00.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kapazität pro Produktionszyklus ', Description=NULL, Help=NULL WHERE AD_Element_ID = 581534
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

-- 2022-10-07T12:21:00.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kapazität pro Produktionszyklus ', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581534
;
