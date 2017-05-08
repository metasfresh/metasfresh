-- 2017-05-08T15:28:26.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,53032,540172,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540241,540172,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540242,540172,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540241,540381,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53563,0,53032,540381,543879,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53564,0,53032,540381,543880,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53565,0,53032,540381,543881,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',30,30,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53606,0,53032,540381,543882,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Versionsnummer','Y','N','Y','Y','N','Versions-Nr.',40,40,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53566,0,53032,540381,543883,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',50,50,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53567,0,53032,540381,543884,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',60,60,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53568,0,53032,540381,543885,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Comment or Hint','The Help field contains a hint, comment or help about the use of this item.','Y','N','Y','Y','N','Kommentar/Hilfe',70,70,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53569,0,53032,540381,543886,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Information für den Kunden','"Notiz" wird für zusätzliche Informationen zu diesem Produkt verwendet.','Y','N','Y','Y','N','Notiz / Zeilentext',80,80,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53570,0,53032,540381,543887,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)','Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.','Y','N','Y','Y','N','UPC/EAN',90,90,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53572,0,53032,540381,543888,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',100,100,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53574,0,53032,540381,543889,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','Y','N','Produkt-Kategorie',110,110,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53578,0,53032,540381,543890,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','Y','N','Maßeinheit',120,120,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53580,0,53032,540381,543891,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Art von Produkt','Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.','Y','N','Y','Y','N','Produktart',130,130,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53582,0,53032,540381,543892,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Gewicht eines Produktes','The Weight indicates the weight  of the product in the Weight UOM of the Client','Y','N','Y','Y','N','Gewicht',140,140,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53583,0,53032,540381,543893,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Volumen eines Produktes','Gibt das Volumen eines Produktes in der Volumen-Mengeneinheit des Mandanten an.','Y','N','Y','Y','N','Volumen',150,150,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53586,0,53032,540381,543894,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Die Organisation hat dieses Produkt auf Lager','"Lagerhaltig" zeigt an, ob die Organisation dieses Produkt auf Lager hält.','Y','N','Y','Y','N','Lagerhaltig',160,160,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53592,0,53032,540381,543895,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Stückliste','Das Selektionsfeld "Stückliste" zeigt an, ob dieses Produkt aus Produkten auf einer Stückliste besteht.','Y','N','Y','Y','N','Stückliste',170,170,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53593,0,53032,540381,543896,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'The BOM configuration has been verified','The Verified check box indicates if the configuration of this product has been verified.  This is used for products that consist of a bill of materials','Y','N','Y','Y','N','Verified',180,180,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53594,0,53032,540381,543897,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Stücklisten-Struktur verifizieren','Das Verifizieren der Stücklistenstruktur überprüft die Elemente und Schritte, die eine Stückliste ausmachen.','Y','N','Y','Y','N','Stückliste verifizieren',190,190,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53597,0,53032,540381,543898,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Die Organisation kauft dieses Produkt ein','Das Selektionsfeld "Eingekauft" zeigt an, ob dieses Produkt von dieser Organisation eingekauft wird.','Y','N','Y','Y','N','Eingekauft',200,200,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53598,0,53032,540381,543899,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Die Organisation verkauft dieses Produkt','Das Selektionsfeld "Verkauft" zeigt an, ob dieses Produkt von dieser Organisation verkauft wird.','Y','N','Y','Y','N','Verkauft',210,210,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555416,0,53032,540381,543900,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Exclude from MRP calculation','Y','N','Y','Y','N','Exclude from MRP',220,220,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,53030,540173,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540243,540173,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:26.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540243,540382,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53508,0,53030,540382,543901,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-08 15:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53509,0,53030,540382,543902,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53510,0,53030,540382,543903,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,30,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53511,0,53030,540382,543904,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,40,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53513,0,53030,540382,543905,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Resource','A manufacturing resource is a place where a product will be made.','Y','N','N','Y','N','Produktions Ressource',0,50,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53512,0,53030,540382,543906,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Storage Warehouse and Service Point','The Warehouse identifies a unique Warehouse where products are stored or Services are provided.

Warehouse place where you locate and control the products','Y','N','N','Y','N','Lager',0,60,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53516,0,53030,540382,543907,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Planer',0,70,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53517,0,53030,540382,543908,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'The name BOM/Formula that you introduce in this window will be considered the default BOM to produce the product in this Organization-Plant-Warehouse. If you do not fill this field the default BOM & Formula for the entity will be the BOM/Formula which has the same name as the product.','Y','N','N','Y','N','Stücklisten Konfiguration',0,80,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53518,0,53030,540382,543909,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Workflow or combination of tasks','The Workflow field identifies a unique Workflow in the system.

