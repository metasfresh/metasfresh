-- me03 #30351 — rename C_BP_Group.IsAssociation → IsDeviatingBillBPartner
-- Renames the physical column and its check constraint, and updates the AD dictionary
-- (ColumnName only; display Name/PrintName/Trl are unchanged — that is a separate task).

-- 1. Rename the physical column (db_alter_table drops/recreates dependent views; pass the full ALTER TABLE statement)
/* DDL */ SELECT public.db_alter_table('C_BP_Group', 'ALTER TABLE C_BP_Group RENAME COLUMN IsAssociation TO IsDeviatingBillBPartner');

-- 2. Rename the check constraint
/* DDL */ SELECT public.db_alter_table('C_BP_Group', 'ALTER TABLE C_BP_Group RENAME CONSTRAINT c_bp_group_isassociation_check TO c_bp_group_isdeviatingbillbpartner_check');

-- 3. Update AD_Column ColumnName
UPDATE AD_Column
SET    ColumnName = 'IsDeviatingBillBPartner',
       Updated    = TO_TIMESTAMP('2026-06-23 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 100
WHERE  AD_Column_ID = 590706
;

-- 4. Update AD_Element ColumnName (Name/PrintName/Trl unchanged — Task D)
UPDATE AD_Element
SET    ColumnName = 'IsDeviatingBillBPartner',
       Updated    = TO_TIMESTAMP('2026-06-23 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 100
WHERE  AD_Element_ID = 583888
;
