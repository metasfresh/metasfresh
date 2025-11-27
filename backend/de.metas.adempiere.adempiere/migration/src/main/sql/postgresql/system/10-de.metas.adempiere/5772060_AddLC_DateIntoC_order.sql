

-- 2025-10-07T10:59:11.173Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584099,0,'LC_Date',TO_TIMESTAMP('2025-10-07 10:59:10.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Letter of Credit Date','Letter of Credit Date',TO_TIMESTAMP('2025-10-07 10:59:10.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-07T10:59:11.180Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584099 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LC_Date
-- 2025-10-07T10:59:16.259Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Akkreditivdatum', PrintName='Akkreditivdatum',Updated=TO_TIMESTAMP('2025-10-07 10:59:16.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584099 AND AD_Language='de_CH'
;

-- 2025-10-07T10:59:16.262Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T10:59:16.544Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584099,'de_CH')
;

-- Element: LC_Date
-- 2025-10-07T10:59:23.384Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Akkreditivdatum', PrintName='Akkreditivdatum',Updated=TO_TIMESTAMP('2025-10-07 10:59:23.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584099 AND AD_Language='de_DE'
;

-- 2025-10-07T10:59:23.386Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-07T10:59:24.010Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584099,'de_DE')
;

-- 2025-10-07T10:59:24.014Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584099,'de_DE')
;

-- Element: LC_Date
-- 2025-10-07T10:59:29.397Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-07 10:59:29.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584099 AND AD_Language='en_US'
;

-- 2025-10-07T10:59:29.401Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584099,'en_US')
;

