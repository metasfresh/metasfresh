-- gh#28014: Two fixes for the Intrastat RTIC export:
--
-- 1) Remove thousand separator (G) from TO_CHAR patterns in Intrastat_Export.
--    Austrian RTIC cannot parse numbers with period as thousand separator.
--    In German locale, TO_CHAR 'G' = period, so '1.442,500' is misinterpreted by RTIC.
--    Replacing 'FM9G999Dxxx' with 'FM9999999Dxxx' outputs '1442,500' (no thousand separator).
--
-- 2) Change CSV delimiter from tab to semicolon.
--    RTIC expects semicolon-delimited input. The original migration (5787490) set tab,
--    but the customer must use semicolon for RTIC upload.

DROP FUNCTION IF EXISTS report.Intrastat_Export(p_c_year_id                 numeric,
                                                p_C_Period_ID               numeric,
                                                IntrastatDeclarationType    character varying(6),
                                                IntrastaNatureOfTransaction character varying(2))
;


CREATE OR REPLACE FUNCTION report.Intrastat_Export(p_c_year_id                   numeric,
                                                   p_C_Period_ID                 numeric,
                                                   p_IntrastatDeclarationType    character varying(6),
                                                   p_IntrastaNatureOfTransaction character varying(2) DEFAULT '11')

    RETURNS TABLE
            (
                CNCode                        Character Varying,
                GoodsDescription              Character Varying,
                CountryDestinationConsignment Character Varying,
                CountryOfOrigin               Character Varying,
                IntrastaNatureOfTransaction   Character Varying,
                NetMass                       Character Varying,
                SupplementaryUnits            Character Varying,
                InvoiceValue                  Character Varying,
                StatisticalValue              Character Varying,
                "Recipient-VAT-No"            Character Varying
            )
AS
$$

SELECT CustomsTariff                                                    AS CNCode,
       productName                                                      AS GoodsDescription,
       deliveryCountry                                                  AS CountryDestinationConsignment,
       deliveredFromCountry                                             AS CountryOfOrigin,
       p_IntrastaNatureOfTransaction                                    AS IntrastaNatureOfTransaction,
       TO_CHAR(weight, 'FM9999999D000')                                  AS NetMass,
       TO_CHAR(movementqty, 'FM9999999D000')                             AS SupplementaryUnits,
       TO_CHAR(grandtotal, 'FM9999999D00')                               AS InvoiceValue,
       TO_CHAR(grandtotal, 'FM9999999D00')                               AS StatisticalValue,
       CASE WHEN p_IntrastatDeclarationType = 'Export' THEN vataxid END AS RecipientVATNo
FROM de_metas_endcustomer_fresh_reports.M_InOut_V
WHERE (C_Period_ID = p_C_Period_ID OR p_C_Period_ID = -1)
  AND (c_year_id = p_c_year_id OR p_c_year_id = -1)


$$
    LANGUAGE sql STABLE
;

-- Change CSV delimiter from tab to semicolon for RTIC compatibility
UPDATE AD_Process
SET CSVFieldDelimiter = ';'
WHERE AD_Process_ID = 585508
  AND CSVFieldDelimiter = E'\t'
;
