-- Enable PostgreSQL Full-Text Search and populate FTS tables
UPDATE AD_SysConfig
SET Value    = 'Y',
    Updated  = NOW(),
    UpdatedBy = 100
WHERE Name = 'de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled'
;

-- Create triggers and populate FTS tables (BPartner, Invoice, Product)
SELECT ops.update_all_fts_if_active()
;
