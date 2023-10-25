-- Column: C_OLCand.C_Auction_ID
-- 2023-09-12T10:37:23.282474900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587461,582634,0,19,540244,'C_Auction_ID',TO_TIMESTAMP('2023-09-12 13:37:22.975','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.ordercandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Auktion',0,0,TO_TIMESTAMP('2023-09-12 13:37:22.975','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-12T10:37:23.291383900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587461 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-12T10:37:23.325055100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582634) 
;

-- 2023-09-12T10:37:27.522227100Z
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN C_Auction_ID NUMERIC(10)')
;

-- 2023-09-12T10:37:27.602154500Z
ALTER TABLE C_OLCand ADD CONSTRAINT CAuction_COLCand FOREIGN KEY (C_Auction_ID) REFERENCES public.C_Auction DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice_Candidate.C_Auction_ID
-- 2023-09-12T10:38:51.769510200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587463,582634,0,19,540270,'C_Auction_ID',TO_TIMESTAMP('2023-09-12 13:38:51.631','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auktion',0,0,TO_TIMESTAMP('2023-09-12 13:38:51.631','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-12T10:38:51.770575700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587463 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-12T10:38:51.773208100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582634) 
;

-- 2023-09-12T10:39:01.618459100Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_Auction_ID NUMERIC(10)')
;

-- 2023-09-12T10:39:01.845837400Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT CAuction_CInvoiceCandidate FOREIGN KEY (C_Auction_ID) REFERENCES public.C_Auction DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice.C_Auction_ID
-- 2023-09-12T10:39:31.956732800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587464,582634,0,19,318,'C_Auction_ID',TO_TIMESTAMP('2023-09-12 13:39:31.836','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auktion',0,0,TO_TIMESTAMP('2023-09-12 13:39:31.836','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-12T10:39:31.958304400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587464 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-12T10:39:31.960444800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582634) 
;

-- 2023-09-12T10:39:35.652083900Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN C_Auction_ID NUMERIC(10)')
;

-- 2023-09-12T10:39:36.386153400Z
ALTER TABLE C_Invoice ADD CONSTRAINT CAuction_CInvoice FOREIGN KEY (C_Auction_ID) REFERENCES public.C_Auction DEFERRABLE INITIALLY DEFERRED
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Auktion
-- Column: C_OLCand.C_Auction_ID
-- 2023-09-13T08:32:03.550373600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587461,720475,0,540282,0,TO_TIMESTAMP('2023-09-13 11:32:03.267','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','N','Auktion',0,470,0,1,1,TO_TIMESTAMP('2023-09-13 11:32:03.267','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-13T08:32:03.559307700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720475 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-13T08:32:03.591636Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582634) 
;

-- 2023-09-13T08:32:03.603304100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720475
;

-- 2023-09-13T08:32:03.604344400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720475)
;

-- UI Element: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Auktion
-- Column: C_OLCand.C_Auction_ID
-- 2023-09-13T08:32:31.042926Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720475,0,540282,540962,620466,'F',TO_TIMESTAMP('2023-09-13 11:32:30.889','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Auktion',700,0,0,TO_TIMESTAMP('2023-09-13 11:32:30.889','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Auftrag_OLD(143,D) -> Auftrag(186,D) -> Auktion
-- Column: C_Order.C_Auction_ID
-- 2023-09-13T08:33:37.291368400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587270,720476,0,186,0,TO_TIMESTAMP('2023-09-13 11:33:37.168','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','N','Auktion',0,760,0,1,1,TO_TIMESTAMP('2023-09-13 11:33:37.168','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-13T08:33:37.292433100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720476 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-13T08:33:37.293991200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582634) 
;

-- 2023-09-13T08:33:37.297211200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720476
;

-- 2023-09-13T08:33:37.297741700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720476)
;

-- UI Element: Auftrag_OLD(143,D) -> Auftrag(186,D) -> advanced edit -> 10 -> advanced edit.Auktion
-- Column: C_Order.C_Auction_ID
-- 2023-09-13T08:33:56.391315700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720476,0,186,540499,620467,'F',TO_TIMESTAMP('2023-09-13 11:33:56.253','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Auktion',470,0,0,TO_TIMESTAMP('2023-09-13 11:33:56.253','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Auktion
-- Column: C_Invoice_Candidate.C_Auction_ID
-- 2023-09-13T08:35:36.566961700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587463,720477,0,540279,0,TO_TIMESTAMP('2023-09-13 11:35:36.378','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Auktion',0,580,0,1,1,TO_TIMESTAMP('2023-09-13 11:35:36.378','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-13T08:35:36.568021600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720477 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-13T08:35:36.569063800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582634) 
;

-- 2023-09-13T08:35:36.571308300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720477
;

-- 2023-09-13T08:35:36.571839700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720477)
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Auktion
-- Column: C_Invoice_Candidate.C_Auction_ID
-- 2023-09-13T08:36:06.404231600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720477,0,540279,540056,620468,'F',TO_TIMESTAMP('2023-09-13 11:36:06.288','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Auktion',1060,0,0,TO_TIMESTAMP('2023-09-13 11:36:06.288','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Auktion
-- Column: C_OLCand.C_Auction_ID
-- 2023-09-13T08:36:42.063545500Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-09-13 11:36:42.063','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620466
;

-- Field: Rechnung_OLD(167,D) -> Rechnung(263,D) -> Auktion
-- Column: C_Invoice.C_Auction_ID
-- 2023-09-13T08:38:13.434798300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587464,720478,0,263,0,TO_TIMESTAMP('2023-09-13 11:38:13.286','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Auktion',0,540,0,1,1,TO_TIMESTAMP('2023-09-13 11:38:13.286','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-13T08:38:13.435848300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720478 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-13T08:38:13.437395300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582634) 
;

-- 2023-09-13T08:38:13.439512900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720478
;

-- 2023-09-13T08:38:13.440094300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720478)
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Auktion
-- Column: C_Invoice.C_Auction_ID
-- 2023-09-13T08:38:29.650877500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720478,0,263,541214,620469,'F',TO_TIMESTAMP('2023-09-13 11:38:29.5','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Auktion',110,0,0,TO_TIMESTAMP('2023-09-13 11:38:29.5','YYYY-MM-DD HH24:MI:SS.US'),100)
;

