CREATE TABLE backup.m_pickingslot_hu_31052023 AS
SELECT *
FROM m_pickingslot_hu;


delete
from m_pickingslot_hu
where m_hu_id in (SELECT m_hu_id
                  FROM m_pickingslot_hu
                  GROUP BY m_hu_id
                  HAVING COUNT(m_hu_id) > 1)

  and m_pickingslot_hu_id not in (SELECT max(m_pickingslot_hu_id)
                                  FROM m_pickingslot_hu
                                  GROUP BY m_hu_id
                                  HAVING COUNT(m_hu_id) > 1);

drop index if exists m_pickingslot_hu_uq
;

create unique index m_pickingslot_hu_uq on m_pickingslot_hu (m_hu_id)
;

