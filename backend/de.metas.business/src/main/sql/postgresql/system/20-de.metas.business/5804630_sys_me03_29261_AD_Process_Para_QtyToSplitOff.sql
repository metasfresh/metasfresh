-- me03 #29261: Order Line Split
-- AD_Process_Para: QtyToSplitOff
-- IDs from ID server (http://idserver.metas.de):
-- AD_Process_Para -> 543209
-- AD_Element -> 584915

-- 2026-05-26T00:00:00.000Z
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName,
    Created, CreatedBy, EntityType, FieldLength, IsActive, IsCentrallyMaintained, IsMandatory, IsRange,
    Name, SeqNo, Updated, UpdatedBy
) VALUES (
    0, 584915, 0, 585622, 543209, 29, 'QtyToSplitOff',
    TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'de.metas.order', 14, 'Y', 'Y', 'Y', 'N',
    'Qty to split off', 10, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100
)
;

-- AD_Process_Para_Trl
INSERT INTO AD_Process_Para_Trl (
    AD_Language, AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, IsActive, IsTranslated, Name, Updated, UpdatedBy
) SELECT
    l.AD_Language, t.AD_Process_Para_ID, t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.IsActive, 'N', t.Name, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543209
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;
