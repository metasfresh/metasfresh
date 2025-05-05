-- UI Column: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10
-- UI Element Group: FEC
-- 2023-01-24T11:45:54.037Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000014,550253,TO_TIMESTAMP('2023-01-24 13:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','FEC',40,TO_TIMESTAMP('2023-01-24 13:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-24T11:46:15.513Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585603,710524,0,257,TO_TIMESTAMP('2023-01-24 13:46:15','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Foreign Exchange Contract',TO_TIMESTAMP('2023-01-24 13:46:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-24T11:46:15.518Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710524 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-24T11:46:15.548Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581935) 
;

-- 2023-01-24T11:46:15.561Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710524
;

-- 2023-01-24T11:46:15.564Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710524)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-24T11:46:46.564Z
UPDATE AD_Field SET DisplayLogic='@C_ForeignExchangeContract_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-24 13:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710524
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-24T11:47:14.130Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710524,0,257,550253,614850,'F',TO_TIMESTAMP('2023-01-24 13:47:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Foreign Exchange Contract',10,0,0,TO_TIMESTAMP('2023-01-24 13:47:13','YYYY-MM-DD HH24:MI:SS'),100)
;

