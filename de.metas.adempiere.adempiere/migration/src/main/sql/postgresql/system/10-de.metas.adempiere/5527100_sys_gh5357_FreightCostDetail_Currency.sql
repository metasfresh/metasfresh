



-- 2019-07-12T17:03:14.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568420,193,0,30,540005,'C_Currency_ID',TO_TIMESTAMP('2019-07-12 17:03:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Währung für diesen Eintrag','de.metas.swat',10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Währung',0,0,TO_TIMESTAMP('2019-07-12 17:03:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-12T17:03:14.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568420 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-12T17:03:14.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- 2019-07-12T17:10:17.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_FreightCostDetail','ALTER TABLE public.M_FreightCostDetail ADD COLUMN C_Currency_ID NUMERIC(10)')
;

-- 2019-07-12T17:10:17.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_FreightCostDetail ADD CONSTRAINT CCurrency_MFreightCostDetail FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;



-- 2019-07-15T17:29:30.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-07-15 17:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568420
;





UPDATE M_FreightCostDetail d
SET C_Currency_ID = coalesce((select c.C_Currency_ID from C_Country c where c.C_Country_ID = d.C_Country_Id), 102);

