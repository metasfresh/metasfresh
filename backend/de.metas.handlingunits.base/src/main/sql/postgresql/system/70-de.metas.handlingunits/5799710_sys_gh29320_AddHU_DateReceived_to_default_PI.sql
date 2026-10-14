-- 2026-04-27T18:00:00
-- gh29320: attach HU_DateReceived (M_Attribute_ID=540036) to the Template PI version (100).
-- All other PI versions inherit it automatically via HUPIAttributesDAO.retrievePIAttributes which
-- merges Template attributes into every PI. PropagationType=TOPD with CopyHUAttributeTransferStrategy
-- (540027) and CopyAttributeSplitterStrategy (540017) so the value follows the HU across split/transform.
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,SplitterStrategy_JavaClass_ID,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2026-04-27 18:00:00','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','Y','N','N','N',540036 /*HU_DateReceived*/,540142 /*From ID Server*/,100,'TOPD',175,540017,TO_TIMESTAMP('2026-04-27 18:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;
