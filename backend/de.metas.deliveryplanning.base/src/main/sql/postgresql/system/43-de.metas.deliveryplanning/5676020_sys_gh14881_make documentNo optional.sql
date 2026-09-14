-- Column: I_DeliveryPlanning.DocumentNo
-- 2023-02-07T14:11:15.656Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-02-07 16:11:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585784
;

-- 2023-02-07T14:12:49.101Z
INSERT INTO t_alter_column values('i_deliveryplanning','DocumentNo','VARCHAR(40)',null,null)
;

-- 2023-02-07T14:12:49.104Z
INSERT INTO t_alter_column values('i_deliveryplanning','DocumentNo',null,'NULL',null)
;

-- 2023-02-07T14:16:33.851Z
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning','ALTER TABLE I_DeliveryPlanning DROP COLUMN IF EXISTS I_LineContent')
;

DELETE FROM AD_UI_Element WHERE AD_Field_ID = 712066
;

DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712066
;

DELETE FROM AD_Field WHERE AD_Field_ID = 712066
;

-- Column: I_DeliveryPlanning.I_LineContent
-- 2023-02-07T14:16:33.906Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585776
;

-- 2023-02-07T14:16:33.913Z
DELETE FROM AD_Column WHERE AD_Column_ID=585776
;

