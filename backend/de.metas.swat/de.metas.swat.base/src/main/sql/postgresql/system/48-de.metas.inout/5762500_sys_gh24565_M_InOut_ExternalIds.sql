-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.ExternalHeaderId
-- 2025-08-11T18:14:32.948Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590601,575915,0,10,500221,'XX','ExternalHeaderId',TO_TIMESTAMP('2025-08-11 18:14:31.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.inoutcandidate',0,100,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe Datensatz-Kopf-ID',0,0,TO_TIMESTAMP('2025-08-11 18:14:31.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-11T18:14:32.952Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590601 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-11T18:14:32.960Z
/* DDL */  select update_Column_Translation_From_AD_Element(575915)
;

-- 2025-08-11T18:14:33.734Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN ExternalHeaderId VARCHAR(100)')
;

-- Column: M_ShipmentSchedule.ExternalLineId
-- 2025-08-11T18:15:07.389Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590602,575914,0,10,500221,'XX','ExternalLineId',TO_TIMESTAMP('2025-08-11 18:15:07.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.inoutcandidate',0,100,'Y','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe Datensatz-Zeilen-ID','NP',0,0,TO_TIMESTAMP('2025-08-11 18:15:07.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-11T18:15:07.390Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590602 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-11T18:15:07.530Z
/* DDL */  select update_Column_Translation_From_AD_Element(575914)
;

-- 2025-08-11T18:15:08.455Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN ExternalLineId VARCHAR(100)')
;

-- Column: M_ShipmentSchedule.ExternalHeaderId
-- 2025-08-11T18:15:30.555Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2025-08-11 18:15:30.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590601
;

-- Run mode: SWING_CLIENT

-- Column: M_InOutLine.ExternalId
-- 2025-08-11T19:20:13.980Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590603,543939,0,10,320,'XX','ExternalId',TO_TIMESTAMP('2025-08-11 19:20:13.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe ID',0,0,TO_TIMESTAMP('2025-08-11 19:20:13.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-11T19:20:13.988Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590603 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-11T19:20:13.998Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939)
;

-- 2025-08-11T19:20:14.960Z
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN ExternalId VARCHAR(255)')
;

-- Run mode: SWING_CLIENT

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Externe Datensatz-Kopf-ID
-- Column: M_ShipmentSchedule.ExternalHeaderId
-- 2025-08-12T11:23:26.663Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590601,751855,0,500221,TO_TIMESTAMP('2025-08-12 11:23:26.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,100,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Externe Datensatz-Kopf-ID',TO_TIMESTAMP('2025-08-12 11:23:26.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-12T11:23:26.667Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751855 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-12T11:23:26.718Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575915)
;

-- 2025-08-12T11:23:26.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751855
;

-- 2025-08-12T11:23:26.748Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751855)
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Externe Datensatz-Zeilen-ID
-- Column: M_ShipmentSchedule.ExternalLineId
-- 2025-08-12T11:23:32.718Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590602,751856,0,500221,TO_TIMESTAMP('2025-08-12 11:23:32.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,100,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Externe Datensatz-Zeilen-ID',TO_TIMESTAMP('2025-08-12 11:23:32.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-12T11:23:32.719Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751856 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-12T11:23:32.720Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575914)
;

-- 2025-08-12T11:23:32.726Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751856
;

-- 2025-08-12T11:23:32.727Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751856)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Externe Datensatz-Kopf-ID
-- Column: M_ShipmentSchedule.ExternalHeaderId
-- 2025-08-12T11:23:56.046Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,751855,0,500221,636134,540052,'F',TO_TIMESTAMP('2025-08-12 11:23:55.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externe Datensatz-Kopf-ID',340,0,0,TO_TIMESTAMP('2025-08-12 11:23:55.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Externe Datensatz-Zeilen-ID
-- Column: M_ShipmentSchedule.ExternalLineId
-- 2025-08-12T11:24:05.445Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,751856,0,500221,636135,540052,'F',TO_TIMESTAMP('2025-08-12 11:24:05.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Externe Datensatz-Zeilen-ID',350,0,0,TO_TIMESTAMP('2025-08-12 11:24:05.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Externe Datensatz-Kopf-ID
-- Column: M_ShipmentSchedule.ExternalHeaderId
-- 2025-08-12T11:24:12.069Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-08-12 11:24:12.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636134
;

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> Externe ID
-- Column: M_InOut.ExternalId
-- 2025-08-12T11:25:18.969Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571119,751857,0,257,TO_TIMESTAMP('2025-08-12 11:25:18.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2025-08-12 11:25:18.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-12T11:25:18.971Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-12T11:25:18.973Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939)
;

-- 2025-08-12T11:25:18.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751857
;

-- 2025-08-12T11:25:18.998Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751857)
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> advanced edit -> 10 -> advanced edit.Externe ID
-- Column: M_InOut.ExternalId
-- 2025-08-12T11:25:37.057Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,751857,0,257,636136,540128,'F',TO_TIMESTAMP('2025-08-12 11:25:36.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Externe ID',120,0,0,TO_TIMESTAMP('2025-08-12 11:25:36.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Lieferung(169,D) -> Lieferposition(258,D) -> Externe ID
-- Column: M_InOutLine.ExternalId
-- 2025-08-12T11:25:46.051Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590603,751858,0,258,TO_TIMESTAMP('2025-08-12 11:25:45.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2025-08-12 11:25:45.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-12T11:25:46.052Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-12T11:25:46.056Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939)
;

-- 2025-08-12T11:25:46.063Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751858
;

-- 2025-08-12T11:25:46.066Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751858)
;

-- UI Element: Lieferung(169,D) -> Lieferposition(258,D) -> main -> 10 -> default.Externe ID
-- Column: M_InOutLine.ExternalId
-- 2025-08-12T11:26:10.334Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,751858,0,258,636137,540976,'F',TO_TIMESTAMP('2025-08-12 11:26:10.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externe ID',180,0,0,TO_TIMESTAMP('2025-08-12 11:26:10.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Externe Datensatz-Kopf-ID
-- Column: M_ShipmentSchedule.ExternalHeaderId
-- 2025-08-12T11:31:47.751Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-08-12 11:31:47.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=751855
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Externe Datensatz-Zeilen-ID
-- Column: M_ShipmentSchedule.ExternalLineId
-- 2025-08-12T11:31:51.896Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-08-12 11:31:51.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=751856
;
