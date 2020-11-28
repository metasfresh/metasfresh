
-- 2017-12-15T16:41:15.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543685,0,'IsIncludeOther',TO_TIMESTAMP('2017-12-15 16:41:15','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll','de.metas.dimension','Y','inkl. "sonstige"-Eintrag','inkl. "sonstige"-Eintrag',TO_TIMESTAMP('2017-12-15 16:41:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-15T16:41:15.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543685 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-12-15T16:41:35.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsIncludeOtherGroup',Updated=TO_TIMESTAMP('2017-12-15 16:41:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543685
;

-- 2017-12-15T16:41:35.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsIncludeOtherGroup', Name='inkl. "sonstige"-Eintrag', Description='Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll', Help=NULL WHERE AD_Element_ID=543685
;

-- 2017-12-15T16:41:35.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsIncludeOtherGroup', Name='inkl. "sonstige"-Eintrag', Description='Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll', Help=NULL, AD_Element_ID=543685 WHERE UPPER(ColumnName)='ISINCLUDEOTHERGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-12-15T16:41:35.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsIncludeOtherGroup', Name='inkl. "sonstige"-Eintrag', Description='Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll', Help=NULL WHERE AD_Element_ID=543685 AND IsCentrallyMaintained='Y'
;

-- 2017-12-15T16:41:43.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558320,543685,0,20,540660,'N','IsIncludeOtherGroup',TO_TIMESTAMP('2017-12-15 16:41:43','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll','de.metas.dimension',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','inkl. "sonstige"-Eintrag',0,0,TO_TIMESTAMP('2017-12-15 16:41:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-12-15T16:41:43.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558320 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-12-15T16:41:45.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DIM_Dimension_Spec','ALTER TABLE public.DIM_Dimension_Spec ADD COLUMN IsIncludeOtherGroup CHAR(1) DEFAULT ''N'' CHECK (IsIncludeOtherGroup IN (''Y'',''N'')) NOT NULL')
;

-- 2017-12-15T16:42:34.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,558320,561185,0,540680,0,TO_TIMESTAMP('2017-12-15 16:42:34','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll',0,'de.metas.dimension',0,'Y','Y','Y','Y','N','N','N','N','Y','inkl. "sonstige"-Eintrag',49,49,0,1,1,TO_TIMESTAMP('2017-12-15 16:42:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-15T16:42:34.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561185 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

