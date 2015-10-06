-- 24.07.2015 13:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='EXISTS SELECT (0) FROM C_BPartner where  C_BPartner_ID = @C_BPartner_ID@ AND isEdiRecipient = ''Y''',Updated=TO_TIMESTAMP('2015-07-24 13:39:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 13:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='NOT EXISTS SELECT (0) FROM C_BPartner where  C_BPartner_ID = @C_BPartner_ID@ AND isEdiRecipient = ''Y''',Updated=TO_TIMESTAMP('2015-07-24 13:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 13:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic=' @C_BPartner_ID@ NOT IN (SELECT C_BPartner_ID from C_BPartner where isEdiRecipient = ''Y'')',Updated=TO_TIMESTAMP('2015-07-24 13:46:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 13:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_BPartner_ID@ NOT IN (SELECT C_BPartner_ID from C_BPartner where isEdiRecipient = ''Y'')',Updated=TO_TIMESTAMP('2015-07-24 13:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 13:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@SQL =  NOT EXISTS (SELECT C_BPartner_ID from C_BPartner where isEdiRecipient = ''Y'' & C_BPartner_ID = @C_BPartner_ID@)',Updated=TO_TIMESTAMP('2015-07-24 13:55:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 13:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_BPartner@.IsEdiRecipient = ''N''',Updated=TO_TIMESTAMP('2015-07-24 13:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 13:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_BPartner_ID@ ! ( Select C_BPartner_ID from C_BPartner where isedirecipient = ''Y'')',Updated=TO_TIMESTAMP('2015-07-24 13:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 14:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='SELECT EXISTS ( Select C_BPartner_ID from C_BPartner where isedirecipient = ''Y'' and C_BPartner_ID = @C_BPartner_ID@)',Updated=TO_TIMESTAMP('2015-07-24 14:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 14:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='NOT EXISTS ( Select C_BPartner_ID from C_BPartner where isedirecipient = ''Y'' and C_BPartner_ID = @C_BPartner_ID@) ',Updated=TO_TIMESTAMP('2015-07-24 14:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 14:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_BPartner_ID@ NOT IN (Select C_BPartner_ID from C_BPartner where isedirecipient = ''Y'')',Updated=TO_TIMESTAMP('2015-07-24 14:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 14:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@SQL = SELECT IsEDIRecipient FROM C_BPartner where C_BPartner_ID = @C_BPartner_ID@',Updated=TO_TIMESTAMP('2015-07-24 14:58:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 15:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_BPartner_ID@ = ( SELECT C_BPartner_ID from C_BPartner where C_BPartner_ID = @C_BPartner_ID@ and isedirecipient = ''Y'')',Updated=TO_TIMESTAMP('2015-07-24 15:00:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 15:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552603,542000,0,20,259,'N','IsEdiRecipient','(SELECT IsEdiRecipient from C_BPartner where C_BPartner_ID = @C_BPartner_ID@)',TO_TIMESTAMP('2015-07-24 15:05:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Erhält EDI-Belege','de.metas.esb.edi',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Erhält EDI-Belege',NULL,0,TO_TIMESTAMP('2015-07-24 15:05:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 24.07.2015 15:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552603 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 24.07.2015 15:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsEdiRecipient@ = ''Y''',Updated=TO_TIMESTAMP('2015-07-24 15:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 15:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsEdiRecipient@ = Y',Updated=TO_TIMESTAMP('2015-07-24 15:07:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 15:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT IsEdiRecipient from C_BPartner where C_BPartner_ID = C_Order.C_BPartner_ID)',Updated=TO_TIMESTAMP('2015-07-24 15:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552603
;

-- 24.07.2015 15:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsEdiRecipient@ = N',Updated=TO_TIMESTAMP('2015-07-24 15:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 15:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue=NULL,Updated=TO_TIMESTAMP('2015-07-24 15:11:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552603
;

-- 24.07.2015 15:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2015-07-24 15:12:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552603
;

-- 24.07.2015 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue=NULL,Updated=TO_TIMESTAMP('2015-07-24 15:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 24.07.2015 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2015-07-24 15:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;


commit;
-- 24.07.2015 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','IsEdiEnabled','CHAR(1)',null,'NULL')
;

commit;
-- 24.07.2015 15:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','IsEdiEnabled',null,'NULL',null)
;

commit;
-- 24.07.2015 15:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2015-07-24 15:19:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556237
;

-- 24.07.2015 15:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2015-07-24 15:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

commit;
-- 24.07.2015 15:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','IsEdiEnabled','CHAR(1)',null,'N')
;

commit;

-- 24.07.2015 15:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Order SET IsEdiEnabled='N' WHERE IsEdiEnabled IS NULL
;

commit;
-- 24.07.2015 15:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','IsEdiEnabled',null,'NOT NULL',null)
;

commit;
-- 24.07.2015 15:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@IsEdiRecipient@',Updated=TO_TIMESTAMP('2015-07-24 15:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;



-- 27.07.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT IsEdiRecipient from C_BPartner where C_BPartner_ID = C_Order.Bill_BPartner_ID)',Updated=TO_TIMESTAMP('2015-07-27 14:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552603
;


