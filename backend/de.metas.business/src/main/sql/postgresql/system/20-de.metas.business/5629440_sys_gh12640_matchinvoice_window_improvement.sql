CREATE TABLE backup.m_matchinv_gh11167_20220311 as SELECT * FROM m_matchinv;

update m_matchinv  set c_invoice_id = (select il.c_invoice_id 
                                       from c_invoiceline il 
                                       where il.c_invoiceline_id = m_matchinv.c_invoiceline_id)
where c_invoice_id is null;


update m_matchinv  set m_inout_id= (select iol.m_inout_id 
                                    from m_inoutline iol
                                    where iol.m_inoutline_id = m_matchinv.m_inoutline_id)
where m_inout_id is null;
