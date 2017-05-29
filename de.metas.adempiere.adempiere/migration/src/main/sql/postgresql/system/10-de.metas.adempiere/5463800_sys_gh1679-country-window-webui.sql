-- 2017-05-29T16:28:24.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540845,0,122,TO_TIMESTAMP('2017-05-29 16:28:24','YYYY-MM-DD HH24:MI:SS'),100,'Länder, Regionen und Städte verwalten','de.metas.materialtracking.ch.lagerkonf','_Land_Region_Stadt','Y','N','N','N','N','Land, Region, Stadt',TO_TIMESTAMP('2017-05-29 16:28:24','YYYY-MM-DD HH24:MI:SS'),100,'Land, Region, Stadt')
;

-- 2017-05-29T16:28:24.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540845 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-05-29T16:28:24.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540845, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540845)
;

-- 2017-05-29T16:28:25.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540842 AND AD_Tree_ID=10
;

-- 2017-05-29T16:28:25.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540843 AND AD_Tree_ID=10
;

-- 2017-05-29T16:28:25.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540810 AND AD_Tree_ID=10
;

-- 2017-05-29T16:28:25.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540812 AND AD_Tree_ID=10
;

-- 2017-05-29T16:28:25.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540813 AND AD_Tree_ID=10
;

-- 2017-05-29T16:28:25.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540780 AND AD_Tree_ID=10
;

-- 2017-05-29T16:28:25.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540845 AND AD_Tree_ID=10
;

-- 2017-05-29T16:28:32.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540845 AND AD_Tree_ID=10
;

-- 2017-05-29T16:33:17.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,135,540237,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-05-29T16:33:17.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540237 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-05-29T16:33:17.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540324,540237,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:17.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540325,540237,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:17.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540324,540552,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:17.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,337,0,135,540552,545104,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:17.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2017,0,135,540552,545105,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:17.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,624,0,135,540552,545106,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html','Für Details - http://www.din.de/gremien/nas/nabd/iso3166ma/codlstp1.html oder - http://www.unece.org/trade/rec/rec03en.htm','Y','N','Y','Y','N','ISO Ländercode',30,30,0,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:17.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551796,0,135,540552,545107,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','ISO Ländercode 3 Stelliger',40,40,0,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,339,0,135,540552,545108,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',50,50,0,TO_TIMESTAMP('2017-05-29 16:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,340,0,135,540552,545109,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',60,60,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,341,0,135,540552,545110,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',70,70,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,342,0,135,540552,545111,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Land mit Regionen','The Country has Region checkbox is selected if the Country being defined is divided into regions.  If this checkbox is selected, the Region Tab is accessible.','Y','N','Y','Y','N','Land hat Regionen',80,80,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,343,0,135,540552,545112,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Name der Region','"Region" definiert die Bezeichnung, die bei der Verwendung in einem Dokument gedruckt wird.','Y','N','Y','Y','N','Region',90,90,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,345,0,135,540552,545113,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Format for printing this Address','The Address Print format defines the format to be used when this address prints.  The following notations are used: @C@=City  @P@=Postal  @A@=PostalAdd  @R@=Region','Y','N','Y','Y','N','Adress-Druckformat',100,100,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10893,0,135,540552,545114,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Print Address in reverse Order','If NOT selected the sequence is Address 1, Address 2, Address 3, Address 4, City/Region/Postal, Country.
If selected the sequence is Country, City/Region/Postal, Address 4, Address 3, Address 2, Address 1.
The sequence of City/Region/Postal is determined by the address format.','Y','N','Y','Y','N','Reverse Address Lines',110,110,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57036,0,135,540552,545115,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'The Capture Sequence defines the fields to be used when capturing an address on this country.  The following notations are used: @CO@=Country, @C@=City, @P@=Postal, @A@=PostalAdd, @R@=Region, @A1@=Address 1 to @A4@=Address 4.  Country is always mandatory, add a bang ! to make another field mandatory, for example @C!@ makes city mandatory, @A1!@ makes Address 1 mandatory.','Y','N','Y','Y','N','Capture Sequence',120,120,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10892,0,135,540552,545116,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Format for printing this Address locally','The optional Local Address Print format defines the format to be used when this address prints for the Country.  If defined, this format is used for printing the address for the country rather then the standard address format.
 The following notations are used: @C@=City  @P@=Postal  @A@=PostalAdd  @R@=Region','Y','N','Y','Y','N','Local Address Format',130,130,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10891,0,135,540552,545117,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Print Local Address in reverse Order','If NOT selected the local sequence is Address 1, Address 2, Address 3, Address 4, City/Region/Postal, Country.
