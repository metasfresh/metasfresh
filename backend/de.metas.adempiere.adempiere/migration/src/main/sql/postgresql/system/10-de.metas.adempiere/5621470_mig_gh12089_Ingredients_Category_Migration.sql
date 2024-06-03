CREATE TABLE backup.BKP_M_Ingredients_gh12089_13012022
AS
SELECT *
FROM m_ingredients
;



UPDATE m_ingredients i
SET category = x.category
FROM (SELECT CASE "Kategorie"
                 WHEN 'Fettlösliches  Vitamin'  THEN 'FSV'
                 WHEN 'Mineralstoff'            THEN 'M'
                 WHEN 'Spurenelement'           THEN 'TE'
                 WHEN 'Wasserlösliches Vitamin' THEN 'WSV'
                 WHEN 'Sonstige Zutat'          THEN 'O'
             END
                 AS category,
             n."Name" as name
      FROM migration_data."Nahrstoffe" n
     ) x
WHERE i.name LIKE x.name;