UPDATE c_invoice invoiceToBeUpdated
SET openamt         = CASE
                          WHEN invoiceToBeUpdated.issotrx = 'N' OR (invoiceToBeUpdated.issotrx = 'Y' AND invoiceToBeUpdated.c_doctype_id IN (SELECT c_doctype_id FROM c_doctype WHERE docbasetype = 'ARC')) THEN invoiceToBeUpdated.grandtotal + COALESCE((SELECT SUM(currencyConvert(al.Amount + al.DiscountAmt + al.WriteOffAmt,
                                                                                                                                                                                                                                                                                      ah.C_Currency_ID, i.C_Currency_ID, ah.DateTrx, COALESCE(i.C_ConversionType_ID, 0), al.AD_Client_ID, al.AD_Org_ID))
                                                                                                                                                                                                                                                           FROM C_AllocationLine al
                                                                                                                                                                                                                                                                    INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                                                                                                                                                                                                                                                                    INNER JOIN C_Invoice i ON (al.C_Invoice_ID = i.C_Invoice_ID)
                                                                                                                                                                                                                                                           WHERE al.C_Invoice_ID = invoiceToBeUpdated.c_invoice_id
                                                                                                                                                                                                                                                             AND ah.IsActive = 'Y'
                                                                                                                                                                                                                                                             AND al.IsActive = 'Y'), 0)
                                                                                                                                                                                                            ELSE invoiceToBeUpdated.grandtotal - COALESCE((SELECT SUM(currencyConvert(al.Amount + al.DiscountAmt + al.WriteOffAmt,
                                                                                                                                                                                                                                                                                      ah.C_Currency_ID, i.C_Currency_ID, ah.DateTrx, COALESCE(i.C_ConversionType_ID, 0), al.AD_Client_ID, al.AD_Org_ID))
                                                                                                                                                                                                                                                           FROM C_AllocationLine al
                                                                                                                                                                                                                                                                    INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                                                                                                                                                                                                                                                                    INNER JOIN C_Invoice i ON (al.C_Invoice_ID = i.C_Invoice_ID)
                                                                                                                                                                                                                                                           WHERE al.C_Invoice_ID = invoiceToBeUpdated.c_invoice_id
                                                                                                                                                                                                                                                             AND ah.IsActive = 'Y'
                                                                                                                                                                                                                                                             AND al.IsActive = 'Y'), 0)
                      END,
    ispartiallypaid = (CASE
                           WHEN invoiceToBeUpdated.grandtotal - COALESCE((SELECT SUM(currencyConvert(al.Amount + al.DiscountAmt + al.WriteOffAmt,
                                                                                                     ah.C_Currency_ID, i.C_Currency_ID, ah.DateTrx, COALESCE(i.C_ConversionType_ID, 0), al.AD_Client_ID, al.AD_Org_ID))
                                                                          FROM C_AllocationLine al
                                                                                   INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                                                                                   INNER JOIN C_Invoice i ON (al.C_Invoice_ID = i.C_Invoice_ID)
                                                                          WHERE al.C_Invoice_ID = invoiceToBeUpdated.c_invoice_id
                                                                            AND ah.IsActive = 'Y'
                                                                            AND al.IsActive = 'Y'), 0) = 0 AND invoiceToBeUpdated.ispaid = 'Y' AND invoiceToBeUpdated.issotrx = 'Y'                                                                                                                                               THEN 'N'
                           WHEN invoiceToBeUpdated.grandtotal + COALESCE((SELECT SUM(currencyConvert(al.Amount + al.DiscountAmt + al.WriteOffAmt,
                                                                                                     ah.C_Currency_ID, i.C_Currency_ID, ah.DateTrx, COALESCE(i.C_ConversionType_ID, 0), al.AD_Client_ID, al.AD_Org_ID))
                                                                          FROM C_AllocationLine al
                                                                                   INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                                                                                   INNER JOIN C_Invoice i ON (al.C_Invoice_ID = i.C_Invoice_ID)
                                                                          WHERE al.C_Invoice_ID = invoiceToBeUpdated.c_invoice_id
                                                                            AND ah.IsActive = 'Y'
                                                                            AND al.IsActive = 'Y'), 0) = 0 AND invoiceToBeUpdated.ispaid = 'Y' AND (invoiceToBeUpdated.issotrx = 'N' OR (invoiceToBeUpdated.issotrx = 'Y' AND invoiceToBeUpdated.c_doctype_id IN (SELECT c_doctype_id FROM c_doctype WHERE docbasetype = 'ARC'))) THEN 'N'
                           WHEN NOT EXISTS(SELECT 1
                                           FROM C_AllocationLine al
                                                    INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                                           WHERE al.C_Invoice_ID = invoiceToBeUpdated.c_invoice_id
                                             AND ah.IsActive = 'Y'
                                             AND al.IsActive = 'Y')                                                                                                                                                                                                                                                               THEN 'N'
                           WHEN EXISTS(SELECT 1
                                       FROM C_AllocationLine al
                                                INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                                       WHERE al.C_Invoice_ID = invoiceToBeUpdated.c_invoice_id
                                         AND ah.IsActive = 'Y'
                                         AND al.IsActive = 'Y') AND invoiceToBeUpdated.grandtotal = invoiceToBeUpdated.openamt                                                                                                                                                                                                    THEN 'N'
                                                                                                                                                                                                                                                                                                                                  ELSE 'Y'
                       END)

WHERE invoiceToBeUpdated.openamt IS NULL
  AND docstatus = 'CO'
;
