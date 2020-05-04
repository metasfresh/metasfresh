-- 2018-07-24T18:18:00.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540877,TO_TIMESTAMP('2018-07-24 18:18:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_PaySelection Target for C_Invoice (Sales)',TO_TIMESTAMP('2018-07-24 18:18:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-24T18:18:00.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540877 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-24T18:19:31.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,5609,5609,0,540877,426,TO_TIMESTAMP('2018-07-24 18:19:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2018-07-24 18:19:31','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from C_PaySelectionLine  psl join C_Invoice i on psl.C_Invoice_ID = i.C_Invoice_ID where i.IsSOTrx = ''Y'' and C_PaySelection.C_PaySelection_ID = psl.C_PaySelection_ID and i.C_Invoice_ID = @C_Invoice_ID@)')
;

-- 2018-07-24T18:20:42.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540739,540877,540206,TO_TIMESTAMP('2018-07-24 18:20:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Invoice (Sales) -> C_PaySelection',TO_TIMESTAMP('2018-07-24 18:20:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-24T18:23:27.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2018-07-24 18:23:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540206
;

