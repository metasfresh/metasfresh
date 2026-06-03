-- Run mode: SWING_CLIENT

-- me03#30195: Add Limit, Offset and Processed filter to Historical_Shipments_JSON and Historical_Invoices_JSON

-- ============================================================
-- Add Processed column to historical_m_inout_json_v (last column)
-- ============================================================

-- 2026-06-03
CREATE OR REPLACE VIEW historical_m_inout_json_v AS
SELECT io.m_inout_id                                   AS "Shipment_ID",
       io.documentno                                   AS "Shipment_DocumentNo",
       io.movementdate                                 AS "Shipment_Date",
       io.docstatus                                    AS "DocStatus",
       dt.docbasetype                                  AS "DocType_Base",
       io.ExternalId                                   AS "ExternalId",
       io.updated::timestamp                           AS "Updated",
       -- ExternalSystemCode uses COALESCE(esystem.value,'') so shipments without an
       -- external system return '' instead of NULL. The Historical_Shipments_JSON
       -- process jsonpath filters with ExternalSystemCode.ilike.'%' by default;
       -- NULL ILIKE '%' = NULL (false) would exclude those shipments.
       COALESCE(esystem.value, '')::varchar(40)        AS "ExternalSystemCode",
       (CASE
            WHEN dsource.internalname IS NOT NULL
                THEN 'int-' || dsource.internalname
                ELSE ''
        END)                                           AS "DataSource",
       o.c_order_id                                    AS "Order_ID",
       o.dateordered                                   AS "Order_Date",
       o.datepromised                                  AS "Order_DatePromised",
       COALESCE(o.poreference, io.poreference)         AS "Order_POReference",
       o.edi_desadv_id                                 AS "DESADV_ID",
       COALESCE(o.deliveryviarule, io.deliveryviarule) AS "DeliveryViaRule",
       partner.value                                   AS "BPartnerValue",
       bPartnerExternalReference.externalreference     AS "BPartnerExternalReference",
       bPartnerExternalSystem.value                    AS "BPartnerExternalSystemValue",

       bp_supplier.bpartner_json                       AS "Supplier",
       bpl_supplier.bpartner_location_json             AS "Supplier_Location",
       bp_buyer.bpartner_json                          AS "Buyer",
       bpl_buyer.bpartner_location_json                AS "Buyer_Location",
       bp_bill.bpartner_json                           AS "Invoicee",
       bpl_bill.bpartner_location_json                 AS "Invoicee_Location",
       bp_handover.bpartner_json                       AS "DeliveryParty",
       bpl_handover.bpartner_location_json             AS "DeliveryParty_Location",
       bp_dropship.bpartner_json                       AS "UltimateConsignee",
       bpl_dropship.bpartner_location_json             AS "UltimateConsignee_Location",

       curr.currency_json                              AS "Currency",

       (SELECT JSONB_AGG(JSONB_BUILD_OBJECT(
                                 'LineNo', iol.line,
                                 'M_InOutLine_ID', iol.m_inoutline_id,
                                 'Product_ID', iol.m_product_id,
                                 'ProductValue', p.value,
                                 'ProductName', p.name,
                                 'QtyEntered', iol.qtyentered,
                                 'UOM', uom.uomsymbol,
                                 'ExternalId', iol.externalid
                             ) ORDER BY iol.line)
        FROM m_inoutline iol
                 LEFT JOIN m_product p ON p.m_product_id = iol.m_product_id
                 LEFT JOIN c_uom uom ON uom.c_uom_id = iol.c_uom_id
        WHERE iol.m_inout_id = io.m_inout_id
          AND iol.isactive = 'Y')                      AS "Lines",

       -- Carrier / parcel tracking infos.
       -- A shipment's physical packages are M_ShippingPackage rows (linked by M_InOut_ID).
       -- Each carrier parcel (Carrier_ShipmentOrder_Parcel) carries the same M_Package_ID,
       -- so we match per-package via M_Package_ID (precise; avoids the transport-level cartesian).
       -- Tracking number (awb) and TrackingURL live on the parcel; the carrier (Versender)
       -- comes from M_Shipper via the parcel's Carrier_ShipmentOrder.
       (SELECT JSONB_AGG(JSONB_BUILD_OBJECT(
                                 'M_Package_ID', par.m_package_id,
                                 'TrackingNumber', par.awb,
                                 'TrackingURL', par.trackingurl,
                                 'Carrier', shp.name,
                                 'CarrierCode', shp.value,
                                 'WeightInKg', par.weightinkg,
                                 'LengthInCm', par.lengthincm,
                                 'WidthInCm', par.widthincm,
                                 'HeightInCm', par.heightincm,
                                 'PackageDescription', par.packagedescription,
                                 'Items', (SELECT JSONB_AGG(JSONB_BUILD_OBJECT(
                                                             'ProductValue', it.articlevalue,
                                                             'ProductName', it.productname,
                                                             'QtyShipped', it.qtyshipped,
                                                             'UOM', uom.uomsymbol,
                                                             'TotalWeightInKg', it.totalweightinkg,
                                                             'CustomsTariffNumber', it.customstariffnumber
                                                         ) ORDER BY it.carrier_shipmentorder_item_id)
                                           FROM carrier_shipmentorder_item it
                                                    LEFT JOIN c_uom uom ON uom.c_uom_id = it.c_uom_id
                                           WHERE it.carrier_shipmentorder_parcel_id = par.carrier_shipmentorder_parcel_id
                                             AND it.isactive = 'Y')
                             ) ORDER BY par.carrier_shipmentorder_parcel_id)
        FROM m_shippingpackage sp
                 JOIN carrier_shipmentorder_parcel par
                      ON par.m_package_id = sp.m_package_id AND par.isactive = 'Y'
                 JOIN carrier_shipmentorder cso
                      ON cso.carrier_shipmentorder_id = par.carrier_shipmentorder_id
                 LEFT JOIN m_shipper shp ON shp.m_shipper_id = cso.m_shipper_id
        WHERE sp.m_inout_id = io.m_inout_id)           AS "Parcels",

       (io.processed = 'Y')                           AS "Processed"

