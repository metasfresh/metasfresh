-- 2021-11-05T18:14:55.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541483,TO_TIMESTAMP('2021-11-05 19:14:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Reltype_Source_DHLDeliveryOrder->Transportation_Order',TO_TIMESTAMP('2021-11-05 19:14:55','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-05T18:14:55.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541483 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-05T18:15:31.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,569224,0,541483,541419,TO_TIMESTAMP('2021-11-05 19:15:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-05 19:15:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T18:16:32.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541484,TO_TIMESTAMP('2021-11-05 19:16:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Reltype_Target_DHLDeliveryOrder->Transportation_Order',TO_TIMESTAMP('2021-11-05 19:16:26','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-05T18:16:32.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541484 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-05T18:22:03.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,540426,0,541484,540030,TO_TIMESTAMP('2021-11-05 19:22:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-05 19:22:03','YYYY-MM-DD HH24:MI:SS'),100,'M_ShipperTransportation_ID=@M_ShipperTransportation_ID/-1@')
;

-- 2021-11-05T18:24:57.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541483,541484,540326,TO_TIMESTAMP('2021-11-05 19:24:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','DHL Delivery Order->Transportation Order',TO_TIMESTAMP('2021-11-05 19:24:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T18:34:12.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540743,Updated=TO_TIMESTAMP('2021-11-05 19:34:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541483
;

-- 2021-11-05T18:47:37.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540020,Updated=TO_TIMESTAMP('2021-11-05 19:47:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541484
;

-- 2021-11-05T18:48:44.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_ShipperTransportation_ID.M_ShipperTransportation_ID=@M_ShipperTransportation_ID/-1@',Updated=TO_TIMESTAMP('2021-11-05 19:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541484
;

-- 2021-11-05T18:54:34.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_ShipperTransportation.M_ShipperTransportation_ID=@M_ShipperTransportation_ID/-1@',Updated=TO_TIMESTAMP('2021-11-05 19:54:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541484
;



-- 2021-11-08T09:31:57.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='M_ShipperTransportation.M_ShipperTransportation_ID IN (select dhl.m_shippertransportation_id                              from dhl_shipmentorder dhl                                                                                           where dhl.dhl_shipmentorder_id=@DHL_ShipmentOrder_ID/-1@)',Updated=TO_TIMESTAMP('2021-11-08 10:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541484
;

-- 2021-11-08T09:34:51.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-08 10:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540326
;

-- 2021-11-08T09:35:28.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540743,Updated=TO_TIMESTAMP('2021-11-08 10:35:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541483
;

-- 2021-11-08T10:18:11.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-11-08 11:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569086
;


