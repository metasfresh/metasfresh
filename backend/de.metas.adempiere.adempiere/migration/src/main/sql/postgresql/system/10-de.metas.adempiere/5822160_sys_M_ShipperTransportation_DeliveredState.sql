-- M_ShipperTransportation.DeliveredState: the delivery instruction's three-state delivered indicator
-- (spec 5.7, Task Q9). Not a boolean: on a consolidated instruction "partly delivered" is the normal
-- intermediate state, and collapsing it into a single "not fully" is exactly the distinction the
-- operator needs to act on.
--
-- Stored column maintained by an interceptor, NOT a ColumnSQL (owner, 2026-09-03): the instruction grid
-- is a document list, so a correlated-subquery aggregate over allocations would cost one extra query per
-- visible row per open, and the column also needs to be filterable/sortable later, which is exactly
-- where a virtual aggregate hurts. Recomputed at the four write points that can change it: an allocation
-- created or deactivated (DeliveryPlanningRepository#createAllocation /
-- #deactivateAllocationRecords), and a receipt or shipment completed or reversed
-- (interceptor/M_InOut#afterComplete / #afterReverseCorrect) - all four route through the single
-- derivation method DeliveryPlanningList#getDeliveredState(), so the stored value cannot drift from a
-- definition that exists twice.
--
-- Reference list, not a bare ColumnSQL display literal, so it carries AD_Ref_List_Trl and is
-- translated. Codes match the Java enum's names, same convention as M_ShipperTransportation's own
-- TransportDirection.
--
-- IDs allocated from idserver.metas.de on 2026-09-03:
--   AD_MigrationScript 5822160 (this file)
--   AD_Element          585421 (M_ShipperTransportation.DeliveredState)
--   AD_Reference        542137 (DeliveredState, List)
--   AD_Ref_List         544357 (NotDelivered), 544358 (PartlyDelivered), 544359 (FullyDelivered)
--   AD_Column           593468 (M_ShipperTransportation.DeliveredState)

-- ============================================================================
-- 1) AD_Reference: DeliveredState (List)
-- ============================================================================
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, Description, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542137 /*From ID Server*/, TO_TIMESTAMP('2026-09-03 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'The three-state delivered indicator of a delivery instruction', 'D', 'Y', 'N', 'DeliveredState', TO_TIMESTAMP('2026-09-03 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'L')
;

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Reference_ID=542137
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- ============================================================================
-- 2) AD_Ref_List: NotDelivered / PartlyDelivered / FullyDelivered
-- ============================================================================
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 542137, 544357 /*From ID Server*/, TO_TIMESTAMP('2026-09-03 09:00:10','YYYY-MM-DD HH24:MI:SS'), 100, 'No allocation''s planning is delivered', 'D', 'Y', 'Not delivered', TO_TIMESTAMP('2026-09-03 09:00:10','YYYY-MM-DD HH24:MI:SS'), 100, 'NotDelivered', 'Not delivered')
;
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544357
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
-- German wording per spec 5.7 is the 31608 section 6.4 bundle item, still open at the time of writing;
-- de_DE/de_CH filled with a reasonable provisional translation, to be reconciled with that bundle rather
-- than left untranslated in the meantime.
UPDATE AD_Ref_List_Trl SET Description='Kein zugeordneter Lieferplan ist zugestellt', Name='Nicht zugestellt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544357
;
UPDATE AD_Ref_List_Trl SET Description='Kein zugeordneter Lieferplan ist zugestellt', Name='Nicht zugestellt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544357
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544357
;
-- fr_CH: en_US text, IsTranslated='N', per the convention 5821150 established for this column family.
UPDATE AD_Ref_List_Trl SET Name='Not delivered', IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-03 09:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=544357
;

INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 542137, 544358 /*From ID Server*/, TO_TIMESTAMP('2026-09-03 09:00:20','YYYY-MM-DD HH24:MI:SS'), 100, 'Some allocations'' plannings are delivered, some are not', 'D', 'Y', 'Partly delivered', TO_TIMESTAMP('2026-09-03 09:00:20','YYYY-MM-DD HH24:MI:SS'), 100, 'PartlyDelivered', 'Partly delivered')
;
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544358
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl SET Description='Einige zugeordnete Lieferpläne sind zugestellt, andere nicht', Name='Teilweise zugestellt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544358
;
UPDATE AD_Ref_List_Trl SET Description='Einige zugeordnete Lieferpläne sind zugestellt, andere nicht', Name='Teilweise zugestellt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544358
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544358
;
UPDATE AD_Ref_List_Trl SET Name='Partly delivered', IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-03 09:00:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=544358
;

INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 542137, 544359 /*From ID Server*/, TO_TIMESTAMP('2026-09-03 09:00:30','YYYY-MM-DD HH24:MI:SS'), 100, 'Every allocation''s planning is delivered', 'D', 'Y', 'Fully delivered', TO_TIMESTAMP('2026-09-03 09:00:30','YYYY-MM-DD HH24:MI:SS'), 100, 'FullyDelivered', 'Fully delivered')
;
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544359
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl SET Description='Alle zugeordneten Lieferpläne sind zugestellt', Name='Vollständig zugestellt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544359
;
UPDATE AD_Ref_List_Trl SET Description='Alle zugeordneten Lieferpläne sind zugestellt', Name='Vollständig zugestellt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:32','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544359
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:33','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544359
;
UPDATE AD_Ref_List_Trl SET Name='Fully delivered', IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-03 09:00:34','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=544359
;

