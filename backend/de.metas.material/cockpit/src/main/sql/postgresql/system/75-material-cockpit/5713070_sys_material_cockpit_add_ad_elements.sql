-- 2023-12-11T12:36:31.891Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582846, 0, 'QtyOrdered_PurchaseOrder_AtDate', TO_TIMESTAMP('2023-12-11 12:36:31.702000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Bestellmenge für das jeweilige Datum', 'de.metas.material.cockpit', 'Y', '📆 Bestellt', '📆 Bestellt',
        TO_TIMESTAMP('2023-12-11 12:36:31.702000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100)
;

-- 2023-12-11T12:36:31.929Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 582846
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: QtyOrdered_PurchaseOrder_AtDate
-- 2023-12-11T12:36:42.642Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 12:36:42.642000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582846
  AND AD_Language = 'de_CH'
;

-- 2023-12-11T12:36:42.663Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582846, 'de_CH')
;

-- Element: QtyOrdered_PurchaseOrder_AtDate
-- 2023-12-11T12:36:57.143Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 12:36:57.142000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582846
  AND AD_Language = 'de_DE'
;

-- 2023-12-11T12:36:57.145Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582846, 'de_DE')
;

-- 2023-12-11T12:36:57.167Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582846, 'de_DE')
;

-- Element: QtyOrdered_PurchaseOrder_AtDate
-- 2023-12-11T12:38:08.773Z
UPDATE AD_Element_Trl
SET Description='Quantity from purchase orders for the respective date', IsTranslated='Y', Name='📆 Purchased', PrintName='📆 Purchased', Updated=TO_TIMESTAMP('2023-12-11 12:38:08.772000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582846
  AND AD_Language = 'en_US'
;

-- 2023-12-11T12:38:08.774Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582846, 'en_US')
;

-- Column: MD_Cockpit.QtyOrdered_PurchaseOrder_AtDate
-- 2023-12-11T12:39:36.964Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587726, 582846, 0, 29, 540863, 'QtyOrdered_PurchaseOrder_AtDate', TO_TIMESTAMP('2023-12-11 12:39:36.846000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'Bestellmenge für das jeweilige Datum', 'de.metas.material.cockpit', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y', 'N', 0, '📆 Bestellt', 0, 0, TO_TIMESTAMP('2023-12-11 12:39:36.846000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T12:39:36.969Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 587726
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T12:39:36.975Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582846)
;

-- 2023-12-11T12:39:39.598Z
/* DDL */

SELECT public.db_alter_table('MD_Cockpit', 'ALTER TABLE public.MD_Cockpit ADD COLUMN QtyOrdered_PurchaseOrder_AtDate NUMERIC')
;

-- 2023-12-11T12:42:09.691Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582847, 0, 'QtyOrdered_SalesOrder_AtDate', TO_TIMESTAMP('2023-12-11 12:42:09.567000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Verkaufte Menge für das jeweilige Datum', 'de.metas.material.cockpit', 'Y', '📆 Verkauft', '📆 Verkauft',
        TO_TIMESTAMP('2023-12-11 12:42:09.567000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100)
;

-- 2023-12-11T12:42:09.694Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 582847
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: QtyOrdered_SalesOrder_AtDate
-- 2023-12-11T12:42:14.822Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 12:42:14.822000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582847
  AND AD_Language = 'de_CH'
;

-- 2023-12-11T12:42:14.824Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582847, 'de_CH')
;

-- Element: QtyOrdered_SalesOrder_AtDate
-- 2023-12-11T12:42:15.831Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 12:42:15.831000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582847
  AND AD_Language = 'de_DE'
;

-- 2023-12-11T12:42:15.832Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582847, 'de_DE')
;

-- 2023-12-11T12:42:15.848Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582847, 'de_DE')
;

-- Element: QtyOrdered_SalesOrder_AtDate
-- 2023-12-11T12:43:29.928Z
UPDATE AD_Element_Trl
SET Description='Quantity from sales orders for the respective date', IsTranslated='Y', Name='📆 Sold', PrintName='📆 Sold', Updated=TO_TIMESTAMP('2023-12-11 12:43:29.928000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582847
  AND AD_Language = 'en_US'
;

-- 2023-12-11T12:43:29.930Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582847, 'en_US')
;

