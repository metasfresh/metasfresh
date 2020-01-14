-- 2020-01-10T18:48:53.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569829,542776,0,29,540645,'QtyDeliveredInUOM',TO_TIMESTAMP('2020-01-10 19:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)','de.metas.esb.edi',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Liefermenge',0,0,TO_TIMESTAMP('2020-01-10 19:48:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-01-10T18:48:53.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569829 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-01-10T18:48:53.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542776) 
;

ALTER TABLE EDI_DesadvLine RENAME COLUMN QtyDeliveredInUOM_BKP TO QtyDeliveredInUOM;
-- 2020-01-10T18:52:02.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569700,593843,0,540663,TO_TIMESTAMP('2020-01-10 19:52:02','YYYY-MM-DD HH24:MI:SS'),100,'Bestellt/ Beauftragt',10,'de.metas.esb.edi','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','N','N','N','N','N','Bestellt/ Beauftragt',TO_TIMESTAMP('2020-01-10 19:52:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-10T18:52:02.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-01-10T18:52:02.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(531) 
;

-- 2020-01-10T18:52:02.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593843
;

-- 2020-01-10T18:52:02.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593843)
;

-- 2020-01-10T18:52:02.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569824,593844,0,540663,TO_TIMESTAMP('2020-01-10 19:52:02','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.',10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Abr. Menge basiert auf',TO_TIMESTAMP('2020-01-10 19:52:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-10T18:52:02.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-01-10T18:52:02.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576948) 
;

-- 2020-01-10T18:52:02.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593844
;

-- 2020-01-10T18:52:02.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593844)
;

-- 2020-01-10T18:52:03Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569829,593845,0,540663,TO_TIMESTAMP('2020-01-10 19:52:02','YYYY-MM-DD HH24:MI:SS'),100,'Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)',10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Liefermenge',TO_TIMESTAMP('2020-01-10 19:52:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-10T18:52:03.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-01-10T18:52:03.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542776) 
;

-- 2020-01-10T18:52:03.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593845
;

-- 2020-01-10T18:52:03.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593845)
;

-- 2020-01-10T18:52:36.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,593845,0,540663,541228,564811,'F',TO_TIMESTAMP('2020-01-10 19:52:36','YYYY-MM-DD HH24:MI:SS'),100,'Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)','Y','N','N','Y','N','N','N',0,'QtyDeliveredInUOM',180,0,0,TO_TIMESTAMP('2020-01-10 19:52:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-10T18:52:50.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2020-01-10 19:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564811
;

-- 2020-01-10T18:52:50.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2020-01-10 19:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563260
;

-- 2020-01-10T18:52:50.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2020-01-10 19:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549241
;

-- 2020-01-10T18:52:50.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2020-01-10 19:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549232
;

-- 2020-01-10T18:54:48.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2020-01-10 19:54:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549223
;

-- 2020-01-10T18:56:52.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='', IsLazyLoading='N',Updated=TO_TIMESTAMP('2020-01-10 19:56:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552776
;

-- 2020-01-10T18:56:56.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','EDI_DESADV_SumPercentage','NUMERIC',null,null)
;

UPDATE edi_desadv 
SET UpdatedBy=99, Updated='2020-01-10 19:56:52',
EDI_DESADV_SumPercentage= (select CASE WHEN SUM (l.QtyOrdered) = 0 THEN NULL ELSE round ((SUM (l.QtyDeliveredInStockingUOM)/SUM (l.QtyOrdered) ) * 100, 2) END from EDI_DesadvLine l where l.EDI_Desadv_ID = EDI_Desadv.EDI_Desadv_ID)
WHERE EDI_DESADV_SumPercentage IS NULL;
;

-- 2020-01-10T19:00:44.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2020-01-10 20:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549219
;

-- 2020-01-10T19:01:19.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540709,543298,TO_TIMESTAMP('2020-01-10 20:01:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','further_infos',20,TO_TIMESTAMP('2020-01-10 20:01:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-10T19:01:56.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543298, SeqNo=10,Updated=TO_TIMESTAMP('2020-01-10 20:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549217
;

-- 2020-01-10T19:02:13.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2020-01-10 20:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549207
;

-- 2020-01-10T19:03:14.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543298, SeqNo=20,Updated=TO_TIMESTAMP('2020-01-10 20:03:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549216
;

-- 2020-01-10T19:12:34.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577456,0,'SumOrderedInStockingUOM',TO_TIMESTAMP('2020-01-10 20:12:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','SumOrderedInStockingUOM','SumOrderedInStockingUOM',TO_TIMESTAMP('2020-01-10 20:12:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-10T19:12:34.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577456 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-01-10T19:12:58.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577457,0,'SumDeliveredInStockingUOM',TO_TIMESTAMP('2020-01-10 20:12:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','SumDeliveredInStockingUOM','SumDeliveredInStockingUOM',TO_TIMESTAMP('2020-01-10 20:12:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-10T19:12:58.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577457 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-01-10T19:13:20.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569830,577457,0,29,540644,'SumDeliveredInStockingUOM',TO_TIMESTAMP('2020-01-10 20:13:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','SumDeliveredInStockingUOM',0,0,TO_TIMESTAMP('2020-01-10 20:13:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-01-10T19:13:20.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569830 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-01-10T19:13:20.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577457) 
;

-- 2020-01-10T19:13:24.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_Desadv','ALTER TABLE public.EDI_Desadv ADD COLUMN SumDeliveredInStockingUOM NUMERIC')
;

-- 2020-01-10T19:13:37.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2020-01-10 20:13:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569830
;

-- 2020-01-10T19:13:41.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','SumDeliveredInStockingUOM','NUMERIC',null,'0')
;

-- 2020-01-10T19:13:41.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EDI_Desadv SET SumDeliveredInStockingUOM=0 WHERE SumDeliveredInStockingUOM IS NULL
;

-- at this point we first need a COMMIT, to avoid
-- 07:15:52   	psql:/opt/metasfresh/dist/sql/80-de.metas.edi/5541160_sys_gh6011-app_add_physical_fullfillment_columns.sql:221: ERROR:  cannot ALTER TABLE "edi_desadv" because it has pending trigger events

-- 2020-01-10T19:13:41.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO t_alter_column values('edi_desadv','SumDeliveredInStockingUOM',null,'NOT NULL',null)
--;

