
-- NO: Norway
update c_country  set   bankcodelength      = 4 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                    , branchcodelength    = null 	   , branchcodechartype    = null		    , branchcodeseqno    = null
                    , accountnumberlength = 6 	   , accountnumberchartype = 'n' 			, accountnumberseqno = 20
                    , nationalcheckdigitlength=1  , nationalcheckdigitchartype = 'n'	    , nationalcheckdigitseqno  = 30
where countrycode  =  'NO'
;

