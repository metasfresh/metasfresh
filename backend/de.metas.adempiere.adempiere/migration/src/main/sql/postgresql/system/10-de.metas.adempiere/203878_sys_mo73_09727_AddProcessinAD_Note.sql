-- 11.02.2016 12:01:36 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,553169,117,0,19,389,'N','AD_Process_ID',TO_TIMESTAMP('2016-02-11 12:01:34','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','D',10,'das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','Prozess',0,TO_TIMESTAMP('2016-02-11 12:01:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 11.02.2016 12:01:36 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=553169 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 11.02.2016 12:01:39 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Note ADD COLUMN AD_Process_ID NUMERIC(10) DEFAULT NULL 
;

-- 11.02.2016 12:02:25 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,553169,556618,0,325,0,TO_TIMESTAMP('2016-02-11 12:02:25','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht',0,'D','das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.',0,'Y','Y','Y','N','N','N','N','N','Prozess',170,0,TO_TIMESTAMP('2016-02-11 12:02:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.02.2016 12:02:25 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556618 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

