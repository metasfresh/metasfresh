-- Field: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> ChangeType
-- Column: Fact_Acct_UserChange.ChangeType
-- 2023-08-01T13:55:46.466Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587243,718145,0,547077,TO_TIMESTAMP('2023-08-01 16:55:46','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.ab182','Y','N','N','N','N','N','N','N','ChangeType',TO_TIMESTAMP('2023-08-01 16:55:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-08-01T13:55:46.469Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718145 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-01T13:55:46.472Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53609) 
;

-- 2023-08-01T13:55:46.480Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718145
;

-- 2023-08-01T13:55:46.482Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718145)
;

-- UI Column: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10
-- UI Element Group: advanced
-- 2023-08-01T13:57:59.329Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546929,550900,TO_TIMESTAMP('2023-08-01 16:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced',20,TO_TIMESTAMP('2023-08-01 16:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.ChangeType
-- Column: Fact_Acct_UserChange.ChangeType
-- 2023-08-01T13:58:05.871Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,718145,0,547077,550900,618956,'F',TO_TIMESTAMP('2023-08-01 16:58:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','ChangeType',10,0,0,TO_TIMESTAMP('2023-08-01 16:58:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.Match Key
-- Column: Fact_Acct_UserChange.MatchKey
-- 2023-08-01T13:58:18.426Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,718140,0,547077,550900,618957,'F',TO_TIMESTAMP('2023-08-01 16:58:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Match Key',20,0,0,TO_TIMESTAMP('2023-08-01 16:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.Document Currency
-- Column: Fact_Acct_UserChange.Document_Currency_ID
-- 2023-08-01T13:58:25.404Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,718143,0,547077,550900,618958,'F',TO_TIMESTAMP('2023-08-01 16:58:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Document Currency',30,0,0,TO_TIMESTAMP('2023-08-01 16:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.Local Currency
-- Column: Fact_Acct_UserChange.Local_Currency_ID
-- 2023-08-01T13:58:31.443Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,718142,0,547077,550900,618959,'F',TO_TIMESTAMP('2023-08-01 16:58:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Local Currency',40,0,0,TO_TIMESTAMP('2023-08-01 16:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.Accounting Schema
-- Column: Fact_Acct_UserChange.C_AcctSchema_ID
-- 2023-08-01T13:58:40.529Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,718130,0,547077,550900,618960,'F',TO_TIMESTAMP('2023-08-01 16:58:40','YYYY-MM-DD HH24:MI:SS'),100,'Rules for accounting','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar','Y','N','Y','N','N','Accounting Schema',50,0,0,TO_TIMESTAMP('2023-08-01 16:58:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.ChangeType
-- Column: Fact_Acct_UserChange.ChangeType
-- 2023-08-01T13:58:48.608Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-08-01 16:58:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618956
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.Match Key
-- Column: Fact_Acct_UserChange.MatchKey
-- 2023-08-01T13:58:49.566Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-08-01 16:58:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618957
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.Document Currency
-- Column: Fact_Acct_UserChange.Document_Currency_ID
-- 2023-08-01T13:58:50.494Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-08-01 16:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618958
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.Local Currency
-- Column: Fact_Acct_UserChange.Local_Currency_ID
-- 2023-08-01T13:58:51.908Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-08-01 16:58:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618959
;

-- UI Element: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments(547077,de.metas.ab182) -> main -> 10 -> advanced.Accounting Schema
-- Column: Fact_Acct_UserChange.C_AcctSchema_ID
-- 2023-08-01T13:58:54.852Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-08-01 16:58:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618960
;

-- Tab: Purchase Invoice(541621,de.metas.ab182) -> Accounting override/adjustments
-- Table: Fact_Acct_UserChange
-- 2023-08-01T13:59:58.426Z
UPDATE AD_Tab SET WhereClause='ChangeType != ''D''',Updated=TO_TIMESTAMP('2023-08-01 16:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547077
;

