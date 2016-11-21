-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,500221,540012,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540016,540012,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540017,540012,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540016,540010,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547785,0,500221,540010,540449,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','Y','N','Belegart',10,10,0,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550477,0,500221,540010,540450,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','Y','N','DB-Tabelle',20,20,0,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550476,0,500221,540010,540451,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','Y','N','Datensatz-ID',30,30,0,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500231,0,500221,540010,540452,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Order','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','Y','N','Auftrag',40,40,0,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500232,0,500221,540010,540453,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','Y','Y','N','Auftragsdatum',50,50,0,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555942,0,500221,540010,540454,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','Y','N','Referenz',60,60,0,TO_TIMESTAMP('2016-10-18 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500233,0,500221,540010,540455,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','Y','N','Produkt',70,70,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546420,0,500221,540010,540456,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Produktbeschreibung','Y','N','Y','N','N','Produktbeschreibung',80,0,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554352,0,500221,540010,540457,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Instanz des Merkmals-Satzes zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','Y','N','Ausprägung Merkmals-Satz',90,80,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556206,0,500221,540010,540458,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge','Die Bestellte Menge bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','Y','N','Bestellt',100,90,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555277,0,500221,540010,540459,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Bestellt abw.','Bestellt abw.','Y','N','Y','Y','N','Bestellt abw.',110,100,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501597,0,500221,540010,540460,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge Effective','Die Bestellte Menge bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','Y','N','Bestellt eff.',120,110,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547264,0,500221,540010,540461,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Einzel-/Sonderpreis',130,0,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,70267,0,500221,540010,540462,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Packvorschrift Version',140,0,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,541240,0,500221,540010,540463,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','Y','N','Lager',150,120,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,66950,0,500221,540010,540464,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','Y','N','Lager abw.',160,130,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500234,0,500221,540010,540465,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Bestand','Bestand gibt die Menge des Produktes an, die im Lager verfügbar ist.','Y','N','Y','Y','N','Bestand',170,140,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547689,0,500221,540010,540466,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferdatum',180,150,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553948,0,500221,540010,540467,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferdatum abw',190,160,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556127,0,500221,540010,540468,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferdatum eff.',200,170,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553949,0,500221,540010,540469,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bereitstellungsdatum',210,180,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556128,0,500221,540010,540470,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bereitstellungsdatum abw.',220,190,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556129,0,500221,540010,540471,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bereitstellungsdatum eff.',230,200,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500237,0,500221,540010,540472,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Reservierte Menge','Die Reservierte Menge bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.','Y','N','Y','Y','N','Offen',240,210,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500240,0,500221,540010,540473,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Noch offener Nettowert der Zeile (offene Menge * Einzelpreis) ohne Fracht und Gebühren','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','N','Y','Y','N','Netto (offen)',250,220,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554689,0,500221,540010,540474,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Währung',260,230,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546686,0,500221,540010,540475,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Auf Packzettel',270,240,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546827,0,500221,540010,540476,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','Y','Y','N','Geliefert',280,250,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500238,0,500221,540010,540477,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Liefermenge',290,260,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554688,0,500221,540010,540478,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Maßeinheit',300,270,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500235,0,500221,540010,540479,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Priority of a document','The Priority indicates the importance (high, medium, low) of this document','Y','N','Y','Y','N','Priorität',310,280,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542110,0,500221,540010,540480,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','N','Y','Y','N','Lieferart',320,290,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500236,0,500221,540010,540481,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Priorität Abw.',330,300,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542111,0,500221,540010,540482,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferart abw.',340,310,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542496,0,500221,540010,540483,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','N','Y','Y','N','Lieferung',350,320,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547784,0,500221,540010,540484,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferung durch abw.',360,330,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,500230,0,500221,540010,540485,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',370,340,0,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,542497,0,540019,540485,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2016-10-18 15:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,547780,0,540020,540485,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547782,0,500221,540010,540486,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner abw.',380,350,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547783,0,500221,540010,540487,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','Y','Y','N','Lieferadresse abw.',390,360,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547781,0,500221,540010,540488,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ansprechpartner abw.',400,370,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555405,0,500221,540010,540489,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert','Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.','Y','N','Y','Y','N','Streckengeschäft',410,380,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555406,0,500221,540010,540490,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','C_BPartner_Vendor_ID',420,390,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547778,0,500221,540010,540491,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Anschrift-Text',430,400,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547779,0,500221,540010,540492,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet die letztendlich verwendete Lieferanschrift','Y','N','Y','Y','N','Verwendeter Adress-Text',440,410,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,549449,0,500221,540010,540493,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ziel-Lager',450,420,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546693,0,500221,540010,540494,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.','Beim nächsten Lauf des Aktualisierungsprozesses wird der so markierten Rechnungskandidat aktualisiert und der Wert durch den Prozess danach auf "nein". gesetzt.','Y','N','Y','Y','N','zu Akt.',460,430,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501596,0,500221,540010,540495,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Status-Info',470,440,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546826,0,500221,540010,540496,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Teilmenge von "Abw. Liefermenge", die bereits Ausgeliefert wurde','Y','N','Y','Y','N','Erl. Liefermenge abw.',480,450,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554353,0,500221,540010,540497,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Kopf-Aggregationsmerkmal',490,460,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546695,0,500221,540010,540498,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Kommisionierer',500,470,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546690,0,500221,540010,540499,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferlauf-ID',510,480,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546689,0,500221,540010,540500,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferlauf beendet',520,490,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555864,0,500221,540010,540501,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','in Verarbeitung',530,500,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550490,0,500221,540010,540502,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege d𲦥n in der Regel nich mehr ge寤ert werden.','Y','N','Y','Y','N','Verarbeitet',540,510,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,553027,0,540021,540455,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554350,0,500221,540010,540503,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Packvorschrift-Produkt Zuordnung abw.',550,520,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553028,0,500221,540010,540504,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Verpackungskapazität',560,530,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553029,0,500221,540010,540505,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Packbeschreibung',570,540,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554651,0,500221,540010,540506,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge (LU)','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','Y','N','Bestellte Menge (LU)',580,550,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554655,0,500221,540010,540507,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ausliefermenge (LU)',590,560,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554654,0,500221,540010,540508,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge (LU)','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','Y','Y','N','Gelieferte Menge (LU)',600,570,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554650,0,500221,540010,540509,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge (TU)','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','Y','N','Bestellte Menge (TU)',610,580,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555273,0,500221,540010,540510,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Menge TU (ausgewiesen)',620,590,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554652,0,500221,540010,540511,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ausliefermenge (TU)',630,600,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554653,0,500221,540010,540512,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge (TU)','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','Y','Y','N','Gelieferte Menge (TU)',640,610,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540564,540013,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540018,540013,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540018,540011,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553766,0,540564,540011,540513,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Qty Picked',0,10,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554952,0,540564,540011,540514,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Virtual Handling Unit (VHU)',0,20,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553763,0,540564,540011,540515,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Handling Unit of type Tranding Unit','Y','N','N','Y','N','Handling Unit (TU)',0,30,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554346,0,540564,540011,540516,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Handling Unit (Loading Unit)','Y','N','N','Y','N','Handling Unit (LU)',0,40,0,TO_TIMESTAMP('2016-10-18 15:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553758,0,540564,540011,540517,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556130,0,540564,540011,540518,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,60,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554649,0,540564,540011,540519,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Position auf Versand- oder Wareneingangsbeleg','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','N','Y','N','Versand-/Wareneingangsposition',0,70,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554657,0,540564,540011,540520,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','Verarbeitet',0,80,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540623,540014,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540019,540014,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540019,540012,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554879,0,540623,540012,540521,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554880,0,540623,540012,540522,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554883,0,540623,540012,540523,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Handling Units',0,30,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554886,0,540623,540012,540524,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','Menge',0,40,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555858,0,540623,540012,540525,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Produkte',0,50,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.10.2016 15:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554881,0,540623,540012,540526,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,60,0,TO_TIMESTAMP('2016-10-18 15:04:49','YYYY-MM-DD HH24:MI:SS'),100)
;

