-- Field: Picking Job -> Picking Job -> Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- 2023-10-16T10:47:51.087Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587553,721584,0,544861,TO_TIMESTAMP('2023-10-16 13:47:50','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Lieferdatum',TO_TIMESTAMP('2023-10-16 13:47:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T10:47:51.089Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721584 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T10:47:51.091Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541376) 
;

-- 2023-10-16T10:47:51.107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721584
;

-- 2023-10-16T10:47:51.111Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721584)
;

-- Field: Picking Job -> Picking Job -> Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- 2023-10-16T10:48:11.888Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2023-10-16 13:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721584
;

-- Field: Picking Job -> Picking Job -> Mitarbeiter
-- Column: M_Picking_Job.Picking_User_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Mitarbeiter
-- Column: M_Picking_Job.Picking_User_ID
-- 2023-10-16T10:48:11.895Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2023-10-16 13:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669039
;

-- Field: Picking Job -> Picking Job -> Picking Slot
-- Column: M_Picking_Job.M_PickingSlot_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Picking Slot
-- Column: M_Picking_Job.M_PickingSlot_ID
-- 2023-10-16T10:48:11.903Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2023-10-16 13:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669042
;

-- Field: Picking Job -> Picking Job -> Belegstatus
-- Column: M_Picking_Job.DocStatus
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Belegstatus
-- Column: M_Picking_Job.DocStatus
-- 2023-10-16T10:48:11.910Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2023-10-16 13:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669040
;

-- Field: Picking Job -> Picking Job -> Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- 2023-10-16T10:48:20.310Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-10-16 13:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721584
;

-- Field: Picking Job -> Picking Job -> Mitarbeiter
-- Column: M_Picking_Job.Picking_User_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Mitarbeiter
-- Column: M_Picking_Job.Picking_User_ID
-- 2023-10-16T10:48:20.318Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-10-16 13:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669039
;

-- Field: Picking Job -> Picking Job -> Picking Slot
-- Column: M_Picking_Job.M_PickingSlot_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Picking Slot
-- Column: M_Picking_Job.M_PickingSlot_ID
-- 2023-10-16T10:48:20.325Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-10-16 13:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669042
;

-- Field: Picking Job -> Picking Job -> Belegstatus
-- Column: M_Picking_Job.DocStatus
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Belegstatus
-- Column: M_Picking_Job.DocStatus
-- 2023-10-16T10:48:20.333Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-10-16 13:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669040
;

