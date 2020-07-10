-- where clause is the same for both queries
CREATE TABLE backup.m_attributesetinstance_gh6848_2020_06_15_Part_2 AS
SELECT *
FROM m_attributesetinstance
WHERE m_attributesetinstance_id IN
      (
          SELECT --
                 asi.m_attributesetinstance_id
                 --        ,
                 --        asi.description
                 --        ,
                 --        asi.*
          FROM m_attributesetinstance asi
          WHERE TRUE
            -- all ASI whose description == an ASI_ID
            AND asi.description::text IN (SELECT DISTINCT m_attributesetinstance_id::text
                                          FROM m_attributesetinstance)
            -- don't update ASIs who have records
            AND NOT exists(SELECT 1 FROM m_attributeinstance ai WHERE ai.m_attributesetinstance_id = asi.m_attributesetinstance_id)
      )
;


UPDATE m_attributesetinstance
SET description=NULL
WHERE m_attributesetinstance_id IN
      (
          SELECT --
                 asi.m_attributesetinstance_id
                 --        ,
                 --        asi.description
                 --        ,
                 --        asi.*
          FROM m_attributesetinstance asi
          WHERE TRUE
            -- all ASI whose description == an ASI_ID
            AND asi.description::text IN (SELECT DISTINCT m_attributesetinstance_id::text
                                          FROM m_attributesetinstance)
            -- don't update ASIs who have records
            AND NOT exists(SELECT 1 FROM m_attributeinstance ai WHERE ai.m_attributesetinstance_id = asi.m_attributesetinstance_id)
      )
;
