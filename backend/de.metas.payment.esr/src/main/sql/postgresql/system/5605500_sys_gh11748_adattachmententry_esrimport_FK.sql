-- ALTER TABLE esr_Import DROP CONSTRAINT IF EXISTS adattachmententry_esrimport;


ALTER TABLE esr_import ADD CONSTRAINT adattachmententry_esrimport FOREIGN KEY (ad_attachmententry_ID)
    REFERENCES ad_attachmententry
    DEFERRABLE INITIALLY DEFERRED;
