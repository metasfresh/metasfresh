
-- Element: BaseLineType
-- 2023-04-19T10:19:12.832Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-19 13:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_CH'
;

-- 2023-04-19T10:19:12.859Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_CH') 
;

-- Element: BaseLineType
-- 2023-04-19T10:19:22.908Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-19 13:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-19T10:19:22.910Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-19T10:21:34.279Z
UPDATE AD_Element_Trl SET Description='The field that describes the date that will be considered as base line date when computing the due date.',Updated=TO_TIMESTAMP('2023-04-19 13:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-19T10:21:34.282Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-19T10:21:45.231Z
UPDATE AD_Element_Trl SET Description='The field that describes the date that will be considered as baseline date when computing the due date.',Updated=TO_TIMESTAMP('2023-04-19 13:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-19T10:21:45.234Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-19T10:21:56.481Z
UPDATE AD_Element_Trl SET Description='The field that describes the date that will be considered as the baseline date when computing the due date.',Updated=TO_TIMESTAMP('2023-04-19 13:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-19T10:21:56.483Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-19T10:22:09.567Z
UPDATE AD_Element_Trl SET Description='Das Feld, das das Datum beschreibt, das bei der Berechnung des Fälligkeitsdatums als Basisdatum betrachtet wird.',Updated=TO_TIMESTAMP('2023-04-19 13:22:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_DE'
;

-- 2023-04-19T10:22:09.569Z
UPDATE AD_Element SET Description='Das Feld, das das Datum beschreibt, das bei der Berechnung des Fälligkeitsdatums als Basisdatum betrachtet wird.' WHERE AD_Element_ID=582205
;

-- 2023-04-19T10:22:10.492Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582205,'de_DE') 
;

-- 2023-04-19T10:22:10.494Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_DE') 
;

-- Element: BaseLineType
-- 2023-04-19T10:22:13.113Z
UPDATE AD_Element_Trl SET Description='Das Feld, das das Datum beschreibt, das bei der Berechnung des Fälligkeitsdatums als Basisdatum betrachtet wird.',Updated=TO_TIMESTAMP('2023-04-19 13:22:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_CH'
;

-- 2023-04-19T10:22:13.115Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_CH') 
;

-- Reference Item: Base Line Types List -> ABL_After Bill of Landing
-- 2023-04-19T10:23:06.545Z
UPDATE AD_Ref_List_Trl SET Description='The actual loading date is taken from the delivery planning', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-19 13:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543428
;

-- Reference: Base Line Types List
-- Value: ABL
-- ValueName: After Bill of Landing
-- 2023-04-19T10:23:36.728Z
UPDATE AD_Ref_List SET Name='Nach der Anlandung (Bill of Loading)',Updated=TO_TIMESTAMP('2023-04-19 13:23:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543428
;

-- 2023-04-19T10:23:36.731Z
UPDATE AD_Ref_List_Trl trl SET Name='Nach der Anlandung (Bill of Loading)' WHERE AD_Ref_List_ID=543428 AND AD_Language='de_DE'
;

-- Reference: Base Line Types List
-- Value: ABL
-- ValueName: After Bill of Landing
-- 2023-04-19T10:24:41.442Z
UPDATE AD_Ref_List SET Name='Nach der Anlandung (Bill of Landing)',Updated=TO_TIMESTAMP('2023-04-19 13:24:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543428
;

-- 2023-04-19T10:24:41.444Z
UPDATE AD_Ref_List_Trl trl SET Name='Nach der Anlandung (Bill of Landing)' WHERE AD_Ref_List_ID=543428 AND AD_Language='de_DE'
;

-- Element: CalculationMethod
-- 2023-04-19T10:25:02.938Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-19 13:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='en_US'
;

-- 2023-04-19T10:25:02.940Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'en_US') 
;

-- Element: CalculationMethod
-- 2023-04-19T10:32:49.802Z
UPDATE AD_Element_Trl SET Description='This field explain how the due date will be computed.', Help='The "Calculation Method" field is used to determine the due date for payments based on the terms agreed upon between the buyer and seller. The field has three options:
* "Base Line Date +X days": This option adds a certain number of days (X) to the baseline date, which is typically the date of the invoice. For example, if the baseline date is January 1 and X is 30, the due date would be January 31.
* "Base Line Date +X days and then end of month": This option adds a certain number of days (X) to the baseline date, and then sets the due date to the end of that month. For example, if the baseline date is January 1 and X is 15, the due date would be January 31.
* "End of the month of baseline date plus X days": This option sets the due date to the end of the month in which the baseline date falls, and then adds a certain number of days (X). For example, if the baseline date is January 15 and X is 15, the due date would be February 15.',Updated=TO_TIMESTAMP('2023-04-19 13:32:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='en_US'
;

-- 2023-04-19T10:32:49.804Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'en_US') 
;

-- Element: CalculationMethod
-- 2023-04-19T10:34:58.575Z
UPDATE AD_Element_Trl SET Help='Das Feld "Berechnungsmethode" wird verwendet, um das Fälligkeitsdatum für Zahlungen auf der Grundlage der zwischen Käufer und Verkäufer vereinbarten Bedingungen zu bestimmen. Das Feld hat drei Optionen:
* "Basisdatum +X Tage": Diese Option fügt eine bestimmte Anzahl von Tagen (X) zum Basisdatum hinzu, das normalerweise das Rechnungsdatum ist. Wenn das Basisdatum beispielsweise der 1. Januar und X der 30. ist, wäre das Fälligkeitsdatum der 31. Januar.
* "Basisdatum +X Tage und dann Ende des Monats": Diese Option fügt dem Basisdatum eine bestimmte Anzahl von Tagen (X) hinzu und setzt dann das Fälligkeitsdatum auf das Ende dieses Monats. Wenn das Basisdatum beispielsweise der 1. Januar und X der 15. ist, wäre das Fälligkeitsdatum der 31. Januar.
* "Ende des Monats des Basisdatums plus X Tage": Diese Option setzt das Fälligkeitsdatum auf das Ende des Monats, in den das Basisdatum fällt, und fügt dann eine bestimmte Anzahl von Tagen (X) hinzu. Wenn das Basisdatum beispielsweise der 15. Januar ist und X der 15. ist, wäre das Fälligkeitsdatum der 15. Februar.',Updated=TO_TIMESTAMP('2023-04-19 13:34:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='de_DE'
;

-- 2023-04-19T10:34:58.578Z
UPDATE AD_Element SET Help='Das Feld "Berechnungsmethode" wird verwendet, um das Fälligkeitsdatum für Zahlungen auf der Grundlage der zwischen Käufer und Verkäufer vereinbarten Bedingungen zu bestimmen. Das Feld hat drei Optionen:
* "Basisdatum +X Tage": Diese Option fügt eine bestimmte Anzahl von Tagen (X) zum Basisdatum hinzu, das normalerweise das Rechnungsdatum ist. Wenn das Basisdatum beispielsweise der 1. Januar und X der 30. ist, wäre das Fälligkeitsdatum der 31. Januar.
* "Basisdatum +X Tage und dann Ende des Monats": Diese Option fügt dem Basisdatum eine bestimmte Anzahl von Tagen (X) hinzu und setzt dann das Fälligkeitsdatum auf das Ende dieses Monats. Wenn das Basisdatum beispielsweise der 1. Januar und X der 15. ist, wäre das Fälligkeitsdatum der 31. Januar.
* "Ende des Monats des Basisdatums plus X Tage": Diese Option setzt das Fälligkeitsdatum auf das Ende des Monats, in den das Basisdatum fällt, und fügt dann eine bestimmte Anzahl von Tagen (X) hinzu. Wenn das Basisdatum beispielsweise der 15. Januar ist und X der 15. ist, wäre das Fälligkeitsdatum der 15. Februar.' WHERE AD_Element_ID=582206
;

-- 2023-04-19T10:34:59.014Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582206,'de_DE') 
;

-- 2023-04-19T10:34:59.017Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'de_DE') 
;



---------------


-- Element: CalculationMethod
-- 2023-04-19T10:37:59.728Z
UPDATE AD_Element_Trl SET Description='The "Calculation Method" field is used to determine the due date for payments based on the terms agreed upon between the buyer and seller. The field has three options: * "Base Line Date +X days": This option adds a certain number of days (X) to the baseline date, which is typically the date of the invoice. For example, if the baseline date is January 1 and X is 30, the due date would be January 31. * "Base Line Date +X days and then end of month": This option adds a certain number of days (X) to the baseline date, and then sets the due date to the end of that month. For example, if the baseline date is January 1 and X is 15, the due date would be January 31. * "End of the month of baseline date plus X days": This option sets the due date to the end of the month in which the baseline date falls, and then adds a certain number of days (X). For example, if the baseline date is January 15 and X is 15, the due date would be February 15.',Updated=TO_TIMESTAMP('2023-04-19 13:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='en_US'
;

-- 2023-04-19T10:37:59.735Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'en_US') 
;

-- Element: CalculationMethod
-- 2023-04-19T10:38:57.154Z
UPDATE AD_Element_Trl SET Help='The "Calculation Method" field is used to determine the due date for payments based on the terms agreed upon between the buyer and seller. The field has three options:<br>
* "Base Line Date +X days": This option adds a certain number of days (X) to the baseline date, which is typically the date of the invoice. For example, if the baseline date is January 1 and X is 30, the due date would be January 31.<br>
* "Base Line Date +X days and then end of month": This option adds a certain number of days (X) to the baseline date, and then sets the due date to the end of that month. For example, if the baseline date is January 1 and X is 15, the due date would be January 31.<br>
* "End of the month of baseline date plus X days": This option sets the due date to the end of the month in which the baseline date falls, and then adds a certain number of days (X). For example, if the baseline date is January 15 and X is 15, the due date would be February 15.',Updated=TO_TIMESTAMP('2023-04-19 13:38:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='en_US'
;

-- 2023-04-19T10:38:57.156Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-19T10:47:26.043Z
UPDATE AD_Element_Trl SET Description='The field is used to determine as the baseline date when computing the due date. And it has three options:<br> * After Bill of Landing - is the Actual Loading Date from Delivery Instructions<br> * After Delivery -  the shipment/receipt date<br> * Invoice Date - the invoice date, as the name implies',Updated=TO_TIMESTAMP('2023-04-19 13:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-19T10:47:26.046Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-19T10:47:40.316Z
UPDATE AD_Element_Trl SET Description='Das Feld wird verwendet, um das Basisdatum bei der Berechnung des Fälligkeitsdatums zu bestimmen. Und es hat drei Optionen:<br> * Nach Frachtbrief – ist das tatsächliche Ladedatum aus den Lieferanweisungen<br> * Nach Lieferung - das Versand-/Erhaltsdatum<br> * Rechnungsdatum - das Rechnungsdatum, wie der Name schon sagt',Updated=TO_TIMESTAMP('2023-04-19 13:47:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_DE'
;

-- 2023-04-19T10:47:40.317Z
UPDATE AD_Element SET Description='Das Feld wird verwendet, um das Basisdatum bei der Berechnung des Fälligkeitsdatums zu bestimmen. Und es hat drei Optionen:<br> * Nach Frachtbrief – ist das tatsächliche Ladedatum aus den Lieferanweisungen<br> * Nach Lieferung - das Versand-/Erhaltsdatum<br> * Rechnungsdatum - das Rechnungsdatum, wie der Name schon sagt' WHERE AD_Element_ID=582205
;

-- 2023-04-19T10:47:40.679Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582205,'de_DE') 
;

-- 2023-04-19T10:47:40.685Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_DE') 
;

-- Element: BaseLineType
-- 2023-04-19T10:48:54.307Z
UPDATE AD_Element_Trl SET Description='Das Feld wird verwendet, um das Basisdatum bei der Berechnung des Fälligkeitsdatums zu bestimmen. Und es hat drei Optionen:<br>
* Nach Frachtbrief – ist das tatsächliche Ladedatum aus den Lieferanweisungen<br>
* Nach Lieferung - das Versand-/Erhaltsdatum<br>
* Rechnungsdatum - das Rechnungsdatum, wie der Name schon sagt',Updated=TO_TIMESTAMP('2023-04-19 13:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_DE'
;

-- 2023-04-19T10:48:54.309Z
UPDATE AD_Element SET Description='Das Feld wird verwendet, um das Basisdatum bei der Berechnung des Fälligkeitsdatums zu bestimmen. Und es hat drei Optionen:
<br>* Nach Frachtbrief – ist das tatsächliche Ladedatum aus den Lieferanweisungen<br>
* Nach Lieferung - das Versand-/Erhaltsdatum<br>
* Rechnungsdatum - das Rechnungsdatum, wie der Name schon sagt' WHERE AD_Element_ID=582205
;

-- 2023-04-19T10:48:54.640Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582205,'de_DE') 
;

-- 2023-04-19T10:48:54.642Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_DE') 
;

-- Element: BaseLineType
-- 2023-04-19T10:50:40.139Z
UPDATE AD_Element_Trl SET Description='The field is used to determine as the baseline date when computing the due date. And it has three options:<br>
* After Bill of Landing - is the Actual Loading Date from Delivery Instructions<br>
* After Delivery -  the shipment/receipt date<br>
* Invoice Date - the invoice date, as the name implies',Updated=TO_TIMESTAMP('2023-04-19 13:50:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-19T10:50:40.141Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-19T10:51:47.129Z
UPDATE AD_Element_Trl SET Description='The field is used to determine as the baseline date when computing the due date. And it has three options:
* After Bill of Landing - is the Actual Loading Date from Delivery Instructions
* After Delivery -  the shipment/receipt date
* Invoice Date - the invoice date, as the name implies',Updated=TO_TIMESTAMP('2023-04-19 13:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-19T10:51:47.132Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-19T10:48:54.307Z
UPDATE AD_Element_Trl SET Description='Das Feld wird verwendet, um das Basisdatum bei der Berechnung des Fälligkeitsdatums zu bestimmen. Und es hat drei Optionen:
* Nach Frachtbrief – ist das tatsächliche Ladedatum aus den Lieferanweisungen
* Nach Lieferung - das Versand-/Erhaltsdatum
* Rechnungsdatum - das Rechnungsdatum, wie der Name schon sagt',Updated=TO_TIMESTAMP('2023-04-19 13:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_DE'
;

-- 2023-04-19T10:48:54.309Z
UPDATE AD_Element SET Description='Das Feld wird verwendet, um das Basisdatum bei der Berechnung des Fälligkeitsdatums zu bestimmen. Und es hat drei Optionen:
* Nach Frachtbrief – ist das tatsächliche Ladedatum aus den Lieferanweisungen
* Nach Lieferung - das Versand-/Erhaltsdatum
* Rechnungsdatum - das Rechnungsdatum, wie der Name schon sagt' WHERE AD_Element_ID=582205
;

-- 2023-04-19T10:48:54.640Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582205,'de_DE') 
;

-- 2023-04-19T10:48:54.642Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_DE') 
;

-- Element: CalculationMethod
-- 2023-04-19T10:53:44.624Z
UPDATE AD_Element_Trl SET Description='The "Calculation Method" field is used to determine the due date for payments based on the terms agreed upon between the buyer and seller. The field has three options:
* "Base Line Date +X days": This option adds a certain number of days (X) to the baseline date, which is typically the date of the invoice. For example, if the baseline date is January 1 and X is 30, the due date would be January 31.
* "Base Line Date +X days and then end of month": This option adds a certain number of days (X) to the baseline date, and then sets the due date to the end of that month. For example, if the baseline date is January 1 and X is 15, the due date would be January 31.
* "End of the month of baseline date plus X days": This option sets the due date to the end of the month in which the baseline date falls, and then adds a certain number of days (X). For example, if the baseline date is January 15 and X is 15, the due date would be February 15.', Help='The "Calculation Method" field is used to determine the due date for payments based on the terms agreed upon between the buyer and seller. The field has three options:* "Base Line Date +X days": This option adds a certain number of days (X) to the baseline date, which is typically the date of the invoice. For example, if the baseline date is January 1 and X is 30, the due date would be January 31.* "Base Line Date +X days and then end of month": This option adds a certain number of days (X) to the baseline date, and then sets the due date to the end of that month. For example, if the baseline date is January 1 and X is 15, the due date would be January 31.* "End of the month of baseline date plus X days": This option sets the due date to the end of the month in which the baseline date falls, and then adds a certain number of days (X). For example, if the baseline date is January 15 and X is 15, the due date would be February 15.',Updated=TO_TIMESTAMP('2023-04-19 13:53:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='en_US'
;

-- 2023-04-19T10:53:44.628Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'en_US') 
;







