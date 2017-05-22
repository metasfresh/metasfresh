-- 2017-05-22T20:34:18.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,322,540207,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540290,540207,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540291,540207,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540290,540477,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3903,0,322,540477,544618,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3904,0,322,540477,544619,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3910,0,322,540477,544620,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',30,30,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3909,0,322,540477,544621,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',40,40,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3906,0,322,540477,544622,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3907,0,322,540477,544623,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',60,60,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3908,0,322,540477,544624,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','Y','Y','N','Standard',70,70,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556294,0,322,540477,544625,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschäftspartner ein Kunde ist','The Customer checkbox indicates if this Business Partner is a customer.  If it is select additional fields will display which further define this customer.','Y','N','Y','Y','N','Kunde',80,80,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10259,0,322,540477,544626,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Farbe für Druck und Anzeige','Farbe für Druck und Anzeige','Y','N','Y','Y','N','Druck - Farbe',90,90,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:18.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12002,0,322,540477,544627,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Base of Priority','When deriving the Priority from Importance, the Base is "added" to the User Importance.','Y','N','Y','Y','N','Priority Base',100,100,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12001,0,322,540477,544628,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Can enter confidential information','When entering/updating Requests over the web, the user can mark his info as confidential','Y','N','Y','Y','N','Confidential Info',110,110,0,TO_TIMESTAMP('2017-05-22 20:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,505274,0,322,540477,544629,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','Y','Y','N','Preissystem',120,120,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,505275,0,322,540477,544630,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Einkaufspreissystem',130,130,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12642,0,322,540477,544631,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Schema um den prozentualen Rabatt zu berechnen','Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.','Y','N','Y','Y','N','Rabatt-Schema',140,140,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12643,0,322,540477,544632,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Schema to calculate the purchase trade discount percentage','Y','N','Y','Y','N','PO Discount Schema',150,150,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12648,0,322,540477,544633,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Credit Watch - Percent of Credit Limit when OK switches to Watch','If Adempiere maintains credit status, the status "Credit OK" is moved to "Credit Watch" if the credit available reaches the percent entered.  If not defined, 90% is used.','Y','N','Y','Y','N','Credit Watch %',160,160,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12649,0,322,540477,544634,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'PO-Invoice Match Price Tolerance in percent of the purchase price','Tolerance in Percent of matching the purchase order price to the invoice price.  The difference is posted as Invoice Price Tolerance for Standard Costing.  If defined, the PO-Invoice match must be explicitly approved, if the matching difference is greater then the tolerance.<br>
Example: if the purchase price is $100 and the tolerance is 1 (percent), the invoice price must be between $99 and 101 to be automatically approved.','Y','N','Y','Y','N','Price Match Tolerance',170,170,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13017,0,322,540477,544635,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Dunning Rules for overdue invoices','The Dunning indicates the rules and method of dunning for past due payments.','Y','N','Y','Y','N','Mahnung',180,180,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,540044,0,322,540477,544636,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Frachtkostenpauschale',190,190,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554377,0,322,540477,544637,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','Y','Y','N','Zahlungsweise',200,200,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1000006,0,322,540477,544638,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','MwSt ausweisen',210,210,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556271,0,322,540477,544639,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','IsPrintTaxSales',220,220,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555417,0,322,540477,544640,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Exclude from MRP calculation','Y','N','Y','Y','N','Exclude from MRP',230,230,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,323,540208,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540292,540208,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540292,540478,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3911,0,323,540478,544641,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3912,0,323,540478,544642,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3914,0,323,540478,544643,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','N','Y','N','Geschäftspartnergruppe',0,30,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3913,0,323,540478,544644,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','N','Y','N','Buchführungs-Schema',0,40,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3918,0,323,540478,544645,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,50,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3916,0,323,540478,544646,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Forderungen aus Lieferungen (und Leistungen)','The Customer Receivables Accounts indicates the account to be used for recording transaction for customers receivables.','Y','N','N','Y','N','Forderungen aus Lieferungen',0,60,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12355,0,323,540478,544647,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Forderungen aus Dienstleistungen','Account to post services related Accounts Receivables if you want to differentiate between Services and Product related revenue. This account is only used, if posting to service accounts is enabled in the accounting schema.','Y','N','N','Y','N','Forderungen aus Dienstleistungen',0,70,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3915,0,323,540478,544648,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Erhaltene Anzahlungen','The Customer Prepayment account indicates the account to be used for recording prepayments from a customer.','Y','N','N','Y','N','Erhaltene Anzahlungen',0,80,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3922,0,323,540478,544649,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Konto für gewährte Skonti','Indicates the account to be charged for payment discount expenses.','Y','N','N','Y','N','Gewährte Skonti',0,90,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3934,0,323,540478,544650,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Forderungsverluste','The Write Off Account identifies the account to book write off transactions to.','Y','N','N','Y','N','Forderungsverluste',0,100,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3920,0,323,540478,544651,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Konto für unfertige Erzeugnisse','The Not Invoiced Receivables account indicates the account used for recording receivables that have not yet been invoiced.','Y','N','N','Y','N','Unfertige Erzeugnisse',0,110,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3921,0,323,540478,544652,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Konto für nicht abgerechnete Einnahmen','The Not Invoiced Revenue account indicates the account used for recording revenue that has not yet been invoiced.','Y','N','N','Y','N','Nicht abgerechnete Einnahmen',0,120,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:19.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3927,0,323,540478,544653,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Konto für vorausberechnete Einnahmen','The Unearned Revenue indicates the account used for recording invoices sent for products or services not yet delivered.  It is used in revenue recognition','Y','N','N','Y','N','Vorausberechnete Einnahmen',0,130,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3930,0,323,540478,544654,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Verbindlichkeiten aus Lieferungen (und Leistungen)','The Vendor Liability account indicates the account used for recording transactions for vendor liabilities','Y','N','N','Y','N','Verbindlichkeiten aus Lieferungen',0,140,0,TO_TIMESTAMP('2017-05-22 20:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3931,0,323,540478,544655,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Verbindlichkeiten aus Dienstleistungen','The Vendor Service Liability account indicates the account to use for recording service liabilities.  It is used if you need to distinguish between Liability for products and services. This account is only used, if posting to service accounts is enabled in the accounting schema.','Y','N','N','Y','N','Verbindlichkeiten aus Dienstleistungen',0,150,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3932,0,323,540478,544656,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Konto für Geleistete Anzahlungen','The Vendor Prepayment Account indicates the account used to record prepayments from a vendor.','Y','N','N','Y','N','Geleistete Anzahlungen',0,160,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3923,0,323,540478,544657,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Konto für erhaltene Skonti','Indicates the account to be charged for payment discount revenues.','Y','N','N','Y','N','Erhaltene Skonti',0,170,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3919,0,323,540478,544658,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Account for not-invoiced Material Receipts','The Not Invoiced Receipts account indicates the account used for recording receipts for materials that have not yet been invoiced.','Y','N','N','Y','N','Not-invoiced Receipts',0,180,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3924,0,323,540478,544659,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Kopieren und überschreiben der Konten der Geschäftspartner dieser Gruppe','Wenn Sie die gegenwärtigen Standardwerte kopieren und überschreiben, müssen Sie eventuell vorherige Aktualisierungen wiederholen (z.B. Forderungskonto setzen,  ...)','Y','N','N','Y','N','Konten kopieren',0,190,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,778,540209,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540293,540209,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540293,540479,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12714,0,778,540479,544660,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12715,0,778,540479,544661,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12731,0,778,540479,544662,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','N','Y','N','Geschäftspartnergruppe',0,30,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12716,0,778,540479,544663,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','Suchschlüssel',0,40,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12717,0,778,540479,544664,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Anrede zum Druck auf Korrespondenz','Anrede, die beim Druck auf Korrespondenz verwendet werden soll.','Y','N','N','Y','N','Anrede',0,50,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12718,0,778,540479,544665,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,60,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12719,0,778,540479,544666,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung','Y','N','N','Y','N','Name 2',0,70,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12720,0,778,540479,544667,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Beschreibung',0,80,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12721,0,778,540479,544668,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,90,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12722,0,778,540479,544669,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.','Y','N','N','Y','N','Zusammenfassungseintrag',0,100,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12725,0,778,540479,544670,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Steuernummer','"Steuer-ID" gibt die offizielle Steuernummer für diese Einheit an.','Y','N','N','Y','N','Steuer-ID',0,110,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12726,0,778,540479,544671,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Steuersatz steuerbefreit','If a business partner is exempt from tax on sales, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.','Y','N','N','Y','N','steuerbefreit',0,120,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12727,0,778,540479,544672,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Dun & Bradstreet - Nummer','Für EDI verwendet - Details unter www.dnb.com/dunsno/list.htm','Y','N','N','Y','N','D-U-N-S',0,130,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12728,0,778,540479,544673,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Ihre Kunden- oder Lieferantennummer beim Geschäftspartner','Die "Referenznummer" kann auf Bestellungen und Rechnungen gedruckt werden um Ihre Dokumente beim Geschäftspartner einfacher zuordnen zu können.','Y','N','N','Y','N','Referenznummer',0,140,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12729,0,778,540479,544674,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Standard Industry Code oder sein Nachfolger NAIC - http://www.osha.gov/oshstats/sicser.html','NAICS/SIC identifiziert einen dieser Codes der für den Geschäftspartner zutrifft','Y','N','N','Y','N','NAICS/SIC',0,150,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12730,0,778,540479,544675,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Klassifizierung oder Wichtigkeit','Das "Rating" wird verwendet um Wichtigkeit zu unterscheiden.','Y','N','N','Y','N','Rating',0,160,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12733,0,778,540479,544676,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org','The URL defines an fully qualified web address like http://www.adempiere.org','Y','N','N','Y','N','URL',0,170,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12732,0,778,540479,544677,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Language for this Business Partner if Multi-Language enabled','The Language identifies the language to use for display and formatting documents. It requires, that on Client level, Multi-Lingual documents are selected and that you have created/loaded the language.','Y','N','N','Y','N','Language',0,180,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12734,0,778,540479,544678,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Kennzeichnet einen Interessenten oder Kunden','Das Selektionsfeld zeigt an, ob es sich um einen aktiven Interessenten oder Kunden handelt.','Y','N','N','Y','N','Aktiver Interessent/Kunde',0,190,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:20.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12736,0,778,540479,544679,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Erwarteter Gesamtertrag','"Möglicher Gesamtertrag" ist der voraussichtliche Ertrag in Buchführungswährung, der durch diesen Geschäftspartner generiert wird.','Y','N','N','Y','N','Möglicher Gesamtertrag',0,200,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:21.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12738,0,778,540479,544680,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Die Kosten um den Interessenten als Kunden zu gewinnen','"Akquisitionskosten" bezeichnet die Kosten, die aufgewendet wurden um den Interessenten als Kunden zu gewinnen.','Y','N','N','Y','N','Akquisitionskosten',0,210,0,TO_TIMESTAMP('2017-05-22 20:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:21.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12739,0,778,540479,544681,TO_TIMESTAMP('2017-05-22 20:34:21','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Mitarbeiter','Indicates the number of employees for this Business Partner.  This field displays only for Prospects.','Y','N','N','Y','N','Mitarbeiter',0,220,0,TO_TIMESTAMP('2017-05-22 20:34:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:21.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12740,0,778,540479,544682,TO_TIMESTAMP('2017-05-22 20:34:21','YYYY-MM-DD HH24:MI:SS'),100,'Anteil dieses Kunden in Prozent','"Anteil" zeigt den prozentualen Anteil dieses Geschäftspartners an den gelieferten Produkten an.','Y','N','N','Y','N','Anteil',0,230,0,TO_TIMESTAMP('2017-05-22 20:34:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:21.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12741,0,778,540479,544683,TO_TIMESTAMP('2017-05-22 20:34:21','YYYY-MM-DD HH24:MI:SS'),100,'Verkaufsvolumen in Tausend der Buchführungswährung','"Verkaufsvolumen" zeigt den Gesamtumfang der Verkäufe für einen Geschäftspartner an.','Y','N','N','Y','N','Verkaufsvolumen in 1.000',0,240,0,TO_TIMESTAMP('2017-05-22 20:34:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:34:21.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12742,0,778,540479,544684,TO_TIMESTAMP('2017-05-22 20:34:21','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Ersten Verkaufs','"Erster Verkauf" zeigt das Datum des ersten Verkaufs an diesen Geschäftspartner an.','Y','N','N','Y','N','Erster Verkauf',0,250,0,TO_TIMESTAMP('2017-05-22 20:34:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:37:48.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540290,540480,TO_TIMESTAMP('2017-05-22 20:37:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-22 20:37:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:38:03.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='advanced edit', SeqNo=99, UIStyle='',Updated=TO_TIMESTAMP('2017-05-22 20:38:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540477
;

-- 2017-05-22T20:38:18.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3909,0,322,540480,544685,TO_TIMESTAMP('2017-05-22 20:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-05-22 20:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:38:32.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3910,0,322,540480,544686,TO_TIMESTAMP('2017-05-22 20:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Suchschlüssel',20,0,0,TO_TIMESTAMP('2017-05-22 20:38:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:38:43.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540290,540481,TO_TIMESTAMP('2017-05-22 20:38:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-05-22 20:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:38:54.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3906,0,322,540481,544687,TO_TIMESTAMP('2017-05-22 20:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2017-05-22 20:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:39:10.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,505274,0,322,540481,544688,TO_TIMESTAMP('2017-05-22 20:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preissystem Verkauf',20,0,0,TO_TIMESTAMP('2017-05-22 20:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:39:24.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12642,0,322,540481,544689,TO_TIMESTAMP('2017-05-22 20:39:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rabatt Schema Verkauf',30,0,0,TO_TIMESTAMP('2017-05-22 20:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:39:32.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Verkauf Preissystem',Updated=TO_TIMESTAMP('2017-05-22 20:39:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544688
;

-- 2017-05-22T20:39:38.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Verkauf Rabatt Schema',Updated=TO_TIMESTAMP('2017-05-22 20:39:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544689
;

-- 2017-05-22T20:40:05.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,505275,0,322,540481,544690,TO_TIMESTAMP('2017-05-22 20:40:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Einkauf Preissystem',40,0,0,TO_TIMESTAMP('2017-05-22 20:40:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:40:27.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12643,0,322,540481,544691,TO_TIMESTAMP('2017-05-22 20:40:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Einkauf Rabatt Schema',50,0,0,TO_TIMESTAMP('2017-05-22 20:40:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:41:12.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Einkauf Rabatt Schema', PrintName='Einkauf Rabatt Schema',Updated=TO_TIMESTAMP('2017-05-22 20:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1717
;

-- 2017-05-22T20:41:12.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=1717
;

-- 2017-05-22T20:41:12.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PO_DiscountSchema_ID', Name='Einkauf Rabatt Schema', Description='Schema to calculate the purchase trade discount percentage', Help=NULL WHERE AD_Element_ID=1717
;

-- 2017-05-22T20:41:12.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PO_DiscountSchema_ID', Name='Einkauf Rabatt Schema', Description='Schema to calculate the purchase trade discount percentage', Help=NULL, AD_Element_ID=1717 WHERE UPPER(ColumnName)='PO_DISCOUNTSCHEMA_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-22T20:41:12.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PO_DiscountSchema_ID', Name='Einkauf Rabatt Schema', Description='Schema to calculate the purchase trade discount percentage', Help=NULL WHERE AD_Element_ID=1717 AND IsCentrallyMaintained='Y'
;

-- 2017-05-22T20:41:12.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einkauf Rabatt Schema', Description='Schema to calculate the purchase trade discount percentage', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1717) AND IsCentrallyMaintained='Y'
;

-- 2017-05-22T20:41:12.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Einkauf Rabatt Schema', Name='Einkauf Rabatt Schema' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1717)
;

-- 2017-05-22T20:42:24.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540291,540482,TO_TIMESTAMP('2017-05-22 20:42:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-05-22 20:42:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:42:33.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3907,0,322,540482,544692,TO_TIMESTAMP('2017-05-22 20:42:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-05-22 20:42:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:42:44.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3908,0,322,540482,544693,TO_TIMESTAMP('2017-05-22 20:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Standard',20,0,0,TO_TIMESTAMP('2017-05-22 20:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:42:53.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556294,0,322,540482,544694,TO_TIMESTAMP('2017-05-22 20:42:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kunde',30,0,0,TO_TIMESTAMP('2017-05-22 20:42:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:43:09.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1000006,0,322,540482,544695,TO_TIMESTAMP('2017-05-22 20:43:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','MwSt. ausweisen',40,0,0,TO_TIMESTAMP('2017-05-22 20:43:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:43:30.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12648,0,322,540482,544696,TO_TIMESTAMP('2017-05-22 20:43:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kreditlinie überwachen %',50,0,0,TO_TIMESTAMP('2017-05-22 20:43:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:43:45.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544696
;

-- 2017-05-22T20:43:57.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540291,540483,TO_TIMESTAMP('2017-05-22 20:43:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','tax',20,TO_TIMESTAMP('2017-05-22 20:43:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:44:10.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1000006,0,322,540483,544697,TO_TIMESTAMP('2017-05-22 20:44:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','MwSt. ausweisen',10,0,0,TO_TIMESTAMP('2017-05-22 20:44:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:44:25.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12648,0,322,540483,544698,TO_TIMESTAMP('2017-05-22 20:44:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kreditlinie überwachen %',20,0,0,TO_TIMESTAMP('2017-05-22 20:44:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:44:32.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540291,540484,TO_TIMESTAMP('2017-05-22 20:44:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2017-05-22 20:44:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:44:40.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3904,0,322,540484,544699,TO_TIMESTAMP('2017-05-22 20:44:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-05-22 20:44:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:44:48.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3903,0,322,540484,544700,TO_TIMESTAMP('2017-05-22 20:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-05-22 20:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:45:46.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544618
;

-- 2017-05-22T20:45:46.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544619
;

-- 2017-05-22T20:45:46.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544620
;

-- 2017-05-22T20:45:46.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544621
;

-- 2017-05-22T20:45:47.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544622
;

-- 2017-05-22T20:45:47.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544623
;

-- 2017-05-22T20:45:47.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544624
;

-- 2017-05-22T20:45:47.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544625
;

-- 2017-05-22T20:46:05.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544629
;

-- 2017-05-22T20:46:05.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544630
;

-- 2017-05-22T20:46:05.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544631
;

-- 2017-05-22T20:46:05.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544632
;

-- 2017-05-22T20:46:11.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544633
;

-- 2017-05-22T20:46:44.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544638
;

-- 2017-05-22T20:46:49.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544637
;

-- 2017-05-22T20:46:59.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544636
;

-- 2017-05-22T20:47:54.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554377,0,322,540481,544701,TO_TIMESTAMP('2017-05-22 20:47:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsweise',60,0,0,TO_TIMESTAMP('2017-05-22 20:47:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:48:24.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=540044, Name='Frachtkostenpauschale',Updated=TO_TIMESTAMP('2017-05-22 20:48:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544697
;

-- 2017-05-22T20:49:05.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544626
;

-- 2017-05-22T20:49:05.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544627
;

-- 2017-05-22T20:49:05.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544628
;

-- 2017-05-22T20:49:05.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544634
;

-- 2017-05-22T20:49:05.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544635
;

-- 2017-05-22T20:49:05.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544639
;

-- 2017-05-22T20:49:05.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544640
;

-- 2017-05-22T20:49:10.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540477
;

-- 2017-05-22T20:49:21.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,322,540210,TO_TIMESTAMP('2017-05-22 20:49:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',20,TO_TIMESTAMP('2017-05-22 20:49:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:49:24.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540294,540210,TO_TIMESTAMP('2017-05-22 20:49:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-22 20:49:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:49:34.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540294,540485,TO_TIMESTAMP('2017-05-22 20:49:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2017-05-22 20:49:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:49:44.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,13017,0,322,540485,544702,TO_TIMESTAMP('2017-05-22 20:49:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mahnung',10,0,0,TO_TIMESTAMP('2017-05-22 20:49:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:49:57.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10259,0,322,540485,544703,TO_TIMESTAMP('2017-05-22 20:49:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Druck Farbe',20,0,0,TO_TIMESTAMP('2017-05-22 20:49:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:50:16.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12002,0,322,540485,544704,TO_TIMESTAMP('2017-05-22 20:50:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Priorität',30,0,0,TO_TIMESTAMP('2017-05-22 20:50:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:50:33.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12001,0,322,540485,544705,TO_TIMESTAMP('2017-05-22 20:50:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vertrauliche Informationen',40,0,0,TO_TIMESTAMP('2017-05-22 20:50:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:51:03.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12649,0,322,540485,544706,TO_TIMESTAMP('2017-05-22 20:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preis Matching Toleranz',50,0,0,TO_TIMESTAMP('2017-05-22 20:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:51:26.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556271,0,322,540485,544707,TO_TIMESTAMP('2017-05-22 20:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Steuer andrucken',60,0,0,TO_TIMESTAMP('2017-05-22 20:51:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:51:42.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555417,0,322,540485,544708,TO_TIMESTAMP('2017-05-22 20:51:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Von MRP ausnehmen',70,0,0,TO_TIMESTAMP('2017-05-22 20:51:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T20:51:43.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 20:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544702
;

-- 2017-05-22T20:51:43.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 20:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544703
;

-- 2017-05-22T20:51:43.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 20:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544704
;

-- 2017-05-22T20:51:44.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 20:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544705
;

-- 2017-05-22T20:51:44.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 20:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544706
;

-- 2017-05-22T20:51:45.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 20:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544707
;

-- 2017-05-22T20:51:46.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-22 20:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544708
;

-- 2017-05-22T20:55:59.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544685
;

-- 2017-05-22T20:55:59.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544686
;

-- 2017-05-22T20:55:59.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544687
;

-- 2017-05-22T20:55:59.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544692
;

-- 2017-05-22T20:55:59.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544693
;

-- 2017-05-22T20:55:59.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544694
;

-- 2017-05-22T20:55:59.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544695
;

-- 2017-05-22T20:55:59.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544688
;

-- 2017-05-22T20:55:59.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544689
;

-- 2017-05-22T20:55:59.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544690
;

-- 2017-05-22T20:55:59.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544691
;

-- 2017-05-22T20:55:59.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544699
;

-- 2017-05-22T20:55:59.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2017-05-22 20:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544700
;

-- 2017-05-22T20:56:36.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-05-22 20:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544685
;

-- 2017-05-22T20:56:36.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-05-22 20:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544687
;

-- 2017-05-22T20:56:36.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-05-22 20:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544692
;

-- 2017-05-22T20:56:36.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-05-22 20:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544693
;

-- 2017-05-22T20:56:36.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2017-05-22 20:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544699
;

-- 2017-05-22T20:56:36.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=60,Updated=TO_TIMESTAMP('2017-05-22 20:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544700
;

