-- 2018-06-13T08:09:26.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=562471
;

-- 2018-06-13T08:09:26.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=562471
;

SELECT db_alter_table('C_PurchaseCandidate', 'ALTER TABLE C_PurchaseCandidate DROP COLUMN IF EXISTS C_BPartner_Product_ID');

-- 2018-06-13T08:11:38.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558618
;

-- 2018-06-13T08:11:38.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=558618
;

-- 2018-06-13T08:12:06.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='DemandReference',Updated=TO_TIMESTAMP('2018-06-13 08:12:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544110
;

-- 2018-06-13T08:12:06.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DemandReference', Name='Bedarfs-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544110
;

-- 2018-06-13T08:12:06.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DemandReference', Name='Bedarfs-ID', Description=NULL, Help=NULL, AD_Element_ID=544110 WHERE UPPER(ColumnName)='DEMANDREFERENCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-06-13T08:12:06.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DemandReference', Name='Bedarfs-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544110 AND IsCentrallyMaintained='Y'
;

SELECT db_alter_table('C_PurchaseCandidate', 'ALTER TABLE C_PurchaseCandidate RENAME COLUMN PurchaseDemandId TO DemandReference');

-- 2018-06-13T08:19:03.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='AD_Sequence_ID of the sequence that is used when the system creates a new DemandReferences for C_PurchaseCandidates', Name='de.metas.purchasecandidate.DemandReference_AD_Sequence_ID',Updated=TO_TIMESTAMP('2018-06-13 08:19:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541217
;

-- 2018-06-13T08:19:57.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='Used to group C_PurchaseCandidates that belong to the same sales order line, requisition etc. Needs to be configured via AD_SysConfig de.metas.purchasecandidate.DemandReference_AD_Sequence_ID', Name='DocumentNo_DemandReference',Updated=TO_TIMESTAMP('2018-06-13 08:19:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554560
;

