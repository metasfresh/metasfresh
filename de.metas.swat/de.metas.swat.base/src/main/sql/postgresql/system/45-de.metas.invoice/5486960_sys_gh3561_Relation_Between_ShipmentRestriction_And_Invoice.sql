-- 2018-02-27T14:35:46.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540839,TO_TIMESTAMP('2018-02-27 14:35:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N','M_Shipment_Constraint Source',TO_TIMESTAMP('2018-02-27 14:35:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-02-27T14:35:46.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540839 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-02-27T14:36:22.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,557309,0,540839,540845,TO_TIMESTAMP('2018-02-27 14:36:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','N',TO_TIMESTAMP('2018-02-27 14:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-27T14:36:30.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540370,Updated=TO_TIMESTAMP('2018-02-27 14:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540839
;

-- 2018-02-27T14:36:49.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540839,540203,TO_TIMESTAMP('2018-02-27 14:36:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','M_Shipment_Constraint -> C_Invoice',TO_TIMESTAMP('2018-02-27 14:36:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-27T14:37:33.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540840,TO_TIMESTAMP('2018-02-27 14:37:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Invoice_SO  Target for M_Shipment_Constraint',TO_TIMESTAMP('2018-02-27 14:37:33','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-02-27T14:37:33.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540840 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-02-27T14:39:01.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2018-02-27 14:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540839
;

-- 2018-02-27T14:44:12.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3484,0,540840,318,TO_TIMESTAMP('2018-02-27 14:44:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2018-02-27 14:44:12','YYYY-MM-DD HH24:MI:SS'),100,' exists   (  select 1         from m_shipment_constraint sc  JOIN c_dunningdoc dd on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')  JOIN c_dunningdoc_line ddl on dd.c_dunningdoc_id = ddl.c_dunningdoc_id  JOIN c_dunningdoc_line_source dls on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id   JOIN c_dunning_candidate dc on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id   join c_invoice i on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')      where i.IsSOTrx = ''Y'' and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and sc.m_shipment_constraint_ID = @sc.m_shipment_constraint_ID/-1@  )')
;

-- 2018-02-27T14:44:31.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2018-02-27 14:44:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540840
;

-- 2018-02-27T14:44:42.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540840,Updated=TO_TIMESTAMP('2018-02-27 14:44:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540203
;

-- 2018-02-27T14:46:34.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists   (  select 1         from m_shipment_constraint sc  JOIN c_dunningdoc dd on sc.sourcedoc_record_id = dd.c_dunningdoc_id   JOIN c_dunningdoc_line ddl on dd.c_dunningdoc_id = ddl.c_dunningdoc_id  JOIN c_dunningdoc_line_source dls on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id   JOIN c_dunning_candidate dc on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id   join c_invoice i on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')      where i.IsSOTrx = ''Y'' and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and sc.m_shipment_constraint_ID = @sc.m_shipment_constraint_ID/-1@  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'') )',Updated=TO_TIMESTAMP('2018-02-27 14:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540840
;

-- 2018-02-27T14:49:47.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists   (  select 1         from m_shipment_constraint sc  JOIN c_dunningdoc dd on sc.sourcedoc_record_id = dd.c_dunningdoc_id   JOIN c_dunningdoc_line ddl on dd.c_dunningdoc_id = ddl.c_dunningdoc_id  JOIN c_dunningdoc_line_source dls on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id   JOIN c_dunning_candidate dc on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id   join c_invoice i on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')      where i.IsSOTrx = ''Y'' and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and sc.m_shipment_constraint_ID = @m_shipment_constraint_ID/-1@  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'') )',Updated=TO_TIMESTAMP('2018-02-27 14:49:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540840
;

-- 2018-02-27T14:54:07.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists   (select 1       from c_invoice i             JOIN c_dunning_candidate dc on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')         JOIN c_dunningdoc_line_source dls on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id        JOIN c_dunningdoc_line ddl on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id        JOIN c_dunningdoc dd on dd.c_dunningdoc_id = ddl.c_dunningdoc_id    JOIN m_shipment_constraint sc on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')    where  i.IsSOTrx = ''Y'' AND i.c_invoice_ID = C_Invoice.C_Invoice_ID AND sc.m_shipment_constraint_ID = @m_shipment_constraint_ID/-1@)',Updated=TO_TIMESTAMP('2018-02-27 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540840
;

-- 2018-02-27T14:57:50.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2018-02-27 14:57:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540839
;

-- 2018-02-27T14:58:05.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='1=1',Updated=TO_TIMESTAMP('2018-02-27 14:58:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540839
;

-- 2018-02-27T15:02:00.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='  exists   (  select 1         from m_shipment_constraint sc  JOIN c_dunningdoc dd on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')  JOIN c_dunningdoc_line ddl on dd.c_dunningdoc_id = ddl.c_dunningdoc_id  JOIN c_dunningdoc_line_source dls on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id   JOIN c_dunning_candidate dc on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id   join c_invoice i on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')      where i.IsSOTrx = ''Y'' and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and sc.m_shipment_constraint_ID = @m_shipment_constraint_ID/-1@  )',Updated=TO_TIMESTAMP('2018-02-27 15:02:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540840
;

-- 2018-02-27T15:04:03.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='  exists   (  select 1         from m_shipment_constraint sc  JOIN c_dunningdoc dd on sc.sourcedoc_record_id = dd.c_dunningdoc_id  AND sc.Sourcedoc_Table_ID = get_Table_ID (''C_DunningDoc'')  JOIN c_dunningdoc_line ddl on dd.c_dunningdoc_id = ddl.c_dunningdoc_id  JOIN c_dunningdoc_line_source dls on ddl.c_dunningdoc_line_id = dls.c_dunningdoc_line_id   JOIN c_dunning_candidate dc on dls.c_dunning_candidate_id = dc.c_dunning_candidate_id   join c_invoice i on dc.record_id = i.c_invoice_id AND dc.ad_table_id = get_table_ID(''C_Invoice'')      where i.IsSOTrx = ''Y'' and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and sc.m_shipment_constraint_ID = @M_Shipment_Constraint_ID@  )',Updated=TO_TIMESTAMP('2018-02-27 15:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540840
;

-- 2018-02-27T15:04:58.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=3484,Updated=TO_TIMESTAMP('2018-02-27 15:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540840
;

-- 2018-02-27T15:05:34.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=557309, IsValueDisplayed='Y', WhereClause='',Updated=TO_TIMESTAMP('2018-02-27 15:05:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540839
;

