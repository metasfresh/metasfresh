SELECT backup_table('m_hu')
;

UPDATE m_hu
SET isexternalproperty='Y',
    updatedby=100,
    updated=TO_TIMESTAMP('2023-06-06 12:12:12', 'YYYY-MM-DD HH24:MI:SS')
FROM m_hu_storage hus,
     m_product p,
     M_HU_Assignment hua
         INNER JOIN M_InOutLine iol ON hua.ad_table_id = get_table_id('M_InOutLine') AND hua.record_id = iol.m_inoutline_id
         INNER JOIN m_inOut io ON iol.m_inout_id = io.m_inout_id
         INNER JOIN c_doctype dt ON io.c_doctype_id = dt.c_doctype_id AND dt.isactive = 'Y' AND dt.ad_client_id = io.ad_client_id AND dt.ad_org_id IN (0, io.ad_org_id)
         INNER JOIN C_Project_Repair_Task prt ON iol.m_inoutline_id = prt.customerreturn_inoutline_id
WHERE m_hu.m_hu_id = hua.m_hu_id
  AND dt.docbasetype = 'MMR' -- material receipt
  AND dt.docsubtype = 'SR'   -- service repair order
  AND prt.type = 'W'         -- Service/Repair order
  AND m_hu.m_hu_id = hus.m_hu_id
  AND hus.m_product_id = p.m_product_id
;
