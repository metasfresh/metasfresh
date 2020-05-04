
-- 2017-05-30T11:08:54.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543356,0,'ESRFullReferenceNumber',TO_TIMESTAMP('2017-05-30 11:08:54','YYYY-MM-DD HH24:MI:SS'),100,'Referenznummer inkl. bankinterner Teilnehmernummer','de.metas.payment.esr','Y','ESR Referenznummer (komplett)','ESR Referenznummer (komplett)',TO_TIMESTAMP('2017-05-30 11:08:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-30T11:08:54.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543356 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-05-30T11:11:58.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Referenznummer der jeweiligen Rechnung', Name='ESR Referenznummer (Rechnung)', PrintName='ESR Referenznummer (Rechnung)',Updated=TO_TIMESTAMP('2017-05-30 11:11:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541834
;

-- 2017-05-30T11:11:58.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541834
;

-- 2017-05-30T11:11:58.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ESRReferenceNumber', Name='ESR Referenznummer (Rechnung)', Description='Referenznummer der jeweiligen Rechnung', Help=NULL WHERE AD_Element_ID=541834
;

-- 2017-05-30T11:11:58.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESRReferenceNumber', Name='ESR Referenznummer (Rechnung)', Description='Referenznummer der jeweiligen Rechnung', Help=NULL, AD_Element_ID=541834 WHERE UPPER(ColumnName)='ESRREFERENCENUMBER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-05-30T11:11:58.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ESRReferenceNumber', Name='ESR Referenznummer (Rechnung)', Description='Referenznummer der jeweiligen Rechnung', Help=NULL WHERE AD_Element_ID=541834 AND IsCentrallyMaintained='Y'
;

-- 2017-05-30T11:11:58.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='ESR Referenznummer (Rechnung)', Description='Referenznummer der jeweiligen Rechnung', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541834) AND IsCentrallyMaintained='Y'
;

-- 2017-05-30T11:11:58.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='ESR Referenznummer (Rechnung)', Name='ESR Referenznummer (Rechnung)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541834)
;

-- 2017-05-30T11:12:22.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556867,543356,0,10,540410,'N','ESRFullReferenceNumber',TO_TIMESTAMP('2017-05-30 11:12:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Referenznummer inkl. bankinterner Teilnehmernummer','de.metas.payment.esr',30,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','ESR Referenznummer (komplett)',0,TO_TIMESTAMP('2017-05-30 11:12:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-05-30T11:12:22.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556867 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
