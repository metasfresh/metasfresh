-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540564,540018,TO_TIMESTAMP('2016-12-12 07:35:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2016-12-12 07:35:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540025,540018,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540025,540020,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553766,0,540564,540020,540622,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Qty Picked',0,10,0,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554952,0,540564,540020,540623,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Virtual Handling Unit (VHU)',0,20,0,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553763,0,540564,540020,540624,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Handling Unit of type Tranding Unit','Y','N','N','Y','N','Handling Unit (TU)',0,30,0,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554346,0,540564,540020,540625,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Handling Unit (Loading Unit)','Y','N','N','Y','N','Handling Unit (LU)',0,40,0,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553758,0,540564,540020,540626,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei MÃ¶glichkeiten, einen Datensatz nicht mehr verfÃ¼gbar zu machen: einer ist, ihn zu lÃ¶schen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr fÃ¼r eine Auswahl verfÃ¼gbar, aber verfÃ¼gbar fÃ¼r die Verwendung in Berichten. Es gibt zwei GrÃ¼nde, DatensÃ¤tze zu deaktivieren und nicht zu lÃ¶schen: (1) Das System braucht den Datensatz fÃ¼r Revisionszwecke. (2) Der Datensatz wird von anderen DatensÃ¤tzen referenziert. Z.B. kÃ¶nnen Sie keinen GeschÃ¤ftspartner lÃ¶schen, wenn es Rechnungen fÃ¼r diesen GeschÃ¤ftspartner gibt. Sie deaktivieren den GeschÃ¤ftspartner und verhindern, dass dieser Eintrag in zukÃ¼nftigen VorgÃ¤ngen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556130,0,540564,540020,540627,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,60,0,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554649,0,540564,540020,540628,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Position auf Versand- oder Wareneingangsbeleg','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','N','Y','N','Versand-/Wareneingangsposition',0,70,0,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554657,0,540564,540020,540629,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dÃ¼rfen in der Regel nich mehr geÃ¤ndert werden.','Y','N','N','Y','N','Verarbeitet',0,80,0,TO_TIMESTAMP('2016-12-12 07:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540621
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540016
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540016
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540012
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,500221,540019,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540026,540019,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540027,540019,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540026,540021,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547785,0,500221,540021,540630,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben fÃ¼r die Belegverarbeitung.','Y','N','Y','Y','N','Belegart',10,10,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550477,0,500221,540021,540631,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','Y','N','DB-Tabelle',20,20,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550476,0,500221,540021,540632,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','Y','N','Datensatz-ID',30,30,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500231,0,500221,540021,540633,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Order','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','Y','N','Auftrag',40,40,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500232,0,500221,540021,540634,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','Y','Y','N','Auftragsdatum',50,50,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555942,0,500221,540021,540635,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','Y','N','Referenz',60,60,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500233,0,500221,540021,540636,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','Y','N','Produkt',70,70,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546420,0,500221,540021,540637,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Produktbeschreibung','Y','N','Y','N','N','Produktbeschreibung',80,0,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554352,0,500221,540021,540638,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Instanz des Merkmals-Satzes zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','Y','N','AusprÃ¤gung Merkmals-Satz',90,80,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556206,0,500221,540021,540639,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge','Die Bestellte Menge bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','Y','N','Bestellt',100,90,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555277,0,500221,540021,540640,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Bestellt abw.','Bestellt abw.','Y','N','Y','Y','N','Bestellt abw.',110,100,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501597,0,500221,540021,540641,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge Effective','Die Bestellte Menge bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','Y','N','Bestellt eff.',120,110,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547264,0,500221,540021,540642,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Einzel-/Sonderpreis',130,0,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,70267,0,500221,540021,540643,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift Version',140,0,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,541240,0,500221,540021,540644,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort fÃ¼r Dienstleistung','Das Lager identifiziert ein einzelnes Lager fÃ¼r Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','Y','N','Lager',150,120,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,66950,0,500221,540021,540645,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort fÃ¼r Dienstleistung','Das Lager identifiziert ein einzelnes Lager fÃ¼r Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','Y','N','Lager abw.',160,130,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500234,0,500221,540021,540646,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Bestand','Bestand gibt die Menge des Produktes an, die im Lager verfÃ¼gbar ist.','Y','N','Y','Y','N','Bestand',170,140,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547689,0,500221,540021,540647,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferdatum',180,150,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553948,0,500221,540021,540648,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferdatum abw',190,160,0,TO_TIMESTAMP('2016-12-12 07:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556127,0,500221,540021,540649,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferdatum eff.',200,170,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553949,0,500221,540021,540650,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bereitstellungsdatum',210,180,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556128,0,500221,540021,540651,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bereitstellungsdatum abw.',220,190,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556129,0,500221,540021,540652,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bereitstellungsdatum eff.',230,200,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500237,0,500221,540021,540653,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Reservierte Menge','Die Reservierte Menge bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.','Y','N','Y','Y','N','Offen',240,210,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500240,0,500221,540021,540654,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Noch offener Nettowert der Zeile (offene Menge * Einzelpreis) ohne Fracht und GebÃ¼hren','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','N','Y','Y','N','Netto (offen)',250,220,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554689,0,500221,540021,540655,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','WÃ¤hrung',260,230,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546686,0,500221,540021,540656,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Auf Packzettel',270,240,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546827,0,500221,540021,540657,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','Y','Y','N','Geliefert',280,250,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500238,0,500221,540021,540658,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Liefermenge',290,260,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554688,0,500221,540021,540659,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','MaÃŸeinheit',300,270,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500235,0,500221,540021,540660,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Priority of a document','The Priority indicates the importance (high, medium, low) of this document','Y','N','Y','Y','N','PrioritÃ¤t',310,280,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542110,0,500221,540021,540661,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','N','Y','Y','N','Lieferart',320,290,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500236,0,500221,540021,540662,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','PrioritÃ¤t Abw.',330,300,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542111,0,500221,540021,540663,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferart abw.',340,310,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542496,0,500221,540021,540664,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','N','Y','Y','N','Lieferung',350,320,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547784,0,500221,540021,540665,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferung durch abw.',360,330,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500230,0,500221,540021,540666,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen GeschÃ¤ftspartner','Ein GeschÃ¤ftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','GeschÃ¤ftspartner',370,340,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,542497,0,540033,540666,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,547780,0,540034,540666,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547782,0,500221,540021,540667,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen GeschÃ¤ftspartner','Ein GeschÃ¤ftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','GeschÃ¤ftspartner abw.',380,350,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547783,0,500221,540021,540668,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des GeschÃ¤ftspartners','Identifiziert die Adresse des GeschÃ¤ftspartners','Y','N','Y','Y','N','Lieferadresse abw.',390,360,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547781,0,500221,540021,540669,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ansprechpartner abw.',400,370,0,TO_TIMESTAMP('2016-12-12 07:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555405,0,500221,540021,540670,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Beim StreckengeschÃ¤ft wird die Ware direkt vom Lieferanten zum Kunden geliefert','Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.','Y','N','Y','Y','N','StreckengeschÃ¤ft',410,380,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555406,0,500221,540021,540671,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','C_BPartner_Vendor_ID',420,390,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547778,0,500221,540021,540672,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Anschrift-Text',430,400,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547779,0,500221,540021,540673,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet die letztendlich verwendete Lieferanschrift','Y','N','Y','Y','N','Verwendeter Adress-Text',440,410,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,549449,0,500221,540021,540674,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ziel-Lager',450,420,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546693,0,500221,540021,540675,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Wert wird bei einer Benutzer-Ã„nderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.','Beim nÃ¤chsten Lauf des Aktualisierungsprozesses wird der so markierten Rechnungskandidat aktualisiert und der Wert durch den Prozess danach auf "nein". gesetzt.','Y','N','Y','Y','N','zu Akt.',460,430,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501596,0,500221,540021,540676,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Status-Info',470,440,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546826,0,500221,540021,540677,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Teilmenge von "Abw. Liefermenge", die bereits Ausgeliefert wurde','Y','N','Y','Y','N','Erl. Liefermenge abw.',480,450,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554353,0,500221,540021,540678,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Kopf-Aggregationsmerkmal',490,460,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546695,0,500221,540021,540679,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Kommisionierer',500,470,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546690,0,500221,540021,540680,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferlauf-ID',510,480,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546689,0,500221,540021,540681,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferlauf beendet',520,490,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555864,0,500221,540021,540682,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','in Verarbeitung',530,500,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550490,0,500221,540021,540683,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dð²¦¥n in der Regel nich mehr geå¯¤ert werden.','Y','N','Y','Y','N','Verarbeitet',540,510,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,553027,0,540035,540636,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554350,0,500221,540021,540684,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Packvorschrift-Produkt Zuordnung abw.',550,520,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553028,0,500221,540021,540685,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','VerpackungskapazitÃ¤t',560,530,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553029,0,500221,540021,540686,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Packbeschreibung',570,540,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554651,0,500221,540021,540687,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge (LU)','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','Y','N','Bestellte Menge (LU)',580,550,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554655,0,500221,540021,540688,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ausliefermenge (LU)',590,560,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554654,0,500221,540021,540689,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge (LU)','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','Y','Y','N','Gelieferte Menge (LU)',600,570,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554650,0,500221,540021,540690,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge (TU)','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','Y','N','Bestellte Menge (TU)',610,580,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555273,0,500221,540021,540691,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Menge TU (ausgewiesen)',620,590,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554652,0,500221,540021,540692,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ausliefermenge (TU)',630,600,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554653,0,500221,540021,540693,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge (TU)','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','Y','Y','N','Gelieferte Menge (TU)',640,610,0,TO_TIMESTAMP('2016-12-12 07:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540672
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540669
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540656
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540688
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540692
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540650
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540651
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540639
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540640
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540687
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540690
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540671
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540631
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540632
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540677
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540689
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540693
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540666
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540667
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540679
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540678
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540645
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540668
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540661
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540663
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540648
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540681
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540680
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540664
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540665
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540691
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540654
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540686
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540684
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540660
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540662
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540676
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540670
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540683
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540685
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540673
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540655
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540674
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540682
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540675
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540633
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540635
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540636
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540638
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540641
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540658
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540653
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540657
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540646
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540659
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540634
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540649
;

-- 12.12.2016 07:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2016-12-12 07:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540652
;

-- 12.12.2016 07:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2016-12-12 07:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540666
;

-- 12.12.2016 07:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2016-12-12 07:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540633
;

-- 12.12.2016 07:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2016-12-12 07:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540636
;

-- 12.12.2016 07:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2016-12-12 07:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540659
;

