-- Column: M_Delivery_Planning.M_Delivery_Planning_Type
-- 2023-01-25T17:34:38.966Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-25 19:34:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585005
;

-- 2023-01-25T17:34:40.146Z
INSERT INTO t_alter_column values('m_delivery_planning','M_Delivery_Planning_Type','VARCHAR(250)',null,null)
;

-- 2023-01-25T17:34:40.151Z
INSERT INTO t_alter_column values('m_delivery_planning','M_Delivery_Planning_Type',null,'NOT NULL',null)
;

