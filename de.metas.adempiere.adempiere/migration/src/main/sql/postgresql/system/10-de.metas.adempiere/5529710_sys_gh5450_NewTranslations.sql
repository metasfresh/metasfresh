-- 2019-09-02T13:57:51.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
<br>Example: 
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
Have Pricing System ''CUSTOMER PRICING SYSTEM'' with a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''.
This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
Have a customer partner for which the flag "PLV from base" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''. It will have base PLV = ''ORIGINAL BASE PLV''. 
Create the product prices for this PLV (e.g. using the process ''Create product prices''.
Select this new Price List Version and run the process "Mutate customer Prices".
Results: 
A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''.
It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
Note: There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''.
The new PLV will have the ValidFrom date same as ''ORIGINAL NEW PLV''.
The new PLV and ''ORIGINAL NEW PLV'' and ''CUSTOMER OLD PLV'' all have ''ORIGINAL BASE PLV'' as their base PLV.
',Updated=TO_TIMESTAMP('2019-09-02 16:57:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T13:58:56.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
<br> Example: 
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
Have Pricing System ''CUSTOMER PRICING SYSTEM'' with a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''.
This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
Have a customer partner for which the flag "PLV from base" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''. It will have base PLV = ''ORIGINAL BASE PLV''. 
Create the product prices for this PLV (e.g. using the process ''Create product prices''.
Select this new Price List Version and run the process "Mutate customer Prices".
Results: 
A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''.
It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
Note: There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''.
The new PLV will have the ValidFrom date same as ''ORIGINAL NEW PLV''.
The new PLV and ''ORIGINAL NEW PLV'' and ''CUSTOMER OLD PLV'' all have ''ORIGINAL BASE PLV'' as their base PLV.
',Updated=TO_TIMESTAMP('2019-09-02 16:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T13:59:03.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
<br> Example:  </br>
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
Have Pricing System ''CUSTOMER PRICING SYSTEM'' with a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''.
This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
Have a customer partner for which the flag "PLV from base" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''. It will have base PLV = ''ORIGINAL BASE PLV''. 
Create the product prices for this PLV (e.g. using the process ''Create product prices''.
Select this new Price List Version and run the process "Mutate customer Prices".
Results: 
A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''.
It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
Note: There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''.
The new PLV will have the ValidFrom date same as ''ORIGINAL NEW PLV''.
The new PLV and ''ORIGINAL NEW PLV'' and ''CUSTOMER OLD PLV'' all have ''ORIGINAL BASE PLV'' as their base PLV.
',Updated=TO_TIMESTAMP('2019-09-02 16:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T14:00:31.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
<p> Example:  </p>
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
Have Pricing System ''CUSTOMER PRICING SYSTEM'' with a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''.
This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
Have a customer partner for which the flag "PLV from base" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''. It will have base PLV = ''ORIGINAL BASE PLV''. 
Create the product prices for this PLV (e.g. using the process ''Create product prices''.
Select this new Price List Version and run the process "Mutate customer Prices".
Results: 
A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''.
It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
Note: There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''.
The new PLV will have the ValidFrom date same as ''ORIGINAL NEW PLV''.
The new PLV and ''ORIGINAL NEW PLV'' and ''CUSTOMER OLD PLV'' all have ''ORIGINAL BASE PLV'' as their base PLV.
',Updated=TO_TIMESTAMP('2019-09-02 17:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T14:07:19.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='<p>This process creates price list versions for the customer pricelists that have the same base price list version as the one selected.</p>
<p>Example: 
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
<br>Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
<br>Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
</p>',Updated=TO_TIMESTAMP('2019-09-02 17:07:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;









-- 2019-09-03T09:49:29.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Help='This process creates price list versions (PLV) for customer price lists that have the same base PLV as the one selected.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with PLV valid from the January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV'' with BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''. It will have BasePLV = ''ORIGINAL BASE PLV''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''.
It should contain 2 product prices for the products A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS''.
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.
The new PLV and ''ORIGINAL NEW PLV'' and ''CUSTOMER OLD PLV'' all have ''ORIGINAL BASE PLV'' as their base PLV.',Updated=TO_TIMESTAMP('2019-09-03 12:49:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541180
;

-- 2019-09-03T09:49:45.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions (PLV) for customer price lists that have the same base PLV as the one selected.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with PLV valid from the January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV'' with BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''. It will have BasePLV = ''ORIGINAL BASE PLV''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''.
It should contain 2 product prices for the products A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS''.
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.
The new PLV and ''ORIGINAL NEW PLV'' and ''CUSTOMER OLD PLV'' all have ''ORIGINAL BASE PLV'' as their base PLV.',Updated=TO_TIMESTAMP('2019-09-03 12:49:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-03T09:50:09.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-PLV wie die selektierte eingestellt ist.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''PREISKONDITIONEN KUNDEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' mit Basis-PLV = ''URSPR. BASIS-PLV'' und Preisschema = ''PREISKONDITIONEN KUNDEN'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen. Sie soll die Basis-PLV = ''URSPR. BASIS-PLV'' bekommen.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die BASIS-PLV = ''URSPR. BASIS-PLV'' und das Preisschema = ''PREISKONDITIONEN KUNDEN'', welches aus der ''PLV KUNDEN ALT'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der Basis-PLV + dem Aufschlag aus den ''PREISKONDITIONEN KUNDEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind.
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.
Die neue PLV und ''URSPR. PLV NEU'' und ''PLV KUNDEN ALT'' haben alle die ''URSPR. BASIS-PLV'' als Basis-PLV.',Updated=TO_TIMESTAMP('2019-09-03 12:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541180
;

-- 2019-09-03T09:50:17.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-PLV wie die selektierte eingestellt ist.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''PREISKONDITIONEN KUNDEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' mit Basis-PLV = ''URSPR. BASIS-PLV'' und Preisschema = ''PREISKONDITIONEN KUNDEN'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen. Sie soll die Basis-PLV = ''URSPR. BASIS-PLV'' bekommen.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die BASIS-PLV = ''URSPR. BASIS-PLV'' und das Preisschema = ''PREISKONDITIONEN KUNDEN'', welches aus der ''PLV KUNDEN ALT'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der Basis-PLV + dem Aufschlag aus den ''PREISKONDITIONEN KUNDEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind.
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.
Die neue PLV und ''URSPR. PLV NEU'' und ''PLV KUNDEN ALT'' haben alle die ''URSPR. BASIS-PLV'' als Basis-PLV.',Updated=TO_TIMESTAMP('2019-09-03 12:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541180
;






-- 2019-09-03T09:53:37.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Auf derivative Preislisten anwenden',Updated=TO_TIMESTAMP('2019-09-03 12:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541180
;

-- 2019-09-03T09:53:46.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auf derivative Preislisten anwenden',Updated=TO_TIMESTAMP('2019-09-03 12:53:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541180
;

-- 2019-09-03T09:54:01.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Apply to derivative price lists',Updated=TO_TIMESTAMP('2019-09-03 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-03T09:54:10.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auf derivative Preislisten anwenden',Updated=TO_TIMESTAMP('2019-09-03 12:54:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541180
;

-- 2019-09-03T09:54:15.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auf derivative Preislisten anwenden',Updated=TO_TIMESTAMP('2019-09-03 12:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541180
;










-- 2019-09-04T09:52:35.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLV von Basis', PrintName='PLV von Basis',Updated=TO_TIMESTAMP('2019-09-04 12:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577030 AND AD_Language='de_CH'
;

-- 2019-09-04T09:52:35.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577030,'de_CH') 
;

-- 2019-09-04T09:52:39.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLV von Basis', PrintName='PLV von Basis',Updated=TO_TIMESTAMP('2019-09-04 12:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577030 AND AD_Language='de_DE'
;

-- 2019-09-04T09:52:39.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577030,'de_DE') 
;

-- 2019-09-04T09:52:39.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577030,'de_DE') 
;

-- 2019-09-04T09:52:39.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAllowPriceMutation', Name='PLV von Basis', Description=NULL, Help=NULL WHERE AD_Element_ID=577030
;

-- 2019-09-04T09:52:39.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllowPriceMutation', Name='PLV von Basis', Description=NULL, Help=NULL, AD_Element_ID=577030 WHERE UPPER(ColumnName)='ISALLOWPRICEMUTATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-04T09:52:39.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllowPriceMutation', Name='PLV von Basis', Description=NULL, Help=NULL WHERE AD_Element_ID=577030 AND IsCentrallyMaintained='Y'
;

-- 2019-09-04T09:52:39.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='PLV von Basis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577030) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577030)
;

-- 2019-09-04T09:52:39.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='PLV von Basis', Name='PLV von Basis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577030)
;

-- 2019-09-04T09:52:39.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='PLV von Basis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577030
;

-- 2019-09-04T09:52:39.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='PLV von Basis', Description=NULL, Help=NULL WHERE AD_Element_ID = 577030
;

-- 2019-09-04T09:52:39.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'PLV von Basis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577030
;

-- 2019-09-04T09:52:44.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-04 12:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577030 AND AD_Language='en_US'
;

-- 2019-09-04T09:52:44.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577030,'en_US') 
;

