

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

