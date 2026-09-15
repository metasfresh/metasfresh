-- 2026-09-07T14:22:00.000Z
-- Empty HU write-off — add reject reason E (empty, auto. inventory)
-- IDs from the central ID server (2026-09-07):
--   AD_Ref_List  544360 — the new reason E on AD_Reference 541422
--   AD_Val_Rule  540801 — hides reason E from every reason column except the manufacturing issue one

-- Reason E marks a handling unit whose remaining quantity is booked off stock automatically.
-- Base language is German per convention; en_US carries the English override.
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,541422,544360 /*From ID Server*/,TO_TIMESTAMP('2026-09-07 14:22:00','YYYY-MM-DD HH24:MI:SS'),100,'Grund für eine während der Materialzuteilung geleerte Handlingseinheit','D','Y','leer (autom. Inventur)',TO_TIMESTAMP('2026-09-07 14:22:00','YYYY-MM-DD HH24:MI:SS'),100,'E','Emptied')
;

-- Seed translation rows for every active system language (copies the German base text).
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Description,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544360 /*From ID Server*/
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- English override.
UPDATE AD_Ref_List_Trl SET Name='empty (auto. inventory)', Description='Reason for a handling unit emptied during a raw-materials issue', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 14:22:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544360 /*From ID Server*/
;

-- German is already the base text; flag the German rows as actively translated.
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 14:22:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Ref_List_ID=544360 /*From ID Server*/
;

-- Reason E is offered only on the mobile raw-materials issue step, where choosing it triggers the
-- automatic write-off. Every other context reduces the list in Java, per context
-- (QtyRejectedReasonCode.reasonsFor). This val rule states the same exclusion on the AD side so the
-- restriction is discoverable from the application dictionary alone.
--
-- It changes nothing today, because no editable dropdown reads it. Verified per gated column:
--   577825 DD_OrderLine_HU_Candidate.RejectReason  rendered (AD_UI_Element 595362) but its tab
--                                                  544874 is AD_Tab.IsReadOnly='Y'
--   577768 M_Picking_Job_Step.RejectReason         AD_Field 669061, no AD_UI_Element; tab 544863 read-only
--   578375 M_Picking_Job_Step_HUAlternative...     AD_Field 669095, no AD_UI_Element; tab 544865 read-only
--   578885 M_Inventory_Candidate.DisposeReason     AD_Field 673874 is itself IsReadOnly='Y', no AD_UI_Element
--   577709 M_Picking_Candidate.RejectReason        no AD_Field in any window
--   578550 DD_Order_MoveSchedule.RejectReason      no AD_Field in any window
-- Note the mechanism differs: for the three picking/distribution columns the field itself is
-- IsReadOnly='N' and only the TAB is read-only, so making one editable means flipping the tab flag
-- (or placing the field on an editable tab) — not the field flag. This rule is a forward guard.
--
-- WARNING: IF REASON E IS LATER EXTENDED TO ANOTHER CONTEXT, REMOVE THAT CONTEXT'S COLUMN FROM THIS RULE.
-- Updating QtyRejectedReasonCode.reasonsFor alone is NOT enough: this rule would keep filtering E
-- out at the AD layer for any field that has since been made editable. Picking
-- (M_Picking_Job_Step 577768, M_Picking_Job_Step_HUAlternative 578375) is the documented first
-- candidate for such an extension.
--
-- Deliberately NOT applied to PP_Order_IssueSchedule.RejectReason (578318): that column stores E.
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy)
VALUES (0,0,540801 /*From ID Server*/,'AD_Ref_List.Value <> ''E''',TO_TIMESTAMP('2026-09-07 14:22:04','YYYY-MM-DD HH24:MI:SS'),100,'Excludes the reason "leer (autom. Inventur)", which belongs to the mobile raw-materials issue step only.','D','Y','Reject reasons excluding Emptied (manufacturing-issue only)','S',TO_TIMESTAMP('2026-09-07 14:22:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Every consumer of AD_Reference 541422 except PP_Order_IssueSchedule.RejectReason (577709
-- M_Picking_Candidate, 577768 M_Picking_Job_Step, 577825 DD_OrderLine_HU_Candidate,
-- 578375 M_Picking_Job_Step_HUAlternative, 578550 DD_Order_MoveSchedule — all .RejectReason — plus
-- 578885 M_Inventory_Candidate.DisposeReason).
UPDATE AD_Column SET AD_Val_Rule_ID=540801 /*From ID Server*/, Updated=TO_TIMESTAMP('2026-09-07 14:22:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID IN (577709, 577768, 577825, 578375, 578550, 578885)
;