-- 2019-09-04T09:52:50.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='PLV von Basis', PrintName='PLV von Basis',Updated=TO_TIMESTAMP('2019-09-04 12:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577030 AND AD_Language='nl_NL'
;

-- 2019-09-04T09:52:50.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577030,'nl_NL') 
;

-- 2019-09-04T09:53:12.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Von Basis derivierte PLV erlauben ',Updated=TO_TIMESTAMP('2019-09-04 12:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577030 AND AD_Language='de_DE'
;

-- 2019-09-04T09:53:12.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577030,'de_DE') 
;

-- 2019-09-04T09:53:12.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577030,'de_DE') 
;

-- 2019-09-04T09:53:12.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAllowPriceMutation', Name='PLV von Basis', Description='Von Basis derivierte PLV erlauben ', Help=NULL WHERE AD_Element_ID=577030
;

-- 2019-09-04T09:53:12.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllowPriceMutation', Name='PLV von Basis', Description='Von Basis derivierte PLV erlauben ', Help=NULL, AD_Element_ID=577030 WHERE UPPER(ColumnName)='ISALLOWPRICEMUTATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-04T09:53:12.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAllowPriceMutation', Name='PLV von Basis', Description='Von Basis derivierte PLV erlauben ', Help=NULL WHERE AD_Element_ID=577030 AND IsCentrallyMaintained='Y'
;

