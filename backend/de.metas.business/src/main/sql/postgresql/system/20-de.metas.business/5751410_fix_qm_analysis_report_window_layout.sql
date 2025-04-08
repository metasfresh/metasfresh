-- Run mode: SWING_CLIENT

-- UI Column: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20
-- UI Element Group: flags
-- 2025-04-07T12:06:18.344Z
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2025-04-07 12:06:18.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552717
;

-- UI Column: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10
-- UI Element Group: other
-- 2025-04-07T12:11:48.968Z
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2025-04-07 12:11:48.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552720
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Belegdatum
-- Column: QM_Analysis_Report.DateDoc
-- 2025-04-07T12:12:50.157Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741846,0,547945,552716,631336,'F',TO_TIMESTAMP('2025-04-07 12:12:49.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Belegs','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','N','Y','N','N','N',0,'Belegdatum',20,0,0,TO_TIMESTAMP('2025-04-07 12:12:49.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Beschreibung
-- Column: QM_Analysis_Report.Description
-- 2025-04-07T12:12:56.820Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741849,0,547945,552716,631337,'F',TO_TIMESTAMP('2025-04-07 12:12:56.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',30,0,0,TO_TIMESTAMP('2025-04-07 12:12:56.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> other.Beschreibung
-- Column: QM_Analysis_Report.Description
-- 2025-04-07T12:13:03.838Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=631299
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> other.Belegdatum
-- Column: QM_Analysis_Report.DateDoc
-- 2025-04-07T12:13:05.825Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=631298
;

-- UI Column: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20
-- UI Element Group: doctype
-- 2025-04-07T12:15:31.321Z
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2025-04-07 12:15:31.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552718
;

-- UI Column: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20
-- UI Element Group: flags
-- 2025-04-07T12:15:36.063Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2025-04-07 12:15:36.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552717
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Belegdatum
-- Column: QM_Analysis_Report.DateDoc
-- 2025-04-07T12:17:36.346Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=631336
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> doctype.Belegdatum
-- Column: QM_Analysis_Report.DateDoc
-- 2025-04-07T12:18:05.868Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741846,0,547945,552718,631338,'F',TO_TIMESTAMP('2025-04-07 12:18:05.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Belegs','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','N','Y','N','N','N',0,'Belegdatum',30,0,0,TO_TIMESTAMP('2025-04-07 12:18:05.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> doctype.Referenz
-- Column: QM_Analysis_Report.POReference
-- 2025-04-07T12:18:09.216Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2025-04-07 12:18:09.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631289
;

-- Tab: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D)
-- UI Section: advanced edit
-- 2025-04-07T12:43:35.610Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547945,546538,TO_TIMESTAMP('2025-04-07 12:43:35.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-04-07 12:43:35.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'advanced edit')
;

-- 2025-04-07T12:43:35.613Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546538 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> advanced edit
-- UI Column: 10
-- 2025-04-07T12:43:51.279Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547981,546538,TO_TIMESTAMP('2025-04-07 12:43:51.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-07 12:43:51.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> advanced edit -> 10
-- UI Element Group: advanced edit
-- 2025-04-07T12:44:01.572Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547981,552734,TO_TIMESTAMP('2025-04-07 12:44:01.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','advanced edit',10,TO_TIMESTAMP('2025-04-07 12:44:01.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> advanced edit -> 10 -> advanced edit.Aktiv
-- Column: QM_Analysis_Report.IsActive
-- 2025-04-07T12:44:14.227Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741837,0,547945,552734,631339,'F',TO_TIMESTAMP('2025-04-07 12:44:14.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-04-07 12:44:14.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> advanced edit -> 10 -> advanced edit.Verarbeitet
-- Column: QM_Analysis_Report.Processed
-- 2025-04-07T12:44:27.640Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741853,0,547945,552734,631340,'F',TO_TIMESTAMP('2025-04-07 12:44:27.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',20,0,0,TO_TIMESTAMP('2025-04-07 12:44:27.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> advanced edit -> 10 -> advanced edit.Verarbeitet
-- Column: QM_Analysis_Report.Processed
-- 2025-04-07T12:44:39.397Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-04-07 12:44:39.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631340
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> advanced edit -> 10 -> advanced edit.Aktiv
-- Column: QM_Analysis_Report.IsActive
-- 2025-04-07T12:44:44.029Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-04-07 12:44:44.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631339
;

-- UI Column: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20
-- UI Element Group: flags
-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> flags.Aktiv
-- Column: QM_Analysis_Report.IsActive
-- 2025-04-07T12:44:52.757Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=631284
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> flags.In Verarbeitung
-- Column: QM_Analysis_Report.IsProcessing
-- 2025-04-07T12:44:52.766Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=631295
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> flags.Verarbeitet
-- Column: QM_Analysis_Report.Processed
-- 2025-04-07T12:44:52.776Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=631296
;

-- 2025-04-07T12:44:52.781Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=552717
;

-- UI Column: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20
-- UI Element Group: other
-- 2025-04-07T12:44:58.703Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2025-04-07 12:44:58.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552721
;

-- UI Column: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20
-- UI Element Group: org
-- 2025-04-07T12:45:02.585Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2025-04-07 12:45:02.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552719
;

