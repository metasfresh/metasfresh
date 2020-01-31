-- 2019-07-26T18:17:15.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-07-26 20:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568479
;

-- 2019-07-29T08:04:02.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inoutline','QtyDeliveredInPriceUOM_CatchWeight','NUMERIC',null,null)
;

-- 2019-07-29T08:04:59.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyDeliveredInCatchUOM',Updated=TO_TIMESTAMP('2019-07-29 10:04:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949
;

-- 2019-07-29T08:04:59.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInCatchUOM', Name='Catch Weight Menge in Preiseinheit', Description='Tatsächlich gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576949
;

-- 2019-07-29T08:04:59.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Catch Weight Menge in Preiseinheit', Description='Tatsächlich gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, AD_Element_ID=576949 WHERE UPPER(ColumnName)='QTYDELIVEREDINCATCHUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T08:04:59.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Catch Weight Menge in Preiseinheit', Description='Tatsächlich gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576949 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T08:11:14.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Tatsächlich gelieferte Menge in Catch Weight Einheit', Name='Menge in Catch Weight Einheit', PrintName='Menge in Catch Weight Einheit',Updated=TO_TIMESTAMP('2019-07-29 10:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_DE'
;

-- 2019-07-29T08:11:14.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_DE') 
;

-- 2019-07-29T08:11:14.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576949,'de_DE') 
;

-- 2019-07-29T08:11:14.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInCatchUOM', Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit', Help=NULL WHERE AD_Element_ID=576949
;

-- 2019-07-29T08:11:14.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit', Help=NULL, AD_Element_ID=576949 WHERE UPPER(ColumnName)='QTYDELIVEREDINCATCHUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T08:11:14.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit', Help=NULL WHERE AD_Element_ID=576949 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T08:11:14.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576949) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576949)
;

-- 2019-07-29T08:11:15.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Menge in Catch Weight Einheit', Name='Menge in Catch Weight Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576949)
;

-- 2019-07-29T08:11:15.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T08:11:15.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit', Help=NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T08:11:15.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Menge in Catch Weight Einheit', Description = 'Tatsächlich gelieferte Menge in Catch Weight Einheit', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T08:11:18.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Menge in Catch Weight Einheit', PrintName='Menge in Catch Weight Einheit',Updated=TO_TIMESTAMP('2019-07-29 10:11:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_CH'
;

-- 2019-07-29T08:11:18.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_CH') 
;

-- 2019-07-29T08:11:22.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.',Updated=TO_TIMESTAMP('2019-07-29 10:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_DE'
;

-- 2019-07-29T08:11:22.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_DE') 
;

-- 2019-07-29T08:11:22.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576949,'de_DE') 
;

-- 2019-07-29T08:11:22.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInCatchUOM', Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576949
;

-- 2019-07-29T08:11:22.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL, AD_Element_ID=576949 WHERE UPPER(ColumnName)='QTYDELIVEREDINCATCHUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T08:11:22.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576949 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T08:11:22.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576949) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576949)
;

-- 2019-07-29T08:11:22.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T08:11:22.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Menge in Catch Weight Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T08:11:22.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Menge in Catch Weight Einheit', Description = 'Tatsächlich gelieferte Menge in Catch Weight Einheit.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T08:11:27.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.',Updated=TO_TIMESTAMP('2019-07-29 10:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_CH'
;

-- 2019-07-29T08:11:27.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_CH') 
;

-- 2019-07-29T08:12:02.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Actually delivered quantity catch weight UOM unit of measurement.', Name='Quantity in catch weight UOM', PrintName='Quantity in catch weight UOM',Updated=TO_TIMESTAMP('2019-07-29 10:12:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='en_US'
;

-- 2019-07-29T08:12:02.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'en_US') 
;

ALTER TABLE M_InOutLine RENAME COLUMN QtyDeliveredInPriceUOM_CatchWeight TO QtyDeliveredInCatchUOM;


-- 2019-07-29T08:14:05.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inoutline','Price_UOM_ID','NUMERIC(10)',null,null)
;

