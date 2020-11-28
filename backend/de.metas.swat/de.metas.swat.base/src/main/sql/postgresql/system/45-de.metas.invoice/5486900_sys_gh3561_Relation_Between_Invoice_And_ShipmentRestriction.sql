-- 2018-02-26T13:49:38.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540739,540202,TO_TIMESTAMP('2018-02-26 13:49:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Invoice -> M_Shipment_Constraint',TO_TIMESTAMP('2018-02-26 13:49:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-26T14:01:37.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540837,TO_TIMESTAMP('2018-02-26 14:01:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_Shipment_Constraint_Target_For_Invoice_SO',TO_TIMESTAMP('2018-02-26 14:01:37','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-02-26T14:01:37.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540837 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-02-26T14:08:09.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,557309,0,540837,540845,TO_TIMESTAMP('2018-02-26 14:08:09','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2018-02-26 14:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-26T14:08:16.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2018-02-26 14:08:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;


-- 2018-02-26T14:33:57.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540837,Updated=TO_TIMESTAMP('2018-02-26 14:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540202
;

-- 2018-02-26T14:39:12.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists  (    select 1 from c_invoice i          LEFT JOIN c_dunning_candidate dc       on dc.record_id = i.c_invoice_id AND dc.ad_table_id = 318 AND dc.c_dunninglevel_id = 540009     LEFT JOIN c_dunningdoc_line_source dls on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id     LEFT JOIN c_dunningdoc_line ddl on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id     LEFT JOIN c_dunningdoc dd on dd.c_dunningdoc_id = ddl.c_dunningdoc_id     LEFT JOIN m_shipment_constraint sc on sc.sourcedoc_record_id = dd.c_dunningdoc_id  where i.c_invoice_ID = @C_Invoice_ID/-1@ and sc.m_shipment_constraint_ID = m_shipment_constraint.m_shipment_constraint_ID )',Updated=TO_TIMESTAMP('2018-02-26 14:39:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;

-- 2018-02-26T14:40:23.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540370,Updated=TO_TIMESTAMP('2018-02-26 14:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;

-- 2018-02-26T16:00:49.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists  (    select 1 from c_invoice i          LEFT JOIN c_dunning_candidate dc       on dc.record_id = i.c_invoice_id AND dc.ad_table_id = 318    LEFT JOIN c_dunningdoc_line_source dls on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id     LEFT JOIN c_dunningdoc_line ddl on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id     LEFT JOIN c_dunningdoc dd on dd.c_dunningdoc_id = ddl.c_dunningdoc_id     LEFT JOIN m_shipment_constraint sc on sc.sourcedoc_record_id = dd.c_dunningdoc_id  where i.c_invoice_ID = @C_Invoice_ID/-1@ and sc.m_shipment_constraint_ID = m_shipment_constraint.m_shipment_constraint_ID )',Updated=TO_TIMESTAMP('2018-02-26 16:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;

-- 2018-02-26T16:10:47.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='select *     from c_invoice i           JOIN c_dunning_candidate dc       on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')       JOIN c_dunningdoc_line_source dls on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id      JOIN c_dunningdoc_line ddl on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id      JOIN c_dunningdoc dd on dd.c_dunningdoc_id = ddl.c_dunningdoc_id  JOIN m_shipment_constraint sc on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')',Updated=TO_TIMESTAMP('2018-02-26 16:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;

-- 2018-02-26T16:11:04.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2018-02-26 16:11:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540202
;

-- 2018-02-26T16:19:42.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1     from c_invoice i           JOIN c_dunning_candidate dc       on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')       JOIN c_dunningdoc_line_source dls on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id      JOIN c_dunningdoc_line ddl on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id      JOIN c_dunningdoc dd on dd.c_dunningdoc_id = ddl.c_dunningdoc_id  JOIN m_shipment_constraint sc on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc''))',Updated=TO_TIMESTAMP('2018-02-26 16:19:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;

-- 2018-02-26T16:28:13.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists   (select 1       from c_invoice i             JOIN c_dunning_candidate dc on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')         JOIN c_dunningdoc_line_source dls on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id        JOIN c_dunningdoc_line ddl on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id        JOIN c_dunningdoc dd on dd.c_dunningdoc_id = ddl.c_dunningdoc_id    JOIN m_shipment_constraint sc on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')    where i.c_invoice_ID = @C_Invoice_ID/-1@ AND m_shipment_constraint.m_shipment_constraint_ID = sc.m_shipment_constraint  )',Updated=TO_TIMESTAMP('2018-02-26 16:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;

-- 2018-02-26T16:29:55.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists   (select 1       from c_invoice i             JOIN c_dunning_candidate dc on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')         JOIN c_dunningdoc_line_source dls on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id        JOIN c_dunningdoc_line ddl on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id        JOIN c_dunningdoc dd on dd.c_dunningdoc_id = ddl.c_dunningdoc_id    JOIN m_shipment_constraint sc on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')    where i.c_invoice_ID = @C_Invoice_ID/-1@ AND m_shipment_constraint.m_shipment_constraint_ID = sc.m_shipment_constraint_ID)',Updated=TO_TIMESTAMP('2018-02-26 16:29:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;

-- 2018-02-26T16:31:25.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists   (select 1       from c_invoice i             JOIN c_dunning_candidate dc on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')         JOIN c_dunningdoc_line_source dls on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id        JOIN c_dunningdoc_line ddl on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id        JOIN c_dunningdoc dd on dd.c_dunningdoc_id = ddl.c_dunningdoc_id    JOIN m_shipment_constraint sc on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')    where  i.c_invoice_ID = @C_Invoice_ID/-1@ AND m_shipment_constraint.m_shipment_constraint_ID = sc.m_shipment_constraint_ID)',Updated=TO_TIMESTAMP('2018-02-26 16:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;

-- 2018-02-26T16:36:54.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists   (select 1       from c_invoice i             JOIN c_dunning_candidate dc on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')         JOIN c_dunningdoc_line_source dls on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id        JOIN c_dunningdoc_line ddl on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id        JOIN c_dunningdoc dd on dd.c_dunningdoc_id = ddl.c_dunningdoc_id    JOIN m_shipment_constraint sc on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')    where  i.IsSOTrx = ''Y'' AND i.c_invoice_ID = @C_Invoice_ID/-1@ AND m_shipment_constraint.m_shipment_constraint_ID = sc.m_shipment_constraint_ID)',Updated=TO_TIMESTAMP('2018-02-26 16:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540837
;