-- 2019-09-04T09:53:12.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='PLV von Basis', Description='Von Basis derivierte PLV erlauben ', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577030) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577030)
;

-- 2019-09-04T09:53:12.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='PLV von Basis', Description='Von Basis derivierte PLV erlauben ', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577030
;

-- 2019-09-04T09:53:12.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='PLV von Basis', Description='Von Basis derivierte PLV erlauben ', Help=NULL WHERE AD_Element_ID = 577030
;

-- 2019-09-04T09:53:12.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'PLV von Basis', Description = 'Von Basis derivierte PLV erlauben ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577030
;

-- 2019-09-04T09:53:36.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Allow derivative PLV from base',Updated=TO_TIMESTAMP('2019-09-04 12:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577030 AND AD_Language='en_US'
;

-- 2019-09-04T09:53:36.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577030,'en_US') 
;

-- 2019-09-04T09:53:50.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Von Basis derivierte PLV erlauben ',Updated=TO_TIMESTAMP('2019-09-04 12:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577030 AND AD_Language='de_CH'
;

-- 2019-09-04T09:53:50.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577030,'de_CH') 
;






-- 2019-09-04T11:28:06.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Produktpreise  für diese Version erstellen', Name='Produktpreise erstellen',Updated=TO_TIMESTAMP('2019-09-04 14:28:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=103
;

-- 2019-09-04T11:28:43.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Produktpreisefür diese Version erstellen', IsTranslated='Y', Name='Produktpreise erstellen',Updated=TO_TIMESTAMP('2019-09-04 14:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=103
;

-- 2019-09-04T11:28:55.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Produktpreise für diese Version erstellen', Name='Produktpreise erstellen',Updated=TO_TIMESTAMP('2019-09-04 14:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=103
;

-- 2019-09-04T11:28:57.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-04 14:28:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=103
;

-- 2019-09-04T11:29:41.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Create Product Prices', Help='Create product prices for this price list version based on the Base Pricelist Version and the discount schema.', Name='Create Product Prices',Updated=TO_TIMESTAMP('2019-09-04 14:29:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Process_ID=103
;





-- 2019-09-04T11:32:52.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='This process creates price list versions (PLV) for customer price lists that have the same base PLV as the one selected.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with PLV valid from the January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV'' with BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''. It will have BasePLV = ''ORIGINAL BASE PLV''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''.
It should contain 2 product prices for the products A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS''.
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.
The new PLV and ''ORIGINAL NEW PLV'' and ''CUSTOMER OLD PLV'' all have ''ORIGINAL BASE PLV'' as their base PLV.',Updated=TO_TIMESTAMP('2019-09-04 14:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-04T11:32:57.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-PLV wie die selektierte eingestellt ist.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''PREISKONDITIONEN KUNDEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' mit Basis-PLV = ''URSPR. BASIS-PLV'' und Preisschema = ''PREISKONDITIONEN KUNDEN'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen. Sie soll die Basis-PLV = ''URSPR. BASIS-PLV'' bekommen.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die BASIS-PLV = ''URSPR. BASIS-PLV'' und das Preisschema = ''PREISKONDITIONEN KUNDEN'', welches aus der ''PLV KUNDEN ALT'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der Basis-PLV + dem Aufschlag aus den ''PREISKONDITIONEN KUNDEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind.
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.
Die neue PLV und ''URSPR. PLV NEU'' und ''PLV KUNDEN ALT'' haben alle die ''URSPR. BASIS-PLV'' als Basis-PLV.',Updated=TO_TIMESTAMP('2019-09-04 14:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541180
;

