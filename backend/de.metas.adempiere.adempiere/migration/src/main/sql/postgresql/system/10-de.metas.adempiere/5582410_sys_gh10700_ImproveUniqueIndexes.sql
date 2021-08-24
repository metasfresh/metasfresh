-- 2021-03-16T16:45:01.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2895,541069,540105,0,TO_TIMESTAMP('2021-03-16 18:45:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2021-03-16 18:45:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T16:45:06.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_bpartner_debtorid_uniqe
;

-- 2021-03-16T16:45:06.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_BPartner_DebtorID_Uniqe ON C_BPartner (DebtorId,AD_Org_ID) WHERE COALESCE(DebtorId, 0) > 0
;

-- 2021-03-16T16:45:18.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2895,541070,540106,0,TO_TIMESTAMP('2021-03-16 18:45:18','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',20,TO_TIMESTAMP('2021-03-16 18:45:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T16:45:19.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_bpartner_creditorid_uniqe
;

-- 2021-03-16T16:45:19.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_BPartner_CreditorID_Uniqe ON C_BPartner (CreditorId,AD_Org_ID) WHERE COALESCE(CreditorId, 0) > 0
;

-- 2021-03-16T16:45:28.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET EntityType='D',Updated=TO_TIMESTAMP('2021-03-16 18:45:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=541070
;

-- 2021-03-16T16:45:30.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_bpartner_creditorid_uniqe
;

-- 2021-03-16T16:45:30.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_BPartner_CreditorID_Uniqe ON C_BPartner (CreditorId,AD_Org_ID) WHERE COALESCE(CreditorId, 0) > 0
;

