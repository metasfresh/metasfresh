-- 2026-04-30 https://github.com/metasfresh/me03/issues/29369
-- AD_Column rows for ReferenceDate and IsPaid

INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name, Description, FieldLength, IsMandatory, IsKey, IsParent, IsUpdateable, IsEncrypted, IsIdentifier, IsAllowLogging, IsSyncDatabase, DefaultValue, Version, EntityType, IsSelectionColumn, FilterOperator, PersonalDataCategory, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy) VALUES
  (592457 /*From ID Server*/, 542539, 584813 /*From ID Server*/, 15, 'ReferenceDate', 'Bezugsdatum', 'Datum, aus dem das Fälligkeitsdatum berechnet wird (Fälligkeitsdatum = Bezugsdatum + Verschiebungstage).', 7, 'N', 'N', 'N', 'Y', 'N', 'N', 'Y', 'Y', NULL, 0, 'D', 'Y', 'B', 'NP', 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0),
  (592458 /*From ID Server*/, 542539, 1402 /*existing AD_Element IsPaid*/, 20, 'IsPaid', 'Bezahlt', 'Wenn aktiviert, ist diese Zahlungsplan-Zeile bezahlt.', 1, 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'Y', 'N', 0, 'D', 'Y', 'E', 'NP', 0, 0, 'Y', '2026-04-30 12:00', 0, '2026-04-30 12:00', 0);

INSERT INTO AD_Column_Trl (AD_Column_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, IsTranslated)
  SELECT t.AD_Column_ID, l.AD_Language, t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy,
         t.Name, t.Description, 'N'
    FROM AD_Column t, AD_Language l
   WHERE t.AD_Column_ID IN (592457, 592458)
     AND l.IsSystemLanguage = 'Y'
     AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl WHERE AD_Column_ID = t.AD_Column_ID AND AD_Language = l.AD_Language);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584813);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1402); -- existing IsPaid element
