CREATE FUNCTION datev_export_aggregat(p_datev_export_id numeric)
    RETURNS TABLE
            (
                "Umsatz (ohne Soll-/Haben-Kennzeichen)" numeric,
                "Soll-/Haben-Kennzeichen"               character,
                "Währung"                               character varying,
                konto                                   character varying,
                gegenkonto                              integer,
                belegdatum                              date,
                belegfeld1                              character varying,
                steuersatz                              numeric,
                land                                    character,
                steuer                                  character varying,
                referenz                                character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT SUM(ex.amt)               AS "Umsatz (ohne Soll-/Haben-Kennzeichen)",
       ex.DebitOrCreditIndicator AS "Soll-/Haben-Kennzeichen",
       ex.currency               AS "Währung",
       ex.dr_account             AS Konto,
       CASE
           WHEN c.countrycode = 'DE' THEN 4400
           WHEN c.countrycode = 'CH' THEN 4120
                                     ELSE 4320
       END                       AS Gegenkonto,
       ex.dateacct::date         AS belegdatum,
       ex.documentno             AS belegfeld1,
       tax.rate                  AS steuersatz,
       c.countrycode             AS Land,
       tax.name                  AS Steuer,
       ci.poreference            AS referenz

FROM datev_exportline ex
         LEFT JOIN fact_acct acc ON acc.fact_acct_id = ex.fact_acct_id
         LEFT JOIN c_Tax tax ON acc.c_tax_id = tax.c_tax_id
         LEFT JOIN c_country c ON tax.to_country_id = c.c_country_id
         LEFT JOIN c_invoice ci ON ex.c_invoice_id = ci.c_invoice_id

WHERE ex.datev_export_id = p_datev_export_id

GROUP BY ex.DebitOrCreditIndicator, ex.currency, ex.dr_account, ex.dateacct, ex.documentno, tax.rate, c.countrycode, tax.name, ci.poreference
$$
;

ALTER FUNCTION datev_export_aggregat(numeric) OWNER TO metasfresh
;

