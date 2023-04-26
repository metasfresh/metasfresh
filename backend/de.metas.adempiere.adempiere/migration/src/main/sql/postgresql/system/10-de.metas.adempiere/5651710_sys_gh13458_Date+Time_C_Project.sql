-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-18T18:15:33.862Z
UPDATE AD_Column SET AD_Reference_ID=16, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-08-18 21:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583964
;

-- 2022-08-18T18:15:37.135Z
INSERT INTO t_alter_column values('c_project','WOProjectCreatedDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- Column: C_Project.DateOfProvisionByBPartner
-- 2022-08-18T18:15:57.904Z
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2022-08-18 21:15:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583616
;

-- 2022-08-18T18:15:59.166Z
INSERT INTO t_alter_column values('c_project','DateOfProvisionByBPartner','TIMESTAMP WITH TIME ZONE',null,null)
;

-- Column: C_Project.BPartnerTargetDate
-- 2022-08-18T18:16:14.035Z
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2022-08-18 21:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583618
;

-- 2022-08-18T18:16:15.745Z
INSERT INTO t_alter_column values('c_project','BPartnerTargetDate','TIMESTAMP WITH TIME ZONE',null,null)
;
