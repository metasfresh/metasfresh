-- 2020-03-03T09:24:58.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET AD_Column_ID=569225,Updated=TO_TIMESTAMP('2020-03-03 10:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540991
;

-- 2020-03-03T09:25:03.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_commission_share_uc
;

-- 2020-03-03T09:25:16.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Commission_Share_UC ON C_Commission_Share (C_Commission_Instance_ID,LevelHierarchy,C_Flatrate_Term_ID) WHERE IsActive='Y'
;

