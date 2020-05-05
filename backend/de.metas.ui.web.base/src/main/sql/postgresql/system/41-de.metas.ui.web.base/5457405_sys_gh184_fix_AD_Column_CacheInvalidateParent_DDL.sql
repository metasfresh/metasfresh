
-- note that as of postgresq-9.5 there is no add-column-if-not-exists
ALTER TABLE AD_COLUMN DROP COLUMN IF EXISTS CacheInvalidateParent;
ALTER TABLE AD_COLUMN ADD COLUMN CacheInvalidateParent character(1) NOT NULL DEFAULT 'Y';
