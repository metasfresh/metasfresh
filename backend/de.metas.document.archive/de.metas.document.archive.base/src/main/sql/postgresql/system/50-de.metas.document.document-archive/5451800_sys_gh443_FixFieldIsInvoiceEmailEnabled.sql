-- 14.10.2016 11:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=319, DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2016-10-14 11:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555107
;

-- 14.10.2016 11:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=175, SeqNoGrid=195,Updated=TO_TIMESTAMP('2016-10-14 11:56:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557307
;

-- 14.10.2016 12:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColumnDisplayLength=110, DisplayLength=11,Updated=TO_TIMESTAMP('2016-10-14 12:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557307
;

-- 14.10.2016 12:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_FieldGroup_ID=107,Updated=TO_TIMESTAMP('2016-10-14 12:03:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557307
;

-- 14.10.2016 12:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=162, SeqNoGrid=162,Updated=TO_TIMESTAMP('2016-10-14 12:17:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557307
;




-- 17.10.2016 11:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=557307
;

-- 17.10.2016 11:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=557307
;

-- 17.10.2016 11:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555108,557309,0,220,206,TO_TIMESTAMP('2016-10-17 11:51:38','YYYY-MM-DD HH24:MI:SS'),100,11,'de.metas.document.archive',0,'Y','Y','Y','Y','N','N','N','N','Y','Invoice Email Enabled',165,165,1,1,TO_TIMESTAMP('2016-10-17 11:51:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.10.2016 11:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557309 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 17.10.2016 11:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=161, SeqNoGrid=161,Updated=TO_TIMESTAMP('2016-10-17 11:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557309
;

-- 17.10.2016 11:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=20, AD_Reference_Value_ID=NULL, DefaultValue='Y',Updated=TO_TIMESTAMP('2016-10-17 11:56:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555107
;


COMMIT;



-- 14.10.2016 11:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doc_outbound_log','IsInvoiceEmailEnabled','CHAR(1)',null,'NULL')
;


-- 14.10.2016 11:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doc_outbound_log','IsInvoiceEmailEnabled',null,'NULL',null)
;


-- 17.10.2016 11:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doc_outbound_log','IsInvoiceEmailEnabled','CHAR(1)',null,'Y')
;

ALTER TABLE C_BPartner
DROP CONSTRAINT c_bpartner_isinvoiceemailenabled_check;

