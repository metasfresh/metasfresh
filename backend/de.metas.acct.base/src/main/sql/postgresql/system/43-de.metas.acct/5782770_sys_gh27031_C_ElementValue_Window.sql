-- Run mode: SWING_CLIENT

-- Field: Konten(540761,D) -> Kontenart(542127,D) -> Kostenklassifizierung
-- Column: C_ElementValue.C_CostClassification_ID
-- 2025-12-22T16:25:40.874Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591816,760966,0,542127,TO_TIMESTAMP('2025-12-22 16:25:40.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die wirtschaftliche Art eines Aufwands oder Ertrags (Kostenart) als eigenständige Controlling-Dimension für Finanzbuchhaltung und Reporting.',10,'D','Y','N','N','N','N','N','N','N','Kostenklassifizierung',TO_TIMESTAMP('2025-12-22 16:25:40.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-22T16:25:40.877Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-22T16:25:41.117Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584379)
;

-- 2025-12-22T16:25:41.146Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760966
;

-- 2025-12-22T16:25:41.151Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760966)
;

-- Field: Konten(540761,D) -> Kontenart(542127,D) -> Kostenklassifizierung
-- Column: C_ElementValue.C_CostClassification_ID
-- 2025-12-22T16:27:26.494Z
UPDATE AD_Field SET DisplayLogic='@AccountType@=''R'' | @AccountType@=''E''',Updated=TO_TIMESTAMP('2025-12-22 16:27:26.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=760966
;

-- UI Element: Konten(540761,D) -> Kontenart(542127,D) -> main -> 10 -> desc.Kostenklassifizierung
-- Column: C_ElementValue.C_CostClassification_ID
-- 2025-12-22T16:28:13.195Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,760966,0,542127,641305,543187,'F',TO_TIMESTAMP('2025-12-22 16:28:12.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die wirtschaftliche Art eines Aufwands oder Ertrags (Kostenart) als eigenständige Controlling-Dimension für Finanzbuchhaltung und Reporting.','Y','N','N','Y','N','N','N',0,'Kostenklassifizierung',70,0,0,TO_TIMESTAMP('2025-12-22 16:28:12.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_ElementValue.C_CostClassification_ID
-- 2025-12-22T16:29:01.708Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-12-22 16:29:01.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591816
;

-- 2025-12-22T16:29:02.900Z
INSERT INTO t_alter_column values('c_elementvalue','C_CostClassification_ID','NUMERIC(10)',null,null)
;