If selected the local sequence is Country, City/Region/Postal, Address 4, Address 3, Address 2, Address 1.
The sequence of City/Region/Postal is determined by the local address format.
','Y','N','Y','Y','N','Reverse Local Address Lines',140,140,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,346,0,135,540552,545118,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Format of the postal code; Can contain fixed format elements, Variables: "_lLoOaAcCa09"','<B>Validation elements:</B>
 	(Space) any character
_	Space (fixed character)
l	any Letter a..Z NO space
L	any Letter a..Z NO space converted to upper case
o	any Letter a..Z or space
O	any Letter a..Z or space converted to upper case
a	any Letters & Digits NO space
A	any Letters & Digits NO space converted to upper case
c	any Letters & Digits or space
C	any Letters & Digits or space converted to upper case
0	Digits 0..9 NO space
9	Digits 0..9 or space

Example of format "(000)_000-0000"','Y','N','Y','Y','N','Postal Code Format',150,150,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,347,0,135,540552,545119,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Has Additional Postal Code','The Additional Postal Code checkbox indicates if this address uses an additional Postal Code.  If it is selected an additional field displays for entry of the additional Postal Code.','Y','N','Y','Y','N','Additional Postal code',160,160,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,348,0,135,540552,545120,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"','<B>Validation elements:</B>
 	(Space) any character
_	Space (fixed character)
l	any Letter a..Z NO space
L	any Letter a..Z NO space converted to upper case
o	any Letter a..Z or space
O	any Letter a..Z or space converted to upper case
a	any Letters & Digits NO space
A	any Letters & Digits NO space converted to upper case
c	any Letters & Digits or space
C	any Letters & Digits or space converted to upper case
0	Digits 0..9 NO space
9	Digits 0..9 or space

Example of format "(000)_000-0000"','Y','N','Y','Y','N','Additional Postal Format',170,170,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,344,0,135,540552,545121,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Format of the phone; Can contain fixed format elements, Variables: "_lLoOaAcCa09"','<B>Validation elements:</B>
 	(Space) any character
_	Space (fixed character)
l	any Letter a..Z NO space
L	any Letter a..Z NO space converted to upper case
o	any Letter a..Z or space
O	any Letter a..Z or space converted to upper case
a	any Letters & Digits NO space
A	any Letters & Digits NO space converted to upper case
c	any Letters & Digits or space
C	any Letters & Digits or space converted to upper case
0	Digits 0..9 NO space
9	Digits 0..9 or space

