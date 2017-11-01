-- 2017-10-24T16:00:10.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Compensation percentage', ColumnName='CompensationGroupPercentage', Name='Compensation percentage',Updated=TO_TIMESTAMP('2017-10-24 16:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543460
;

-- 2017-10-24T16:00:11.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CompensationGroupPercentage', Name='Compensation percentage', Description=NULL, Help=NULL WHERE AD_Element_ID=543460
;

-- 2017-10-24T16:00:11.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CompensationGroupPercentage', Name='Compensation percentage', Description=NULL, Help=NULL, AD_Element_ID=543460 WHERE UPPER(ColumnName)='COMPENSATIONGROUPPERCENTAGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-10-24T16:00:11.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CompensationGroupPercentage', Name='Compensation percentage', Description=NULL, Help=NULL WHERE AD_Element_ID=543460 AND IsCentrallyMaintained='Y'
;

-- 2017-10-24T16:00:11.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Compensation percentage', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543460) AND IsCentrallyMaintained='Y'
;

-- 2017-10-24T16:00:11.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Compensation percentage', Name='Compensation percentage' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543460)
;

-- 2017-10-24T16:00:21.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-24 16:00:21','YYYY-MM-DD HH24:MI:SS'),Name='Compensation percentage',PrintName='Compensation percentage' WHERE AD_Element_ID=543460 AND AD_Language='de_CH'
;

-- 2017-10-24T16:00:21.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543460,'de_CH') 
;

-- 2017-10-24T16:00:26.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-24 16:00:26','YYYY-MM-DD HH24:MI:SS'),Name='Compensation percentage',PrintName='Compensation percentage' WHERE AD_Element_ID=543460 AND AD_Language='nl_NL'
;

-- 2017-10-24T16:00:26.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543460,'nl_NL') 
;

-- 2017-10-24T16:00:34.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-24 16:00:34','YYYY-MM-DD HH24:MI:SS'),Name='Compensation percentage',PrintName='Compensation percentage' WHERE AD_Element_ID=543460 AND AD_Language='en_US'
;

-- 2017-10-24T16:00:34.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543460,'en_US') 
;

alter table C_OrderLIne rename CompensationGroupDiscount to COmpensationGroupPercentage;

-- 2017-10-24T16:02:10.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='GroupCompensationPercentage',Updated=TO_TIMESTAMP('2017-10-24 16:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543460
;

-- 2017-10-24T16:02:10.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='GroupCompensationPercentage', Name='Compensation percentage', Description=NULL, Help=NULL WHERE AD_Element_ID=543460
;

-- 2017-10-24T16:02:10.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GroupCompensationPercentage', Name='Compensation percentage', Description=NULL, Help=NULL, AD_Element_ID=543460 WHERE UPPER(ColumnName)='GROUPCOMPENSATIONPERCENTAGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-10-24T16:02:10.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GroupCompensationPercentage', Name='Compensation percentage', Description=NULL, Help=NULL WHERE AD_Element_ID=543460 AND IsCentrallyMaintained='Y'
;

alter table C_OrderLIne rename CompensationGroupPercentage to GroupCompensationPercentage;

-- 2017-10-24T16:03:26.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,ColumnName,AD_Element_ID,Description,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (0,'Y',TO_TIMESTAMP('2017-10-24 16:03:26','YYYY-MM-DD HH24:MI:SS'),100,'Compensation base amount','GroupCompensationBaseAmt',543464,'Base amount for calculating percentage group compensation',0,'Compensation base amount','D',TO_TIMESTAMP('2017-10-24 16:03:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-24T16:03:26.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543464 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-10-24T16:03:58.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Updated,UpdatedBy,Name,EntityType) VALUES (12,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-10-24 16:03:57','YYYY-MM-DD HH24:MI:SS'),100,543464,'Y','N','N','N','N','N','Y','N','N','N',260,'N','N','GroupCompensationBaseAmt','N',557787,'N','N','N','N','N','N','Base amount for calculating percentage group compensation',0,0,TO_TIMESTAMP('2017-10-24 16:03:57','YYYY-MM-DD HH24:MI:SS'),100,'Compensation base amount','D')
;

-- 2017-10-24T16:03:58.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557787 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-10-24T16:04:03.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN GroupCompensationBaseAmt NUMERIC')
;

-- 2017-10-24T16:05:06.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IsCentrallyMaintained,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES (187,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2017-10-24 16:05:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',560415,557787,'Base amount for calculating percentage group compensation',0,'Compensation base amount','D',TO_TIMESTAMP('2017-10-24 16:05:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-24T16:05:06.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560415 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-24T16:05:22.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-10-24 16:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560415
;

