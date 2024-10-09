
-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T10:36:41.379Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589285,582425,0,30,318,'ModCntr_Settings_ID',TO_TIMESTAMP('2024-10-09 13:36:41.257','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Einstellungen für modulare Verträge',0,0,TO_TIMESTAMP('2024-10-09 13:36:41.257','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-09T10:36:41.381Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589285 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T10:36:41.384Z
/* DDL */  select update_Column_Translation_From_AD_Element(582425)
;

-- 2024-10-09T10:36:42.209Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN ModCntr_Settings_ID NUMERIC(10)')
;

-- 2024-10-09T10:36:43.541Z
ALTER TABLE C_Invoice ADD CONSTRAINT ModCntrSettings_CInvoice FOREIGN KEY (ModCntr_Settings_ID) REFERENCES public.ModCntr_Settings DEFERRABLE INITIALLY DEFERRED
;




-- Field: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> Einstellungen für modulare Verträge
-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T11:01:57.654Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589285,731887,0,290,TO_TIMESTAMP('2024-10-09 14:01:57.53','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Einstellungen für modulare Verträge',TO_TIMESTAMP('2024-10-09 14:01:57.53','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-09T11:01:57.655Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731887 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T11:01:57.656Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582425)
;

-- 2024-10-09T11:01:57.659Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731887
;

-- 2024-10-09T11:01:57.660Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731887)
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> advanced edit -> 10 -> advanced edit.Einstellungen für modulare Verträge
-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T11:02:40.260Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731887,0,290,540218,626168,'F',TO_TIMESTAMP('2024-10-09 14:02:40.124','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Einstellungen für modulare Verträge',360,0,0,TO_TIMESTAMP('2024-10-09 14:02:40.124','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> advanced edit -> 10 -> advanced edit.Einstellungen für modulare Verträge
-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T11:02:48.824Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-10-09 14:02:48.824','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626168
;





-- Field: Rechnung_OLD(167,D) -> Rechnung(263,D) -> Einstellungen für modulare Verträge
-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T11:03:32.322Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589285,731888,0,263,TO_TIMESTAMP('2024-10-09 14:03:32.204','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Einstellungen für modulare Verträge',TO_TIMESTAMP('2024-10-09 14:03:32.204','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-09T11:03:32.324Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T11:03:32.325Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582425)
;

-- 2024-10-09T11:03:32.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731888
;

-- 2024-10-09T11:03:32.330Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731888)
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Einstellungen für modulare Verträge
-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T11:03:56.627Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731888,0,263,541214,626169,'F',TO_TIMESTAMP('2024-10-09 14:03:56.505','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Einstellungen für modulare Verträge',130,0,0,TO_TIMESTAMP('2024-10-09 14:03:56.505','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Einstellungen für modulare Verträge
-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T11:04:06.058Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-10-09 14:04:06.058','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626169
;










-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T14:45:47.200Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2024-10-09 17:45:47.2','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589285
;

-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T14:46:22.003Z
UPDATE AD_Column SET IsUpdateable='Y', ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2024-10-09 17:46:22.003','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589285
;


-- Column: C_Invoice.ModCntr_Settings_ID
-- 2024-10-09T15:21:36.624Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-09 18:21:36.624','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589285
;




