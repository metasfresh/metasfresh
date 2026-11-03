-- Run mode: SWING_CLIENT

-- Reference: ExternalSystem_Outbound_Endpoint_AuthType
-- Value: SAS
-- ValueName: SAS
-- 2025-11-17T13:52:40.541Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544066,542017,TO_TIMESTAMP('2025-11-17 13:52:40.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SAS-Signatur-basierte Authentifizierung','D','Y','SAS',TO_TIMESTAMP('2025-11-17 13:52:40.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SAS','SAS')
;

-- 2025-11-17T13:52:40.558Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544066 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: ExternalSystem_Outbound_Endpoint.OutboundHttpEP
-- 2025-11-17T16:10:55.705Z
UPDATE AD_Column SET FieldLength=1000,Updated=TO_TIMESTAMP('2025-11-17 16:10:55.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591478
;

-- 2025-11-17T16:10:57.387Z
INSERT INTO t_alter_column values('externalsystem_outbound_endpoint','OutboundHttpEP','VARCHAR(1000)',null,null)
;

