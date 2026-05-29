-- Run mode: SWING_CLIENT

-- 2025-08-28T07:06:11.042Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583881,0,'ShipperRouteCodeName',TO_TIMESTAMP('2025-08-28 07:06:10.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Lieferweg - Leitweg','Lieferweg - Leitweg',TO_TIMESTAMP('2025-08-28 07:06:10.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T07:06:11.046Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583881 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ShipperRouteCodeName
-- 2025-08-28T07:07:31.457Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipper - Routingcode', PrintName='Shipper - Routingcode',Updated=TO_TIMESTAMP('2025-08-28 07:07:31.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583881 AND AD_Language='en_US'
;

-- 2025-08-28T07:07:31.458Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-28T07:07:31.692Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583881,'en_US')
;

-- Element: ShipperRouteCodeName
-- 2025-08-28T07:07:32.490Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-28 07:07:32.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583881 AND AD_Language='de_CH'
;

-- 2025-08-28T07:07:32.492Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583881,'de_CH')
;

-- Element: ShipperRouteCodeName
-- 2025-08-28T07:07:33.666Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-28 07:07:33.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583881 AND AD_Language='de_DE'
;

-- 2025-08-28T07:07:33.668Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583881,'de_DE')
;

-- 2025-08-28T07:07:33.670Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583881,'de_DE')
;

-- Column: I_BPartner.M_Shipper_RoutingCode_ID
-- 2025-08-28T07:08:34.768Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590641,583860,0,19,533,'XX','M_Shipper_RoutingCode_ID',TO_TIMESTAMP('2025-08-28 07:08:34.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Leitcode',0,0,TO_TIMESTAMP('2025-08-28 07:08:34.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T07:08:34.771Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590641 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T07:08:34.773Z
/* DDL */  select update_Column_Translation_From_AD_Element(583860)
;

-- 2025-08-28T07:08:36.948Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN M_Shipper_RoutingCode_ID NUMERIC(10)')
;

-- 2025-08-28T07:08:36.956Z
ALTER TABLE I_BPartner ADD CONSTRAINT MShipperRoutingCode_IBPartner FOREIGN KEY (M_Shipper_RoutingCode_ID) REFERENCES public.M_Shipper_RoutingCode DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_BPartner.ShipperRouteCodeName
-- 2025-08-28T07:24:22.782Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590653,583881,0,10,533,'XX','ShipperRouteCodeName',TO_TIMESTAMP('2025-08-28 07:24:22.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,40,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Lieferweg - Leitweg',0,0,TO_TIMESTAMP('2025-08-28 07:24:22.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T07:24:22.784Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590653 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T07:24:22.888Z
/* DDL */  select update_Column_Translation_From_AD_Element(583881)
;

-- 2025-08-28T07:24:30.306Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN ShipperRouteCodeName VARCHAR(40)')
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Leitcode
-- Column: I_BPartner.M_Shipper_RoutingCode_ID
-- 2025-08-28T08:05:20.497Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590641,752523,0,441,TO_TIMESTAMP('2025-08-28 08:05:20.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Leitcode',TO_TIMESTAMP('2025-08-28 08:05:20.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T08:05:20.500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752523 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T08:05:20.501Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583860)
;

-- 2025-08-28T08:05:20.511Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752523
;

-- 2025-08-28T08:05:20.514Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752523)
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Lieferweg - Leitweg
-- Column: I_BPartner.ShipperRouteCodeName
-- 2025-08-28T08:05:33.037Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590653,752524,0,441,TO_TIMESTAMP('2025-08-28 08:05:32.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','Lieferweg - Leitweg',TO_TIMESTAMP('2025-08-28 08:05:32.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T08:05:33.038Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752524 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T08:05:33.039Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583881)
;

-- 2025-08-28T08:05:33.041Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752524
;

-- 2025-08-28T08:05:33.041Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752524)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Leitcode
-- Column: I_BPartner.M_Shipper_RoutingCode_ID
-- 2025-08-28T08:06:56.896Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752523,0,441,541262,636469,'F',TO_TIMESTAMP('2025-08-28 08:06:56.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Leitcode',593,0,0,TO_TIMESTAMP('2025-08-28 08:06:56.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Lieferweg - Leitweg
-- Column: I_BPartner.ShipperRouteCodeName
-- 2025-08-28T08:07:36.285Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752524,0,441,541262,636470,'F',TO_TIMESTAMP('2025-08-28 08:07:36.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Lieferweg - Leitweg',597,0,0,TO_TIMESTAMP('2025-08-28 08:07:36.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Shipper name
-- Column: I_BPartner.ShipperName
-- 2025-08-28T08:10:36.702Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=635386
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Lieferweg
-- Column: I_BPartner.M_Shipper_ID
-- 2025-08-28T08:10:40.294Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=635387
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Lieferweg Name
-- Column: I_BPartner.ShipperName
-- 2025-08-28T08:10:55.790Z
UPDATE AD_UI_Element SET SeqNo=530,Updated=TO_TIMESTAMP('2025-08-28 08:10:55.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636048
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Lieferweg
-- Column: I_BPartner.M_Shipper_ID
-- 2025-08-28T08:11:05.563Z
UPDATE AD_UI_Element SET SeqNo=540,Updated=TO_TIMESTAMP('2025-08-28 08:11:05.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636049
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Lieferweg Name für Adresse
-- Column: I_BPartner.LocationShipperName
-- 2025-08-28T08:11:18.780Z
UPDATE AD_UI_Element SET SeqNo=550,Updated=TO_TIMESTAMP('2025-08-28 08:11:18.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636050
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Lieferweg für Adresse
-- Column: I_BPartner.Location_M_Shipper_ID
-- 2025-08-28T08:11:25.041Z
UPDATE AD_UI_Element SET SeqNo=560,Updated=TO_TIMESTAMP('2025-08-28 08:11:25.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636051
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Leitcode
-- Column: I_BPartner.M_Shipper_RoutingCode_ID
-- 2025-08-28T08:11:31.718Z
UPDATE AD_UI_Element SET SeqNo=570,Updated=TO_TIMESTAMP('2025-08-28 08:11:31.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636469
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Lieferweg - Leitweg
-- Column: I_BPartner.ShipperRouteCodeName
-- 2025-08-28T08:11:51.385Z
UPDATE AD_UI_Element SET SeqNo=580,Updated=TO_TIMESTAMP('2025-08-28 08:11:51.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636470
;

-- Element: M_Shipper_RoutingCode_ID
-- 2025-08-28T08:13:15.918Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Routingcode', PrintName='Routingcode',Updated=TO_TIMESTAMP('2025-08-28 08:13:15.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583860 AND AD_Language='en_US'
;

-- 2025-08-28T08:13:15.919Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-28T08:13:16.148Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583860,'en_US')
;

-- Element: M_Shipper_RoutingCode_ID
-- 2025-08-28T08:13:18.256Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-28 08:13:18.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583860 AND AD_Language='de_CH'
;

-- 2025-08-28T08:13:18.259Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583860,'de_CH')
;

-- Element: M_Shipper_RoutingCode_ID
-- 2025-08-28T08:13:20.142Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-28 08:13:20.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583860 AND AD_Language='de_DE'
;

-- 2025-08-28T08:13:20.145Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583860,'de_DE')
;

-- 2025-08-28T08:13:20.148Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583860,'de_DE')
;

