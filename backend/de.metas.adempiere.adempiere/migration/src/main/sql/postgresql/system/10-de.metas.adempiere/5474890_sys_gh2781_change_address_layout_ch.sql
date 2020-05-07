update c_country
set displaysequence = '@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@',
displaysequencelocal = '@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@'
where countrycode in ('CH');