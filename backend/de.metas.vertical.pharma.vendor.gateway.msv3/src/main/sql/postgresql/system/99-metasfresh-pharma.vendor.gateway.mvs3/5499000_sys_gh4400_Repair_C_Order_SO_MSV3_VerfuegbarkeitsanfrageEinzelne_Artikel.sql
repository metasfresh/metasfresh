-- 2018-08-07T18:26:57.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540889
;

-- 2018-08-07T18:26:57.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540889
;

-- 2018-08-07T18:28:20.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540901,TO_TIMESTAMP('2018-08-07 18:28:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N','MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel target for SO',TO_TIMESTAMP('2018-08-07 18:28:20','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-08-07T18:28:20.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540901 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-08-07T18:32:13.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,558807,558807,0,540901,540919,540407,TO_TIMESTAMP('2018-08-07 18:32:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N',TO_TIMESTAMP('2018-08-07 18:32:13','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_OrderLine ol on vea.C_OrderLineSO_ID =ol.C_OrderLine_ID where ol.C_Order_ID = @C_Order_ID@ and   msv3_verfuegbarkeitsanfrageeinzelne.msv3_verfuegbarkeitsanfrageeinzelne_id =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_ID  )')
;

-- 2018-08-07T18:33:09.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540901,Updated=TO_TIMESTAMP('2018-08-07 18:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540211
;

