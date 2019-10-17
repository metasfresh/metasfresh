-- 2019-04-05T22:07:26.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-04-05 22:07:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=9942
;

-- 2019-04-05T22:07:38.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_private_access','AD_User_ID','NUMERIC(10)',null,null)
;

-- 2019-04-05T22:07:38.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_private_access','AD_User_ID',null,'NULL',null)
;

-- 2019-04-05T22:08:03.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567638,576186,0,30,627,'AD_UserGroup_ID',TO_TIMESTAMP('2019-04-05 22:08:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Nutzergruppe',0,0,TO_TIMESTAMP('2019-04-05 22:08:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-05T22:08:03.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567638 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-05T22:08:05.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Private_Access','ALTER TABLE public.AD_Private_Access ADD COLUMN AD_UserGroup_ID NUMERIC(10)')
;

-- 2019-04-05T22:08:05.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Private_Access ADD CONSTRAINT ADUserGroup_ADPrivateAccess FOREIGN KEY (AD_UserGroup_ID) REFERENCES public.AD_UserGroup DEFERRABLE INITIALLY DEFERRED
;

-- 2019-04-05T22:08:18.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, IsUpdateable='N',Updated=TO_TIMESTAMP('2019-04-05 22:08:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=9940
;

