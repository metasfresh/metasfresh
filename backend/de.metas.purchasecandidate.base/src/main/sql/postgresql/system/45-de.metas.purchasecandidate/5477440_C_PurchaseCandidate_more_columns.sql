-- 2017-11-17T17:04:44.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-11-17 17:04:44','YYYY-MM-DD HH24:MI:SS'),100,459,'N','N','N','N','N','N','Y','N','N','N',540861,'N','N','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','M_Warehouse_ID','N',557866,'N','Y','N','N','N','N','Lager oder Ort für Dienstleistung',0,0,TO_TIMESTAMP('2017-11-17 17:04:44','YYYY-MM-DD HH24:MI:SS'),100,'Lager','de.metas.purchasecandidate')
;

-- 2017-11-17T17:04:44.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557866 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-17T17:05:21.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN M_Warehouse_ID NUMERIC(10) NOT NULL')
;

-- 2017-11-17T17:05:21.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT MWarehouse_CPurchaseCandidate FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
;

-- 2017-11-17T17:08:11.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (PO_Name,PO_PrintName,AD_Client_ID,IsActive,Created,CreatedBy,PrintName,PO_Description,PO_Help,Help,ColumnName,AD_Element_ID,Description,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES ('','',0,'Y',TO_TIMESTAMP('2017-11-17 17:08:11','YYYY-MM-DD HH24:MI:SS'),100,'Bestellposition','','','','C_OrderLinePO_ID',543477,'',0,'Bestellposition','D',TO_TIMESTAMP('2017-11-17 17:08:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-17T17:08:11.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543477 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-11-17T17:08:34.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-17 17:08:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Purchase Order Line',PrintName='Purchase Order Line' WHERE AD_Element_ID=543477 AND AD_Language='en_US'
;

-- 2017-11-17T17:08:37.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543477,'en_US') 
;

-- 2017-11-17T17:09:21.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,AD_Reference_Value_ID,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-11-17 17:09:21','YYYY-MM-DD HH24:MI:SS'),100,543477,'Y','N','N','N','N','N','Y','N','N','N',540861,'N','N','',540226,'C_OrderLinePO_ID','N',557867,'N','N','N','N','N','N','',0,0,TO_TIMESTAMP('2017-11-17 17:09:21','YYYY-MM-DD HH24:MI:SS'),100,'Bestellposition','de.metas.purchasecandidate')
;

-- 2017-11-17T17:09:21.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557867 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-17T17:09:27.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN C_OrderLinePO_ID NUMERIC(10)')
;

-- 2017-11-17T17:09:27.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT COrderLinePO_CPurchaseCandidat FOREIGN KEY (C_OrderLinePO_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED
;

