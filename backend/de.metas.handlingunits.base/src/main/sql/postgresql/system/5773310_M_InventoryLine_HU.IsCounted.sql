-- Run mode: SWING_CLIENT

-- Column: M_InventoryLine_HU.IsCounted
-- 2025-10-14T11:18:16.405Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591304,1835,0,20,541345,'XX','IsCounted',TO_TIMESTAMP('2025-10-14 11:18:15.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Sagt aus, ob das entsprechende Item as inventarisiert/gezählt gilt.','de.metas.handlingunits',0,1,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gezählt',0,0,TO_TIMESTAMP('2025-10-14 11:18:15.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-14T11:18:16.446Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591304 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-14T11:18:16.700Z
/* DDL */  select update_Column_Translation_From_AD_Element(1835)
;

-- 2025-10-14T11:18:18.322Z
/* DDL */ SELECT public.db_alter_table('M_InventoryLine_HU','ALTER TABLE public.M_InventoryLine_HU ADD COLUMN IsCounted CHAR(1) DEFAULT ''N'' CHECK (IsCounted IN (''Y'',''N'')) NOT NULL')
;

-- Field: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> Gezählt
-- Column: M_InventoryLine_HU.IsCounted
-- 2025-10-14T11:18:51.068Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591304,754968,0,548441,TO_TIMESTAMP('2025-10-14 11:18:50.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sagt aus, ob das entsprechende Item as inventarisiert/gezählt gilt.',1,'de.metas.handlingunits','','Y','N','N','N','N','N','N','N','Gezählt',TO_TIMESTAMP('2025-10-14 11:18:50.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-14T11:18:51.081Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-14T11:18:51.089Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1835)
;

-- 2025-10-14T11:18:51.115Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754968
;

-- 2025-10-14T11:18:51.121Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754968)
;

-- UI Column: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 20
-- UI Element Group: org&client
-- 2025-10-14T11:19:18.434Z
UPDATE AD_UI_ElementGroup SET SeqNo=999,Updated=TO_TIMESTAMP('2025-10-14 11:19:18.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553555
;

-- UI Column: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 20
-- UI Element Group: flags
-- 2025-10-14T11:19:25.686Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548481,553639,TO_TIMESTAMP('2025-10-14 11:19:25.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-10-14 11:19:25.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventurpositions-HUs(541951,de.metas.handlingunits) -> Inventurpositions-HUs(548441,de.metas.handlingunits) -> main -> 20 -> flags.Gezählt
-- Column: M_InventoryLine_HU.IsCounted
-- 2025-10-14T11:19:39.337Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754968,0,548441,553639,637867,'F',TO_TIMESTAMP('2025-10-14 11:19:39.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sagt aus, ob das entsprechende Item as inventarisiert/gezählt gilt.','','Y','N','Y','N','N','Gezählt',10,0,0,TO_TIMESTAMP('2025-10-14 11:19:39.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_InventoryLine_HU.IsCounted
-- 2025-10-14T11:19:58.592Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-14 11:19:58.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591304
;

