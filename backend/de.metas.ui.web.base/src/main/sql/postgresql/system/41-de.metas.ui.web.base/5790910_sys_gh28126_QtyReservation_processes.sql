-- gh#28126: Register Make/Delete Qty Reservation processes on Material Cockpit V2

-- ==========================================================================
-- 1. AD_Element for Make Reservation process
-- ==========================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584596, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        'MD_CockpitV2_MakeQtyReservation', 'Reservierung anlegen', 'Reservierung anlegen', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584596, 'Reservierung anlegen', 'Reservierung anlegen', 'N', 0, 0,
       TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Element_ID = 584596);

UPDATE AD_Element_Trl
SET Name       = 'Make Reservation',
    PrintName  = 'Make Reservation',
    Updated    = TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy  = 0
WHERE AD_Element_ID = 584596
  AND AD_Language = 'en_US';

-- propagate element translations
SELECT update_ad_element_on_ad_element_trl_update(584596, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584596, 'en_US');

-- ==========================================================================
-- 2. AD_Element for Delete Reservation process
-- ==========================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (584597, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        'MD_CockpitV2_DeleteQtyReservation', 'Reservierung löschen', 'Reservierung löschen', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584597, 'Reservierung löschen', 'Reservierung löschen', 'N', 0, 0,
       TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Element_ID = 584597);

UPDATE AD_Element_Trl
SET Name       = 'Delete Reservation',
    PrintName  = 'Delete Reservation',
    Updated    = TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy  = 0
WHERE AD_Element_ID = 584597
  AND AD_Language = 'en_US';

SELECT update_ad_element_on_ad_element_trl_update(584597, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584597, 'en_US');

-- ==========================================================================
-- 3. AD_Process: Make Qty Reservation
-- ==========================================================================
INSERT INTO AD_Process (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, Name, AccessLevel, EntityType, Type, Classname,
                        IsReport, IsDirectPrint, CopyFromProcess, AllowProcessReRun,
                        IsApplySecuritySettings, IsBetaFunctionality, IsFormatExcelFile, IsLogWarning,
                        IsNotifyUserAfterExecution, IsOneInstanceOnly, IsTranslateExcelHeaders,
                        IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout,
                        PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat)
VALUES (585588, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        'MD_CockpitV2_MakeQtyReservation',
        'Reservierung anlegen',
        '3', 'D', 'Java',
        'de.metas.ui.web.material.cockpit.v2.reservation.MD_CockpitV2_MakeQtyReservation',
        'N', 'N', 'N', 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'Y',
        'N', 'Y', 0,
        'json', 'N', 'Y', 'xls');

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 585588, NULL, NULL, 'Reservierung anlegen', 'N', 0, 0,
       TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Process_ID = 585588);

UPDATE AD_Process_Trl
SET Name    = 'Make Reservation',
    Updated = TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 0
WHERE AD_Process_ID = 585588
  AND AD_Language = 'en_US';

UPDATE AD_Process base
SET Name = trl.Name, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID = base.AD_Process_ID
  AND trl.AD_Language = 'en_US'
  AND trl.AD_Language = getBaseLanguage();

-- ==========================================================================
-- 4. AD_Process_Para: QtyTU parameter for Make Reservation
-- ==========================================================================
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, AD_Reference_ID,
                             ColumnName, Name, SeqNo, FieldLength, IsMandatory, IsRange, EntityType)
VALUES (543136, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        585588,   -- AD_Process_ID (Make Reservation)
        542490,   -- AD_Element_ID (QtyTU)
        29,       -- AD_Reference_ID = Amount (BigDecimal)
        'QtyTU', 'TU Anzahl', 10, 0, 'Y', 'N', 'D');

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 543136, NULL, NULL, 'TU Anzahl', 'N', 0, 0,
       TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Process_Para_ID = 543136);

UPDATE AD_Process_Para_Trl
SET Name    = 'TU Quantity',
    Updated = TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 543136
  AND AD_Language = 'en_US';

-- ==========================================================================
-- 5. AD_Process: Delete Qty Reservation
-- ==========================================================================
INSERT INTO AD_Process (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, Name, AccessLevel, EntityType, Type, Classname,
                        IsReport, IsDirectPrint, CopyFromProcess, AllowProcessReRun,
                        IsApplySecuritySettings, IsBetaFunctionality, IsFormatExcelFile, IsLogWarning,
                        IsNotifyUserAfterExecution, IsOneInstanceOnly, IsTranslateExcelHeaders,
                        IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout,
                        PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat)
VALUES (585589, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        'MD_CockpitV2_DeleteQtyReservation',
        'Reservierung löschen',
        '3', 'D', 'Java',
        'de.metas.ui.web.material.cockpit.v2.reservation.MD_CockpitV2_DeleteQtyReservation',
        'N', 'N', 'N', 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'Y',
        'N', 'Y', 0,
        'json', 'N', 'N', 'xls');

INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 585589, NULL, NULL, 'Reservierung löschen', 'N', 0, 0,
       TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Process_ID = 585589);

UPDATE AD_Process_Trl
SET Name    = 'Delete Reservation',
    Updated = TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 0
WHERE AD_Process_ID = 585589
  AND AD_Language = 'en_US';

UPDATE AD_Process base
SET Name = trl.Name, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID = base.AD_Process_ID
  AND trl.AD_Language = 'en_US'
  AND trl.AD_Language = getBaseLanguage();

-- ==========================================================================
-- 6. AD_Table_Process: Register Make Reservation on QtyDemand_QtySupply_V
-- ==========================================================================
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Process_ID, AD_Table_ID, EntityType,
                              WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction,
                              WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (541626, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        585588,    -- AD_Process_ID (Make Reservation)
        542218,    -- AD_Table_ID (QtyDemand_QtySupply_V)
        'D',
        'N', 'N', 'N',
        'Y', 'N');

-- ==========================================================================
-- 7. AD_Table_Process: Register Delete Reservation on QtyDemand_QtySupply_V
-- ==========================================================================
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Process_ID, AD_Table_ID, EntityType,
                              WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction,
                              WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (541627, 0, 0, 'Y', TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-01 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        585589,    -- AD_Process_ID (Delete Reservation)
        542218,    -- AD_Table_ID (QtyDemand_QtySupply_V)
        'D',
        'N', 'N', 'N',
        'Y', 'N');
