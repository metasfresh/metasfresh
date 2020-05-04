-- 2020-04-01T19:44:48.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.AD_SQLColumn_SourceTableColumn
          (
              AD_Client_ID                          NUMERIC(10)                            NOT NULL,
              AD_Column_ID                          NUMERIC(10)                            NOT NULL,
              AD_Org_ID                             NUMERIC(10)                            NOT NULL,
              AD_SQLColumn_SourceTableColumn_ID     NUMERIC(10)                            NOT NULL,
              AD_Table_ID                           NUMERIC(10)                            NOT NULL,
              Created                               TIMESTAMP WITH TIME ZONE               NOT NULL,
              CreatedBy                             NUMERIC(10)                            NOT NULL,
              IsActive                              CHAR(1) CHECK (IsActive IN ('Y', 'N')) NOT NULL,
              Source_Column_ID                      NUMERIC(10),
              Source_Table_ID                       NUMERIC(10)                            NOT NULL,
              SQL_GetTargetRecordIdBySourceRecordId TEXT                                   NOT NULL,
              Updated                               TIMESTAMP WITH TIME ZONE               NOT NULL,
              UpdatedBy                             NUMERIC(10)                            NOT NULL,
              CONSTRAINT ADColumn_ADSQLColumnSourceTableColumn FOREIGN KEY (AD_Column_ID) REFERENCES public.AD_Column DEFERRABLE INITIALLY DEFERRED,
              CONSTRAINT AD_SQLColumn_SourceTableColumn_Key PRIMARY KEY (AD_SQLColumn_SourceTableColumn_ID),
              CONSTRAINT ADTable_ADSQLColumnSourceTableColumn FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED,
              CONSTRAINT SourceColumn_ADSQLColumnSourceTableColumn FOREIGN KEY (Source_Column_ID) REFERENCES public.AD_Column DEFERRABLE INITIALLY DEFERRED,
              CONSTRAINT SourceTable_ADSQLColumnSourceTableColumn FOREIGN KEY (Source_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED
          )
;

