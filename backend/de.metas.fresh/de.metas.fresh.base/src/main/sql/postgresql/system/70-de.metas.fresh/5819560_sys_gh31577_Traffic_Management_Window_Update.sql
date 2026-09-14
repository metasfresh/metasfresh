-- Run mode: SWING_CLIENT

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Land
-- Column: M_Picking_Job_Schedule_view.C_Country_ID
-- 2026-08-19T09:38:05.634Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593334,782378,0,548377,0,TO_TIMESTAMP('2026-08-19 09:38:04.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Land',0,'D',0,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Land',0,0,65,0,1,1,TO_TIMESTAMP('2026-08-19 09:38:04.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-19T09:38:05.719Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782378 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-08-19T09:38:05.808Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(192)
;

-- 2026-08-19T09:38:05.901Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782378
;

-- 2026-08-19T09:38:05.982Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(782378)
;

-- 2026-08-19T09:39:32.342Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585314,0,TO_TIMESTAMP('2026-08-19 09:39:31.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Lieferland','Lieferland',TO_TIMESTAMP('2026-08-19 09:39:31.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-19T09:39:32.438Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585314 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-08-19T09:40:26.922Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivery country', PrintName='Delivery country',Updated=TO_TIMESTAMP('2026-08-19 09:40:26.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585314 AND AD_Language='en_US'
;

-- 2026-08-19T09:40:27.013Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-08-19T09:40:47.436Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585314,'en_US')
;

-- Field: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> Lieferland
-- Column: M_Picking_Job_Schedule_view.C_Country_ID
-- 2026-08-19T09:41:01.215Z
UPDATE AD_Field SET AD_Name_ID=585314, Description=NULL, Help=NULL, Name='Lieferland',Updated=TO_TIMESTAMP('2026-08-19 09:41:01.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=782378
;

-- 2026-08-19T09:41:01.300Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Lieferland' WHERE AD_Field_ID=782378 AND AD_Language='de_DE'
;

-- 2026-08-19T09:41:01.387Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585314)
;

-- 2026-08-19T09:41:01.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782378
;

-- 2026-08-19T09:41:01.553Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(782378)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Lieferland
-- Column: M_Picking_Job_Schedule_view.C_Country_ID
-- 2026-08-19T09:44:03.534Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782378,0,548377,553423,653204,'F',TO_TIMESTAMP('2026-08-19 09:44:02.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Lieferland',55,0,0,TO_TIMESTAMP('2026-08-19 09:44:02.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Lieferweg
-- Column: M_Picking_Job_Schedule_view.M_Shipper_ID
-- 2026-08-19T09:46:09.958Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-08-19 09:46:09.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636485
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Maßeinheit
-- Column: M_Picking_Job_Schedule_view.C_UOM_ID
-- 2026-08-19T09:46:10.458Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-08-19 09:46:10.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636477
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Übergabe-Partner
-- Column: M_Picking_Job_Schedule_view.HandOver_Partner_ID
-- 2026-08-19T09:46:10.953Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-08-19 09:46:10.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636483
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Lieferland
-- Column: M_Picking_Job_Schedule_view.C_Country_ID
-- 2026-08-19T09:46:11.462Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-08-19 09:46:11.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=653204
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Belegart
-- Column: M_Picking_Job_Schedule_view.C_DocType_ID
-- 2026-08-19T09:46:11.969Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-08-19 09:46:11.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652340
;

-- UI Element: Traffic Management(541929,de.metas.handlingunits) -> Kommissionierplan(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Lieferland
-- Column: M_Picking_Job_Schedule_view.C_Country_ID
-- 2026-08-19T09:49:54.220Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2026-08-19 09:49:54.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=653204
;

