-- AD_Element IsDropShipTaxFromDestCountry (585636): a shorter label, so the checkbox label is not truncated in the Organisation window

-- de_DE (base language)
UPDATE AD_Element_Trl SET Name='Streckengeschäft: Zielland-USt', PrintName='Streckengeschäft: Zielland-USt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-27 18:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585636 AND AD_Language='de_DE'
;

/* DDL */ select update_ad_element_on_ad_element_trl_update(585636,'de_DE')
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585636,'de_DE')
;

-- de_CH
UPDATE AD_Element_Trl SET Name='Streckengeschäft: Zielland-USt', PrintName='Streckengeschäft: Zielland-USt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-27 18:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585636 AND AD_Language='de_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585636,'de_CH')
;

-- en_US
UPDATE AD_Element_Trl SET Name='Drop-ship: destination tax', PrintName='Drop-ship: destination tax', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-27 18:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585636 AND AD_Language='en_US'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585636,'en_US')
;

-- the untranslated copies of the other languages follow the base language
UPDATE AD_Element_Trl SET Name='Streckengeschäft: Zielland-USt', PrintName='Streckengeschäft: Zielland-USt', Updated=TO_TIMESTAMP('2026-11-27 18:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585636 AND AD_Language NOT IN ('de_DE','de_CH','en_US') AND IsTranslated='N'
;

/* DDL */ select update_Column_Translation_From_AD_Element(585636)
;

-- the UI element carries the element's name as well
UPDATE AD_UI_Element SET Name='Streckengeschäft: Zielland-USt', Updated=TO_TIMESTAMP('2026-11-27 18:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_UI_Element_ID=656525
;
