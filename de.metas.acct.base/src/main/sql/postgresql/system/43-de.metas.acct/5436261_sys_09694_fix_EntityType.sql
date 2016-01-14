update AD_Column set EntityType='de.metas.acct' where AD_Table_ID=540695;
update AD_Table set EntityType='de.metas.acct' where AD_Table_ID=540695;
update AD_Process set EntityType='de.metas.acct' where AD_Process_ID=540634;

update AD_Tab_Callout set EntityType='de.metas.acct', classname='de.metas.acct.callout.GL_JournalLine_TabCallout' where classname='org.adempiere.acct.callout.GL_JournalLine_TabCallout';
update AD_Tab_Callout set EntityType='de.metas.acct', classname='de.metas.acct.callout.GL_Journal_TabCallout' where classname='org.adempiere.acct.callout.GL_Journal_TabCallout';

update AD_Process set EntityType='de.metas.acct', classname='de.metas.acct.process.C_ValidCombination_UpdateDescriptionForAll' where classname like '%C_ValidCombination_UpdateDescriptionForAll';



-- Fact_Acct_Summary:
update AD_Table set EntityType='de.metas.acct' where AD_Table_ID=53203;
update AD_Column set EntityType='de.metas.acct' where AD_Table_ID=53203;

