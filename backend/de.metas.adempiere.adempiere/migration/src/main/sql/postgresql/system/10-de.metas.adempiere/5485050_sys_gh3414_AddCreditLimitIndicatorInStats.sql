-- 2018-02-09T16:52:15.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550563
;

-- 2018-02-09T16:52:25.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=561987
;

-- 2018-02-09T16:52:25.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=561987
;


/* DDL */ SELECT public.db_alter_table('C_BPartner_CreditLimit','ALTER TABLE public.C_BPartner_CreditLimit DROP COLUMN DateTo')
;

-- 2018-02-09T17:05:41.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558989
;

-- 2018-02-09T17:05:41.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=558989
;



-------------------

-- 2018-02-09T17:47:51.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559022,543845,0,10,540763,'N','CreditLimitIndicator',TO_TIMESTAMP('2018-02-09 17:47:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Percent of Credit used from the limit','D',10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Credit limit indicator %',0,0,TO_TIMESTAMP('2018-02-09 17:47:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-09T17:47:51.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559022 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-09T17:47:59.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Stats','ALTER TABLE public.C_BPartner_Stats ADD COLUMN CreditLimitIndicator VARCHAR(10)')
;

-- 2018-02-09T17:59:06.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select CreditLimitIndicator from C_BPartner_Stats s where s.C_BPartner_ID = C_BPartner.C_BPartner_ID)',Updated=TO_TIMESTAMP('2018-02-09 17:59:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;


-- 2018-02-09T17:47:59.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner DROP COLUMN CreditLimitIndicator')
;

