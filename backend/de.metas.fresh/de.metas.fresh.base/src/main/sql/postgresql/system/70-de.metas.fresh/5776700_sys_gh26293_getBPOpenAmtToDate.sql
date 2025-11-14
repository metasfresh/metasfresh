DROP FUNCTION IF EXISTS getBPOpenAmtToDate(numeric, numeric, date, numeric, numeric, text, text);

CREATE OR REPLACE FUNCTION getBPOpenAmtToDate(p_AD_Client_ID numeric,
                                              p_AD_Org_ID numeric,
                                              p_Date date,
                                              p_C_BPartner_id numeric,
                                              p_C_Currency_ID numeric,
                                              p_UseDateAcct text = 'Y',
                                              p_IsSOTrx text = NULL)
    RETURNS numeric
AS
$$
DECLARE
    v_sum       numeric;
    v_orgNotSet boolean;
    v_dateType  text;
BEGIN
    v_orgNotSet := COALESCE(p_AD_Org_ID, 0) <= 0;

    v_dateType := CASE
                      WHEN p_UseDateAcct = 'Y' THEN 'A'
                                               ELSE 'T'
                  END;

    WITH t AS
             (SELECT i.C_Invoice_ID,
                     ips.C_InvoicePaySchedule_ID
              FROM C_Invoice i
                       LEFT OUTER JOIN C_InvoicePaySchedule ips
                                       ON i.C_Invoice_ID = ips.C_Invoice_ID
                                           AND ips.isvalid = 'Y'
                                           AND ips.isActive = 'Y'
              WHERE TRUE
                AND i.issotrx = COALESCE(p_isSoTrx, i.issotrx)
                AND (CASE
                         WHEN p_UseDateAcct = 'Y' THEN i.dateacct
                                                  ELSE i.dateinvoiced
                     END) <= p_Date
                AND i.DocStatus IN ('CO', 'CL')
                AND i.c_bpartner_id = p_c_bpartner_id
                AND i.AD_CLient_ID = p_AD_Client_ID
                AND (v_orgNotSet OR i.AD_Org_ID = p_AD_Org_ID))

    SELECT INTO v_sum sum(
                              (SELECT openamt
                               FROM invoiceOpenToDate(
                                       p_C_Invoice_ID := t.C_Invoice_ID,
                                       p_c_invoicepayschedule_id := COALESCE(t.C_InvoicePaySchedule_ID, 0::numeric),
                                       p_DateType := v_dateType,
                                       p_Date := p_Date,
                                       p_Result_Currency_ID := p_C_Currency_ID
                                    )
                              )
                      )


    FROM t;


    RETURN coalesce(v_sum, 0);
END ;
$$
    LANGUAGE plpgsql VOLATILE;



COMMENT ON FUNCTION getBPOpenAmtToDate(numeric, numeric, date, numeric, numeric, text, text) IS
    '  TEST
    SELECT value,
           C_Bpartner_ID,
           getBPOpenAmtToDate(1000000,
                              1000000,
                              ''9999-01-01'' :: date,
                              C_BPartner_ID,
                              318,
                               ''Y'',
                               ''Y'')

     FROM C_Bpartner ; ';

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

/* TEST:

SELECT value,
       C_Bpartner_ID,
       getBPOpenAmtToDate(1000000,
                          1000000,
                          '9999-01-01' :: date,
                          C_BPartner_ID,
                          318,
                           'Y',
                           'Y')

 FROM C_Bpartner ;

*/