-- Element: CalculationMethod
-- 2023-04-20T15:28:57.772Z
UPDATE AD_Element_Trl SET Description='This field is used to determine the due date for payments based on the terms agreed upon between the buyer and seller. It offers three options: 
* "Baseline date + X days": This option adds a certain number of days (X) to the baseline date, which is typically the date of the invoice. For example, if the baseline date is January 1 and X is 30, the due date would be January 31. 
* "Baseline date + X days to end of month": This option adds a certain number of days (X) to the baseline date and then sets the due date to the end of that month. For example, if the baseline date is January 1 and X is 15, the due date would be January 31. 
* "End of month of baseline date + X days": This option sets the due date to the end of the month in which the baseline date falls, and then adds a certain number of days (X) to it. For example, if the baseline date is January 15 and X is 15, the due date would be February 15.', Help='',Updated=TO_TIMESTAMP('2023-04-20 18:28:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='en_US'
;

-- 2023-04-20T15:28:57.796Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'en_US') 
;

-- Element: CalculationMethod
-- 2023-04-20T15:29:54.733Z
UPDATE AD_Element_Trl SET Description='Dieses Feld wird verwendet, um das Fälligkeitsdatum für Zahlungen auf Grundlage der zwischen Käufer und Verkäufer vereinbarten Bedingungen zu bestimmen. Es bietet drei Optionen: 
* "Basisdatum + X Tage": Diese Option verlängert das Basisdatum (i.d.R. das Datum der Rechnung) um eine bestimmte Anzahl von Tagen (X). Ist das Basisdatum z.B. der 1. Januar und X gleich 30, wäre das Fälligkeitsdatum der 31. Januar. 
* "Basisdatum + X Tage zum Monatsende": Diese Option verlängert das Basisdatum um eine bestimmte Anzahl von Tagen (X) und setzt dann das Fälligkeitsdatum auf das Ende dieses Monats. Ist das Basisdatum z.B. der 1. Januar und X gleich 15, wäre das Fälligkeitsdatum der 31. Januar. 
* "Ende des Monats des Basisdatums + X Tage": Diese Option setzt das Fälligkeitsdatum auf das Ende des Monats, in den das Basisdatum fällt, und verlängert es dann um eine bestimmte Anzahl von Tagen (X). Ist das Basisdatum z.B. der 15. Januar und X gleich 15, wäre das Fälligkeitsdatum der 15. Februar.', Help='',Updated=TO_TIMESTAMP('2023-04-20 18:29:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='de_DE'
;

