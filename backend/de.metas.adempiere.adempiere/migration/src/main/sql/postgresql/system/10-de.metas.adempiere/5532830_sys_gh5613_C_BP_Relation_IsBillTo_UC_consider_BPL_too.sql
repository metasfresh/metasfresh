-- 2019-10-03T08:30:25.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,11115,540959,540365,0,'COALESCE(C_BPartner_Location_ID, 0)',TO_TIMESTAMP('2019-10-03 11:30:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y',30,TO_TIMESTAMP('2019-10-03 11:30:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-03T08:30:33.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET SeqNo=15,Updated=TO_TIMESTAMP('2019-10-03 11:30:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540959
;

-- 2019-10-03T08:30:39.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_bp_relation_uc_isbillto
;

-- 2019-10-03T08:30:39.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_BP_Relation_UC_IsBillTo ON C_BP_Relation (C_BPartner_ID,COALESCE(C_BPartner_Location_ID, 0),IsBillTo) WHERE IsActive='Y' AND IsBillTo='Y'
;

