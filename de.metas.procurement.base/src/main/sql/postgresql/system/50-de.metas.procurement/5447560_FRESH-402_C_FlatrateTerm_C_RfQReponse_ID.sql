-- 27.06.2016 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554466,2446,0,30,540320,'N','C_RfQResponse_ID',TO_TIMESTAMP('2016-06-27 15:58:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Request for Quotation Response from a potential Vendor','de.metas.flatrate',10,'Request for Quotation Response from a potential Vendor','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ausschreibungs-Antwort',0,TO_TIMESTAMP('2016-06-27 15:58:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 27.06.2016 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554466 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 27.06.2016 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-27 15:58:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554466
;

-- 27.06.2016 16:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.procurement',Updated=TO_TIMESTAMP('2016-06-27 16:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554466
;

-- 27.06.2016 16:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=2381, ColumnName='C_RfQResponseLine_ID', Description='Request for Quotation Response Line', Help='Request for Quotation Response Line from a potential Vendor', Name='RfQ Response Line',Updated=TO_TIMESTAMP('2016-06-27 16:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554466
;

-- 27.06.2016 16:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=554466
;

-- 27.06.2016 16:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='RfQ Response Line', Description='Request for Quotation Response Line', Help='Request for Quotation Response Line from a potential Vendor' WHERE AD_Column_ID=554466 AND IsCentrallyMaintained='Y'
;

-- 27.06.2016 16:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2016-06-27 16:06:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554466
;

-- 27.06.2016 16:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Flatrate_Term ADD C_RfQResponseLine_ID NUMERIC(10) DEFAULT NULL 
;

