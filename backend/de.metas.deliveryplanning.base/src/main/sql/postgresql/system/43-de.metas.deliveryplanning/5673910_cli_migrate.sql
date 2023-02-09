UPDATE m_inout
SET isfec='Y'
WHERE (c_foreignexchangecontract_id IS NOT NULL OR fec_currencyRate IS NOT NULL)
;

UPDATE m_inout io
SET fec_order_currency_id=(SELECT o.c_currency_id FROM c_order o WHERE o.c_order_id = io.c_order_id)
WHERE io.isfec = 'Y'
  AND io.fec_order_currency_id IS NULL
;

UPDATE m_inout io
SET fec_from_currency_id=fec.c_currency_id,
    fec_to_currency_id=fec.to_currency_id,
    fec_currencyrate=fec.currencyrate
FROM c_foreignexchangecontract fec
WHERE io.isfec = 'Y'
  AND io.c_foreignexchangecontract_id IS NOT NULL
  AND fec.c_foreignexchangecontract_id = io.c_foreignexchangecontract_id
;

UPDATE c_foreignexchangecontract fec
SET fec_maturitydate=fec_validitydate
WHERE fec.fec_maturitydate < fec.fec_validitydate
;


