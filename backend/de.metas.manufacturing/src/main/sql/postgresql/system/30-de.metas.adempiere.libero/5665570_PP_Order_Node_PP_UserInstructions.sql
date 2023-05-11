-- Column: PP_Order_Node.PP_UserInstructions
-- 2022-11-22T08:42:57.375Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585111,581715,0,10,53022,'PP_UserInstructions',TO_TIMESTAMP('2022-11-22 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'User Instructions',0,0,TO_TIMESTAMP('2022-11-22 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-22T08:42:57.376Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585111 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-22T08:42:57.379Z
/* DDL */  select update_Column_Translation_From_AD_Element(581715) 
;

-- 2022-11-22T08:42:58.091Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Node','ALTER TABLE public.PP_Order_Node ADD COLUMN PP_UserInstructions VARCHAR(2000)')
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> User Instructions
-- Column: PP_Order_Node.PP_UserInstructions
-- 2022-11-22T08:43:48.678Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585111,708143,0,53036,TO_TIMESTAMP('2022-11-22 10:43:48','YYYY-MM-DD HH24:MI:SS'),100,2000,'EE01','Y','N','N','N','N','N','N','N','User Instructions',TO_TIMESTAMP('2022-11-22 10:43:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T08:43:48.680Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-22T08:43:48.682Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581715) 
;

-- 2022-11-22T08:43:48.685Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708143
;

-- 2022-11-22T08:43:48.686Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708143)
;





















-- **********************************************************************************************************************************************
-- **********************************************************************************************************************************************
-- **********************************************************************************************************************************************
-- Produktionsauftrag_OLD(53009,EE01)
-- **********************************************************************************************************************************************
-- **********************************************************************************************************************************************
-- **********************************************************************************************************************************************





-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Name
-- Column: PP_Order_Node.Name
-- 2022-11-22T08:44:33.040Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578133,708144,0,53036,TO_TIMESTAMP('2022-11-22 10:44:32','YYYY-MM-DD HH24:MI:SS'),100,'',255,'EE01','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-11-22 10:44:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T08:44:33.041Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708144 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-22T08:44:33.042Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-11-22T08:44:33.136Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708144
;

-- 2022-11-22T08:44:33.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708144)
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Beschreibung
-- Column: PP_Order_Node.Description
-- 2022-11-22T08:45:31.101Z
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2022-11-22 10:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53666
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Aktiv
-- Column: PP_Order_Node.IsActive
-- 2022-11-22T08:45:31.108Z
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2022-11-22 10:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53668
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Name
-- Column: PP_Order_Node.Name
-- 2022-11-22T08:45:31.114Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-11-22 10:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708144
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> User Instructions
-- Column: PP_Order_Node.PP_UserInstructions
-- 2022-11-22T08:45:31.119Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-11-22 10:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708143
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Geschäftspartner
-- Column: PP_Order_Node.C_BPartner_ID
-- 2022-11-22T08:45:50.302Z
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53672
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Is Milestone
-- Column: PP_Order_Node.IsMilestone
-- 2022-11-22T08:45:50.308Z
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53670
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Is Subcontracting
-- Column: PP_Order_Node.IsSubcontracting
-- 2022-11-22T08:45:50.313Z
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53671
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Overlap Units
-- Column: PP_Order_Node.OverlapUnits
-- 2022-11-22T08:45:50.318Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53699
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Queuing Time
-- Column: PP_Order_Node.QueuingTime
-- 2022-11-22T08:45:50.323Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53700
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Setup Time
-- Column: PP_Order_Node.SetupTime
-- 2022-11-22T08:45:50.328Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53701
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration
-- Column: PP_Order_Node.Duration
-- 2022-11-22T08:45:50.333Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53702
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Warte Zeit
-- Column: PP_Order_Node.WaitingTime
-- 2022-11-22T08:45:50.338Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53704
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Moving Time
-- Column: PP_Order_Node.MovingTime
-- 2022-11-22T08:45:50.343Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53705
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Setup Time Requiered
-- Column: PP_Order_Node.SetupTimeRequiered
-- 2022-11-22T08:45:50.348Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53706
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Setup Time Real
-- Column: PP_Order_Node.SetupTimeReal
-- 2022-11-22T08:45:50.353Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53707
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration Requiered
-- Column: PP_Order_Node.DurationRequiered
-- 2022-11-22T08:45:50.357Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53708
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration Real
-- Column: PP_Order_Node.DurationReal
-- 2022-11-22T08:45:50.363Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53709
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> DateStart
-- Column: PP_Order_Node.DateStart
-- 2022-11-22T08:45:50.367Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53710
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Projektabschluss
-- Column: PP_Order_Node.DateFinish
-- 2022-11-22T08:45:50.373Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53711
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> DateStartSchedule
-- Column: PP_Order_Node.DateStartSchedule
-- 2022-11-22T08:45:50.379Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53712
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> DateFinishSchedule
-- Column: PP_Order_Node.DateFinishSchedule
-- 2022-11-22T08:45:50.384Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53713
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Menge angefragt
-- Column: PP_Order_Node.QtyRequiered
-- 2022-11-22T08:45:50.388Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53714
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Gelieferte Menge
-- Column: PP_Order_Node.QtyDelivered
-- 2022-11-22T08:45:50.393Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53715
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Qty Reject
-- Column: PP_Order_Node.QtyReject
-- 2022-11-22T08:45:50.399Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53716
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> QtyScrap
-- Column: PP_Order_Node.QtyScrap
-- 2022-11-22T08:45:50.404Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53717
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Belegstatus
-- Column: PP_Order_Node.DocStatus
-- 2022-11-22T08:45:50.411Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56519
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Vorlage Arbeitsschritte (ID)
-- Column: PP_Order_Node.AD_WF_Node_Template_ID
-- 2022-11-22T08:45:50.417Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593771
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Manufacturing Activity Type
-- Column: PP_Order_Node.PP_Activity_Type
-- 2022-11-22T08:45:50.422Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=667614
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Always available to user
-- Column: PP_Order_Node.PP_AlwaysAvailableToUser
-- 2022-11-22T08:45:50.427Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2022-11-22 10:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707758
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Knoten
-- Column: PP_Order_Node.AD_WF_Node_ID
-- 2022-11-22T08:47:08.558Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53658
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Suchschlüssel
-- Column: PP_Order_Node.Value
-- 2022-11-22T08:47:08.563Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53664
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Name
-- Column: PP_Order_Node.Name
-- 2022-11-22T08:47:08.568Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708144
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> User Instructions
-- Column: PP_Order_Node.PP_UserInstructions
-- 2022-11-22T08:47:08.573Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708143
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Produktionsressource
-- Column: PP_Order_Node.S_Resource_ID
-- 2022-11-22T08:47:08.577Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53669
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Overlap Units
-- Column: PP_Order_Node.OverlapUnits
-- 2022-11-22T08:47:08.582Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53699
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Queuing Time
-- Column: PP_Order_Node.QueuingTime
-- 2022-11-22T08:47:08.587Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53700
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Setup Time
-- Column: PP_Order_Node.SetupTime
-- 2022-11-22T08:47:08.591Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53701
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration
-- Column: PP_Order_Node.Duration
-- 2022-11-22T08:47:08.596Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53702
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Warte Zeit
-- Column: PP_Order_Node.WaitingTime
-- 2022-11-22T08:47:08.600Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53704
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Moving Time
-- Column: PP_Order_Node.MovingTime
-- 2022-11-22T08:47:08.605Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53705
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Setup Time Requiered
-- Column: PP_Order_Node.SetupTimeRequiered
-- 2022-11-22T08:47:08.610Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53706
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Setup Time Real
-- Column: PP_Order_Node.SetupTimeReal
-- 2022-11-22T08:47:08.614Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53707
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration Requiered
-- Column: PP_Order_Node.DurationRequiered
-- 2022-11-22T08:47:08.619Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53708
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration Real
-- Column: PP_Order_Node.DurationReal
-- 2022-11-22T08:47:08.623Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53709
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> DateStart
-- Column: PP_Order_Node.DateStart
-- 2022-11-22T08:47:08.628Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53710
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Projektabschluss
-- Column: PP_Order_Node.DateFinish
-- 2022-11-22T08:47:08.633Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53711
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> DateStartSchedule
-- Column: PP_Order_Node.DateStartSchedule
-- 2022-11-22T08:47:08.637Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53712
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> DateFinishSchedule
-- Column: PP_Order_Node.DateFinishSchedule
-- 2022-11-22T08:47:08.642Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53713
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Menge angefragt
-- Column: PP_Order_Node.QtyRequiered
-- 2022-11-22T08:47:08.646Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53714
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Gelieferte Menge
-- Column: PP_Order_Node.QtyDelivered
-- 2022-11-22T08:47:08.651Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53715
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Qty Reject
-- Column: PP_Order_Node.QtyReject
-- 2022-11-22T08:47:08.656Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53716
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> QtyScrap
-- Column: PP_Order_Node.QtyScrap
-- 2022-11-22T08:47:08.660Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53717
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Belegstatus
-- Column: PP_Order_Node.DocStatus
-- 2022-11-22T08:47:08.665Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56519
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Vorlage Arbeitsschritte (ID)
-- Column: PP_Order_Node.AD_WF_Node_Template_ID
-- 2022-11-22T08:47:08.670Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593771
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Manufacturing Activity Type
-- Column: PP_Order_Node.PP_Activity_Type
-- 2022-11-22T08:47:08.674Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=667614
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Always available to user
-- Column: PP_Order_Node.PP_AlwaysAvailableToUser
-- 2022-11-22T08:47:08.679Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2022-11-22 10:47:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707758
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Geschäftspartner
-- Column: PP_Order_Node.C_BPartner_ID
-- 2022-11-22T08:49:31.964Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53672
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Projektabschluss
-- Column: PP_Order_Node.DateFinish
-- 2022-11-22T08:49:31.973Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53711
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> DateFinishSchedule
-- Column: PP_Order_Node.DateFinishSchedule
-- 2022-11-22T08:49:31.980Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53713
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> DateStart
-- Column: PP_Order_Node.DateStart
-- 2022-11-22T08:49:31.987Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53710
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> DateStartSchedule
-- Column: PP_Order_Node.DateStartSchedule
-- 2022-11-22T08:49:31.994Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53712
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Beschreibung
-- Column: PP_Order_Node.Description
-- 2022-11-22T08:49:31.999Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53666
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration Real
-- Column: PP_Order_Node.DurationReal
-- 2022-11-22T08:49:32.004Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53709
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration Requiered
-- Column: PP_Order_Node.DurationRequiered
-- 2022-11-22T08:49:32.009Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53708
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Aktiv
-- Column: PP_Order_Node.IsActive
-- 2022-11-22T08:49:32.015Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53668
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Is Milestone
-- Column: PP_Order_Node.IsMilestone
-- 2022-11-22T08:49:32.019Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53670
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Is Subcontracting
-- Column: PP_Order_Node.IsSubcontracting
-- 2022-11-22T08:49:32.024Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53671
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Moving Time
-- Column: PP_Order_Node.MovingTime
-- 2022-11-22T08:49:32.028Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53705
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Overlap Units
-- Column: PP_Order_Node.OverlapUnits
-- 2022-11-22T08:49:32.033Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53699
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Produktionsaktivität
-- Column: PP_Order_Node.PP_Order_Node_ID
-- 2022-11-22T08:49:32.038Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53659
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Gelieferte Menge
-- Column: PP_Order_Node.QtyDelivered
-- 2022-11-22T08:49:32.043Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53715
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Qty Reject
-- Column: PP_Order_Node.QtyReject
-- 2022-11-22T08:49:32.047Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53716
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Menge angefragt
-- Column: PP_Order_Node.QtyRequiered
-- 2022-11-22T08:49:32.052Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53714
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> QtyScrap
-- Column: PP_Order_Node.QtyScrap
-- 2022-11-22T08:49:32.057Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53717
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Queuing Time
-- Column: PP_Order_Node.QueuingTime
-- 2022-11-22T08:49:32.062Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53700
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Setup Time
-- Column: PP_Order_Node.SetupTime
-- 2022-11-22T08:49:32.066Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53701
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Setup Time Real
-- Column: PP_Order_Node.SetupTimeReal
-- 2022-11-22T08:49:32.071Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53707
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Setup Time Requiered
-- Column: PP_Order_Node.SetupTimeRequiered
-- 2022-11-22T08:49:32.076Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53706
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Warte Zeit
-- Column: PP_Order_Node.WaitingTime
-- 2022-11-22T08:49:32.080Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53704
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Name
-- Column: PP_Order_Node.Name
-- 2022-11-22T08:49:32.085Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708144
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> User Instructions
-- Column: PP_Order_Node.PP_UserInstructions
-- 2022-11-22T08:49:32.089Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708143
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Produktionsressource
-- Column: PP_Order_Node.S_Resource_ID
-- 2022-11-22T08:49:32.094Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53669
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Duration
-- Column: PP_Order_Node.Duration
-- 2022-11-22T08:49:32.099Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53702
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Belegstatus
-- Column: PP_Order_Node.DocStatus
-- 2022-11-22T08:49:32.104Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56519
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Vorlage Arbeitsschritte (ID)
-- Column: PP_Order_Node.AD_WF_Node_Template_ID
-- 2022-11-22T08:49:32.109Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593771
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Always available to user
-- Column: PP_Order_Node.PP_AlwaysAvailableToUser
-- 2022-11-22T08:49:32.113Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-11-22 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707758
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Manufacturing Activity Type
-- Column: PP_Order_Node.PP_Activity_Type
-- 2022-11-22T08:50:04.738Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-11-22 10:50:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=667614
;

-- Field: Produktionsauftrag_OLD(53009,EE01) -> Knoten Arbeitsablauf(53036,EE01) -> Always available to user
-- Column: PP_Order_Node.PP_AlwaysAvailableToUser
-- 2022-11-22T08:50:04.743Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-11-22 10:50:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707758
;


