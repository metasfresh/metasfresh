-- Create the missing standard currency rows RUB (Russian Ruble) and TRY (Turkish Lira).
-- Created INACTIVE (IsActive='N') so existing instances are unaffected; activation is a
-- separate per-instance decision. Matches the seed shape of the existing C_Currency rows
-- (client 1000000 / org 0, StdPrecision=2, CostingPrecision=4, non-EUR, non-EMU).
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5817430 (this script)
--   C_Currency         540005  (RUB)
--   C_Currency         540006  (TRY)

INSERT INTO C_Currency (
    C_Currency_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ISO_Code, CurSymbol, Description, StdPrecision, CostingPrecision,
    IsEuro, IsEMUMember, EMURate, ISO_4217Numeric
) VALUES (
    540005 /*From ID Server*/, 1000000, 0, 'N',
    TO_TIMESTAMP('2026-08-03 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-08-03 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0,
    'RUB', 'руб', 'Rubel (100 Kopeken)', 2, 4,
    'N', 'N', 0, '643'
);

INSERT INTO C_Currency (
    C_Currency_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ISO_Code, CurSymbol, Description, StdPrecision, CostingPrecision,
    IsEuro, IsEMUMember, EMURate, ISO_4217Numeric
) VALUES (
    540006 /*From ID Server*/, 1000000, 0, 'N',
    TO_TIMESTAMP('2026-08-03 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 0, TO_TIMESTAMP('2026-08-03 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 0,
    'TRY', '₺', 'Lira (100 Kuruş)', 2, 4,
    'N', 'N', 0, '949'
);

-- Backfill C_Currency_Trl rows for every active language (C_Currency has a _Trl companion).
SELECT add_missing_translations();

-- English override for the two new rows (C_Currency_Trl has a _Trl companion → backup first).
SELECT backup_table('c_currency_trl', '_create_rub_try');

UPDATE C_Currency_Trl SET Description = 'Ruble (100 Kopeks)', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-08-03 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 0
WHERE AD_Language = 'en_US' AND C_Currency_ID = 540005;

UPDATE C_Currency_Trl SET Description = 'Lira (100 Kurus)', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-08-03 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 0
WHERE AD_Language = 'en_US' AND C_Currency_ID = 540006;
