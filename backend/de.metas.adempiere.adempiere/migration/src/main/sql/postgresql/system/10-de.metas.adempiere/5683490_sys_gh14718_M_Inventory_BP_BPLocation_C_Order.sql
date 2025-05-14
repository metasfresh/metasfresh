-- Column: M_Inventory.C_BPartner_Location_ID
-- 2023-03-30T22:55:22.800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586383,189,0,18,159,321,131,'C_BPartner_Location_ID',TO_TIMESTAMP('2023-03-31 01:55:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort',0,0,TO_TIMESTAMP('2023-03-31 01:55:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-30T22:55:22.814Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586383 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-30T22:55:23.488Z
/* DDL */  select update_Column_Translation_From_AD_Element(189) 
;

-- 2023-03-30T22:55:31.097Z
/* DDL */ SELECT public.db_alter_table('M_Inventory','ALTER TABLE public.M_Inventory ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- 2023-03-30T22:55:31.370Z
ALTER TABLE M_Inventory ADD CONSTRAINT CBPartnerLocation_MInventory FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Standort
-- Column: M_Inventory.C_BPartner_Location_ID
-- 2023-03-30T22:56:22.630Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586383,713584,0,255,TO_TIMESTAMP('2023-03-31 01:56:22','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'D','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2023-03-31 01:56:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T22:56:22.634Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713584 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-30T22:56:22.640Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2023-03-30T22:56:22.663Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713584
;

-- 2023-03-30T22:56:22.671Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713584)
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> default.Standort
-- Column: M_Inventory.C_BPartner_Location_ID
-- 2023-03-30T22:56:46.052Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,713584,0,255,616492,541512,'F',TO_TIMESTAMP('2023-03-31 01:56:45','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Standort',210,0,0,TO_TIMESTAMP('2023-03-31 01:56:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T23:06:14.105Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582200,0,'C_PO_Order_ID',TO_TIMESTAMP('2023-03-31 02:06:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Purchase Order','Purchase Order',TO_TIMESTAMP('2023-03-31 02:06:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-30T23:06:14.120Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582200 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Inventory.C_PO_Order_ID
-- Column SQL (old): (SELECT DISTINCT dp.c_order_id from m_inventoryline il LEFT JOIN m_hu_assignment hua on (hua.m_hu_id = il.m_hu_id) LEFT JOIN m_inoutline iol on (iol.m_inoutline_id = hua.record_id) LEFT JOIN m_inout io on iol.m_inout_id = io.m_inout_id LEFT JOIN m_delivery_planning dp on (io.m_delivery_planning_id = dp.m_delivery_planning_id) where (il.m_inventory_id = M_Inventory.m_inventory_id) LIMIT 1)
-- 2023-03-30T23:06:40.759Z
UPDATE AD_Column SET AD_Element_ID=582200, AD_Reference_ID=18, AD_Reference_Value_ID=540250, ColumnName='C_PO_Order_ID', ColumnSQL='', Description=NULL, Help=NULL, IsLazyLoading='N', Name='Purchase Order',Updated=TO_TIMESTAMP('2023-03-31 02:06:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585929
;

-- 2023-03-30T23:06:40.761Z
UPDATE AD_Column_Trl trl SET Name='Purchase Order' WHERE AD_Column_ID=585929 AND AD_Language='de_DE'
;

-- 2023-03-30T23:06:40.765Z
UPDATE AD_Field SET Name='Purchase Order', Description=NULL, Help=NULL WHERE AD_Column_ID=585929
;

-- 2023-03-30T23:06:40.767Z
/* DDL */  select update_Column_Translation_From_AD_Element(582200) 
;

-- Column: M_Inventory.C_BPartner_ID
-- Column SQL (old): (SELECT DISTINCT dp.c_bpartner_id from m_inventoryline il LEFT JOIN m_hu_assignment hua on (hua.m_hu_id = il.m_hu_id) LEFT JOIN m_inoutline iol on (iol.m_inoutline_id = hua.record_id) LEFT JOIN m_inout io on iol.m_inout_id = io.m_inout_id LEFT JOIN m_delivery_planning dp on (io.m_delivery_planning_id = dp.m_delivery_planning_id) where (il.m_inventory_id = M_Inventory.m_inventory_id) LIMIT 1)
-- 2023-03-30T23:07:46.425Z
UPDATE AD_Column SET ColumnSQL='', IsLazyLoading='N',Updated=TO_TIMESTAMP('2023-03-31 02:07:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585930
;

-- 2023-03-30T23:07:49.692Z
/* DDL */ SELECT public.db_alter_table('M_Inventory','ALTER TABLE public.M_Inventory ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2023-03-30T23:07:50.005Z
ALTER TABLE M_Inventory ADD CONSTRAINT CBPartner_MInventory FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2023-03-30T23:07:58.345Z
/* DDL */ SELECT public.db_alter_table('M_Inventory','ALTER TABLE public.M_Inventory ADD COLUMN C_PO_Order_ID NUMERIC(10)')
;

-- 2023-03-30T23:07:58.589Z
ALTER TABLE M_Inventory ADD CONSTRAINT CPOOrder_MInventory FOREIGN KEY (C_PO_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- 2023-03-30T23:08:09.552Z
INSERT INTO t_alter_column values('m_inventory','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Purchase Order
-- Column: M_Inventory.C_PO_Order_ID
-- 2023-03-30T23:09:51.942Z
UPDATE AD_Field SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-03-31 02:09:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712217
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Geschäftspartner
-- Column: M_Inventory.C_BPartner_ID
-- 2023-03-30T23:10:18.878Z
UPDATE AD_Field SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-03-31 02:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712218
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Standort
-- Column: M_Inventory.C_BPartner_Location_ID
-- 2023-03-30T23:10:43.016Z
UPDATE AD_Field SET DisplayLogic='@DocSubType@=''ISD'' | @DocSubType@=''IOD''', ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-03-31 02:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713584
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> default.Standort
-- Column: M_Inventory.C_BPartner_Location_ID
-- 2023-03-30T23:11:12.310Z
UPDATE AD_UI_Element SET SeqNo=55,Updated=TO_TIMESTAMP('2023-03-31 02:11:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616492
;


-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> default.Standort
-- Column: M_Inventory.C_BPartner_Location_ID
-- 2023-03-30T23:48:57.228Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=616492
;

-- 2023-03-30T23:49:09.170Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_Element_ID,AD_UI_ElementField_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,712218,0,615613,541705,TO_TIMESTAMP('2023-03-31 02:49:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2023-03-31 02:49:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Standort
-- Column: Fact_Acct_Transactions_View.C_BPartner_Location_ID
-- 2023-03-30T23:50:16.809Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=616488
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

-- 2023-03-30T23:50:42.417Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_Element_ID,AD_UI_ElementField_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,713580,0,543382,541706,TO_TIMESTAMP('2023-03-31 02:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2023-03-31 02:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> default.Standort
-- Column: C_Payment.C_BPartner_Location_ID
-- 2023-03-30T23:51:40.573Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=616491
;

-- 2023-03-30T23:51:56.350Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_Element_ID,AD_UI_ElementField_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,713583,0,541188,541707,TO_TIMESTAMP('2023-03-31 02:51:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2023-03-31 02:51:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-31T00:11:19.571Z
UPDATE AD_UI_ElementField SET AD_Field_ID=713584,Updated=TO_TIMESTAMP('2023-03-31 03:11:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementField_ID=541705
;


-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Standort
-- Column: M_Inventory.C_BPartner_Location_ID
-- 2023-03-31T00:13:38.110Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-03-31 03:13:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713584
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Purchase Order
-- Column: M_Inventory.C_PO_Order_ID
-- 2023-03-31T00:13:55.122Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-03-31 03:13:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712217
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Geschäftspartner
-- Column: M_Inventory.C_BPartner_ID
-- 2023-03-31T00:14:03.138Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-03-31 03:14:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712218
;

-- Column: M_Inventory.C_BPartner_ID
-- 2023-03-31T00:17:23.463Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-03-31 03:17:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585930
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Geschäftspartner
-- Column: M_Inventory.C_BPartner_ID
-- 2023-03-31T00:19:11.932Z
UPDATE AD_Field SET AD_Reference_ID=NULL, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2023-03-31 03:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712218
;

