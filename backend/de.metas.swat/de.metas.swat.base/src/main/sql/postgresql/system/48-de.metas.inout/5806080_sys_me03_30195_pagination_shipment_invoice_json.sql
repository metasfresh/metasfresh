-- Run mode: SWING_CLIENT

-- me03#30195: Add Limit and Offset UI parameters to Historical_Shipments_JSON and Historical_Invoices_JSON.
-- The JSONPath already uses &limit=@Limit/2000@&offset=@Offset/0@ (set by migration 5779820);
-- these AD_Process_Para records expose Limit and Offset as configurable fields in the process dialog.

-- ============================================================
-- Add COALESCE to ExternalSystemCode in historical_invoices_json_v
-- ============================================================

-- 2026-06-03
CREATE OR REPLACE VIEW historical_invoices_json_v AS
SELECT invoic_v.c_invoice_id                       AS "Invoice_ID",
       invoic_v.C_Order_ID                         AS "Order_ID",
       bpartner.value                              AS "BPartnerValue",
       bPartnerExternalReference.externalreference AS "BPartnerExternalReference",
       bPartnerExternalSystem.value                AS "BPartnerExternalSystemValue",
       invoic_v.ReceiverGLN                        AS "Invoice_Receiver_Tec_GLN",
       invoic_v.SenderGLN                          AS "Invoice_Sender_Tec_GLN",
       invoic_v.CountryCode                        AS "Invoice_Sender_CountryCode",
       invoic_v.VATaxId                            AS "Invoice_Sender_VATaxId",
       invoic_v.Invoice_DocumentNo                 AS "Invoice_DocumentNo",
       invoic_v.DateInvoiced                       AS "Invoice_Date",
       invoic_v.DateAcct                           AS "Invoice_Acct_Date",
       invoic_v.docbasetype                        AS "DocType_Base",
       invoic_v.docsubtype                         AS "DocType_Sub",
       invoic_v.CreditMemoReason                   AS "CreditMemo_Reason",
       invoic_v.CreditMemoReasonText               AS "CreditMemo_ReasonText",
       invoic_v.POReference                        AS "Order_POReference",
       invoic_v.DateOrdered                        AS "Order_Date",
       invoic_v.MovementDate                       AS "Shipment_Date",
       invoic_v.Shipment_DocumentNo                AS "Shipment_DocumentNo",
       invoic_v.EDIDesadvDocumentNo                AS "DESADV_DocumentNo",
       invoic_v.ISO_Code                           AS "Invoice_Currency_Code",
       invoic_v.GrandTotal                         AS "Invoice_GrandTotal",
       invoic_v.TotalLines                         AS "Invoice_TotalLines",
       invoic_v.TotalVAT                           AS "Invoice_TotalVAT",
       invoic_v.TotalTaxBaseAmt                    AS "Invoice_TotalVATBaseAmt",
       invoic_v.SurchargeAmt                       AS "Invoice_SurchargeAmt",
       invoic_v.TotalLinesWithSurchargeAmt         AS "Invoice_TotalLinesWithSurchargeAmt",
       invoic_v.TotalVatWithSurchargeAmt           AS "Invoice_TotalVATWithSurchargeAmt",
       invoic_v.GrandTotalWithSurchargeAmt         AS "Invoice_GrandTotalWithSurchargeAmt",
       invoic_v.updated::timestamp                 AS "Updated",
       invoic_v.ExternalId                         AS "ExternalId",
       COALESCE(invoic_v.ExternalSystemCode, '')::varchar(40) AS "ExternalSystemCode",
       COALESCE(invoic_v.DataSource, '')           AS "DataSource",
       invoic_v.DocStatus                          AS "DocStatus",
       edi_119_v.json_data                         AS "Partners",
       edi_120_v.json_data                         AS "PaymentTerms",
       edi_140_v.json_data                         AS "PaymentDiscounts",
       edi_500_v.json_data                         AS "Lines",
       edi_901_991_v.json_data                     AS "Sums"
