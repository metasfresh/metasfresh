-- 2021-03-25T10:23:14.026Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2021-03-25 11:23:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579536
;

-- 2021-03-25T10:23:21.309Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-03-25 11:23:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579535
;

-- 2021-03-25T10:23:33.447Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2021-03-25 11:23:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579532
;

-- 2021-03-25T10:25:08.505Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543534,542890,TO_TIMESTAMP('2021-03-25 11:25:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,TO_TIMESTAMP('2021-03-25 11:25:08','YYYY-MM-DD HH24:MI:SS'),100,'default')
;

-- 2021-03-25T10:25:08.505Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542890 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-03-25T10:25:19.860Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543641,542890,TO_TIMESTAMP('2021-03-25 11:25:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-03-25 11:25:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:25:29.007Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543641,545482,TO_TIMESTAMP('2021-03-25 11:25:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,TO_TIMESTAMP('2021-03-25 11:25:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:26:05.854Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635151,0,543534,545482,582159,'F',TO_TIMESTAMP('2021-03-25 11:26:05','YYYY-MM-DD HH24:MI:SS'),100,'Hilfsmittelpositionsnummer','Y','N','N','N','N','N','N',0,'HiMiPo',10,0,0,TO_TIMESTAMP('2021-03-25 11:26:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:27:09.877Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635153,0,543534,545482,582160,'F',TO_TIMESTAMP('2021-03-25 11:27:09','YYYY-MM-DD HH24:MI:SS'),100,'Beeinflusst die Darstellung im Alberta-Warenkorb','Y','N','N','N','N','N','N',0,'Sortimentszuordnung',20,0,0,TO_TIMESTAMP('2021-03-25 11:27:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:27:22.887Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635148,0,543534,545482,582161,'F',TO_TIMESTAMP('2021-03-25 11:27:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N',0,'GrÃ¶ÃŸe',30,0,0,TO_TIMESTAMP('2021-03-25 11:27:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:27:47.577Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635150,0,543534,545482,582162,'F',TO_TIMESTAMP('2021-03-25 11:27:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N',0,'Artikelstatus',40,0,0,TO_TIMESTAMP('2021-03-25 11:27:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:28:29.330Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635145,0,543534,545482,582163,'F',TO_TIMESTAMP('2021-03-25 11:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei MÃ¶glichkeiten, einen Datensatz nicht mehr verfÃ¼gbar zu machen: einer ist, ihn zu lÃ¶schen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr fÃ¼r eine Auswahl verfÃ¼gbar, aber verfÃ¼gbar fÃ¼r die Verwendung in Berichten. Es gibt zwei GrÃ¼nde, DatensÃ¤tze zu deaktivieren und nicht zu lÃ¶schen: (1) Das System braucht den Datensatz fÃ¼r Revisionszwecke. (2) Der Datensatz wird von anderen DatensÃ¤tzen referenziert. Z.B. kÃ¶nnen Sie keinen GeschÃ¤ftspartner lÃ¶schen, wenn es Rechnungen fÃ¼r diesen GeschÃ¤ftspartner gibt. Sie deaktivieren den GeschÃ¤ftspartner und verhindern, dass dieser Eintrag in zukÃ¼nftigen VorgÃ¤ngen verwendet wird.','Y','N','N','N','N','N','N',0,'Aktiv',50,0,0,TO_TIMESTAMP('2021-03-25 11:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:28:51.121Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635147,0,543534,545482,582164,'F',TO_TIMESTAMP('2021-03-25 11:28:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N',0,'Zusatzbeschreibung',60,0,0,TO_TIMESTAMP('2021-03-25 11:28:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:29:21.480Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635149,0,543534,545482,582165,'F',TO_TIMESTAMP('2021-03-25 11:29:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N',0,'Artikeltyp',70,0,0,TO_TIMESTAMP('2021-03-25 11:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:29:57.465Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635152,0,543534,545482,582166,'F',TO_TIMESTAMP('2021-03-25 11:29:57','YYYY-MM-DD HH24:MI:SS'),100,'Sterneranking (0-5) des einzelnen Artikels im Warenkorb, falls keine Wirtschaftlichkeitsberechnung gewÃ¼nscht','Y','N','N','N','N','N','N',0,'Sterne',80,0,0,TO_TIMESTAMP('2021-03-25 11:29:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:30:24.364Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635154,0,543534,545482,582167,'F',TO_TIMESTAMP('2021-03-25 11:30:24','YYYY-MM-DD HH24:MI:SS'),100,'Wirtschaftlichkeitsberechung - Werte von A bis F (A = sehr wirtschaftlich F = nicht wirtschaftlich)','Y','N','N','N','N','N','N',0,'Preisrating',90,0,0,TO_TIMESTAMP('2021-03-25 11:30:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:30:37.043Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635155,0,543534,545482,582168,'F',TO_TIMESTAMP('2021-03-25 11:30:36','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N',0,'Produkt',100,0,0,TO_TIMESTAMP('2021-03-25 11:30:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:30:52.478Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635143,0,543534,545482,582169,'F',TO_TIMESTAMP('2021-03-25 11:30:52','YYYY-MM-DD HH24:MI:SS'),100,'Mandant fÃ¼r diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie kÃ¶nnen keine Daten Ã¼ber Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N',0,'Mandant',110,0,0,TO_TIMESTAMP('2021-03-25 11:30:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T10:31:04.140Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635144,0,543534,545482,582170,'F',TO_TIMESTAMP('2021-03-25 11:31:04','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kÃ¶nnen Daten Ã¼ber Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N',0,'Sektion',120,0,0,TO_TIMESTAMP('2021-03-25 11:31:04','YYYY-MM-DD HH24:MI:SS'),100)
;

--Annamaria Seq NO

-- 2021-03-25T11:31:10.414Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=10, SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-03-25 12:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635151
;

-- 2021-03-25T11:31:22.828Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=20, SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-25 12:31:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635149
;

-- 2021-03-25T11:31:31.909Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=30, SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-03-25 12:31:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635148
;

-- 2021-03-25T11:31:39.358Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=40, SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-03-25 12:31:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635150
;

-- 2021-03-25T11:31:48.513Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=50, SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-03-25 12:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635145
;

-- 2021-03-25T11:32:09.666Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=60, SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-03-25 12:32:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635147
;

-- 2021-03-25T11:32:16.930Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=20, SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-25 12:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635153
;

-- 2021-03-25T11:32:26.494Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=70, SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-03-25 12:32:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635149
;

-- 2021-03-25T11:32:34.020Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=80, SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-03-25 12:32:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635152
;

-- 2021-03-25T11:32:40.204Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=90, SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-03-25 12:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635154
;

-- 2021-03-25T11:32:53.460Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=100, SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-03-25 12:32:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635155
;

-- 2021-03-25T11:33:07.147Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=110, SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-03-25 12:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635143
;

-- 2021-03-25T11:33:13.307Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=120, SeqNoGrid=120,Updated=TO_TIMESTAMP('2021-03-25 12:33:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635144
;

-- 2021-03-25T11:33:20.633Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=130, SeqNoGrid=130,Updated=TO_TIMESTAMP('2021-03-25 12:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635146
;

-- 2021-03-25T11:36:33.603Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=10, SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-03-25 12:36:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635551
;

-- 2021-03-25T11:36:42.663Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=20, SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-25 12:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635550
;

-- 2021-03-25T11:36:47.135Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=30, SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-03-25 12:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635546
;

-- 2021-03-25T11:36:52.226Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=40, SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-03-25 12:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635547
;

-- 2021-03-25T11:36:58.517Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=50, SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-03-25 12:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635548
;

-- 2021-03-25T11:37:04.103Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=60, SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-03-25 12:37:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635552
;

-- 2021-03-25T11:37:11.110Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=70, SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-03-25 12:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635549
;

-- 2021-03-25T12:06:03.673Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-03-25 13:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=582159
;

-- 2021-03-25T12:06:03.675Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-25 13:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=582164
;

-- 2021-03-25T12:06:03.677Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-03-25 13:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=582161
;

-- 2021-03-25T12:06:03.679Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-03-25 13:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=582160
;

-- 2021-03-25T12:07:03.893Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-03-25 13:07:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=582164
;

-- 2021-03-25T12:07:03.899Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-25 13:07:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=582160
;

-- 2021-03-25T12:07:03.905Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-03-25 13:07:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=582162
;

-- 2021-03-25T12:07:33.133Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-03-25 13:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579536
;

-- 2021-03-25T12:07:33.134Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-25 13:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579535
;

-- 2021-03-25T12:07:48.248Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2021-03-25 13:07:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635548
;

-- 2021-03-25T12:07:49.959Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2021-03-25 13:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635552
;

-- 2021-03-25T12:07:54.702Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2021-03-25 13:07:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635549
;
