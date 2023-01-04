-- 2022-08-24T13:29:50.886Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581377,0,'BPartnerInvoicesWithVendors',TO_TIMESTAMP('2022-08-24 16:29:50','YYYY-MM-DD HH24:MI:SS'),100,'If ticked and the warehouse belongs to a third-party business-partner, then this partner is assumed to settle accounts with vendors that deliver to this warehouse. As a consequence, when this warehouse is selected in a purchase order, the warehouse-partner and not the actual vendor is set to be the order''s bill-partner.','D','Y','Invoices with suppliers','Invoices with suppliers',TO_TIMESTAMP('2022-08-24 16:29:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T13:29:50.894Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581377 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-24T13:30:38.544Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnet mit Lieferanten ab', PrintName='Rechnet mit Lieferanten ab',Updated=TO_TIMESTAMP('2022-08-24 16:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='de_CH'
;

-- 2022-08-24T13:30:38.573Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'de_CH') 
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-24T13:31:13.612Z
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Rechnet mit Lieferanten ab', PrintName='Rechnet mit Lieferanten ab',Updated=TO_TIMESTAMP('2022-08-24 16:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='de_DE'
;

-- 2022-08-24T13:31:13.613Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'de_DE') 
;

-- 2022-08-24T13:31:13.619Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581377,'de_DE') 
;

-- 2022-08-24T13:31:13.621Z
UPDATE AD_Column SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='', Help=NULL WHERE AD_Element_ID=581377
;

-- 2022-08-24T13:31:13.623Z
UPDATE AD_Process_Para SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='', Help=NULL, AD_Element_ID=581377 WHERE UPPER(ColumnName)='BPARTNERINVOICESWITHVENDORS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-24T13:31:13.623Z
UPDATE AD_Process_Para SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='', Help=NULL WHERE AD_Element_ID=581377 AND IsCentrallyMaintained='Y'
;

-- 2022-08-24T13:31:13.623Z
UPDATE AD_Field SET Name='Rechnet mit Lieferanten ab', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581377) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581377)
;

-- 2022-08-24T13:31:13.632Z
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnet mit Lieferanten ab', Name='Rechnet mit Lieferanten ab' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581377)
;

-- 2022-08-24T13:31:13.633Z
UPDATE AD_Tab SET Name='Rechnet mit Lieferanten ab', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581377
;

-- 2022-08-24T13:31:13.634Z
UPDATE AD_WINDOW SET Name='Rechnet mit Lieferanten ab', Description='', Help=NULL WHERE AD_Element_ID = 581377
;

-- 2022-08-24T13:31:13.635Z
UPDATE AD_Menu SET   Name = 'Rechnet mit Lieferanten ab', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581377
;

