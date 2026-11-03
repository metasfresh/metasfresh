-- Add a generic, declarative "Related Documents" (zoom-across) jump from the Auftrags-Board
-- Uebersicht row (M_Picking_OrderBoard_Overview_v, AD_Table 542626) to Traffic Management
-- (M_Picking_Job_Schedule_view, AD_Table 542514, AD_Window 541929), analogous to the existing
-- QtyDemand_QtySupply_V -> M_Forecast_ProductQty_V jump (AD_Process 585515).
--
-- The relation matches the board row's own grouping key back onto the schedule view via an EXISTS
-- back-join to the board view itself (M_Product_ID, C_UOM_ID, AD_Client_ID, AD_Org_ID, the
-- delivery-date truncated to a date, and the delivery country via C_BPartner_Location ->
-- C_Location). The EXISTS only constrains that a board aggregate row with this id and this grouping
-- tuple exists -- it says nothing about the *target* schedule row being matched. The board view's
-- own `isassigned='Y' OR qtyonhand>0` restriction governs which schedule rows form that aggregate,
-- not which target rows share its tuple, so the same test is repeated as a trailing AND on the
-- target row below; without it, a target row sharing the tuple but failing that test (e.g. an
-- unassigned, no-stock schedule alongside an assigned one for the same product/UOM/date/country)
-- would still be returned, even though it is invisible on the board.
--
-- A plain @DeliveryDate@ context variable was considered and rejected: POZoomSource (the context a
-- relation-type where-clause evaluates against) exports only Integer and String source-column
-- values, so a Timestamp/Date column is silently dropped and the where-clause throws
-- ExpressionEvaluationException at zoom time (OnVariableNotFound.Fail). The board row's own integer
-- key (@M_Picking_OrderBoard_Overview_v_ID@) is the only context value this needs.
--
-- AD_Process.IsUseAutoFilters='N' (added by an earlier script in this branch) makes the jump show
-- exactly the resolved rows without re-applying the target window's own "not assigned" default
-- filter -- otherwise already-assigned schedules (board status "In Kommissionierung" / "Packen")
-- would land on an apparently empty grid.
--
-- IDs allocated from idserver.metas.de on 2026-09-03:
--   AD_Reference     542135 (source reference, board overview row)
--   AD_Reference     542136 (target reference, schedule view)
--   AD_RelationType  540506 (M_Picking_OrderBoard_Overview_v -> Traffic Management)
--   AD_Process       585657 (the jump process itself)
--   AD_Table_Process 541671 (wires the process onto AD_Table 542626 as a quick action)

-- Source reference (ValidationType 'T' = Table): the Auftrags-Board Uebersicht row.
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542135 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 15:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','Traffic Management Source for M_Picking_OrderBoard_Overview_v',TO_TIMESTAMP('2026-09-03 15:00:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,IsActive,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N','Y',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542135
  AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Source ref-table: M_Picking_OrderBoard_Overview_v (AD_Table 542626), key
