CREATE TABLE backup.BKP_ESR_Import_23072021
AS
    SELECT * from ESR_Import;

CREATE TABLE backup.BKP_ESR_ImportLine_23072021
AS
SELECT * from ESR_ImportLine;



INSERT INTO ESR_ImportFile
(ad_client_id, ad_org_id, created, createdby, isReceipt, datatype, description, esr_control_amount, esr_control_trx_qty,  esr_import_id, filename, hash, isactive, isvalid, processed, updated, updatedby, c_bp_bankaccount_id, ad_attachmententry_id, esr_importfile_id)

SELECT

   ei.ad_client_id, ei.ad_org_id, ei.created, ei.createdby, ei.isReceipt,  ei.datatype, ei.description, COALESCE(ei.esr_control_amount,0), ei.esr_control_trx_qty,  ei.esr_import_id, ae.filename, ei.hash, ei.isactive, ei.isvalid, ei.processed, ei.updated, ei.updatedby, ei.c_bp_bankaccount_id, ei.ad_attachmententry_id, nextval('esr_importfile_seq')

from esr_import ei LEFT

JOIN ad_attachmententry ae on ei.ad_attachmententry_id = ae.ad_attachmententry_id;


UPDATE esr_import set isarchivefile = 'N' where isarchivefile IS NULL;


UPDATE esr_importline l
    SET esr_importfile_id
        = x.esr_importfile_id

from ESR_ImportFile x
where l.esr_import_id = x.esr_import_id;


