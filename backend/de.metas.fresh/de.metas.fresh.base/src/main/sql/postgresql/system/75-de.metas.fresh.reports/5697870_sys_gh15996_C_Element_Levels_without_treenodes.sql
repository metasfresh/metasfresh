
DROP VIEW IF EXISTS C_Element_levels;
CREATE OR REPLACE VIEW C_Element_levels AS
SELECT
    ev1.C_ElementValue_ID AS lvl1_C_ElementValue_ID, ev1.value AS lvl1_value, ev1.name AS lvl1_name, (ev1.value::text || ' '::text) || ev1.name::text AS lvl1_label
     , ev2.C_ElementValue_ID AS lvl2_C_ElementValue_ID, ev2.value AS lvl2_value, ev2.name AS lvl2_name, (ev2.value::text || ' '::text) || ev2.name::text AS lvl2_label
     , ev3.C_ElementValue_ID AS lvl3_C_ElementValue_ID, ev3.value AS lvl3_value, ev3.name AS lvl3_name, (ev3.value::text || ' '::text) || ev3.name::text AS lvl3_label
     , ev4.C_ElementValue_ID AS lvl4_C_ElementValue_ID, ev4.value AS lvl4_value, ev4.name AS lvl4_name, (ev4.value::text || ' '::text) || ev4.name::text AS lvl4_label
     , ev5.C_ElementValue_ID AS lvl5_C_ElementValue_ID, ev5.value AS lvl5_value, ev5.name AS lvl5_name, (ev5.value::text || ' '::text) || ev5.name::text AS lvl5_label
     , COALESCE(ev5.C_ElementValue_ID, ev4.C_ElementValue_ID, ev3.C_ElementValue_ID, ev2.C_ElementValue_ID, ev1.C_ElementValue_ID) AS C_ElementValue_ID
     , (CASE
            WHEN ev5.C_ElementValue_ID IS NOT NULL THEN ev5.value
            WHEN ev4.C_ElementValue_ID IS NOT NULL THEN ev4.value
            WHEN ev3.C_ElementValue_ID IS NOT NULL THEN ev3.value
            WHEN ev2.C_ElementValue_ID IS NOT NULL THEN ev2.value
            WHEN ev1.C_ElementValue_ID IS NOT NULL THEN ev1.value
                                                   ELSE NULL::character varying
        END) AS Value
     , (CASE
            WHEN ev5.C_ElementValue_ID IS NOT NULL THEN ev5.name
            WHEN ev4.C_ElementValue_ID IS NOT NULL THEN ev4.name
            WHEN ev3.C_ElementValue_ID IS NOT NULL THEN ev3.name
            WHEN ev2.C_ElementValue_ID IS NOT NULL THEN ev2.name
            WHEN ev1.C_ElementValue_ID IS NOT NULL THEN ev1.name
                                                   ELSE NULL::character varying
        END) AS Name
     , (CASE
            WHEN ev5.C_ElementValue_ID IS NOT NULL THEN (ev5.value::text || ' '::text) || ev5.name::text
            WHEN ev4.C_ElementValue_ID IS NOT NULL THEN (ev4.value::text || ' '::text) || ev4.name::text
            WHEN ev3.C_ElementValue_ID IS NOT NULL THEN (ev3.value::text || ' '::text) || ev3.name::text
            WHEN ev2.C_ElementValue_ID IS NOT NULL THEN (ev2.value::text || ' '::text) || ev2.name::text
            WHEN ev1.C_ElementValue_ID IS NOT NULL THEN (ev1.value::text || ' '::text) || ev1.name::text
                                                   ELSE NULL::text
        END) AS Label
     , acctBalance(COALESCE(ev5.C_ElementValue_ID, ev4.C_ElementValue_ID, ev3.C_ElementValue_ID, ev2.C_ElementValue_ID, ev1.C_ElementValue_ID), 1::numeric, 0::numeric) AS Multiplicator
FROM C_ElementValue ev1
         LEFT JOIN C_ElementValue ev2 ON ev2.Parent_ID = ev1.C_ElementValue_ID
         LEFT JOIN C_ElementValue ev3 ON ev3.Parent_ID = ev2.C_ElementValue_ID
         LEFT JOIN C_ElementValue ev4 ON ev4.Parent_ID = ev3.C_ElementValue_ID
         LEFT JOIN C_ElementValue ev5 ON ev5.Parent_ID = ev4.C_ElementValue_ID
;
