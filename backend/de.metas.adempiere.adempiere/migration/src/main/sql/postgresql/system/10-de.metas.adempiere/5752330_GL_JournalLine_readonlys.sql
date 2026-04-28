-- Run mode: SWING_CLIENT

-- 2025-04-18T07:15:37.221Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583587,0,'DR_Locator_ID',TO_TIMESTAMP('2025-04-18 07:15:37.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Lagerort (Soll)','Lagerort (Soll)',TO_TIMESTAMP('2025-04-18 07:15:37.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-18T07:15:37.225Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583587 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DR_Locator_ID
-- 2025-04-18T07:15:41.977Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-18 07:15:41.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583587 AND AD_Language='de_CH'
;

-- 2025-04-18T07:15:41.982Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583587,'de_CH')
;

-- Element: DR_Locator_ID
-- 2025-04-18T07:15:49.617Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Locator (Debit)', PrintName='Locator (Debit)',Updated=TO_TIMESTAMP('2025-04-18 07:15:49.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583587 AND AD_Language='en_US'
;

-- 2025-04-18T07:15:49.619Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-18T07:15:49.995Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583587,'en_US')
;

-- Element: DR_Locator_ID
-- 2025-04-18T07:15:51.371Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-18 07:15:51.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583587 AND AD_Language='de_DE'
;

-- 2025-04-18T07:15:51.373Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583587,'de_DE')
;

-- 2025-04-18T07:15:51.374Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583587,'de_DE')
;

-- 2025-04-18T07:16:10.110Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583588,0,'CR_Locator_ID',TO_TIMESTAMP('2025-04-18 07:16:09.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Lagerort (Haben)','Lagerort (Haben)',TO_TIMESTAMP('2025-04-18 07:16:09.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-18T07:16:10.111Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583588 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CR_Locator_ID
-- 2025-04-18T07:16:14.429Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-18 07:16:14.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583588 AND AD_Language='de_DE'
;

-- 2025-04-18T07:16:14.433Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583588,'de_DE')
;

-- 2025-04-18T07:16:14.435Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583588,'de_DE')
;

-- Element: CR_Locator_ID
-- 2025-04-18T07:16:20.621Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Locator (Credit)', PrintName='Locator (Credit)',Updated=TO_TIMESTAMP('2025-04-18 07:16:20.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583588 AND AD_Language='en_US'
;

-- 2025-04-18T07:16:20.622Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-18T07:16:20.964Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583588,'en_US')
;

-- Element: CR_Locator_ID
-- 2025-04-18T07:16:27.336Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-18 07:16:27.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583588 AND AD_Language='de_CH'
;

-- 2025-04-18T07:16:27.339Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583588,'de_CH')
;

