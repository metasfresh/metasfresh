-- Column: C_Project_WO_ObjectUnderTest.M_Product_ID
-- 2023-04-20T09:27:28.571379500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586486,454,0,19,542184,'M_Product_ID',TO_TIMESTAMP('2023-04-20 12:27:28.191','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2023-04-20 12:27:28.191','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-04-20T09:27:28.587360800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586486 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-20T09:27:30.215476600Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2023-04-20T09:30:05.741690300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582254,0,'ObjectDeliveredDate',TO_TIMESTAMP('2023-04-20 12:30:05.585','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Object Delivered Date','Object Delivered Date',TO_TIMESTAMP('2023-04-20 12:30:05.585','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-04-20T09:30:05.745482300Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582254 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ObjectDeliveredDate
-- 2023-04-20T09:30:54.721860900Z
UPDATE AD_Element_Trl SET Name='Objekt Geliefert Datum', PrintName='Objekt Geliefert Datum',Updated=TO_TIMESTAMP('2023-04-20 12:30:54.721','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582254 AND AD_Language='de_CH'
;

-- 2023-04-20T09:30:54.729858900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582254,'de_CH') 
;

-- Element: ObjectDeliveredDate
-- 2023-04-20T09:31:01.203256200Z
UPDATE AD_Element_Trl SET Name='Objekt Geliefert Datum', PrintName='Objekt Geliefert Datum',Updated=TO_TIMESTAMP('2023-04-20 12:31:01.203','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582254 AND AD_Language='de_DE'
;

-- 2023-04-20T09:31:01.213827200Z
UPDATE AD_Element SET Name='Objekt Geliefert Datum', PrintName='Objekt Geliefert Datum' WHERE AD_Element_ID=582254
;

-- 2023-04-20T09:31:02.133418900Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582254,'de_DE') 
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

-- 2023-04-20T09:31:02.138454900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582254,'de_DE') 
;

