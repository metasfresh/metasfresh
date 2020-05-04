



-- 2018-10-02T15:36:17.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540452,0,259,TO_TIMESTAMP('2018-10-02 15:36:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','C_Order_DocumentNo_UQ','N',TO_TIMESTAMP('2018-10-02 15:36:17','YYYY-MM-DD HH24:MI:SS'),100,'DocStatus IN (''CO'', ''CL'')')
;

-- 2018-10-02T15:36:17.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540452 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-10-02T15:36:28.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2172,540892,540452,0,TO_TIMESTAMP('2018-10-02 15:36:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2018-10-02 15:36:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-02T15:36:56.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2762,540893,540452,0,TO_TIMESTAMP('2018-10-02 15:36:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2018-10-02 15:36:56','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2018-10-02T15:58:13.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2169,540894,540452,0,TO_TIMESTAMP('2018-10-02 15:58:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2018-10-02 15:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2018-10-10T11:15:36.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='DocStatus IN (''CO'', ''CL'') AND COALESCE(C_DocType_ID, 0)>0',Updated=TO_TIMESTAMP('2018-10-10 11:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540452
;

-- 2018-10-10T11:15:46.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET AD_Column_ID=2163,Updated=TO_TIMESTAMP('2018-10-10 11:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540893
;

-- 2018-10-10T11:15:50.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540892
;








-- 2018-10-10T11:40:20.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2172,540895,540452,0,TO_TIMESTAMP('2018-10-10 11:40:20','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',40,TO_TIMESTAMP('2018-10-10 11:40:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-10T11:40:41.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET EntityType='D',Updated=TO_TIMESTAMP('2018-10-10 11:40:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540895
;
