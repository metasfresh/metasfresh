-- gh#29279: Change the shipment report's PRINTER_OPTS_IsPrintLogo default to use @SQL= with a context variable.
-- This exercises DB.resolveSqlDefaultValue() end-to-end: @Variable@ resolution via Env.parseContext + SQL execution.
-- The result is 'Y' (same as before) since @#AD_Client_ID@ is always > 0 in a real session.
-- 2026-04-14

UPDATE AD_Process_Para
SET DefaultValue = '@SQL=SELECT CASE WHEN @#AD_Client_ID@ > 0 THEN ''Y'' ELSE ''N'' END',
    Updated = now(),
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 541909  -- Lieferschein (Jasper) / PRINTER_OPTS_IsPrintLogo
  AND DefaultValue = 'Y'           -- only change if still the static default (idempotent)
;
