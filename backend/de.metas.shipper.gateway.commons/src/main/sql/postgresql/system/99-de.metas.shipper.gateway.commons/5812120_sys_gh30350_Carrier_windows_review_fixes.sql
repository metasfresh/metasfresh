-- gh30350 (review) — carrier master-window review fixes:
--   (1) detail-tab element groups must use a white (NULL) UIStyle, not 'primary' (which is header-only);
--   (2) align the Carrier_Service_ID element label with its window (Lieferweg-Service / Carrier Service);
--   (3) mark the en_US translations of the carrier PK elements as translated (sibling Carrier_Product_ID already is).

-- (1) Lieferweg-Produkt detail tabs (Warenarten + Services): element groups -> non-primary (white background)
UPDATE AD_UI_ElementGroup
   SET UIStyle=NULL,
       Updated=TO_TIMESTAMP('2026-07-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_UI_ElementGroup_ID IN (555454, 555455);

-- (2) Rename Carrier_Service_ID element (584113): Lieferweg-Servicekatalog -> Lieferweg-Service / Carrier Service.
--     Non-en_US rows hold the (untranslated) German base; en_US carries the real English translation.
UPDATE AD_Element
   SET Name='Lieferweg-Service', PrintName='Lieferweg-Service',
       Updated=TO_TIMESTAMP('2026-07-03 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=584113;

UPDATE AD_Element_Trl
   SET Name='Lieferweg-Service', PrintName='Lieferweg-Service', IsTranslated='N',
       Updated=TO_TIMESTAMP('2026-07-03 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=584113 AND AD_Language<>'en_US';

UPDATE AD_Element_Trl
   SET Name='Carrier Service', PrintName='Carrier Service', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-07-03 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=584113 AND AD_Language='en_US';

-- (3) The en_US translation of Carrier_Goods_Type_ID is a real translation -> IsTranslated='Y'
--     (Carrier_Service_ID's en_US flag is already set to 'Y' in step 2.)
UPDATE AD_Element_Trl
   SET IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-07-03 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=584112 AND AD_Language='en_US';

-- (4) Cascade the element name/flag changes into the referencing AD_Column_Trl / AD_Field_Trl tables
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584112 /*Carrier_Goods_Type_ID element*/);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584113 /*Carrier_Service_ID element*/);
