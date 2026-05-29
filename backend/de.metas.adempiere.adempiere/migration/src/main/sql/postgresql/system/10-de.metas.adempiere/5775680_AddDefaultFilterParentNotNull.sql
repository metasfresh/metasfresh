-- Run mode: SWING_CLIENT

-- Column: C_ElementValue.Parent_ID
-- 2025-11-04T17:34:15.265Z
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-04 17:34:15.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=570105
;

-- 2025-11-04T18:54:24.442Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584195,0,'HasParent',TO_TIMESTAMP('2025-11-04 18:54:24.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Has Parent','Has Parent',TO_TIMESTAMP('2025-11-04 18:54:24.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-04T18:54:24.459Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584195 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: HasParent
-- 2025-11-04T18:54:35.174Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Hat übergeordnetes Konto', PrintName='Hat übergeordnetes Konto',Updated=TO_TIMESTAMP('2025-11-04 18:54:35.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584195 AND AD_Language='de_CH'
;

-- 2025-11-04T18:54:35.176Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-04T18:54:35.504Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584195,'de_CH')
;

-- Element: HasParent
-- 2025-11-04T18:54:45.345Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Hat übergeordnetes Konto', PrintName='Hat übergeordnetes Konto',Updated=TO_TIMESTAMP('2025-11-04 18:54:45.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584195 AND AD_Language='de_DE'
;

-- 2025-11-04T18:54:45.347Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-04T18:54:46.364Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584195,'de_DE')
;

-- 2025-11-04T18:54:46.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584195,'de_DE')
;

-- Element: HasParent
-- 2025-11-04T18:54:56.622Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Has Parent Account', PrintName='Has Parent Account',Updated=TO_TIMESTAMP('2025-11-04 18:54:56.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584195 AND AD_Language='en_US'
;

-- 2025-11-04T18:54:56.624Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-04T18:54:56.877Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584195,'en_US')
;

-- Column: C_ElementValue.HasParent
-- 2025-11-04T18:55:19.690Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591489,584195,0,20,188,'XX','HasParent',TO_TIMESTAMP('2025-11-04 18:55:19.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Hat übergeordnetes Konto',0,0,TO_TIMESTAMP('2025-11-04 18:55:19.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-04T18:55:19.697Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591489 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-04T18:55:19.704Z
/* DDL */  select update_Column_Translation_From_AD_Element(584195)
;

-- Column: C_ElementValue.HasParent
-- 2025-11-04T18:57:55.225Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN parent_id IS NULL THEN ''N'' ELSE ''Y'' END)', IsLazyLoading='Y', IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-11-04 18:57:55.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591489
;



-- Column: C_ElementValue.HasParent
-- 2025-11-04T19:01:51.028Z
UPDATE AD_Column SET FilterDefaultValue='N', FilterOperator='E', IsSelectionColumn='Y', IsShowFilterInline='Y',Updated=TO_TIMESTAMP('2025-11-04 19:01:51.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591489
;

-- Column: C_ElementValue.HasParent
-- 2025-11-04T19:03:16.621Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2025-11-04 19:03:16.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591489
;

-- Column: C_ElementValue.HasParent
-- 2025-11-04T19:04:36.702Z
UPDATE AD_Column SET FilterDefaultValue='',Updated=TO_TIMESTAMP('2025-11-04 19:04:36.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591489
;

-- Column: C_ElementValue.HasParent
-- 2025-11-04T19:04:41.270Z
UPDATE AD_Column SET FilterOperator='N',Updated=TO_TIMESTAMP('2025-11-04 19:04:41.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591489
;

-- Field: Konten(540761,D) -> Kontenart(542127,D) -> Hat übergeordnetes Konto
-- Column: C_ElementValue.HasParent
-- 2025-11-04T19:05:54.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591489,755951,0,542127,0,TO_TIMESTAMP('2025-11-04 19:05:54.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Hat übergeordnetes Konto',0,0,40,0,1,1,TO_TIMESTAMP('2025-11-04 19:05:54.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-04T19:05:54.426Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755951 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-04T19:05:54.431Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584195)
;

-- 2025-11-04T19:05:54.441Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755951
;

-- 2025-11-04T19:05:54.452Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755951)
;

-- UI Element: Konten(540761,D) -> Kontenart(542127,D) -> advancedEdit -> 10 -> adv.Hat übergeordnetes Konto
-- Column: C_ElementValue.HasParent
-- 2025-11-04T19:06:17.147Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755951,0,542127,543190,638558,'F',TO_TIMESTAMP('2025-11-04 19:06:16.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Hat übergeordnetes Konto',90,0,0,TO_TIMESTAMP('2025-11-04 19:06:16.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_ElementValue.HasParent
-- 2025-11-04T19:08:33.214Z
UPDATE AD_Column SET FilterDefaultValue='N', FilterOperator='E', IsShowFilterInline='N',Updated=TO_TIMESTAMP('2025-11-04 19:08:33.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591489
;

