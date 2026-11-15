-- Card settlement of an open invoice at the POS till: the German label of the flag IsAbandoned (AD_Element 585600) and of
-- the status value AB (AD_Ref_List 544397, list 542154) was "Freigegeben", which reads like "approved" and collides with
-- the status "Genehmigt". It becomes "Als aufgegeben freigegeben". en_US stays "Released as abandoned".
--
-- Impact (element 585600 is used by exactly one record):
-- | Record                                         | old         | new                        |
-- |------------------------------------------------|-------------|----------------------------|
-- | AD_Column 593806 C_POS_InvoiceCardSettlement.IsAbandoned (+ its fields, via propagation) | Freigegeben | Als aufgegeben freigegeben |
-- | AD_Ref_List 544397 (AB) of list 542154          | Freigegeben | Als aufgegeben freigegeben |
-- Untranslated rows (IsTranslated='N', e.g. fr_CH) carry the German base text and follow it.

-- 2026-11-10T12:00:00
UPDATE AD_Element_Trl SET Name='Als aufgegeben freigegeben',PrintName='Als aufgegeben freigegeben',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585600 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-10T12:00:01
UPDATE AD_Element_Trl SET Name='Als aufgegeben freigegeben',PrintName='Als aufgegeben freigegeben',Updated=TO_TIMESTAMP('2026-11-10 12:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585600 AND IsTranslated='N'
;

-- 2026-11-10T12:00:02
/* DDL */  select update_ad_element_on_ad_element_trl_update(585600,'de_DE')
;

-- 2026-11-10T12:00:03
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585600)
;

-- 2026-11-10T12:00:04
UPDATE AD_Ref_List SET Name='Als aufgegeben freigegeben',Updated=TO_TIMESTAMP('2026-11-10 12:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=544397
;

-- 2026-11-10T12:00:05
UPDATE AD_Ref_List_Trl SET Name='Als aufgegeben freigegeben',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 12:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=544397 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-10T12:00:06
UPDATE AD_Ref_List_Trl SET Name='Als aufgegeben freigegeben',Updated=TO_TIMESTAMP('2026-11-10 12:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=544397 AND IsTranslated='N'
;
