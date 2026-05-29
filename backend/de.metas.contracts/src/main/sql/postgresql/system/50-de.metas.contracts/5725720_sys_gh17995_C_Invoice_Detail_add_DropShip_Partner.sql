-- Column: C_Invoice_Detail.DropShip_BPartner_ID
-- 2024-06-05T09:12:26.321Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588331,53458,0,18,541252,540614,'DropShip_BPartner_ID',TO_TIMESTAMP('2024-06-05 11:12:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Business Partner to ship to','de.metas.ordercandidate',0,10,'If empty the business partner will be shipped to.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferempfänger',0,0,TO_TIMESTAMP('2024-06-05 11:12:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-06-05T09:12:26.326Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588331 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-05T09:12:26.365Z
/* DDL */  select update_Column_Translation_From_AD_Element(53458) 
;

-- 2024-06-05T09:12:31.833Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Detail','ALTER TABLE public.C_Invoice_Detail ADD COLUMN DropShip_BPartner_ID NUMERIC(10)')
;

-- 2024-06-05T09:12:31.855Z
ALTER TABLE C_Invoice_Detail ADD CONSTRAINT DropShipBPartner_CInvoiceDetail FOREIGN KEY (DropShip_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice_Detail.DropShip_Location_ID
-- 2024-06-05T09:13:02.143Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588332,53459,0,18,159,540614,'DropShip_Location_ID',TO_TIMESTAMP('2024-06-05 11:13:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Business Partner Location for shipping to','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferadresse',0,0,TO_TIMESTAMP('2024-06-05 11:13:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-06-05T09:13:02.146Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588332 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-05T09:13:02.152Z
/* DDL */  select update_Column_Translation_From_AD_Element(53459) 
;

-- 2024-06-05T09:13:04.106Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Detail','ALTER TABLE public.C_Invoice_Detail ADD COLUMN DropShip_Location_ID NUMERIC(10)')
;

-- 2024-06-05T09:13:04.114Z
ALTER TABLE C_Invoice_Detail ADD CONSTRAINT DropShipLocation_CInvoiceDetail FOREIGN KEY (DropShip_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice_Detail.DropShip_User_ID
-- 2024-06-05T09:13:42.320Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588333,53460,0,18,110,540614,'DropShip_User_ID',TO_TIMESTAMP('2024-06-05 11:13:42','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferkontakt',0,0,TO_TIMESTAMP('2024-06-05 11:13:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-06-05T09:13:42.323Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588333 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-05T09:13:42.328Z
/* DDL */  select update_Column_Translation_From_AD_Element(53460) 
;

-- 2024-06-05T09:13:42.998Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Detail','ALTER TABLE public.C_Invoice_Detail ADD COLUMN DropShip_User_ID NUMERIC(10)')
;

-- 2024-06-05T09:13:43.004Z
ALTER TABLE C_Invoice_Detail ADD CONSTRAINT DropShipUser_CInvoiceDetail FOREIGN KEY (DropShip_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2024-06-05T09:19:14.735Z
INSERT INTO t_alter_column values('c_invoice_detail','DropShip_BPartner_ID','NUMERIC(10)',null,null)
;

-- Column: C_Invoice_Detail.DropShip_BPartner_ID
-- 2024-06-05T09:20:10.489Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-06-05 11:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588331
;

-- 2024-06-05T09:21:12.861Z
INSERT INTO t_alter_column values('c_invoice_detail','DropShip_BPartner_ID','NUMERIC(10)',null,null)
;

-- Field: Rechnungsdisposition_OLD -> Rechnungszeilendetails -> Lieferempfänger
-- Column: C_Invoice_Detail.DropShip_BPartner_ID
-- 2024-06-05T09:25:06.593Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588331,728988,0,540622,TO_TIMESTAMP('2024-06-05 11:25:06','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to ship to',10,'de.metas.invoicecandidate','If empty the business partner will be shipped to.','Y','Y','N','N','N','N','N','Lieferempfänger',TO_TIMESTAMP('2024-06-05 11:25:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-05T09:25:06.598Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-05T09:25:06.602Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53458) 
;

-- 2024-06-05T09:25:06.627Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728988
;

-- 2024-06-05T09:25:06.632Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728988)
;

-- Field: Rechnungsdisposition_OLD -> Rechnungszeilendetails -> Lieferadresse
-- Column: C_Invoice_Detail.DropShip_Location_ID
-- 2024-06-05T09:25:06.739Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588332,728989,0,540622,TO_TIMESTAMP('2024-06-05 11:25:06','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Location for shipping to',10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Lieferadresse',TO_TIMESTAMP('2024-06-05 11:25:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-05T09:25:06.742Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-05T09:25:06.746Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53459) 
;

-- 2024-06-05T09:25:06.762Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728989
;

-- 2024-06-05T09:25:06.764Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728989)
;

-- Field: Rechnungsdisposition_OLD -> Rechnungszeilendetails -> Lieferkontakt
-- Column: C_Invoice_Detail.DropShip_User_ID
-- 2024-06-05T09:25:06.869Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588333,728990,0,540622,TO_TIMESTAMP('2024-06-05 11:25:06','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','Y','Y','N','N','N','N','N','Lieferkontakt',TO_TIMESTAMP('2024-06-05 11:25:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-05T09:25:06.871Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-05T09:25:06.875Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53460) 
;

-- 2024-06-05T09:25:06.884Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728990
;

-- 2024-06-05T09:25:06.886Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728990)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungszeilendetails.Mandant
-- Column: C_Invoice_Detail.AD_Client_ID
-- 2024-06-05T09:25:41.491Z
UPDATE AD_UI_Element SET SeqNo=250,Updated=TO_TIMESTAMP('2024-06-05 11:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549107
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungszeilendetails.Sektion
-- Column: C_Invoice_Detail.AD_Org_ID
-- 2024-06-05T09:25:47.535Z
UPDATE AD_UI_Element SET SeqNo=240,Updated=TO_TIMESTAMP('2024-06-05 11:25:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549106
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungszeilendetails.DropShip_BPartner_ID
-- Column: C_Invoice_Detail.DropShip_BPartner_ID
-- 2024-06-05T09:26:22.907Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728988,0,540622,1000046,624931,'F',TO_TIMESTAMP('2024-06-05 11:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to ship to','If empty the business partner will be shipped to.','Y','N','N','Y','N','N','N',0,'DropShip_BPartner_ID',210,0,0,TO_TIMESTAMP('2024-06-05 11:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungszeilendetails.DropShip_Location_ID
-- Column: C_Invoice_Detail.DropShip_Location_ID
-- 2024-06-05T09:26:54.263Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728989,0,540622,1000046,624932,'F',TO_TIMESTAMP('2024-06-05 11:26:54','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Location for shipping to','Y','N','N','Y','N','N','N',0,'DropShip_Location_ID',220,0,0,TO_TIMESTAMP('2024-06-05 11:26:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungszeilendetails.DropShip_User_ID
-- Column: C_Invoice_Detail.DropShip_User_ID
-- 2024-06-05T09:27:15.851Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728990,0,540622,1000046,624933,'F',TO_TIMESTAMP('2024-06-05 11:27:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DropShip_User_ID',230,0,0,TO_TIMESTAMP('2024-06-05 11:27:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungszeilendetails.DropShip_BPartner_ID
-- Column: C_Invoice_Detail.DropShip_BPartner_ID
-- 2024-06-05T09:27:30.001Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2024-06-05 11:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624931
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungszeilendetails.DropShip_Location_ID
-- Column: C_Invoice_Detail.DropShip_Location_ID
-- 2024-06-05T09:27:30.012Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2024-06-05 11:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624932
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungszeilendetails.DropShip_User_ID
-- Column: C_Invoice_Detail.DropShip_User_ID
-- 2024-06-05T09:27:30.024Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2024-06-05 11:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624933
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungszeilendetails.Sektion
-- Column: C_Invoice_Detail.AD_Org_ID
-- 2024-06-05T09:27:30.036Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2024-06-05 11:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549106
;

