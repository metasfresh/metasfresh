-- update  displaysequence
with rec as
         (
             select c.c_country_id,
                    c.name,
                    c.countrycode,
                    c.displaysequence,
                    replace(c.displaysequence, '@BP@', '@BP@ @CON@') as new_displaysequence
             from c_country c
             where displaysequence not ilike '%@CON@%'
             order by name
         )
update c_country set  displaysequence = new_displaysequence
from rec where rec.c_country_id = c_country.c_country_id;


-- update  displaysequencelocal
with rec as
         (
             select c.c_country_id,
                    c.name,
                    c.countrycode,
                    c.displaysequencelocal,
                    replace(c.displaysequencelocal, '@BP@', '@BP@ @CON@') as new_displaysequencelocal
             from c_country c
             where displaysequencelocal not ilike '%@CON@%'
             order by name
         )
update c_country set  displaysequencelocal = new_displaysequencelocal
from rec where rec.c_country_id = c_country.c_country_id;
