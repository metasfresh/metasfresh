/*
 * #%L
 * de.metas.payment.sepa.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

DROP VIEW IF EXISTS C_Invoice_PaySelectionTrxType_V;
CREATE OR REPLACE VIEW C_Invoice_PaySelectionTrxType_V AS
SELECT C_Invoice_ID,
       CASE
           WHEN i.IsFinancial='N' THEN 'XX'
           WHEN /* CREDIT_TRANSFER_TO_VENDOR - "OUT" */ i.IsSOTrx='N' AND i.PaymentRule IN ('T'/*DirectDeposit*/, 'P'/*OnCredit*/) AND i.DocStatus IN ('CO', 'CL') THEN 'CT'
           WHEN /* DIRECT_DEBIT_FROM_CUSTOMER - "CDD" */ i.IsSOTrx='Y' AND dt.DocBaseType != 'ARC' AND i.PaymentRule = 'D'/*DirectDebit*/ AND i.DocStatus IN ('CO', 'CL') THEN 'DD'
           WHEN /* CREDIT_TRANSFER_TO_CUSTOMER - "CRE" */ i.IsSOTrx='Y' AND dt.DocBaseType = 'ARC' AND i.PaymentRule IN ('T'/*DirectDeposit*/, 'P'/*OnCredit*/) AND i.DocStatus IN ('CO', 'CL') THEN 'CT'
           ELSE /* not compatible to any type */ 'XX'
       END AS PaySelectionTrxType,
       i.IsSOTrx,
       i.PaymentRule,
       i.DocStatus,
       i.DocumentNo,
       dt.DocBaseType
FROM C_Invoice i
         JOIN C_DocType dt ON dt.C_DocType_ID=i.C_DocType_ID;
COMMENT ON VIEW C_Invoice_PaySelectionTrxType_V IS
    'Selects C_Invoice_IDs together with their respective PaySelectionTrxTypes. XX means "none".
    The other columns are just for diagnostics/troubleshooting.
    Please keep in sync with the code at de.metas.banking.payment.impl.PaySelectionUpdater.buildSelectSQL_MatchRequirement()';
