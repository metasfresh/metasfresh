UPDATE c_invoice
SET isfec='Y'
WHERE (c_foreignexchangecontract_id IS NOT NULL OR fec_currencyRate IS NOT NULL)
;

UPDATE c_invoice i
SET fec_order_currency_id=(SELECT o.c_currency_id FROM c_order o WHERE o.c_order_id = i.c_order_id)
WHERE i.isfec = 'Y'
  AND i.fec_order_currency_id IS NULL
;

UPDATE c_invoice i
SET fec_from_currency_id=fec.c_currency_id,
    fec_to_currency_id=fec.to_currency_id,
    fec_currencyrate=fec.currencyrate
FROM c_foreignexchangecontract fec
WHERE i.isfec = 'Y'
  AND i.c_foreignexchangecontract_id IS NOT NULL
  AND fec.c_foreignexchangecontract_id = i.c_foreignexchangecontract_id
;


UPDATE c_invoice i
SET fec_from_currency_id=i.fec_order_currency_id
WHERE i.isfec = 'Y'
  AND i.fec_from_currency_id IS NULL
;

UPDATE c_invoice i
SET fec_to_currency_id=(SELECT acs.c_currency_id FROM c_acctschema acs WHERE acs.c_acctschema_id = getC_AcctSchema_ID(i.ad_client_id, i.ad_org_id))
WHERE i.isfec = 'Y'
  AND i.fec_to_currency_id IS NULL
;

