-- 2021-02-18T13:21:16.290Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.AD_Zebra_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Zebra_Config_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Header_Line1 VARCHAR(1000) NOT NULL, Header_Line2 VARCHAR(1000) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsDefault CHAR(1) DEFAULT 'Y' CHECK (IsDefault IN ('Y','N')) NOT NULL, SQL_Select VARCHAR(5000) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT AD_Zebra_Config_Key PRIMARY KEY (AD_Zebra_Config_ID))
;

-- 2021-02-18T14:53:00.325Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BP_PrintFormat','ALTER TABLE public.C_BP_PrintFormat ADD COLUMN AD_Zebra_Config_ID NUMERIC(10)')
;


-- 2021-02-18T15:22:34.016Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_Zebra_Config','ALTER TABLE public.AD_Zebra_Config ADD COLUMN Encoding VARCHAR(1000)')
;

-- 2021-02-19T09:28:53.869Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_Zebra_Config','ALTER TABLE public.AD_Zebra_Config ADD COLUMN Name VARCHAR(100)')
;