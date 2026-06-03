-- me03 #29261: Order Line Split
-- AD_Element: QtyToSplitOff
-- IDs from ID server (http://idserver.metas.de):
-- AD_Element -> 584915

-- 2026-05-26T00:00:00.000Z
INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, Name, PrintName, EntityType
) VALUES (
    584915, 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100,
    'QtyToSplitOff', 'Qty to split off', 'Qty to split off', 'de.metas.order'
)
;

-- AD_Element_Trl
INSERT INTO AD_Element_Trl (
    AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Name, PrintName, IsTranslated
) VALUES
    (584915, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Aufzuteilende Menge', 'Aufzuteilende Menge', 'Y'),
    (584915, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Aufzuteilende Menge', 'Aufzuteilende Menge', 'Y')
;
