alter table dd_order_candidate
    drop constraint if exists forwardpporderbomline_ddordercandidate
;

ALTER TABLE dd_order_candidate
    ADD CONSTRAINT forwardpporderbomline_ddordercandidate FOREIGN KEY (Forward_PP_Order_BOMLine_ID)
        REFERENCES public.PP_Order_BOMLine
        DEFERRABLE INITIALLY DEFERRED
;
