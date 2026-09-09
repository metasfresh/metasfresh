-- Run mode: SWING_CLIENT

-- gh#29558 F00127.1 Single price for bundle
-- 1. Add new ref-list value 'B' = "Bestandteil Handelsstückliste" to AD_Reference 541968
--    (Reason for without charge — existing values: W/G/F/P/I)
-- 2. Retype C_InvoiceLine.Reason + C_Invoice_Candidate.Reason from plain Reference 14 (String)
--    to Reference 17 (List) backed by AD_Reference 541968, so they align with C_OrderLine.Reason
--    and the propagation OL → IC → IL stores the ref-list code consistently.

-- =============================================================
-- 1) AD_Ref_List: new value 'B'
-- =============================================================
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID/*From ID Server*/,AD_Reference_ID,Value,Name,EntityType,IsActive,Created,CreatedBy,Updated,UpdatedBy)
VALUES (0,0,544237,541968,'B',$msg$Bestandteil Handelsstückliste$msg$,'D','Y',TO_TIMESTAMP('2026-05-28 06:50:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-05-28 06:50:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,544237,
       CASE
         WHEN l.AD_Language IN ('de_CH','de_DE') THEN $msg$Bestandteil Handelsstückliste$msg$
         WHEN l.AD_Language IN ('en_US','en_GB') THEN 'Component of bundle'
         WHEN l.AD_Language='fr_CH' THEN 'Composant de lot'
         ELSE $msg$Bestandteil Handelsstückliste$msg$
       END,
       CASE WHEN l.AD_Language IN ('de_CH','de_DE') THEN 'N' ELSE 'Y' END,
       0,0,TO_TIMESTAMP('2026-05-28 06:50:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-05-28 06:50:01','YYYY-MM-DD HH24:MI:SS'),100,'Y'
FROM AD_Language l
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID=544237 AND tt.AD_Language=l.AD_Language)
;

-- =============================================================
-- 2) Retype C_InvoiceLine.Reason: Reference 14 (String) → Reference 17 (List) backed by 541968
-- =============================================================
UPDATE AD_Column
SET AD_Reference_ID=17, AD_Reference_Value_ID=541968, FieldLength=1, Updated=TO_TIMESTAMP('2026-05-28 06:50:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592640
;

-- =============================================================
-- 3) Retype C_Invoice_Candidate.Reason: Reference 14 (String) → Reference 17 (List) backed by 541968
-- =============================================================
UPDATE AD_Column c
SET AD_Reference_ID=17, AD_Reference_Value_ID=541968, FieldLength=1, Updated=TO_TIMESTAMP('2026-05-28 06:50:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM AD_Table t
WHERE c.AD_Table_ID=t.AD_Table_ID AND t.TableName='C_Invoice_Candidate' AND c.ColumnName='Reason'
;

-- =============================================================
-- 4) Physical column type changes — the column currently is VARCHAR(4000) on both tables.
--    Switch to CHAR(1) NULL via t_alter_column (compatible: existing rows are NULL).
-- =============================================================
INSERT INTO t_alter_column values('c_invoiceline','Reason','CHAR(1)',null,null);
INSERT INTO t_alter_column values('c_invoice_candidate','Reason','CHAR(1)',null,null);
