-- 2020-07-07T04:17:05.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsDefaultForPicking = ''Y'' AND IsActive = ''Y''',Updated=TO_TIMESTAMP('2020-07-07 07:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540545
;

-- 2020-07-07T04:17:08.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS m_hu_pi_isdefaultforpicking_uq
;

-- 2020-07-07T04:17:08.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_HU_PI_IsDefaultForPicking_UQ ON M_HU_PI (IsDefaultForPicking) WHERE IsDefaultForPicking = 'Y' AND IsActive = 'Y'
;