-- 2023-04-20T15:29:54.735Z
UPDATE AD_Element SET Description='Dieses Feld wird verwendet, um das Fälligkeitsdatum für Zahlungen auf Grundlage der zwischen Käufer und Verkäufer vereinbarten Bedingungen zu bestimmen. Es bietet drei Optionen: 
* "Basisdatum + X Tage": Diese Option verlängert das Basisdatum (i.d.R. das Datum der Rechnung) um eine bestimmte Anzahl von Tagen (X). Ist das Basisdatum z.B. der 1. Januar und X gleich 30, wäre das Fälligkeitsdatum der 31. Januar. 
* "Basisdatum + X Tage zum Monatsende": Diese Option verlängert das Basisdatum um eine bestimmte Anzahl von Tagen (X) und setzt dann das Fälligkeitsdatum auf das Ende dieses Monats. Ist das Basisdatum z.B. der 1. Januar und X gleich 15, wäre das Fälligkeitsdatum der 31. Januar. 
* "Ende des Monats des Basisdatums + X Tage": Diese Option setzt das Fälligkeitsdatum auf das Ende des Monats, in den das Basisdatum fällt, und verlängert es dann um eine bestimmte Anzahl von Tagen (X). Ist das Basisdatum z.B. der 15. Januar und X gleich 15, wäre das Fälligkeitsdatum der 15. Februar.', Help='' WHERE AD_Element_ID=582206
;

