-- 2018-05-04T16:12:37.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DerKurier_Shipper_Config','ALTER TABLE public.DerKurier_Shipper_Config ADD COLUMN AD_Sequence_ID NUMERIC(10) NOT NULL')
;

-- 2018-05-04T16:12:37.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DerKurier_Shipper_Config ADD CONSTRAINT ADSequence_DerKurierShipperConfig FOREIGN KEY (AD_Sequence_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;

-- 2018-05-04T16:38:25.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@ShipperGateway@=go',Updated=TO_TIMESTAMP('2018-05-04 16:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540982
;

-- 2018-05-04T16:38:45.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@ShipperGateway@=''derKurier''',Updated=TO_TIMESTAMP('2018-05-04 16:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541084
;

-- 2018-05-04T16:38:50.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@ShipperGateway@=''go''',Updated=TO_TIMESTAMP('2018-05-04 16:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540982
;

-- 2018-05-04T16:39:31.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2018-05-04 16:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563873
;

-- 2018-05-04T16:39:31.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2018-05-04 16:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563872
;

-- 2018-05-04T16:39:31.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2018-05-04 16:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563869
;

-- 2018-05-04T16:39:31.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2018-05-04 16:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563870
;

-- 2018-05-04T16:39:42.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-05-04 16:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563873
;

-- 2018-05-04T16:39:44.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-05-04 16:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563872
;

-- 2018-05-04T16:39:45.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-05-04 16:39:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563869
;

-- 2018-05-04T16:39:49.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-05-04 16:39:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563870
;

-- 2018-05-04T16:39:54.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-05-04 16:39:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563872
;

-- 2018-05-04T16:40:13.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-05-04 16:40:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563870
;

-- 2018-05-04T17:36:37.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,559907,1269,0,10,540965,'N','EMail_To',TO_TIMESTAMP('2018-05-04 17:36:37','YYYY-MM-DD HH24:MI:SS'),100,'N','EMail address to send requests to - e.g. edi@manufacturer.com ','de.metas.shipper.gateway.derkurier',100,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','EMail Empfänger',0,0,TO_TIMESTAMP('2018-05-04 17:36:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-05-04T17:36:37.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559907 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-04T17:36:39.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DerKurier_Shipper_Config','ALTER TABLE public.DerKurier_Shipper_Config ADD COLUMN EMail_To VARCHAR(100)')
;

-- 2018-05-04T17:36:58.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559907,563978,0,541084,TO_TIMESTAMP('2018-05-04 17:36:58','YYYY-MM-DD HH24:MI:SS'),100,'EMail address to send requests to - e.g. edi@manufacturer.com ',100,'de.metas.shipper.gateway.derkurier','Y','Y','N','N','N','N','N','EMail Empfänger',TO_TIMESTAMP('2018-05-04 17:36:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-04T17:36:58.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-05-04T17:37:09.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2018-05-04 17:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563870
;

-- 2018-05-04T17:37:25.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=45, SeqNoGrid=45,Updated=TO_TIMESTAMP('2018-05-04 17:37:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563978
;

-- 2018-05-04T17:39:56.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544032,0,TO_TIMESTAMP('2018-05-04 17:39:56','YYYY-MM-DD HH24:MI:SS'),100,'Zugangsdaten für den Mailsserver, über den "Der Kurier"-Leiferaufträge versendet werden','de.metas.shipper.gateway.derkurier','Y','Der Kurier Auftrags-Mailserver','Der Kurier Auftrags-Mailserver',TO_TIMESTAMP('2018-05-04 17:39:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-04T17:39:56.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544032 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-04T17:40:01.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=544032, Description='Zugangsdaten für den Mailsserver, über den "Der Kurier"-Leiferaufträge versendet werden', Help=NULL, Name='Der Kurier Auftrags-Mailserver',Updated=TO_TIMESTAMP('2018-05-04 17:40:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563869
;

-- 2018-05-04T17:40:01.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544032) 
;

-- 2018-05-04T17:41:01.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544033,0,TO_TIMESTAMP('2018-05-04 17:41:01','YYYY-MM-DD HH24:MI:SS'),100,'Mailaddresse, an die "Der Kurier" Lieferaufträge gesendet werden.','de.metas.shipper.gateway.derkurier','Y','Der Kurier Email-Empfängeraddresse','Der Kurier Email-Empfängeraddresse',TO_TIMESTAMP('2018-05-04 17:41:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-04T17:41:01.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544033 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-04T17:41:12.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=544033, Description='Mailaddresse, an die "Der Kurier" Lieferaufträge gesendet werden.', Help=NULL, Name='Der Kurier Email-Empfängeraddresse',Updated=TO_TIMESTAMP('2018-05-04 17:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563978
;

-- 2018-05-04T17:41:12.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544033) 
;

