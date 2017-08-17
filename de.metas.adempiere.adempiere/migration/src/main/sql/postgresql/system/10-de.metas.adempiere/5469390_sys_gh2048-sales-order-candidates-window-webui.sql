-- 2017-08-14T17:06:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540282,540405,TO_TIMESTAMP('2017-08-14 17:06:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:06:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-08-14T17:06:15.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540405 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-14T17:06:15.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540544,540405,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540545,540405,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540544,540962,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547043,0,540282,540962,547197,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Sagt aus, ob der Eintrag in eine Auftragsposition überführt werden oder irgnoriert werden soll','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Eintrag übernehmen',10,10,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547053,0,540282,540962,547198,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kand.-Datum',20,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547049,0,540282,540962,547199,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Erfasst durch',30,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551029,0,540282,540962,547200,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','Datensatz-ID',40,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547478,0,540282,540962,547201,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Eingabequelle',50,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551084,0,540282,540962,547202,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Data Destination',60,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547061,0,540282,540962,547203,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt imp.',70,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555174,0,540282,540962,547204,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produkt abw.',80,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555175,0,540282,540962,547205,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Produkt eff.',90,20,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,553756,0,540175,547203,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555178,0,540282,540962,547206,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Packvorschrift-Produkt Zuordnung abw.',100,30,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555179,0,540282,540962,547207,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Packvorschrift-Produkt Zuordnung eff.',110,40,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547057,0,540282,540962,547208,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','Y','N','Menge',120,50,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553757,0,540282,540962,547209,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verpackungskapazität',130,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547056,0,540282,540962,547210,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit imp.',140,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555183,0,540282,540962,547211,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Maßeinheit int.',150,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555177,0,540282,540962,547212,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Interne Preiseinheit laut Stammdaten','Y','N','Y','Y','N','Preiseinheit int.',160,60,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553994,0,540282,540962,547213,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','Y','N','Zugesagter Termin',170,70,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554066,0,540282,540962,547214,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Y','N','Y','Y','N','Zugesagter Termin abw.',180,80,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554080,0,540282,540962,547215,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Y','N','Y','Y','N','Zugesagter Termin eff.',190,90,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547048,0,540282,540962,547216,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',200,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555762,0,540282,540962,547217,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Produktbeschreibung','Y','N','Y','N','N','Produktbeschreibung',210,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547052,0,540282,540962,547218,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',220,100,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,547062,0,540176,547218,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,547063,0,540177,547218,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554064,0,540282,540962,547219,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','Geschäftspartner abw.',230,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:15.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554065,0,540282,540962,547220,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','Standort abw.',240,0,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554081,0,540282,540962,547221,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Geschäftspartner eff.',250,110,0,TO_TIMESTAMP('2017-08-14 17:06:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554082,0,540282,540962,547222,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','Y','Y','N','Standort eff.',260,120,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551069,0,540282,540962,547223,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','N','Y','N','N','Lieferart',270,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551070,0,540282,540962,547224,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','N','Y','N','N','Lieferung',280,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550289,0,540282,540962,547225,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartners für die Rechnungsstellung','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','N','Rechnungspartner',290,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,550290,0,540178,547225,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,550288,0,540179,547225,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553715,0,540282,540962,547226,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Übergabe an',300,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556943,0,540282,540962,547227,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Übergabe an abw.',310,130,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556944,0,540282,540962,547228,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Übergabe an eff.',320,140,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,553714,0,540180,547226,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556945,0,540282,540962,547229,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Übergabeadresse abw.',330,150,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556946,0,540282,540962,547230,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Übergabeadresse eff.',340,160,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,549447,0,540282,540962,547231,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ziel-Lager',350,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551085,0,540282,540962,547232,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','Y','N','N','Preissystem',360,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547047,0,540282,540962,547233,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','Merkmale',370,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550286,0,540282,540962,547234,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Importierter Preis','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet','Y','N','Y','Y','N','Preis imp.',380,170,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555176,0,540282,540962,547235,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Interner Preis laut Stammdaten','Y','N','Y','Y','N','Preis int.',390,180,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555163,0,540282,540962,547236,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','Y','Y','N','Preis eff.',400,190,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555186,0,540282,540962,547237,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Preisdifferenz (imp. - int.)',410,200,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550292,0,540282,540962,547238,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',420,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550287,0,540282,540962,547239,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Abschlag in Prozent','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','Y','N','N','Rabatt %',430,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547094,0,540282,540962,547240,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','Y','N','Verarbeitet',440,210,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548747,0,540282,540962,547241,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten','Y','N','Y','Y','N','Fehler',450,220,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,549595,0,540282,540962,547242,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Fehlermeldung',460,230,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553469,0,540282,540962,547243,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','Y','N','Referenz',470,240,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:16.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553470,0,540282,540962,547244,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','Zeile Nr.',480,0,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554018,0,540282,540962,547245,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Replikationstransaktion',490,250,0,TO_TIMESTAMP('2017-08-14 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554017,0,540282,540962,547246,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Freigabe erforderlich',500,260,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553746,0,540282,540962,547247,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to ship to','If empty the business partner will be shipped to.','Y','N','Y','N','N','Lieferempfänger',510,0,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556939,0,540282,540962,547248,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferempfänger abw.',520,270,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556941,0,540282,540962,547249,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferempfänger eff.',530,280,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,553745,0,540181,547247,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556940,0,540282,540962,547250,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferadresse abw.',540,290,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556942,0,540282,540962,547251,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferadresse eff.',550,300,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540452,540406,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-08-14T17:06:17.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540406 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-14T17:06:17.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540546,540406,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540546,540963,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551020,0,540452,540963,547252,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','Y','N','Auftragsposition',0,10,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551022,0,540452,540963,547253,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','Y','N','Bestellte Menge',0,20,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551021,0,540452,540963,547254,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','N','Belegstatus',0,30,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:06:17.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551019,0,540452,540963,547255,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Auftragskand. Verarb.',0,40,0,TO_TIMESTAMP('2017-08-14 17:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:10:49.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540544,540964,TO_TIMESTAMP('2017-08-14 17:10:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-14 17:10:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:11:02.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=20, UIStyle='',Updated=TO_TIMESTAMP('2017-08-14 17:11:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540962
;

-- 2017-08-14T17:12:14.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547053,0,540282,540964,547256,TO_TIMESTAMP('2017-08-14 17:12:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kand.-Datum',10,0,0,TO_TIMESTAMP('2017-08-14 17:12:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:12:31.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547478,0,540282,540964,547257,TO_TIMESTAMP('2017-08-14 17:12:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Eingabequelle',20,0,0,TO_TIMESTAMP('2017-08-14 17:12:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:12:58.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540545,540965,TO_TIMESTAMP('2017-08-14 17:12:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-08-14 17:12:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:13:15.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547043,0,540282,540965,547258,TO_TIMESTAMP('2017-08-14 17:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Eintrag übernehmen',10,0,0,TO_TIMESTAMP('2017-08-14 17:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:13:27.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547094,0,540282,540965,547259,TO_TIMESTAMP('2017-08-14 17:13:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',20,0,0,TO_TIMESTAMP('2017-08-14 17:13:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:13:35.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548747,0,540282,540965,547260,TO_TIMESTAMP('2017-08-14 17:13:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Fehler',30,0,0,TO_TIMESTAMP('2017-08-14 17:13:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:13:55.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554017,0,540282,540965,547261,TO_TIMESTAMP('2017-08-14 17:13:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Freigabe erforderlich',40,0,0,TO_TIMESTAMP('2017-08-14 17:13:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:14:40.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540545,540966,TO_TIMESTAMP('2017-08-14 17:14:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',90,TO_TIMESTAMP('2017-08-14 17:14:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:14:56.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547060,0,540282,540966,547262,TO_TIMESTAMP('2017-08-14 17:14:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-08-14 17:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:15:05.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547055,0,540282,540966,547263,TO_TIMESTAMP('2017-08-14 17:15:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-08-14 17:15:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:17:12.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540544,540967,TO_TIMESTAMP('2017-08-14 17:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','product',20,TO_TIMESTAMP('2017-08-14 17:17:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:17:16.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=90,Updated=TO_TIMESTAMP('2017-08-14 17:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540962
;

-- 2017-08-14T17:17:31.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555175,0,540282,540967,547264,TO_TIMESTAMP('2017-08-14 17:17:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produkt eff.',10,0,0,TO_TIMESTAMP('2017-08-14 17:17:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:17:44.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555179,0,540282,540967,547265,TO_TIMESTAMP('2017-08-14 17:17:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift eff.',20,0,0,TO_TIMESTAMP('2017-08-14 17:17:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:19:25.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554081,0,540282,540964,547266,TO_TIMESTAMP('2017-08-14 17:19:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner eff.',30,0,0,TO_TIMESTAMP('2017-08-14 17:19:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:19:44.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547062,0,540282,540964,547267,TO_TIMESTAMP('2017-08-14 17:19:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Standort eff.',40,0,0,TO_TIMESTAMP('2017-08-14 17:19:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:19:59.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554082,0,540182,547266,TO_TIMESTAMP('2017-08-14 17:19:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:19:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:20:35.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547267
;

-- 2017-08-14T17:21:44.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555175,0,540282,540964,547268,TO_TIMESTAMP('2017-08-14 17:21:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produkt',40,0,0,TO_TIMESTAMP('2017-08-14 17:21:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:21:55.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547264
;

-- 2017-08-14T17:21:58.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547265
;

-- 2017-08-14T17:22:17.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555179,0,540183,547268,TO_TIMESTAMP('2017-08-14 17:22:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 17:22:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:24:11.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540967
;

-- 2017-08-14T17:24:27.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540544,540968,TO_TIMESTAMP('2017-08-14 17:24:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','qty price',20,TO_TIMESTAMP('2017-08-14 17:24:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:24:44.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547057,0,540282,540968,547269,TO_TIMESTAMP('2017-08-14 17:24:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Menge',10,0,0,TO_TIMESTAMP('2017-08-14 17:24:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:25:17.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547056,0,540282,540968,547270,TO_TIMESTAMP('2017-08-14 17:25:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Masseinheit',20,0,0,TO_TIMESTAMP('2017-08-14 17:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:25:29.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555163,0,540282,540968,547271,TO_TIMESTAMP('2017-08-14 17:25:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preis eff.',30,0,0,TO_TIMESTAMP('2017-08-14 17:25:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:25:47.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555177,0,540282,540968,547272,TO_TIMESTAMP('2017-08-14 17:25:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preiseinheit',40,0,0,TO_TIMESTAMP('2017-08-14 17:25:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:27:04.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551085,0,540282,540968,547273,TO_TIMESTAMP('2017-08-14 17:27:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preissystem',50,0,0,TO_TIMESTAMP('2017-08-14 17:27:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:28:04.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550292,0,540282,540968,547274,TO_TIMESTAMP('2017-08-14 17:28:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Währung',60,0,0,TO_TIMESTAMP('2017-08-14 17:28:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:28:48.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540545,540969,TO_TIMESTAMP('2017-08-14 17:28:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','reference',20,TO_TIMESTAMP('2017-08-14 17:28:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:29:00.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553469,0,540282,540969,547275,TO_TIMESTAMP('2017-08-14 17:29:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Referenz',10,0,0,TO_TIMESTAMP('2017-08-14 17:29:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:29:21.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,549447,0,540282,540969,547276,TO_TIMESTAMP('2017-08-14 17:29:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lager',20,0,0,TO_TIMESTAMP('2017-08-14 17:29:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:31:28.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551069,0,540282,540969,547277,TO_TIMESTAMP('2017-08-14 17:31:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferart',30,0,0,TO_TIMESTAMP('2017-08-14 17:31:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T17:31:41.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551070,0,540282,540969,547278,TO_TIMESTAMP('2017-08-14 17:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferung',40,0,0,TO_TIMESTAMP('2017-08-14 17:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T18:20:11.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-08-14 18:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540966
;

-- 2017-08-14T18:20:23.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540282,540407,TO_TIMESTAMP('2017-08-14 18:20:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-08-14 18:20:23','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-08-14T18:20:23.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540407 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-14T18:20:27.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540547,540407,TO_TIMESTAMP('2017-08-14 18:20:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-14 18:20:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T18:20:57.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547198
;

-- 2017-08-14T18:20:57.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547201
;

-- 2017-08-14T18:21:04.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547205
;

-- 2017-08-14T18:21:10.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547208
;

-- 2017-08-14T18:21:21.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547221
;

-- 2017-08-14T18:21:24.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547222
;

-- 2017-08-14T18:21:28.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547223
;

-- 2017-08-14T18:21:30.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547224
;

-- 2017-08-14T18:21:36.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547231
;

-- 2017-08-14T18:21:39.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547232
;

-- 2017-08-14T18:21:48.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547236
;

-- 2017-08-14T18:21:57.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547238
;

-- 2017-08-14T18:22:01.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547240
;

-- 2017-08-14T18:22:04.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547241
;

-- 2017-08-14T18:22:09.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547243
;

-- 2017-08-14T18:22:15.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547246
;

-- 2017-08-14T18:22:30.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547197
;

-- 2017-08-14T18:22:31.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547199
;

-- 2017-08-14T18:22:31.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547200
;

-- 2017-08-14T18:22:32.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547202
;

-- 2017-08-14T18:22:32.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547203
;

-- 2017-08-14T18:22:33.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547204
;

-- 2017-08-14T18:22:33.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547206
;

-- 2017-08-14T18:22:34.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547207
;

-- 2017-08-14T18:22:35.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547209
;

-- 2017-08-14T18:22:35.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547210
;

-- 2017-08-14T18:22:36.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547211
;

-- 2017-08-14T18:22:36.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547212
;

-- 2017-08-14T18:22:37.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547213
;

-- 2017-08-14T18:22:38.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547214
;

-- 2017-08-14T18:22:38.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547215
;

-- 2017-08-14T18:22:39.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547216
;

-- 2017-08-14T18:22:39.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547217
;

-- 2017-08-14T18:22:40.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547218
;

-- 2017-08-14T18:22:41.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547219
;

-- 2017-08-14T18:22:41.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547220
;

-- 2017-08-14T18:22:42.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547225
;

-- 2017-08-14T18:22:42.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547226
;

-- 2017-08-14T18:22:43.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547227
;

-- 2017-08-14T18:22:44.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547228
;

-- 2017-08-14T18:22:45.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547229
;

-- 2017-08-14T18:22:45.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547230
;

-- 2017-08-14T18:22:46.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547233
;

-- 2017-08-14T18:22:46.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547234
;

-- 2017-08-14T18:22:47.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547235
;

-- 2017-08-14T18:22:48.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547237
;

-- 2017-08-14T18:22:49.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547239
;

-- 2017-08-14T18:22:49.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547242
;

-- 2017-08-14T18:22:50.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547244
;

-- 2017-08-14T18:22:50.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547245
;

-- 2017-08-14T18:22:51.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547247
;

-- 2017-08-14T18:22:52.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547248
;

-- 2017-08-14T18:22:52.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547249
;

-- 2017-08-14T18:22:53.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547250
;

-- 2017-08-14T18:22:55.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-08-14 18:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547251
;

-- 2017-08-14T18:23:04.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-08-14 18:23:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540962
;

-- 2017-08-14T18:23:15.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540547, SeqNo=10,Updated=TO_TIMESTAMP('2017-08-14 18:23:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540962
;

-- 2017-08-14T18:24:47.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547197
;

-- 2017-08-14T18:24:47.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547242
;

-- 2017-08-14T18:24:47.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547218
;

-- 2017-08-14T18:24:47.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547250
;

-- 2017-08-14T18:24:47.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547251
;

-- 2017-08-14T18:24:47.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547248
;

-- 2017-08-14T18:24:47.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547249
;

-- 2017-08-14T18:24:47.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547206
;

-- 2017-08-14T18:24:47.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547207
;

-- 2017-08-14T18:24:47.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547234
;

-- 2017-08-14T18:24:47.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547235
;

-- 2017-08-14T18:24:47.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547237
;

-- 2017-08-14T18:24:47.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547212
;

-- 2017-08-14T18:24:47.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547245
;

-- 2017-08-14T18:24:47.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547213
;

-- 2017-08-14T18:24:47.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547214
;

-- 2017-08-14T18:24:47.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547215
;

-- 2017-08-14T18:24:47.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547227
;

-- 2017-08-14T18:24:47.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547228
;

-- 2017-08-14T18:24:47.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547229
;

-- 2017-08-14T18:24:47.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547230
;

-- 2017-08-14T18:24:47.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-08-14 18:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547266
;

-- 2017-08-14T18:26:53.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:26:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540407
;

-- 2017-08-14T18:30:11.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:30:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540407
;

-- 2017-08-14T18:30:26.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547248
;

-- 2017-08-14T18:30:26.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547247
;

-- 2017-08-14T18:30:27.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547251
;

-- 2017-08-14T18:30:28.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547250
;

-- 2017-08-14T18:30:28.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547249
;

-- 2017-08-14T18:30:29.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547227
;

-- 2017-08-14T18:30:29.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547225
;

-- 2017-08-14T18:30:30.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547245
;

-- 2017-08-14T18:30:30.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547244
;

-- 2017-08-14T18:30:31.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547242
;

-- 2017-08-14T18:30:31.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547239
;

-- 2017-08-14T18:30:32.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547237
;

-- 2017-08-14T18:30:32.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547235
;

-- 2017-08-14T18:30:32.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547234
;

-- 2017-08-14T18:30:33.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547233
;

-- 2017-08-14T18:30:33.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547230
;

-- 2017-08-14T18:30:34.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547229
;

-- 2017-08-14T18:30:35.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547228
;

-- 2017-08-14T18:30:38.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547226
;

-- 2017-08-14T18:30:45.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:30:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547200
;

-- 2017-08-14T18:31:10.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547200
;

-- 2017-08-14T18:31:10.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547226
;

-- 2017-08-14T18:31:11.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547228
;

-- 2017-08-14T18:31:12.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547229
;

-- 2017-08-14T18:31:12.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547230
;

-- 2017-08-14T18:31:13.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547233
;

-- 2017-08-14T18:31:13.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547234
;

-- 2017-08-14T18:31:14.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547235
;

-- 2017-08-14T18:31:15.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547237
;

-- 2017-08-14T18:31:15.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547239
;

-- 2017-08-14T18:31:16.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547242
;

-- 2017-08-14T18:31:16.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547244
;

-- 2017-08-14T18:31:18.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547245
;

-- 2017-08-14T18:31:19.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547225
;

-- 2017-08-14T18:31:20.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547227
;

-- 2017-08-14T18:31:21.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547249
;

-- 2017-08-14T18:31:21.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547250
;

-- 2017-08-14T18:31:22.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547251
;

-- 2017-08-14T18:31:22.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547247
;

-- 2017-08-14T18:31:24.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:31:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547248
;

-- 2017-08-14T18:31:24.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547197
;

-- 2017-08-14T18:31:25.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547220
;

-- 2017-08-14T18:31:26.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547219
;

-- 2017-08-14T18:31:26.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547218
;

-- 2017-08-14T18:31:27.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547217
;

-- 2017-08-14T18:31:27.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547216
;

-- 2017-08-14T18:31:28.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547215
;

-- 2017-08-14T18:31:28.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547214
;

-- 2017-08-14T18:31:28.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547213
;

-- 2017-08-14T18:31:29.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547212
;

-- 2017-08-14T18:31:29.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547211
;

-- 2017-08-14T18:31:30.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547210
;

-- 2017-08-14T18:31:30.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547209
;

-- 2017-08-14T18:31:31.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547207
;

-- 2017-08-14T18:31:31.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547206
;

-- 2017-08-14T18:31:31.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547204
;

-- 2017-08-14T18:31:32.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547203
;

-- 2017-08-14T18:31:32.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547202
;

-- 2017-08-14T18:31:34.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:31:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547199
;

-- 2017-08-14T18:32:06.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547197
;

-- 2017-08-14T18:32:06.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547220
;

-- 2017-08-14T18:32:07.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547219
;

-- 2017-08-14T18:32:07.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547218
;

-- 2017-08-14T18:32:08.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547217
;

-- 2017-08-14T18:32:08.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547216
;

-- 2017-08-14T18:32:09.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547215
;

-- 2017-08-14T18:32:09.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547214
;

-- 2017-08-14T18:32:10.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547213
;

-- 2017-08-14T18:32:12.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547212
;

-- 2017-08-14T18:32:14.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547211
;

-- 2017-08-14T18:32:50.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547210
;

-- 2017-08-14T18:32:50.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547209
;

-- 2017-08-14T18:32:51.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547207
;

-- 2017-08-14T18:32:52.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547206
;

-- 2017-08-14T18:33:17.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:33:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547206
;

-- 2017-08-14T18:33:22.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547207
;

-- 2017-08-14T18:33:23.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:33:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547209
;

-- 2017-08-14T18:33:28.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2017-08-14 18:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547210
;

-- 2017-08-14T18:33:49.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547199
;

-- 2017-08-14T18:33:49.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547202
;

-- 2017-08-14T18:33:49.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547203
;

-- 2017-08-14T18:33:51.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:33:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547204
;

-- 2017-08-14T18:34:14.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547207
;

-- 2017-08-14T18:34:16.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:34:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547206
;

-- 2017-08-14T18:34:16.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:34:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547209
;

-- 2017-08-14T18:34:18.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2017-08-14 18:34:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547210
;

-- 2017-08-14T18:37:54.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540183
;

-- 2017-08-14T18:38:19.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555179,0,540282,540964,547279,TO_TIMESTAMP('2017-08-14 18:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift Zurodnung eff.',50,0,0,TO_TIMESTAMP('2017-08-14 18:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-14T18:38:25.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Packvorschrift Zuordnung eff.',Updated=TO_TIMESTAMP('2017-08-14 18:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547279
;

-- 2017-08-14T18:41:48.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547256
;

-- 2017-08-14T18:41:48.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547257
;

-- 2017-08-14T18:41:48.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547266
;

-- 2017-08-14T18:41:48.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547268
;

-- 2017-08-14T18:41:48.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547279
;

-- 2017-08-14T18:41:48.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547269
;

-- 2017-08-14T18:41:48.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547271
;

-- 2017-08-14T18:41:48.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547273
;

-- 2017-08-14T18:41:48.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547274
;

-- 2017-08-14T18:41:48.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547259
;

-- 2017-08-14T18:41:48.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547260
;

-- 2017-08-14T18:41:48.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547261
;

-- 2017-08-14T18:41:48.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-08-14 18:41:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547262
;

-- 2017-08-14T18:42:39.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-08-14 18:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547256
;

-- 2017-08-14T18:42:39.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-08-14 18:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547266
;

-- 2017-08-14T18:42:39.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-08-14 18:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547268
;

-- 2017-08-14T18:42:39.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-08-14 18:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547259
;

-- 2017-08-14T18:44:50.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-08-14 18:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547266
;

-- 2017-08-14T18:44:56.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-08-14 18:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547268
;

-- 2017-08-14T18:45:02.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-14 18:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547256
;

-- 2017-08-14T18:45:12.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-14 18:45:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547269
;

-- 2017-08-14T18:45:15.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-14 18:45:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547270
;

-- 2017-08-14T18:45:18.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-14 18:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547271
;

-- 2017-08-14T18:45:22.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-14 18:45:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547272
;

-- 2017-08-14T18:45:26.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-08-14 18:45:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547273
;

-- 2017-08-14T18:45:30.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-14 18:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547274
;

-- 2017-08-14T18:45:42.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-08-14 18:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547262
;

-- 2017-08-14T18:45:45.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-08-14 18:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547263
;

