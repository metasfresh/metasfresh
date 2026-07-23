-- Source DDL: (removed — the function is no longer needed)
--
-- The plpgsql set-returning function {@code report_factoring_op_liste(numeric, numeric, numeric)}
-- was created by 5815400 to feed the Factoring OP-Liste export process. The export has been
-- rewritten to fetch + aggregate in Java (IQueryBL on I_C_Invoice + I_C_BPartner + I_C_DocType,
-- formatting in Java via FactoringOpListeCsvWriter), so the SQL function is dead code. Drop it
-- and remove its DDL file.

DROP FUNCTION IF EXISTS report_factoring_op_liste(numeric, numeric, numeric);
