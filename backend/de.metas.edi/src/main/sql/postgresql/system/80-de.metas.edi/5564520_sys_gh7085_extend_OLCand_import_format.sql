-- 2020-07-30T10:19:16.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','DropShip_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2020-07-30T10:19:37.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','DropShip_Location_ID','NUMERIC(10)',null,null)
;

-- 2020-07-30T10:20:14.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','HandOver_Partner_ID','NUMERIC(10)',null,null)
;

-- 2020-07-30T10:31:20.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571074,0,TO_TIMESTAMP('2020-07-30 12:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to ship to','de.metas.esb.edi',540405,550291,'If empty the business partner will be shipped to.','N','N','Lieferempfänger',260,'R',TO_TIMESTAMP('2020-07-30 12:31:20','YYYY-MM-DD HH24:MI:SS'),100,'DropShip_BPartner_ID')
;

-- 2020-07-30T10:31:20.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571073,0,TO_TIMESTAMP('2020-07-30 12:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Location for shipping to','de.metas.esb.edi',540405,550292,'N','N','Lieferadresse',270,'R',TO_TIMESTAMP('2020-07-30 12:31:20','YYYY-MM-DD HH24:MI:SS'),100,'DropShip_Location_ID')
;

-- 2020-07-30T10:31:20.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552776,0,TO_TIMESTAMP('2020-07-30 12:31:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550293,'N','Y','Geliefert %',280,'E',TO_TIMESTAMP('2020-07-30 12:31:20','YYYY-MM-DD HH24:MI:SS'),100,'FulfillmentPercent')
;

-- 2020-07-30T10:31:21.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552779,0,TO_TIMESTAMP('2020-07-30 12:31:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550294,'N','N','Geliefert % Minimum',290,'E',TO_TIMESTAMP('2020-07-30 12:31:20','YYYY-MM-DD HH24:MI:SS'),100,'FulfillmentPercentMin')
;

-- 2020-07-30T10:31:21.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571075,0,TO_TIMESTAMP('2020-07-30 12:31:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550295,'N','N','Übergabe-Partner',300,'R',TO_TIMESTAMP('2020-07-30 12:31:21','YYYY-MM-DD HH24:MI:SS'),100,'HandOver_Partner_ID')
;

-- 2020-07-30T10:31:21.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569830,0,TO_TIMESTAMP('2020-07-30 12:31:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550296,'N','Y','SumDeliveredInStockingUOM',310,'E',TO_TIMESTAMP('2020-07-30 12:31:21','YYYY-MM-DD HH24:MI:SS'),100,'SumDeliveredInStockingUOM')
;

-- 2020-07-30T10:31:21.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569831,0,TO_TIMESTAMP('2020-07-30 12:31:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540405,550297,'N','Y','SumOrderedInStockingUOM',320,'E',TO_TIMESTAMP('2020-07-30 12:31:21','YYYY-MM-DD HH24:MI:SS'),100,'SumOrderedInStockingUOM')
;

-- 2020-07-30T10:31:21.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,554400,0,TO_TIMESTAMP('2020-07-30 12:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Can be used to flag records and thus make them selectable from the UI via advanced search.','de.metas.esb.edi',540405,550298,'N','N','UserFlag',330,'E',TO_TIMESTAMP('2020-07-30 12:31:21','YYYY-MM-DD HH24:MI:SS'),100,'UserFlag')
;

-- 2020-07-30T10:32:44.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540396, IsActive='Y', Name='DropShip_Location_ID',Updated=TO_TIMESTAMP('2020-07-30 12:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550292
;

-- 2020-08-11T14:10:24.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2020-08-11 16:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54523
;

--ALTER TABLE EXP_FormatLine DROP COLUMN DateFormat;
-- 2020-08-11T14:12:59.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='Y',Updated=TO_TIMESTAMP('2020-08-11 16:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54523
;
