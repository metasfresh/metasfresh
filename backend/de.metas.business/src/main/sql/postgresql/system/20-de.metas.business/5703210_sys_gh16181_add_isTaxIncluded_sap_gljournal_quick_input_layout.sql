-- Add the IsTaxIncluded to the QuickInput
SELECT set_sysconfig_value('SAP_GLJournalLine.quickInput.layout', 'PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,IsTaxIncluded?,C_Tax_ID?')
;
