-- 16.12.2015 18:08
-- Fix AD_Table Client+Org Access validation rule
-- NOTE: AccessLevel is string column and not numeric
UPDATE AD_Val_Rule SET Code='AD_Table.AccessLevel=''3'' AND AD_Table.IsView=''N''',Updated=TO_TIMESTAMP('2015-12-16 18:08:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=256
;

