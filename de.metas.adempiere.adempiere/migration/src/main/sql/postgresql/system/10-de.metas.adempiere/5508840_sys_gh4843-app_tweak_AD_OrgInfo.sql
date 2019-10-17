-- 2018-12-21T06:06:47.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-12-21 06:06:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2352
;

-- 2018-12-21T06:06:50.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','DUNS','VARCHAR(11)',null,null)
;

-- 2018-12-21T06:06:50.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','DUNS',null,'NULL',null)
;

-- 2018-12-21T06:09:19.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-12-21 06:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2353
;

-- 2018-12-21T06:09:20.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','TaxID','VARCHAR(20)',null,null)
;

-- 2018-12-21T06:09:20.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','TaxID',null,'NULL',null)
;

-- 2018-12-21T06:10:03.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Deactivate this column; I think it''s obsolete since we have C_BPartner.VATTaxID',Updated=TO_TIMESTAMP('2018-12-21 06:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2353
;

-- 2018-12-21T06:10:04.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','TaxID','VARCHAR(20)',null,null)
;

-- 2018-12-21T06:10:07.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2018-12-21 06:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2353
;

-- 2018-12-21T06:11:44.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Deactivating this column; I don''t know that we ever used it; 
Hint; when we use a DUNS, I recommend to add it as another field to C_BPartner..then everyone can have a DUNS, not just our own orgs.',Updated=TO_TIMESTAMP('2018-12-21 06:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2352
;

-- 2018-12-21T06:11:44.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','DUNS','VARCHAR(11)',null,null)
;

-- 2018-12-21T06:11:47.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2018-12-21 06:11:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2352
;

-- 2018-12-21T06:13:46.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N', TechnicalNote='Obsolete since we don''t do ZK anymore',Updated=TO_TIMESTAMP('2018-12-21 06:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544245
;

-- 2018-12-21T06:13:54.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N', TechnicalNote='Obsolete since we don''t do ZK anymore',Updated=TO_TIMESTAMP('2018-12-21 06:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544247
;

-- 2018-12-21T06:14:02.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N', TechnicalNote='Obsolete since we don''t do ZK anymore',Updated=TO_TIMESTAMP('2018-12-21 06:14:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544246
;

-- 2018-12-21T06:16:33.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='file:////opt/metasfresh/reports', IsMandatory='Y',Updated=TO_TIMESTAMP('2018-12-21 06:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=502220
;

-- 2018-12-21T06:16:34.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','ReportPrefix','VARCHAR(512)',null,'file:////opt/metasfresh/reports')
;

-- 2018-12-21T06:16:34.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_OrgInfo SET ReportPrefix='file:////opt/metasfresh/reports' WHERE ReportPrefix IS NULL
;

COMMIT;

-- 2018-12-21T06:16:34.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_orginfo','ReportPrefix',null,'NOT NULL',null)
;
