-- Nov 29, 2016 12:01 PM
-- C_Flatrate_Conditions eligible from OL
UPDATE AD_Val_Rule SET Code='Type_Conditions=''Subscr'' AND DocStatus=''CO'' AND IsSimulation=''N''
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
 ) )

AND ''@IsDropShip@''=''N'' AND ''@DeliveryRule@''!=''M'' 
AND (select dt.DocSubType from C_DocType dt where dt.C_DocType_ID=@C_DocTypeTarget_ID@) not in (''ON'',''OB'',''WR'')',Updated=TO_TIMESTAMP('2016-11-29 12:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540001
;

