
ALTER TABLE C_Printing_Queue_Recipient ADD CONSTRAINT ADUserToPrint_CPrintingQueueRe FOREIGN KEY (AD_User_ToPrint_ID) REFERENCES AD_User DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE C_Printing_Queue_Recipient ADD CONSTRAINT CPrintingQueue_CPrintingQueueR FOREIGN KEY (C_Printing_Queue_ID) REFERENCES C_Printing_Queue DEFERRABLE INITIALLY DEFERRED;

CREATE INDEX c_printing_queue_recipient_c_printing_queue_id
   ON c_printing_queue_recipient (c_printing_queue_id ASC NULLS LAST);
