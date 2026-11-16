-- Column: M_HU_Label_Config.AutoPrintCopies
-- 2026-06-26T13:45:01.903Z
UPDATE AD_Column SET DefaultValue='1',Updated=TO_TIMESTAMP('2026-06-26 13:45:01.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=585308
;

-- 2026-06-26T13:45:12.321Z
INSERT INTO t_alter_column values('m_hu_label_config','AutoPrintCopies','NUMERIC(10)',null,'1')
;

-- 2026-06-26T13:45:12.376Z
UPDATE M_HU_Label_Config SET AutoPrintCopies=1 WHERE AutoPrintCopies IS NULL
;

-- UI Element: HU-Labels Konfiguration(541647,de.metas.handlingunits) -> HU-Labels Konfiguration(546701,de.metas.handlingunits) -> main -> 20 -> flags.Sofort drucken
-- Column: M_HU_Label_Config.IsAutoPrint
-- 2026-06-26T13:46:15.035Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708934,0,546701,550117,652376,'F',TO_TIMESTAMP('2026-06-26 13:46:14.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Sofort drucken',20,0,0,TO_TIMESTAMP('2026-06-26 13:46:14.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: HU-Labels Konfiguration(541647,de.metas.handlingunits) -> HU-Labels Konfiguration(546701,de.metas.handlingunits) -> main -> 20 -> flags.Exemplare zum Sofortdruck
-- Column: M_HU_Label_Config.AutoPrintCopies
-- 2026-06-26T13:46:32.516Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708935,0,546701,550117,652377,'F',TO_TIMESTAMP('2026-06-26 13:46:32.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Exemplare zum Sofortdruck',30,0,0,TO_TIMESTAMP('2026-06-26 13:46:32.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

