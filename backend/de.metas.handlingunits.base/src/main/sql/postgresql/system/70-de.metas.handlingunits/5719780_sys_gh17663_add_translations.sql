-- Value: de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG
-- 2024-03-20T18:33:45.687Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545384,0,TO_TIMESTAMP('2024-03-20 20:33:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','The HU cannot be picked! It might be that it is reserved to another doument or already picked/shipped.','E',TO_TIMESTAMP('2024-03-20 20:33:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG')
;

-- 2024-03-20T18:33:45.709Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545384 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG
-- 2024-03-20T18:36:02.937Z
UPDATE AD_Message SET MsgText='Die HU kann nicht kommissioniert! Es könnte sein, dass sie für ein anderes Dokument reserviert ist oder bereits kommissioniert/gesendet wurde.',Updated=TO_TIMESTAMP('2024-03-20 20:36:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545384
;

-- Value: de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG
-- 2024-03-20T18:36:06.832Z
UPDATE AD_Message_Trl SET MsgText='Die HU kann nicht kommissioniert! Es könnte sein, dass sie für ein anderes Dokument reserviert ist oder bereits kommissioniert/gesendet wurde.',Updated=TO_TIMESTAMP('2024-03-20 20:36:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545384
;

-- Value: de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG
-- 2024-03-20T18:36:09.315Z
UPDATE AD_Message_Trl SET MsgText='Die HU kann nicht kommissioniert! Es könnte sein, dass sie für ein anderes Dokument reserviert ist oder bereits kommissioniert/gesendet wurde.',Updated=TO_TIMESTAMP('2024-03-20 20:36:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545384
;

-- Value: de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG
-- 2024-03-20T18:36:11.856Z
UPDATE AD_Message_Trl SET MsgText='Die HU kann nicht kommissioniert! Es könnte sein, dass sie für ein anderes Dokument reserviert ist oder bereits kommissioniert/gesendet wurde.',Updated=TO_TIMESTAMP('2024-03-20 20:36:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545384
;

-- Value: de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG
-- 2024-03-20T18:36:29.883Z
UPDATE AD_Message_Trl SET MsgText='The HU cannot be picked! It could be that it is reserved for another document or has already been picked/shipped.',Updated=TO_TIMESTAMP('2024-03-20 20:36:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545384
;

-- Element: IsAlwaysSplitHUsEnabled
-- 2024-03-20T18:38:41.596Z
UPDATE AD_Element_Trl SET Description='If checked, the selected HU is always split before picking.',Updated=TO_TIMESTAMP('2024-03-20 20:38:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583041 AND AD_Language='de_CH'
;

-- 2024-03-20T18:38:41.603Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583041,'de_CH') 
;

-- Element: IsAlwaysSplitHUsEnabled
-- 2024-03-20T18:39:00.883Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, wird die ausgewählte HU vor der Kommissionierung immer aufgeteilt.',Updated=TO_TIMESTAMP('2024-03-20 20:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583041 AND AD_Language='de_DE'
;

-- 2024-03-20T18:39:00.886Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583041,'de_DE') 
;

-- 2024-03-20T18:39:00.888Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583041,'de_DE') 
;

-- Element: IsAlwaysSplitHUsEnabled
-- 2024-03-20T18:39:20.021Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, wird die ausgewählte HU vor der Kommissionierung immer aufgeteilt.',Updated=TO_TIMESTAMP('2024-03-20 20:39:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583041 AND AD_Language='fr_CH'
;

-- 2024-03-20T18:39:20.024Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583041,'fr_CH') 
;

-- Element: IsAlwaysSplitHUsEnabled
-- 2024-03-20T18:39:29.329Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, wird die ausgewählte HU vor der Kommissionierung immer aufgeteilt.',Updated=TO_TIMESTAMP('2024-03-20 20:39:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583041 AND AD_Language='de_CH'
;

-- 2024-03-20T18:39:29.331Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583041,'de_CH') 
;

-- Element: IsAlwaysSplitHUsEnabled
-- 2024-03-20T18:39:37.105Z
UPDATE AD_Element_Trl SET Description='If checked, the selected HU is always split before picking.',Updated=TO_TIMESTAMP('2024-03-20 20:39:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583041 AND AD_Language='en_US'
;

-- 2024-03-20T18:39:37.108Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583041,'en_US') 
;

-- Element: IsAlwaysSplitHUsEnabled
-- 2024-03-20T18:40:10.202Z
UPDATE AD_Element_Trl SET Name='HU immer splitten', PrintName='HU immer splitten',Updated=TO_TIMESTAMP('2024-03-20 20:40:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583041 AND AD_Language='de_CH'
;

-- 2024-03-20T18:40:10.205Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583041,'de_CH') 
;

-- Element: IsAlwaysSplitHUsEnabled
-- 2024-03-20T18:40:17.767Z
UPDATE AD_Element_Trl SET Name='HU immer splitten', PrintName='HU immer splitten',Updated=TO_TIMESTAMP('2024-03-20 20:40:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583041 AND AD_Language='de_DE'
;

-- 2024-03-20T18:40:17.770Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583041,'de_DE') 
;

-- 2024-03-20T18:40:17.784Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583041,'de_DE') 
;

-- Element: IsAlwaysSplitHUsEnabled
-- 2024-03-20T18:40:26.729Z
UPDATE AD_Element_Trl SET Name='HU immer splitten', PrintName='HU immer splitten',Updated=TO_TIMESTAMP('2024-03-20 20:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583041 AND AD_Language='fr_CH'
;

-- 2024-03-20T18:40:26.732Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583041,'fr_CH') 
;

