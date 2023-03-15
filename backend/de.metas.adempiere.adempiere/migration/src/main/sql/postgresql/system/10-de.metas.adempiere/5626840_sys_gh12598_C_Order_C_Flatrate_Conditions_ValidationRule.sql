-- 2022-02-18T11:55:55.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='Type_Conditions IN (''Subscr'',''CallOrder'' )AND DocStatus=''CO'' AND IsSimulation=''N''
AND (
M_PricingSystem_ID IS NULL OR

/* following are conditions for the subscription contract''s pricing system */
M_PricingSystem_ID IN (
     select pl.M_PricingSystem_ID from M_PriceList pl 
        where 
           pl.M_PricingSystem_ID=C_Flatrate_Conditions.M_PricingSystem_ID 
           and pl.IsSOPriceList = ''@IsSOTrx@'' 

           /* the PL has no country or C_BPartner_Location_ID is not yet set or PL''s country matches the C_BPartner_Location''s country */
           and (pl.C_Country_ID is null or @C_BPartner_Location_ID@=0
              or pl.C_Country_ID=(
                 select max(C_Country_ID) from (
                   select l.C_Country_ID 
                   from C_BPartner_Location bpl left join C_Location l on l.C_Location_ID=bpl.C_Location_ID
                   where bpl.C_BPartner_Location_ID=@C_BPartner_Location_ID@
                 union 
                   select 0) val
               )
           )
          
          /* M_Product_ID is not yet set or there is a PP for the current M_Product_ID */
          and (@M_Product_ID@=0 
                    or exists (
                        select 1 
                        from M_PriceList_Version plv left join M_ProductPrice pp ON pp.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
                        where pl.M_PriceList_ID=plv.M_PriceList_ID
                              and plv.ValidFrom<=''@DateOrdered@''
                              and plv.ValidFrom=(select max(plv2.ValidFrom) from M_PriceList_Version plv2 where pl.M_PriceList_ID=plv2.M_PriceList_ID)
                              and pp.M_Product_ID=@M_Product_ID@
                    )
          )
 ) )',Updated=TO_TIMESTAMP('2022-02-18 13:55:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540001
;

-- 2022-02-18T12:55:58.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder''',Updated=TO_TIMESTAMP('2022-02-18 14:55:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551009
;

-- 2022-02-18T12:56:38.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder''',Updated=TO_TIMESTAMP('2022-02-18 14:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551476
;

-- 2022-02-18T12:57:25.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder''',Updated=TO_TIMESTAMP('2022-02-18 14:57:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563597
;

-- 2022-02-18T14:36:11.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder''',Updated=TO_TIMESTAMP('2022-02-18 16:36:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559719
;

-- 2022-02-25T12:01:24.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission''',Updated=TO_TIMESTAMP('2022-02-25 14:01:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559719
;



