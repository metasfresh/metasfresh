-- Column: DD_Order_Candidate.DemandDate
-- Column: DD_Order_Candidate.DemandDate
-- 2024-08-02T06:00:54.915Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-08-02 09:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588899
;

-- 2024-08-02T06:00:55.618Z
INSERT INTO t_alter_column values('dd_order_candidate','DemandDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-08-02T06:00:55.621Z
INSERT INTO t_alter_column values('dd_order_candidate','DemandDate',null,'NOT NULL',null)
;

