-- 2019-10-17T13:32:41.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2019-10-17 16:32:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540707
;

-- 2019-10-17T13:33:55.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2019-10-17 16:33:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=393
;

-- 2019-10-17T13:34:04.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2019-10-17 16:34:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=392
;

-- 2019-10-17T13:34:24.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=194,Updated=TO_TIMESTAMP('2019-10-17 16:34:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=392
;

-- 2019-10-17T13:34:31.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=194,Updated=TO_TIMESTAMP('2019-10-17 16:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=393
;

-- 2019-10-17T13:35:22.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2019-10-17 16:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=393
;

-- 2019-10-17T13:35:29.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2019-10-17 16:35:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=392
;

-- 2019-10-17T13:39:42.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsKey='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-10-17 16:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552818
;

-- 2019-10-17T13:52:03.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=NULL, AD_Element_ID=1382, AD_Table_ID=393, CommitWarning=NULL, Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.', InternalName='C_BankStatementLine', Name='Auszugs-Position',Updated=TO_TIMESTAMP('2019-10-17 16:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540707
;

-- 2019-10-17T13:52:03.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=4937, Description='Zahlung', Help='Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).', Name='Zahlung',Updated=TO_TIMESTAMP('2019-10-17 16:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556403
;

-- 2019-10-17T13:52:03.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1384) 
;

-- 2019-10-17T13:52:03.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556403
;

-- 2019-10-17T13:52:03.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556403)
;

-- 2019-10-17T13:52:03.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=4929, Description='Der Eintrag ist im System aktiv', Help='Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.', Name='Aktiv',Updated=TO_TIMESTAMP('2019-10-17 16:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556412
;

-- 2019-10-17T13:52:03.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-10-17T13:52:04.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556412
;

-- 2019-10-17T13:52:04.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556412)
;

-- 2019-10-17T13:52:04.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=4928, Description='Organisatorische Einheit des Mandanten', Help='Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.', Name='Sektion',Updated=TO_TIMESTAMP('2019-10-17 16:52:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556411
;

-- 2019-10-17T13:52:04.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-10-17T13:52:04.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556411
;

-- 2019-10-17T13:52:04.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556411)
;

-- 2019-10-17T13:52:04.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=4927, Description='Mandant für diese Installation.', Help='Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .', Name='Mandant',Updated=TO_TIMESTAMP('2019-10-17 16:52:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556410
;

-- 2019-10-17T13:52:04.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-10-17T13:52:05.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556410
;

-- 2019-10-17T13:52:05.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556410)
;

-- 2019-10-17T13:52:05.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=54669, Description='Calculated amount of discount', Help='The Discount Amount indicates the discount amount for a document or line.', Name='Skonto',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556395
;

-- 2019-10-17T13:52:05.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1395) 
;

-- 2019-10-17T13:52:05.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556395
;

-- 2019-10-17T13:52:05.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556395)
;

-- 2019-10-17T13:52:05.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=54670, Description='Amount to write-off', Help='The Write Off Amount indicates the amount to be written off as uncollectible.', Name='Write-off Amount',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556396
;

-- 2019-10-17T13:52:05.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1450) 
;

-- 2019-10-17T13:52:05.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556396
;

-- 2019-10-17T13:52:05.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556396)
;

-- 2019-10-17T13:52:05.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=54671, Description='Over-Payment (unallocated) or Under-Payment (partial payment)', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.', Name='Over/Under Payment',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556397
;

-- 2019-10-17T13:52:05.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1818) 
;

-- 2019-10-17T13:52:05.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556397
;

-- 2019-10-17T13:52:05.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556397)
;

-- 2019-10-17T13:52:05.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=54672, Description='Over-Payment (unallocated) or Under-Payment (partial payment) Amount', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.', Name='Over/Under Payment',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556398
;

-- 2019-10-17T13:52:05.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1819) 
;

-- 2019-10-17T13:52:05.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556398
;

-- 2019-10-17T13:52:05.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556398)
;

-- 2019-10-17T13:52:05.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=10780, Description='Bezeichnet einen Geschäftspartner', Help='Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.', Name='Geschäftspartner',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556407
;

-- 2019-10-17T13:52:05.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001232) 
;

-- 2019-10-17T13:52:05.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556407
;

-- 2019-10-17T13:52:05.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556407)
;

-- 2019-10-17T13:52:05.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=5219, Description='Einzelne Zeile in dem Dokument', Help='Indicates the unique line for a document.  It will also control the display order of the lines within a document.', Name='Zeile Nr.',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556406
;

-- 2019-10-17T13:52:05.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439) 
;

-- 2019-10-17T13:52:05.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556406
;

-- 2019-10-17T13:52:05.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556406)
;

