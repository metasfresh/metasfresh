-- M_HU_Label_Config.AutoPrintCopies is NOT NULL with no default, while its field is hidden by
-- DisplayLogic (@IsAutoPrint/N@=Y), and IsAutoPrint itself is hidden for HU_SourceDocType=MO
-- (Produktion). So any record where IsAutoPrint stays N -- e.g. a Produktion config -- cannot be
-- saved via the WebUI: the INSERT omits AutoPrintCopies and the NOT-NULL constraint is violated.
-- Give the column a default of 1 (one copy) so the record saves when the field is not shown; the
-- value is only used when IsAutoPrint=Y. No new fields are added. Core defect (also on new_dawn_uat).

-- AD dictionary default: PO.saveNew applies it when the field carries no value
UPDATE AD_Column
SET DefaultValue='1',
    Updated=TO_TIMESTAMP('2026-06-16 16:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE columnname='AutoPrintCopies'
  AND ad_table_id=(SELECT ad_table_id FROM AD_Table WHERE tablename='M_HU_Label_Config');

-- DB column default: backstop for any INSERT that omits the column
INSERT INTO t_alter_column VALUES('M_HU_Label_Config','AutoPrintCopies','NUMERIC(10)','NOT NULL','1');
