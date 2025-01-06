-- Column: PP_Order_Node.TargetPlanningStatus
-- Column: PP_Order_Node.TargetPlanningStatus
-- 2024-05-13T12:49:38.555Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588261,583106,0,17,541869,53022,'TargetPlanningStatus',TO_TIMESTAMP('2024-05-13 15:49:38','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Target Planning status',0,0,TO_TIMESTAMP('2024-05-13 15:49:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-05-13T12:49:38.557Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588261 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-13T12:49:38.562Z
/* DDL */  select update_Column_Translation_From_AD_Element(583106) 
;

-- 2024-05-13T12:49:39.694Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Node','ALTER TABLE public.PP_Order_Node ADD COLUMN TargetPlanningStatus CHAR(1)')
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Target Planning status
-- Column: PP_Order_Node.TargetPlanningStatus
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Target Planning status
-- Column: PP_Order_Node.TargetPlanningStatus
-- 2024-05-13T12:50:17.861Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588261,728704,0,53036,TO_TIMESTAMP('2024-05-13 15:50:17','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Target Planning status',TO_TIMESTAMP('2024-05-13 15:50:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-13T12:50:17.864Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728704 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-13T12:50:17.867Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583106) 
;

-- 2024-05-13T12:50:17.870Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728704
;

-- 2024-05-13T12:50:17.871Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728704)
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Target Planning status
-- Column: PP_Order_Node.TargetPlanningStatus
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Target Planning status
-- Column: PP_Order_Node.TargetPlanningStatus
-- 2024-05-13T12:51:09.321Z
UPDATE AD_Field SET DisplayLogic='@PP_Activity_Type/-@=AC', IsDisplayed='Y', SeqNo=265,Updated=TO_TIMESTAMP('2024-05-13 15:51:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728704
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Belegstatus
-- Column: PP_Order_Node.DocStatus
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Belegstatus
-- Column: PP_Order_Node.DocStatus
-- 2024-05-13T15:01:28.635Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-13 18:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56519
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Duration
-- Column: PP_Order_Node.Duration
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration
-- Column: PP_Order_Node.Duration
-- 2024-05-13T15:01:28.644Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-05-13 18:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53702
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Vorlage Arbeitsschritte (ID)
-- Column: PP_Order_Node.AD_WF_Node_Template_ID
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Vorlage Arbeitsschritte (ID)
-- Column: PP_Order_Node.AD_WF_Node_Template_ID
-- 2024-05-13T15:01:28.651Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-05-13 18:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593771
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Manufacturing Activity Type
-- Column: PP_Order_Node.PP_Activity_Type
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Manufacturing Activity Type
-- Column: PP_Order_Node.PP_Activity_Type
-- 2024-05-13T15:01:28.656Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-05-13 18:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=667614
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Always available to user
-- Column: PP_Order_Node.PP_AlwaysAvailableToUser
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Always available to user
-- Column: PP_Order_Node.PP_AlwaysAvailableToUser
-- 2024-05-13T15:01:28.662Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-05-13 18:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707758
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Belegstatus
-- Column: PP_Order_Node.DocStatus
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Belegstatus
-- Column: PP_Order_Node.DocStatus
-- 2024-05-13T15:01:55.722Z
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2024-05-13 18:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56519
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Vorlage Arbeitsschritte (ID)
-- Column: PP_Order_Node.AD_WF_Node_Template_ID
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Vorlage Arbeitsschritte (ID)
-- Column: PP_Order_Node.AD_WF_Node_Template_ID
-- 2024-05-13T15:01:55.728Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2024-05-13 18:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593771
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Manufacturing Activity Type
-- Column: PP_Order_Node.PP_Activity_Type
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Manufacturing Activity Type
-- Column: PP_Order_Node.PP_Activity_Type
-- 2024-05-13T15:01:55.733Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2024-05-13 18:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=667614
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Target Planning status
-- Column: PP_Order_Node.TargetPlanningStatus
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Target Planning status
-- Column: PP_Order_Node.TargetPlanningStatus
-- 2024-05-13T15:01:55.738Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2024-05-13 18:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728704
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Scanned QR Code
-- Column: PP_Order_Node.ScannedQRCode
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Scanned QR Code
-- Column: PP_Order_Node.ScannedQRCode
-- 2024-05-13T15:01:55.744Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2024-05-13 18:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707988
;

-- Field: Produktionsauftrag -> Knoten Arbeitsablauf -> Target Planning status
-- Column: PP_Order_Node.TargetPlanningStatus
-- Field: Produktionsauftrag(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Target Planning status
-- Column: PP_Order_Node.TargetPlanningStatus
-- 2024-05-13T15:07:34.674Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-05-13 18:07:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728704
;

