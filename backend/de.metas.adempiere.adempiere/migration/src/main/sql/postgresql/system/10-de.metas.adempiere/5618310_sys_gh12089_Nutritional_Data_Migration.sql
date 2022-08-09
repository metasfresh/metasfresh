INSERT INTO m_ingredients (ad_client_id,
                           ad_org_id,
                           created,
                           createdby,
                           isactive,
                           m_ingredients_id,
                           name,
                           updated,
                           updatedby)
SELECT 1000000,
       1000000,
       NOW(),
       99,
       'Y',
       NEXTVAL('m_ingredients_seq'),
       x."Name",
       NOW(),
       99
FROM (SELECT DISTINCT "Name" FROM migration_data."Nahrstoffe" WHERE "Kategorie" NOT LIKE 'Nährwert') x
;