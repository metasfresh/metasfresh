-- Name: M_MatchInv_Type
-- 2023-02-13T13:19:44.285Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541716,TO_TIMESTAMP('2023-02-13 15:19:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_MatchInv_Type',TO_TIMESTAMP('2023-02-13 15:19:43','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-02-13T13:19:44.288Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541716 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_MatchInv_Type
-- Value: M
-- ValueName: Material
-- 2023-02-13T13:20:01.835Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541716,543406,TO_TIMESTAMP('2023-02-13 15:20:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Material',TO_TIMESTAMP('2023-02-13 15:20:01','YYYY-MM-DD HH24:MI:SS'),100,'M','Material')
;

-- 2023-02-13T13:20:01.838Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543406 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: M_MatchInv_Type
-- Value: C
-- ValueName: Cost
-- 2023-02-13T13:20:17.747Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541716,543407,TO_TIMESTAMP('2023-02-13 15:20:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost',TO_TIMESTAMP('2023-02-13 15:20:17','YYYY-MM-DD HH24:MI:SS'),100,'C','Cost')
;

-- 2023-02-13T13:20:17.749Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543407 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: M_MatchInv.Type
-- 2023-02-13T13:20:38.390Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586042,600,0,17,541716,472,'Type',TO_TIMESTAMP('2023-02-13 15:20:38','YYYY-MM-DD HH24:MI:SS'),100,'N','M','Type of Validation (SQL, Java Script, Java Language)','D',0,1,'The Type indicates the type of validation that will occur.  This can be SQL, Java Script or Java Language.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Type',0,0,TO_TIMESTAMP('2023-02-13 15:20:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T13:20:38.393Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586042 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T13:20:38.427Z
/* DDL */  select update_Column_Translation_From_AD_Element(600) 
;

-- 2023-02-13T13:20:43.935Z
/* DDL */ SELECT public.db_alter_table('M_MatchInv','ALTER TABLE public.M_MatchInv ADD COLUMN Type CHAR(1) DEFAULT ''M'' NOT NULL')
;

-- Column: M_MatchInv.M_InOut_Cost_ID
-- 2023-02-13T13:21:30.436Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586043,582043,0,30,472,'M_InOut_Cost_ID',TO_TIMESTAMP('2023-02-13 15:21:30','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Shipment/Receipt Costs',0,0,TO_TIMESTAMP('2023-02-13 15:21:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T13:21:30.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586043 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T13:21:30.441Z
/* DDL */  select update_Column_Translation_From_AD_Element(582043) 
;

-- 2023-02-13T13:21:32.908Z
/* DDL */ SELECT public.db_alter_table('M_MatchInv','ALTER TABLE public.M_MatchInv ADD COLUMN M_InOut_Cost_ID NUMERIC(10)')
;

-- 2023-02-13T13:21:33.045Z
ALTER TABLE M_MatchInv ADD CONSTRAINT MInOutCost_MMatchInv FOREIGN KEY (M_InOut_Cost_ID) REFERENCES public.M_InOut_Cost DEFERRABLE INITIALLY DEFERRED
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Sales Transaction
-- Column: M_MatchInv.IsSOTrx
-- 2023-02-13T13:21:51.155Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551869,712370,0,408,TO_TIMESTAMP('2023-02-13 15:21:50','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction',1,'D','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Sales Transaction',TO_TIMESTAMP('2023-02-13 15:21:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T13:21:51.159Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712370 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T13:21:51.164Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2023-02-13T13:21:51.207Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712370
;

-- 2023-02-13T13:21:51.213Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712370)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> UOM
-- Column: M_MatchInv.C_UOM_ID
-- 2023-02-13T13:21:51.308Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568542,712371,0,408,TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Measure',10,'D','The UOM defines a unique non monetary Unit of Measure','Y','N','N','N','N','N','N','N','UOM',TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T13:21:51.310Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712371 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T13:21:51.312Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-02-13T13:21:51.484Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712371
;

-- 2023-02-13T13:21:51.486Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712371)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Quantity in UOM
-- Column: M_MatchInv.QtyInUOM
-- 2023-02-13T13:21:51.583Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568543,712372,0,408,TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Quantity in UOM',TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T13:21:51.585Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712372 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T13:21:51.586Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576984) 
;

-- 2023-02-13T13:21:51.591Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712372
;

-- 2023-02-13T13:21:51.591Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712372)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Posting Error
-- Column: M_MatchInv.PostingError_Issue_ID
-- 2023-02-13T13:21:51.682Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570873,712373,0,408,TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Posting Error',TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T13:21:51.684Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712373 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T13:21:51.685Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
;

-- 2023-02-13T13:21:51.697Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712373
;

-- 2023-02-13T13:21:51.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712373)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Type
-- Column: M_MatchInv.Type
-- 2023-02-13T13:21:51.796Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586042,712374,0,408,TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100,'Type of Validation (SQL, Java Script, Java Language)',1,'D','The Type indicates the type of validation that will occur.  This can be SQL, Java Script or Java Language.','Y','N','N','N','N','N','N','N','Type',TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T13:21:51.798Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712374 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T13:21:51.801Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2023-02-13T13:21:51.848Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712374
;

-- 2023-02-13T13:21:51.849Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712374)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Shipment/Receipt Costs
-- Column: M_MatchInv.M_InOut_Cost_ID
-- 2023-02-13T13:21:51.942Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586043,712375,0,408,TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Shipment/Receipt Costs',TO_TIMESTAMP('2023-02-13 15:21:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T13:21:51.944Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712375 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T13:21:51.945Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582043) 
;

-- 2023-02-13T13:21:51.952Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712375
;

-- 2023-02-13T13:21:51.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712375)
;

