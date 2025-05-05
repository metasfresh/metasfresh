-- Column: ModCntr_Settings.StorageCostStartDate
-- 2024-04-23T12:41:22.371Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-23 14:41:22.371','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588191
;

-- 2024-04-23T12:47:03.990Z
INSERT INTO t_alter_column values('modcntr_settings','StorageCostStartDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2024-04-23T12:47:03.991Z
INSERT INTO t_alter_column values('modcntr_settings','StorageCostStartDate',null,'NOT NULL',null)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Lagerkosten Startdatum
-- Column: ModCntr_Settings.StorageCostStartDate
-- 2024-04-23T13:07:59.933Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588191,728053,0,547013,10,TO_TIMESTAMP('2024-04-23 15:07:59.791','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Lagerkosten Startdatum',0,40,0,1,1,TO_TIMESTAMP('2024-04-23 15:07:59.791','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-23T13:07:59.937Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-23T13:07:59.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583082)
;

-- 2024-04-23T13:07:59.947Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728053
;

-- 2024-04-23T13:07:59.949Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728053)
;

-- UI Column: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20
-- UI Element Group: additional
-- 2024-04-23T13:08:35.038Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546866,551809,TO_TIMESTAMP('2024-04-23 15:08:34.906','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','additional',7,TO_TIMESTAMP('2024-04-23 15:08:34.906','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Lagerkosten Startdatum
-- Column: ModCntr_Settings.StorageCostStartDate
-- 2024-04-23T13:09:00.168Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728053,0,547013,551809,624525,'F',TO_TIMESTAMP('2024-04-23 15:09:00.036','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Lagerkosten Startdatum',10,0,0,TO_TIMESTAMP('2024-04-23 15:09:00.036','YYYY-MM-DD HH24:MI:SS.US'),100)
;

