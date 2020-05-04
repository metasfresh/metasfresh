
CREATE INDEX IF NOT EXISTS ad_element_link_ad_element_id
    ON public.ad_element_link USING btree
    (ad_element_id)
    TABLESPACE pg_default;
