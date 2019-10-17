-- 2019-05-21T20:14:36.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540670,540225,TO_TIMESTAMP('2019-05-21 20:14:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','M_InOut -> Customs Invoice',TO_TIMESTAMP('2019-05-21 20:14:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-21T20:15:05.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540999,TO_TIMESTAMP('2019-05-21 20:15:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Customs Invoice Target For Shipment',TO_TIMESTAMP('2019-05-21 20:15:05','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-05-21T20:15:05.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=540999 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-05-21T20:17:06.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,567982,567982,0,540999,541360,169,TO_TIMESTAMP('2019-05-21 20:17:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2019-05-21 20:17:06','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from C_Customs_Invoice i join M_InOut io on i.C_Customs_Invoice_ID = io.C_Customs_Invoice_ID and io.M_InOut_ID = @M_InOut_ID@)')
;

-- 2019-05-21T20:17:25.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540999, EntityType='D',Updated=TO_TIMESTAMP('2019-05-21 20:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540225
;

-- 2019-05-21T20:19:00.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540643,Updated=TO_TIMESTAMP('2019-05-21 20:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540999
;

-- 2019-05-21T20:20:53.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2019-05-21 20:20:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540225
;

-- 2019-05-21T20:22:56.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from C_Customs_Invoice i join M_InOut io on i.C_Customs_Invoice_ID = io.C_Customs_Invoice_ID and io.M_InOut_ID = @M_InOut_ID/-1@)',Updated=TO_TIMESTAMP('2019-05-21 20:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540999
;

-- 2019-05-21T20:24:12.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from C_Customs_Invoice i join M_InOut io on i.C_Customs_Invoice_ID = io.C_Customs_Invoice_ID where  io.M_InOut_ID = @M_InOut_ID/-1@)',Updated=TO_TIMESTAMP('2019-05-21 20:24:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540999
;

-- 2019-05-21T20:25:57.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from C_Customs_Invoice i join M_InOut io on i.C_Customs_Invoice_ID = io.C_Customs_Invoice_ID where  io.M_InOut_ID = @M_InOut_ID/-1@ AND C_Customs_Invoice.C_Customs_Invoice_ID = i.C_Customs_Invoice_ID)',Updated=TO_TIMESTAMP('2019-05-21 20:25:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540999
;

