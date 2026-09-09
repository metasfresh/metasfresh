-- F01010.4 Invoice Accounting Overrides
-- Task 2: Turn C_Invoice_Acct.AccountName (AD_Column 585480) into a List dropdown
-- of the 6 account concepts an invoice can post to.
-- The stored Value stays the technical concept string (P_Revenue_Acct, etc.) so
-- the posting matcher (InvoiceAcctRuleMatcher) keeps working unchanged.
-- DDL column type stays VARCHAR — only the AD reference is changed.
--
-- IDs allocated from idserver.metas.de on 2026-06-18:
--   AD_Reference    542108  (Invoice Posting Account Concept)
--   AD_Ref_List     544268  P_Revenue_Acct              / Erlöskonto
--   AD_Ref_List     544269  P_Expense_Acct              / Aufwandskonto
--   AD_Ref_List     544270  P_TradeDiscountGrant_Acct   / Gewährter Rabatt
--   AD_Ref_List     544271  P_TradeDiscountRec_Acct     / Erhaltener Rabatt
--   AD_Ref_List     544272  P_InventoryClearing_Acct    / Bestandsverrechnung
--   AD_Ref_List     544273  P_InvoicePriceVariance_Acct / Rechnungs-Preisabweichung

-- ===========================================================================
-- 1. AD_Reference  (ValidationType='L', EntityType='D')
-- ===========================================================================
INSERT INTO AD_Reference
    (AD_Client_ID, IsActive, Created, CreatedBy, IsOrderByValue,
     Updated, UpdatedBy, AD_Reference_ID, ValidationType, Name, AD_Org_ID, EntityType)
