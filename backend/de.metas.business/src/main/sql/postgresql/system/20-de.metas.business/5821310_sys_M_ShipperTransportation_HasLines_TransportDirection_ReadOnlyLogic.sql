-- M_ShipperTransportation.HasLines: a ColumnSQL flag saying whether the document already carries
-- cargo, and the ReadOnlyLogic it exists for -- the transport direction stays editable while the
-- document is empty and locks the moment the first package is on it.
--
-- Keyed on M_ShippingPackage alone, with no OR over M_Delivery_Planning_Alloc: an active
-- allocation always has an active package, so the package already covers both routes onto the
-- document, the transport-order path and the delivery-planning path.
--
-- IsLazyLoading stays 'N': the value has to be part of the document's own SELECT for @HasLines@
-- to resolve when the readonly logic is evaluated.
--
-- HasLines is evaluated when the document is loaded, so a package attached from somewhere else
-- reaches the field on the next load of the document rather than instantly.
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript             5821310 (this file)
--   AD_Element                     585392 (HasLines)
--   AD_Column                      593440 (M_ShipperTransportation.HasLines)
--   AD_SQLColumn_SourceTableColumn 540230

-- ============================================================================
-- 1) AD_Element: HasLines
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585392 /*From ID Server*/, 0, 'HasLines', TO_TIMESTAMP('2026-08-31 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'Zeigt an, ob dem Dokument bereits Versandpakete zugeordnet sind.', 'D', 'Zeigt an, ob dem Dokument bereits Versandpakete zugeordnet sind. Solange das Dokument leer ist, kann die Richtung noch geändert werden.', 'Y', 'Versandpakete vorhanden', 'Versandpakete vorhanden', TO_TIMESTAMP('2026-08-31 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585392
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Has Shipping Packages', PrintName='Has Shipping Packages', Description='Indicates whether shipping packages are already assigned to the document.', Help='Indicates whether shipping packages are already assigned to the document. While the document is still empty, the direction can be changed.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-31 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585392 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585392,'en_US')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-31 12:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585392 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585392,'de_DE')
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-31 12:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585392 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585392,'de_CH')
;

-- fr_CH is pointed at the en_US text and left IsTranslated='N': no French wording exists for this
-- element, and English text in an fr_CH row is not correct text for that language - but it is at
-- least readable there, which the seeded German copy is not.
UPDATE AD_Element_Trl SET Name='Has Shipping Packages', PrintName='Has Shipping Packages', Description='Indicates whether shipping packages are already assigned to the document.', Help='Indicates whether shipping packages are already assigned to the document. While the document is still empty, the direction can be changed.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-31 12:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585392 AND AD_Language='fr_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585392,'fr_CH')
;

-- ============================================================================
-- 2) AD_Column: M_ShipperTransportation.HasLines (ColumnSQL, no physical column)
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, Description, EntityType, FieldLength, Help, IsActive, IsLazyLoading, IsSelectionColumn, IsUpdateable, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
VALUES (0, 593440 /*From ID Server*/, 585392, 0, 20, 540030, 'HasLines',
        '(case when exists (select 1 from m_shippingpackage p where p.m_shippertransportation_id = M_ShipperTransportation.M_ShipperTransportation_ID and p.isactive = ''Y'') then ''Y'' else ''N'' end)',
        TO_TIMESTAMP('2026-08-31 12:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
        'Zeigt an, ob dem Dokument bereits Versandpakete zugeordnet sind.', 'D', 1,
        'Zeigt an, ob dem Dokument bereits Versandpakete zugeordnet sind. Solange das Dokument leer ist, kann die Richtung noch geändert werden.', 'Y', 'N', 'N', 'N',
        'Versandpakete vorhanden', 'NP', TO_TIMESTAMP('2026-08-31 12:01:00','YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593440
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(585392)
;

-- ============================================================================
-- 3) AD_SQLColumn_SourceTableColumn -- cache invalidation
-- ============================================================================
-- HasLines depends on M_ShippingPackage; the link column is the package's own FK back to the
-- transport document (AD_Column 540458), which names the target's PK directly.
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID, AD_SQLColumn_SourceTableColumn_ID, AD_Org_ID, AD_Table_ID, AD_Column_ID, Created, CreatedBy, FetchTargetRecordsMethod, IsActive, link_column_id, source_table_id, Updated, UpdatedBy)
VALUES (0, 540230 /*From ID Server*/, 0, 540030, 593440, TO_TIMESTAMP('2026-08-31 12:02:00','YYYY-MM-DD HH24:MI:SS'), 100, 'L', 'Y', 540458, 540031, TO_TIMESTAMP('2026-08-31 12:02:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- ============================================================================
-- 4) The direction fields: let HasLines govern them on both windows
-- ============================================================================
-- A field's own IsReadOnly='Y' wins over its ReadOnlyLogic, so both fields are set explicitly.
UPDATE AD_Field
SET    ReadOnlyLogic = '@HasLines@=''Y''',
       IsReadOnly    = 'N',
       Updated       = TO_TIMESTAMP('2026-08-31 12:03:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 100
WHERE  AD_Field_ID IN (783020, 783021)
;
