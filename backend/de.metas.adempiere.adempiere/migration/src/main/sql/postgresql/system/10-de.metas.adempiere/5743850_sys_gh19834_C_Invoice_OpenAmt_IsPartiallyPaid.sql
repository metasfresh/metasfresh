UPDATE c_invoice invoiceToBeUpdated
SET openamt         = invoiceToBeUpdated.grandtotal - COALESCE((SELECT SUM(currencyConvert(al.Amount + al.DiscountAmt + al.WriteOffAmt,
                                                                                           ah.C_Currency_ID, i.C_Currency_ID, ah.DateTrx, COALESCE(i.C_ConversionType_ID, 0), al.AD_Client_ID, al.AD_Org_ID))
                                                                FROM C_AllocationLine al
                                                                         INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                                                                         INNER JOIN C_Invoice i ON (al.C_Invoice_ID = i.C_Invoice_ID)
                                                                WHERE al.C_Invoice_ID = invoiceToBeUpdated.c_invoice_id
                                                                  AND ah.IsActive = 'Y'
                                                                  AND al.IsActive = 'Y'), 0),
    ispartiallypaid = (CASE
                           WHEN invoiceToBeUpdated.grandtotal - COALESCE((SELECT SUM(currencyConvert(al.Amount + al.DiscountAmt + al.WriteOffAmt,
                                                                                                     ah.C_Currency_ID, i.C_Currency_ID, ah.DateTrx, COALESCE(i.C_ConversionType_ID, 0), al.AD_Client_ID, al.AD_Org_ID))
                                                                          FROM C_AllocationLine al
                                                                                   INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                                                                                   INNER JOIN C_Invoice i ON (al.C_Invoice_ID = i.C_Invoice_ID)
                                                                          WHERE al.C_Invoice_ID = invoiceToBeUpdated.c_invoice_id
                                                                            AND ah.IsActive = 'Y'
                                                                            AND al.IsActive = 'Y'), 0) = 0 AND invoiceToBeUpdated.ispaid = 'Y' THEN 'N'
                           WHEN NOT EXISTS(SELECT 1
                                           FROM C_AllocationLine al
                                                    INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID)
                                           WHERE al.C_Invoice_ID = invoiceToBeUpdated.c_invoice_id
                                             AND ah.IsActive = 'Y'
                                             AND al.IsActive = 'Y')                                         THEN 'N'
                                                                                                            ELSE 'Y'
                       END)

WHERE invoiceToBeUpdated.openamt IS NULL
  AND docstatus = 'CO'
;
