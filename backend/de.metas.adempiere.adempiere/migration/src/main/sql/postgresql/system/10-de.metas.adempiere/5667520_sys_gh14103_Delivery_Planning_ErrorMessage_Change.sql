-- Value: M_Delivery_Planning_AtLeastOnePerOrderLine
-- 2022-12-07T16:16:49.792Z
UPDATE AD_Message SET MsgText='Line can not be deleted as it is the last delivery planning for this Orderline',Updated=TO_TIMESTAMP('2022-12-07 18:16:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545213
;

