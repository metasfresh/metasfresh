-- 2019-10-16T08:26:57.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569220,215,0,19,541411,'C_UOM_ID',TO_TIMESTAMP('2019-10-16 11:26:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','de.metas.shipper.gateway.dhl',10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Maßeinheit',0,0,TO_TIMESTAMP('2019-10-16 11:26:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-16T08:26:57.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569220 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-16T08:26:57.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- 2019-10-16T08:29:48.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569220,589593,0,542055,0,TO_TIMESTAMP('2019-10-16 11:29:48','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',255,'D','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','Y','Y','N','N','N','N','N','Maßeinheit',70,70,0,1,1,TO_TIMESTAMP('2019-10-16 11:29:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T08:29:48.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589593 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-16T08:29:48.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2019-10-16T08:29:49.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589593
;

-- 2019-10-16T08:29:49.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589593)
;

-- 2019-10-16T08:30:27.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DHL_Shipper_Config','ALTER TABLE public.DHL_Shipper_Config ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2019-10-16T08:30:27.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DHL_Shipper_Config ADD CONSTRAINT CUOM_DHLShipperConfig FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- move millimeter to client 0
update c_uom set ad_client_id = 0 where c_uom_id = 1000001;

-- 2019-10-16T09:15:37.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM (AD_Client_ID,AD_Org_ID,CostingPrecision,Created,CreatedBy,C_UOM_ID,IsActive,IsDefault,Name,StdPrecision,UOMSymbol,UOMType,Updated,UpdatedBy,X12DE355) VALUES (0,0,0,TO_TIMESTAMP('2019-10-16 12:15:37','YYYY-MM-DD HH24:MI:SS'),100,540047,'Y','N','Centimeter',2,'CM','LE',TO_TIMESTAMP('2019-10-16 12:15:37','YYYY-MM-DD HH24:MI:SS'),100,'CM')
;

-- 2019-10-16T09:15:37.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM_Trl (AD_Language,C_UOM_ID, Description,Name,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_UOM_ID, t.Description,t.Name,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_UOM t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_UOM_ID=540047 AND NOT EXISTS (SELECT 1 FROM C_UOM_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_UOM_ID=t.C_UOM_ID)
;

-- 2019-10-16T09:21:38.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMType='LE',Updated=TO_TIMESTAMP('2019-10-16 12:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=1000001
;

-- 2019-10-16T09:22:36.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM_Conversion (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_Conversion_ID,C_UOM_ID,C_UOM_To_ID,DivideRate,IsActive,IsCatchUOMForProduct,MultiplyRate,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2019-10-16 12:22:36','YYYY-MM-DD HH24:MI:SS'),100,540012,540047,1000001,0.100000000000,'Y','N',10.000000000000,TO_TIMESTAMP('2019-10-16 12:22:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T09:24:28.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET UOMSymbol='DK', X12DE355='DK',Updated=TO_TIMESTAMP('2019-10-16 12:24:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540018
;

-- 2019-10-16T09:24:28.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET Description=NULL, Name='Kilometre', UOMSymbol='DK', IsTranslated='Y' WHERE C_UOM_ID=540018
;

-- 2019-10-16T09:29:04.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577224,0,'DhlLenghtUOM_ID',TO_TIMESTAMP('2019-10-16 12:29:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dhl Lenght UOM ID','Dhl Lenght UOM ID',TO_TIMESTAMP('2019-10-16 12:29:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T09:29:04.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577224 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-16T09:29:31.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Dhl_LenghtUOM_ID', Name='Dhl_LenghtUOM_ID', PrintName='Dhl_LenghtUOM_ID',Updated=TO_TIMESTAMP('2019-10-16 12:29:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577224
;

-- 2019-10-16T09:29:31.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Dhl_LenghtUOM_ID', Name='Dhl_LenghtUOM_ID', Description=NULL, Help=NULL WHERE AD_Element_ID=577224
;

-- 2019-10-16T09:29:31.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Dhl_LenghtUOM_ID', Name='Dhl_LenghtUOM_ID', Description=NULL, Help=NULL, AD_Element_ID=577224 WHERE UPPER(ColumnName)='DHL_LENGHTUOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-16T09:29:31.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Dhl_LenghtUOM_ID', Name='Dhl_LenghtUOM_ID', Description=NULL, Help=NULL WHERE AD_Element_ID=577224 AND IsCentrallyMaintained='Y'
;

-- 2019-10-16T09:29:31.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dhl_LenghtUOM_ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577224) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577224)
;

-- 2019-10-16T09:29:31.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Dhl_LenghtUOM_ID', Name='Dhl_LenghtUOM_ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577224)
;

-- 2019-10-16T09:29:31.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Dhl_LenghtUOM_ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577224
;

-- 2019-10-16T09:29:31.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Dhl_LenghtUOM_ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 577224
;

-- 2019-10-16T09:29:31.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Dhl_LenghtUOM_ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577224
;

-- 2019-10-16T09:32:47.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=577224, AD_Reference_Value_ID=114, ColumnName='Dhl_LenghtUOM_ID', Description=NULL, Help=NULL, Name='Dhl_LenghtUOM_ID',Updated=TO_TIMESTAMP('2019-10-16 12:32:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T09:32:47.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dhl_LenghtUOM_ID', Description=NULL, Help=NULL WHERE AD_Column_ID=569220
;

-- 2019-10-16T09:32:47.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577224) 
;

-- 2019-10-16T09:33:09.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2019-10-16 12:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T09:33:40.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DHL_Shipper_Config','ALTER TABLE public.DHL_Shipper_Config ADD COLUMN Dhl_LenghtUOM_ID NUMERIC(10)')
;

-- 2019-10-16T09:33:40.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DHL_Shipper_Config ADD CONSTRAINT DhlLenghtUOM_DHLShipperConfig FOREIGN KEY (Dhl_LenghtUOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2019-10-16T10:11:34.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589593
;

-- 2019-10-16T10:11:34.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589593
;

-- 2019-10-16T10:11:34.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=589593
;

-- 2019-10-16T10:14:31.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2019-10-16 13:14:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T10:14:31.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','Dhl_LenghtUOM_ID','NUMERIC(10)',null,null)
;

-- 2019-10-16T10:19:48.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540467,'C_UOM.x12de355=''CM''',TO_TIMESTAMP('2019-10-16 13:19:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_UOM_Centimeter','S',TO_TIMESTAMP('2019-10-16 13:19:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:20:11.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540467,Updated=TO_TIMESTAMP('2019-10-16 13:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T10:20:20.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','Dhl_LenghtUOM_ID','NUMERIC(10)',null,null)
;

-- 2019-10-16T10:20:42.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2019-10-16 13:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T10:20:51.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','Dhl_LenghtUOM_ID','NUMERIC(10)',null,null)
;

-- 2019-10-16T10:22:15.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569220,589595,0,542055,0,TO_TIMESTAMP('2019-10-16 13:22:15','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','Dhl_LenghtUOM_ID',70,70,0,1,1,TO_TIMESTAMP('2019-10-16 13:22:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:22:15.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-16T10:22:15.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577224) 
;

-- 2019-10-16T10:22:15.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589595
;

-- 2019-10-16T10:22:15.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589595)
;

-- 2019-10-16T10:25:05.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_UOM.x12de355=CM',Updated=TO_TIMESTAMP('2019-10-16 13:25:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540467
;

-- 2019-10-16T10:26:42.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_UOM.x12de355=''CM''',Updated=TO_TIMESTAMP('2019-10-16 13:26:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540467
;

-- 2019-10-16T10:29:38.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2019-10-16 13:29:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589595
;

-- 2019-10-16T10:31:37.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL, DefaultValue='@SQL = SELECT C_UOM_ID FROM C_UOM WHERE x12355 = ''CM''', IsAutoApplyValidationRule='N',Updated=TO_TIMESTAMP('2019-10-16 13:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T10:31:59.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL = SELECT C_UOM_ID FROM C_UOM WHERE x12de355 = ''CM''',Updated=TO_TIMESTAMP('2019-10-16 13:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T10:37:35.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2019-10-16 13:37:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T10:37:53.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540467,Updated=TO_TIMESTAMP('2019-10-16 13:37:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T10:37:54.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2019-10-16 13:37:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569220
;

-- 2019-10-16T10:44:18.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542055,541597,TO_TIMESTAMP('2019-10-16 13:44:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-10-16 13:44:17','YYYY-MM-DD HH24:MI:SS'),100,'default')
;

-- 2019-10-16T10:44:18.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541597 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-10-16T10:44:36.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542034,541597,TO_TIMESTAMP('2019-10-16 13:44:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-10-16 13:44:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:44:58.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542034,543079,TO_TIMESTAMP('2019-10-16 13:44:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-10-16 13:44:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:45:52.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589484,0,542055,543079,563253,'F',TO_TIMESTAMP('2019-10-16 13:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DHL API URL',10,0,0,TO_TIMESTAMP('2019-10-16 13:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:46:10.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589547,0,542055,543079,563254,'F',TO_TIMESTAMP('2019-10-16 13:46:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Application Id',20,0,0,TO_TIMESTAMP('2019-10-16 13:46:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:46:23.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589548,0,542055,543079,563255,'F',TO_TIMESTAMP('2019-10-16 13:46:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Application Token',30,0,0,TO_TIMESTAMP('2019-10-16 13:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:46:35.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589549,0,542055,543079,563256,'F',TO_TIMESTAMP('2019-10-16 13:46:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Account Number',40,0,0,TO_TIMESTAMP('2019-10-16 13:46:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:46:46.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589552,0,542055,543079,563257,'F',TO_TIMESTAMP('2019-10-16 13:46:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserName',50,0,0,TO_TIMESTAMP('2019-10-16 13:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:46:54.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589550,0,542055,543079,563258,'F',TO_TIMESTAMP('2019-10-16 13:46:54','YYYY-MM-DD HH24:MI:SS'),100,'https://entwickler.dhl.de/group/ep/authentifizierung4','Y','N','N','Y','N','N','N',0,'Signature',60,0,0,TO_TIMESTAMP('2019-10-16 13:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:47:00.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589595,0,542055,543079,563259,'F',TO_TIMESTAMP('2019-10-16 13:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Dhl_LenghtUOM_ID',70,0,0,TO_TIMESTAMP('2019-10-16 13:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-16T10:49:31.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-10-16 13:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589595
;

-- 2019-10-16T10:50:25.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','Dhl_LenghtUOM_ID','NUMERIC(10)',null,null)
;

-- 2019-10-16T10:56:23.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET EntityType='de.metas.shipper.gateway.dhl',Updated=TO_TIMESTAMP('2019-10-16 13:56:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542055
;

-- 2019-10-16T10:56:41.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=255,Updated=TO_TIMESTAMP('2019-10-16 13:56:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589595
;

-- 2019-10-16T10:58:57.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','AccountNumber','VARCHAR(255)',null,null)
;

-- 2019-10-16T10:59:02.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2019-10-16T10:59:08.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','applicationID','VARCHAR(255)',null,null)
;

-- 2019-10-16T10:59:12.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','ApplicationToken','VARCHAR(255)',null,null)
;

-- 2019-10-16T10:59:16.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','dhl_api_url','VARCHAR(1024)',null,null)
;

-- 2019-10-16T10:59:23.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','Dhl_LenghtUOM_ID','NUMERIC(10)',null,null)
;

-- 2019-10-16T10:59:27.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','DHL_Shipper_Config_ID','NUMERIC(10)',null,null)
;

-- 2019-10-16T10:59:30.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dhl_shipper_config','UserName','VARCHAR(255)',null,null)
;

-- 2019-10-16T11:03:26.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description,AD_Org_ID,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-10-16 14:03:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-10-16 14:03:26','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541411,'N','"Lieferweg" bezeichnet die Art der Warenlieferung.',569222,'N','N','N','N','N','N','N','N',0,455,'N','N','M_Shipper_ID','N','Lieferweg','Methode oder Art der Warenlieferung',0,'de.metas.shipper.gateway.dhl')
;

-- 2019-10-16T11:03:26.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569222 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-16T11:03:26.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(455) 
;

-- 2019-10-16T11:03:29.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DHL_Shipper_Config','ALTER TABLE public.DHL_Shipper_Config ADD COLUMN M_Shipper_ID NUMERIC(10)')
;

-- 2019-10-16T11:03:29.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DHL_Shipper_Config ADD CONSTRAINT MShipper_DHLShipperConfig FOREIGN KEY (M_Shipper_ID) REFERENCES public.M_Shipper DEFERRABLE INITIALLY DEFERRED
;

-- 2019-10-16T11:03:50.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=569222,Updated=TO_TIMESTAMP('2019-10-16 14:03:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542055
;

