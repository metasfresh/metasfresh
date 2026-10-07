-- Cost-monitor window 542175, tab 549352 — layout refinement requested by the controller.
--
-- Nr. (DocumentNo) loses its grid column but deliberately STAYS a filter — a window-scoped exception
-- to "a filter column is also shown in the grid"; the order number stays reachable via the
-- Produktionsauftrag zoom column (654728). Grid removal is applied on both grid layers
-- (AD_UI_Element = WebUI, AD_Field = legacy Swing), as in 5814720/5814990 for this tab.
-- Surviving siblings are not renumbered: SeqNo/SeqNoGrid are relative, so the vacated numbers
-- leave no visible gap.
--
-- IDs allocated from idserver.metas.de: AD_UI_ElementGroup 555768

-- Nr. + Belegart out of the grid
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,
    Updated=TO_TIMESTAMP('2026-09-10 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID IN (652681 /* DocumentNo */, 652682 /* C_DocType_ID */)
;

UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,
    Updated=TO_TIMESTAMP('2026-09-10 09:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID IN (781750 /* DocumentNo */, 781751 /* C_DocType_ID */)
;

-- New group in the right column (549605), between the flags group (SeqNo 10) and Sektion/Mandant (SeqNo 20)
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (555768 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-10 09:00:10','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-10 09:00:10','YYYY-MM-DD HH24:MI:SS'), 100, 549605, 15, NULL, 'default')
;

UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555768, SeqNo=10,
    Updated=TO_TIMESTAMP('2026-09-10 09:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID=652682 /* C_DocType_ID */
;

UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555768, SeqNo=20,
    Updated=TO_TIMESTAMP('2026-09-10 09:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID=652688 /* DatePromised */
;

UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555768, SeqNo=30,
    Updated=TO_TIMESTAMP('2026-09-10 09:00:25','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID=652689 /* DateFinishSchedule */
;

-- Lager into the left column's primary group (555514), after Kostendifferenz (SeqNo 40)
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555514, SeqNo=50,
    Updated=TO_TIMESTAMP('2026-09-10 09:00:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID=652690 /* M_Warehouse_ID */
;
