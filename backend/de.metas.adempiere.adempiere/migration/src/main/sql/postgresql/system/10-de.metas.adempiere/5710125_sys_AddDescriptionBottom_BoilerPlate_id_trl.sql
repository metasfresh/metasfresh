
UPDATE AD_Element SET Description='Dies ist ein wählbarer Text, der im unteren Schlusstextsfeld festgelegt wird.' WHERE AD_Element_ID=582798
;

-- Element: DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:18:11.547550100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Description='Dies ist ein wählbarer Text, der im unteren Schlusstextsfeld festgelegt wird.' , Updated=TO_TIMESTAMP('2023-11-09 18:18:11.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582798 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Description='Dies ist ein wählbarer Text, der im unteren Schlusstextsfeld festgelegt wird.' , Updated=TO_TIMESTAMP('2023-11-09 18:18:11.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582798 AND AD_Language='de_CH';
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Description='This is a selectable text that will be set in the description bottom field.' , Updated=TO_TIMESTAMP('2023-11-09 18:18:11.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582798 AND AD_Language='en_US';


/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582798,'de_CH')
;

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582798,'de_DE');

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582798,'en_US');
