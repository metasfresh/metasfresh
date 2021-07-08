-- 2021-06-29T10:06:43.471Z
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='isActive=''Y'' and IsDefault=''Y''',Updated=TO_TIMESTAMP('2021-06-29 12:06:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540565
;

-- 2021-06-29T10:06:46.571Z
-- URL zum Konzept
DROP INDEX IF EXISTS isdefaultconfig
;

-- 2021-06-29T10:06:46.576Z
-- URL zum Konzept
CREATE UNIQUE INDEX isDefaultConfig ON AD_Zebra_Config (AD_Org_ID,IsDefault) WHERE isActive='Y' and IsDefault='Y'
;

