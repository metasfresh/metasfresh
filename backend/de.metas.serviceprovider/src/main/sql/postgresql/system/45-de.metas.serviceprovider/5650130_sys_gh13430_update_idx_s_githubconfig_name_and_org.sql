
-- 2022-08-09T07:30:00.211Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570636,541259,540529,0,TO_TIMESTAMP('2022-08-09 10:29:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',20,TO_TIMESTAMP('2022-08-09 10:29:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-09T07:30:43.957Z
DROP INDEX IF EXISTS idx_s_githubconfig_name
;

-- 2022-08-09T07:30:43.988Z
CREATE UNIQUE INDEX IDX_S_GithubConfig_Name ON S_GithubConfig (Name,AD_Org_ID)
;

