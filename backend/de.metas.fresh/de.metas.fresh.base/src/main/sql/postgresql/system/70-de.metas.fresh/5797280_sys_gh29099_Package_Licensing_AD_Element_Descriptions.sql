-- gh#29099: Update AD_Element descriptions for OuterPackagingWeight and SmallPackagingWeight
-- to clarify that OuterPackagingWeight is the weight of the entire outer package (not per unit),
-- while SmallPackagingWeight is per unit.

-- OuterPackagingWeight (AD_Element_ID=583987): clarify it's the weight of the entire outer package
UPDATE AD_Element
SET Description   = 'Gewicht der gesamten Überverpackung (z.B. ein Karton), nicht pro Stück. Das Stückgewicht wird im Bericht durch Division mit dem Packvorschrift-Faktor berechnet.',
    Help          = 'Gewicht der gesamten Überverpackung (z.B. ein Karton) in kg. Im Zusammenfassungsbericht wird dieses Gewicht durch den Packvorschrift-Faktor geteilt, um das Überverpackungsgewicht pro Stück zu ermitteln.',
    Updated       = '2026-04-07 14:00',
    UpdatedBy     = 0
WHERE AD_Element_ID = 583987;

-- Propagate to AD_Element_Trl for de_DE and de_CH (base language = German)
UPDATE AD_Element_Trl
SET Description   = 'Gewicht der gesamten Überverpackung (z.B. ein Karton), nicht pro Stück. Das Stückgewicht wird im Bericht durch Division mit dem Packvorschrift-Faktor berechnet.',
    Help          = 'Gewicht der gesamten Überverpackung (z.B. ein Karton) in kg. Im Zusammenfassungsbericht wird dieses Gewicht durch den Packvorschrift-Faktor geteilt, um das Überverpackungsgewicht pro Stück zu ermitteln.',
    Updated       = '2026-04-07 14:00',
    UpdatedBy     = 0
WHERE AD_Element_ID = 583987
  AND AD_Language IN ('de_DE', 'de_CH');

-- English translation
UPDATE AD_Element_Trl
SET Description   = 'Weight of the entire outer package (e.g. one cardboard box), not per unit. The per-unit weight is calculated in the report by dividing by the Packaging Instruction Factor.',
    Help          = 'Weight of the entire outer package (e.g. one cardboard box) in kg. In the summary report, this weight is divided by the Packaging Instruction Factor to determine the outer packaging weight per unit.',
    IsTranslated  = 'Y',
    Updated       = '2026-04-07 14:00',
    UpdatedBy     = 0
WHERE AD_Element_ID = 583987
  AND AD_Language = 'en_US';

-- SmallPackagingWeight (AD_Element_ID=583986): clarify it's per unit
UPDATE AD_Element
SET Description   = 'Gewicht der Kleinverpackung (Haushalt) pro Stück in kg.',
    Help          = 'Gewicht der Kleinverpackung (Haushaltsverpackung) pro Stück in kg. Diese Verpackung erreicht den Endverbraucher (z.B. Glasgefäß, Kunststoffbecher).',
    Updated       = '2026-04-07 14:00',
    UpdatedBy     = 0
WHERE AD_Element_ID = 583986;

UPDATE AD_Element_Trl
SET Description   = 'Gewicht der Kleinverpackung (Haushalt) pro Stück in kg.',
    Help          = 'Gewicht der Kleinverpackung (Haushaltsverpackung) pro Stück in kg. Diese Verpackung erreicht den Endverbraucher (z.B. Glasgefäß, Kunststoffbecher).',
    Updated       = '2026-04-07 14:00',
    UpdatedBy     = 0
WHERE AD_Element_ID = 583986
  AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Description   = 'Weight of the small (household) packaging per unit in kg.',
    Help          = 'Weight of the small (household) packaging per unit in kg. This is the packaging that reaches the end consumer (e.g. glass jar, plastic cup).',
    IsTranslated  = 'Y',
    Updated       = '2026-04-07 14:00',
    UpdatedBy     = 0
WHERE AD_Element_ID = 583986
  AND AD_Language = 'en_US';

-- Propagate element changes to all dependent columns, fields, etc.
SELECT update_ad_element_on_ad_element_trl_update(583987, 'de_DE');
SELECT update_ad_element_on_ad_element_trl_update(583987, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583987, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583987, 'en_US');

SELECT update_ad_element_on_ad_element_trl_update(583986, 'de_DE');
SELECT update_ad_element_on_ad_element_trl_update(583986, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583986, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583986, 'en_US');
