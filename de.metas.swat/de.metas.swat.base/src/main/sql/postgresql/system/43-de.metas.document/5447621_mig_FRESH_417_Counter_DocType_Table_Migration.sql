

INSERT INTO C_DocBaseType_Counter
(
	AD_Client_ID,
	AD_Org_ID,
	C_DOCBASETYPE_COUNTER_ID,
	Counter_DocBaseType ,
	Created,
	CreatedBy,
	DocBaseType ,
	IsActive, Updated,
	UpdatedBy
)

VALUES

(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'SOO', now(), 100, 'POO', 'Y' , now(), 100), -- Sales Order -> Puschase Order
(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'POO', now(), 100, 'SOO', 'Y' , now(), 100), -- Purchase Order -> Sales Order
(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'API', now(), 100, 'ARI', 'Y' , now(), 100), -- AP Invoice -> AR Invoice
(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'ARI', now(), 100, 'API', 'Y' , now(), 100), -- AR Invoice -> AP Invoice
(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'MMS', now(), 100, 'MMR', 'Y' , now(), 100), -- Material Delivery -> Material Receipt
(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'MMR', now(), 100, 'MMS', 'Y' , now(), 100), -- Material Receipt -> Material Delivery
(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'APC', now(), 100, 'ARC', 'Y' , now(), 100), -- AP Credit Memo -> AR Credit Memo
(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'ARC', now(), 100, 'APC', 'Y' , now(), 100), -- AR Credit Memo -> AP Credit Memo
(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'ARR', now(), 100, 'APP', 'Y' , now(), 100), -- AR Receipt -> AP Payment
(1000000, 0, nextval('C_DOCBASETYPE_COUNTER_SEQ'), 'APP', now(), 100, 'ARR', 'Y' , now(), 100)  -- AP Payment -> AR Receipt

;