-- Column: MD_Cockpit.QtyOrdered_SalesOrder_AtDate
-- 2023-12-11T12:45:03.941Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587727, 582847, 0, 29, 540863, 'QtyOrdered_SalesOrder_AtDate', TO_TIMESTAMP('2023-12-11 12:45:03.810000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'Verkaufte Menge für das jeweilige Datum', 'de.metas.material.cockpit', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y', 'N', 0, '📆 Verkauft', 0, 0, TO_TIMESTAMP('2023-12-11 12:45:03.810000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T12:45:03.943Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 587727
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T12:45:03.947Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582847)
;

-- 2023-12-11T12:45:05.290Z
/* DDL */

SELECT public.db_alter_table('MD_Cockpit', 'ALTER TABLE public.MD_Cockpit ADD COLUMN QtyOrdered_SalesOrder_AtDate NUMERIC')
;

-- 2023-12-11T12:48:15.872Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582848, 0, 'PMM_QtyPromised_NextDay', TO_TIMESTAMP('2023-12-11 12:48:15.737000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Vom Lieferanten per Webapplikation zugesagte Menge für morgen', 'de.metas.material.cockpit', 'Y', 'Zusage Lieferant Folgetag', 'Zusage Lieferant Folgetag',
        TO_TIMESTAMP('2023-12-11 12:48:15.737000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100)
;

-- 2023-12-11T12:48:15.877Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 582848
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: PMM_QtyPromised_NextDay
-- 2023-12-11T12:48:25.682Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 12:48:25.682000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582848
  AND AD_Language = 'de_CH'
;

-- 2023-12-11T12:48:25.684Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582848, 'de_CH')
;

-- Element: PMM_QtyPromised_NextDay
-- 2023-12-11T12:48:31.537Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 12:48:31.537000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582848
  AND AD_Language = 'de_DE'
;

-- 2023-12-11T12:48:31.538Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582848, 'de_DE')
;

-- 2023-12-11T12:48:31.545Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582848, 'de_DE')
;

-- Element: PMM_QtyPromised_NextDay
-- 2023-12-11T12:49:50.057Z
UPDATE AD_Element_Trl
SET Description='', IsTranslated='Y', Name='Vendor promised for next day', PrintName='Vendor promised for next day', Updated=TO_TIMESTAMP('2023-12-11 12:49:50.057000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582848
  AND AD_Language = 'en_US'
;

-- 2023-12-11T12:49:50.058Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582848, 'en_US')
;

-- Column: MD_Cockpit.PMM_QtyPromised_NextDay
-- 2023-12-11T12:58:11.697Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587728, 582848, 0, 29, 540863, 'PMM_QtyPromised_NextDay', TO_TIMESTAMP('2023-12-11 12:58:11.578000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'Vom Lieferanten per Webapplikation zugesagte Menge für morgen', 'de.metas.material.cockpit', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Zusage Lieferant Folgetag', 0, 0, TO_TIMESTAMP('2023-12-11 12:58:11.578000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T12:58:11.702Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 587728
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T12:58:11.706Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582848)
;

-- 2023-12-11T12:58:13.037Z
/* DDL */

SELECT public.db_alter_table('MD_Cockpit', 'ALTER TABLE public.MD_Cockpit ADD COLUMN PMM_QtyPromised_NextDay NUMERIC')
;

-- 2023-12-11T13:08:57.853Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582849, 0, 'HighestPurchasePrice_AtDate', TO_TIMESTAMP('2023-12-11 13:08:57.705000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Höchster Einkaufspreis für das jeweilige Datum', 'de.metas.material.cockpit', 'Y', 'Höchster EP', 'Höchster EP',
        TO_TIMESTAMP('2023-12-11 13:08:57.705000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100)
;

-- 2023-12-11T13:08:57.861Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 582849
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: HighestPurchasePrice_AtDate
-- 2023-12-11T13:09:03.814Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 13:09:03.814000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582849
  AND AD_Language = 'de_CH'
;

-- 2023-12-11T13:09:03.816Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582849, 'de_CH')
;

-- Element: HighestPurchasePrice_AtDate
-- 2023-12-11T13:09:04.679Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 13:09:04.679000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582849
  AND AD_Language = 'de_DE'
;

-- 2023-12-11T13:09:04.682Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582849, 'de_DE')
;

-- 2023-12-11T13:09:04.705Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582849, 'de_DE')
;

