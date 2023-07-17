-- 2022-01-25T19:17:01.820Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579127,1720,0,40,632,'ImageURL',TO_TIMESTAMP('2022-01-25 20:17:00','YYYY-MM-DD HH24:MI:SS'),100,'N','URL of  image','D',0,120,'URL of image; The image is not stored in the database, but retrieved at runtime. The image can be a gif, jpeg or png.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bild-URL',0,0,TO_TIMESTAMP('2022-01-25 20:17:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-25T19:17:01.913Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579127 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-25T19:17:02.103Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1720) 
;

-- 2022-01-25T19:17:24.020Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product','ALTER TABLE public.C_BPartner_Product ADD COLUMN ImageURL VARCHAR(120)')
;

-- 2022-01-25T19:17:58.695Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579128,1920,0,40,632,'DescriptionURL',TO_TIMESTAMP('2022-01-25 20:17:57','YYYY-MM-DD HH24:MI:SS'),100,'N','URL für die Beschreibung','D',0,120,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibungs-URL',0,0,TO_TIMESTAMP('2022-01-25 20:17:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-25T19:17:58.785Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579128 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-25T19:17:58.960Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1920) 
;

-- 2022-01-25T19:18:25.433Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product','ALTER TABLE public.C_BPartner_Product ADD COLUMN DescriptionURL VARCHAR(120)')
;

-- 2022-01-25T19:20:42.911Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579127,677945,0,562,TO_TIMESTAMP('2022-01-25 20:20:42','YYYY-MM-DD HH24:MI:SS'),100,'URL of  image',120,'D','URL of image; The image is not stored in the database, but retrieved at runtime. The image can be a gif, jpeg or png.','Y','N','N','N','N','N','N','N','Bild-URL',TO_TIMESTAMP('2022-01-25 20:20:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-25T19:20:42.993Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677945 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-25T19:20:43.059Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1720) 
;

-- 2022-01-25T19:20:43.148Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677945
;

-- 2022-01-25T19:20:43.208Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677945)
;

-- 2022-01-25T19:20:44.062Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579128,677946,0,562,TO_TIMESTAMP('2022-01-25 20:20:43','YYYY-MM-DD HH24:MI:SS'),100,'URL für die Beschreibung',120,'D','Y','N','N','N','N','N','N','N','Beschreibungs-URL',TO_TIMESTAMP('2022-01-25 20:20:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-25T19:20:44.130Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677946 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-25T19:20:44.193Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1920) 
;

-- 2022-01-25T19:20:44.267Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677946
;

-- 2022-01-25T19:20:44.327Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677946)
;

-- 2022-01-25T19:23:06.601Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677945,0,562,1000021,600074,'F',TO_TIMESTAMP('2022-01-25 20:23:05','YYYY-MM-DD HH24:MI:SS'),100,'URL of  image','URL of image; The image is not stored in the database, but retrieved at runtime. The image can be a gif, jpeg or png.','Y','N','N','Y','N','N','N',0,'Bild-URL',240,0,0,TO_TIMESTAMP('2022-01-25 20:23:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-25T19:23:52.939Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=51,Updated=TO_TIMESTAMP('2022-01-25 20:23:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=600074
;

-- 2022-01-25T19:24:36.803Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677946,0,562,1000021,600075,'F',TO_TIMESTAMP('2022-01-25 20:24:36','YYYY-MM-DD HH24:MI:SS'),100,'URL für die Beschreibung','Y','N','N','Y','N','N','N',0,'Beschreibungs-URL',52,0,0,TO_TIMESTAMP('2022-01-25 20:24:36','YYYY-MM-DD HH24:MI:SS'),100)
;

