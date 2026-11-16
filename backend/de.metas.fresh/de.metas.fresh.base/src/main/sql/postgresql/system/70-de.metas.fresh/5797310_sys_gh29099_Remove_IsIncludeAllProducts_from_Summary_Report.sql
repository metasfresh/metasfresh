-- gh#29099: Remove IsIncludeAllProducts parameter from the Summary Report process.
-- In the summary report, products without packaging material assignments cannot contribute
-- to the pivot table (they have no material to group by), so the parameter is a no-op.
-- Keeping it confuses users into thinking it affects the summary output.

-- Deactivate the parameter on the summary report (AD_Process_ID=585504)
UPDATE AD_Process_Para
SET IsActive = 'N',
    Updated = '2026-04-07 16:00',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 543159; -- IsIncludeAllProducts on Package-Licencing-Summary-Report

-- Remove the parameter from the SQLStatement — the function still accepts it with DEFAULT 'Y'
UPDATE AD_Process
SET SQLStatement = 'SELECT * FROM report.Package_Licensing_InOut_Summary_Report(''@DateFrom@''::timestamp with time zone, ''@DateTo@''::timestamp with time zone, @C_Country_ID/null@)',
    Updated = '2026-04-07 16:00',
    UpdatedBy = 0
WHERE AD_Process_ID = 585504;
