-- Run mode: SWING_CLIENT

-- Tab: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D)
-- UI Section: main
-- 2025-04-15T13:51:55.017Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,646,546539,TO_TIMESTAMP('2025-04-15 13:51:54.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-15 13:51:54.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-04-15T13:51:55.069Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546539 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main
-- UI Column: 10
-- 2025-04-15T13:52:15.261Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547982,546539,TO_TIMESTAMP('2025-04-15 13:52:14.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-04-15 13:52:14.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 10
-- UI Element Group: schema
-- 2025-04-15T13:53:18.474Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547982,552735,TO_TIMESTAMP('2025-04-15 13:53:17.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','schema',10,'primary',TO_TIMESTAMP('2025-04-15 13:53:17.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 10 -> schema.Buchführungs Schema
-- Column: GL_Distribution.C_AcctSchema_ID
-- 2025-04-15T13:54:17.570Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10144,0,646,552735,631344,'F',TO_TIMESTAMP('2025-04-15 13:54:16.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stammdaten für Buchhaltung','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','N','Y','N','N','N',0,'Buchführungs Schema',10,0,0,TO_TIMESTAMP('2025-04-15 13:54:16.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 10 -> schema.Name
-- Column: GL_Distribution.Name
-- 2025-04-15T13:54:37.906Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10151,0,646,552735,631345,'F',TO_TIMESTAMP('2025-04-15 13:54:37.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2025-04-15 13:54:37.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 10 -> schema.Beschreibung
-- Column: GL_Distribution.Description
-- 2025-04-15T13:55:08.809Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10147,0,646,552735,631346,'F',TO_TIMESTAMP('2025-04-15 13:55:08.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',30,0,0,TO_TIMESTAMP('2025-04-15 13:55:08.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 10
-- UI Element Group: Divers
-- 2025-04-15T13:55:53.406Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547982,552736,TO_TIMESTAMP('2025-04-15 13:55:52.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Divers',20,TO_TIMESTAMP('2025-04-15 13:55:52.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 10 -> Divers.Kommentar/Hilfe
-- Column: GL_Distribution.Help
-- 2025-04-15T13:56:37.402Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10152,0,646,552736,631347,'F',TO_TIMESTAMP('2025-04-15 13:56:36.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','N','Y','N','N','N',0,'Kommentar/Hilfe',10,0,0,TO_TIMESTAMP('2025-04-15 13:56:36.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 10 -> Divers.Buchungsart
-- Column: GL_Distribution.PostingType
-- 2025-04-15T13:56:54.096Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10875,0,646,552736,631348,'F',TO_TIMESTAMP('2025-04-15 13:56:53.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','N','Y','N','N','N',0,'Buchungsart',20,0,0,TO_TIMESTAMP('2025-04-15 13:56:53.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 10 -> Divers.Belegart
-- Column: GL_Distribution.C_DocType_ID
-- 2025-04-15T13:57:08.814Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10862,0,646,552736,631349,'F',TO_TIMESTAMP('2025-04-15 13:57:08.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','N','N','N',0,'Belegart',30,0,0,TO_TIMESTAMP('2025-04-15 13:57:08.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 10 -> Divers.Total Percent
-- Column: GL_Distribution.PercentTotal
-- 2025-04-15T13:57:29.315Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10870,0,646,552736,631350,'F',TO_TIMESTAMP('2025-04-15 13:57:28.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sum of the Percent details ','Y','N','N','Y','N','N','N',0,'Total Percent',40,0,0,TO_TIMESTAMP('2025-04-15 13:57:28.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main
-- UI Column: 20
-- 2025-04-15T13:57:52.668Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547983,546539,TO_TIMESTAMP('2025-04-15 13:57:52.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-04-15 13:57:52.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20
-- UI Element Group: flags
-- 2025-04-15T13:58:14.723Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547983,552737,TO_TIMESTAMP('2025-04-15 13:58:14.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-04-15 13:58:14.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Aktiv
-- Column: GL_Distribution.IsActive
-- 2025-04-15T13:58:36.384Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10146,0,646,552737,631351,'F',TO_TIMESTAMP('2025-04-15 13:58:35.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-04-15 13:58:35.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Create Reversal
-- Column: GL_Distribution.IsCreateReversal
-- 2025-04-15T13:58:54.169Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56748,0,646,552737,631352,'F',TO_TIMESTAMP('2025-04-15 13:58:53.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Indicates that reversal movement will be created, if disabled the original movement will be deleted.','Y','N','N','Y','N','N','N',0,'Create Reversal',20,0,0,TO_TIMESTAMP('2025-04-15 13:58:53.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Any Organization
-- Column: GL_Distribution.AnyOrg
-- 2025-04-15T13:59:11.649Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10866,0,646,552737,631353,'F',TO_TIMESTAMP('2025-04-15 13:59:10.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Match any value of the Organization segment','If selected, any value of the account segment will match. If not selected, but no value of the accounting segment is selected, the matched value must be null (i.e. not defined).','Y','N','N','Y','N','N','N',0,'Any Organization',30,0,0,TO_TIMESTAMP('2025-04-15 13:59:10.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Any Account
-- Column: GL_Distribution.AnyAcct
-- 2025-04-15T13:59:25.367Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10860,0,646,552737,631354,'F',TO_TIMESTAMP('2025-04-15 13:59:24.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Match any value of the Account segment','If selected, any value of the account segment will match. If not selected, but no value of the accounting segment is selected, the matched value must be null (i.e. not defined).','Y','N','N','Y','N','N','N',0,'Any Account',40,0,0,TO_TIMESTAMP('2025-04-15 13:59:24.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Any Activity
-- Column: GL_Distribution.AnyActivity
-- 2025-04-15T13:59:38.527Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10868,0,646,552737,631355,'F',TO_TIMESTAMP('2025-04-15 13:59:37.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Match any value of the Activity segment','If selected, any value of the account segment will match. If not selected, but no value of the accounting segment is selected, the matched value must be null (i.e. not defined).','Y','N','N','Y','N','N','N',0,'Any Activity',50,0,0,TO_TIMESTAMP('2025-04-15 13:59:37.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Any Product
-- Column: GL_Distribution.AnyProduct
-- 2025-04-15T13:59:52.327Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10869,0,646,552737,631356,'F',TO_TIMESTAMP('2025-04-15 13:59:51.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Match any value of the Product segment','If selected, any value of the account segment will match. If not selected, but no value of the accounting segment is selected, the matched value must be null (i.e. not defined).','Y','N','N','Y','N','N','N',0,'Any Product',60,0,0,TO_TIMESTAMP('2025-04-15 13:59:51.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Any Bus.Partner
-- Column: GL_Distribution.AnyBPartner
-- 2025-04-15T14:00:06.143Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10864,0,646,552737,631357,'F',TO_TIMESTAMP('2025-04-15 14:00:05.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Match any value of the Business Partner segment','If selected, any value of the account segment will match. If not selected, but no value of the accounting segment is selected, the matched value must be null (i.e. not defined).','Y','N','N','Y','N','N','N',0,'Any Bus.Partner',70,0,0,TO_TIMESTAMP('2025-04-15 14:00:05.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Gültig
-- Column: GL_Distribution.IsValid
-- 2025-04-15T14:00:20.273Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10134,0,646,552737,631358,'F',TO_TIMESTAMP('2025-04-15 14:00:19.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Element ist gültig','Das Element hat die Überprüfung bestanden','Y','N','N','Y','N','N','N',0,'Gültig',80,0,0,TO_TIMESTAMP('2025-04-15 14:00:19.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Process Now
-- Column: GL_Distribution.Processing
-- 2025-04-15T14:00:38.263Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10140,0,646,552737,631359,'F',TO_TIMESTAMP('2025-04-15 14:00:37.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Process Now',90,0,0,TO_TIMESTAMP('2025-04-15 14:00:37.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20
-- UI Element Group: org
-- 2025-04-15T14:01:05.698Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547983,552738,TO_TIMESTAMP('2025-04-15 14:01:05.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-04-15 14:01:05.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> org.Sektion
-- Column: GL_Distribution.AD_Org_ID
-- 2025-04-15T14:01:26.684Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10139,0,646,552738,631360,'F',TO_TIMESTAMP('2025-04-15 14:01:26.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-04-15 14:01:26.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> org.Mandant
-- Column: GL_Distribution.AD_Client_ID
-- 2025-04-15T14:01:40.260Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10153,0,646,552738,631361,'F',TO_TIMESTAMP('2025-04-15 14:01:39.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-04-15 14:01:39.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: AnyAcct
-- 2025-04-15T14:06:01.714Z
UPDATE AD_Element_Trl SET Description='Übereinstimmung mit einem beliebigen Wert des Kontosegments', Help='Wenn diese Option ausgewählt ist, wird jeder Wert des Kontensegments abgeglichen. Wenn nicht ausgewählt, aber kein Wert des Buchhaltungssegments ausgewählt ist, muss der übereinstimmende Wert null sein (d. h. nicht definiert).', IsTranslated='Y', Name='Jedes Konto', PrintName='Jedes Konto',Updated=TO_TIMESTAMP('2025-04-15 14:06:01.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2591 AND AD_Language='de_DE'
;

-- 2025-04-15T14:06:01.770Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:06:07.845Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2591,'de_DE')
;

-- Element: AnyActivity
-- 2025-04-15T14:07:14.182Z
UPDATE AD_Element_Trl SET Description='Übereinstimmung mit einem beliebigen Wert des Aktivitätssegments', Help='Wenn diese Option ausgewählt ist, wird jeder Wert des Kontensegments abgeglichen. Wenn nicht ausgewählt, aber kein Wert des Buchhaltungssegments ausgewählt ist, muss der übereinstimmende Wert null sein (d. h. nicht definiert).', IsTranslated='Y', Name='Jede Aktivität', PrintName='Jede Aktivität',Updated=TO_TIMESTAMP('2025-04-15 14:07:14.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2592 AND AD_Language='de_DE'
;

-- 2025-04-15T14:07:14.237Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:07:20.889Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2592,'de_DE')
;

-- Element: AnyBPartner
-- 2025-04-15T14:08:27.387Z
UPDATE AD_Element_Trl SET Description='Übereinstimmung mit einem beliebigen Wert des Geschäftspartnersegments', Help='Wenn diese Option ausgewählt ist, wird jeder Wert des Kontensegments abgeglichen. Wenn nicht ausgewählt, aber kein Wert des Buchhaltungssegments ausgewählt ist, muss der übereinstimmende Wert null sein (d. h. nicht definiert).', IsTranslated='Y', Name='Jeder Geschäftspartner', PrintName='Jeder Geschäftspartner',Updated=TO_TIMESTAMP('2025-04-15 14:08:27.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2593 AND AD_Language='de_DE'
;

-- 2025-04-15T14:08:27.438Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:08:32.691Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2593,'de_DE')
;

-- Element: AnyOrg
-- 2025-04-15T14:10:54.498Z
UPDATE AD_Element_Trl SET Description='Passt zu jedem Wert des Organisationssegments', Help='Wenn diese Option ausgewählt ist, wird jeder Wert des Kontosegments abgeglichen. Wenn diese Option nicht ausgewählt ist, aber kein Wert des Buchhaltungssegments ausgewählt ist, muss der abgeglichene Wert null (d. h. nicht definiert) sein.', IsTranslated='Y', Name='Jede Organisation', PrintName='Jede Organisation',Updated=TO_TIMESTAMP('2025-04-15 14:10:54.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2597 AND AD_Language='de_DE'
;

-- 2025-04-15T14:10:54.555Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:11:00.701Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2597,'de_DE')
;

-- Element: AnyOrg
-- 2025-04-15T14:11:56.998Z
UPDATE AD_Element_Trl SET Description='Passt zu jedem Wert des Organisationssegments', IsTranslated='Y', Name='Jede Organisation', PrintName='Jede Organisation',Updated=TO_TIMESTAMP('2025-04-15 14:11:56.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2597 AND AD_Language='de_CH'
;

-- 2025-04-15T14:11:57.051Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:12:02.811Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2597,'de_CH')
;

-- 2025-04-15T14:12:02.863Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2597,'de_CH')
;

-- Element: AnyAcct
-- 2025-04-15T14:13:53.746Z
UPDATE AD_Element_Trl SET Description='Passt zu jedem Wert des Kontosegments', Help='Wenn diese Option ausgewählt ist, wird jeder Wert des Kontosegments abgeglichen. Wenn diese Option nicht ausgewählt ist, aber kein Wert des Buchhaltungssegments ausgewählt ist, muss der abgeglichene Wert null (d. h. nicht definiert) sein.', IsTranslated='Y', Name='Jedes Konto', PrintName='Jedes Konto',Updated=TO_TIMESTAMP('2025-04-15 14:13:53.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2591 AND AD_Language='de_CH'
;

-- 2025-04-15T14:13:53.799Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:14:02.022Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2591,'de_CH')
;

-- 2025-04-15T14:14:02.077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2591,'de_CH')
;

-- Element: AnyActivity
-- 2025-04-15T14:15:15.420Z
UPDATE AD_Element_Trl SET Description='Passt zu jedem Wert des Aktivitätssegments', Help='Wenn diese Option ausgewählt ist, wird jeder Wert des Kontosegments abgeglichen. Wenn diese Option nicht ausgewählt ist, aber kein Wert des Buchhaltungssegments ausgewählt ist, muss der abgeglichene Wert null (d. h. nicht definiert) sein.', IsTranslated='Y', Name='Jede Aktivität', PrintName='Jede Aktivität',Updated=TO_TIMESTAMP('2025-04-15 14:15:15.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2592 AND AD_Language='de_CH'
;

-- 2025-04-15T14:15:15.472Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:15:20.610Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2592,'de_CH')
;

-- 2025-04-15T14:15:20.662Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2592,'de_CH')
;

-- Element: AnyBPartner
-- 2025-04-15T14:16:17.634Z
UPDATE AD_Element_Trl SET Description='Passt zu jedem Wert des Geschäftspartnersegments', Help='Wenn diese Option ausgewählt ist, wird jeder Wert des Kontosegments abgeglichen. Wenn diese Option nicht ausgewählt ist, aber kein Wert des Buchhaltungssegments ausgewählt ist, muss der abgeglichene Wert null (d. h. nicht definiert) sein.', IsTranslated='Y', Name='Jeder Geschäftspartner', PrintName='Jeder Geschäftspartner',Updated=TO_TIMESTAMP('2025-04-15 14:16:17.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2593 AND AD_Language='de_CH'
;

-- 2025-04-15T14:16:17.686Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:16:22.267Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2593,'de_CH')
;

-- 2025-04-15T14:16:22.321Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2593,'de_CH')
;

-- Element: AnyProduct
-- 2025-04-15T14:17:33.970Z
UPDATE AD_Element_Trl SET Description='Passt zu jedem Wert des Produktsegments', Help='Wenn diese Option ausgewählt ist, wird jeder Wert des Kontosegments abgeglichen. Wenn diese Option nicht ausgewählt ist, aber kein Wert des Buchhaltungssegments ausgewählt ist, muss der abgeglichene Wert null (d. h. nicht definiert) sein.', IsTranslated='Y', Name='Jedes Produkt', PrintName='Jedes Produkt',Updated=TO_TIMESTAMP('2025-04-15 14:17:33.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2599 AND AD_Language='de_CH'
;

-- 2025-04-15T14:17:34.024Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:17:39.446Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2599,'de_CH')
;

-- 2025-04-15T14:17:39.499Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2599,'de_CH')
;

-- Element: AnyProduct
-- 2025-04-15T14:17:59.515Z
UPDATE AD_Element_Trl SET Description='Passt zu jedem Wert des Produktsegments', IsTranslated='Y', Name='Jedes Produkt', PrintName='Jedes Produkt',Updated=TO_TIMESTAMP('2025-04-15 14:17:59.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2599 AND AD_Language='de_DE'
;

-- 2025-04-15T14:17:59.567Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:18:04.972Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2599,'de_DE')
;

-- Element: IsCreateReversal
-- 2025-04-15T14:19:13.323Z
UPDATE AD_Element_Trl SET Description='Gibt an, dass eine Umkehrbewegung erstellt wird. Wenn diese Option deaktiviert ist, wird die ursprüngliche Bewegung gelöscht.', IsTranslated='Y', Name='Stornierung erstellen', PrintName='Stornierung erstellen',Updated=TO_TIMESTAMP('2025-04-15 14:19:13.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53795 AND AD_Language='de_DE'
;

-- 2025-04-15T14:19:13.376Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:19:19.352Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53795,'de_DE')
;

-- Element: IsCreateReversal
-- 2025-04-15T14:19:35.217Z
UPDATE AD_Element_Trl SET Description='Gibt an, dass eine Umkehrbewegung erstellt wird. Wenn diese Option deaktiviert ist, wird die ursprüngliche Bewegung gelöscht.', IsTranslated='Y', Name='Stornierung erstellen', PrintName='Stornierung erstellen',Updated=TO_TIMESTAMP('2025-04-15 14:19:35.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53795 AND AD_Language='de_CH'
;

-- 2025-04-15T14:19:35.268Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:19:42.277Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53795,'de_CH')
;

-- 2025-04-15T14:19:42.330Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53795,'de_CH')
;

-- Column: GL_Distribution.Processing
-- 2025-04-15T14:29:27.784Z
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='N', IsExcludeFromZoomTargets='Y', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-04-15 14:29:27.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=11653
;

-- UI Element: Hauptbuch - Aufteilung(323,D) -> Verteilung(646,D) -> main -> 20 -> flags.Process Now
-- Column: GL_Distribution.Processing
-- 2025-04-15T14:32:30.554Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2025-04-15 14:32:30.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631359
;

-- Element: PercentTotal
-- 2025-04-15T14:34:08.272Z
UPDATE AD_Element_Trl SET Description='Summe der Prozentangaben', IsTranslated='Y', Name='Gesamtprozentsatz', PrintName='Gesamtprozentsatz',Updated=TO_TIMESTAMP('2025-04-15 14:34:08.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2618 AND AD_Language='de_DE'
;

-- 2025-04-15T14:34:08.324Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:34:13.911Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2618,'de_DE')
;

-- Element: PercentTotal
-- 2025-04-15T14:34:26.993Z
UPDATE AD_Element_Trl SET Description='Summe der Prozentangaben', IsTranslated='Y', Name='Gesamtprozentsatz', PrintName='Gesamtprozentsatz',Updated=TO_TIMESTAMP('2025-04-15 14:34:26.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2618 AND AD_Language='de_CH'
;

-- 2025-04-15T14:34:27.045Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:34:31.883Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2618,'de_CH')
;

-- 2025-04-15T14:34:31.934Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2618,'de_CH')
;

-- Element: Percent
-- 2025-04-15T14:36:51.817Z
UPDATE AD_Element_Trl SET Help='Der Prozentsatz gibt den verwendeten Prozentsatz an.', IsTranslated='Y', Name='Prozent', PrintName='Prozent',Updated=TO_TIMESTAMP('2025-04-15 14:36:51.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=951 AND AD_Language='de_DE'
;

-- 2025-04-15T14:36:51.871Z
UPDATE AD_Element base SET Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:36:57.539Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(951,'de_DE')
;

-- Element: Percent
-- 2025-04-15T14:37:19.071Z
UPDATE AD_Element_Trl SET Description='Prozentsatz', Help='Der Prozentsatz gibt den verwendeten Prozentsatz an.', IsTranslated='Y', Name='Prozent', PrintName='Prozent',Updated=TO_TIMESTAMP('2025-04-15 14:37:19.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=951 AND AD_Language='de_CH'
;

-- 2025-04-15T14:37:19.124Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:37:25.202Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(951,'de_CH')
;

-- 2025-04-15T14:37:25.254Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(951,'de_CH')
;

-- Element: Percent
-- 2025-04-15T14:37:31.989Z
UPDATE AD_Element_Trl SET Description='Prozentsatz',Updated=TO_TIMESTAMP('2025-04-15 14:37:31.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=951 AND AD_Language='de_DE'
;

-- 2025-04-15T14:37:32.042Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:37:39.506Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(951,'de_DE')
;

-- Element: OverwriteOrg
-- 2025-04-15T14:38:41.993Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontosegment „Organisation“ mit dem angegebenen Wert', Help='Wenn nicht überschrieben, wird der Wert der ursprünglichen Kontenkombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Organisation überschreiben', PrintName='Organisation überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:38:41.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2611 AND AD_Language='de_DE'
;

-- 2025-04-15T14:38:42.044Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:38:51.505Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2611,'de_DE')
;

-- Element: OverwriteOrg
-- 2025-04-15T14:39:29.050Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontosegment Organisation mit dem angegebenen Wert', Help='Wenn nicht überschrieben, wird der Wert der ursprünglichen Kontenkombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Organisation überschreiben', PrintName='Organisation überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:39:29.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2611 AND AD_Language='de_CH'
;

-- 2025-04-15T14:39:29.104Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:39:35.295Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2611,'de_CH')
;

-- 2025-04-15T14:39:35.347Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2611,'de_CH')
;

-- Element: OverwriteAcct
-- 2025-04-15T14:41:15.362Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontosegment Konto mit dem angegebenen Wert', Help='Wenn es nicht überschrieben wird, wird der Wert der ursprünglichen Kontokombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Konto überschreiben', PrintName='Konto überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:41:15.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2605 AND AD_Language='de_CH'
;

-- 2025-04-15T14:41:15.415Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:41:21.239Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2605,'de_CH')
;

-- 2025-04-15T14:41:21.290Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2605,'de_CH')
;

-- Element: OverwriteAcct
-- 2025-04-15T14:42:15.016Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontosegment Konto mit dem angegebenen Wert', Help='Wenn es nicht überschrieben wird, wird der Wert der ursprünglichen Kontokombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Konto überschreiben', PrintName='Konto überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:42:15.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2605 AND AD_Language='de_DE'
;

-- 2025-04-15T14:42:15.069Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:42:21.770Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2605,'de_DE')
;

-- Element: OverwriteActivity
-- 2025-04-15T14:43:20.898Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontosegment Aktivität mit dem angegebenen Wert', Help='Wenn es nicht überschrieben wird, wird der Wert der ursprünglichen Kontokombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Aktivität überschreiben', PrintName='Aktivität überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:43:20.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2606 AND AD_Language='de_CH'
;

-- 2025-04-15T14:43:20.950Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:43:27.405Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2606,'de_CH')
;

-- 2025-04-15T14:43:27.459Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2606,'de_CH')
;

-- Element: OverwriteActivity
-- 2025-04-15T14:43:47.904Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontosegment Aktivität mit dem angegebenen Wert', Help='Wenn es nicht überschrieben wird, wird der Wert der ursprünglichen Kontokombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Aktivität überschreiben', PrintName='Aktivität überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:43:47.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2606 AND AD_Language='de_DE'
;

-- 2025-04-15T14:43:47.958Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:43:53.392Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2606,'de_DE')
;

-- Element: OverwriteProduct
-- 2025-04-15T14:44:46.553Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontosegment Product mit dem angegebenen Wert', Help='Wenn es nicht überschrieben wird, wird der Wert der ursprünglichen Kontokombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Produkt überschreiben', PrintName='Produkt überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:44:46.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2613 AND AD_Language='de_CH'
;

-- 2025-04-15T14:44:46.604Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:44:52.373Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2613,'de_CH')
;

-- 2025-04-15T14:44:52.425Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2613,'de_CH')
;

-- Element: OverwriteProduct
-- 2025-04-15T14:45:12.971Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontosegment Product mit dem angegebenen Wert', Help='Wenn es nicht überschrieben wird, wird der Wert der ursprünglichen Kontokombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Produkt überschreiben', PrintName='Produkt überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:45:12.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2613 AND AD_Language='de_DE'
;

-- 2025-04-15T14:45:13.023Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:45:17.430Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2613,'de_DE')
;

-- Element: OverwriteBPartner
-- 2025-04-15T14:46:18.499Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontosegment Geschäftspartner mit dem angegebenen Wert', Help='Wenn es nicht überschrieben wird, wird der Wert der ursprünglichen Kontokombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Geschäftspartner überschreiben', PrintName='Geschäftspartner überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:46:18.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2607 AND AD_Language='de_CH'
;

-- 2025-04-15T14:46:18.552Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:46:25.275Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2607,'de_CH')
;

-- 2025-04-15T14:46:25.329Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2607,'de_CH')
;

-- Element: OverwriteBPartner
-- 2025-04-15T14:46:51.320Z
UPDATE AD_Element_Trl SET Description='Überschreiben Sie das Kontensegment Geschäftspartner mit dem angegebenen Wert', Help='Wenn es nicht überschrieben wird, wird der Wert der ursprünglichen Kontokombination verwendet. Wenn ausgewählt, aber nicht angegeben, wird das Segment auf Null gesetzt.', IsTranslated='Y', Name='Geschäftspartner überschreiben', PrintName='Geschäftspartner überschreiben',Updated=TO_TIMESTAMP('2025-04-15 14:46:51.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2607 AND AD_Language='de_DE'
;

-- 2025-04-15T14:46:51.374Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-15T14:46:57.657Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2607,'de_DE')
;

-- Reordering children of `Finance`
-- Node name: `Remittance Advice (REMADV)`
-- 2025-04-15T14:49:21.684Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `GL Journal`
-- 2025-04-15T14:49:21.737Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account`
-- 2025-04-15T14:49:21.789Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement`
-- 2025-04-15T14:49:21.842Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP)`
-- 2025-04-15T14:49:21.893Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution`
-- 2025-04-15T14:49:21.948Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement`
-- 2025-04-15T14:49:22.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal`
-- 2025-04-15T14:49:22.055Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2025-04-15T14:49:22.106Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment`
-- 2025-04-15T14:49:22.158Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations`
-- 2025-04-15T14:49:22.282Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection`
-- 2025-04-15T14:49:22.334Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation`
-- 2025-04-15T14:49:22.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture`
-- 2025-04-15T14:49:22.540Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning`
-- 2025-04-15T14:49:22.680Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates`
-- 2025-04-15T14:49:22.734Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions`
-- 2025-04-15T14:49:22.828Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import`
-- 2025-04-15T14:49:22.981Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination`
-- 2025-04-15T14:49:23.075Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts`
-- 2025-04-15T14:49:23.126Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values`
-- 2025-04-15T14:49:23.180Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts`
-- 2025-04-15T14:49:23.294Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs`
-- 2025-04-15T14:49:23.346Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost`
-- 2025-04-15T14:49:23.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity`
-- 2025-04-15T14:49:23.553Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail`
-- 2025-04-15T14:49:23.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents`
-- 2025-04-15T14:49:23.776Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Center`
-- 2025-04-15T14:49:23.920Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation`
-- 2025-04-15T14:49:23.980Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Referenz No`
-- 2025-04-15T14:49:24.072Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type`
-- 2025-04-15T14:49:24.125Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export`
-- 2025-04-15T14:49:24.177Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices`
-- 2025-04-15T14:49:24.230Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents`
-- 2025-04-15T14:49:24.281Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment`
-- 2025-04-15T14:49:24.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents`
-- 2025-04-15T14:49:24.385Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-04-15T14:49:24.437Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2025-04-15T14:49:24.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2025-04-15T14:49:24.541Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-04-15T14:49:24.592Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-04-15T14:49:24.644Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File`
-- 2025-04-15T14:49:24.695Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides`
-- 2025-04-15T14:49:24.747Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

