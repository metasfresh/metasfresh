-- Product Life Cycle Status (BBS-Status) ref-list (AD_Reference 542123) — fix de_DE display regression.
--
-- The tenant's base language is de_DE, so AD_Ref_List.Name is the GERMAN (base-language) display label
-- shown in the WebUI dropdown/grid — it must be German, not English. The generated Java constant is NOT
-- derived from Name; it comes from AD_Ref_List.ValueName (the English identifier). Compare the core
-- ProductType ref-list: Name='Artikel' (German), ValueName='Item' (English) -> constant PRODUCTTYPE_Item,
-- while de_DE users see "Artikel".
--
-- 5817220 wrongly put the English text into Name (leaving ValueName empty), so de_DE users saw the English
-- label ("Blocked" instead of "Gesperrt"). This restores the German base Name AND sets the English
-- ValueName, which keeps the English constants (_PhaseOut / _Blocked / _DeliveryStop) unchanged. de_DE/de_CH
-- Trl (German) and en_US Trl (English) are already correct and untouched.

UPDATE AD_Ref_List SET Name='Auslauf',     ValueName='PhaseOut',     Updated=TO_TIMESTAMP('2026-08-01 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544325 /* A */ AND Name='Phase out';

UPDATE AD_Ref_List SET Name='Gesperrt',    ValueName='Blocked',      Updated=TO_TIMESTAMP('2026-08-01 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544326 /* G */ AND Name='Blocked';

UPDATE AD_Ref_List SET Name='Lieferstopp', ValueName='DeliveryStop', Updated=TO_TIMESTAMP('2026-08-01 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544327 /* N */ AND Name='Delivery stop';

UPDATE AD_Ref_List SET ValueName='OK',     Updated=TO_TIMESTAMP('2026-08-01 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544324 /* O */ AND Name='OK';
