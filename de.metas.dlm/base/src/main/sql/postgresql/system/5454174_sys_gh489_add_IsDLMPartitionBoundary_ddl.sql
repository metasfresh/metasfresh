
-- 07.12.2016 14:58
-- URL zum Konzept
ALTER TABLE public.DLM_Partition_Config_Reference ADD Description VARCHAR(2000) DEFAULT NULL 
;

-- 07.12.2016 15:07
-- URL zum Konzept
ALTER TABLE public.AD_Column ADD IsDLMPartitionBoundary CHAR(1) DEFAULT 'N' CHECK (IsDLMPartitionBoundary IN ('Y','N')) NOT NULL
;
