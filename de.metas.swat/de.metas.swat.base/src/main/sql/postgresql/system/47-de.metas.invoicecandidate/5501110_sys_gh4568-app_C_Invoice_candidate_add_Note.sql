-- 2018-09-11T08:25:53.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560992,2112,0,14,540270,'Memo',TO_TIMESTAMP('2018-09-11 08:25:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Memo Text','de.metas.invoicecandidate',999999999,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Memo',0,0,TO_TIMESTAMP('2018-09-11 08:25:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-09-11T08:25:53.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560992 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-11T08:27:04.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560992,568961,0,540279,0,TO_TIMESTAMP('2018-09-11 08:27:04','YYYY-MM-DD HH24:MI:SS'),100,'Memo Text',0,'de.metas.invoicecandidate',0,'Y','N','N','N','N','N','N','N','Memo',1070,420,0,1,1,TO_TIMESTAMP('2018-09-11 08:27:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-11T08:27:04.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-11T08:32:50.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,568961,0,540279,540056,553795,'F',TO_TIMESTAMP('2018-09-11 08:32:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','Y',2,'Notiz',10,0,0,TO_TIMESTAMP('2018-09-11 08:32:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-11T08:33:28.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=1115, ColumnName='Note', Description='Optional weitere Information für ein Dokument', Help='Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben', Name='Notiz',Updated=TO_TIMESTAMP('2018-09-11 08:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560992
;

-- 2018-09-11T08:33:28.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Notiz', Description='Optional weitere Information für ein Dokument', Help='Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben' WHERE AD_Column_ID=560992
;

-- 2018-09-11T08:33:31.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN Note TEXT')
;

-- 2018-09-11T08:34:01.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Optional weitere Information',Updated=TO_TIMESTAMP('2018-09-11 08:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1115
;

-- 2018-09-11T08:34:01.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Note', Name='Notiz', Description='Optional weitere Information', Help='Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben' WHERE AD_Element_ID=1115
;

-- 2018-09-11T08:34:01.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Note', Name='Notiz', Description='Optional weitere Information', Help='Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben', AD_Element_ID=1115 WHERE UPPER(ColumnName)='NOTE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-11T08:34:01.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Note', Name='Notiz', Description='Optional weitere Information', Help='Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben' WHERE AD_Element_ID=1115 AND IsCentrallyMaintained='Y'
;

-- 2018-09-11T08:34:01.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Notiz', Description='Optional weitere Information', Help='Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1115) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1115)
;

-- 2018-09-11T08:34:14.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1115 AND AD_Language='fr_CH'
;

-- 2018-09-11T08:34:15.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1115 AND AD_Language='it_CH'
;

-- 2018-09-11T08:34:17.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1115 AND AD_Language='en_GB'
;

-- 2018-09-11T08:34:26.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-11 08:34:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Optional weitere Information' WHERE AD_Element_ID=1115 AND AD_Language='de_CH'
;

-- 2018-09-11T08:34:26.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1115,'de_CH') 
;

