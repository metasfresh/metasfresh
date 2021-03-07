
CREATE TABLE backup.ad_sysconfig_gh10667 as select * from ad_sysconfig;

delete from ad_sysconfig where name ILIKE '%de.metas.handlingunit.sscc18Label.zebra.sql-select%';
delete from ad_sysconfig where name ILIKE '%de.metas.handlingunit.sscc18Label.zebra.header.line-1%';
delete from ad_sysconfig where name ILIKE '%de.metas.handlingunit.sscc18Label.zebra.header.line-2%';
delete from ad_sysconfig where name ILIKE '%de.metas.handlingunit.sscc18Label.zebra.encoding%';
delete from ad_sysconfig where name ILIKE '%de.metas.handlingunit.sscc18Label.zebra%';