-- 2019-07-29T08:15:27.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576953,0,'CatchWeightUOM',TO_TIMESTAMP('2019-07-29 10:15:27','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit','D','Y','Catch Weight Einheit','Catch Weight Einheit',TO_TIMESTAMP('2019-07-29 10:15:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T08:15:27.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576953 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T08:15:34.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 10:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576953 AND AD_Language='de_DE'
;

-- 2019-07-29T08:15:34.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576953,'de_DE') 
;

-- 2019-07-29T08:15:34.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576953,'de_DE') 
;

-- 2019-07-29T08:15:34.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CatchWeightUOM', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576953
;

-- 2019-07-29T08:15:34.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CatchWeightUOM', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL, AD_Element_ID=576953 WHERE UPPER(ColumnName)='CATCHWEIGHTUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T08:15:34.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CatchWeightUOM', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576953 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T08:15:34.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576953) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576953)
;

-- 2019-07-29T08:15:34.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576953
;

-- 2019-07-29T08:15:34.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID = 576953
;

-- 2019-07-29T08:15:34.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Catch Weight Einheit', Description = 'Aus dem Produktstamm übenommene Catch Weight Einheit.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576953
;

-- 2019-07-29T08:15:36.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 10:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576953 AND AD_Language='de_CH'
;

-- 2019-07-29T08:15:36.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576953,'de_CH') 
;

-- 2019-07-29T08:16:45.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Catch weight UOM as taken from the product master data.', IsTranslated='Y', Name='Catch weight UOM', PrintName='Catch weight UOM',Updated=TO_TIMESTAMP('2019-07-29 10:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576953 AND AD_Language='en_US'
;

-- 2019-07-29T08:16:45.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576953,'en_US') 
;

