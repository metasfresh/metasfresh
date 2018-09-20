-- 2018-09-20T08:54:51.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (29,'N','N','N','N',0,'Y',100,528,'N','N','N','Y','N',540823,'Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','QtyDelivered',561013,'N','Gelieferte Menge',0,100,'Gelieferte Menge','de.metas.inoutcandidate',131089,0,TO_TIMESTAMP('2018-09-20 08:54:50','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-09-20 08:54:50','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-09-20T08:54:51.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=561013 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

