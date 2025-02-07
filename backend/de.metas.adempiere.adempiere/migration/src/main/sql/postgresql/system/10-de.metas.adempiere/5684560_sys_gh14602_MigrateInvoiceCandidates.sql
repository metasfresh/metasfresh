/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- Column: C_Invoice_Candidate.C_PaymentTerm_ID
-- 2023-04-07T10:20:34.845Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-04-07 13:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558578
;


-- backup
CREATE TABLE backup.c_invoice_candidate_payment_term AS
SELECT *
FROM c_invoice_candidate
;


-- update from based on orderline
UPDATE c_invoice_candidate
SET c_paymentterm_id=o.c_paymentterm_id
FROM c_orderLine ol
         JOIN c_order o ON o.c_order_id = ol.c_order_id
WHERE c_invoice_candidate.ad_table_id = 260
  AND c_invoice_candidate.record_id = ol.c_orderLine_ID
  AND c_invoice_candidate.c_paymentterm_id IS NULL
;

-- update from based on inoutline
UPDATE c_invoice_candidate
SET c_paymentterm_id=o.c_paymentterm_id
FROM c_orderLine ol
         JOIN c_order o ON o.c_order_id = ol.c_order_id
         JOIN m_inoutline iol on iol.c_orderline_id=ol.c_orderline_id
WHERE c_invoice_candidate.ad_table_id = 320
  AND c_invoice_candidate.record_id = iol.m_inoutline_ID
  AND c_invoice_candidate.c_paymentterm_id IS NULL
;

-- update to default one
UPDATE c_invoice_candidate
SET c_paymentterm_id= (select c_paymentterm_id from c_paymentterm pt where pt.isdefault='Y' and pt.ad_org_id = c_invoice_candidate.ad_org_id and pt.isactive='Y' and pt.AD_Client_ID=c_invoice_candidate.AD_Client_ID) where c_paymentterm_id IS NULL;




--- drop unneeded parameter
-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: SupplementMissingPaymentTermIds
-- 2023-04-07T12:19:10.617Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541281
;

-- 2023-04-07T12:19:10.628Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541281
;

