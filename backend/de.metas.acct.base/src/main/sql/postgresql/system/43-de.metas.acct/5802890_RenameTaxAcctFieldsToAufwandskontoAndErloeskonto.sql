/*From ID Server*/

-- Rename the per-tax-rate G/L override fields on the Tax Rate window / Accounting tab so the
-- UI labels reflect what the fields actually do today:
--
--   "Sonstige Steuern" → "Aufwandskonto" (AD_Element 1438, columns T_Expense_Acct on
--                                          C_Tax_Acct + C_AcctSchema_Default)
--   "Erlös Konto"       → "Erlöskonto"     (AD_Element 53699, column T_Revenue_Acct on
--                                          C_Tax_Acct)
--
-- Technical context (kept here, not in user-facing Help text):
--   AD_Element 1438 was historically the AD label for the AP-tax-line account used when
--   C_Tax.IsSalesTax='Y' (the US/CA non-deductible sales-tax pass-through). After the
--   IsSalesTax cleanup in 5802880 it serves as the per-tax-rate override of P_Expense_Acct
--   for AP-invoice product-line postings, symmetric to AD_Element 53699 (T_Revenue_Acct)
--   on the sales side.
--
-- Approach: AD_Element_Trl is the source of truth. After updating each language row, call
-- update_TRL_Tables_On_AD_Element_TRL_Update(<AD_Element_ID>, <language>) — the metasfresh
-- helper that cascades changes to AD_Element base (when the language is base), AD_Column,
-- AD_Column_Trl, AD_Field, AD_Field_Trl, and AD_Tab/Window/Menu/Process_Para/PrintFormat
-- translations.

------------------------------------------------------------------------------------------
-- T_Expense_Acct → "Aufwandskonto" (AD_Element 1438)
------------------------------------------------------------------------------------------

UPDATE AD_Element_Trl SET
    Name='Aufwandskonto',
    PrintName='Aufwandskonto',
    Description='Steuerabhängiges Konto zur Verbuchung Aufwand',
    Help='Wenn ein Konto eingetragen ist, werden Einkaufsbuchungen mit dieser Steuer auf dieses Konto gebucht — unabhängig vom Aufwandskonto des Produkts.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.000','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=1438 AND AD_Language='de_DE'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1438,'de_DE')
;

UPDATE AD_Element_Trl SET
    Name='Aufwandskonto',
    PrintName='Aufwandskonto',
    Description='Steuerabhängiges Konto zur Verbuchung Aufwand',
    Help='Wenn ein Konto eingetragen ist, werden Einkaufsbuchungen mit dieser Steuer auf dieses Konto gebucht — unabhängig vom Aufwandskonto des Produkts.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.001','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=1438 AND AD_Language='de_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1438,'de_CH')
;

UPDATE AD_Element_Trl SET
    Name='Expense account',
    PrintName='Expense account',
    Description='Tax-dependent account for booking expense',
    Help='If a value is set, purchases using this tax are posted to this account instead of the product''s default expense account.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.002','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=1438 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1438,'en_US')
;

UPDATE AD_Element_Trl SET
    Name='Expense account',
    PrintName='Expense account',
    Description='Tax-dependent account for booking expense',
    Help='If a value is set, purchases using this tax are posted to this account instead of the product''s default expense account.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.003','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=1438 AND AD_Language='en_GB'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1438,'en_GB')
;

UPDATE AD_Element_Trl SET
    Name='Compte de charges',
    PrintName='Compte de charges',
    Description='Compte dépendant de la taxe pour la comptabilisation des charges',
    Help='Si un compte est défini, les achats utilisant cette taxe sont comptabilisés sur ce compte au lieu du compte de charges habituel du produit.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.004','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=1438 AND AD_Language='fr_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1438,'fr_CH')
;

UPDATE AD_Element_Trl SET
    Name='Conto di spesa',
    PrintName='Conto di spesa',
    Description='Conto dipendente dall''imposta per la contabilizzazione delle spese',
    Help='Se viene impostato un conto, gli acquisti che utilizzano questa imposta sono contabilizzati su questo conto invece che sul conto di spesa abituale del prodotto.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.005','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=1438 AND AD_Language='it_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1438,'it_CH')
;

------------------------------------------------------------------------------------------
-- T_Revenue_Acct → "Erlöskonto" (AD_Element 53699; was "Erlös Konto", normalise to one word)
-- Description / Help left as-is — the semantic is unchanged, only the spelling normalises.
------------------------------------------------------------------------------------------

UPDATE AD_Element_Trl SET
    Name='Erlöskonto',
    PrintName='Erlöskonto',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.010','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=53699 AND AD_Language='de_DE'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53699,'de_DE')
;

UPDATE AD_Element_Trl SET
    Name='Erlöskonto',
    PrintName='Erlöskonto',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.011','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=53699 AND AD_Language='de_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53699,'de_CH')
;

UPDATE AD_Element_Trl SET
    Name='Revenue account',
    PrintName='Revenue account',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.012','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=53699 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53699,'en_US')
;

UPDATE AD_Element_Trl SET
    Name='Revenue account',
    PrintName='Revenue account',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.013','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=53699 AND AD_Language='en_GB'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53699,'en_GB')
;

UPDATE AD_Element_Trl SET
    Name='Compte de revenus',
    PrintName='Compte de revenus',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.014','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=53699 AND AD_Language='fr_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53699,'fr_CH')
;

UPDATE AD_Element_Trl SET
    Name='Conto ricavi',
    PrintName='Conto ricavi',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-18 16:00:00.015','YYYY-MM-DD HH24:MI:SS.US'),
    UpdatedBy=100
 WHERE AD_Element_ID=53699 AND AD_Language='it_CH'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53699,'it_CH')
;
