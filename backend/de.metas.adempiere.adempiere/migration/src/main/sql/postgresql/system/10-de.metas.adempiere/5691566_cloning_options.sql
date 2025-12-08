-- Name: AD_Table_CloningStrategy
-- 2023-06-13T20:38:57.600Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541756,TO_TIMESTAMP('2023-06-13 23:38:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Table_CloningStrategy',TO_TIMESTAMP('2023-06-13 23:38:57','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-06-13T20:38:57.602Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541756 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_Table_CloningStrategy
-- Value: S
-- ValueName: Skip
-- 2023-06-13T20:39:27.913Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541756,543496,TO_TIMESTAMP('2023-06-13 23:39:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Skip when cloning as a child',TO_TIMESTAMP('2023-06-13 23:39:27','YYYY-MM-DD HH24:MI:SS'),100,'S','Skip')
;

-- 2023-06-13T20:39:27.914Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543496 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Table_CloningStrategy
-- Value: C
-- ValueName: Clone
-- 2023-06-13T20:40:22.228Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541756,543497,TO_TIMESTAMP('2023-06-13 23:40:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Include when cloning as a child',TO_TIMESTAMP('2023-06-13 23:40:22','YYYY-MM-DD HH24:MI:SS'),100,'C','Clone')
;

-- 2023-06-13T20:40:22.230Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543497 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Table_CloningStrategy
-- Value: C
-- ValueName: Clone
-- 2023-06-13T20:42:46.487Z
UPDATE AD_Ref_List SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543497
;

-- 2023-06-13T20:42:46.488Z
UPDATE AD_Ref_List_Trl trl SET Name='Allow including when cloning as a child' WHERE AD_Ref_List_ID=543497 AND AD_Language='en_US'
;


update AD_Ref_List set value='A', valueName='AllowCloning' where AD_Ref_List_ID=543497;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:10.782Z
UPDATE AD_Ref_List_Trl SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543497
;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:12.555Z
UPDATE AD_Ref_List_Trl SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543497
;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:15.580Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-13 23:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543497
;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:18.419Z
UPDATE AD_Ref_List_Trl SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:44:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543497
;

-- Reference Item: AD_Table_CloningStrategy -> A_AllowCloning
-- 2023-06-13T20:44:21.463Z
UPDATE AD_Ref_List_Trl SET Name='Allow including when cloning as a child',Updated=TO_TIMESTAMP('2023-06-13 23:44:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543497
;

-- Column: AD_Table.CloningStrategy
-- 2023-06-13T20:44:43.933Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586813,582434,0,17,541756,100,'CloningStrategy',TO_TIMESTAMP('2023-06-13 23:44:43','YYYY-MM-DD HH24:MI:SS'),100,'N','A','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cloning Strategy',0,0,TO_TIMESTAMP('2023-06-13 23:44:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-13T20:44:43.935Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586813 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T20:44:44.442Z
/* DDL */  select update_Column_Translation_From_AD_Element(582434)
;

-- 2023-06-13T20:44:45.219Z
/* DDL */ SELECT public.db_alter_table('AD_Table','ALTER TABLE public.AD_Table ADD COLUMN CloningStrategy CHAR(1) DEFAULT ''A'' NOT NULL')
;

