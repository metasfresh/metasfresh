-- Refine the en_US label of M_Product.IsSerialNoPicked (AD_Element 585003).
-- Was "Serial No Picked" (reads as a past-participle state, not the config flag it is);
-- now "Require Serial No. on Picking" (clearer config-flag wording).
-- en_US-only translation change; de_DE/de_CH base ("Seriennummer kommissionieren") unchanged.

UPDATE AD_Element_Trl
   SET Name='Require Serial No. on Picking',
       PrintName='Require Serial No. on Picking',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-06-19 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
 WHERE AD_Element_ID=585003 AND AD_Language='en_US'
;

-- propagate AD_Element_Trl -> dependent _Trl tables (AD_Column_Trl, AD_Field_Trl, ...)
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585003,'en_US')
;
