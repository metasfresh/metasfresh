-- Run mode: SWING_CLIENT

-- Column: M_ReceiptSchedule.M_ShipperTransportation_ID
-- 2025-10-31T08:53:50.610Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591446,540089,0,19,540524,'XX','M_ShipperTransportation_ID','(SELECT DISTINCT st.m_shippertransportation_id from m_receiptschedule rs INNER JOIN m_shippingpackage sp on sp.c_order_id = rs.c_order_id INNER JOIN m_shippertransportation st on st.m_shippertransportation_id = sp.m_shippertransportation_id where rs.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)',TO_TIMESTAMP('2025-10-31 08:53:48.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Transport Auftrag',0,0,TO_TIMESTAMP('2025-10-31 08:53:48.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-31T08:53:50.688Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591446 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-31T08:53:50.868Z
/* DDL */  select update_Column_Translation_From_AD_Element(540089)
;

-- Column: M_ReceiptSchedule.M_ShipperTransportation_ID
-- Column SQL (old): (SELECT DISTINCT st.m_shippertransportation_id from m_receiptschedule rs INNER JOIN m_shippingpackage sp on sp.c_order_id = rs.c_order_id INNER JOIN m_shippertransportation st on st.m_shippertransportation_id = sp.m_shippertransportation_id where rs.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)
-- 2025-10-31T10:11:56.827Z
UPDATE AD_Column SET ColumnSQL='(SELECT MAX(st.m_shippertransportation_id) from m_receiptschedule rs INNER JOIN m_shippingpackage sp on sp.c_order_id = rs.c_order_id INNER JOIN m_shippertransportation st on st.m_shippertransportation_id = sp.m_shippertransportation_id where rs.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)',Updated=TO_TIMESTAMP('2025-10-31 10:11:56.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591446
;

-- Column: M_ReceiptSchedule.M_ShipperTransportation_ID
-- Source Table: M_ShippingPackage
-- 2025-10-31T11:01:48.302Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,591446,0,540183,540524,TO_TIMESTAMP('2025-10-31 11:01:47.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L','Y',550698,540031,TO_TIMESTAMP('2025-10-31 11:01:47.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_ReceiptSchedule.M_ShipperTransportation_ID
-- 2025-10-31T08:58:31.749Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=542013, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-10-31 08:58:31.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591446
;

-- Column: M_ReceiptSchedule.M_ShipperTransportation_ID
-- 2025-10-31T08:59:19.483Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-31 08:59:19.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591446
;

-- Column: M_ReceiptSchedule.M_ShipperTransportation_ID
-- 2025-10-31T09:13:27.810Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2025-10-31 09:13:27.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591446
;

-- Column: M_ReceiptSchedule.CalendarWeek
-- 2025-10-31T09:13:39.385Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2025-10-31 09:13:39.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590640
;

-- Column: M_ReceiptSchedule.DatePromised_Effective
-- 2025-10-31T09:13:50.462Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2025-10-31 09:13:50.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=579327
;

-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2025-10-31T09:14:01.358Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2025-10-31 09:14:01.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589629
;

-- Column: M_ReceiptSchedule.POReference
-- 2025-10-31T09:14:13.637Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2025-10-31 09:14:13.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588327
;

-- Column: M_ReceiptSchedule.C_BPartner_ID
-- 2025-10-31T09:14:27.061Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2025-10-31 09:14:27.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549499
;

-- Column: M_ReceiptSchedule.C_Order_ID
-- 2025-10-31T09:14:41.045Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2025-10-31 09:14:41.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549488
;

-- Column: M_ReceiptSchedule.M_Product_ID
-- 2025-10-31T09:14:53.268Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2025-10-31 09:14:53.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549508
;

-- Column: M_ReceiptSchedule.M_Warehouse_Effective_ID
-- 2025-10-31T09:15:03.850Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2025-10-31 09:15:03.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=570612
;

-- Column: M_ReceiptSchedule.ExportStatus
-- 2025-10-31T09:15:13.483Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2025-10-31 09:15:13.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=571008
;

-- Column: M_ReceiptSchedule.Shipper_BPartner_ID
-- 2025-10-31T09:15:24.095Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2025-10-31 09:15:24.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591271
;

-- Column: M_ReceiptSchedule.ETD
-- 2025-10-31T09:15:35.325Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=120,Updated=TO_TIMESTAMP('2025-10-31 09:15:35.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591259
;

-- Column: M_ReceiptSchedule.ETA
-- 2025-10-31T09:15:47.137Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=130,Updated=TO_TIMESTAMP('2025-10-31 09:15:47.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591265
;

-- Column: M_ReceiptSchedule.ATD
-- 2025-10-31T09:15:58.188Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=140,Updated=TO_TIMESTAMP('2025-10-31 09:15:58.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591261
;

-- Column: M_ReceiptSchedule.ATA
-- 2025-10-31T09:16:54.695Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=150,Updated=TO_TIMESTAMP('2025-10-31 09:16:54.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591260
;

-- Column: M_ReceiptSchedule.CRD
-- 2025-10-31T09:17:10.320Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=160,Updated=TO_TIMESTAMP('2025-10-31 09:17:10.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591264
;

-- Column: M_ReceiptSchedule.ContainerNo
-- 2025-10-31T09:17:26.743Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=170,Updated=TO_TIMESTAMP('2025-10-31 09:17:26.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591263
;

-- Column: M_ReceiptSchedule.TrackingID
-- 2025-10-31T09:17:40.744Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=180,Updated=TO_TIMESTAMP('2025-10-31 09:17:40.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591272
;

-- Column: M_ReceiptSchedule.IsBLReceived
-- 2025-10-31T09:17:54.421Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=190,Updated=TO_TIMESTAMP('2025-10-31 09:17:54.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591266
;

-- Column: M_ReceiptSchedule.IsBookingConfirmed
-- 2025-10-31T09:18:08.080Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=200,Updated=TO_TIMESTAMP('2025-10-31 09:18:08.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591267
;

-- Column: M_ReceiptSchedule.IsWENotice
-- 2025-10-31T09:18:23.176Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=210,Updated=TO_TIMESTAMP('2025-10-31 09:18:23.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591268
;

-- Column: M_ReceiptSchedule.VesselName
-- 2025-10-31T09:18:38.222Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=220,Updated=TO_TIMESTAMP('2025-10-31 09:18:38.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591273
;

-- Column: M_ReceiptSchedule.POL_ID
-- 2025-10-31T09:18:51.221Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=230,Updated=TO_TIMESTAMP('2025-10-31 09:18:51.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591270
;

-- Column: M_ReceiptSchedule.POD_ID
-- 2025-10-31T09:19:07.312Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=240,Updated=TO_TIMESTAMP('2025-10-31 09:19:07.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591269
;

-- Column: M_ReceiptSchedule.Processed
-- 2025-10-31T09:19:21.200Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=250,Updated=TO_TIMESTAMP('2025-10-31 09:19:21.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549513
;

-- Column: M_ReceiptSchedule.AD_Org_ID
-- 2025-10-31T09:19:39.414Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=260,Updated=TO_TIMESTAMP('2025-10-31 09:19:39.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549481
;
