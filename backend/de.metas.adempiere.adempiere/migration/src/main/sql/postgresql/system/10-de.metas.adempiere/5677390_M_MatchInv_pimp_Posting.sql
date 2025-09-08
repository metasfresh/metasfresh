-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Posting Error
-- Column: M_MatchInv.PostingError_Issue_ID
-- 2023-02-15T15:12:55.399Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570873,712602,0,408,TO_TIMESTAMP('2023-02-15 17:12:55','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Posting Error',TO_TIMESTAMP('2023-02-15 17:12:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T15:12:55.401Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712602 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-15T15:12:55.403Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
;

-- 2023-02-15T15:12:55.407Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712602
;

-- 2023-02-15T15:12:55.407Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712602)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 20 -> posted.Buchungsdatum
-- Column: M_MatchInv.DateAcct
-- 2023-02-15T15:13:22.005Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-02-15 17:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577962
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 20 -> posted.Verbucht
-- Column: M_MatchInv.Posted
-- 2023-02-15T15:13:25.175Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-02-15 17:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561872
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 20 -> posted.Posting Error
-- Column: M_MatchInv.PostingError_Issue_ID
-- 2023-02-15T15:13:42.326Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712602,0,408,543266,615837,'F',TO_TIMESTAMP('2023-02-15 17:13:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Posting Error',30,0,0,TO_TIMESTAMP('2023-02-15 17:13:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Posting Error
-- Column: M_MatchInv.PostingError_Issue_ID
-- 2023-02-15T15:14:07.510Z
UPDATE AD_Field SET DisplayLogic='@PostingError_Issue_ID/0@>0',Updated=TO_TIMESTAMP('2023-02-15 17:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712602
;

