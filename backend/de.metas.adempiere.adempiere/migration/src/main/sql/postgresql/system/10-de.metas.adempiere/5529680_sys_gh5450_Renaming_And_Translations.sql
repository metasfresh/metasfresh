-- 2019-09-05T13:28:38.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=276447230,Updated=TO_TIMESTAMP('2019-09-05 15:28:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2810
;

-- 2019-09-05T13:28:42.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=276447231,Updated=TO_TIMESTAMP('2019-09-05 15:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2810
;

-- 2019-09-05T13:28:45.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_process','Description','TEXT',null,null)
;
INSERT INTO t_alter_column values('ad_process_trl','Description','TEXT',null,null)
;

-- 2019-09-05T13:30:09.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=276447231,Updated=TO_TIMESTAMP('2019-09-05 15:30:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2811
;

-- 2019-09-05T13:30:09.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_process','Help','TEXT',null,null)
;
INSERT INTO t_alter_column values('ad_process_trl','Help','TEXT',null,null)
;



COMMIT;

-- 2019-09-02T10:18:54.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N', Name='Apply to derived price lists',Updated=TO_TIMESTAMP('2019-09-02 13:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T10:19:27.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N', Name='PLV from base', PrintName='PLV from base',Updated=TO_TIMESTAMP('2019-09-02 13:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577030 AND AD_Language='en_US'
;

-- 2019-09-02T10:19:28.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577030,'en_US') 
;

-- 2019-09-02T10:21:14.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Create product prices',Updated=TO_TIMESTAMP('2019-09-02 13:21:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=103
;

-- 2019-09-02T11:36:56.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
	Example: 
	* Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
	* Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
	* Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
	* Have Pricing System ''CUSTOMER PRICING SYSTEM'' with 
		* a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''
		* This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
	* Have a customer partner for which the flag "PLV from base" is set on true.
	* Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID  for this customer.
	* Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''
		* ''ORIGINAL NEW PLV'' will have base PLV = ''ORIGINAL BASE PLV''
		* Create the product prices for this PLV (e.g. using the process ''Create product prices''
	* Select this new Price List Version and run the process "Mutate customer Prices":
		* A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
		* BasePLV = ''ORIGINAL BASE PLV''
		* Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''
		* It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
			* There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''
		* The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed. but it has the same validFrom date.
		* The new PLV an',Updated=TO_TIMESTAMP('2019-09-02 14:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541180
;

-- 2019-09-02T11:37:22.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
Example: 
* Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
* Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
* Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
* Have Pricing System ''CUSTOMER PRICING SYSTEM'' with 
	* a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''
	* This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
* Have a customer partner for which the flag "PLV from base" is set on true.
* Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID  for this customer.
* Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''
	* ''ORIGINAL NEW PLV'' will have base PLV = ''ORIGINAL BASE PLV''
	* Create the product prices for this PLV (e.g. using the process ''Create product prices''
* Select this new Price List Version and run the process "Mutate customer Prices":
	* A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
	* BasePLV = ''ORIGINAL BASE PLV''
	* Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''
	* It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
		* There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''
	* The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed. but it has the same validFrom date.
	* The new PLV and ''ORIGINAL NEW PLV''',Updated=TO_TIMESTAMP('2019-09-02 14:37:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541180
;

-- 2019-09-02T11:38:38.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='', Help='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
Example: 
* Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
* Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
* Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
* Have Pricing System ''CUSTOMER PRICING SYSTEM'' with 
	* a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''
	* This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
* Have a customer partner for which the flag "PLV from base" is set on true.
* Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID  for this customer.
* Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''
	* ''ORIGINAL NEW PLV'' will have base PLV = ''ORIGINAL BASE PLV''
	* Create the product prices for this PLV (e.g. using the process ''Create product prices''
* Select this new Price List Version and run the process "Mutate customer Prices":
	* A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
	* BasePLV = ''ORIGINAL BASE PLV''
	* Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''
	* It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
		* There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''
	* The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed. but it has the same validFrom date.
	* The new PLV and ''ORIGINAL NEW PLV''',Updated=TO_TIMESTAMP('2019-09-02 14:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541180
;

-- 2019-09-02T11:42:49.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
Example: 
* Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
* Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
* Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
* Have Pricing System ''CUSTOMER PRICING SYSTEM'' with 
	* a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''
	* This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
* Have a customer partner for which the flag "PLV from base" is set on true.
* Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID  for this customer.
* Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''
	* ''ORIGINAL NEW PLV'' will have base PLV = ''ORIGINAL BASE PLV''
	* Create the product prices for this PLV (e.g. using the process ''Create product prices''
* Select this new Price List Version and run the process "Mutate customer Prices":
	* A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
	* BasePLV = ''ORIGINAL BASE PLV''
	* Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''
	* It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
		* There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''
	* The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed. but it has the same validFrom date.
	* The new PLV and ''ORIGINAL NEW PLV''',Updated=TO_TIMESTAMP('2019-09-02 14:42:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T11:42:50.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-02 14:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T11:43:28.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Help='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
Example: 
* Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
* Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
* Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
* Have Pricing System ''CUSTOMER PRICING SYSTEM'' with 
	* a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''
	* This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
* Have a customer partner for which the flag "PLV from base" is set on true.
* Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID  for this customer.
* Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''
	* ''ORIGINAL NEW PLV'' will have base PLV = ''ORIGINAL BASE PLV''
	* Create the product prices for this PLV (e.g. using the process ''Create product prices''
* Select this new Price List Version and run the process "Mutate customer Prices":
	* A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
	* BasePLV = ''ORIGINAL BASE PLV''
	* Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''
	* It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
		* There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''
	* The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed. but it has the same validFrom date.
	* The new PLV and ''ORIGINAL NEW PLV''',Updated=TO_TIMESTAMP('2019-09-02 14:43:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T11:44:11.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
Example: 
* Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
* Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
* Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
* Have Pricing System ''CUSTOMER PRICING SYSTEM'' with 
	* a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''
	* This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
* Have a customer partner for which the flag "PLV from base" is set on true.
* Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID  for this customer.
* Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''
	* ''ORIGINAL NEW PLV'' will have base PLV = ''ORIGINAL BASE PLV''
	* Create the product prices for this PLV (e.g. using the process ''Create product prices''
* Select this new Price List Version and run the process "Mutate customer Prices":
	* A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
	* BasePLV = ''ORIGINAL BASE PLV''
	* Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''
	* It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
		* There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''
	* The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed. but it has the same validFrom date.
	* The new PLV and ''ORIGINAL NEW PLV''',Updated=TO_TIMESTAMP('2019-09-02 14:44:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T12:05:57.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 

Example: 
* Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 

* Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 

* Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.

* Have Pricing System ''CUSTOMER PRICING SYSTEM'' with :

	* a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''

	* This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 

* Have a customer partner for which the flag "PLV from base" is set on true.

* Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID  for this customer.

* Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''

	* ''ORIGINAL NEW PLV'' will have base PLV = ''ORIGINAL BASE PLV''

	* Create the product prices for this PLV (e.g. using the process ''Create product prices''

* Select this new Price List Version and run the process "Mutate customer Prices":

	* A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.

	* BasePLV = ''ORIGINAL BASE PLV''

	* Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''

	* It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 

		* There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''

	* The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed. but it has the same validFrom date.

	* The new PLV an',Updated=TO_TIMESTAMP('2019-09-02 15:05:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T12:06:04.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='',Updated=TO_TIMESTAMP('2019-09-02 15:06:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T12:06:30.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 

Example: 
* Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 

* Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 

* Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.

* Have Pricing System ''CUSTOMER PRICING SYSTEM'' with :

	* a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''

	* This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 

* Have a customer partner for which the flag "PLV from base" is set on true.

* Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID  for this customer.

* Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''

	* ''ORIGINAL NEW PLV'' will have base PLV = ''ORIGINAL BASE PLV''

	* Create the product prices for this PLV (e.g. using the process ''Create product prices''

* Select this new Price List Version and run the process "Mutate customer Prices":

	* A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.

	* BasePLV = ''ORIGINAL BASE PLV''

	* Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''

	* It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 

		* There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''

	* The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed. but it has the same validFrom date.

	* The new PLV an', Help='',Updated=TO_TIMESTAMP('2019-09-02 15:06:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T12:07:46.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
Example: 
* Have a Pricing System ''ORIGINAL BASE PRICING SYSTEM'' with Price List Version valid from the first of January 2019. 
* Have a price list version ''ORIGINAL BASE PLV''. This BasePLV has 3 Product Prices (let''s say A, B  and C). 
* Have a Pricelist Schema ( Pricing conditions) ''CUSTOMER PRICING CONDITIONS'' with 2 lines for the products A and B.
* Have Pricing System ''CUSTOMER PRICING SYSTEM'' with 
	* a Price List Version ''CUSTOMER OLD PLV'' with Base Pricelist Version = ''ORIGINAL BASE PLV'' and Pricelist Schema = ''CUSTOMER PRICING CONDITIONS''
	* This PLV has 2 product prices for the product A and B. The prices are the sum betweem the prices from BasePLV and the surcharge from the Pricelist Schema. 
* Have a customer partner for which the flag "PLV from base" is set on true.
* Set ''CUSTOMER PRICING SYSTEM'' as M_Pricing_System_ID  for this customer.
* Create a new Price List Version ''ORIGINAL NEW PLV'' in the ''ORIGINAL BASE PRICING SYSTEM''
	* ''ORIGINAL NEW PLV'' will have base PLV = ''ORIGINAL BASE PLV''
	* Create the product prices for this PLV (e.g. using the process ''Create product prices''
* Select this new Price List Version and run the process "Mutate customer Prices":
	* A new Price List Version should be created in ''CUSTOMER PRICING SYSTEM''.
	* BasePLV = ''ORIGINAL BASE PLV''
	* Pricelist Schema = ''CUSTOMER PRICING CONDITIONS'', which is taken from ''CUSTOMER OLD PLV''
	* It should contain 2 product prices, for the product A and B which equal base prices from the BasePLV + the surcharge from the ''CUSTOMER PRICING CONDITIONS''. 
		* There are product prices only if the respective product (or its category) is covered by ''CUSTOMER PRICING CONDITIONS''
	* The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed. but it has the same validFrom date.
	* The new PLV and ''ORIGINAL NEW PLV''',Updated=TO_TIMESTAMP('2019-09-02 15:07:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T12:11:43.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
Example: 
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
The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed, but it has the same validFrom date.
The new PLV and ''ORIGINAL NEW PLV'' and ''CUSTOMER OLD PLV'' all',Updated=TO_TIMESTAMP('2019-09-02 15:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T12:12:33.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
Example: 
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
The new PLV is not linked to the ''ORIGINAL NEW PLV'' that was selected when "Mutate customer Prices" was executed, but it has the same validFrom date.
The new PLV and ''ORIGINAL NEW PLV'' and ''CUSTOMER OLD PLV'' all ',Updated=TO_TIMESTAMP('2019-09-02 15:12:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

-- 2019-09-02T12:13:54.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process creates price list versions for the customer pricelists that have the same base price list version as the one selected. 
Example: 
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
',Updated=TO_TIMESTAMP('2019-09-02 15:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541180
;

