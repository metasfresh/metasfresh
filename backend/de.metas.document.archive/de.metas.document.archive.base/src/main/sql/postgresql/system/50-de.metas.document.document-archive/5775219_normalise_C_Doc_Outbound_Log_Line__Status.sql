SELECT backup_table('C_Doc_Outbound_Log_Line', 'C_Doc_Outbound_Log_Line_20251030')
;

UPDATE C_Doc_Outbound_Log_Line
SET status    =
        CASE
            WHEN status IS NULL                                                                                                                                    THEN NULL
            WHEN status = 'Success' AND action = 'print'                                                                                                           THEN 'Print_Success'
            WHEN status = 'Failure' AND action = 'print'                                                                                                           THEN 'Print_Failure'
            WHEN status IN ('Mitteilung versendet.', 'Notification envoyée.', 'Message sent.') AND action = 'eMail'                                                THEN 'Email_Success'
            WHEN (status ILIKE 'Mitteilung nicht gesendet%' OR status ILIKE 'Notification non envoyée%' OR status ILIKE 'Message not sent.%') AND action = 'eMail' THEN 'Email_Failure'
        END,
    updatedBy = 100,
    updated   = TO_TIMESTAMP('2025-10-30 19:29:39', 'YYYY-MM-DD HH24:MI:SS')
;
