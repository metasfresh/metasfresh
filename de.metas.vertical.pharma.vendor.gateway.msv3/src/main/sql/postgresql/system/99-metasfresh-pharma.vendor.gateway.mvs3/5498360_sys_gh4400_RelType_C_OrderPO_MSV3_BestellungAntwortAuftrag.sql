-- 2018-07-30T17:12:10.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540676,540215,TO_TIMESTAMP('2018-07-30 17:12:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','N','C_Order(PO) -> MSV3_BestellungAntwortAuftrag',TO_TIMESTAMP('2018-07-30 17:12:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T17:14:59.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540892,TO_TIMESTAMP('2018-07-30 17:14:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','MSV3_BestellungAntwortAuftrag target for PO',TO_TIMESTAMP('2018-07-30 17:14:59','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T17:14:59.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540892 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T17:15:54.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,558715,558715,0,540892,540911,540402,TO_TIMESTAMP('2018-07-30 17:15:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N',TO_TIMESTAMP('2018-07-30 17:15:54','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from  MSV3_BestellungAntwort ba join MSV3_BestellungAntwortAuftrag baa on ba.MSV3_BestellungAntwort_ID = baa.MSV3_BestellungAntwort_ID  join C_Order po on baa.C_OrderPO_ID = po.C_Order_ID  where po.C_Order_ID = @C_Order_ID@ and ba.MSV3_BestellungAntwort_ID = MSV3_BestellungAntwort.MSV3_BestellungAntwort_ID)')
;

-- 2018-07-30T17:16:22.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540892,Updated=TO_TIMESTAMP('2018-07-30 17:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540215
;






