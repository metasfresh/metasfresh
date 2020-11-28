-- 2018-10-19T07:20:31.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (Created,CreatedBy,Updated,AD_Client_ID,AD_Index_Table_ID,IsActive,AD_Column_ID,SeqNo,UpdatedBy,AD_Index_Column_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2018-10-19 07:20:31','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-19 07:20:31','YYYY-MM-DD HH24:MI:SS'),0,540460,'Y',2895,20,100,540914,0,'D')
;

-- 2018-10-19T07:21:02.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_bpartner_externalid_uniqe
;

-- 2018-10-19T07:21:02.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_BPartner_ExternalId_Uniqe ON C_BPartner (ExternalId,AD_Org_ID) WHERE IsActive='Y' AND COALESCE(ExternalId, '') != ''
;

