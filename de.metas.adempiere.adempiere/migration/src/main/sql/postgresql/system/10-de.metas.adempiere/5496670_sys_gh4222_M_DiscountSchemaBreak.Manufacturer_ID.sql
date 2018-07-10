-- 2018-06-27T13:54:52.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Val_Rule_ID,AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (540401,30,'N','N','N','N',0,'Y',100,544128,'Y','N','N','N','N','N','N','N','Y','N','N','N',476,'N','N','','Manufacturer_ID',560512,'N','N','N','N','N','N','Hersteller des Produktes',0,100,'Hersteller','D','N',10,0,0,TO_TIMESTAMP('2018-06-27 13:54:51','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-06-27 13:54:51','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-06-27T13:54:52.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560512 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-06-27T13:55:09.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=138,Updated=TO_TIMESTAMP('2018-06-27 13:55:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560512
;

-- 2018-06-27T13:55:10.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_DiscountSchemaBreak','ALTER TABLE public.M_DiscountSchemaBreak ADD COLUMN Manufacturer_ID NUMERIC(10)')
;

-- 2018-06-27T13:55:10.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_DiscountSchemaBreak ADD CONSTRAINT Manufacturer_MDiscountSchemaBreak FOREIGN KEY (Manufacturer_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

