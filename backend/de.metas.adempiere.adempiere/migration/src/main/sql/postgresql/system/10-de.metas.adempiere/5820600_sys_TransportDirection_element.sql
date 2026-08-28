-- TransportDirection: new AD_Element for the three-valued transport direction
-- (Incoming/Outgoing/Dropship) currently carried under the misnamed AD_Element 581679
-- "M_Delivery_Planning_Type".
--
-- Why a NEW element instead of renaming 581679 in place: 581679 is named after *delivery
-- planning*, but the column it backs (M_Delivery_Planning.M_Delivery_Planning_Type and, since
-- this branch's earlier script 5820430, M_ShipperTransportation.M_Delivery_Planning_Type)
-- describes the *transport direction* -- M_ShipperTransportation also carries it for transport
-- orders, which are not deliveries. The tell was already in the metadata: all three AD_Field
-- rows on 581679 (708076, 783020, 783021) carry an AD_Name_ID override to element 540579
-- ("Richtung"/"Direction")
-- -- an element whose own name is displayed nowhere is misnamed. 540579 itself cannot be reused:
-- it already backs ImpEx_ConnectorType, RabbitMQ_Message_Audit and R_Request, so a fourth
-- unrelated meaning is out, and metasfresh convention requires AD_Element.ColumnName to equal the
-- column's name anyway.
--
-- Description/Help live on the element rather than as per-field overrides: all three consumers
-- (AD_Column 585005, AD_Column 593410, the direction process parameter) mean the same concept, so
-- one shared text is right and a fork would be wrong. The value is mandatory and defaultless and
-- its "Dropship" arm is not self-evident, so each of the three arms is spelled out.
--
-- This script only creates the element + its translations, copying 540579's caption exactly
-- (de_DE/de_CH "Richtung", en_US "Direction", fr_CH "Direction" IsTranslated='N' -- matching what
-- the three AD_Field overrides render today) so the on-screen caption does not change once later
-- scripts (5820610, 5820620, 5820630) repoint AD_Column 585005 / 593410 to this element and delete
-- the overrides.
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

-- 4) de_DE: Name/Description/Help already match the base row; flip IsTranslated='Y'
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585383 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585383,'de_DE')
;

-- 5) de_CH: Name/Description/Help already match the base row; flip IsTranslated='Y'
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 09:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585383 AND AD_Language='de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585383,'de_CH')
;

-- 6) fr_CH: override to "Direction", left IsTranslated='N' -- matches 540579's fr_CH exactly (and
--    what the three AD_Field overrides render today), so the caption does not change there either.
--    Description/Help keep the seeded German and stay IsTranslated='N', i.e. untranslated.
UPDATE AD_Element_Trl SET Name='Direction', PrintName='Direction', Updated=TO_TIMESTAMP('2026-08-27 09:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585383 AND AD_Language='fr_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585383,'fr_CH')
;
