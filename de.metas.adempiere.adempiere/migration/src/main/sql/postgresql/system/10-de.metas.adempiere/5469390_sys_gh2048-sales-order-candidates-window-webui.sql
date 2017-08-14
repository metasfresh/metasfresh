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

