-- 2019-09-19T09:00:45.098Z
-- URL zum Konzept
UPDATE AD_Window_Trl SET Name='Abgleich Rechnung',Updated=TO_TIMESTAMP('2019-09-19 12:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Window_ID=107
;

-- 2019-09-19T09:01:13.020Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,408,541500,TO_TIMESTAMP('2019-09-19 12:01:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-19 12:01:12','YYYY-MM-DD HH24:MI:SS'),100,'primary')
;

-- 2019-09-19T09:01:13.020Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541500 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-19T09:02:11.560Z
-- URL zum Konzept
UPDATE AD_UI_Section SET Value='main',Updated=TO_TIMESTAMP('2019-09-19 12:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=541500
;

-- 2019-09-19T09:02:24.674Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541919,541500,TO_TIMESTAMP('2019-09-19 12:02:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-19 12:02:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:02:26.873Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541920,541500,TO_TIMESTAMP('2019-09-19 12:02:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-09-19 12:02:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:03:06.437Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541920,542904,TO_TIMESTAMP('2019-09-19 12:03:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-09-19 12:03:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:03:16.678Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541920,542905,TO_TIMESTAMP('2019-09-19 12:03:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2019-09-19 12:03:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:03:49.341Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='flags',Updated=TO_TIMESTAMP('2019-09-19 12:03:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542904
;

-- 2019-09-19T09:03:59.668Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2019-09-19 12:03:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542904
;

-- 2019-09-19T09:04:07.300Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='org',Updated=TO_TIMESTAMP('2019-09-19 12:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542905
;

-- 2019-09-19T09:04:36.568Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541919,542906,TO_TIMESTAMP('2019-09-19 12:04:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-09-19 12:04:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:04:45.052Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541919,542907,TO_TIMESTAMP('2019-09-19 12:04:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2019-09-19 12:04:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:08:31.949Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,11187,0,408,561864,542906,'F',TO_TIMESTAMP('2019-09-19 12:08:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Nr.',10,0,0,TO_TIMESTAMP('2019-09-19 12:08:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:09:47.964Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5354,0,408,561865,542906,'F',TO_TIMESTAMP('2019-09-19 12:09:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Wareneingangs- Position',20,0,0,TO_TIMESTAMP('2019-09-19 12:09:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:10:25.643Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5356,0,408,561866,542906,'F',TO_TIMESTAMP('2019-09-19 12:10:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Produkt',30,0,0,TO_TIMESTAMP('2019-09-19 12:10:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:10:44.935Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,12166,0,408,561867,542906,'F',TO_TIMESTAMP('2019-09-19 12:10:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Merkmale',40,0,0,TO_TIMESTAMP('2019-09-19 12:10:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:12:07.717Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,12650,0,408,561868,542907,'F',TO_TIMESTAMP('2019-09-19 12:12:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2019-09-19 12:12:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:12:47.726Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5351,0,408,561869,542907,'F',TO_TIMESTAMP('2019-09-19 12:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Abgleich Rechnung',20,0,0,TO_TIMESTAMP('2019-09-19 12:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:13:42.877Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5360,0,408,561870,542904,'F',TO_TIMESTAMP('2019-09-19 12:13:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2019-09-19 12:13:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:14:22.697Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5361,0,408,561871,542904,'F',TO_TIMESTAMP('2019-09-19 12:14:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verarbeitet',20,0,0,TO_TIMESTAMP('2019-09-19 12:14:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:14:43.644Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5359,0,408,561872,542904,'F',TO_TIMESTAMP('2019-09-19 12:14:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verbucht',30,0,0,TO_TIMESTAMP('2019-09-19 12:14:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:16:01.342Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5358,0,408,561873,542904,'F',TO_TIMESTAMP('2019-09-19 12:16:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Löschen',40,0,0,TO_TIMESTAMP('2019-09-19 12:16:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:16:49.531Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5353,0,408,561874,542905,'F',TO_TIMESTAMP('2019-09-19 12:16:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2019-09-19 12:16:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:17:07.261Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5352,0,408,561875,542905,'F',TO_TIMESTAMP('2019-09-19 12:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2019-09-19 12:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:17:53.923Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5355,0,408,561876,542907,'F',TO_TIMESTAMP('2019-09-19 12:17:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnungsposition',30,0,0,TO_TIMESTAMP('2019-09-19 12:17:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:18:35.466Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5362,0,408,561877,542907,'F',TO_TIMESTAMP('2019-09-19 12:18:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Menge',40,0,0,TO_TIMESTAMP('2019-09-19 12:18:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:19:20.857Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541920,542908,TO_TIMESTAMP('2019-09-19 12:19:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2019-09-19 12:19:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:19:27.512Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-09-19 12:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542905
;

-- 2019-09-19T09:20:01.900Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,11194,0,408,561878,542908,'F',TO_TIMESTAMP('2019-09-19 12:20:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Buchungsdatum',10,0,0,TO_TIMESTAMP('2019-09-19 12:20:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:20:25Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,5357,0,408,561879,542908,'F',TO_TIMESTAMP('2019-09-19 12:20:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vorgangsdatum',20,0,0,TO_TIMESTAMP('2019-09-19 12:20:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T09:22:53.976Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.978Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.978Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.979Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.979Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.980Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.980Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.981Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.981Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.982Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.982Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.983Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.983Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.984Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.984Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.985Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.985Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.986Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.986Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.987Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.987Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.988Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.988Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.996Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.996Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.997Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.997Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.998Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.998Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2019-09-19T09:22:53.999Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2019-09-19T09:24:40.269Z
-- URL zum Konzept
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2019-09-19 12:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11194
;

-- 2019-09-19T10:22:34.634Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=20, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2019-09-19 13:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6512
;

-- 2019-09-19T10:23:59.697Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='N',Updated=TO_TIMESTAMP('2019-09-19 13:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6510
;

-- 2019-09-19T10:25:37.097Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542907, SeqNo=50,Updated=TO_TIMESTAMP('2019-09-19 13:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561867
;

-- 2019-09-19T10:25:53.646Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542906, SeqNo=40,Updated=TO_TIMESTAMP('2019-09-19 13:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561869
;

-- 2019-09-19T10:30:56.620Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-09-19 13:30:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561876
;

-- 2019-09-19T10:31:03.132Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-09-19 13:31:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561867
;

-- 2019-09-19T10:38:20.075Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-09-19 13:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561877
;

-- 2019-09-19T10:38:26.675Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-09-19 13:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561867
;

