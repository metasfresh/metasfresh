-- 2019-10-10T08:39:12.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569088,577135,0,10,208,'GTIN',TO_TIMESTAMP('2019-10-10 11:39:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',100,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','GTIN',0,0,TO_TIMESTAMP('2019-10-10 11:39:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-10T08:39:12.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569088 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-10T08:39:12.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577135) 
;

-- 2019-10-10T08:39:30.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN GTIN VARCHAR(100)')
;

-- 2019-10-10T08:41:25.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569088,589539,0,180,0,TO_TIMESTAMP('2019-10-10 11:41:25','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','GTIN',430,460,0,1,1,TO_TIMESTAMP('2019-10-10 11:41:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-10T08:41:25.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589539 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-10T08:41:25.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577135) 
;

-- 2019-10-10T08:41:25.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589539
;

-- 2019-10-10T08:41:25.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589539)
;

-- 2019-10-10T08:42:03.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2019-10-10 11:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000092
;

-- 2019-10-10T08:42:34.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,589539,0,180,563218,1000015,'F',TO_TIMESTAMP('2019-10-10 11:42:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'GTIN',10,0,0,TO_TIMESTAMP('2019-10-10 11:42:34','YYYY-MM-DD HH24:MI:SS'),100)
;

