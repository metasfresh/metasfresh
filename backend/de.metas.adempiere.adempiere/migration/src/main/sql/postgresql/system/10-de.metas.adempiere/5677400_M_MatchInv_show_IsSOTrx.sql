-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Sales Transaction
-- Column: M_MatchInv.IsSOTrx
-- 2023-02-15T15:15:24.307Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551869,712603,0,408,TO_TIMESTAMP('2023-02-15 17:15:24','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction',1,'D','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Sales Transaction',TO_TIMESTAMP('2023-02-15 17:15:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T15:15:24.308Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712603 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-15T15:15:24.310Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2023-02-15T15:15:24.319Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712603
;

-- 2023-02-15T15:15:24.320Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712603)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Sales Transaction
-- Column: M_MatchInv.IsSOTrx
-- 2023-02-15T15:15:40.918Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712603,0,408,542906,615838,'F',TO_TIMESTAMP('2023-02-15 17:15:40','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','Sales Transaction',70,0,0,TO_TIMESTAMP('2023-02-15 17:15:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> default.Sales Transaction
-- Column: M_MatchInv.IsSOTrx
-- 2023-02-15T15:15:53.381Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-02-15 17:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615838
;

