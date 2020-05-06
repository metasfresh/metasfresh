-- 2017-07-24T07:51:48.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Mandant/ Organisation ',Updated=TO_TIMESTAMP('2017-07-24 07:51:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540833
;

-- 2017-07-24T07:51:53.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Mandant/ Organisation',Updated=TO_TIMESTAMP('2017-07-24 07:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540833
;

-- 2017-07-24T07:53:28.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540508,540394,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-24T07:53:28.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540394 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-24T07:53:28.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540531,540394,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540532,540394,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540531,540910,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552133,0,540508,540910,547017,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552134,0,540508,540910,547018,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552132,0,540508,540910,547019,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Packvorschrift Version',30,30,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552131,0,540508,540910,547020,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Handling Units Item Parent ID',40,40,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552135,0,540508,540910,547021,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',50,50,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553621,0,540508,540910,547022,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','Y','Y','N','Lagerort',60,60,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554205,0,540508,540910,547023,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',70,70,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554345,0,540171,547023,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554539,0,540508,540910,547024,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gebindekonfiguration',80,80,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553930,0,540508,540910,547025,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gebinde Status',90,90,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554344,0,540508,540910,547026,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gesperrt',100,100,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555394,0,540508,540910,547027,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Sagt aus, ob die gegenbene HU in einer anderen HU enthalten ist (z.B. IFCO auf einer Palette)','Y','N','Y','Y','N','in HU enthalten',110,110,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555396,0,540508,540910,547028,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes

