UPDATE c_country
SET bankcodechartype         = 'n', branchcodechartype = NULL, accountnumberlength = '16',
    branchcodelength         = NULL, bankcodelength = '5', accountnumberchartype = 'c', accounttypelength = NULL,
    accounttypechartype      = NULL, nationalcheckdigitlength = '1', nationalcheckdigitchartype = 'n',
    owneraccountnumberlength = NULL, owneraccountnumberchartype = NULL, bankcodeseqno = '10',
    branchcodeseqno          = '20', accountnumberseqno = '30', accounttypeseqno = NULL,
    owneraccountnumberseqno  = NULL, nationalcheckdigitseqno = '40'
WHERE c_country_id = 326
;