-- 2018-02-05T15:04:36.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558994,543477,0,30,540226,540914,'N','C_OrderLinePO_ID',TO_TIMESTAMP('2018-02-05 15:04:36','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.vertical.pharma.vendor.gateway.mvs3',10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Bestellposition',0,0,TO_TIMESTAMP('2018-02-05 15:04:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-05T15:04:36.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558994 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-05T15:04:43.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MSV3_BestellungAnteil','ALTER TABLE public.MSV3_BestellungAnteil ADD COLUMN C_OrderLinePO_ID NUMERIC(10)')
;

-- 2018-02-05T15:04:43.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MSV3_BestellungAnteil ADD CONSTRAINT COrderLinePO_MSV3BestellungAnteil FOREIGN KEY (C_OrderLinePO_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED
;

-- 2018-02-05T15:05:32.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558995,2180,0,30,540250,540912,'N','C_OrderPO_ID',TO_TIMESTAMP('2018-02-05 15:05:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Bestellung','de.metas.vertical.pharma.vendor.gateway.mvs3',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Bestellung',0,0,TO_TIMESTAMP('2018-02-05 15:05:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-05T15:05:32.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558995 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-05T15:05:34.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MSV3_BestellungAntwortAuftrag','ALTER TABLE public.MSV3_BestellungAntwortAuftrag ADD COLUMN C_OrderPO_ID NUMERIC(10)')
;

-- 2018-02-05T15:05:34.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MSV3_BestellungAntwortAuftrag ADD CONSTRAINT COrderPO_MSV3BestellungAntwortAuftrag FOREIGN KEY (C_OrderPO_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- 2018-02-05T15:09:19.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='Y',Updated=TO_TIMESTAMP('2018-02-05 15:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558995
;

-- 2018-02-05T15:09:31.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='Y',Updated=TO_TIMESTAMP('2018-02-05 15:09:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558994
;
