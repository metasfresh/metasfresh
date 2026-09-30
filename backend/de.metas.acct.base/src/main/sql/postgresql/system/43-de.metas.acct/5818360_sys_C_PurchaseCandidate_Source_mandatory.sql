-- Make C_PurchaseCandidate.Source mandatory: the sales-order interceptor and the dispo path both key on
-- Source to decide whether a candidate may be auto-ordered, so it must always be present.
-- Runs after the NULL backfill (lower prefix). Keeps AD_Column.IsMandatory in sync with the PG NOT NULL.
INSERT INTO t_alter_column values('C_PurchaseCandidate','Source',null,'NOT NULL',null)
;

UPDATE AD_Column SET IsMandatory='Y', Updated=TO_TIMESTAMP('2026-08-11 10:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE ColumnName='Source' AND AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_PurchaseCandidate')
;
