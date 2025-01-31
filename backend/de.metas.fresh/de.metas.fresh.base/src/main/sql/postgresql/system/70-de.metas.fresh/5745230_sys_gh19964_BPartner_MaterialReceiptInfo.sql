/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- 2025-01-30T16:15:35.111Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583461,0,'MaterialReceiptInfo',TO_TIMESTAMP('2025-01-30 17:15:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Wareneingangsinfo','Optionale Info, die in der Wareneingangs angezeigt wird',TO_TIMESTAMP('2025-01-30 17:15:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-01-30T16:15:35.423Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583461 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: MaterialReceiptInfo
-- 2025-01-30T16:16:34.756Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Material Receipt Info', PrintName='Optional info that is displayed in the goods receipt',Updated=TO_TIMESTAMP('2025-01-30 17:16:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583461 AND AD_Language='en_US'
;

-- 2025-01-30T16:16:34.885Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583461,'en_US')
;
-- Column: C_BPartner.MaterialReceiptInfo
-- Column: C_BPartner.MaterialReceiptInfo
-- 2025-01-30T16:21:04.998Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589627,583461,0,14,291,'XX','MaterialReceiptInfo',TO_TIMESTAMP('2025-01-30 17:21:04','YYYY-MM-DD HH24:MI:SS'),100,'N','D',2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Wareneingangsinfo',0,0,TO_TIMESTAMP('2025-01-30 17:21:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2025-01-30T16:21:05.157Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589627 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-30T16:21:05.260Z
/* DDL */  select update_Column_Translation_From_AD_Element(583461)
;

-- 2025-01-30T17:23:54.532Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN MaterialReceiptInfo VARCHAR(2000)')
;

-- Field: Geschäftspartner_OLD -> Lieferant -> Wareneingangsinfo
-- Column: C_BPartner.MaterialReceiptInfo
-- Field: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> Wareneingangsinfo
-- Column: C_BPartner.MaterialReceiptInfo
-- 2025-01-30T17:32:47.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589627,735599,0,224,0,TO_TIMESTAMP('2025-01-30 18:32:47','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N',0,'Wareneingangsinfo',0,0,200,0,1,1,TO_TIMESTAMP('2025-01-30 18:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-01-30T17:32:48.151Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=735599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-30T17:32:48.202Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583461)
;

-- 2025-01-30T17:32:48.255Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=735599
;

-- 2025-01-30T17:32:48.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(735599)
;

-- UI Element: Geschäftspartner_OLD -> Lieferant.Wareneingangsinfo
-- Column: C_BPartner.MaterialReceiptInfo
-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Wareneingangsinfo
-- Column: C_BPartner.MaterialReceiptInfo
-- 2025-01-30T17:34:02.123Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,735599,0,224,1000033,628434,'F',TO_TIMESTAMP('2025-01-30 18:34:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Wareneingangsinfo',65,0,0,TO_TIMESTAMP('2025-01-30 18:34:01','YYYY-MM-DD HH24:MI:SS'),100)
;