Example of format "(000)_000-0000"','Y','N','Y','Y','N','Phone Format',180,180,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11184,0,135,540552,545122,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Java Media Size','The Java Media Size. Example: "MediaSize.ISO.A4" (the package javax.print.attribute.standard is assumed). If you define your own media size, use the fully qualified name.
If the pattern for your language is not correct, please create a Adempiere support request with the correct information','Y','N','Y','Y','N','Media Size',190,190,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10895,0,135,540552,545123,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Format of the Bank Routing Number','Y','N','Y','Y','N','Bank Routing No Format',200,200,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10894,0,135,540552,545124,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Format of the Bank Account','Y','N','Y','Y','N','Bank Account No Format',210,210,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5753,0,135,540552,545125,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','Y','Y','N','Sprache',220,220,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5752,0,135,540552,545126,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','Y','N','Währung',230,230,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51000,0,135,540552,545127,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Does this country have a post code web service','Enable the IsPostcodeLookup if you wish to configure a post code lookup web service','Y','N','Y','Y','N','Use Postcode Lookup',240,240,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57035,0,135,540552,545128,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'A flag to allow cities, currently not in the list, to be entered','Y','N','Y','Y','N','Allow Cities out of List',250,250,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51003,0,135,540552,545129,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'The URL of the web service that the plugin connects to in order to retrieve postcode data','Enter the URL of the web service that the plugin connects to in order to retrieve postcode data','Y','N','Y','Y','N','Lookup URL',260,260,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51002,0,135,540552,545130,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'The ClientID or Login submitted to the Lookup URL','Enter the ClientID or Login for your account provided by the post code web service provider','Y','N','Y','Y','N','Lookup Client ID',270,270,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51004,0,135,540552,545131,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'The password submitted to the Lookup URL','Enter the password for your account provided by the post code web service provider','Y','N','Y','Y','N','Lookup Password',280,280,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51001,0,135,540552,545132,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'The class name of the postcode lookup plugin','Enter the class name of the post code lookup plugin for your postcode web service provider','Y','N','Y','Y','N','Lookup ClassName',290,290,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555682,0,135,540552,545133,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bank Code Length',300,300,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555683,0,135,540552,545134,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bank Code Char Type',310,310,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555718,0,135,540552,545135,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bank Code SeqNo',320,320,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555684,0,135,540552,545136,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Branch Code Length',330,330,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555685,0,135,540552,545137,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Branch Code Char Type',340,340,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555719,0,135,540552,545138,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Branch Code SeqNo',350,350,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555686,0,135,540552,545139,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Account Number Length',360,360,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555687,0,135,540552,545140,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Account Number CharType',370,370,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555720,0,135,540552,545141,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Account Number SeqNo',380,380,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:18.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555688,0,135,540552,545142,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Account Type Length',390,390,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555690,0,135,540552,545143,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Account Type Char Type',400,400,0,TO_TIMESTAMP('2017-05-29 16:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555721,0,135,540552,545144,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Account Type SeqNo',410,410,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555691,0,135,540552,545145,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','National Check Digit Length',420,420,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555692,0,135,540552,545146,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','National Check Digit Char Type',430,430,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555722,0,135,540552,545147,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','National Check Digit SeqNo',440,440,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555716,0,135,540552,545148,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Owner Account Number Length',450,450,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555717,0,135,540552,545149,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Owner Account Number Char Type',460,460,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555723,0,135,540552,545150,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Owner Account Number SeqNo',470,470,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549,540238,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-05-29T16:33:19.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540238 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-05-29T16:33:19.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540326,540238,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540326,540553,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7846,0,549,540553,545151,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7840,0,549,540553,545152,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7845,0,549,540553,545153,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','Land',0,30,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7844,0,549,540553,545154,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','Y','N','Sprache',0,40,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7838,0,549,540553,545155,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7843,0,549,540553,545156,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Diese Spalte ist übersetzt','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist','Y','N','N','Y','N','Übersetzt',0,60,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7842,0,549,540553,545157,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,70,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7841,0,549,540553,545158,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,80,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7839,0,549,540553,545159,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Name der Region','"Region" definiert die Bezeichnung, die bei der Verwendung in einem Dokument gedruckt wird.','Y','N','N','Y','N','Region',0,90,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540792,540239,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-05-29T16:33:19.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540239 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-05-29T16:33:19.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540327,540239,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540327,540554,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557959,0,540792,540554,545160,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Format for printing this Address','The Address Print format defines the format to be used when this address prints.  The following notations are used: @C@=City  @P@=Postal  @A@=PostalAdd  @R@=Region','Y','N','N','Y','N','Adress-Druckformat',0,10,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557960,0,540792,540554,545161,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,20,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557961,0,540792,540554,545162,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,30,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557962,0,540792,540554,545163,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,40,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557963,0,540792,540554,545164,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Country Sequence',0,50,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557964,0,540792,540554,545165,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,60,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557965,0,540792,540554,545166,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,70,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557966,0,540792,540554,545167,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,80,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557967,0,540792,540554,545168,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,90,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557968,0,540792,540554,545169,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','N','Y','N','Sprache',0,100,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557973,0,540792,540554,545170,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Format for printing this Address locally','The optional Local Address Print format defines the format to be used when this address prints for the Country.  If defined, this format is used for printing the address for the country rather then the standard address format.
 The following notations are used: @C@=City  @P@=Postal  @A@=PostalAdd  @R@=Region','Y','N','N','Y','N','Local Address Format',0,110,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557974,0,540792,540554,545171,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','Land',0,120,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540291,540240,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-05-29T16:33:19.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540240 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-05-29T16:33:19.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540328,540240,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:19.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540328,540555,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547204,0,540291,540555,545172,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','Land',0,10,0,TO_TIMESTAMP('2017-05-29 16:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547206,0,540291,540555,545173,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547201,0,540291,540555,545174,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'The Capture Sequence defines the fields to be used when capturing an address on this country.  The following notations are used: @CO@=Country, @C@=City, @P@=Postal, @A@=PostalAdd, @R@=Region, @A1@=Address 1 to @A4@=Address 4.  Country is always mandatory, add a bang ! to make another field mandatory, for example @C!@ makes city mandatory, @A1!@ makes Address 1 mandatory.','Y','N','N','Y','N','Capture Sequence',0,30,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547198,0,540291,540555,545175,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,136,540241,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-05-29T16:33:20.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540241 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-05-29T16:33:20.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540329,540241,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540329,540556,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,350,0,136,540556,545176,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2018,0,136,540556,545177,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,354,0,136,540556,545178,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','Land',0,30,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,351,0,136,540556,545179,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,40,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,352,0,136,540556,545180,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,50,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,353,0,136,540556,545181,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,60,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:33:20.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3051,0,136,540556,545182,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','N','Y','N','Standard',0,70,0,TO_TIMESTAMP('2017-05-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:37:37.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540324,540557,TO_TIMESTAMP('2017-05-29 16:37:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-29 16:37:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:37:47.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', UIStyle='',Updated=TO_TIMESTAMP('2017-05-29 16:37:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540552
;

-- 2017-05-29T16:38:21.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=99,Updated=TO_TIMESTAMP('2017-05-29 16:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540552
;

-- 2017-05-29T16:46:49.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,339,0,135,540557,545183,TO_TIMESTAMP('2017-05-29 16:46:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-05-29 16:46:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:47:42.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,624,0,135,540557,545184,TO_TIMESTAMP('2017-05-29 16:47:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','ISO Ländercode',20,0,0,TO_TIMESTAMP('2017-05-29 16:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:47:56.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551796,0,135,540557,545185,TO_TIMESTAMP('2017-05-29 16:47:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','ISO Ländercode 3-stellig',30,0,0,TO_TIMESTAMP('2017-05-29 16:47:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:49:56.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,345,0,135,540557,545186,TO_TIMESTAMP('2017-05-29 16:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Adress Druckformat',40,0,0,TO_TIMESTAMP('2017-05-29 16:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:51:59.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540324,540558,TO_TIMESTAMP('2017-05-29 16:51:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-05-29 16:51:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:52:14.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,340,0,135,540558,545187,TO_TIMESTAMP('2017-05-29 16:52:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-05-29 16:52:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:52:33.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57036,0,135,540558,545188,TO_TIMESTAMP('2017-05-29 16:52:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Feld Sequenz',20,0,0,TO_TIMESTAMP('2017-05-29 16:52:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:53:25.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10892,0,135,540558,545189,TO_TIMESTAMP('2017-05-29 16:53:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Adress Format Lokal',30,0,0,TO_TIMESTAMP('2017-05-29 16:53:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:53:49.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,346,0,135,540558,545190,TO_TIMESTAMP('2017-05-29 16:53:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Postleitzahl Format',40,0,0,TO_TIMESTAMP('2017-05-29 16:53:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:54:04.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540325,540559,TO_TIMESTAMP('2017-05-29 16:54:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-05-29 16:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:54:21.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,341,0,135,540559,545191,TO_TIMESTAMP('2017-05-29 16:54:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-05-29 16:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:55:28.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10893,0,135,540559,545192,TO_TIMESTAMP('2017-05-29 16:55:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Adress Reihenfolge umkehren',20,0,0,TO_TIMESTAMP('2017-05-29 16:55:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:55:57.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545192
;

-- 2017-05-29T16:56:17.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51000,0,135,540559,545193,TO_TIMESTAMP('2017-05-29 16:56:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Postleitzahlen Lookup',20,0,0,TO_TIMESTAMP('2017-05-29 16:56:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:56:43.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57035,0,135,540559,545194,TO_TIMESTAMP('2017-05-29 16:56:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Städteliste',30,0,0,TO_TIMESTAMP('2017-05-29 16:56:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:56:49.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Postleitzahlen Verzeichnis',Updated=TO_TIMESTAMP('2017-05-29 16:56:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545193
;

-- 2017-05-29T16:57:03.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540325,540560,TO_TIMESTAMP('2017-05-29 16:57:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','currency',20,TO_TIMESTAMP('2017-05-29 16:57:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:57:12.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5752,0,135,540560,545195,TO_TIMESTAMP('2017-05-29 16:57:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Währung',10,0,0,TO_TIMESTAMP('2017-05-29 16:57:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:57:18.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540325,540561,TO_TIMESTAMP('2017-05-29 16:57:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2017-05-29 16:57:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:57:29.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2017,0,135,540561,545196,TO_TIMESTAMP('2017-05-29 16:57:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-05-29 16:57:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:57:37.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,337,0,135,540561,545197,TO_TIMESTAMP('2017-05-29 16:57:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-05-29 16:57:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T16:58:47.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545104
;

-- 2017-05-29T16:58:47.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545105
;

-- 2017-05-29T16:58:47.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545106
;

-- 2017-05-29T16:58:47.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545107
;

-- 2017-05-29T16:58:47.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545108
;

-- 2017-05-29T16:58:47.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545109
;

-- 2017-05-29T16:58:47.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545110
;

-- 2017-05-29T16:59:16.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545113
;

-- 2017-05-29T16:59:16.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545115
;

-- 2017-05-29T16:59:16.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545116
;

-- 2017-05-29T16:59:16.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545118
;

-- 2017-05-29T16:59:41.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545125
;

-- 2017-05-29T16:59:41.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545126
;

-- 2017-05-29T16:59:41.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545127
;

-- 2017-05-29T16:59:41.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545128
;

-- 2017-05-29T17:00:45.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5753,0,135,540560,545198,TO_TIMESTAMP('2017-05-29 17:00:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sprache',20,0,0,TO_TIMESTAMP('2017-05-29 17:00:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:05:01.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,135,540242,TO_TIMESTAMP('2017-05-29 17:05:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-29 17:05:01','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-05-29T17:05:01.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540242 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-05-29T17:05:33.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545111
;

-- 2017-05-29T17:05:33.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545112
;

-- 2017-05-29T17:05:33.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545114
;

-- 2017-05-29T17:05:33.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545117
;

-- 2017-05-29T17:05:33.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545119
;

-- 2017-05-29T17:05:33.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545120
;

-- 2017-05-29T17:05:33.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545121
;

-- 2017-05-29T17:05:33.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545122
;

-- 2017-05-29T17:05:33.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545123
;

-- 2017-05-29T17:05:33.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545124
;

-- 2017-05-29T17:05:33.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545129
;

-- 2017-05-29T17:05:33.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545130
;

-- 2017-05-29T17:05:33.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545131
;

-- 2017-05-29T17:05:33.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545132
;

-- 2017-05-29T17:05:33.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545133
;

-- 2017-05-29T17:05:33.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545134
;

-- 2017-05-29T17:05:33.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545135
;

-- 2017-05-29T17:05:33.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545136
;

-- 2017-05-29T17:05:33.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545137
;

-- 2017-05-29T17:05:33.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545138
;

-- 2017-05-29T17:05:33.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545139
;

-- 2017-05-29T17:05:33.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545140
;

-- 2017-05-29T17:05:33.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545141
;

-- 2017-05-29T17:05:33.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545142
;

-- 2017-05-29T17:05:33.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545143
;

-- 2017-05-29T17:05:33.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545144
;

-- 2017-05-29T17:05:33.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545145
;

-- 2017-05-29T17:05:33.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545146
;

-- 2017-05-29T17:05:33.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545147
;

-- 2017-05-29T17:05:33.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545148
;

-- 2017-05-29T17:05:33.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545149
;

-- 2017-05-29T17:05:33.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545150
;

-- 2017-05-29T17:05:38.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540552
;

-- 2017-05-29T17:05:45.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540330,540242,TO_TIMESTAMP('2017-05-29 17:05:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-29 17:05:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:06:08.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540330,540562,TO_TIMESTAMP('2017-05-29 17:06:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','region',10,TO_TIMESTAMP('2017-05-29 17:06:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:06:27.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,342,0,135,540562,545199,TO_TIMESTAMP('2017-05-29 17:06:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Land hat Reqionen',10,0,0,TO_TIMESTAMP('2017-05-29 17:06:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:06:42.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,343,0,135,540562,545200,TO_TIMESTAMP('2017-05-29 17:06:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Region',20,0,0,TO_TIMESTAMP('2017-05-29 17:06:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:08:06.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540330,540563,TO_TIMESTAMP('2017-05-29 17:08:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','bank',20,TO_TIMESTAMP('2017-05-29 17:08:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:08:24.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555682,0,135,540563,545201,TO_TIMESTAMP('2017-05-29 17:08:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bank Code Länge',10,0,0,TO_TIMESTAMP('2017-05-29 17:08:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:08:45.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555683,0,135,540563,545202,TO_TIMESTAMP('2017-05-29 17:08:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bank Code Zeichensatz',20,0,0,TO_TIMESTAMP('2017-05-29 17:08:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:09:00.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555718,0,135,540563,545203,TO_TIMESTAMP('2017-05-29 17:09:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bank Code Sequenz',30,0,0,TO_TIMESTAMP('2017-05-29 17:09:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:09:32.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10895,0,135,540563,545204,TO_TIMESTAMP('2017-05-29 17:09:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bank Code Format',40,0,0,TO_TIMESTAMP('2017-05-29 17:09:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:09:51.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10894,0,135,540563,545205,TO_TIMESTAMP('2017-05-29 17:09:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bank Konto Format',50,0,0,TO_TIMESTAMP('2017-05-29 17:09:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:10:24.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540330,540564,TO_TIMESTAMP('2017-05-29 17:10:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','lookup',30,TO_TIMESTAMP('2017-05-29 17:10:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:10:41.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51003,0,135,540564,545206,TO_TIMESTAMP('2017-05-29 17:10:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','URL PLZ Verzeichnis',10,0,0,TO_TIMESTAMP('2017-05-29 17:10:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:10:58.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51002,0,135,540564,545207,TO_TIMESTAMP('2017-05-29 17:10:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','URL ',20,0,0,TO_TIMESTAMP('2017-05-29 17:10:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:11:02.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Lookup URL PLZ Verzeichnis',Updated=TO_TIMESTAMP('2017-05-29 17:11:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545206
;

-- 2017-05-29T17:11:11.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Lookup URL Login',Updated=TO_TIMESTAMP('2017-05-29 17:11:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545207
;

-- 2017-05-29T17:11:28.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51004,0,135,540564,545208,TO_TIMESTAMP('2017-05-29 17:11:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lookup Passwort',30,0,0,TO_TIMESTAMP('2017-05-29 17:11:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:11:36.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Lookup Login',Updated=TO_TIMESTAMP('2017-05-29 17:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545207
;

-- 2017-05-29T17:11:51.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,51001,0,135,540564,545209,TO_TIMESTAMP('2017-05-29 17:11:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lookup Klassenname',40,0,0,TO_TIMESTAMP('2017-05-29 17:11:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:12:10.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540330,540566,TO_TIMESTAMP('2017-05-29 17:12:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',40,TO_TIMESTAMP('2017-05-29 17:12:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:12:29.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,342,0,135,540566,545211,TO_TIMESTAMP('2017-05-29 17:12:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Land hat Regionen',10,0,0,TO_TIMESTAMP('2017-05-29 17:12:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:12:48.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540330,540567,TO_TIMESTAMP('2017-05-29 17:12:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','phone',50,TO_TIMESTAMP('2017-05-29 17:12:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:13:04.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,344,0,135,540567,545214,TO_TIMESTAMP('2017-05-29 17:13:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Phone Format',10,0,0,TO_TIMESTAMP('2017-05-29 17:13:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:13:26.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11184,0,135,540567,545216,TO_TIMESTAMP('2017-05-29 17:13:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Media Abmessungen ISO',20,0,0,TO_TIMESTAMP('2017-05-29 17:13:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-29T17:13:37.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545216
;

-- 2017-05-29T17:13:38.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545214
;

-- 2017-05-29T17:13:44.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545211
;

-- 2017-05-29T17:13:49.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545209
;

-- 2017-05-29T17:13:49.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545208
;

-- 2017-05-29T17:13:49.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545207
;

-- 2017-05-29T17:13:51.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545206
;

-- 2017-05-29T17:13:56.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545205
;

-- 2017-05-29T17:13:56.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545204
;

-- 2017-05-29T17:13:56.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545203
;

-- 2017-05-29T17:13:57.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545202
;

-- 2017-05-29T17:13:58.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545201
;

-- 2017-05-29T17:14:04.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545200
;

-- 2017-05-29T17:14:06.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-29 17:14:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545199
;

-- 2017-05-29T17:14:48.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545211
;

-- 2017-05-29T17:14:52.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540566
;

-- 2017-05-29T17:20:49.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545183
;

-- 2017-05-29T17:20:49.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545184
;

-- 2017-05-29T17:20:49.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545185
;

-- 2017-05-29T17:20:49.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545186
;

-- 2017-05-29T17:20:49.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545191
;

-- 2017-05-29T17:20:49.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545194
;

-- 2017-05-29T17:20:49.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545199
;

-- 2017-05-29T17:20:49.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545195
;

-- 2017-05-29T17:20:49.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545196
;

-- 2017-05-29T17:20:49.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545197
;

-- 2017-05-29T17:20:49.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-05-29 17:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545198
;

-- 2017-05-29T17:23:43.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-05-29 17:23:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545183
;

-- 2017-05-29T17:23:46.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-05-29 17:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545184
;

-- 2017-05-29T17:23:50.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-05-29 17:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545185
;

-- 2017-05-29T17:23:54.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-05-29 17:23:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545186
;

-- 2017-05-29T17:24:20.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-05-29 17:24:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545195
;

-- 2017-05-29T17:24:33.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-05-29 17:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545198
;

-- 2017-05-29T17:24:55.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-05-29 17:24:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545196
;

-- 2017-05-29T17:24:59.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-05-29 17:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545197
;

-- 2017-05-29T17:25:21.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-29 17:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545198
;

-- 2017-05-29T17:25:21.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-29 17:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545196
;

-- 2017-05-29T17:25:21.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-05-29 17:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545197
;

-- 2017-05-29T17:25:38.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-05-29 17:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545183
;

-- 2017-05-29T17:25:38.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-05-29 17:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545185
;

-- 2017-05-29T17:25:38.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-05-29 17:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545186
;

-- 2017-05-29T17:25:38.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-05-29 17:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545191
;

