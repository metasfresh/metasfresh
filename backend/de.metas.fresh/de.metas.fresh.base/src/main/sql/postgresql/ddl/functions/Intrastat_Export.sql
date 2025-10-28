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
       TO_CHAR(weight, 'FM9G999D000')                                   AS NetMass,
       TO_CHAR(movementqty, 'FM9G999D000')                              AS SupplementaryUnits,
       TO_CHAR(grandtotal, 'FM9G999D00')                                AS InvoiceValue,
       TO_CHAR(grandtotal, 'FM9G999D00')                                AS StatisticalValue,
       CASE WHEN p_IntrastatDeclarationType = 'Export' THEN vataxid END AS RecipientVATNo
FROM de_metas_endcustomer_fresh_reports.M_InOut_V
WHERE (C_Period_ID = p_C_Period_ID OR p_C_Period_ID = -1)
  AND (c_year_id = p_c_year_id OR p_c_year_id = -1)


$$
    LANGUAGE sql STABLE
;
