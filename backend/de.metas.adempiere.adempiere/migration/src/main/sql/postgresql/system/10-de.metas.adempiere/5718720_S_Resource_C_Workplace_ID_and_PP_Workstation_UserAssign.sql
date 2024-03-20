-- Column: S_Resource.C_Workplace_ID
-- Column: S_Resource.C_Workplace_ID
-- 2024-03-07T10:12:44.524Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587979,582772,0,30,487,'XX','C_Workplace_ID',TO_TIMESTAMP('2024-03-07 12:12:44','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Arbeitsplatz',0,0,TO_TIMESTAMP('2024-03-07 12:12:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:12:44.530Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587979 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:12:44.534Z
/* DDL */  select update_Column_Translation_From_AD_Element(582772) 
;

-- 2024-03-07T10:12:45.345Z
/* DDL */ SELECT public.db_alter_table('S_Resource','ALTER TABLE public.S_Resource ADD COLUMN C_Workplace_ID NUMERIC(10)')
;

-- 2024-03-07T10:12:45.520Z
ALTER TABLE S_Resource ADD CONSTRAINT CWorkplace_SResource FOREIGN KEY (C_Workplace_ID) REFERENCES public.C_Workplace DEFERRABLE INITIALLY DEFERRED
;

-- Field: Produktions Ressource -> Produktions Ressource -> Arbeitsplatz
-- Column: S_Resource.C_Workplace_ID
-- Field: Produktions Ressource(53004,EE01) -> Produktions Ressource(53015,EE01) -> Arbeitsplatz
-- Column: S_Resource.C_Workplace_ID
-- 2024-03-07T10:13:37.152Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587979,726570,0,53015,TO_TIMESTAMP('2024-03-07 12:13:37','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Arbeitsplatz',TO_TIMESTAMP('2024-03-07 12:13:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:13:37.154Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726570 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-07T10:13:37.156Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582772) 
;

-- 2024-03-07T10:13:37.163Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726570
;

-- 2024-03-07T10:13:37.164Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726570)
;

-- Field: Ressource -> Ressource -> Arbeitsplatz
-- Column: S_Resource.C_Workplace_ID
-- Field: Ressource(236,D) -> Ressource(414,D) -> Arbeitsplatz
-- Column: S_Resource.C_Workplace_ID
-- 2024-03-07T10:15:45.585Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587979,726571,0,414,TO_TIMESTAMP('2024-03-07 12:15:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Arbeitsplatz',TO_TIMESTAMP('2024-03-07 12:15:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:15:45.588Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726571 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-07T10:15:45.590Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582772) 
;

-- 2024-03-07T10:15:45.596Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726571
;

-- 2024-03-07T10:15:45.597Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726571)
;

-- UI Element: Ressource -> Ressource.Arbeitsplatz
-- Column: S_Resource.C_Workplace_ID
-- UI Element: Ressource(236,D) -> Ressource(414,D) -> main -> 10 -> default.Arbeitsplatz
-- Column: S_Resource.C_Workplace_ID
-- 2024-03-07T10:16:22.358Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726571,0,414,543924,623748,'F',TO_TIMESTAMP('2024-03-07 12:16:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitsplatz',70,0,0,TO_TIMESTAMP('2024-03-07 12:16:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktions Ressource -> Produktions Ressource.Arbeitsplatz
-- Column: S_Resource.C_Workplace_ID
-- UI Element: Produktions Ressource(53004,EE01) -> Produktions Ressource(53015,EE01) -> main -> 10 -> default.Arbeitsplatz
-- Column: S_Resource.C_Workplace_ID
-- 2024-03-07T10:16:55.778Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726570,0,53015,540357,623749,'F',TO_TIMESTAMP('2024-03-07 12:16:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitsplatz',50,0,0,TO_TIMESTAMP('2024-03-07 12:16:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Ressource -> Ressource -> Planning Horizon
-- Column: S_Resource.PlanningHorizon
-- Field: Ressource(236,D) -> Ressource(414,D) -> Planning Horizon
-- Column: S_Resource.PlanningHorizon
-- 2024-03-07T10:19:15.639Z
UPDATE AD_Field SET DisplayLogic='@IsManufacturingResource@=''Y'' & @ManufacturingResourceType@=''PT''',Updated=TO_TIMESTAMP('2024-03-07 12:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=614858
;






update AD_Ref_List set ValueName='ProductionLine' where AD_Reference_ID=53223 and value='PL';
update AD_Ref_List set ValueName='Plant' where AD_Reference_ID=53223 and value='PT';
update AD_Ref_List set ValueName='WorkCenter' where AD_Reference_ID=53223 and value='WC';
update AD_Ref_List set ValueName='WorkStation' where AD_Reference_ID=53223 and value='WS';






-- Table: PP_Workstation_UserAssign
-- Table: PP_Workstation_UserAssign
-- 2024-03-07T10:55:36Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('2',0,0,0,542398,'A','N',TO_TIMESTAMP('2024-03-07 12:55:35','YYYY-MM-DD HH24:MI:SS'),100,'A','D','N','Y','N','N','Y','N','N','N','N','N',0,'Workstation User Assignment','NP','L','PP_Workstation_UserAssign','DTI',TO_TIMESTAMP('2024-03-07 12:55:35','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-03-07T10:55:36.002Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542398 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-03-07T10:55:36.105Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556334,TO_TIMESTAMP('2024-03-07 12:55:36','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table PP_Workstation_UserAssign',1,'Y','N','Y','Y','PP_Workstation_UserAssign','N',1000000,TO_TIMESTAMP('2024-03-07 12:55:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:55:36.112Z
CREATE SEQUENCE PP_WORKSTATION_USERASSIGN_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: PP_Workstation_UserAssign.AD_Client_ID
-- Column: PP_Workstation_UserAssign.AD_Client_ID
-- 2024-03-07T10:55:41.594Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587980,102,0,19,542398,'AD_Client_ID',TO_TIMESTAMP('2024-03-07 12:55:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-03-07 12:55:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:55:41.599Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587980 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:55:41.604Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: PP_Workstation_UserAssign.AD_Org_ID
-- Column: PP_Workstation_UserAssign.AD_Org_ID
-- 2024-03-07T10:55:42.333Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587981,113,0,30,542398,'AD_Org_ID',TO_TIMESTAMP('2024-03-07 12:55:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-03-07 12:55:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:55:42.335Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587981 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:55:42.339Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: PP_Workstation_UserAssign.Created
-- Column: PP_Workstation_UserAssign.Created
-- 2024-03-07T10:55:43.019Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587982,245,0,16,542398,'Created',TO_TIMESTAMP('2024-03-07 12:55:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-03-07 12:55:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:55:43.021Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587982 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:55:43.043Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: PP_Workstation_UserAssign.CreatedBy
-- Column: PP_Workstation_UserAssign.CreatedBy
-- 2024-03-07T10:55:43.981Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587983,246,0,18,110,542398,'CreatedBy',TO_TIMESTAMP('2024-03-07 12:55:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-03-07 12:55:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:55:43.983Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587983 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:55:43.985Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: PP_Workstation_UserAssign.IsActive
-- Column: PP_Workstation_UserAssign.IsActive
-- 2024-03-07T10:55:44.642Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587984,348,0,20,542398,'IsActive',TO_TIMESTAMP('2024-03-07 12:55:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-03-07 12:55:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:55:44.643Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587984 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:55:44.646Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: PP_Workstation_UserAssign.Updated
-- Column: PP_Workstation_UserAssign.Updated
-- 2024-03-07T10:55:45.301Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587985,607,0,16,542398,'Updated',TO_TIMESTAMP('2024-03-07 12:55:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-03-07 12:55:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:55:45.303Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587985 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:55:45.307Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: PP_Workstation_UserAssign.UpdatedBy
-- Column: PP_Workstation_UserAssign.UpdatedBy
-- 2024-03-07T10:55:45.998Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587986,608,0,18,110,542398,'UpdatedBy',TO_TIMESTAMP('2024-03-07 12:55:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-03-07 12:55:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:55:46.001Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587986 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:55:46.005Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-03-07T10:55:46.540Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583021,0,'PP_Workstation_UserAssign_ID',TO_TIMESTAMP('2024-03-07 12:55:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Workstation User Assignment','Workstation User Assignment',TO_TIMESTAMP('2024-03-07 12:55:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:55:46.542Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583021 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PP_Workstation_UserAssign.PP_Workstation_UserAssign_ID
-- Column: PP_Workstation_UserAssign.PP_Workstation_UserAssign_ID
-- 2024-03-07T10:55:47.193Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587987,583021,0,13,542398,'PP_Workstation_UserAssign_ID',TO_TIMESTAMP('2024-03-07 12:55:46','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Workstation User Assignment',0,0,TO_TIMESTAMP('2024-03-07 12:55:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:55:47.195Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587987 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:55:47.199Z
/* DDL */  select update_Column_Translation_From_AD_Element(583021) 
;

-- Column: PP_Workstation_UserAssign.AD_User_ID
-- Column: PP_Workstation_UserAssign.AD_User_ID
-- 2024-03-07T10:56:02.659Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587988,138,0,30,542398,'XX','AD_User_ID',TO_TIMESTAMP('2024-03-07 12:56:02','YYYY-MM-DD HH24:MI:SS'),100,'N','User within the system - Internal or Business Partner Contact','D',0,10,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Ansprechpartner',0,0,TO_TIMESTAMP('2024-03-07 12:56:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:56:02.662Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587988 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:56:02.666Z
/* DDL */  select update_Column_Translation_From_AD_Element(138) 
;

-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- 2024-03-07T10:56:48.354Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587989,583018,0,30,541855,542398,540669,'XX','WorkStation_ID',TO_TIMESTAMP('2024-03-07 12:56:48','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Arbeitsstation',0,0,TO_TIMESTAMP('2024-03-07 12:56:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-07T10:56:48.357Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587989 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-07T10:56:48.359Z
/* DDL */  select update_Column_Translation_From_AD_Element(583018) 
;

-- 2024-03-07T10:56:54.502Z
/* DDL */ CREATE TABLE public.PP_Workstation_UserAssign (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_User_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, PP_Workstation_UserAssign_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, WorkStation_ID NUMERIC(10) NOT NULL, CONSTRAINT ADUser_PPWorkstationUserAssign FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PP_Workstation_UserAssign_Key PRIMARY KEY (PP_Workstation_UserAssign_ID), CONSTRAINT WorkStation_PPWorkstationUserAssign FOREIGN KEY (WorkStation_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED)
;

-- 2024-03-07T10:57:31.847Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540789,0,542398,TO_TIMESTAMP('2024-03-07 12:57:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','PP_Workstation_UserAssign_UQ','N',TO_TIMESTAMP('2024-03-07 12:57:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:57:31.849Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540789 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-03-07T10:57:39.742Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587988,541397,540789,0,TO_TIMESTAMP('2024-03-07 12:57:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2024-03-07 12:57:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:57:48.980Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587989,541398,540789,0,TO_TIMESTAMP('2024-03-07 12:57:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2024-03-07 12:57:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:57:53.757Z
CREATE UNIQUE INDEX PP_Workstation_UserAssign_UQ ON PP_Workstation_UserAssign (AD_User_ID,WorkStation_ID)
;

-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- 2024-03-07T10:58:19.484Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-03-07 12:58:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587989
;

-- Column: PP_Workstation_UserAssign.AD_User_ID
-- Column: PP_Workstation_UserAssign.AD_User_ID
-- 2024-03-07T10:58:29.377Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-03-07 12:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587988
;

-- Window: Workstation User Assignment, InternalName=null
-- Window: Workstation User Assignment, InternalName=null
-- 2024-03-07T10:59:00.478Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583021,0,541789,TO_TIMESTAMP('2024-03-07 12:59:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Workstation User Assignment','N',TO_TIMESTAMP('2024-03-07 12:59:00','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-03-07T10:59:00.480Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541789 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-03-07T10:59:00.483Z
/* DDL */  select update_window_translation_from_ad_element(583021) 
;

-- 2024-03-07T10:59:00.486Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541789
;

-- 2024-03-07T10:59:00.487Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541789)
;

-- Tab: Workstation User Assignment -> Workstation User Assignment
-- Table: PP_Workstation_UserAssign
-- Tab: Workstation User Assignment(541789,D) -> Workstation User Assignment
-- Table: PP_Workstation_UserAssign
-- 2024-03-07T10:59:15.254Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583021,0,547484,542398,541789,'Y',TO_TIMESTAMP('2024-03-07 12:59:15','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','PP_Workstation_UserAssign','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Workstation User Assignment','N',10,0,TO_TIMESTAMP('2024-03-07 12:59:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:59:15.255Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547484 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-03-07T10:59:15.257Z
/* DDL */  select update_tab_translation_from_ad_element(583021) 
;

-- 2024-03-07T10:59:15.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547484)
;

-- Window: Workstation User Assignment, InternalName=PP_Workstation_UserAssign
-- Window: Workstation User Assignment, InternalName=PP_Workstation_UserAssign
-- 2024-03-07T10:59:23.476Z
UPDATE AD_Window SET InternalName='PP_Workstation_UserAssign',Updated=TO_TIMESTAMP('2024-03-07 12:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541789
;

-- Field: Workstation User Assignment -> Workstation User Assignment -> Mandant
-- Column: PP_Workstation_UserAssign.AD_Client_ID
-- Field: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> Mandant
-- Column: PP_Workstation_UserAssign.AD_Client_ID
-- 2024-03-07T10:59:27.006Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587980,726572,0,547484,TO_TIMESTAMP('2024-03-07 12:59:26','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-03-07 12:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:59:27.009Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726572 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-07T10:59:27.011Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-03-07T10:59:27.197Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726572
;

-- 2024-03-07T10:59:27.199Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726572)
;

-- Field: Workstation User Assignment -> Workstation User Assignment -> Sektion
-- Column: PP_Workstation_UserAssign.AD_Org_ID
-- Field: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> Sektion
-- Column: PP_Workstation_UserAssign.AD_Org_ID
-- 2024-03-07T10:59:27.304Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587981,726573,0,547484,TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:59:27.305Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726573 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-07T10:59:27.307Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-03-07T10:59:27.385Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726573
;

-- 2024-03-07T10:59:27.387Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726573)
;

-- Field: Workstation User Assignment -> Workstation User Assignment -> Aktiv
-- Column: PP_Workstation_UserAssign.IsActive
-- Field: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> Aktiv
-- Column: PP_Workstation_UserAssign.IsActive
-- 2024-03-07T10:59:27.499Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587984,726574,0,547484,TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:59:27.500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726574 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-07T10:59:27.502Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-03-07T10:59:27.563Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726574
;

-- 2024-03-07T10:59:27.564Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726574)
;

-- Field: Workstation User Assignment -> Workstation User Assignment -> Workstation User Assignment
-- Column: PP_Workstation_UserAssign.PP_Workstation_UserAssign_ID
-- Field: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> Workstation User Assignment
-- Column: PP_Workstation_UserAssign.PP_Workstation_UserAssign_ID
-- 2024-03-07T10:59:27.669Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587987,726575,0,547484,TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Workstation User Assignment',TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:59:27.672Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726575 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-07T10:59:27.673Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583021) 
;

-- 2024-03-07T10:59:27.678Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726575
;

-- 2024-03-07T10:59:27.679Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726575)
;

-- Field: Workstation User Assignment -> Workstation User Assignment -> Ansprechpartner
-- Column: PP_Workstation_UserAssign.AD_User_ID
-- Field: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> Ansprechpartner
-- Column: PP_Workstation_UserAssign.AD_User_ID
-- 2024-03-07T10:59:27.779Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587988,726576,0,547484,TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',10,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:59:27.780Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726576 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-07T10:59:27.782Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2024-03-07T10:59:27.790Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726576
;

-- 2024-03-07T10:59:27.791Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726576)
;

-- Field: Workstation User Assignment -> Workstation User Assignment -> Arbeitsstation
-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- Field: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> Arbeitsstation
-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- 2024-03-07T10:59:27.900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587989,726577,0,547484,TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Arbeitsstation',TO_TIMESTAMP('2024-03-07 12:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-07T10:59:27.901Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726577 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-07T10:59:27.903Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583018) 
;

-- 2024-03-07T10:59:27.906Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726577
;

-- 2024-03-07T10:59:27.907Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726577)
;

-- Tab: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D)
-- UI Section: main
-- 2024-03-07T10:59:36.428Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547484,546063,TO_TIMESTAMP('2024-03-07 12:59:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-07 12:59:36','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-03-07T10:59:36.430Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546063 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main
-- UI Column: 10
-- 2024-03-07T10:59:36.535Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547405,546063,TO_TIMESTAMP('2024-03-07 12:59:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-07 12:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main
-- UI Column: 20
-- 2024-03-07T10:59:36.633Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547406,546063,TO_TIMESTAMP('2024-03-07 12:59:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-03-07 12:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main -> 10
-- UI Element Group: default
-- 2024-03-07T10:59:36.733Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547405,551691,TO_TIMESTAMP('2024-03-07 12:59:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-03-07 12:59:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Workstation User Assignment -> Workstation User Assignment.Arbeitsstation
-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- UI Element: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main -> 10 -> default.Arbeitsstation
-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- 2024-03-07T11:00:00.644Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726577,0,547484,551691,623750,'F',TO_TIMESTAMP('2024-03-07 13:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitsstation',10,0,0,TO_TIMESTAMP('2024-03-07 13:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Workstation User Assignment -> Workstation User Assignment.Ansprechpartner
-- Column: PP_Workstation_UserAssign.AD_User_ID
-- UI Element: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main -> 10 -> default.Ansprechpartner
-- Column: PP_Workstation_UserAssign.AD_User_ID
-- 2024-03-07T11:00:10.855Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726576,0,547484,551691,623751,'F',TO_TIMESTAMP('2024-03-07 13:00:09','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','Ansprechpartner',20,0,0,TO_TIMESTAMP('2024-03-07 13:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main -> 20
-- UI Element Group: flags
-- 2024-03-07T11:00:22.940Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547406,551692,TO_TIMESTAMP('2024-03-07 13:00:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-03-07 13:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Workstation User Assignment -> Workstation User Assignment.Aktiv
-- Column: PP_Workstation_UserAssign.IsActive
-- UI Element: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main -> 20 -> flags.Aktiv
-- Column: PP_Workstation_UserAssign.IsActive
-- 2024-03-07T11:00:30.416Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726574,0,547484,551692,623752,'F',TO_TIMESTAMP('2024-03-07 13:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-03-07 13:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Workstation User Assignment -> Workstation User Assignment.Arbeitsstation
-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- UI Element: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main -> 10 -> default.Arbeitsstation
-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- 2024-03-07T11:00:56.459Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-03-07 13:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623750
;

-- UI Element: Workstation User Assignment -> Workstation User Assignment.Ansprechpartner
-- Column: PP_Workstation_UserAssign.AD_User_ID
-- UI Element: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main -> 10 -> default.Ansprechpartner
-- Column: PP_Workstation_UserAssign.AD_User_ID
-- 2024-03-07T11:00:56.471Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-03-07 13:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623751
;

-- UI Element: Workstation User Assignment -> Workstation User Assignment.Aktiv
-- Column: PP_Workstation_UserAssign.IsActive
-- UI Element: Workstation User Assignment(541789,D) -> Workstation User Assignment(547484,D) -> main -> 20 -> flags.Aktiv
-- Column: PP_Workstation_UserAssign.IsActive
-- 2024-03-07T11:00:56.480Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-03-07 13:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623752
;

-- Table: PP_Workstation_UserAssign
-- Table: PP_Workstation_UserAssign
-- 2024-03-07T11:59:43.837Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2024-03-07 13:59:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542398
;

-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- Column: PP_Workstation_UserAssign.WorkStation_ID
-- 2024-03-07T13:38:54.817Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-03-07 15:38:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587989
;

