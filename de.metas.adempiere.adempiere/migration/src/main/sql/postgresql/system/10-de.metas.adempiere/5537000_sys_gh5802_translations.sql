-- 2019-11-27T13:45:13.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions (PLV) for customer price lists that have the BasePricelist as the Pricelist of the selected Pricelist Version.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with PLV valid from the January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''Base PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM'' with the discount schema ''Base PRICING CONDITIONS''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''CUSTOMER OLD PLV'' and Pricelist Schema = ''Base PRICING CONDITIONS'', which is taken from the process parameter pricelist version .
It should contain 2 product prices for the products A and B which equal base prices from the ''CUSTOMER OLD PLV'' + the surcharge from the ''Base PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS'' or if the ''CUSTOMER PRICING CONDITIONS'' has a line for any product ( no product or category set).
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.', Help='This process creates price list versions (PLV) for customer price lists that have the BasePricelist as the Pricelist of the selected Pricelist Version.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with PLV valid from the January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''Base PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM'' with the discount schema ''Base PRICING CONDITIONS''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''CUSTOMER OLD PLV'' and Pricelist Schema = ''Base PRICING CONDITIONS'', which is taken from the process parameter pricelist version .
It should contain 2 product prices for the products A and B which equal base prices from the ''CUSTOMER OLD PLV'' + the surcharge from the ''Base PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS'' or if the ''CUSTOMER PRICING CONDITIONS'' has a line for any product ( no product or category set).
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.',Updated=TO_TIMESTAMP('2019-11-27 15:45:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-11-27T14:21:30.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions (PLV) for customer price lists that have the BasePricelist as the Pricelist of the selected Pricelist Version.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with PLV valid from the January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''Base PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM'' with the pricing schema ''Base PRICING CONDITIONS''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''CUSTOMER OLD PLV'' and Pricelist Schema = ''Base PRICING CONDITIONS'', which is taken from the process parameter pricelist version .
It should contain 2 product prices for the products A and B which equal base prices from the ''CUSTOMER OLD PLV'' + the surcharge from the ''Base PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS'' or if the ''CUSTOMER PRICING CONDITIONS'' has a line for any product ( no product or category set).
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.', Help='This process creates price list versions (PLV) for customer price lists that have the BasePricelist as the Pricelist of the selected Pricelist Version.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with PLV valid from the January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''Base PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM'' with the pricelist  schema ''Base PRICING CONDITIONS''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''CUSTOMER OLD PLV'' and Pricelist Schema = ''Base PRICING CONDITIONS'', which is taken from the process parameter pricelist version .
It should contain 2 product prices for the products A and B which equal base prices from the ''CUSTOMER OLD PLV'' + the surcharge from the ''Base PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS'' or if the ''CUSTOMER PRICING CONDITIONS'' has a line for any product ( no product or category set).
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.',Updated=TO_TIMESTAMP('2019-11-27 16:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-11-27T15:01:12.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='	
Kay Kostelnik
4:35 PM
EN Version
This process creates price list versions (PLV) for customer price lists that have the same BasePricelist as the Pricelist of the selected PLV.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with a PLV valid from January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''Base PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM'' with the Pricelist Schema ''Base PRICING CONDITIONS''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''CUSTOMER OLD PLV'' and Pricelist Schema = ''Base PRICING CONDITIONS'', which is taken from the process parameter ''pricelist version''.
It should contain 2 product prices for the products A and B which equal base prices from the ''CUSTOMER OLD PLV'' + the surcharge from the ''Base PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS'' or if the ''CUSTOMER PRICING CONDITIONS'' has a line for any product (no product or category set).
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.', Help='	
Kay Kostelnik
4:35 PM
EN Version
This process creates price list versions (PLV) for customer price lists that have the same BasePricelist as the Pricelist of the selected PLV.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with a PLV valid from January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''Base PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM'' with the Pricelist Schema ''Base PRICING CONDITIONS''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''CUSTOMER OLD PLV'' and Pricelist Schema = ''Base PRICING CONDITIONS'', which is taken from the process parameter ''pricelist version''.
It should contain 2 product prices for the products A and B which equal base prices from the ''CUSTOMER OLD PLV'' + the surcharge from the ''Base PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS'' or if the ''CUSTOMER PRICING CONDITIONS'' has a line for any product (no product or category set).
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.',Updated=TO_TIMESTAMP('2019-11-27 17:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-11-27T15:01:38.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-Preisliste eingestellt ist wie in der Preisliste der selektierten PLV.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''Basis PREISKONDITIONEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen mit dem Preisschema ''Basis PREISKONDITIONEN''.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die Basis-PLV = ''PLV KUNDEN ALT'' und das Preisschema = ''Basis PREISKONDITIONEN'', welches aus dem Prozessparameter ''Preislistenversion'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der ''PLV KUNDEN ALT'' + dem Aufschlag aus den ''Basis PREISKONDITIONEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind oder wenn in den ''PREISKONDITIONEN KUNDEN'' eine allgemeine Schemaposition für Produkte vorhanden ist (d.h. es ist kein Produkt und keine Produktkategorie eingestellt).
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.', Help='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-Preisliste eingestellt ist wie in der Preisliste der selektierten PLV.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''Basis PREISKONDITIONEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen mit dem Preisschema ''Basis PREISKONDITIONEN''.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die Basis-PLV = ''PLV KUNDEN ALT'' und das Preisschema = ''Basis PREISKONDITIONEN'', welches aus dem Prozessparameter ''Preislistenversion'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der ''PLV KUNDEN ALT'' + dem Aufschlag aus den ''Basis PREISKONDITIONEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind oder wenn in den ''PREISKONDITIONEN KUNDEN'' eine allgemeine Schemaposition für Produkte vorhanden ist (d.h. es ist kein Produkt und keine Produktkategorie eingestellt).
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.',Updated=TO_TIMESTAMP('2019-11-27 17:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541180
;

