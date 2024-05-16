-- Create new column in C_Order table
-- 2021-10-05T07:37:51.696Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577420,579045,0,18,541252,259,'C_BPartner_Pharmacy_ID',TO_TIMESTAMP('2021-10-05 09:37:51','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Apotheke',0,0,TO_TIMESTAMP('2021-10-05 09:37:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-05T07:37:51.698Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577420 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-05T07:37:51.716Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579045)
;




-- Display the new field in Standard Sales Order window advanced edit view
-- 2021-10-05T07:41:15.952Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577420,661937,0,186,0,TO_TIMESTAMP('2021-10-05 09:41:15','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Apotheke',740,740,0,1,1,TO_TIMESTAMP('2021-10-05 09:41:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-05T07:41:15.953Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=661937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-10-05T07:41:15.954Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579045)
;

-- 2021-10-05T07:41:15.959Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=661937
;

-- 2021-10-05T07:41:15.960Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(661937)
;

-- 2021-10-05T07:42:06.068Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,661937,0,186,540499,592085,'F',TO_TIMESTAMP('2021-10-05 09:42:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Apotheke',440,0,0,TO_TIMESTAMP('2021-10-05 09:42:06','YYYY-MM-DD HH24:MI:SS'),100)
;



-- define validation rule for the new field
-- 2021-10-05T08:15:01.613Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540560,'C_BPartner_AlbertaRole.C_BPartner_ID = @C_BPartner_ID@ and albertarole = ''PH''',TO_TIMESTAMP('2021-10-05 10:15:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','PharmacyValRule','S',TO_TIMESTAMP('2021-10-05 10:15:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-05T08:16:22.998Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540560,Updated=TO_TIMESTAMP('2021-10-05 10:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577420
;

-- 2021-10-05T09:30:16.418Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS(SELECT 1 from C_BPartner_AlbertaRole where albertarole = ''PH'')',Updated=TO_TIMESTAMP('2021-10-05 11:30:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540560
;