-- Element: HighestPurchasePrice_AtDate
-- 2023-12-11T13:10:02.362Z
UPDATE AD_Element_Trl
SET Description='Highest Purchase Price for the respective date', IsTranslated='Y', Name='Highest PP', PrintName='Highest PP', Updated=TO_TIMESTAMP('2023-12-11 13:10:02.362000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582849
  AND AD_Language = 'en_US'
;

-- 2023-12-11T13:10:02.363Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582849, 'en_US')
;

-- 2023-12-11T13:14:09.033Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582850, 0, 'AvailableQty_AtDate', TO_TIMESTAMP('2023-12-11 13:14:08.886000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Zählstand + Bestellt für das jeweilige Datum', 'de.metas.material.cockpit', 'Y', 'Verfügbare Menge', 'Verfügbare Menge',
        TO_TIMESTAMP('2023-12-11 13:14:08.886000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100)
;

-- 2023-12-11T13:14:09.036Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 582850
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: AvailableQty_AtDate
-- 2023-12-11T13:14:15.178Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 13:14:15.178000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582850
  AND AD_Language = 'de_CH'
;

-- 2023-12-11T13:14:15.180Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582850, 'de_CH')
;

-- Element: AvailableQty_AtDate
-- 2023-12-11T13:14:16.244Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 13:14:16.244000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582850
  AND AD_Language = 'de_DE'
;

-- 2023-12-11T13:14:16.246Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582850, 'de_DE')
;

-- 2023-12-11T13:14:16.252Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582850, 'de_DE')
;

-- Element: AvailableQty_AtDate
-- 2023-12-11T13:16:48.561Z
UPDATE AD_Element_Trl
SET Description='Stock count + Purchased for respective Date', IsTranslated='Y', Name='📆 Available Qty', PrintName='📆 Available Qty', Updated=TO_TIMESTAMP('2023-12-11 13:16:48.561000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582850
  AND AD_Language = 'en_US'
;

-- 2023-12-11T13:16:48.564Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582850, 'en_US')
;

-- Element: AvailableQty_AtDate
-- 2023-12-11T13:17:00.826Z
UPDATE AD_Element_Trl
SET Name='📆 Verfügbare Menge', Updated=TO_TIMESTAMP('2023-12-11 13:17:00.826000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582850
  AND AD_Language = 'de_DE'
;

-- 2023-12-11T13:17:00.840Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582850, 'de_DE')
;

-- 2023-12-11T13:17:00.846Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582850, 'de_DE')
;

-- 2023-12-11T13:17:00.850Z
UPDATE AD_Column
SET ColumnName='AvailableQty_AtDate', Name='📆 Verfügbare Menge', Description='Zählstand + Bestellt für das jeweilige Datum', Help=NULL
WHERE AD_Element_ID = 582850
;

-- 2023-12-11T13:17:00.854Z
UPDATE AD_Process_Para
SET ColumnName='AvailableQty_AtDate', Name='📆 Verfügbare Menge', Description='Zählstand + Bestellt für das jeweilige Datum', Help=NULL, AD_Element_ID=582850
WHERE UPPER(ColumnName) = 'AVAILABLEQTY_ATDATE'
  AND IsCentrallyMaintained = 'Y'
  AND AD_Element_ID IS NULL
;

-- 2023-12-11T13:17:00.894Z
UPDATE AD_Process_Para
SET ColumnName='AvailableQty_AtDate', Name='📆 Verfügbare Menge', Description='Zählstand + Bestellt für das jeweilige Datum', Help=NULL
WHERE AD_Element_ID = 582850
  AND IsCentrallyMaintained = 'Y'
;

-- 2023-12-11T13:17:00.896Z
UPDATE AD_Field
SET Name='📆 Verfügbare Menge', Description='Zählstand + Bestellt für das jeweilige Datum', Help=NULL
WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID = 582850) AND AD_Name_ID IS NULL)
   OR (AD_Name_ID = 582850)
;

