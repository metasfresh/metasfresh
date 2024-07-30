
--
-- make column mandatory
--
-- Column: EDI_Desadv_Pack.Line
-- Column: EDI_Desadv_Pack.Line
-- 2024-07-29T08:17:56.099Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-07-29 10:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588875
;

-- 2024-07-29T08:18:06.943Z
INSERT INTO t_alter_column values('edi_desadv_pack','Line','NUMERIC(10)',null,null)
;

-- 2024-07-29T08:18:06.948Z
INSERT INTO t_alter_column values('edi_desadv_pack','Line',null,'NOT NULL',null)
;

