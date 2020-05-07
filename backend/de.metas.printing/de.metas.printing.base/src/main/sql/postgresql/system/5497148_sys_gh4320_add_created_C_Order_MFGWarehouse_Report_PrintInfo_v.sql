-- 2018-07-05T10:49:53.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (16,35,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-07-05 10:49:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N',TO_TIMESTAMP('2018-07-05 10:49:52','YYYY-MM-DD HH24:MI:SS'),100,541001,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Created',560658,'N','Datum, an dem dieser Eintrag erstellt wurde',0,'Erstellt',245,'de.metas.printing')
;

-- 2018-07-05T10:49:53.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560658 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-05T10:50:23.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560658,565094,0,541162,TO_TIMESTAMP('2018-07-05 10:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',35,'de.metas.printing','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2018-07-05 10:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-05T10:50:23.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565094 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-07-05T10:55:51.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (18,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-07-05 10:55:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N',TO_TIMESTAMP('2018-07-05 10:55:50','YYYY-MM-DD HH24:MI:SS'),100,541001,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,'CreatedBy',560659,'N','Nutzer, der diesen Eintrag erstellt hat',0,'Erstellt durch',246,'de.metas.printing')
;

-- 2018-07-05T10:55:51.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560659 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-05T10:55:51.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (16,35,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-07-05 10:55:51','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N',TO_TIMESTAMP('2018-07-05 10:55:51','YYYY-MM-DD HH24:MI:SS'),100,541001,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Updated',560660,'N','Datum, an dem dieser Eintrag aktualisiert wurde',0,'Aktualisiert',607,'de.metas.printing')
;

-- 2018-07-05T10:55:51.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560660 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-05T10:55:51.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (18,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-07-05 10:55:51','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N',TO_TIMESTAMP('2018-07-05 10:55:51','YYYY-MM-DD HH24:MI:SS'),100,541001,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,'UpdatedBy',560661,'N','Nutzer, der diesen Eintrag aktualisiert hat',0,'Aktualisiert durch',608,'de.metas.printing')
;

-- 2018-07-05T10:55:51.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560661 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-05T10:56:32.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565094
;

-- 2018-07-05T10:56:32.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=565094
;

-- 2018-07-05T10:56:57.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565091
;

-- 2018-07-05T10:56:57.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=565091
;

