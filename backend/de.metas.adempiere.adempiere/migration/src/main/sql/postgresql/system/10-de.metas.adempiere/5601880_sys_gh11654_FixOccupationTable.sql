-- 2021-08-25T07:27:18.820Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('CRM_Occupation','ALTER TABLE CRM_Occupation DROP COLUMN IF EXISTS crm_occupation_parent_id')
;

-- 2021-08-25T07:32:18.953Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('CRM_Occupation','ALTER TABLE public.CRM_Occupation ADD COLUMN CRM_Occupation_Parent_ID NUMERIC(10)')
;

-- 2021-08-25T07:32:19.029Z
-- URL zum Konzept
ALTER TABLE CRM_Occupation ADD CONSTRAINT CRMOccupationParent_CRMOccupation FOREIGN KEY (CRM_Occupation_Parent_ID) REFERENCES public.CRM_Occupation DEFERRABLE INITIALLY DEFERRED
;

