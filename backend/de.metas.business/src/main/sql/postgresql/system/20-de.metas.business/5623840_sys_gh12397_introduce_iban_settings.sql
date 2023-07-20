-- Creata a backup of c_country
DROP TABLE IF EXISTS backup.c_country_gh12397;

create table backup.c_country_gh12397 AS
select * from c_country;


-- structure provided on : https://bank.codes/iban/structure/

-- AD: Andorra
update c_country  set   bankcodelength  = 4		 	   , bankcodechartype ='n'					, bankcodeseqno  = 10
                      , branchcodelength = 4	 	   , branchcodechartype = 'n'				, branchcodeseqno  = 20
                      , accountnumberlength = 12 	   , accountnumberchartype ='c' 			, accountnumberseqno =30                                             
                      , nationalcheckdigitlength=null  , nationalcheckdigitchartype = null		, nationalcheckdigitseqno  = null
where countrycode  =  'AD'
;  

-- CZ
update c_country  set   bankcodelength  = 4		 	, bankcodechartype ='n'					, bankcodeseqno  = 10
                      , accountnumberlength = 16 	, accountnumberchartype ='n' 			, accountnumberseqno =20
                      , branchcodelength = null	 	, branchcodechartype = null				, branchcodeseqno  = null
                      , nationalcheckdigitlength = null, nationalcheckdigitchartype = null	, nationalcheckdigitseqno = null
where countrycode  =  'CZ'
;

-- HR: Croatia
update c_country  set   bankcodelength  = 7 		   , bankcodechartype ='n'					, bankcodeseqno  = 10
                      , branchcodelength = null	 	   , branchcodechartype = null				, branchcodeseqno  = null
                      , accountnumberlength = 10 	   , accountnumberchartype ='n' 			, accountnumberseqno =20                                             
                      , nationalcheckdigitlength=null  , nationalcheckdigitchartype = null		, nationalcheckdigitseqno  = null
where countrycode  =  'HR'
;  

-- CY: Cyprus
update c_country  set   bankcodelength      = 3 	   , bankcodechartype      ='n'				, bankcodeseqno      = 10
                      , branchcodelength    = 5	 	   , branchcodechartype    ='n'				, branchcodeseqno    = 20
                      , accountnumberlength = 16 	   , accountnumberchartype ='c' 			, accountnumberseqno = 30                                             
                      , nationalcheckdigitlength=null  , nationalcheckdigitchartype = null		, nationalcheckdigitseqno  = null
where countrycode  =  'CY'
; 

-- EE: Estonia
update c_country  set   bankcodelength      = 2 	   , bankcodechartype      ='n'				, bankcodeseqno      = 10
                      , branchcodelength    = 2	 	   , branchcodechartype    ='n'				, branchcodeseqno    = 20
                      , accountnumberlength = 11 	   , accountnumberchartype ='n' 			, accountnumberseqno = 30                                             
                      , nationalcheckdigitlength=1     , nationalcheckdigitchartype = 'n'		, nationalcheckdigitseqno  = 40
where countrycode  =  'EE'
; 

-- FI: Finland
update c_country  set   bankcodelength      = 6 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = null	   , branchcodechartype    = null		    , branchcodeseqno    = null
                      , accountnumberlength = 7 	   , accountnumberchartype = 'n' 			, accountnumberseqno = 20                                             
                      , nationalcheckdigitlength=1     , nationalcheckdigitchartype = 'n'		, nationalcheckdigitseqno  = 30
where countrycode  =  'FI'
; 

-- HU: Hungary
update c_country  set   bankcodelength      = 3 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = 4	       , branchcodechartype    = 'n'		    , branchcodeseqno    = 20
                      , accountnumberlength = 16 	   , accountnumberchartype = 'n' 			, accountnumberseqno = 30                                             
                      , nationalcheckdigitlength=1     , nationalcheckdigitchartype = 'n'		, nationalcheckdigitseqno  = 40
where countrycode  =  'HU'
; 

-- LT: Lithuania
update c_country  set   bankcodelength      = 5 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = null	   , branchcodechartype    = null		    , branchcodeseqno    = null
                      , accountnumberlength = 11 	   , accountnumberchartype = 'n' 			, accountnumberseqno = 20                                             
                      , nationalcheckdigitlength=null     , nationalcheckdigitchartype = null	, nationalcheckdigitseqno  = null
where countrycode  =  'LT'
; 

-- PL: Poland
update c_country  set   bankcodelength      = 3 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = 4 	   , branchcodechartype    = 'n'		    , branchcodeseqno    = 20
                      , accountnumberlength = 16 	   , accountnumberchartype = 'n' 			, accountnumberseqno = 40                                             
                      , nationalcheckdigitlength=1     , nationalcheckdigitchartype = 'n'		, nationalcheckdigitseqno  = 30
where countrycode  =  'PL'
; 

