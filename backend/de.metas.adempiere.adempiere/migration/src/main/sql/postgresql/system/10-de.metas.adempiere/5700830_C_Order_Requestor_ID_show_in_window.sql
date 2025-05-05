-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Requestor
-- Column: C_Order.Requestor_ID
-- 2023-08-30T12:28:56.394842700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587347,720320,0,294,TO_TIMESTAMP('2023-08-30 15:28:56.044','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Requestor',TO_TIMESTAMP('2023-08-30 15:28:56.044','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-30T12:28:56.404334500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-30T12:28:56.407465900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582667) 
;

-- 2023-08-30T12:28:56.420999600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720320
;

-- 2023-08-30T12:28:56.423100200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720320)
;

-- UI Column: Bestellung(181,D) -> Bestellung(294,D) -> main -> 10
-- UI Element Group: requisition
-- 2023-08-30T12:58:43.741003600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540056,551117,TO_TIMESTAMP('2023-08-30 15:58:43.575','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','requisition',40,TO_TIMESTAMP('2023-08-30 15:58:43.575','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 10 -> requisition.Requestor
-- Column: C_Order.Requestor_ID
-- 2023-08-30T12:59:21.705986900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720320,0,294,551117,620384,'F',TO_TIMESTAMP('2023-08-30 15:59:21.541','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Requestor',10,0,0,TO_TIMESTAMP('2023-08-30 15:59:21.541','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 10 -> requisition.Requestor
-- Column: C_Order.Requestor_ID
-- 2023-08-30T12:59:31.831297Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-08-30 15:59:31.83','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620384
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Requestor
-- Column: C_Order.Requestor_ID
-- 2023-08-30T13:01:30.825377300Z
UPDATE AD_Field SET DisplayLogic='@Requestor_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-08-30 16:01:30.824','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720320
;