-- M_Picking_OrderBoard_Overview_v_ID (AD_Column 592940).
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy)
VALUES (0,592940,0,542135,542626,TO_TIMESTAMP('2026-09-03 15:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N',TO_TIMESTAMP('2026-09-03 15:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- The board overview row's key column must be usable as a relation-type SOURCE. A relation-type
-- source only matches when the source zoom column carries IsGenericZoomOrigin='Y'
-- (SpecificRelationTypeRelatedDocumentsProvider.matchesAsSource(), which otherwise short-circuits
-- to "no related documents found" for any single-key-record zoom source). The two other existing
-- RelationTypeInOverlay sources already carry it -- C_Order_ID (AD_Column 2161, used by the
-- predecessor C_Order -> Traffic Management relation) and QtyDemand_QtySupply_V_ID (AD_Column
-- 591429, the Material Cockpit v2 -> Forecast relation) -- so this is not a new column, just this
-- table's key column catching up to the same precondition. AD_Column 592940 and its table
-- (M_Picking_OrderBoard_Overview_v) are core, EntityType='D' -- this script's own rows use
-- EntityType='de.metas.handlingunits', but this one UPDATEs a pre-existing core dictionary flag on
-- an existing core column, so it is deliberately left at its own 'D' EntityType rather than
-- reassigned.
UPDATE AD_Column SET IsGenericZoomOrigin='Y', Updated=TO_TIMESTAMP('2026-09-03 15:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592940
;

-- Target reference: the Picking Job Schedule view.
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542136 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 15:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','Traffic Management Target for M_Picking_OrderBoard_Overview_v',TO_TIMESTAMP('2026-09-03 15:00:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,IsActive,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N','Y',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542136
  AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Target ref-table: M_Picking_Job_Schedule_view (AD_Table 542514), key M_ShipmentSchedule_ID
-- (AD_Column 590664, non-null on every row -- the synthetic to-be-scheduled row carries
-- M_Picking_Job_Schedule_ID=0, so that column is unsuitable as the zoom key), opening AD_Window
-- 541929. WhereClause resolves via an EXISTS back-join to the board view itself, keyed on the
-- board row's own integer id (the only context value POZoomSource makes available).
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause)
VALUES (0,590664,0,542136,542514,541929,TO_TIMESTAMP('2026-09-03 15:00:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N',TO_TIMESTAMP('2026-09-03 15:00:03','YYYY-MM-DD HH24:MI:SS'),100,
'EXISTS (SELECT 1 FROM M_Picking_OrderBoard_Overview_v b JOIN C_BPartner_Location bl ON bl.C_BPartner_Location_ID = M_Picking_Job_Schedule_view.C_BPartner_Location_ID JOIN C_Location l ON l.C_Location_ID = bl.C_Location_ID WHERE b.M_Picking_OrderBoard_Overview_v_ID = @M_Picking_OrderBoard_Overview_v_ID/-1@ AND b.M_Product_ID = M_Picking_Job_Schedule_view.M_Product_ID AND b.C_UOM_ID = M_Picking_Job_Schedule_view.C_UOM_ID AND b.AD_Client_ID = M_Picking_Job_Schedule_view.AD_Client_ID AND b.AD_Org_ID = M_Picking_Job_Schedule_view.AD_Org_ID AND b.DeliveryDate = CAST(M_Picking_Job_Schedule_view.DeliveryDate AS date) AND b.C_Country_ID = l.C_Country_ID) AND (M_Picking_Job_Schedule_view.IsAssigned = ''Y'' OR M_Picking_Job_Schedule_view.QtyOnHand > 0)')
;

-- The directed relation itself: board overview row (542135) -> Traffic Management (542136).
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,Updated,UpdatedBy,EntityType,IsActive,IsTableRecordIDTarget,Name,InternalName,AD_Reference_Source_ID,AD_Reference_Target_ID)
VALUES (0,0,540506 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 15:00:04','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-03 15:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','Auftrags-Board -> Traffic Management','M_Picking_OrderBoard_Overview_v_to_TrafficManagement',542135,542136)
;

-- The jump process. Type and Classname are both set explicitly because the interceptor that
-- derives one from the other does not run for migration SQL. IsUseAutoFilters='N' is the whole
-- point of this jump: show exactly the resolved schedules, not the target window's own default
-- "not assigned" filter. Value is pinned to a fixed, human-readable string (rather than left to
-- default to the numeric AD_Process_ID) because the WebUI quick-action data-testid is derived from
-- it (ADProcessDescriptorsFactory -> InternalName.ofString(adProcess.getValue())).
-- AD_Process has no AD_Element_ID -- Name/Description/Help are self-owned; the base row and its
-- AD_Process_Trl rows are set directly and in sync below. This process gets no AD_Menu entry.
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_RelationType_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseAutoFilters,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585657 /*From ID Server*/,540506,'Y','de.metas.ui.web.view.process.RelationTypeInOverlayProcess','N',TO_TIMESTAMP('2026-09-03 15:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','N','N','N','N','Y',0,'Sprung zu Traffic Management','json','N','N','xls','RelationTypeInOverlay',TO_TIMESTAMP('2026-09-03 15:00:05','YYYY-MM-DD HH24:MI:SS'),100,'M_Picking_OrderBoard_Overview_v_to_TrafficManagement')
;

INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585657
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- de_DE / de_CH already carry the correct text via the generic copy above; only en_US differs.
UPDATE AD_Process_Trl SET Name='Go to Traffic Management',Updated=TO_TIMESTAMP('2026-09-03 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Process_ID=585657
;

UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Wire the process onto the board's Uebersicht table as a quick action.
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default)
VALUES (0,0,585657,542626,541671 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 15:00:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2026-09-03 15:00:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','Y')
;
