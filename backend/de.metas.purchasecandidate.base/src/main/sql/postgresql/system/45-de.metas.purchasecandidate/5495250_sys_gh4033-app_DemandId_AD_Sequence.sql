
-- 2018-06-06T13:25:45.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554560,TO_TIMESTAMP('2018-06-06 13:25:45','YYYY-MM-DD HH24:MI:SS'),100,10000,10000000,'DemandId used to group C_PurchaseCandidates that belong to the same sales order line, requisition etc.',1,'Y','N','Y','N','DocumentNo_DemandId','N',10000000,TO_TIMESTAMP('2018-06-06 13:25:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-06T13:26:44.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='Used to group C_PurchaseCandidates that belong to the same sales order line, requisition etc. Needs to be configured via AD_SysConfig de.metas.purchasecandidate.DemandId_AD_Sequence',Updated=TO_TIMESTAMP('2018-06-06 13:26:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554560
;

-- 2018-06-06T13:27:56.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='Used to group C_PurchaseCandidates that belong to the same sales order line, requisition etc. Needs to be configured via AD_SysConfig de.metas.purchasecandidate.DemandId_AD_Sequence_ID',Updated=TO_TIMESTAMP('2018-06-06 13:27:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554560
;

-- 2018-06-06T13:32:49.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541217,'O',TO_TIMESTAMP('2018-06-06 13:32:49','YYYY-MM-DD HH24:MI:SS'),100,'AD_Sequence_ID of the sequence that is used when the system creates a new DemandId for C_PurchaseCandidates','de.metas.purchasecandidate','Y','de.metas.purchasecandidate.DemandId_AD_Sequence_ID',TO_TIMESTAMP('2018-06-06 13:32:49','YYYY-MM-DD HH24:MI:SS'),100,'554560')
;