-- 2019-10-17T13:52:05.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=61508, Description=NULL, Help=NULL, Name='Linked Statement Line',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556405
;

-- 2019-10-17T13:52:05.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(55172) 
;

-- 2019-10-17T13:52:05.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556405
;

-- 2019-10-17T13:52:05.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556405)
;

-- 2019-10-17T13:52:05.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=61507, Description='Cashbook/Bank account To', Help=NULL, Name='Cashbook/Bank account To',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556404
;

-- 2019-10-17T13:52:05.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542687) 
;

-- 2019-10-17T13:52:05.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556404
;

-- 2019-10-17T13:52:05.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556404)
;

-- 2019-10-17T13:52:05.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=4934, Description='Bank Statement of account', Help='The Bank Statement identifies a unique Bank Statement for a defined time period.  The statement defines all transactions that occurred', Name='Bankauszug',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556391
;

-- 2019-10-17T13:52:05.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1381) 
;

-- 2019-10-17T13:52:05.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556391
;

-- 2019-10-17T13:52:05.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556391)
;

-- 2019-10-17T13:52:05.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=4926, Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.', Name='Auszugs-Position',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556392
;

-- 2019-10-17T13:52:05.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1382) 
;

-- 2019-10-17T13:52:05.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556392
;

-- 2019-10-17T13:52:05.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556392)
;

-- 2019-10-17T13:52:05.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541180
;

-- 2019-10-17T13:52:05.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556393
;

-- 2019-10-17T13:52:05.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=556393
;

-- 2019-10-17T13:52:05.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=556393
;

-- 2019-10-17T13:52:05.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=5216, Description='Accounting Date', Help='The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.', Name='Buchungsdatum',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556400
;

-- 2019-10-17T13:52:05.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(263) 
;

-- 2019-10-17T13:52:05.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556400
;

-- 2019-10-17T13:52:05.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556400)
;

-- 2019-10-17T13:52:05.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=5222, Description='Date when money is available', Help='The Effective Date indicates the date that money is available from the bank.', Name='Effective date',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556401
;

-- 2019-10-17T13:52:05.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1487) 
;

-- 2019-10-17T13:52:05.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556401
;

-- 2019-10-17T13:52:05.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556401)
;

-- 2019-10-17T13:52:05.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=5221, Description='Betrag einer Transaktion', Help='"Bewegungs-Betrag" gibt den Betrag für einen einzelnen Vorgang an.', Name='Bewegungs-Betrag',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556394
;

-- 2019-10-17T13:52:05.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1136) 
;

-- 2019-10-17T13:52:05.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556394
;

-- 2019-10-17T13:52:05.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556394)
;

-- 2019-10-17T13:52:05.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=5217, Description='Die Währung für diesen Eintrag', Help='Bezeichnet die auf Dokumenten oder Berichten verwendete Währung', Name='Währung',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556399
;

-- 2019-10-17T13:52:05.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2019-10-17T13:52:05.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556399
;

-- 2019-10-17T13:52:05.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556399)
;

-- 2019-10-17T13:52:05.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=10779, Description='Invoice Identifier', Help='The Invoice Document.', Name='Rechnung',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556402
;

-- 2019-10-17T13:52:05.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008) 
;

-- 2019-10-17T13:52:05.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556402
;

-- 2019-10-17T13:52:05.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556402)
;

-- 2019-10-17T13:52:05.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=8986, Description='Ihre Kunden- oder Lieferantennummer beim Geschäftspartner', Help='Die "Referenznummer" kann auf Bestellungen und Rechnungen gedruckt werden um Ihre Dokumente beim Geschäftspartner einfacher zuordnen zu können.', Name='Referenznummer',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556409
;

-- 2019-10-17T13:52:05.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540) 
;

-- 2019-10-17T13:52:05.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556409
;

-- 2019-10-17T13:52:05.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556409)
;

-- 2019-10-17T13:52:05.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=12463, Description='Checkbox sagt aus, ob der Beleg verarbeitet wurde. ', Help='Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.', Name='Verarbeitet',Updated=TO_TIMESTAMP('2019-10-17 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556408
;

-- 2019-10-17T13:52:05.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000173) 
;

-- 2019-10-17T13:52:05.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556408
;

-- 2019-10-17T13:52:05.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556408)
;

-- 2019-10-17T13:52:05.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(1382) 
;

-- 2019-10-17T13:52:05.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(540707)
;

