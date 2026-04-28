/*
-- 2018-02-10T16:36:30.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
SELECT public.db_alter_table('MSV3_Verfuegbarkeit_Transaction','ALTER TABLE public.MSV3_Verfuegbarkeit_Transaction ADD COLUMN C_PurchaseCandidate_ID NUMERIC(10)')
;

-- 2018-02-10T16:36:30.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MSV3_Verfuegbarkeit_Transaction ADD CONSTRAINT CPurchaseCandidate_MSV3VerfuegbarkeitTransaction FOREIGN KEY (C_PurchaseCandidate_ID) REFERENCES public.C_PurchaseCandidate DEFERRABLE INITIALLY DEFERRED
;
*/
-- 2018-02-11T20:56:26.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=561979
;

-- 2018-02-11T20:56:26.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=561979
;

-- 2018-02-11T20:56:36.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558940
;

-- 2018-02-11T20:56:36.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=558940
;

-- 2018-02-11T21:04:24.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-02-11 21:04:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-02-11 21:04:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540918,'N','N','C_PurchaseCandidate_ID','N',559023,'N','N','N','N','N','N','N','N',0,0,'Purchase candidate',543476,'de.metas.vertical.pharma.vendor.gateway.mvs3')
;

-- 2018-02-11T21:04:24.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559023 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-11T21:05:08.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-02-11 21:05:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559023
;

-- 2018-02-11T21:05:15.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-02-11 21:05:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559023
;

-- 2018-02-11T21:05:17.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MSV3_BestellungPosition','ALTER TABLE public.MSV3_BestellungPosition ADD COLUMN C_PurchaseCandidate_ID NUMERIC(10)')
;

-- 2018-02-11T21:05:17.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MSV3_BestellungPosition ADD CONSTRAINT CPurchaseCandidate_MSV3BestellungPosition FOREIGN KEY (C_PurchaseCandidate_ID) REFERENCES public.C_PurchaseCandidate DEFERRABLE INITIALLY DEFERRED
;

-- 2018-02-11T21:06:56.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-02-11 21:06:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-02-11 21:06:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540913,'N','N','C_PurchaseCandidate_ID','N',559024,'N','N','N','N','N','N','N','N',0,0,'Purchase candidate',543476,'de.metas.vertical.pharma.vendor.gateway.mvs3')
;

-- 2018-02-11T21:06:56.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559024 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-11T21:07:08.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-02-11 21:07:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559024
;

-- 2018-02-11T21:07:12.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MSV3_BestellungAntwortPosition','ALTER TABLE public.MSV3_BestellungAntwortPosition ADD COLUMN C_PurchaseCandidate_ID NUMERIC(10)')
;

-- 2018-02-11T21:07:12.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MSV3_BestellungAntwortPosition ADD CONSTRAINT CPurchaseCandidate_MSV3BestellungAntwortPosition FOREIGN KEY (C_PurchaseCandidate_ID) REFERENCES public.C_PurchaseCandidate DEFERRABLE INITIALLY DEFERRED
;

-- 2018-02-11T21:08:00.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='Y',Updated=TO_TIMESTAMP('2018-02-11 21:08:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559024
;

-- 2018-02-11T21:08:50.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559024,562682,0,541001,TO_TIMESTAMP('2018-02-11 21:08:50','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.pharma.vendor.gateway.mvs3','Y','Y','N','N','N','N','N','Purchase candidate',TO_TIMESTAMP('2018-02-11 21:08:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-11T21:08:50.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562682 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-11T21:08:50.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543476,NULL) 
;

-- 2018-02-11T21:09:20.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559023,562683,0,540998,TO_TIMESTAMP('2018-02-11 21:09:20','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.vertical.pharma.vendor.gateway.mvs3','Y','Y','N','N','N','N','N','Purchase candidate',TO_TIMESTAMP('2018-02-11 21:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-11T21:09:20.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562683 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-11T21:09:20.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543476,NULL) 
;

-- 2018-02-11T22:43:15.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='Y',Updated=TO_TIMESTAMP('2018-02-11 22:43:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559023
;