-- Column: C_Project_WO_ObjectUnderTest.ObjectDeliveredDate
-- 2023-04-20T09:31:34.554940900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586487,582254,0,15,542184,'ObjectDeliveredDate',TO_TIMESTAMP('2023-04-20 12:31:34.373','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Objekt Geliefert Datum',0,0,TO_TIMESTAMP('2023-04-20 12:31:34.373','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-04-20T09:31:34.560431600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586487 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-20T09:31:35.853351800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582254) 
;

-- 2023-04-20T09:31:39.443149800Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN ObjectDeliveredDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2023-04-20T09:31:55.203872200Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2023-04-20T09:31:55.226873Z
ALTER TABLE C_Project_WO_ObjectUnderTest ADD CONSTRAINT MProduct_CProjectWOObjectUnderTest FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2023-04-24T20:49:10.690941500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582259,0,'C_OrderLine_Provision_ID',TO_TIMESTAMP('2023-04-24 23:49:09.606','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Order Line Provision','Order Line Provision',TO_TIMESTAMP('2023-04-24 23:49:09.606','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-04-24T20:49:10.700021500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582259 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_OrderLine_Provision_ID
-- 2023-04-24T20:49:58.717946200Z
UPDATE AD_Element_Trl SET Name='Auftragsposition Bereitstellung', PrintName='Auftragsposition Bereitstellung',Updated=TO_TIMESTAMP('2023-04-24 23:49:58.717','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582259 AND AD_Language='de_CH'
;

-- 2023-04-24T20:49:58.724436200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582259,'de_CH')
;

-- Element: C_OrderLine_Provision_ID
-- 2023-04-24T20:50:04.875738500Z
UPDATE AD_Element_Trl SET Name='Auftragsposition Bereitstellung', PrintName='Auftragsposition Bereitstellung',Updated=TO_TIMESTAMP('2023-04-24 23:50:04.875','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582259 AND AD_Language='de_DE'
;

-- 2023-04-24T20:50:04.877654900Z
UPDATE AD_Element SET Name='Auftragsposition Bereitstellung', PrintName='Auftragsposition Bereitstellung' WHERE AD_Element_ID=582259
;

-- 2023-04-24T20:50:06.182859600Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582259,'de_DE')
;

-- 2023-04-24T20:50:06.185092700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582259,'de_DE')
;

-- Column: C_Project_WO_ObjectUnderTest.C_OrderLine_Provision_ID
-- 2023-04-24T21:17:15.494106200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586493,582259,0,22,542184,'C_OrderLine_Provision_ID',TO_TIMESTAMP('2023-04-25 00:17:15.264','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftragsposition Bereitstellung',0,0,TO_TIMESTAMP('2023-04-25 00:17:15.264','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-04-24T21:17:15.505602400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586493 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-24T21:17:17.068485600Z
/* DDL */  select update_Column_Translation_From_AD_Element(582259)
;

-- 2023-04-24T21:17:44.908064600Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_ObjectUnderTest','ALTER TABLE public.C_Project_WO_ObjectUnderTest ADD COLUMN C_OrderLine_Provision_ID NUMERIC')
;

-- Column: C_Project_WO_ObjectUnderTest.C_OrderLine_Provision_ID
-- 2023-04-26T10:31:19.047345500Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=271,Updated=TO_TIMESTAMP('2023-04-26 13:31:19.047','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586493
;

-- 2023-04-26T10:32:28.861650800Z
INSERT INTO t_alter_column values('c_project_wo_objectundertest','C_OrderLine_Provision_ID',null,'NULL',null)
;

-- Column: C_Project_WO_ObjectUnderTest.M_Product_ID
-- 2023-04-28T00:08:57.203346400Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-04-28 03:08:57.203','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586486
;

-- Element: ObjectDeliveredDate
-- 2023-05-03T14:42:41.549218400Z
UPDATE AD_Element_Trl SET Name='Gegenstand geliefert am', PrintName='Gegenstand geliefert am',Updated=TO_TIMESTAMP('2023-05-03 17:42:41.548','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582254 AND AD_Language='de_CH'
;

-- 2023-05-03T14:42:41.569824900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582254,'de_CH')
;

-- Element: ObjectDeliveredDate
-- 2023-05-03T14:42:56.173414200Z
UPDATE AD_Element_Trl SET Name='Gegenstand geliefert am', PrintName='Gegenstand geliefert am',Updated=TO_TIMESTAMP('2023-05-03 17:42:56.173','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582254 AND AD_Language='de_DE'
;

-- 2023-05-03T14:42:56.175472600Z
UPDATE AD_Element SET Name='Gegenstand geliefert am', PrintName='Gegenstand geliefert am' WHERE AD_Element_ID=582254
;

-- 2023-05-03T14:42:57.379675600Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582254,'de_DE')
;

-- 2023-05-03T14:42:57.384671500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582254,'de_DE')
;

-- Element: ObjectDeliveredDate
-- 2023-05-03T14:43:19.722859700Z
UPDATE AD_Element_Trl SET Name='Object delivered on', PrintName='Object delivered on',Updated=TO_TIMESTAMP('2023-05-03 17:43:19.721','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582254 AND AD_Language='en_US'
;

-- 2023-05-03T14:43:19.726955700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582254,'en_US')
;

-- Element: ObjectDeliveredDate
-- 2023-05-03T14:43:28.462277900Z
UPDATE AD_Element_Trl SET Name='Object delivered on', PrintName='Object delivered on',Updated=TO_TIMESTAMP('2023-05-03 17:43:28.462','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582254 AND AD_Language='fr_CH'
;

-- 2023-05-03T14:43:28.465269800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582254,'fr_CH')
;

-- Element: C_OrderLine_Provision_ID
-- 2023-05-03T14:44:48.357635400Z
UPDATE AD_Element_Trl SET Name='Beistellung Position', PrintName='Beistellung Position',Updated=TO_TIMESTAMP('2023-05-03 17:44:48.356','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582259 AND AD_Language='de_CH'
;

-- 2023-05-03T14:44:48.360623900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582259,'de_CH')
;

-- Element: C_OrderLine_Provision_ID
-- 2023-05-03T14:44:56.399711100Z
UPDATE AD_Element_Trl SET Name='Beistellung Position', PrintName='Beistellung Position',Updated=TO_TIMESTAMP('2023-05-03 17:44:56.399','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582259 AND AD_Language='de_DE'
;

-- 2023-05-03T14:44:56.401710100Z
UPDATE AD_Element SET Name='Beistellung Position', PrintName='Beistellung Position' WHERE AD_Element_ID=582259
;

-- 2023-05-03T14:44:57.601605800Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582259,'de_DE')
;

-- 2023-05-03T14:44:57.603583600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582259,'de_DE')
;

-- Element: C_OrderLine_Provision_ID
-- 2023-05-03T14:45:18.852288500Z
UPDATE AD_Element_Trl SET Name='Provision order line', PrintName='Provision order line',Updated=TO_TIMESTAMP('2023-05-03 17:45:18.852','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582259 AND AD_Language='en_US'
;

-- 2023-05-03T14:45:18.855280300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582259,'en_US')
;

-- Element: C_OrderLine_Provision_ID
-- 2023-05-03T14:45:28.080158300Z
UPDATE AD_Element_Trl SET Name='Provision order line', PrintName='Provision order line',Updated=TO_TIMESTAMP('2023-05-03 17:45:28.079','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582259 AND AD_Language='fr_CH'
;

-- 2023-05-03T14:45:28.083166400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582259,'fr_CH')
;

