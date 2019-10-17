-- 2019-08-27T12:19:33.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAllowLogging='N', TechnicalNote='AllowLogging=''N'' because C_Location is generally immutable. But if we derive a changelog from e.g. C_BPartner_Location.C_Location_ID''s change log, then we want this IsActive not to be confused with C_BPartnerLocation''s IsActive.
Also see the technical note of C_Location (AD_Table).',Updated=TO_TIMESTAMP('2019-08-27 12:19:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=811
;

