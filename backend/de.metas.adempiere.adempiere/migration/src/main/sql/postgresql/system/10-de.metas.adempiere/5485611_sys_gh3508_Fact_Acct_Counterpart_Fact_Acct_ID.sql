-- 2018-02-16T15:58:23.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Counterpart Accounting Fact','D','Counterpart_Fact_Acct_ID',543871,0,'Counterpart Accounting Fact',100,TO_TIMESTAMP('2018-02-16 15:58:23','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 15:58:23','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T15:58:23.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543871 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-02-16T15:59:58.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,AD_Reference_Value_ID,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,Created,SelectionColumnSeqNo,Updated) VALUES (30,'N','N','N','N',0,'Y',100,543871,'N','N','N','N','N','N','N','Y','N','N','N',270,'N','N',540773,'Counterpart_Fact_Acct_ID','N',559124,'N','N','N','N','N','Y',0,100,'Counterpart Accounting Fact','D','N',10,1,TO_TIMESTAMP('2018-02-16 15:59:58','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-16 15:59:58','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T15:59:58.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559124 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T16:00:09.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN Counterpart_Fact_Acct_ID NUMERIC(10)')
;

-- 2018-02-16T16:00:10.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE Fact_Acct ADD CONSTRAINT CounterpartFactAcct_FactAcct FOREIGN KEY (Counterpart_Fact_Acct_ID) REFERENCES public.Fact_Acct DEFERRABLE INITIALLY DEFERRED
;

