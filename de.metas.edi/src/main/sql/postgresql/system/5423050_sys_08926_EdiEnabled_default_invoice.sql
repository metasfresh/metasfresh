-- 27.07.2015 14:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsEdiRecipient@ = N',Updated=TO_TIMESTAMP('2015-07-27 14:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552599
;

-- 27.07.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT IsEdiRecipient from C_BPartner where C_BPartner_ID = C_Order.Bill_BPartner_ID)',Updated=TO_TIMESTAMP('2015-07-27 14:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552603
;

-- 27.07.2015 14:58


-- 27.07.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552604,542000,0,20,318,'N','IsEdiRecipient',TO_TIMESTAMP('2015-07-27 15:13:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Erhält EDI-Belege','de.metas.esb.edi',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Erhält EDI-Belege',0,TO_TIMESTAMP('2015-07-27 15:13:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 27.07.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552604 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 27.07.2015 15:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT IsEdiRecipient from C_BPartner where C_BPartner_ID = C_Invoice.C_BPartner_ID)', IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-07-27 15:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552604
;

-- 27.07.2015 15:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsEdiRecipient@ = N',Updated=TO_TIMESTAMP('2015-07-27 15:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548484
;

-- 27.07.2015 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2015-07-27 15:18:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552604
;

-- 27.07.2015 15:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2015-07-27 15:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551567
;

