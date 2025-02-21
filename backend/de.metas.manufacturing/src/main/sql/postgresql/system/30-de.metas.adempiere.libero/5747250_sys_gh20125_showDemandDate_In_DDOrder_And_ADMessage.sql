-- Run mode: SWING_CLIENT

-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Kommissionierdatum
-- Column: DD_Order.PickDate
-- 2025-02-21T04:54:48.546Z
UPDATE AD_Field SET DisplayLogic='@DeliveryViaRule@=S | @DeliveryViaRule@=P',Updated=TO_TIMESTAMP('2025-02-21 04:54:48.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=54209
;

-- Value: org.eevolution.model.validator.DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED
-- 2025-02-21T04:56:13.988Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545501,0,TO_TIMESTAMP('2025-02-21 04:56:13.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Changes are not allowed. There are Distribution Candidates that are already processed.','E',TO_TIMESTAMP('2025-02-21 04:56:13.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'org.eevolution.model.validator.DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED')
;

-- 2025-02-21T04:56:14.009Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545501 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: org.eevolution.model.validator.DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED
-- 2025-02-21T04:57:22.286Z
UPDATE AD_Message_Trl SET MsgText='Änderungen sind nicht zulässig. Es gibt bereits verarbeitete Distributionskandidaten.',Updated=TO_TIMESTAMP('2025-02-21 04:57:22.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545501
;

-- 2025-02-21T04:57:22.287Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: org.eevolution.model.validator.DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED
-- 2025-02-21T04:57:25.422Z
UPDATE AD_Message_Trl SET MsgText='Änderungen sind nicht zulässig. Es gibt bereits verarbeitete Distributionskandidaten.',Updated=TO_TIMESTAMP('2025-02-21 04:57:25.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545501
;

-- 2025-02-21T04:57:25.422Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: org.eevolution.model.validator.DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED
-- 2025-02-21T04:57:31.715Z
UPDATE AD_Message_Trl SET MsgText='Änderungen sind nicht zulässig. Es gibt bereits verarbeitete Distributionskandidaten.',Updated=TO_TIMESTAMP('2025-02-21 04:57:31.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545501
;

-- 2025-02-21T04:57:31.717Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- UI Element: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20 -> docno.Kommissionierdatum
-- Column: DD_Order.PickDate
-- 2025-02-21T04:59:51.129Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,54209,0,53055,629842,540427,'F',TO_TIMESTAMP('2025-02-21 04:59:50.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum/Zeit der Kommissionierung für die Lieferung','Y','N','N','Y','N','N','N',0,'Kommissionierdatum',50,0,0,TO_TIMESTAMP('2025-02-21 04:59:50.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Kommissionierdatum
-- Column: DD_Order.PickDate
-- 2025-02-21T05:02:06.573Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-02-21 05:02:06.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=54209
;

-- UI Element: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> advanced edit -> 10 -> advanced edit.Kommissionier-Datum
-- Column: DD_Order.PickDate
-- 2025-02-21T05:23:02.616Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544247
;

-- Name: RelType PP_Order -> DD_Order_Candidate
-- 2025-02-21T05:27:17.440Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541930,TO_TIMESTAMP('2025-02-21 05:27:17.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EE01','Y','N','RelType PP_Order -> DD_Order_Candidate',TO_TIMESTAMP('2025-02-21 05:27:17.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-02-21T05:27:17.446Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541930 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: RelType PP_Order -> DD_Order_Candidate
-- Table: DD_Order_Candidate
-- Key: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2025-02-21T05:30:22.371Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,588723,0,541930,542424,TO_TIMESTAMP('2025-02-21 05:30:22.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-02-21 05:30:22.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Forward_PP_Order_ID = @PP_Order_ID@')
;

-- Name: PP_Order -> DD_Order_Candidate
-- Source Reference: RelType PP_Order -> DD_Order_Candidate
-- Target Reference: -
-- 2025-02-21T05:31:27.821Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541930,540451,TO_TIMESTAMP('2025-02-21 05:31:27.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','N','PP_Order -> DD_Order_Candidate',TO_TIMESTAMP('2025-02-21 05:31:27.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: PP_Order -> DD_Order_Candidate
-- Source Reference: PP_Order
-- Target Reference: RelType PP_Order -> DD_Order_Candidate
-- 2025-02-21T05:31:55.746Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=540503, AD_Reference_Target_ID=541930, EntityType='EE01',Updated=TO_TIMESTAMP('2025-02-21 05:31:55.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540451
;

-- Reference: RelType PP_Order -> DD_Order_Candidate
-- Table: DD_Order_Candidate
-- Key: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2025-02-21T05:39:39.580Z
UPDATE AD_Ref_Table SET AD_Window_ID=541807,Updated=TO_TIMESTAMP('2025-02-21 05:39:39.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541930
;