-- ============================================================================
-- 3) AD_Element: M_ShipperTransportation.DeliveredState (new - a new concept at instruction level,
--    no existing element carries this text without misleading another column, same reasoning 5821150
--    used for IsAllocated)
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585421 /*From ID Server*/, 0, 'DeliveredState', TO_TIMESTAMP('2026-09-03 09:00:40','YYYY-MM-DD HH24:MI:SS'), 100, 'Zeigt an, ob keine, einige oder alle zugeordneten Lieferplanungen der Auslieferungsanweisung bereits zugestellt sind.', 'D', 'Zeigt an, ob keine, einige oder alle zugeordneten Lieferplanungen der Auslieferungsanweisung bereits zugestellt sind.', 'Y', 'Zustellstatus', 'Zustellstatus', TO_TIMESTAMP('2026-09-03 09:00:40','YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585421
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET Name='Delivered state', PrintName='Delivered state', Description='Indicates whether none, some, or all of the delivery instruction''s allocated plannings are already delivered.', Help='Indicates whether none, some, or all of the delivery instruction''s allocated plannings are already delivered.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:41','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585421 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585421,'en_US')
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:42','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585421 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585421,'de_DE')
;
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 09:00:43','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585421 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585421,'de_CH')
;
-- fr_CH per the convention 5820520 established: the en_US text, IsTranslated='N'.
UPDATE AD_Element_Trl SET Name='Delivered state', PrintName='Delivered state', Description='Indicates whether none, some, or all of the delivery instruction''s allocated plannings are already delivered.', Help='Indicates whether none, some, or all of the delivery instruction''s allocated plannings are already delivered.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-03 09:00:44','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585421 AND AD_Language='fr_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585421,'fr_CH')
;

-- ============================================================================
-- 4) AD_Column: M_ShipperTransportation.DeliveredState - real (stored) column, List reference over
--    the AD_Ref_List above. AD_Table_ID=540030 (M_ShipperTransportation). Not lazy-loading: it must be
--    readable from Java (DeliveryPlanningRepository#recomputeDeliveredState writes it; a later reader
--    that lists instructions may filter/sort on it).
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DefaultValue, Description, EntityType, FieldLength, Help, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 593468 /*From ID Server*/, 585421, 0, 17, 542137, 540030, 'DeliveredState',
        TO_TIMESTAMP('2026-09-03 09:00:50','YYYY-MM-DD HH24:MI:SS'), 100, 'NotDelivered',
        'Zeigt an, ob keine, einige oder alle zugeordneten Lieferplanungen der Auslieferungsanweisung bereits zugestellt sind.', 'D', 40,
        'Zeigt an, ob keine, einige oder alle zugeordneten Lieferplanungen der Auslieferungsanweisung bereits zugestellt sind.', 'Y', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'Y',
        'Zustellstatus', 0, TO_TIMESTAMP('2026-09-03 09:00:50','YYYY-MM-DD HH24:MI:SS'), 100, 0)
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593468
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(585421)
;

-- Business table: the ADD COLUMN + backfill below touch every row, so back it up first.
SELECT backup_table('m_shippertransportation', '_31789_Q9_DeliveredState');

-- Physical column. NOT NULL DEFAULT 'NotDelivered': an instruction is NotDelivered until it has any
-- allocation whose planning is delivered - the same vacuous case DeliveryPlanningList#getDeliveredState
-- answers for an empty selection - so a freshly-inserted row (before its first allocation write point
-- runs) is correct by construction, never a NULL needing a special case.
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation', 'ALTER TABLE public.M_ShipperTransportation ADD COLUMN DeliveredState VARCHAR(40) DEFAULT ''NotDelivered'' NOT NULL')
;

-- Backfill every pre-existing instruction from the same definition DeliveryPlanningList#getDeliveredState
-- evaluates, over its currently ACTIVE allocations' plannings' M_InOut_ID - otherwise every instruction
-- created before this script runs silently reads NotDelivered regardless of its real state.
UPDATE M_ShipperTransportation t
SET DeliveredState = (
    SELECT CASE
               WHEN NOT EXISTS (SELECT 1 FROM M_Delivery_Planning_Alloc a WHERE a.M_ShipperTransportation_ID = t.M_ShipperTransportation_ID AND a.IsActive = 'Y')
                   THEN 'NotDelivered'
               WHEN NOT EXISTS (
                   SELECT 1 FROM M_Delivery_Planning_Alloc a
                   JOIN M_Delivery_Planning p ON p.M_Delivery_Planning_ID = a.M_Delivery_Planning_ID
                   WHERE a.M_ShipperTransportation_ID = t.M_ShipperTransportation_ID AND a.IsActive = 'Y' AND p.M_InOut_ID IS NULL)
                   THEN 'FullyDelivered'
               WHEN EXISTS (
                   SELECT 1 FROM M_Delivery_Planning_Alloc a
                   JOIN M_Delivery_Planning p ON p.M_Delivery_Planning_ID = a.M_Delivery_Planning_ID
                   WHERE a.M_ShipperTransportation_ID = t.M_ShipperTransportation_ID AND a.IsActive = 'Y' AND p.M_InOut_ID IS NOT NULL)
                   THEN 'PartlyDelivered'
               ELSE 'NotDelivered'
           END
)
;
