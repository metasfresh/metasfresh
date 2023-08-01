/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details_local_currency_conversion(numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details_local_currency_conversion(IN p_C_invoice_id numeric)
    RETURNS TABLE
            (
                c_invoice_id                numeric(10),
                IsPrintLocalTaxReporting    boolean,
                local_iso_code              bpchar(3),
                conversion_rate             numeric,
                conversion_date             timestamp with time zone,
                local_taxbaseamt            numeric,
                local_taxamt                numeric
            )
AS
$$

WITH records as (
    SELECT it.C_Invoice_ID,
           CASE
               WHEN (i.m_inout_id IS NULL AND tr.taxreportingratebase = 'S') OR i.c_order_id IS NULL   THEN FALSE --aggregated ICs aren't printed
               WHEN tr.taxreportingratebase IS NULL                THEN FALSE --if tax reporting has no entry in doctype
               WHEN i.isprintlocalcurrencyinfo = 'N'               THEN FALSE
               WHEN bp.isprintlocalcurrencyinfo = 'N'
                   AND   i.isprintlocalcurrencyinfo <> 'Y'        THEN FALSE
                                                                   ELSE TRUE
           END             AS IsPrintLocalTaxReporting,
           cu.iso_code AS local_iso_code,
           c.c_currency_id AS currency_from,
           cu.c_currency_id AS currency_to,
           CASE
               WHEN (i.m_inout_id IS NULL AND tr.taxreportingratebase = 'S') OR i.c_order_id IS NULL THEN NULL --aggregated ICs aren't printed
               WHEN tr.taxreportingratebase IS NULL THEN NULL --if tax reporting has no entry in doctype
                                                                 ELSE
                                                                     getlocaltaxreportingconversionratedate(
                                                                             tr.taxreportingratebase,
                                                                             tr.c_calendar_id,
                                                                             i.dateinvoiced,
                                                                             m.movementdate)
           END AS conversion_date,
           tr.c_conversiontype_id,
           i.ad_client_id,
           i.ad_org_id,
           SUM(TaxBaseAmt) AS TaxBaseAmt,
           SUM(TaxAmt) AS TaxAmt

    FROM C_InvoiceTax it
             INNER JOIN C_Invoice i ON it.C_Invoice_ID = i.C_Invoice_ID
             LEFT JOIN m_inout m ON i.m_inout_id = m.m_inout_id
             INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
             INNER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID
             INNER JOIN c_location l ON i.c_bpartner_location_value_id = l.c_location_id
             INNER JOIN c_country co ON COALESCE(i.c_tax_departure_country_id,l.c_country_id) = co.c_country_id
             INNER JOIN c_currency cu ON co.c_currency_id = cu.c_currency_id
             LEFT JOIN c_doctype_taxreporting tr ON i.c_doctype_id = tr.c_doctype_id AND co.c_country_id = tr.c_country_id
    WHERE it.C_Invoice_ID = p_C_invoice_id
    GROUP BY it.C_Invoice_ID,
             i.c_order_id,
             i.isprintlocalcurrencyinfo,
             i.dateinvoiced,
             i.ad_client_id,
             i.ad_org_id,
             i.m_inout_id,
             bp.isprintlocalcurrencyinfo,
             c.c_currency_id,
             cu.iso_code,
             cu.c_currency_id,
             tr.taxreportingratebase,
             tr.c_calendar_id,
             tr.c_country_id,
             tr.c_conversiontype_id,
             m.movementdate
)

SELECT
    C_Invoice_ID,
    IsPrintLocalTaxReporting,
    local_iso_code,
    CASE
        WHEN conversion_date IS NULL THEN NULL
                                     ELSE
                                         currencyrate(currency_from,
                                                      currency_to,
                                                      conversion_date,
                                                      c_conversiontype_id,
                                                      ad_client_id,
                                                      ad_org_id)
    END AS conversion_rate,
    conversion_date,
    CASE
        WHEN conversion_date IS NULL THEN NULL
                                     ELSE
                                         currencyconvert(TaxBaseAmt,
                                                         currency_from,
                                                         currency_to,
                                                         conversion_date,
                                                         c_conversiontype_id,
                                                         ad_client_id,
                                                         ad_org_id)
    END AS local_taxbaseamt,
    CASE
        WHEN conversion_date IS NULL THEN NULL
                                     ELSE
                                         currencyconvert(TaxAmt,
                                                         currency_from,
                                                         currency_to,
                                                         conversion_date,
                                                         c_conversiontype_id,
                                                         ad_client_id,
                                                         ad_org_id)
    END AS local_taxamt

FROM records

$$

    LANGUAGE sql STABLE
;

