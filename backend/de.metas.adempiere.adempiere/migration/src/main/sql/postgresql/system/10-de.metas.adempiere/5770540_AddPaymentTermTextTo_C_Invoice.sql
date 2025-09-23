-- Run mode: SWING_CLIENT

-- 2025-09-23T11:12:29.393Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583998,0,'PaymentTermText',TO_TIMESTAMP('2025-09-23 11:12:29.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Payment term text that computed based on the payment term details and invoice data','D','Y','Payment Term Text','Payment Term Text',TO_TIMESTAMP('2025-09-23 11:12:29.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-23T11:12:29.402Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583998 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PaymentTermText
-- 2025-09-23T11:14:02.372Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zahlungsbedingungstext', PrintName='Zahlungsbedingungstext',Updated=TO_TIMESTAMP('2025-09-23 11:14:02.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583998 AND AD_Language='de_CH'
;

-- 2025-09-23T11:14:02.374Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-23T11:14:02.661Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583998,'de_CH')
;

-- Element: PaymentTermText
-- 2025-09-23T11:14:07.617Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zahlungsbedingungstext', PrintName='Zahlungsbedingungstext',Updated=TO_TIMESTAMP('2025-09-23 11:14:07.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583998 AND AD_Language='de_DE'
;

-- 2025-09-23T11:14:07.620Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-23T11:14:08.591Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583998,'de_DE')
;

-- 2025-09-23T11:14:08.593Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583998,'de_DE')
;

-- Element: PaymentTermText
-- 2025-09-23T11:14:48.589Z
UPDATE AD_Element_Trl SET Description='Zahlungsbedingungstext, der aus den Details der Zahlungsbedingungen und den Rechnungsdaten ermittelt wird.',Updated=TO_TIMESTAMP('2025-09-23 11:14:48.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583998 AND AD_Language='de_DE'
;

-- 2025-09-23T11:14:48.591Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-23T11:14:49.167Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583998,'de_DE')
;

-- 2025-09-23T11:14:49.169Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583998,'de_DE')
;

-- Element: PaymentTermText
-- 2025-09-23T11:14:52.085Z
UPDATE AD_Element_Trl SET Description='Zahlungsbedingungstext, der aus den Details der Zahlungsbedingungen und den Rechnungsdaten ermittelt wird.',Updated=TO_TIMESTAMP('2025-09-23 11:14:52.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583998 AND AD_Language='de_CH'
;

-- 2025-09-23T11:14:52.087Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-23T11:14:52.333Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583998,'de_CH')
;

-- Element: PaymentTermText
-- 2025-09-23T11:15:00.203Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-23 11:15:00.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583998 AND AD_Language='en_US'
;

-- 2025-09-23T11:15:00.208Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583998,'en_US')
;

-- Column: C_Invoice.PaymentTermText
-- 2025-09-23T11:15:47.430Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591006,583998,0,10,318,'XX','PaymentTermText',TO_TIMESTAMP('2025-09-23 11:15:47.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','@paymenttermtext@','Zahlungsbedingungstext, der aus den Details der Zahlungsbedingungen und den Rechnungsdaten ermittelt wird.','D',0,2000,'Y','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsbedingungstext',0,0,TO_TIMESTAMP('2025-09-23 11:15:47.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-23T11:15:47.433Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591006 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-23T11:15:47.438Z
/* DDL */  select update_Column_Translation_From_AD_Element(583998)
;

-- 2025-09-23T11:15:57.131Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN PaymentTermText VARCHAR(2000)')
;

-- Value: PayUntil
-- 2025-09-23T15:55:03.376Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545582,0,TO_TIMESTAMP('2025-09-23 15:55:03.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Zahlung bis','I',TO_TIMESTAMP('2025-09-23 15:55:03.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PayUntil')
;

-- 2025-09-23T15:55:03.386Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545582 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: PayUntil
-- 2025-09-23T15:55:06.832Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-23 15:55:06.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545582
;

-- Value: PayUntil
-- 2025-09-23T15:55:20.533Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Pay until',Updated=TO_TIMESTAMP('2025-09-23 15:55:20.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545582
;

-- 2025-09-23T15:55:20.536Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: PayUntil
-- 2025-09-23T15:55:26.404Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-23 15:55:26.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545582
;

-- Value: paymentterm.payby
-- 2025-09-23T15:58:30.399Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545583,0,TO_TIMESTAMP('2025-09-23 15:58:30.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','über','I',TO_TIMESTAMP('2025-09-23 15:58:30.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'paymentterm.payby')
;

-- 2025-09-23T15:58:30.401Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545583 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