-- 2019-09-04T11:33:02.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-PLV wie die selektierte eingestellt ist.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''PREISKONDITIONEN KUNDEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' mit Basis-PLV = ''URSPR. BASIS-PLV'' und Preisschema = ''PREISKONDITIONEN KUNDEN'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen. Sie soll die Basis-PLV = ''URSPR. BASIS-PLV'' bekommen.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die BASIS-PLV = ''URSPR. BASIS-PLV'' und das Preisschema = ''PREISKONDITIONEN KUNDEN'', welches aus der ''PLV KUNDEN ALT'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der Basis-PLV + dem Aufschlag aus den ''PREISKONDITIONEN KUNDEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind.
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.
Die neue PLV und ''URSPR. PLV NEU'' und ''PLV KUNDEN ALT'' haben alle die ''URSPR. BASIS-PLV'' als Basis-PLV.',Updated=TO_TIMESTAMP('2019-09-04 14:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541180
;

-- 2019-09-04T12:36:21.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2019-09-04 15:36:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541180
;

-- 2019-09-04T12:36:28.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-PLV wie die selektierte eingestellt ist.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''PREISKONDITIONEN KUNDEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' mit Basis-PLV = ''URSPR. BASIS-PLV'' und Preisschema = ''PREISKONDITIONEN KUNDEN'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen. Sie soll die Basis-PLV = ''URSPR. BASIS-PLV'' bekommen.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die BASIS-PLV = ''URSPR. BASIS-PLV'' und das Preisschema = ''PREISKONDITIONEN KUNDEN'', welches aus der ''PLV KUNDEN ALT'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der Basis-PLV + dem Aufschlag aus den ''PREISKONDITIONEN KUNDEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind.
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.
Die neue PLV und ''URSPR. PLV NEU'' und ''PLV KUNDEN ALT'' haben alle die ''URSPR. BASIS-PLV'' als Basis-PLV.', Help='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-PLV wie die selektierte eingestellt ist.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''PREISKONDITIONEN KUNDEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' mit Basis-PLV = ''URSPR. BASIS-PLV'' und Preisschema = ''PREISKONDITIONEN KUNDEN'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen. Sie soll die Basis-PLV = ''URSPR. BASIS-PLV'' bekommen.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die BASIS-PLV = ''URSPR. BASIS-PLV'' und das Preisschema = ''PREISKONDITIONEN KUNDEN'', welches aus der ''PLV KUNDEN ALT'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der Basis-PLV + dem Aufschlag aus den ''PREISKONDITIONEN KUNDEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind.
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.
Die neue PLV und ''URSPR. PLV NEU'' und ''PLV KUNDEN ALT'' haben alle die ''URSPR. BASIS-PLV'' als Basis-PLV.',Updated=TO_TIMESTAMP('2019-09-04 15:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541180
;

-- 2019-09-04T12:36:36.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-PLV wie die selektierte eingestellt ist.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''PREISKONDITIONEN KUNDEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' mit Basis-PLV = ''URSPR. BASIS-PLV'' und Preisschema = ''PREISKONDITIONEN KUNDEN'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen. Sie soll die Basis-PLV = ''URSPR. BASIS-PLV'' bekommen.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die BASIS-PLV = ''URSPR. BASIS-PLV'' und das Preisschema = ''PREISKONDITIONEN KUNDEN'', welches aus der ''PLV KUNDEN ALT'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der Basis-PLV + dem Aufschlag aus den ''PREISKONDITIONEN KUNDEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind.
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.
Die neue PLV und ''URSPR. PLV NEU'' und ''PLV KUNDEN ALT'' haben alle die ''URSPR. BASIS-PLV'' als Basis-PLV.',Updated=TO_TIMESTAMP('2019-09-04 15:36:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541180
;

-- 2019-09-04T12:36:41.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Help='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-PLV wie die selektierte eingestellt ist.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''PREISKONDITIONEN KUNDEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' mit Basis-PLV = ''URSPR. BASIS-PLV'' und Preisschema = ''PREISKONDITIONEN KUNDEN'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen. Sie soll die Basis-PLV = ''URSPR. BASIS-PLV'' bekommen.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die BASIS-PLV = ''URSPR. BASIS-PLV'' und das Preisschema = ''PREISKONDITIONEN KUNDEN'', welches aus der ''PLV KUNDEN ALT'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der Basis-PLV + dem Aufschlag aus den ''PREISKONDITIONEN KUNDEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind.
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.
Die neue PLV und ''URSPR. PLV NEU'' und ''PLV KUNDEN ALT'' haben alle die ''URSPR. BASIS-PLV'' als Basis-PLV.',Updated=TO_TIMESTAMP('2019-09-04 15:36:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541180
;










