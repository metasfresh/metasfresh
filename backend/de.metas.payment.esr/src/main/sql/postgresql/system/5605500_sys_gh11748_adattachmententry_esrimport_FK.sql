-- ALTER TABLE esr_Import DROP CONSTRAINT IF EXISTS adattachmententry_esrimport;

create table backup.esr_import_gh11748 as select * from esr_import;

UPDATE esr_import e
SET ad_attachmententry_id=NULL, updated='2022-11-25 08:35', updatedby=99
WHERE NOT EXISTS(SELECT 1 FROM ad_attachmententry ae WHERE ae.ad_attachmententry_ID = e.ad_attachmententry_ID)
;

ALTER TABLE esr_import ADD CONSTRAINT adattachmententry_esrimport FOREIGN KEY (ad_attachmententry_ID)
    REFERENCES ad_attachmententry
    DEFERRABLE INITIALLY DEFERRED;