-- Column: C_Order.LC_Date
-- 2025-10-07T10:59:43.149Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591278,584099,0,15,259,'XX','LC_Date',TO_TIMESTAMP('2025-10-07 10:59:43.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Akkreditivdatum',0,0,TO_TIMESTAMP('2025-10-07 10:59:43.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-07T10:59:43.153Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591278 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-07T10:59:43.159Z
/* DDL */  select update_Column_Translation_From_AD_Element(584099)
;

-- 2025-10-07T11:01:25.351Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN LC_Date TIMESTAMP WITHOUT TIME ZONE')
;

-- Reference: ReferenceDateType
-- Value: ET
-- ValueName: ETADate
-- 2025-10-07T11:06:55.198Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541989,543990,TO_TIMESTAMP('2025-10-07 11:06:55.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliche Ankunftszeit','D','Y','Vorauss. Ankunft',TO_TIMESTAMP('2025-10-07 11:06:55.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ET','ETADate')
;

-- 2025-10-07T11:06:55.201Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543990 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ReferenceDateType -> ET_ETADate
-- 2025-10-07T11:07:01.223Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-07 11:07:01.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543990
;

-- Reference Item: ReferenceDateType -> ET_ETADate
-- 2025-10-07T11:07:03.720Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-07 11:07:03.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543990
;

-- Reference Item: ReferenceDateType -> ET_ETADate
-- 2025-10-07T11:07:22.730Z
UPDATE AD_Ref_List_Trl SET Description='Estimated Time of Arrival', IsTranslated='Y', Name='ETA Date',Updated=TO_TIMESTAMP('2025-10-07 11:07:22.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543990
;

-- 2025-10-07T11:07:22.731Z
UPDATE AD_Ref_List base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Column: C_Order.C_BPartner_SalesRep_ID
-- 2025-10-07T11:23:35.192Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-07 11:23:35.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=568785
;

-- Column: C_PaymentTerm_Break.OffsetDays
-- 2025-10-07T11:27:21.024Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-07 11:27:21.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591200
;

-- Column: C_PaymentTerm_Break.OffsetDays
-- 2025-10-07T11:31:40.759Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-10-07 11:31:40.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591200
;

-- 2025-10-07T11:31:42.803Z
INSERT INTO t_alter_column values('c_paymentterm_break','OffsetDays','NUMERIC(10)',null,'0')
;

-- 2025-10-07T11:31:42.840Z
UPDATE C_PaymentTerm_Break SET OffsetDays=0 WHERE OffsetDays IS NULL
;

-- 2025-10-07T11:31:42.842Z
INSERT INTO t_alter_column values('c_paymentterm_break','OffsetDays',null,'NOT NULL',null)
;



-- Column: C_PaymentTerm_Break.Percent
-- 2025-10-07T11:40:31.864Z
UPDATE AD_Column SET DefaultValue='100', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-10-07 11:40:31.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591198
;

-- 2025-10-07T11:40:35.936Z
INSERT INTO t_alter_column values('c_paymentterm_break','Percent','NUMERIC',null,'100')
;

-- 2025-10-07T11:40:35.939Z
UPDATE C_PaymentTerm_Break SET Percent=100 WHERE Percent IS NULL
;

-- 2025-10-07T11:40:35.940Z
INSERT INTO t_alter_column values('c_paymentterm_break','Percent',null,'NOT NULL',null)
;

-- Column: C_PaymentTerm_Break.Percent
-- 2025-10-07T11:55:58.121Z
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2025-10-07 11:55:58.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591198
;

-- 2025-10-07T11:56:00.406Z
INSERT INTO t_alter_column values('c_paymentterm_break','Percent','NUMERIC(10)',null,'100')
;

-- 2025-10-07T11:56:00.422Z
UPDATE C_PaymentTerm_Break SET Percent=100 WHERE Percent IS NULL
;




-- Column: C_PaymentTerm_Break.SeqNo
-- 2025-10-07T13:05:18.892Z
UPDATE AD_Column SET AD_Element_ID=566, ColumnName='SeqNo', DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM C_PaymentTerm_Break WHERE C_PaymentTerm_ID=@C_PaymentTerm_ID@', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge', Name='Reihenfolge',Updated=TO_TIMESTAMP('2025-10-07 13:05:18.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591197
;

-- 2025-10-07T13:05:18.899Z
UPDATE AD_Column_Trl trl SET Name='Reihenfolge' WHERE AD_Column_ID=591197 AND AD_Language='de_DE'
;

-- 2025-10-07T13:05:18.901Z
UPDATE AD_Field SET Name='Reihenfolge', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Column_ID=591197
;

-- 2025-10-07T13:05:18.903Z
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- Column: C_PaymentTerm_Break.SeqNo
-- 2025-10-07T13:05:34.458Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-10-07 13:05:34.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591197
;

-- 2025-10-07T13:06:05.494Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm_Break','ALTER TABLE public.C_PaymentTerm_Break ADD COLUMN SeqNo NUMERIC(10)')
;

update C_PaymentTerm_Break set SeqNo=line ;

/* DDL */ SELECT public.db_alter_table('C_PaymentTerm_Break','ALTER TABLE public.C_PaymentTerm_Break DROP COLUMN Line')
;

-- Column: C_OrderPaySchedule.SeqNo
-- 2025-10-07T13:33:18.156Z
UPDATE AD_Column SET AD_Element_ID=566, ColumnName='SeqNo', DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM C_OrderPaySchedule WHERE C_Order_ID=@C_Order_ID@', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge', IsMandatory='N', Name='Reihenfolge',Updated=TO_TIMESTAMP('2025-10-07 13:33:18.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591210
;

-- 2025-10-07T13:33:18.159Z
UPDATE AD_Column_Trl trl SET Name='Reihenfolge' WHERE AD_Column_ID=591210 AND AD_Language='de_DE'
;

-- 2025-10-07T13:33:18.163Z
UPDATE AD_Field SET Name='Reihenfolge', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Column_ID=591210
;

-- 2025-10-07T13:33:18.167Z
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- 2025-10-07T13:33:20.370Z
/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE public.C_OrderPaySchedule ADD COLUMN SeqNo NUMERIC(10)')
;

update C_OrderPaySchedule set SeqNo=line ;

/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE public.C_OrderPaySchedule DROP COLUMN Line')
;
-- Column: C_OrderPaySchedule.Percent
-- 2025-10-07T13:35:22.052Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591279,951,0,11,542539,'XX','Percent',TO_TIMESTAMP('2025-10-07 13:35:21.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','0','Prozentsatz','D',0,22,'Der Prozentsatz gibt den verwendeten Prozentsatz an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Prozent',0,0,TO_TIMESTAMP('2025-10-07 13:35:21.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-07T13:35:22.058Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591279 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-07T13:35:22.189Z
/* DDL */  select update_Column_Translation_From_AD_Element(951)
;

-- 2025-10-07T13:35:23.681Z
/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE public.C_OrderPaySchedule ADD COLUMN Percent NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Column: C_OrderPaySchedule.SeqNo
-- 2025-10-07T13:35:30.869Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-10-07 13:35:30.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591210
;

-- 2025-10-07T13:35:32.645Z
INSERT INTO t_alter_column values('c_orderpayschedule','SeqNo','NUMERIC(10)',null,null)
;

-- 2025-10-07T13:35:32.650Z
INSERT INTO t_alter_column values('c_orderpayschedule','SeqNo',null,'NOT NULL',null)
;

-- Name: C_OrderPaySchedule_Status
-- 2025-10-07T13:36:26.022Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541993,TO_TIMESTAMP('2025-10-07 13:36:25.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_OrderPaySchedule_Status',TO_TIMESTAMP('2025-10-07 13:36:25.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-10-07T13:36:26.027Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541993 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Column: C_OrderPaySchedule.Status
-- 2025-10-07T13:36:36.988Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591280,3020,0,17,541993,542539,'XX','Status',TO_TIMESTAMP('2025-10-07 13:36:36.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,255,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Status',0,0,TO_TIMESTAMP('2025-10-07 13:36:36.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-07T13:36:36.991Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591280 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-07T13:36:36.994Z
/* DDL */  select update_Column_Translation_From_AD_Element(3020)
;

-- Column: C_OrderPaySchedule.Status
-- 2025-10-07T13:36:43.794Z
UPDATE AD_Column SET FieldLength=3,Updated=TO_TIMESTAMP('2025-10-07 13:36:43.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591280
;

-- 2025-10-07T13:36:45.249Z
/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE public.C_OrderPaySchedule ADD COLUMN Status VARCHAR(3)')
;

-- Reference: C_OrderPaySchedule_Status
-- Value: PR
-- ValueName: Pending
-- 2025-10-07T13:37:26.190Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541993,543991,TO_TIMESTAMP('2025-10-07 13:37:26.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Pending reference',TO_TIMESTAMP('2025-10-07 13:37:26.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PR','Pending')
;

-- 2025-10-07T13:37:26.194Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543991 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_OrderPaySchedule_Status
-- Value: PR
-- ValueName: Pending_Ref
-- 2025-10-07T13:38:37.715Z
UPDATE AD_Ref_List SET Description='Pending_Ref', ValueName='Pending_Ref',Updated=TO_TIMESTAMP('2025-10-07 13:38:37.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543991
;

-- 2025-10-07T13:38:37.716Z
UPDATE AD_Ref_List_Trl trl SET Description='Pending_Ref' WHERE AD_Ref_List_ID=543991 AND AD_Language='de_DE'
;

-- Reference Item: C_OrderPaySchedule_Status -> PR_Pending_Ref
-- 2025-10-07T13:38:50.068Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Referenz ausstehend',Updated=TO_TIMESTAMP('2025-10-07 13:38:50.068000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543991
;

-- 2025-10-07T13:38:50.070Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_OrderPaySchedule_Status -> PR_Pending_Ref
-- 2025-10-07T13:38:53.721Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Referenz ausstehend',Updated=TO_TIMESTAMP('2025-10-07 13:38:53.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543991
;

-- 2025-10-07T13:38:53.722Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: C_OrderPaySchedule_Status
-- Value: PR
-- ValueName: Pending_Ref
-- 2025-10-07T13:39:00.232Z
UPDATE AD_Ref_List SET Description='',Updated=TO_TIMESTAMP('2025-10-07 13:39:00.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543991
;

-- 2025-10-07T13:39:00.234Z
UPDATE AD_Ref_List_Trl trl SET Description='' WHERE AD_Ref_List_ID=543991 AND AD_Language='de_DE'
;

-- Reference Item: C_OrderPaySchedule_Status -> PR_Pending_Ref
-- 2025-10-07T13:39:04.107Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-07 13:39:04.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543991
;

-- Reference: C_OrderPaySchedule_Status
-- Value: WP
-- ValueName: Awaiting_Pay
-- 2025-10-07T13:40:27.851Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541993,543992,TO_TIMESTAMP('2025-10-07 13:40:27.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Waiting Payment',TO_TIMESTAMP('2025-10-07 13:40:27.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'WP','Awaiting_Pay')
;

-- 2025-10-07T13:40:27.854Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543992 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_OrderPaySchedule_Status -> WP_Awaiting_Pay
-- 2025-10-07T13:40:35.427Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-07 13:40:35.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543992
;

-- Reference Item: C_OrderPaySchedule_Status -> WP_Awaiting_Pay
-- 2025-10-07T13:40:39.224Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Zahlung wird erwartet',Updated=TO_TIMESTAMP('2025-10-07 13:40:39.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543992
;

-- 2025-10-07T13:40:39.225Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_OrderPaySchedule_Status -> WP_Awaiting_Pay
-- 2025-10-07T13:40:42.398Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Zahlung wird erwartet',Updated=TO_TIMESTAMP('2025-10-07 13:40:42.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543992
;

-- 2025-10-07T13:40:42.399Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: C_OrderPaySchedule_Status
-- Value: P
-- ValueName: Paid
-- 2025-10-07T13:41:16.194Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541993,543993,TO_TIMESTAMP('2025-10-07 13:41:16.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Paid',TO_TIMESTAMP('2025-10-07 13:41:16.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'P','Paid')
;

-- 2025-10-07T13:41:16.197Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543993 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_OrderPaySchedule_Status -> P_Paid
-- 2025-10-07T13:42:09.903Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-07 13:42:09.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543993
;

-- Reference Item: C_OrderPaySchedule_Status -> P_Paid
-- 2025-10-07T13:42:13.235Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Bezahlt',Updated=TO_TIMESTAMP('2025-10-07 13:42:13.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543993
;

-- 2025-10-07T13:42:13.237Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_OrderPaySchedule_Status -> P_Paid
-- 2025-10-07T13:42:16.033Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Bezahlt',Updated=TO_TIMESTAMP('2025-10-07 13:42:16.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543993
;

-- 2025-10-07T13:42:16.034Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;




-- Field: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> Prozent
-- Column: C_OrderPaySchedule.Percent
-- 2025-10-07T14:22:45.137Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591279,754840,0,548449,TO_TIMESTAMP('2025-10-07 14:22:44.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prozentsatz',22,'D','Der Prozentsatz gibt den verwendeten Prozentsatz an.','Y','N','N','N','N','N','N','N','Prozent',TO_TIMESTAMP('2025-10-07 14:22:44.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-07T14:22:45.143Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-07T14:22:45.148Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(951)
;

-- 2025-10-07T14:22:45.171Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754840
;

-- 2025-10-07T14:22:45.182Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754840)
;

-- Field: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> Status
-- Column: C_OrderPaySchedule.Status
-- 2025-10-07T14:22:45.293Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591280,754841,0,548449,TO_TIMESTAMP('2025-10-07 14:22:45.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',3,'D','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2025-10-07 14:22:45.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-07T14:22:45.295Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-07T14:22:45.297Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020)
;

-- 2025-10-07T14:22:45.340Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754841
;

-- 2025-10-07T14:22:45.342Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754841)
;

-- UI Element: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> main -> 20 -> main.Status
-- Column: C_OrderPaySchedule.Status
-- 2025-10-07T14:23:32.037Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754841,0,548449,553583,637774,'F',TO_TIMESTAMP('2025-10-07 14:23:31.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','N','Y','Y','N','N','Status',12,60,0,TO_TIMESTAMP('2025-10-07 14:23:31.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> main -> 10 -> main.Prozent
-- Column: C_OrderPaySchedule.Percent
-- 2025-10-07T14:24:09.310Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754840,0,548449,553582,637775,'F',TO_TIMESTAMP('2025-10-07 14:24:09.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prozentsatz','Der Prozentsatz gibt den verwendeten Prozentsatz an.','Y','N','N','Y','Y','N','N','Prozent',32,30,0,TO_TIMESTAMP('2025-10-07 14:24:09.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> main -> 10 -> main.Referenzdatumstyp
-- Column: C_OrderPaySchedule.ReferenceDateType
-- 2025-10-07T14:24:20.073Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-07 14:24:20.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637527
;

-- UI Element: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> main -> 10 -> main.Fälliger Betrag
-- Column: C_OrderPaySchedule.DueAmt
-- 2025-10-07T14:24:20.084Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-07 14:24:20.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637528
;

-- UI Element: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> main -> 10 -> main.Datum Fälligkeit
-- Column: C_OrderPaySchedule.DueDate
-- 2025-10-07T14:24:20.094Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-07 14:24:20.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637529
;

-- UI Element: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> main -> 20 -> main.Status
-- Column: C_OrderPaySchedule.Status
-- 2025-10-07T14:24:20.102Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-07 14:24:20.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637774
;

-- UI Element: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> main -> 20 -> main.Aktiv
-- Column: C_OrderPaySchedule.IsActive
-- 2025-10-07T14:24:20.111Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-07 14:24:20.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637530
;

-- UI Element: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> main -> 20 -> main.Sektion
-- Column: C_OrderPaySchedule.AD_Org_ID
-- 2025-10-07T14:24:20.120Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-10-07 14:24:20.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637531
;

