-- Column: PP_Order.SeqNo
-- Column: PP_Order.SeqNo
-- 2024-08-21T15:29:32.961Z
-- oldValue: '@SQL=SELECT COALESCE(MAX(seqno),0)+10 AS DefaultValue FROM PP_Order' removed because set before interceptor `org.eevolution.model.validator.PP_Order#setSeqNo`
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2024-08-21 17:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584868
;

