-- 2019-10-11T10:59:50.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','AccountNumber','VARCHAR(255)',null,null)
;

-- 2019-10-11T11:01:17.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','AccountNumber','VARCHAR(255)',null,null)
;

-- 2019-10-11T11:01:24.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DHL_Shipper_Config','ALTER TABLE public.DHL_Shipper_Config ADD COLUMN ApplicationToken VARCHAR(255)')
;

-- 2019-10-11T11:01:42.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DHL_Shipper_Config','ALTER TABLE public.DHL_Shipper_Config ADD COLUMN Signature VARCHAR(255)')
;

-- 2019-10-11T11:02:48.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description,AD_Org_ID,EntityType) VALUES (10,255,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-10-11 14:02:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-10-11 14:02:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541411,'N','',569143,'N','N','N','N','N','N','N','N',0,1903,'N','N','UserName','N','Nutzer-ID/Login','',0,'de.metas.shipper.gateway.dhl')
;

-- 2019-10-11T11:02:48.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569143 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-11T11:02:48.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(1903) 
;

-- 2019-10-11T11:02:55.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589551
;

-- 2019-10-11T11:02:55.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589551
;

-- 2019-10-11T11:02:55.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=589551
;

-- 2019-10-11T11:03:05.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569143,589552,0,542055,0,TO_TIMESTAMP('2019-10-11 14:03:05','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','',0,'Y','Y','Y','N','N','N','N','N','Nutzer-ID/Login',60,60,0,1,1,TO_TIMESTAMP('2019-10-11 14:03:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-11T11:03:05.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589552 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-11T11:03:05.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1903) 
;

-- 2019-10-11T11:03:05.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589552
;

-- 2019-10-11T11:03:05.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589552)
;

-- 2019-10-11T11:03:10.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DHL_Shipper_Config','ALTER TABLE public.DHL_Shipper_Config ADD COLUMN UserName VARCHAR(255)')
;

-- 2019-10-11T11:03:48.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=569141
;

-- 2019-10-11T11:03:48.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=569141
;

-- 2019-10-11T11:10:10.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=60,Updated=TO_TIMESTAMP('2019-10-11 14:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589550
;

-- 2019-10-11T11:10:14.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2019-10-11 14:10:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589552
;

-- 2019-10-11T11:24:55.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-10-11 14:24:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589552
;

-- 2019-10-11T11:24:59.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-10-11 14:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589550
;

