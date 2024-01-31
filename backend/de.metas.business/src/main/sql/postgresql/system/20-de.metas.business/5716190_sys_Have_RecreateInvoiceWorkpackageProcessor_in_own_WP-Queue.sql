


-- reactivate RecreateInvoiceWorkpackageProcessor
UPDATE c_queue_processor
SET isactive='Y', updated='2024-01-31 10:04', updatedby=100,
    description='! Do not deactivate in favor of C_InvoiceCandidateQueue !
The workpackage-processor "RecreateInvoiceWorkpackageProcessor" enqueues C_Invoice_Candidates for invoicing 
and this needs to be done **outside** the queue in charge of updating those invoice candidates.'
WHERE c_queue_processor_id = 540055
;

-- remove if from C_InvoiceCandidateQueue
DELETE
FROM c_queue_processor_assign
WHERE c_queue_processor_id = 540066 /*C_InvoiceCandidateQueue*/
  AND c_queue_packageprocessor_id = 540069 /*RecreateInvoiceWorkpackageProcessor*/
;


-- just fix the description of C_Invoice_CandidateQueue (the duplicate with the _)
UPDATE c_queue_processor
SET updated='2024-01-31 10:04', updatedby=100, description='Deactivated in favor of C_InvoiceCandidateQueue'
WHERE c_queue_processor_id = 540072
;
-- just fix the description of C_Invoice_Candidate - Create missing invoice candidates
UPDATE c_queue_processor
SET updated='2024-01-31 10:04', updatedby=100, description='Deactivated in favor of C_InvoiceCandidateQueue'
WHERE c_queue_processor_id = 540029
;

-- make the description of C_InvoiceCandidateQueue clearer
UPDATE c_queue_processor
SET updated='2024-01-31 10:04', updatedby=100,
    description=description || '

( ! RecreateInvoiceWorkpackageProcessor needs to be in its own queue ! )'
WHERE c_queue_processor_id = 540066
;