-- Column: M_Warehouse.BPartnerInvoicesWithVendors
-- 2022-08-24T13:33:44.311Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584207,581377,0,20,190,'BPartnerInvoicesWithVendors',TO_TIMESTAMP('2022-08-24 16:33:44','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Rechnet mit Lieferanten ab',0,0,TO_TIMESTAMP('2022-08-24 16:33:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T13:33:44.313Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584207 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T13:33:44.318Z
/* DDL */  select update_Column_Translation_From_AD_Element(581377) 
;

-- Field: Lager und Lagerort(139,D) -> Lager(177,D) -> Rechnet mit Lieferanten ab
-- Column: M_Warehouse.BPartnerInvoicesWithVendors
-- 2022-08-24T13:42:17.935Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584207,705520,0,177,0,TO_TIMESTAMP('2022-08-24 16:42:17','YYYY-MM-DD HH24:MI:SS'),100,'',0,'D',0,'Y','Y','Y','N','N','N','N','N','Rechnet mit Lieferanten ab',0,200,0,1,1,TO_TIMESTAMP('2022-08-24 16:42:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T13:42:17.938Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705520 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T13:42:17.942Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581377) 
;

-- 2022-08-24T13:42:17.956Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705520
;

-- 2022-08-24T13:42:17.960Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705520)
;

-- UI Element: Lager und Lagerort(139,D) -> Lager(177,D) -> main -> 20 -> flags.Rechnet mit Lieferanten ab
-- Column: M_Warehouse.BPartnerInvoicesWithVendors
-- 2022-08-24T13:43:50.473Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705520,0,177,612200,540174,'F',TO_TIMESTAMP('2022-08-24 16:43:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnet mit Lieferanten ab',100,0,0,TO_TIMESTAMP('2022-08-24 16:43:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T13:57:17.634Z
/* DDL */ SELECT public.db_alter_table('M_Warehouse','ALTER TABLE public.M_Warehouse ADD COLUMN BPartnerInvoicesWithVendors CHAR(1) DEFAULT ''N'' CHECK (BPartnerInvoicesWithVendors IN (''Y'',''N'')) NOT NULL')
;

-- 2022-08-24T15:38:57.472Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581380,0,'isExternalBPartner',TO_TIMESTAMP('2022-08-24 18:38:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','External BPartner','External BPartner',TO_TIMESTAMP('2022-08-24 18:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T15:38:57.480Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581380 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Warehouse.isExternalBPartner
-- 2022-08-24T15:43:35.400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584220,581380,0,20,190,'isExternalBPartner','(SELECT NOT EXISTS(SELECT 1 from ad_orginfo where ad_orginfo.org_bpartner_id = m_warehouse.c_bpartner_id AND ad_orginfo.ad_org_id = m_warehouse.ad_org_id))',TO_TIMESTAMP('2022-08-24 18:43:35','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.inout',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'External BPartner',0,0,TO_TIMESTAMP('2022-08-24 18:43:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T15:43:35.406Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584220 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T15:43:35.417Z
/* DDL */  select update_Column_Translation_From_AD_Element(581380) 
;

-- 2022-08-24T15:44:07.385Z
UPDATE AD_Element SET ColumnName='IsExternalBPartner',Updated=TO_TIMESTAMP('2022-08-24 18:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581380
;

-- 2022-08-24T15:44:07.393Z
UPDATE AD_Column SET ColumnName='IsExternalBPartner', Name='External BPartner', Description=NULL, Help=NULL WHERE AD_Element_ID=581380
;

-- 2022-08-24T15:44:07.394Z
UPDATE AD_Process_Para SET ColumnName='IsExternalBPartner', Name='External BPartner', Description=NULL, Help=NULL, AD_Element_ID=581380 WHERE UPPER(ColumnName)='ISEXTERNALBPARTNER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-24T15:44:07.395Z
UPDATE AD_Process_Para SET ColumnName='IsExternalBPartner', Name='External BPartner', Description=NULL, Help=NULL WHERE AD_Element_ID=581380 AND IsCentrallyMaintained='Y'
;

-- Field: Lager und Lagerort(139,D) -> Lager(177,D) -> Rechnet mit Lieferanten ab
-- Column: M_Warehouse.BPartnerInvoicesWithVendors
-- 2022-08-24T15:46:50.597Z
UPDATE AD_Field SET DisplayLogic='@IsExternalBPartner/N@=Y',Updated=TO_TIMESTAMP('2022-08-24 18:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705520
;

-- Field: Lager und Lagerort(139,D) -> Lager(177,D) -> External BPartner
-- Column: M_Warehouse.IsExternalBPartner
-- 2022-08-24T15:52:09.852Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584220,705529,0,177,0,TO_TIMESTAMP('2022-08-24 18:52:09','YYYY-MM-DD HH24:MI:SS'),100,0,'1=0','de.metas.inout',0,'Y','Y','Y','N','N','N','N','N','External BPartner',0,210,0,1,1,TO_TIMESTAMP('2022-08-24 18:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T15:52:09.858Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705529 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T15:52:09.862Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581380) 
;

-- 2022-08-24T15:52:09.865Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705529
;

-- 2022-08-24T15:52:09.873Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705529)
;

-- UI Element: Lager und Lagerort(139,D) -> Lager(177,D) -> main -> 20 -> flags.External BPartner
-- Column: M_Warehouse.IsExternalBPartner
-- 2022-08-24T15:54:03.892Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705529,0,177,612204,540174,'F',TO_TIMESTAMP('2022-08-24 18:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External BPartner',110,0,0,TO_TIMESTAMP('2022-08-24 18:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Lager und Lagerort(139,D) -> Lager(177,D) -> External BPartner
-- Column: M_Warehouse.IsExternalBPartner
-- 2022-08-24T15:54:46.453Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2022-08-24 18:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705529
;

-- Column: M_Warehouse.IsExternalBPartner
-- 2022-08-24T15:55:49.137Z
UPDATE AD_Column SET IsLazyLoading='N',Updated=TO_TIMESTAMP('2022-08-24 18:55:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584220
;

-- Column: M_Warehouse.IsExternalBPartner
-- Column SQL (old): (SELECT NOT EXISTS(SELECT 1 from ad_orginfo where ad_orginfo.org_bpartner_id = m_warehouse.c_bpartner_id AND ad_orginfo.ad_org_id = m_warehouse.ad_org_id))
-- 2022-08-24T16:00:02.556Z
UPDATE AD_Column SET ColumnSQL='(case when (SELECT NOT EXISTS(SELECT 1 from ad_orginfo where ad_orginfo.org_bpartner_id = m_warehouse.c_bpartner_id AND ad_orginfo.ad_org_id = m_warehouse.ad_org_id)) then ''Y'' else ''N'' end)', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-08-24 19:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584220
;

-- UI Element: Lager und Lagerort(139,D) -> Lager(177,D) -> main -> 20 -> flags.External BPartner
-- Column: M_Warehouse.IsExternalBPartner
-- 2022-08-24T16:00:56.758Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-08-24 19:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612204
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T15:29:52.813Z
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 18:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='en_US'
;

-- 2022-08-25T15:29:52.841Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'en_US')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T15:30:44.106Z
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 18:30:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='fr_CH'
;

-- 2022-08-25T15:30:44.108Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'fr_CH')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T15:45:31.476Z
UPDATE AD_Element_Trl SET Description='', Help='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.',Updated=TO_TIMESTAMP('2022-08-25 18:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='de_CH'
;

-- 2022-08-25T15:45:31.477Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'de_CH')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T15:45:38.909Z
UPDATE AD_Element_Trl SET Help='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.',Updated=TO_TIMESTAMP('2022-08-25 18:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='de_DE'
;

-- 2022-08-25T15:45:38.912Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'de_DE')
;

-- 2022-08-25T15:45:38.917Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581377,'de_DE')
;

-- 2022-08-25T15:45:38.918Z
UPDATE AD_Column SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='', Help='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE AD_Element_ID=581377
;

-- 2022-08-25T15:45:38.920Z
UPDATE AD_Process_Para SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='', Help='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.', AD_Element_ID=581377 WHERE UPPER(ColumnName)='BPARTNERINVOICESWITHVENDORS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T15:45:38.922Z
UPDATE AD_Process_Para SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='', Help='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE AD_Element_ID=581377 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T15:45:38.922Z
UPDATE AD_Field SET Name='Rechnet mit Lieferanten ab', Description='', Help='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581377) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581377)
;

-- 2022-08-25T15:45:38.976Z
UPDATE AD_Tab SET Name='Rechnet mit Lieferanten ab', Description='', Help='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.', CommitWarning = NULL WHERE AD_Element_ID = 581377
;

-- 2022-08-25T15:45:38.977Z
UPDATE AD_WINDOW SET Name='Rechnet mit Lieferanten ab', Description='', Help='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE AD_Element_ID = 581377
;

-- 2022-08-25T15:45:38.978Z
UPDATE AD_Menu SET   Name = 'Rechnet mit Lieferanten ab', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581377
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T15:51:19.457Z
UPDATE AD_Element_Trl SET Description='Wenn gesetzt ', Help='Wenn Y  und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.',Updated=TO_TIMESTAMP('2022-08-25 18:51:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='de_DE'
;

-- 2022-08-25T15:51:19.460Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'de_DE')
;

-- 2022-08-25T15:51:19.465Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581377,'de_DE')
;

-- 2022-08-25T15:51:19.467Z
UPDATE AD_Column SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='Wenn gesetzt ', Help='Wenn Y  und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE AD_Element_ID=581377
;

-- 2022-08-25T15:51:19.468Z
UPDATE AD_Process_Para SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='Wenn gesetzt ', Help='Wenn Y  und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.', AD_Element_ID=581377 WHERE UPPER(ColumnName)='BPARTNERINVOICESWITHVENDORS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T15:51:19.470Z
UPDATE AD_Process_Para SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='Wenn gesetzt ', Help='Wenn Y  und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE AD_Element_ID=581377 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T15:51:19.470Z
UPDATE AD_Field SET Name='Rechnet mit Lieferanten ab', Description='Wenn gesetzt ', Help='Wenn Y  und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581377) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581377)
;

-- 2022-08-25T15:51:19.481Z
UPDATE AD_Tab SET Name='Rechnet mit Lieferanten ab', Description='Wenn gesetzt ', Help='Wenn Y  und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.', CommitWarning = NULL WHERE AD_Element_ID = 581377
;

-- 2022-08-25T15:51:19.481Z
UPDATE AD_WINDOW SET Name='Rechnet mit Lieferanten ab', Description='Wenn gesetzt ', Help='Wenn Y  und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE AD_Element_ID = 581377
;

-- 2022-08-25T15:51:19.482Z
UPDATE AD_Menu SET   Name = 'Rechnet mit Lieferanten ab', Description = 'Wenn gesetzt ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581377
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T15:51:28.648Z
UPDATE AD_Element_Trl SET Description='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.',Updated=TO_TIMESTAMP('2022-08-25 18:51:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='de_DE'
;

-- 2022-08-25T15:51:28.649Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'de_DE')
;

-- 2022-08-25T15:51:28.656Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581377,'de_DE')
;

-- 2022-08-25T15:51:28.657Z
UPDATE AD_Column SET ColumnName='BPartnerInvoicesWithVendors', Name='Rechnet mit Lieferanten ab', Description='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.', Help='Wenn Y  und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE AD_Element_ID=581377
;


-- 2022-08-25T15:51:28.659Z
UPDATE AD_Field SET Name='Rechnet mit Lieferanten ab', Description='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.', Help='Wenn Y  und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.
Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581377) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581377)
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T15:51:50.776Z
UPDATE AD_Element_Trl SET Description='Wenn gesetzt und das Lager zu einem externen Geschäftspartner gehört, wird davon ausgegangen, dass dieser Partner mit den Lieferanten, die an dieses Lager liefern, direkt abrechnet.Wenn dieses Lager in einer Bestellung ausgewählt wird, wird folglich der Lagerpartner und nicht der eigentliche Lieferant als Rechnungspartner der Bestellung festgelegt.',Updated=TO_TIMESTAMP('2022-08-25 18:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='de_CH'
;

-- 2022-08-25T15:51:50.777Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'de_CH')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T16:08:28.205Z
UPDATE AD_Element_Trl SET Description='If ticked and the warehouse belongs to a third-party business-partner, then this partner is assumed to settle accounts with vendors that deliver to this warehouse. As a consequence, when this warehouse is selected in a purchase order, the warehouse-partner and not the actual vendor is set to be the order''s bill-partner.',Updated=TO_TIMESTAMP('2022-08-25 19:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='en_US'
;

-- 2022-08-25T16:08:28.207Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'en_US')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T16:08:53.247Z
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2022-08-25 19:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='nl_NL'
;

-- 2022-08-25T16:08:53.247Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'nl_NL')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T16:09:21.005Z
UPDATE AD_Element_Trl SET Description='If ticked and the warehouse belongs to a third-party business-partner, then this partner is assumed to settle accounts with vendors that deliver to this warehouse. As a consequence, when this warehouse is selected in a purchase order, the warehouse-partner and not the actual vendor is set to be the order''s bill-partner.',Updated=TO_TIMESTAMP('2022-08-25 19:09:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='fr_CH'
;

-- 2022-08-25T16:09:21.005Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'fr_CH')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T16:09:23.700Z
UPDATE AD_Element_Trl SET Description='If ticked and the warehouse belongs to a third-party business-partner, then this partner is assumed to settle accounts with vendors that deliver to this warehouse. As a consequence, when this warehouse is selected in a purchase order, the warehouse-partner and not the actual vendor is set to be the order''s bill-partner.',Updated=TO_TIMESTAMP('2022-08-25 19:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='nl_NL'
;

-- 2022-08-25T16:09:23.701Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'nl_NL')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T16:22:26.675Z
UPDATE AD_Element_Trl SET Help='If ticked and the warehouse belongs to a third-party business-partner, then this partner is assumed to settle accounts with vendors that deliver to this warehouse. As a consequence, when this warehouse is selected in a purchase order, the warehouse-partner and not the actual vendor is set to be the order''s bill-partner.
',Updated=TO_TIMESTAMP('2022-08-25 19:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='en_US'
;

-- 2022-08-25T16:22:26.676Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'en_US')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T16:22:37.638Z
UPDATE AD_Element_Trl SET Help='If ticked and the warehouse belongs to a third-party business-partner, then this partner is assumed to settle accounts with vendors that deliver to this warehouse. As a consequence, when this warehouse is selected in a purchase order, the warehouse-partner and not the actual vendor is set to be the order''s bill-partner.',Updated=TO_TIMESTAMP('2022-08-25 19:22:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='fr_CH'
;

-- 2022-08-25T16:22:37.639Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'fr_CH')
;

-- Element: BPartnerInvoicesWithVendors
-- 2022-08-25T16:22:47.420Z
UPDATE AD_Element_Trl SET Help='If ticked and the warehouse belongs to a third-party business-partner, then this partner is assumed to settle accounts with vendors that deliver to this warehouse. As a consequence, when this warehouse is selected in a purchase order, the warehouse-partner and not the actual vendor is set to be the order''s bill-partner.',Updated=TO_TIMESTAMP('2022-08-25 19:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581377 AND AD_Language='nl_NL'
;

-- 2022-08-25T16:22:47.421Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581377,'nl_NL')
;

