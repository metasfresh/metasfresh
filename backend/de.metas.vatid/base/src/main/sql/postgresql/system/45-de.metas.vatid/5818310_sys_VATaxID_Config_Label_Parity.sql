-- VAT-ID online check: English label parity fixes on VATaxID_Config fields.
--   M1: "On Service Unavailable" (AD_Field 781907) drops the sense of the German
--       "Verhalten bei nicht erreichbarem Dienst" -> rename to "Behavior on Service Unavailable".
--   M2: "Requester VAT Number" (AD_Field 781905) mixes terminology with the window's "VAT-ID" ->
--       rename to "Requester VAT-ID" for parity with the fixed German term "USt-IdNr.".
-- Both column-level AD_Elements (585172, 585170) are each used by exactly one column, so they are
-- mutated directly (no other usage to fork away from).

UPDATE AD_Element_Trl
SET Name = 'Behavior on Service Unavailable', Updated = TO_TIMESTAMP('2026-08-11 16:55:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585172;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585172);

UPDATE AD_Element_Trl
SET Name = 'Requester VAT-ID', Updated = TO_TIMESTAMP('2026-08-11 16:55:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585170;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585170);
