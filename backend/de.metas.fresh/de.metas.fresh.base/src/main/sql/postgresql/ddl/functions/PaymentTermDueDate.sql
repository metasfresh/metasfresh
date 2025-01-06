/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

DROP FUNCTION IF EXISTS PaymentTermDueDate(numeric,
                                           timestamp with time zone)
;

CREATE FUNCTION PaymentTermDueDate(paymentterm_id numeric,
                                   docdate        timestamp with time zone) RETURNS timestamp with time zone
    LANGUAGE plpgsql
AS
$$
    /*************************************************************************
     * The contents of this file are subject to the Compiere License.  You may
     * obtain a copy of the License at    http://www.compiere.org/license.html
     * Software is on an  "AS IS" basis,  WITHOUT WARRANTY OF ANY KIND, either
     * express or implied. See the License for details. Code: Compiere ERP+CRM
     * Copyright (C) 1999-2001 Jorg Janke, ComPiere, Inc. All Rights Reserved.
     *
     * converted to postgreSQL by Karsten Thiemann (Schaeffer AG),
     * kthiemann@adempiere.org
     *************************************************************************
     * Title:	Get Due timestamp with time zone
     * Description:
     *	Returns the due timestamp with time zone
     * Test:
     *	select paymenttermDueDate(106, now()) from Test; => now()+30 days
     ************************************************************************/
DECLARE
    Days     NUMERIC                  := 0;
    DueDate  timestamp with time zone := TRUNC(DocDate);
    --
    FirstDay timestamp with time zone;
    NoDays   NUMERIC;
    p        RECORD;
BEGIN
    FOR p IN
        SELECT *
        FROM C_PaymentTerm
        WHERE C_PaymentTerm_ID = PaymentTerm_ID
        LOOP --	for convineance only
    --	Due 15th of following month
            IF (p.IsDueFixed = 'Y') THEN
                FirstDay := TRUNC(DocDate, 'MM');
                NoDays := EXTRACT(EPOCH FROM TRUNC(DocDate) - FirstDay);
                DueDate := FirstDay + (p.FixMonthDay - 1); --	starting on 1st
                DueDate := ADD_MONTHS(DueDate, p.FixMonthOffset);
                IF (NoDays > p.FixMonthCutoff) THEN
                    DueDate := ADD_MONTHS(DueDate, 1);
                END IF;
            ELSE
                DueDate := TRUNC(DocDate) + p.NetDays;
            END IF;
        END LOOP;
    RETURN DueDate;
END;
$$
;

