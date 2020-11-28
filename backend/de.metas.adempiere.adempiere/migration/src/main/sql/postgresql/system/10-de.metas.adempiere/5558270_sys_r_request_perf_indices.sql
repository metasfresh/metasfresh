
CREATE INDEX IF NOT EXISTS r_request_c_invoice_id
    ON public.r_request (c_invoice_id);
CREATE INDEX IF NOT EXISTS r_request_m_inout_id
    ON public.r_request (m_inout_id);	
CREATE INDEX IF NOT EXISTS r_request_c_order_id
    ON public.r_request (c_order_id);	
CREATE INDEX IF NOT EXISTS r_request_c_payment_id
    ON public.r_request (c_payment_id);	
CREATE INDEX IF NOT EXISTS r_request_record_id
    ON public.r_request (record_id);
CREATE INDEX IF NOT EXISTS r_request_c_project_id
    ON public.r_request (c_project_id);	
