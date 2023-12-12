-- 2022-05-16T08:45:14.135494900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541457,'O',TO_TIMESTAMP('2022-05-16 11:45:13','YYYY-MM-DD HH24:MI:SS'),100,'Specify in what tables should the C_Order.Email be propagated.
The table names will be comma-separated.
If the value is empty, the order email will not be propagated anywhere.','D','Y','de.metas.order.C_Order_Email_Propagation',TO_TIMESTAMP('2022-05-16 11:45:13','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut, C_Invoice, C_DocOutbound_Log')
;

-- 2022-05-16T08:45:55.331143300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Value='N',Updated=TO_TIMESTAMP('2022-05-16 11:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541457
;

-- 2022-05-16T08:48:14.567194300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Specify in what tables should the C_Order.Email be propagated.
The table names will be comma-separated.
If  the order email should  not be propagated anywhere, set the value to ''N''',Updated=TO_TIMESTAMP('2022-05-16 11:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541457
;

-- 2022-05-16T12:51:43.892192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Specify in what tables should the C_Order.Email be propagated.
The table names will be comma-separated. Possible table names are "M_InOut, C_Invoice, C_Doc_Outbound_Log"
If  the order email should  not be propagated anywhere, set the value to ''N''.'
,Updated=TO_TIMESTAMP('2022-05-16 15:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541457
;

