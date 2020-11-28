






-- 2018-07-30T17:35:58.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540676,540216,TO_TIMESTAMP('2018-07-30 17:35:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','Y','N','C_OrderLinePO -> MSV3_BestellungAnteil',TO_TIMESTAMP('2018-07-30 17:35:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T17:36:27.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540893,TO_TIMESTAMP('2018-07-30 17:36:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','MSV3_BestellungAnteil target for Purchase Order Line',TO_TIMESTAMP('2018-07-30 17:36:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T17:36:27.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540893 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T17:40:06.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,558715,558715,0,540893,540911,540402,TO_TIMESTAMP('2018-07-30 17:40:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N',TO_TIMESTAMP('2018-07-30 17:40:06','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from  MSV3_BestellungAntwort ba  join MSV3_BestellungAntwortAuftrag baa on ba.MSV3_BestellungAntwort_ID = baa.MSV3_BestellungAntwort_ID  join MSV3_BestellungAntwortPosition bap on baa.MSV3_BestellungAntwortAuftrag_ID = bap.MSV3_BestellungAntwortAuftrag_ID  join MSV3_BestellungAnteil bant on bap.MSV3_BestellungAntwortPosition_ID = bant.MSV3_BestellungAntwortPosition_ID join C_OrderLine ol on bant.C_OrderLinePO_ID = ol.C_OrderLine_ID  where ol.C_Order_ID = @C_Order_ID@  and ba.MSV3_BestellungAntwort_ID = MSV3_BestellungAntwort.MSV3_BestellungAntwort_ID)')
;

-- 2018-07-30T17:40:17.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540893,Updated=TO_TIMESTAMP('2018-07-30 17:40:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540216
;

-- 2018-07-30T17:41:27.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_BestellungAntwort ba  join MSV3_BestellungAntwortAuftrag baa on ba.MSV3_BestellungAntwort_ID = baa.MSV3_BestellungAntwort_ID  join MSV3_BestellungAntwortPosition bap on baa.MSV3_BestellungAntwortAuftrag_ID = bap.MSV3_BestellungAntworttestAuftrag_ID  join MSV3_BestellungAnteil bant on bap.MSV3_BestellungAntwortPosition_ID = bant.MSV3_BestellungAntwortPosition_ID join C_OrderLine ol on bant.C_OrderLinePO_ID = ol.C_OrderLine_ID  where ol.C_Order_ID = @C_Order_ID@  and ba.MSV3_BestellungAntwort_ID = MSV3_BestellungAntwort.MSV3_BestellungAntwort_ID)',Updated=TO_TIMESTAMP('2018-07-30 17:41:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540893
;

-- 2018-07-30T17:44:59.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_BestellungAntwort ba  join MSV3_BestellungAntwortAuftrag baa on ba.MSV3_BestellungAntwort_ID = baa.MSV3_BestellungAntwort_ID  join MSV3_BestellungAntwortPosition bap on baa.MSV3_BestellungAntwortAuftrag_ID = bap.MSV3_BestellungAntwortAuftrag_ID  join MSV3_BestellungAnteil bant on bap.MSV3_BestellungAntwortPosition_ID = bant.MSV3_BestellungAntwortPosition_ID join C_OrderLine ol on bant.C_OrderLinePO_ID = ol.C_OrderLine_ID  where ol.C_Order_ID = @C_Order_ID@  and ba.MSV3_BestellungAntwort_ID = MSV3_BestellungAntwort.MSV3_BestellungAntwort_ID)',Updated=TO_TIMESTAMP('2018-07-30 17:44:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540893
;

