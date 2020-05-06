-- 04.07.2016 13:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TABLE C_DocType_Sequence (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_DocType_ID NUMERIC(10) NOT NULL, C_DocType_Sequence_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DocNoSequence_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_DocType_Sequence_Key PRIMARY KEY (C_DocType_Sequence_ID))
;

ALTER TABLE C_DocType_Sequence ADD CONSTRAINT CDocType_CDocTypeSequence FOREIGN KEY (C_DocType_ID) REFERENCES C_DocType DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE C_DocType_Sequence ADD CONSTRAINT DocNoSequence_CDocTypeSequence FOREIGN KEY (DocNoSequence_ID) REFERENCES AD_Sequence DEFERRABLE INITIALLY DEFERRED;


create index C_DocType_Sequence_Parent on C_DocType_Sequence(C_DocType_ID);
create unique index C_DocType_Sequence_UQ on C_DocType_Sequence(C_DocType_ID, AD_Client_ID, AD_Org_ID);

