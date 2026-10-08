-- gh#28336: Update Intrastat_Export function to support both Export and Import directions.
-- Now queries from Intrastat_Report_V (instead of M_InOut_V) and filters by issotrx.
-- Export (issotrx='Y'): sales shipments, CountryOfOrigin = org/warehouse country (AT)
-- Import (issotrx='N'): purchase receipts, CountryOfOrigin = product rawmaterialorigin or vendor country

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
       CASE
           WHEN p_IntrastatDeclarationType = 'Export' THEN deliveredFromCountry
           ELSE COALESCE(OriginCountry, deliveryCountry)
       END                                                              AS CountryOfOrigin,
       p_IntrastaNatureOfTransaction                                    AS IntrastaNatureOfTransaction,
       TO_CHAR(weight, 'FM9999999D000')                                  AS NetMass,
       TO_CHAR(movementqty, 'FM9999999D000')                             AS SupplementaryUnits,
       TO_CHAR(grandtotal, 'FM9999999D00')                               AS InvoiceValue,
       TO_CHAR(grandtotal, 'FM9999999D00')                               AS StatisticalValue,
       CASE WHEN p_IntrastatDeclarationType = 'Export' THEN vataxid END AS "Recipient-VAT-No"
FROM de_metas_endcustomer_fresh_reports.Intrastat_Report_V
WHERE (C_Period_ID = p_C_Period_ID OR p_C_Period_ID = -1)
  AND (c_year_id = p_c_year_id OR p_c_year_id = -1)
  AND issotrx = CASE WHEN p_IntrastatDeclarationType = 'Export' THEN 'Y' ELSE 'N' END


$$
    LANGUAGE sql STABLE
;
