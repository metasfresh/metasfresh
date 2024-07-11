-- 2022-02-07T13:21:03.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579208,541904,0,19,540888,'C_Queue_WorkPackage_ID',TO_TIMESTAMP('2022-02-07 15:21:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.event',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Asynchrone Verarbeitungswarteschlange',0,0,TO_TIMESTAMP('2022-02-07 15:21:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-07T13:21:03.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579208 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-07T13:21:03.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(541904) 
;

-- 2022-02-07T13:21:07.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_EventLog','ALTER TABLE public.AD_EventLog ADD COLUMN C_Queue_WorkPackage_ID NUMERIC(10)')
;

-- 2022-02-07T13:21:07.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_EventLog ADD CONSTRAINT CQueueWorkPackage_ADEventLog FOREIGN KEY (C_Queue_WorkPackage_ID) REFERENCES public.C_Queue_WorkPackage DEFERRABLE INITIALLY DEFERRED
;

-- 2022-02-07T15:34:05.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,'de.metas.material.event.MaterialEventWorkpackageProcessor',540094,TO_TIMESTAMP('2022-02-07 17:34:05','YYYY-MM-DD HH24:MI:SS'),100,'See https://github.com/metasfresh/metasfresh/issues/12481','de.metas.material.dispo','MaterialEventWorkpackageProcessor','Y',TO_TIMESTAMP('2022-02-07 17:34:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-07T15:35:15.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540064,TO_TIMESTAMP('2022-02-07 17:35:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'MaterialEventWorkpackageProcessor',1,TO_TIMESTAMP('2022-02-07 17:35:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-07T15:35:55.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540094,540000,540064,TO_TIMESTAMP('2016-04-18 17:29:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-04-18 17:29:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-07T16:56:10.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579208,678617,0,540979,0,TO_TIMESTAMP('2022-02-07 18:56:10','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Asynchrone Verarbeitungswarteschlange',0,110,0,1,1,TO_TIMESTAMP('2022-02-07 18:56:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-07T16:56:10.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678617 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-02-07T16:56:10.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541904)
;

-- 2022-02-07T16:56:10.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678617
;

-- 2022-02-07T16:56:10.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(678617)
;

-- 2022-02-07T17:20:25.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545289,540590,TO_TIMESTAMP('2022-02-07 19:20:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',15,TO_TIMESTAMP('2022-02-07 19:20:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-07T17:20:32.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=545289
;

-- 2022-02-07T17:20:42.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540792,548030,TO_TIMESTAMP('2022-02-07 19:20:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','workpackage',30,TO_TIMESTAMP('2022-02-07 19:20:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-07T17:20:56.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,678617,0,540979,548030,600476,'F',TO_TIMESTAMP('2022-02-07 19:20:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Asynchrone Verarbeitungswarteschlange',10,0,0,TO_TIMESTAMP('2022-02-07 19:20:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-07T17:21:04.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-02-07 19:21:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=600476
;

-- 2022-02-07T17:22:01.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=15,Updated=TO_TIMESTAMP('2022-02-07 19:22:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=548030
;

-- 2022-02-07T17:23:03.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-02-07 19:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=600476
;

-- 2022-02-07T17:23:03.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-02-07 19:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550147
;

-- 2022-02-07T17:23:03.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-02-07 19:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550150
;

-- 2022-02-07T17:23:03.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-02-07 19:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550151
;

-- 2022-02-07T17:23:03.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-02-07 19:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550149
;

-- 2022-02-07T17:57:27.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_eventlog','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2022-02-07T17:59:34.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540527, FilterOperator='E', IsExcludeFromZoomTargets='Y', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-02-07 19:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579208
;

-- 2022-02-07T18:14:05.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2022-02-07 20:14:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579208
;

-- 2022-02-07T18:18:55.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2022-02-07 20:18:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579208
;

-- 2022-02-07T18:19:32.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-02-07 20:19:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=678617
;

