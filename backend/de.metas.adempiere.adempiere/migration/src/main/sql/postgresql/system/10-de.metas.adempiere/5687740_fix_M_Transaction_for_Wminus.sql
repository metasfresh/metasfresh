-- select migrationscript_ignore('10-de.metas.adempiere/5687740_fix_M_Transaction_for_Wminus.sql');

CREATE TABLE backup.m_transaction_before_Wminus_fix AS
SELECT *
FROM m_transaction
;

UPDATE m_transaction
SET movementqty= -movementqty
WHERE movementtype = 'W-'
;

