-- 2019-07-11T10:10:36.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1  from c_dunningdoc dd           INNER JOIN c_dunningdoc_line ddl on dd.c_dunningdoc_id = ddl.c_dunningdoc_id           INNER JOIN c_dunningdoc_line_source ddls on ddl.c_dunningdoc_line_id = ddls.c_dunningdoc_line_id           INNER JOIN c_dunning_candidate dc on ddls.c_dunning_candidate_id = dc.c_dunning_candidate_id           INNER JOIN c_invoice inv on dc.record_id = inv.c_invoice_id AND                                       dc.ad_table_id = get_table_id(''C_Invoice'')  where 1 = 1    AND inv.issotrx = ''Y''    AND C_dunningdoc.c_dunningdoc_id = dd.c_dunningdoc_id    AND inv.c_invoice_id = @C_Invoice_ID / -1@)',Updated=TO_TIMESTAMP('2019-07-11 10:10:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541012
;

-- 2019-07-11T10:12:11.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1  from c_dunningdoc dd           INNER JOIN c_dunningdoc_line ddl on dd.c_dunningdoc_id = ddl.c_dunningdoc_id           INNER JOIN c_dunningdoc_line_source ddls on ddl.c_dunningdoc_line_id = ddls.c_dunningdoc_line_id           INNER JOIN c_dunning_candidate dc on ddls.c_dunning_candidate_id = dc.c_dunning_candidate_id           INNER JOIN c_invoice inv on dc.record_id = inv.c_invoice_id AND                                       dc.ad_table_id = get_table_id(''C_Invoice'')  where 1 = 1    AND c_invoice.c_invoice_id = inv.c_invoice_id    AND dd.c_dunningdoc_id = @C_DunningDoc_ID / -1@)',Updated=TO_TIMESTAMP('2019-07-11 10:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541014
;

-- 2019-07-11T10:23:51.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET InternalName='C_Invoice -> C_DunningDoc',Updated=TO_TIMESTAMP('2019-07-11 10:23:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540226
;

-- 2019-07-11T10:24:03.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET InternalName='C_DunningDoc -> C_Invoice',Updated=TO_TIMESTAMP('2019-07-11 10:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540227
;

-- 2019-07-11T11:06:27.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (CreatedBy,Processing,Created,AD_Client_ID,IsActive,IsUnique,AD_Table_ID,Updated,UpdatedBy,AD_Index_Table_ID,AD_Org_ID,Name,EntityType) VALUES (100,'N',TO_TIMESTAMP('2019-07-11 11:06:27','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',540402,TO_TIMESTAMP('2019-07-11 11:06:27','YYYY-MM-DD HH24:MI:SS'),100,540496,0,'C_DunningDoc_Line.C_DunningDoc_Id.Index','D')
;

-- 2019-07-11T11:06:27.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540496 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-07-11T11:06:46.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (Created,CreatedBy,Updated,AD_Client_ID,AD_Index_Table_ID,IsActive,AD_Column_ID,SeqNo,UpdatedBy,AD_Index_Column_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2019-07-11 11:06:46','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-07-11 11:06:46','YYYY-MM-DD HH24:MI:SS'),0,540496,'Y',547422,10,100,540952,0,'D')
;

-- 2019-07-11T11:09:00.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET Name='C_DunningDoc_Line__C_DunningDoc_Id__Index',Updated=TO_TIMESTAMP('2019-07-11 11:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540496
;

-- 2019-07-11T11:09:02.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX C_DunningDoc_Line__C_DunningDoc_Id__Index ON C_DunningDoc_Line (C_DunningDoc_ID)
;

-- 2019-07-11T11:10:15.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (CreatedBy,Processing,Created,AD_Client_ID,IsActive,IsUnique,AD_Table_ID,Updated,UpdatedBy,AD_Index_Table_ID,AD_Org_ID,Name,EntityType) VALUES (100,'N',TO_TIMESTAMP('2019-07-11 11:10:15','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',540403,TO_TIMESTAMP('2019-07-11 11:10:15','YYYY-MM-DD HH24:MI:SS'),100,540497,0,'c_dunningdoc_line_source__c_dunningdoc_line_id__index ','D')
;

-- 2019-07-11T11:10:15.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540497 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-07-11T11:10:43.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (Created,CreatedBy,Updated,AD_Client_ID,AD_Index_Table_ID,IsActive,AD_Column_ID,SeqNo,UpdatedBy,AD_Index_Column_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2019-07-11 11:10:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-07-11 11:10:43','YYYY-MM-DD HH24:MI:SS'),0,540497,'Y',547442,10,100,540953,0,'D')
;

-- 2019-07-11T11:10:48.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX c_dunningdoc_line_source__c_dunningdoc_line_id__index  ON C_DunningDoc_Line_Source (C_DunningDoc_Line_ID)
;

-- 2019-07-11T11:26:39.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET AD_Column_ID=547332,Updated=TO_TIMESTAMP('2019-07-11 11:26:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540406
;

-- 2019-07-11T11:26:43.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET AD_Column_ID=547333,Updated=TO_TIMESTAMP('2019-07-11 11:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540405
;

-- 2019-07-11T11:26:51.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS c_dunningcandidate_unique
;

-- 2019-07-11T11:26:51.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_DunningCandidate_Unique ON C_Dunning_Candidate (Record_ID,AD_Table_ID,C_DunningLevel_ID) WHERE IsActive='Y'
;

