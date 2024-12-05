
DROP FUNCTION IF EXISTS sales_invoice_excel_report(date,
                                                   date)
;


CREATE FUNCTION sales_invoice_excel_report(p_DateInvoiced date,
                                           p_DeliveryDate date)
    RETURNS TABLE
            (
                DocTypeName     character varying,
                ProductName     character varying,
                ProductCategory character varying,
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
       x.ProductName     AS ProductName,
       x.ProductCategory AS ProductCategory,
       x.BPName          AS BPName,
       x.BPGroupName     AS BPGroupName,
       x.SalesRep_Name   AS SalesRep_Name,
       x.DeliveryDate    AS DeliveryDate,
       x.DateInvoiced    AS DateInvoiced,
       x.InvoicedQty     AS InvoicedQty,
       x.LineNetAmt      AS LineNetAmt

FROM rv_sales_invoice_report x
    WHERE (p_DateInvoiced IS NULL OR p_DateInvoiced = x.DateInvoiced)
    AND (p_DeliveryDate IS NULL OR p_DeliveryDate = x.DeliveryDate)

$$
;

ALTER FUNCTION sales_invoice_excel_report(date, date) OWNER TO metasfresh
;