-- 2019-07-29T08:19:42.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='CatchWeight_UOM_ID',Updated=TO_TIMESTAMP('2019-07-29 10:19:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576953
;

-- 2019-07-29T08:19:42.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CatchWeight_UOM_ID', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576953
;

-- 2019-07-29T08:19:42.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CatchWeight_UOM_ID', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL, AD_Element_ID=576953 WHERE UPPER(ColumnName)='CATCHWEIGHT_UOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T08:19:42.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CatchWeight_UOM_ID', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576953 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T08:20:38.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Help=NULL, AD_Element_ID=576953, ColumnName='CatchWeight_UOM_ID', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.',Updated=TO_TIMESTAMP('2019-07-29 10:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568479
;

-- 2019-07-29T08:20:38.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Column_ID=568479
;

-- 2019-07-29T08:20:38.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- 2019-07-29T08:20:50.572Z
-- will be dropped again further down this file!
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN CatchWeight_UOM_ID NUMERIC(10)')
--;

-- 2019-07-29T08:20:50.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--ALTER TABLE M_InOutLine ADD CONSTRAINT CatchWeightUOM_MInOutLine FOREIGN KEY (CatchWeight_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
--;

-- 2019-07-29T08:28:24.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=210,Updated=TO_TIMESTAMP('2019-07-29 10:28:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1010
;

-- 2019-07-29T08:33:42.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540447,'/* the current product''s stocking UOM */
C_UOM.C_UOM_ID = (select C_UOM_ID from M_Product where M_Product_ID=@M_Product_ID/-1@) 

OR /* no product => any UOM */
@M_Product_ID/-1@=-1
',TO_TIMESTAMP('2019-07-29 10:33:42','YYYY-MM-DD HH24:MI:SS'),100,'Validation rule for C_UOM_Conversion.C_UOM_ID','D','Y','C_UOM_Conversion.C_UOM_ID','S',TO_TIMESTAMP('2019-07-29 10:33:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T08:34:05.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540447, IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2019-07-29 10:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1010
;

-- 2019-07-29T08:34:33.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2019-07-29 10:34:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1010
;

-- 2019-07-29T08:34:36.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-07-29 10:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1002
;

-- 2019-07-29T08:34:43.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2019-07-29 10:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1011
;

-- 2019-07-29T08:34:52.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2019-07-29 10:34:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12866
;

-- 2019-07-29T08:36:41.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576693, Description='Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.', Help=NULL, Name='Faktor für Ziel-Maßeinheit',Updated=TO_TIMESTAMP('2019-07-29 10:36:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57412
;

-- 2019-07-29T08:36:41.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576693) 
;

-- 2019-07-29T08:36:41.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=57412
;

-- 2019-07-29T08:36:41.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(57412)
;

-- 2019-07-29T09:04:05.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576956,0,'IsCatchUOM',TO_TIMESTAMP('2019-07-29 11:04:04','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird','D','Y','Ziel ist Catch-Maßeinheit','Ziel ist Catch-Maßeinheit',TO_TIMESTAMP('2019-07-29 11:04:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T09:04:05.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576956 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T09:04:12.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsProductCatchUOM',Updated=TO_TIMESTAMP('2019-07-29 11:04:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576956
;

-- 2019-07-29T09:04:12.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsProductCatchUOM', Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help=NULL WHERE AD_Element_ID=576956
;

-- 2019-07-29T09:04:12.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsProductCatchUOM', Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help=NULL, AD_Element_ID=576956 WHERE UPPER(ColumnName)='ISPRODUCTCATCHUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T09:04:12.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsProductCatchUOM', Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help=NULL WHERE AD_Element_ID=576956 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:04:22.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsCatchUOMForProduct',Updated=TO_TIMESTAMP('2019-07-29 11:04:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576956
;

-- 2019-07-29T09:04:22.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCatchUOMForProduct', Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help=NULL WHERE AD_Element_ID=576956
;

-- 2019-07-29T09:04:22.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCatchUOMForProduct', Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help=NULL, AD_Element_ID=576956 WHERE UPPER(ColumnName)='ISCATCHUOMFORPRODUCT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T09:04:22.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCatchUOMForProduct', Name='Ziel ist Catch-Maßeinheit', Description='Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird', Help=NULL WHERE AD_Element_ID=576956 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:04:29.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 11:04:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576956 AND AD_Language='de_CH'
;

-- 2019-07-29T09:04:29.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576956,'de_CH') 
;

-- 2019-07-29T09:04:32.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 11:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576956 AND AD_Language='de_DE'
;

-- 2019-07-29T09:04:32.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576956,'de_DE') 
;

-- 2019-07-29T09:04:32.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576956,'de_DE') 
;

-- 2019-07-29T09:05:28.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies if the target UOM is the product''s parallel UOM which in used in catch weight invoicing.', IsTranslated='Y', Name='Target is catch UOM', PrintName='Target is catch UOM',Updated=TO_TIMESTAMP('2019-07-29 11:05:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576956 AND AD_Language='en_US'
;

-- 2019-07-29T09:05:28.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576956,'en_US') 
;

-- 2019-07-29T09:06:39.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,TechnicalNote,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-29 11:06:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-29 11:06:38','YYYY-MM-DD HH24:MI:SS'),100,'N','N',175,'N',568488,'N','Y','N','N','N','N','N','N',0,0,576956,'D','N','Column is relevant only for product specifc UOM conversions (e.g. PCE to MG or PCE to M)','N','IsCatchUOMForProduct','N','Ziel ist Catch-Maßeinheit','Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird')
;

-- 2019-07-29T09:06:39.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568488 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T09:06:39.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576956) 
;

-- 2019-07-29T09:06:41.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_UOM_Conversion','ALTER TABLE public.C_UOM_Conversion ADD COLUMN IsCatchUOMForProduct CHAR(1) DEFAULT ''N'' CHECK (IsCatchUOMForProduct IN (''Y'',''N'')) NOT NULL')
;

-- 2019-07-29T09:07:26.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568488,582446,0,53246,0,TO_TIMESTAMP('2019-07-29 11:07:26','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest ob die Ziel-Maßeinheit die Parallel-Maßeinheit des Produktes ist, auf die bei einer Catch-Weight-Abrechnung zurückgegriffen wird',0,'U',0,'Y','Y','Y','N','N','N','N','N','Ziel ist Catch-Maßeinheit',80,80,0,1,1,TO_TIMESTAMP('2019-07-29 11:07:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T09:07:26.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582446 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T09:07:26.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576956) 
;

-- 2019-07-29T09:07:26.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582446
;

-- 2019-07-29T09:07:26.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582446)
;

-- 2019-07-29T09:08:41.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_UOM_ID',Updated=TO_TIMESTAMP('2019-07-29 11:08:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000203
;

-- 2019-07-29T09:08:48.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_UOM_To_ID',Updated=TO_TIMESTAMP('2019-07-29 11:08:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000204
;

-- 2019-07-29T09:09:07.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582446,0,53246,1000024,560297,'F',TO_TIMESTAMP('2019-07-29 11:09:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsCatchUOMForProduct',80,0,0,TO_TIMESTAMP('2019-07-29 11:09:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T09:09:22.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2019-07-29 11:09:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560297
;

-- 2019-07-29T09:10:12.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-07-29 11:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000203
;

-- 2019-07-29T09:10:12.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-07-29 11:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000204
;

-- 2019-07-29T09:10:12.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-07-29 11:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000206
;

-- 2019-07-29T09:10:12.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-07-29 11:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560297
;

-- 2019-07-29T09:12:03.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='C_UOM_Conversion',Updated=TO_TIMESTAMP('2019-07-29 11:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=175
;

-- 2019-07-29T09:12:59.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540498,0,175,TO_TIMESTAMP('2019-07-29 11:12:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','C_UOM_Conversion_IsCatchUOMForProduct_UC','N',TO_TIMESTAMP('2019-07-29 11:12:59','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2019-07-29T09:12:59.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540498 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-07-29T09:13:28.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,1010,540954,540498,0,TO_TIMESTAMP('2019-07-29 11:13:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2019-07-29 11:13:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T09:14:02.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,12866,540955,540498,0,'',TO_TIMESTAMP('2019-07-29 11:14:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2019-07-29 11:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T09:14:44.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET BeforeChangeCode='IsCatchUOMForProduct=''N''', BeforeChangeCodeType='SQLS', WhereClause='IsActive=''Y'' AND IsCatchUOMForProduct=''Y''',Updated=TO_TIMESTAMP('2019-07-29 11:14:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540498
;

-- 2019-07-29T09:14:58.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,568488,540956,540498,0,TO_TIMESTAMP('2019-07-29 11:14:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2019-07-29 11:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T09:14:59.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_UOM_Conversion_IsCatchUOMForProduct_UC ON C_UOM_Conversion (C_UOM_ID,M_Product_ID,IsCatchUOMForProduct) WHERE IsActive='Y' AND IsCatchUOMForProduct='Y'
;

-- 2019-07-29T09:14:59.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION C_UOM_Conversion_IsCatchUOMForProduct_UC_tgfn()
 RETURNS TRIGGER AS $C_UOM_Conversion_IsCatchUOMForProduct_UC_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE C_UOM_Conversion SET IsCatchUOMForProduct='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_UOM_ID=NEW.C_UOM_ID AND M_Product_ID=NEW.M_Product_ID AND IsCatchUOMForProduct=NEW.IsCatchUOMForProduct AND C_UOM_Conversion_ID<>NEW.C_UOM_Conversion_ID AND IsActive='Y' AND IsCatchUOMForProduct='Y';
 ELSE
IF OLD.C_UOM_ID<>NEW.C_UOM_ID OR OLD.M_Product_ID<>NEW.M_Product_ID OR OLD.IsCatchUOMForProduct<>NEW.IsCatchUOMForProduct THEN
UPDATE C_UOM_Conversion SET IsCatchUOMForProduct='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_UOM_ID=NEW.C_UOM_ID AND M_Product_ID=NEW.M_Product_ID AND IsCatchUOMForProduct=NEW.IsCatchUOMForProduct AND C_UOM_Conversion_ID<>NEW.C_UOM_Conversion_ID AND IsActive='Y' AND IsCatchUOMForProduct='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $C_UOM_Conversion_IsCatchUOMForProduct_UC_tg$ LANGUAGE plpgsql;
;

-- 2019-07-29T09:14:59.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS C_UOM_Conversion_IsCatchUOMForProduct_UC_tg ON C_UOM_Conversion
;

-- 2019-07-29T09:14:59.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER C_UOM_Conversion_IsCatchUOMForProduct_UC_tg BEFORE INSERT OR UPDATE  ON C_UOM_Conversion FOR EACH ROW EXECUTE PROCEDURE C_UOM_Conversion_IsCatchUOMForProduct_UC_tgfn()
;

-- 2019-07-29T09:17:41.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET Description='If IsCatchUOMForProduct is set to Y for one record, then it''s automatically set to N for all other recordss that have the same product and (source-)UOM',Updated=TO_TIMESTAMP('2019-07-29 11:17:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540498
;

-- 2019-07-29T09:23:20.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','QtyInPriceUOMVia','VARCHAR(11)',null,'Nominal')
;

-- 2019-07-29T09:23:20.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Invoice_Candidate SET QtyInPriceUOMVia='Nominal' WHERE QtyInPriceUOMVia IS NULL
;


/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine DROP COLUMN IF EXISTS Price_UOM_ID');
;

ALTER TABLE C_Invoice_Candidate RENAME COLUMN QtyInPriceUOMVia TO InvoicableQtyBasedOn;

-- 2019-07-29T09:27:43.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='InvoicableQtyBasedOn',Updated=TO_TIMESTAMP('2019-07-29 11:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948
;

-- 2019-07-29T09:27:43.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.', Help=NULL WHERE AD_Element_ID=576948
;

-- 2019-07-29T09:27:43.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.', Help=NULL, AD_Element_ID=576948 WHERE UPPER(ColumnName)='INVOICABLEQTYBASEDON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T09:27:43.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.', Help=NULL WHERE AD_Element_ID=576948 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:29:52.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Name='Abr. Menge basiert auf', PrintName='Abr. Menge basiert auf',Updated=TO_TIMESTAMP('2019-07-29 11:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='de_CH'
;

-- 2019-07-29T09:29:52.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'de_CH') 
;

-- 2019-07-29T09:29:57.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.',Updated=TO_TIMESTAMP('2019-07-29 11:29:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='de_DE'
;

-- 2019-07-29T09:29:57.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'de_DE') 
;

-- 2019-07-29T09:29:57.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576948,'de_DE') 
;

-- 2019-07-29T09:29:57.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID=576948
;

-- 2019-07-29T09:29:57.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL, AD_Element_ID=576948 WHERE UPPER(ColumnName)='INVOICABLEQTYBASEDON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T09:29:57.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID=576948 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:29:57.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576948) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576948)
;

-- 2019-07-29T09:29:57.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:29:57.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:29:57.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Menge in Preiseinheit durch', Description = 'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:30:02.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abr. Menge basiert auf', PrintName='Abr. Menge basiert auf',Updated=TO_TIMESTAMP('2019-07-29 11:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='de_DE'
;

-- 2019-07-29T09:30:02.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'de_DE') 
;

-- 2019-07-29T09:30:02.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576948,'de_DE') 
;

-- 2019-07-29T09:30:02.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoicableQtyBasedOn', Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID=576948
;

-- 2019-07-29T09:30:02.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL, AD_Element_ID=576948 WHERE UPPER(ColumnName)='INVOICABLEQTYBASEDON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T09:30:02.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID=576948 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:30:02.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576948) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576948)
;

-- 2019-07-29T09:30:02.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abr. Menge basiert auf', Name='Abr. Menge basiert auf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576948)
;

-- 2019-07-29T09:30:02.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:30:02.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:30:02.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abr. Menge basiert auf', Description = 'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:30:16.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoicable Quantity per', PrintName='Invoicable Quantity per',Updated=TO_TIMESTAMP('2019-07-29 11:30:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='en_US'
;

-- 2019-07-29T09:30:16.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'en_US') 
;

-- 2019-07-29T09:30:38.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='InvoicableQtyBasedOn',Updated=TO_TIMESTAMP('2019-07-29 11:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541023
;

-- 2019-07-29T09:31:43.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (17,'Nominal',11,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-29 11:31:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-29 11:31:43','YYYY-MM-DD HH24:MI:SS'),100,'N','N',251,'N',541023,568489,'N','N','N','N','N','N','N','N',0,0,576948,'D','N','N','InvoicableQtyBasedOn','N','Abr. Menge basiert auf','Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.')
;

-- 2019-07-29T09:31:43.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568489 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T09:31:43.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576948) 
;

-- 2019-07-29T09:31:55.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ProductPrice','ALTER TABLE public.M_ProductPrice ADD COLUMN InvoicableQtyBasedOn VARCHAR(11) DEFAULT ''Nominal''')
;

-- 2019-07-29T09:34:15.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568489,582447,0,183,0,TO_TIMESTAMP('2019-07-29 11:34:15','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Abr. Menge basiert auf',130,110,0,1,1,TO_TIMESTAMP('2019-07-29 11:34:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T09:34:15.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582447 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T09:34:15.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576948) 
;

-- 2019-07-29T09:34:15.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582447
;

-- 2019-07-29T09:34:15.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582447)
;

-- 2019-07-29T09:34:53.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582447,0,183,1000018,560298,'F',TO_TIMESTAMP('2019-07-29 11:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'InvoicableQtyBasedOn',65,0,0,TO_TIMESTAMP('2019-07-29 11:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T09:35:08.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-07-29 11:35:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560298
;

-- 2019-07-29T09:35:08.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-07-29 11:35:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000112
;

-- 2019-07-29T09:35:08.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-07-29 11:35:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000109
;

-- 2019-07-29T09:35:28.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Description='', Help='', Name='C_UOM_ID',Updated=TO_TIMESTAMP('2019-07-29 11:35:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000117
;

-- 2019-07-29T09:35:40.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=542464, Description=NULL, Name='Preiseinheit',Updated=TO_TIMESTAMP('2019-07-29 11:35:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582447
;

-- 2019-07-29T09:35:40.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542464) 
;

-- 2019-07-29T09:35:40.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582447
;

-- 2019-07-29T09:35:40.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582447)
;

-- 2019-07-29T09:36:41.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_productprice','InvoicableQtyBasedOn','VARCHAR(11)',null,'Nominal')
;

-- 2019-07-29T09:36:49.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-07-29 11:36:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568489
;

-- 2019-07-29T09:36:54.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_productprice','InvoicableQtyBasedOn','VARCHAR(11)',null,'Nominal')
;

-- 2019-07-29T09:36:54.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_ProductPrice SET InvoicableQtyBasedOn='Nominal' WHERE InvoicableQtyBasedOn IS NULL
;

-- 2019-07-29T09:36:54.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_productprice','InvoicableQtyBasedOn',null,'NOT NULL',null)
;

-- 2019-07-29T09:37:43.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','InvoicableQtyBasedOn','VARCHAR(11)',null,'Nominal')
;

-- 2019-07-29T09:37:43.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Invoice_Candidate SET InvoicableQtyBasedOn='Nominal' WHERE InvoicableQtyBasedOn IS NULL
;

-- 2019-07-29T09:49:57.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Name='Abr. Menge basiert auf',Updated=TO_TIMESTAMP('2019-07-29 11:49:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582447
;

-- 2019-07-29T09:49:57.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576948) 
;

-- 2019-07-29T09:49:57.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582447
;

-- 2019-07-29T09:49:57.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582447)
;

-- 2019-07-29T09:50:06.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=542464, Description=NULL, Help=NULL, Name='Preiseinheit',Updated=TO_TIMESTAMP('2019-07-29 11:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554663
;

-- 2019-07-29T09:50:06.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542464) 
;

-- 2019-07-29T09:50:06.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554663
;

-- 2019-07-29T09:50:06.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(554663)
;

-- 2019-07-29T09:51:42.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit für die der jeweilige Preis gilt.',Updated=TO_TIMESTAMP('2019-07-29 11:51:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542464 AND AD_Language='de_DE'
;

-- 2019-07-29T09:51:42.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542464,'de_DE') 
;

-- 2019-07-29T09:51:42.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542464,'de_DE') 
;

-- 2019-07-29T09:51:42.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Preiseinheit', Description='Maßeinheit für die der jeweilige Preis gilt.', Help=NULL WHERE AD_Element_ID=542464
;

-- 2019-07-29T09:51:42.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Preiseinheit', Description='Maßeinheit für die der jeweilige Preis gilt.', Help=NULL WHERE AD_Element_ID=542464 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:51:42.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preiseinheit', Description='Maßeinheit für die der jeweilige Preis gilt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542464) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542464)
;

-- 2019-07-29T09:51:42.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preiseinheit', Description='Maßeinheit für die der jeweilige Preis gilt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542464
;

-- 2019-07-29T09:51:42.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preiseinheit', Description='Maßeinheit für die der jeweilige Preis gilt.', Help=NULL WHERE AD_Element_ID = 542464
;

-- 2019-07-29T09:51:42.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Preiseinheit', Description = 'Maßeinheit für die der jeweilige Preis gilt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542464
;

-- 2019-07-29T09:51:44.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 11:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542464 AND AD_Language='de_DE'
;

-- 2019-07-29T09:51:44.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542464,'de_DE') 
;

-- 2019-07-29T09:51:44.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542464,'de_DE') 
;

-- 2019-07-29T09:52:08.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit auf die sich der Preis bezieht',Updated=TO_TIMESTAMP('2019-07-29 11:52:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542464 AND AD_Language='de_DE'
;

-- 2019-07-29T09:52:08.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542464,'de_DE') 
;

-- 2019-07-29T09:52:08.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542464,'de_DE') 
;

-- 2019-07-29T09:52:08.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Preiseinheit', Description='Maßeinheit auf die sich der Preis bezieht', Help=NULL WHERE AD_Element_ID=542464
;

-- 2019-07-29T09:52:08.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Preiseinheit', Description='Maßeinheit auf die sich der Preis bezieht', Help=NULL WHERE AD_Element_ID=542464 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:52:08.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preiseinheit', Description='Maßeinheit auf die sich der Preis bezieht', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542464) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542464)
;

-- 2019-07-29T09:52:08.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preiseinheit', Description='Maßeinheit auf die sich der Preis bezieht', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542464
;

-- 2019-07-29T09:52:08.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preiseinheit', Description='Maßeinheit auf die sich der Preis bezieht', Help=NULL WHERE AD_Element_ID = 542464
;

-- 2019-07-29T09:52:08.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Preiseinheit', Description = 'Maßeinheit auf die sich der Preis bezieht', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542464
;

-- 2019-07-29T09:52:12.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit auf die sich der Preis bezieht', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 11:52:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542464 AND AD_Language='de_CH'
;

-- 2019-07-29T09:52:12.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542464,'de_CH') 
;

ALTER TABLE M_InOutLine DROP COLUMN IF EXISTS price_uom_id;
ALTER TABLE M_InOutLine DROP COLUMN IF EXISTS qtydeliveredinpriceuom_catchweight;
ALTER TABLE M_InOutLine DROP COLUMN IF EXISTS CatchWeight_UOM_ID;

-- 2019-07-29T09:54:09.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Catch_UOM_ID',Updated=TO_TIMESTAMP('2019-07-29 11:54:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576953
;

-- 2019-07-29T09:54:09.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Catch_UOM_ID', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576953
;

-- 2019-07-29T09:54:09.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Catch_UOM_ID', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL, AD_Element_ID=576953 WHERE UPPER(ColumnName)='CATCH_UOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T09:54:09.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Catch_UOM_ID', Name='Catch Weight Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576953 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:54:21.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN Catch_UOM_ID NUMERIC(10)')
;

-- 2019-07-29T09:54:21.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_InOutLine ADD CONSTRAINT CatchUOM_MInOutLine FOREIGN KEY (Catch_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2019-07-29T09:54:57.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN QtyDeliveredInCatchUOM NUMERIC')
--;