-- PT: Portugal
update c_country  set   bankcodelength      = 4 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = 4 	   , branchcodechartype    = 'n'		    , branchcodeseqno    = 20
                      , accountnumberlength = 11 	   , accountnumberchartype = 'n' 			, accountnumberseqno = 30                                             
                      , nationalcheckdigitlength=2     , nationalcheckdigitchartype = 'n'		, nationalcheckdigitseqno  = 40
where countrycode  =  'PT'
; 

-- SI: Slovenia
update c_country  set   bankcodelength      = 2 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = 3 	   , branchcodechartype    = 'n'		    , branchcodeseqno    = 20
                      , accountnumberlength = 8 	   , accountnumberchartype = 'n' 			, accountnumberseqno = 30                                             
                      , nationalcheckdigitlength=2     , nationalcheckdigitchartype = 'n'		, nationalcheckdigitseqno  = 40
where countrycode  =  'SI'
; 

-- SE: Sweden
update c_country  set   bankcodelength      = 3 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = null 	   , branchcodechartype    = null		    , branchcodeseqno    = null
                      , accountnumberlength = 17 	   , accountnumberchartype = 'n' 			, accountnumberseqno = 20                                             
                      , nationalcheckdigitlength=null    , nationalcheckdigitchartype = null	, nationalcheckdigitseqno  = null
where countrycode  =  'SE'
; 

-- GR: Greece
update c_country  set   bankcodelength      = 3 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = 4 	   , branchcodechartype    = 'n'		    , branchcodeseqno    = 20
                      , accountnumberlength = 16 	   , accountnumberchartype = 'c' 			, accountnumberseqno = 30                                             
                      , nationalcheckdigitlength=null    , nationalcheckdigitchartype = null	, nationalcheckdigitseqno  = null
where countrycode  =  'GR'
; 

-- MC: Monaco
update c_country  set   bankcodelength      = 5 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = 5 	   , branchcodechartype    = 'n'		    , branchcodeseqno    = 20
                      , accountnumberlength = 11 	   , accountnumberchartype = 'c' 			, accountnumberseqno = 30                                             
                      , nationalcheckdigitlength=2    , nationalcheckdigitchartype = 'n'	, nationalcheckdigitseqno  = 40
where countrycode  =  'MC'
; 

-- BG: Bulgaria
update c_country  set   bankcodelength      = 4 	   , bankcodechartype      = 'a'			, bankcodeseqno      = 10
                      , branchcodelength    = 4 	   , branchcodechartype    = 'n'		    , branchcodeseqno    = 20
                      , accounttypelength = 2          , accounttypechartype = 'n'              , accounttypeseqno   = 30
                      , accountnumberlength = 8 	   , accountnumberchartype = 'c' 			, accountnumberseqno = 40                                             
                      , nationalcheckdigitlength=null    , nationalcheckdigitchartype = null	, nationalcheckdigitseqno  = null
where countrycode  =  'BG'
;

-- LV: Latvia
update c_country  set   bankcodelength      = 4 	   , bankcodechartype      = 'a'			, bankcodeseqno      = 10
                      , branchcodelength    = null 	   , branchcodechartype    = null		    , branchcodeseqno    = null
                      , accountnumberlength = 13 	   , accountnumberchartype = 'c' 			, accountnumberseqno = 20                                             
                      , nationalcheckdigitlength=null    , nationalcheckdigitchartype = null	, nationalcheckdigitseqno  = null
where countrycode  =  'LV'
; 

-- MT: Malta
update c_country  set   bankcodelength      = 4 	   , bankcodechartype      = 'a'			, bankcodeseqno      = 10
                      , branchcodelength    = 5 	   , branchcodechartype    = 'n'		    , branchcodeseqno    = 20
                      , accountnumberlength = 18 	   , accountnumberchartype = 'c' 			, accountnumberseqno = 30                                             
                      , nationalcheckdigitlength=null    , nationalcheckdigitchartype = null	, nationalcheckdigitseqno  = null
where countrycode  =  'MT'
; 


-- SK: Slovakia
update c_country  set   bankcodelength      = 4 	   , bankcodechartype      = 'n'			, bankcodeseqno      = 10
                      , branchcodelength    = null 	   , branchcodechartype    = null		    , branchcodeseqno    = null
                      , accountnumberlength = 16 	   , accountnumberchartype = 'n' 			, accountnumberseqno = 20                                             
                      , nationalcheckdigitlength=null    , nationalcheckdigitchartype = null	, nationalcheckdigitseqno  = null
where countrycode  =  'SK'
; 

-- RO: Romania
update c_country  set   bankcodelength      = 4 	   , bankcodechartype      = 'a'			, bankcodeseqno      = 10
                      , branchcodelength    = null 	   , branchcodechartype    = null		    , branchcodeseqno    = null
                      , accountnumberlength = 16 	   , accountnumberchartype = 'c' 			, accountnumberseqno = 20                                             
                      , nationalcheckdigitlength=null  , nationalcheckdigitchartype = null	    , nationalcheckdigitseqno  = null
where countrycode  =  'RO'
; 
