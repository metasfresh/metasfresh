-- Run mode: SWING_CLIENT


-- Tab: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung
-- Table: C_Proforma_Order_Alloc
-- 2026-04-13T12:14:01.848Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584755,0,549155,542590,183,'Y',TO_TIMESTAMP('2026-04-13 12:14:01.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','C_Proforma_Order_Alloc','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Proforma Bestellung Zuordnung','N',120,1,TO_TIMESTAMP('2026-04-13 12:14:01.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-13T12:14:01.859Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549155 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-04-13T12:14:01.881Z
/* DDL */  select update_tab_translation_from_ad_element(584755)
;

-- 2026-04-13T12:14:01.912Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549155)
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Mandant
-- Column: C_Proforma_Order_Alloc.AD_Client_ID
-- 2026-04-13T12:14:07.581Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592351,777068,0,549155,TO_TIMESTAMP('2026-04-13 12:14:07.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-04-13 12:14:07.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-13T12:14:07.589Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-13T12:14:07.600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-04-13T12:14:08.325Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777068
;

-- 2026-04-13T12:14:08.327Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777068)
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Sektion
-- Column: C_Proforma_Order_Alloc.AD_Org_ID
-- 2026-04-13T12:14:08.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592352,777069,0,549155,TO_TIMESTAMP('2026-04-13 12:14:08.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2026-04-13 12:14:08.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-13T12:14:08.424Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-13T12:14:08.427Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-04-13T12:14:08.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777069
;

-- 2026-04-13T12:14:08.808Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777069)
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Aktiv
-- Column: C_Proforma_Order_Alloc.IsActive
-- 2026-04-13T12:14:08.928Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592355,777070,0,549155,TO_TIMESTAMP('2026-04-13 12:14:08.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-04-13 12:14:08.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-13T12:14:08.933Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-13T12:14:08.940Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-04-13T12:14:09.202Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777070
;

-- 2026-04-13T12:14:09.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777070)
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Proforma Bestellung Zuordnung
-- Column: C_Proforma_Order_Alloc.C_Proforma_Order_Alloc_ID
-- 2026-04-13T12:14:09.289Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592358,777071,0,549155,TO_TIMESTAMP('2026-04-13 12:14:09.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Proforma Bestellung Zuordnung',TO_TIMESTAMP('2026-04-13 12:14:09.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-13T12:14:09.297Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-13T12:14:09.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584755)
;

-- 2026-04-13T12:14:09.312Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777071
;

-- 2026-04-13T12:14:09.316Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777071)
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Rechnung
-- Column: C_Proforma_Order_Alloc.C_Invoice_ID
-- 2026-04-13T12:14:09.410Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592359,777072,0,549155,TO_TIMESTAMP('2026-04-13 12:14:09.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Invoice Identifier',10,'D','The Invoice Document.','Y','N','N','N','N','N','N','N','Rechnung',TO_TIMESTAMP('2026-04-13 12:14:09.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-13T12:14:09.418Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-13T12:14:09.421Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008)
;

-- 2026-04-13T12:14:09.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777072
;

-- 2026-04-13T12:14:09.464Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777072)
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Auftrag
-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-13T12:14:09.568Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592360,777073,0,549155,TO_TIMESTAMP('2026-04-13 12:14:09.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2026-04-13 12:14:09.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-13T12:14:09.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-13T12:14:09.574Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558)
;

-- 2026-04-13T12:14:09.600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777073
;

-- 2026-04-13T12:14:09.602Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777073)
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Mandant
-- Column: C_Proforma_Order_Alloc.AD_Client_ID
-- 2026-04-13T12:14:24.068Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-04-13 12:14:24.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=777068
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Sektion
-- Column: C_Proforma_Order_Alloc.AD_Org_ID
-- 2026-04-13T12:14:24.075Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-04-13 12:14:24.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=777069
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Aktiv
-- Column: C_Proforma_Order_Alloc.IsActive
-- 2026-04-13T12:14:24.083Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-04-13 12:14:24.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=777070
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Proforma Bestellung Zuordnung
-- Column: C_Proforma_Order_Alloc.C_Proforma_Order_Alloc_ID
-- 2026-04-13T12:14:24.090Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-04-13 12:14:24.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=777071
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Rechnung
-- Column: C_Proforma_Order_Alloc.C_Invoice_ID
-- 2026-04-13T12:14:24.096Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-04-13 12:14:24.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=777072
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Auftrag
-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-13T12:14:24.102Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-04-13 12:14:24.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=777073
;

-- Tab: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D)
-- UI Section: main
-- 2026-04-13T12:14:37.381Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549155,547671,TO_TIMESTAMP('2026-04-13 12:14:37.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-04-13 12:14:37.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-04-13T12:14:37.386Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547671 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> main
-- UI Column: 10
-- 2026-04-13T12:14:50.149Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549365,547671,TO_TIMESTAMP('2026-04-13 12:14:50.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-04-13 12:14:50.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> main -> 10
-- UI Element Group: main
-- 2026-04-13T12:14:57.355Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549365,555123,TO_TIMESTAMP('2026-04-13 12:14:57.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-04-13 12:14:57.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> Bestellung
-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-13T12:15:53.220Z
UPDATE AD_Field SET AD_Name_ID=574896, Description='Bestellungen eingeben und verwalten', Help=NULL, Name='Bestellung',Updated=TO_TIMESTAMP('2026-04-13 12:15:53.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=777073
;

-- 2026-04-13T12:15:53.223Z
UPDATE AD_Field_Trl trl SET Description='Bestellungen eingeben und verwalten',Help=NULL,Name='Bestellung' WHERE AD_Field_ID=777073 AND AD_Language='de_DE'
;

-- 2026-04-13T12:15:53.227Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(574896)
;

-- 2026-04-13T12:15:53.233Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777073
;

-- 2026-04-13T12:15:53.234Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777073)
;

-- UI Element: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> main -> 10 -> main.Bestellung
-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-13T12:16:24.756Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777073,0,549155,555123,649811,'F',TO_TIMESTAMP('2026-04-13 12:16:24.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellungen eingeben und verwalten','Y','N','N','Y','N','N','N',0,'Bestellung',10,0,0,TO_TIMESTAMP('2026-04-13 12:16:24.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> main -> 10 -> main.Aktiv
-- Column: C_Proforma_Order_Alloc.IsActive
-- 2026-04-13T12:16:33.557Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777070,0,549155,555123,649812,'F',TO_TIMESTAMP('2026-04-13 12:16:33.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2026-04-13 12:16:33.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> main -> 10 -> main.Sektion
-- Column: C_Proforma_Order_Alloc.AD_Org_ID
-- 2026-04-13T12:16:44.178Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777069,0,549155,555123,649813,'F',TO_TIMESTAMP('2026-04-13 12:16:44.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',30,0,0,TO_TIMESTAMP('2026-04-13 12:16:44.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> main -> 10 -> main.Bestellung
-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-13T12:16:55.279Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-04-13 12:16:55.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=649811
;

-- UI Element: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> main -> 10 -> main.Aktiv
-- Column: C_Proforma_Order_Alloc.IsActive
-- 2026-04-13T12:16:55.286Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-04-13 12:16:55.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=649812
;

-- UI Element: Eingangsrechnung(183,D) -> Proforma Bestellung Zuordnung(549155,D) -> main -> 10 -> main.Sektion
-- Column: C_Proforma_Order_Alloc.AD_Org_ID
-- 2026-04-13T12:16:55.292Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-04-13 12:16:55.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=649813
;

