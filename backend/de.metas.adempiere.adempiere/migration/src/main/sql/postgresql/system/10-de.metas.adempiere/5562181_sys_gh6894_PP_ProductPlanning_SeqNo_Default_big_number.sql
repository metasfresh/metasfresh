-- 2020-06-24T10:51:45.290Z
-- description fields
UPDATE AD_Column SET DefaultValue='1000',Updated=TO_TIMESTAMP('2020-06-23 13:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557820
;

-- 2020-06-24T10:51:54.273Z
-- description fields
INSERT INTO t_alter_column values('pp_product_planning','SeqNo','NUMERIC(10)',null,'1000')
;

-- 2020-06-24T10:51:54.319Z
-- description fields
UPDATE PP_Product_Planning SET SeqNo=1000 WHERE SeqNo IS NULL
;

-- 2020-06-24T10:51:54.353Z
-- description fields
INSERT INTO t_alter_column values('pp_product_planning','SeqNo',null,'NOT NULL',null)
;

