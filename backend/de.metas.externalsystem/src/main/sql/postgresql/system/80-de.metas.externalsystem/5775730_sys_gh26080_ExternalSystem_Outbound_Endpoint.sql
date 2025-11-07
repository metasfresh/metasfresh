-- Run mode: SWING_CLIENT

-- Column: ExternalSystem_Outbound_Endpoint.LoginUsername
-- 2025-11-05T15:52:18.633Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591493,54443,0,10,542551,'DC','LoginUsername',TO_TIMESTAMP('2025-11-05 15:52:18.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Login User Name',0,0,TO_TIMESTAMP('2025-11-05 15:52:18.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-05T15:52:18.642Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591493 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-05T15:52:18.679Z
/* DDL */  select update_Column_Translation_From_AD_Element(54443)
;

-- 2025-11-05T15:52:27.014Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN LoginUsername VARCHAR(255)')
;

-- Table: ExternalSystem_Outbound_Endpoint
-- 2025-11-05T15:55:09.130Z
UPDATE AD_Table SET AD_Window_ID=541967,Updated=TO_TIMESTAMP('2025-11-05 15:55:09.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542551
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Login User Name
-- Column: ExternalSystem_Outbound_Endpoint.LoginUsername
-- 2025-11-05T15:55:46.766Z
UPDATE AD_Field SET AD_Column_ID=591493, Description=NULL, Help=NULL, Name='Login User Name',Updated=TO_TIMESTAMP('2025-11-05 15:55:46.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755949
;

-- 2025-11-05T15:55:46.769Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Login User Name' WHERE AD_Field_ID=755949 AND AD_Language='de_DE'
;

-- 2025-11-05T15:55:46.772Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(54443)
;

-- 2025-11-05T15:55:46.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755949
;

-- 2025-11-05T15:55:46.786Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755949)
;

-- Reference: EndpointType
-- Value: EMAIL
-- ValueName: EMAIL
-- 2025-11-05T15:56:19.396Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2025-11-05 15:56:19.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544053
;

-- Reference: EndpointType
-- Value: FILE
-- ValueName: FILE
-- 2025-11-05T15:56:20.370Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2025-11-05 15:56:20.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544052
;

-- Reference: EndpointType
-- Value: SFTP
-- ValueName: SFTP
-- 2025-11-05T15:56:20.898Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2025-11-05 15:56:20.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544051
;

-- Reference: EndpointType
-- Value: TCP
-- ValueName: TCP
-- 2025-11-05T15:56:22.321Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2025-11-05 15:56:22.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544054
;

-- Name: ExternalSystem_Outbound_Endpoint_EndpointType
-- 2025-11-05T15:57:39.116Z
UPDATE AD_Reference SET Name='ExternalSystem_Outbound_Endpoint_EndpointType',Updated=TO_TIMESTAMP('2025-11-05 15:57:39.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542016
;

-- 2025-11-05T15:57:39.119Z
UPDATE AD_Reference_Trl trl SET Name='ExternalSystem_Outbound_Endpoint_EndpointType' WHERE AD_Reference_ID=542016 AND AD_Language='de_DE'
;

-- Name: ExternalSystem_Outbound_Endpoint_AuthType
-- 2025-11-05T15:58:00.039Z
UPDATE AD_Reference SET Name='ExternalSystem_Outbound_Endpoint_AuthType',Updated=TO_TIMESTAMP('2025-11-05 15:58:00.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542017
;

-- 2025-11-05T15:58:00.040Z
UPDATE AD_Reference_Trl trl SET Name='ExternalSystem_Outbound_Endpoint_AuthType' WHERE AD_Reference_ID=542017 AND AD_Language='de_DE'
;

-- Reference: ExternalSystem_Outbound_Endpoint_AuthType
-- Value: Basic
-- ValueName: Basic
-- 2025-11-05T15:58:16.693Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2025-11-05 15:58:16.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544056
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Login User Name
-- Column: ExternalSystem_Outbound_Endpoint.LoginUsername
-- 2025-11-05T15:58:42.131Z
UPDATE AD_Field SET DisplayLogic='@AuthType/X@=''OAuth''',Updated=TO_TIMESTAMP('2025-11-05 15:58:42.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755949
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Kennwort
-- Column: ExternalSystem_Outbound_Endpoint.Password
-- 2025-11-05T15:59:00.122Z
UPDATE AD_Field SET DisplayLogic='@AuthType/X@=''OAuth''',Updated=TO_TIMESTAMP('2025-11-05 15:59:00.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755950
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> OAuth_OAuth
-- 2025-11-05T16:01:43.123Z
UPDATE AD_Ref_List_Trl SET Description='Mit den Zugansdaten wird zuerst ein temporares Token abgerufen, dass dann intern für die eigentlichen Aufrufe verwendet wird.',Updated=TO_TIMESTAMP('2025-11-05 16:01:43.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544057
;

-- 2025-11-05T16:01:43.125Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> OAuth_OAuth
-- 2025-11-05T16:01:48.170Z
UPDATE AD_Ref_List_Trl SET Description='Mit den Zugansdaten wird zuerst ein temporares Token abgerufen, dass dann intern für die eigentlichen Aufrufe verwendet wird.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 16:01:48.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544057
;

-- 2025-11-05T16:01:48.171Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> OAuth_OAuth
-- 2025-11-05T16:02:25.262Z
UPDATE AD_Ref_List_Trl SET Description='The credentials are first used to retrieve a temporary token, which is then used internally for the actual calls.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 16:02:25.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544057
;

-- 2025-11-05T16:02:25.263Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> OAuth_OAuth
-- 2025-11-05T16:02:36.120Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 16:02:36.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544057
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> OAuth_OAuth
-- 2025-11-05T16:02:40.510Z
UPDATE AD_Ref_List_Trl SET Description='The credentials are first used to retrieve a temporary token, which is then used internally for the actual calls.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 16:02:40.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=544057
;

-- 2025-11-05T16:02:40.511Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> Token_Token
-- 2025-11-05T16:03:16.040Z
UPDATE AD_Ref_List_Trl SET Description='A static token is used for all http-API calls', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 16:03:16.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=544055
;

-- 2025-11-05T16:03:16.041Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> Token_Token
-- 2025-11-05T16:03:19.515Z
UPDATE AD_Ref_List_Trl SET Description='A static token is used for all http-API calls', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 16:03:19.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544055
;

-- 2025-11-05T16:03:19.516Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> Token_Token
-- 2025-11-05T16:03:32.188Z
UPDATE AD_Ref_List_Trl SET Description='Für alle HTTP-API-Aufrufe wird ein statisches Token verwendet.',Updated=TO_TIMESTAMP('2025-11-05 16:03:32.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544055
;

-- 2025-11-05T16:03:32.189Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> Token_Token
-- 2025-11-05T16:03:38.472Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 16:03:38.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544055
;

-- Reference Item: ExternalSystem_Outbound_Endpoint_AuthType -> Token_Token
-- 2025-11-05T16:03:46.085Z
UPDATE AD_Ref_List_Trl SET Description='Für alle HTTP-API-Aufrufe wird ein statisches Token verwendet.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-05 16:03:46.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544055
;

-- 2025-11-05T16:03:46.087Z
UPDATE AD_Ref_List base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;


-- Column: ExternalSystem_Outbound_Endpoint.EMail
-- 2025-11-05T16:20:38.299Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591487
;

-- 2025-11-05T16:20:38.319Z
DELETE FROM AD_Column WHERE AD_Column_ID=591487
;

