-- AD_Message DropShipWarehouse_NotFlagged (Error)
--
-- Raised by the AD_OrgInfo BEFORE_NEW/BEFORE_CHANGE model interceptor
-- AD_OrgInfo_DropShipWarehouse when the user tries to set DropShip_Warehouse_ID to a warehouse
-- that does not have IsDropShipWarehouse='Y'.
-- Created: 2026-05-14 22:02

INSERT INTO AD_Message
    (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Value, MsgType, MsgText, ErrorCode)
VALUES
    (545687 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-05-14 22:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-05-14 22:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'DropShipWarehouse_NotFlagged', 'E',
     'The selected warehouse must be marked as ''Dropship warehouse''.',
     'DROPSHIP_WAREHOUSE_NOT_FLAGGED')
;

INSERT INTO AD_Message_Trl
    (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES
    (545687 /*From ID Server*/, 'de_DE', 0, 0, 'Y',
     TO_TIMESTAMP('2026-05-14 22:02:01','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-05-14 22:02:01','YYYY-MM-DD HH24:MI:SS'), 100,
     'Das gewählte Lager muss als ''Dropship-Lager'' markiert sein.')
;

INSERT INTO AD_Message_Trl
    (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES
    (545687 /*From ID Server*/, 'de_CH', 0, 0, 'Y',
     TO_TIMESTAMP('2026-05-14 22:02:02','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-05-14 22:02:02','YYYY-MM-DD HH24:MI:SS'), 100,
     'Das gewählte Lager muss als ''Dropship-Lager'' markiert sein.')
;

INSERT INTO AD_Message_Trl
    (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy, MsgText)
VALUES
    (545687 /*From ID Server*/, 'en_US', 0, 0, 'Y',
     TO_TIMESTAMP('2026-05-14 22:02:03','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-05-14 22:02:03','YYYY-MM-DD HH24:MI:SS'), 100,
     'The selected warehouse must be marked as ''Dropship warehouse''.')
;
