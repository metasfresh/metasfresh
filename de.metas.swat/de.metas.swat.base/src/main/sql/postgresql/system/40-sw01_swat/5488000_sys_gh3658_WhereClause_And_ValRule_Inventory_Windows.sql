-- 2018-03-12T15:38:14.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause=' EXISTS (Select 1 from  M_Inventry i  JOIN C_DocType dt on i.CDoctYpe_ID  = dt.C_DocType_ID  WHERE dt.DocBaseType = ''MMI'' AND dt.DocSubType IS NULL )',Updated=TO_TIMESTAMP('2018-03-12 15:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=255
;

-- 2018-03-12T15:48:23.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause=' EXISTS (Select 1 from  M_Inventry i  JOIN C_DocType dt on i.CDoctYpe_ID  = dt.C_DocType_ID  WHERE dt.DocBaseType = ''MMI'' AND dt.DocSubType = ''IUI'' )',Updated=TO_TIMESTAMP('2018-03-12 15:48:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=682
;

-- 2018-03-12T15:50:27.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause=' EXISTS (Select 1 from  M_Inventory i  JOIN C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  WHERE dt.DocBaseType = ''MMI'' AND dt.DocSubType = ''IUI'' )',Updated=TO_TIMESTAMP('2018-03-12 15:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=682
;

-- 2018-03-12T15:51:10.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause=' EXISTS (Select 1 from  M_Inventory i  JOIN C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  WHERE dt.DocBaseType = ''MMI'' AND dt.DocSubType IS NULL )',Updated=TO_TIMESTAMP('2018-03-12 15:51:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=255
;

-- 2018-03-12T15:54:01.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2018-03-12 15:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10812
;

-- 2018-03-12T15:56:12.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540392,'C_DocType.DcBaseType = ''MMI'' AND C_DocType. DocSubType = ''IUI''',TO_TIMESTAMP('2018-03-12 15:56:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','M_Inventory_InternalUseInventory','S',TO_TIMESTAMP('2018-03-12 15:56:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-12T15:56:56.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Val_Rule_ID=540392,Updated=TO_TIMESTAMP('2018-03-12 15:56:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10812
;

-- 2018-03-12T15:57:21.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540393,'C_DocType.DcBaseType = ''MMI'' AND C_DocType. DocSubType is null',TO_TIMESTAMP('2018-03-12 15:57:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','M_Inventory_PhysicalInventory','S',TO_TIMESTAMP('2018-03-12 15:57:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-12T16:07:06.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2018-03-12 16:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10812
;

-- 2018-03-12T16:07:31.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Val_Rule_ID=540393,Updated=TO_TIMESTAMP('2018-03-12 16:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10812
;

-- 2018-03-12T16:08:06.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=19, AD_Val_Rule_ID=540392,Updated=TO_TIMESTAMP('2018-03-12 16:08:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10969
;

-- 2018-03-12T16:11:11.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType = ''MMI'' AND C_DocType. DocSubType is null',Updated=TO_TIMESTAMP('2018-03-12 16:11:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540393
;

-- 2018-03-12T16:11:21.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType = ''MMI'' AND C_DocType. DocSubType = ''IUI''',Updated=TO_TIMESTAMP('2018-03-12 16:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540392
;

-- 2018-03-12T16:34:09.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause=' EXISTS (select 1 from  M_Inventory i  join C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  where dt.DocBaseType = ''MMI'' and dt.DocSubType is null)',Updated=TO_TIMESTAMP('2018-03-12 16:34:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=255
;

-- 2018-03-12T16:39:43.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='select * from  M_Inventory i  join C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  where dt.DocBaseType = ''MMI'' and dt.DocSubType is null and M_inventory.M_Inventory_ID = i.m_inventory_ID',Updated=TO_TIMESTAMP('2018-03-12 16:39:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=255
;

-- 2018-03-12T16:41:41.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='    M_Inventory.M_Inventory_ID in(Select i.M_Inventory_ID from  M_Inventory i  JOIN C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  WHERE dt.DocBaseType = ''MMI'' AND dt.DocSubType IS NULL)',Updated=TO_TIMESTAMP('2018-03-12 16:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=255
;

-- 2018-03-12T16:43:13.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='    M_Inventory.M_Inventory_ID in(Select i.M_Inventory_ID from  M_Inventory i  JOIN C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  WHERE dt.DocBaseType = ''MMI'' AND dt.DocSubType = ''IUI'')',Updated=TO_TIMESTAMP('2018-03-12 16:43:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=682
;

-- 2018-03-12T16:44:10.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause=' M_Inventory.M_Inventory_ID in (select i.M_Inventory_ID from  M_Inventory i  join C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  where dt.DocBaseType = ''MMI'' and dt.DocSubType = ''IUI'')',Updated=TO_TIMESTAMP('2018-03-12 16:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=682
;

-- 2018-03-12T16:44:43.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='M_Inventory.M_Inventory_ID in (select i.M_Inventory_ID from  M_Inventory i  join C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  where  dt.DocBaseType = ''MMI'' and dt.DocSubType IS NULL)',Updated=TO_TIMESTAMP('2018-03-12 16:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=255
;

