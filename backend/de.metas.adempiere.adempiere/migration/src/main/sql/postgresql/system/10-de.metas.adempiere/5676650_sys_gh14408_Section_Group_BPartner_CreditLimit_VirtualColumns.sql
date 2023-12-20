-- Column: C_BPartner.CreditLimitIndicator
-- Column SQL (old): (CASE WHEN C_BPartner.IsSectionGroupPartner = 'Y' THEN (SELECT CASE WHEN v.SO_CreditUsed = 0 THEN '0%'     ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 ) || '%' END from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.Section_Group_Partner_ID limit 1)     ELSE (SELECT MAX(CreditLimitIndicator)     from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)
-- 2023-02-10T19:01:19.587Z
UPDATE AD_Column SET ColumnSQL='(SELECT CASE          WHEN C_BPartner.IsSectionGroupPartner = ''Y'' THEN (SELECT getSOCreditUsedPercentForSectionGroupPartner(C_BPartner.C_BPartner_ID, now()::date))                                                      ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)      END)',Updated=TO_TIMESTAMP('2023-02-10 21:01:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Column SQL (old): (CASE WHEN C_BPartner.IsSectionGroupPartner = 'Y' THEN (SELECT CASE WHEN v.Delivery_CreditUsed = 0 THEN '0%' ELSE round((v.Delivery_CreditUsed*100/v.CreditLimit),3 )     || '%' END from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.Section_Group_Partner_ID limit 1)     ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)
-- 2023-02-10T19:01:57.520Z
UPDATE AD_Column SET ColumnSQL='(SELECT CASE             WHEN C_BPartner.IsSectionGroupPartner = ''Y'' THEN (SELECT getDeliveryCreditUsedPercentForSectionGroupPartner(C_BPartner.C_BPartner_ID, now()::date))                                                         ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)         END)',Updated=TO_TIMESTAMP('2023-02-10 21:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585625
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Column SQL (old): (SELECT CASE             WHEN C_BPartner.IsSectionGroupPartner = 'Y' THEN (SELECT getDeliveryCreditUsedPercentForSectionGroupPartner(C_BPartner.C_BPartner_ID, now()::date))                                                         ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)         END)
-- 2023-02-10T19:51:53.812Z
UPDATE AD_Column SET ColumnSQL='(SELECT CASE             WHEN C_BPartner.IsSectionGroupPartner = ''Y'' THEN (SELECT getDeliveryCreditUsedPercentForSectionGroupPartner(C_BPartner.C_BPartner_ID, now()::date))                                                         ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)         END)',Updated=TO_TIMESTAMP('2023-02-10 21:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585625
;

