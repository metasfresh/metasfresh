-- Column: M_Packageable_V.IsCatchWeight
-- Column: M_Packageable_V.IsCatchWeight
-- 2023-10-25T08:15:59.001Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587607,582779,0,20,540823,'IsCatchWeight',TO_TIMESTAMP('2023-10-25 11:15:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate',1,'Y','Y','N','N','N','N','N','N','N','N','N','Catch Weight',TO_TIMESTAMP('2023-10-25 11:15:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-25T08:15:59.003Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587607 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-25T08:15:59.008Z
/* DDL */  select update_Column_Translation_From_AD_Element(582779) 
;

-- Column: M_Packageable_V.Catch_UOM_ID
-- Column: M_Packageable_V.Catch_UOM_ID
-- 2023-10-25T08:18:11.506Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587608,576953,0,30,540823,'Catch_UOM_ID',TO_TIMESTAMP('2023-10-25 11:18:11','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit.','de.metas.inoutcandidate',10,'Y','Y','N','N','N','N','N','N','N','N','N','Catch Einheit',TO_TIMESTAMP('2023-10-25 11:18:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-25T08:18:11.508Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587608 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-25T08:18:11.511Z
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- Column: M_Packageable_V.Catch_UOM_ID
-- Column: M_Packageable_V.Catch_UOM_ID
-- 2023-10-25T08:26:20.521Z
UPDATE AD_Column SET AD_Reference_Value_ID=114,Updated=TO_TIMESTAMP('2023-10-25 11:26:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587608
;

