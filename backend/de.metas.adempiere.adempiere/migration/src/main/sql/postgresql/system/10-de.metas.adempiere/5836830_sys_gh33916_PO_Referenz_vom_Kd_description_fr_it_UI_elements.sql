-- Follow-up of 5836820: the new short German label "PO-Referenz vom Kd." of IsPOReferenceBuyerOrderNo (AD_Element 585626) also goes into
--  (1) the German description of IsEDIChannel (AD_Element 585625) that quotes it (en_US quotes the English label, unchanged),
--  (2) the fr_CH / it_CH translation rows of element 585626, which hold copies of the old German text,
--  (3) the name of the UI element of the new field on the Auftrag window.
-- IsTranslated stays 'N' on the German copies (per docs/REVIEW.md).

-- (1) Element IsEDIChannel: German text in de_DE / de_CH / fr_CH / it_CH (the non-en_US rows are copies of the German base text)
UPDATE AD_Element_Trl
SET Description = REPLACE(Description, '"PO-Referenz ist Bestellnr. des Kunden"', '"PO-Referenz vom Kd."'),
    Updated = TO_TIMESTAMP('2026-11-20 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585625 AND AD_Language <> 'en_US'
;

-- (2) Element IsPOReferenceBuyerOrderNo: fr_CH / it_CH
UPDATE AD_Element_Trl
SET Name = 'PO-Referenz vom Kd.', PrintName = 'PO-Referenz vom Kd.',
    Updated = TO_TIMESTAMP('2026-11-20 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585626 AND AD_Language IN ('fr_CH', 'it_CH')
;

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585625, 'de_DE')
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585625, 'de_DE')
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585625, 'de_CH')
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585625, 'fr_CH')
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585625, 'it_CH')
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585626, 'fr_CH')
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585626, 'it_CH')
;

-- (3) UI element of the new field on window 143 (tab 186); AD_UI_Element has no translation table and no propagation
UPDATE AD_UI_Element
SET Name = 'PO-Referenz vom Kd.', Updated = TO_TIMESTAMP('2026-11-20 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 656510
;
