-- 2021-01-24T22:20:02.439Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,Help,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540562,0,541407,TO_TIMESTAMP('2021-01-24 23:20:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','This uniqueness is assumed in de.metas.contracts.commission.commissioninstance.services.repos.CommissionInstanceRepository#createNewFactRecords','Y','Y','C_Commission_Fact_UC','N',TO_TIMESTAMP('2021-01-24 23:20:01','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2021-01-24T22:20:02.478Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540562 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-01-24T22:20:46.441Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,568777,541052,540562,0,TO_TIMESTAMP('2021-01-24 23:20:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',10,TO_TIMESTAMP('2021-01-24 23:20:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-24T22:21:06.968Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,568784,541053,540562,0,TO_TIMESTAMP('2021-01-24 23:21:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',20,TO_TIMESTAMP('2021-01-24 23:21:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-24T22:21:44.595Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,568779,541054,540562,0,TO_TIMESTAMP('2021-01-24 23:21:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',30,TO_TIMESTAMP('2021-01-24 23:21:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-24T22:21:53.215Z
-- URL zum Konzept
CREATE UNIQUE INDEX C_Commission_Fact_UC ON C_Commission_Fact (C_Commission_Share_ID,CommissionFactTimestamp,Commission_Fact_State) WHERE IsActive='Y'
;

