-- 2026-03-25T16:15:01.872Z
UPDATE AD_Column SET FilterOperator='E', IsIdentifier='Y',Updated=TO_TIMESTAMP('2026-03-25 16:15:01.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=531087
;

-- 2026-03-25T16:15:24.079Z
UPDATE AD_Column SET SeqNo=30,Updated=TO_TIMESTAMP('2026-03-25 16:15:24.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=531087
;

-- 2026-03-25T16:16:18.590Z
UPDATE AD_Column SET FilterOperator='E', IsIdentifier='Y', SeqNo=40,Updated=TO_TIMESTAMP('2026-03-25 16:16:18.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=531088
;

-- Cleanup: this column had a seqNo but ws not marked as identifier. Removed the seqNo
-- 2026-03-25T16:26:06.043Z
UPDATE AD_Column SET SeqNo=NULL,Updated=TO_TIMESTAMP('2026-03-25 16:26:05.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569255
;

