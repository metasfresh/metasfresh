DROP FUNCTION IF EXISTS getopeninvoices(
    numeric, -- 1 - c_bpartner_id
    numeric, -- 2 - c_currency_id
    numeric, -- 3 - ad_client_id
    numeric, -- 4 - ad_org_id
    timestamp WITHOUT TIME ZONE, -- 5 - date
    numeric, -- 6 - onlyInvoicesSelectionId
    numeric -- 7 - additionalInvoicesSelectionId
)
;

CREATE OR REPLACE FUNCTION getopeninvoices(
    p_c_bpartner_id                 numeric = NULL, -- 1
    p_c_currency_id                 numeric = NULL, -- 2
    p_ad_client_id                  numeric = NULL, -- 3
    p_ad_org_id                     numeric = NULL, -- 4
    p_date                          timestamp WITHOUT TIME ZONE = NOW(), -- 5
    p_onlyInvoicesSelectionId       numeric = NULL, -- 6
    p_additionalInvoicesSelectionId numeric = NULL -- 7
)
    RETURNS TABLE
            (
                ad_org_id                   numeric,
                ad_client_id                numeric,
                c_invoice_id                numeric,
                c_bpartner_id               numeric,
                isprepayorder               character(1),
                docno                       character varying,
                invoicedate                 timestamp WITHOUT TIME ZONE,
                doctype                     character varying,
                C_DocType_ID                numeric,
                bpartnername                character varying,
                iso_code                    character(3),
                ConvertTo_Currency_ID       numeric,
                ConvertTo_Currency_ISO_Code character(3),
                orig_total                  numeric,
                conv_total                  numeric,
                conv_open                   numeric,
                discount                    numeric,
                multiplierap                numeric,
                multiplier                  numeric,
                poreference                 character varying,
                dateacct                    timestamp WITHOUT TIME ZONE,
                C_ConversionType_ID         numeric,
                evaluation_date             timestamp WITHOUT TIME ZONE
            )
AS
$BODY$
    --
WITH bpartners AS (
    SELECT p_c_bpartner_id::integer AS C_BPartner_ID
    UNION ALL
    SELECT r.C_BPartner_ID
    FROM C_BP_Relation r
    WHERE r.C_BpartnerRelation_ID = p_c_bpartner_id
      AND r.isPayFrom = 'Y'
      AND r.isActive = 'Y'
)
     --
SELECT i.AD_Org_ID                                                                                                                       AS ad_org_id,
       i.AD_Client_ID                                                                                                                    AS ad_client_id,
       i.C_Invoice_ID                                                                                                                    AS c_invoice_id,
       i.C_BPartner_ID                                                                                                                   AS c_bpartner_id,
       i.isPrePayOrder                                                                                                                   AS isprepayorder,
       i.DocNo                                                                                                                           AS docno,
       i.Date                                                                                                                            AS InvoiceDate,
       d.Name                                                                                                                            AS DocType,
       i.C_DocType_ID                                                                                                                    AS c_doctype_id,
       bp.name                                                                                                                           AS BPartnerName,
       currency.ISO_Code                                                                                                                 AS iso_code,
       i.ConvertTo_Currency_ID                                                                                                           AS ConvertTo_Currency_ID,
       convertToCurrency.ISO_Code                                                                                                        AS ConvertTo_Currency_ISO_Code,
       i.Total                                                                                                                           AS Orig_Total,
       currencyConvert(i.Total, i.C_Currency_ID, i.ConvertTo_Currency_ID, p_date, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID)    AS Conv_Total,
       currencyConvert(i.open, i.C_Currency_ID, i.ConvertTo_Currency_ID, p_date, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID)     AS Conv_Open,
       currencyConvert(i.discount, i.C_Currency_ID, i.ConvertTo_Currency_ID, i.Date, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID) AS Discount,
       i.multiplierAP                                                                                                                    AS multiplierAP,
       i.multiplier                                                                                                                      AS multiplier,
       i.POReference                                                                                                                     AS poreference,
       i.DateAcct                                                                                                                        AS dateacct, -- task 09643: separate transaction date form accounting date
       i.c_conversiontype_id                                                                                                             AS c_conversiontype_id,
       p_date                                                                                                                            AS evaluation_date
FROM (
         --
         -- Invoices
         (
             SELECT i.C_Invoice_ID
                  , i.DocumentNo                                                       AS docno
                  , invoiceOpen(i.C_Invoice_ID, i.C_InvoicePaySchedule_ID)             AS open
                  , invoiceDiscount(i.C_Invoice_ID, p_date, i.C_InvoicePaySchedule_ID) AS discount
                  , i.GrandTotal                                                       AS total
                  , i.DateInvoiced                                                     AS date
                  , i.C_Currency_ID
                  , i.C_ConversionType_ID
                  , COALESCE(p_c_currency_id, i.C_Currency_ID)                         AS ConvertTo_Currency_ID
                  , i.AD_Client_ID
                  , i.AD_Org_ID
                  , i.multiplier
                  , i.multiplierAP
                  , i.C_BPartner_ID
                  , i.C_DocType_ID
                  , 'N'::character varying                                             AS IsPrePayOrder
                  , i.POReference
                  , i.DateAcct
             FROM C_Invoice_v i
             WHERE IsPaid = 'N'
               AND Processed = 'Y'
               -- Only invoices:
               AND (p_onlyInvoicesSelectionId IS NULL OR EXISTS(SELECT 1 FROM t_selection s WHERE s.ad_pinstance_id = p_onlyInvoicesSelectionId AND s.t_selection_id = i.c_invoice_id))
               AND (
                     (p_c_bpartner_id IS NULL AND p_additionalInvoicesSelectionId IS NULL) -- no C_BPartner_ID nor additionalInvoicesSelectionId is set
                     OR i.C_BPartner_ID = ANY (ARRAY(SELECT bp.C_BPartner_ID FROM bpartners bp)) -- NOTE: we transform subquery to scalar for performances
                 -- Include additional invoices if any:
                     OR EXISTS(SELECT 1 FROM t_selection s WHERE s.ad_pinstance_id = p_additionalInvoicesSelectionId AND s.t_selection_id = i.c_invoice_id)
                 )
         )
     ) i
         INNER JOIN C_DocType d ON (i.C_DocType_ID = d.C_DocType_ID)
         INNER JOIN C_Currency currency ON (i.C_Currency_ID = currency.C_Currency_ID)
         INNER JOIN C_Currency convertToCurrency ON (i.ConvertTo_Currency_ID = convertToCurrency.C_Currency_ID)
         INNER JOIN C_BPartner bp ON (i.C_BPartner_ID = bp.C_BPartner_ID)
WHERE TRUE
  AND (p_c_currency_id IS NULL OR i.c_currency_id = p_c_currency_id)
  AND (p_ad_client_id IS NULL OR p_ad_client_id = 1000000 OR i.ad_client_id = p_ad_client_id) -- Client
  AND (p_ad_org_id IS NULL OR p_ad_org_id = 1000000 OR i.AD_Org_ID = p_ad_org_id)             -- Organisation
ORDER BY i.Date, i.DocNo
    ;
$BODY$
    LANGUAGE sql STABLE
                 ROWS 1000
;


