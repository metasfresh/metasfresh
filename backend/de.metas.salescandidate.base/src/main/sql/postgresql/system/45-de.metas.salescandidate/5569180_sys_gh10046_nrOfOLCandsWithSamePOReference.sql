-- 2020-10-01T08:59:17.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=571533
;

-- 2020-10-01T08:59:17.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=617675
;

-- 2020-10-01T08:59:17.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=617675
;

-- 2020-10-01T08:59:17.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=617675
;

-- 2020-10-01T08:59:17.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE M_ShipmentSchedule DROP COLUMN IF EXISTS FilteredItemsWithSameHeaderAggregationKey')
;

-- 2020-10-01T08:59:17.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=571515
;

-- 2020-10-01T08:59:17.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=571515
;

-- 2020-10-01T09:02:49.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='N', ColumnSQL='',Updated=TO_TIMESTAMP('2020-10-01 12:02:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552464
;

-- 2020-10-01T09:02:53.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN POReference VARCHAR(40)')
;

-- 2020-10-01T09:07:19.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578380,0,'nrOfOLCandsWithSamePOReference',TO_TIMESTAMP('2020-10-01 12:07:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nr of order line candidates with the same PO ref','Nr of order line candidates with the same PO ref',TO_TIMESTAMP('2020-10-01 12:07:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-01T09:07:19.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578380 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-10-01T09:08:10.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,FacetFilterSeqNo,MaxFacetsToFetch,IsFacetFilter,AD_Element_ID,EntityType) VALUES (11,'0',10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-10-01 12:08:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2020-10-01 12:08:10','YYYY-MM-DD HH24:MI:SS'),100,'N','N',500221,'N',571839,'N','N','N','N','N','N','N','N',0,'N','N','nrOfOLCandsWithSamePOReference','N','Nr of order line candidates with the same PO ref',0,0,0,'N',578380,'de.metas.inoutcandidate')
;

-- 2020-10-01T09:08:10.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571839 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-10-01T09:08:10.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578380) 
;

-- 2020-10-01T09:10:55.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN nrOfOLCandsWithSamePOReference NUMERIC(10) DEFAULT 0')
;

-- 2020-10-01T09:12:33.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='NrOfOLCandsWithSamePOReference',Updated=TO_TIMESTAMP('2020-10-01 12:12:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578380
;

-- 2020-10-01T09:12:33.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NrOfOLCandsWithSamePOReference', Name='Nr of order line candidates with the same PO ref', Description=NULL, Help=NULL WHERE AD_Element_ID=578380
;

-- 2020-10-01T09:12:33.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NrOfOLCandsWithSamePOReference', Name='Nr of order line candidates with the same PO ref', Description=NULL, Help=NULL, AD_Element_ID=578380 WHERE UPPER(ColumnName)='NROFOLCANDSWITHSAMEPOREFERENCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-10-01T09:12:33.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NrOfOLCandsWithSamePOReference', Name='Nr of order line candidates with the same PO ref', Description=NULL, Help=NULL WHERE AD_Element_ID=578380 AND IsCentrallyMaintained='Y'
;

-- 2020-10-01T09:12:47.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE M_ShipmentSchedule DROP COLUMN IF EXISTS NrOfOLCandsWithSamePOReference')
;

-- 2020-10-01T09:12:47.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=571839
;

-- 2020-10-01T09:12:47.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=571839
;

-- 2020-10-01T09:13:58.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,FacetFilterSeqNo,MaxFacetsToFetch,IsFacetFilter,AD_Element_ID,EntityType) VALUES (11,'0',10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-10-01 12:13:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2020-10-01 12:13:58','YYYY-MM-DD HH24:MI:SS'),100,'N','N',500221,'N',571840,'N','N','N','N','N','N','N','N',0,'N','N','NrOfOLCandsWithSamePOReference','N','Nr of order line candidates with the same PO ref',0,0,0,'N',578380,'de.metas.inoutcandidate')
;

-- 2020-10-01T09:13:58.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571840 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-10-01T09:13:58.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578380) 
;

-- 2020-10-01T09:14:00.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN NrOfOLCandsWithSamePOReference NUMERIC(10) DEFAULT 0')
;

-- 2020-10-01T11:20:49.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-10-01 14:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555942
;

-- 2020-10-01T11:21:06.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571840,619156,0,500221,0,TO_TIMESTAMP('2020-10-01 14:21:06','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','Nr of order line candidates with the same PO ref',620,690,0,1,1,TO_TIMESTAMP('2020-10-01 14:21:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-01T11:21:06.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=619156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-01T11:21:06.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578380) 
;

-- 2020-10-01T11:21:06.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=619156
;

-- 2020-10-01T11:21:06.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(619156)
;

-- 2020-10-01T11:21:46.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,619156,0,500221,572229,540052,'F',TO_TIMESTAMP('2020-10-01 14:21:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Nr of order line candidates with the same PO ref',330,0,0,TO_TIMESTAMP('2020-10-01 14:21:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-01T13:41:11.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541344,'S',TO_TIMESTAMP('2020-10-01 16:41:10','YYYY-MM-DD HH24:MI:SS'),100,'If set, when computing the M_ShipmentSchedule.NrOfOLCandsWithSamePOReference the sys config will be used to determine a target time window of the query. (Only C_OLCand records created in the resulted time window will be considered ).
The time frame is calculated as: (shipmentSchedule.created - daysOffset, shipmentSchedule.created + daysOffset).','D','Y','shipmentSchedule.recomputeNrOfOLCandsWithSamePORef.daysOffset',TO_TIMESTAMP('2020-10-01 16:41:10','YYYY-MM-DD HH24:MI:SS'),100,'-1')
;

