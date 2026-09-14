-- Make M_Product.ProductLifeCycleStatus mandatory.
--
-- The column was added by 5816400 as VARCHAR(1) DEFAULT 'O', nullable. NULL and 'O' already mean the
-- same thing to the application: BBSStatus.ofNullableCode(null) yields no status and every
-- ProductLifeCycleAction is allowed, which is exactly what 'O' (OK) does. So NULL carries no
-- information the status codes do not, and allowing it only forces every reader to handle a second
-- representation of "unrestricted".
--
-- The backfill is EXPLICIT and must stay. t_alter_column does NOT populate existing rows: it issues
-- ALTER COLUMN ... SET DEFAULT (which only affects future inserts, unlike ADD COLUMN ... DEFAULT) and
-- then ALTER COLUMN ... SET NOT NULL, with no UPDATE in between -- see
-- de.metas.swat.base/.../ddl/functions/altercolumn.sql. So on any instance still holding one NULL row
-- the SET NOT NULL aborts with "column ... contains null values" and takes the whole migration run
-- with it. Most instances have no NULLs, because core 5816400 added the column with
-- ADD COLUMN ... DEFAULT 'O', which does backfill at add time -- but that is a different mechanism and
-- it only holds where nothing has written an explicit NULL since.
--
-- No row changes meaning: NULL and 'O' are already equivalent to the application (see above).

SELECT backup_table('m_product', '_31659_ProductLifeCycleStatus_mandatory');

UPDATE M_Product
SET ProductLifeCycleStatus='O',
    Updated=TO_TIMESTAMP('2026-08-24 11:19:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=99
WHERE ProductLifeCycleStatus IS NULL
;

INSERT INTO t_alter_column values('m_product','ProductLifeCycleStatus','VARCHAR(1)','NOT NULL','O')
;

UPDATE AD_Column
SET IsMandatory='Y',
    Updated=TO_TIMESTAMP('2026-08-24 11:20:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID=593038 /* M_Product.ProductLifeCycleStatus */
;