-- 2019-11-27T15:02:22.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions (PLV) for customer price lists that have the same BasePricelist as the Pricelist of the selected PLV.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with a PLV valid from January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''Base PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM'' with the Pricelist Schema ''Base PRICING CONDITIONS''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''CUSTOMER OLD PLV'' and Pricelist Schema = ''Base PRICING CONDITIONS'', which is taken from the process parameter ''pricelist version''.
It should contain 2 product prices for the products A and B which equal base prices from the ''CUSTOMER OLD PLV'' + the surcharge from the ''Base PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS'' or if the ''CUSTOMER PRICING CONDITIONS'' has a line for any product (no product or category set).
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.', Help='This process creates price list versions (PLV) for customer price lists that have the same BasePricelist as the Pricelist of the selected PLV.
Example:
Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with a PLV valid from January 1, 2019.
Have a PLV ''ORIGINAL BASE PLV''. This BasePLV has 3 product prices (e.g. A, B and C).
Have a Pricelist Schema (Pricing conditions) ''Base PRICING CONDITIONS'' with 2 lines for the products A and B.
Have a Pricing System ''CUSTOMER PRICING SYSTEM'' with a PLV ''CUSTOMER OLD PLV''.
This PLV has 2 product prices for the products A and B. The prices each are the sum of the prices from BasePLV and the surcharge from the Pricelist Schema.
Have a customer partner for whom the flag "Mutate Prices" is set on true. Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID for this customer.
Create a new PLV ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM'' with the Pricelist Schema ''Base PRICING CONDITIONS''.
Create the product prices for this PLV (e.g. using the process ''Create product prices'').
Select this new PLV and run the process "Apply to derivative price lists".
Results:
A new PLV will be created in ''CUSTOMER PRICING SYSTEM''.
It will have BasePLV = ''CUSTOMER OLD PLV'' and Pricelist Schema = ''Base PRICING CONDITIONS'', which is taken from the process parameter ''pricelist version''.
It should contain 2 product prices for the products A and B which equal base prices from the ''CUSTOMER OLD PLV'' + the surcharge from the ''Base PRICING CONDITIONS''.
Note:
Product prices are created only if the respective product (or its category) is included in ''CUSTOMER PRICING CONDITIONS'' or if the ''CUSTOMER PRICING CONDITIONS'' has a line for any product (no product or category set).
The new PLV will have the same ValidFrom date as ''ORIGINAL NEW PLV''.',Updated=TO_TIMESTAMP('2019-11-27 17:02:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-11-27T15:08:52.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-Preisliste eingestellt ist wie in der Preisliste der selektierten PLV.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''Basis PREISKONDITIONEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen mit dem Preisschema ''Basis PREISKONDITIONEN''.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die Basis-PLV = ''PLV KUNDEN ALT'' und das Preisschema = ''Basis PREISKONDITIONEN'', welches aus dem Prozessparameter ''Preislistenversion'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der ''PLV KUNDEN ALT'' + dem Aufschlag aus den ''Basis PREISKONDITIONEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind oder wenn in den ''PREISKONDITIONEN KUNDEN'' eine allgemeine Schemaposition für Produkte vorhanden ist (d.h. es ist kein Produkt und keine Produktkategorie eingestellt).
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.', Help='Mittels dieses Prozesses werden Preislistenversionen (PLV) für Kundenpreislisten erstellt, in denen dieselbe Basis-Preisliste eingestellt ist wie in der Preisliste der selektierten PLV.
Beispiel:
Ein Preissystem ''URSPR. BASISPREISSYSTEM'' mit einer ab dem 01. Januar 2019 gültigen PLV erstellen.
Eine PLV ''URSPR. BASIS-PLV'' erstellen. Diese Basis-PLV beinhaltet 3 Produkte (etwa A, B und C).
Ein Preisschema (Preiskonditionen) ''Basis PREISKONDITIONEN'' mit zwei Schemapositionen für Produkte A und B erstellen.
Ein Preissystem ''PREISSYSTEM KUNDEN'' mit einer PLV ''PLV KUNDEN ALT'' erstellen.
Diese PLV beinhaltet 2 Produktpreise für Produkte A und B. Die Preise bilden jeweils die Summe aus den Preisen aus der Basis-PLV und dem Aufschlag aus dem Preisschema.
Einen Kunden erfassen, für den das Kontrollkästchen "Preise mutieren" aktiviert ist. Für diesen Kunden ''PREISSYSTEM KUNDEN'' als M_Pricing_System_ID einstellen.
Im ''URSPR. BASISPREISSYSTEM'' eine neue PLV ''URSPR. PLV NEU'' erstellen mit dem Preisschema ''Basis PREISKONDITIONEN''.
Die Produktpreise für diese PLV erstellen (z.B. mittels Prozess ''Produktpreise erstellen'').
Diese neue PLV selektieren und den Prozess "Auf derivative Preislisten anwenden" starten.
Ergebnisse:
In dem ''PREISSYSTEM KUNDEN'' wird eine neue PLV erstellt.
Sie beinhaltet die Basis-PLV = ''PLV KUNDEN ALT'' und das Preisschema = ''Basis PREISKONDITIONEN'', welches aus dem Prozessparameter ''Preislistenversion'' übernommen wurde.
Sie beinhaltet 2 Produktpreise für Produkte A und B, die die Summe aus den Basis-Preisen der ''PLV KUNDEN ALT'' + dem Aufschlag aus den ''Basis PREISKONDITIONEN'' bilden.
Hinweis:
Produktpreise werden nur erstellt, wenn die jeweiligen Produkte (oder ihre Kategorien) in den ''PREISKONDITIONEN KUNDEN'' beinhaltet sind oder wenn in den ''PREISKONDITIONEN KUNDEN'' eine allgemeine Schemaposition für Produkte vorhanden ist (d.h. es ist kein Produkt und keine Produktkategorie eingestellt).
Die neue PLV hat dasselbe "Gültig ab"-Datum wie ''URSPR. PLV NEU''.',Updated=TO_TIMESTAMP('2019-11-27 17:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541180
;

