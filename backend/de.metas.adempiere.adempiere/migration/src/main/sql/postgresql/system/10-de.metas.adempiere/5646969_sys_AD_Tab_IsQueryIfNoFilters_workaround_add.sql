-- IMPORTANT: 
-- I had to add this column here to make my migration scripts not fail.
-- The column is introduced in master branch by 5644340_sys_AD_Tab_IsQueryIfNoFilters.sql.
-- After merging master into this branch, feel free to delete this.

ALTER TABLE public.AD_Tab
    ADD COLUMN IF NOT EXISTS IsQueryIfNoFilters CHAR(1) DEFAULT 'Y' CHECK (IsQueryIfNoFilters IN ('Y', 'N')) NOT NULL
;

