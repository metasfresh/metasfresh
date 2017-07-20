-- 2017-07-19T14:08:13.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,EntityType,AD_Org_ID,Name) VALUES (29,'0',10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-07-19 14:08:10','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-07-19 14:08:10','YYYY-MM-DD HH24:MI:SS'),100,542270,'Y','N','N','N','N','N','Y','N','N','N',540831,'N','N','QtyPicked','N',557014,'N','Y','N','N','N','N','de.metas.picking',0,'Qty Picked')
;

-- 2017-07-19T14:08:14.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557014 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-19T14:08:33.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_picking_candidate','ALTER TABLE public.M_Picking_Candidate ADD COLUMN QtyPicked NUMERIC DEFAULT 0 NOT NULL')
;

-- 2017-07-19T14:09:13.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,EntityType,AD_Org_ID,Name,Description) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2017-07-19 14:09:13','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-07-19 14:09:13','YYYY-MM-DD HH24:MI:SS'),100,215,'Y','N','N','N','N','N','Y','N','N','N',540831,'N','N','Eine eindeutige (nicht monetäre) Maßeinheit','C_UOM_ID','N',557015,'N','N','N','N','N','N','de.metas.picking',0,'Maßeinheit','Maßeinheit')
;

-- 2017-07-19T14:09:13.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557015 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-19T14:09:19.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_picking_candidate','ALTER TABLE public.M_Picking_Candidate ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2017-07-19T14:09:19.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Picking_Candidate ADD CONSTRAINT CUOM_MPickingCandidate FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

