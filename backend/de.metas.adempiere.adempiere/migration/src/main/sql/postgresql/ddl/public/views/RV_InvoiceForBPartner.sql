CREATE OR REPLACE VIEW RV_InvoiceForBPartner AS
select
    inv.c_invoice_id RV_InvoiceForBPartner_ID,
    inv.c_bpartner_id,
    inv.c_doctype_id,
    inv.documentno,
    inv.dateinvoiced,
    inv.grandtotal,
    inv.c_invoice_id,
    inv.ad_client_id,
    inv.ad_org_id, --id of your organisation. usually 1000000
    inv.created, --or a fixed timestamp
    inv.createdby, --or any other ad_user_id
    inv.updated, --or a fixed timestamp
    inv.updatedby, --or any other ad_user_id
    inv.isactive
from C_Invoice inv where inv.IsActive='Y';