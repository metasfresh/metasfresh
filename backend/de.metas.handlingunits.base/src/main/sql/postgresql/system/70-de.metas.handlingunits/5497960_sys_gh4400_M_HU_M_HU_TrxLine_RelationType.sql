-- 2018-07-25T15:47:20.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540883,TO_TIMESTAMP('2018-07-25 15:47:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','M_HU_Trx_Line Target for M_HU',TO_TIMESTAMP('2018-07-25 15:47:20','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-25T15:47:20.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540883 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-25T15:52:16.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,549243,549243,0,540883,540515,540190,TO_TIMESTAMP('2018-07-25 15:52:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N',TO_TIMESTAMP('2018-07-25 15:52:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-25T15:53:44.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from M_HU_Trx_Line trx where trx.M_HU_ID = M_HU.M_HU_ID and trx.M_HU_ID = @M_HU_ID@)',Updated=TO_TIMESTAMP('2018-07-25 15:53:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540883
;

-- 2018-07-25T16:25:33.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540499,540883,540208,TO_TIMESTAMP('2018-07-25 16:25:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','M_HU -> M_HU_Trx_Line',TO_TIMESTAMP('2018-07-25 16:25:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-25T16:28:06.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from M_HU_Trx_Line trx where trx.M_HU_Trx_Line_ID = M_HU_Trx_Line.M_HU_Trx_Line_ID and trx.M_HU_ID = @M_HU_ID@)',Updated=TO_TIMESTAMP('2018-07-25 16:28:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540883
;

