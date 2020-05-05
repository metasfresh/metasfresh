-- 2018-01-05T13:24:29.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,ColumnName,AD_Element_ID,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Deliver To BPartner','GO_DeliverToBPartner_ID',543741,0,'Deliver To BPartner','de.metas.shipper.gateway.go',100,TO_TIMESTAMP('2018-01-05 13:24:29','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-05 13:24:29','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-05T13:24:29.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543741 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-05T13:25:01.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,ColumnName,AD_Element_ID,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Deliver To BPartner Location','GO_DeliverToBPLocation_ID',543742,0,'Deliver To BPartner Location','de.metas.shipper.gateway.go',100,TO_TIMESTAMP('2018-01-05 13:25:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-05 13:25:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-05T13:25:01.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543742 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-05T13:26:02.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,AD_Reference_Value_ID,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (30,'N','N','N','N',0,'Y',100,543741,'Y','N','N','N','N','N','N','Y','N','N','N',540890,'N','N',138,'GO_DeliverToBPartner_ID','N',558496,'N','Y','N','N','N','N',0,100,'Deliver To BPartner','de.metas.shipper.gateway.go','N',10,0,0,TO_TIMESTAMP('2018-01-05 13:26:02','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-01-05 13:26:02','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-05T13:26:02.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558496 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-05T13:26:09.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('GO_DeliveryOrder','ALTER TABLE public.GO_DeliveryOrder ADD COLUMN GO_DeliverToBPartner_ID NUMERIC(10) NOT NULL')
;

-- 2018-01-05T13:26:09.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE GO_DeliveryOrder ADD CONSTRAINT GODeliverToBPartner_GODelivery FOREIGN KEY (GO_DeliverToBPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2018-01-05T13:26:57.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,AD_Reference_Value_ID,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (30,'N','N','N','N',0,'Y',100,543742,'Y','N','N','N','N','N','N','Y','N','N','N',540890,'N','N',159,'GO_DeliverToBPLocation_ID','N',558497,'N','Y','N','N','N','N',0,100,'Deliver To BPartner Location','de.metas.shipper.gateway.go','N',10,0,0,TO_TIMESTAMP('2018-01-05 13:26:57','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-01-05 13:26:57','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-05T13:26:57.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558497 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-05T13:27:00.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('GO_DeliveryOrder','ALTER TABLE public.GO_DeliveryOrder ADD COLUMN GO_DeliverToBPLocation_ID NUMERIC(10) NOT NULL')
;

-- 2018-01-05T13:27:00.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE GO_DeliveryOrder ADD CONSTRAINT GODeliverToBPLocation_GODelive FOREIGN KEY (GO_DeliverToBPLocation_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- 2018-01-05T14:22:56.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (20,'N','N','N','N','N',0,'Y',100,1047,'Y','N','N','N','N','N','N','Y','N','N','N',540890,'Y','N','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Processed','N',558498,'N','Y','N','N','N','N','Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',0,100,'Verarbeitet','de.metas.shipper.gateway.go','N',1,0,0,TO_TIMESTAMP('2018-01-05 14:22:56','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-01-05 14:22:56','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-05T14:22:56.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558498 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-05T14:23:02.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('GO_DeliveryOrder','ALTER TABLE public.GO_DeliveryOrder ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

