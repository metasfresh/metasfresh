-- P_CostAdjustment_Acct (AD_Element 2848): the account is the counter-account of the cost revaluation document ("Kosten Neubewertung"),
-- not a landed-cost account. Rename it in de_DE / de_CH / fr_CH and describe it in every system language.
-- en_GB keeps its row (not a system language).

-- de_DE
UPDATE AD_Element_Trl SET Name='Kostenanpassung (Neubewertung)', PrintName='Kostenanpassung (Neubewertung)', Description='Gegenkonto des Belegs „Kosten Neubewertung“ für die Bewertungsdifferenz des Lagerbestands', Help='Konto, auf das der Beleg „Kosten Neubewertung“ die Wertdifferenz des Lagerbestands bucht, wenn sich der Einstandspreis eines Produkts ändert.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-27 16:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=2848 AND AD_Language='de_DE'
;

-- de_CH
UPDATE AD_Element_Trl SET Name='Kostenanpassung (Neubewertung)', PrintName='Kostenanpassung (Neubewertung)', Description='Gegenkonto des Belegs „Kosten Neubewertung“ für die Bewertungsdifferenz des Lagerbestands', Help='Konto, auf das der Beleg „Kosten Neubewertung“ die Wertdifferenz des Lagerbestands bucht, wenn sich der Einstandspreis eines Produkts ändert.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-27 16:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=2848 AND AD_Language='de_CH'
;

-- en_US: the name "Cost Adjustment" stays
UPDATE AD_Element_Trl SET Name='Cost Adjustment', PrintName='Cost Adjustment', Description='Counter-account of the cost revaluation for the stock value difference', Help='Account to which the cost revaluation posts the difference in stock value when a product''s cost price changes.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-27 16:30:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=2848 AND AD_Language='en_US'
;

-- fr_CH
UPDATE AD_Element_Trl SET Name='Ajustement des coûts (réévaluation)', PrintName='Ajustement des coûts (réévaluation)', Description='Contre-compte de la réévaluation des coûts pour l''écart de valeur du stock', Help='Compte sur lequel la réévaluation des coûts comptabilise l''écart de valeur du stock lorsque le prix de revient d''un produit change.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-27 16:30:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=2848 AND AD_Language='fr_CH'
;

-- propagate to AD_Element (base language) and to the columns, fields, process parameters etc. of the element
SELECT update_ad_element_on_ad_element_trl_update(2848,'de_DE')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2848,'de_DE')
;

SELECT update_ad_element_on_ad_element_trl_update(2848,'de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2848,'de_CH')
;

SELECT update_ad_element_on_ad_element_trl_update(2848,'en_US')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2848,'en_US')
;

SELECT update_ad_element_on_ad_element_trl_update(2848,'fr_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2848,'fr_CH')
;
