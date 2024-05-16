-- 2022-02-18T09:24:55.320Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579326,542184,0,15,540524,'DatePromised_Override',TO_TIMESTAMP('2022-02-18 10:24:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Zugesagter Termin für diesen Auftrag','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zugesagter Termin abw.',0,0,TO_TIMESTAMP('2022-02-18 10:24:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-18T09:24:55.392Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579326 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-18T09:24:55.567Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(542184) 
;

-- 2022-02-18T09:25:19.118Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN DatePromised_Override TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-02-18T09:27:35.293Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579327,542422,0,15,540524,'DatePromised_Effective','COALESCE(M_ReceiptSchedule.DatePromised_Override, M_ReceiptSchedule.DatePromised)',TO_TIMESTAMP('2022-02-18 10:27:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Zugesagter Termin für diesen Auftrag','de.metas.inoutcandidate',0,7,'B','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N',0,'Zuges. Termin (eff.)',0,0,TO_TIMESTAMP('2022-02-18 10:27:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-18T09:27:35.364Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579327 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-18T09:27:35.514Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(542422) 
;

-- 2022-02-18T09:32:30.126Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579326,680646,0,540526,TO_TIMESTAMP('2022-02-18 10:32:29','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag',7,'D','Y','N','N','N','N','N','N','N','Zugesagter Termin abw.',TO_TIMESTAMP('2022-02-18 10:32:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-18T09:32:30.199Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=680646 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-02-18T09:32:30.264Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542184) 
;

-- 2022-02-18T09:32:30.341Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=680646
;

-- 2022-02-18T09:32:30.419Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(680646)
;

-- 2022-02-18T09:37:10.243Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,680646,0,540526,540133,601367,'F',TO_TIMESTAMP('2022-02-18 10:37:09','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Y','N','N','Y','N','N','N',0,'Zugesagter Termin abw.',32,0,0,TO_TIMESTAMP('2022-02-18 10:37:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-18T09:40:53.669Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-02-18 10:40:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680646
;

-- 2022-02-18T09:41:36.593Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,680646,0,540526,540133,601368,'F',TO_TIMESTAMP('2022-02-18 10:41:35','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Y','N','N','Y','N','N','N',0,'Zugesagter Termin abw.',50,0,0,TO_TIMESTAMP('2022-02-18 10:41:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-18T09:41:47.851Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=34,Updated=TO_TIMESTAMP('2022-02-18 10:41:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601368
;

-- 2022-02-18T09:42:11.590Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-02-18 10:42:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680646
;

-- 2022-02-18T09:55:12.102Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='COALESCE(M_ReceiptSchedule.DatePromised_Override, M_ReceiptSchedule.MovementDate)',Updated=TO_TIMESTAMP('2022-02-18 10:55:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579327
;

-- 2022-02-18T10:01:57.991Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579327,680647,0,540526,0,TO_TIMESTAMP('2022-02-18 11:01:56','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag',0,'D',0,'Y','Y','Y','N','N','N','N','N','Zuges. Termin (eff.)',0,210,0,1,1,TO_TIMESTAMP('2022-02-18 11:01:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-18T10:01:58.058Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=680647 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-02-18T10:01:58.124Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542422) 
;

-- 2022-02-18T10:01:58.194Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=680647
;

-- 2022-02-18T10:01:58.254Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(680647)
;

-- 2022-02-18T10:02:11.278Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLength=7, IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-02-18 11:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680647
;

-- 2022-02-18T10:02:31.431Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-02-18 11:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680646
;

-- 2022-02-18T10:03:06.770Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_Field_ID=680647, Name='Zuges. Termin (eff.)',Updated=TO_TIMESTAMP('2022-02-18 11:03:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601368
;

-- 2022-02-18T10:07:33.405Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-02-18 11:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680646
;

-- 2022-02-18T10:10:28.620Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2022-02-18 11:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549491
;