','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','Y','N','Produkt-Kategorie',120,120,0,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:28.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540510,540395,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-24T07:53:28.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540395 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-24T07:53:29.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540533,540395,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540533,540911,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552168,0,540510,540911,547029,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552170,0,540510,540911,547030,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552167,0,540510,540911,547031,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Packvorschrift Position',0,30,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557504,0,540510,540911,547032,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Positionsart',0,40,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552169,0,540510,540911,547033,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','Menge',0,50,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557505,0,540510,540911,547034,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Packmittel',0,60,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552160,0,540510,540911,547035,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,70,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540512,540396,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-24T07:53:29.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540396 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-24T07:53:29.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540534,540396,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540534,540912,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552192,0,540512,540912,547036,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552194,0,540512,540912,547037,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552191,0,540512,540912,547038,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Packvorschrift',0,30,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552193,0,540512,540912,547039,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Merkmal','Product Attribute like Color, Size','Y','N','N','Y','N','Merkmal',0,40,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554052,0,540512,540912,547040,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Initial Value of the Attribute','Y','N','N','Y','N','Merkmals-Wert (initial)',0,50,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552195,0,540512,540912,547041,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Suchschlüssel',0,60,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554053,0,540512,540912,547042,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Initial Numeric Value','Y','N','N','Y','N','Zahlwert (initial)',0,70,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552196,0,540512,540912,547043,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Numeric Value','Y','N','N','Y','N','Zahlwert',0,80,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556344,0,540512,540912,547044,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Datum (initial)',0,90,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556343,0,540512,540912,547045,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Datum',0,100,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552184,0,540512,540912,547046,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,110,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540513,540397,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-24T07:53:29.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540397 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-24T07:53:29.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540535,540397,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:29.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540535,540913,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552205,0,540513,540913,547047,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-07-24 07:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552209,0,540513,540913,547048,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552200,0,540513,540913,547049,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','Merkmale',0,30,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552208,0,540513,540913,547050,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,40,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552207,0,540513,540913,547051,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','Menge',0,50,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552206,0,540513,540913,547052,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','Maßeinheit',0,60,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552197,0,540513,540913,547053,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,70,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540579,540398,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-07-24T07:53:30.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540398 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-07-24T07:53:30.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540536,540398,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540536,540914,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554006,0,540579,540914,547054,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','DB-Tabelle',0,10,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554007,0,540579,540914,547055,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','Datensatz-ID',0,20,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554005,0,540579,540914,547056,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Handling Units',0,30,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555172,0,540579,540914,547057,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Handling Unit (Loading Unit)','Y','N','N','Y','N','Handling Unit (LU)',0,40,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555173,0,540579,540914,547058,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Handling Unit of type Tranding Unit','Y','N','N','Y','N','Handling Unit (TU)',0,50,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555857,0,540579,540914,547059,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Produkte',0,60,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T07:53:30.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555171,0,540579,540914,547060,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100,'Shall we transfer packing materials along with the HU','Y','N','N','Y','N','Transfer Packing Materials',0,70,0,TO_TIMESTAMP('2017-07-24 07:53:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:03:13.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540531,540915,TO_TIMESTAMP('2017-07-24 08:03:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-07-24 08:03:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:03:26.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=90, UIStyle='',Updated=TO_TIMESTAMP('2017-07-24 08:03:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540910
;

-- 2017-07-24T08:03:57.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540532,540916,TO_TIMESTAMP('2017-07-24 08:03:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',10,TO_TIMESTAMP('2017-07-24 08:03:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:04:09.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552134,0,540508,540916,547061,TO_TIMESTAMP('2017-07-24 08:04:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-07-24 08:04:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:04:18.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552133,0,540508,540916,547062,TO_TIMESTAMP('2017-07-24 08:04:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-07-24 08:04:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:04:35.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547017
;

-- 2017-07-24T08:04:35.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547018
;

-- 2017-07-24T08:05:09.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,UIStyle,Updated,UpdatedBy) VALUES (0,552135,0,540508,540915,547063,TO_TIMESTAMP('2017-07-24 08:05:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Suchschlüssel',10,0,0,'small',TO_TIMESTAMP('2017-07-24 08:05:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:06:29.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,UIStyle,Updated,UpdatedBy) VALUES (0,554539,0,540508,540915,547064,TO_TIMESTAMP('2017-07-24 08:06:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gebindekonfiguration',20,0,0,'large',TO_TIMESTAMP('2017-07-24 08:06:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:08:36.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554205,0,540508,540915,547065,TO_TIMESTAMP('2017-07-24 08:08:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschäftspartner',30,0,0,TO_TIMESTAMP('2017-07-24 08:08:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:08:47.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553621,0,540508,540915,547066,TO_TIMESTAMP('2017-07-24 08:08:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lagerort',40,0,0,TO_TIMESTAMP('2017-07-24 08:08:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:09:03.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553930,0,540508,540915,547067,TO_TIMESTAMP('2017-07-24 08:09:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gebinde Status',50,0,0,TO_TIMESTAMP('2017-07-24 08:09:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:09:21.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540531,540917,TO_TIMESTAMP('2017-07-24 08:09:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','packing',20,TO_TIMESTAMP('2017-07-24 08:09:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:09:35.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552132,0,540508,540917,547068,TO_TIMESTAMP('2017-07-24 08:09:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift',10,0,0,TO_TIMESTAMP('2017-07-24 08:09:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:10:00.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552131,0,540508,540917,547069,TO_TIMESTAMP('2017-07-24 08:09:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Handling Unit Eltern',20,0,0,TO_TIMESTAMP('2017-07-24 08:09:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:10:20.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Gehört zu HU',Updated=TO_TIMESTAMP('2017-07-24 08:10:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547069
;

-- 2017-07-24T08:10:56.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540532,540918,TO_TIMESTAMP('2017-07-24 08:10:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',5,TO_TIMESTAMP('2017-07-24 08:10:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:11:10.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552126,0,540508,540918,547070,TO_TIMESTAMP('2017-07-24 08:11:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-07-24 08:11:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:11:23.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555394,0,540508,540918,547071,TO_TIMESTAMP('2017-07-24 08:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','In HU enthalten',20,0,0,TO_TIMESTAMP('2017-07-24 08:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:11:36.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554344,0,540508,540918,547072,TO_TIMESTAMP('2017-07-24 08:11:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gesperrt',30,0,0,TO_TIMESTAMP('2017-07-24 08:11:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:13:31.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET UIStyle='',Updated=TO_TIMESTAMP('2017-07-24 08:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547063
;

-- 2017-07-24T08:13:33.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET UIStyle='',Updated=TO_TIMESTAMP('2017-07-24 08:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547064
;

-- 2017-07-24T08:13:37.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-07-24 08:13:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547063
;

-- 2017-07-24T08:13:40.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-07-24 08:13:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547064
;

-- 2017-07-24T08:13:54.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-07-24 08:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547061
;

-- 2017-07-24T08:13:57.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-07-24 08:13:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547062
;

-- 2017-07-24T08:16:24.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-24 08:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540918
;

-- 2017-07-24T08:16:28.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 08:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540916
;

-- 2017-07-24T08:16:36.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540532,540919,TO_TIMESTAMP('2017-07-24 08:16:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','category',20,TO_TIMESTAMP('2017-07-24 08:16:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:16:51.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555396,0,540508,540919,547073,TO_TIMESTAMP('2017-07-24 08:16:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produkt Kategorie',10,0,0,TO_TIMESTAMP('2017-07-24 08:16:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:17:39.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547019
;

-- 2017-07-24T08:17:39.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547020
;

-- 2017-07-24T08:17:39.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547021
;

-- 2017-07-24T08:17:39.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547022
;

-- 2017-07-24T08:17:42.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547024
;

-- 2017-07-24T08:17:42.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547025
;

-- 2017-07-24T08:17:42.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547026
;

-- 2017-07-24T08:17:42.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547027
;

-- 2017-07-24T08:17:42.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547028
;

-- 2017-07-24T08:18:03.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554345,0,540172,547065,TO_TIMESTAMP('2017-07-24 08:18:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-07-24 08:18:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T08:18:14.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540171
;

-- 2017-07-24T08:18:17.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547023
;

-- 2017-07-24T08:18:20.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540910
;

-- 2017-07-24T08:34:56.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547063
;

-- 2017-07-24T08:34:56.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547064
;

-- 2017-07-24T08:34:56.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547065
;

-- 2017-07-24T08:34:56.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547066
;

-- 2017-07-24T08:34:56.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547067
;

-- 2017-07-24T08:34:56.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547068
;

-- 2017-07-24T08:34:56.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547069
;

-- 2017-07-24T08:34:56.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547070
;

-- 2017-07-24T08:34:56.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547071
;

-- 2017-07-24T08:34:56.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547072
;

-- 2017-07-24T08:34:56.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547073
;

-- 2017-07-24T08:34:56.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547061
;

-- 2017-07-24T08:34:56.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-07-24 08:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547062
;

-- 2017-07-24T08:36:14.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-07-24 08:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547063
;

-- 2017-07-24T08:36:14.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-07-24 08:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547064
;

-- 2017-07-24T08:36:14.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-07-24 08:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547065
;

-- 2017-07-24T08:36:14.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-07-24 08:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547067
;

-- 2017-07-24T08:43:08.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=70,Updated=TO_TIMESTAMP('2017-07-24 08:43:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540508
;

-- 2017-07-24T08:49:19.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-24 08:49:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540510
;

-- 2017-07-24T08:49:22.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-24 08:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540511
;

-- 2017-07-24T08:49:25.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 08:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540512
;

-- 2017-07-24T08:49:27.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-24 08:49:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540513
;

-- 2017-07-24T08:49:29.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=50,Updated=TO_TIMESTAMP('2017-07-24 08:49:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540579
;

-- 2017-07-24T08:49:52.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-24 08:49:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540508
;

-- 2017-07-24T08:50:14.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=60,Updated=TO_TIMESTAMP('2017-07-24 08:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540510
;

-- 2017-07-24T08:50:55.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 08:50:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540513
;

-- 2017-07-24T08:50:58.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-24 08:50:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540579
;

-- 2017-07-24T08:51:02.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=50,Updated=TO_TIMESTAMP('2017-07-24 08:51:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540510
;

-- 2017-07-24T08:51:24.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-24 08:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540513
;

-- 2017-07-24T08:52:24.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-24 08:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547050
;

-- 2017-07-24T08:52:24.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-24 08:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547049
;

-- 2017-07-24T08:52:24.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-07-24 08:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547051
;

-- 2017-07-24T08:52:24.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-07-24 08:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547052
;

-- 2017-07-24T08:52:24.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-07-24 08:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547053
;

-- 2017-07-24T08:52:24.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-07-24 08:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547048
;

-- 2017-07-24T08:52:24.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-07-24 08:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547047
;

-- 2017-07-24T08:52:40.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-07-24 08:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547050
;

-- 2017-07-24T08:52:40.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-07-24 08:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547051
;

-- 2017-07-24T08:52:40.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-07-24 08:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547052
;

-- 2017-07-24T08:52:40.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-07-24 08:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547053
;

-- 2017-07-24T08:53:25.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-24 08:53:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547050
;

-- 2017-07-24T08:53:34.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 08:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547051
;

-- 2017-07-24T08:53:37.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-24 08:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547049
;

-- 2017-07-24T08:53:40.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-07-24 08:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547053
;

-- 2017-07-24T08:53:43.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-07-24 08:53:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547048
;

-- 2017-07-24T08:53:45.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-24 08:53:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547052
;

-- 2017-07-24T08:53:48.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-07-24 08:53:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547047
;

-- 2017-07-24T08:54:17.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-24 08:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547047
;

-- 2017-07-24T08:54:34.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-24 08:54:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547062
;

-- 2017-07-24T08:56:04.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547036
;

-- 2017-07-24T08:56:04.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547038
;

-- 2017-07-24T08:56:04.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547039
;

-- 2017-07-24T08:56:04.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547040
;

-- 2017-07-24T08:56:04.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547041
;

-- 2017-07-24T08:56:04.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547042
;

-- 2017-07-24T08:56:04.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547043
;

-- 2017-07-24T08:56:04.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547044
;

-- 2017-07-24T08:56:04.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547045
;

-- 2017-07-24T08:56:04.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547046
;

-- 2017-07-24T08:56:04.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-07-24 08:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547037
;

-- 2017-07-24T08:56:18.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-07-24 08:56:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547039
;

-- 2017-07-24T08:56:18.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-07-24 08:56:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547041
;

-- 2017-07-24T08:56:18.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-07-24 08:56:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547043
;

-- 2017-07-24T08:56:18.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-07-24 08:56:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547045
;

-- 2017-07-24T08:56:26.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2017-07-24 08:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547046
;

-- 2017-07-24T09:18:00.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-24 09:18:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547054
;

-- 2017-07-24T09:18:02.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-07-24 09:18:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547055
;

-- 2017-07-24T09:18:05.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-24 09:18:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547056
;

-- 2017-07-24T09:18:07.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-07-24 09:18:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547057
;

-- 2017-07-24T09:18:10.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-07-24 09:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547058
;

-- 2017-07-24T09:18:12.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-07-24 09:18:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547059
;

-- 2017-07-24T09:18:28.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-07-24 09:18:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547060
;

-- 2017-07-24T09:18:45.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554002,0,540579,540914,547074,TO_TIMESTAMP('2017-07-24 09:18:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',80,0,0,TO_TIMESTAMP('2017-07-24 09:18:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T09:18:55.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554001,0,540579,540914,547075,TO_TIMESTAMP('2017-07-24 09:18:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',90,0,0,TO_TIMESTAMP('2017-07-24 09:18:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-24T09:19:12.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-07-24 09:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547074
;

-- 2017-07-24T09:19:13.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-07-24 09:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547075
;

-- 2017-07-24T09:19:20.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-07-24 09:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547054
;

-- 2017-07-24T09:19:20.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-07-24 09:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547055
;

-- 2017-07-24T09:19:20.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-07-24 09:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547056
;

-- 2017-07-24T09:19:21.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-07-24 09:19:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547057
;

-- 2017-07-24T09:19:21.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-07-24 09:19:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547058
;

-- 2017-07-24T09:19:22.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-07-24 09:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547059
;

-- 2017-07-24T09:19:22.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-07-24 09:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547060
;

-- 2017-07-24T09:19:24.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-07-24 09:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547074
;

-- 2017-07-24T09:19:25.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-07-24 09:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547075
;

-- 2017-07-24T09:19:31.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-07-24 09:19:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547074
;

-- 2017-07-24T09:21:16.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-07-24 09:21:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547029
;

-- 2017-07-24T09:21:16.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-24 09:21:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547031
;

-- 2017-07-24T09:21:16.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-24 09:21:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547035
;

-- 2017-07-24T09:21:16.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-07-24 09:21:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547033
;

-- 2017-07-24T09:21:16.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-07-24 09:21:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547034
;

-- 2017-07-24T09:21:16.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-07-24 09:21:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547030
;

-- 2017-07-24T09:21:26.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-07-24 09:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547031
;

-- 2017-07-24T09:21:26.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-07-24 09:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547035
;

-- 2017-07-24T09:21:26.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-07-24 09:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547033
;

-- 2017-07-24T09:21:26.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-07-24 09:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547034
;

-- 2017-07-24T09:27:10.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:27:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Value',Description='Value' WHERE AD_Field_ID=552135 AND AD_Language='en_US'
;

-- 2017-07-24T09:28:23.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:28:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Packing Configuration',PrintName='Packing Configuration' WHERE AD_Element_ID=542486 AND AD_Language='en_US'
;

-- 2017-07-24T09:28:23.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542486,'en_US') 
;

-- 2017-07-24T09:29:15.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:29:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Business Partner',Description='Business Partner',Help='' WHERE AD_Field_ID=554205 AND AD_Language='en_US'
;

-- 2017-07-24T09:29:59.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:29:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Locator',Description='Locator',Help='' WHERE AD_Field_ID=553621 AND AD_Language='en_US'
;

-- 2017-07-24T09:54:22.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:54:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Packing Status' WHERE AD_Field_ID=553930 AND AD_Language='en_US'
;

-- 2017-07-24T09:55:07.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:55:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Packing Instruktion' WHERE AD_Field_ID=552132 AND AD_Language='en_US'
;

-- 2017-07-24T09:55:21.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:55:21','YYYY-MM-DD HH24:MI:SS'),Name='Packing Instruction' WHERE AD_Field_ID=552132 AND AD_Language='en_US'
;

-- 2017-07-24T09:55:56.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:55:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Handling Unit Parent' WHERE AD_Field_ID=552131 AND AD_Language='en_US'
;

-- 2017-07-24T09:56:27.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:56:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Included in other HU',Description='' WHERE AD_Field_ID=555394 AND AD_Language='en_US'
;

-- 2017-07-24T09:56:51.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:56:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Locked' WHERE AD_Field_ID=554344 AND AD_Language='en_US'
;

-- 2017-07-24T09:57:16.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:57:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product Category',Description='Product Category',Help='' WHERE AD_Field_ID=555396 AND AD_Language='en_US'
;

-- 2017-07-24T09:58:19.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:58:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Handling Unit Element Warehouse' WHERE AD_Tab_ID=540511 AND AD_Language='en_US'
;

-- 2017-07-24T09:58:49.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:58:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Handling Units Storage' WHERE AD_Tab_ID=540513 AND AD_Language='en_US'
;

-- 2017-07-24T09:59:14.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:59:14','YYYY-MM-DD HH24:MI:SS'),Name='Handling Unit Element Storage' WHERE AD_Tab_ID=540511 AND AD_Language='en_US'
;

-- 2017-07-24T09:59:30.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:59:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Handling Unit Attributes' WHERE AD_Tab_ID=540512 AND AD_Language='en_US'
;

-- 2017-07-24T09:59:42.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 09:59:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Tab_ID=540579 AND AD_Language='en_US'
;

-- 2017-07-24T09:59:58.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Handling Unit Referenzen',Updated=TO_TIMESTAMP('2017-07-24 09:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540579
;

-- 2017-07-24T10:00:11.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:00:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Tab_ID=540510 AND AD_Language='en_US'
;

-- 2017-07-24T10:01:20.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:01:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product',Description='',Help='' WHERE AD_Field_ID=552182 AND AD_Language='en_US'
;

-- 2017-07-24T10:01:48.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:01:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Attribute Set Instance',Description='',Help='' WHERE AD_Field_ID=552174 AND AD_Language='en_US'
;

-- 2017-07-24T10:02:11.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:02:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quantity',Description='',Help='' WHERE AD_Field_ID=552181 AND AD_Language='en_US'
;

-- 2017-07-24T10:02:24.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:02:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='UOM',Description='',Help='' WHERE AD_Field_ID=552179 AND AD_Language='en_US'
;

-- 2017-07-24T10:03:31.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:03:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Attribute Set Instance',Description='',Help='' WHERE AD_Field_ID=552200 AND AD_Language='en_US'
;

-- 2017-07-24T10:03:41.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:03:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product',Description='',Help='' WHERE AD_Field_ID=552208 AND AD_Language='en_US'
;

-- 2017-07-24T10:03:56.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:03:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quantity',Description='',Help='' WHERE AD_Field_ID=552207 AND AD_Language='en_US'
;

-- 2017-07-24T10:04:10.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:04:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='UOM',Description='UOM',Help='' WHERE AD_Field_ID=552206 AND AD_Language='en_US'
;

-- 2017-07-24T10:05:06.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:05:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Packing Instruction' WHERE AD_Field_ID=552191 AND AD_Language='en_US'
;

-- 2017-07-24T10:05:51.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:05:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Attribute',Description='',Help='' WHERE AD_Field_ID=552193 AND AD_Language='en_US'
;

-- 2017-07-24T10:06:18.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:06:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Initial Value' WHERE AD_Field_ID=554052 AND AD_Language='en_US'
;

-- 2017-07-24T10:06:38.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:06:38','YYYY-MM-DD HH24:MI:SS'),Name='Initial Numeric Value' WHERE AD_Field_ID=554053 AND AD_Language='en_US'
;

-- 2017-07-24T10:06:50.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:06:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='initial Date Value' WHERE AD_Field_ID=556344 AND AD_Language='en_US'
;

-- 2017-07-24T10:07:01.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:07:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Value',Description='' WHERE AD_Field_ID=552195 AND AD_Language='en_US'
;

-- 2017-07-24T10:07:13.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:07:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Numeric Value' WHERE AD_Field_ID=552196 AND AD_Language='en_US'
;

-- 2017-07-24T10:07:22.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:07:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Date Value' WHERE AD_Field_ID=556343 AND AD_Language='en_US'
;

-- 2017-07-24T10:09:10.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:09:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Products' WHERE AD_Field_ID=555857 AND AD_Language='en_US'
;

-- 2017-07-24T10:09:57.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:09:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Packing Item' WHERE AD_Field_ID=552167 AND AD_Language='en_US'
;

-- 2017-07-24T10:10:22.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:10:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Item Type' WHERE AD_Field_ID=557504 AND AD_Language='en_US'
;

-- 2017-07-24T10:10:48.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:10:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Quantity',Description='Quantity',Help='' WHERE AD_Field_ID=552169 AND AD_Language='en_US'
;

-- 2017-07-24T10:11:07.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-24 10:11:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Packing Material' WHERE AD_Field_ID=557505 AND AD_Language='en_US'
;

