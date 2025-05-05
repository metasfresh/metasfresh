-- Tab: HU QR Code -> HU QR Code Assignment
-- Table: M_HU_QRCode_Assignment
-- Tab: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment
-- Table: M_HU_QRCode_Assignment
-- 2024-02-15T09:59:21.946Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,587945,582969,0,547413,542395,541422,'Y',TO_TIMESTAMP('2024-02-15 11:59:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','N','A','M_HU_QRCode_Assignment','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'HU QR Code Assignment',579136,'N',20,1,TO_TIMESTAMP('2024-02-15 11:59:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T09:59:21.969Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547413 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-02-15T09:59:21.984Z
/* DDL */  select update_tab_translation_from_ad_element(582969) 
;

-- 2024-02-15T09:59:22.009Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547413)
;

-- Field: HU QR Code -> HU QR Code Assignment -> Mandant
-- Column: M_HU_QRCode_Assignment.AD_Client_ID
-- Field: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> Mandant
-- Column: M_HU_QRCode_Assignment.AD_Client_ID
-- 2024-02-15T09:59:29.470Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587936,725178,0,547413,TO_TIMESTAMP('2024-02-15 11:59:29','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.handlingunits','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-02-15 11:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T09:59:29.479Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725178 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-15T09:59:29.491Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-02-15T09:59:31.068Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725178
;

-- 2024-02-15T09:59:31.110Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725178)
;

-- Field: HU QR Code -> HU QR Code Assignment -> Sektion
-- Column: M_HU_QRCode_Assignment.AD_Org_ID
-- Field: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> Sektion
-- Column: M_HU_QRCode_Assignment.AD_Org_ID
-- 2024-02-15T09:59:31.226Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587937,725179,0,547413,TO_TIMESTAMP('2024-02-15 11:59:31','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.handlingunits','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-02-15 11:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T09:59:31.227Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725179 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-15T09:59:31.231Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-02-15T09:59:32.180Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725179
;

-- 2024-02-15T09:59:32.181Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725179)
;

-- Field: HU QR Code -> HU QR Code Assignment -> Aktiv
-- Column: M_HU_QRCode_Assignment.IsActive
-- Field: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> Aktiv
-- Column: M_HU_QRCode_Assignment.IsActive
-- 2024-02-15T09:59:32.274Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587940,725180,0,547413,TO_TIMESTAMP('2024-02-15 11:59:32','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.handlingunits','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-02-15 11:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T09:59:32.276Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725180 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-15T09:59:32.277Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-02-15T09:59:32.927Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725180
;

-- 2024-02-15T09:59:32.927Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725180)
;