-- 2023-04-20T15:29:55.606Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582206,'de_DE') 
;

-- 2023-04-20T15:29:55.607Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'de_DE') 
;

-- Element: BaseLineType
-- 2023-04-20T15:31:13.750Z
UPDATE AD_Element_Trl SET Description='The field is used to determine as the baseline date when computing the due date. And it has three options:  
* After Bill of Landing - is the Actual Loading Date from Delivery Instructions 
* After Delivery - the shipment/receipt date 
* Invoice Date - the invoice date, as the name implies',Updated=TO_TIMESTAMP('2023-04-20 18:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-20T15:31:13.752Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-20T15:31:53.439Z
UPDATE AD_Element_Trl SET Description='Das Feld wird verwendet, um das Basisdatum bei der Berechnung des Fälligkeitsdatums zu bestimmen. Und es hat drei Optionen:  
* Nach Frachtbrief – ist das tatsächliche Ladedatum aus den Lieferanweisungen 
* Nach Lieferung - das Versand-/Erhaltsdatum 
* Rechnungsdatum - das Rechnungsdatum, wie der Name schon sagt',Updated=TO_TIMESTAMP('2023-04-20 18:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_DE'
;

-- 2023-04-20T15:31:53.440Z
UPDATE AD_Element SET Description='Das Feld wird verwendet, um das Basisdatum bei der Berechnung des Fälligkeitsdatums zu bestimmen. Und es hat drei Optionen:  
* Nach Frachtbrief – ist das tatsächliche Ladedatum aus den Lieferanweisungen 
* Nach Lieferung - das Versand-/Erhaltsdatum 
* Rechnungsdatum - das Rechnungsdatum, wie der Name schon sagt' WHERE AD_Element_ID=582205
;

