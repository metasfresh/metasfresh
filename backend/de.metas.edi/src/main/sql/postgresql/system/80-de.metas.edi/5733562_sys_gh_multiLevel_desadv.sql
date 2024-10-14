
-- Column: EDI_Desadv_Pack_Item.Line
-- Column: EDI_Desadv_Pack_Item.Line
-- 2024-09-17T13:15:25.607Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-17 15:15:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588984
;

-- 2024-09-17T13:15:26.768Z
INSERT INTO t_alter_column values('edi_desadv_pack_item','Line','NUMERIC(10)',null,null)
;

-- 2024-09-17T13:15:26.796Z
INSERT INTO t_alter_column values('edi_desadv_pack_item','Line',null,'NOT NULL',null)
;
