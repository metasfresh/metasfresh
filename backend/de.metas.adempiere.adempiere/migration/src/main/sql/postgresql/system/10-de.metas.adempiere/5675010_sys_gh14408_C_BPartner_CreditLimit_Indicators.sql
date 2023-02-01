-- Column: C_BPartner.CreditLimitIndicator
-- Column SQL (old): (select MAX(CreditLimitIndicator) from C_BPartner_Stats s where s.C_BPartner_ID = C_BPartner.C_BPartner_ID)
-- 2023-02-01T23:26:12.314Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL               THEN (SELECT CASE                             WHEN v.SO_CreditUsed = 0                             THEN ''0%''                             ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 )  || ''%''                             END                             from C_BPartner_CreditLimit_Departments_V v)                             ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)',Updated=TO_TIMESTAMP('2023-02-02 01:26:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Column SQL (old): (select MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats s where s.C_BPartner_ID = C_BPartner.C_BPartner_ID)
-- 2023-02-01T23:27:23.051Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL     THEN (SELECT CASE     WHEN v.Delivery_CreditUsed = 0     THEN ''0%''     ELSE round((v.Delivery_CreditUsed*100/v.CreditLimit),3 )  || ''%''     END     from C_BPartner_CreditLimit_Departments_V v)     ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)     END)',Updated=TO_TIMESTAMP('2023-02-02 01:27:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585625
;

