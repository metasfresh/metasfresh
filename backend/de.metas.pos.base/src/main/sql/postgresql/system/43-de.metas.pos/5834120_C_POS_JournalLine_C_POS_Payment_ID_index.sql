-- Lookup index for the "is this order payment already journaled on any journal" check (POSCashJournalRepository.retrieveJournaledPaymentIds).
-- Partial: only the order-payment lines carry C_POS_Payment_ID.
CREATE INDEX c_pos_journalline_payment ON C_POS_JournalLine (C_POS_Payment_ID) WHERE C_POS_Payment_ID IS NOT NULL
;
