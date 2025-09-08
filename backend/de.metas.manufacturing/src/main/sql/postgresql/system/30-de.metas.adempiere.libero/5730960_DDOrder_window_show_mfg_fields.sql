-- Field: Distributionsauftrag -> Distributionsauftrag -> Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- 2024-08-06T11:46:32.215Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588876,729786,0,53055,TO_TIMESTAMP('2024-08-06 14:46:32','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Forward Manufacturing Order',TO_TIMESTAMP('2024-08-06 14:46:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-06T11:46:32.219Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729786 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-06T11:46:32.247Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583182) 
;

-- 2024-08-06T11:46:32.260Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729786
;

-- 2024-08-06T11:46:32.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729786)
;

-- Field: Distributionsauftrag -> Distributionsauftrag -> Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- 2024-08-06T11:46:41.190Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588877,729787,0,53055,TO_TIMESTAMP('2024-08-06 14:46:41','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Forward Manufacturing Order BOM Line',TO_TIMESTAMP('2024-08-06 14:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-06T11:46:41.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-06T11:46:41.195Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583183) 
;

-- 2024-08-06T11:46:41.198Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729787
;

-- 2024-08-06T11:46:41.200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729787)
;

-- Field: Distributionsauftrag -> Distributionsauftrag -> Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- 2024-08-06T11:47:08.173Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-06 14:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729786
;

-- Field: Distributionsauftrag -> Distributionsauftrag -> Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- 2024-08-06T11:47:11.167Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-06 14:47:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729787
;

-- UI Column: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20
-- UI Element Group: manufacturing
-- 2024-08-06T11:48:42.494Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540264,551892,TO_TIMESTAMP('2024-08-06 14:48:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','manufacturing',35,TO_TIMESTAMP('2024-08-06 14:48:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distributionsauftrag -> Distributionsauftrag.Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- UI Element: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20 -> manufacturing.Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- 2024-08-06T11:48:55.112Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729786,0,53055,551892,625251,'F',TO_TIMESTAMP('2024-08-06 14:48:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Forward Manufacturing Order',10,0,0,TO_TIMESTAMP('2024-08-06 14:48:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distributionsauftrag -> Distributionsauftrag.Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- UI Element: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20 -> manufacturing.Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- 2024-08-06T11:49:02.792Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729787,0,53055,551892,625252,'F',TO_TIMESTAMP('2024-08-06 14:49:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Forward Manufacturing Order BOM Line',20,0,0,TO_TIMESTAMP('2024-08-06 14:49:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Distributionsauftrag -> Distributionsauftrag -> Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- 2024-08-06T11:49:16.734Z
UPDATE AD_Field SET DisplayLogic='@Forward_PP_Order_ID/0@>0',Updated=TO_TIMESTAMP('2024-08-06 14:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729787
;

-- Field: Distributionsauftrag -> Distributionsauftrag -> Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- 2024-08-06T11:49:26.590Z
UPDATE AD_Field SET DisplayLogic='@Forward_PP_Order_ID/0@>0',Updated=TO_TIMESTAMP('2024-08-06 14:49:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729786
;

-- Field: Distributionsauftrag -> Distributionsauftrag -> Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- 2024-08-06T11:49:51.936Z
UPDATE AD_Field SET DisplayLogic='@Forward_PP_Order_BOMLine_ID/0@>0',Updated=TO_TIMESTAMP('2024-08-06 14:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729787
;

