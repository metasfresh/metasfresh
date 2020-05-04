-- 2019-09-20T06:31:40.077Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568794,2039,0,30,540961,'Bill_BPartner_ID',TO_TIMESTAMP('2019-09-20 08:31:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Geschäftspartner für die Rechnungsstellung','U',10,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Rechnungspartner',0,0,TO_TIMESTAMP('2019-09-20 08:31:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-20T06:31:40.081Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568794 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-20T06:31:40.110Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2039) 
;

-- 2019-09-20T06:31:59.188Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=192,Updated=TO_TIMESTAMP('2019-09-20 08:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568794
;

-- 2019-09-20T06:33:23.448Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541045,TO_TIMESTAMP('2019-09-20 08:33:23','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Bill_BPartner',TO_TIMESTAMP('2019-09-20 08:33:23','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-09-20T06:33:23.450Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541045 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-09-20T06:33:39.189Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2902,2893,0,541045,291,TO_TIMESTAMP('2019-09-20 08:33:39','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Y',TO_TIMESTAMP('2019-09-20 08:33:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:33:48.166Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=541045, AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2019-09-20 08:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568794
;

-- 2019-09-20T06:33:55.350Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project_User','ALTER TABLE public.C_Project_User ADD COLUMN Bill_BPartner_ID NUMERIC(10)')
;

-- 2019-09-20T06:33:55.362Z
-- URL zum Konzept
ALTER TABLE C_Project_User ADD CONSTRAINT BillBPartner_CProjectUser FOREIGN KEY (Bill_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2019-09-20T06:36:15.257Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568795,2040,0,18,159,540961,540229,'Bill_Location_ID',TO_TIMESTAMP('2019-09-20 08:36:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Standort des Geschäftspartners für die Rechnungsstellung','U',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Rechnungsstandort',0,0,TO_TIMESTAMP('2019-09-20 08:36:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-20T06:36:15.259Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568795 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-20T06:36:15.260Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2040) 
;

-- 2019-09-20T06:36:37.186Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project_User','ALTER TABLE public.C_Project_User ADD COLUMN Bill_Location_ID NUMERIC(10)')
;

-- 2019-09-20T06:36:37.199Z
-- URL zum Konzept
ALTER TABLE C_Project_User ADD CONSTRAINT BillLocation_CProjectUser FOREIGN KEY (Bill_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- 2019-09-20T06:37:22.056Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568796,2041,0,18,110,540961,191,'Bill_User_ID',TO_TIMESTAMP('2019-09-20 08:37:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Ansprechpartner des Geschäftspartners für die Rechnungsstellung','U',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Rechnungskontakt',0,0,TO_TIMESTAMP('2019-09-20 08:37:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-20T06:37:22.057Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568796 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-20T06:37:22.058Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2041) 
;

-- 2019-09-20T06:37:56.431Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568794,586851,0,541867,TO_TIMESTAMP('2019-09-20 08:37:56','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner für die Rechnungsstellung',10,'U','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','N','N','N','N','N','Rechnungspartner',TO_TIMESTAMP('2019-09-20 08:37:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:37:56.434Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586851 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T06:37:56.443Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2039) 
;

-- 2019-09-20T06:37:56.462Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586851
;

-- 2019-09-20T06:37:56.465Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586851)
;

-- 2019-09-20T06:37:56.563Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568795,586852,0,541867,TO_TIMESTAMP('2019-09-20 08:37:56','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'U','Y','N','N','N','N','N','N','N','Rechnungsstandort',TO_TIMESTAMP('2019-09-20 08:37:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:37:56.564Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586852 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T06:37:56.567Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2040) 
;

-- 2019-09-20T06:37:56.572Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586852
;

-- 2019-09-20T06:37:56.573Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586852)
;

-- 2019-09-20T06:37:56.633Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568796,586853,0,541867,TO_TIMESTAMP('2019-09-20 08:37:56','YYYY-MM-DD HH24:MI:SS'),100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung',10,'U','Y','N','N','N','N','N','N','N','Rechnungskontakt',TO_TIMESTAMP('2019-09-20 08:37:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:37:56.634Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586853 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T06:37:56.637Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2041) 
;

-- 2019-09-20T06:37:56.642Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586853
;

-- 2019-09-20T06:37:56.642Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586853)
;

-- 2019-09-20T06:38:06.375Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-20 08:38:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586851
;

-- 2019-09-20T06:38:08.340Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-20 08:38:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586852
;

-- 2019-09-20T06:38:10.245Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-20 08:38:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586853
;

-- 2019-09-20T06:38:26.333Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586851,0,541867,542757,562103,'F',TO_TIMESTAMP('2019-09-20 08:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnungspartner_',40,0,0,TO_TIMESTAMP('2019-09-20 08:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:38:38.789Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586852,0,541867,542757,562104,'F',TO_TIMESTAMP('2019-09-20 08:38:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnungsstandort',50,0,0,TO_TIMESTAMP('2019-09-20 08:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:38:46.566Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586853,0,541867,542757,562105,'F',TO_TIMESTAMP('2019-09-20 08:38:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnungskontakt',60,0,0,TO_TIMESTAMP('2019-09-20 08:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:39:11.028Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_order','Bill_User_ID','NUMERIC(10)',null,null)
;

-- 2019-09-20T06:41:03.900Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_order','Bill_User_ID','NUMERIC(10)',null,null)
;

-- 2019-09-20T06:41:20.808Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project_User','ALTER TABLE public.C_Project_User ADD COLUMN Bill_User_ID NUMERIC(10)')
;

-- 2019-09-20T06:41:20.814Z
-- URL zum Konzept
ALTER TABLE C_Project_User ADD CONSTRAINT BillUser_CProjectUser FOREIGN KEY (Bill_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2019-09-20T06:42:01.277Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-09-20 08:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562103
;

-- 2019-09-20T06:42:01.279Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-09-20 08:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562104
;

-- 2019-09-20T06:42:01.282Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-09-20 08:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562105
;

-- 2019-09-20T06:42:29.733Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568794,586854,0,541870,TO_TIMESTAMP('2019-09-20 08:42:29','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner für die Rechnungsstellung',10,'U','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','N','N','N','N','N','Rechnungspartner',TO_TIMESTAMP('2019-09-20 08:42:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:42:29.734Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586854 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T06:42:29.737Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2039) 
;

-- 2019-09-20T06:42:29.741Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586854
;

-- 2019-09-20T06:42:29.742Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586854)
;

-- 2019-09-20T06:42:29.801Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568795,586855,0,541870,TO_TIMESTAMP('2019-09-20 08:42:29','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'U','Y','N','N','N','N','N','N','N','Rechnungsstandort',TO_TIMESTAMP('2019-09-20 08:42:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:42:29.802Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586855 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T06:42:29.804Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2040) 
;

-- 2019-09-20T06:42:29.807Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586855
;

-- 2019-09-20T06:42:29.807Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586855)
;

-- 2019-09-20T06:42:29.869Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568796,586856,0,541870,TO_TIMESTAMP('2019-09-20 08:42:29','YYYY-MM-DD HH24:MI:SS'),100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung',10,'U','Y','N','N','N','N','N','N','N','Rechnungskontakt',TO_TIMESTAMP('2019-09-20 08:42:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:42:29.870Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586856 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T06:42:29.872Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2041) 
;

-- 2019-09-20T06:42:29.874Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586856
;

-- 2019-09-20T06:42:29.874Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586856)
;

-- 2019-09-20T06:42:57.093Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-20 08:42:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586854
;

-- 2019-09-20T06:42:58.836Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-20 08:42:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586855
;

-- 2019-09-20T06:43:00.742Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-20 08:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586856
;

-- 2019-09-20T06:43:11.626Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586854,0,541870,542759,562106,'F',TO_TIMESTAMP('2019-09-20 08:43:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnungspartner_Bill_BPartner_ID_Rechnungspartner',50,0,0,TO_TIMESTAMP('2019-09-20 08:43:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:43:21.245Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586855,0,541870,542759,562107,'F',TO_TIMESTAMP('2019-09-20 08:43:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnungsstandort',60,0,0,TO_TIMESTAMP('2019-09-20 08:43:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:43:27.715Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586856,0,541870,542759,562108,'F',TO_TIMESTAMP('2019-09-20 08:43:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnungskontakt_Bill_User_ID_Rechnungskontakt',70,0,0,TO_TIMESTAMP('2019-09-20 08:43:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:44:03.705Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-09-20 08:44:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562106
;

-- 2019-09-20T06:44:03.710Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-09-20 08:44:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562107
;

-- 2019-09-20T06:44:03.714Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-09-20 08:44:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562108
;

-- 2019-09-20T06:46:33.232Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577104,0,'IsAccommodationBooking',TO_TIMESTAMP('2019-09-20 08:46:33','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Accomodation Booking','Accomodation Booking',TO_TIMESTAMP('2019-09-20 08:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:46:33.236Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577104 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-20T06:46:43.476Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Hotel buchen', PrintName='Hotel buchen',Updated=TO_TIMESTAMP('2019-09-20 08:46:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577104 AND AD_Language='de_DE'
;

-- 2019-09-20T06:46:43.481Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577104,'de_DE') 
;

-- 2019-09-20T06:46:43.507Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(577104,'de_DE') 
;

-- 2019-09-20T06:46:43.509Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsAccommodationBooking', Name='Hotel buchen', Description=NULL, Help=NULL WHERE AD_Element_ID=577104
;

-- 2019-09-20T06:46:43.510Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsAccommodationBooking', Name='Hotel buchen', Description=NULL, Help=NULL, AD_Element_ID=577104 WHERE UPPER(ColumnName)='ISACCOMMODATIONBOOKING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-20T06:46:43.511Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsAccommodationBooking', Name='Hotel buchen', Description=NULL, Help=NULL WHERE AD_Element_ID=577104 AND IsCentrallyMaintained='Y'
;

-- 2019-09-20T06:46:43.512Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Hotel buchen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577104) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577104)
;

-- 2019-09-20T06:46:43.639Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Hotel buchen', Name='Hotel buchen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577104)
;

-- 2019-09-20T06:46:43.643Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Hotel buchen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577104
;

-- 2019-09-20T06:46:43.644Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Hotel buchen', Description=NULL, Help=NULL WHERE AD_Element_ID = 577104
;

-- 2019-09-20T06:46:43.645Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Hotel buchen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577104
;

-- 2019-09-20T06:46:46.512Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Hotel buchen', PrintName='Hotel buchen',Updated=TO_TIMESTAMP('2019-09-20 08:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577104 AND AD_Language='de_CH'
;

-- 2019-09-20T06:46:46.514Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577104,'de_CH') 
;

-- 2019-09-20T06:46:57.917Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568797,577104,0,20,540961,'IsAccommodationBooking',TO_TIMESTAMP('2019-09-20 08:46:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N','U',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Hotel buchen',0,0,TO_TIMESTAMP('2019-09-20 08:46:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-20T06:46:57.919Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568797 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-20T06:46:57.921Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(577104) 
;

-- 2019-09-20T06:47:00.252Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project_User','ALTER TABLE public.C_Project_User ADD COLUMN IsAccommodationBooking CHAR(1) DEFAULT ''N'' CHECK (IsAccommodationBooking IN (''Y'',''N'')) NOT NULL')
;

-- 2019-09-20T06:47:21.063Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568797,586857,0,541867,0,TO_TIMESTAMP('2019-09-20 08:47:20','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Hotel buchen',130,60,0,1,1,TO_TIMESTAMP('2019-09-20 08:47:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:47:21.064Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T06:47:21.066Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577104) 
;

-- 2019-09-20T06:47:21.068Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586857
;

-- 2019-09-20T06:47:21.069Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586857)
;

-- 2019-09-20T06:47:38.167Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586857,0,541867,542757,562109,'F',TO_TIMESTAMP('2019-09-20 08:47:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Hotel buchen',70,0,0,TO_TIMESTAMP('2019-09-20 08:47:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T06:47:44.795Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-09-20 08:47:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562109
;

-- 2019-09-20T06:47:44.799Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-09-20 08:47:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562103
;

-- 2019-09-20T06:47:44.802Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-09-20 08:47:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562104
;

-- 2019-09-20T06:47:44.805Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-09-20 08:47:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562105
;

-- 2019-09-20T07:38:44.548Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568798,2706,0,19,540961,'R_Status_ID',TO_TIMESTAMP('2019-09-20 09:38:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Request Status','U',10,'Status if the request (open, closed, investigating, ..)','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Status',0,0,TO_TIMESTAMP('2019-09-20 09:38:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-20T07:38:44.550Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568798 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-20T07:38:44.552Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2706) 
;

-- 2019-09-20T07:38:46.151Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project_User','ALTER TABLE public.C_Project_User ADD COLUMN R_Status_ID NUMERIC(10)')
;

-- 2019-09-20T07:38:46.158Z
-- URL zum Konzept
ALTER TABLE C_Project_User ADD CONSTRAINT RStatus_CProjectUser FOREIGN KEY (R_Status_ID) REFERENCES public.R_Status DEFERRABLE INITIALLY DEFERRED
;

-- 2019-09-20T07:39:00.815Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568798,586858,0,541867,0,TO_TIMESTAMP('2019-09-20 09:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Request Status',0,'U','Status if the request (open, closed, investigating, ..)',0,'Y','Y','Y','N','N','N','N','N','Status',140,70,0,1,1,TO_TIMESTAMP('2019-09-20 09:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T07:39:00.818Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T07:39:00.823Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2706) 
;

-- 2019-09-20T07:39:00.842Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586858
;

-- 2019-09-20T07:39:00.845Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586858)
;

-- 2019-09-20T07:39:10.815Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586858,0,541867,542757,562110,'F',TO_TIMESTAMP('2019-09-20 09:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Status_R',80,0,0,TO_TIMESTAMP('2019-09-20 09:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T07:39:17.725Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-09-20 09:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562110
;

-- 2019-09-20T07:39:17.730Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-09-20 09:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562103
;

-- 2019-09-20T07:39:17.735Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-09-20 09:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562104
;

-- 2019-09-20T07:39:17.739Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-09-20 09:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562105
;

-- 2019-09-20T07:46:01.049Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540453,'EXISTS (SELECT * from R_RequestType rt 
INNER JOIN R_StatusCategory sc ON (rt.R_StatusCategory_ID=sc.R_StatusCategory_ID)
where R_Status.R_StatusCategory_ID=sc.R_StatusCategory_ID
AND rt.R_RequestType_ID=@R_RequestType_ID@)',TO_TIMESTAMP('2019-09-20 09:46:00','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','R_Status Seminar','S',TO_TIMESTAMP('2019-09-20 09:46:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T07:46:42.617Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT * from R_RequestType rt 
INNER JOIN R_StatusCategory sc ON (rt.R_StatusCategory_ID=sc.R_StatusCategory_ID)
where R_Status.R_StatusCategory_ID=sc.R_StatusCategory_ID
AND rt.R_RequestType_ID=540011)',Updated=TO_TIMESTAMP('2019-09-20 09:46:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540453
;

-- 2019-09-20T07:47:28.472Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540453,Updated=TO_TIMESTAMP('2019-09-20 09:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568798
;

-- 2019-09-20T07:48:51.181Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT * from R_StatusCategory sc 
where sc.R_StatusCategory_ID=540011)
',Updated=TO_TIMESTAMP('2019-09-20 09:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540453
;

-- 2019-09-20T07:49:40.392Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='R_StatusCategory_ID=540011
',Updated=TO_TIMESTAMP('2019-09-20 09:49:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540453
;

-- 2019-09-20T07:51:06.491Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='case when @C_Project_Role@=''ST'' then R_StatusCategory_ID=540011 end
',Updated=TO_TIMESTAMP('2019-09-20 09:51:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540453
;

-- 2019-09-20T07:51:50.036Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='case when c_project_user.C_Project_Role=''ST'' then R_StatusCategory_ID=540011 end
',Updated=TO_TIMESTAMP('2019-09-20 09:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540453
;

-- 2019-09-20T07:53:34.048Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='case when (select C_Project_Role from c_project_user where c_project_user_id = @C_Project_User_ID@)=''ST'' then R_StatusCategory_ID=540011 end
',Updated=TO_TIMESTAMP('2019-09-20 09:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540453
;

-- 2019-09-20T07:54:27.930Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='case when (select C_Project_Role from c_project_user where c_project_user_id = @C_Project_User_ID@)=''ST'' then R_StatusCategory_ID=540011 
when (select C_Project_Role from c_project_user where c_project_user_id = @C_Project_User_ID@)=''SD'' then R_StatusCategory_ID=540012 
end
',Updated=TO_TIMESTAMP('2019-09-20 09:54:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540453
;

-- 2019-09-20T07:56:22.813Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541047,TO_TIMESTAMP('2019-09-20 09:56:22','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','R_Status - Name',TO_TIMESTAMP('2019-09-20 09:56:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-09-20T07:56:22.815Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541047 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-09-20T07:56:41.438Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,Updated,UpdatedBy) VALUES (0,13570,13562,0,541047,776,TO_TIMESTAMP('2019-09-20 09:56:41','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','seqno',TO_TIMESTAMP('2019-09-20 09:56:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T07:56:49.551Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541047,Updated=TO_TIMESTAMP('2019-09-20 09:56:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568798
;

-- 2019-09-20T07:58:02.938Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='case when (select C_Project_Role from c_project_user where c_project_user_id = @C_Project_User_ID@)=''ST'' then R_StatusCategory_ID=540011 
when (select C_Project_Role from c_project_user where c_project_user_id = @C_Project_User_ID@)=''SD'' then R_StatusCategory_ID=540012 
when (select C_Project_Role from c_project_user where c_project_user_id = @C_Project_User_ID@)=''SH'' then R_StatusCategory_ID=540013 
end
',Updated=TO_TIMESTAMP('2019-09-20 09:58:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540453
;

-- 2019-09-20T08:07:03.076Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568797,586859,0,541870,TO_TIMESTAMP('2019-09-20 10:07:03','YYYY-MM-DD HH24:MI:SS'),100,1,'U','Y','N','N','N','N','N','N','N','Hotel buchen',TO_TIMESTAMP('2019-09-20 10:07:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T08:07:03.077Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T08:07:03.081Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577104) 
;

-- 2019-09-20T08:07:03.085Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586859
;

-- 2019-09-20T08:07:03.086Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586859)
;

-- 2019-09-20T08:07:03.144Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568798,586860,0,541870,TO_TIMESTAMP('2019-09-20 10:07:03','YYYY-MM-DD HH24:MI:SS'),100,'Request Status',10,'U','Status if the request (open, closed, investigating, ..)','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2019-09-20 10:07:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T08:07:03.146Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-20T08:07:03.149Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2706) 
;

-- 2019-09-20T08:07:03.157Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586860
;

-- 2019-09-20T08:07:03.157Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586860)
;

-- 2019-09-20T08:07:13.457Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-20 10:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586860
;

-- 2019-09-20T08:07:27.639Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586860,0,541870,542759,562111,'F',TO_TIMESTAMP('2019-09-20 10:07:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Status_R',80,0,0,TO_TIMESTAMP('2019-09-20 10:07:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-20T08:07:33.976Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-09-20 10:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562111
;

-- 2019-09-20T08:07:33.981Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-09-20 10:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562106
;

-- 2019-09-20T08:07:33.985Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-09-20 10:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562107
;

-- 2019-09-20T08:07:33.989Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-09-20 10:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562108
;

