DROP FUNCTION IF EXISTS sales_invoice_excel_report(date,
                                                   date)
;


DROP FUNCTION IF EXISTS sales_invoice_excel_report(date,
                                                   date,
                                                   date,
                                                   date)
;

CREATE FUNCTION sales_invoice_excel_report(p_DateInvoicedFrom date,
                                           p_DateInvoicedTo   date,
                                           p_DeliveryDateFrom date,
                                           p_DeliveryDateTo   date)
    RETURNS TABLE
            (
                DocTypeName     character varying,
                ProductValue    character varying,
                ProductName     character varying,
                Postal          character varying,
                ProductCategory character varying,
                BPValue         character varying,
                BPName          character varying,
                BPGroupName     character varying,
                SalesRep_Name   character varying,
                DeliveryDate    date,
                DateInvoiced    date,
                InvoicedQty     numeric,
                LineNetAmt      numeric
            )
    STABLE
    LANGUAGE sql
AS
$$

SELECT x.DocTypeName     AS DocTypeName,
       x.ProductValue    AS ProductValue,
       x.ProductName     AS ProductName,
       x.Postal          AS Postal,
       x.ProductCategory AS ProductCategory,
       x.BPValue         AS BPValue,
       x.BPName          AS BPName,
       x.BPGroupName     AS BPGroupName,
       x.SalesRep_Name   AS SalesRep_Name,
       x.DeliveryDate    AS DeliveryDate,
       x.DateInvoiced    AS DateInvoiced,
       x.InvoicedQty     AS InvoicedQty,
       x.LineNetAmt      AS LineNetAmt

FROM rv_sales_invoice_report x
WHERE (p_DateInvoicedFrom <= x.DateInvoiced)
  AND (p_DateInvoicedTo >= x.DateInvoiced)
  AND (p_DeliveryDateFrom <= x.DeliveryDate)
  AND (p_DeliveryDateTo >= x.DeliveryDate)

$$
;

ALTER FUNCTION sales_invoice_excel_report(date, date, date, date) OWNER TO metasfresh
;