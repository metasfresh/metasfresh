-- Run mode: SWING_CLIENT

-- Field: HU-Labels Konfiguration(541647,de.metas.handlingunits) -> HU-Labels Konfiguration(546701,de.metas.handlingunits) -> Sofort drucken
-- Column: M_HU_Label_Config.IsAutoPrint
-- 2025-12-11T14:42:02.730Z
UPDATE AD_Field SET DisplayLogic='@HU_SourceDocType/XX@=MR | @HU_SourceDocType/XX@=PI | @HU_SourceDocType/XX@=MI',Updated=TO_TIMESTAMP('2025-12-11 14:42:02.729000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=708934
;

