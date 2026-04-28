-- Run mode: SWING_CLIENT

-- Column: C_OLCand.AD_InputDataSource_ID
-- 2025-10-21T10:53:00.301Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-10-21 10:53:00.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544998
;

-- 2025-10-21T10:53:01.779Z
INSERT INTO t_alter_column values('c_olcand','AD_InputDataSource_ID','NUMERIC(10)',null,null)
;

-- 2025-10-21T10:53:01.789Z
INSERT INTO t_alter_column values('c_olcand','AD_InputDataSource_ID',null,'NULL',null)
;

-- Column: C_OLCand.ExternalSystem_ID
-- 2025-10-21T10:55:10.318Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591412,583968,0,19,540244,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-10-21 10:55:10.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','540008','de.metas.ordercandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-10-21 10:55:10.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-21T10:55:10.321Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591412 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-21T10:55:10.370Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-10-21T10:55:11.111Z
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN ExternalSystem_ID NUMERIC(10) DEFAULT 540008')
;

-- 2025-10-21T10:55:11.455Z
ALTER TABLE C_OLCand ADD CONSTRAINT ExternalSystem_COLCand FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Run mode: SWING_CLIENT

-- Column: C_OLCand.ExternalSystem_ID
-- 2025-10-21T10:56:19.814Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-10-21 10:56:19.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591412
;

-- 2025-10-21T10:56:20.677Z
INSERT INTO t_alter_column values('c_olcand','ExternalSystem_ID','NUMERIC(10)',null,'540008')
;

-- 2025-10-21T10:56:20.682Z
UPDATE C_OLCand SET ExternalSystem_ID=540008 WHERE ExternalSystem_ID IS NULL
;

-- 2025-10-21T10:56:20.691Z
INSERT INTO t_alter_column values('c_olcand','ExternalSystem_ID',null,'NOT NULL',null)
;

-- Run mode: SWING_CLIENT

