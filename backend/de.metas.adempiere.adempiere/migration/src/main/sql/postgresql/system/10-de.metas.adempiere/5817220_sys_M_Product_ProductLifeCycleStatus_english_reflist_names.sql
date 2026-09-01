-- Product Life Cycle Status (BBS-Status) ref-list (AD_Reference 542123): the generated model constants
-- (X_M_Product.PRODUCTLIFECYCLESTATUS_*) are derived from AD_Ref_List.Name, so the base Name must be in
-- ENGLISH (like the O = "OK" value already is). 5816400 created the A/G/N items with GERMAN base Names
-- (Auslauf / Gesperrt / Lieferstopp), which produced German constants. Flip the base Name to English.
--
-- The user-visible label is unaffected and stays EN/DE per locale: the de_DE (and de_CH) AD_Ref_List_Trl
-- already carry the German label and the en_US Trl already carries the English one (both set by 5816400) --
-- only the technical, language-independent Name (and thus the generated constant) changes here.
--
-- After applying this, X_M_Product was regenerated via GenerateModel so PRODUCTLIFECYCLESTATUS_Auslauf/
-- Gesperrt/Lieferstopp become the English constants, and the BBSStatus enum + tests referencing them were
-- updated to match.

-- base Name uses a space ("Phase out") so the generated constant is a clean PRODUCTLIFECYCLESTATUS_PhaseOut
-- (camelCase), consistent with _DeliveryStop; the polished en_US UI label "Phase-out" (5816400) is kept.
UPDATE AD_Ref_List SET Name='Phase out',     Updated=TO_TIMESTAMP('2026-07-31 13:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544325 /* Value A */ AND Name='Auslauf';

UPDATE AD_Ref_List SET Name='Blocked',       Updated=TO_TIMESTAMP('2026-07-31 13:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544326 /* Value G */ AND Name='Gesperrt';

UPDATE AD_Ref_List SET Name='Delivery stop', Updated=TO_TIMESTAMP('2026-07-31 13:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544327 /* Value N */ AND Name='Lieferstopp';
