-- Run mode: SWING_CLIENT

-- Column: M_ReceiptSchedule.C_BP_Group_ID
-- 2025-11-30T09:12:47.860Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591609,1383,0,30,540996,540524,'XX','C_BP_Group_ID','(SELECT MAX(bp.C_BP_Group_ID) from C_BPartner bp where bp.C_BPartner_ID=M_ReceiptSchedule.C_BPartner_ID)',TO_TIMESTAMP('2025-11-30 09:12:46.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Geschäftspartnergruppe','de.metas.inoutcandidate',0,10,'E','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N',0,'Geschäftspartnergruppe',0,0,TO_TIMESTAMP('2025-11-30 09:12:46.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-30T09:12:47.938Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591609 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-30T09:12:48.116Z
/* DDL */  select update_Column_Translation_From_AD_Element(1383)
;

-- Column: M_ReceiptSchedule.C_BP_Group_ID
-- 2025-11-30T09:14:39.598Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2025-11-30 09:14:39.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591609
;

-- Column: M_ReceiptSchedule.C_Order_ID
-- 2025-11-30T09:14:52.720Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2025-11-30 09:14:52.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549488
;

-- Column: M_ReceiptSchedule.M_Product_ID
-- 2025-11-30T09:15:02.378Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2025-11-30 09:15:02.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549508
;

-- Column: M_ReceiptSchedule.M_Warehouse_Effective_ID
-- 2025-11-30T09:15:15.409Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2025-11-30 09:15:15.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=570612
;

-- Column: M_ReceiptSchedule.ExportStatus
-- 2025-11-30T09:15:23.305Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2025-11-30 09:15:23.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=571008
;

-- Column: M_ReceiptSchedule.Shipper_BPartner_ID
-- 2025-11-30T09:15:35.043Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=120,Updated=TO_TIMESTAMP('2025-11-30 09:15:35.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591271
;

-- Column: M_ReceiptSchedule.ETD
-- 2025-11-30T09:15:49.398Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=130,Updated=TO_TIMESTAMP('2025-11-30 09:15:49.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591259
;

-- Column: M_ReceiptSchedule.ETA
-- 2025-11-30T09:16:02.924Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=140,Updated=TO_TIMESTAMP('2025-11-30 09:16:02.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591265
;

-- Column: M_ReceiptSchedule.ATD
-- 2025-11-30T09:16:14.430Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=150,Updated=TO_TIMESTAMP('2025-11-30 09:16:14.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591261
;

-- Column: M_ReceiptSchedule.ATA
-- 2025-11-30T09:16:25.581Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=160,Updated=TO_TIMESTAMP('2025-11-30 09:16:25.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591260
;

-- Column: M_ReceiptSchedule.CRD
-- 2025-11-30T09:16:38.632Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=170,Updated=TO_TIMESTAMP('2025-11-30 09:16:38.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591264
;

-- Column: M_ReceiptSchedule.ContainerNo
-- 2025-11-30T09:16:51.234Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=180,Updated=TO_TIMESTAMP('2025-11-30 09:16:51.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591263
;

-- Column: M_ReceiptSchedule.TrackingID
-- 2025-11-30T09:16:59.521Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=190,Updated=TO_TIMESTAMP('2025-11-30 09:16:59.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591272
;

-- Column: M_ReceiptSchedule.IsBLReceived
-- 2025-11-30T09:17:11.340Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=200,Updated=TO_TIMESTAMP('2025-11-30 09:17:11.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591266
;

-- Column: M_ReceiptSchedule.IsBookingConfirmed
-- 2025-11-30T09:17:24.200Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=210,Updated=TO_TIMESTAMP('2025-11-30 09:17:24.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591267
;

-- Column: M_ReceiptSchedule.IsWENotice
-- 2025-11-30T09:17:37.849Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=220,Updated=TO_TIMESTAMP('2025-11-30 09:17:37.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591268
;

-- Column: M_ReceiptSchedule.VesselName
-- 2025-11-30T09:17:51.529Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=230,Updated=TO_TIMESTAMP('2025-11-30 09:17:51.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591273
;

-- Column: M_ReceiptSchedule.POL_ID
-- 2025-11-30T09:18:02.683Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=240,Updated=TO_TIMESTAMP('2025-11-30 09:18:02.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591270
;

-- Column: M_ReceiptSchedule.POD_ID
-- 2025-11-30T09:18:13.573Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=250,Updated=TO_TIMESTAMP('2025-11-30 09:18:13.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591269
;

-- Column: M_ReceiptSchedule.Processed
-- 2025-11-30T09:18:23.210Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=260,Updated=TO_TIMESTAMP('2025-11-30 09:18:23.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549513
;

-- Column: M_ReceiptSchedule.M_CustomsTariff_ID
-- 2025-11-30T09:18:32.527Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=270,Updated=TO_TIMESTAMP('2025-11-30 09:18:32.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591601
;

-- Column: M_ReceiptSchedule.AD_Org_ID
-- 2025-11-30T09:18:46.477Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=280,Updated=TO_TIMESTAMP('2025-11-30 09:18:46.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549481
;

-- Column: M_ReceiptSchedule.C_BP_Group_ID
-- Source Table: C_BPartner
-- 2025-11-30T09:35:16.728Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,591609,0,540185,540524,TO_TIMESTAMP('2025-11-30 09:35:16.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L','Y',2893,2893,291,TO_TIMESTAMP('2025-11-30 09:35:16.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
