DROP FUNCTION IF EXISTS getopeninvoices(numeric, numeric, character varying, numeric, timestamp WITHOUT TIME ZONE, numeric, numeric)
;

DROP VIEW IF EXISTS t_getopeninvoices
;

CREATE OR REPLACE VIEW t_getopeninvoices AS
SELECT NULL::numeric                     AS ad_org_id,
       NULL::numeric                     AS ad_client_id,
       NULL::numeric                     AS c_invoice_id,
       NULL::numeric                     AS c_bpartner_id,
       NULL::character(1)                AS isprepayorder,
       NULL::character varying           AS docno,
       NULL::timestamp WITHOUT TIME ZONE AS invoicedate,
       NULL::character varying           AS doctype,
       NULL::numeric                     AS C_DocType_ID,
       NULL::character varying           AS bpartnername,
       NULL::character(3)                AS iso_code,
       NULL::numeric                     AS ConvertTo_Currency_ID,
       NULL::character(3)                AS ConvertTo_Currency_ISO_Code,
       NULL::numeric                     AS orig_total,
       NULL::numeric                     AS conv_total,
       NULL::numeric                     AS conv_open,
       NULL::numeric                     AS discount,
       NULL::numeric                     AS multiplierap,
       NULL::numeric                     AS multiplier,
       NULL::character varying           AS poreference,
       NULL::timestamp WITHOUT TIME ZONE AS dateacct,
       NULL::numeric                     AS C_ConversionType_ID
;

COMMENT ON VIEW t_getopeninvoices IS 'Used as return type in the SQL-function getopeninvoices'
;

CREATE OR REPLACE FUNCTION getopeninvoices(c_bpartner_id    numeric -- 1
                                          , c_currency_id   numeric -- 2
                                          , ismulticurrency character varying -- 3
                                          , ad_org_id       numeric -- 4
                                          , date            timestamp WITHOUT TIME ZONE -- 5
                                          , c_invoice_id    numeric -- 6
                                          , c_order_id      numeric -- 7
)
    RETURNS SETOF t_getopeninvoices
AS
$BODY$
    --
WITH bpartners AS (
    SELECT $1::integer AS C_BPartner_ID
    UNION ALL
    SELECT r.C_BPartner_ID
    FROM C_BP_Relation r
    WHERE r.C_BpartnerRelation_ID = $1
      AND r.isPayFrom = 'Y'
      AND r.isActive = 'Y'
)
     --
SELECT i.AD_Org_ID,
       i.AD_Client_ID,
       i.C_Invoice_ID,
       i.C_BPartner_ID,
       i.isPrePayOrder,
       i.DocNo,
       i.Date                                                                                                                            AS InvoiceDate,
       d.Name                                                                                                                            AS DocType,
       i.C_DocType_ID,
       bp.name                                                                                                                           AS BPartnerName,
       currency.ISO_Code,
       i.ConvertTo_Currency_ID,
       convertToCurrency.ISO_Code                                                                                                        AS ConvertTo_Currency_ISO_Code,
       i.Total                                                                                                                           AS Orig_Total,
       currencyConvert(i.Total, i.C_Currency_ID, i.ConvertTo_Currency_ID, $5, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID)        AS Conv_Total,
       currencyConvert(i.open, i.C_Currency_ID, i.ConvertTo_Currency_ID, $5, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID)         AS Conv_Open,
       currencyConvert(i.discount, i.C_Currency_ID, i.ConvertTo_Currency_ID, i.Date, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID) AS Discount,
       i.multiplierAP,
       i.multiplier,
       i.POReference,
       i.DateAcct, -- task 09643: separate transaction date form accounting date
       i.c_conversiontype_id
FROM (
         --
         -- Invoices
         (
             SELECT i.C_Invoice_ID
                  , DocumentNo                                                   AS docno
                  , invoiceOpen(i.C_Invoice_ID, C_InvoicePaySchedule_ID)         AS open
                  , invoiceDiscount(i.C_Invoice_ID, $5, C_InvoicePaySchedule_ID) AS discount
                  , GrandTotal                                                   AS total
                  , DateInvoiced                                                 AS date
                  , i.C_Currency_ID
                  , C_ConversionType_ID
                  , COALESCE($2, i.C_Currency_ID)                                AS ConvertTo_Currency_ID
                  , i.AD_Client_ID
                  , i.AD_Org_ID
                  , multiplier
                  , multiplierAP
                  , i.C_BPartner_ID
                  , i.C_DocType_ID
                  , 'N'::character varying                                       AS IsPrePayOrder
                  , i.POReference
                  , i.DateAcct
             FROM C_Invoice_v i
             WHERE IsPaid = 'N'
               AND Processed = 'Y'
               AND (
                     ($1 IS NULL AND $6 IS NULL) -- no C_BPartner_ID nor C_Invoice_ID is set
                     OR i.C_BPartner_ID = ANY (array(SELECT bp.C_BPartner_ID FROM bpartners bp)) -- NOTE: we transform subquery to scalar for performances
                 -- Include the invoice which were precisely specified, if any
                     OR (i.C_Invoice_ID = $6)
                 )
         )
     ) i
         INNER JOIN C_DocType d ON (i.C_DocType_ID = d.C_DocType_ID)
         INNER JOIN C_Currency currency ON (i.C_Currency_ID = currency.C_Currency_ID)
         INNER JOIN C_Currency convertToCurrency ON (i.ConvertTo_Currency_ID = convertToCurrency.C_Currency_ID)
         INNER JOIN C_BPartner bp ON (i.C_BPartner_ID = bp.C_BPartner_ID)
WHERE TRUE
  AND (CASE WHEN $3 = 'Y' THEN i.C_Currency_ID ELSE $2 END) = i.C_Currency_ID
  AND ($4 IS NULL OR $4 = 1000000 OR i.AD_Org_ID = $4) -- Organisation
ORDER BY i.Date, i.DocNo
    ;
$BODY$
    LANGUAGE sql STABLE
                 ROWS 1000
;

COMMENT ON FUNCTION getopeninvoices(numeric, numeric, character varying, numeric, timestamp WITHOUT TIME ZONE, numeric, numeric) IS '
* Used in de.mets.paymentallocation.form.Allocation.queryInvoiceTable()
* Uses the view T_GetOpenInvoices as return type'
;


