-- 2019-01-25T15:14:37.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.DataEntry_Group (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DataEntry_Group_ID NUMERIC(10) NOT NULL, DataEntry_TargetWindow_ID NUMERIC(10) NOT NULL, Description TEXT, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(200) NOT NULL, TabName VARCHAR(40) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DataEntry_Group_Key PRIMARY KEY (DataEntry_Group_ID), CONSTRAINT DataEntryTargetWindow_DataEntryGroup FOREIGN KEY (DataEntry_TargetWindow_ID) REFERENCES public.AD_Window DEFERRABLE INITIALLY DEFERRED)
;

-- 2019-01-25T15:14:45.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.DataEntry_SubGroup (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DataEntry_Group_ID NUMERIC(10) NOT NULL, DataEntry_SubGroup_ID NUMERIC(10) NOT NULL, Description TEXT, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(200) NOT NULL, TabName VARCHAR(40) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DataEntryGroup_DataEntrySubGroup FOREIGN KEY (DataEntry_Group_ID) REFERENCES public.DataEntry_Group DEFERRABLE INITIALLY DEFERRED, CONSTRAINT DataEntry_SubGroup_Key PRIMARY KEY (DataEntry_SubGroup_ID))
;

-- 2019-01-25T15:14:53.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.DataEntry_Field (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DataEntry_Field_ID NUMERIC(10) NOT NULL, DataEntry_RecordType CHAR(1) DEFAULT 'T' NOT NULL, DataEntry_SubGroup_ID NUMERIC(10) NOT NULL, Description TEXT, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsMandatory CHAR(1) DEFAULT 'N' CHECK (IsMandatory IN ('Y','N')) NOT NULL, Name VARCHAR(200) NOT NULL, PersonalDataCategory VARCHAR(2) DEFAULT 'NP' NOT NULL, SeqNo NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DataEntry_Field_Key PRIMARY KEY (DataEntry_Field_ID), CONSTRAINT DataEntrySubGroup_DataEntryField FOREIGN KEY (DataEntry_SubGroup_ID) REFERENCES public.DataEntry_SubGroup DEFERRABLE INITIALLY DEFERRED)
;

-- 2019-01-25T15:15:00.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.DataEntry_ListValue (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DataEntry_Field_ID NUMERIC(10) NOT NULL, DataEntry_ListValue_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(40) NOT NULL, SeqNo NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DataEntryField_DataEntryListValue FOREIGN KEY (DataEntry_Field_ID) REFERENCES public.DataEntry_Field DEFERRABLE INITIALLY DEFERRED, CONSTRAINT DataEntry_ListValue_Key PRIMARY KEY (DataEntry_ListValue_ID))
;

-- 2019-01-25T15:24:02.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dataentry_listvalue','DataEntry_ListValue_ID','NUMERIC(10)',null,null)
;

-- 2019-01-25T15:24:09.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.DataEntry_Record (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DataEntry_Field_ID NUMERIC(10) NOT NULL, DataEntry_Record_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, ValueDate TIMESTAMP WITHOUT TIME ZONE, ValueNumber NUMERIC, ValueStr TEXT, ValueYesNo CHAR(1), CONSTRAINT DataEntryField_DataEntryRecord FOREIGN KEY (DataEntry_Field_ID) REFERENCES public.DataEntry_Field DEFERRABLE INITIALLY DEFERRED, CONSTRAINT DataEntry_Record_Key PRIMARY KEY (DataEntry_Record_ID))
;

-- 2019-01-25T15:24:16.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.DataEntry_Record_Assignment (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DataEntry_Record_Assignment_ID NUMERIC(10) NOT NULL, DataEntry_Record_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DataEntry_Record_Assignment_Key PRIMARY KEY (DataEntry_Record_Assignment_ID), CONSTRAINT DataEntryRecord_DataEntryRecordAssignment FOREIGN KEY (DataEntry_Record_ID) REFERENCES public.DataEntry_Record DEFERRABLE INITIALLY DEFERRED)
;

-- 2019-01-28T15:22:05.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DataEntry_ListValue','ALTER TABLE public.DataEntry_ListValue ADD COLUMN Description TEXT')
;

