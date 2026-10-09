-- Rewording of the description of element 585530 "Abrufvertrag" / "Call-off Contract" (created by 5828890; the
-- caption of C_OrderLine.C_Flatrate_Term_ID on the sales and purchase order line tabs): the last sentence now says
-- where the contract of a compensation group is recorded.

UPDATE AD_Element_Trl
SET Description  = 'Vertrag, aus dem diese Position abgerufen wird; Preis und Restmenge kommen aus dem Vertrag. Der Vertrag einer Kompensationsgruppe steht an der Kompensationsgruppe.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 17:20:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585530
  AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Element_Trl
SET Description  = 'Contract this line is called off from; price and remaining quantity come from the contract. The contract of a compensation group is recorded on the compensation group.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 17:20:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585530
  AND AD_Language = 'en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585530, 'de_DE')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585530, 'de_CH')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585530, 'en_US')
;
