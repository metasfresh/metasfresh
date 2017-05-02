-- 2017-05-02T17:52:24.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,227,540148,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540203,540148,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540204,540148,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540203,540313,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2206,0,227,540313,543409,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',10,10,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2211,0,227,540313,543410,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',20,20,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,11015,0,227,540313,543411,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',30,30,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2209,0,227,540313,543412,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',40,40,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2208,0,227,540313,543413,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Adresse oder Anschrift','Das Feld "Adresse" definiert die Adressangaben eines Standortes.','Y','N','Y','Y','N','Anschrift',50,50,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2210,0,227,540313,543414,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Bank for this Organization','The Own Bank field indicates if this bank is for this Organization as opposed to a Bank for a Business Partner.','Y','N','Y','Y','N','Eigene Bank',60,60,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2212,0,227,540313,543415,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Bankleitzahl','The Bank Routing Number (ABA Number) identifies a legal Bank.  It is used in routing checks and electronic transactions.','Y','N','Y','Y','N','BLZ',70,70,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2213,0,227,540313,543416,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Swift Code or BIC','The Swift Code (Society of Worldwide Interbank Financial Telecommunications) or BIC (Bank Identifier Code) is an identifier of a Bank. The first 4 characters are the bank code, followed by the 2 character country code, the two character location code and optional 3 character branch code. For details see http://www.swift.com/biconline/index.cfm','Y','N','Y','Y','N','Swift code',80,80,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,61466,0,227,540313,543417,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Cash Bank',90,90,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551171,0,227,540313,543418,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Markiert die Postfinanz-Bank. Hinweis: auch andere Banken können am ESR-Verfahren teilnehmen, ohne selbst die Postfinance-Bank zu sein.','Y','N','Y','Y','N','Bank ist die Postfinance-Bank',100,100,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540659,540149,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540205,540149,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540205,540314,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555343,0,540659,540314,543419,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,10,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555344,0,540659,540314,543420,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,20,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555345,0,540115,543420,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555346,0,540659,540314,543421,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,30,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555347,0,540659,540314,543422,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Automated Clearing House / Clearing-Zentrale','Das Auswahlfeld "ACH" zeigt an, ob dieses Konto ACH-Transaktionen akzeptiert.','Y','N','N','Y','N','ACH',0,40,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555348,0,540659,540314,543423,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Bank Account usage','Determines how the bank account is used.','Y','N','N','Y','N','Kontonutzung',0,50,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:24.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555349,0,540659,540314,543424,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Bank','"Bank" ist die eindeutige Identifizierung einer Bank für diese Organisation oder für eien Geschäftspartner mit dem die Organisation interagiert.','Y','N','N','Y','N','Bank',0,60,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555350,0,540659,540314,543425,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100,'Kontenart','The Bank Account Type field indicates the type of account (savings, checking etc) this account  is defined as.','Y','N','N','Y','N','Kontenart',0,70,0,TO_TIMESTAMP('2017-05-02 17:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555351,0,540659,540314,543426,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Bankleitzahl','The Bank Routing Number (ABA Number) identifies a legal Bank.  It is used in routing checks and electronic transactions.','Y','N','N','Y','N','BLZ',0,80,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555352,0,540659,540314,543427,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Kontonummer','The Account Number indicates the Number assigned to this bank account. ','Y','N','N','Y','N','Konto-Nr.',0,90,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555353,0,540659,540314,543428,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number','If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.','Y','N','N','Y','N','IBAN',0,100,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555356,0,540659,540314,543429,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Verifizierungs-Code der Kreditkarte','Der "Verifizierungs-Code" gibt den Verifizierungs-Code uf der Kreditkarte an (AMEX 4 Stellen auf der Vorderseite; MC / Visa 3 Stellen auf der Rückseite)','Y','N','N','Y','N','Verifizierungs-Code',0,110,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555357,0,540659,540314,543430,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis Monat','Gibt den letzten Monat der Gültigkeit dieser Kreditkarte an.','Y','N','N','Y','N','Exp. Monat',0,120,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555358,0,540659,540314,543431,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis Jahr','Gibt das letzte Jahr der Gültigkeit dieser Kreditkarte an.','Y','N','N','Y','N','Exp. Jahr',0,130,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555359,0,540659,540314,543432,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','Währung',0,140,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555360,0,540659,540314,543433,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Name auf Kreditkarte oder des Kontoeigners','"Name" bezeichnet den Namen des Eigentümers von Kreditkarte oder Konto.','Y','N','N','Y','N','Name',0,150,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555361,0,540659,540314,543434,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Straße des Eigentümers von Kreditkarte oder Konto','"Straße" bezeichnet die Straße des Eigentümers von Kreditkarte oder Konto.','Y','N','N','Y','N','Straße',0,160,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555362,0,540659,540314,543435,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Ort des Eigentümers der Kreditkarte oder des Bankkontos','"Ort" bezeichnet den Ort des Eigentümers der Kreditkarte oder des Bankkontos','Y','N','N','Y','N','Ort',0,170,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555363,0,540659,540314,543436,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl des Eigentümers von Kreditkarte oder Konto','"Postleitzahl" bezeichnet die Postleitzahl des Eigentümers von Kreditkarte oder Konto','Y','N','N','Y','N','Postleitzahl',0,180,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555364,0,540659,540314,543437,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Bundesstaat / -land des Eigentümers von Kreditkarte oder Konto','Bundesstaat / -land bezeichnet den Bundesstaat oder das Bundesland des Eigentümers von Kreditkarte oder Konto.','Y','N','N','Y','N','Bundesstaat / -land',0,190,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555365,0,540659,540314,543438,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Land','Name des Landes','Y','N','N','Y','N','Land',0,200,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555366,0,540659,540314,543439,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Identifikation - Führerschein-Nr.','Die "Führerschein-Nr." zur Identifikation bei der Zahlung.','Y','N','N','Y','N','Führerschein-Nr.',0,210,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555367,0,540659,540314,543440,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Identifikation - Ausweis-Nr.','Ausweis-Nr. als Identifikation.','Y','N','N','Y','N','Ausweis-Nr.',0,220,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555368,0,540659,540314,543441,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Email-Adresse','"EMail" bezeichnet die EMail-Adresse des Eigentümers von Kreditkarte oder Konto.','Y','N','N','Y','N','EMail',0,230,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555369,0,540659,540314,543442,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Diese Adresse wurde verifiziert','"Addresse verifiziert" zeigt an, dass diese Adresse durch die Kreditkartenfirma bestätigt wurde.','Y','N','N','Y','N','Addresse verifiziert',0,240,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555370,0,540659,540314,543443,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Die Postleitzahl wurde verifiziert','"Postleitzahl verifiziert" zeigt an, dass diese Postleitzahl durch die Kreditkartenfirma bestätigt wurde.','Y','N','N','Y','N','Postleitzahl verifiziert',0,250,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555371,0,540659,540314,543444,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners','Y','N','N','Y','N','Bankverbindung',0,260,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555372,0,540659,540314,543445,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR Teilnehmernummer',0,270,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555373,0,540659,540314,543446,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR_RenderedReceiver',0,280,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555374,0,540659,540314,543447,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','ESR Konto',0,290,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555375,0,540659,540314,543448,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Standard ESR Konto',0,300,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-02T17:52:25.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555376,0,540659,540314,543449,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','N','Y','N','Standard',0,310,0,TO_TIMESTAMP('2017-05-02 17:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

