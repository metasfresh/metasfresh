-- Field: Inventur -> Bestandszählungs Position -> Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- Field: Inventur(168,D) -> Bestandszählungs Position(256,D) -> Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- 2024-04-17T19:53:25.703Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588189,728051,0,256,TO_TIMESTAMP('2024-04-17 22:53:25','YYYY-MM-DD HH24:MI:SS'),100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.',9999999,'D','Y','N','N','N','N','N','N','N','Rendered QR Code',TO_TIMESTAMP('2024-04-17 22:53:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-04-17T19:53:25.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-17T19:53:25.709Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580597) 
;

-- 2024-04-17T19:53:25.722Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728051
;

-- 2024-04-17T19:53:25.724Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728051)
;

-- UI Element: Inventur -> Bestandszählungs Position.Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- 2024-04-17T19:54:23.248Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728051,0,256,541513,624523,'F',TO_TIMESTAMP('2024-04-17 22:54:23','YYYY-MM-DD HH24:MI:SS'),100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.','Y','N','Y','N','N','Rendered QR Code',160,0,0,TO_TIMESTAMP('2024-04-17 22:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur -> Bestandszählungs Position.Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- 2024-04-17T19:54:32.608Z
UPDATE AD_UI_Element SET SeqNo=85,Updated=TO_TIMESTAMP('2024-04-17 22:54:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624523
;

-- Field: Inventur -> Bestandszählungs Position -> Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- Field: Inventur(168,D) -> Bestandszählungs Position(256,D) -> Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- 2024-04-17T19:55:01.292Z
UPDATE AD_Field SET DisplayLogic='@RenderedQRCode@!''''',Updated=TO_TIMESTAMP('2024-04-17 22:55:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728051
;

-- Field: Inventur -> Bestandszählungs Position -> Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- Field: Inventur(168,D) -> Bestandszählungs Position(256,D) -> Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- 2024-04-17T19:55:06.086Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-04-17 22:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728051
;

-- Field: Inventur -> Bestandszählungs Position -> Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- Field: Inventur(168,D) -> Bestandszählungs Position(256,D) -> Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- 2024-04-17T19:55:49.073Z
UPDATE AD_Field SET DisplayLogic='@HUAggregationType/''S''@=''S'' & @RenderedQRCode@!''''',Updated=TO_TIMESTAMP('2024-04-17 22:55:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728051
;

-- UI Element: Inventur -> Bestandszählungs Position.Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- UI Element: Inventur(168,D) -> Bestandszählungs Position(256,D) -> main -> 10 -> default.Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- 2024-04-17T19:57:23.134Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-04-17 22:57:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624523
;

