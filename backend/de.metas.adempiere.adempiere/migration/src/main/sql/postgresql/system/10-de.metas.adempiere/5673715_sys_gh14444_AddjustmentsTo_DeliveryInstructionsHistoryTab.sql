-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_ShipperTransportation_ID
-- 2023-01-27T19:43:21.863Z
UPDATE AD_Column SET IsKey='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-27 21:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585628
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.PlannedLoadedQuantity
-- 2023-01-27T19:43:45.324Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585667,581794,0,22,542287,'PlannedLoadedQuantity',TO_TIMESTAMP('2023-01-27 21:43:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Geplante Verlademenge',0,0,TO_TIMESTAMP('2023-01-27 21:43:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-27T19:43:45.325Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585667 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-27T19:43:45.328Z
/* DDL */  select update_Column_Translation_From_AD_Element(581794) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.PlannedLoadedQuantity
-- 2023-01-27T19:44:01.724Z
UPDATE AD_Column SET AD_Reference_ID=29, FieldLength=14,Updated=TO_TIMESTAMP('2023-01-27 21:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585667
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.PlannedDischargeQuantity
-- 2023-01-27T19:44:13.346Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585668,581795,0,29,542287,'PlannedDischargeQuantity',TO_TIMESTAMP('2023-01-27 21:44:13','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Geplante Entlademenge',0,0,TO_TIMESTAMP('2023-01-27 21:44:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-27T19:44:13.347Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585668 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-27T19:44:13.349Z
/* DDL */  select update_Column_Translation_From_AD_Element(581795) 
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Geplante Verlademenge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.PlannedLoadedQuantity
-- 2023-01-27T19:45:05.580Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585667,710760,0,546754,TO_TIMESTAMP('2023-01-27 21:45:05','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Geplante Verlademenge',TO_TIMESTAMP('2023-01-27 21:45:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T19:45:05.581Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710760 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T19:45:05.583Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581794) 
;

-- 2023-01-27T19:45:05.586Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710760
;

-- 2023-01-27T19:45:05.586Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710760)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Geplante Entlademenge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.PlannedDischargeQuantity
-- 2023-01-27T19:45:05.700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585668,710761,0,546754,TO_TIMESTAMP('2023-01-27 21:45:05','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Geplante Entlademenge',TO_TIMESTAMP('2023-01-27 21:45:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T19:45:05.702Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710761 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T19:45:05.703Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581795) 
;

-- 2023-01-27T19:45:05.705Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710761
;

-- 2023-01-27T19:45:05.706Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710761)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Geplante Verlademenge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.PlannedLoadedQuantity
-- 2023-01-27T19:45:38.556Z
UPDATE AD_UI_Element SET AD_Field_ID=710760, Name='Geplante Verlademenge',Updated=TO_TIMESTAMP('2023-01-27 21:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614869
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Geplante Entlademenge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.PlannedDischargeQuantity
-- 2023-01-27T19:45:54.798Z
UPDATE AD_UI_Element SET AD_Field_ID=710761, Name='Geplante Entlademenge',Updated=TO_TIMESTAMP('2023-01-27 21:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614870
;

