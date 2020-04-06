-- 2017-05-23T15:19:02.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540840,0,256,TO_TIMESTAMP('2017-05-23 15:19:02','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals-Satz zum Produkt verwalten','U','_Merkmals-Satz','Y','N','N','N','N','Merkmals Satz',TO_TIMESTAMP('2017-05-23 15:19:02','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Satz')
;

-- 2017-05-23T15:19:02.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540840 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-05-23T15:19:02.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540840, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540840)
;

-- 2017-05-23T15:19:03.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:03.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:03.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:03.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:03.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:03.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:03.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:03.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:05.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:05.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:05.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:05.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:05.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:05.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:05.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:05.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2017-05-23T15:19:46.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,461,540212,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540296,540212,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540297,540212,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540296,540487,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6355,0,461,540487,544713,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6348,0,461,540487,544714,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6352,0,461,540487,544715,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6356,0,461,540487,544716,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',40,40,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6349,0,461,540487,544717,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',50,50,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8254,0,461,540487,544718,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)','If selected, the individual instance of the product has this attribute - like the individual Serial or Lot Numbers or  Guarantee Date of a product instance.  If not selected, all instances of the product share the attribute (e.g. color=green).','Y','N','Y','Y','N','Instanz-Attribut',60,60,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6357,0,461,540487,544719,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'The product instances have a Lot Number','For individual products, you can define Lot Numbers','Y','N','Y','Y','N','Los',70,70,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8375,0,461,540487,544720,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'The entry of Lot info is mandatory when creating a Product Instance','Y','N','Y','Y','N','Mandatory Lot',80,80,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6354,0,461,540487,544721,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Definition einer Los-Nummer','Einstellungen für die Erzeugung von Los-Nummern für Produkte','Y','N','Y','Y','N','Los-Definition',90,90,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12370,0,461,540487,544722,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Lot/Batch Start Indicator overwrite - default «','If not defined, the default character « is used','Y','N','Y','Y','N','Lot Char Start Overwrite',100,100,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12369,0,461,540487,544723,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Lot/Batch End Indicator overwrite - default »','If not defined, the default character » is used','Y','N','Y','Y','N','Lot Char End Overwrite',110,110,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6351,0,461,540487,544724,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'The product instances have Serial Numbers','For individual products, you can define Serial Numbers','Y','N','Y','Y','N','Serien-Nr.',120,120,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8376,0,461,540487,544725,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'The entry of a Serial No is mandatory when creating a Product Instance','Y','N','Y','Y','N','Mandatory Serial No',130,130,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6347,0,461,540487,544726,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Definition einer Serien-Nummer','Einstellungen für die Erzeugung von Serien-Nummern für Produkte','Y','N','Y','Y','N','Seriennummern-Definition',140,140,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12372,0,461,540487,544727,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Serial Number Start Indicator overwrite - default #','If not defined, the default character # is used','Y','N','Y','Y','N','SerNo Char Start Overwrite',150,150,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12371,0,461,540487,544728,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Serial Number End Indicator overwrite - default empty','If not defined, no character is used','Y','N','Y','Y','N','SerNo Char End Overwrite',160,160,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6350,0,461,540487,544729,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Produkt hat ein Garantie- oder Ablauf-Datum','Für individuelle Produkte können Sie ein Garantie- oder Ablauf-Datum definieren','Y','N','Y','Y','N','Garantie-Datum',170,170,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8377,0,461,540487,544730,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Die Angabe eines Garantie-Datums ist Pflicht, wenn eine Produkt-Instanz erzeugt wird','Y','N','Y','Y','N','Garantie-Datum ist Pflicht',180,180,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10419,0,461,540487,544731,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'The specification of a Product Attribute Instance is mandatory','Y','N','Y','Y','N','Mandatory Type',190,190,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,467,540213,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540298,540213,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540298,540488,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6409,0,467,540488,544732,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6410,0,467,540488,544733,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6411,0,467,540488,544734,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals-Satz zum Produkt','Definieren Sie Merkmals-Sätze um einem Produkt zusätzliche Merkmale und Eigenschaften zuzuordnen. Sie müssen einen Merkmals-Satz anlegen, um Serien- und Los-Nummern verfolgen zu können.','Y','N','N','Y','N','Merkmals-Satz',0,30,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6408,0,467,540488,544735,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Merkmal','Product Attribute like Color, Size','Y','N','N','Y','N','Merkmal',0,40,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6407,0,467,540488,544736,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:46.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6428,0,467,540488,544737,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','Reihenfolge',0,60,0,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:47.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,752,540214,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-23 15:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:47.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540299,540214,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:47.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540299,540489,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:47.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12201,0,752,540489,544738,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:47.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12203,0,752,540489,544739,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:47.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12200,0,752,540489,544740,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals-Satz zum Produkt','Definieren Sie Merkmals-Sätze um einem Produkt zusätzliche Merkmale und Eigenschaften zuzuordnen. Sie müssen einen Merkmals-Satz anlegen, um Serien- und Los-Nummern verfolgen zu können.','Y','N','N','Y','N','Merkmals-Satz',0,30,0,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:47.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12199,0,752,540489,544741,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:47.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12205,0,752,540489,544742,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','DB-Tabelle',0,50,0,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:19:47.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12204,0,752,540489,544743,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist eine Verkaufs-Transaktion','Das Selektionsfeld "Verkaufs-Transaktion" zeigt an, dass es sich um eine Verkaufs-Transaktion handelt.','Y','N','N','Y','N','Verkaufs-Transaktion',0,60,0,TO_TIMESTAMP('2017-05-23 15:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:29:25.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540296,540490,TO_TIMESTAMP('2017-05-23 15:29:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-23 15:29:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:29:36.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', UIStyle='',Updated=TO_TIMESTAMP('2017-05-23 15:29:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540487
;

-- 2017-05-23T15:29:42.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=99,Updated=TO_TIMESTAMP('2017-05-23 15:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540487
;

-- 2017-05-23T15:34:35.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6352,0,461,540490,544744,TO_TIMESTAMP('2017-05-23 15:34:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-05-23 15:34:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:35:30.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10419,0,461,540490,544745,TO_TIMESTAMP('2017-05-23 15:35:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Pflichtfeld Art',20,0,0,TO_TIMESTAMP('2017-05-23 15:35:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:35:42.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540296,540491,TO_TIMESTAMP('2017-05-23 15:35:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-05-23 15:35:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:35:59.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6356,0,461,540491,544746,TO_TIMESTAMP('2017-05-23 15:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-05-23 15:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:36:17.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6351,0,461,540491,544747,TO_TIMESTAMP('2017-05-23 15:36:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Serien Nr.',20,0,0,TO_TIMESTAMP('2017-05-23 15:36:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:36:46.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6347,0,461,540491,544748,TO_TIMESTAMP('2017-05-23 15:36:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Serien Nr. Definition',30,0,0,TO_TIMESTAMP('2017-05-23 15:36:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:37:00.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12372,0,461,540491,544749,TO_TIMESTAMP('2017-05-23 15:37:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Serien Nr. Start',40,0,0,TO_TIMESTAMP('2017-05-23 15:37:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:37:18.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12371,0,461,540491,544750,TO_TIMESTAMP('2017-05-23 15:37:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Serien Nr. Ende',50,0,0,TO_TIMESTAMP('2017-05-23 15:37:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:38:20.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540297,540492,TO_TIMESTAMP('2017-05-23 15:38:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-05-23 15:38:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:38:29.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6349,0,461,540492,544751,TO_TIMESTAMP('2017-05-23 15:38:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-05-23 15:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:38:54.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8254,0,461,540492,544752,TO_TIMESTAMP('2017-05-23 15:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Instanz Merkmal',20,0,0,TO_TIMESTAMP('2017-05-23 15:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:39:07.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540297,540493,TO_TIMESTAMP('2017-05-23 15:39:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-05-23 15:39:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:39:15.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6348,0,461,540493,544753,TO_TIMESTAMP('2017-05-23 15:39:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-05-23 15:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:39:23.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6355,0,461,540493,544754,TO_TIMESTAMP('2017-05-23 15:39:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-05-23 15:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:40:18.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544746
;

-- 2017-05-23T15:40:21.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-05-23 15:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544747
;

-- 2017-05-23T15:40:23.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-05-23 15:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544748
;

-- 2017-05-23T15:40:26.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-05-23 15:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544749
;

-- 2017-05-23T15:40:29.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-05-23 15:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544750
;

-- 2017-05-23T15:40:41.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540296,540494,TO_TIMESTAMP('2017-05-23 15:40:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-05-23 15:40:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:40:50.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='serNo', SeqNo=30,Updated=TO_TIMESTAMP('2017-05-23 15:40:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540491
;

-- 2017-05-23T15:41:07.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6356,0,461,540494,544755,TO_TIMESTAMP('2017-05-23 15:41:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-05-23 15:41:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:41:20.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540297,540495,TO_TIMESTAMP('2017-05-23 15:41:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','lot',30,TO_TIMESTAMP('2017-05-23 15:41:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:41:48.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6357,0,461,540495,544756,TO_TIMESTAMP('2017-05-23 15:41:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lot Nr.',10,0,0,TO_TIMESTAMP('2017-05-23 15:41:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:42:08.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6354,0,461,540495,544757,TO_TIMESTAMP('2017-05-23 15:42:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lot Nr. Definition',20,0,0,TO_TIMESTAMP('2017-05-23 15:42:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:42:28.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12370,0,461,540495,544758,TO_TIMESTAMP('2017-05-23 15:42:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lot Nr. Start',30,0,0,TO_TIMESTAMP('2017-05-23 15:42:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:42:39.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12369,0,461,540495,544759,TO_TIMESTAMP('2017-05-23 15:42:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lot Nr. Ende',40,0,0,TO_TIMESTAMP('2017-05-23 15:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:43:45.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540296,540496,TO_TIMESTAMP('2017-05-23 15:43:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','guarantee',40,TO_TIMESTAMP('2017-05-23 15:43:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:43:58.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6350,0,461,540496,544760,TO_TIMESTAMP('2017-05-23 15:43:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Garantie Datum',10,0,0,TO_TIMESTAMP('2017-05-23 15:43:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:44:24.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8377,0,461,540496,544761,TO_TIMESTAMP('2017-05-23 15:44:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Garantie Datum Pflicht',20,0,0,TO_TIMESTAMP('2017-05-23 15:44:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:45:03.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544760
;

-- 2017-05-23T15:45:06.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544761
;

-- 2017-05-23T15:45:10.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540496
;

-- 2017-05-23T15:45:20.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,461,540215,TO_TIMESTAMP('2017-05-23 15:45:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',20,TO_TIMESTAMP('2017-05-23 15:45:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:45:23.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540300,540215,TO_TIMESTAMP('2017-05-23 15:45:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-23 15:45:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:45:37.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540300,540497,TO_TIMESTAMP('2017-05-23 15:45:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','guarantee',10,TO_TIMESTAMP('2017-05-23 15:45:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:46:34.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6350,0,461,540497,544762,TO_TIMESTAMP('2017-05-23 15:46:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Garantie Datum',10,0,0,TO_TIMESTAMP('2017-05-23 15:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:46:49.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8377,0,461,540497,544763,TO_TIMESTAMP('2017-05-23 15:46:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Garantie Datum Pflicht',20,0,0,TO_TIMESTAMP('2017-05-23 15:46:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T15:46:50.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-23 15:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544762
;

-- 2017-05-23T15:46:52.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-23 15:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544763
;

-- 2017-05-23T15:47:08.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544731
;

-- 2017-05-23T15:47:08.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544730
;

-- 2017-05-23T15:47:08.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544729
;

-- 2017-05-23T15:47:08.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544728
;

-- 2017-05-23T15:47:08.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544727
;

-- 2017-05-23T15:47:08.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544726
;

-- 2017-05-23T15:47:09.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544725
;

-- 2017-05-23T15:47:09.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544724
;

-- 2017-05-23T15:47:17.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544723
;

-- 2017-05-23T15:47:17.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544722
;

-- 2017-05-23T15:47:17.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544721
;

-- 2017-05-23T15:47:17.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544720
;

-- 2017-05-23T15:47:17.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544719
;

-- 2017-05-23T15:47:17.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544718
;

-- 2017-05-23T15:47:17.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544717
;

-- 2017-05-23T15:47:17.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544716
;

-- 2017-05-23T15:47:22.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544715
;

-- 2017-05-23T15:47:22.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544714
;

-- 2017-05-23T15:47:22.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544713
;

-- 2017-05-23T15:47:26.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540487
;

-- 2017-05-23T15:57:09.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544744
;

-- 2017-05-23T15:57:09.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544745
;

-- 2017-05-23T15:57:09.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544755
;

-- 2017-05-23T15:57:09.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544751
;

-- 2017-05-23T15:57:09.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544752
;

-- 2017-05-23T15:57:09.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544762
;

-- 2017-05-23T15:57:09.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544756
;

-- 2017-05-23T15:57:09.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544747
;

-- 2017-05-23T15:57:09.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544753
;

-- 2017-05-23T15:57:09.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-23 15:57:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544754
;

-- 2017-05-23T15:57:25.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-05-23 15:57:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544744
;

-- 2017-05-23T15:57:25.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-05-23 15:57:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544745
;

-- 2017-05-23T15:57:25.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-05-23 15:57:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544755
;

-- 2017-05-23T15:57:25.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-05-23 15:57:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544751
;

-- 2017-05-23T16:01:44.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Pflichtfeld Art', PrintName='Pflichtfeld Art',Updated=TO_TIMESTAMP('2017-05-23 16:01:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2537
;

-- 2017-05-23T16:01:44.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2537
;

-- 2017-05-23T16:01:44.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MandatoryType', Name='Pflichtfeld Art', Description='The specification of a Product Attribute Instance is mandatory', Help=NULL WHERE AD_Element_ID=2537
;

-- 2017-05-23T16:01:44.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MandatoryType', Name='Pflichtfeld Art', Description='The specification of a Product Attribute Instance is mandatory', Help=NULL, AD_Element_ID=2537 WHERE UPPER(ColumnName)='MANDATORYTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:01:44.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MandatoryType', Name='Pflichtfeld Art', Description='The specification of a Product Attribute Instance is mandatory', Help=NULL WHERE AD_Element_ID=2537 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:01:44.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pflichtfeld Art', Description='The specification of a Product Attribute Instance is mandatory', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2537) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:01:44.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Pflichtfeld Art', Name='Pflichtfeld Art' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2537)
;

-- 2017-05-23T16:02:53.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Instanz Merkmal', PrintName='Instanz Merkmal',Updated=TO_TIMESTAMP('2017-05-23 16:02:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2012
;

-- 2017-05-23T16:02:53.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2012
;

-- 2017-05-23T16:02:53.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInstanceAttribute', Name='Instanz Merkmal', Description='The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)', Help='If selected, the individual instance of the product has this attribute - like the individual Serial or Lot Numbers or  Guarantee Date of a product instance.  If not selected, all instances of the product share the attribute (e.g. color=green).' WHERE AD_Element_ID=2012
;

-- 2017-05-23T16:02:53.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInstanceAttribute', Name='Instanz Merkmal', Description='The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)', Help='If selected, the individual instance of the product has this attribute - like the individual Serial or Lot Numbers or  Guarantee Date of a product instance.  If not selected, all instances of the product share the attribute (e.g. color=green).', AD_Element_ID=2012 WHERE UPPER(ColumnName)='ISINSTANCEATTRIBUTE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:02:53.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInstanceAttribute', Name='Instanz Merkmal', Description='The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)', Help='If selected, the individual instance of the product has this attribute - like the individual Serial or Lot Numbers or  Guarantee Date of a product instance.  If not selected, all instances of the product share the attribute (e.g. color=green).' WHERE AD_Element_ID=2012 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:02:53.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Instanz Merkmal', Description='The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)', Help='If selected, the individual instance of the product has this attribute - like the individual Serial or Lot Numbers or  Guarantee Date of a product instance.  If not selected, all instances of the product share the attribute (e.g. color=green).' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2012) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:02:53.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Instanz Merkmal', Name='Instanz Merkmal' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2012)
;

-- 2017-05-23T16:03:37.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Serien Nr.', PrintName='Serien Nr.',Updated=TO_TIMESTAMP('2017-05-23 16:03:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2014
;

-- 2017-05-23T16:03:37.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2014
;

-- 2017-05-23T16:03:37.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSerNo', Name='Serien Nr.', Description='The product instances have Serial Numbers', Help='For individual products, you can define Serial Numbers' WHERE AD_Element_ID=2014
;

-- 2017-05-23T16:03:37.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSerNo', Name='Serien Nr.', Description='The product instances have Serial Numbers', Help='For individual products, you can define Serial Numbers', AD_Element_ID=2014 WHERE UPPER(ColumnName)='ISSERNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:03:37.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSerNo', Name='Serien Nr.', Description='The product instances have Serial Numbers', Help='For individual products, you can define Serial Numbers' WHERE AD_Element_ID=2014 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:03:37.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serien Nr.', Description='The product instances have Serial Numbers', Help='For individual products, you can define Serial Numbers' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2014) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:03:37.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Serien Nr.', Name='Serien Nr.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2014)
;

-- 2017-05-23T16:04:37.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Serien Nr. Definition', PrintName='Serien Nr. Definition',Updated=TO_TIMESTAMP('2017-05-23 16:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2023
;

-- 2017-05-23T16:04:37.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2023
;

-- 2017-05-23T16:04:37.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_SerNoCtl_ID', Name='Serien Nr. Definition', Description='Definition einer Serien-Nummer', Help='Einstellungen für die Erzeugung von Serien-Nummern für Produkte' WHERE AD_Element_ID=2023
;

-- 2017-05-23T16:04:37.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_SerNoCtl_ID', Name='Serien Nr. Definition', Description='Definition einer Serien-Nummer', Help='Einstellungen für die Erzeugung von Serien-Nummern für Produkte', AD_Element_ID=2023 WHERE UPPER(ColumnName)='M_SERNOCTL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:04:37.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_SerNoCtl_ID', Name='Serien Nr. Definition', Description='Definition einer Serien-Nummer', Help='Einstellungen für die Erzeugung von Serien-Nummern für Produkte' WHERE AD_Element_ID=2023 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:04:37.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serien Nr. Definition', Description='Definition einer Serien-Nummer', Help='Einstellungen für die Erzeugung von Serien-Nummern für Produkte' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2023) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:04:37.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Serien Nr. Definition', Name='Serien Nr. Definition' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2023)
;

-- 2017-05-23T16:05:29.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serien Nr. Start',Updated=TO_TIMESTAMP('2017-05-23 16:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12372
;

-- 2017-05-23T16:05:29.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=12372
;

-- 2017-05-23T16:05:43.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Serien Nr. Start', PrintName='Serien Nr. Start',Updated=TO_TIMESTAMP('2017-05-23 16:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2856
;

-- 2017-05-23T16:05:43.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2856
;

-- 2017-05-23T16:05:43.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SerNoCharSOverwrite', Name='Serien Nr. Start', Description='Serial Number Start Indicator overwrite - default #', Help='If not defined, the default character # is used' WHERE AD_Element_ID=2856
;

-- 2017-05-23T16:05:43.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SerNoCharSOverwrite', Name='Serien Nr. Start', Description='Serial Number Start Indicator overwrite - default #', Help='If not defined, the default character # is used', AD_Element_ID=2856 WHERE UPPER(ColumnName)='SERNOCHARSOVERWRITE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:05:43.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SerNoCharSOverwrite', Name='Serien Nr. Start', Description='Serial Number Start Indicator overwrite - default #', Help='If not defined, the default character # is used' WHERE AD_Element_ID=2856 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:05:43.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serien Nr. Start', Description='Serial Number Start Indicator overwrite - default #', Help='If not defined, the default character # is used' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2856) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:05:43.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Serien Nr. Start', Name='Serien Nr. Start' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2856)
;

-- 2017-05-23T16:06:27.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Serien Nr. Ende', PrintName='Serien Nr. Ende',Updated=TO_TIMESTAMP('2017-05-23 16:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2857
;

-- 2017-05-23T16:06:27.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2857
;

-- 2017-05-23T16:06:27.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SerNoCharEOverwrite', Name='Serien Nr. Ende', Description='Serial Number End Indicator overwrite - default empty', Help='If not defined, no character is used' WHERE AD_Element_ID=2857
;

-- 2017-05-23T16:06:27.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SerNoCharEOverwrite', Name='Serien Nr. Ende', Description='Serial Number End Indicator overwrite - default empty', Help='If not defined, no character is used', AD_Element_ID=2857 WHERE UPPER(ColumnName)='SERNOCHAREOVERWRITE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:06:27.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SerNoCharEOverwrite', Name='Serien Nr. Ende', Description='Serial Number End Indicator overwrite - default empty', Help='If not defined, no character is used' WHERE AD_Element_ID=2857 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:06:27.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serien Nr. Ende', Description='Serial Number End Indicator overwrite - default empty', Help='If not defined, no character is used' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2857) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:06:27.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Serien Nr. Ende', Name='Serien Nr. Ende' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2857)
;

-- 2017-05-23T16:08:40.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Lot Nr.', PrintName='Lot Nr.',Updated=TO_TIMESTAMP('2017-05-23 16:08:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2013
;

-- 2017-05-23T16:08:40.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2013
;

-- 2017-05-23T16:08:40.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsLot', Name='Lot Nr.', Description='The product instances have a Lot Number', Help='For individual products, you can define Lot Numbers' WHERE AD_Element_ID=2013
;

-- 2017-05-23T16:08:40.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLot', Name='Lot Nr.', Description='The product instances have a Lot Number', Help='For individual products, you can define Lot Numbers', AD_Element_ID=2013 WHERE UPPER(ColumnName)='ISLOT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:08:40.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLot', Name='Lot Nr.', Description='The product instances have a Lot Number', Help='For individual products, you can define Lot Numbers' WHERE AD_Element_ID=2013 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:08:40.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lot Nr.', Description='The product instances have a Lot Number', Help='For individual products, you can define Lot Numbers' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2013) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:08:40.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lot Nr.', Name='Lot Nr.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2013)
;

-- 2017-05-23T16:09:20.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Lot Nr. Definition', PrintName='Lot Nr. Definition',Updated=TO_TIMESTAMP('2017-05-23 16:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2022
;

-- 2017-05-23T16:09:20.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2022
;

-- 2017-05-23T16:09:20.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_LotCtl_ID', Name='Lot Nr. Definition', Description='Definition einer Los-Nummer', Help='Einstellungen für die Erzeugung von Los-Nummern für Produkte' WHERE AD_Element_ID=2022
;

-- 2017-05-23T16:09:20.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_LotCtl_ID', Name='Lot Nr. Definition', Description='Definition einer Los-Nummer', Help='Einstellungen für die Erzeugung von Los-Nummern für Produkte', AD_Element_ID=2022 WHERE UPPER(ColumnName)='M_LOTCTL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:09:20.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_LotCtl_ID', Name='Lot Nr. Definition', Description='Definition einer Los-Nummer', Help='Einstellungen für die Erzeugung von Los-Nummern für Produkte' WHERE AD_Element_ID=2022 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:09:20.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lot Nr. Definition', Description='Definition einer Los-Nummer', Help='Einstellungen für die Erzeugung von Los-Nummern für Produkte' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2022) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:09:20.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lot Nr. Definition', Name='Lot Nr. Definition' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2022)
;

-- 2017-05-23T16:09:50.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Lot Nr. Start', PrintName='Lot Nr. Start',Updated=TO_TIMESTAMP('2017-05-23 16:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2858
;

-- 2017-05-23T16:09:50.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2858
;

-- 2017-05-23T16:09:50.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LotCharSOverwrite', Name='Lot Nr. Start', Description='Lot/Batch Start Indicator overwrite - default «', Help='If not defined, the default character « is used' WHERE AD_Element_ID=2858
;

-- 2017-05-23T16:09:50.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LotCharSOverwrite', Name='Lot Nr. Start', Description='Lot/Batch Start Indicator overwrite - default «', Help='If not defined, the default character « is used', AD_Element_ID=2858 WHERE UPPER(ColumnName)='LOTCHARSOVERWRITE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:09:50.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LotCharSOverwrite', Name='Lot Nr. Start', Description='Lot/Batch Start Indicator overwrite - default «', Help='If not defined, the default character « is used' WHERE AD_Element_ID=2858 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:09:50.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lot Nr. Start', Description='Lot/Batch Start Indicator overwrite - default «', Help='If not defined, the default character « is used' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2858) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:09:50.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lot Nr. Start', Name='Lot Nr. Start' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2858)
;

-- 2017-05-23T16:10:28.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Lot Nr. Ende', PrintName='Lot Nr. Ende',Updated=TO_TIMESTAMP('2017-05-23 16:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2859
;

-- 2017-05-23T16:10:28.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2859
;

-- 2017-05-23T16:10:28.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LotCharEOverwrite', Name='Lot Nr. Ende', Description='Lot/Batch End Indicator overwrite - default »', Help='If not defined, the default character » is used' WHERE AD_Element_ID=2859
;

-- 2017-05-23T16:10:28.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LotCharEOverwrite', Name='Lot Nr. Ende', Description='Lot/Batch End Indicator overwrite - default »', Help='If not defined, the default character » is used', AD_Element_ID=2859 WHERE UPPER(ColumnName)='LOTCHAREOVERWRITE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:10:28.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LotCharEOverwrite', Name='Lot Nr. Ende', Description='Lot/Batch End Indicator overwrite - default »', Help='If not defined, the default character » is used' WHERE AD_Element_ID=2859 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:10:28.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lot Nr. Ende', Description='Lot/Batch End Indicator overwrite - default »', Help='If not defined, the default character » is used' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2859) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:10:28.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lot Nr. Ende', Name='Lot Nr. Ende' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2859)
;

-- 2017-05-23T16:11:59.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serien Nr. Pflicht',Updated=TO_TIMESTAMP('2017-05-23 16:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8376
;

-- 2017-05-23T16:11:59.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=8376
;

-- 2017-05-23T16:12:11.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Serien Nr. Pflicht', PrintName='Serien Nr. Pflicht',Updated=TO_TIMESTAMP('2017-05-23 16:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2263
;

-- 2017-05-23T16:12:11.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2263
;

-- 2017-05-23T16:12:11.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSerNoMandatory', Name='Serien Nr. Pflicht', Description='The entry of a Serial No is mandatory when creating a Product Instance', Help=NULL WHERE AD_Element_ID=2263
;

-- 2017-05-23T16:12:11.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSerNoMandatory', Name='Serien Nr. Pflicht', Description='The entry of a Serial No is mandatory when creating a Product Instance', Help=NULL, AD_Element_ID=2263 WHERE UPPER(ColumnName)='ISSERNOMANDATORY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:12:11.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSerNoMandatory', Name='Serien Nr. Pflicht', Description='The entry of a Serial No is mandatory when creating a Product Instance', Help=NULL WHERE AD_Element_ID=2263 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:12:11.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Serien Nr. Pflicht', Description='The entry of a Serial No is mandatory when creating a Product Instance', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2263) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:12:11.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Serien Nr. Pflicht', Name='Serien Nr. Pflicht' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2263)
;

-- 2017-05-23T16:12:46.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Lot Nr. Pflicht', PrintName='Lot Nr. Pflicht',Updated=TO_TIMESTAMP('2017-05-23 16:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2262
;

-- 2017-05-23T16:12:46.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2262
;

-- 2017-05-23T16:12:46.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsLotMandatory', Name='Lot Nr. Pflicht', Description='The entry of Lot info is mandatory when creating a Product Instance', Help=NULL WHERE AD_Element_ID=2262
;

-- 2017-05-23T16:12:46.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLotMandatory', Name='Lot Nr. Pflicht', Description='The entry of Lot info is mandatory when creating a Product Instance', Help=NULL, AD_Element_ID=2262 WHERE UPPER(ColumnName)='ISLOTMANDATORY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-23T16:12:46.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLotMandatory', Name='Lot Nr. Pflicht', Description='The entry of Lot info is mandatory when creating a Product Instance', Help=NULL WHERE AD_Element_ID=2262 AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:12:46.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lot Nr. Pflicht', Description='The entry of Lot info is mandatory when creating a Product Instance', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2262) AND IsCentrallyMaintained='Y'
;

-- 2017-05-23T16:12:46.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lot Nr. Pflicht', Name='Lot Nr. Pflicht' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2262)
;

-- 2017-05-23T16:14:21.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8376,0,461,540491,544764,TO_TIMESTAMP('2017-05-23 16:14:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Serien Nr. Pflicht',50,0,0,TO_TIMESTAMP('2017-05-23 16:14:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T16:14:37.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8375,0,461,540495,544765,TO_TIMESTAMP('2017-05-23 16:14:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lot Nr. Pflicht',50,0,0,TO_TIMESTAMP('2017-05-23 16:14:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-23T16:26:51.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-05-23 16:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544734
;

-- 2017-05-23T16:26:51.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-05-23 16:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544737
;

-- 2017-05-23T16:26:51.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-05-23 16:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544735
;

-- 2017-05-23T16:26:51.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-23 16:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544736
;

-- 2017-05-23T16:26:51.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-23 16:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544733
;

-- 2017-05-23T16:26:51.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-23 16:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544732
;

-- 2017-05-23T16:27:22.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-05-23 16:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544740
;

-- 2017-05-23T16:27:22.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-05-23 16:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544742
;

-- 2017-05-23T16:27:22.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-05-23 16:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544743
;

-- 2017-05-23T16:27:22.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-23 16:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544741
;

-- 2017-05-23T16:27:22.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-23 16:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544739
;

-- 2017-05-23T16:27:22.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-23 16:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544738
;

