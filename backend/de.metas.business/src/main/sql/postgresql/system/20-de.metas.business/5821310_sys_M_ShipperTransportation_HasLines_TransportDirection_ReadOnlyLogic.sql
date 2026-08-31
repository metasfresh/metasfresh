-- M_ShipperTransportation.HasLines: a ColumnSQL flag saying whether the document already carries
-- cargo, and the ReadOnlyLogic it exists for -- the transport direction stays editable while the
-- document is empty and locks the moment the first package is on it.
--
-- Why the direction needs BOTH states, not one of them:
--   * settable at New -- the column is mandatory and 5821080 deliberately removed its default at
--     both layers, precisely so that no creation path silently invents 'Outgoing' any more. A
--     mandatory, defaultless, permanently read-only field is a record nobody can save.
--   * locked once loaded -- every M_ShippingPackage on the document was attached under the
--     direction the document had at the time, and the delivery-instruction admissibility check
--     compares a planning's direction against the instruction's. Flipping the direction afterwards
--     would silently contradict every package already attached, with nothing in the UI saying so.
-- '@HasLines@=''Y''' is exactly that boundary, and it replaces nothing: neither field carried a
-- ReadOnlyLogic before. The processed-document lock is unrelated and still applies on top -- the
-- WebUI makes every field of a processed document read-only regardless of this expression.
--
-- Keyed on M_ShippingPackage ALONE, deliberately -- no OR over M_Delivery_Planning_Alloc. An active
-- allocation always has an active package: createAllocation saves the package before it references
-- it, and the deactivate paths retire both together. So the package covers both routes onto the
-- document, the transport-order path and the delivery-planning path, and a second EXISTS would only
-- add a second index probe for a state the first one already reports.
--
-- Note the failure direction if that invariant were ever broken: an allocation without a package
-- would make HasLines read 'N' and leave the direction EDITABLE (fail-open) rather than locking a
-- field that should be free (fail-closed). Editable-when-it-should-be-locked is recoverable -- the
-- planner sees the direction and the cargo on the same screen; a false lock on a mandatory,
-- defaultless field is not, because it makes the record unsaveable with no way out.
--
-- Shape, AD_Reference choice and documentation style all mirror M_Delivery_Planning.IsAllocated
-- (AD_Column 593412, added by 5821150 on this branch): a YesNo (AD_Reference_ID=20) ColumnSQL
-- column whose CASE ... EXISTS resolves against an index, plus an AD_SQLColumn_SourceTableColumn
-- row so the WebUI invalidates it when the source table changes. Two deliberate differences:
--   * IsSelectionColumn stays 'N'. IsAllocated is a filter a planner uses on their working list;
--     HasLines exists to feed a logic expression, and nobody asked for a "has cargo" filter on the
--     transport-order list. Adding one would occupy a filter slot for a UI nobody requested.
--   * IsLazyLoading stays 'N'. The value has to be part of the document's own SELECT for
--     @HasLines@ to resolve when the readonly logic is evaluated.
--
-- No AD_Field and no AD_UI_Element for HasLines, and none is needed: ad_field_v INNER JOINs
-- AD_Column and only LEFT JOINs AD_Field, so every active column of the tab's table becomes a
-- document field and is selected with the row -- an AD_Field row would only add a rendered widget
-- nobody wants to see. (Same reason 5821150 added its four columns without any AD_Field.)
--
-- Why the logic goes on the two AD_Field rows and not on AD_Column 593410: the WebUI reads
-- COALESCE(NULLIF(AD_Field.ReadOnlyLogic,''), AD_Column.ReadOnlyLogic), but a field whose own
-- AD_Field.IsReadOnly='Y' short-circuits to a constant TRUE and never evaluates either expression.
-- 783021 (Lieferanweisungen) is exactly that case today, so it takes IsReadOnly='N' here: the
-- logic, not the flag, is what governs it from now on. The deliberate consequence is that an EMPTY
-- draft delivery instruction now has an editable direction on that window -- which is the point,
-- since that document is mandatory-direction and defaultless too.
--
-- HasLines is read when the document is loaded, so a package attached from somewhere else reaches
-- the field on the next load of the document rather than instantly. That is the same freshness
-- IsAllocated has, and it is the safe side of the boundary: a stale 'N' leaves the field editable.
--
-- IDs allocated from idserver.metas.de on 2026-08-31:
--   AD_MigrationScript             5821310 (this file)
--   AD_Element                     585392 (HasLines)
--   AD_Column                      593440 (M_ShipperTransportation.HasLines)
--   AD_SQLColumn_SourceTableColumn 540230
--
-- DB lookups (deep_tundra_uat_2, port 21632, 2026-08-31):
--   AD_Table_ID  of M_ShipperTransportation                     -> 540030
--   AD_Table_ID  of M_ShippingPackage                           -> 540031
--   AD_Column_ID of M_ShippingPackage.M_ShipperTransportation_ID -> 540458
--   AD_Column_ID of M_ShipperTransportation.TransportDirection   -> 593410
--   AD_Field_ID  783020 (AD_Window 540020 -> AD_Tab 540096), 783021 (541657 -> 546732)
--   AD_Element.ColumnName 'HasLines' -> not in use, so a new element is correct here
--
-- Index check on the live DB: the per-document form of the EXISTS plans as
-- "Index Scan using m_shippingpackage_m_shipperttransportation_id", 3 buffers, no scan.

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

-- IsTranslated='Y' says the row's text is CORRECT for its language, not that a translation step
-- happened -- same reading 5821150 spells out. de_DE is the base language and carries the authored
-- German verbatim; de_CH takes the same German (no Swiss variant wanted); en_US carries real
-- English. fr_CH keeps the seeded German and therefore stays 'N'.
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
-- 783020 (Transport Auftrag) is already IsReadOnly='N'; 783021 (Lieferanweisungen) is not, and the
-- flag would win over the expression -- so both are set explicitly rather than only the one that
-- changes, which also documents that neither may go back to a hardcoded 'Y'.
UPDATE AD_Field
SET    ReadOnlyLogic = '@HasLines@=''Y''',
       IsReadOnly    = 'N',
       Updated       = TO_TIMESTAMP('2026-08-31 12:03:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 100
WHERE  AD_Field_ID IN (783020, 783021)
;
