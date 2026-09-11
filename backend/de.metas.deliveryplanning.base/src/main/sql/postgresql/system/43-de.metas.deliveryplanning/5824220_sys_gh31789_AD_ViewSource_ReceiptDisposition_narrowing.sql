-- Replaces the coarse whole-view invalidation on this window with DECLARATIVE, ROUTED invalidation.
--
-- Before: window 542190 carried seven WEBUI_ViewInvalidateOnChange rows (M_Delivery_Planning,
-- M_ReceiptSchedule, M_ShipperTransportation, M_ShippingPackage, M_InOut, C_Order,
-- M_ReceiptSchedule_Alloc). Each one invalidates the ENTIRE view on ANY write to that table, so a
-- transport order edited anywhere in the system re-queries every row of this window.
--
-- After: an AD_ViewSource row per source pairs a link column on the VIEW with one on the SOURCE
-- (Parent_LinkColumn_ID / Source_LinkColumn_ID), so a write routes to the rows it can actually affect.
-- AD_ViewSource_Column narrows further, to the columns the view genuinely reads.
--
-- WHICH SOURCES CAN BE ROUTED - measured against the view's own projected columns, not assumed:
--
--   M_Delivery_Planning       M_Delivery_Planning_ID       its own PK, on both sides
--   M_ReceiptSchedule         M_ReceiptSchedule_ID         its own PK, on both sides
--   M_ShipperTransportation   M_ShipperTransportation_ID   its own PK, on both sides (column exposed by 5823970)
--   C_Order                   C_Order_ID                   its own PK, on both sides
--   M_ReceiptSchedule_Alloc   M_ReceiptSchedule_ID         its FK; the view has no alloc id, but an alloc
--                                                          change affects exactly that schedule's rows
--   M_ShippingPackage         M_ShipperTransportation_ID   its FK; same shape, via the transport order
--
--   M_InOut                   NOT ROUTABLE, and deliberately left on its coarse row. The view projects no
--                             M_InOut_ID. Its other shared columns (C_Order_ID, M_ShipperTransportation_ID)
--                             would invalidate every row of an order or transport order on any receipt write
--                             - a DIFFERENT, coarser rule wearing the same clothes, not this one expressed
--                             declaratively. Forcing it would trade an honest coarse row for a dishonest one.
--
-- THE EVENT FLAGS follow the pattern already set on the sibling view
-- M_Delivery_Planning_Delivery_Instructions_V (AD_ViewSource 540000-540002):
--   CONTENT sources    (the row persists, its values change) -> IsInvalidateOnBeforeChange + AfterChange
--   MEMBERSHIP sources (rows appear / disappear)             -> IsInvalidateOnAfterNew + AfterDelete
-- Setting all five everywhere would fire on events that cannot move the view, which is the very
-- over-firing this migration exists to remove.
--
-- THE CLOSING TRIGGER ROW. An AD_ViewSource-built request names the VIEW table, because this view
-- registers in AD_Window_ParentChildTableNames_v1 as a parent with a NULL child, so the Direct factory
-- emits rootRecord(<view table>, rowId). Without a WEBUI_ViewInvalidateOnChange row for the VIEW TABLE
-- (542644) the request reaches no listener and nothing refreshes - the piece that makes the other two
-- pieces do anything at all.

-- ---------------------------------------------------------------------------------------------------
-- AD_ViewSource - one per routable source
-- ---------------------------------------------------------------------------------------------------

INSERT INTO AD_ViewSource (AD_ViewSource_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
    AD_Table_ID,Source_Table_ID,Parent_LinkColumn_ID,Source_LinkColumn_ID,
    IsInvalidateOnBeforeChange,IsInvalidateOnAfterChange,IsInvalidateOnBeforeNew,IsInvalidateOnAfterNew,IsInvalidateOnAfterDelete,TechnicalNote)
SELECT x.id,0,0,'Y',now(),100,now(),100,
       (SELECT AD_Table_ID FROM AD_Table WHERE TableName='RV_ReceiptDisposition_DeliveryPlanning'),
       (SELECT AD_Table_ID FROM AD_Table WHERE TableName=x.src),
       (SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID=c.AD_Table_ID
         WHERE t.TableName='RV_ReceiptDisposition_DeliveryPlanning' AND c.ColumnName=x.parent_col),
       (SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID=c.AD_Table_ID
         WHERE t.TableName=x.src AND c.ColumnName=x.source_col),
       x.bchg,x.achg,'N',x.anew,x.adel,x.note
