
-- 2018-07-30T14:27:55.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540666,540211,TO_TIMESTAMP('2018-07-30 14:27:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Order (SO) -> MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel',TO_TIMESTAMP('2018-07-30 14:27:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-30T14:28:46.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540887,TO_TIMESTAMP('2018-07-30 14:28:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel target for OL',TO_TIMESTAMP('2018-07-30 14:28:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-30T14:28:46.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540887 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-30T14:32:35.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,558816,558816,0,540887,540920,540407,TO_TIMESTAMP('2018-07-30 14:32:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.vendor.gateway.msv3','Y','N',TO_TIMESTAMP('2018-07-30 14:32:35','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from  VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_OrderLine sol on vea.C_OrderLineSO_ID = sol.C_OrderLine_ID where sol.C_Order_ID = @C_Order_ID@ and  VerfuegbarkeitsanfrageEinzelne_Artikel.VerfuegbarkeitsanfrageEinzelne_Artikel_ID =  vea.VerfuegbarkeitsanfrageEinzelne_Artikel_ID  )')
;

-- 2018-07-30T14:33:34.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540887,Updated=TO_TIMESTAMP('2018-07-30 14:33:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540211
;

-- 2018-07-30T14:34:56.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_OrderLine sol on vea.C_OrderLineSO_ID = sol.C_OrderLine_ID where sol.C_Order_ID = @C_Order_ID@ and  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel_ID  )',Updated=TO_TIMESTAMP('2018-07-30 14:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;

-- 2018-07-30T14:44:45.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=558807, AD_Key=558807, AD_Table_ID=540919, WhereClause='exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_OrderLine sol on vea.C_OrderLineSO_ID = sol.C_OrderLine_ID where sol.C_Order_ID = @C_Order_ID@ and  MSV3_VerfuegbarkeitsanfrageEinzelne.MSV3_VerfuegbarkeitsanfrageEinzelnel_ID =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_ID  )',Updated=TO_TIMESTAMP('2018-07-30 14:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;



-- 2018-07-30T15:03:22.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.vertical.pharma.vendor.gateway.msv3',Updated=TO_TIMESTAMP('2018-07-30 15:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;


-- 2018-07-30T15:31:48.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from  MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel vea join C_OrderLine sol on vea.C_OrderLineSO_ID = sol.C_OrderLine_ID where sol.C_Order_ID = @C_Order_ID@ and   msv3_verfuegbarkeitsanfrageeinzelne.msv3_verfuegbarkeitsanfrageeinzelne_id =  vea.MSV3_VerfuegbarkeitsanfrageEinzelne_ID  )',Updated=TO_TIMESTAMP('2018-07-30 15:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540887
;

