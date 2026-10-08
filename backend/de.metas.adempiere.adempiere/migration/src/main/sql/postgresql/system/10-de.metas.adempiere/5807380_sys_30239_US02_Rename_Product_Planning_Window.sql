-- Rename window/tab "Product Planning" → "Product Plan Data" / "Produkt Plandaten"
-- AD_Element 53268 is shared by AD_Window 540750 and AD_Tab 542102

UPDATE AD_Element
SET    Name='Produkt Plandaten', PrintName='Produkt Plandaten',
       Updated=TO_TIMESTAMP('2026-06-11 00:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE  AD_Element_ID=53268;

UPDATE AD_Element_Trl
SET    Name='Product Plan Data', PrintName='Product Plan Data',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-06-11 00:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE  AD_Element_ID=53268 AND AD_Language='en_US';

SELECT update_window_translation_from_ad_element(53268);
SELECT update_tab_translation_from_ad_element(53268);
