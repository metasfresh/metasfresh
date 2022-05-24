-- 2021-09-29T12:22:14.585Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_Incoterms (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Incoterms_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(40), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Value VARCHAR(40), CONSTRAINT C_Incoterms_Key PRIMARY KEY (C_Incoterms_ID))
;

-- 2021-09-29T12:28:11.341Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_Incoterms_Trl (AD_Client_ID NUMERIC(10) NOT NULL, AD_Language VARCHAR(10), AD_Org_ID NUMERIC(10) NOT NULL, C_Incoterms_Trl_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description TEXT, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsTranslated CHAR(1) DEFAULT 'N' CHECK (IsTranslated IN ('Y','N')) NOT NULL, Name VARCHAR(40), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Incoterms_Trl_Key PRIMARY KEY (C_Incoterms_Trl_ID))
;

-- 2021-09-29T12:31:07.495Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Incoterms','ALTER TABLE public.C_Incoterms ADD COLUMN Description TEXT')
;

-- 2021-09-29T13:01:27.114Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Incoterms_Trl','ALTER TABLE public.C_Incoterms_Trl ADD COLUMN C_Incoterms_ID NUMERIC(10)')
;

-- 2021-09-29T12:34:17.113Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN C_Incoterms_ID NUMERIC(10)')
;

