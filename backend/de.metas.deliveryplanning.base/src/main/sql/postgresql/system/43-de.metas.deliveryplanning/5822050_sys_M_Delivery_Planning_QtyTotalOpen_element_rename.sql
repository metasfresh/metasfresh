-- Rename element 581682 (currently 'Menge gesamt (offen)' / no description) to the approved wording for
-- M_Delivery_Planning.QtyTotalOpen -- "Offene Menge (geliefert)" -- and give it its first description.
-- Safe to mutate directly: this element is used by exactly one column (QtyTotalOpen) and carries no
-- description/help today, so nothing else is affected and no description is being overwritten.
-- This is still a user-visible rename (the field currently reads "Menge gesamt (offen)").
--
-- fr_CH carries the en_US text with IsTranslated='N' -- see the fr_CH CONVENTION stated once in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql.

UPDATE AD_Element SET
  Name='Offene Menge (geliefert)', PrintName='Offene Menge (geliefert)',
  Description='Menge der Auftragsposition, die noch nicht geliefert wurde: Positionsmenge abzüglich der tatsächlichen Menge. Maßgeblich ist die Richtung — bei Wareneingang die Entlademenge, bei Warenausgang die Verlademenge.',
  Updated=TO_TIMESTAMP('2026-09-02 11:10:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581682
;

UPDATE AD_Element_Trl SET
  Name='Offene Menge (geliefert)', PrintName='Offene Menge (geliefert)',
  Description='Menge der Auftragsposition, die noch nicht geliefert wurde: Positionsmenge abzüglich der tatsächlichen Menge. Maßgeblich ist die Richtung — bei Wareneingang die Entlademenge, bei Warenausgang die Verlademenge.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-09-02 11:10:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581682 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Element_Trl SET
  Name='Open Quantity (delivered)', PrintName='Open Quantity (delivered)',
  Description='Quantity of the order line not yet delivered: ordered quantity less the actual quantity — discharge for a receipt, load for a shipment.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-09-02 11:10:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581682 AND AD_Language='en_US'
;

-- fr_CH per the fr_CH CONVENTION: the en_US text, IsTranslated='N'
UPDATE AD_Element_Trl trl
   SET Name         = en.Name,
       PrintName    = en.PrintName,
       Description  = en.Description,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-02 11:10:14', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Element_Trl en
 WHERE en.AD_Element_ID = trl.AD_Element_ID
   AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH'
   AND trl.AD_Element_ID = 581682
;

-- Propagate to every dependent (AD_Column/AD_Column_Trl, AD_Field/AD_Field_Trl, ...) for all four
-- languages in one call.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581682, NULL);
