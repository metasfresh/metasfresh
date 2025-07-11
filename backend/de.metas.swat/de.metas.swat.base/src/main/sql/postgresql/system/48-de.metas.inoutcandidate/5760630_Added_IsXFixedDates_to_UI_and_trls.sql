-- Run mode: SWING_CLIENT

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Versand ab zugesagtem Termin
-- Column: C_Order.IsPromisedFixedDate
-- 2025-07-10T12:01:40.665Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590501,749966,0,186,0,TO_TIMESTAMP('2025-07-10 12:01:40.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stellt sicher, dass der Versand nicht vor dem zugesagten Termin erfolgt. Wird verwendet, wenn der Kunde eine genaue Einhaltung des Lieferzeitpunkts verlangt.',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Versand ab zugesagtem Termin',0,0,780,0,1,1,TO_TIMESTAMP('2025-07-10 12:01:40.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-10T12:01:40.667Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T12:01:40.692Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583783)
;

-- 2025-07-10T12:01:40.704Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749966
;

-- 2025-07-10T12:01:40.707Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749966)
;

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Kommissionierung nicht vor Bereitstellungsdatum
-- Column: C_Order.IsProvisioningFixedDate
-- 2025-07-10T12:01:57.084Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590502,749967,0,186,0,TO_TIMESTAMP('2025-07-10 12:01:56.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verhindert die Kommissionierung vor dem Bereitstellungsdatum. Wird verwendet, wenn Materialien nicht vor dem geplanten Zeitpunkt bereitgestellt werden dürfen.',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Kommissionierung nicht vor Bereitstellungsdatum',0,0,790,0,1,1,TO_TIMESTAMP('2025-07-10 12:01:56.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-10T12:01:57.086Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T12:01:57.087Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583784)
;

-- 2025-07-10T12:01:57.089Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749967
;