-- 2019-10-17T13:52:26.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=4937,Updated=TO_TIMESTAMP('2019-10-17 16:52:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540707
;

-- 2019-10-17T14:00:00.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsSOTrx='N',Updated=TO_TIMESTAMP('2019-10-17 17:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540722
;

-- 2019-10-17T14:00:26.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=194, PO_Window_ID=540722,Updated=TO_TIMESTAMP('2019-10-17 17:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=392
;

-- 2019-10-17T14:00:39.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=194, PO_Window_ID=540722,Updated=TO_TIMESTAMP('2019-10-17 17:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=393
;

-- 2019-10-17T14:04:07.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=NULL, AD_Element_ID=543988, AD_Table_ID=540687, CommitWarning=NULL, Description=NULL, Help=NULL, InternalName='C_BankStatementLine_v1', Name='Validation Rule Depends On',Updated=TO_TIMESTAMP('2019-10-17 17:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540707
;

-- 2019-10-17T14:04:07.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552821, Description='Calculated amount of discount', Help='The Discount Amount indicates the discount amount for a document or line.', Name='Skonto',Updated=TO_TIMESTAMP('2019-10-17 17:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556395
;

-- 2019-10-17T14:04:07.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1395) 
;

-- 2019-10-17T14:04:07.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556395
;

-- 2019-10-17T14:04:07.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556395)
;

-- 2019-10-17T14:04:07.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552836, Description='Mandant für diese Installation.', Help='Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .', Name='Mandant',Updated=TO_TIMESTAMP('2019-10-17 17:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556410
;

-- 2019-10-17T14:04:07.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-10-17T14:04:08.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556410
;

-- 2019-10-17T14:04:08.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556410)
;

-- 2019-10-17T14:04:08.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552837, Description='Organisatorische Einheit des Mandanten', Help='Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.', Name='Sektion',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556411
;

-- 2019-10-17T14:04:08.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-10-17T14:04:08.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556411
;

-- 2019-10-17T14:04:08.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556411)
;

-- 2019-10-17T14:04:08.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552842, Description='Der Eintrag ist im System aktiv', Help='Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.', Name='Aktiv',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556412
;

-- 2019-10-17T14:04:08.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-10-17T14:04:08.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556412
;

-- 2019-10-17T14:04:08.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556412)
;

-- 2019-10-17T14:04:08.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552833, Description='Bezeichnet einen Geschäftspartner', Help='Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.', Name='Geschäftspartner',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556407
;

-- 2019-10-17T14:04:08.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001232) 
;

-- 2019-10-17T14:04:08.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556407
;

-- 2019-10-17T14:04:08.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556407)
;

-- 2019-10-17T14:04:08.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552829, Description='Zahlung', Help='Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).', Name='Zahlung',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556403
;

-- 2019-10-17T14:04:08.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1384) 
;

-- 2019-10-17T14:04:08.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556403
;

-- 2019-10-17T14:04:08.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556403)
;

-- 2019-10-17T14:04:08.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552831, Description=NULL, Help=NULL, Name='Linked Statement Line',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556405
;

-- 2019-10-17T14:04:08.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(55172) 
;

-- 2019-10-17T14:04:08.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556405
;

-- 2019-10-17T14:04:08.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556405)
;

-- 2019-10-17T14:04:08.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552830, Description='Cashbook/Bank account To', Help=NULL, Name='Cashbook/Bank account To',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556404
;

-- 2019-10-17T14:04:08.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542687) 
;

-- 2019-10-17T14:04:08.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556404
;

-- 2019-10-17T14:04:08.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556404)
;

-- 2019-10-17T14:04:08.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552832, Description='Einzelne Zeile in dem Dokument', Help='Indicates the unique line for a document.  It will also control the display order of the lines within a document.', Name='Zeile Nr.',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556406
;

-- 2019-10-17T14:04:08.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439) 
;

-- 2019-10-17T14:04:08.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556406
;

-- 2019-10-17T14:04:08.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556406)
;

-- 2019-10-17T14:04:08.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552824, Description='Over-Payment (unallocated) or Under-Payment (partial payment) Amount', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.', Name='Over/Under Payment',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556398
;

-- 2019-10-17T14:04:08.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1819) 
;

-- 2019-10-17T14:04:08.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556398
;

-- 2019-10-17T14:04:08.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556398)
;

-- 2019-10-17T14:04:08.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552823, Description='Over-Payment (unallocated) or Under-Payment (partial payment)', Help='Overpayments (negative) are unallocated amounts and allow you to receive money for more than the particular invoice. 
Underpayments (positive) is a partial payment for the invoice. You do not write off the unpaid amount.', Name='Over/Under Payment',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556397
;

-- 2019-10-17T14:04:08.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1818) 
;

-- 2019-10-17T14:04:08.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556397
;

-- 2019-10-17T14:04:08.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556397)
;

-- 2019-10-17T14:04:08.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552822, Description='Amount to write-off', Help='The Write Off Amount indicates the amount to be written off as uncollectible.', Name='Write-off Amount',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556396
;

