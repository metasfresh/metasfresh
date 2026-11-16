-- gh#28967: Fix SQLStatement quoting for Package Licensing reports
-- Date parameters must be quoted and cast to timestamp, otherwise the raw
-- substitution produces invalid SQL like: 2026-03-01 00:00:00.0 (unquoted)

-- Fix detail report (585503)
UPDATE AD_Process
SET SQLStatement = 'SELECT * FROM report.Package_Licensing_InOut_Report(''@DateFrom@''::timestamp with time zone, ''@DateTo@''::timestamp with time zone, @C_Country_ID/null@, ''@IsIncludeAllProducts@'')',
    Updated      = '2026-03-21 18:00',
    UpdatedBy    = 100
WHERE AD_Process_ID = 585503;

-- Fix summary report (585504)
UPDATE AD_Process
SET SQLStatement = 'SELECT * FROM report.Package_Licensing_InOut_Summary_Report(''@DateFrom@''::timestamp with time zone, ''@DateTo@''::timestamp with time zone, @C_Country_ID/null@, ''@IsIncludeAllProducts@'')',
    Updated      = '2026-03-21 18:00',
    UpdatedBy    = 100
WHERE AD_Process_ID = 585504;

-- Fix product report (585578)
UPDATE AD_Process
SET SQLStatement = 'SELECT * FROM report.Package_Licensing_Product_Report(''@DateFrom@''::timestamp with time zone, ''@DateTo@''::timestamp with time zone, @C_Country_ID/null@, ''@IsIncludeAllProducts@'')',
    Updated      = '2026-03-21 18:00',
    UpdatedBy    = 100
WHERE AD_Process_ID = 585578;