FROM edi_cctop_invoic_v invoic_v
         LEFT JOIN c_bpartner bpartner ON bpartner.c_bpartner_id = invoic_v.C_BPartner_ID
         LEFT JOIN s_externalreference bPartnerExternalReference ON bPartnerExternalReference.record_id = bpartner.c_bpartner_id
    AND bPartnerExternalReference.type = 'BPartner'
    AND bPartnerExternalReference.isactive = 'Y'
         LEFT JOIN externalsystem bPartnerExternalSystem ON bPartnerExternalSystem.externalsystem_id = bPartnerExternalReference.externalsystem_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                   'EANCOM_LocationType', eancom_locationtype,
                                   'GLN', GLN,
                                   'Name', Name,
                                   'Name2', Name2,
                                   'PartnerNo', Value,
                                   'VATaxID', VATaxID,
                                   'ReferenceNo', ReferenceNo,
                                   'SiteName', SiteName,
                                   'Setup_Place_No', Setup_Place_No,
                                   'Address1', Address1,
                                   'Address2', Address2,
                                   'Postal', Postal,
                                   'City', City,
                                   'CountryCode', CountryCode,
                                   'Phone', Phone,
                                   'Fax', Fax,
                                   'CustomEdiAttributes', attr_v.json_data
                               )) AS json_data
                    FROM edi_cctop_119_v cctop119
                             LEFT JOIN (SELECT bpartner_value,
                                               ad_client_id,
                                               ad_org_id,
                                               JSON_AGG(JSON_BUILD_OBJECT(
                                                       bpartner_attr_value.attr_value,
                                                       bpartner_attr_value.valuestring
                                                   )) AS json_data
                                        FROM edi_bpartner_attribute_valuestring_v bpartner_attr_value
                                        GROUP BY bpartner_value, ad_client_id, ad_org_id) attr_v
                                       ON attr_v.bpartner_value = cctop119.value
                                           AND attr_v.ad_client_id = cctop119.ad_client_id
                                           AND (cctop119.ad_org_id = 0 OR attr_v.ad_org_id = cctop119.ad_org_id)
                    GROUP BY c_invoice_id) edi_119_v ON edi_119_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                   'Net_Days', NetDays
                               )) AS json_data
                    FROM edi_cctop_120_v
                    GROUP BY c_invoice_id) edi_120_v ON edi_120_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                   'Discount_Name', Name,
                                   'Tax_Percent', Rate,
                                   'Discount_Days', discountdays,
                                   'Discount_Percent', Discount,
                                   'Discount_BaseAmt', DiscountBaseAmt,
                                   'Discount_Amt', DiscountAmt
                               )) AS json_data
                    FROM edi_cctop_140_v
                    GROUP BY c_invoice_id) edi_140_v ON edi_140_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'Invoice_Line', v.line,
                                            'Invoice_QtyInvoiced', v.QtyInvoiced,
                                            'Invoice_QtyInvoiced_UOM', v.eancom_uom,
                                            'ORDERS_Line', v.externalseqno,
                                            'ORDERS_QtyInvoiced', v.qtyEnteredInBPartnerUOM,
                                            'ORDERS_QtyInvoiced_UOM', uom.x12de355,
                                            'Order_POReference', v.orderporeference,
                                            'Order_Line', v.orderline,
                                            'Order_QtyInvoiced', v.QtyInvoicedInOrderedUOM,
                                            'Order_QtyInvoiced_UOM', v.eancom_ordered_uom,
                                            'Currency_Code', v.iso_code,
                                            'PricePerUnit', v.priceactual,
                                            'PriceUOM', v.eancom_price_uom,
                                            'Discount_Amt', v.discount,
                                            'QtyBasedOn', v.invoicableqtybasedon,
                                            'NetAmt', v.linenetamt,
                                            'Tax_Percent', v.rate,
                                            'Tax_Amount', v.taxamtinfo,
                                            'Product_Name', v.name || v.name2,
                                            'Product_Description', v.productdescription,
                                            'Product_Buyer_CU_GTIN', v.Buyer_GTIN_CU,
                                            'Product_Buyer_TU_GTIN', v.Buyer_GTIN_TU,
                                            'Product_Buyer_ProductNo', v.CustomerProductNo,
                                            'Product_Supplier_TU_GTIN', v.Supplier_GTIN_CU,
                                            'Product_Supplier_ProductNo', v.Value,
                                            'ExternalId', v.ExternalId
                                        ) ORDER BY v.line) AS json_data
                    FROM edi_cctop_invoic_500_v v
                             LEFT JOIN c_uom uom ON uom.c_uom_id = v.C_UOM_BPartner_ID
                    GROUP BY c_invoice_id) edi_500_v ON edi_500_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'TotalAmt', TotalAmt,
                                            'Tax_Amt', TaxAmt,
                                            'Tax_BaseAmt', TaxBaseAmt,
                                            'Tax_Percent', Rate,
                                            'Tax_Exempt', IsTaxExempt = 'Y',
                                            'SurchargeAmt', SurchargeAmt,
                                            'Tax_BaseAmtWithSurchargeAmt', TaxBaseAmtWithSurchargeAmt,
                                            'Tax_AmtWithSurchargeAmt', TaxAmtWithSurchargeAmt
                                        ) ORDER BY rate DESC) AS json_data
                    FROM edi_cctop_901_991_v
                    GROUP BY c_invoice_id) edi_901_991_v ON edi_901_991_v.c_invoice_id = invoic_v.c_invoice_id