FROM m_inout io
         LEFT JOIN C_DocType dt ON dt.C_DocType_ID = io.C_DocType_ID
         LEFT JOIN c_bpartner partner ON io.c_bpartner_id = partner.c_bpartner_id
         LEFT JOIN s_externalreference bPartnerExternalReference ON bPartnerExternalReference.record_id = partner.c_bpartner_id
    AND bPartnerExternalReference.type = 'BPartner'
    AND bPartnerExternalReference.isactive = 'Y'
         LEFT JOIN externalsystem bPartnerExternalSystem ON bPartnerExternalSystem.externalsystem_id = bPartnerExternalReference.externalsystem_id
         LEFT JOIN c_order o ON io.c_order_id = o.c_order_id
         LEFT JOIN AD_InputDataSource dsource ON dsource.AD_InputDataSource_ID = io.AD_InputDataSource_ID
         LEFT JOIN json_object.currency_object_v curr ON curr.c_currency_id = o.c_currency_id
         LEFT JOIN json_object.bpartner_object_v bp_buyer ON bp_buyer.c_bpartner_id = o.c_bpartner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_buyer ON bpl_buyer.c_bpartner_location_id = o.c_bpartner_location_id
         LEFT JOIN c_bpartner_location bpl_bill_table ON bpl_bill_table.c_bpartner_location_id = o.bill_location_id
         LEFT JOIN json_object.bpartner_object_v bp_bill ON bp_bill.c_bpartner_id = bpl_bill_table.c_bpartner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_bill ON bpl_bill.c_bpartner_location_id = o.bill_location_id
         LEFT JOIN json_object.bpartner_object_v bp_handover ON bp_handover.c_bpartner_id = o.handover_partner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_handover ON bpl_handover.c_bpartner_location_id = o.handover_location_id
         LEFT JOIN json_object.bpartner_object_v bp_dropship ON bp_dropship.c_bpartner_id = o.dropship_bpartner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_dropship ON bpl_dropship.c_bpartner_location_id = o.dropship_location_id
         LEFT JOIN ad_orginfo org ON org.ad_org_id = io.ad_org_id
         LEFT JOIN json_object.bpartner_object_v bp_supplier ON bp_supplier.c_bpartner_id = org.org_bpartner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_supplier ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id
         LEFT JOIN ExternalSystem esystem ON esystem.externalsystem_id = io.externalsystem_id
WHERE io.isactive = 'Y'
ORDER BY io.movementdate DESC, io.updated DESC, io.m_inout_id DESC
;

-- ============================================================
-- Historical_Shipments_JSON (AD_Process_ID=585488)
-- ============================================================

-- Process description
-- 2026-06-02
UPDATE AD_Process
SET Description='Exportiert historische Lieferscheine als JSON für externe Systeme',
    Help=E'Exportiert historische Lieferscheine als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur den Lieferschein mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Processed: Nur abgeschlossene Lieferscheine zurückgeben. Standard: true\n- Order_ID: Nur Lieferscheine dieser Bestellung\n- BPartnerValue: Nur Lieferscheine dieses Geschäftspartners (Suchschlüssel)\n- ShipmentDateGE: Nur Lieferscheine ab diesem Bewegungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585488
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exports historical shipments as JSON for external systems',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585488 AND AD_Language='en_US'
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exportiert historische Lieferscheine als JSON für externe Systeme',
    Help=E'Exportiert historische Lieferscheine als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur den Lieferschein mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Processed: Nur abgeschlossene Lieferscheine zurückgeben. Standard: true\n- Order_ID: Nur Lieferscheine dieser Bestellung\n- BPartnerValue: Nur Lieferscheine dieses Geschäftspartners (Suchschlüssel)\n- ShipmentDateGE: Nur Lieferscheine ab diesem Bewegungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585488 AND AD_Language IN ('de_DE', 'de_CH')
;

