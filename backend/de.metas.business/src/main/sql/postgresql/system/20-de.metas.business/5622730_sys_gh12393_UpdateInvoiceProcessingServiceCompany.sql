UPDATE c_doctype
SET docsubtype = 'SI'
FROM InvoiceProcessingServiceCompany serviceCompanySettings
WHERE (1 = 1)
  AND serviceCompanySettings.ServiceInvoice_DocType_ID = c_doctype.c_doctype_id
  AND serviceCompanySettings.isactive = 'Y'
  AND c_doctype.docbasetype != 'API';

UPDATE InvoiceProcessingServiceCompany
SET ServiceInvoice_DocType_ID = docType.c_doctype_id
FROM c_doctype docType
WHERE (1 = 1)
  AND docType.docbasetype = 'API'
  AND docType.docsubtype = 'SI'
  AND docType.isactive = 'Y';