-- 2019-10-17T14:04:08.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1450) 
;

-- 2019-10-17T14:04:08.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556396
;

-- 2019-10-17T14:04:08.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556396)
;

-- 2019-10-17T14:04:08.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552817, Description='Bank Statement of account', Help='The Bank Statement identifies a unique Bank Statement for a defined time period.  The statement defines all transactions that occurred', Name='Bankauszug',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556391
;

-- 2019-10-17T14:04:08.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1381) 
;

-- 2019-10-17T14:04:08.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556391
;

-- 2019-10-17T14:04:08.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556391)
;

-- 2019-10-17T14:04:08.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552818, Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.', Name='Auszugs-Position',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556392
;

-- 2019-10-17T14:04:08.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1382) 
;

-- 2019-10-17T14:04:08.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556392
;

-- 2019-10-17T14:04:08.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556392)
;

-- 2019-10-17T14:04:08.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552826, Description='Accounting Date', Help='The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.', Name='Buchungsdatum',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556400
;

-- 2019-10-17T14:04:08.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(263) 
;

-- 2019-10-17T14:04:08.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556400
;

-- 2019-10-17T14:04:08.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556400)
;

-- 2019-10-17T14:04:08.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552827, Description='Date when money is available', Help='The Effective Date indicates the date that money is available from the bank.', Name='Effective date',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556401
;

-- 2019-10-17T14:04:08.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1487) 
;

-- 2019-10-17T14:04:08.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556401
;

-- 2019-10-17T14:04:08.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556401)
;

-- 2019-10-17T14:04:08.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552820, Description='Betrag einer Transaktion', Help='"Bewegungs-Betrag" gibt den Betrag für einen einzelnen Vorgang an.', Name='Bewegungs-Betrag',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556394
;

-- 2019-10-17T14:04:08.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1136) 
;

-- 2019-10-17T14:04:08.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556394
;

-- 2019-10-17T14:04:08.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556394)
;

-- 2019-10-17T14:04:08.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552825, Description='Die Währung für diesen Eintrag', Help='Bezeichnet die auf Dokumenten oder Berichten verwendete Währung', Name='Währung',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556399
;

-- 2019-10-17T14:04:08.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2019-10-17T14:04:08.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556399
;

-- 2019-10-17T14:04:08.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556399)
;

-- 2019-10-17T14:04:08.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552828, Description='Invoice Identifier', Help='The Invoice Document.', Name='Rechnung',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556402
;

-- 2019-10-17T14:04:08.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008) 
;

-- 2019-10-17T14:04:08.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556402
;

-- 2019-10-17T14:04:08.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556402)
;

-- 2019-10-17T14:04:08.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552835, Description='Ihre Kunden- oder Lieferantennummer beim Geschäftspartner', Help='Die "Referenznummer" kann auf Bestellungen und Rechnungen gedruckt werden um Ihre Dokumente beim Geschäftspartner einfacher zuordnen zu können.', Name='Referenznummer',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556409
;

-- 2019-10-17T14:04:08.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540) 
;

-- 2019-10-17T14:04:08.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556409
;

-- 2019-10-17T14:04:08.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556409)
;

-- 2019-10-17T14:04:08.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=552834, Description='Checkbox sagt aus, ob der Beleg verarbeitet wurde. ', Help='Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.', Name='Verarbeitet',Updated=TO_TIMESTAMP('2019-10-17 17:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556408
;

-- 2019-10-17T14:04:08.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000173) 
;

-- 2019-10-17T14:04:08.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556408
;

-- 2019-10-17T14:04:08.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556408)
;

-- 2019-10-17T14:04:08.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(543988) 
;

-- 2019-10-17T14:04:08.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(540707)
;

-- 2019-10-17T14:04:50.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=1001846, CommitWarning=NULL, Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.', Name='Auszugs-Position',Updated=TO_TIMESTAMP('2019-10-17 17:04:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540707
;

-- 2019-10-17T14:04:50.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(1001846) 
;

-- 2019-10-17T14:04:50.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(540707)
;

-- 2019-10-17T14:05:28.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=552829,Updated=TO_TIMESTAMP('2019-10-17 17:05:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540707
;

-- 2019-10-17T14:12:40.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2019-10-17 17:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540722
;

-- 2019-10-17T14:13:27.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET PO_Window_ID=NULL,Updated=TO_TIMESTAMP('2019-10-17 17:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=393
;

-- 2019-10-17T14:13:39.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET PO_Window_ID=NULL,Updated=TO_TIMESTAMP('2019-10-17 17:13:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=392
;

-- 2019-10-17T14:16:26.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=14327,Updated=TO_TIMESTAMP('2019-10-17 17:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=755
;

