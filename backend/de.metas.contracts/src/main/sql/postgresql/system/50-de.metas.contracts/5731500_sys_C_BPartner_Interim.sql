
-- Tab: Geschäftspartner(123,D) -> Vorfinanzierungsvertrag
-- Table: C_BPartner_InterimContract
-- 2024-08-20T15:45:48.480Z
UPDATE AD_Tab SET IsActive='Y',Updated=TO_TIMESTAMP('2024-08-20 15:45:48.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547145
;

-- UI Element: Geschäftspartner(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default -> 10 -> default.Erntekalender
-- Column: C_BPartner_InterimContract.C_Harvesting_Calendar_ID
-- 2024-08-20T15:46:35.169Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-08-20 15:46:35.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=619685
;

-- UI Element: Geschäftspartner(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default -> 10 -> default.Erntejahr
-- Column: C_BPartner_InterimContract.Harvesting_Year_ID
-- 2024-08-20T15:46:35.176Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-08-20 15:46:35.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=619686
;

-- UI Element: Geschäftspartner(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default -> 10 -> default.Vorfinanzierungsvertrag
-- Column: C_BPartner_InterimContract.IsInterimContract
-- 2024-08-20T15:46:35.182Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-08-20 15:46:35.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=619688
;

-- UI Element: Geschäftspartner(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default -> 10 -> default.Organisation
-- Column: C_BPartner_InterimContract.AD_Org_ID
-- 2024-08-20T15:46:35.189Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-08-20 15:46:35.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=619689
;



-- Process: C_BPartner_InterimContract(de.metas.contracts.modular.interim.bpartner.process.C_BPartner_InterimContract_Upsert)
-- Table: C_BPartner
-- EntityType: de.metas.contracts
-- 2024-08-20T15:57:10.113Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585301,291,541515,TO_TIMESTAMP('2024-08-20 15:57:10.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-08-20 15:57:10.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;



