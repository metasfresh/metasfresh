-- Shorter German label for the sales order flag IsPOReferenceBuyerOrderNo (AD_Element 585626):
-- "PO-Referenz ist Bestellnr. des Kunden" -> "PO-Referenz vom Kd."  (English "PO reference is customer's order no." and the descriptions stay unchanged).
-- The AD_Column and AD_Field names (windows 143 and its override) follow via the element propagation.
-- de_DE / de_CH keep IsTranslated='N' (German base text), per docs/REVIEW.md

-- Element: IsPOReferenceBuyerOrderNo
UPDATE AD_Element_Trl
SET Name = 'PO-Referenz vom Kd.', PrintName = 'PO-Referenz vom Kd.',
    Updated = TO_TIMESTAMP('2026-11-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585626 AND AD_Language = 'de_DE'
;

UPDATE AD_Element_Trl
SET Name = 'PO-Referenz vom Kd.', PrintName = 'PO-Referenz vom Kd.',
    Updated = TO_TIMESTAMP('2026-11-20 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585626 AND AD_Language = 'de_CH'
;

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585626, 'de_DE')
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585626, 'de_DE')
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585626, 'de_CH')
;
