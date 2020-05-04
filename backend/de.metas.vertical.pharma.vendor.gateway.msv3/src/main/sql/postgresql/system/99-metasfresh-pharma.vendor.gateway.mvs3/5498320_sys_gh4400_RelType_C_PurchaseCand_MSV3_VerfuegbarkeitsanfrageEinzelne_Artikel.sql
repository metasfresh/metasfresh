-- 2018-07-30T15:01:29.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540888,TO_TIMESTAMP('2018-07-30 15:01:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','C_PurchaseCandidate Source',TO_TIMESTAMP('2018-07-30 15:01:29','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T15:01:29.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540888 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T15:02:26.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,557857,557857,0,540888,540861,540375,TO_TIMESTAMP('2018-07-30 15:02:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N',TO_TIMESTAMP('2018-07-30 15:02:26','YYYY-MM-DD HH24:MI:SS'),100,'')
;

-- 2018-07-30T15:02:44.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540888,540212,TO_TIMESTAMP('2018-07-30 15:02:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','Purchase Cand -> MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel',TO_TIMESTAMP('2018-07-30 15:02:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T15:03:22.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.vertical.pharma.vendor.gateway.msv3',Updated=TO_TIMESTAMP('2018-07-30 15:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;

-- 2018-07-30T15:29:16.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540889,TO_TIMESTAMP('2018-07-30 15:29:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel target olcand',TO_TIMESTAMP('2018-07-30 15:29:16','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T15:29:16.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540889 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T15:31:48.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_OrderLine sol on vea.C_OrderLineSO_ID = sol.C_OrderLine_ID where sol.C_Order_ID = @C_Order_ID@ and   msv3_verfuegbarkeitsanfrageeinzelne.msv3_verfuegbarkeitsanfrageeinzelne_id =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_ID  )',Updated=TO_TIMESTAMP('2018-07-30 15:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;

-- 2018-07-30T15:32:44.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,558807,558807,0,540889,540919,TO_TIMESTAMP('2018-07-30 15:32:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N',TO_TIMESTAMP('2018-07-30 15:32:44','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_PurchaseCandidate pc on vea.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID where pc.C_PurchaseCandidate_ID = @_PurchaseCandidate_ID@ and   msv3_verfuegbarkeitsanfrageeinzelne.msv3_verfuegbarkeitsanfrageeinzelne_id =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_ID  )')
;

-- 2018-07-30T15:33:21.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel target for PC',Updated=TO_TIMESTAMP('2018-07-30 15:33:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;

-- 2018-07-30T15:33:33.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540887,Updated=TO_TIMESTAMP('2018-07-30 15:33:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540212
;

-- 2018-07-30T15:34:11.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.vertical.pharma.vendor.gateway.msv3',Updated=TO_TIMESTAMP('2018-07-30 15:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540212
;

-- 2018-07-30T15:35:03.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_PurchaseCandidate pc on vea.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID where pc.C_PurchaseCandidate_ID = @C_PurchaseCandidate_ID@ and   msv3_verfuegbarkeitsanfrageeinzelne.msv3_verfuegbarkeitsanfrageeinzelne_id =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_ID  )',Updated=TO_TIMESTAMP('2018-07-30 15:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540889
;

-- 2018-07-30T15:38:58.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540407,Updated=TO_TIMESTAMP('2018-07-30 15:38:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540889
;




-- 2018-07-30T16:34:49.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelnetest_Artikel vea join C_OrderLine sol on vea.C_OrderLineSO_ID = sol.C_OrderLine_ID where sol.C_Order_ID = @C_Order_ID@ and   msv3_verfuegbarkeitsanfrageeinzelne.msv3_verfuegbarkeitsanfrageeinzelne_id =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_ID  )',Updated=TO_TIMESTAMP('2018-07-30 16:34:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;

-- 2018-07-30T16:36:59.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_OrderLine sol on vea.C_OrderLineSO_ID = sol.C_OrderLine_ID where sol.C_Order_ID = @C_Order_ID@ and   msv3_verfuegbarkeitsanfrageeinzelne.msv3_verfuegbarkeitsanfrageeinzelne_id =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_ID  )',Updated=TO_TIMESTAMP('2018-07-30 16:36:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;

-- 2018-07-30T16:38:49.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_PurchaseCandidate pc on vea.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID where pc.C_PurchaseCandidate_ID = @C_PurchaseCandidate_ID@ and   msv3_verfuegbarkeitsanfrageeinzelne.msv3_verfuegbarkeitsanfrageeinzelne_id =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_ID  )  ',Updated=TO_TIMESTAMP('2018-07-30 16:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;


