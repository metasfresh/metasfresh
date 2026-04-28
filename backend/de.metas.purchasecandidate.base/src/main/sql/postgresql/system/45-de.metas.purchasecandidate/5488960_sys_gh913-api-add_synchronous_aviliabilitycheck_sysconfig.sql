
-- 2018-03-20T21:08:39.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541200,'S',TO_TIMESTAMP('2018-03-20 21:08:38','YYYY-MM-DD HH24:MI:SS'),100,'If Y, then the webui''s purchase dispo window will display the purchase candidates and then do availiability queries asynchronously.
It will display the availiability results as soon as they are available.

If N, the system will make the availiability queries and not display anything before all the infos are there.','de.metas.purchasecandidate','Y','de.metas.ui.web.order.sales.purchasePlanning.view.SalesOrder2PurchaseViewFactory.AsyncAvailiabilityCheck',TO_TIMESTAMP('2018-03-20 21:08:38','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

