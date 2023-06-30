-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T20:56:02.904Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587009,582466,0,30,156,259,'XX','C_Tax_Departure_Country_ID',TO_TIMESTAMP('2023-06-27 23:56:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Steuerabgangsland',0,0,TO_TIMESTAMP('2023-06-27 23:56:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-27T20:56:04.142Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587009 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-27T20:56:04.583Z
/* DDL */  select update_Column_Translation_From_AD_Element(582466) 
;

-- 2023-06-27T20:56:19.308Z
INSERT INTO t_alter_column values('c_invoice','C_Tax_Departure_Country_ID','NUMERIC(10)',null,null)
;

-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T20:57:12.964Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587010,582466,0,30,156,540270,'XX','C_Tax_Departure_Country_ID',TO_TIMESTAMP('2023-06-27 23:57:12','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Steuerabgangsland',0,0,TO_TIMESTAMP('2023-06-27 23:57:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-27T20:57:12.969Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587010 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-27T20:57:15.017Z
/* DDL */  select update_Column_Translation_From_AD_Element(582466) 
;

-- 2023-06-27T20:57:18.242Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_Tax_Departure_Country_ID NUMERIC(10)')
;

-- 2023-06-27T20:57:19.564Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CTaxDepartureCountry_CInvoiceCandidate FOREIGN KEY (C_Tax_Departure_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Steuerabgangsland
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T21:03:24.083Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587009,716462,0,186,0,TO_TIMESTAMP('2023-06-28 00:03:23','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Steuerabgangsland',0,760,0,1,1,TO_TIMESTAMP('2023-06-28 00:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-27T21:03:24.087Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716462 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-27T21:03:24.094Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-27T21:03:24.116Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716462
;

-- 2023-06-27T21:03:24.125Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716462)
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Steuerabgangsland
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T21:04:32.509Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587010,716463,0,540279,0,TO_TIMESTAMP('2023-06-28 00:04:32','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Steuerabgangsland',0,580,0,1,1,TO_TIMESTAMP('2023-06-28 00:04:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-27T21:04:32.514Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716463 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-27T21:04:32.518Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-27T21:04:32.525Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716463
;

-- 2023-06-27T21:04:32.537Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716463)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Steuerabgangsland
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T21:13:18.772Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716462,0,186,618097,540005,'F',TO_TIMESTAMP('2023-06-28 00:13:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Steuerabgangsland',190,0,0,TO_TIMESTAMP('2023-06-28 00:13:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Steuerabgangsland
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T21:13:46.781Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2023-06-28 00:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618097
;

-- UI Element: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 10 -> override.Steuerabgangsland
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T21:16:17.807Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716463,0,540279,618098,541087,'F',TO_TIMESTAMP('2023-06-28 00:16:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Steuerabgangsland',35,0,0,TO_TIMESTAMP('2023-06-28 00:16:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order_OLD(143,D) -> Auftrag(186,D) -> main view -> 10 -> main.Steuerabgangsland
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T21:30:59.288Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2023-06-28 00:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618097
;

-- Field: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T21:39:21.163Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587009,716464,0,294,0,TO_TIMESTAMP('2023-06-28 00:39:20','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Tax Departure Country',0,220,0,1,1,TO_TIMESTAMP('2023-06-28 00:39:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-27T21:39:21.171Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716464 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-27T21:39:21.236Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-27T21:39:21.266Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716464
;

-- 2023-06-27T21:39:21.274Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716464)
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> main -> 10 -> main.Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T21:41:21.831Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716464,0,294,618099,540072,'F',TO_TIMESTAMP('2023-06-28 00:41:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tax Departure Country',70,0,0,TO_TIMESTAMP('2023-06-28 00:41:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Purchase Order(294,D) -> main -> 10 -> main.Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T21:41:46.347Z
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2023-06-28 00:41:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618099
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T21:42:54.211Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587010,716465,0,543052,0,TO_TIMESTAMP('2023-06-28 00:42:54','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Tax Departure Country',0,550,0,1,1,TO_TIMESTAMP('2023-06-28 00:42:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-27T21:42:54.215Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716465 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-27T21:42:54.222Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-27T21:42:54.233Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716465
;

-- 2023-06-27T21:42:54.240Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716465)
;

-- UI Element: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> main -> 10 -> override.Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T21:43:56.741Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716465,0,543052,618100,544364,'F',TO_TIMESTAMP('2023-06-28 00:43:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tax Departure Country',35,0,0,TO_TIMESTAMP('2023-06-28 00:43:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-27T22:14:18.577Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN C_Tax_Departure_Country_ID NUMERIC(10)')
;

-- 2023-06-27T22:14:23.248Z
ALTER TABLE C_Order ADD CONSTRAINT CTaxDepartureCountry_COrder FOREIGN KEY (C_Tax_Departure_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

-- Field: Purchase Invoice Candidates_OLD(540983,de.metas.invoicecandidate) -> Invoice Candidates(543052,de.metas.invoicecandidate) -> Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T22:38:28.841Z
UPDATE AD_Field SET ReadOnlyLogic='DocStatus==''DR''',Updated=TO_TIMESTAMP('2023-06-28 01:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716465
;

-- Field: Sales Invoice Candidates(541651,de.metas.ab182) -> Invoice Candidates(546707,de.metas.ab182) -> Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T22:41:51.662Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587010,716466,0,546707,0,TO_TIMESTAMP('2023-06-28 01:41:51','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Tax Departure Country',0,610,0,1,1,TO_TIMESTAMP('2023-06-28 01:41:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-27T22:41:51.665Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716466 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-27T22:41:51.673Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-27T22:41:51.685Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716466
;

-- 2023-06-27T22:41:51.697Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716466)
;

-- Field: Sales Invoice Candidates(541651,de.metas.ab182) -> Invoice Candidates(546707,de.metas.ab182) -> Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T22:41:58.961Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-06-28 01:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716466
;

-- UI Element: Sales Invoice Candidates(541651,de.metas.ab182) -> Invoice Candidates(546707,de.metas.ab182) -> main -> 10 -> override.Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T22:42:59.776Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716466,0,546707,618101,550135,'F',TO_TIMESTAMP('2023-06-28 01:42:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tax Departure Country',90,0,0,TO_TIMESTAMP('2023-06-28 01:42:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Invoice Candidates(541651,de.metas.ab182) -> Invoice Candidates(546707,de.metas.ab182) -> main -> 10 -> override.Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T22:43:29.716Z
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2023-06-28 01:43:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618101
;

-- Field: Sales Invoice Candidates(541651,de.metas.ab182) -> Invoice Candidates(546707,de.metas.ab182) -> Tax Departure Country
-- Column: C_Invoice_Candidate.C_Tax_Departure_Country_ID
-- 2023-06-27T22:44:00.603Z
UPDATE AD_Field SET ReadOnlyLogic='DocStatus==''DR''',Updated=TO_TIMESTAMP('2023-06-28 01:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716466
;

-- Field: Sales Order(541590,de.metas.ab182) -> Order(546547,de.metas.ab182) -> Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T23:43:16.969Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587009,716467,0,546547,0,TO_TIMESTAMP('2023-06-28 02:43:16','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Tax Departure Country',0,820,0,1,1,TO_TIMESTAMP('2023-06-28 02:43:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-27T23:43:16.980Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716467 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-27T23:43:16.991Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-27T23:43:17.020Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716467
;

-- 2023-06-27T23:43:17.034Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716467)
;

-- UI Element: Sales Order(541590,de.metas.ab182) -> Order(546547,de.metas.ab182) -> main view -> 10 -> main.Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T23:43:53.812Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716467,0,546547,618102,549713,'F',TO_TIMESTAMP('2023-06-28 02:43:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tax Departure Country',190,0,0,TO_TIMESTAMP('2023-06-28 02:43:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Sales Order(541590,de.metas.ab182) -> Order(546547,de.metas.ab182) -> main view -> 10 -> main.Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T23:44:52.922Z
UPDATE AD_UI_Element SET SeqNo=75,Updated=TO_TIMESTAMP('2023-06-28 02:44:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618102
;

-- Field: Purchase Order(541595,de.metas.ab182) -> Purchase Order(546550,de.metas.ab182) -> Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T23:46:01.178Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587009,716468,0,546550,0,TO_TIMESTAMP('2023-06-28 02:46:01','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Tax Departure Country',0,280,0,1,1,TO_TIMESTAMP('2023-06-28 02:46:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-27T23:46:01.187Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716468 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-27T23:46:01.196Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-27T23:46:01.218Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716468
;

-- 2023-06-27T23:46:01.231Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716468)
;

-- UI Element: Purchase Order(541595,de.metas.ab182) -> Purchase Order(546550,de.metas.ab182) -> main -> 10 -> main.Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T23:47:05.181Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716468,0,546550,618103,549732,'F',TO_TIMESTAMP('2023-06-28 02:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tax Departure Country',140,0,0,TO_TIMESTAMP('2023-06-28 02:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order(541595,de.metas.ab182) -> Purchase Order(546550,de.metas.ab182) -> main -> 10 -> main.Tax Departure Country
-- Column: C_Order.C_Tax_Departure_Country_ID
-- 2023-06-27T23:47:29.512Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-06-28 02:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618103
;

-- Column: C_OrderLine.C_VAT_Code_ID
-- 2023-06-28T01:34:59.018Z
UPDATE AD_Column SET AD_Reference_Value_ID=541772,Updated=TO_TIMESTAMP('2023-06-28 04:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585131
;
-- Name: VAT Code for Tax Departure Country
-- 2023-06-28T01:48:55.756Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540635,'(@C_Tax_Departure_Country_ID/-1@ IN NOT NULL AND )',TO_TIMESTAMP('2023-06-28 04:48:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','VAT Code for Tax Departure Country','S',TO_TIMESTAMP('2023-06-28 04:48:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: VAT Code for Tax Departure Country
-- 2023-06-28T01:55:25.414Z
UPDATE AD_Val_Rule SET Code='(@C_Tax_Departure_Country_ID/-1@ IN NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax ON tax.C_Tax_ID=v.C_Tax_ID WHERE v.C_Country_ID=@C_Tax_Departure_Country_ID@))',Updated=TO_TIMESTAMP('2023-06-28 04:55:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540635
;

-- Name: VAT Code for Tax Departure Country
-- 2023-06-28T01:55:41.500Z
UPDATE AD_Val_Rule SET Code='(@C_Tax_Departure_Country_ID/-1@ IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax ON tax.C_Tax_ID=v.C_Tax_ID WHERE v.C_Country_ID=@C_Tax_Departure_Country_ID@))',Updated=TO_TIMESTAMP('2023-06-28 04:55:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540635
;

-- Name: VAT_Code_Override_Tax_Departure_Country
-- 2023-06-28T01:59:25.545Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540636,'(@C_Tax_Departure_Country_ID/-1@) IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax ON tax.C_Tax_ID=v.C_Tax_ID WHERE tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@))',TO_TIMESTAMP('2023-06-28 04:59:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','VAT_Code_Override_Tax_Departure_Country','S',TO_TIMESTAMP('2023-06-28 04:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: VAT_Code_Override_Tax_Departure_Country
-- 2023-06-28T01:59:52.495Z
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540636
;

-- Name: Tax override SO
-- 2023-06-28T02:00:47.578Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540637,TO_TIMESTAMP('2023-06-28 05:00:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Tax override SO','S',TO_TIMESTAMP('2023-06-28 05:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Invoice_Candidate.C_Tax_Override_ID
-- 2023-06-28T02:01:08.760Z
UPDATE AD_Column SET AD_Val_Rule_ID=540637,Updated=TO_TIMESTAMP('2023-06-28 05:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551338
;

-- Name: Tax override SO
-- 2023-06-28T02:01:22.038Z
UPDATE AD_Val_Rule SET Code='(@C_Tax_Departure_Country_ID/-1@) IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax ON tax.C_Tax_ID=v.C_Tax_ID WHERE tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@))',Updated=TO_TIMESTAMP('2023-06-28 05:01:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540637
;

-- Column: C_Invoice_Candidate.C_Tax_Override_ID
-- 2023-06-28T09:04:37.136Z
UPDATE AD_Column SET AD_Reference_Value_ID=158,Updated=TO_TIMESTAMP('2023-06-28 12:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551338
;

-- Column: C_OrderLine.C_VAT_Code_ID
-- 2023-06-28T09:04:54.897Z
UPDATE AD_Column SET AD_Reference_Value_ID=541071,Updated=TO_TIMESTAMP('2023-06-28 12:04:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585131
;

-- Name: VAT Code Tax Departure Country
-- 2023-06-28T09:27:39.294Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540639,TO_TIMESTAMP('2023-06-28 12:27:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','VAT Code Tax Departure Country','C',TO_TIMESTAMP('2023-06-28 12:27:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: VAT Code Tax Departure Country
-- 2023-06-28T09:27:54.049Z
UPDATE AD_Val_Rule SET Type='S',Updated=TO_TIMESTAMP('2023-06-28 12:27:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540639
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T09:31:55.666Z
UPDATE AD_Ref_Table SET WhereClause='(@C_Tax_Departure_Country_ID/-1@ IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where v.C_Country_ID=@C_Tax_Departure_Country_ID@))',Updated=TO_TIMESTAMP('2023-06-28 12:31:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T09:33:02.535Z
UPDATE AD_Ref_Table SET WhereClause='(@C_Tax_Departure_Country_ID/-1@ IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID@))',Updated=TO_TIMESTAMP('2023-06-28 12:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T09:34:38.624Z
UPDATE AD_Ref_Table SET WhereClause='',Updated=TO_TIMESTAMP('2023-06-28 12:34:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T09:36:24.136Z
UPDATE AD_Ref_Table SET WhereClause='(@C_Tax_Departure_Country_ID/-1@ IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@))',Updated=TO_TIMESTAMP('2023-06-28 12:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T09:41:39.723Z
UPDATE AD_Ref_Table SET WhereClause='C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@))',Updated=TO_TIMESTAMP('2023-06-28 12:41:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T09:42:27.043Z
UPDATE AD_Ref_Table SET WhereClause='C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-28 12:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T09:46:17.468Z
UPDATE AD_Ref_Table SET WhereClause='@C_Tax_Departure_Country_ID@ IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-28 12:46:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T09:48:25.370Z
UPDATE AD_Ref_Table SET WhereClause='@C_Tax_Departure_Country_ID/-1@ IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-28 12:48:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T10:07:00.096Z
UPDATE AD_Ref_Table SET WhereClause='@C_Tax_Departure_Country_ID/-1@ IS NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-28 13:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T10:08:23.800Z
UPDATE AD_Ref_Table SET WhereClause='@C_Tax_Departure_Country_ID/-1@ IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-28 13:08:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T10:09:58.799Z
UPDATE AD_Ref_Table SET WhereClause='C_VAT_Code_ID IN ((@C_Tax_Departure_Country_ID/-1@ IS NOT NULL) AND (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@))',Updated=TO_TIMESTAMP('2023-06-28 13:09:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T10:11:27.560Z
UPDATE AD_Ref_Table SET WhereClause='C_VAT_Code_ID IN ((@C_Tax_Departure_Country_ID/-1@ IS NOT NULL) AND EXISTS (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@))',Updated=TO_TIMESTAMP('2023-06-28 13:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T10:13:14.163Z
UPDATE AD_Ref_Table SET WhereClause='C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax on tax.C_Tax_ID=v.C_Tax_ID where tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@ AND @C_Tax_Departure_Country_ID/-1@ IS NOT NULL)',Updated=TO_TIMESTAMP('2023-06-28 13:13:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Name: VAT Code for Tax Departure Country
-- 2023-06-28T16:51:22.492Z
UPDATE AD_Val_Rule SET Code='(@C_Tax_Departure_Country_ID/-1@ IS NOT NULL AND C_VAT_Code_ID IN (SELECT v.C_VAT_Code_ID from C_VAT_Code v INNER JOIN C_Tax tax ON tax.C_Tax_ID=v.C_Tax_ID WHERE v.C_Country_ID=@C_Tax_Departure_Country_ID/-1@))',Updated=TO_TIMESTAMP('2023-06-28 19:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540635
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T16:55:57.622Z
UPDATE AD_Ref_Table SET WhereClause='C_Tax_ID IN (SELECT tax.C_Tax_ID from C_Tax tax where @C_Tax_Departure_Country_ID/-1@ = -1 OR tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-28 19:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

-- Reference: C_VAT_Code
-- Table: C_VAT_Code
-- Key: C_VAT_Code.C_VAT_Code_ID
-- 2023-06-28T16:57:59.566Z
UPDATE AD_Ref_Table SET WhereClause='C_Tax_ID IN (SELECT tax.C_Tax_ID from C_Tax tax where @C_Tax_Departure_Country_ID/-1@ <= 0 OR tax.C_Country_ID=@C_Tax_Departure_Country_ID/-1@)',Updated=TO_TIMESTAMP('2023-06-28 19:57:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541071
;

