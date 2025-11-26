-- Run mode: SWING_CLIENT

-- Column: MobileUI_UserProfile_Picking.BestBeforeDate
-- 2025-11-19T14:41:07.533Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591593,577375,0,20,542373,'XX','BestBeforeDate',TO_TIMESTAMP('2025-11-19 14:41:07.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Mindesthaltbarkeitsdatum',0,0,TO_TIMESTAMP('2025-11-19 14:41:07.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-19T14:41:07.561Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591593 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-19T14:41:07.731Z
/* DDL */  select update_Column_Translation_From_AD_Element(577375)
;

-- 2025-11-19T14:41:09.221Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN BestBeforeDate CHAR(1) DEFAULT ''Y'' CHECK (BestBeforeDate IN (''Y'',''N'')) NOT NULL')
;

-- Column: MobileUI_UserProfile_Picking.LotNumber
-- 2025-11-19T14:41:37.679Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591594,576652,0,20,542373,'XX','LotNumber',TO_TIMESTAMP('2025-11-19 14:41:37.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','de.metas.vertical.pharma.securpharm',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Chargennummer',0,0,TO_TIMESTAMP('2025-11-19 14:41:37.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-19T14:41:37.691Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591594 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-19T14:41:37.787Z
/* DDL */  select update_Column_Translation_From_AD_Element(576652)
;

-- 2025-11-19T14:41:38.413Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN LotNumber CHAR(1) DEFAULT ''Y'' CHECK (LotNumber IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Mindesthaltbarkeitsdatum
-- Column: MobileUI_UserProfile_Picking.BestBeforeDate
-- 2025-11-19T14:41:56.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591593,756213,0,547258,TO_TIMESTAMP('2025-11-19 14:41:56.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Mindesthaltbarkeitsdatum',TO_TIMESTAMP('2025-11-19 14:41:56.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T14:41:56.387Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756213 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-19T14:41:56.390Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577375)
;

-- 2025-11-19T14:41:56.416Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756213
;

-- 2025-11-19T14:41:56.423Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756213)
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Chargennummer
-- Column: MobileUI_UserProfile_Picking.LotNumber
-- 2025-11-19T14:41:56.556Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591594,756214,0,547258,TO_TIMESTAMP('2025-11-19 14:41:56.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Chargennummer',TO_TIMESTAMP('2025-11-19 14:41:56.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T14:41:56.559Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756214 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-19T14:41:56.563Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576652)
;

-- 2025-11-19T14:41:56.570Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756214
;

-- 2025-11-19T14:41:56.571Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756214)
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20
-- UI Element Group: pick attributes
-- 2025-11-19T14:43:00.218Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547137,553776,TO_TIMESTAMP('2025-11-19 14:42:59.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','pick attributes',25,TO_TIMESTAMP('2025-11-19 14:42:59.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> pick attributes.Mindesthaltbarkeitsdatum
-- Column: MobileUI_UserProfile_Picking.BestBeforeDate
-- 2025-11-19T14:43:11.369Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756213,0,547258,553776,638759,'F',TO_TIMESTAMP('2025-11-19 14:43:11.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Mindesthaltbarkeitsdatum',10,0,0,TO_TIMESTAMP('2025-11-19 14:43:11.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> pick attributes.Chargennummer
-- Column: MobileUI_UserProfile_Picking.LotNumber
-- 2025-11-19T14:43:18.728Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756214,0,547258,553776,638760,'F',TO_TIMESTAMP('2025-11-19 14:43:18.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Chargennummer',20,0,0,TO_TIMESTAMP('2025-11-19 14:43:18.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: MobileUI_UserProfile_Picking.LotNumber
-- 2025-11-19T14:48:50.956Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-19 14:48:50.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591594
;

