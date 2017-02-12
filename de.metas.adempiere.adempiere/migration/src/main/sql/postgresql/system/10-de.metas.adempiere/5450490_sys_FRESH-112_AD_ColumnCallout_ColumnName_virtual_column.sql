-- Sep 11, 2016 12:11 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555047,228,0,10,53262,'N','ColumnName','(select c.ColumnName from AD_Column c where c.AD_Column_ID=AD_ColumnCallout.AD_Column_ID)',TO_TIMESTAMP('2016-09-11 00:11:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Name der Spalte in der Datenbank','D',40,'"Spaltenname" bezeichnet den Namen einer Spalte einer Tabelle wie in der Datenbank definiert.','Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Spaltenname',0,TO_TIMESTAMP('2016-09-11 00:11:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Sep 11, 2016 12:11 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555047 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

