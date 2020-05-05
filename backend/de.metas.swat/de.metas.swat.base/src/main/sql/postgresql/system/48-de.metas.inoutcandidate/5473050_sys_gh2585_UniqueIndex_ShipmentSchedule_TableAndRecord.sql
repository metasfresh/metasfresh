-- 2017-09-27T17:02:28.273
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540409,0,500221,TO_TIMESTAMP('2017-09-27 17:02:28','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Y','M_ShipmentSchedule_Unique_TableAndRecord','N',TO_TIMESTAMP('2017-09-27 17:02:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-27T17:02:28.278
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540409 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-09-27T17:03:12.907
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2017-09-27 17:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540409
;

-- 2017-09-27T17:03:41.676
-- URL zum Konzept
UPDATE AD_Index_Table SET EntityType='de.metas.inoutcandidate',Updated=TO_TIMESTAMP('2017-09-27 17:03:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540409
;

-- 2017-09-27T17:03:57.106
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,547287,540815,540409,0,TO_TIMESTAMP('2017-09-27 17:03:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y',10,TO_TIMESTAMP('2017-09-27 17:03:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-27T17:04:11.780
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,547288,540816,540409,0,TO_TIMESTAMP('2017-09-27 17:04:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y',20,TO_TIMESTAMP('2017-09-27 17:04:11','YYYY-MM-DD HH24:MI:SS'),100)
;




-- 2017-09-27T17:05:49.767
-- URL zum Konzept
CREATE UNIQUE INDEX M_ShipmentSchedule_Unique_TableAndRecord ON M_ShipmentSchedule (AD_Table_ID,Record_ID) WHERE IsActive = 'Y'
;

