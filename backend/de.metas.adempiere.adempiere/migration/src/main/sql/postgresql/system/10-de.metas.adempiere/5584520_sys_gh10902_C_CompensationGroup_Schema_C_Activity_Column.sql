-- 2021-03-31T13:53:58.986723600Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573326,1005,0,30,540940,'C_Activity_ID',TO_TIMESTAMP('2021-03-31 16:53:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Kostenstelle','de.metas.order',0,10,'Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenstelle',0,0,TO_TIMESTAMP('2021-03-31 16:53:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-31T13:53:59.206836100Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573326 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-31T13:53:59.360947100Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(1005) 
;

-- 2021-03-31T13:55:26.848331300Z
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_Schema','ALTER TABLE public.C_CompensationGroup_Schema ADD COLUMN C_Activity_ID NUMERIC(10)')
;

-- 2021-03-31T13:55:26.923617300Z
-- #298 changing anz. stellen
ALTER TABLE C_CompensationGroup_Schema ADD CONSTRAINT CActivity_CCompensationGroupSchema FOREIGN KEY (C_Activity_ID) REFERENCES public.C_Activity DEFERRABLE INITIALLY DEFERRED
;




-- 2021-03-31T14:04:18.008206500Z
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573326,642297,0,541041,0,TO_TIMESTAMP('2021-03-31 17:04:17','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',0,'D','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','Y','N','N','N','N','N','Kostenstelle',30,30,0,1,1,TO_TIMESTAMP('2021-03-31 17:04:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-31T14:04:18.059781200Z
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=642297 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-31T14:04:18.120569400Z
-- #298 changing anz. stellen
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2021-03-31T14:04:18.344144900Z
-- #298 changing anz. stellen
DELETE FROM AD_Element_Link WHERE AD_Field_ID=642297
;

-- 2021-03-31T14:04:18.394071500Z
-- #298 changing anz. stellen
/* DDL */ select AD_Element_Link_Create_Missing_Field(642297)
;

-- 2021-03-31T14:04:54.580460800Z
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,642297,0,541041,541467,582904,'F',TO_TIMESTAMP('2021-03-31 17:04:54','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','N','N',0,'Kostenstelle',20,0,0,TO_TIMESTAMP('2021-03-31 17:04:54','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2021-04-02T14:11:38.063Z
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-04-02 17:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=582904
;

-- 2021-04-02T14:11:38.529Z
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-04-02 17:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550999
;

-- 2021-04-02T14:11:38.773Z
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-04-02 17:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551000
;


