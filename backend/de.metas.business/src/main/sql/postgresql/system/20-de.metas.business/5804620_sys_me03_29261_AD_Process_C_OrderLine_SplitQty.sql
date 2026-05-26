-- me03 #29261: Order Line Split
-- AD_Process: C_OrderLine_SplitQty
-- IDs from ID server (http://idserver.metas.de):
-- AD_Process -> 585622

-- 2026-05-26T00:00:00.000Z
INSERT INTO AD_Process (
    AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, Classname, Created, CreatedBy, Description, EntityType,
    IsActive, IsReport, Name, ShowHelp, Type, Updated, UpdatedBy, Value
) VALUES (
    '7', 0, 0, 585622, 'de.metas.order.process.C_OrderLine_SplitQty', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100,
    'Splits this order line into two: original capped at delivered qty, new sibling line for the remainder. Project is cleared on the new line.',
    'de.metas.order',
    'Y', 'N', 'Split order line', 'Y', 'Java', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'C_OrderLine_SplitQty'
)
;

-- AD_Process_Trl
INSERT INTO AD_Process_Trl (
    AD_Language, AD_Process_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Description, IsActive, IsTranslated, Name, Updated, UpdatedBy
) VALUES
    ('de_DE', 585622, 0, 0, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Teilt diese Auftragsposition in zwei: Die ursprüngliche Position wird auf die gelieferte Menge reduziert, eine neue Position wird für die Restmenge erzeugt. Das Projekt wird in der neuen Position geleert.', 'Y', 'Y', 'Auftragsposition aufteilen', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100),
    ('de_CH', 585622, 0, 0, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Teilt diese Auftragsposition in zwei: Die ursprüngliche Position wird auf die gelieferte Menge reduziert, eine neue Position wird für die Restmenge erzeugt. Das Projekt wird in der neuen Position geleert.', 'Y', 'Y', 'Auftragsposition aufteilen', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100)
;