-- 2023-02-13T13:22:54.386Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712370
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Sales Transaction
-- Column: M_MatchInv.IsSOTrx
-- 2023-02-13T13:22:54.392Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712370
;

-- 2023-02-13T13:22:54.398Z
DELETE FROM AD_Field WHERE AD_Field_ID=712370
;

-- 2023-02-13T13:22:56.442Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712371
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> UOM
-- Column: M_MatchInv.C_UOM_ID
-- 2023-02-13T13:22:56.446Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712371
;

-- 2023-02-13T13:22:56.452Z
DELETE FROM AD_Field WHERE AD_Field_ID=712371
;

-- 2023-02-13T13:22:59.756Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712372
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Quantity in UOM
-- Column: M_MatchInv.QtyInUOM
-- 2023-02-13T13:22:59.760Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712372
;

-- 2023-02-13T13:22:59.765Z
DELETE FROM AD_Field WHERE AD_Field_ID=712372
;

-- 2023-02-13T13:23:01.913Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712373
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Posting Error
-- Column: M_MatchInv.PostingError_Issue_ID
-- 2023-02-13T13:23:01.917Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712373
;

-- 2023-02-13T13:23:01.921Z
DELETE FROM AD_Field WHERE AD_Field_ID=712373
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_MatchInv.M_InOut_ID
-- 2023-02-13T13:36:19.723Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2023-02-13 15:36:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605229
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Rechnungsposition
-- Column: M_MatchInv.C_InvoiceLine_ID
-- 2023-02-13T13:36:57.786Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542906, IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2023-02-13 15:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561876
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Type
-- Column: M_MatchInv.Type
-- 2023-02-13T13:37:34.945Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712374,0,408,542906,615723,'F',TO_TIMESTAMP('2023-02-13 15:37:34','YYYY-MM-DD HH24:MI:SS'),100,'Type of Validation (SQL, Java Script, Java Language)','The Type indicates the type of validation that will occur.  This can be SQL, Java Script or Java Language.','Y','N','Y','N','N','Type',70,0,0,TO_TIMESTAMP('2023-02-13 15:37:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Shipment/Receipt Costs
-- Column: M_MatchInv.M_InOut_Cost_ID
-- 2023-02-13T13:37:40.544Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712375,0,408,542906,615724,'F',TO_TIMESTAMP('2023-02-13 15:37:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Shipment/Receipt Costs',80,0,0,TO_TIMESTAMP('2023-02-13 15:37:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Abgleich Rechnung
-- Column: M_MatchInv.M_MatchInv_ID
-- 2023-02-13T13:37:56.886Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=561869
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Type
-- Column: M_MatchInv.Type
-- 2023-02-13T13:38:01.613Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-02-13 15:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615723
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Rechnung
-- Column: M_MatchInv.C_Invoice_ID
-- 2023-02-13T13:38:14.265Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-02-13 15:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605228
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Rechnungsposition
-- Column: M_MatchInv.C_InvoiceLine_ID
-- 2023-02-13T13:38:21.880Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-02-13 15:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561876
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_MatchInv.M_InOut_ID
-- 2023-02-13T13:38:25.988Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-02-13 15:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605229
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Wareneingangs- Position
-- Column: M_MatchInv.M_InOutLine_ID
-- 2023-02-13T13:38:32.959Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2023-02-13 15:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561865
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Shipment/Receipt Costs
-- Column: M_MatchInv.M_InOut_Cost_ID
-- 2023-02-13T13:38:37.755Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2023-02-13 15:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615724
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Produkt
-- Column: M_MatchInv.M_Product_ID
-- 2023-02-13T13:38:41.032Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2023-02-13 15:38:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561866
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Shipment/Receipt Costs
-- Column: M_MatchInv.M_InOut_Cost_ID
-- 2023-02-13T13:39:11.823Z
UPDATE AD_Field SET DisplayLogic='@Type/X@=C',Updated=TO_TIMESTAMP('2023-02-13 15:39:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712375
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Shipment/Receipt Costs
-- Column: M_MatchInv.M_InOut_Cost_ID
-- 2023-02-13T13:39:17.156Z
UPDATE AD_Field SET DisplayLogic='@Type/-@=C',Updated=TO_TIMESTAMP('2023-02-13 15:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712375
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Type
-- Column: M_MatchInv.Type
-- 2023-02-13T13:39:46.422Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615723
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Rechnungsposition
-- Column: M_MatchInv.C_InvoiceLine_ID
-- 2023-02-13T13:39:46.432Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561876
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Wareneingangs- Position
-- Column: M_MatchInv.M_InOutLine_ID
-- 2023-02-13T13:39:46.440Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561865
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Shipment/Receipt Costs
-- Column: M_MatchInv.M_InOut_Cost_ID
-- 2023-02-13T13:39:46.447Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615724
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Produkt
-- Column: M_MatchInv.M_Product_ID
-- 2023-02-13T13:39:46.453Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561866
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> description.Menge
-- Column: M_MatchInv.Qty
-- 2023-02-13T13:39:46.460Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561877
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> description.Merkmale
-- Column: M_MatchInv.M_AttributeSetInstance_ID
-- 2023-02-13T13:39:46.465Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561867
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 20 -> flags.Aktiv
-- Column: M_MatchInv.IsActive
-- 2023-02-13T13:39:46.470Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561870
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 20 -> posted.Verbucht
-- Column: M_MatchInv.Posted
-- 2023-02-13T13:39:46.476Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561872
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 20 -> org.Sektion
-- Column: M_MatchInv.AD_Org_ID
-- 2023-02-13T13:39:46.480Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561874
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_MatchInv.M_InOut_ID
-- 2023-02-13T13:39:46.485Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605229
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Rechnung
-- Column: M_MatchInv.C_Invoice_ID
-- 2023-02-13T13:39:46.490Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-02-13 15:39:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605228
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 20 -> flags.Löschen
-- Column: M_MatchInv.Processing
-- 2023-02-13T13:40:08.446Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=561873
;

-- Column: M_MatchInv.C_Invoice_ID
-- 2023-02-13T13:43:05.823Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-13 15:43:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551380
;