-- 2025-07-10T12:01:57.090Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749967)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> Dates.Versand ab zugesagtem Termin
-- Column: C_Order.IsPromisedFixedDate
-- 2025-07-10T12:31:12.375Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749966,0,186,540920,635164,'F',TO_TIMESTAMP('2025-07-10 12:31:12.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stellt sicher, dass der Versand nicht vor dem zugesagten Termin erfolgt. Wird verwendet, wenn der Kunde eine genaue Einhaltung des Lieferzeitpunkts verlangt.','Y','N','N','Y','N','N','N',0,'Versand ab zugesagtem Termin',20,0,0,TO_TIMESTAMP('2025-07-10 12:31:12.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> Dates.Kommissionierung nicht vor Bereitstellungsdatum
-- Column: C_Order.IsProvisioningFixedDate
-- 2025-07-10T12:32:08.744Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749967,0,186,540920,635165,'F',TO_TIMESTAMP('2025-07-10 12:32:08.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verhindert die Kommissionierung vor dem Bereitstellungsdatum. Wird verwendet, wenn Materialien nicht vor dem geplanten Zeitpunkt bereitgestellt werden dürfen.','Y','N','N','Y','N','N','N',0,'Kommissionierung nicht vor Bereitstellungsdatum',45,0,0,TO_TIMESTAMP('2025-07-10 12:32:08.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Versand ab zugesagtem Termin
-- Column: C_Order.IsPromisedFixedDate
-- 2025-07-10T12:34:29.785Z
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2025-07-10 12:34:29.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=749966
;

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Kommissionierung nicht vor Bereitstellungsdatum
-- Column: C_Order.IsProvisioningFixedDate
-- 2025-07-10T12:34:33.358Z
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2025-07-10 12:34:33.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=749967
;

-- Element: IsPromisedFixedDate
-- 2025-07-10T13:42:48.044Z
UPDATE AD_Element_Trl SET Name='Versand ab Termin', PrintName='Versand ab Termin',Updated=TO_TIMESTAMP('2025-07-10 13:42:48.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583783 AND AD_Language='de_CH'
;

-- 2025-07-10T13:42:48.045Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T13:42:48.309Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583783,'de_CH')
;

-- Element: IsPromisedFixedDate
-- 2025-07-10T13:42:50.371Z
UPDATE AD_Element_Trl SET Name='Versand ab Termin', PrintName='Versand ab Termin',Updated=TO_TIMESTAMP('2025-07-10 13:42:50.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583783 AND AD_Language='fr_CH'
;

-- 2025-07-10T13:42:50.373Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T13:42:50.644Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583783,'fr_CH')
;

-- Element: IsPromisedFixedDate
-- 2025-07-10T13:42:56.890Z
UPDATE AD_Element_Trl SET Name='Versand ab Termin', PrintName='Versand ab Termin',Updated=TO_TIMESTAMP('2025-07-10 13:42:56.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583783 AND AD_Language='de_DE'
;

-- 2025-07-10T13:42:56.891Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T13:42:57.749Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583783,'de_DE')
;

-- 2025-07-10T13:42:57.751Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583783,'de_DE')
;

-- Element: IsPromisedFixedDate
-- 2025-07-10T13:44:03.823Z
UPDATE AD_Element_Trl SET Name='Ship After Date', PrintName='Ship After Date',Updated=TO_TIMESTAMP('2025-07-10 13:44:03.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583783 AND AD_Language='en_US'
;

-- 2025-07-10T13:44:03.824Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T13:44:04.074Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583783,'en_US')
;

-- Element: IsProvisioningFixedDate
-- 2025-07-10T13:45:32.723Z
UPDATE AD_Element_Trl SET Name='Picking After Date', PrintName='Picking After Date',Updated=TO_TIMESTAMP('2025-07-10 13:45:32.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583784 AND AD_Language='en_US'
;

-- 2025-07-10T13:45:32.724Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T13:45:32.984Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583784,'en_US')
;

-- Element: IsProvisioningFixedDate
-- 2025-07-10T13:45:35.090Z
UPDATE AD_Element_Trl SET Name='Kommissionierung ab Datum', PrintName='Kommissionierung ab Datum',Updated=TO_TIMESTAMP('2025-07-10 13:45:35.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583784 AND AD_Language='de_CH'
;

-- 2025-07-10T13:45:35.090Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T13:45:35.347Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583784,'de_CH')
;

-- Element: IsProvisioningFixedDate
-- 2025-07-10T13:45:37.390Z
UPDATE AD_Element_Trl SET Name='Kommissionierung ab Datum', PrintName='Kommissionierung ab Datum',Updated=TO_TIMESTAMP('2025-07-10 13:45:37.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583784 AND AD_Language='fr_CH'
;

-- 2025-07-10T13:45:37.391Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T13:45:37.651Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583784,'fr_CH')
;

-- Element: IsProvisioningFixedDate
-- 2025-07-10T13:45:40.334Z
UPDATE AD_Element_Trl SET Name='Kommissionierung ab Datum', PrintName='Kommissionierung ab Datum',Updated=TO_TIMESTAMP('2025-07-10 13:45:40.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583784 AND AD_Language='de_DE'
;

-- 2025-07-10T13:45:40.335Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-10T13:45:40.894Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583784,'de_DE')
;

-- 2025-07-10T13:45:40.895Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583784,'de_DE')
;

-- Column: M_Packageable_V.DatePromised
-- 2025-07-10T16:39:09.452Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590524,269,0,15,540823,'XX','DatePromised',TO_TIMESTAMP('2025-07-10 16:39:09.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Zugesagter Termin für diesen Auftrag','de.metas.inoutcandidate',0,7,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Zugesagter Termin',0,0,TO_TIMESTAMP('2025-07-10 16:39:09.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-10T16:39:09.455Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590524 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T16:39:09.460Z
/* DDL */  select update_Column_Translation_From_AD_Element(269)
;

-- Value: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords
-- 2025-07-10T17:36:24.001Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545570,0,TO_TIMESTAMP('2025-07-10 17:36:23.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Für die Auswahl kann keine Lieferung erstellt werden. Prüfen Sie, ob Einschränkungen bezüglich des zugesagten Termins oder des Bereitstellungsdatums bestehen.','E',TO_TIMESTAMP('2025-07-10 17:36:23.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords')
;

-- 2025-07-10T17:36:24.004Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545570 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords
-- 2025-07-10T17:37:51.290Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No shipments can be created for the selection. Check if any restrictions exist pertaining to ''Date Promised'' or ''Date of Provisioning''.',Updated=TO_TIMESTAMP('2025-07-10 17:37:51.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545570
;

-- 2025-07-10T17:37:51.291Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

