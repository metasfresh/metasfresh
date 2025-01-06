-- Run mode: SWING_CLIENT

-- Column: I_Invoice_Candidate.M_PriceList_Version_Name
-- 2024-10-30T18:26:17.989Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589365,542889,0,10,542207,'M_PriceList_Version_Name',TO_TIMESTAMP('2024-10-30 20:26:17.707','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Bezeichnet eine einzelne Version der Preisliste','D',0,255,'E','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Version Preisliste',0,0,TO_TIMESTAMP('2024-10-30 20:26:17.707','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-30T18:26:17.996Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589365 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-30T18:26:18.031Z
/* DDL */  select update_Column_Translation_From_AD_Element(542889)
;

-- Column: I_Invoice_Candidate.M_PriceList_Version_ID
-- 2024-10-30T18:26:57.011Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589366,450,0,19,542207,'M_PriceList_Version_ID',TO_TIMESTAMP('2024-10-30 20:26:56.859','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Bezeichnet eine einzelne Version der Preisliste','D',0,10,'Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Version Preisliste',0,0,TO_TIMESTAMP('2024-10-30 20:26:56.859','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-30T18:26:57.013Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589366 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-30T18:26:57.015Z
/* DDL */  select update_Column_Translation_From_AD_Element(450)
;

-- 2024-10-30T18:32:57.887Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN M_PriceList_Version_ID NUMERIC(10)')
;

-- 2024-10-30T18:32:57.958Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT MPriceListVersion_IInvoiceCandidate FOREIGN KEY (M_PriceList_Version_ID) REFERENCES public.M_PriceList_Version DEFERRABLE INITIALLY DEFERRED
;

-- 2024-10-30T18:33:08.249Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN M_PriceList_Version_Name VARCHAR(255)')
;

-- Run mode: WEBUI

-- 2024-10-30T18:44:36.793Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,589365,540077,542040,0,TO_TIMESTAMP('2024-10-30 20:44:36.791','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','Version Preisliste',230,TO_TIMESTAMP('2024-10-30 20:44:36.791','YYYY-MM-DD HH24:MI:SS.US'),100)
;

/*
 * #%L
 * de.metas.swat.base
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

-- 2024-10-30T18:44:48.382Z
UPDATE AD_ImpFormat_Row SET StartNo=23,Updated=TO_TIMESTAMP('2024-10-30 20:44:48.382','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542040
;

-- 2024-10-30T18:45:34.700Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,589365,540093,542041,0,TO_TIMESTAMP('2024-10-30 20:45:34.698','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','Version Preisliste',240,TO_TIMESTAMP('2024-10-30 20:45:34.698','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-30T18:45:38.080Z
UPDATE AD_ImpFormat_Row SET StartNo=24,Updated=TO_TIMESTAMP('2024-10-30 20:45:38.08','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542041
;

-- 2024-10-30T18:45:58.327Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2024-10-30 20:45:58.327','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=542041
;

-- Run mode: SWING_CLIENT

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Version Preisliste
-- Column: I_Invoice_Candidate.M_PriceList_Version_ID
-- 2024-10-30T18:50:08.733Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589366,732013,0,546594,TO_TIMESTAMP('2024-10-30 20:50:08.558','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnet eine einzelne Version der Preisliste',10,'D','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','N','N','N','N','N','N','Version Preisliste',TO_TIMESTAMP('2024-10-30 20:50:08.558','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-30T18:50:08.736Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=732013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-30T18:50:08.740Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(450)
;

-- 2024-10-30T18:50:08.769Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732013
;

-- 2024-10-30T18:50:08.776Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732013)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Version Preisliste
-- Column: I_Invoice_Candidate.M_PriceList_Version_ID
-- 2024-10-30T18:50:27.771Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,732013,0,546594,626246,549841,'F',TO_TIMESTAMP('2024-10-30 20:50:27.62','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnet eine einzelne Version der Preisliste','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','N','Y','N','N','N',0,'Version Preisliste',45,0,0,TO_TIMESTAMP('2024-10-30 20:50:27.62','YYYY-MM-DD HH24:MI:SS.US'),100)
;
