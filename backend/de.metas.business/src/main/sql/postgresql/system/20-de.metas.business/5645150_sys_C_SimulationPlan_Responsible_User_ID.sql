-- Column: C_SimulationPlan.AD_User_Responsible_ID
-- 2022-06-26T07:56:33.203735300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583527,542007,0,30,110,542173,'AD_User_Responsible_ID',TO_TIMESTAMP('2022-06-26 10:56:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.workflow',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verantwortlicher Benutzer',0,0,TO_TIMESTAMP('2022-06-26 10:56:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-26T07:56:33.210733900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583527 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-26T07:56:33.217682400Z
/* DDL */  select update_Column_Translation_From_AD_Element(542007) 
;

-- Column: C_SimulationPlan.AD_User_Responsible_ID
-- 2022-06-26T07:56:43.433709Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-06-26 10:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583527
;

-- 2022-06-26T07:56:45.811379600Z
/* DDL */ SELECT public.db_alter_table('C_SimulationPlan','ALTER TABLE public.C_SimulationPlan ADD COLUMN AD_User_Responsible_ID NUMERIC(10) NOT NULL')
;

-- 2022-06-26T07:56:45.820411700Z
ALTER TABLE C_SimulationPlan ADD CONSTRAINT ADUserResponsible_CSimulationPlan FOREIGN KEY (AD_User_Responsible_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: Simulation Plan -> Simulation Plan -> Verantwortlicher Benutzer
-- Column: C_SimulationPlan.AD_User_Responsible_ID
-- 2022-06-26T07:57:02.675888800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583527,700788,0,546390,TO_TIMESTAMP('2022-06-26 10:57:02','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Verantwortlicher Benutzer',TO_TIMESTAMP('2022-06-26 10:57:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-26T07:57:02.681883900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-26T07:57:02.691882100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542007) 
;

-- 2022-06-26T07:57:02.699877300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700788
;

-- 2022-06-26T07:57:02.700877200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700788)
;

-- UI Element: Simulation Plan -> Simulation Plan.Verantwortlicher Benutzer
-- Column: C_SimulationPlan.AD_User_Responsible_ID
-- 2022-06-26T07:57:29.757015600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700788,0,546390,609650,549388,'F',TO_TIMESTAMP('2022-06-26 10:57:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verantwortlicher Benutzer',30,0,0,TO_TIMESTAMP('2022-06-26 10:57:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Simulation Plan.Erstellt durch
-- Column: C_SimulationPlan.CreatedBy
-- 2022-06-26T07:57:55.250087Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-06-26 10:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609639
;

-- UI Element: Simulation Plan -> Simulation Plan.Verantwortlicher Benutzer
-- Column: C_SimulationPlan.AD_User_Responsible_ID
-- 2022-06-26T07:57:55.255088700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-06-26 10:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609650
;

-- UI Element: Simulation Plan -> Simulation Plan.Erstellt
-- Column: C_SimulationPlan.Created
-- 2022-06-26T07:57:55.261088700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-06-26 10:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609638
;

-- UI Element: Simulation Plan -> Simulation Plan.Verarbeitet
-- Column: C_SimulationPlan.Processed
-- 2022-06-26T07:57:55.266088700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-06-26 10:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609637
;

-- Column: C_SimulationPlan.AD_User_Responsible_ID
-- 2022-06-26T08:03:41.662555500Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-06-26 11:03:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583527
;

