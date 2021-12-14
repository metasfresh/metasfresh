INSERT INTO m_nutrition_fact (ad_client_id,
                              ad_org_id,
                              created,
                              createdby,
                              description,
                              isactive,
                              m_nutrition_fact_id,
                              name,
                              updated,
                              updatedby)

SELECT 1000000,
       1000000,
       NOW(),
       99,
       x."Zusatzinfo",
       'Y',
       NEXTVAL('m_nutrition_fact_seq'),
       x."Name",
       NOW(),
       99
FROM (SELECT DISTINCT "Name", "Zusatzinfo" FROM migration_data."Nahrstoffe" WHERE "Kategorie" LIKE 'Nährwert') x
;