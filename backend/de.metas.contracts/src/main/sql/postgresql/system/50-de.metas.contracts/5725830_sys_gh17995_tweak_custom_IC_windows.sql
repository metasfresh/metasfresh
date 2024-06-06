--
-- insert it into the custom window
--

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Merkmale
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-06-06T07:34:34.955Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588326,728994,0,543624,0,TO_TIMESTAMP('2024-06-06 09:34:34','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',0,'@M_AttributeSetInstance_ID/0@!0','U','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','Y','Y','N','N','N','N','N','Merkmale',0,540,0,1,1,TO_TIMESTAMP('2024-06-06 09:34:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-06T07:34:34.964Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-06T07:34:34.968Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2024-06-06T07:34:35.053Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728994
;

-- 2024-06-06T07:34:35.058Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728994)
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.M_AttributeSetInstance_ID
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-06-06T07:35:10.319Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728994,0,543624,545250,624938,'F',TO_TIMESTAMP('2024-06-06 09:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'M_AttributeSetInstance_ID',75,0,0,TO_TIMESTAMP('2024-06-06 09:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Rechnungsdisposition -> Rechnungskandidaten -> Merkmale
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-06-06T07:37:39.909Z
UPDATE AD_Field SET EntityType='de.metas.customer.ma193', IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-06-06 09:37:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728994
;

-- Field: Rechnungsdisposition Einkauf -> Rechnungskandidaten -> Merkmale
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-06-06T07:40:39.203Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588326,728995,0,543648,0,TO_TIMESTAMP('2024-06-06 09:40:39','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',0,'@M_AttributeSetInstance_ID/0@!0','de.metas.customer.ma193','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','Y','Y','N','N','N','Y','N','Merkmale',0,530,0,1,1,TO_TIMESTAMP('2024-06-06 09:40:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-06T07:40:39.205Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728995 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-06T07:40:39.209Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2024-06-06T07:40:39.223Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728995
;

-- 2024-06-06T07:40:39.226Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728995)
;

-- UI Element: Rechnungsdisposition Einkauf -> Rechnungskandidaten.M_AttributeSetInstance_ID
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2024-06-06T07:41:22.003Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728995,0,543648,545299,624939,'F',TO_TIMESTAMP('2024-06-06 09:41:21','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'M_AttributeSetInstance_ID',75,0,0,TO_TIMESTAMP('2024-06-06 09:41:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.zur Verrechnung
-- Column: C_Invoice_Candidate.IsToClear
-- 2024-06-06T07:45:19.071Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2024-06-06 09:45:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=580552
;

-- UI Element: Rechnungsdisposition -> Rechnungskandidaten.zur Verrechnung
-- Column: C_Invoice_Candidate.IsToClear
-- 2024-06-06T07:45:34.604Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545254, SeqNo=60,Updated=TO_TIMESTAMP('2024-06-06 09:45:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=580552
;

