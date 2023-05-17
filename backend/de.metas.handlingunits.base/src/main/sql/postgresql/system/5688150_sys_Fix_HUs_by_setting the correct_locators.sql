create table backup.m_hu_bkp_locator as SELECT * from m_HU;

with records as (
    SELECT t.qty,
           hu.m_hu_id,
           hu.hustatus,
           hu.m_locator_id,
           io.c_doctype_id,
           t.m_inout_id,
           iol.m_inoutline_id,
           iol.m_locator_id AS iol_locator_id,
           iol.m_product_id

    FROM m_hu hu
             JOIN m_hu_trace t ON t.m_hu_id = hu.m_hu_id
             JOIN m_inout io ON io.m_inout_id = t.m_inout_id and io.c_doctype_id=541005 -- repair
             JOIN m_hu_assignment hua ON hu.m_hu_id = hua.m_hu_id AND hua.ad_table_id = 320
             JOIN m_inoutline iol ON io.m_inout_id = iol.m_inout_id AND iol.m_inoutline_id = hua.record_id
   -- WHERE hu.m_locator_id IS NULL
    ORDER BY hu.updated DESC
)
update m_hu set m_locator_id= records.iol_locator_id from records where records.m_hu_id=m_hu.m_hu_id;