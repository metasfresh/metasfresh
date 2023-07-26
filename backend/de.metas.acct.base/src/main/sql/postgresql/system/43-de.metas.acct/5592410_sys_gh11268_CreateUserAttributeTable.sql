-- 2021-06-11T12:45:18.112Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.AD_User_Attribute (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_User_Attribute_ID NUMERIC(10) NOT NULL, AD_User_ID NUMERIC(10) NOT NULL, Attribute CHAR(1) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT AD_User_Attribute_Key PRIMARY KEY (AD_User_Attribute_ID), CONSTRAINT ADUser_ADUserAttribute FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED)
;


-- 2021-06-11T12:58:36.267Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_User_Attribute','ALTER TABLE public.AD_User_Attribute ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2021-06-11T13:14:39.773Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_User_Attribute','ALTER TABLE AD_User_Attribute DROP COLUMN IF EXISTS C_BPartner_ID')
;