-- JSONPath: add Processed filter (Limit+Offset were already present from prior migration)
-- 2026-06-03
UPDATE AD_Process
SET JSONPath='historical_m_inout_json_v?and=(ExternalSystemCode.ilike.@ExternalSystemCode/%%@,Processed.is.@Processed/true@,or(Updated.gte.@UpdatedGE/9999-01-01T00:00:00@,ExternalId.eq.@ExternalId/-1@,Order_ID.eq.@Order_ID/-1@,BPartnerValue.eq.@BPartnerValue/-1@,DocType_Base.eq.@DocType_Base/-1@,Shipment_Date.gte.@ShipmentDateGE/9999-01-01T00:00:00@,and(BPartnerExternalSystemValue.eq.@BPartnerExternalSystemValue/-1@,BPartnerExternalReference.eq.@BPartnerExternalReference/-1@)))&limit=@Limit/2000@&offset=@Offset/0@',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585488
;

-- ParameterName: Processed (SeqNo=35)
-- 2026-06-02
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,1047,0,585488,543238 /*From ID Server*/,10,'Processed',now(),100,'true','D',10,'Y','N','Y','N','N','N','Verarbeitet',35,now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543238
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ParameterName: Limit (SeqNo=100)
-- 2026-06-02
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMax)
VALUES (0,543188,0,585488,543239 /*From ID Server*/,11,'Limit',now(),100,'2000','D',0,'Y','N','Y','N','Y','N','Limit',100,now(),100,'2000')
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543239
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ParameterName: Offset (SeqNo=110)
-- 2026-06-02
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,576802,0,585488,543240 /*From ID Server*/,11,'Offset',now(),100,'0','de.metas.dao.selection',0,'Y','N','Y','N','N','N','Offset',110,now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543240
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ============================================================
-- Historical_Invoices_JSON (AD_Process_ID=585485)
-- ============================================================

-- Process description
-- 2026-06-02
UPDATE AD_Process
SET Description='Exportiert historische Rechnungen als JSON für externe Systeme',
    Help=E'Exportiert historische Rechnungen als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur die Rechnung mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Rechnungen dieser Bestellung\n- BPartnerValue: Nur Rechnungen dieses Geschäftspartners (Suchschlüssel)\n- DateInvoicedGE: Nur Rechnungen ab diesem Rechnungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585485
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exports historical invoices as JSON for external systems',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585485 AND AD_Language='en_US'
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exportiert historische Rechnungen als JSON für externe Systeme',
    Help=E'Exportiert historische Rechnungen als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur die Rechnung mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Rechnungen dieser Bestellung\n- BPartnerValue: Nur Rechnungen dieses Geschäftspartners (Suchschlüssel)\n- DateInvoicedGE: Nur Rechnungen ab diesem Rechnungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585485 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ParameterName: Limit (SeqNo=100)
-- 2026-06-02
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMax)
VALUES (0,543188,0,585485,543242 /*From ID Server*/,11,'Limit',now(),100,'2000','D',0,'Y','N','Y','N','Y','N','Limit',100,now(),100,'2000')
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543242
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ParameterName: Offset (SeqNo=110)
-- 2026-06-02
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,576802,0,585485,543243 /*From ID Server*/,11,'Offset',now(),100,'0','de.metas.dao.selection',0,'Y','N','Y','N','N','N','Offset',110,now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543243
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ============================================================
-- Parameter descriptions
-- ============================================================

-- New parameters (Processed, Limit, Offset) for both processes
-- 2026-06-03
UPDATE AD_Process_Para SET Description='Return only processed (completed) records. Default: true', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543238;

UPDATE AD_Process_Para SET Description='Maximum number of records to return. Default and maximum: 2000', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543239, 543242);

UPDATE AD_Process_Para SET Description='Number of records to skip for pagination. Default: 0', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543240, 543243);

-- Existing parameters with null descriptions
UPDATE AD_Process_Para SET Description='External ID of the record. Default: -1 (return all)', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (542970, 542967);

UPDATE AD_Process_Para SET Description='Return only records for this order', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543062, 543067);

UPDATE AD_Process_Para SET Description='Search key of the business partner', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543065, 543068);

UPDATE AD_Process_Para SET Description='Return only shipments with movement date on or after this date', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543066;

UPDATE AD_Process_Para SET Description='Return only invoices with invoice date on or after this date', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543069;

UPDATE AD_Process_Para SET Description='External reference of the business partner in the external system', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543070, 543072);

UPDATE AD_Process_Para SET Description='Search key of the business partner in the external system', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543071, 543073);

UPDATE AD_Process_Para SET Description='Base document type filter', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543074, 543075);

-- German translations (de_DE, de_CH) for all parameters
-- 2026-06-03
UPDATE AD_Process_Para_Trl SET Description='Nur abgeschlossene (verarbeitete) Datensätze zurückgeben. Standard: true', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543238 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543239, 543242) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543240, 543243) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Externe ID des Datensatzes. Standard: -1 (alle zurückgeben)', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (542970, 542967) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Nur Datensätze dieser Bestellung zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543062, 543067) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543065, 543068) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Nur Lieferscheine ab diesem Bewegungsdatum zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543066 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Nur Rechnungen ab diesem Rechnungsdatum zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543069 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Externe Referenz des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543070, 543072) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543071, 543073) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl SET Description='Basis-Dokumenttyp-Filter', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543074, 543075) AND AD_Language IN ('de_DE', 'de_CH');
