-- Forced ordering (sub-slot): must run before 5665510_sys_gh13758_DateTrx_C_Payment.sql
-- That script converts C_Payment.DateTrx, C_BankStatement.StatementDate and C_BankStatementLine.ValutaDate/DateAcct/StatementLineDate
-- to TIMESTAMP WITH TIME ZONE, which rewrites those tables. Back them up first.
-- A table is backed up only while its column is still TIMESTAMP WITHOUT TIME ZONE, so DBs that already applied the conversion are not copied again.
DO
$$
    BEGIN
        IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'c_payment' AND column_name = 'datetrx' AND data_type = 'timestamp without time zone') THEN
            PERFORM backup_table('c_payment');
        END IF;
        IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'c_bankstatement' AND column_name = 'statementdate' AND data_type = 'timestamp without time zone') THEN
            PERFORM backup_table('c_bankstatement');
        END IF;
        IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'c_bankstatementline' AND column_name IN ('valutadate', 'dateacct', 'statementlinedate') AND data_type = 'timestamp without time zone') THEN
            PERFORM backup_table('c_bankstatementline');
        END IF;
    END
$$
;
