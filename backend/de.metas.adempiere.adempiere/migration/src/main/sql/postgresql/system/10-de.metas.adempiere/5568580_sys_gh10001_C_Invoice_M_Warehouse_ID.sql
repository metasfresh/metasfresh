-- 2020-09-23T14:48:36.932Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571520,459,0,30,318,'M_Warehouse_ID',TO_TIMESTAMP('2020-09-23 17:48:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Lager oder Ort für Dienstleistung','D',0,10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lager',0,0,TO_TIMESTAMP('2020-09-23 17:48:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-23T14:48:37.183Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=571520 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-23T14:48:37.220Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(459) 
;

-- 2020-09-23T14:48:46.932Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN M_Warehouse_ID NUMERIC(10)')
;

-- 2020-09-23T14:48:48.093Z
-- URL zum Konzept
ALTER TABLE C_Invoice ADD CONSTRAINT MWarehouse_CInvoice FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
;

-- 2020-09-23T15:29:14.755Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2020-09-23 18:29:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571520
;

-- 2020-09-23T15:29:20.472Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_invoice','M_Warehouse_ID','NUMERIC(10)',null,null)
;






-- 2020-09-23T20:56:03.154Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578120,0,TO_TIMESTAMP('2020-09-23 23:56:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Warehouse_ID','M_Warehouse_ID',TO_TIMESTAMP('2020-09-23 23:56:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-23T20:56:03.227Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578120 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-23T20:56:31.997Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.',Updated=TO_TIMESTAMP('2020-09-23 23:56:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578120 AND AD_Language='de_DE'
;

-- 2020-09-23T20:56:32.054Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578120,'de_DE') 
;

-- 2020-09-23T20:56:32.158Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578120,'de_DE') 
;

-- 2020-09-23T20:56:32.193Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='M_Warehouse_ID', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL WHERE AD_Element_ID=578120
;

-- 2020-09-23T20:56:32.227Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='M_Warehouse_ID', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL WHERE AD_Element_ID=578120 AND IsCentrallyMaintained='Y'
;

-- 2020-09-23T20:56:32.261Z
-- URL zum Konzept
UPDATE AD_Field SET Name='M_Warehouse_ID', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578120) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578120)
;

-- 2020-09-23T20:56:32.460Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='M_Warehouse_ID', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578120
;

-- 2020-09-23T20:56:32.494Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='M_Warehouse_ID', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL WHERE AD_Element_ID = 578120
;

-- 2020-09-23T20:56:32.527Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'M_Warehouse_ID', Description = 'Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578120
;

-- 2020-09-23T20:56:34.662Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-23 23:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578120 AND AD_Language='de_DE'
;

-- 2020-09-23T20:56:34.697Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578120,'de_DE') 
;

-- 2020-09-23T20:56:34.780Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578120,'de_DE') 
;

-- 2020-09-23T20:56:40.931Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-23 23:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578120 AND AD_Language='de_CH'
;

-- 2020-09-23T20:56:40.968Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578120,'de_CH') 
;

-- 2020-09-23T20:57:02.372Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If this field is set, the country of the warehouse is considered when calculating the tax. If it''s not set, he tax will be calculated based on the organization''s country or the country of the warehouse from the shipment.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-23 23:57:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578120 AND AD_Language='en_US'
;

-- 2020-09-23T20:57:02.405Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578120,'en_US') 
;




-- 2020-09-23T21:01:35.531Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571520,617692,0,263,0,TO_TIMESTAMP('2020-09-24 00:01:35','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',0,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','Y','Y','N','N','N','N','N','Lager',470,490,0,1,1,TO_TIMESTAMP('2020-09-24 00:01:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-23T21:01:35.604Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=617692 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-23T21:01:35.654Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2020-09-23T21:01:35.700Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=617692
;

-- 2020-09-23T21:01:35.732Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(617692)
;

-- 2020-09-23T21:01:50.134Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=578120, Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL, Name='M_Warehouse_ID',Updated=TO_TIMESTAMP('2020-09-24 00:01:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=617692
;

-- 2020-09-23T21:01:50.199Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578120) 
;

-- 2020-09-23T21:01:50.234Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=617692
;

-- 2020-09-23T21:01:50.265Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(617692)
;

-- 2020-09-23T21:03:07.621Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,617692,0,263,541214,571535,'F',TO_TIMESTAMP('2020-09-24 00:03:07','YYYY-MM-DD HH24:MI:SS'),100,'Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.','Y','Y','N','Y','N','N','N',0,'M_Warehouse_ID',55,0,0,TO_TIMESTAMP('2020-09-24 00:03:07','YYYY-MM-DD HH24:MI:SS'),100)
;






-- 2020-09-23T21:04:09.810Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Lager', PrintName='Lager',Updated=TO_TIMESTAMP('2020-09-24 00:04:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578120 AND AD_Language='de_DE'
;

-- 2020-09-23T21:04:09.846Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578120,'de_DE') 
;

-- 2020-09-23T21:04:09.955Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578120,'de_DE') 
;

-- 2020-09-23T21:04:09.990Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Lager', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL WHERE AD_Element_ID=578120
;

-- 2020-09-23T21:04:10.023Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Lager', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL WHERE AD_Element_ID=578120 AND IsCentrallyMaintained='Y'
;

-- 2020-09-23T21:04:10.057Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Lager', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578120) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578120)
;

-- 2020-09-23T21:04:10.110Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lager', Name='Lager' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578120)
;

-- 2020-09-23T21:04:10.145Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Lager', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578120
;

-- 2020-09-23T21:04:10.181Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Lager', Description='Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', Help=NULL WHERE AD_Element_ID = 578120
;

-- 2020-09-23T21:04:10.215Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Lager', Description = 'Falls angegeben wird das Land des Lagers als "Quell-Land" für die Steuerberechnung benutzt. Ansonsten wird hierfür das Land der jeweiligen Organisation oder das der jeweiligen Lieferung benutzt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578120
;

-- 2020-09-23T21:04:19.549Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Warehouse', PrintName='Warehouse',Updated=TO_TIMESTAMP('2020-09-24 00:04:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578120 AND AD_Language='en_US'
;

-- 2020-09-23T21:04:19.585Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578120,'en_US') 
;

-- 2020-09-23T21:04:27.665Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Lager', PrintName='Lager',Updated=TO_TIMESTAMP('2020-09-24 00:04:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578120 AND AD_Language='de_CH'
;

-- 2020-09-23T21:04:27.701Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578120,'de_CH') 
;







