DROP INDEX IF EXISTS c_project_value
;

-- 2022-08-18T09:32:21.992Z
CREATE UNIQUE INDEX idx_c_project_value_unq_with_org_category ON C_Project (Value, AD_ORG_ID, ProjectCategory) WHERE ProjectCategory IS NOT NULL
;

-- 2022-08-18T09:32:21.992Z
CREATE UNIQUE INDEX idx_c_project_value_unq_with_org ON C_Project (Value, AD_ORG_ID) WHERE ProjectCategory IS NULL
;