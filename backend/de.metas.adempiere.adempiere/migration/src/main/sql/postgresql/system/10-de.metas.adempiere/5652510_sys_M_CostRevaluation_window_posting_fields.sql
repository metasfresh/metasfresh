-- Field: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> Cost Revaluation
-- Column: M_CostRevaluation.M_CostRevaluation_ID
-- 2022-08-23T11:01:24.220Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583740,705512,0,546464,TO_TIMESTAMP('2022-08-23 14:01:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Revaluation',TO_TIMESTAMP('2022-08-23 14:01:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T11:01:24.223Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705512 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T11:01:24.231Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581160) 
;

-- 2022-08-23T11:01:24.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705512
;

-- 2022-08-23T11:01:24.256Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705512)
;

-- Field: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> Buchungsstatus
-- Column: M_CostRevaluation.Posted
-- 2022-08-23T11:01:24.351Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584135,705513,0,546464,TO_TIMESTAMP('2022-08-23 14:01:24','YYYY-MM-DD HH24:MI:SS'),100,'Buchungsstatus',1,'D','Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.','Y','N','N','N','N','N','N','N','Buchungsstatus',TO_TIMESTAMP('2022-08-23 14:01:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T11:01:24.352Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705513 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T11:01:24.353Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1308) 
;

-- 2022-08-23T11:01:24.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705513
;

-- 2022-08-23T11:01:24.391Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705513)
;

-- Field: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> Verbuchungsfehler
-- Column: M_CostRevaluation.PostingError_Issue_ID
-- 2022-08-23T11:01:24.486Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584136,705514,0,546464,TO_TIMESTAMP('2022-08-23 14:01:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Verbuchungsfehler',TO_TIMESTAMP('2022-08-23 14:01:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T11:01:24.487Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705514 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-23T11:01:24.488Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
;

-- 2022-08-23T11:01:24.493Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705514
;

-- 2022-08-23T11:01:24.493Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705514)
;

-- Field: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> Buchungsstatus
-- Column: M_CostRevaluation.Posted
-- 2022-08-23T11:01:38.829Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-23 14:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705513
;

-- Field: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> Verbuchungsfehler
-- Column: M_CostRevaluation.PostingError_Issue_ID
-- 2022-08-23T11:02:02.342Z
UPDATE AD_Field SET DisplayLogic='@PostingError_Issue_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-23 14:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705514
;

-- Field: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> Buchungsstatus
-- Column: M_CostRevaluation.Posted
-- 2022-08-23T11:02:31.832Z
UPDATE AD_Field SET DisplayLogic='@Processed/X@=Y',Updated=TO_TIMESTAMP('2022-08-23 14:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705513
;

-- UI Element: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> main -> 20 -> status.Buchungsstatus
-- Column: M_CostRevaluation.Posted
-- 2022-08-23T11:25:53.698Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705513,0,546464,612193,549561,'F',TO_TIMESTAMP('2022-08-23 14:25:53','YYYY-MM-DD HH24:MI:SS'),100,'Buchungsstatus','Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.','Y','N','Y','N','N','Buchungsstatus',30,0,0,TO_TIMESTAMP('2022-08-23 14:25:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> main -> 20 -> status.Verbuchungsfehler
-- Column: M_CostRevaluation.PostingError_Issue_ID
-- 2022-08-23T11:26:08.039Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705514,0,546464,612194,549561,'F',TO_TIMESTAMP('2022-08-23 14:26:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verbuchungsfehler',40,0,0,TO_TIMESTAMP('2022-08-23 14:26:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> main -> 20 -> org.Organisation
-- Column: M_CostRevaluation.AD_Org_ID
-- 2022-08-23T11:26:32.647Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-08-23 14:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610535
;

-- UI Element: Kosten Neubewertung(541568,D) -> Cost Revaluation(546464,D) -> main -> 20 -> status.Buchungsstatus
-- Column: M_CostRevaluation.Posted
-- 2022-08-23T11:26:32.653Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-08-23 14:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612193
;

