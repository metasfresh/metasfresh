-- Sep 7, 2016 12:50 PM
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540384,0,255,TO_TIMESTAMP('2016-09-07 12:50:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pricing','Y','Y','M_PriceList_UC_C_Country','N',TO_TIMESTAMP('2016-09-07 12:50:37','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- Sep 7, 2016 12:50 PM
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540384 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- Sep 7, 2016 12:51 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,505147,540765,540384,0,TO_TIMESTAMP('2016-09-07 12:51:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pricing','Y',10,TO_TIMESTAMP('2016-09-07 12:51:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 7, 2016 12:51 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2761,540766,540384,0,TO_TIMESTAMP('2016-09-07 12:51:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pricing','Y',20,TO_TIMESTAMP('2016-09-07 12:51:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 7, 2016 12:52 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,505148,540767,540384,0,'COALESCE(C_Country_ID,0)',TO_TIMESTAMP('2016-09-07 12:52:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pricing','Y',30,TO_TIMESTAMP('2016-09-07 12:52:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 7, 2016 12:55 PM
-- URL zum Konzept
UPDATE AD_Index_Table SET ErrorMsg='Es kann pro Land nur je eine VK und eine EK-Preisliste geben.', Help='see metasfresh issue #366',Updated=TO_TIMESTAMP('2016-09-07 12:55:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540384
;

-- Sep 7, 2016 12:55 PM
-- URL zum Konzept
UPDATE AD_Index_Table_Trl SET IsTranslated='N' WHERE AD_Index_Table_ID=540384
;


----------------------------------------------------
-- As bonus, we also fix the UC for M_PriceList.Name
----------------------------------------------------


-- Sep 7, 2016 1:18 PM
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540385,0,255,TO_TIMESTAMP('2016-09-07 13:18:13','YYYY-MM-DD HH24:MI:SS'),100,NULL,'de.metas.pricing','Jeder Preislistenname kann pro Organisation nur einmal vergeben werden.','Y','Y','M_PriceList_UC_Name','N',TO_TIMESTAMP('2016-09-07 13:18:13','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- Sep 7, 2016 1:18 PM
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540385 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- Sep 7, 2016 1:18 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2101,540768,540385,0,TO_TIMESTAMP('2016-09-07 13:18:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pricing','Y',10,TO_TIMESTAMP('2016-09-07 13:18:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 7, 2016 1:19 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2102,540769,540385,0,TO_TIMESTAMP('2016-09-07 13:19:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pricing','Y',20,TO_TIMESTAMP('2016-09-07 13:19:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 7, 2016 1:19 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2108,540770,540385,0,TO_TIMESTAMP('2016-09-07 13:19:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pricing','Y',30,TO_TIMESTAMP('2016-09-07 13:19:27','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Sep 7, 2016 1:20 PM
-- URL zum Konzept
UPDATE AD_Index_Table SET ErrorMsg='Jeder Preislistenname kann pro Preissystem nur einmal vergeben werden.',Updated=TO_TIMESTAMP('2016-09-07 13:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540385
;

-- Sep 7, 2016 1:20 PM
-- URL zum Konzept
UPDATE AD_Index_Table_Trl SET IsTranslated='N' WHERE AD_Index_Table_ID=540385
;

-- Sep 7, 2016 1:20 PM
-- URL zum Konzept
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540768
;

-- Sep 7, 2016 1:20 PM
-- URL zum Konzept
UPDATE AD_Index_Column SET AD_Column_ID=505147, SeqNo=10,Updated=TO_TIMESTAMP('2016-09-07 13:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540769
;

-- Sep 7, 2016 1:20 PM
-- URL zum Konzept
UPDATE AD_Index_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2016-09-07 13:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540770
;






--
-- DDL
--
COMMIT;

DROP INDEX IF EXISTS m_pricelist_country_pricingsys;
ALTER TABLE m_pricelist DROP CONSTRAINT IF EXISTS m_pricelist_country;
-- Sep 7, 2016 12:55 PM
-- URL zum Konzept
CREATE UNIQUE INDEX M_PriceList_UC_C_Country ON M_PriceList (M_PricingSystem_ID,IsSOPriceList,COALESCE(C_Country_ID,0)) WHERE IsActive='Y'
;

DROP INDEX IF EXISTS m_pricelist_name;
-- Sep 7, 2016 1:21 PM
-- URL zum Konzept
DROP INDEX IF EXISTS m_pricelist_uc_name
;
-- Sep 7, 2016 1:21 PM
-- URL zum Konzept
CREATE UNIQUE INDEX M_PriceList_UC_Name ON M_PriceList (M_PricingSystem_ID,Name) WHERE IsActive='Y'
;