-- 2021-10-29T05:59:42.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544444, AD_Reference_Value_ID=540499, ColumnName='PickFrom_HU_ID', Description=NULL, Help=NULL, IsExcludeFromZoomTargets='Y', Name='Pick From HU',Updated=TO_TIMESTAMP('2021-10-29 08:59:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577771
;

-- 2021-10-29T05:59:42.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pick From HU', Description=NULL, Help=NULL WHERE AD_Column_ID=577771
;

-- 2021-10-29T05:59:42.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(544444) 
;

alter table m_picking_job_step rename column m_hu_id to PickFrom_HU_ID;

-- 2021-10-29T06:01:41.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=580106, AD_Reference_ID=30, AD_Reference_Value_ID=191, ColumnName='PickFrom_Locator_ID', Description=NULL, Help=NULL, Name='Pick From Locator',Updated=TO_TIMESTAMP('2021-10-29 09:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577773
;

-- 2021-10-29T06:01:41.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pick From Locator', Description=NULL, Help=NULL WHERE AD_Column_ID=577773
;

-- 2021-10-29T06:01:41.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580106) 
;

-- 2021-10-29T06:02:31.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=580105, AD_Reference_Value_ID=540420, ColumnName='PickFrom_Warehouse_ID', Description=NULL, Help=NULL, IsExcludeFromZoomTargets='Y', Name='Pick From Warehouse',Updated=TO_TIMESTAMP('2021-10-29 09:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577772
;

-- 2021-10-29T06:02:31.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pick From Warehouse', Description=NULL, Help=NULL WHERE AD_Column_ID=577772
;

-- 2021-10-29T06:02:31.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580105) 
;

alter table m_picking_job_step rename column m_locator_id to PickFrom_Locator_ID;
alter table m_picking_job_step rename column m_warehouse_id to PickFrom_Warehouse_ID;


