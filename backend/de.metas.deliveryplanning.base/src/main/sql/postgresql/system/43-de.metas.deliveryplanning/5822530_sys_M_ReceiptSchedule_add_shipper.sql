-- M_ReceiptSchedule carries C_Order_ID but neither IsSOTrx nor M_Shipper_ID; C_Order has both. A
-- ColumnSQL read-through gives the receipt schedule a shipper of its own, in the shape of the existing
-- M_Delivery_Planning.POReference (AD_Column 585010): lowercase keywords and table names, raw
-- "M_ReceiptSchedule." self-reference (M_ReceiptSchedule has a generated PO model class, so the
-- @JoinTableNameOrAliasIncludingDot@ placeholder is not substituted on that load path). Reused element
-- 455 (M_Shipper_ID / "Lieferweg"), the same element C_Order.M_Shipper_ID already uses, so the caption
-- and its translations are already correct.
--
-- IsLazyLoading='Y': nothing in this issue reads this column from Java; it is a display/filter-context
-- column, matching the convention every other cross-table virtual column on this table family follows
-- (M_Delivery_Planning.POReference is lazy too).
--
-- The AD_SQLColumn_SourceTableColumn entry is mandatory, not optional: of M_Delivery_Planning's nine
-- virtual columns, eight omit it and consequently show stale values in the WebUI grid until a manual
-- reload -- this column links via M_ReceiptSchedule.C_Order_ID to C_Order.M_Shipper_ID so a change to
-- the order's shipper invalidates the cache correctly.
--
-- C_Order and the planning's own shipper column are untouched by this script.

-- Column: M_ReceiptSchedule.M_Shipper_ID
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnSQL,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593496 /*From ID Server*/,455,0,19,NULL,540524,'(select o.M_Shipper_ID from c_order o where o.C_Order_ID = M_ReceiptSchedule.C_Order_ID)','M_Shipper_ID',TO_TIMESTAMP('2026-09-03 09:20:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',22,'Y','N','N','N','N','N','Y','N','N','N','N','N','N','Lieferweg','NP',0,TO_TIMESTAMP('2026-09-03 09:20:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593496 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Pull the already-translated captions down from element 455 onto the new column's translations
UPDATE AD_Column_Trl ct
SET Name = et.Name, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-03 09:20:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID
  AND et.AD_Language = ct.AD_Language
  AND c.AD_Column_ID = 593496
  AND ct.AD_Language <> 'de_DE'
  AND et.IsTranslated = 'Y'
;

-- Cache invalidation: M_ReceiptSchedule.M_Shipper_ID must refresh when the linked C_Order.M_Shipper_ID
-- changes, via M_ReceiptSchedule.C_Order_ID.
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,AD_Column_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,FetchTargetRecordsMethod,Link_Column_ID,Source_Column_ID,Source_Table_ID)
VALUES (0,0,540239 /*From ID Server*/,540524,593496,TO_TIMESTAMP('2026-09-03 09:20:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',TO_TIMESTAMP('2026-09-03 09:20:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'L',549488,2197,259)
;
