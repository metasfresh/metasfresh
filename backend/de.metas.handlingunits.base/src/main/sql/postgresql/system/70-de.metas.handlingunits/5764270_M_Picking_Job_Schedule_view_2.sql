-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job_Schedule_view.C_BPartner_Customer_ID
-- 2025-08-28T11:15:43.299Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,590693,541356,0,30,542514,'C_BPartner_Customer_ID',TO_TIMESTAMP('2025-08-28 11:15:42.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits',10,'Y','Y','N','N','N','N','N','N','N','N','N','Kunde',TO_TIMESTAMP('2025-08-28 11:15:42.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T11:15:43.303Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590693 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T11:15:43.480Z
/* DDL */  select update_Column_Translation_From_AD_Element(541356)
;

-- Column: M_Picking_Job_Schedule_view.C_OrderSO_ID
-- 2025-08-28T11:15:43.946Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,590694,543479,0,30,542514,'C_OrderSO_ID',TO_TIMESTAMP('2025-08-28 11:15:43.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','de.metas.handlingunits',10,'Y','Y','N','N','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2025-08-28 11:15:43.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T11:15:43.951Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590694 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T11:15:44.056Z
/* DDL */  select update_Column_Translation_From_AD_Element(543479)
;

-- Column: M_Picking_Job_Schedule_view.HandOver_Partner_ID
-- 2025-08-28T11:15:44.455Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,590695,542280,0,30,542514,'HandOver_Partner_ID',TO_TIMESTAMP('2025-08-28 11:15:44.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits',10,'Y','Y','N','N','N','N','N','N','N','N','N','Übergabe-Partner',TO_TIMESTAMP('2025-08-28 11:15:44.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T11:15:44.456Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590695 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T11:15:44.555Z
/* DDL */  select update_Column_Translation_From_AD_Element(542280)
;

-- Column: M_Picking_Job_Schedule_view.HandOver_Location_ID
-- 2025-08-28T11:15:44.949Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,590696,542281,0,30,542514,'HandOver_Location_ID',TO_TIMESTAMP('2025-08-28 11:15:44.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits',10,'Y','Y','N','N','N','N','N','N','N','N','N','Übergabeadresse',TO_TIMESTAMP('2025-08-28 11:15:44.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T11:15:44.950Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590696 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T11:15:45.043Z
/* DDL */  select update_Column_Translation_From_AD_Element(542281)
;

-- Column: M_Picking_Job_Schedule_view.C_OrderLineSO_ID
-- 2025-08-28T11:15:45.454Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,590697,542435,0,30,542514,'C_OrderLineSO_ID',TO_TIMESTAMP('2025-08-28 11:15:45.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition','de.metas.handlingunits',10,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','Y','N','N','N','N','N','N','N','N','N','Auftragsposition',TO_TIMESTAMP('2025-08-28 11:15:45.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T11:15:45.457Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590697 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T11:15:45.544Z
/* DDL */  select update_Column_Translation_From_AD_Element(542435)
;

-- Column: M_Picking_Job_Schedule_view.Catch_UOM_ID
-- 2025-08-28T11:15:45.975Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,590698,576953,0,30,542514,'Catch_UOM_ID',TO_TIMESTAMP('2025-08-28 11:15:45.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Aus dem Produktstamm übenommene Catch Weight Einheit.','de.metas.handlingunits',10,'Y','Y','N','N','N','N','N','N','N','N','N','Catch Einheit',TO_TIMESTAMP('2025-08-28 11:15:45.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T11:15:45.977Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590698 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T11:15:46.073Z
/* DDL */  select update_Column_Translation_From_AD_Element(576953)
;

-- Column: M_Picking_Job_Schedule_view.PackTo_HU_PI_Item_Product_ID
-- 2025-08-28T11:15:46.473Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,590699,580216,0,30,542514,'PackTo_HU_PI_Item_Product_ID',TO_TIMESTAMP('2025-08-28 11:15:46.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits',10,'Y','Y','N','N','N','N','N','N','N','N','N','Packvorschrift',TO_TIMESTAMP('2025-08-28 11:15:46.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T11:15:46.475Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590699 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T11:15:46.588Z
/* DDL */  select update_Column_Translation_From_AD_Element(580216)
;

-- Column: M_Picking_Job_Schedule_view.M_Picking_Job_Schedule_ID
-- 2025-08-28T11:15:47.011Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,590700,583882,0,30,542514,'M_Picking_Job_Schedule_ID',TO_TIMESTAMP('2025-08-28 11:15:46.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits',10,'Y','Y','N','N','N','N','N','N','N','N','N','Picking Job Schedule',TO_TIMESTAMP('2025-08-28 11:15:46.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-28T11:15:47.013Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590700 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-28T11:15:47.116Z
/* DDL */  select update_Column_Translation_From_AD_Element(583882)
;

-- Column: M_Picking_Job_Schedule_view.Catch_UOM_ID
-- 2025-08-28T11:19:11.767Z
UPDATE AD_Column SET AD_Reference_Value_ID=114,Updated=TO_TIMESTAMP('2025-08-28 11:19:11.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590698
;

-- Column: M_Picking_Job_Schedule_view.Catch_UOM_ID
-- 2025-08-28T11:19:33.705Z
UPDATE AD_Column SET AD_Reference_Value_ID=541960,Updated=TO_TIMESTAMP('2025-08-28 11:19:33.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590698
;

-- Column: M_Picking_Job_Schedule_view.C_BPartner_Customer_ID
-- 2025-08-28T11:19:39.066Z
UPDATE AD_Column SET AD_Reference_Value_ID=541252,Updated=TO_TIMESTAMP('2025-08-28 11:19:39.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590693
;

-- Column: M_Picking_Job_Schedule_view.C_OrderLineSO_ID
-- 2025-08-28T11:19:51.316Z
UPDATE AD_Column SET AD_Reference_Value_ID=271,Updated=TO_TIMESTAMP('2025-08-28 11:19:51.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590697
;

-- Column: M_Picking_Job_Schedule_view.C_OrderLineSO_ID
-- 2025-08-28T11:19:56.050Z
UPDATE AD_Column SET AD_Reference_Value_ID=541413,Updated=TO_TIMESTAMP('2025-08-28 11:19:56.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590697
;

-- Column: M_Picking_Job_Schedule_view.C_OrderSO_ID
-- 2025-08-28T11:20:02.606Z
UPDATE AD_Column SET AD_Reference_Value_ID=290,Updated=TO_TIMESTAMP('2025-08-28 11:20:02.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590694
;

-- Column: M_Picking_Job_Schedule_view.DeliveryViaRule
-- 2025-08-28T11:20:31.841Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=152,Updated=TO_TIMESTAMP('2025-08-28 11:20:31.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590676
;

-- Column: M_Picking_Job_Schedule_view.HandOver_Location_ID
-- 2025-08-28T11:20:42.731Z
UPDATE AD_Column SET AD_Reference_Value_ID=159,Updated=TO_TIMESTAMP('2025-08-28 11:20:42.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590696
;

-- Column: M_Picking_Job_Schedule_view.HandOver_Partner_ID
-- 2025-08-28T11:20:48.749Z
UPDATE AD_Column SET AD_Reference_Value_ID=541252,Updated=TO_TIMESTAMP('2025-08-28 11:20:48.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590695
;

-- Column: M_Picking_Job_Schedule_view.PackTo_HU_PI_Item_Product_ID
-- 2025-08-28T11:21:10.005Z
UPDATE AD_Column SET AD_Reference_Value_ID=540500,Updated=TO_TIMESTAMP('2025-08-28 11:21:10.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590699
;

-- Column: M_Picking_Job_Schedule_view.PriorityRule
-- 2025-08-28T11:21:35.639Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=154,Updated=TO_TIMESTAMP('2025-08-28 11:21:35.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590678
;

-- Column: M_Picking_Job_Schedule_view.Setup_Place_No
-- 2025-08-28T11:21:58.206Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2025-08-28 11:21:58.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590659
;

-- Column: M_Picking_Job_Schedule_view.HandOver_Partner_ID
-- 2025-08-28T11:22:45.195Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-28 11:22:45.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590695
;

-- Column: M_Picking_Job_Schedule_view.C_OrderSO_ID
-- 2025-08-28T11:22:57.821Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-28 11:22:57.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590694
;

-- Column: M_Picking_Job_Schedule_view.C_Workplace_ID
-- 2025-08-28T11:23:08.411Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-28 11:23:08.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590692
;

-- Column: M_Picking_Job_Schedule_view.DateOrdered
-- 2025-08-28T11:23:19.567Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-28 11:23:19.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590660
;

-- Column: M_Picking_Job_Schedule_view.DatePromised
-- 2025-08-28T11:23:25.540Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-28 11:23:25.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590681
;

-- Column: M_Picking_Job_Schedule_view.POReference
-- 2025-08-28T11:23:42.161Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-28 11:23:42.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590658
;

-- Column: M_Picking_Job_Schedule_view.PreparationDate
-- 2025-08-28T11:23:49.025Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-28 11:23:49.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590665
;

-- Field: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> Kunde
-- Column: M_Picking_Job_Schedule_view.C_BPartner_Customer_ID
-- 2025-08-28T11:29:04.123Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590693,752559,0,548377,TO_TIMESTAMP('2025-08-28 11:29:03.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Kunde',TO_TIMESTAMP('2025-08-28 11:29:03.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T11:29:04.127Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752559 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T11:29:04.132Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541356)
;

-- 2025-08-28T11:29:04.158Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752559
;

-- 2025-08-28T11:29:04.165Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752559)
;

-- Field: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> Auftrag
-- Column: M_Picking_Job_Schedule_view.C_OrderSO_ID
-- 2025-08-28T11:29:04.371Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590694,752560,0,548377,TO_TIMESTAMP('2025-08-28 11:29:04.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2025-08-28 11:29:04.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T11:29:04.373Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752560 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T11:29:04.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543479)
;

-- 2025-08-28T11:29:04.385Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752560
;

-- 2025-08-28T11:29:04.387Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752560)
;

-- Field: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> Übergabe-Partner
-- Column: M_Picking_Job_Schedule_view.HandOver_Partner_ID
-- 2025-08-28T11:29:04.548Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590695,752561,0,548377,TO_TIMESTAMP('2025-08-28 11:29:04.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Übergabe-Partner',TO_TIMESTAMP('2025-08-28 11:29:04.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T11:29:04.549Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752561 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T11:29:04.551Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542280)
;

-- 2025-08-28T11:29:04.559Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752561
;

-- 2025-08-28T11:29:04.560Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752561)
;

-- Field: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> Übergabeadresse
-- Column: M_Picking_Job_Schedule_view.HandOver_Location_ID
-- 2025-08-28T11:29:04.713Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590696,752562,0,548377,TO_TIMESTAMP('2025-08-28 11:29:04.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Übergabeadresse',TO_TIMESTAMP('2025-08-28 11:29:04.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T11:29:04.714Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752562 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T11:29:04.717Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542281)
;

-- 2025-08-28T11:29:04.721Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752562
;

-- 2025-08-28T11:29:04.722Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752562)
;

-- Field: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> Auftragsposition
-- Column: M_Picking_Job_Schedule_view.C_OrderLineSO_ID
-- 2025-08-28T11:29:04.872Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590697,752563,0,548377,TO_TIMESTAMP('2025-08-28 11:29:04.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition',10,'de.metas.handlingunits','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','N','N','N','N','N','Auftragsposition',TO_TIMESTAMP('2025-08-28 11:29:04.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T11:29:04.874Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752563 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T11:29:04.877Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542435)
;

-- 2025-08-28T11:29:04.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752563
;

-- 2025-08-28T11:29:04.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752563)
;

-- Field: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> Catch Einheit
-- Column: M_Picking_Job_Schedule_view.Catch_UOM_ID
-- 2025-08-28T11:29:05.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590698,752564,0,548377,TO_TIMESTAMP('2025-08-28 11:29:04.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Aus dem Produktstamm übenommene Catch Weight Einheit.',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Catch Einheit',TO_TIMESTAMP('2025-08-28 11:29:04.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T11:29:05.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752564 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T11:29:05.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576953)
;

-- 2025-08-28T11:29:05.044Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752564
;

-- 2025-08-28T11:29:05.045Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752564)
;

-- Field: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> Packvorschrift
-- Column: M_Picking_Job_Schedule_view.PackTo_HU_PI_Item_Product_ID
-- 2025-08-28T11:29:05.183Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590699,752565,0,548377,TO_TIMESTAMP('2025-08-28 11:29:05.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Packvorschrift',TO_TIMESTAMP('2025-08-28 11:29:05.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T11:29:05.185Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752565 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T11:29:05.188Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580216)
;

-- 2025-08-28T11:29:05.193Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752565
;

-- 2025-08-28T11:29:05.194Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752565)
;

-- Field: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> Picking Job Schedule
-- Column: M_Picking_Job_Schedule_view.M_Picking_Job_Schedule_ID
-- 2025-08-28T11:29:05.327Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590700,752566,0,548377,TO_TIMESTAMP('2025-08-28 11:29:05.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Picking Job Schedule',TO_TIMESTAMP('2025-08-28 11:29:05.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-28T11:29:05.329Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752566 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-28T11:29:05.330Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583882)
;

-- 2025-08-28T11:29:05.332Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752566
;

-- 2025-08-28T11:29:05.333Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752566)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Auftrag
-- Column: M_Picking_Job_Schedule_view.C_OrderSO_ID
-- 2025-08-28T11:29:23.994Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752560,0,548377,553423,636471,'F',TO_TIMESTAMP('2025-08-28 11:29:23.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','Y','N','Y','N','N','Auftrag',10,0,0,TO_TIMESTAMP('2025-08-28 11:29:23.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Referenz
-- Column: M_Picking_Job_Schedule_view.POReference
-- 2025-08-28T11:29:36.614Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752528,0,548377,553423,636472,'F',TO_TIMESTAMP('2025-08-28 11:29:36.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','Referenz',20,0,0,TO_TIMESTAMP('2025-08-28 11:29:36.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Lieferdisposition
-- Column: M_Picking_Job_Schedule_view.M_ShipmentSchedule_ID
-- 2025-08-28T11:29:46.280Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752534,0,548377,553423,636473,'F',TO_TIMESTAMP('2025-08-28 11:29:45.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Lieferdisposition',30,0,0,TO_TIMESTAMP('2025-08-28 11:29:45.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10
-- UI Element Group: qtys
-- 2025-08-28T11:30:00.963Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548402,553424,TO_TIMESTAMP('2025-08-28 11:30:00.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','qtys',20,TO_TIMESTAMP('2025-08-28 11:30:00.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Ausliefermenge
-- Column: M_Picking_Job_Schedule_view.QtyToDeliver
-- 2025-08-28T11:30:21.674Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752540,0,548377,553424,636474,'F',TO_TIMESTAMP('2025-08-28 11:30:21.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Ausliefermenge',10,0,0,TO_TIMESTAMP('2025-08-28 11:30:21.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10
-- UI Element Group: product
-- 2025-08-28T11:31:12.757Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548402,553425,TO_TIMESTAMP('2025-08-28 11:31:12.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','product',30,TO_TIMESTAMP('2025-08-28 11:31:12.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10
-- UI Element Group: dates
-- 2025-08-28T11:31:19.291Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548402,553426,TO_TIMESTAMP('2025-08-28 11:31:19.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','dates',40,TO_TIMESTAMP('2025-08-28 11:31:19.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10
-- UI Element Group: schedule
-- 2025-08-28T11:31:27.891Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548402,553427,TO_TIMESTAMP('2025-08-28 11:31:24.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','schedule',50,TO_TIMESTAMP('2025-08-28 11:31:24.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> product.Produkt
-- Column: M_Picking_Job_Schedule_view.M_Product_ID
-- 2025-08-28T11:31:39.586Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752536,0,548377,553425,636475,'F',TO_TIMESTAMP('2025-08-28 11:31:39.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',10,0,0,TO_TIMESTAMP('2025-08-28 11:31:39.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> product.Merkmale
-- Column: M_Picking_Job_Schedule_view.M_AttributeSetInstance_ID
-- 2025-08-28T11:31:49.060Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752538,0,548377,553425,636476,'F',TO_TIMESTAMP('2025-08-28 11:31:48.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','Merkmale',20,0,0,TO_TIMESTAMP('2025-08-28 11:31:48.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Maßeinheit
-- Column: M_Picking_Job_Schedule_view.C_UOM_ID
-- 2025-08-28T11:32:03.535Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752537,0,548377,553424,636477,'F',TO_TIMESTAMP('2025-08-28 11:32:03.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',20,0,0,TO_TIMESTAMP('2025-08-28 11:32:03.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> dates.Auftragsdatum
-- Column: M_Picking_Job_Schedule_view.DateOrdered
-- 2025-08-28T11:32:20.136Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752530,0,548377,553426,636478,'F',TO_TIMESTAMP('2025-08-28 11:32:19.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','Y','N','N','Auftragsdatum',10,0,0,TO_TIMESTAMP('2025-08-28 11:32:19.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> dates.Bereitstellungsdatum
-- Column: M_Picking_Job_Schedule_view.PreparationDate
-- 2025-08-28T11:32:32.965Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752535,0,548377,553426,636479,'F',TO_TIMESTAMP('2025-08-28 11:32:31.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Bereitstellungsdatum',20,0,0,TO_TIMESTAMP('2025-08-28 11:32:31.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> dates.Zugesagter Termin
-- Column: M_Picking_Job_Schedule_view.DatePromised
-- 2025-08-28T11:32:41.577Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752551,0,548377,553426,636480,'F',TO_TIMESTAMP('2025-08-28 11:32:41.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','N','Zugesagter Termin',30,0,0,TO_TIMESTAMP('2025-08-28 11:32:41.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Arbeitsplatz
-- Column: M_Picking_Job_Schedule_view.C_Workplace_ID
-- 2025-08-28T11:33:01.952Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752558,0,548377,553427,636481,'F',TO_TIMESTAMP('2025-08-28 11:33:01.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Arbeitsplatz',10,0,0,TO_TIMESTAMP('2025-08-28 11:33:01.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Qty To Pick
-- Column: M_Picking_Job_Schedule_view.QtyToPick
-- 2025-08-28T11:33:09.285Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752557,0,548377,553427,636482,'F',TO_TIMESTAMP('2025-08-28 11:33:09.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Qty To Pick',20,0,0,TO_TIMESTAMP('2025-08-28 11:33:09.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Übergabe-Partner
-- Column: M_Picking_Job_Schedule_view.HandOver_Partner_ID
-- 2025-08-28T11:33:32.414Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752561,0,548377,553423,636483,'F',TO_TIMESTAMP('2025-08-28 11:33:32.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Übergabe-Partner',40,0,0,TO_TIMESTAMP('2025-08-28 11:33:32.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Übergabeadresse
-- Column: M_Picking_Job_Schedule_view.HandOver_Location_ID
-- 2025-08-28T11:33:41.640Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752562,0,548377,553423,636484,'F',TO_TIMESTAMP('2025-08-28 11:33:41.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Übergabeadresse',50,0,0,TO_TIMESTAMP('2025-08-28 11:33:41.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Lieferweg
-- Column: M_Picking_Job_Schedule_view.M_Shipper_ID
-- 2025-08-28T11:36:15.145Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752550,0,548377,553423,636485,'F',TO_TIMESTAMP('2025-08-28 11:36:14.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','Y','N','N','Lieferweg',60,0,0,TO_TIMESTAMP('2025-08-28 11:36:14.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_Picking_Job_Schedule_view.M_Shipper_ID
-- 2025-08-28T11:36:33.777Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-28 11:36:33.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590680
;

-- Column: M_Picking_Job_Schedule_view.M_Product_ID
-- 2025-08-28T11:36:41.615Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-28 11:36:41.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590666
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Auftrag
-- Column: M_Picking_Job_Schedule_view.C_OrderSO_ID
-- 2025-08-28T11:38:23.243Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636471
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> product.Produkt
-- Column: M_Picking_Job_Schedule_view.M_Product_ID
-- 2025-08-28T11:38:23.248Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636475
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Übergabe-Partner
-- Column: M_Picking_Job_Schedule_view.HandOver_Partner_ID
-- 2025-08-28T11:38:23.254Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636483
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Übergabeadresse
-- Column: M_Picking_Job_Schedule_view.HandOver_Location_ID
-- 2025-08-28T11:38:23.260Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636484
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> sales order.Lieferweg
-- Column: M_Picking_Job_Schedule_view.M_Shipper_ID
-- 2025-08-28T11:38:23.264Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636485
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> dates.Bereitstellungsdatum
-- Column: M_Picking_Job_Schedule_view.PreparationDate
-- 2025-08-28T11:38:23.269Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636479
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Maßeinheit
-- Column: M_Picking_Job_Schedule_view.C_UOM_ID
-- 2025-08-28T11:38:23.273Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636477
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> qtys.Ausliefermenge
-- Column: M_Picking_Job_Schedule_view.QtyToDeliver
-- 2025-08-28T11:38:23.277Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636474
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Qty To Pick
-- Column: M_Picking_Job_Schedule_view.QtyToPick
-- 2025-08-28T11:38:23.282Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636482
;

-- UI Element: Picking Job Schedule(541929,de.metas.handlingunits) -> Picking Job Schedule(548377,de.metas.handlingunits) -> primary -> 10 -> schedule.Arbeitsplatz
-- Column: M_Picking_Job_Schedule_view.C_Workplace_ID
-- 2025-08-28T11:38:23.288Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-08-28 11:38:23.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636481
;

-- Column: M_Picking_Job_Schedule_view.M_ShipmentSchedule_ID
-- 2025-08-28T11:43:21.053Z
UPDATE AD_Column SET IsMandatory='Y', IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-08-28 11:43:21.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590664
;

-- Column: M_Picking_Job_Schedule_view.M_Picking_Job_Schedule_ID
-- 2025-08-28T11:44:41.558Z
UPDATE AD_Column SET IsMandatory='Y', IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-08-28 11:44:41.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590700
;

-- Column: M_Picking_Job_Schedule_view.M_Picking_Job_Schedule_ID
-- 2025-08-28T11:46:15.604Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=1,Updated=TO_TIMESTAMP('2025-08-28 11:46:15.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590700
;

