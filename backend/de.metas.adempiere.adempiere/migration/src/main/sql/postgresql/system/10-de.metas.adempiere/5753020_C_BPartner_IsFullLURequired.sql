-- Run mode: SWING_CLIENT

-- 2025-04-28T17:56:46.316Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583599,0,'IsFullLoadingUnitRequired',TO_TIMESTAMP('2025-04-28 17:56:45.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'This flag indicates whether the partner requires the loading unit (LU) to be full when adding product quantities to an order line. If the flag is set to ''Y'', it means the order line must contain a full loading unit. If it is set to ''N'', partial loading units are allowed.','D','This flag indicates whether the partner requires the loading unit (LU) to be full when adding product quantities to an order line. If the flag is set to ''Y'', it means the order line must contain a full loading unit. If it is set to ''N'', partial loading units are allowed.','Y','Full Loading Unit Required','Full Loading Unit Required',TO_TIMESTAMP('2025-04-28 17:56:45.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-28T17:56:46.352Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583599 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsFullLoadingUnitRequired
-- 2025-04-28T17:57:42.150Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vollständige LU Erforderlich', PrintName='Vollständige LU Erforderlich',Updated=TO_TIMESTAMP('2025-04-28 17:57:42.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583599 AND AD_Language='de_CH'
;

-- 2025-04-28T17:57:42.182Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-28T17:57:44.139Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583599,'de_CH')
;

-- Element: IsFullLoadingUnitRequired
-- 2025-04-28T17:58:11.184Z
UPDATE AD_Element_Trl SET Description='Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.', Help='Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.',Updated=TO_TIMESTAMP('2025-04-28 17:58:11.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583599 AND AD_Language='de_CH'
;

-- 2025-04-28T17:58:11.252Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-28T17:58:12.221Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583599,'de_CH')
;

-- Element: IsFullLoadingUnitRequired
-- 2025-04-28T17:58:19.667Z
UPDATE AD_Element_Trl SET Description='Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.', Help='Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.',Updated=TO_TIMESTAMP('2025-04-28 17:58:19.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583599 AND AD_Language='de_DE'
;

-- 2025-04-28T17:58:19.699Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-28T17:58:21.357Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583599,'de_DE')
;

-- 2025-04-28T17:58:21.384Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583599,'de_DE')
;

-- Element: IsFullLoadingUnitRequired
-- 2025-04-28T17:58:29.019Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vollständige LU Erforderlich', PrintName='Vollständige LU Erforderlich',Updated=TO_TIMESTAMP('2025-04-28 17:58:29.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583599 AND AD_Language='de_DE'
;

-- 2025-04-28T17:58:29.047Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-28T17:58:29.872Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583599,'de_DE')
;

-- 2025-04-28T17:58:29.900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583599,'de_DE')
;

-- Element: IsFullLoadingUnitRequired
-- 2025-04-28T17:58:45.349Z
UPDATE AD_Element_Trl SET Name='Full LU Required', PrintName='Full LU Required',Updated=TO_TIMESTAMP('2025-04-28 17:58:45.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583599 AND AD_Language='en_US'
;

-- 2025-04-28T17:58:45.379Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-28T17:58:46.085Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583599,'en_US')
;

-- Element: IsFullLoadingUnitRequired
-- 2025-04-28T17:58:50.679Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-28 17:58:50.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583599 AND AD_Language='en_US'
;

-- 2025-04-28T17:58:50.735Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583599,'en_US')
;

-- 2025-04-28T18:00:46.714Z
UPDATE AD_Element SET ColumnName='IsFullLURequired',Updated=TO_TIMESTAMP('2025-04-28 18:00:46.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583599
;

-- 2025-04-28T18:00:46.742Z
UPDATE AD_Column SET ColumnName='IsFullLURequired' WHERE AD_Element_ID=583599
;

-- 2025-04-28T18:00:46.770Z
UPDATE AD_Process_Para SET ColumnName='IsFullLURequired' WHERE AD_Element_ID=583599
;

-- 2025-04-28T18:00:46.880Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583599,'de_DE')
;

-- Column: C_BPartner.IsFullLURequired
-- 2025-04-28T18:01:08.564Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589919,583599,0,20,291,'XX','IsFullLURequired',TO_TIMESTAMP('2025-04-28 18:01:08.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.','D',0,1,'Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Vollständige LU Erforderlich',0,0,TO_TIMESTAMP('2025-04-28 18:01:08.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-28T18:01:08.592Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589919 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-28T18:01:08.648Z
/* DDL */  select update_Column_Translation_From_AD_Element(583599)
;

-- 2025-04-28T18:01:12.824Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsFullLURequired CHAR(1) DEFAULT ''N'' CHECK (IsFullLURequired IN (''Y'',''N'')) NOT NULL')
;

-- Field: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> Vollständige LU Erforderlich
-- Column: C_BPartner.IsFullLURequired
-- 2025-04-28T18:02:21.901Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589919,741986,0,223,TO_TIMESTAMP('2025-04-28 18:02:21.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.',1,'D','Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.','Y','N','N','N','N','N','N','N','Vollständige LU Erforderlich',TO_TIMESTAMP('2025-04-28 18:02:21.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-28T18:02:21.931Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741986 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-28T18:02:21.960Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583599)
;

-- 2025-04-28T18:02:22.004Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741986
;

-- 2025-04-28T18:02:22.031Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741986)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Vollständige LU Erforderlich
-- Column: C_BPartner.IsFullLURequired
-- 2025-04-28T18:03:43.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741986,0,223,540672,631381,'F',TO_TIMESTAMP('2025-04-28 18:03:42.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.','Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.','Y','N','Y','N','N','Vollständige LU Erforderlich',330,0,0,TO_TIMESTAMP('2025-04-28 18:03:42.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Vollständige LU Erforderlich
-- Column: C_BPartner.IsFullLURequired
-- 2025-04-28T18:04:24.893Z
UPDATE AD_UI_Element SET SeqNo=287,Updated=TO_TIMESTAMP('2025-04-28 18:04:24.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631381
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Vollständige LU Erforderlich
-- Column: C_BPartner.IsFullLURequired
-- 2025-04-28T18:05:34.696Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-04-28 18:05:34.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631381
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Sektion
-- Column: C_BPartner.AD_Org_ID
-- 2025-04-28T18:05:34.864Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-04-28 18:05:34.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545731
;

