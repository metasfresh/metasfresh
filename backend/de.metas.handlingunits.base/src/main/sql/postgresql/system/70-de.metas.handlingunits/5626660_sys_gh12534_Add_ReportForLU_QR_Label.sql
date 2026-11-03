-- 2022-02-17T15:59:46.435032900Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579287,1639,0,19,558,'AD_Image_ID',TO_TIMESTAMP('2022-02-17 17:59:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Image or Icon','D',0,10,'Images and Icon can be used to display supported graphic formats (gif, jpg, png).
You can either load the image (in the database) or point to a graphic via a URI (i.e. it can point to a resource, http address)','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bild',0,0,TO_TIMESTAMP('2022-02-17 17:59:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-17T15:59:46.470830900Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579287 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-17T15:59:46.568315300Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1639) 
;

-- 2022-02-17T15:59:52.254884400Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_AttributeValue','ALTER TABLE public.M_AttributeValue ADD COLUMN AD_Image_ID NUMERIC(10)')
;

-- 2022-02-17T15:59:52.554802700Z
-- URL zum Konzept
ALTER TABLE M_AttributeValue ADD CONSTRAINT ADImage_MAttributeValue FOREIGN KEY (AD_Image_ID) REFERENCES public.AD_Image DEFERRABLE INITIALLY DEFERRED
;

-- 2022-02-17T16:00:41.491312700Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579287,680615,0,542161,TO_TIMESTAMP('2022-02-17 18:00:41','YYYY-MM-DD HH24:MI:SS'),100,'Image or Icon',10,'D','Images and Icon can be used to display supported graphic formats (gif, jpg, png).
You can either load the image (in the database) or point to a graphic via a URI (i.e. it can point to a resource, http address)','Y','N','N','N','N','N','N','N','Bild',TO_TIMESTAMP('2022-02-17 18:00:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-17T16:00:41.527181Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=680615 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-02-17T16:00:41.563147600Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1639) 
;

-- 2022-02-17T16:00:41.624366700Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=680615
;

-- 2022-02-17T16:00:41.655986300Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(680615)
;

-- 2022-02-17T16:01:14.124370900Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,680615,0,542161,543246,601348,'F',TO_TIMESTAMP('2022-02-17 18:01:13','YYYY-MM-DD HH24:MI:SS'),100,'Image or Icon','Images and Icon can be used to display supported graphic formats (gif, jpg, png).
You can either load the image (in the database) or point to a graphic via a URI (i.e. it can point to a resource, http address)','Y','N','N','Y','N','N','N',0,'Bild',30,0,0,TO_TIMESTAMP('2022-02-17 18:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;




-- 2022-02-17T16:11:43.083962400Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584997,'N','de.metas.handlingunits.process.M_HU_Report_LU_Label','N',TO_TIMESTAMP('2022-02-17 18:11:42','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','N','N','N','N','N','N','N','Y','Y',0,'QR Etikett','json','N','S','xls','Hooked in M_HU_Report. When called, it expects the M_HU_IDs in T_Selection. Will generate the QR codes for them and will call the actual Jasper Report which prints the LU label that contains QR labels and addtional product infos.','Java',TO_TIMESTAMP('2022-02-17 18:11:42','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_Report_LU_Label')
;

-- 2022-02-17T16:11:43.118024300Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584997 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;



-- 2022-02-17T16:16:29.889209100Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584998,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2022-02-17 18:16:29','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.handlingunits','Y','N','Y','Y','Y','N','N','Y','Y','Y','@PREFIX@de/metas/docs/label/label_lu.jasper',0,'LU Label with QR Code (jasper)','json','N','N','xls','','JasperReportsSQL',TO_TIMESTAMP('2022-02-17 18:16:29','YYYY-MM-DD HH24:MI:SS'),100,'LU_Label_QRCode')
;

-- 2022-02-17T16:16:29.919996200Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584998 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;


-- 2022-02-18T11:20:15.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=32, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-02-18 13:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579287
;

-- 2022-02-18T11:20:18.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_attributevalue','AD_Image_ID','NUMERIC(10)',null,null)
;

-- 2022-02-18T13:41:30.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Paletten Etikett',Updated=TO_TIMESTAMP('2022-02-18 15:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584997
;

-- 2022-02-18T13:41:36.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Paletten Etikett',Updated=TO_TIMESTAMP('2022-02-18 15:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584997
;

-- 2022-02-18T13:41:39.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Paletten Etikett',Updated=TO_TIMESTAMP('2022-02-18 15:41:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584997
;

-- 2022-02-18T13:41:54.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='LU Label (QR)',Updated=TO_TIMESTAMP('2022-02-18 15:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584997
;

-- 2022-02-18T13:42:09.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,IsActive,IsApplyToCUs,IsApplyToLUs,IsApplyToTopLevelHUsOnly,IsApplyToTUs,IsProvideAsUserAction,M_HU_Process_ID,Updated,UpdatedBy) VALUES (0,0,584997,TO_TIMESTAMP('2022-02-18 15:42:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Y',540021,TO_TIMESTAMP('2022-02-18 15:42:09','YYYY-MM-DD HH24:MI:SS'),100)
;

