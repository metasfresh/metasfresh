-- New AD_Element 585383 'TransportDirection' ("Richtung"/"Direction") plus its translations, for
-- the three-valued transport direction Incoming/Outgoing/Dropship.
--
-- A new element rather than a rename of 581679 ("Lieferplanung Art"/"Type"): 581679 is shared by
-- M_Delivery_Planning, M_ShipperTransportation and a process parameter, and it is named after the
-- delivery-planning record type, not the direction its columns hold. The wanted caption already
-- exists verbatim on AD_Element 540579, but 540579 backs three unrelated columns and
-- AD_Element.ColumnName must equal the column's name anyway.
--
-- Description/Help live on the element rather than as per-field overrides: all three consumers
-- (AD_Column 585005, AD_Column 593410, the direction process parameter) mean the same concept, and
-- the "Dropship" arm is not self-evident, so each arm is spelled out.
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_MigrationScript 5820600 (this file)
--   AD_Element 585383 (TransportDirection)

-- 1) base element row (German in base column per convention)
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Description, Help, Updated, UpdatedBy)
VALUES (0, 585383 /*From ID Server*/, 0, 'TransportDirection', TO_TIMESTAMP('2026-08-27 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Richtung', 'Richtung',
        'Richtung des Transports: Eingehend, Ausgehend oder Streckengeschäft.',
        'Eingehend: Die Ware kommt vom Lieferanten ins eigene Lager. Ausgehend: Die Ware verlässt das eigene Lager zum Kunden. Streckengeschäft: Der Lieferant liefert direkt an den Kunden, die Ware berührt das eigene Lager nicht.',
        TO_TIMESTAMP('2026-08-27 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2) seed _Trl rows for every active system language (copies base DE text; IsTranslated='N')
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585383
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 3) en_US override: "Direction" (matches 540579) + the English Description/Help
UPDATE AD_Element_Trl SET Name='Direction', PrintName='Direction',
       Description='Direction of the transport: Incoming, Outgoing or Dropship.',
       Help='Incoming: the goods arrive from the vendor at your own warehouse. Outgoing: the goods leave your own warehouse for the customer. Dropship: the vendor delivers directly to the customer, so the goods never touch your own warehouse.',
       IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585383 AND AD_Language='en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585383,'en_US')
;

-- 4) de_DE: IsTranslated='Y'. The flag says this row's text is CORRECT for its language, not that
--    somebody translated it -- the seeded row already carries the authored German verbatim.
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585383 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585383,'de_DE')
;

-- 5) de_CH: the de_DE wording is correct Swiss German here too (no 'ss' divergence, no term swap).
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585383 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585383,'de_CH')
;

-- 6) fr_CH: Name overridden to "Direction"; IsTranslated stays 'N' because Description and Help
--    keep the seeded German, so the row as a whole is not correct for fr_CH.
UPDATE AD_Element_Trl SET Name='Direction', PrintName='Direction', Updated=TO_TIMESTAMP('2026-08-27 09:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585383 AND AD_Language='fr_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585383,'fr_CH')
;
