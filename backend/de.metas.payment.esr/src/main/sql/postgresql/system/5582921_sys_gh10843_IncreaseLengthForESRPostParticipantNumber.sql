ALTER TABLE esr_importline ALTER COLUMN esrpostparticipantnumber TYPE varchar(25);

-- 2021-03-19T06:43:13.156Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=25,Updated=TO_TIMESTAMP('2021-03-19 07:43:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547566
;

-- 2021-03-19T06:43:28.696Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLength=25,Updated=TO_TIMESTAMP('2021-03-19 07:43:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550791
;

