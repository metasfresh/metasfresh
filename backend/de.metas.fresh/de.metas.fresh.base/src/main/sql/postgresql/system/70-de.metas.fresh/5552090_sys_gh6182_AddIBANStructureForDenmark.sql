-- add iban structure and Contact in the display sequence
UPDATE c_country 
SET displaysequence = '@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@',  displaysequencelocal = '@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@', 
bankcodechartype = 'n', bankcodelength = '4', bankcodeseqno = '10',
accountnumberchartype = 'n', accountnumberlength = '10', accountnumberseqno = '20' WHERE c_country_id = 167;