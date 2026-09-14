-- M_Delivery_Planning.IsReadyForReceipt: the readiness status the receipt-disposition delivery-planning
-- window filters by. A planning is ready for receipt once it is allocated to a COMPLETED delivery
-- instruction; until then a planner is still arranging the transport and the logistics worker has nothing
-- to act on. A receipt schedule with no planning has nothing being arranged, so it is always ready - that
-- half needs no column and is served by the view (5823490).
--
-- Why a STORED column and not a ColumnSQL: the same reason 5822060 converted IsAllocated. The window this
-- backs is a GRID over RV_ReceiptDisposition_DeliveryPlanning and this is the column the grid FILTERS on,
-- so a lazy ColumnSQL would cost one extra query per row and could not be pushed into the view's WHERE at
-- all. A real column is read straight off the row and is invalidated by its own table's cache reset.
--
-- Why it is not derivable from IsAllocated: allocation and completion are two different moments. Add-to and
-- Move-to both refuse a non-DRAFT target (DeliveryPlanningService#putOnDeliveryInstruction's guards), so
-- EVERY allocation starts life on a draft instruction - a planning can be allocated for as long as the
-- planner needs and still not be actionable.
--
-- Completed = DocStatus 'CO' only, matching DeliveryInstructionRepository#hasCompletedAmong, which is this
-- codebase's existing "instruction is completed" predicate. 'CL' is deliberately absent: no delivery
-- instruction in the deep_tundra_release DB carries it (672 rows: DR 564 / CO 165 / VO 75 / IP 6, measured
-- 2026-09-09), so including it would be a guard with no scenario behind it.
--
-- Kept in step by two interceptor points, both re-deriving from the same predicate as the backfill below:
--   * M_Delivery_Planning_Alloc (AFTER_NEW / AFTER_CHANGE on IsActive / AFTER_DELETE) - the points that
--     already maintain IsAllocated. A MOVE deactivates the old allocation and inserts a new one rather than
--     repointing M_ShipperTransportation_ID, so those three timings cover it, and a VOID reaches them too
--     via unlinkDeliveryPlannings' saveRecord.
--   * M_ShipperTransportation (AFTER_COMPLETE / AFTER_REACTIVATE) - the instruction's own DocStatus is the
--     other half of the predicate, and re-activating one takes its plannings back out of the ready set.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   AD_MigrationScript 5823480 (this file), AD_Element 585451, AD_Column 593533

-- Business table: the ADD COLUMN + backfill below touch every row, so back it up first.
SELECT backup_table('m_delivery_planning', '_IsReadyForReceipt');

-- Same physical shape as the table's other boolean flags (IsAllocated, IsClosed, Processed).
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning', 'ALTER TABLE public.M_Delivery_Planning ADD COLUMN IsReadyForReceipt CHAR(1) DEFAULT ''N'' CHECK (IsReadyForReceipt IN (''Y'',''N'')) NOT NULL')
;

-- Backfill: every row just got 'N' from the ADD COLUMN default, so every planning that is ALREADY on a
-- completed instruction must be corrected here - the interceptors only see changes from now on, and without
-- this those plannings stay invisible to the window's readiness filter until something touches them again.
UPDATE M_Delivery_Planning
SET IsReadyForReceipt = (CASE
                             WHEN EXISTS (SELECT 1
                                          FROM M_Delivery_Planning_Alloc a
                                                   JOIN M_ShipperTransportation st
                                                        ON st.M_ShipperTransportation_ID = a.M_ShipperTransportation_ID
                                          WHERE a.M_Delivery_Planning_ID = M_Delivery_Planning.M_Delivery_Planning_ID
                                            AND a.IsActive = 'Y'
                                            AND st.DocStatus = 'CO')
                                 THEN 'Y'
                             ELSE 'N'
                         END)
;

-- Element: no existing AD_Element for this concept (checked: no ColumnName='IsReadyForReceipt' anywhere in
-- AD). ONE element serves both AD_Columns - this one and the view's (5823500) - so the wording is stated
-- once and covers both row types.
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585451 /*From ID Server*/,0,'IsReadyForReceipt',TO_TIMESTAMP('2026-09-09 09:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist; eine Zeile ohne Lieferplanung ist immer bereit.',
        'D',NULL,'Y','Bereit für Wareneingang','Bereit für Wareneingang',TO_TIMESTAMP('2026-09-09 09:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Description,t.Help,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585451
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl
SET Name='Ready for Receipt', PrintName='Ready for Receipt',
    Description='Indicates whether the material receipt can be done for this row. A delivery planning is ready once it is allocated to a completed delivery instruction; a row with no delivery planning is always ready.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-09-09 09:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585451
;

-- Base-language text is already correct for de_DE / de_CH ("Bereit für Wareneingang" carries no ß) -- just
-- flip IsTranslated.
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 09:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585451
;

-- fr_CH per the convention stated once in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql: the en_US text,
-- IsTranslated='N'. Runs after the en_US override above, so it copies the English text -- without this the
-- seeded row keeps the German base text, which is unusable rather than merely untranslated for an fr_CH user.
UPDATE AD_Element_Trl trl
SET Name         = en.Name,
    PrintName    = en.PrintName,
    Description  = en.Description,
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-09-09 09:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
FROM AD_Element_Trl en
WHERE en.AD_Element_ID = trl.AD_Element_ID
  AND en.AD_Language = 'en_US'
  AND trl.AD_Language = 'fr_CH'
  AND trl.AD_Element_ID = 585451
;

-- Column: M_Delivery_Planning.IsReadyForReceipt. IsUpdateable='N' - interceptor-maintained, like
-- IsAllocated (593412), whose IsMandatory/DefaultValue this mirrors onto the new NOT NULL DEFAULT 'N'
-- physical column. No AD_Field on the delivery-planning window: the status belongs on the
-- receipt-disposition grid (5823500), and the planner's own window is not asked to show it.
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593533 /*From ID Server*/,585451,0,20,NULL,542259,'IsReadyForReceipt',TO_TIMESTAMP('2026-09-09 09:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N',
        'Zeigt an, ob der Wareneingang für diese Zeile durchgeführt werden kann. Eine Lieferplanung ist bereit, sobald sie einer abgeschlossenen Auslieferungsanweisung zugeordnet ist; eine Zeile ohne Lieferplanung ist immer bereit.',
        'D',1,NULL,'Y','Y','N','N','N','N','Y','N','N','N','N','N','Bereit für Wareneingang','NP',0,
        TO_TIMESTAMP('2026-09-09 09:01:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593533
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Pushes the element's per-language text onto the AD_Column_Trl skeleton rows just seeded - they carry the
-- German base text until this runs.
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585451);
