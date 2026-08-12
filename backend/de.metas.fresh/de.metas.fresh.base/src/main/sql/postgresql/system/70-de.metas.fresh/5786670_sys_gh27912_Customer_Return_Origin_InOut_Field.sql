-- Run mode: SWING_CLIENT

-- 2026-02-04T08:10:30.993Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584493,0,'Return_Origin_InOut_ID',TO_TIMESTAMP('2026-02-04 08:10:30.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Ursprüngliche Lieferung','Ursprüngliche Lieferung',TO_TIMESTAMP('2026-02-04 08:10:30.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T08:10:31.107Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584493 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Return_Origin_InOut_ID
-- 2026-02-04T08:11:27.630Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Original Shipment', PrintName='Original Shipment',Updated=TO_TIMESTAMP('2026-02-04 08:11:27.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584493 AND AD_Language='en_US'
;

-- 2026-02-04T08:11:27.713Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T08:11:40.126Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584493,'en_US')
;

-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-02-04T08:12:55.934Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591915,584493,0,18,540299,319,'XX','Return_Origin_InOut_ID',TO_TIMESTAMP('2026-02-04 08:12:54.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ursprüngliche Lieferung',0,0,TO_TIMESTAMP('2026-02-04 08:12:54.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-04T08:12:56.029Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591915 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-04T08:12:56.200Z
/* DDL */  select update_Column_Translation_From_AD_Element(584493)
;

-- 2026-02-04T08:13:21.716Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN Return_Origin_InOut_ID NUMERIC(10)')
;

-- 2026-02-04T08:13:22.211Z
ALTER TABLE M_InOut ADD CONSTRAINT ReturnOriginInOut_MInOut FOREIGN KEY (Return_Origin_InOut_ID) REFERENCES public.M_InOut DEFERRABLE INITIALLY DEFERRED
;

-- Field: Kundenrücklieferung(53097,D) -> Kundenrücklieferung(53271,D) -> Ursprüngliche Lieferung
-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-02-04T08:15:01.952Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591915,771767,0,53271,0,TO_TIMESTAMP('2026-02-04 08:15:00.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','Y','N',0,'Ursprüngliche Lieferung',0,0,430,0,1,1,TO_TIMESTAMP('2026-02-04 08:15:00.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T08:15:02.049Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771767 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T08:15:02.133Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584493)
;

-- 2026-02-04T08:15:02.230Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771767
;

-- 2026-02-04T08:15:02.321Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771767)
;

-- UI Element: Kundenrücklieferung(53097,D) -> Kundenrücklieferung(53271,D) -> main -> 20 -> doctype.Ursprüngliche Lieferung
-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-02-04T08:15:58.188Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771767,0,53271,540509,646759,'F',TO_TIMESTAMP('2026-02-04 08:15:57.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Ursprüngliche Lieferung',80,0,0,TO_TIMESTAMP('2026-02-04 08:15:57.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_InOut.Return_Origin_InOut_ID
-- 2026-02-04T08:17:14.055Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2026-02-04 08:17:14.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591915
;

