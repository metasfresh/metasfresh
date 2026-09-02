-- gh26253: retire the "Initialize Moving Average Invoice Costing" process (AD_Process_ID=585629).
--
-- The process seeded M_Cost straight from the current live cost price - with no cut-off date and
-- with no backing M_CostDetail row - and then flipped C_AcctSchema.CostingMethod to 'M'.
-- That seeding half has been superseded by the M_CostRevaluation document
-- (RevaluationSource=CopyFromCostElement), which seeds as of a cut-off date and leaves a proper
-- cost-detail anchor behind. Keeping the old process around only lets date-blind, anchor-less
-- M_Cost rows be created for whatever the correct path skipped.
--
-- The process registration also made C_AcctSchema.CostingMethod read-only on every window; that
-- lock is released here (step 3). No server-side guard replaces it: a switch is not checked, by
-- design. A seeding check was considered and rejected - the only cheap check available is
-- schema-wide presence of M_CostDetail rows for the target cost element, whereas the defect it
-- would have to catch is PER-PRODUCT rows that exist but carry amount 0. Those rows do exist in
-- the incident this script comes from, so such a guard would have permitted that very switch.
--
-- The PL/pgSQL function behind the process is dropped by the next migration script.

-- 1) Drop the process and its document action on the Accounting Schema window
DELETE FROM AD_Table_Process WHERE AD_Process_ID=585629
;

DELETE FROM AD_Process_Trl WHERE AD_Process_ID=585629
;

DELETE FROM AD_Process WHERE AD_Process_ID=585629
;

-- 2) Drop the AD_Element that was created solely for this process (AD_Element_ID=584927).
--    AD_Process carries no AD_Element_ID, so the element was already unused; it is removed only if
--    nothing references it. The guard enumerates every table that carries a foreign key to
--    AD_Element (ad_column, ad_element_link, ad_infocolumn, ad_menu, ad_process_para, ad_tab,
--    ad_window, webui_kpi_field, plus the two AD_Name_ID holders ad_field and ad_ui_element).
--    If any reference exists, both statements below are no-ops and the element stays.
DELETE FROM AD_Element_Trl
WHERE AD_Element_ID=584927
  AND NOT EXISTS (
      SELECT 1 FROM AD_Column x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Element_Link x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_InfoColumn x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Menu x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Process_Para x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Tab x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Window x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM WEBUI_KPI_Field x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Field x WHERE x.AD_Name_ID=584927
      UNION ALL SELECT 1 FROM AD_UI_Element x WHERE x.AD_Name_ID=584927
  )
;

DELETE FROM AD_Element
WHERE AD_Element_ID=584927
  AND NOT EXISTS (
      SELECT 1 FROM AD_Column x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Element_Link x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_InfoColumn x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Menu x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Process_Para x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Tab x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Window x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM WEBUI_KPI_Field x WHERE x.AD_Element_ID=584927
      UNION ALL SELECT 1 FROM AD_Field x WHERE x.AD_Name_ID=584927
      UNION ALL SELECT 1 FROM AD_UI_Element x WHERE x.AD_Name_ID=584927
  )
;

-- 3) Release the read-only lock the process registration put on C_AcctSchema.CostingMethod.
--    The lock was field-level only, so any window added after it was already editable; unlocking
--    every active field makes the state uniform again.
--    AD_Column has no IsReadOnly column - the column-level equivalent is AD_Column.IsUpdateable,
--    which the registration migration never touched and which is already 'Y'. Nothing to do there.
UPDATE AD_Field
SET IsReadOnly='N', Updated=TO_TIMESTAMP('2026-09-01 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE IsActive='Y'
  AND IsReadOnly='Y'
  AND AD_Column_ID IN (
      SELECT c.AD_Column_ID
      FROM AD_Column c
      JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID
      WHERE t.TableName = 'C_AcctSchema' AND c.ColumnName = 'CostingMethod'
  )
;
