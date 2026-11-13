-- Lookup index for the background sweep of the till's card payments, run in every SumUp poll cycle: it looks for card
-- payments whose result is unknown (to import SumUp's transaction) and for pending ones whose checkout stopped (orphans).
-- Partial: only unfinished card payments, so the index stays small however many payments the till makes.
CREATE INDEX c_pos_payment_card_unfinished ON C_POS_Payment (C_POS_Payment_ID) WHERE POSPaymentMethod = 'CARD' AND POSPaymentProcessingStatus IN ('PENDING', 'UNKNOWN')
;