VALUES
    (0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'N',
     TO_TIMESTAMP('2026-06-18 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542108 /*From ID Server*/, 'L', 'Invoice Posting Account Concept', 0, 'D');

-- ===========================================================================
-- 2. AD_Reference_Trl — skeleton rows for all active system languages
-- ===========================================================================
INSERT INTO AD_Reference_Trl
    (AD_Language, AD_Reference_ID, Help, Name, Description,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help, t.Name, t.Description,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-18 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-18 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Reference_ID=542108
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID);

-- Override en_US name
UPDATE AD_Reference_Trl
SET Name='Invoice Posting Account Concept', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Reference_ID=542108;

-- ===========================================================================
-- 3. AD_Ref_List entries — German Name in base column, Value = technical name
-- ===========================================================================

-- 3.1 P_Revenue_Acct / Erlöskonto
INSERT INTO AD_Ref_List
    (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
     Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES
    (542108, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
     'Erlöskonto',
     TO_TIMESTAMP('2026-06-18 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
     544268 /*From ID Server*/, 'P_Revenue_Acct', 'P_Revenue_Acct', 0, NULL, 'D');

-- 3.2 P_Expense_Acct / Aufwandskonto
INSERT INTO AD_Ref_List
    (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
     Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES
    (542108, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
     'Aufwandskonto',
     TO_TIMESTAMP('2026-06-18 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
     544269 /*From ID Server*/, 'P_Expense_Acct', 'P_Expense_Acct', 0, NULL, 'D');

-- 3.3 P_TradeDiscountGrant_Acct / Gewährter Rabatt
INSERT INTO AD_Ref_List
    (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
     Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES
    (542108, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
     'Gewährter Rabatt',
     TO_TIMESTAMP('2026-06-18 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
     544270 /*From ID Server*/, 'P_TradeDiscountGrant_Acct', 'P_TradeDiscountGrant_Acct', 0, NULL, 'D');

-- 3.4 P_TradeDiscountRec_Acct / Erhaltener Rabatt
INSERT INTO AD_Ref_List
    (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
     Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES
    (542108, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
     'Erhaltener Rabatt',
     TO_TIMESTAMP('2026-06-18 10:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
     544271 /*From ID Server*/, 'P_TradeDiscountRec_Acct', 'P_TradeDiscountRec_Acct', 0, NULL, 'D');

-- 3.5 P_InventoryClearing_Acct / Bestandsverrechnung
INSERT INTO AD_Ref_List
    (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
     Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES
    (542108, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:00:07','YYYY-MM-DD HH24:MI:SS'), 100,
     'Bestandsverrechnung',
     TO_TIMESTAMP('2026-06-18 10:00:07','YYYY-MM-DD HH24:MI:SS'), 100,
     544272 /*From ID Server*/, 'P_InventoryClearing_Acct', 'P_InventoryClearing_Acct', 0, NULL, 'D');

-- 3.6 P_InvoicePriceVariance_Acct / Rechnungs-Preisabweichung
INSERT INTO AD_Ref_List
    (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
     Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES
    (542108, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:00:08','YYYY-MM-DD HH24:MI:SS'), 100,
     'Rechnungs-Preisabweichung',
     TO_TIMESTAMP('2026-06-18 10:00:08','YYYY-MM-DD HH24:MI:SS'), 100,
     544273 /*From ID Server*/, 'P_InvoicePriceVariance_Acct', 'P_InvoicePriceVariance_Acct', 0, NULL, 'D');

-- ===========================================================================
-- 4. AD_Ref_List_Trl — skeleton rows + English overrides for all 6 entries
-- ===========================================================================

-- Seed all languages for all 6 entries
INSERT INTO AD_Ref_List_Trl
    (AD_Language, AD_Ref_List_ID, Name, Description,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-18 10:00:09','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-18 10:00:09','YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Ref_List_ID IN (544268, 544269, 544270, 544271, 544272, 544273)
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- English overrides
UPDATE AD_Ref_List_Trl
SET Name='Revenue Account', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544268;

UPDATE AD_Ref_List_Trl
SET Name='Expense Account', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544269;

UPDATE AD_Ref_List_Trl
SET Name='Trade Discount Granted', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544270;

UPDATE AD_Ref_List_Trl
SET Name='Trade Discount Received', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544271;

UPDATE AD_Ref_List_Trl
SET Name='Inventory Clearing', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544272;

UPDATE AD_Ref_List_Trl
SET Name='Invoice Price Variance', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544273;

-- de_DE / de_CH: mark as translated (same text as German base)
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Ref_List_ID IN (544268, 544269, 544270, 544271, 544272, 544273);

-- ===========================================================================
-- 5. Repoint AD_Column 585480 to the new List reference
--    DDL column type (VARCHAR) is NOT changed — only the AD reference metadata
-- ===========================================================================
UPDATE AD_Column
SET AD_Reference_ID=17, AD_Reference_Value_ID=542108,
    Updated=TO_TIMESTAMP('2026-06-18 10:00:17','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=585480;

-- ===========================================================================
-- 6. Repoint AD_Field 710155 (window 541659) — set AD_Reference_Value_ID
--    so the field inherits the List lookup from the column
-- ===========================================================================
UPDATE AD_Field
SET AD_Reference_ID=17, AD_Reference_Value_ID=542108,
    Updated=TO_TIMESTAMP('2026-06-18 10:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=710155;

-- ===========================================================================
-- 7. Check constraint on C_Invoice_Acct.AccountName to enforce valid values
--    (column stays VARCHAR; constraint guards against invalid strings)
--    AccountName is optional — empty (NULL) means the override applies to every
--    account concept of that invoice/line (AC1), so NULL must explicitly pass.
-- ===========================================================================
SELECT db_alter_table('C_Invoice_Acct', 'ALTER TABLE public.C_Invoice_Acct ADD CONSTRAINT AccountName_Check
    CHECK (AccountName IS NULL OR AccountName IN (
        ''P_Revenue_Acct'',
        ''P_Expense_Acct'',
        ''P_TradeDiscountGrant_Acct'',
        ''P_TradeDiscountRec_Acct'',
        ''P_InventoryClearing_Acct'',
        ''P_InvoicePriceVariance_Acct''
    ))');

