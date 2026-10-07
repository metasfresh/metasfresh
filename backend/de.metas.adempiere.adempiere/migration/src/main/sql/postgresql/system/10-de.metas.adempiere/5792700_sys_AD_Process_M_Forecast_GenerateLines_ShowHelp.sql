-- Migration: Change ShowHelp from 'S' (Run Silently) to 'Y' (Show Help) for M_Forecast_GenerateLines
-- Reason: The process has 5 optional parameters (comparison period, precision unit, product category,
--         product, delete existing lines) that users should be able to set before execution.
--         ShowHelp='S' skips the parameter form and runs with defaults, which is not desirable here.

UPDATE AD_Process SET
  ShowHelp='Y',
  Updated=TO_TIMESTAMP('2026-03-07 14:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585593;