ORDER BY invoic_v.dateinvoiced DESC, invoic_v.updated DESC, invoic_v.c_invoice_id DESC
;

-- ============================================================
-- Historical_Shipments_JSON (AD_Process_ID=585488)
-- ============================================================

-- Process description
-- 2026-06-02
UPDATE AD_Process
SET Description='Exportiert historische Lieferscheine als JSON für externe Systeme',
    Help=E'Exportiert historische Lieferscheine als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur den Lieferschein mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Lieferscheine dieser Bestellung\n- BPartnerValue: Nur Lieferscheine dieses Geschäftspartners (Suchschlüssel)\n- ShipmentDateGE: Nur Lieferscheine ab diesem Bewegungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
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
    Help=E'Exportiert historische Lieferscheine als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur den Lieferschein mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Lieferscheine dieser Bestellung\n- BPartnerValue: Nur Lieferscheine dieses Geschäftspartners (Suchschlüssel)\n- ShipmentDateGE: Nur Lieferscheine ab diesem Bewegungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585488 AND AD_Language IN ('de_DE', 'de_CH')
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
-- Main record (base language DE) = German; en_US Trl = English
-- ============================================================

-- 2026-06-03
-- Limit
UPDATE AD_Process_Para SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543239, 543242);

UPDATE AD_Process_Para_Trl SET Description='Maximum number of records to return. Default and maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543239, 543242) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543239, 543242) AND AD_Language IN ('de_DE', 'de_CH');

-- Offset
UPDATE AD_Process_Para SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543240, 543243);

UPDATE AD_Process_Para_Trl SET Description='Number of records to skip for pagination. Default: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543240, 543243) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543240, 543243) AND AD_Language IN ('de_DE', 'de_CH');

-- Existing parameters: German on main record, English on en_US Trl
UPDATE AD_Process_Para SET Description='Externe ID des Datensatzes. Standard: -1 (alle zurückgeben)', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (542970, 542967);

UPDATE AD_Process_Para_Trl SET Description='External ID of the record. Default: -1 (return all)', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (542970, 542967) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Externe ID des Datensatzes. Standard: -1 (alle zurückgeben)', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (542970, 542967) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Nur Datensätze dieser Bestellung zurückgeben', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543062, 543067);

UPDATE AD_Process_Para_Trl SET Description='Return only records for this order', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543062, 543067) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Nur Datensätze dieser Bestellung zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543062, 543067) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Suchschlüssel des Geschäftspartners', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543065, 543068);

UPDATE AD_Process_Para_Trl SET Description='Search key of the business partner', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543065, 543068) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543065, 543068) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Nur Lieferscheine ab diesem Bewegungsdatum zurückgeben', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543066;

UPDATE AD_Process_Para_Trl SET Description='Return only shipments with movement date on or after this date', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543066 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Nur Lieferscheine ab diesem Bewegungsdatum zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543066 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Nur Rechnungen ab diesem Rechnungsdatum zurückgeben', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543069;

UPDATE AD_Process_Para_Trl SET Description='Return only invoices with invoice date on or after this date', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543069 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Nur Rechnungen ab diesem Rechnungsdatum zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543069 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Externe Referenz des Geschäftspartners im externen System', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543070, 543072);

UPDATE AD_Process_Para_Trl SET Description='External reference of the business partner in the external system', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543070, 543072) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Externe Referenz des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543070, 543072) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Suchschlüssel des Geschäftspartners im externen System', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543071, 543073);

UPDATE AD_Process_Para_Trl SET Description='Search key of the business partner in the external system', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543071, 543073) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543071, 543073) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Basis-Dokumenttyp-Filter', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543074, 543075);

UPDATE AD_Process_Para_Trl SET Description='Base document type filter', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543074, 543075) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Basis-Dokumenttyp-Filter', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543074, 543075) AND AD_Language IN ('de_DE', 'de_CH');
