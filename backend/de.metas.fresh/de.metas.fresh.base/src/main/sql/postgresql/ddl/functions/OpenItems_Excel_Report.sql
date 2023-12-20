DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.openitems_excel_report(numeric,
                                                                                  numeric,
                                                                                  varchar,
                                                                                  numeric,
                                                                                  varchar,
                                                                                  date,
                                                                                  date,
                                                                                  date,
                                                                                  varchar,
                                                                                  varchar)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.openitems_excel_report(ad_org_id             numeric,
                                                                          c_bpartner_id         numeric,
                                                                          issotrx               character varying,
                                                                          daysdue               numeric,
                                                                          invoicecollectiontype character varying,
                                                                          startdate             date,
                                                                          enddate               date,
                                                                          reference_date        date,
                                                                          passforpayment        character varying,
                                                                          switchdate            character varying)
    RETURNS TABLE
            (
                C_Invoice_ID   character varying,
                Date           date,
                Customer_No    character varying,
                Customer_Name  character varying,
                Total          numeric,
                IsPaid         numeric,
                Open           numeric,
                Currency       character,
                DiscountAmt    numeric,
                Discount_until date,
                netdays        numeric,
                Net_until      date,
                daysdue        integer,
                passforpayment character varying
            )
    STABLE
    LANGUAGE sql
AS
$$

SELECT x.DocumentNo   AS C_Invoice_ID,
       x.DateInvoiced AS Date,
       x.value        AS Customer_No,
       x.name         AS Customer_Name,
       x.grandtotal   AS Total,
       x.paidamt      AS IsPaid,
       x.openamt      AS Open,
       x.iso_code     AS Currency,
       x.discountdays AS DiscountAmt,
       x.discountdate AS Discount_until,
       x.netdays,
       x.duedate      AS Net_until,
       x.daysdue,
       CASE
           WHEN x.passforpayment THEN 'Ja'
                                 ELSE 'Nein'
       END            AS passforpayment
FROM de_metas_endcustomer_fresh_reports.OpenItems_Report_Details(ad_org_id,
                                                                 c_bpartner_id,
                                                                 issotrx,
                                                                 daysdue,
                                                                 invoicecollectiontype,
                                                                 startdate,
                                                                 enddate,
                                                                 reference_date,
                                                                 passforpayment,
                                                                 switchdate) x
ORDER BY x.iso_code,
         x.Name,
         x.Value,
         x.DateInvoiced,
         x.DocumentNo
$$
;

ALTER FUNCTION de_metas_endcustomer_fresh_reports.openitems_excel_report(numeric, numeric, varchar, numeric, varchar, date, date, date, varchar, varchar) OWNER TO metasfresh
;