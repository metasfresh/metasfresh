
-- guard against the case that two orders refer to the same DESADV 

-- Column: EDI_Desadv.DatePromised
-- Column SQL (old): (select o.DatePromised from c_order o where o.EDI_Desadv_ID=EDI_Desadv.EDI_Desadv_ID)
UPDATE AD_Column SET ColumnSQL='(select min(o.DatePromised) from c_order o where o.EDI_Desadv_ID=EDI_Desadv.EDI_Desadv_ID)',Updated=TO_TIMESTAMP('2024-12-04 16:33:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588664
;
