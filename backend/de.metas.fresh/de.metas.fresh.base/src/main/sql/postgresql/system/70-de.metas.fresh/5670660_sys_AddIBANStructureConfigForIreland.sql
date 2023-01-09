-- Setup IBAN structure for Ireland

UPDATE c_country
SET bankcodechartype = 'a', branchcodechartype = 'n', accountnumberlength = '8', branchcodelength = '6',
    bankcodelength = '4', accountnumberchartype = 'n',  bankcodeseqno  = '10'
WHERE c_country_id = 212
;