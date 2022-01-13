-- validation rule for patient
-- 2021-07-09T13:14:08.669Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540544,'C_BPartner.C_BPartner_ID = (select bp.c_bpartner_id   from c_bpartner_AlbertaRole bpr   where bpr.c_bpartner_id = @C_BPartner_ID@   and bpr.albertarole = ''PD''',TO_TIMESTAMP('2021-07-09 16:14:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BPartner_Patient','S',TO_TIMESTAMP('2021-07-09 16:14:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-09T13:19:01.196Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='C_BPartner.C_BPartner_ID = (select bp.c_bpartner_id   from c_bpartner_AlbertaRole bpr   where bpr.c_bpartner_id = @C_BPartner_ID@   and bpr.albertarole = ''PD'')',Updated=TO_TIMESTAMP('2021-07-09 16:19:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540544
;

-- 2021-07-09T13:21:26.088Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540544,Updated=TO_TIMESTAMP('2021-07-09 16:21:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573558
;

-- 2021-07-09T14:46:14.511Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540088,Updated=TO_TIMESTAMP('2021-07-09 17:46:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573558
;

-- 2021-07-09T14:48:10.551Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540544,Updated=TO_TIMESTAMP('2021-07-09 17:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573558
;

-- 2021-07-09T14:48:59.186Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS  (SELECT  1 from C_BPartner_AlbertaRole where C_BPartner_AlbertaRole.C_BPartner_ID=C_BPartner.C_BPartner_ID and C_BPartner_AlbertaRole.albertarole=''PD'')',Updated=TO_TIMESTAMP('2021-07-09 17:48:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540544
;

-- 2021-07-09T14:49:46.739Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS  (SELECT  1 from C_BPartner_AlbertaRole where C_BPartner_AlbertaRole.C_BPartner_ID=C_BPartner.C_BPartner_ID and C_BPartner_AlbertaRole.albertarole=''PT'')',Updated=TO_TIMESTAMP('2021-07-09 17:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540544
;

-- Validation rule for pharmacy

-- 2021-07-12T07:37:18.604Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540545,'EXISTS  (SELECT  1
from C_BPartner_AlbertaRole
where C_BPartner_AlbertaRole.C_BPartner_ID=C_BPartner.C_BPartner_ID and C_BPartner_AlbertaRole.albertarole=''PH'')',TO_TIMESTAMP('2021-07-12 10:37:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BPartner_Pharmacy','S',TO_TIMESTAMP('2021-07-12 10:37:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-12T07:38:01.472Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540545,Updated=TO_TIMESTAMP('2021-07-12 10:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573569
;

-- Validation rule for doctor
-- 2021-07-12T07:41:14.440Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540546,'EXISTS  (SELECT  1
from C_BPartner_AlbertaRole
where C_BPartner_AlbertaRole.C_BPartner_ID=C_BPartner.C_BPartner_ID and C_BPartner_AlbertaRole.albertarole=''PD'')',TO_TIMESTAMP('2021-07-12 10:41:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BPartner_Doctor','S',TO_TIMESTAMP('2021-07-12 10:41:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-12T07:42:15.832Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540546,Updated=TO_TIMESTAMP('2021-07-12 10:42:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573568
;

-- Create C_Order column
-- 2021-07-12T07:45:18.814Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574994,558,0,19,541622,'C_Order_ID',TO_TIMESTAMP('2021-07-12 10:45:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftrag','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag',0,0,TO_TIMESTAMP('2021-07-12 10:45:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-12T07:45:18.855Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574994 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-12T07:45:18.982Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(558)
;

-- 2021-07-12T07:58:58.587Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574994
;

-- 2021-07-12T07:58:58.819Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574994
;

-- 2021-07-12T08:00:36.079Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574995,558,0,30,290,541622,'C_Order_ID',TO_TIMESTAMP('2021-07-12 11:00:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftrag','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag',0,0,TO_TIMESTAMP('2021-07-12 11:00:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-12T08:00:36.117Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574995 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-12T08:00:36.240Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(558)
;






-- Create Validation rule for C_Order columns
-- 2021-07-12T08:34:07.734Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540547,'EXISTS( SELECT 1 from C_Order where C_Order.issotrx = ''Y'' C_Order.C_BPartner_ID = Alberta_PrescriptionRequest.C_BPartner_Patient_ID)',TO_TIMESTAMP('2021-07-12 10:34:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Order_Patient','S',TO_TIMESTAMP('2021-07-12 10:34:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-12T08:34:29.244Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540547,Updated=TO_TIMESTAMP('2021-07-12 10:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574995
;


-- Create field C_Order
-- 2021-07-12T08:37:45.130Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574995,650267,0,543839,0,TO_TIMESTAMP('2021-07-12 10:37:45','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',0,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.',0,'Y','Y','Y','N','N','N','N','N','Auftrag',40,40,0,1,1,TO_TIMESTAMP('2021-07-12 10:37:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-12T08:37:45.131Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650267 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-12T08:37:45.132Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558)
;

-- 2021-07-12T08:37:45.151Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650267
;

-- 2021-07-12T08:37:45.152Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(650267)
;

-- Display C_Order field

-- 2021-07-12T08:39:11.289Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543775,546196,TO_TIMESTAMP('2021-07-12 10:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','order',15,TO_TIMESTAMP('2021-07-12 10:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-12T08:39:25.797Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650267,0,543839,546196,587049,'F',TO_TIMESTAMP('2021-07-12 10:39:25','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','N','N','N',0,'Auftrag',10,0,0,TO_TIMESTAMP('2021-07-12 10:39:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-12T08:41:16.345Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS( SELECT 1 from C_Order where C_Order.issotrx = ''Y'' and C_Order.C_BPartner_ID = Alberta_PrescriptionRequest.C_BPartner_Patient_ID)',Updated=TO_TIMESTAMP('2021-07-12 10:41:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540547
;

-- Fix Validation rule for C_Order_ID
-- 2021-07-12T08:43:57.829Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS( SELECT 1 from C_Order where C_Order.issotrx = ''Y'' and C_Order.C_BPartner_ID = @C_BPartner_Patient_ID@)',Updated=TO_TIMESTAMP('2021-07-12 10:43:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540547
;

-- Create virtual column C_BPartner_Payer_ID
-- 2021-07-12T08:57:03.990Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-12 10:57:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579182 AND AD_Language='de_CH'
;

-- 2021-07-12T08:57:03.993Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579182,'de_CH')
;

-- 2021-07-12T08:57:06.437Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-12 10:57:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579182 AND AD_Language='de_DE'
;

-- 2021-07-12T08:57:06.440Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579182,'de_DE')
;

-- 2021-07-12T08:57:06.465Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579182,'de_DE')
;

-- 2021-07-12T08:57:10.385Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-12 10:57:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579182 AND AD_Language='en_US'
;

-- 2021-07-12T08:57:10.389Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579182,'en_US')
;

-- 2021-07-12T08:57:28.935Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kostenträger', PrintName='Kostenträger',Updated=TO_TIMESTAMP('2021-07-12 10:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579182 AND AD_Language='nl_NL'
;

-- 2021-07-12T08:57:28.938Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579182,'nl_NL')
;

-- 2021-07-12T09:00:53.236Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575014,579182,0,19,541622,'C_BPartner_Payer_ID','(SELECT C_BPartner_Payer_ID from C_BPartner_AlbertaPatient where C_BPartner_ID  = C_BPartner_AlbertaPatient=C_BPartner_Patient_ID)',TO_TIMESTAMP('2021-07-12 11:00:53','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.healthcare.alberta',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Kostenträger',0,0,TO_TIMESTAMP('2021-07-12 11:00:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-12T09:00:53.237Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575014 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-12T09:00:53.238Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579182)
;

-- Create field for C_BPartner_Payer_ID
-- 2021-07-12T09:01:44.569Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575014,650281,0,543839,0,TO_TIMESTAMP('2021-07-12 11:01:44','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Kostenträger',50,50,0,1,1,TO_TIMESTAMP('2021-07-12 11:01:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-12T09:01:44.570Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650281 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-12T09:01:44.570Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579182)
;

-- 2021-07-12T09:01:44.572Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650281
;

-- 2021-07-12T09:01:44.573Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(650281)
;

-- Display field C_BPartner_Payer_ID
-- 2021-07-12T09:03:07.326Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650281,0,543839,546196,587059,'F',TO_TIMESTAMP('2021-07-12 11:03:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kostenträger',20,0,0,TO_TIMESTAMP('2021-07-12 11:03:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-12T09:04:57.854Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(SELECT C_BPartner_Payer_ID from C_BPartner_AlbertaPatient where C_BPartner_ID  = C_BPartner_AlbertaPatient.C_BPartner_Patient_ID)',Updated=TO_TIMESTAMP('2021-07-12 11:04:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575014
;

-- 2021-07-12T09:10:55.251Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(SELECT C_BPartner_Payer_ID from C_BPartner_AlbertaPatient where C_BPartner_AlbertaPatient.C_BPartner_ID  = Alberta_PrescriptionRequest.C_BPartner_Patient_ID)',Updated=TO_TIMESTAMP('2021-07-12 11:10:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575014
;

-- 2021-07-12T09:12:08.311Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541252, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-07-12 11:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575014
;

-- AD_SQLColumn_SourceTableColumn
-- 2021-07-12T09:16:40.502Z
-- URL zum Konzept
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,575014,0,540044,541622,TO_TIMESTAMP('2021-07-12 11:16:40','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',573879,573906,541644,TO_TIMESTAMP('2021-07-12 11:16:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Fix C_Order_ID validation rule


-- 2021-07-12T09:29:54.545Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS( SELECT 1 from C_Order where C_Order.issotrx = ''Y'' and C_Order.C_BPartner_ID = Alberta_PrescriptionRequest.C_BPartner_Patient_ID)',Updated=TO_TIMESTAMP('2021-07-12 11:29:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540547
;

-- 2021-07-12T09:36:22.711Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS( SELECT 1 from C_Order where C_Order.issotrx = ''Y'' and C_Order.C_BPartner_ID = @C_BPartner_Patient_ID@)',Updated=TO_TIMESTAMP('2021-07-12 11:36:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540547
;

-- 2021-07-12T09:39:12.448Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540235,Updated=TO_TIMESTAMP('2021-07-12 11:39:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574995
;

-- 2021-07-12T09:39:34.587Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540547,Updated=TO_TIMESTAMP('2021-07-12 11:39:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574995
;

-- Update C_Order_ID validation rule
-- 2021-07-12T09:56:42.940Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='C_Order.C_Order_ID in ( SELECT C_Order.C_Order_ID from C_Order where C_Order.issotrx = ''Y'' and C_Order.C_BPartner_ID = @C_BPartner_Patient_ID@)',Updated=TO_TIMESTAMP('2021-07-12 11:56:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540547
;

-- 2021-07-12T12:34:55.130Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='C_Order.C_Order_ID in ( SELECT C_Order.C_Order_ID from C_Order where C_Order.issotrx = ''Y'' and C_Order.C_BPartner_ID = @C_BPartner_Patient_ID/-1@)',Updated=TO_TIMESTAMP('2021-07-12 14:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540547
;
