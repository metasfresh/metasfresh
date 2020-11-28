-- 11.11.2015 11:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,54075,0,TO_TIMESTAMP('2015-11-11 11:07:55','YYYY-MM-DD HH24:MI:SS'),100,'Dunning writeoff','de.metas.dunning.writeoff',NULL,'Y','de.metas.dunning.model','de.metas.dunning.writeoff','N',TO_TIMESTAMP('2015-11-11 11:07:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.11.2015 11:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.dunning.writeoff',Updated=TO_TIMESTAMP('2015-11-11 11:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548500
;

-- 11.11.2015 11:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.dunning.writeoff',Updated=TO_TIMESTAMP('2015-11-11 11:09:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548501
;

-- 11.11.2015 11:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.dunning.writeoff',Updated=TO_TIMESTAMP('2015-11-11 11:09:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548979
;

-- 11.11.2015 11:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.dunning.writeoff',Updated=TO_TIMESTAMP('2015-11-11 11:10:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548817
;

-- 11.11.2015 11:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.dunning.writeoff',Updated=TO_TIMESTAMP('2015-11-11 11:10:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548816
;

-- ts: update not only the columns, but also the fields and elements
UPDATE AD_Field f 
SET EntityType=c.EntityType, Updated=c.Updated, UpdatedBy=c.UpdatedBy
FROM AD_Column c
WHERE c.EntityType='de.metas.dunning.writeoff' 
	AND c.AD_Column_ID=f.AD_Column_ID
	AND f.EntityType!=c.EntityType
;
UPDATE AD_Element e
SET EntityType=c.EntityType, Updated=c.Updated, UpdatedBy=c.UpdatedBy
FROM AD_Column c
WHERE e.EntityType='de.metas.dunning.writeoff' 
	AND e.AD_Element_ID=c.AD_Element_ID
	AND e.EntityType!=c.EntityType
;


-- 11.11.2015 11:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.dunning.writeoff',Updated=TO_TIMESTAMP('2015-11-11 11:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540323
;

-- 11.11.2015 11:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.dunning.writeoff.invoice.process.C_Dunning_Candidate_MassWriteOff',Updated=TO_TIMESTAMP('2015-11-11 11:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540323
;


