-- 11.11.2015 17:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540074,Updated=TO_TIMESTAMP('2015-11-11 17:38:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552813
;

-- 11.11.2015 17:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_allocationline','Counter_AllocationLine_ID','NUMERIC(10)',null,'NULL')
;

ALTER TABLE C_AllocationLine ADD CONSTRAINT CounterAllocationLine_CAllocat FOREIGN KEY (Counter_AllocationLine_ID) REFERENCES C_AllocationLine DEFERRABLE INITIALLY DEFERRED;

-- 11.11.2015 19:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540370,0,390,TO_TIMESTAMP('2015-11-11 19:59:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Unique_Counter_AllocationLine','N',TO_TIMESTAMP('2015-11-11 19:59:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.11.2015 19:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540370 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 11.11.2015 20:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='Counter_AllocationLine_ID IS NOT NULL',Updated=TO_TIMESTAMP('2015-11-11 20:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540370
;

-- 11.11.2015 20:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,552813,540739,540370,0,TO_TIMESTAMP('2015-11-11 20:00:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2015-11-11 20:00:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.11.2015 20:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX Unique_Counter_AllocationLine ON C_AllocationLine (Counter_AllocationLine_ID) WHERE Counter_AllocationLine_ID IS NOT NULL
;