FROM (VALUES
    (540009,'M_Delivery_Planning',    'M_Delivery_Planning_ID',    'M_Delivery_Planning_ID',    'Y','Y','N','N','content: the planning row persists and its quantities/dates change'),
    (540010,'M_ReceiptSchedule',      'M_ReceiptSchedule_ID',      'M_ReceiptSchedule_ID',      'Y','Y','N','N','content: the schedule row persists and its quantities/dates change'),
    (540011,'M_ShipperTransportation','M_ShipperTransportation_ID','M_ShipperTransportation_ID','Y','Y','N','N','content: the four flags and the container number are edited in place'),
    (540012,'C_Order',                'C_Order_ID',                'C_Order_ID',                'Y','Y','N','N','content: preparation date and shipper are edited in place'),
    (540013,'M_ReceiptSchedule_Alloc','M_ReceiptSchedule_ID',      'M_ReceiptSchedule_ID',      'N','N','Y','Y','membership: allocations appear and disappear; routed through the schedule they belong to'),
    (540014,'M_ShippingPackage',      'M_ShipperTransportation_ID','M_ShipperTransportation_ID','N','N','Y','Y','membership: packages appear and disappear; routed through their transport order')
) AS x(id,src,parent_col,source_col,bchg,achg,anew,adel,note)
WHERE NOT EXISTS (SELECT 1 FROM AD_ViewSource e WHERE e.AD_ViewSource_ID=x.id)
;

-- ---------------------------------------------------------------------------------------------------
-- AD_ViewSource_Column - narrow to the columns the view actually reads
--
-- Only for the four sources where it pays. M_Delivery_Planning and M_ReceiptSchedule are deliberately
-- left unnarrowed: ~15 of their ~20 content columns feed this view, so listing them would add thirty
-- config rows to exclude almost nothing, and thirty more things to keep in step whenever the view
-- changes. For those two the LINK is the whole benefit. For the four below the narrowing is the point -
-- M_ShipperTransportation has ~40 columns and this view reads five of them.
-- ---------------------------------------------------------------------------------------------------

INSERT INTO AD_ViewSource_Column (AD_ViewSource_Column_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
    AD_ViewSource_ID,AD_Column_ID)
SELECT y.id,0,0,'Y',now(),100,now(),100,y.vs,
       (SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID=c.AD_Table_ID
         WHERE t.TableName=y.src AND c.ColumnName=y.col)
FROM (VALUES
    -- M_ShipperTransportation: the four flags the window shows, the container number, and activation
    (540005,540011,'M_ShipperTransportation','ContainerNo'),
    (540006,540011,'M_ShipperTransportation','IsBLReceived'),
    (540007,540011,'M_ShipperTransportation','IsBookingConfirmed'),
    (540008,540011,'M_ShipperTransportation','IsWENotice'),
    (540009,540011,'M_ShipperTransportation','IsActive'),
    -- C_Order: only these two reach the view
    (540010,540012,'C_Order','PreparationDate'),
    (540011,540012,'C_Order','M_Shipper_ID'),
    -- M_ReceiptSchedule_Alloc: the LATERAL reads these
    (540012,540013,'M_ReceiptSchedule_Alloc','M_InOutLine_ID'),
    (540013,540013,'M_ReceiptSchedule_Alloc','M_ReceiptSchedule_ID'),
    (540014,540013,'M_ReceiptSchedule_Alloc','IsActive'),
    -- M_ShippingPackage: the LATERAL reads these
    (540015,540014,'M_ShippingPackage','C_Order_ID'),
    (540016,540014,'M_ShippingPackage','C_OrderLine_ID'),
    (540017,540014,'M_ShippingPackage','M_ShipperTransportation_ID'),
    (540018,540014,'M_ShippingPackage','IsActive')
) AS y(id,vs,src,col)
WHERE EXISTS (SELECT 1 FROM AD_ViewSource e WHERE e.AD_ViewSource_ID=y.vs)
  AND NOT EXISTS (SELECT 1 FROM AD_ViewSource_Column e WHERE e.AD_ViewSource_Column_ID=y.id)
;

-- ---------------------------------------------------------------------------------------------------
-- The closing trigger row: the VIEW table itself
-- ---------------------------------------------------------------------------------------------------

INSERT INTO WEBUI_ViewInvalidateOnChange (WEBUI_ViewInvalidateOnChange_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID,AD_Table_ID)
SELECT 540011,0,0,'Y',now(),100,now(),100,542190,
       (SELECT AD_Table_ID FROM AD_Table WHERE TableName='RV_ReceiptDisposition_DeliveryPlanning')
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange e
     WHERE e.AD_Window_ID=542190
       AND e.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='RV_ReceiptDisposition_DeliveryPlanning'))
;

-- ---------------------------------------------------------------------------------------------------
-- Retire the six coarse rows the AD_ViewSource rows now replace. M_InOut's stays: it is the one source
-- this mechanism cannot route, so its whole-view invalidation is still the only correct rule for it.
-- Deactivated rather than deleted, so the previous behaviour is one UPDATE away if the routed
-- invalidation turns out to miss a case in the field.
-- ---------------------------------------------------------------------------------------------------

UPDATE WEBUI_ViewInvalidateOnChange w
   SET IsActive='N', Updated=now(), UpdatedBy=100
 WHERE w.AD_Window_ID=542190
   AND w.IsActive='Y'
   AND w.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
        'M_Delivery_Planning','M_ReceiptSchedule','M_ShipperTransportation','C_Order',
        'M_ReceiptSchedule_Alloc','M_ShippingPackage'))
;
