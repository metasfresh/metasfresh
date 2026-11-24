-- Data source flag "EDI channel": marks a data source whose orders come in via EDI.
-- It only decides the initial value of the sales order flag IsPOReferenceBuyerOrderNo when an order is created from order candidates; it does not control any EDI export.

-- AD_Element
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID /*From ID Server*/, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585625 /*From ID Server*/, 0, 'IsEDIChannel',
        TO_TIMESTAMP('2026-11-19 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Aufträge aus dieser Eingabequelle kommen per EDI. Ein neuer Auftrag aus solchen Auftragskandidaten mit PO-Referenz wird als "PO-Referenz ist Bestellnr. des Kunden" markiert. Steuert keinen EDI-Export.',
        'de.metas.esb.edi', 'Y', 'EDI-Kanal', 'EDI-Kanal',
        TO_TIMESTAMP('2026-11-19 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, PO_Name, PO_PrintName, PO_Description, PO_Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, t.PO_Name, t.PO_PrintName, t.PO_Description, t.PO_Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585625
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl
SET Name = 'EDI channel', PrintName = 'EDI channel',
    Description = 'Orders from this data source come in via EDI. A new order created from such order candidates with a PO reference is marked "PO reference is customer''s order no.". Does not control any EDI export.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-19 14:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585625 AND AD_Language = 'en_US'
;

-- de_DE / de_CH keep IsTranslated='N' (German base text copied by the Trl seed), per docs/REVIEW.md

-- AD_Column (PG type CHAR(1) YesNo; the table owner EntityType is de.metas.impex, hence IsForceIncludeInGeneratedModel)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID /*From ID Server*/, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DefaultValue, Description, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsEncrypted, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 593855 /*From ID Server*/, 585625 /*From ID Server*/, 0, 20, 540273, 'IsEDIChannel',
        TO_TIMESTAMP('2026-11-19 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N',
        'Aufträge aus dieser Eingabequelle kommen per EDI. Ein neuer Auftrag aus solchen Auftragskandidaten mit PO-Referenz wird als "PO-Referenz ist Bestellnr. des Kunden" markiert. Steuert keinen EDI-Export.',
        'de.metas.esb.edi', 1, 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'N', 'Y', 'N', 'EDI-Kanal', 'NP', 0,
        TO_TIMESTAMP('2026-11-19 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593855
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585625)
;

-- DDL: new column, existing rows get 'N'
/* DDL */ SELECT public.db_alter_table('AD_InputDataSource','ALTER TABLE public.AD_InputDataSource ADD COLUMN IsEDIChannel CHAR(1) DEFAULT ''N'' NOT NULL CHECK (IsEDIChannel IN (''Y'', ''N''))')
;
