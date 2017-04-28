-- 2017-04-23T15:57:28.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-04-23 15:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556503
;

drop table if exists R_RequestType_Trl;


-- 2017-04-23T16:00:02.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.R_RequestType_Trl (AD_Client_ID NUMERIC(10) NOT NULL, AD_Language VARCHAR(6) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, IsTranslated CHAR(1) CHECK (IsTranslated IN ('Y','N')) NOT NULL, Name VARCHAR(60) NOT NULL, R_RequestType_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT R_RequestType_Trl_Key PRIMARY KEY (AD_Language, R_RequestType_ID), CONSTRAINT ADLangu_RRequestTypeTrl FOREIGN KEY (AD_Language) REFERENCES AD_Language DEFERRABLE INITIALLY DEFERRED, CONSTRAINT RRequestType_RRequestTypeTrl FOREIGN KEY (R_RequestType_ID) REFERENCES R_RequestType DEFERRABLE INITIALLY DEFERRED)
;

-- 2017-04-23T16:08:37.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2017-04-23 16:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7776
;

