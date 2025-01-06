-- Field: Material Disposition -> Bereitstellungsdetail -> Distributionsdisposition
-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Distributionsdisposition
-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- 2024-08-06T11:52:55.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588901,729788,0,540821,TO_TIMESTAMP('2024-08-06 14:52:55','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Distributionsdisposition',TO_TIMESTAMP('2024-08-06 14:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-06T11:52:55.496Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-06T11:52:55.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583179) 
;

-- 2024-08-06T11:52:55.505Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729788
;

-- 2024-08-06T11:52:55.506Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729788)
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Distributionsdisposition
-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Distributionsdisposition
-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- 2024-08-06T11:53:09.641Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-08-06 14:53:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729788
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Aktiv
-- Column: MD_Candidate_Dist_Detail.IsActive
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Aktiv
-- Column: MD_Candidate_Dist_Detail.IsActive
-- 2024-08-06T11:53:54.912Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558423
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Istmenge
-- Column: MD_Candidate_Dist_Detail.ActualQty
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Istmenge
-- Column: MD_Candidate_Dist_Detail.ActualQty
-- 2024-08-06T11:53:54.919Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561484
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Sofort Kommissionieren wenn möglich
-- Column: MD_Candidate_Dist_Detail.IsPickDirectlyIfFeasible
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Sofort Kommissionieren wenn möglich
-- Column: MD_Candidate_Dist_Detail.IsPickDirectlyIfFeasible
-- 2024-08-06T11:53:54.928Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561487
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Mandant
-- Column: MD_Candidate_Dist_Detail.AD_Client_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Mandant
-- Column: MD_Candidate_Dist_Detail.AD_Client_ID
-- 2024-08-06T11:53:54.934Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558421
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Dispositionskandidat
-- Column: MD_Candidate_Dist_Detail.MD_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Dispositionskandidat
-- Column: MD_Candidate_Dist_Detail.MD_Candidate_ID
-- 2024-08-06T11:53:54.942Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558425
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Distributionsdisposition
-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Distributionsdisposition
-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- 2024-08-06T11:53:54.950Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729788
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Distribution Order
-- Column: MD_Candidate_Dist_Detail.DD_Order_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Distribution Order
-- Column: MD_Candidate_Dist_Detail.DD_Order_ID
-- 2024-08-06T11:53:54.960Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558428
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Distribution Order Line
-- Column: MD_Candidate_Dist_Detail.DD_OrderLine_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Distribution Order Line
-- Column: MD_Candidate_Dist_Detail.DD_OrderLine_ID
-- 2024-08-06T11:53:54.968Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558429
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Produktionsstätte
-- Column: MD_Candidate_Dist_Detail.PP_Plant_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Produktionsstätte
-- Column: MD_Candidate_Dist_Detail.PP_Plant_ID
-- 2024-08-06T11:53:54.975Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558432
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Network Distribution Line
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistributionLine_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Network Distribution Line
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistributionLine_ID
-- 2024-08-06T11:53:54.984Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558430
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Sektion
-- Column: MD_Candidate_Dist_Detail.AD_Org_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Sektion
-- Column: MD_Candidate_Dist_Detail.AD_Org_ID
-- 2024-08-06T11:53:54.990Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558422
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Geplante Menge
-- Column: MD_Candidate_Dist_Detail.PlannedQty
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Geplante Menge
-- Column: MD_Candidate_Dist_Detail.PlannedQty
-- 2024-08-06T11:53:54.995Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-08-06 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561486
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Product Planning
-- Column: MD_Candidate_Dist_Detail.PP_Product_Planning_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Product Planning
-- Column: MD_Candidate_Dist_Detail.PP_Product_Planning_ID
-- 2024-08-06T11:53:55.002Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-08-06 14:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558427
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Lieferweg
-- Column: MD_Candidate_Dist_Detail.M_Shipper_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Lieferweg
-- Column: MD_Candidate_Dist_Detail.M_Shipper_ID
-- 2024-08-06T11:53:55.009Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-08-06 14:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558431
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Belegstatus
-- Column: MD_Candidate_Dist_Detail.DD_Order_DocStatus
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Belegstatus
-- Column: MD_Candidate_Dist_Detail.DD_Order_DocStatus
-- 2024-08-06T11:53:55.017Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-08-06 14:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558426
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Vom System vorgeschlagen
-- Column: MD_Candidate_Dist_Detail.IsAdvised
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Vom System vorgeschlagen
-- Column: MD_Candidate_Dist_Detail.IsAdvised
-- 2024-08-06T11:53:55.024Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-08-06 14:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561485
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Aktiv
-- Column: MD_Candidate_Dist_Detail.IsActive
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Aktiv
-- Column: MD_Candidate_Dist_Detail.IsActive
-- 2024-08-06T11:54:07.623Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558423
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Istmenge
-- Column: MD_Candidate_Dist_Detail.ActualQty
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Istmenge
-- Column: MD_Candidate_Dist_Detail.ActualQty
-- 2024-08-06T11:54:07.633Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561484
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Sofort Kommissionieren wenn möglich
-- Column: MD_Candidate_Dist_Detail.IsPickDirectlyIfFeasible
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Sofort Kommissionieren wenn möglich
-- Column: MD_Candidate_Dist_Detail.IsPickDirectlyIfFeasible
-- 2024-08-06T11:54:07.642Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561487
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Mandant
-- Column: MD_Candidate_Dist_Detail.AD_Client_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Mandant
-- Column: MD_Candidate_Dist_Detail.AD_Client_ID
-- 2024-08-06T11:54:07.650Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558421
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Dispositionskandidat
-- Column: MD_Candidate_Dist_Detail.MD_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Dispositionskandidat
-- Column: MD_Candidate_Dist_Detail.MD_Candidate_ID
-- 2024-08-06T11:54:07.658Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558425
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Distributionsdisposition
-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Distributionsdisposition
-- Column: MD_Candidate_Dist_Detail.DD_Order_Candidate_ID
-- 2024-08-06T11:54:07.665Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729788
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Distribution Order
-- Column: MD_Candidate_Dist_Detail.DD_Order_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Distribution Order
-- Column: MD_Candidate_Dist_Detail.DD_Order_ID
-- 2024-08-06T11:54:07.673Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558428
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Distribution Order Line
-- Column: MD_Candidate_Dist_Detail.DD_OrderLine_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Distribution Order Line
-- Column: MD_Candidate_Dist_Detail.DD_OrderLine_ID
-- 2024-08-06T11:54:07.679Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558429
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Produktionsstätte
-- Column: MD_Candidate_Dist_Detail.PP_Plant_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Produktionsstätte
-- Column: MD_Candidate_Dist_Detail.PP_Plant_ID
-- 2024-08-06T11:54:07.687Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558432
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Network Distribution Line
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistributionLine_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Network Distribution Line
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistributionLine_ID
-- 2024-08-06T11:54:07.695Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558430
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Sektion
-- Column: MD_Candidate_Dist_Detail.AD_Org_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Sektion
-- Column: MD_Candidate_Dist_Detail.AD_Org_ID
-- 2024-08-06T11:54:07.702Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558422
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Geplante Menge
-- Column: MD_Candidate_Dist_Detail.PlannedQty
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Geplante Menge
-- Column: MD_Candidate_Dist_Detail.PlannedQty
-- 2024-08-06T11:54:07.709Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561486
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Product Planning
-- Column: MD_Candidate_Dist_Detail.PP_Product_Planning_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Product Planning
-- Column: MD_Candidate_Dist_Detail.PP_Product_Planning_ID
-- 2024-08-06T11:54:07.715Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558427
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Lieferweg
-- Column: MD_Candidate_Dist_Detail.M_Shipper_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Lieferweg
-- Column: MD_Candidate_Dist_Detail.M_Shipper_ID
-- 2024-08-06T11:54:07.722Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558431
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Belegstatus
-- Column: MD_Candidate_Dist_Detail.DD_Order_DocStatus
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Belegstatus
-- Column: MD_Candidate_Dist_Detail.DD_Order_DocStatus
-- 2024-08-06T11:54:07.730Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558426
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Vom System vorgeschlagen
-- Column: MD_Candidate_Dist_Detail.IsAdvised
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Vom System vorgeschlagen
-- Column: MD_Candidate_Dist_Detail.IsAdvised
-- 2024-08-06T11:54:07.737Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2024-08-06 14:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561485
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Network Distribution
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistribution_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Network Distribution
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistribution_ID
-- 2024-08-06T11:54:42.580Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729137
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Network Distribution Line
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistributionLine_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Network Distribution Line
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistributionLine_ID
-- 2024-08-06T11:54:42.587Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558430
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Sektion
-- Column: MD_Candidate_Dist_Detail.AD_Org_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Sektion
-- Column: MD_Candidate_Dist_Detail.AD_Org_ID
-- 2024-08-06T11:54:42.594Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558422
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Geplante Menge
-- Column: MD_Candidate_Dist_Detail.PlannedQty
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Geplante Menge
-- Column: MD_Candidate_Dist_Detail.PlannedQty
-- 2024-08-06T11:54:42.599Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561486
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Product Planning
-- Column: MD_Candidate_Dist_Detail.PP_Product_Planning_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Product Planning
-- Column: MD_Candidate_Dist_Detail.PP_Product_Planning_ID
-- 2024-08-06T11:54:42.604Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558427
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- 2024-08-06T11:54:42.610Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729138
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- 2024-08-06T11:54:42.615Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729139
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Lieferweg
-- Column: MD_Candidate_Dist_Detail.M_Shipper_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Lieferweg
-- Column: MD_Candidate_Dist_Detail.M_Shipper_ID
-- 2024-08-06T11:54:42.620Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558431
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Belegstatus
-- Column: MD_Candidate_Dist_Detail.DD_Order_DocStatus
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Belegstatus
-- Column: MD_Candidate_Dist_Detail.DD_Order_DocStatus
-- 2024-08-06T11:54:42.627Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558426
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Vom System vorgeschlagen
-- Column: MD_Candidate_Dist_Detail.IsAdvised
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Vom System vorgeschlagen
-- Column: MD_Candidate_Dist_Detail.IsAdvised
-- 2024-08-06T11:54:42.634Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2024-08-06 14:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561485
;

