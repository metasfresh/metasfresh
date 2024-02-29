
-- 2024-01-11
CREATE INDEX IF NOT EXISTS c_queue_workpackage_C_Queue_PackageProcessor_ID
    ON public.c_queue_workpackage
        (C_Queue_PackageProcessor_ID);
