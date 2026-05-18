-- Column: PP_Product_BOM.CostStandard
-- 2026-05-18T17:22:17.834Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592547,240,0,37,53018,'XX','CostStandard','(getCurrentCost(                (PP_Product_BOM.M_Product_ID),                (select p.C_UOM_ID from M_Product p where p.M_Product_ID = PP_Product_BOM.M_Product_ID),                now()::date,                (select ci.C_AcctSchema1_ID from AD_ClientInfo ci where PP_Product_BOM.AD_Client_ID = ci.AD_Client_ID),                (select M_CostElement_ID from M_CostElement where IsActive=''Y'' and CostElementType=''M'' and CostingMethod=''S''),                (PP_Product_BOM.AD_Client_ID),                (PP_Product_BOM.AD_Org_ID)        ))',TO_TIMESTAMP('2026-05-18 17:22:17.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Standardkosten','EE01',0,22,'Standard- oder Plankosten.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Standardkosten',0,0,TO_TIMESTAMP('2026-05-18 17:22:17.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-18T17:22:17.844Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592547 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-18T17:22:17.854Z
/* DDL */  select update_Column_Translation_From_AD_Element(240)
;

-- Column: PP_Product_BOM.Cost
-- 2026-05-18T17:35:49.128Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592548,2319,0,37,53018,'XX','Cost','(getCurrentCost(     (PP_Product_BOM.M_Product_ID),     (select p.C_UOM_ID from M_Product p where p.M_Product_ID = PP_Product_BOM.M_Product_ID),     now()::date,     (select ci.C_AcctSchema1_ID from AD_ClientInfo ci where PP_Product_BOM.AD_Client_ID = ci.AD_Client_ID),     (select M_CostElement_ID from M_CostElement where IsActive=''Y'' and CostElementType=''M'' and exists (select 1 from C_AcctSchema cas where cas.CostingMethod = M_CostElement.CostingMethod                                                                                                    and cas.C_AcctSchema_ID=(select ci.C_AcctSchema1_ID from AD_ClientInfo ci where PP_Product_BOM.AD_Client_ID = ci.AD_Client_ID))),     (PP_Product_BOM.AD_Client_ID),     (PP_Product_BOM.AD_Org_ID)     ))',TO_TIMESTAMP('2026-05-18 17:35:49.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Kosteninformation','EE01',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Kosten',0,0,TO_TIMESTAMP('2026-05-18 17:35:49.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-18T17:35:49.131Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592548 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-18T17:35:49.268Z
/* DDL */  select update_Column_Translation_From_AD_Element(2319)
;

-- Column: PP_Product_BOMLine.Cost
-- 2026-05-18T17:40:32.627Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592549,2319,0,37,53019,'XX','Cost','(getCurrentCost(     (PP_Product_BOMLine.M_Product_ID),     (select p.C_UOM_ID from M_Product p where p.M_Product_ID = PP_Product_BOMLine.M_Product_ID),     now()::date,     (select ci.C_AcctSchema1_ID from AD_ClientInfo ci where PP_Product_BOMLine.AD_Client_ID = ci.AD_Client_ID),     (select M_CostElement_ID from M_CostElement where IsActive=''Y'' and CostElementType=''M'' and exists (select 1 from C_AcctSchema cas where cas.CostingMethod = M_CostElement.CostingMethod                                                                                                   and cas.C_AcctSchema_ID=(select ci.C_AcctSchema1_ID from AD_ClientInfo ci where PP_Product_BOMLine.AD_Client_ID = ci.AD_Client_ID))),     (PP_Product_BOMLine.AD_Client_ID),     (PP_Product_BOMLine.AD_Org_ID)     ))',TO_TIMESTAMP('2026-05-18 17:40:31.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Kosteninformation','EE01',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Kosten',0,0,TO_TIMESTAMP('2026-05-18 17:40:31.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-18T17:40:32.631Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592549 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-18T17:40:32.636Z
/* DDL */  select update_Column_Translation_From_AD_Element(2319)
;

-- Column: PP_Product_BOMLine.CurrentCostPrice
-- 2026-05-18T17:40:49.261Z
UPDATE AD_Column SET AD_Element_ID=1394, ColumnName='CurrentCostPrice', Description='Der gegenwärtig verwendete Kostenpreis', FieldLength=10, Help=NULL, IsMandatory='N', Name='Kostenpreis aktuell',Updated=TO_TIMESTAMP('2026-05-18 17:40:49.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592549
;

-- 2026-05-18T17:40:49.265Z
UPDATE AD_Column_Trl trl SET Name='Kostenpreis aktuell' WHERE AD_Column_ID=592549 AND AD_Language='de_DE'
;

-- 2026-05-18T17:40:49.266Z
UPDATE AD_Field SET Name='Kostenpreis aktuell', Description='Der gegenwärtig verwendete Kostenpreis', Help=NULL WHERE AD_Column_ID=592549
;

-- 2026-05-18T17:40:49.267Z
/* DDL */  select update_Column_Translation_From_AD_Element(1394)
;

-- Column: PP_Product_BOMLine.CostStandard
-- 2026-05-18T17:41:16.269Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592550,240,0,37,53019,'XX','CostStandard','(getCurrentCost(                (PP_Product_BOMLine.M_Product_ID),                (select p.C_UOM_ID from M_Product p where p.M_Product_ID = PP_Product_BOMLine.M_Product_ID),                now()::date,                (select ci.C_AcctSchema1_ID from AD_ClientInfo ci where PP_Product_BOMLine.AD_Client_ID = ci.AD_Client_ID),                (select M_CostElement_ID from M_CostElement where IsActive=''Y'' and CostElementType=''M'' and CostingMethod=''S''),                (PP_Product_BOMLine.AD_Client_ID),                (PP_Product_BOMLine.AD_Org_ID)        ))',TO_TIMESTAMP('2026-05-18 17:41:16.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Standardkosten','EE01',0,22,'Standard- oder Plankosten.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Standardkosten',0,0,TO_TIMESTAMP('2026-05-18 17:41:16.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-18T17:41:16.271Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592550 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-18T17:41:16.345Z
/* DDL */  select update_Column_Translation_From_AD_Element(240)
;

-- Column: PP_Product_BOM.CurrentCostPrice
-- 2026-05-18T17:41:32.351Z
UPDATE AD_Column SET AD_Element_ID=1394, ColumnName='CurrentCostPrice', Description='Der gegenwärtig verwendete Kostenpreis', FieldLength=10, Help=NULL, IsMandatory='N', Name='Kostenpreis aktuell',Updated=TO_TIMESTAMP('2026-05-18 17:41:32.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592548
;

-- 2026-05-18T17:41:32.352Z
UPDATE AD_Column_Trl trl SET Name='Kostenpreis aktuell' WHERE AD_Column_ID=592548 AND AD_Language='de_DE'
;

-- 2026-05-18T17:41:32.353Z
UPDATE AD_Field SET Name='Kostenpreis aktuell', Description='Der gegenwärtig verwendete Kostenpreis', Help=NULL WHERE AD_Column_ID=592548
;

-- 2026-05-18T17:41:32.354Z
/* DDL */  select update_Column_Translation_From_AD_Element(1394)
;

-- Field: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Fertigprodukt
-- Column: PP_Product_BOMLine.Parent_Product_ID
-- 2026-05-18T17:41:48.179Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591897,780240,0,53029,TO_TIMESTAMP('2026-05-18 17:41:48.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01','Y','N','N','N','N','N','N','N','Fertigprodukt',TO_TIMESTAMP('2026-05-18 17:41:48.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-18T17:41:48.184Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780240 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-18T17:41:48.190Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584473)
;

-- 2026-05-18T17:41:48.218Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780240
;

-- 2026-05-18T17:41:48.225Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780240)
;

-- Field: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Kostenpreis aktuell
-- Column: PP_Product_BOMLine.CurrentCostPrice
-- 2026-05-18T17:41:48.343Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592549,780241,0,53029,TO_TIMESTAMP('2026-05-18 17:41:48.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der gegenwärtig verwendete Kostenpreis',10,'EE01','Y','N','N','N','N','N','N','N','Kostenpreis aktuell',TO_TIMESTAMP('2026-05-18 17:41:48.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-18T17:41:48.345Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780241 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-18T17:41:48.348Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1394)
;

-- 2026-05-18T17:41:48.358Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780241
;

-- 2026-05-18T17:41:48.358Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780241)
;

-- Field: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Standardkosten
-- Column: PP_Product_BOMLine.CostStandard
-- 2026-05-18T17:41:48.457Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592550,780242,0,53029,TO_TIMESTAMP('2026-05-18 17:41:48.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Standardkosten',22,'EE01','Standard- oder Plankosten.','Y','N','N','N','N','N','N','N','Standardkosten',TO_TIMESTAMP('2026-05-18 17:41:48.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-18T17:41:48.459Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780242 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-18T17:41:48.460Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(240)
;

-- 2026-05-18T17:41:48.464Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780242
;

-- 2026-05-18T17:41:48.464Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780242)
;

-- Field: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> Standardkosten
-- Column: PP_Product_BOM.CostStandard
-- 2026-05-18T17:41:56.733Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592547,780243,0,53028,TO_TIMESTAMP('2026-05-18 17:41:56.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Standardkosten',22,'EE01','Standard- oder Plankosten.','Y','N','N','N','N','N','N','N','Standardkosten',TO_TIMESTAMP('2026-05-18 17:41:56.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-18T17:41:56.736Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-18T17:41:56.739Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(240)
;

-- 2026-05-18T17:41:56.741Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780243
;

-- 2026-05-18T17:41:56.742Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780243)
;

-- Field: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> Kostenpreis aktuell
-- Column: PP_Product_BOM.CurrentCostPrice
-- 2026-05-18T17:41:56.840Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592548,780244,0,53028,TO_TIMESTAMP('2026-05-18 17:41:56.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der gegenwärtig verwendete Kostenpreis',10,'EE01','Y','N','N','N','N','N','N','N','Kostenpreis aktuell',TO_TIMESTAMP('2026-05-18 17:41:56.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-18T17:41:56.844Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780244 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-18T17:41:56.845Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1394)
;

-- 2026-05-18T17:41:56.848Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780244
;

-- 2026-05-18T17:41:56.848Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780244)
;

-- UI Column: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 20
-- UI Element Group: Cost
-- 2026-05-18T17:42:33.597Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540273,555368,TO_TIMESTAMP('2026-05-18 17:42:33.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Cost',35,TO_TIMESTAMP('2026-05-18 17:42:33.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 20 -> Cost.Standardkosten
-- Column: PP_Product_BOM.CostStandard
-- 2026-05-18T17:42:46.139Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,780243,0,53028,555368,651682,'F',TO_TIMESTAMP('2026-05-18 17:42:46.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Standardkosten','Standard- oder Plankosten.','Y','N','N','Y','N','N','N',0,'Standardkosten',10,0,0,TO_TIMESTAMP('2026-05-18 17:42:46.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenartikel(53028,EE01) -> main -> 20 -> Cost.Kostenpreis aktuell
-- Column: PP_Product_BOM.CurrentCostPrice
-- 2026-05-18T17:42:53.760Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,780244,0,53028,555368,651683,'F',TO_TIMESTAMP('2026-05-18 17:42:53.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der gegenwärtig verwendete Kostenpreis','Y','N','N','Y','N','N','N',0,'Kostenpreis aktuell',20,0,0,TO_TIMESTAMP('2026-05-18 17:42:53.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20
-- UI Element Group: cost
-- 2026-05-18T17:43:19.893Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546470,555369,TO_TIMESTAMP('2026-05-18 17:43:19.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','cost',20,TO_TIMESTAMP('2026-05-18 17:43:19.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> cost.Standardkosten
-- Column: PP_Product_BOMLine.CostStandard
-- 2026-05-18T17:43:26.733Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,780242,0,53029,555369,651684,'F',TO_TIMESTAMP('2026-05-18 17:43:26.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Standardkosten','Standard- oder Plankosten.','Y','N','N','Y','N','N','N',0,'Standardkosten',10,0,0,TO_TIMESTAMP('2026-05-18 17:43:26.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> cost.Kostenpreis aktuell
-- Column: PP_Product_BOMLine.CurrentCostPrice
-- 2026-05-18T17:43:32.296Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,780241,0,53029,555369,651685,'F',TO_TIMESTAMP('2026-05-18 17:43:32.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der gegenwärtig verwendete Kostenpreis','Y','N','N','Y','N','N','N',0,'Kostenpreis aktuell',20,0,0,TO_TIMESTAMP('2026-05-18 17:43:32.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> cost.Standardkosten
-- Column: PP_Product_BOMLine.CostStandard
-- 2026-05-18T17:44:23.569Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=651684
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 20 -> cost.Kostenpreis aktuell
-- Column: PP_Product_BOMLine.CurrentCostPrice
-- 2026-05-18T17:44:23.576Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=651685
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Qty Attribute
-- Column: PP_Product_BOMLine.Qty_Attribute_ID
-- 2026-05-18T17:44:23.583Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552447
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.% Ausschuss
-- Column: PP_Product_BOMLine.Scrap
-- 2026-05-18T17:44:23.590Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544412
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Maßeinheit
-- Column: PP_Product_BOMLine.C_UOM_ID
-- 2026-05-18T17:44:23.596Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544401
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Menge Konsumentenlabel
-- Column: PP_Product_BOMLine.CULabelQuanitity
-- 2026-05-18T17:44:23.601Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552311
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Inhaltsstoffe Unterstückliste anzeigen
-- Column: PP_Product_BOMLine.ShowSubBOMIngredients
-- 2026-05-18T17:44:23.607Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552312
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Issue Method
-- Column: PP_Product_BOMLine.IssueMethod
-- 2026-05-18T17:44:23.612Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544416
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Jedes Produkt zuteilbar
-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- 2026-05-18T17:44:23.618Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=620374
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Aktiv
-- Column: PP_Product_BOMLine.IsActive
-- 2026-05-18T17:44:23.623Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544405
;

-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Sektion
-- Column: PP_Product_BOMLine.AD_Org_ID
-- 2026-05-18T17:44:23.628Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2026-05-18 17:44:23.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547353
;

