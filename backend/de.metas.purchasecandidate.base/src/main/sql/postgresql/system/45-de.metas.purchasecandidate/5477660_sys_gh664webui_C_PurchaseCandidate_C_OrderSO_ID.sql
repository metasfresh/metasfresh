-- 2017-11-20T16:45:59.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,ColumnName,AD_Element_ID,Description,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (0,'Y',TO_TIMESTAMP('2017-11-20 16:45:58','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','C_OrderSO_ID',543479,'Auftrag',0,'Auftrag','D',TO_TIMESTAMP('2017-11-20 16:45:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-20T16:45:59.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543479 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-11-20T16:46:19.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-20 16:46:19','YYYY-MM-DD HH24:MI:SS'),Name='Sales Order',PrintName='Sales Order',Description='' WHERE AD_Element_ID=543479 AND AD_Language='en_US'
;

-- 2017-11-20T16:46:19.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543479,'en_US') 
;

-- 2017-11-20T16:46:46.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,AD_Reference_Value_ID,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-11-20 16:46:46','YYYY-MM-DD HH24:MI:SS'),100,543479,'Y','N','N','N','N','N','Y','N','N','N',540861,'N','N',290,'C_OrderSO_ID','N',557870,'N','N','N','N','N','N','Auftrag',0,0,TO_TIMESTAMP('2017-11-20 16:46:46','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','de.metas.purchasecandidate')
;

-- 2017-11-20T16:46:46.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557870 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-20T16:47:07.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN C_OrderSO_ID NUMERIC(10)')
;

-- 2017-11-20T16:47:07.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT COrderSO_CPurchaseCandidate FOREIGN KEY (C_OrderSO_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

