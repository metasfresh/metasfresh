-- 2017-04-23T09:14:12.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,189,540140,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540191,540140,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540192,540140,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540191,540287,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2055,0,189,540287,543220,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',10,10,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2118,0,189,540287,543221,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',20,20,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1155,0,189,540287,543222,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1156,0,189,540287,543223,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',40,40,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1154,0,189,540287,543224,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',50,50,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,50181,0,189,540287,543225,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Übergeordnete Kategorie',60,60,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11203,0,189,540287,543226,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Methode für den Materialfluß','The Material Movement Policy determines how the stock is flowing (FiFo or LiFo) if a specific Product Instance was not selected.  The policy can not contradict the costing method (e.g. FiFo movement policy and LiFo costing method).','Y','N','Y','Y','N','Materialfluß',70,70,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3748,0,189,540287,543227,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','Y','Y','N','Standard',80,80,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6134,0,189,540287,543228,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Gruppe von Assets','Eine Gruppe von Assets bestimmt Standardkonten. If an asset group is selected in the product category, assets are created when delivering the asset.','Y','N','Y','Y','N','Anlage- Gruppe',90,90,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553957,0,189,540287,543229,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals-Satz zum Produkt','Definieren Sie Merkmals-Sätze um einem Produkt zusätzliche Merkmale und Eigenschaften zuzuordnen. Sie müssen einen Merkmals-Satz anlegen, um Serien- und Los-Nummern verfolgen zu können.','Y','N','Y','Y','N','Merkmals-Satz',100,100,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556611,0,189,540287,543230,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Packaging Material ',110,110,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556619,0,189,540287,543231,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gebinde',120,120,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555418,0,189,540287,543232,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Exclude from MRP calculation','Y','N','Y','Y','N','Exclude from MRP',130,130,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,324,540141,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540193,540141,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540193,540288,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3935,0,324,540288,543233,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:12.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3936,0,324,540288,543234,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3939,0,324,540288,543235,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','Y','N','Produkt-Kategorie',0,30,0,TO_TIMESTAMP('2017-04-23 09:14:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3937,0,324,540288,543236,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','N','Y','N','Buchführungs-Schema',0,40,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3938,0,324,540288,543237,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11204,0,324,540288,543238,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Gibt an, wie die Kosten berechnet werden','"Kostenrechnungsmethode" gibt an, wie die Kosten berechnet werden (Standard, Durchschnitt, LiFo, FiFo). Die Standardmethode ist auf Ebene des Kontenrahmens definiert und kann optional in der Produktkategorie überschrieben werden. Die Methode der Kostenrechnung kann nicht zur Art der Materialbewegung (definiert in der Produktkategorie) im Widerspruch stehen.','Y','N','N','Y','N','Kostenrechnungsmethode',0,60,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12158,0,324,540288,543239,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Die unterste Stufe um Kosteninformation zu kumulieren','Wenn Sie verschiedene Kosten pro Organisation (Lager) oder pro Charge/Los verwalten möchten dann stellen Sie sicher, dass Sie diese für jede Organistaion und jede Charge bzw. jedes Los definieren. Die Kostenrechnungsstufe ist pro Kontenrahmens definiert und kann durch die Produktkategorie oder  den Kontenrahmen überschrieben werden.','Y','N','N','Y','N','Kostenrechnungsstufe',0,70,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3940,0,324,540288,543240,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Warenbestand','"Produkt-Asset" bezeichnet das Konto für die Bewertung eines Produktes im Bestand.','Y','N','N','Y','N','Warenbestand',0,80,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3942,0,324,540288,543241,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Produkt Aufwand','The Product Expense Account indicates the account used to record expenses associated with this product.','Y','N','N','Y','N','Produkt Aufwand',0,90,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12352,0,324,540288,543242,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Bezugsnebenkosten','Account used for posting product cost adjustments (e.g. landed costs)','Y','N','N','Y','N','Bezugsnebenkosten',0,100,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12353,0,324,540288,543243,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Product Inventory Clearing Account','Account used for posting matched product (item) expenses (e.g. AP Invoice, Invoice Match).  You would use a different account then Product Expense, if you want to differentate service related costs from item related costs. The balance on the clearing account should be zero and accounts for the timing difference between invoice receipt and matching.
','Y','N','N','Y','N','Inventory Clearing',0,110,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3941,0,324,540288,543244,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Produkt Vertriebsausgaben','The Product COGS Account indicates the account used when recording costs associated with this product.','Y','N','N','Y','N','Produkt Vertriebsausgaben',0,120,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3943,0,324,540288,543245,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.','The Purchase Price Variance is used in Standard Costing. It reflects the difference between the Standard Cost and the Purchase Order Price.','Y','N','N','Y','N','Preisdifferenz Bestellung',0,130,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4871,0,324,540288,543246,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Preisdifferenz Einkauf Rechnung','The Invoice Price Variance is used reflects the difference between the current Costs and the Invoice Price.','Y','N','N','Y','N','Preisdifferenz Einkauf Rechnung',0,140,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4872,0,324,540288,543247,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Konto für erhaltene Rabatte','The Trade Discount Receivables Account indicates the account for received trade discounts in vendor invoices','Y','N','N','Y','N','Erhaltene Rabatte',0,150,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4873,0,324,540288,543248,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Konto für gewährte Rabatte','The Trade Discount Granted Account indicates the account for granted trade discount in sales invoices','Y','N','N','Y','N','Gewährte Rabatte',0,160,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3944,0,324,540288,543249,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Produkt Ertrag','The Product Revenue Account indicates the account used for recording sales revenue for this product.','Y','N','N','Y','N','Produkt Ertrag',0,170,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56539,0,324,540288,543250,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet','Y','N','N','Y','N','Unfertige Leistungen',0,180,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56532,0,324,540288,543251,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Floor Stock account is the account used Manufacturing Order','The Floor Stock is used for accounting the component with Issue method  is set Floor stock  into Bill of Material & Formula Window.

