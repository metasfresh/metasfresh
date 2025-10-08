-- Run mode: SWING_CLIENT

-- Column: C_OrderPaySchedule.C_Currency_ID
-- 2025-10-08T07:18:37.701Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591282,193,0,19,542539,'XX','C_Currency_ID',TO_TIMESTAMP('2025-10-08 07:18:37.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Die Währung für diesen Eintrag','D',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2025-10-08 07:18:37.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-08T07:18:37.710Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591282 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-08T07:18:37.741Z
/* DDL */  select update_Column_Translation_From_AD_Element(193)
;

-- Column: C_OrderPaySchedule.C_Currency_ID
-- 2025-10-08T07:18:48.796Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-10-08 07:18:48.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591282
;

-- 2025-10-08T07:18:50.277Z
/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE public.C_OrderPaySchedule ADD COLUMN C_Currency_ID NUMERIC(10) NOT NULL')
;

-- 2025-10-08T07:18:50.296Z
ALTER TABLE C_OrderPaySchedule ADD CONSTRAINT CCurrency_COrderPaySchedule FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> Währung
-- Column: C_OrderPaySchedule.C_Currency_ID
-- 2025-10-08T07:19:03.440Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591282,754842,0,548449,TO_TIMESTAMP('2025-10-08 07:19:03.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2025-10-08 07:19:03.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-08T07:19:03.444Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-08T07:19:03.447Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2025-10-08T07:19:03.585Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754842
;

-- 2025-10-08T07:19:03.592Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754842)
;

-- UI Element: Bestellung_OLD(181,D) -> Zahlungsplan(548449,D) -> main -> 10 -> main.Währung
-- Column: C_OrderPaySchedule.C_Currency_ID
-- 2025-10-08T07:19:34.238Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754842,0,548449,553582,637776,'F',TO_TIMESTAMP('2025-10-08 07:19:34.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','Y','N','N','Währung',42,50,0,TO_TIMESTAMP('2025-10-08 07:19:34.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_OrderPaySchedule.C_PaymentTerm_ID
-- 2025-10-08T09:38:32.071Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591283,204,0,19,542539,'XX','C_PaymentTerm_ID',TO_TIMESTAMP('2025-10-08 09:38:31.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Die Bedingungen für die Bezahlung dieses Vorgangs','D',0,10,'Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsbedingung',0,0,TO_TIMESTAMP('2025-10-08 09:38:31.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-08T09:38:32.080Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591283 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-08T09:38:32.092Z
/* DDL */  select update_Column_Translation_From_AD_Element(204)
;

-- 2025-10-08T09:38:34.913Z
/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE public.C_OrderPaySchedule ADD COLUMN C_PaymentTerm_ID NUMERIC(10) NOT NULL')
;

-- 2025-10-08T09:38:34.928Z
ALTER TABLE C_OrderPaySchedule ADD CONSTRAINT CPaymentTerm_COrderPaySchedule FOREIGN KEY (C_PaymentTerm_ID) REFERENCES public.C_PaymentTerm DEFERRABLE INITIALLY DEFERRED
;

-- Tab: Bestellung_OLD(181,D) -> Zahlungsplan
-- Table: C_OrderPaySchedule
-- 2025-10-08T11:24:51.159Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-10-08 11:24:51.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548449
;

-- Value: C_PaymentTerm_Break_TotalPercentTooHigh
-- 2025-10-08T11:46:23.387Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545595,0,TO_TIMESTAMP('2025-10-08 11:46:23.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','The total percentage for all payment term breaks {0} % exceeds 100% and cannot be saved.','I',TO_TIMESTAMP('2025-10-08 11:46:23.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_PaymentTerm_Break_TotalPercentTooHigh')
;

-- 2025-10-08T11:46:23.393Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545595 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_PaymentTerm_Break_TotalPercentTooHigh
-- 2025-10-08T11:47:00.927Z
UPDATE AD_Message SET EntityType='D', MsgType='E',Updated=TO_TIMESTAMP('2025-10-08 11:47:00.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545595
;

-- Value: C_PaymentTerm_Break_TotalPercentTooHigh
-- 2025-10-08T11:47:05.016Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Gesamtprozentsatz aller Zahlungsbedingungen ({0} %) überschreitet 100 % und kann nicht gespeichert werden.',Updated=TO_TIMESTAMP('2025-10-08 11:47:05.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545595
;

-- 2025-10-08T11:47:05.019Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_PaymentTerm_Break_TotalPercentTooHigh
-- 2025-10-08T11:47:09.490Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Gesamtprozentsatz aller Zahlungsbedingungen ({0} %) überschreitet 100 % und kann nicht gespeichert werden.',Updated=TO_TIMESTAMP('2025-10-08 11:47:09.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545595
;

-- 2025-10-08T11:47:09.492Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_PaymentTerm_Break_TotalPercentTooHigh
-- 2025-10-08T11:47:13.380Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-08 11:47:13.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545595
;

-- Value: OrderPaymentScheduleCreator_ComplexTermConflict
-- 2025-10-08T12:20:56.085Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545596,0,TO_TIMESTAMP('2025-10-08 12:20:55.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Payment Term {0} is flagged as complex and cannot coexist with Pay Schedule records.','E',TO_TIMESTAMP('2025-10-08 12:20:55.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'OrderPaymentScheduleCreator_ComplexTermConflict')
;

-- 2025-10-08T12:20:56.093Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545596 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: OrderPaymentScheduleCreator_ComplexTermConflict
-- 2025-10-08T12:21:29.891Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zahlungsbedingung {0} ist komplex und kann nicht mit Zahlungsplänen kombiniert werden.',Updated=TO_TIMESTAMP('2025-10-08 12:21:29.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545596
;

-- 2025-10-08T12:21:29.893Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: OrderPaymentScheduleCreator_ComplexTermConflict
-- 2025-10-08T12:21:37.732Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-08 12:21:37.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545596
;

-- Value: OrderPaymentScheduleCreator_ComplexTermConflict
-- 2025-10-08T12:21:40.936Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zahlungsbedingung {0} ist komplex und kann nicht mit Zahlungsplänen kombiniert werden.',Updated=TO_TIMESTAMP('2025-10-08 12:21:40.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545596
;

-- 2025-10-08T12:21:40.937Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;


-- Value: C_PaymentTerm_ComplexTermConflict
-- 2025-10-08T12:27:40.005Z
UPDATE AD_Message SET Value='C_PaymentTerm_ComplexTermConflict',Updated=TO_TIMESTAMP('2025-10-08 12:27:40.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545596
;

