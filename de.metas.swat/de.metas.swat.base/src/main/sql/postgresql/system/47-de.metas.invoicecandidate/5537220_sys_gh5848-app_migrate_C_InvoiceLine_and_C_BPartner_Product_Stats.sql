
UPDATE C_InvoiceLine il 
SET InvoicableQtyBasedOn=COALESCE(data.InvoicableQtyBasedOn, 'Nominal'),
    Updated='2019-11-28 15:59:28.101886+01',
    UpdatedBy=99
FROM (
    SELECT DISTINCT ON (ila.C_InvoiceLine_ID) 
        ila.C_InvoiceLine_ID, ic.InvoicableQtyBasedOn
    FROM C_Invoice_Line_Alloc ila
        JOIN C_Invoice_Candidate ic ON ic.C_Invoice_Candidate_ID=ila.C_Invoice_Candidate_ID
    ORDER BY 
        ila.C_InvoiceLine_ID, 
        ic.InvoicableQtyBasedOn DESC /*in case of two ICs with different InvoicableQtyBasedOn values, we prefere "Nominal" over "Catch"*/
) data 
WHERE data.C_InvoiceLine_ID = il.C_InvoiceLine_ID
    AND il.InvoicableQtyBasedOn IS NULL;

UPDATE C_BPartner_Product_Stats s
SET LastSalesInvoicableQtyBasedOn = v.InvoicableQtyBasedOn,
    Updated='2019-11-28 15:59:28.101886+01',
    UpdatedBy=99
FROM C_BPartner_Product_Stats_Invoice_Online_V v

WHERE v.C_Invoice_ID = s.LastSales_Invoice_ID
    AND v.M_Product_ID = v.M_Product_ID
    AND v.IsSOTrx = 'Y'
    AND s.LastSalesInvoicableQtyBasedOn IS NULL;
