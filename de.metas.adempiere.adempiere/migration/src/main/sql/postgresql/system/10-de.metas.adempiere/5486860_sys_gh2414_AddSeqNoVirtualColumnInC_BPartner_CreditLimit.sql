-- 2018-02-26T13:59:09.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559467,566,0,11,540929,'N','SeqNo','(SELECT SeqNo from C_CreditLimit_Type t where t.C_CreditLimit_Type_ID = C_BPartner_CreditLimit.C_CreditLimit_Type_ID)',TO_TIMESTAMP('2018-02-26 13:59:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',14,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Reihenfolge',0,0,TO_TIMESTAMP('2018-02-26 13:59:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-26T13:59:09.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559467 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-26T13:59:39.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2018-02-26 13:59:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559123
;

-- 2018-02-26T13:59:42.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_creditlimit_type','SeqNo','NUMERIC(10)',null,'1')
;

-- 2018-02-26T13:59:42.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_CreditLimit_Type SET SeqNo=1 WHERE SeqNo IS NULL
;

-- 2018-02-26T14:15:25.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2018-02-26 14:15:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559123
;

-- 2018-02-26T14:15:47.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2018-02-26 14:15:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559123
;

-- 2018-02-26T14:21:06.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT SeqNo from C_CreditLimit_Type t where t.C_CreditLimit_Type_ID = C_BPartner_CreditLimit.C_CreditLimit_Type_ID) ',Updated=TO_TIMESTAMP('2018-02-26 14:21:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559467
;

-- 2018-02-26T14:21:10.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT SeqNo from C_CreditLimit_Type t where t.C_CreditLimit_Type_ID = C_BPartner_CreditLimit.C_CreditLimit_Type_ID)',Updated=TO_TIMESTAMP('2018-02-26 14:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559467
;

-- 2018-02-26T14:21:30.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2018-02-26 14:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559467
;

-- 2018-02-26T14:21:33.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=559467
;

-- 2018-02-26T14:21:33.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=559467
;

-- 2018-02-26T14:21:49.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559473,566,0,11,540929,'N','SeqNo','(SELECT SeqNo from C_CreditLimit_Type t where t.C_CreditLimit_Type_ID = C_BPartner_CreditLimit.C_CreditLimit_Type_ID)',TO_TIMESTAMP('2018-02-26 14:21:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',10,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Reihenfolge',0,0,TO_TIMESTAMP('2018-02-26 14:21:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-02-26T14:21:49.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559473 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-26T14:21:58.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-02-26 14:21:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559473
;

-- 2018-02-26T14:22:32.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2018-02-26 14:22:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559123
;

-- 2018-02-26T14:22:48.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_creditlimit_type','SeqNo','NUMERIC',null,'1')
;

-- 2018-02-26T14:22:48.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_CreditLimit_Type SET SeqNo=1 WHERE SeqNo IS NULL
;

-- 2018-02-26T14:22:55.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2018-02-26 14:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559123
;

-- 2018-02-26T14:22:56.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_creditlimit_type','SeqNo','NUMERIC(10)',null,'1')
;

-- 2018-02-26T14:22:56.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_CreditLimit_Type SET SeqNo=1 WHERE SeqNo IS NULL
;


-- 2018-02-26T14:21:33.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=559473
;

-- 2018-02-26T14:21:33.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=559473
;


