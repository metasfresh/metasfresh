-- Column: C_BP_BankAccount.SwiftCode
-- 2022-04-13T14:11:56.944Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582766,973,0,10,298,'SwiftCode',TO_TIMESTAMP('2022-04-13 15:11:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Swift Code or BIC','D',0,20,'The Swift Code (Society of Worldwide Interbank Financial Telecommunications) or BIC (Bank Identifier Code) is an identifier of a Bank. The first 4 characters are the bank code, followed by the 2 character country code, the two character location code and optional 3 character branch code. For details see http://www.swift.com/biconline/index.cfm','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Swift code',0,0,TO_TIMESTAMP('2022-04-13 15:11:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T14:11:57.015Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582766 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T14:11:57.175Z
/* DDL */  select update_Column_Translation_From_AD_Element(973) 
;

-- 2022-04-13T14:12:16.936Z
/* DDL */ SELECT public.db_alter_table('C_BP_BankAccount','ALTER TABLE public.C_BP_BankAccount ADD COLUMN SwiftCode VARCHAR(20)')
;

-- Field: Bankkonto -> Konto -> Swift code
-- Column: C_BP_BankAccount.SwiftCode
-- 2022-04-13T14:18:15.948Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582766,691674,0,540812,0,TO_TIMESTAMP('2022-04-13 15:18:12','YYYY-MM-DD HH24:MI:SS'),100,'Swift Code or BIC',0,'U','The Swift Code (Society of Worldwide Interbank Financial Telecommunications) or BIC (Bank Identifier Code) is an identifier of a Bank. The first 4 characters are the bank code, followed by the 2 character country code, the two character location code and optional 3 character branch code. For details see http://www.swift.com/biconline/index.cfm',0,'Y','Y','Y','N','N','N','N','N','Swift code',0,340,0,1,1,TO_TIMESTAMP('2022-04-13 15:18:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T14:18:16.023Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691674 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-13T14:18:16.098Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(973) 
;

-- 2022-04-13T14:18:16.193Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691674
;

-- 2022-04-13T14:18:16.275Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691674)
;

-- Field: Bankkonto -> Konto -> Swift code
-- Column: C_BP_BankAccount.SwiftCode
-- 2022-04-13T14:18:41.693Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2022-04-13 15:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691674
;

-- UI Element: Bankkonto -> Konto.Swift code
-- Column: C_BP_BankAccount.SwiftCode
-- 2022-04-13T14:20:24.868Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691674,0,540812,540335,605326,'F',TO_TIMESTAMP('2022-04-13 15:20:24','YYYY-MM-DD HH24:MI:SS'),100,'Swift Code or BIC','The Swift Code (Society of Worldwide Interbank Financial Telecommunications) or BIC (Bank Identifier Code) is an identifier of a Bank. The first 4 characters are the bank code, followed by the 2 character country code, the two character location code and optional 3 character branch code. For details see http://www.swift.com/biconline/index.cfm','Y','N','N','Y','N','N','N',0,'Swift code',32,0,0,TO_TIMESTAMP('2022-04-13 15:20:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Geschäftspartner -> Bankkonto -> Swift code
-- Column: C_BP_BankAccount.SwiftCode
-- 2022-04-13T14:25:29.582Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582766,691675,0,226,0,TO_TIMESTAMP('2022-04-13 15:25:28','YYYY-MM-DD HH24:MI:SS'),100,'Swift Code or BIC',0,'D','The Swift Code (Society of Worldwide Interbank Financial Telecommunications) or BIC (Bank Identifier Code) is an identifier of a Bank. The first 4 characters are the bank code, followed by the 2 character country code, the two character location code and optional 3 character branch code. For details see http://www.swift.com/biconline/index.cfm',0,'Y','Y','Y','N','N','N','N','N','Swift code',0,300,0,1,1,TO_TIMESTAMP('2022-04-13 15:25:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T14:25:29.689Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691675 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-13T14:25:29.753Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(973) 
;

-- 2022-04-13T14:25:29.846Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691675
;

-- 2022-04-13T14:25:29.936Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691675)
;

-- UI Element: Geschäftspartner -> Bankkonto.QR IBAN
-- Column: C_BP_BankAccount.QR_IBAN
-- 2022-04-13T14:28:48.197Z
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2022-04-13 15:28:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570134
;

-- UI Element: Geschäftspartner -> Bankkonto.Swift code
-- Column: C_BP_BankAccount.SwiftCode
-- 2022-04-13T14:29:11.343Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691675,0,226,1000036,605327,'F',TO_TIMESTAMP('2022-04-13 15:29:10','YYYY-MM-DD HH24:MI:SS'),100,'Swift Code or BIC','The Swift Code (Society of Worldwide Interbank Financial Telecommunications) or BIC (Bank Identifier Code) is an identifier of a Bank. The first 4 characters are the bank code, followed by the 2 character country code, the two character location code and optional 3 character branch code. For details see http://www.swift.com/biconline/index.cfm','Y','N','N','Y','N','N','N',0,'Swift code',42,0,0,TO_TIMESTAMP('2022-04-13 15:29:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T14:44:40.225Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,227,544739,TO_TIMESTAMP('2022-04-13 15:44:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-04-13 15:44:39','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2022-04-13T14:44:40.305Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544739 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-04-13T14:45:32.394Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545736,544739,TO_TIMESTAMP('2022-04-13 15:45:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-04-13 15:45:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T14:46:12.989Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545736,548795,TO_TIMESTAMP('2022-04-13 15:46:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2022-04-13 15:46:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Bank -> Bank.BLZ
-- Column: C_Bank.RoutingNo
-- 2022-04-13T14:46:47.107Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548795, SeqNo=10,Updated=TO_TIMESTAMP('2022-04-13 15:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543415
;

-- UI Element: Bank -> Bank.BLZ
-- Column: C_Bank.RoutingNo
-- 2022-04-13T14:47:04.467Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-04-13 15:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543415
;

-- UI Element: Bankkonto -> Konto.BLZ
-- Column: C_BP_BankAccount.RoutingNo
-- 2022-04-13T14:56:55.098Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540333, SeqNo=300,Updated=TO_TIMESTAMP('2022-04-13 15:56:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543590
;

-- UI Element: Bankkonto -> Konto.BLZ
-- Column: C_BP_BankAccount.RoutingNo
-- 2022-04-13T14:57:21.719Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-04-13 15:57:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543590
;

