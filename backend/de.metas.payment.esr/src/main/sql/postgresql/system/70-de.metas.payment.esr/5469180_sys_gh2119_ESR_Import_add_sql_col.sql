-- 2017-08-08T18:19:51.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Anzahl Transaktionen (kontr.)', PrintName='Anzahl Transaktionen (kontr.)',Updated=TO_TIMESTAMP('2017-08-08 18:19:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541899
;

-- 2017-08-08T18:19:51.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ESR_Control_Trx_Qty', Name='Anzahl Transaktionen (kontr.)', Description=NULL, Help=NULL WHERE AD_Element_ID=541899
;

-- 2017-08-08T18:19:51.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Control_Trx_Qty', Name='Anzahl Transaktionen (kontr.)', Description=NULL, Help=NULL, AD_Element_ID=541899 WHERE UPPER(ColumnName)='ESR_CONTROL_TRX_QTY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-08T18:19:51.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Control_Trx_Qty', Name='Anzahl Transaktionen (kontr.)', Description=NULL, Help=NULL WHERE AD_Element_ID=541899 AND IsCentrallyMaintained='Y'
;

-- 2017-08-08T18:19:51.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Transaktionen (kontr.)', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541899) AND IsCentrallyMaintained='Y'
;

-- 2017-08-08T18:19:51.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anzahl Transaktionen (kontr.)', Name='Anzahl Transaktionen (kontr.)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541899)
;

-- 2017-08-08T18:20:09.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543395,0,'ESR_Trx_Qty',TO_TIMESTAMP('2017-08-08 18:20:09','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Anzahl Transaktionen','Anzahl Transaktionen',TO_TIMESTAMP('2017-08-08 18:20:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-08T18:20:09.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543395 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-08-08T18:20:37.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)',Updated=TO_TIMESTAMP('2017-08-08 18:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541899
;

-- 2017-08-08T18:20:37.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ESR_Control_Trx_Qty', Name='Anzahl Transaktionen (kontr.)', Description='Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)', Help=NULL WHERE AD_Element_ID=541899
;

-- 2017-08-08T18:20:37.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Control_Trx_Qty', Name='Anzahl Transaktionen (kontr.)', Description='Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)', Help=NULL, AD_Element_ID=541899 WHERE UPPER(ColumnName)='ESR_CONTROL_TRX_QTY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-08T18:20:37.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Control_Trx_Qty', Name='Anzahl Transaktionen (kontr.)', Description='Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)', Help=NULL WHERE AD_Element_ID=541899 AND IsCentrallyMaintained='Y'
;

-- 2017-08-08T18:20:37.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Transaktionen (kontr.)', Description='Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541899) AND IsCentrallyMaintained='Y'
;

-- 2017-08-08T18:20:55.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Anzahl der importierten Zeilen',Updated=TO_TIMESTAMP('2017-08-08 18:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543395
;

-- 2017-08-08T18:20:55.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ESR_Trx_Qty', Name='Anzahl Transaktionen', Description='Anzahl der importierten Zeilen', Help=NULL WHERE AD_Element_ID=543395
;

-- 2017-08-08T18:20:55.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Trx_Qty', Name='Anzahl Transaktionen', Description='Anzahl der importierten Zeilen', Help=NULL, AD_Element_ID=543395 WHERE UPPER(ColumnName)='ESR_TRX_QTY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-08T18:20:55.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Trx_Qty', Name='Anzahl Transaktionen', Description='Anzahl der importierten Zeilen', Help=NULL WHERE AD_Element_ID=543395 AND IsCentrallyMaintained='Y'
;

-- 2017-08-08T18:20:55.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Transaktionen', Description='Anzahl der importierten Zeilen', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543395) AND IsCentrallyMaintained='Y'
;

-- 2017-08-08T18:21:04.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)',Updated=TO_TIMESTAMP('2017-08-08 18:21:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541899
;

-- 2017-08-08T18:21:04.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ESR_Control_Trx_Qty', Name='Anzahl Transaktionen (kontr.)', Description='Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)', Help=NULL WHERE AD_Element_ID=541899
;

-- 2017-08-08T18:21:04.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Control_Trx_Qty', Name='Anzahl Transaktionen (kontr.)', Description='Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)', Help=NULL, AD_Element_ID=541899 WHERE UPPER(ColumnName)='ESR_CONTROL_TRX_QTY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-08T18:21:04.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESR_Control_Trx_Qty', Name='Anzahl Transaktionen (kontr.)', Description='Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)', Help=NULL WHERE AD_Element_ID=541899 AND IsCentrallyMaintained='Y'
;

-- 2017-08-08T18:21:04.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Transaktionen (kontr.)', Description='Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541899) AND IsCentrallyMaintained='Y'
;

-- 2017-08-08T18:23:34.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557044,543395,0,29,540409,'N','ESR_Trx_Qty','(select count(8) from ESR_ImportLine l where l.ESR_Import_ID = ESR_Import.ESR_Import_ID)',TO_TIMESTAMP('2017-08-08 18:23:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Anzahl der importierten Zeilen','de.metas.payment.esr',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Anzahl Transaktionen',0,TO_TIMESTAMP('2017-08-08 18:23:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-08-08T18:23:34.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557044 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-08-08T18:24:42.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-08-08 18:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551083
;

-- 2017-08-08T18:25:14.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557044,559451,0,540442,131,TO_TIMESTAMP('2017-08-08 18:25:14','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der importierten Zeilen',14,'de.metas.payment.esr',0,'Y','Y','Y','Y','N','N','N','Y','N','Anzahl Transaktionen',90,40,1,1,TO_TIMESTAMP('2017-08-08 18:25:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-08T18:25:14.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559451 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
