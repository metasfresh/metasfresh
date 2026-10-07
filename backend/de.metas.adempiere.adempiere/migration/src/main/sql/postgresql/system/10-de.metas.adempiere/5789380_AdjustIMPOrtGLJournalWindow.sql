-- Run mode: SWING_CLIENT

-- Name: Import - Hauptbuchjournal
-- Action Type: W
-- Window: Import - Hauptbuchjournal(278,D)
-- 2026-02-19T15:31:17.355Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,574197,542298,0,278,TO_TIMESTAMP('2026-02-19 15:31:16.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Import von Hauptbuch-Journalen','D','Import GL Journal','Y','N','N','N','N','Import - Hauptbuchjournal',TO_TIMESTAMP('2026-02-19 15:31:16.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
ON CONFLICT DO NOTHING
;

-- 2026-02-19T15:31:17.730Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542298 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-02-19T15:31:17.802Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542298, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542298)
;

-- 2026-02-19T15:31:17.919Z
/* DDL */  select update_menu_translation_from_ad_element(574197)
;

-- Reordering children of `Einstellungen`
-- Node name: `Erweiterte Dateneingabe - Register`
-- 2026-02-19T15:31:23.325Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541181 AND AD_Tree_ID=10
;

-- Node name: `Erweiterte Dateneingabe - Sektion`
-- 2026-02-19T15:31:23.411Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541201 AND AD_Tree_ID=10
;

-- Node name: `Erweiterte Dateneingabe - Feld`
-- 2026-02-19T15:31:23.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541180 AND AD_Tree_ID=10
;

-- Node name: `Import - Erweiterte Dateneingabe`
-- 2026-02-19T15:31:23.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541285 AND AD_Tree_ID=10
;

-- Node name: `Nutzer Filter`
-- 2026-02-19T15:31:23.652Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540989 AND AD_Tree_ID=10
;

-- Node name: `Land, Region`
-- 2026-02-19T15:31:23.736Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540845 AND AD_Tree_ID=10
;

-- Node name: `Land, Ort und Postleitzahl`
-- 2026-02-19T15:31:23.829Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540960 AND AD_Tree_ID=10
;

-- Node name: `Land Übersetzung`
-- 2026-02-19T15:31:23.911Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541110 AND AD_Tree_ID=10
;

-- Node name: `Board Einstellung`
-- 2026-02-19T15:31:23.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540988 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Prozess`
-- 2026-02-19T15:31:24.069Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541081 AND AD_Tree_ID=10
;

-- Node name: `Aggregation`
-- 2026-02-19T15:31:24.147Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541275 AND AD_Tree_ID=10
;

-- Node name: `Meldung Übersetzung`
-- 2026-02-19T15:31:24.226Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541105 AND AD_Tree_ID=10
;

-- Node name: `Belegart Übersetzung`
-- 2026-02-19T15:31:24.302Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541106 AND AD_Tree_ID=10
;

-- Node name: `Ausgelieferte HUs aus Altanwendung`
-- 2026-02-19T15:31:24.377Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541646 AND AD_Tree_ID=10
;

-- Node name: `Import - Hauptbuchjournal`
-- 2026-02-19T15:31:24.472Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542298 AND AD_Tree_ID=10
;

-- Reordering children of `Finanzen`
-- Node name: `Kontenauszug`
-- 2026-02-19T15:31:50.507Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `Import - Hauptbuchjournal`
-- 2026-02-19T15:31:50.532Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542298 AND AD_Tree_ID=10
;

-- Node name: `Zahlungsavis (REMADV)`
-- 2026-02-19T15:31:50.556Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Hauptbuch Journal`
-- 2026-02-19T15:31:50.582Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bankkonto`
-- 2026-02-19T15:31:50.608Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `Hauptbuch - Aufteilung`
-- 2026-02-19T15:31:50.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP)`
-- 2026-02-19T15:31:50.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import - Bankauszug`
-- 2026-02-19T15:31:50.684Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bankauszug`
-- 2026-02-19T15:31:50.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Kassenbuch`
-- 2026-02-19T15:31:50.735Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankauszug Referenz`
-- 2026-02-19T15:31:50.761Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Zahlung`
-- 2026-02-19T15:31:50.789Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Zahlung Zuordnungen`
-- 2026-02-19T15:31:50.815Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Zahlung anweisen`
-- 2026-02-19T15:31:50.841Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Zahlungsvorbehalt`
-- 2026-02-19T15:31:50.870Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Zahlungsvorbehalt Erfassung`
-- 2026-02-19T15:31:50.895Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Mahnungen`
-- 2026-02-19T15:31:50.922Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Mahndisposition`
-- 2026-02-19T15:31:50.948Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Buchführungs-Details`
-- 2026-02-19T15:31:50.974Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Zahlungsimport`
-- 2026-02-19T15:31:51Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Kontenkombination`
-- 2026-02-19T15:31:51.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Kontenrahmen`
-- 2026-02-19T15:31:51.052Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Konten`
-- 2026-02-19T15:31:51.079Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Produktkosten`
-- 2026-02-19T15:31:51.105Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Produkt aktuelle Kosten`
-- 2026-02-19T15:31:51.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Produkte ohne initialen Kostenpreis`
-- 2026-02-19T15:31:51.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Buchbestand (Excel)`
-- 2026-02-19T15:31:51.182Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Type`
-- 2026-02-19T15:31:51.209Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Kosten-Detail`
-- 2026-02-19T15:31:51.234Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Kostenstellen Belege`
-- 2026-02-19T15:31:51.259Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Kostenstelle`
-- 2026-02-19T15:31:51.284Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz Nr.`
-- 2026-02-19T15:31:51.309Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz Nr. Art`
-- 2026-02-19T15:31:51.337Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Kosten Neubewertung`
-- 2026-02-19T15:31:51.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Buchungen Export`
-- 2026-02-19T15:31:51.388Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Abgeglichene Rechnungen`
-- 2026-02-19T15:31:51.414Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `Ungebuchte Belege`
-- 2026-02-19T15:31:51.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Zahlung`
-- 2026-02-19T15:31:51.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Alle ungebuchten Belege in die Warteschlange einreihen`
-- 2026-02-19T15:31:51.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2026-02-19T15:31:51.519Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Aktionen`
-- 2026-02-19T15:31:51.545Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2026-02-19T15:31:51.571Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `Berichte`
-- 2026-02-19T15:31:51.595Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Einstellungen`
-- 2026-02-19T15:31:51.621Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Kontoauszug Import-Datei`
-- 2026-02-19T15:31:51.647Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides`
-- 2026-02-19T15:31:51.673Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Tab: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D)
-- UI Section: main
-- 2026-02-19T15:32:37.215Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,508,547578,TO_TIMESTAMP('2026-02-19 15:32:36.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-02-19 15:32:36.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-19T15:32:37.296Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547578 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main
-- UI Column: 10
-- 2026-02-19T15:32:43.506Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549248,547578,TO_TIMESTAMP('2026-02-19 15:32:43.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-19 15:32:43.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main
-- UI Column: 20
-- 2026-02-19T15:32:47.537Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549249,547578,TO_TIMESTAMP('2026-02-19 15:32:47.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-19 15:32:47.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20
-- UI Element Group: main
-- 2026-02-19T15:32:58.667Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549249,554939,TO_TIMESTAMP('2026-02-19 15:32:58.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-02-19 15:32:58.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20 -> main.Aktiv
-- Column: I_GLJournal.IsActive
-- 2026-02-19T15:33:54.921Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7145,0,508,554939,648157,'F',TO_TIMESTAMP('2026-02-19 15:33:54.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2026-02-19 15:33:54.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20 -> main.Imported
-- Column: I_GLJournal.I_IsImported
-- 2026-02-19T15:34:18.797Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7123,0,508,554939,648158,'F',TO_TIMESTAMP('2026-02-19 15:34:18.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Has this import been processed','The Imported check box indicates if this import has been processed.','Y','N','N','Y','N','N','N',0,'Imported',20,0,0,TO_TIMESTAMP('2026-02-19 15:34:18.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20 -> main.Daten Import
-- Column: I_GLJournal.C_DataImport_ID
-- 2026-02-19T15:34:39.456Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768062,0,508,554939,648159,'F',TO_TIMESTAMP('2026-02-19 15:34:39.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Daten Import',30,0,0,TO_TIMESTAMP('2026-02-19 15:34:39.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20 -> main.Daten Import Verlauf
-- Column: I_GLJournal.C_DataImport_Run_ID
-- 2026-02-19T15:35:00.070Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768061,0,508,554939,648160,'F',TO_TIMESTAMP('2026-02-19 15:34:59.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Daten Import Verlauf',40,0,0,TO_TIMESTAMP('2026-02-19 15:34:59.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20 -> main.Import Fehlermeldung
-- Column: I_GLJournal.I_ErrorMsg
-- 2026-02-19T15:35:32.611Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7130,0,508,554939,648161,'F',TO_TIMESTAMP('2026-02-19 15:35:32.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Meldungen, die durch den Importprozess generiert wurden','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','Y','N','N','N',0,'Import Fehlermeldung',50,0,0,TO_TIMESTAMP('2026-02-19 15:35:32.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20 -> main.Journal
-- Column: I_GLJournal.GL_Journal_ID
-- 2026-02-19T15:36:02.893Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7153,0,508,554939,648162,'F',TO_TIMESTAMP('2026-02-19 15:36:02.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Business Partner to ship to','Y','N','N','Y','N','N','N',0,'Journal',60,0,0,TO_TIMESTAMP('2026-02-19 15:36:02.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20 -> main.Journal
-- Column: I_GLJournal.GL_Journal_ID
-- 2026-02-19T15:36:19.415Z
UPDATE AD_UI_Element SET Description='',Updated=TO_TIMESTAMP('2026-02-19 15:36:19.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648162
;

-- UI Column: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10
-- UI Element Group: main
-- 2026-02-19T15:37:35.451Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549248,554940,TO_TIMESTAMP('2026-02-19 15:37:35.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-02-19 15:37:35.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Kontoschlüssel von
-- Column: I_GLJournal.AccountValueFrom
-- 2026-02-19T15:38:43.345Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7106,0,508,554940,648163,'F',TO_TIMESTAMP('2026-02-19 15:38:42.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Kontoschlüssel von',10,0,0,TO_TIMESTAMP('2026-02-19 15:38:42.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Konto Aus
-- Column: I_GLJournal.AccountFrom_ID
-- 2026-02-19T15:38:59.746Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7104,0,508,554940,648164,'F',TO_TIMESTAMP('2026-02-19 15:38:59.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwendetes Konto','Das verwendete (Standard-) Konto','Y','N','Y','N','N','Konto Aus',20,0,0,TO_TIMESTAMP('2026-02-19 15:38:59.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Kontoschlüssel bis
-- Column: I_GLJournal.AccountValueTo
-- 2026-02-19T15:39:19.080Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556354,0,508,554940,648165,'F',TO_TIMESTAMP('2026-02-19 15:39:18.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Kontoschlüssel bis',30,0,0,TO_TIMESTAMP('2026-02-19 15:39:18.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Konto Zu
-- Column: I_GLJournal.AccountTo_ID
-- 2026-02-19T15:39:33.269Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556355,0,508,554940,648166,'F',TO_TIMESTAMP('2026-02-19 15:39:32.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwendetes Konto','Das verwendete (Standard-) Konto','Y','N','Y','N','N','Konto Zu',40,0,0,TO_TIMESTAMP('2026-02-19 15:39:32.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer Kontoschlüssel von
-- Column: I_GLJournal.TaxAccountValueFrom
-- 2026-02-19T15:40:14.666Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768072,0,508,554940,648167,'F',TO_TIMESTAMP('2026-02-19 15:40:14.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Steuer Kontoschlüssel von',50,0,0,TO_TIMESTAMP('2026-02-19 15:40:14.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer Konto Soll
-- Column: I_GLJournal.DR_Tax_Acct_ID
-- 2026-02-19T15:40:36.987Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768075,0,508,554940,648168,'F',TO_TIMESTAMP('2026-02-19 15:40:36.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Steuer Konto Soll',60,0,0,TO_TIMESTAMP('2026-02-19 15:40:36.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer Kontoschlüssel bis
-- Column: I_GLJournal.TaxAccountValueTo
-- 2026-02-19T15:41:09.159Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768073,0,508,554940,648169,'F',TO_TIMESTAMP('2026-02-19 15:41:08.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Steuer Kontoschlüssel bis',70,0,0,TO_TIMESTAMP('2026-02-19 15:41:08.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer Konto Haben
-- Column: I_GLJournal.CR_Tax_Acct_ID
-- 2026-02-19T15:45:06.175Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768074,0,508,554940,648170,'F',TO_TIMESTAMP('2026-02-19 15:45:05.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Steuer Konto Haben',80,0,0,TO_TIMESTAMP('2026-02-19 15:45:05.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.DR Tax Name
-- Column: I_GLJournal.DR_TaxName
-- 2026-02-19T15:45:27.912Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768070,0,508,554940,648171,'F',TO_TIMESTAMP('2026-02-19 15:45:27.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','DR Tax Name',90,0,0,TO_TIMESTAMP('2026-02-19 15:45:27.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer (Soll)
-- Column: I_GLJournal.DR_Tax_ID
-- 2026-02-19T15:45:39.722Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768069,0,508,554940,648172,'F',TO_TIMESTAMP('2026-02-19 15:45:39.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Steuerart','Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','N','Y','N','N','Steuer (Soll)',100,0,0,TO_TIMESTAMP('2026-02-19 15:45:39.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.CR Tax Name
-- Column: I_GLJournal.CR_TaxName
-- 2026-02-19T15:46:10.010Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768071,0,508,554940,648173,'F',TO_TIMESTAMP('2026-02-19 15:46:09.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','CR Tax Name',110,0,0,TO_TIMESTAMP('2026-02-19 15:46:09.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer (Haben)
-- Column: I_GLJournal.CR_Tax_ID
-- 2026-02-19T15:46:19.800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768068,0,508,554940,648174,'F',TO_TIMESTAMP('2026-02-19 15:46:19.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Steuerart','Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','N','Y','N','N','Steuer (Haben)',120,0,0,TO_TIMESTAMP('2026-02-19 15:46:19.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Summe (Soll)
-- Column: I_GLJournal.DR_TaxTotalAmt
-- 2026-02-19T15:46:33.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768064,0,508,554940,648175,'F',TO_TIMESTAMP('2026-02-19 15:46:32.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Summe (Soll)',130,0,0,TO_TIMESTAMP('2026-02-19 15:46:32.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Summe (Haben)
-- Column: I_GLJournal.CR_TaxTotalAmt
-- 2026-02-19T15:46:44.822Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768065,0,508,554940,648176,'F',TO_TIMESTAMP('2026-02-19 15:46:44.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Summe (Haben)',140,0,0,TO_TIMESTAMP('2026-02-19 15:46:44.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.ISO Währungscode
-- Column: I_GLJournal.ISO_Code
-- 2026-02-19T15:47:11.344Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7143,0,508,554940,648177,'F',TO_TIMESTAMP('2026-02-19 15:47:10.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dreibuchstabiger ISO 4217 Code für die Währung','Für Details - http://www.unece.org/cefact/recommendations/rec09/rec09_ecetrd203.pdf','Y','N','Y','N','N','ISO Währungscode',150,0,0,TO_TIMESTAMP('2026-02-19 15:47:10.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Währung
-- Column: I_GLJournal.C_Currency_ID
-- 2026-02-19T15:47:28.429Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7147,0,508,554940,648178,'F',TO_TIMESTAMP('2026-02-19 15:47:27.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',160,0,0,TO_TIMESTAMP('2026-02-19 15:47:27.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.ActivityValue
-- Column: I_GLJournal.ActivityValue
-- 2026-02-19T15:47:42.295Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,768063,0,508,554940,648179,'F',TO_TIMESTAMP('2026-02-19 15:47:41.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','ActivityValue',170,0,0,TO_TIMESTAMP('2026-02-19 15:47:41.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Kostenstelle
-- Column: I_GLJournal.C_Activity_ID
-- 2026-02-19T15:47:58.667Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7109,0,508,554940,648180,'F',TO_TIMESTAMP('2026-02-19 15:47:58.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','Kostenstelle',180,0,0,TO_TIMESTAMP('2026-02-19 15:47:58.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Soll
-- Column: I_GLJournal.AmtAcctDr
-- 2026-02-19T15:48:24.518Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7127,0,508,554940,648181,'F',TO_TIMESTAMP('2026-02-19 15:48:24.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ausgewiesener Verbindlichkeitsbetrag','"Ausgewiesene Verbindlichkeit" zeigt den in die Buchführungswährung dieser Organisation umgerechneten Betrag der Transaktion an.','Y','N','Y','N','N','Soll',190,0,0,TO_TIMESTAMP('2026-02-19 15:48:24.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Haben
-- Column: I_GLJournal.AmtAcctCr
-- 2026-02-19T15:48:37.738Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7137,0,508,554940,648182,'F',TO_TIMESTAMP('2026-02-19 15:48:37.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Effektiver Preis','"Ausgewiesene Forderung" zeigt den in die Buchführungswährung dieser Organisation umgerechneten Betrag der Transaktion an.','Y','N','Y','N','N','Haben',200,0,0,TO_TIMESTAMP('2026-02-19 15:48:37.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Buchungsdatum
-- Column: I_GLJournal.DateAcct
-- 2026-02-19T15:49:01.321Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7125,0,508,554940,648183,'F',TO_TIMESTAMP('2026-02-19 15:49:00.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','N','Buchungsdatum',210,0,0,TO_TIMESTAMP('2026-02-19 15:49:00.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Buchungsart
-- Column: I_GLJournal.PostingType
-- 2026-02-19T15:49:16.079Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7112,0,508,554940,648184,'F',TO_TIMESTAMP('2026-02-19 15:49:15.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Art des gebuchten Betrages dieser Transaktion','Die "Buchungsart" zeigt die Art des Betrages (Ist, Budget, Reservierung, Statistitisch) der Transaktion an.','Y','N','Y','N','N','Buchungsart',220,0,0,TO_TIMESTAMP('2026-02-19 15:49:15.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20 -> main.Journal
-- Column: I_GLJournal.GL_Journal_ID
-- 2026-02-19T15:51:12.079Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-02-19 15:51:12.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648162
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 20 -> main.Imported
-- Column: I_GLJournal.I_IsImported
-- 2026-02-19T15:51:12.290Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-19 15:51:12.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648158
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Buchungsart
-- Column: I_GLJournal.PostingType
-- 2026-02-19T15:51:12.499Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-19 15:51:12.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648184
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Buchungsdatum
-- Column: I_GLJournal.DateAcct
-- 2026-02-19T15:51:12.710Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-19 15:51:12.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648183
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.ISO Währungscode
-- Column: I_GLJournal.ISO_Code
-- 2026-02-19T15:51:12.939Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-19 15:51:12.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648177
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Konto Zu
-- Column: I_GLJournal.AccountTo_ID
-- 2026-02-19T15:51:13.149Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-19 15:51:13.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648166
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Konto Aus
-- Column: I_GLJournal.AccountFrom_ID
-- 2026-02-19T15:51:13.360Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-19 15:51:13.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648164
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Soll
-- Column: I_GLJournal.AmtAcctDr
-- 2026-02-19T15:51:13.570Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-19 15:51:13.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648181
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Haben
-- Column: I_GLJournal.AmtAcctCr
-- 2026-02-19T15:51:13.782Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-19 15:51:13.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648182
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.CR Tax Name
-- Column: I_GLJournal.CR_TaxName
-- 2026-02-19T15:51:13.980Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-02-19 15:51:13.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648173
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.DR Tax Name
-- Column: I_GLJournal.DR_TaxName
-- 2026-02-19T15:51:14.162Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-02-19 15:51:14.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648171
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer Konto Haben
-- Column: I_GLJournal.CR_Tax_Acct_ID
-- 2026-02-19T15:51:14.343Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-02-19 15:51:14.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648170
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer Konto Soll
-- Column: I_GLJournal.DR_Tax_Acct_ID
-- 2026-02-19T15:51:14.530Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-02-19 15:51:14.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648168
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer (Haben)
-- Column: I_GLJournal.CR_Tax_ID
-- 2026-02-19T15:51:14.711Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-02-19 15:51:14.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648174
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Steuer (Soll)
-- Column: I_GLJournal.DR_Tax_ID
-- 2026-02-19T15:51:14.904Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-02-19 15:51:14.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648172
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Summe (Haben)
-- Column: I_GLJournal.CR_TaxTotalAmt
-- 2026-02-19T15:51:15.081Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-02-19 15:51:15.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648176
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Summe (Soll)
-- Column: I_GLJournal.DR_TaxTotalAmt
-- 2026-02-19T15:51:15.270Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-02-19 15:51:15.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648175
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Währung
-- Column: I_GLJournal.C_Currency_ID
-- 2026-02-19T15:51:15.449Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-02-19 15:51:15.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648178
;

-- UI Element: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> main -> 10 -> main.Kostenstelle
-- Column: I_GLJournal.C_Activity_ID
-- 2026-02-19T15:51:15.629Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-02-19 15:51:15.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648180
;

