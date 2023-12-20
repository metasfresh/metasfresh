-- Column: M_InOut_Cost.C_BPartner_ID
-- 2023-02-09T12:17:48.454Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-09 14:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585958
;

-- Column: M_InOut_Cost.C_BPartner_ID
-- 2023-02-09T12:18:11.620Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-02-09 14:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585958
;

-- Column: M_InOut_Cost.C_Cost_Type_ID
-- 2023-02-09T12:19:00.041Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-09 14:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585959
;

-- 2023-02-09T12:19:00.792Z
INSERT INTO t_alter_column values('m_inout_cost','C_Cost_Type_ID','NUMERIC(10)',null,null)
;

-- 2023-02-09T12:19:00.797Z
INSERT INTO t_alter_column values('m_inout_cost','C_Cost_Type_ID',null,'NOT NULL',null)
;

