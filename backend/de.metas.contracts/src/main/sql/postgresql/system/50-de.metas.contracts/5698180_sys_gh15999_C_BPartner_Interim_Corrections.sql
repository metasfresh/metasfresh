-- Column: C_BPartner_InterimContract.C_Harvesting_Calendar_ID
-- 2023-08-07T13:01:11.991998900Z
UPDATE AD_Column SET AD_Reference_Value_ID=540260,Updated=TO_TIMESTAMP('2023-08-07 15:01:11.991','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587239
;

-- 2023-08-07T13:03:12.863578400Z
INSERT INTO t_alter_column values('c_bpartner_interimcontract','C_Harvesting_Calendar_ID','NUMERIC(10)',null,null)
;

-- Table: C_BPartner_InterimContract
-- 2023-08-07T14:03:15.870167800Z
UPDATE AD_Table SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2023-08-07 16:03:15.868','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542357
;