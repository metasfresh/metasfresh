-- 2017-09-01T11:03:23.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557067,1027,0,30,323,'N','M_Inventory_ID',TO_TIMESTAMP('2017-09-01 11:03:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Parameter für eine physische Inventur','D',10,'Bezeichnet die eindeutigen Parameter für eine physische Inventur','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Inventur',0,TO_TIMESTAMP('2017-09-01 11:03:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-01T11:03:23.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557067 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-01T11:04:01.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2017-09-01 11:04:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550496
;

-- 2017-09-01T11:32:00.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_movement','ALTER TABLE public.M_Movement ADD COLUMN M_Inventory_ID NUMERIC(10)')
;

-- 2017-09-01T11:32:01.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Movement ADD CONSTRAINT MInventory_MMovement FOREIGN KEY (M_Inventory_ID) REFERENCES public.M_Inventory DEFERRABLE INITIALLY DEFERRED
;

