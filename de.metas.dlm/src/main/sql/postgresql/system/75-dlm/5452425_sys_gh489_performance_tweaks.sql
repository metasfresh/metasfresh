
--
-- DDL
--

--
-- to spead up the DLM trigger fuctions in particular
--
CREATE INDEX IF NOT EXISTS m_receiptschedule_alloc_m_hu_trx_line_id
   ON public.m_receiptschedule_alloc (m_hu_trx_line_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS ad_pinstance_record_id_ad_table_id
   ON public.ad_pinstance (record_id, ad_table_id);

CREATE INDEX IF NOT EXISTS c_queue_element_record_id_ad_table_id
   ON public.c_queue_element (record_id, ad_table_id);

CREATE INDEX IF NOT EXISTS m_transsaction_M_MovementLine_ID
  ON public.m_transaction (M_MovementLine_ID);

CREATE INDEX IF NOT EXISTS M_MovementLine_M_InOutLine_ID
  ON public.M_MovementLine (M_InOutLine_ID);

CREATE INDEX IF NOT EXISTS ad_pinstance_para_ad_pinstance_id
  ON public.ad_pinstance_para (ad_pinstance_id);

CREATE INDEX IF NOT EXISTS ad_pinstance_log_ad_pinstance_id
  ON public.ad_pinstance_log (ad_pinstance_id);
  
CREATE INDEX IF NOT EXISTS ad_issue_r_request_id
  ON public.ad_issue (r_request_id);

CREATE INDEX IF NOT EXISTS c_orderline_C_PackingMaterial_OrderLine_ID
  ON public.c_orderline (C_PackingMaterial_OrderLine_ID);

CREATE INDEX IF NOT EXISTS c_orderline_link_orderLine_id
   ON public.c_orderline (link_orderline_id);

CREATE INDEX c_orderline_ref_orderline_id
   ON public.c_orderline (ref_orderline_id);


CREATE INDEX IF NOT EXISTS pp_mrp_c_orderlineso_id
  ON public.pp_mrp (c_orderlineso_id);
  
--
-- to speed up loading the "latest" C_Orders when an order window is opened
-- i think C_Order.Updated is not changed that often, so i don'T expect a big punishment from loosing the HOT advantage
--
CREATE INDEX IF NOT EXISTS c_order_updated
  ON public.c_order (updated);
  
COMMIT;

--
-- DML
--


--
-- make C_OrderLine SQL columns lazy-loaded so the DB doesn't need to execute them every time it loads an order line somewhere in the BL
--
-- 26.10.2016 21:45
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-10-26 21:45:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544242
;

-- 26.10.2016 21:45
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-10-26 21:45:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544241
;

-- 26.10.2016 21:45
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-10-26 21:45:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544239
;

-- 26.10.2016 21:45
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-10-26 21:45:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544227
;

-- 26.10.2016 21:45
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-10-26 21:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544230
;

-- 26.10.2016 21:45
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-10-26 21:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544449
;

-- 26.10.2016 21:45
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-10-26 21:45:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544229
;

-- 26.10.2016 21:45
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-10-26 21:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544228
;

