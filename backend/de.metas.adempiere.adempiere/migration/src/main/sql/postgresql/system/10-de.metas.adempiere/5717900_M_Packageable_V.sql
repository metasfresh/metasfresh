-- Column: M_Packageable_V.Setup_Place_No
-- Column: M_Packageable_V.Setup_Place_No
-- 2024-02-26T11:18:51.197Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587952,578643,0,22,540823,'Setup_Place_No',TO_TIMESTAMP('2024-02-26 13:18:50','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Rüstplatz beim Logistik-Partner oder im eigenen Lager','de.metas.inoutcandidate',14,'Y','Y','N','N','N','N','N','N','N','N','N','Rüstplatz-Nr.',TO_TIMESTAMP('2024-02-26 13:18:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-26T11:18:51.201Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587952 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-26T11:18:51.213Z
/* DDL */  select update_Column_Translation_From_AD_Element(578643) 
;

-- Column: M_Packageable_V.Setup_Place_No
-- Column: M_Packageable_V.Setup_Place_No
-- 2024-02-26T11:19:10.441Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', FieldLength=10, IsMandatory='Y',Updated=TO_TIMESTAMP('2024-02-26 13:19:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587952
;

-- Column: M_Packageable_V.Setup_Place_No
-- Column: M_Packageable_V.Setup_Place_No
-- 2024-02-26T11:19:16.046Z
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2024-02-26 13:19:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587952
;

