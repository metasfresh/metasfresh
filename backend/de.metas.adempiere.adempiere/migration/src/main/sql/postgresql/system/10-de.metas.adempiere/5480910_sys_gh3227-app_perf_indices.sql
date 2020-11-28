

CREATE UNIQUE INDEX IF NOT EXISTS ad_attachment_record_id_ad_table_id
  ON public.ad_attachment
  USING btree
  (record_id, ad_table_id);

DROP INDEX IF EXISTS ad_attachment_record;

CREATE INDEX IF NOT EXISTS ad_attachment_ad_table_id
  ON public.ad_attachment
  USING btree
  (ad_table_id);
COMMENT ON INDEX public.ad_attachment_ad_table_id IS
'This index is needed by org.compiere.model.GridTab, which loads 101 AD_Attachments by AD_Table_ID
see https://github.com/metasfresh/metasfresh/issues/3227
';


CREATE INDEX IF NOT EXISTS ad_archive_record_id_ad_table_id
  ON public.ad_archive
  USING btree
  (record_id, ad_table_id);
DROP INDEX IF EXISTS ad_archive_reference;

CREATE INDEX IF NOT EXISTS ad_archive_ad_table_id
  ON public.ad_archive
  USING btree
  (ad_table_id);
  COMMENT ON INDEX public.ad_archive_ad_table_id IS
'This index is needed by org.compiere.apps.AArchive, which counts AD_Archive records by AD_Table_ID
see https://github.com/metasfresh/metasfresh/issues/3227
';
