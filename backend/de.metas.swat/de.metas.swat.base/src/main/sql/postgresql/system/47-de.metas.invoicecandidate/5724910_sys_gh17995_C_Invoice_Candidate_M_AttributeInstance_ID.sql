-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-05-29T12:38:33.772Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,588326,2019,0,35,540270,'M_AttributeSetInstance_ID',TO_TIMESTAMP('2024-05-29 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Merkmals Ausprägungen zum Produkt','de.metas.invoicecandidate',0,10,'The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Merkmale',0,0,'If this IC is invoiced, then its Attribute-Instances are copied towards the invoice-line, together with the intersection of the IC''s inoutLines'' Attribute-Instances.',TO_TIMESTAMP('2024-05-29 14:38:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-05-29T12:38:33.779Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588326 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-29T12:38:33.823Z
/* DDL */  select update_Column_Translation_From_AD_Element(2019) 
;

-- 2024-05-29T12:38:40.012Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN M_AttributeSetInstance_ID NUMERIC(10)')
;

-- 2024-05-29T12:38:41.192Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT MAttributeSetInstance_CInvoiceCandidate FOREIGN KEY (M_AttributeSetInstance_ID) REFERENCES public.M_AttributeSetInstance DEFERRABLE INITIALLY DEFERRED
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Merkmale
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-05-29T12:39:25.778Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588326,728769,0,540279,0,TO_TIMESTAMP('2024-05-29 14:39:25','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',0,'de.metas.invoicecandidate','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','Y','Y','N','N','N','N','N','Merkmale',0,560,0,1,1,TO_TIMESTAMP('2024-05-29 14:39:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-29T12:39:25.786Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728769 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-29T12:39:25.795Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2024-05-29T12:39:25.926Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728769
;

-- 2024-05-29T12:39:25.947Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728769)
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Merkmale
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-05-29T12:40:11.588Z
UPDATE AD_Field SET DisplayLogic='@M_AttributeSetInstance_ID/0@!0',Updated=TO_TIMESTAMP('2024-05-29 14:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728769
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.M_AttributeSetInstance_ID
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-05-29T12:42:38.826Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728769,0,540279,540057,624782,'F',TO_TIMESTAMP('2024-05-29 14:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'M_AttributeSetInstance_ID',75,0,0,TO_TIMESTAMP('2024-05-29 14:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Merkmale
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-05-31T13:07:54.445Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-05-31 15:07:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728769
;

-- Field: Rechnungsdisposition Einkauf -> Rechnungskandidaten -> Merkmale
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-05-31T13:11:11.485Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588326,728773,0,543052,0,TO_TIMESTAMP('2024-05-31 15:11:11','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',0,'@M_AttributeSetInstance_ID/0@!0','de.metas.invoicecandidate','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','Y','Y','N','N','N','Y','N','Merkmale',0,530,0,1,1,TO_TIMESTAMP('2024-05-31 15:11:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-31T13:11:11.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728773 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-31T13:11:11.524Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2024-05-31T13:11:11.554Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728773
;

-- 2024-05-31T13:11:11.557Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728773)
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungskandidaten.M_AttributeSetInstance_ID
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-05-31T13:11:49.770Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728773,0,543052,544361,624785,'F',TO_TIMESTAMP('2024-05-31 15:11:49','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'M_AttributeSetInstance_ID',75,0,0,TO_TIMESTAMP('2024-05-31 15:11:49','YYYY-MM-DD HH24:MI:SS'),100)
;
