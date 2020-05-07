-- 07.03.2017 16:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2017-03-07 16:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557938
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540791,540077,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540109,540077,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540110,540077,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540109,540161,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557939,0,540791,540161,542032,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Bezeichnung','Y','N','Y','Y','N','Name 2',10,10,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557940,0,540791,540161,542033,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Firma',20,20,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557941,0,540791,540161,542034,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Firmenname',30,30,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557942,0,540791,540161,542035,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','Y','N','Geschäftspartnergruppe',40,40,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557943,0,540791,540161,542036,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Sprache für diesen Eintrag','Definiert die Sprache für Anzeige und Aufbereitung','Y','N','Y','Y','N','Sprache',50,50,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557944,0,540791,540161,542037,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschäftspartner ein Kunde ist','The Customer checkbox indicates if this Business Partner is a customer.  If it is select additional fields will display which further define this customer.','Y','N','Y','Y','N','Kunde',60,60,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557945,0,540791,540161,542038,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','N','Y','Y','N','Zahlungskondition',70,70,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557946,0,540791,540161,542039,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','Y','Y','N','Preissystem',80,80,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557947,0,540791,540161,542040,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist','The Vendor checkbox indicates if this Business Partner is a Vendor.  If it is selected, additional fields will display which further identify this vendor.','Y','N','Y','Y','N','Lieferant',90,90,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557948,0,540791,540161,542041,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Zahlungskondition für die Bestellung','Die "Zahlungskondition" zeigen die Vorgaben an, die gelten sollen, wenn diese Bestellung zu einer Rechnung wird.','Y','N','Y','Y','N','Zahlungskondition',100,100,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557949,0,540791,540161,542042,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Einkaufspreissystem',110,110,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557950,0,540791,540161,542043,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Vorname','Y','N','Y','Y','N','Vorname',120,120,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557951,0,540791,540161,542044,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Nachname',130,130,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557952,0,540791,540161,542045,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','Y','N','EMail',140,140,0,TO_TIMESTAMP('2017-03-07 22:44:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557953,0,540791,540161,542046,TO_TIMESTAMP('2017-03-07 22:44:42','YYYY-MM-DD HH24:MI:SS'),100,'Identifies a telephone number','The Phone field identifies a telephone number','Y','N','Y','Y','N','Phone',150,150,0,TO_TIMESTAMP('2017-03-07 22:44:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557954,0,540791,540161,542047,TO_TIMESTAMP('2017-03-07 22:44:42','YYYY-MM-DD HH24:MI:SS'),100,'Adresse oder Anschrift','Das Feld "Adresse" definiert die Adressangaben eines Standortes.','Y','N','Y','Y','N','Anschrift',160,160,0,TO_TIMESTAMP('2017-03-07 22:44:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-03-07 22:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542033
;

-- 07.03.2017 22:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-03-07 22:45:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542034
;

-- 07.03.2017 22:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-03-07 22:47:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542032
;

-- 07.03.2017 22:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540110,540162,TO_TIMESTAMP('2017-03-07 22:51:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','contact',10,TO_TIMESTAMP('2017-03-07 22:51:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557950,0,540791,540162,542048,TO_TIMESTAMP('2017-03-07 22:51:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Vorname',10,0,0,TO_TIMESTAMP('2017-03-07 22:51:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557951,0,540791,540162,542049,TO_TIMESTAMP('2017-03-07 22:52:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Nachname',20,0,0,TO_TIMESTAMP('2017-03-07 22:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557952,0,540791,540162,542050,TO_TIMESTAMP('2017-03-07 22:52:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','eMail',30,0,0,TO_TIMESTAMP('2017-03-07 22:52:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557953,0,540791,540162,542051,TO_TIMESTAMP('2017-03-07 22:52:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Telefon',40,0,0,TO_TIMESTAMP('2017-03-07 22:52:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540110,540163,TO_TIMESTAMP('2017-03-07 22:53:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','adresse',20,TO_TIMESTAMP('2017-03-07 22:53:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557954,0,540791,540163,542052,TO_TIMESTAMP('2017-03-07 22:53:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anschrift',10,0,0,TO_TIMESTAMP('2017-03-07 22:53:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540791,540078,TO_TIMESTAMP('2017-03-07 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','customer vendor',20,TO_TIMESTAMP('2017-03-07 22:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540111,540078,TO_TIMESTAMP('2017-03-07 22:54:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-03-07 22:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540112,540078,TO_TIMESTAMP('2017-03-07 22:54:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-03-07 22:54:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540111,540164,TO_TIMESTAMP('2017-03-07 22:54:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','customer',10,TO_TIMESTAMP('2017-03-07 22:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557944,0,540791,540164,542053,TO_TIMESTAMP('2017-03-07 22:54:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kunde',10,0,0,TO_TIMESTAMP('2017-03-07 22:54:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557945,0,540791,540164,542054,TO_TIMESTAMP('2017-03-07 22:55:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsbedingung',20,0,0,TO_TIMESTAMP('2017-03-07 22:55:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557946,0,540791,540164,542055,TO_TIMESTAMP('2017-03-07 22:56:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preissystem',30,0,0,TO_TIMESTAMP('2017-03-07 22:56:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540112,540165,TO_TIMESTAMP('2017-03-07 22:56:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','vendor',10,TO_TIMESTAMP('2017-03-07 22:56:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557947,0,540791,540165,542056,TO_TIMESTAMP('2017-03-07 22:56:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferant',10,0,0,TO_TIMESTAMP('2017-03-07 22:56:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557948,0,540791,540165,542057,TO_TIMESTAMP('2017-03-07 22:57:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsbedingung',20,0,0,TO_TIMESTAMP('2017-03-07 22:57:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557949,0,540791,540165,542058,TO_TIMESTAMP('2017-03-07 22:57:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preissystem',30,0,0,TO_TIMESTAMP('2017-03-07 22:57:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542037
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542038
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542039
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542040
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542041
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542042
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542043
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542044
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542045
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542046
;

-- 07.03.2017 22:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542047
;

-- 07.03.2017 23:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Neuer Geschäftspartner',Updated=TO_TIMESTAMP('2017-03-07 23:00:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540327
;

-- 07.03.2017 23:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET IsTranslated='N' WHERE AD_Window_ID=540327
;

-- 07.03.2017 23:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Neuer Geschäftspartner',Updated=TO_TIMESTAMP('2017-03-07 23:02:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540791
;

-- 07.03.2017 23:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540791
;

-- 07.03.2017 23:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-07 23:03:45','YYYY-MM-DD HH24:MI:SS'),Name='BPartner quick input engl' WHERE AD_Tab_ID=540791 AND AD_Language='en_US'
;

-- 07.03.2017 23:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-07 23:03:58','YYYY-MM-DD HH24:MI:SS'),Name='BPartner quick input eng' WHERE AD_Window_ID=540327 AND AD_Language='en_US'
;


