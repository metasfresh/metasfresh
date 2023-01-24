-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-24T11:54:29.691Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585603,710525,0,296,TO_TIMESTAMP('2023-01-24 13:54:29','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Foreign Exchange Contract',TO_TIMESTAMP('2023-01-24 13:54:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-24T11:54:29.695Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710525 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-24T11:54:29.697Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581935) 
;

-- 2023-01-24T11:54:29.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710525
;

-- 2023-01-24T11:54:29.703Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710525)
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-24T11:54:55.566Z
UPDATE AD_Field SET DisplayLogic='@C_ForeignExchangeContract_ID/0@>0',Updated=TO_TIMESTAMP('2023-01-24 13:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710525
;

-- UI Column: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10
-- UI Element Group: FEC
-- 2023-01-24T11:55:17.697Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540060,550254,TO_TIMESTAMP('2023-01-24 13:55:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','FEC',20,TO_TIMESTAMP('2023-01-24 13:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-24T11:55:36.173Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710525,0,296,550254,614851,'F',TO_TIMESTAMP('2023-01-24 13:55:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Foreign Exchange Contract',10,0,0,TO_TIMESTAMP('2023-01-24 13:55:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-24T11:55:40.804Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-24 13:55:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710525
;

