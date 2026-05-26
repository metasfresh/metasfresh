-- DD_Order picking reconcile — drift watchdog rebuild trigger process
-- me03 #29966

INSERT INTO AD_Process (
	AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID,
	AllowProcessReRun, Classname, CopyFromProcess,
	Created, CreatedBy, EntityType, IsActive,
	IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint,
	IsOneInstanceOnly, IsReport, IsServerProcess, IsUseBPartnerLanguage,
	LockWaitTimeout, Name, RefreshAllAfterExecution, ShowHelp,
	Type, Updated, UpdatedBy, Value
) VALUES (
	'3', 0, 0, 585623 /*From ID Server*/,
	'Y', 'de.metas.handlingunits.picking.dd_order.reconcile.process.DD_Order_Picking_Rebuild', 'N',
	TO_TIMESTAMP('2026-05-27 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
	'N', 'N', 'N', 'N', 'N', 'Y', 'Y', 0,
	'Rebuild Picking DD_Orders',
	'N', 'Y', 'Java',
	TO_TIMESTAMP('2026-05-27 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'DD_Order_Picking_Rebuild'
);

INSERT INTO AD_Process_Trl (
	AD_Language, AD_Process_ID, Description, Help, Name,
	IsTranslated, AD_Client_ID, AD_Org_ID,
	Created, Createdby, Updated, UpdatedBy
) SELECT
	l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name,
	'N', t.AD_Client_ID, t.AD_Org_ID,
	t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
	AND t.AD_Process_ID=585623
	AND NOT EXISTS (
		SELECT 1 FROM AD_Process_Trl tt
		WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID
	);
