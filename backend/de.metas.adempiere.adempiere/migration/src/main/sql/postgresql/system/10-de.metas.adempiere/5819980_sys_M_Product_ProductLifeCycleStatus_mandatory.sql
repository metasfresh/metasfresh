-- Make M_Product.ProductLifeCycleStatus mandatory.
--
-- The column was added by 5816400 as VARCHAR(1) DEFAULT 'O', nullable. NULL and 'O' already mean the
-- same thing to the application: BBSStatus.ofNullableCode(null) yields no status and every
-- ProductLifeCycleAction is allowed, which is exactly what 'O' (OK) does. So NULL carries no
-- information the status codes do not, and allowing it only forces every reader to handle a second
-- representation of "unrestricted".
--
-- t_alter_column's 5th argument sets the column default AND backfills the existing NULLs inside the
-- same DDL, so this is safe on an instance that still has NULL rows -- no separate UPDATE pass is
-- needed, and none of the rows change meaning (NULL and 'O' were already equivalent).
--
-- AD_Column.IsMandatory must always match the physical NOT NULL constraint: the framework's
-- PO/InterfaceWrapperHelper save path enforces the AD flag, while the constraint catches every write
-- that bypasses it (raw SQL, importers, cucumber step defs). Both sides move together, here.

INSERT INTO t_alter_column values('m_product','ProductLifeCycleStatus','VARCHAR(1)','NOT NULL','O')
;

UPDATE AD_Column
SET IsMandatory='Y',
    Updated=TO_TIMESTAMP('2026-08-24 11:20:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID=593038 /* M_Product.ProductLifeCycleStatus */
;