-- Column: GL_JournalLine.DR_Locator_ID
-- 2025-04-18T07:17:21.087Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589916,583587,0,30,191,226,'XX','DR_Locator_ID',TO_TIMESTAMP('2025-04-18 07:17:20.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lagerort (Soll)',0,0,TO_TIMESTAMP('2025-04-18 07:17:20.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-18T07:17:21.094Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589916 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-18T07:17:21.099Z
/* DDL */  select update_Column_Translation_From_AD_Element(583587)
;

-- 2025-04-18T07:17:24.304Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN DR_Locator_ID NUMERIC(10)')
;

-- 2025-04-18T07:17:24.674Z
ALTER TABLE GL_JournalLine ADD CONSTRAINT DRLocator_GLJournalLine FOREIGN KEY (DR_Locator_ID) REFERENCES public.M_Locator DEFERRABLE INITIALLY DEFERRED
;

-- Column: GL_JournalLine.CR_Locator_ID
-- 2025-04-18T07:17:40.642Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589917,583588,0,30,191,226,'XX','CR_Locator_ID',TO_TIMESTAMP('2025-04-18 07:17:40.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lagerort (Haben)',0,0,TO_TIMESTAMP('2025-04-18 07:17:40.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-18T07:17:40.645Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589917 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-18T07:17:40.782Z
/* DDL */  select update_Column_Translation_From_AD_Element(583588)
;

-- 2025-04-18T07:17:42.813Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN CR_Locator_ID NUMERIC(10)')
;

-- 2025-04-18T07:17:42.941Z
ALTER TABLE GL_JournalLine ADD CONSTRAINT CRLocator_GLJournalLine FOREIGN KEY (CR_Locator_ID) REFERENCES public.M_Locator DEFERRABLE INITIALLY DEFERRED
;

-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Lagerort (Soll)
-- Column: GL_JournalLine.DR_Locator_ID
-- 2025-04-18T07:17:58.097Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589916,741979,0,540855,TO_TIMESTAMP('2025-04-18 07:17:57.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Lagerort (Soll)',TO_TIMESTAMP('2025-04-18 07:17:57.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-18T07:17:58.099Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-18T07:17:58.102Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583587)
;

-- 2025-04-18T07:17:58.106Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741979
;

-- 2025-04-18T07:17:58.110Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741979)
;

-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Lagerort (Haben)
-- Column: GL_JournalLine.CR_Locator_ID
-- 2025-04-18T07:17:58.242Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589917,741980,0,540855,TO_TIMESTAMP('2025-04-18 07:17:58.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Lagerort (Haben)',TO_TIMESTAMP('2025-04-18 07:17:58.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-18T07:17:58.244Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-18T07:17:58.246Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583588)
;

-- 2025-04-18T07:17:58.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741980
;

-- 2025-04-18T07:17:58.250Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741980)
;

-- UI Element: Hauptbuch Journal(540356,D) -> Position(540855,D) -> accounts and amounts -> 10 -> DR.Lagerort (Soll)
-- Column: GL_JournalLine.DR_Locator_ID
-- 2025-04-18T07:18:23.123Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741979,0,540855,542600,631374,'F',TO_TIMESTAMP('2025-04-18 07:18:21.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Lagerort (Soll)',70,0,0,TO_TIMESTAMP('2025-04-18 07:18:21.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Hauptbuch Journal(540356,D) -> Position(540855,D) -> accounts and amounts -> 20 -> CR.Lagerort (Haben)
-- Column: GL_JournalLine.CR_Locator_ID
-- 2025-04-18T07:18:37.117Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741980,0,540855,542601,631375,'F',TO_TIMESTAMP('2025-04-18 07:18:36.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Lagerort (Haben)',70,0,0,TO_TIMESTAMP('2025-04-18 07:18:36.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: GL_JournalLine.DR_AccountConceptualName
-- 2025-04-18T08:09:11.618Z
UPDATE AD_Column SET ReadOnlyLogic='@IsAllowAccountDR@=N',Updated=TO_TIMESTAMP('2025-04-18 08:09:11.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589913
;

-- Column: GL_JournalLine.DR_AccountConceptualName
-- 2025-04-18T08:10:05.681Z
UPDATE AD_Column SET ReadOnlyLogic='@IsAllowAccountDR@=N | @Type@=T',Updated=TO_TIMESTAMP('2025-04-18 08:10:05.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589913
;

-- Column: GL_JournalLine.DR_C_Order_ID
-- 2025-04-18T08:10:13.215Z
UPDATE AD_Column SET ReadOnlyLogic='@IsAllowAccountDR@=N | @Type@=T',Updated=TO_TIMESTAMP('2025-04-18 08:10:13.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=585368
;

-- Column: GL_JournalLine.DR_Locator_ID
-- 2025-04-18T08:10:15.437Z
UPDATE AD_Column SET ReadOnlyLogic='@IsAllowAccountDR@=N | @Type@=T',Updated=TO_TIMESTAMP('2025-04-18 08:10:15.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589916
;

-- Column: GL_JournalLine.DR_M_Product_ID
-- 2025-04-18T08:10:18.022Z
UPDATE AD_Column SET ReadOnlyLogic='@IsAllowAccountDR@=N | @Type@=T',Updated=TO_TIMESTAMP('2025-04-18 08:10:18.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=585366
;

-- Column: GL_JournalLine.CR_AccountConceptualName
-- 2025-04-18T08:10:54.414Z
UPDATE AD_Column SET ReadOnlyLogic='@IsAllowAccountCR@=N',Updated=TO_TIMESTAMP('2025-04-18 08:10:54.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589914
;

-- Column: GL_JournalLine.CR_C_Order_ID
-- 2025-04-18T08:10:57.951Z
UPDATE AD_Column SET ReadOnlyLogic='@IsAllowAccountCR@=N | @Type@=T',Updated=TO_TIMESTAMP('2025-04-18 08:10:57.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=585369
;

-- Column: GL_JournalLine.CR_Locator_ID
-- 2025-04-18T08:10:59.693Z
UPDATE AD_Column SET ReadOnlyLogic='@IsAllowAccountCR@=N | @Type@=T',Updated=TO_TIMESTAMP('2025-04-18 08:10:59.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589917
;

-- Column: GL_JournalLine.CR_M_Product_ID
-- 2025-04-18T08:11:02.104Z
UPDATE AD_Column SET ReadOnlyLogic='@IsAllowAccountCR@=N | @Type@=T',Updated=TO_TIMESTAMP('2025-04-18 08:11:02.104000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=585367
;

