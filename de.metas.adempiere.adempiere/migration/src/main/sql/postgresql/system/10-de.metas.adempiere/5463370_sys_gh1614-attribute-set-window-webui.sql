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

