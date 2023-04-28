-- drop table if exists public.RabbitMQ_Message_Audit;

-- 2022-02-24T15:21:30.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.RabbitMQ_Message_Audit (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Content TEXT, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Direction CHAR(1) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, RabbitMQ_Message_Audit_ID NUMERIC(10) NOT NULL, RabbitMQ_QueueName VARCHAR(2000), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT RabbitMQ_Message_Audit_Key PRIMARY KEY (RabbitMQ_Message_Audit_ID))
;

