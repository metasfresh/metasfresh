-- Run mode: SWING_CLIENT

-- 2026-02-05T15:10:15.478Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584498,0,'IsAllowDifferenShipmentInSameInvoiceLine',TO_TIMESTAMP('2026-02-05 15:10:15.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','de.metas.invoicecandidate','Wenn aktiviert, können Rechnungskandidaten aus unterschiedlichen Lieferungen in derselben Rechnungszeile zusammengefasst werden.
Wenn deaktiviert, wird für jede Lieferung eine separate Rechnungszeile erstellt.','Y','Versch. Lieferungen in selber Rechn.-Pos.','Versch. Lieferungen in selber Rechn.-Pos.',TO_TIMESTAMP('2026-02-05 15:10:15.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T15:10:15.492Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584498 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAllowDifferenShipmentInSameInvoiceLine
-- 2026-02-05T15:10:20.743Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-05 15:10:20.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584498 AND AD_Language='de_CH'
;

-- 2026-02-05T15:10:20.791Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584498,'de_CH')
;

-- Element: IsAllowDifferenShipmentInSameInvoiceLine
-- 2026-02-05T15:10:23.437Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-05 15:10:23.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584498 AND AD_Language='de_DE'
;

-- 2026-02-05T15:10:23.450Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584498,'de_DE')
;

-- 2026-02-05T15:10:23.451Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584498,'de_DE')
;

-- Element: IsAllowDifferenShipmentInSameInvoiceLine
-- 2026-02-05T15:11:46.233Z
UPDATE AD_Element_Trl SET Help='Wenn aktiviert, unterschiedlichen Lieferungen in derselben Rechnungszeile zusammengefasst werden.
Wenn deaktiviert, wird für jede Lieferung eine separate Rechnungszeile erstellt.', IsTranslated='Y', Name='Multiple Shipments per Invoice Line', PrintName='Multiple Shipments per Invoice Line',Updated=TO_TIMESTAMP('2026-02-05 15:11:46.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584498 AND AD_Language='en_US'
;

-- 2026-02-05T15:11:46.235Z
UPDATE AD_Element base SET Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-05T15:11:46.563Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584498,'en_US')
;

-- Element: IsAllowDifferenShipmentInSameInvoiceLine
-- 2026-02-05T15:13:21.832Z
UPDATE AD_Element_Trl SET Help='Wenn aktiviert, können unterschiedliche Lieferungen in derselben Rechnungszeile zusammengefasst werden.
Wenn deaktiviert, wird für jede Lieferung eine separate Rechnungszeile erstellt.',Updated=TO_TIMESTAMP('2026-02-05 15:13:21.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584498 AND AD_Language='de_CH'
;

-- 2026-02-05T15:13:21.843Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-05T15:13:22.133Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584498,'de_CH')
;

-- Element: IsAllowDifferenShipmentInSameInvoiceLine
-- 2026-02-05T15:14:16.279Z
UPDATE AD_Element_Trl SET Description='Erlaubt Lieferscheinzeilen aus mehreren Lieferungen in einer Rechnungszeile.', Help='Wenn aktiviert, kann ein Rechnungskandidat, der Lieferscheinzeilen aus mehreren Lieferungen referenziert, eine einzelne Rechnungszeile erzeugen.
Wenn deaktiviert, wird der Rechnungskandidat je Lieferung immer auf separate Rechnungszeilen aufgeteilt.',Updated=TO_TIMESTAMP('2026-02-05 15:14:16.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584498 AND AD_Language='de_CH'
;

-- 2026-02-05T15:14:16.290Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-05T15:14:16.618Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584498,'de_CH')
;

-- Element: IsAllowDifferenShipmentInSameInvoiceLine
-- 2026-02-05T15:14:35.171Z
UPDATE AD_Element_Trl SET Description='Erlaubt Lieferscheinzeilen aus mehreren Lieferungen in einer Rechnungszeile.', Help='Wenn aktiviert, kann ein Rechnungskandidat, der Lieferscheinzeilen aus mehreren Lieferungen referenziert, eine einzelne Rechnungszeile erzeugen.
Wenn deaktiviert, wird der Rechnungskandidat je Lieferung immer auf separate Rechnungszeilen aufgeteilt.',Updated=TO_TIMESTAMP('2026-02-05 15:14:35.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584498 AND AD_Language='de_DE'
;

-- 2026-02-05T15:14:35.172Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-05T15:14:36.180Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584498,'de_DE')
;

-- 2026-02-05T15:14:36.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584498,'de_DE')
;

-- Element: IsAllowDifferenShipmentInSameInvoiceLine
-- 2026-02-05T15:15:13.299Z
UPDATE AD_Element_Trl SET Description='Allow shipment lines from multiple shipments in one invoice line.', Help='If enabled, an invoice candidate that references shipment lines from multiple shipments may create a single invoice line. 
If disabled, the invoice candidate is always split into separate invoice lines, one per shipment.',Updated=TO_TIMESTAMP('2026-02-05 15:15:13.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584498 AND AD_Language='en_US'
;

-- 2026-02-05T15:15:13.300Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-05T15:15:13.598Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584498,'en_US')
;

-- 2026-02-05T15:15:28.367Z
UPDATE AD_Element SET ColumnName='IsAllowDifferentShipmentsInSameInvoiceLine',Updated=TO_TIMESTAMP('2026-02-05 15:15:28.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584498
;

-- 2026-02-05T15:15:28.368Z
UPDATE AD_Column SET ColumnName='IsAllowDifferentShipmentsInSameInvoiceLine' WHERE AD_Element_ID=584498
;

-- 2026-02-05T15:15:28.369Z
UPDATE AD_Process_Para SET ColumnName='IsAllowDifferentShipmentsInSameInvoiceLine' WHERE AD_Element_ID=584498
;

-- 2026-02-05T15:15:28.372Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584498,'de_DE')
;

-- Column: C_ILCandHandler.IsAllowDifferentShipmentsInSameInvoiceLine
-- 2026-02-05T15:16:11.128Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,591952,584498,0,20,540340,'XX','IsAllowDifferentShipmentsInSameInvoiceLine',TO_TIMESTAMP('2026-02-05 15:16:10.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Erlaubt Lieferscheinzeilen aus mehreren Lieferungen in einer Rechnungszeile.','de.metas.invoicecandidate',0,1,'Wenn aktiviert, kann ein Rechnungskandidat, der Lieferscheinzeilen aus mehreren Lieferungen referenziert, eine einzelne Rechnungszeile erzeugen.
Wenn deaktiviert, wird der Rechnungskandidat je Lieferung immer auf separate Rechnungszeilen aufgeteilt.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Versch. Lieferungen in selber Rechn.-Pos.',0,0,'No for backwards-compatibility',TO_TIMESTAMP('2026-02-05 15:16:10.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-05T15:16:11.132Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591952 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-05T15:16:11.136Z
/* DDL */  select update_Column_Translation_From_AD_Element(584498)
;

-- 2026-02-07T19:20:22.358Z
/* DDL */ SELECT public.db_alter_table('C_ILCandHandler','ALTER TABLE public.C_ILCandHandler ADD COLUMN IsAllowDifferentShipmentsInSameInvoiceLine CHAR(1) DEFAULT ''N'' CHECK (IsAllowDifferentShipmentsInSameInvoiceLine IN (''Y'',''N'')) NOT NULL')
;

