-- 2019-09-19T19:47:00.499Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET OrderByClause='name',Updated=TO_TIMESTAMP('2019-09-19 21:47:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541035
;

-- 2019-09-19T19:51:09.834Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-09-19 21:51:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;

-- 2019-09-19T19:53:12.710Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Help='startdatetime',Updated=TO_TIMESTAMP('2019-09-19 21:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577047 AND AD_Language='de_CH'
;

-- 2019-09-19T19:53:12.750Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577047,'de_CH') 
;

-- 2019-09-19T19:53:17.547Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='startdatetime',Updated=TO_TIMESTAMP('2019-09-19 21:53:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577047
;

-- 2019-09-19T19:53:17.552Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='startdatetime', Name='Seminarbeginn', Description=NULL, Help=NULL WHERE AD_Element_ID=577047
;

-- 2019-09-19T19:53:17.553Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='startdatetime', Name='Seminarbeginn', Description=NULL, Help=NULL, AD_Element_ID=577047 WHERE UPPER(ColumnName)='STARTDATETIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-19T19:53:17.555Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='startdatetime', Name='Seminarbeginn', Description=NULL, Help=NULL WHERE AD_Element_ID=577047 AND IsCentrallyMaintained='Y'
;

-- 2019-09-19T19:53:41.937Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='enddatetime',Updated=TO_TIMESTAMP('2019-09-19 21:53:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577046
;

-- 2019-09-19T19:53:41.939Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='enddatetime', Name='Seminarende', Description=NULL, Help=NULL WHERE AD_Element_ID=577046
;

-- 2019-09-19T19:53:41.940Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='enddatetime', Name='Seminarende', Description=NULL, Help=NULL, AD_Element_ID=577046 WHERE UPPER(ColumnName)='ENDDATETIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-19T19:53:41.941Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='enddatetime', Name='Seminarende', Description=NULL, Help=NULL WHERE AD_Element_ID=577046 AND IsCentrallyMaintained='Y'
;

-- 2019-09-19T19:54:09.248Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568792,577047,0,16,203,'startdatetime',TO_TIMESTAMP('2019-09-19 21:54:09','YYYY-MM-DD HH24:MI:SS'),100,'N','U',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Seminarbeginn',0,0,TO_TIMESTAMP('2019-09-19 21:54:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-19T19:54:09.250Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568792 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-19T19:54:09.252Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(577047) 
;

-- 2019-09-19T19:54:36.444Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568793,577046,0,16,203,'enddatetime',TO_TIMESTAMP('2019-09-19 21:54:36','YYYY-MM-DD HH24:MI:SS'),100,'N','U',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Seminarende',0,0,TO_TIMESTAMP('2019-09-19 21:54:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-19T19:54:36.446Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568793 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-19T19:54:36.447Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(577046) 
;

-- 2019-09-19T19:54:39.487Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN enddatetime TIMESTAMP WITH TIME ZONE')
;

-- 2019-09-19T19:55:07.589Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN startdatetime TIMESTAMP WITH TIME ZONE')
;

-- 2019-09-19T19:55:37.878Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568792,586849,0,541865,0,TO_TIMESTAMP('2019-09-19 21:55:37','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Seminarbeginn',380,370,0,1,1,TO_TIMESTAMP('2019-09-19 21:55:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T19:55:37.880Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586849 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-19T19:55:37.885Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577047) 
;

-- 2019-09-19T19:55:37.893Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586849
;

-- 2019-09-19T19:55:37.896Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586849)
;

-- 2019-09-19T19:55:43.978Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568793,586850,0,541865,0,TO_TIMESTAMP('2019-09-19 21:55:43','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Seminarende',390,380,0,1,1,TO_TIMESTAMP('2019-09-19 21:55:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T19:55:43.980Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586850 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-19T19:55:43.982Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577046) 
;

-- 2019-09-19T19:55:43.985Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586850
;

-- 2019-09-19T19:55:43.985Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586850)
;

-- 2019-09-19T19:55:54.177Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Datum des Auftragseingangs', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.', Name='Datum Auftragseingang',Updated=TO_TIMESTAMP('2019-09-19 21:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583496
;

-- 2019-09-19T19:55:54.181Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1556) 
;

-- 2019-09-19T19:55:54.191Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583496
;

-- 2019-09-19T19:55:54.192Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583496)
;

-- 2019-09-19T19:56:08.460Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Finish or (planned) completion date', Help='Dieses Datum gibt das erwartete oder tatsächliche Projektende an', Name='Projektabschluss',Updated=TO_TIMESTAMP('2019-09-19 21:56:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583497
;

-- 2019-09-19T19:56:08.461Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1557) 
;

-- 2019-09-19T19:56:08.466Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583497
;

-- 2019-09-19T19:56:08.466Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583497)
;

-- 2019-09-19T19:56:40.804Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_Field_ID=586849, Name='Seminarbeginn',Updated=TO_TIMESTAMP('2019-09-19 21:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560707
;

-- 2019-09-19T19:56:51.273Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_Field_ID=586850, Name='Seminarende',Updated=TO_TIMESTAMP('2019-09-19 21:56:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560708
;

-- 2019-09-19T19:57:26.918Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-09-19 21:57:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568792
;

-- 2019-09-19T19:57:32.135Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-09-19 21:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568793
;

-- 2019-09-19T20:01:23.499Z
-- URL zum Konzept
UPDATE AD_Column SET IsRangeFilter='Y',Updated=TO_TIMESTAMP('2019-09-19 22:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568792
;

