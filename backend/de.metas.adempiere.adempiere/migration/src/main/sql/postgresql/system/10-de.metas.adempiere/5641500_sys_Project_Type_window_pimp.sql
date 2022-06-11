-- Tab: Projekt - Projektart -> Standard-Phase
-- Table: C_Phase
-- 2022-05-31T08:12:07.085Z
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2022-05-31 11:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=477
;

-- Tab: Projekt - Projektart -> Standard-Aufgabe
-- Table: C_Task
-- 2022-05-31T08:12:12.944Z
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2022-05-31 11:12:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=492
;

-- 2022-05-31T08:12:35.271Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,476,544940,TO_TIMESTAMP('2022-05-31 11:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 11:12:35','YYYY-MM-DD HH24:MI:SS'),100,'default')
;

-- 2022-05-31T08:12:35.273Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544940 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-31T08:12:40.903Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545976,544940,TO_TIMESTAMP('2022-05-31 11:12:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 11:12:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T08:12:43.160Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545977,544940,TO_TIMESTAMP('2022-05-31 11:12:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-05-31 11:12:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T08:12:55.022Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545976,549205,TO_TIMESTAMP('2022-05-31 11:12:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2022-05-31 11:12:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Name
-- Column: C_ProjectType.Name
-- 2022-05-31T08:13:29.555Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6626,0,476,549205,608329,'F',TO_TIMESTAMP('2022-05-31 11:13:29','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2022-05-31 11:13:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Project Category
-- Column: C_ProjectType.ProjectCategory
-- 2022-05-31T08:13:51.200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8227,0,476,549205,608330,'F',TO_TIMESTAMP('2022-05-31 11:13:51','YYYY-MM-DD HH24:MI:SS'),100,'Project Category','The Project Category determines the behavior of the project:
General - no special accounting, e.g. for Presales or general tracking
Service - no special accounting, e.g. for Service/Charge projects
Work Order - creates Project/Job WIP transactions - ability to issue material
Asset - create Project Asset transactions - ability to issue material
','Y','N','Y','N','N','Project Category',20,0,0,TO_TIMESTAMP('2022-05-31 11:13:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Projekt-Nummernfolge
-- Column: C_ProjectType.AD_Sequence_ProjectValue_ID
-- 2022-05-31T08:13:58.722Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,565096,0,476,549205,608331,'F',TO_TIMESTAMP('2022-05-31 11:13:58','YYYY-MM-DD HH24:MI:SS'),100,'Nummernfolge für Projekt-Suchschlüssel','Y','N','Y','N','N','Projekt-Nummernfolge',30,0,0,TO_TIMESTAMP('2022-05-31 11:13:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T08:14:20.668Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545976,549206,TO_TIMESTAMP('2022-05-31 11:14:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',20,TO_TIMESTAMP('2022-05-31 11:14:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Aktiv
-- Column: C_ProjectType.IsActive
-- 2022-05-31T08:14:33.213Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6628,0,476,549206,608332,'F',TO_TIMESTAMP('2022-05-31 11:14:32','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2022-05-31 11:14:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T08:14:41.714Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545976,549207,TO_TIMESTAMP('2022-05-31 11:14:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','description & help',30,TO_TIMESTAMP('2022-05-31 11:14:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Beschreibung
-- Column: C_ProjectType.Description
-- 2022-05-31T08:14:59.132Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6625,0,476,549207,608333,'F',TO_TIMESTAMP('2022-05-31 11:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2022-05-31 11:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Kommentar/Hilfe
-- Column: C_ProjectType.Help
-- 2022-05-31T08:15:07.740Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6624,0,476,549207,608334,'F',TO_TIMESTAMP('2022-05-31 11:15:07','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','Y','N','N','Kommentar/Hilfe',20,0,0,TO_TIMESTAMP('2022-05-31 11:15:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T08:15:16.432Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545976,549208,TO_TIMESTAMP('2022-05-31 11:15:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','client & org',40,TO_TIMESTAMP('2022-05-31 11:15:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Sektion
-- Column: C_ProjectType.AD_Org_ID
-- 2022-05-31T08:15:28.659Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6627,0,476,549208,608335,'F',TO_TIMESTAMP('2022-05-31 11:15:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-05-31 11:15:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Mandant
-- Column: C_ProjectType.AD_Client_ID
-- 2022-05-31T08:15:37.160Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6623,0,476,549208,608336,'F',TO_TIMESTAMP('2022-05-31 11:15:37','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-05-31 11:15:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T08:15:51.830Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=545977, SeqNo=10,Updated=TO_TIMESTAMP('2022-05-31 11:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549206
;

-- 2022-05-31T08:16:00.045Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=545977, SeqNo=20,Updated=TO_TIMESTAMP('2022-05-31 11:16:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549208
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Name
-- Column: C_ProjectType.Name
-- 2022-05-31T08:16:24.555Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-05-31 11:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608329
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Project Category
-- Column: C_ProjectType.ProjectCategory
-- 2022-05-31T08:16:24.564Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-05-31 11:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608330
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Beschreibung
-- Column: C_ProjectType.Description
-- 2022-05-31T08:16:24.572Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-31 11:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608333
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Sektion
-- Column: C_ProjectType.AD_Org_ID
-- 2022-05-31T08:16:24.580Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-31 11:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608335
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Aktiv
-- Column: C_ProjectType.IsActive
-- 2022-05-31T08:16:39.154Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-31 11:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608332
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Beschreibung
-- Column: C_ProjectType.Description
-- 2022-05-31T08:16:39.163Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-31 11:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608333
;

-- UI Element: Projekt - Projektart -> Projekt - Projektart.Sektion
-- Column: C_ProjectType.AD_Org_ID
-- 2022-05-31T08:16:39.171Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-05-31 11:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608335
;

-- Column: C_ProjectType.ProjectCategory
-- 2022-05-31T08:18:36.347Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-05-31 11:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=9837
;

