

--
-- tweak existing stuff
--
-- 01.03.2016 10:28
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.contracts.subscription', IsCentrallyMaintained='N', Name='Abo-Vertragsbedingungen',Updated=TO_TIMESTAMP('2016-03-01 10:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=540003
;

-- 01.03.2016 10:28
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=540003
;

-- 01.03.2016 11:03
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNo=75,Updated=TO_TIMESTAMP('2016-03-01 11:03:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=540003
;

-- 01.03.2016 11:07
-- URL zum Konzept
UPDATE AD_Val_Rule SET Description='Completed conditions with type=''Subscr''; the conditions'' pricing system needs to have sales price list applicable to the bpartner-locations country; ol''s product needs to be yet unset or needs to be in a matching price list; no dropship, and others',Updated=TO_TIMESTAMP('2016-03-01 11:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540001
;

-- 01.03.2016 11:07
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.contracts.subscription',Updated=TO_TIMESTAMP('2016-03-01 11:07:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540003
;

-- 01.03.2016 11:13
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=85, SeqNoGrid=85,Updated=TO_TIMESTAMP('2016-03-01 11:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548104
;

-- 01.03.2016 11:46
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2016-03-01 11:46:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=540003
;

-- 01.03.2016 11:46
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=75,Updated=TO_TIMESTAMP('2016-03-01 11:46:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=540003
;

-- 01.03.2016 12:00
-- URL zum Konzept
UPDATE AD_Ref_List SET Description='Regelmäßige Lieferungen ohne explizite Beauftragung jeder einzelnen Lieferung', EntityType='de.metas.contracts.subscription',Updated=TO_TIMESTAMP('2016-03-01 12:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540461
;

-- 01.03.2016 12:00
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=540461
;

-- 01.03.2016 12:02
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.contracts.subscription',Updated=TO_TIMESTAMP('2016-03-01 12:02:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551045
;

--
-- hide the conditions-product-mapping included tab for subscriptions (too much complexity for the user)
--
-- 01.03.2016 12:02
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Subscr''',Updated=TO_TIMESTAMP('2016-03-01 12:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548088
;

--
-- allow to select subscription conditions without pricing system
-- 
-- 01.03.2016 12:07
-- URL zum Konzept
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
                              and plv.ValidFrom=(select max(plv2.ValidFrom) from M_PriceList_Version plv2 WHERE pl.M_PriceList_ID=plv2.M_PriceList_ID)
                              and pp.M_Product_ID=@M_Product_ID@
                    )
          )
 ) )

AND ''@IsDropShip@''=''N'' AND ''@DeliveryRule@''!=''M'' 
AND (select dt.DocSubType from C_DocType dt where dt.C_DocType_ID=@C_DocTypeTarget_ID@) not in (''ON'',''OB'',''WR'')',Updated=TO_TIMESTAMP('2016-03-01 12:07:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540001
;

--
-- delete legacy callyouts that we moved to be programmatic
--
-- 01.03.2016 12:50
-- URL zum Konzept
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=540799
;

-- 01.03.2016 12:52
-- URL zum Konzept
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=540809
;

--
-- make C_Flatrate_Term.C_UOM_ID physical
--
-- 01.03.2016 15:24
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL=NULL,Updated=TO_TIMESTAMP('2016-03-01 15:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546003
;

--
-- make sure this handler is not accidentally started
-- 03.03.2016 11:31
-- URL zum Konzept
UPDATE C_ILCandHandler SET Description='Erstellt einen Rechnungskandiaten pro laufenden Vertrag.', IsActive='N', Name='Laufender Vertrag (keine Abos)',Updated=TO_TIMESTAMP('2016-03-03 11:31:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ILCandHandler_ID=540004
;



COMMIT;
-- 01.03.2016 15:24
-- URL zum Konzept
ALTER TABLE C_Flatrate_Term ADD C_UOM_ID NUMERIC(10) DEFAULT NULL 
;

