-- 2018-10-10T14:36:36.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,Name,ColumnName) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-10 14:36:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-10 14:36:36','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540971,'N',563260,'N','N','N','N','N','N','N','N',0,0,2159,'de.metas.marketing.base','N','N','Sprache','AD_Language_ID')
;

-- 2018-10-10T14:36:36.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563260 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-10T14:40:03.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, Help='Definiert die Sprache für Anzeige und Aufbereitung', AD_Reference_Value_ID=327, Description='Sprache für diesen Eintrag', AD_Element_ID=109, Name='Sprache', ColumnName='AD_Language',Updated=TO_TIMESTAMP('2018-10-10 14:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563260
;

-- 2018-10-10T14:40:03.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sprache', Description='Sprache für diesen Eintrag', Help='Definiert die Sprache für Anzeige und Aufbereitung' WHERE AD_Column_ID=563260
;

-- 2018-10-10T14:40:07.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MKTG_ContactPerson','ALTER TABLE public.MKTG_ContactPerson ADD COLUMN AD_Language VARCHAR(10)')
;

-- 2018-10-10T14:40:07.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MKTG_ContactPerson ADD CONSTRAINT ADLangu_MKTGContactPerson FOREIGN KEY (AD_Language) REFERENCES public.AD_Language DEFERRABLE INITIALLY DEFERRED
;

-- 2018-10-10T14:40:45.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Description,AD_Org_ID,Name,EntityType) VALUES (541097,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-10 14:40:45','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-10 14:40:45','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die Sprache für Anzeige und Aufbereitung',569311,'N',563260,'Sprache für diesen Eintrag',0,'Sprache','de.metas.marketing.base')
;

-- 2018-10-10T14:40:45.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-10T14:40:59.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-10-10 14:40:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569311
;

