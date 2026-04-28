--
-- Note: I stumbled over the incorrect UC when i worked on https://github.com/metasfresh/metasfresh-webui/issues/209
--
-- 04.03.2017 15:03
-- URL zum Konzept
UPDATE AD_Table SET Description='A record of this table can add additional HU related infos to an AD_Process. There can be one one M_HU_Process record per AD_Process record.',Updated=TO_TIMESTAMP('2017-03-04 15:03:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540607
;

-- 04.03.2017 15:03
-- URL zum Konzept
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540623
;

-- 04.03.2017 15:03
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2017-03-04 15:03:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540312
;
COMMIT;
-- 04.03.2017 15:03
-- URL zum Konzept
DROP INDEX IF EXISTS m_hu_process_unique_ad_process_id
;

-- 04.03.2017 15:03
-- URL zum Konzept
CREATE UNIQUE INDEX M_HU_Process_Unique_AD_process_ID ON M_HU_Process (AD_Process_ID) WHERE IsActive='Y'
;

