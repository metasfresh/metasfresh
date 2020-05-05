-- 2019-09-18T11:57:59.411Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,662,541496,TO_TIMESTAMP('2019-09-18 14:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-18 14:57:59','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-09-18T11:57:59.427Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541496 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-18T11:58:39.724Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541911,541496,TO_TIMESTAMP('2019-09-18 14:58:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-18 14:58:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T11:59:06.939Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541911,542891,TO_TIMESTAMP('2019-09-18 14:59:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','Greeting & Name',10,'primary',TO_TIMESTAMP('2019-09-18 14:59:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:00:39.533Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541911,542892,TO_TIMESTAMP('2019-09-18 15:00:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','Sprache & Mitarbeiter',20,TO_TIMESTAMP('2019-09-18 15:00:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:04:43.136Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,56370,0,662,561826,542891,'F',TO_TIMESTAMP('2019-09-18 15:04:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verbucht',10,0,0,TO_TIMESTAMP('2019-09-18 15:04:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:07:18.688Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,10447,0,662,561827,542891,'F',TO_TIMESTAMP('2019-09-18 15:07:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Nr.',20,0,0,TO_TIMESTAMP('2019-09-18 15:07:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:08:05.345Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,10446,0,662,561828,542891,'F',TO_TIMESTAMP('2019-09-18 15:08:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verkaufs-Transaktion',30,0,0,TO_TIMESTAMP('2019-09-18 15:08:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:08:26.432Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,10450,0,662,561829,542891,'F',TO_TIMESTAMP('2019-09-18 15:08:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Belegdatum',40,0,0,TO_TIMESTAMP('2019-09-18 15:08:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:09:17.914Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,10452,0,662,561830,542891,'F',TO_TIMESTAMP('2019-09-18 15:09:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Buchungsdatum',50,0,0,TO_TIMESTAMP('2019-09-18 15:09:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:09:50.584Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,56369,0,662,561831,542891,'F',TO_TIMESTAMP('2019-09-18 15:09:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Belegstatus',60,0,0,TO_TIMESTAMP('2019-09-18 15:09:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:10:14.774Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,56371,0,662,561832,542891,'F',TO_TIMESTAMP('2019-09-18 15:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verarbeitet',70,0,0,TO_TIMESTAMP('2019-09-18 15:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:10:40.625Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,56372,0,662,561833,542891,'F',TO_TIMESTAMP('2019-09-18 15:10:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verarbeiten',80,0,0,TO_TIMESTAMP('2019-09-18 15:10:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:10:56.371Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,10445,0,662,561834,542891,'F',TO_TIMESTAMP('2019-09-18 15:10:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Aktiv',90,0,0,TO_TIMESTAMP('2019-09-18 15:10:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:11:36.504Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542892
;

-- 2019-09-18T12:12:09.420Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541912,541496,TO_TIMESTAMP('2019-09-18 15:12:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-09-18 15:12:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:13:40.589Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541912,542893,TO_TIMESTAMP('2019-09-18 15:13:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2019-09-18 15:13:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:14:16.309Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542893, SeqNo=10,Updated=TO_TIMESTAMP('2019-09-18 15:14:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561827
;

-- 2019-09-18T12:14:27.393Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542893, SeqNo=20,Updated=TO_TIMESTAMP('2019-09-18 15:14:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561834
;

-- 2019-09-18T12:15:53.224Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542893, SeqNo=30,Updated=TO_TIMESTAMP('2019-09-18 15:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561828
;

-- 2019-09-18T12:16:06.772Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542893, SeqNo=40,Updated=TO_TIMESTAMP('2019-09-18 15:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561832
;

-- 2019-09-18T12:16:13.760Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542893, SeqNo=50,Updated=TO_TIMESTAMP('2019-09-18 15:16:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561833
;

-- 2019-09-18T12:28:03.054Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-09-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561834
;

-- 2019-09-18T12:28:03.055Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-09-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561829
;

-- 2019-09-18T12:28:03.057Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-09-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561831
;

-- 2019-09-18T12:28:03.058Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-09-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561830
;

-- 2019-09-18T12:28:03.059Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-09-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561827
;

-- 2019-09-18T12:28:03.061Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-09-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561833
;

-- 2019-09-18T12:28:03.062Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-09-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561832
;

-- 2019-09-18T12:28:03.064Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-09-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561826
;

-- 2019-09-18T12:28:03.065Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-09-18 15:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561828
;

-- 2019-09-18T12:39:14.190Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,574146,541368,0,294,TO_TIMESTAMP('2019-09-18 15:39:14','YYYY-MM-DD HH24:MI:SS'),100,'Nicht verbuchte Belege','D','UnPosted Documents','Y','N','N','N','N','Ungebuchte Belege',TO_TIMESTAMP('2019-09-18 15:39:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T12:39:14.191Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541368 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-09-18T12:39:14.200Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541368, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541368)
;

-- 2019-09-18T12:39:14.249Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(574146) 
;

-- 2019-09-18T12:39:22.380Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53324 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.383Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=241 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.383Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=288 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.384Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=432 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.384Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=243 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.385Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=413 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.385Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=538 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.385Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=462 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.386Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=505 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.386Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=235 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.387Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=511 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.387Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=245 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.388Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=251 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.388Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=246 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.389Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=509 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.389Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=510 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.389Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=496 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.390Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=304 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.390Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=255 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.391Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=286 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.391Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=287 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.392Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=438 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.392Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=234 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.392Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=244 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.393Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53190 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.393Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53187 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.394Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540642 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.394Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540651 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.394Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540666 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000103 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:22.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.164Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.164Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.165Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.165Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.166Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.166Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.167Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.167Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.168Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.169Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.169Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.170Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.171Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.171Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.172Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.173Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.173Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.173Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.174Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.174Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.175Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:36.175Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.720Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.721Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.721Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.722Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.722Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.723Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.723Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.724Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.724Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.725Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.725Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.726Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.726Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.727Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.727Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.728Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:40.729Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.393Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.394Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.396Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.396Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.397Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.398Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.398Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.399Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.399Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.400Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.400Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.401Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.401Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.402Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.402Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.403Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.404Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.404Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.405Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.405Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.406Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.407Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.407Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.408Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.409Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.409Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2019-09-18T12:39:46.410Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2019-09-18T12:44:18.015Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2019-09-18 15:44:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=56349
;

-- 2019-09-18T12:45:45.991Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2019-09-18 15:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=56347
;

-- 2019-09-18T13:01:00.314Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542891, SeqNo=70,Updated=TO_TIMESTAMP('2019-09-18 16:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561827
;

-- 2019-09-18T13:02:40.192Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542893, SeqNo=60,Updated=TO_TIMESTAMP('2019-09-18 16:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561826
;

-- 2019-09-18T13:03:12.914Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-09-18 16:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561831
;

-- 2019-09-18T13:03:20.897Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2019-09-18 16:03:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561827
;

-- 2019-09-18T13:03:30.977Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-09-18 16:03:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561830
;

-- 2019-09-18T13:04:03.160Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-09-18 16:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561834
;

-- 2019-09-18T13:04:26.395Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-09-18 16:04:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561828
;

-- 2019-09-18T13:04:33.001Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2019-09-18 16:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561832
;

-- 2019-09-18T13:04:39.868Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2019-09-18 16:04:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561833
;

-- 2019-09-18T13:04:54.094Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2019-09-18 16:04:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561826
;

-- 2019-09-18T13:05:20.726Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541912,542894,TO_TIMESTAMP('2019-09-18 16:05:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','Org',20,TO_TIMESTAMP('2019-09-18 16:05:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T13:06:18.827Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,10449,0,662,561835,542891,'F',TO_TIMESTAMP('2019-09-18 16:06:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Record',100,0,0,TO_TIMESTAMP('2019-09-18 16:06:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T13:08:20.794Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,10451,0,662,561836,542891,'F',TO_TIMESTAMP('2019-09-18 16:08:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Table',110,0,0,TO_TIMESTAMP('2019-09-18 16:08:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T13:09:06.997Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542894, SeqNo=10,Updated=TO_TIMESTAMP('2019-09-18 16:09:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561836
;

-- 2019-09-18T13:09:15.095Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542894, SeqNo=20,Updated=TO_TIMESTAMP('2019-09-18 16:09:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561835
;

-- 2019-09-18T13:22:02.217Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=120,Updated=TO_TIMESTAMP('2019-09-18 16:22:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10448
;

-- 2019-09-18T13:25:48.587Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,10448,0,662,561838,542894,'F',TO_TIMESTAMP('2019-09-18 16:25:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',120,0,0,TO_TIMESTAMP('2019-09-18 16:25:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T13:26:14.900Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,10453,0,662,561839,542894,'F',TO_TIMESTAMP('2019-09-18 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',130,0,0,TO_TIMESTAMP('2019-09-18 16:26:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T13:29:39.999Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=10,Updated=TO_TIMESTAMP('2019-09-18 16:29:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10447
;

-- 2019-09-18T13:29:56.665Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=20,Updated=TO_TIMESTAMP('2019-09-18 16:29:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56369
;

-- 2019-09-18T13:30:00.337Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=30,Updated=TO_TIMESTAMP('2019-09-18 16:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10450
;

-- 2019-09-18T13:30:10.883Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=40,Updated=TO_TIMESTAMP('2019-09-18 16:30:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10452
;

-- 2019-09-18T13:30:24.034Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2019-09-18 16:30:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10445
;

-- 2019-09-18T13:30:30.314Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=60,Updated=TO_TIMESTAMP('2019-09-18 16:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56372
;

-- 2019-09-18T13:30:34.156Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=70,Updated=TO_TIMESTAMP('2019-09-18 16:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56370
;

-- 2019-09-18T13:30:46.087Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=80,Updated=TO_TIMESTAMP('2019-09-18 16:30:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56371
;

-- 2019-09-18T13:30:49.067Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=90,Updated=TO_TIMESTAMP('2019-09-18 16:30:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10449
;

-- 2019-09-18T13:31:05.584Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=100,Updated=TO_TIMESTAMP('2019-09-18 16:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10451
;

-- 2019-09-18T13:31:17.683Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=110,Updated=TO_TIMESTAMP('2019-09-18 16:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10446
;

-- 2019-09-18T13:31:25.578Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=130,Updated=TO_TIMESTAMP('2019-09-18 16:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10453
;

-- 2019-09-18T13:31:59.867Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=110,Updated=TO_TIMESTAMP('2019-09-18 16:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10449
;

-- 2019-09-18T13:32:08.907Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=90,Updated=TO_TIMESTAMP('2019-09-18 16:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10446
;

-- 2019-09-18T13:32:43.634Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=NULL,Updated=TO_TIMESTAMP('2019-09-18 16:32:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56369
;

-- 2019-09-18T13:32:53.114Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=NULL,Updated=TO_TIMESTAMP('2019-09-18 16:32:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10447
;

-- 2019-09-18T13:33:03.441Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=20,Updated=TO_TIMESTAMP('2019-09-18 16:33:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10447
;

-- 2019-09-18T13:33:29.017Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=10,Updated=TO_TIMESTAMP('2019-09-18 16:33:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56369
;

-- 2019-09-18T13:34:04.264Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=NULL,Updated=TO_TIMESTAMP('2019-09-18 16:34:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10448
;

-- 2019-09-18T13:34:15.932Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=120,Updated=TO_TIMESTAMP('2019-09-18 16:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10448
;

-- 2019-09-18T13:38:48.268Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541912,542895,TO_TIMESTAMP('2019-09-18 16:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','table',20,TO_TIMESTAMP('2019-09-18 16:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T13:38:58.131Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='org', SeqNo=30,Updated=TO_TIMESTAMP('2019-09-18 16:38:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542894
;

-- 2019-09-18T13:41:52.830Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542895, SeqNo=10,Updated=TO_TIMESTAMP('2019-09-18 16:41:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561836
;

-- 2019-09-18T13:41:58.023Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542895, SeqNo=20,Updated=TO_TIMESTAMP('2019-09-18 16:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561835
;

-- 2019-09-18T13:47:09.258Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2019-09-18 16:47:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561838
;

-- 2019-09-18T13:47:17.689Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2019-09-18 16:47:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561839
;

-- 2019-09-18T13:58:30.113Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=541911, SeqNo=20,Updated=TO_TIMESTAMP('2019-09-18 16:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542895
;

