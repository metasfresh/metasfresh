-- Column: Mobile_Application_Trl.IsUseCustomization
-- 2025-08-21T19:29:22.116Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590627,576141,0,20,542445,'XX','IsUseCustomization',TO_TIMESTAMP('2025-08-21 19:29:21.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Individuelle Anpassungen verwenden',0,0,TO_TIMESTAMP('2025-08-21 19:29:21.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-21T19:29:22.118Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590627 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-21T19:29:22.195Z
/* DDL */  select update_Column_Translation_From_AD_Element(576141)
;

-- 2025-08-21T19:29:22.640Z
/* DDL */ SELECT public.db_alter_table('Mobile_Application_Trl','ALTER TABLE public.Mobile_Application_Trl ADD COLUMN IsUseCustomization CHAR(1) DEFAULT ''N'' CHECK (IsUseCustomization IN (''Y'',''N'')) NOT NULL')
;

-- Column: Mobile_Application_Trl.Name_Customized
-- 2025-08-21T19:29:32.782Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590628,576142,0,10,542445,'XX','Name_Customized',TO_TIMESTAMP('2025-08-21 19:29:32.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,40,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Name Anpassung',0,0,TO_TIMESTAMP('2025-08-21 19:29:32.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-21T19:29:32.784Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590628 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-21T19:29:32.866Z
/* DDL */  select update_Column_Translation_From_AD_Element(576142)
;

-- 2025-08-21T19:29:33.289Z
/* DDL */ SELECT public.db_alter_table('Mobile_Application_Trl','ALTER TABLE public.Mobile_Application_Trl ADD COLUMN Name_Customized VARCHAR(40)')
;

-- Column: Mobile_Application_Trl.Description_Customized
-- 2025-08-21T19:29:54.687Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590629,576143,0,36,542445,'XX','Description_Customized',TO_TIMESTAMP('2025-08-21 19:29:54.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung Anpassung',0,0,TO_TIMESTAMP('2025-08-21 19:29:54.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-21T19:29:54.689Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590629 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-21T19:29:54.756Z
/* DDL */  select update_Column_Translation_From_AD_Element(576143)
;

-- 2025-08-21T19:29:55.159Z
/* DDL */ SELECT public.db_alter_table('Mobile_Application_Trl','ALTER TABLE public.Mobile_Application_Trl ADD COLUMN Description_Customized TEXT')
;



-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Individuelle Anpassungen verwenden
-- Column: Mobile_Application_Trl.IsUseCustomization
-- 2025-08-21T22:12:10.693Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590627,752005,0,547619,TO_TIMESTAMP('2025-08-21 22:12:09.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Individuelle Anpassungen verwenden',TO_TIMESTAMP('2025-08-21 22:12:09.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-21T22:12:10.695Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-21T22:12:10.697Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576141)
;

-- 2025-08-21T22:12:10.707Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752005
;

-- 2025-08-21T22:12:10.708Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752005)
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Name Anpassung
-- Column: Mobile_Application_Trl.Name_Customized
-- 2025-08-21T22:12:10.886Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590628,752006,0,547619,TO_TIMESTAMP('2025-08-21 22:12:10.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','Name Anpassung',TO_TIMESTAMP('2025-08-21 22:12:10.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-21T22:12:10.887Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-21T22:12:10.889Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576142)
;

-- 2025-08-21T22:12:10.891Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752006
;

-- 2025-08-21T22:12:10.892Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752006)
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Beschreibung Anpassung
-- Column: Mobile_Application_Trl.Description_Customized
-- 2025-08-21T22:12:11.014Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590629,752007,0,547619,TO_TIMESTAMP('2025-08-21 22:12:10.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung Anpassung',TO_TIMESTAMP('2025-08-21 22:12:10.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-21T22:12:11.016Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-21T22:12:11.017Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576143)
;

-- 2025-08-21T22:12:11.019Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752007
;

-- 2025-08-21T22:12:11.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752007)
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Name Anpassung
-- Column: Mobile_Application_Trl.Name_Customized
-- 2025-08-21T22:14:00.229Z
UPDATE AD_Field SET DisplayLogic='@IsUseCustomization@=Y',Updated=TO_TIMESTAMP('2025-08-21 22:14:00.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752006
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Beschreibung Anpassung
-- Column: Mobile_Application_Trl.Description_Customized
-- 2025-08-21T22:14:23.351Z
UPDATE AD_Field SET DisplayLogic='@IsUseCustomization@=Y',Updated=TO_TIMESTAMP('2025-08-21 22:14:23.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752007
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Beschreibung Anpassung
-- Column: Mobile_Application_Trl.Description_Customized
-- 2025-08-21T22:14:30.735Z
UPDATE AD_Field SET DisplayLogic='@IsUseCustomization@=''Y''',Updated=TO_TIMESTAMP('2025-08-21 22:14:30.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752007
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Name Anpassung
-- Column: Mobile_Application_Trl.Name_Customized
-- 2025-08-21T22:14:40.389Z
UPDATE AD_Field SET DisplayLogic='@IsUseCustomization@=''Y''',Updated=TO_TIMESTAMP('2025-08-21 22:14:40.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752006
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Name Anpassung
-- Column: Mobile_Application_Trl.Name_Customized
-- 2025-08-21T22:15:41.533Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-08-21 22:15:41.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752006
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Individuelle Anpassungen verwenden
-- Column: Mobile_Application_Trl.IsUseCustomization
-- 2025-08-21T22:15:52.074Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-08-21 22:15:52.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752005
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Beschreibung Anpassung
-- Column: Mobile_Application_Trl.Description_Customized
-- 2025-08-21T22:15:57.361Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-08-21 22:15:57.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752007
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Individuelle Anpassungen verwenden
-- Column: Mobile_Application_Trl.IsUseCustomization
-- 2025-08-21T22:16:10.279Z
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2025-08-21 22:16:10.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752005
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Name Anpassung
-- Column: Mobile_Application_Trl.Name_Customized
-- 2025-08-21T22:16:13.302Z
UPDATE AD_Field SET SeqNo=60,Updated=TO_TIMESTAMP('2025-08-21 22:16:13.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752006
;

-- Field: Mobile Application(541826,D) -> Translation(547619,D) -> Beschreibung Anpassung
-- Column: Mobile_Application_Trl.Description_Customized
-- 2025-08-21T22:16:16.559Z
UPDATE AD_Field SET SeqNo=70,Updated=TO_TIMESTAMP('2025-08-21 22:16:16.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752007
;

-- UI Element: Mobile Application(541826,D) -> Translation(547619,D) -> main -> 10 -> default.Individuelle Anpassungen verwenden
-- Column: Mobile_Application_Trl.IsUseCustomization
-- 2025-08-21T22:16:42.177Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752005,0,547619,552038,636232,'F',TO_TIMESTAMP('2025-08-21 22:16:41.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Individuelle Anpassungen verwenden',50,0,0,TO_TIMESTAMP('2025-08-21 22:16:41.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application(541826,D) -> Translation(547619,D) -> main -> 10 -> default.Name Anpassung
-- Column: Mobile_Application_Trl.Name_Customized
-- 2025-08-21T22:16:52.376Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752006,0,547619,552038,636233,'F',TO_TIMESTAMP('2025-08-21 22:16:52.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Name Anpassung',60,0,0,TO_TIMESTAMP('2025-08-21 22:16:52.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application(541826,D) -> Translation(547619,D) -> main -> 10 -> default.Beschreibung Anpassung
-- Column: Mobile_Application_Trl.Description_Customized
-- 2025-08-21T22:17:01.838Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752007,0,547619,552038,636234,'F',TO_TIMESTAMP('2025-08-21 22:17:01.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Beschreibung Anpassung',70,0,0,TO_TIMESTAMP('2025-08-21 22:17:01.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
