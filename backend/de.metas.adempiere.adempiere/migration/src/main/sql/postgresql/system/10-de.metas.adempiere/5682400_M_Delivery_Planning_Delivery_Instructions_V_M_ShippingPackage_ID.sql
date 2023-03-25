-- Column: M_Delivery_Planning_Delivery_Instructions_V.M_ShippingPackage_ID
-- 2023-03-23T09:30:56.797Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586323,540097,0,30,542280,'M_ShippingPackage_ID',TO_TIMESTAMP('2023-03-23 11:30:56','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Shipping Package',TO_TIMESTAMP('2023-03-23 11:30:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-23T09:30:56.801Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586323 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-23T09:30:56.836Z
/* DDL */  select update_Column_Translation_From_AD_Element(540097) 
;

-- Table: M_Delivery_Planning_Delivery_Instructions_V
-- 2023-03-23T09:31:21.351Z
UPDATE AD_Table SET IsView='Y',Updated=TO_TIMESTAMP('2023-03-23 11:31:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542280
;

