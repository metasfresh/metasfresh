
--cleanup
DROP INDEX IF EXISTS idx_s_externalreference_externalsystem_type_externalreferenc;

-- 2021-09-28T04:12:14.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570133,541184,540528,0,TO_TIMESTAMP('2021-09-28 06:12:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',40,TO_TIMESTAMP('2021-09-28 06:12:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-28T04:13:37.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS idx_s_externalprojectreference_reference_owner_system
;

-- 2021-09-28T04:13:37.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_ExternalProjectReference_Reference_Owner_System ON S_ExternalProjectReference (ExternalProjectOwner,ExternalReference,ExternalSystem,AD_Org_ID) WHERE S_ExternalProjectReference.isActive='Y'
;
