-- 2019-07-10T12:54:40.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-07-10 12:54:40','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-07-10 12:54:40','YYYY-MM-DD HH24:MI:SS'),100,541012,'T','C_DunningDoc_for_C_Invoice',0,'D')
;

-- 2019-07-10T12:54:40.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541012 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-07-10T12:57:42.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,WhereClause,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541012,547408,' EXISTS (     SELECT 1     from c_dunningdoc dd              INNER JOIN c_dunningdoc_line ddl on dd.c_dunningdoc_id = ddl.c_dunningdoc_id              INNER JOIN c_dunningdoc_line_source ddls on ddl.c_dunningdoc_line_id = ddls.c_dunningdoc_line_id              INNER JOIN c_dunning_candidate dc on ddls.c_dunning_candidate_id = dc.c_dunning_candidate_id              INNER JOIN c_invoice inv on dc.documentno = inv.documentno     where 1 = 1       AND inv.issotrx = ''Y''       AND C_dunningdoc.c_dunningdoc_id = dd.c_dunningdoc_id       AND inv.c_invoice_id = @C_Invoice_ID / -1@ )',0,'Y',TO_TIMESTAMP('2019-07-10 12:57:42','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-07-10 12:57:42','YYYY-MM-DD HH24:MI:SS'),'N',540155,100,540401,0,'D')
;

-- 2019-07-10T12:58:48.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (Created,CreatedBy,IsActive,AD_Client_ID,Updated,UpdatedBy,AD_RelationType_ID,AD_Reference_Target_ID,AD_Org_ID,IsTableRecordIdTarget,IsDirected,AD_Reference_Source_ID,Name,EntityType) VALUES (TO_TIMESTAMP('2019-07-10 12:58:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,TO_TIMESTAMP('2019-07-10 12:58:48','YYYY-MM-DD HH24:MI:SS'),100,540226,541012,0,'N','Y',540678,'C_Invoice -> C_DunningDoc','D')
;

-- 2019-07-10T13:03:04.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-07-10 13:03:04','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-07-10 13:03:04','YYYY-MM-DD HH24:MI:SS'),100,541013,'T','C_DunningDoc Source',0,'D')
;

-- 2019-07-10T13:03:04.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541013 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-07-10T13:03:46.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541013,547408,0,'Y',TO_TIMESTAMP('2019-07-10 13:03:46','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-07-10 13:03:46','YYYY-MM-DD HH24:MI:SS'),'N',540155,100,540401,0,'D')
;

-- 2019-07-10T13:05:23.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET Name='Dunning Documents',Updated=TO_TIMESTAMP('2019-07-10 13:05:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540155 AND AD_Language='en_US'
;

-- 2019-07-10T13:05:59.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_DunningDoc_Source',Updated=TO_TIMESTAMP('2019-07-10 13:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541013
;

-- 2019-07-10T13:06:03.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (Created,CreatedBy,IsActive,AD_Client_ID,Updated,UpdatedBy,AD_RelationType_ID,AD_Org_ID,IsTableRecordIdTarget,IsDirected,AD_Reference_Source_ID,Name,EntityType) VALUES (TO_TIMESTAMP('2019-07-10 13:06:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,TO_TIMESTAMP('2019-07-10 13:06:03','YYYY-MM-DD HH24:MI:SS'),100,540227,0,'N','Y',541013,'C_DunningDoc -> C_Invoice','de.metas.swat')
;

-- 2019-07-10T13:11:52.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-07-10 13:11:52','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-07-10 13:11:52','YYYY-MM-DD HH24:MI:SS'),100,541014,'T','C_Invoice_for_C_DunningDoc',0,'D')
;

-- 2019-07-10T13:11:52.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541014 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-07-10T13:17:47.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,WhereClause,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541014,3484,'EXISTS (     SELECT 1     from c_dunningdoc dd              INNER JOIN c_dunningdoc_line ddl on dd.c_dunningdoc_id = ddl.c_dunningdoc_id              INNER JOIN c_dunningdoc_line_source ddls on ddl.c_dunningdoc_line_id = ddls.c_dunningdoc_line_id              INNER JOIN c_dunning_candidate dc on ddls.c_dunning_candidate_id = dc.c_dunning_candidate_id              INNER JOIN c_invoice inv on dc.documentno = inv.documentno     where 1 = 1       AND c_invoice.c_invoice_id = inv.c_invoice_id       AND dd.c_dunningdoc_id = @C_DunningDoc_ID / -1@ )',0,'Y',TO_TIMESTAMP('2019-07-10 13:17:47','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-07-10 13:17:47','YYYY-MM-DD HH24:MI:SS'),'N',167,100,318,0,'D')
;

-- 2019-07-10T13:18:03.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541014,Updated=TO_TIMESTAMP('2019-07-10 13:18:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540227
;

-- 2019-07-10T13:19:38.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Sales_Invoice_for_C_DunningDoc',Updated=TO_TIMESTAMP('2019-07-10 13:19:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541014
;

