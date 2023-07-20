-- Column: Fact_Acct.C_Order_ID
-- 2022-12-15T14:52:26.203Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585373,558,0,30,270,'C_Order_ID',TO_TIMESTAMP('2022-12-15 16:52:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Order','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sales order',0,0,TO_TIMESTAMP('2022-12-15 16:52:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T14:52:26.232Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585373 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T14:52:26.289Z
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- 2022-12-15T14:52:30.577Z
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2022-12-15T14:52:30.726Z
ALTER TABLE Fact_Acct ADD CONSTRAINT COrder_FactAcct FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Line ID
-- Column: Fact_Acct_Transactions_View.Line_ID
-- 2022-12-15T14:53:11.774Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571101,710011,0,242,TO_TIMESTAMP('2022-12-15 16:53:11','YYYY-MM-DD HH24:MI:SS'),100,'Transaction line ID (internal)',10,'D','Internal link','Y','Y','N','N','N','N','N','Line ID',TO_TIMESTAMP('2022-12-15 16:53:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:11.801Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:53:11.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1738) 
;

-- 2022-12-15T14:53:11.860Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710011
;

-- 2022-12-15T14:53:11.887Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710011)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Asset
-- Column: Fact_Acct_Transactions_View.A_Asset_ID
-- 2022-12-15T14:53:12.397Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571104,710012,0,242,TO_TIMESTAMP('2022-12-15 16:53:11','YYYY-MM-DD HH24:MI:SS'),100,'Asset used internally or by customers',10,'D','An asset is either created by purchasing or by delivering a product.  An asset can be used internally or be a customer asset.','Y','Y','N','N','N','N','N','Asset',TO_TIMESTAMP('2022-12-15 16:53:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:12.424Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:53:12.452Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1884) 
;

-- 2022-12-15T14:53:12.496Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710012
;

-- 2022-12-15T14:53:12.524Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710012)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Project Phase
-- Column: Fact_Acct_Transactions_View.C_ProjectPhase_ID
-- 2022-12-15T14:53:12.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571105,710013,0,242,TO_TIMESTAMP('2022-12-15 16:53:12','YYYY-MM-DD HH24:MI:SS'),100,'Phase of a Project',10,'D','Y','Y','N','N','N','N','N','Project Phase',TO_TIMESTAMP('2022-12-15 16:53:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:13.017Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:53:13.045Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2073) 
;

-- 2022-12-15T14:53:13.084Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710013
;

-- 2022-12-15T14:53:13.153Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710013)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Project Task
-- Column: Fact_Acct_Transactions_View.C_ProjectTask_ID
-- 2022-12-15T14:53:13.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571106,710014,0,242,TO_TIMESTAMP('2022-12-15 16:53:13','YYYY-MM-DD HH24:MI:SS'),100,'Actual Project Task in a Phase',10,'D','A Project Task in a Project Phase represents the actual work.','Y','Y','N','N','N','N','N','Project Task',TO_TIMESTAMP('2022-12-15 16:53:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:13.655Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:53:13.682Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2074) 
;

-- 2022-12-15T14:53:13.718Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710014
;

-- 2022-12-15T14:53:13.745Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710014)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> SubLine ID
-- Column: Fact_Acct_Transactions_View.SubLine_ID
-- 2022-12-15T14:53:14.218Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571107,710015,0,242,TO_TIMESTAMP('2022-12-15 16:53:13','YYYY-MM-DD HH24:MI:SS'),100,'Transaction sub line ID (internal)',10,'D','Y','Y','N','N','N','N','N','SubLine ID',TO_TIMESTAMP('2022-12-15 16:53:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:14.246Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:53:14.277Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542922) 
;

-- 2022-12-15T14:53:14.307Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710015
;

-- 2022-12-15T14:53:14.334Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710015)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Document Type
-- Column: Fact_Acct_Transactions_View.C_DocType_ID
-- 2022-12-15T14:53:14.797Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571108,710016,0,242,TO_TIMESTAMP('2022-12-15 16:53:14','YYYY-MM-DD HH24:MI:SS'),100,'Document type or rules',10,'D','The Document Type determines document sequence and processing rules','Y','Y','N','N','N','N','N','Document Type',TO_TIMESTAMP('2022-12-15 16:53:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:14.824Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:53:14.854Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2022-12-15T14:53:14.901Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710016
;

-- 2022-12-15T14:53:14.928Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710016)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Document Base Type
-- Column: Fact_Acct_Transactions_View.DocBaseType
-- 2022-12-15T14:53:15.444Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571109,710017,0,242,TO_TIMESTAMP('2022-12-15 16:53:14','YYYY-MM-DD HH24:MI:SS'),100,'Logical type of document',3,'D','The Document Base Type identifies the base or starting point for a document.  Multiple document types may share a single document base type.','Y','Y','N','N','N','N','N','Document Base Type',TO_TIMESTAMP('2022-12-15 16:53:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:15.471Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:53:15.499Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(865) 
;

-- 2022-12-15T14:53:15.532Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710017
;

-- 2022-12-15T14:53:15.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710017)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> VAT Code
-- Column: Fact_Acct_Transactions_View.VATCode
-- 2022-12-15T14:53:16.025Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571110,710018,0,242,TO_TIMESTAMP('2022-12-15 16:53:15','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','VAT Code',TO_TIMESTAMP('2022-12-15 16:53:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:16.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:53:16.081Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542959) 
;

-- 2022-12-15T14:53:16.158Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710018
;

-- 2022-12-15T14:53:16.186Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710018)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Counterpart Accounting Fact
-- Column: Fact_Acct_Transactions_View.Counterpart_Fact_Acct_ID
-- 2022-12-15T14:53:16.680Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571111,710019,0,242,TO_TIMESTAMP('2022-12-15 16:53:16','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Counterpart Accounting Fact',TO_TIMESTAMP('2022-12-15 16:53:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:53:16.707Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:53:16.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543871) 
;

-- 2022-12-15T14:53:16.765Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710019
;

-- 2022-12-15T14:53:16.791Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710019)
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Material
-- Column: Fact_Acct_Transactions_View.M_Product_ID
-- 2022-12-15T15:08:23.735Z
UPDATE AD_Field SET AD_Name_ID=581748, Description=NULL, Help=NULL, Name='Material',Updated=TO_TIMESTAMP('2022-12-15 17:08:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2363
;

-- 2022-12-15T15:08:23.771Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581748) 
;

-- 2022-12-15T15:08:23.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=2363
;

-- 2022-12-15T15:08:23.837Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(2363)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 10 -> default.Section Code
-- Column: Fact_Acct_Transactions_View.M_SectionCode_ID
-- 2022-12-15T15:09:04.028Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611366
;

