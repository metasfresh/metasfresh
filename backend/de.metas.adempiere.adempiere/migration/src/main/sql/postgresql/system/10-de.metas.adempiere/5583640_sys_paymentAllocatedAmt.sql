/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2021 metas GmbH
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

-- DROP FUNCTION IF EXISTS paymentAllocatedAmt(numeric);

CREATE OR REPLACE FUNCTION paymentAllocatedAmt(p_c_payment_id numeric)
    RETURNS numeric
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_C_Charge_ID                     NUMERIC(10);
    v_Payment_Currency_ID             NUMERIC(10);
    v_FixedConversion_FromCurrency_ID NUMERIC(10);
    v_FixedConversion_Rate            NUMERIC(10);
    v_PayAmt                          NUMERIC := 0;
    v_AllocatedAmt                    NUMERIC := 0;
    v_CurrentAllocatedAmt             NUMERIC := 0;
    alloc                             RECORD;
BEGIN
    --
    --	Get payment info
    SELECT C_Charge_ID,
           PayAmt,
           C_Currency_ID,
           source_currency_id,
           currencyrate
    INTO
        v_C_Charge_ID,
        v_PayAmt,
        v_Payment_Currency_ID,
        v_FixedConversion_FromCurrency_ID,
        v_FixedConversion_Rate
    FROM C_Payment_v -- corrected for AP/AR
    WHERE C_Payment_ID = p_C_Payment_ID;

    --
    --  Payments with Charge are always fully allocated (legacy)
    IF (v_C_Charge_ID IS NOT NULL AND v_C_Charge_ID > 0) THEN
        RETURN v_PayAmt;
    END IF;

    --	Calculate Allocated Amount
    FOR alloc IN (SELECT ah.AD_Client_ID,
                         ah.AD_Org_ID,
                         ah.C_Currency_ID,
                         ah.DateTrx,
                         SUM(al.Amount + al.PaymentWriteOffAmt) AS AllocatedAmt
                  FROM C_AllocationLine al
                           INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                  WHERE al.C_Payment_ID = p_C_Payment_ID
                    AND ah.IsActive = 'Y' AND al.IsActive='Y'
                    -- IMPORTANT: do not filter by DocStatus because if we do then
                    -- the payment testAllocation performed when the allocation is completed will skip that allocation
                    -- so the result will be wrong.
                    -- AND ah.DocStatus IN ('CO', 'CL')
                  GROUP BY ah.AD_Client_ID,
                           ah.AD_Org_ID,
                           ah.C_Currency_ID,
                           ah.DateTrx)
        LOOP
            IF (v_FixedConversion_FromCurrency_ID IS NOT NULL
                AND v_FixedConversion_Rate IS NOT NULL
                AND v_FixedConversion_Rate <> 0
                AND v_FixedConversion_FromCurrency_ID = alloc.C_Currency_ID)
            THEN
                v_CurrentAllocatedAmt := alloc.AllocatedAmt / v_FixedConversion_Rate;
                v_CurrentAllocatedAmt := currencyround(v_CurrentAllocatedAmt, v_Payment_Currency_ID, 'N');
                RAISE DEBUG '[C_Payment_ID=%] Alloc Amt: % (currency:%) => % (currency:%) | FIXED currencyRate=%', p_c_payment_id, alloc.AllocatedAmt, alloc.c_currency_id, v_CurrentAllocatedAmt, v_Payment_Currency_ID, v_FixedConversion_Rate;
            ELSE
                v_CurrentAllocatedAmt := currencyConvert(
                        alloc.AllocatedAmt,
                        alloc.C_Currency_ID,
                        v_Payment_Currency_ID,
                        alloc.DateTrx,
                        NULL,
                        alloc.AD_Client_ID,
                        alloc.AD_Org_ID);
                RAISE DEBUG '[C_Payment_ID=%] Alloc Amt: % (currency:%) => % (currency:%)', p_c_payment_id, alloc.AllocatedAmt, alloc.c_currency_id, v_CurrentAllocatedAmt, v_Payment_Currency_ID;
            END IF;

            v_AllocatedAmt := v_AllocatedAmt + v_CurrentAllocatedAmt;
            RAISE DEBUG '[C_Payment_ID=%] => AllocatedAmt: %', p_c_payment_id, v_AllocatedAmt;
        END LOOP;

    RETURN v_AllocatedAmt;
END;
$$
;
