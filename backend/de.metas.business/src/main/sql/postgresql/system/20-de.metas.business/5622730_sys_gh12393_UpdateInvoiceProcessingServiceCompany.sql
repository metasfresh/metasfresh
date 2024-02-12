-- dev-note: update all `c_doctype` records referenced in `InvoiceProcessingServiceCompany` to have the specific `ServiceFeeInvoice` docsubtype
-- with one exception, i.e. any default doctype will be skipped
UPDATE c_doctype
SET docsubtype = 'SI'
FROM InvoiceProcessingServiceCompany serviceCompanySettings
WHERE (1 = 1)
  AND serviceCompanySettings.ServiceInvoice_DocType_ID = c_doctype.c_doctype_id
  AND serviceCompanySettings.isactive = 'Y'
  AND c_doctype.isdefault != 'Y';

-- dev-note: update all `InvoiceProcessingServiceCompany` that are referencing a default doctype to have a specific `ServiceFeeInvoice` one
UPDATE InvoiceProcessingServiceCompany
SET ServiceInvoice_DocType_ID = docType.c_doctype_id
FROM c_doctype docType
WHERE (1 = 1)
  AND docType.docbasetype = 'API'
  AND docType.docsubtype = 'SI'
  AND docType.isactive = 'Y'
  AND docType.ad_org_id in (0,InvoiceProcessingServiceCompany.ad_org_id)
  AND EXISTS(select 1
             from c_doctype serviceFeeDocType
             where serviceFeeDocType.c_doctype_id = InvoiceProcessingServiceCompany.serviceinvoice_doctype_id
                   AND isdefault = 'Y');
