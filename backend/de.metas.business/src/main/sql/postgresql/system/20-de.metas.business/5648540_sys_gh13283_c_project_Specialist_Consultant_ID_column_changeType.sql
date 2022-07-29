-- Column: C_Project.Specialist_Consultant_ID
-- 2022-07-29T12:29:28.986Z
UPDATE AD_Column SET AD_Reference_ID=10, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2022-07-29 15:29:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583614
;

-- Column: C_Project.Specialist_Consultant_ID
-- 2022-07-29T12:29:41.211Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2022-07-29 15:29:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583614
;

--drop FK constraint
ALTER TABLE public.C_Project DROP CONSTRAINT IF EXISTS specialistconsultant_cproject;


-- 2022-07-29T13:23:44.450Z
INSERT INTO t_alter_column values('c_project','Specialist_Consultant_ID','VARCHAR(255)',null,null)
;

