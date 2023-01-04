-- 2022-05-24T07:55:41.387Z
CREATE SEQUENCE S_RESOURCE_GROUP_ASSIGNMENT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2022-05-24T08:01:49.256Z
/* DDL */ CREATE TABLE public.S_Resource_Group_Assignment (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AssignDateFrom TIMESTAMP WITH TIME ZONE NOT NULL, AssignDateTo TIMESTAMP WITH TIME ZONE NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(255), IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, IsAllDay CHAR(1) DEFAULT 'N' CHECK (IsAllDay IN ('Y','N')) NOT NULL, Name VARCHAR(60) NOT NULL, S_Resource_Group_Assignment_ID NUMERIC(10) NOT NULL, S_Resource_Group_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT S_Resource_Group_Assignment_Key PRIMARY KEY (S_Resource_Group_Assignment_ID), CONSTRAINT SResourceGroup_SResourceGroupAssignment FOREIGN KEY (S_Resource_Group_ID) REFERENCES public.S_Resource_Group DEFERRABLE INITIALLY DEFERRED)
;

create index S_Resource_Group_Assignment_S_Resource_Group_ID on S_Resource_Group_Assignment (S_Resource_Group_ID);
create index S_Resource_Group_Assignment_assigndatefrom on S_Resource_Group_Assignment (assigndatefrom);
create index S_Resource_Group_Assignment_assigndateto on S_Resource_Group_Assignment (assigndateto);

