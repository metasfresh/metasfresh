-- 2019-06-05T13:43:03.549
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-05 13:43:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550667
;

-- 2019-06-05T14:06:01.175
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Mahndatum', PrintName='Mahndatum',Updated=TO_TIMESTAMP('2019-06-05 14:06:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1883 AND AD_Language='fr_CH'
;

-- 2019-06-05T14:06:38.532
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2019-06-05 14:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1883 AND AD_Language='fr_CH'
;

-- 2019-06-05T14:07:11.530
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Mahndatum', PrintName='Mahndatum',Updated=TO_TIMESTAMP('2019-06-05 14:07:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1883 AND AD_Language='de_CH'
;

-- 2019-06-05T14:07:29.765
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Mahndatum', PrintName='Mahndatum',Updated=TO_TIMESTAMP('2019-06-05 14:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1883 AND AD_Language='de_DE'
;

-- 2019-06-05T14:07:47.709
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2019-06-05 14:07:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1883 AND AD_Language='en_US'
;

select update_trl_tables_on_ad_element_trl_update(1883, 'en_US');
select update_trl_tables_on_ad_element_trl_update(1883, 'de_DE');
select update_trl_tables_on_ad_element_trl_update(1883, 'de_CH');
