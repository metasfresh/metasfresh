drop index if exists ad_language_isbaselanguage_uq;

create unique index ad_language_isbaselanguage_uq on ad_language(isbaselanguage) where isbaselanguage='Y';

