-- Column: AD_Note.AD_Message_ParamsJSON
-- 2022-07-08T12:02:07.620Z
UPDATE AD_Column SET FieldLength=6000,Updated=TO_TIMESTAMP('2022-07-08 15:02:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557054
;

-- 2022-07-08T12:02:12.250Z
INSERT INTO t_alter_column values('ad_note','AD_Message_ParamsJSON','TEXT',null,null)
;