The Workflow you introduce in this window will be considered the default Workflow to produce the product in this Organization-Plant-Warehouse. If you do not fill this field the defaul Workflow for the entity will be the Workflow with the same name as the product.','Y','N','N','Y','N','Arbeitsablauf',0,90,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,54392,0,53030,540382,543910,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Network Distribution',0,100,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53520,0,53030,540382,543911,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Indicates whether planned orders will be generated by MRP','Indicates whether planned orders will be generated by MRP, if this flag is not just MRP generate a ''Create'' action notice','Y','N','N','Y','N','Plan erzeugen',0,110,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53521,0,53030,540382,543912,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Erfordert MRP Berechnung',0,120,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,55434,0,53030,540382,543913,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Erfordert DRP Berechnung',0,130,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53534,0,53030,540382,543914,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent','ADempiere Calculate the total yield for a product from the yield for each activity when the process Workflow Cost Roll-Up is executed.

The expected yield for an Activity can be expressed as:

Yield = Acceptable Units at Activity End x 100

The Total manufacturing yield for a product is determined by multiplying the yied percentage for each activity.

Manufacturing Yield = Yield % for Activity 10 x Yied % for Activity 20 , etc 

Take care when setting yield to anything but 100% particularly when yied is used for multiples activities

','Y','N','N','Y','N','Ausbringung %',0,140,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554084,0,53030,540382,543915,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Die Organisation kauft dieses Produkt ein','Das Selektionsfeld "Eingekauft" zeigt an, ob dieses Produkt von dieser Organisation eingekauft wird.','Y','N','N','Y','N','Eingekauft',0,150,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554083,0,53030,540382,543916,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,160,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554085,0,53030,540382,543917,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Wird produziert',0,170,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555188,0,53030,540382,543918,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','IsDocComplete ',0,180,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556495,0,53030,540382,543919,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. ','Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen auftragsposition ein Eintrag erstellt','Y','N','N','Y','N','Wird gehandelt',0,190,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53525,0,53030,540382,543920,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Order Policy',0,200,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53526,0,53030,540382,543921,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Order Period',0,210,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555145,0,53030,540382,543922,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Aggregate on BPartner Level',0,220,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,53033,540174,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540244,540174,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:27.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540244,540383,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53613,0,53033,540383,543923,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-08 15:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53614,0,53033,540383,543924,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53615,0,53033,540383,543925,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,30,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53616,0,53033,540383,543926,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','Lager',0,40,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53617,0,53033,540383,543927,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53618,0,53033,540383,543928,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Methode für das Nachbestellen des Produktes','The Replenish Type indicates if this product will be manually re-ordered, ordered when the quantity is below the minimum quantity or ordered when it is below the maximum quantity. If you select a custom replenishment type, you need to create a class implementing org.compiere.util.ReplenishInterface and set that on warehouse level.','Y','N','N','Y','N','Art der Wiederauffüllung',0,60,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53619,0,53033,540383,543929,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Minimaler Bestand für dieses Produkt','Indicates the minimum quantity of this product to be stocked in inventory.
','Y','N','N','Y','N','Mindestmenge',0,70,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53620,0,53033,540383,543930,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Maximaler Bestand für dieses Produkt','Gibt die maximale Menge an, die für dieses Produkt auf Lager gehalten werden soll.','Y','N','N','Y','N','Maximalmenge',0,80,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540586,540175,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540245,540175,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540245,540384,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554158,0,540586,540384,543931,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554159,0,540586,540384,543932,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554160,0,540586,540384,543933,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,30,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554161,0,540586,540384,543934,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,40,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554162,0,540586,540384,543935,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,50,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554163,0,540586,540384,543936,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,60,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554164,0,540586,540384,543937,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Hersteller des Produktes','Der Hersteller des Produktes (genutzt, wenn abweichend von Geschäftspartner / Lieferant).','Y','N','N','Y','N','Hersteller',0,70,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554165,0,540586,540384,543938,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Methode zur Bewertung von Lieferanten','Die "Qualitäts-Einstufung" zeigt an, wie dieser Lieferant bewertet wird (höherer Wert = höhere Qualität)','Y','N','N','Y','N','Qualitäts-Einstufung',0,80,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554166,0,540586,540384,543939,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Mindesthaltbarkeit in Prozent, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz','Miminum Shelf Life of products with Guarantee Date instance. If > 0 you cannot select products with a shelf life ((Guarantee Date-Today) / Guarantee Days) less than the minum shelf life, unless you select "Show All"','Y','N','N','Y','N','Mindesthaltbarkeit %',0,90,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554167,0,540586,540384,543940,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Mindesthaltbarkeit in Tagen, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz','Miminum Shelf Life of products with Guarantee Date instance. If > 0 you cannot select products with a shelf life less than the minum shelf life, unless you select "Show All"','Y','N','N','Y','N','Mindesthaltbarkeit Tage',0,100,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554172,0,540586,540384,543941,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Name des Produktes','Y','N','N','Y','N','Produktname',0,110,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554173,0,540586,540384,543942,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Produktnummer',0,120,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554174,0,540586,540384,543943,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Produktidentifikation der Customer Unit durch European Article Number','Y','N','N','Y','N','CU-EAN',0,130,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554175,0,540586,540384,543944,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Produktkategorie',0,140,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554176,0,540586,540384,543945,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Produktbeschreibung','Y','N','N','Y','N','Produktbeschreibung',0,150,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554177,0,540586,540384,543946,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Verwendet für Kunden',0,160,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554178,0,540586,540384,543947,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Verwendet für Lieferant',0,170,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554179,0,540586,540384,543948,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Diesen Lieferanten für Bepreisung und Lagerauffüllung verwenden','"Gegenwärtiger Lieferant" zeigt an, ob dessen Preise verwendet und das Produkt bei ihm bestellt wird.','Y','N','N','Y','N','Gegenwärtiger Lieferant',0,180,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,53034,540176,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540246,540176,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540246,540385,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53622,0,53034,540385,543949,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53623,0,53034,540385,543950,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53624,0,53034,540385,543951,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,30,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53625,0,53034,540385,543952,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','Merkmale',0,40,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53626,0,53034,540385,543953,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:28.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53627,0,53034,540385,543954,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','Y','N','Lagerort',0,60,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:29.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53628,0,53034,540385,543955,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100,'Menge eines bewegten Produktes.','Die "Bewegungs-Menge" bezeichnet die Menge einer Ware, die bewegt wurde.','Y','N','N','Y','N','Bewegungs-Menge',0,70,0,TO_TIMESTAMP('2017-05-08 15:28:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:29.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53629,0,53034,540385,543956,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.','Y','N','N','Y','N','Bewegungs-Datum',0,80,0,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:29.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53630,0,53034,540385,543957,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Art der Warenbewegung','"Bewegungs-Art" zeigt die Art der Warenbewegung (eingehend, ausgehend, an Produktion, usw.) an.','Y','N','N','Y','N','Bewegungs-Art',0,90,0,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:29.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53631,0,53034,540385,543958,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Position auf Versand- oder Wareneingangsbeleg','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','N','Y','N','Versand-/Wareneingangsposition',0,100,0,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:29.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53632,0,53034,540385,543959,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Eindeutige Position in einem Inventurdokument','"Inventur-Position" bezeichnet die Poition in dem Inventurdokument (wenn zutreffend) für diese Transaktion.','Y','N','N','Y','N','Inventur-Position',0,110,0,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:29.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53633,0,53034,540385,543960,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Eindeutige Position in einem Warenbewegungsdokument','"Warenbewegungs-Position" bezeichnet die Poition in dem Warenbewegungsdokument (wenn zutreffend) für diese Transaktion.','Y','N','N','Y','N','Warenbewegungs- Position',0,120,0,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:29.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53634,0,53034,540385,543961,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Belegposition, die eine Produktion repräsentiert','"Produktions-Position" bezeichnet die Poition in dem Produktionsdokument (wenn zutreffend) für diese Transaktion.','Y','N','N','Y','N','Produktions-Position',0,130,0,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:29.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53636,0,53034,540385,543962,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Manufacturing Cost Collector',0,140,0,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-08T15:28:29.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53637,0,53034,540385,543963,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Project Issues (Material, Labor)','Issues to the project initiated by the "Issue to Project" process. You can issue Receipts, Time and Expenses, or Stock.','Y','N','N','Y','N','Projekt-Problem',0,150,0,TO_TIMESTAMP('2017-05-08 15:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;
