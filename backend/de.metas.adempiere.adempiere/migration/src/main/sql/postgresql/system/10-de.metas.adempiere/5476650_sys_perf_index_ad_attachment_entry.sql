
CREATE INDEX IF NOT EXISTS ad_attachmententry_ad_attachment_id
   ON public.ad_attachmententry (ad_attachment_id ASC NULLS LAST);
