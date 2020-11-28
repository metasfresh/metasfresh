-- 2018-07-30T17:04:40.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540888,540214,TO_TIMESTAMP('2018-07-30 17:04:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','N','Purchase Cand -> MSV3_BestellungPosition',TO_TIMESTAMP('2018-07-30 17:04:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T17:05:14.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540891,TO_TIMESTAMP('2018-07-30 17:05:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','MSV3_BestellungPosition target for Purchase Cand',TO_TIMESTAMP('2018-07-30 17:05:13','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T17:05:14.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540891 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T17:06:11.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,558772,558772,0,540891,540916,540401,TO_TIMESTAMP('2018-07-30 17:06:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N',TO_TIMESTAMP('2018-07-30 17:06:11','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from  MSV3_Bestellung b join MSV3_BestellungAuftrag ba on b.MSV3_Bestellung_ID = ba.MSV3_Bestellung_ID join MSV3_BestellungPosition bp on ba.MSV3_BestellungAuftrag_ID = bp.MSV3_BestellungAuftrag_ID join C_PurchaseCandidate pc on bp.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID where pc.C_PurchaseCandidate_ID = @C_PurchaseCandidate_ID@ and b.MSV3_Bestellung_ID = MSV3_Bestellung.MSV3_Bestellung_ID)')
;

-- 2018-07-30T17:06:44.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540891,Updated=TO_TIMESTAMP('2018-07-30 17:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540214
;