-- 2025-10-21T10:58:34.383Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591412,541477,540509,0,TO_TIMESTAMP('2025-10-21 10:58:34.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.ordercandidate','Y',5,TO_TIMESTAMP('2025-10-21 10:58:34.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-21T10:58:45.489Z
DROP INDEX IF EXISTS C_OLCand_External_ID
;

-- 2025-10-21T10:58:45.490Z
CREATE UNIQUE INDEX C_OLCand_External_ID ON C_OLCand (ExternalSystem_ID,ExternalHeaderId,ExternalLineId,AD_Org_ID) WHERE IsActive='Y'
;

-- Run mode: SWING_CLIENT

-- 2025-10-21T16:31:16.018Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,591412,0,TO_TIMESTAMP('2025-10-21 16:31:15.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540378,550958,'E','Y','Y','N','ExternalSystem_ID',5,'E',TO_TIMESTAMP('2025-10-21 16:31:15.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ExternalSystem_ID')
;

-- 2025-10-21T16:32:15.420Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,591412,0,TO_TIMESTAMP('2025-10-21 16:32:15.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540411,550959,'E','Y','Y','N','ExternalSystem_ID',5,'E',TO_TIMESTAMP('2025-10-21 16:32:15.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ExternalSystem_ID')
;

-- 2025-10-21T16:32:19.369Z
UPDATE EXP_FormatLine SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-10-21 16:32:19.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550088
;

-- 2025-10-21T16:31:20.190Z
UPDATE EXP_FormatLine SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-10-21 16:31:20.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=549044
;

-- Run mode: SWING_CLIENT

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Externes System
-- Column: C_OLCand.ExternalSystem_ID
-- 2025-10-22T08:58:33.188Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591412,755056,0,548442,TO_TIMESTAMP('2025-10-22 08:58:32.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-10-22 08:58:32.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-22T08:58:33.196Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-22T08:58:33.239Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-10-22T08:58:33.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755056
;

-- 2025-10-22T08:58:33.267Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755056)
;

-- UI Element: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Externes System
-- Column: C_OLCand.ExternalSystem_ID
-- 2025-10-22T08:59:03.658Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755056,0,548442,637922,553564,'F',TO_TIMESTAMP('2025-10-22 08:59:03.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Externes System',5,0,0,TO_TIMESTAMP('2025-10-22 08:59:03.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Auftragsdisposition(541952,de.metas.ordercandidate) -> Kandidat(548442,de.metas.ordercandidate) -> Externes System
-- Column: C_OLCand.ExternalSystem_ID
-- 2025-10-22T09:02:45.609Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-10-22 09:02:45.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755056
;

-- Field: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Externes System
-- Column: C_OLCand.ExternalSystem_ID
-- 2025-10-22T09:04:12.068Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591412,755057,0,540282,TO_TIMESTAMP('2025-10-22 09:04:11.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-10-22 09:04:11.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-22T09:04:12.071Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-22T09:04:12.074Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-10-22T09:04:12.088Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755057
;

-- 2025-10-22T09:04:12.094Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755057)
;

-- UI Element: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> advanced edit -> 10 -> advanced edit.Externes System
-- Column: C_OLCand.ExternalSystem_ID
-- 2025-10-22T09:04:47.949Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755057,0,540282,637923,540962,'F',TO_TIMESTAMP('2025-10-22 09:04:47.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Externes System',5,0,0,TO_TIMESTAMP('2025-10-22 09:04:47.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Auftragsdisposition (Legacy-EDI-Import)(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Externes System
-- Column: C_OLCand.ExternalSystem_ID
-- 2025-10-22T09:04:56.076Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-10-22 09:04:56.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755057
;

-- Run mode: SWING_CLIENT

-- Column: C_Order.ExternalSystem_ID
-- 2025-10-22T09:49:52.968Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591418,583968,0,30,259,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-10-22 09:49:52.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-10-22 09:49:52.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-22T09:49:52.977Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591418 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-22T09:49:52.988Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-10-22T09:49:53.881Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-10-22T09:49:56.227Z
ALTER TABLE C_Order ADD CONSTRAINT ExternalSystem_COrder FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Externes System
-- Column: C_Order.ExternalSystem_ID
-- 2025-10-22T09:51:42.692Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591418,755058,0,186,TO_TIMESTAMP('2025-10-22 09:51:42.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-10-22 09:51:42.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-22T09:51:42.695Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-22T09:51:42.703Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-10-22T09:51:42.714Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755058
;

-- 2025-10-22T09:51:42.721Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755058)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> DocType.Externes System
-- Column: C_Order.ExternalSystem_ID
-- 2025-10-22T09:52:24.759Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755058,0,186,637924,1000001,'F',TO_TIMESTAMP('2025-10-22 09:52:24.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',45,0,0,TO_TIMESTAMP('2025-10-22 09:52:24.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_ShipmentSchedule.ExternalSystem_ID
-- 2025-10-22T09:57:19.309Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591419,583968,0,30,500221,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-10-22 09:57:19.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-10-22 09:57:19.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-22T09:57:19.313Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591419 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-22T09:57:19.320Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-10-22T09:57:20.119Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-10-22T09:57:20.391Z
ALTER TABLE M_ShipmentSchedule ADD CONSTRAINT ExternalSystem_MShipmentSchedule FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Externes System
-- Column: M_ShipmentSchedule.ExternalSystem_ID
-- 2025-10-22T09:57:43.731Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591419,755059,0,500221,TO_TIMESTAMP('2025-10-22 09:57:43.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-10-22 09:57:43.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-22T09:57:43.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-22T09:57:43.737Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-10-22T09:57:43.748Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755059
;

-- 2025-10-22T09:57:43.756Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755059)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> order dates.Externes System
-- Column: M_ShipmentSchedule.ExternalSystem_ID
-- 2025-10-22T09:58:27.662Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755059,0,500221,637925,540048,'F',TO_TIMESTAMP('2025-10-22 09:58:27.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',24,0,0,TO_TIMESTAMP('2025-10-22 09:58:27.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Order.ExternalSystem_ID
-- 2025-10-22T10:02:41.500Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-22 10:02:41.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591418
;

-- 2025-10-22T10:02:46.892Z
INSERT INTO t_alter_column values('c_order','ExternalSystem_ID','NUMERIC(10)',null,null)
;

CREATE UNIQUE INDEX C_Order_ExternalHeader_ID ON C_Order (ExternalSystem_ID,ExternalId,AD_Org_ID) WHERE IsActive='Y' AND ExternalId IS NOT NULL
;

CREATE UNIQUE INDEX M_ShipmentSchedule_External_ID ON M_ShipmentSchedule (ExternalSystem_ID,ExternalHeaderId,ExternalLineId,AD_Org_ID) WHERE IsActive='Y'
;

-- Column: M_InOut.ExternalSystem_ID
-- 2025-10-22T10:51:49.869Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591420,583968,0,30,319,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-10-22 10:51:49.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-10-22 10:51:49.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-22T10:51:49.871Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591420 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-22T10:51:49.875Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-10-22T10:51:50.697Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-10-22T10:51:51.914Z
ALTER TABLE M_InOut ADD CONSTRAINT ExternalSystem_MInOut FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> Externes System
-- Column: M_InOut.ExternalSystem_ID
-- 2025-10-22T10:52:19.388Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591420,755060,0,257,TO_TIMESTAMP('2025-10-22 10:52:19.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-10-22 10:52:19.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-22T10:52:19.389Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-22T10:52:19.391Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-10-22T10:52:19.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755060
;

-- 2025-10-22T10:52:19.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755060)
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> main -> 20 -> dates.Externes System
-- Column: M_InOut.ExternalSystem_ID
-- 2025-10-22T10:53:00.939Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755060,0,257,637926,1000029,'F',TO_TIMESTAMP('2025-10-22 10:53:00.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',64,0,0,TO_TIMESTAMP('2025-10-22 10:53:00.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Invoice_Candidate.ExternalSystem_ID
-- 2025-10-22T12:26:00.846Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591421,583968,0,30,540270,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-10-22 12:26:00.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-10-22 12:26:00.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-22T12:26:00.855Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591421 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-22T12:26:00.865Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-10-22T12:26:01.692Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-10-22T12:26:02.281Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT ExternalSystem_CInvoiceCandidate FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Externes System
-- Column: C_Invoice_Candidate.ExternalSystem_ID
-- 2025-10-22T12:51:37.034Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591421,755061,0,540279,TO_TIMESTAMP('2025-10-22 12:51:36.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-10-22 12:51:36.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-22T12:51:37.038Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-22T12:51:37.044Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-10-22T12:51:37.055Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755061
;

-- 2025-10-22T12:51:37.061Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755061)
;

-- UI Element: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Externes System
-- Column: C_Invoice_Candidate.ExternalSystem_ID
-- 2025-10-22T12:52:35.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755061,0,540279,637927,540058,'F',TO_TIMESTAMP('2025-10-22 12:52:35.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',60,0,0,TO_TIMESTAMP('2025-10-22 12:52:35.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Externes System
-- Column: C_Invoice_Candidate.ExternalSystem_ID
-- 2025-10-22T12:53:28.147Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-10-22 12:53:28.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755061
;

CREATE UNIQUE INDEX C_Invoice_Candidate_External_ID ON C_Invoice_Candidate (ExternalSystem_ID,ExternalHeaderId,ExternalLineId,AD_Org_ID) WHERE IsActive='Y'
;
-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Externes System
-- Column: C_Invoice_Candidate.ExternalSystem_ID
-- 2025-10-22T12:55:54.328Z
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2025-10-22 12:55:54.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755061
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Externes System
-- Column: C_Invoice_Candidate.ExternalSystem_ID
-- 2025-10-22T12:56:19.409Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-10-22 12:56:19.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755061
;

-- Column: C_Invoice.ExternalSystem_ID
-- 2025-10-22T13:00:11.313Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591422,583968,0,30,318,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-10-22 13:00:11.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-10-22 13:00:11.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-22T13:00:11.317Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591422 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-22T13:00:11.325Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-10-22T13:00:12.132Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-10-22T13:00:13.850Z
ALTER TABLE C_Invoice ADD CONSTRAINT ExternalSystem_CInvoice FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice.ExternalSystem_ID
-- 2025-10-22T13:00:20.844Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-22 13:00:20.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591422
;

-- 2025-10-22T13:00:21.588Z
INSERT INTO t_alter_column values('c_invoice','ExternalSystem_ID','NUMERIC(10)',null,null)
;

-- Column: C_OLCand.AD_InputDataSource_ID
-- 2025-10-22T14:53:25.181Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2025-10-22 14:53:25.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544998
;

-- 2025-10-22T14:53:27.048Z
INSERT INTO t_alter_column values('c_olcand','AD_InputDataSource_ID','NUMERIC(10)',null,null)
;

ALTER TABLE C_OLCand
    ALTER COLUMN AD_InputDataSource_ID
        DROP DEFAULT
;

-- Run mode: SWING_CLIENT

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Externes System
-- Column: C_Invoice.ExternalSystem_ID
-- 2025-10-22T16:45:49.893Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591422,755062,0,263,TO_TIMESTAMP('2025-10-22 16:45:49.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-10-22 16:45:49.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-22T16:45:49.901Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-22T16:45:49.947Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-10-22T16:45:49.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755062
;

-- 2025-10-22T16:45:49.975Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755062)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> main -> 20 -> dates.Externes System
-- Column: C_Invoice.ExternalSystem_ID
-- 2025-10-22T16:46:32.014Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755062,0,263,637928,540027,'F',TO_TIMESTAMP('2025-10-22 16:46:31.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',34,0,0,TO_TIMESTAMP('2025-10-22 16:46:31.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Run mode: SWING_CLIENT

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Externe ID
-- Column: C_Invoice.ExternalId
-- 2025-10-23T11:11:12.998Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572375,755064,0,263,TO_TIMESTAMP('2025-10-23 11:11:12.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,100,'D','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2025-10-23 11:11:12.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T11:11:13.008Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T11:11:13.061Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939)
;

-- 2025-10-23T11:11:13.102Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755064
;

-- 2025-10-23T11:11:13.113Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755064)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Externe ID
-- Column: C_Invoice.ExternalId
-- 2025-10-23T11:11:34.613Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755064,0,263,637930,541214,'F',TO_TIMESTAMP('2025-10-23 11:11:34.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Externe ID',100,0,0,TO_TIMESTAMP('2025-10-23 11:11:34.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Rechnung(167,D) -> Rechnungsposition(270,D) -> External IDs
-- Column: C_InvoiceLine.ExternalIds
-- 2025-10-23T11:12:36.051Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563269,755065,0,270,TO_TIMESTAMP('2025-10-23 11:12:35.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'List of external IDs from C_Invoice_Candidates; delimited with '';,;''',255,'D','Y','N','N','N','N','N','N','N','External IDs',TO_TIMESTAMP('2025-10-23 11:12:35.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-23T11:12:36.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755065 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-23T11:12:36.054Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544448)
;

-- 2025-10-23T11:12:36.063Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755065
;

-- 2025-10-23T11:12:36.064Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755065)
;

-- UI Element: Rechnung(167,D) -> Rechnungsposition(270,D) -> main -> 10 -> default.External IDs
-- Column: C_InvoiceLine.ExternalIds
-- 2025-10-23T11:13:17.190Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755065,0,270,637931,540023,'F',TO_TIMESTAMP('2025-10-23 11:13:17.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'List of external IDs from C_Invoice_Candidates; delimited with '';,;''','Y','N','N','Y','N','N','N',0,'External IDs',15,0,0,TO_TIMESTAMP('2025-10-23 11:13:17.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