The components with Issue Method  defined as Floor stock is acounting next way:

Debit Floor Stock Account
Credit Work in Process Account ','Y','N','N','Y','N','Floor Stock',0,190,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56534,0,324,540288,543252,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Method Change Variance account is the account used Manufacturing Order','The Method Change Variance is used in Standard Costing. It reflects the difference between the Standard BOM , Standard Manufacturing Workflow and Manufacturing BOM Manufacturing Workflow.

If you change the method the manufacturing defined in BOM or Workflow Manufacturig then this variance is generate.','Y','N','N','Y','N','Method Change Variance',0,200,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56538,0,324,540288,543253,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Usage Variance account is the account used Manufacturing Order','The Usage Variance is used in Standard Costing. It reflects the difference between the  Quantities of Standard BOM  or Time Standard Manufacturing Workflow and Quantities of Manufacturing BOM or Time Manufacturing Workflow of Manufacturing Order.

If you change the Quantities or Time  defined in BOM or Workflow Manufacturig then this variance is generate.','Y','N','N','Y','N','Usage Variance',0,210,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56537,0,324,540288,543254,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Rate Variance account is the account used Manufacturing Order','The Rate Variance is used in Standard Costing. It reflects the difference between the Standard Cost Rates and  The Cost Rates of Manufacturing Order.

If you change the Standard Rates then this variance is generate.','Y','N','N','Y','N','Rate Variance',0,220,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56535,0,324,540288,543255,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Mix Variance account is the account used Manufacturing Order','The Mix Variance is used when a co-product  received in Inventory  is different the quantity  expected
','Y','N','N','Y','N','Mix Variance',0,230,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56533,0,324,540288,543256,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Labor account is the account used Manufacturing Order','The Labor is used for accounting the productive Labor
','Y','N','N','Y','N','Labor',0,240,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56530,0,324,540288,543257,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Burden account is the account used Manufacturing Order','The Burden is used for accounting the Burden','Y','N','N','Y','N','Burden',0,250,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56531,0,324,540288,543258,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Cost Of Production account is the account used Manufacturing Order','The Cost Of Production is used for accounting Non productive Labor
','Y','N','N','Y','N','Cost Of Production',0,260,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56536,0,324,540288,543259,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Outside Processing Account is the account used in Manufacturing Order','The Outside Processing Account is used for accounting the Outside Processing','Y','N','N','Y','N','Outside Processing',0,270,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56552,0,324,540288,543260,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Overhead account is the account used  in Manufacturing Order ','Y','N','N','Y','N','Overhead',0,280,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56553,0,324,540288,543261,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'The Scrap account is the account used  in Manufacturing Order ','Y','N','N','Y','N','Scrap',0,290,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3945,0,324,540288,543262,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Kopieren und überschreiben der Konten der Produkte dieser Kategorie','If you copy and overwrite the current default values, you may have to repeat previous updates (e.g. set the revenue account, ...). If no Accounting Schema is selected all Accounting Schemas will be updated / inserted for products of this category.','Y','N','N','Y','N','Konten kopieren',0,300,0,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,407,540142,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:13.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540194,540142,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540194,540289,TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-04-23 09:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5290,0,407,540289,543263,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5291,0,407,540289,543264,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5295,0,407,540289,543265,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','Y','N','Produkt-Kategorie',0,30,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5319,0,407,540289,543266,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Suchschlüssel',0,40,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5320,0,407,540289,543267,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,50,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5318,0,407,540289,543268,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,60,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5287,0,407,540289,543269,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.','Y','N','N','Y','N','Zusammenfassungseintrag',0,70,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5306,0,407,540289,543270,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Dieses Produkt ist nicht mehr verfügbar','Das Selektionsfeld "Eingestellt" zeigt ein Produkt an, das nicht länger verfügbar ist.','Y','N','N','Y','N','Eingestellt',0,80,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5885,0,407,540289,543271,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Art von Produkt','Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.','Y','N','N','Y','N','Produktart',0,90,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5389,0,407,540289,543272,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Expense report type','Y','N','N','Y','N','Aufwandsart',0,100,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,5391,0,407,540289,543273,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Ressource','Y','N','N','Y','N','Ressource',0,110,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-23T09:14:14.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8609,0,407,540289,543274,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Wenn selektiert wird dieses Produkt anfänglich oder bei leeren Suchkriterien angezeigt','In the display of products in the Web Store, the product is displayed in the inital view or if no search criteria are entered. To be displayed, the product must be in the price list used.','Y','N','N','Y','N','Beworben im Web-Shop',0,120,0,TO_TIMESTAMP('2017-04-23 09:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