-- 2023-12-11T13:17:01.435Z
UPDATE AD_PrintFormatItem pi
SET PrintName='Verfügbare Menge', Name='📆 Verfügbare Menge'
WHERE IsCentrallyMaintained = 'Y'
  AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID = pi.AD_Column_ID AND c.AD_Element_ID = 582850)
;

-- 2023-12-11T13:17:01.439Z
UPDATE AD_Tab
SET Name='📆 Verfügbare Menge', Description='Zählstand + Bestellt für das jeweilige Datum', Help=NULL, CommitWarning = NULL
WHERE AD_Element_ID = 582850
;

-- 2023-12-11T13:17:01.442Z
UPDATE AD_WINDOW
SET Name='📆 Verfügbare Menge', Description='Zählstand + Bestellt für das jeweilige Datum', Help=NULL
WHERE AD_Element_ID = 582850
;

-- 2023-12-11T13:17:01.444Z
UPDATE AD_Menu
SET Name = '📆 Verfügbare Menge', Description = 'Zählstand + Bestellt für das jeweilige Datum', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL
WHERE AD_Element_ID = 582850
;

-- Element: AvailableQty_AtDate
-- 2023-12-11T13:17:04.858Z
UPDATE AD_Element_Trl
SET Name='📆 Verfügbare Menge', Updated=TO_TIMESTAMP('2023-12-11 13:17:04.858000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582850
  AND AD_Language = 'de_CH'
;

-- 2023-12-11T13:17:04.859Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582850, 'de_CH')
;

-- Element: AvailableQty_AtDate
-- 2023-12-11T13:17:10.498Z
UPDATE AD_Element_Trl
SET PrintName='📆 Verfügbare Menge', Updated=TO_TIMESTAMP('2023-12-11 13:17:10.498000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582850
  AND AD_Language = 'de_DE'
;

-- 2023-12-11T13:17:10.500Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582850, 'de_DE')
;

-- 2023-12-11T13:17:10.505Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582850, 'de_DE')
;

-- 2023-12-11T13:17:10.506Z
UPDATE AD_PrintFormatItem pi
SET PrintName='📆 Verfügbare Menge', Name='📆 Verfügbare Menge'
WHERE IsCentrallyMaintained = 'Y'
  AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID = pi.AD_Column_ID AND c.AD_Element_ID = 582850)
;

-- Element: AvailableQty_AtDate
-- 2023-12-11T13:17:13.816Z
UPDATE AD_Element_Trl
SET PrintName='📆 Verfügbare Menge', Updated=TO_TIMESTAMP('2023-12-11 13:17:13.816000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582850
  AND AD_Language = 'de_CH'
;

-- 2023-12-11T13:17:13.817Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582850, 'de_CH')
;

-- 2023-12-11T13:21:02.045Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582851, 0, 'RemainingStock_AtDate', TO_TIMESTAMP('2023-12-11 13:21:01.927000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Verfügbare Menge - Verkauft für das jeweilige Datum', 'de.metas.material.cockpit', 'Y', '📆 Restbestand', '📆 Restbestand',
        TO_TIMESTAMP('2023-12-11 13:21:01.927000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100)
;

-- 2023-12-11T13:21:02.048Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 582851
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: RemainingStock_AtDate
-- 2023-12-11T13:21:09.978Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 13:21:09.978000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582851
  AND AD_Language = 'de_CH'
;

-- 2023-12-11T13:21:09.979Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582851, 'de_CH')
;

-- Element: RemainingStock_AtDate
-- 2023-12-11T13:21:11.344Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-12-11 13:21:11.344000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582851
  AND AD_Language = 'de_DE'
;

-- 2023-12-11T13:21:11.346Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582851, 'de_DE')
;

-- 2023-12-11T13:21:11.352Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582851, 'de_DE')
;

-- Element: RemainingStock_AtDate
-- 2023-12-11T13:22:01.281Z
UPDATE AD_Element_Trl
SET Description='Qty Available - Sold for respective date', IsTranslated='Y', Name='📆 Remaining Stock', PrintName='📆 Remaining Stock', Updated=TO_TIMESTAMP('2023-12-11 13:22:01.281000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID = 582851
  AND AD_Language = 'en_US'
;

-- 2023-12-11T13:22:01.282Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582851, 'en_US')
;

