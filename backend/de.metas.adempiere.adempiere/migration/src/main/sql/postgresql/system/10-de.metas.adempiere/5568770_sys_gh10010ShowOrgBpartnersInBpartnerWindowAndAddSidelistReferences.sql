-- 2020-09-24T15:38:16.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541189,TO_TIMESTAMP('2020-09-24 18:38:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BPartner_Org_Masterdata_To_C_BPartner',TO_TIMESTAMP('2020-09-24 18:38:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-09-24T15:38:16.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541189 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-09-24T15:42:15.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2893,0,541189,291,123,TO_TIMESTAMP('2020-09-24 18:42:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2020-09-24 18:42:14','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from C_BPartner bp where bp.AD_OrgBP_ID=@AD_OrgBP_ID/-1@ and C_BPartner.C_BPartner_ID = bp.C_BPartner_ID)')
;

-- 2020-09-24T15:43:04.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541039,541189,540265,TO_TIMESTAMP('2020-09-24 18:43:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','C_Bpartner_Org_Masterdata -> C_Bpartner',TO_TIMESTAMP('2020-09-24 18:43:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-24T15:45:45.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='',Updated=TO_TIMESTAMP('2020-09-24 18:45:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=220
;

-- 2020-09-24T15:52:06.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@AD_OrgBP_ID@>0',Updated=TO_TIMESTAMP('2020-09-24 18:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=9620
;

-- 2020-09-24T15:52:56.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,9620,0,220,1000010,571536,'F',TO_TIMESTAMP('2020-09-24 18:52:56','YYYY-MM-DD HH24:MI:SS'),100,'The Business Partner is another Organization for explicit Inter-Org transactions','The business partner is another organization in the system. So when performing transactions, the counter-document is created automatically. Example: You have BPartnerA linked to OrgA and BPartnerB linked to OrgB.  If you create a sales order for BPartnerB in OrgA a purchase order is created for BPartnerA in OrgB.  This allows to have explicit documents for Inter-Org transactions.','Y','N','N','Y','N','N','N',0,'Linked Organization',6,0,0,TO_TIMESTAMP('2020-09-24 18:52:56','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

