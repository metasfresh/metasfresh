
CREATE INDEX IF NOT EXISTS ad_user_issystemuser
    ON public.ad_user USING btree
    (issystemuser ASC NULLS LAST)
WHERE IsSystemUser='Y';
COMMENT ON INDEX ad_user_issystemuser 
IS 'Supports lookup fields wshere a system user needs to be selected; Background: often there just a few dozend system users out of 100Ks of AD_Users.';