-- Field: HU QR Code -> HU QR Code Assignment -> HU QR Code Assignment
-- Column: M_HU_QRCode_Assignment.M_HU_QRCode_Assignment_ID
-- Field: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> HU QR Code Assignment
-- Column: M_HU_QRCode_Assignment.M_HU_QRCode_Assignment_ID
-- 2024-02-15T09:59:33.022Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587943,725181,0,547413,TO_TIMESTAMP('2024-02-15 11:59:32','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','HU QR Code Assignment',TO_TIMESTAMP('2024-02-15 11:59:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T09:59:33.024Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725181 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-15T09:59:33.026Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582969) 
;

-- 2024-02-15T09:59:33.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725181
;

-- 2024-02-15T09:59:33.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725181)
;

-- Field: HU QR Code -> HU QR Code Assignment -> Handling Unit
-- Column: M_HU_QRCode_Assignment.M_HU_ID
-- Field: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> Handling Unit
-- Column: M_HU_QRCode_Assignment.M_HU_ID
-- 2024-02-15T09:59:33.129Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587944,725182,0,547413,TO_TIMESTAMP('2024-02-15 11:59:33','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Handling Unit',TO_TIMESTAMP('2024-02-15 11:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T09:59:33.131Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725182 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-15T09:59:33.134Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542140) 
;

-- 2024-02-15T09:59:33.180Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725182
;

-- 2024-02-15T09:59:33.180Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725182)
;

-- Field: HU QR Code -> HU QR Code Assignment -> HU QR Code
-- Column: M_HU_QRCode_Assignment.M_HU_QRCode_ID
-- Field: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> HU QR Code
-- Column: M_HU_QRCode_Assignment.M_HU_QRCode_ID
-- 2024-02-15T09:59:33.272Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587945,725183,0,547413,TO_TIMESTAMP('2024-02-15 11:59:33','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','HU QR Code',TO_TIMESTAMP('2024-02-15 11:59:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T09:59:33.274Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725183 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-15T09:59:33.276Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580520) 
;

-- 2024-02-15T09:59:33.295Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725183
;

-- 2024-02-15T09:59:33.296Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725183)
;

-- Tab: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits)
-- UI Section: main
-- 2024-02-15T10:00:10.802Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,545387,545990,TO_TIMESTAMP('2024-02-15 12:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-02-15 12:00:10','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-02-15T10:00:10.805Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545990 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> main
-- UI Column: 10
-- 2024-02-15T10:00:10.918Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547309,545990,TO_TIMESTAMP('2024-02-15 12:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-02-15 12:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> main
-- UI Column: 20
-- 2024-02-15T10:00:11.020Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547310,545990,TO_TIMESTAMP('2024-02-15 12:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-02-15 12:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> main -> 10
-- UI Element Group: default
-- 2024-02-15T10:00:11.126Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547309,551541,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code.Unique ID
-- Column: M_HU_QRCode.UniqueId
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> main -> 10 -> default.Unique ID
-- Column: M_HU_QRCode.UniqueId
-- 2024-02-15T10:00:11.299Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,679027,0,545387,622937,551541,'F',TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Unique ID',10,0,10,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code.Displayable QR Code
-- Column: M_HU_QRCode.DisplayableQRCode
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> main -> 10 -> default.Displayable QR Code
-- Column: M_HU_QRCode.DisplayableQRCode
-- 2024-02-15T10:00:11.427Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,680651,0,545387,622938,551541,'F',TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'It''s the user friendly QR Code which displayed to the user. This might not be unique but it shall be unique enough for help the user distinquish between several nearby items. Also it''s shorter and easy to remember on short term.','Y','N','Y','N','Y','Displayable QR Code',20,0,20,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code.Rendered QR Code
-- Column: M_HU_QRCode.RenderedQRCode
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> main -> 10 -> default.Rendered QR Code
-- Column: M_HU_QRCode.RenderedQRCode
-- 2024-02-15T10:00:11.518Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,680652,0,545387,622939,551541,'F',TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.','Y','N','Y','N','N','Rendered QR Code',30,0,0,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code.attributes
-- Column: M_HU_QRCode.attributes
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> main -> 10 -> default.attributes
-- Column: M_HU_QRCode.attributes
-- 2024-02-15T10:00:11.621Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,679028,0,545387,622940,551541,'F',TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','attributes',40,0,0,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code.Aktiv
-- Column: M_HU_QRCode.IsActive
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> main -> 10 -> default.Aktiv
-- Column: M_HU_QRCode.IsActive
-- 2024-02-15T10:00:11.729Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,679025,0,545387,622941,551541,'F',TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','Y','Aktiv',50,0,30,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits)
-- UI Section: main
-- 2024-02-15T10:00:11.828Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547413,545991,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-02-15T10:00:11.829Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545991 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main
-- UI Column: 10
-- 2024-02-15T10:00:11.923Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547311,545991,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 10
-- UI Element Group: default
-- 2024-02-15T10:00:12.017Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547311,551542,TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-02-15 12:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code Assignment.HU QR Code
-- Column: M_HU_QRCode_Assignment.M_HU_QRCode_ID
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 10 -> default.HU QR Code
-- Column: M_HU_QRCode_Assignment.M_HU_QRCode_ID
-- 2024-02-15T10:02:24.274Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725183,0,547413,622942,551542,'F',TO_TIMESTAMP('2024-02-15 12:02:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'HU QR Code',10,0,0,TO_TIMESTAMP('2024-02-15 12:02:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code Assignment.Handling Unit
-- Column: M_HU_QRCode_Assignment.M_HU_ID
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 10 -> default.Handling Unit
-- Column: M_HU_QRCode_Assignment.M_HU_ID
-- 2024-02-15T10:02:34.168Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725182,0,547413,622943,551542,'F',TO_TIMESTAMP('2024-02-15 12:02:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Handling Unit',20,0,0,TO_TIMESTAMP('2024-02-15 12:02:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main
-- UI Column: 20
-- 2024-02-15T10:04:31.140Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547312,545991,TO_TIMESTAMP('2024-02-15 12:04:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-02-15 12:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 20
-- UI Element Group: flags
-- 2024-02-15T10:04:38.892Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547312,551543,TO_TIMESTAMP('2024-02-15 12:04:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-02-15 12:04:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code Assignment.Aktiv
-- Column: M_HU_QRCode_Assignment.IsActive
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 20 -> flags.Aktiv
-- Column: M_HU_QRCode_Assignment.IsActive
-- 2024-02-15T10:04:48.495Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725180,0,547413,622944,551543,'F',TO_TIMESTAMP('2024-02-15 12:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-02-15 12:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 20
-- UI Element Group: client+org
-- 2024-02-15T10:06:42.739Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547312,551544,TO_TIMESTAMP('2024-02-15 12:06:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','client+org',20,TO_TIMESTAMP('2024-02-15 12:06:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code Assignment.Sektion
-- Column: M_HU_QRCode_Assignment.AD_Org_ID
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 20 -> client+org.Sektion
-- Column: M_HU_QRCode_Assignment.AD_Org_ID
-- 2024-02-15T10:08:00.385Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725179,0,547413,622945,551544,'F',TO_TIMESTAMP('2024-02-15 12:08:00','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2024-02-15 12:08:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code Assignment.Mandant
-- Column: M_HU_QRCode_Assignment.AD_Client_ID
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 20 -> client+org.Mandant
-- Column: M_HU_QRCode_Assignment.AD_Client_ID
-- 2024-02-15T10:08:10.413Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725178,0,547413,622946,551544,'F',TO_TIMESTAMP('2024-02-15 12:08:10','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-02-15 12:08:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU QR Code -> HU QR Code Assignment.HU QR Code
-- Column: M_HU_QRCode_Assignment.M_HU_QRCode_ID
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 10 -> default.HU QR Code
-- Column: M_HU_QRCode_Assignment.M_HU_QRCode_ID
-- 2024-02-15T10:09:04.256Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-02-15 12:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622942
;

-- UI Element: HU QR Code -> HU QR Code Assignment.Handling Unit
-- Column: M_HU_QRCode_Assignment.M_HU_ID
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 10 -> default.Handling Unit
-- Column: M_HU_QRCode_Assignment.M_HU_ID
-- 2024-02-15T10:09:04.264Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-02-15 12:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622943
;

-- UI Element: HU QR Code -> HU QR Code Assignment.Aktiv
-- Column: M_HU_QRCode_Assignment.IsActive
-- UI Element: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code Assignment(547413,de.metas.handlingunits) -> main -> 20 -> flags.Aktiv
-- Column: M_HU_QRCode_Assignment.IsActive
-- 2024-02-15T10:09:04.269Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-02-15 12:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622944
;
-- Name: HU_QR_Code Target for HU
-- 2024-02-15T10:23:30.025Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541854,TO_TIMESTAMP('2024-02-15 12:23:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','HU_QR_Code Target for HU',TO_TIMESTAMP('2024-02-15 12:23:29','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-02-15T10:23:30.028Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541854 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: HU_QR_Code Target for HU
-- Table: M_HU_QRCode
-- Key: M_HU_QRCode.M_HU_QRCode_ID
-- 2024-02-15T10:24:22.383Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,579136,0,541854,541977,541422,TO_TIMESTAMP('2024-02-15 12:24:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N',TO_TIMESTAMP('2024-02-15 12:24:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: HU_QR_Code Target for HU
-- Table: M_HU_QRCode
-- Key: M_HU_QRCode.M_HU_QRCode_ID
-- 2024-02-15T10:25:52.807Z
UPDATE AD_Ref_Table SET WhereClause='M_HU_QRCode_ID in (select M_HU_QRCode_ID from M_HU_QRCode_Assignment where M_HU_ID = @M_HU_ID/-1@)',Updated=TO_TIMESTAMP('2024-02-15 12:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541854
;

-- 2024-02-15T10:26:08.770Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540499,540437,TO_TIMESTAMP('2024-02-15 12:26:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','HU -> HU_QR_Code',TO_TIMESTAMP('2024-02-15 12:26:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-15T10:26:30.728Z
UPDATE AD_RelationType SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2024-02-15 12:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540437
;

-- 2024-02-15T10:26:53.775Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541854,Updated=TO_TIMESTAMP('2024-02-15 12:26:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540437
;