-- 2023-04-20T15:31:53.754Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582205,'de_DE') 
;

-- 2023-04-20T15:31:53.755Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_DE') 
;

-- Element: BaseLineType
-- 2023-04-20T15:31:57.068Z
UPDATE AD_Element_Trl SET Description='Das Feld wird verwendet, um das Basisdatum bei der Berechnung des Fälligkeitsdatums zu bestimmen. Und es hat drei Optionen:  
* Nach Frachtbrief – ist das tatsächliche Ladedatum aus den Lieferanweisungen 
* Nach Lieferung - das Versand-/Erhaltsdatum 
* Rechnungsdatum - das Rechnungsdatum, wie der Name schon sagt',Updated=TO_TIMESTAMP('2023-04-20 18:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_CH'
;

-- 2023-04-20T15:31:57.070Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_CH') 
;

-- Element: BaseLineType
-- 2023-04-20T15:33:44.280Z
UPDATE AD_Element_Trl SET Description='This field is used to determine the baseline date as the basis for calculating the due date. It offers three options: 
* After Bill of Lading: corresponds to the actual loading date from the delivery instructions. 
* After delivery: corresponds to the delivery/receipt date. 
* Invoice date: corresponds to the date of the invoice.',Updated=TO_TIMESTAMP('2023-04-20 18:33:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-20T15:33:44.282Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-20T15:34:05.429Z
UPDATE AD_Element_Trl SET Name='Baseline Date', PrintName='Baseline Date',Updated=TO_TIMESTAMP('2023-04-20 18:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-20T15:34:05.431Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-20T15:34:43.212Z
UPDATE AD_Element_Trl SET Description='Dieses Feld dient zur Bestimmung des Basisdatums als Grundlage für die Berechnung des Fälligkeitsdatums. Es bietet drei Optionen: 
* Nach Frachtbrief: entspricht dem tatsächlichen Verladedatum aus den Lieferanweisungen. 
* Nach Lieferung: entspricht dem Liefer-/Empfangsdatum. 
* Rechnungsdatum: entspricht dem Datum der Rechnung.', Name='Basisdatum', PrintName='Basisdatum',Updated=TO_TIMESTAMP('2023-04-20 18:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_DE'
;

-- 2023-04-20T15:34:43.213Z
UPDATE AD_Element SET Description='Dieses Feld dient zur Bestimmung des Basisdatums als Grundlage für die Berechnung des Fälligkeitsdatums. Es bietet drei Optionen: 
* Nach Frachtbrief: entspricht dem tatsächlichen Verladedatum aus den Lieferanweisungen. 
* Nach Lieferung: entspricht dem Liefer-/Empfangsdatum. 
* Rechnungsdatum: entspricht dem Datum der Rechnung.', Name='Basisdatum', PrintName='Basisdatum' WHERE AD_Element_ID=582205
;

-- 2023-04-20T15:34:43.594Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582205,'de_DE') 
;

-- 2023-04-20T15:34:43.595Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_DE') 
;

-- Element: BaseLineType
-- 2023-04-20T15:35:28.093Z
UPDATE AD_Element_Trl SET Description='Dieses Feld dient zur Bestimmung des Basisdatums als Grundlage für die Berechnung des Fälligkeitsdatums. Es bietet drei Optionen:  
* Nach Frachtbrief: entspricht dem tatsächlichen Verladedatum aus den Lieferanweisungen.  
* Nach Lieferung: entspricht dem Liefer-/Empfangsdatum.  
* Rechnungsdatum: entspricht dem Datum der Rechnung.', Name='Basisdatum', PrintName='Basisdatum',Updated=TO_TIMESTAMP('2023-04-20 18:35:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_CH'
;

-- 2023-04-20T15:35:28.095Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_CH') 
;

