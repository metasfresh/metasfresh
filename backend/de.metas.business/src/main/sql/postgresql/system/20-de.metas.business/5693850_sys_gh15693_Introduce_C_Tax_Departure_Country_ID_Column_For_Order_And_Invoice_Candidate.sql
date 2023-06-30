-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-30T12:30:13.244Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587019,582466,0,30,156,259,'XX','C_Tax_Departure_Country_ID',TO_TIMESTAMP('2023-06-30 15:30:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tax Departure Country',0,0,TO_TIMESTAMP('2023-06-30 15:30:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-30T12:30:13.247Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587019 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-30T12:30:14.745Z
/* DDL */  select update_Column_Translation_From_AD_Element(582466) 
;

-- 2023-06-30T12:30:18.034Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN C_Tax_Departure_Country_ID NUMERIC(10)')
;

-- 2023-06-30T12:30:24.491Z
ALTER TABLE C_Order ADD CONSTRAINT CTaxDepartureCountry_COrder FOREIGN KEY (C_Tax_Departure_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-30T12:31:22.353Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587020,582466,0,30,156,540270,'XX','C_Tax_Departure_Country_ID',TO_TIMESTAMP('2023-06-30 15:31:22','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tax Departure Country',0,0,TO_TIMESTAMP('2023-06-30 15:31:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-30T12:31:22.353Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587020 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-30T12:31:24.453Z
/* DDL */  select update_Column_Translation_From_AD_Element(582466) 
;

-- 2023-06-30T12:31:32.076Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_Tax_Departure_Country_ID NUMERIC(10)')
;

-- 2023-06-30T12:31:33.963Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CTaxDepartureCountry_CInvoiceCandidate FOREIGN KEY (C_Tax_Departure_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

-- Field: Sales Order_OLD(143,D) -> Order(186,D) -> Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-30T12:32:44.614Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587019,716470,0,186,0,TO_TIMESTAMP('2023-06-30 15:32:44','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Tax Departure Country',0,760,0,1,1,TO_TIMESTAMP('2023-06-30 15:32:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-30T12:32:44.624Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716470 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-30T12:32:44.639Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-30T12:32:44.670Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716470
;

-- 2023-06-30T12:32:44.686Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716470)
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> main view -> 10 -> main.Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-30T12:34:27.145Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716470,0,186,618105,540005,'F',TO_TIMESTAMP('2023-06-30 15:34:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tax Departure Country',190,0,0,TO_TIMESTAMP('2023-06-30 15:34:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Order(186,D) -> main view -> 10 -> main.Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-30T12:34:36.352Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2023-06-30 15:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618105
;

-- Name: C_Tax_Departure_Country
-- 2023-06-30T12:36:44.644Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540641,'C_Tax_ID IN (SELECT tax.C_Tax_ID from C_Tax tax where @C_Tax_Departure_Country_ID/-1@ <= 0 OR tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',TO_TIMESTAMP('2023-06-30 15:36:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Tax_Departure_Country','S',TO_TIMESTAMP('2023-06-30 15:36:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-30T12:37:16.562Z
UPDATE AD_Column SET AD_Val_Rule_ID=540641,Updated=TO_TIMESTAMP('2023-06-30 15:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587019
;

-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-30T12:38:04.302Z
UPDATE AD_Column SET AD_Val_Rule_ID=540641,Updated=TO_TIMESTAMP('2023-06-30 15:38:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587020
;

-- Column: C_Invoice.C_Tax_Departure_Country_ID
-- 2023-06-30T12:38:33.424Z
UPDATE AD_Column SET AD_Val_Rule_ID=540641,Updated=TO_TIMESTAMP('2023-06-30 15:38:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586847
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-30T12:39:23.729Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587019,716471,0,294,0,TO_TIMESTAMP('2023-06-30 15:39:23','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Tax Departure Country',0,220,0,1,1,TO_TIMESTAMP('2023-06-30 15:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-30T12:39:23.732Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716471 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-30T12:39:23.732Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-30T12:39:23.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716471
;

-- 2023-06-30T12:39:23.743Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716471)
;

-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-30T12:39:49.695Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2023-06-30 15:39:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587019
;

-- Column: C_Invoice.C_Tax_Departure_Country_ID
-- 2023-06-30T12:40:06.426Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2023-06-30 15:40:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586847
;

-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-30T12:40:18.232Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2023-06-30 15:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587020
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> main -> 10 -> main.Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-30T12:44:04.387Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716471,0,294,618106,540072,'F',TO_TIMESTAMP('2023-06-30 15:44:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tax Departure Country',35,0,0,TO_TIMESTAMP('2023-06-30 15:44:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-30T12:45:10.038Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587020,716472,0,540279,0,TO_TIMESTAMP('2023-06-30 15:45:09','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Tax Departure Country',0,580,0,1,1,TO_TIMESTAMP('2023-06-30 15:45:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-30T12:45:10.043Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716472 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-30T12:45:10.052Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-30T12:45:10.052Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716472
;

-- 2023-06-30T12:45:10.052Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716472)
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-30T12:52:58.691Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587020,716473,0,543052,0,TO_TIMESTAMP('2023-06-30 15:52:58','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Tax Departure Country',0,550,0,1,1,TO_TIMESTAMP('2023-06-30 15:52:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-30T12:52:58.691Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716473 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-30T12:52:58.691Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-30T12:52:58.704Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716473
;

-- 2023-06-30T12:52:58.704Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716473)
;

-- Name: C_Tax_Departure_Country
-- 2023-06-30T12:56:53.508Z
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540641
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-30T12:57:22.801Z
UPDATE AD_Ref_Table SET WhereClause='C_Tax_ID IN (SELECT tax.C_Tax_ID from C_Tax tax where @C_Tax_Departure_Country_ID/-1@ <= 0 OR tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-30 15:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-30T12:59:15.602Z
UPDATE AD_Field SET ReadOnlyLogic='DocStatus==''DR''',Updated=TO_TIMESTAMP('2023-06-30 15:59:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716472
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> VAT Code override
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2023-06-30T12:59:27.197Z
UPDATE AD_Field SET ReadOnlyLogic='DocStatus==''DR''',Updated=TO_TIMESTAMP('2023-06-30 15:59:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708908
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> VAT Code override
-- Column: C_Invoice_Candidate.C_VAT_Code_Override_ID
-- 2023-06-30T12:59:40.357Z
UPDATE AD_Field SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2023-06-30 15:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708908
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-30T13:00:25.694Z
UPDATE AD_Field SET ReadOnlyLogic='DocStatus==''DR''',Updated=TO_TIMESTAMP('2023-06-30 16:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716473
;

-- Field: Purchase Invoice(541621,de.metas.ab182) -> Invoice(546648,de.metas.ab182) -> Tax Departure Country
-- Column: C_Invoice.C_Tax_Departure_Country_ID
-- 2023-06-30T16:10:50.331Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586847,716478,0,546648,0,TO_TIMESTAMP('2023-06-30 19:10:50','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Tax Departure Country',0,410,0,1,1,TO_TIMESTAMP('2023-06-30 19:10:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-30T16:10:50.341Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716478 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-30T16:10:50.348Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-30T16:10:50.357Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716478
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

-- 2023-06-30T16:10:50.363Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716478)
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Invoice(546648,de.metas.ab182) -> main -> 10 -> preise.Tax Departure Country
-- Column: C_Invoice.C_Tax_Departure_Country_ID
-- 2023-06-30T16:12:04.950Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716478,0,546648,618111,549959,'F',TO_TIMESTAMP('2023-06-30 19:12:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tax Departure Country',17,0,0,TO_TIMESTAMP('2023-06-30 19:12:04','YYYY-MM-DD HH24:MI:SS'),100)
;

