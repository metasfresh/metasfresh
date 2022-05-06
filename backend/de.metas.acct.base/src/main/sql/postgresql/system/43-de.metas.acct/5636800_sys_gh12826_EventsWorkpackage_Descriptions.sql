UPDATE C_Queue_Processor SET description='Events not handled anymore through a workpackage. Deactivated and not deleted to avoid foreign key constraint fails.', UpdatedBy=99, Updated='2022-04-26 13:55:47.057538+03' WHERE c_queue_processor_id = 540064
;

UPDATE C_Queue_PackageProcessor SET description='Events not handled anymore through a workpackage. Deactivated and not deleted to avoid foreign key constraint fails.', UpdatedBy=99, Updated='2022-04-26 13:55:47.057538+03' WHERE C_Queue_PackageProcessor_ID = 540094
;