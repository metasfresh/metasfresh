--C_BParnter_Attribute2 Creation
-- 2021-06-14T08:41:18.034Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_Attribute2 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Attribute2 VARCHAR(40), C_BPartner_Attribute2_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_Attribute2_Key PRIMARY KEY (C_BPartner_Attribute2_ID))
;

-- Create Table C_BPartner_Attribute3
-- 2021-06-14T08:47:15.236Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_Attribute3 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Attributes3 VARCHAR(40) NOT NULL, C_BPartner_Attribute3_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_Attribute3_Key PRIMARY KEY (C_BPartner_Attribute3_ID))
;

-- Create Table C_BPartner_Attribute4
-- 2021-06-14T08:50:23.787Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_Attribute4 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Attributes4 VARCHAR(40) NOT NULL, C_BPartner_Attribute4_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_Attribute4_Key PRIMARY KEY (C_BPartner_Attribute4_ID))
;

-- Create Table C_BPartner_Attribute5
-- 2021-06-14T08:53:29.767Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_Attribute5 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Attributes5 VARCHAR(40) NOT NULL, C_BPartner_Attribute5_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_Attribute5_Key PRIMARY KEY (C_BPartner_Attribute5_ID))
;

--Fix Attribute2 Table column
-- 2021-06-14T08:56:56.761Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Attribute2','ALTER TABLE public.C_BPartner_Attribute2 ADD COLUMN Attributes2 VARCHAR(40) NOT NULL')
;

--Drop renamed column
/* DDL */ SELECT public.db_alter_table('C_BPartner_Attribute2','ALTER TABLE public.C_BPartner_Attribute2 DROP COLUMN attribute2')
;

-- 2021-06-14T09:15:20.762Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Attribute2','ALTER TABLE public.C_BPartner_Attribute2 ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2021-06-14T09:17:02.748Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Attribute3','ALTER TABLE public.C_BPartner_Attribute3 ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2021-06-14T09:18:34.391Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Attribute4','ALTER TABLE public.C_BPartner_Attribute4 ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2021-06-14T09:19:55.239Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Attribute5','ALTER TABLE public.C_BPartner_Attribute5 ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

