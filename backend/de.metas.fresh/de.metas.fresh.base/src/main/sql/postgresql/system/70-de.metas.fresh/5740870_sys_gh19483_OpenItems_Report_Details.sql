DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report_Details(IN p_AD_Org_ID             numeric,
                                                                                    IN p_C_BPartner_ID         numeric,
                                                                                    IN p_IsSOTrx               character varying,
                                                                                    IN p_DaysDue               numeric,
                                                                                    IN p_InvoiceCollectionType character varying,
                                                                                    IN p_StartDate             date,
                                                                                    IN p_EndDate               date,
                                                                                    IN p_Reference_Date        date,
                                                                                    IN p_PassForPayment        character varying,
                                                                                    IN p_switchDate            character varying)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.OpenItems_Report_Details(IN p_AD_Org_ID             numeric,
                                                                                       IN p_C_BPartner_ID         numeric,
                                                                                       IN p_IsSOTrx               character varying,
                                                                                       IN p_DaysDue               numeric,
                                                                                       IN p_InvoiceCollectionType character varying,
                                                                                       IN p_StartDate             date,
                                                                                       IN p_EndDate               date,
                                                                                       IN p_Reference_Date        date,
                                                                                       IN p_PassForPayment        character varying,
                                                                                       IN p_switchDate            character varying)
    RETURNS TABLE
            (
                iso_code          character(3),
                DocumentNo        character varying(30),
                POReference       character varying(30),
                DateInvoiced      date,
                DateAcct          date,
                NetDays           numeric,
                DiscountDays      numeric(10, 0),
                DueDate           date,
                DaysDue           integer,
                DiscountDate      date,
                GrandTotal        numeric,
                PaidAmt           numeric,
                OpenAmt           numeric,
                Value             character varying(40),
                Name              character varying(60),
                PassForPayment    boolean,
                GrandTotalConvert numeric,
                OpenAmtConvert    numeric,
                main_currency     character(3)
            )
AS
$$

SELECT oi.CurrencyCode     AS iso_code,
       oi.DocumentNo,
       oi.POReference,
       oi.DateInvoiced::date,
       oi.DateAcct::date,
       oi.NetDays,
       oi.DiscountDays,
       oi.DueDate::date,
       oi.DaysDue,
       oi.DiscountDate::date,
       oi.GrandTotal,
       oi.PaidAmt,
       oi.OpenAmt,
       oi.BPValue          AS Value,
       oi.BPName           AS Name,
       oi.IsInPaySelection AS PassForPayment,
       oi.GrandTotalConvert,
       oi.OpenAmtConvert,
       oi.main_iso_code

FROM de_metas_endcustomer_fresh_reports.OpenItems_Report(p_Reference_Date, p_switchDate) oi


WHERE oi.AD_Org_ID = (CASE WHEN p_AD_Org_ID IS NULL THEN oi.AD_Org_ID ELSE p_AD_Org_ID END)
  AND oi.IsSOTrx = (CASE WHEN p_IsSOTrx IS NULL THEN oi.IsSOTrx ELSE p_IsSOTrx END)
  AND oi.C_BPartner_ID = (CASE WHEN p_C_BPartner_ID IS NULL THEN oi.C_BPartner_ID ELSE p_C_BPartner_ID END)
  AND (p_DaysDue IS NULL OR p_DaysDue <= 0 OR oi.daysdue >= p_DaysDue)
  AND COALESCE(oi.InvoiceCollectionType, '') =
      (CASE WHEN COALESCE(p_InvoiceCollectionType, '') = '' THEN COALESCE(oi.InvoiceCollectionType, '') ELSE p_InvoiceCollectionType END)
  AND (
    --09738 if flag is set then switches the given date to dateacct instead of dateinvoiced
    CASE
        WHEN p_switchDate = 'Y' THEN
            (oi.DateAcct >= (CASE WHEN p_StartDate::date IS NULL THEN oi.DateAcct ELSE p_StartDate::date END)
                AND oi.DateAcct <= (CASE WHEN p_EndDate::date IS NULL THEN oi.DateAcct ELSE p_EndDate::date END))
                                ELSE
            (oi.DateInvoiced >= (CASE WHEN p_StartDate::date IS NULL THEN oi.DateInvoiced ELSE p_StartDate::date END)
                AND oi.DateInvoiced <= (CASE WHEN p_EndDate::date IS NULL THEN oi.DateInvoiced ELSE p_EndDate::date END))
    END
    )
  -- 08394: If Flag is not set, only display invoices that are not part of a processed PaySelection
  AND (p_PassForPayment = 'Y' OR NOT oi.IsInPaySelection)
$$
    LANGUAGE sql STABLE
;

