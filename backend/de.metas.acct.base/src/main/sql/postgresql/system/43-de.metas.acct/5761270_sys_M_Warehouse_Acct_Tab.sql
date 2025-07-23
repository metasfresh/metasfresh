-- Run mode: SWING_CLIENT

-- Tab: Lager und Lagerort(139,D) -> Buchführung
-- Table: M_Warehouse_Acct
-- 2025-07-21T16:13:34.982Z
UPDATE AD_Tab SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-07-21 16:13:34.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=209
;

-- Tab: Lager und Lagerort(139,D) -> Buchführung(209,D)
-- UI Section: default
-- 2025-07-21T16:14:14.607Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,209,546819,TO_TIMESTAMP('2025-07-21 16:14:14.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-07-21 16:14:14.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'default')
;

-- 2025-07-21T16:14:14.609Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546819 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default
-- UI Column: 10
-- 2025-07-21T16:14:19.529Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548301,546819,TO_TIMESTAMP('2025-07-21 16:14:19.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-07-21 16:14:19.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10
-- UI Element Group: default
-- 2025-07-21T16:14:33.993Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548301,553265,TO_TIMESTAMP('2025-07-21 16:14:33.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,TO_TIMESTAMP('2025-07-21 16:14:33.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Buchführungs-Schema
-- Column: M_Warehouse_Acct.C_AcctSchema_ID
-- 2025-07-21T16:14:55.610Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1455,0,209,553265,635295,'F',TO_TIMESTAMP('2025-07-21 16:14:55.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stammdaten für Buchhaltung','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','N','Buchführungs-Schema',10,0,0,TO_TIMESTAMP('2025-07-21 16:14:55.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Aktiv
-- Column: M_Warehouse_Acct.IsActive
-- 2025-07-21T16:15:19.331Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1458,0,209,553265,635296,'F',TO_TIMESTAMP('2025-07-21 16:15:19.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',20,0,0,TO_TIMESTAMP('2025-07-21 16:15:19.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Lager Wert Korrektur
-- Column: M_Warehouse_Acct.W_InvActualAdjust_Acct
-- 2025-07-21T16:15:43.528Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4865,0,209,553265,635297,'F',TO_TIMESTAMP('2025-07-21 16:15:43.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Korrekturen am Lager Wert (i.d.R. mit Konto Warenbestand identisch)','In actual costing systems, this account is used to post Inventory value adjustments. You could set it to the standard Inventory Asset account.','Y','N','Y','N','N','Lager Wert Korrektur',30,0,0,TO_TIMESTAMP('2025-07-21 16:15:43.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Lager Bestand Korrektur
-- Column: M_Warehouse_Acct.W_Differences_Acct
-- 2025-07-21T16:15:49.890Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2598,0,209,553265,635298,'F',TO_TIMESTAMP('2025-07-21 16:15:49.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Differenzen im Lagerbestand (erfasst durch Inventur)','The Warehouse Differences Account indicates the account used recording differences identified during inventory counts.','Y','N','Y','N','N','Lager Bestand Korrektur',40,0,0,TO_TIMESTAMP('2025-07-21 16:15:49.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Lager Wert Korrektur Währungsdifferenz
-- Column: M_Warehouse_Acct.W_Revaluation_Acct
-- 2025-07-21T16:15:56.634Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3860,0,209,553265,635299,'F',TO_TIMESTAMP('2025-07-21 16:15:56.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Lager Wert Korrektur Währungsdifferenz','The Inventory Revaluation Account identifies the account used to records changes in inventory value due to currency revaluation.','Y','N','Y','N','N','Lager Wert Korrektur Währungsdifferenz',50,0,0,TO_TIMESTAMP('2025-07-21 16:15:56.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Organisation
-- Column: M_Warehouse_Acct.AD_Org_ID
-- 2025-07-21T16:16:04.129Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2063,0,209,553265,635300,'F',TO_TIMESTAMP('2025-07-21 16:16:04.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',60,0,0,TO_TIMESTAMP('2025-07-21 16:16:04.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Buchführungs-Schema
-- Column: M_Warehouse_Acct.C_AcctSchema_ID
-- 2025-07-21T16:16:39.792Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-07-21 16:16:39.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635295
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Aktiv
-- Column: M_Warehouse_Acct.IsActive
-- 2025-07-21T16:16:39.797Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-07-21 16:16:39.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635296
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Lager Bestand Korrektur
-- Column: M_Warehouse_Acct.W_Differences_Acct
-- 2025-07-21T16:16:39.804Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-07-21 16:16:39.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635298
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Lager Wert Korrektur
-- Column: M_Warehouse_Acct.W_InvActualAdjust_Acct
-- 2025-07-21T16:16:39.810Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-07-21 16:16:39.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635297
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Lager Wert Korrektur Währungsdifferenz
-- Column: M_Warehouse_Acct.W_Revaluation_Acct
-- 2025-07-21T16:16:39.816Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-07-21 16:16:39.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635299
;

-- UI Element: Lager und Lagerort(139,D) -> Buchführung(209,D) -> default -> 10 -> default.Organisation
-- Column: M_Warehouse_Acct.AD_Org_ID
-- 2025-07-21T16:16:39.821Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-07-21 16:16:39.821000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635300
;

