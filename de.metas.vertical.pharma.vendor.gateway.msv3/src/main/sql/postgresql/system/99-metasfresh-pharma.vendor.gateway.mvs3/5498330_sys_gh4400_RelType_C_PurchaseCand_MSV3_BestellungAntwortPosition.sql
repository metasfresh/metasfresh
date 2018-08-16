-- 2018-07-30T16:12:58.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540888,540213,TO_TIMESTAMP('2018-07-30 16:12:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','N','Purchase Cand -> MSV3_BestellungAntwortPosition',TO_TIMESTAMP('2018-07-30 16:12:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T16:13:27.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540890,TO_TIMESTAMP('2018-07-30 16:13:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','MSV3_BestellungAntwortPosition target for PC',TO_TIMESTAMP('2018-07-30 16:13:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T16:13:27.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540890 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T16:22:18.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,558715,0,540890,540911,540402,TO_TIMESTAMP('2018-07-30 16:22:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N',TO_TIMESTAMP('2018-07-30 16:22:18','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from  MSV3_BestellungAntwortPosition bap  join C_PurchaseCandidate pc on bap.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID where pc.C_PurchaseCandidate_ID = @C_PurchaseCandidate_ID@ and bap.MSV3_BestellungAntwortPosition_ID = MSV3_BestellungAntwortPosition.MSV3_BestellungAntwortPosition_ID)')
;

-- 2018-07-30T16:22:45.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=558734, AD_Key=558734, AD_Table_ID=540913,Updated=TO_TIMESTAMP('2018-07-30 16:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540890
;

-- 2018-07-30T16:23:07.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540890,Updated=TO_TIMESTAMP('2018-07-30 16:23:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540213
;

-- 2018-07-30T16:27:28.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=558715, AD_Key=558715, AD_Table_ID=540911, WhereClause='exists ( select 1 from  MSV3_BestellungAntwort ba join MSV3_BestellungAntwortAuftrag baa on ba.MSV3_BestellungAntwort_ID = baa.MSV3_BestellungAntwort_ID join MSV3_BestellungAntwortPosition bap on baa.MSV3_BestellungAntwortAuftrag_ID = bap.MSV3_BestellungAntwortAuftrag_ID join C_PurchaseCandidate pc on bap.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID where pc.C_PurchaseCandidate_ID = @C_PurchaseCandidate_ID@ and bap.MSV3_BestellungAntwortPosition_ID = MSV3_BestellungAntwortPosition.MSV3_BestellungAntwortPosition_ID)',Updated=TO_TIMESTAMP('2018-07-30 16:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540890
;

-- 2018-07-30T16:28:33.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_BestellungAntwort ba join MSV3_BestellungAntwortAuftrag baa on ba.MSV3_BestellungAntwort_ID = baa.MSV3_BestellungAntwort_ID join MSV3_BestellungAntwortPosition bap on baa.MSV3_BestellungAntwortAuftrag_ID = bap.MSV3_BestellungAntwortAuftrag_ID join C_PurchaseCandidate pc on bap.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID where pc.C_PurchaseCandidate_ID = @C_PurchaseCandidate_ID@ and ba.MSV3_BestellungAntwort_ID = MSV3_BestellungAntwort.MSV3_BestellungAntwort_ID)',Updated=TO_TIMESTAMP('2018-07-30 16:28:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540890
;

-- 2018-07-30T16:29:50.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2018-07-30 16:29:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540213
;

-- 2018-07-30T16:30:18.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_BestellungAntworttest ba join MSV3_BestellungAntwortAuftrag baa on ba.MSV3_BestellungAntwort_ID = baa.MSV3_BestellungAntwort_ID join MSV3_BestellungAntwortPosition bap on baa.MSV3_BestellungAntwortAuftrag_ID = bap.MSV3_BestellungAntwortAuftrag_ID join C_PurchaseCandidate pc on bap.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID where pc.C_PurchaseCandidate_ID = @C_PurchaseCandidate_ID@ and ba.MSV3_BestellungAntwort_ID = MSV3_BestellungAntwort.MSV3_BestellungAntwort_ID)',Updated=TO_TIMESTAMP('2018-07-30 16:30:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540890
;

-- 2018-07-30T16:32:44.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_BestellungAntwort ba join MSV3_BestellungAntwortAuftrag baa on ba.MSV3_BestellungAntwort_ID = baa.MSV3_BestellungAntwort_ID join MSV3_BestellungAntwortPosition bap on baa.MSV3_BestellungAntwortAuftrag_ID = bap.MSV3_BestellungAntwortAuftrag_ID join C_PurchaseCandidate pc on bap.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID where pc.C_PurchaseCandidate_ID = @C_PurchaseCandidate_ID@ and ba.MSV3_BestellungAntwort_ID = MSV3_BestellungAntwort.MSV3_BestellungAntwort_ID)',Updated=TO_TIMESTAMP('2018-07-30 16:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540890
;

