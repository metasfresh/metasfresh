update c_referenceno_type set description='This C_Referenceno_Type record is still referenced from C_ReferenceNo, but the class is no longer invoked. We need no C_ReferenceNo_Type_Table record(s). ESRReferenceNumber creation is done "hard-coded" by a C_Invoice model validator on invoice completion.
Background: we also create a C_Payment_Request and the invoice reference string at the same time, and we need them to be "in sync" (e.g. with the underlying bank-account).' where c_referenceno_type_id=540005;

update c_referenceno_type set description='This C_Referenceno_Type record is still referenced from C_ReferenceNo, but the class is no longer invoked. We need no C_ReferenceNo_Type_Table record(s). InvoiceReference creation is done "hard-coded" by a C_Invoice model validator on invoice completion.
Background: we also create a C_Payment_Request and the coded ESR string - of which this InvoiceReference is a part - at the same time, and we need them to be "in sync" (e.g. with the underlying bank-account).'
where c_referenceno_type_id=540006;
