-- 2020-02-27T20:01:00.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570067,540991,540499,0,TO_TIMESTAMP('2020-02-27 21:01:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',30,TO_TIMESTAMP('2020-02-27 21:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-27T20:01:04.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_commission_share_uc
;

-- 2020-02-27T20:01:04.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Commission_Share_UC ON C_Commission_Share (C_Commission_Instance_ID,LevelHierarchy,Commission_Product_ID) WHERE IsActive='Y'
;

