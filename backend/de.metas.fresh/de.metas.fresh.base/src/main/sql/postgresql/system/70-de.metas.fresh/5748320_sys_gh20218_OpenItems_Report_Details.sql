/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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

CREATE FUNCTION de_metas_endcustomer_fresh_reports.OpenItems_Report_Details(p_ad_org_id             numeric,
                                                                            p_c_bpartner_id         numeric,
                                                                            p_issotrx               character varying,
                                                                            p_daysdue               numeric,
                                                                            p_invoicecollectiontype character varying,
                                                                            p_startdate             date,
                                                                            p_enddate               date,
                                                                            p_reference_date        date,
                                                                            p_passforpayment        character varying,
                                                                            p_switchdate            character varying)
    RETURNS TABLE
            (
                iso_code          character,
                documentno        character varying,
                dateinvoiced      date,
                dateacct          date,
                netdays           numeric,
                discountdays      numeric,
                duedate           date,
                daysdue           integer,
                discountdate      date,
                grandtotal        numeric,
                paidamt           numeric,
                openamt           numeric,
                value             character varying,
                ExternalID        character varying,
                name              character varying,
                passforpayment    boolean,
                grandtotalconvert numeric,
                openamtconvert    numeric,
                main_currency     character
            )
    STABLE
    LANGUAGE sql
AS
$$

SELECT oi.CurrencyCode     AS iso_code,
       oi.DocumentNo,
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
       oi.ExternalID,
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
;
